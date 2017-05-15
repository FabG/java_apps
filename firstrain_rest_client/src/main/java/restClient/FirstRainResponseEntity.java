package restClient;
import java.io.*;

/**
 * Created by Fab on 5/15/17.
 */
public class FirstRainResponseEntity {
    private String name;
    private String searchToken;
    private String relevanceScore;
    private String relevanceBand;

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

    public String getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(String relevanceScore) {
        this.relevanceScore = relevanceScore;
    }

    public String getRelevanceBand() {
        return relevanceBand;
    }

    public void setRelevanceBand(String relevanceBand) {
        this.relevanceBand = relevanceBand;
    }


    @Override
    public String toString() {

        if (searchToken.startsWith("C:")) {
            return "{name=" + name +
                    ", searchToken=" + searchToken +
                    ", relevanceScore='" + relevanceScore +
                    ", relevanceBand=" + relevanceBand +
                    "}";
        } else
            return null;
    }
}
