package restClient;

import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.text.SimpleDateFormat;
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
        Properties prop = new Properties();
        InputStream config = null;

        try {
            config = new FileInputStream("src/resources/development.properties");
            prop.load(config);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        final String baseURI = prop.getProperty("baseURI");

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(baseURI)
                .queryParam("sections", "{fr(ic:500,io:0)}")
                .queryParam("results", "{D}")
                .queryParam("entitylinks", true);
        URI uri = builder.build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("frUserId", prop.getProperty("frUserId"));
        headers.add("authKey", prop.getProperty("authKey"));
        headers.add("Accept", "application/json");

        RestTemplate firstRainRest = new RestTemplate();
        try {
            ResponseEntity<FirstRainResponseRoot> response = firstRainRest.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<String>(headers),
                    FirstRainResponseRoot.class);

            //log.info(response.toString());
            log.info("REST Results for Topic: [" + response.getBody().getResult().getName() + "] and id: [" + response.getBody().getResult().getId() + "]");
            log.info("Total Items Count: [" + response.getBody().getResult().getData().getFr().getTotalItemCount() + "]");


            // TSV File export
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String fileName = "firstrain_company_website_" + dateFormat.format(date) + ".tsv";
            String eol = System.getProperty("line.separator");

            try (Writer writer = new FileWriter(fileName)) {
                writer.append("FirstRainCompany\tWebsite\tFirstRainID\ttimeStamp\tsourceName\tsourceSearchToken\tlink\ttitle\tquotes").append(eol);

                // Loop through each document
                for (FirstRainResponseDocs document : response.getBody().getResult().getData().getFr().getDocuments()
                        ) {
                    // Loop through each entity and only keep the companies
                    for (FirstRainResponseEntity entity : document.getEntity()
                            ) {
                        if (entity.getSearchToken().startsWith("C:")) {
                            String company = entity.getSearchToken().toString().substring(2);
                            String companyURI = prop.getProperty("companyURI") + company + "/map";

                            // Issue a 2nd REST request to get the web url for the company just extracted
                            UriComponentsBuilder builderCompany = UriComponentsBuilder
                                    .fromHttpUrl(companyURI);
                            URI uriC = builderCompany.build().toUri();
                            RestTemplate firstRainRestC = new RestTemplate();
                            try {
                                ResponseEntity<FirstRainResponseRoot> responseC = firstRainRestC.exchange(
                                        uriC,
                                        HttpMethod.GET,
                                        new HttpEntity<String>(headers),
                                        FirstRainResponseRoot.class);

                                log.info("REST Results for Company: [" + company + "] " + responseC.getBody().getResult().getName() + "] => Website: " + responseC.getBody().getResult().getData().getEntityMap().getWebsite());
                                String companyWebsite = responseC.getBody().getResult().getData().getEntityMap().getWebsite();

                                // Append to TSV the company, its website and the parent article information
                                writer.append(company).append("\t").
                                        append(companyWebsite).append("\t").
                                        append(document.getId().toString()).append("\t").
                                        append(document.getTimeStamp()).append("\t").
                                        append(document.getSource().getName()).append("\t").
                                        append(document.getSource().getSearchToken()).append("\t").
                                        append(document.getLink()).append("\t").
                                        append(document.getTitle()).append("\t").
                                        append(document.getQuotes()).append("\t").append(eol);

                            } catch (HttpClientErrorException e) {
                                throw e;
                            }

                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }

        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
}
