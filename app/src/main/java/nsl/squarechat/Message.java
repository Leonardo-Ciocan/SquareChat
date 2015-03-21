package nsl.squarechat;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Message")
public class Message extends ParseObject {


    public String getSquare() {
        return getString("square");
    }

    public ParseUser getFrom() {
        return getParseUser("from");

    }
    public void setFrom(ParseUser userId) {
        put("from", userId);
    }

    public ParseUser getTo() {
        return getParseUser("to");
    }
    public void setTo(ParseUser userId) {
        put("to", userId);
    }

    public void setSquare(String body) {
        put("square", body);
    }
}