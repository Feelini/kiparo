package com.kiparo.news;

import org.json.JSONException;
import org.json.JSONObject;

public class MediaEntity {

    private String url;

    public MediaEntity(JSONObject jsonObject) throws JSONException {
        url = jsonObject.getString("url");
    }

    public String getUrl() {
        return url;
    }
}
