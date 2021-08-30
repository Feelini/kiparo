package com.kiparo.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<NewsEntity> newsItemList;
    private Handler handler = new Handler(Looper.getMainLooper());
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        recyclerView = findViewById(R.id.list);
        newsItemList = new ArrayList<>();
        loadResource(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadResource(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.nytimes.com/svc/topstories/v2/technology.json?api-key=19gxuR7LBHOuqiZm7iyV6Jq84EBXIhhx");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    String readStream = readStream(con.getInputStream());
                    callback.onResult(readStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void onResult(final String data) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject;

                try {
                    jsonObject = new JSONObject(data);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject newsObject = resultArray.getJSONObject(i);
                        NewsEntity newsEntity = new NewsEntity(newsObject);
                        newsItemList.add(newsEntity);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "fail to parse json string");
                }

                NewsListAdapter adapter = new NewsListAdapter(newsItemList, new NewsListAdapter.OnClickListener() {
                    @Override
                    public void onItemClick(NewsEntity newsEntity) {
                        String title = newsEntity.getTitle();
                        String storyURL = newsEntity.getArticleUrl();
                        String summary = newsEntity.getSummary();
                        String imageURL = newsEntity.getMediaEntity().get(0).getUrl();
                        Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
                        intent.putExtra("detailNews", new DetailNewsEntity(title, storyURL, summary, imageURL));
                        startActivity(intent);
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setAdapter(adapter);
            }
        }, 0);
    }
}
