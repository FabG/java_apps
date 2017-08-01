package restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Fab on 5/15/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FirstRainResponseDocs {
    private String id;
    private String contentType;
    private String timeStamp;
    private FirstRainResponseSource source;
    private String link;
    private String title;
    private String groupId;
    private String snippet;
    private String quotes;
    private List<FirstRainResponseEntity> entity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public FirstRainResponseSource getSource() {
        return source;
    }

    public void setSource(FirstRainResponseSource source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<FirstRainResponseEntity> getEntity() {
        return entity;
    }

    public void setEntity(List<FirstRainResponseEntity> entity) {
        this.entity = entity;
    }

    public String getSnippet() {
        if (snippet != null)
            return snippet.substring(1,50);
        else
            return null;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", entity=" + entity +
                ", timeStamp='" + timeStamp +
                ", groupId=" + groupId +
                ", snippet=" + snippet +
                ", quotes=" + quotes +
                "}";
    }
}
