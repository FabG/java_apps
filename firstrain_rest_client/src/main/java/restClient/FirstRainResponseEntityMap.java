package restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fab on 5/15/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class FirstRainResponseEntityMap {
    private String website;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "entityMap {" +
                "website =" + website +
                "}";
    }

}
