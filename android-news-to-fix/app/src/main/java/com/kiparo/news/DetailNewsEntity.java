package com.kiparo.news;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailNewsEntity implements Parcelable {

    private String title;
    private String storyURL;
    private String summary;
    private String imageURL;

    public DetailNewsEntity(String title, String storyURL, String summary, String imageURL) {
        this.title = title;
        this.storyURL = storyURL;
        this.summary = summary;
        this.imageURL = imageURL;
    }

    public DetailNewsEntity(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        this.title = data[0];
        this.storyURL = data[1];
        this.summary = data[2];
        this.imageURL = data[3];
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStoryURL() {
        return storyURL;
    }

    public void setStoryURL(String storyURL) {
        this.storyURL = storyURL;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{title, storyURL, summary, imageURL});
    }

    public static final Parcelable.Creator<DetailNewsEntity> CREATOR = new Parcelable.Creator<DetailNewsEntity>() {

        @Override
        public DetailNewsEntity createFromParcel(Parcel source) {
            return new DetailNewsEntity(source);
        }

        @Override
        public DetailNewsEntity[] newArray(int size) {
            return new DetailNewsEntity[size];
        }
    };
}
