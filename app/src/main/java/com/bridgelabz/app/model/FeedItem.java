package com.bridgelabz.app.model;

/**
 * Created by eshvar289 on 17/7/16.
 */

public class FeedItem {
    private int id;
    private String name,status,image,profile,timeStamp,url;
    public FeedItem(){
    }

    public FeedItem(int id, String name, String status, String image, String profile, String timeStamp, String url) {
        super();
        this.id = id;
        this.name = name;
        this.status = status;
        this.image = image;
        this.profile = profile;
        this.timeStamp = timeStamp;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
