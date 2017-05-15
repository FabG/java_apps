package restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fab on 5/9/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstRainResponseData {
    FirstRainResponseFr fr;
    FirstTainResponseEntityMap entityMap;

    public FirstRainResponseData() {
    }

    public void setFr(FirstRainResponseFr fr) {
        this.fr = fr;
    }

    public FirstRainResponseFr getFr() {
        return fr;
    }

    public FirstTainResponseEntityMap getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(FirstTainResponseEntityMap entityMap) {
        this.entityMap = entityMap;
    }

    @Override
    public String toString() {
        return "fr {" + fr +
                "}";
    }
}
