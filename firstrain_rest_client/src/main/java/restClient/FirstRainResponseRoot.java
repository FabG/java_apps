package restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fab on 5/9/17.
 */
// Class to store root level data from FirstRain
// {
//  "message": "Data populated successfully.",
//  "result": {...}.
//  "version": "v1",
//  "status": "SUCCESS"
// }

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstRainResponseRoot {
    private String status;
    private String version;
    private String errorCode;
    private FirstRainResponseResult result;

    public FirstRainResponseRoot() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public FirstRainResponseResult getResult() {
        return result;
    }

    public void setResult(FirstRainResponseResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "root {" +
                "status='" + status +
                ", version=" + version +
                ", result=" + result +
                ", error_code=" + errorCode +
                "}";    }

}
