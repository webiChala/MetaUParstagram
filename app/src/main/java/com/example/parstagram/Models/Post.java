package com.example.parstagram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Post")
//@Parcel
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_USERSLIKED = "usersliked";

    public Post() {
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }
    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public User getUser() {
        return (User) getParseUser(KEY_USER);
    }

    public void setUser(User user) {
        put(KEY_USER, user);
    }

    public String likeCountDisplayText() {
        String likestext = String.valueOf(getLike().size());
        likestext += getLike().size() == 1 ? " like" : " likes" ;
        return likestext;
    }

    public List<String> getLike() {
        List<String> likedBy = getList(KEY_USERSLIKED);
        if (likedBy == null) {
            likedBy = new ArrayList<>();
        }
        return likedBy;
    }

    public void setUserLike(List<String> likedBy) {put(KEY_USERSLIKED, likedBy);}


}
