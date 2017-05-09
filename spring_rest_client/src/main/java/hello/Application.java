package hello;

import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


/**
 * Created by Fab on 5/9/17.
 */
public class Application {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        log.info(quote.toString());
    }

}