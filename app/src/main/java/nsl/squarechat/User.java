package nsl.squarechat;

import android.graphics.Bitmap;

public class User {
    Bitmap avatar;
    String name;

    public User(Bitmap avatar, String name) {
        this.avatar = avatar;
        this.name = name;
    }

    public Bitmap getAvatar() {

        return avatar;
    }

    public String getName() {
        return name;
    }
}
