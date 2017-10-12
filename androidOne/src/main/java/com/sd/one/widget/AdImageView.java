/*
    Easy Android Client, AnimationTabHost
    Copyright (c) 2015 Easy Tech Company Limited
 */

package com.sd.one.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sd.one.R;
import com.sd.one.utils.CommonUtils;

/**
 * [广告ImageView]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2015-5-18
 * 
 **/
public class AdImageView extends AppCompatImageView {

	public AdImageView(Context context) {
		super(context);
		setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		setBackgroundResource(R.drawable.icon_default);
	}

	public AdImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public synchronized void setUrl(String url) {
		this.setTag(url);

		ImageLoader.getInstance().displayImage(url, this, CommonUtils.getAdOptions(), new SimpleImageLoadingListener() {
			@Override
		    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		    	((ImageView)view).setImageBitmap(null);
		    	((ImageView)view).setBackgroundDrawable(new BitmapDrawable(loadedImage));
		    }
		});
	}
}