package com.kiparo.news;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsEntity {

    private static final String TAG = NewsEntity.class.getSimpleName();

    private String title;
    private String summary;
    private String articleUrl;
    private List<MediaEntity> mediaEntityList;

    public NewsEntity(JSONObject jsonObject) {
        try {
            mediaEntityList = new ArrayList<>();
            title = jsonObject.getString("title");
            summary = jsonObject.getString("abstract");
            articleUrl = jsonObject.getString("url");
            JSONArray mediaArray = jsonObject.getJSONArray("multimedia");
            for (int i = 0; i < mediaArray.length(); i++) {
                JSONObject mediaObject = mediaArray.getJSONObject(i);
                MediaEntity mediaEntity = new MediaEntity(mediaObject);
                mediaEntityList.add(mediaEntity);
            }
        } catch (JSONException exception) {
            Log.e(TAG, exception.getMessage());
        }
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public List<MediaEntity> getMediaEntity() {
        return mediaEntityList;
    }
}
