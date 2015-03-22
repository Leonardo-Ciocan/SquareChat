package nsl.squarechat;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Avatar")
public class Avatar extends ParseObject {

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public void setUser(ParseUser user){
        put("user" , user);
    }

    public String getAvatar(){
        return  getString("data");
    }

    public void setAvatar(String data){
        put("data" , data);
    }

    public String getFavorite(){
        return  getString("favorite");
    }

    public void setFavorite(String data){
        put("favorite" , data);
    }
}
