package com.licheedev.glideissue;

import android.graphics.*;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.view.*;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.*;
import com.bumptech.glide.util.LogTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.bumptech.glide.util.LogTime.getElapsedMillis;

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
            File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            File doge1 = new File(downloads, "doge1.jpg");
            File doge2 = new File(downloads, "doge2.jpg");
            File doge3 = new File(downloads, "doge3.jpg");

            loadDirectly(mIvOrigin, doge1);
            loadGlide(mIvGlide, doge2);
            loadDirectly(mIvOrigin2, doge3);
        }
    }

    private void loadGlide(final ImageView imageView, final File file) {
        Log.i("GLIDE", "Load " + file + " into " + imageView);
        final long fullTime = LogTime.getLogTime();
        Glide.with(this)
             .load(file)
             .asBitmap()
             .diskCacheStrategy(DiskCacheStrategy.NONE)
             .dontAnimate()
             .into(new BitmapImageViewTarget(imageView) {
                @Override protected void setResource(Bitmap resource) {
                    Log.d("GLIDE", file + " loaded in " + getElapsedMillis(fullTime) + " into " + imageView);
                    super.setResource(resource);
                }
            });
    }

    public void loadDirectly(final ImageView imageView, final File file) {
        Log.i("DIRECT", "Load " + file + " into " + imageView);
        final long fullTime = LogTime.getLogTime();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileInputStream fis = null;
                try {
                    long startTime = LogTime.getLogTime();
                    fis = new FileInputStream(file);
                    final Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    Log.v("DIRECT", "Decoded " + file + " in " + getElapsedMillis(startTime));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("DIRECT", file + " loaded in " + getElapsedMillis(fullTime) + " into " + imageView);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        item(menu, 1, "Clear cache", "#888888");
        item(menu, 2, "Clear memory cache", "#888800");
        item(menu, 3, "Click disk cache", "#ff8800");
        item(menu, 4, "Clear image views", "#ff0000");
        return true;
    }

    private void item(Menu menu, int id, String title, String color) {
        Drawable icon = ContextCompat.getDrawable(this, android.R.drawable.checkbox_off_background).mutate();
        icon.setColorFilter(Color.parseColor(color), Mode.SCREEN);
        menu.add(0, id, 0, title)
            .setIcon(icon)
            .setShowAsAction(MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                new ClearCachesTask(this, true, true).execute();
                return true;
            case 2:
                new ClearCachesTask(this, true, false).execute();
                return true;
            case 3:
                new ClearCachesTask(this, false, true).execute();
                return true;
            case 4:
                Glide.clear(mIvGlide);
                Glide.clear(mIvOrigin);
                Glide.clear(mIvOrigin2);
                mIvGlide.setImageDrawable(null);
                mIvOrigin.setImageDrawable(null);
                mIvOrigin2.setImageDrawable(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
