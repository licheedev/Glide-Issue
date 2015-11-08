package com.licheedev.glideissue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvOrigin;
    private ImageView mIvGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mIvOrigin = (ImageView) findViewById(R.id.ivOrigin);
        mIvGlide = (ImageView) findViewById(R.id.ivGlide);


    }


    public void load(View view) {
        mIvOrigin.setImageResource(R.drawable.doge);
        Glide.with(this)
            .load(R.drawable.doge2)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .into(mIvGlide);
        //mIvGlide.setImageResource(R.drawable.doge2);
    }
}
