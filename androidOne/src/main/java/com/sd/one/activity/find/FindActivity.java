/*
    ShengDao Android Client, CartActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.find;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.activity.adapter.VerticalBannerAdapter;
import com.sd.one.widget.verticalbanner.VerticalBannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * [工具页面]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 *
 **/
public class FindActivity extends BaseActivity implements View.OnClickListener{

	private VerticalBannerAdapter verticalAdapter;
	private VerticalBannerView bannerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_layout);

		tv_title.setText(getString(R.string.menu_find_title));
		tv_title.setFocusable(true);
		tv_title.setFocusableInTouchMode(true);
		tv_title.requestFocus();

		btn_left.setVisibility(View.GONE);
		btn_right.setVisibility(View.GONE);

		verticalAdapter = new VerticalBannerAdapter();
		bannerView = getViewById(R.id.banner_vertical);
		bannerView.setOnClickListener(this);

		List<String> list = new ArrayList<>();
		list.add("港币兑人民币今天的汇率是8.5");
		list.add("香港今天台风所有店铺停止营业");
		verticalAdapter.setData(list);
		bannerView.setAdapter(verticalAdapter);
		bannerView.stop();
		bannerView.start();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return getParent().onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {

	}
}
