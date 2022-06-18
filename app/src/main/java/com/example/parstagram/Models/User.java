package com.example.parstagram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {

    public static final String KEY_USERIMAGE = "userImage";

    public ParseFile getImage() {
        return getParseFile(KEY_USERIMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_USERIMAGE, parseFile);
    }

    public User(){

    }
}
