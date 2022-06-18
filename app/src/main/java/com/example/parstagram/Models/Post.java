package com.example.parstagram.Models;

import android.text.format.DateUtils;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ParseClassName("Post")
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
        likestext += getLike().size() == 1 ? " likes" : " like" ;
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

    public String getRelativeCreatedDate() {
        return getRelativeTimeAgo(getCreatedAt().toString());
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


}
