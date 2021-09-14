package com.kiparo.news;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.security.InvalidParameterException;

public class DetailViewActivity extends Activity {
    private DetailNewsEntity detailNewsEntity;
    public static final String DETAIL_NEWS = "detailNews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailNewsEntity = getIntent().getParcelableExtra(DETAIL_NEWS);

        if (detailNewsEntity == null){
            throw new InvalidParameterException("detailNews is required field");
        }

        TextView titleView = (TextView) findViewById(R.id.title);
        DraweeView imageView = (DraweeView) findViewById(R.id.news_image);
        TextView summaryView = (TextView) findViewById(R.id.summary_content);

        titleView.setText(detailNewsEntity.getTitle());
        summaryView.setText(detailNewsEntity.getSummary());

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(detailNewsEntity.getImageURL())))
                .setOldController(imageView.getController()).build();
        imageView.setController(draweeController);
    }

    public void onFullStoryClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(detailNewsEntity.getStoryURL()));
        startActivity(intent);
    }
}
