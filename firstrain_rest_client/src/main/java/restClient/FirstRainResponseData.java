package restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fab on 5/9/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstRainResponseData {
    FirstRainResponseFr fr;

    public FirstRainResponseData() {
    }

    public void setFr(FirstRainResponseFr fr) {
        this.fr = fr;
    }

    public FirstRainResponseFr getFr() {
        return fr;
    }

    @Override
    public String toString() {
        return "fr {" + fr +
                "}";
    }
}
