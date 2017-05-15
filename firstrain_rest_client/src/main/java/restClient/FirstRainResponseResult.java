package restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fab on 5/9/17.
 */

// Class to hold data from FirsRain
//"result": {
//        "name": "F500—CorpInitiatives&Rebranding",
//        "id": "M:548853",
//        "data": {...}
//        }

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstRainResponseResult {
    private String name;
    private String id;
    private FirstRainResponseData data;

    public FirstRainResponseResult() {
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FirstRainResponseData getData() {
        return data;
    }

    public void setData(FirstRainResponseData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "result {" +
                "name='" + name +
                ", id=" + id +
                ", data=" + data +
                "}";    }
}
