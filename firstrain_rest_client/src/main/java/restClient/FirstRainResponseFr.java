package restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Fab on 5/15/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstRainResponseFr {
    private List<FirstRainResponseDocs> documents;
    private int totalItemCount;
    private int itemOffset;
    private int itemCount;

    public FirstRainResponseFr() {
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getItemOffset() {
        return itemOffset;
    }

    public void setItemOffset(int itemOffset) {
        this.itemOffset = itemOffset;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public void setDocuments(List<FirstRainResponseDocs> documents) {
        this.documents = documents;
    }

    public List<FirstRainResponseDocs> getDocuments() {
        return documents;
    }

    @Override
    public String toString() {
        return "documents [" + documents + "]" +
                "totalItemCount='" + totalItemCount +
                "}";
    }
}
