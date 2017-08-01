package restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fab on 5/15/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstRainResponseSource {
    private String name;
    private String searchToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchToken() {
        return searchToken;
    }

    public void setSearchToken(String searchToken) {
        this.searchToken = searchToken;
    }
}
