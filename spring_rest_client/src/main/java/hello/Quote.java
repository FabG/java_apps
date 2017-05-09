package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.org.apache.xpath.internal.operations.Quo;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

/**
 * Created by Fab on 5/9/17.
 */
@JsonIgnoreProperties(ignoreUnknown =  true)
public class Quote {

    private  String type;
    private  Value value;

    public Quote() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "type='" + type + '\'' +
                ", value=" + value +
                "}";
    }

}
