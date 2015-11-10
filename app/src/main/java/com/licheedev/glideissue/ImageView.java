package com.licheedev.glideissue;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.*;

public class ImageView extends android.widget.ImageView {
	public ImageView(Context context) {
		super(context);
	}
	public ImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public ImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.v("IMAGE", "onMeasure " + this);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		Log.v("IMAGE", "onLayout " + this);
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override protected void onDraw(@NonNull Canvas canvas) {
		Log.v("IMAGE", "onDraw " + this);
		super.onDraw(canvas);
	}
}
