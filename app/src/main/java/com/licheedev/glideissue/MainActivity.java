package com.licheedev.glideissue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvOrigin;
    private ImageView mIvGlide;
    private ImageView mIvOrigin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvOrigin = (ImageView) findViewById(R.id.ivOrigin1);
        mIvGlide = (ImageView) findViewById(R.id.ivGlide);
        mIvOrigin2 = (ImageView) findViewById(R.id.ivOrigin2);
        
    }


    public void load(View view) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdcard = Environment.getExternalStorageDirectory();
            File doge1 = new File(sdcard, "Download/doge1.jpg");
            File doge2 = new File(sdcard, "Download/doge2.jpg");
            File doge3 = new File(sdcard, "Download/doge3.jpg");

            loadDirectly(mIvOrigin, doge1);
            
            Glide.with(this)
                .load(doge2)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .into(mIvGlide);
            
            loadDirectly(mIvOrigin2, doge3);

        }
    }

    public void loadDirectly(final ImageView imageView, final File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    final Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        
    }
}
