package nsl.squarechat;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {


    public String getSquare() {
        return getString("square");
    }

    public String getFrom() {
        return getString("from");
    }
    public void setFrom(String userId) {
        put("from", userId);
    }

    public String getTo() {
        return getString("to");
    }
    public void setTo(String userId) {
        put("to", userId);
    }

    public void setSquare(String body) {
        put("square", body);
    }
}