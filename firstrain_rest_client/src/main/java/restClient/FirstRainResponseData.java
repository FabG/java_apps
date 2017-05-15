package restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fab on 5/9/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstRainResponseData {
    FirstRainResponseFr fr;
    FirstRainResponseEntityMap entityMap;

    public FirstRainResponseData() {
    }

    public void setFr(FirstRainResponseFr fr) {
        this.fr = fr;
    }

    public FirstRainResponseFr getFr() {
        return fr;
    }

    public FirstRainResponseEntityMap getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(FirstRainResponseEntityMap entityMap) {
        this.entityMap = entityMap;
    }

    @Override
    public String toString() {
        return "fr {" + fr +
                "}";
    }
}
