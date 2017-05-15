package restClient;

import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.*;

/**
 * Created by Fab on 5/8/17.
 */
// Simple Rest Client to consume the FirstRain REST API and extract company name/domain info so we can match our topcards against
// SIQ-1906
@Service
public class Application {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        final String baseURI  = "https://api.firstrain.com/standard/v1/monitor/M:548853/brief";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(baseURI)
                .queryParam("sections", "{fr}")
                .queryParam("results", "{D}")
                .queryParam("entitylinks", true);
        URI uri = builder.build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("frUserId", "U:4733821");
        headers.add("Accept", "application/json");
        headers.add("authKey", "f1ea689a73fe233decf6ea9fd70d8b97dfd548e7a4d32954d1cbec861505939c");

        RestTemplate firstRainRest = new RestTemplate();
        try {
            ResponseEntity<FirstRainResponseRoot> response = firstRainRest.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<String>(headers),
                    FirstRainResponseRoot.class);

            log.info(response.toString());
            log.info("REST Results for Topic: [" + response.getBody().getResult().getName() + "] and id: [" + response.getBody().getResult().getId() + "]");
            log.info("Total Items Count: [" + response.getBody().getResult().getData().getFr().getTotalItemCount() + "]");

            // Store Tuples: {Document1, [C1, C2,...], Document2, [C1,C4,...]} when there is a company or more for a given document
            Map<String, List<String>> docCompany = new LinkedHashMap<String, List<String>>();

            // Can also store as a set the unique companies
            Set<String> companySet = new HashSet<String>();

            for (FirstRainResponseDocs document:response.getBody().getResult().getData().getFr().getDocuments()
                 ) {
                docCompany.put(document.getId().toString(), new LinkedList<String>());
                //log.info("DocumentId: " + document.getId() + "\t\t" + "contentType: [" + document.getContentType() + "] \t\timeStamp: [" + document.getTimeStamp() + "]");
                for (FirstRainResponseEntity entity:document.getEntity()
                     ) {
                    if (entity.getSearchToken().startsWith("C:")) {
                        String company = entity.getSearchToken().toString().substring(2);
                        docCompany.get(document.getId()).add(company);
                        companySet.add(company);
                        //log.info("==> CompanyId: " + entity.getSearchToken().toString().substring(2));
                    }
                }
            }
            log.info("Total Documents: [" + docCompany.size() + "]");
            log.info(docCompany.toString());
            log.info("Total Companies: [" + companySet.size() + "]");
            log.info(companySet.toString());

            // For each Company, issue a 2nd REST request to get the web url
            //int sum = 0;
            // Store {companyName, CompanyUrl} as tuples
            Map<String, String> companyWebsite = new HashMap<String, String>();

            for (String company:companySet
                    ) {
                final String uriCompany  = "https://api.firstrain.com/standard/v1/entity/C:" + company + "/map";
                //sum += 1;
                //if (sum == 4) break;

                UriComponentsBuilder builderCompany = UriComponentsBuilder
                        .fromHttpUrl(uriCompany);
                URI uriC = builderCompany.build().toUri();
                RestTemplate firstRainRestC = new RestTemplate();
                try {
                    ResponseEntity<FirstRainResponseRoot> responseC = firstRainRestC.exchange(
                            uriC,
                            HttpMethod.GET,
                            new HttpEntity<String>(headers),
                            FirstRainResponseRoot.class);

                    log.info("REST Results for Company: [" + company + "] " + responseC.getBody().getResult().getName() + "] => Website: " + responseC.getBody().getResult().getData().getEntityMap().getWebsite());
                    companyWebsite.put(company, responseC.getBody().getResult().getData().getEntityMap().getWebsite());

                } catch (HttpClientErrorException e) {
                    throw e;
                }
            }

            log.info("Total Companies + Website: [" + companyWebsite.size() + "]");
            log.info(companyWebsite.toString());

            // Write to CSV file
            String fileName = "firstrain_company_website" + new Date().getTime() + ".csv";
            String eol = System.getProperty("line.separator");

            try (Writer writer = new FileWriter(fileName)) {
                for (Map.Entry<String, String> entry : companyWebsite.entrySet()) {
                    writer.append(entry.getKey())
                            .append(',')
                            .append(entry.getValue())
                            .append(eol);
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }

        } catch (HttpClientErrorException e) {
            throw e;
        }




    }
}
