package restClient;

import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
            log.info("TimeStamp: [" + response.getBody().getResult().getData().getFr().getDocuments() + "]");
            log.info("Total Items Count: [" + response.getBody().getResult().getData().getFr().getTotalItemCount() + "]");
            //return response.getBody();

            // Add logic to only store company names from entity = C:<name>
            for (FirstRainResponseDocs document:response.getBody().getResult().getData().getFr().getDocuments()
                 ) {
                log.info("DocumentId: " + document.getId() + "\t\t" + "contentType: [" + document.getContentType() + "] \t\ttimeStamp: [" + document.getTimeStamp() + "]");
                for (FirstRainResponseEntity entity:document.getEntity()
                     ) {
                    if (entity.getSearchToken().startsWith("C:")) {
                        //log.info("==> CompanyId: [" + entity.getSearchToken().toString().substring(2) + "]\t\t" + "relevanceScore: [" + entity.getRelevanceScore() + "]");
                        log.info("==> CompanyId: " + entity.getSearchToken().toString().substring(2));

                    }
                }
            }
        } catch (HttpClientErrorException e) {
            throw e;
    }

    }
}
