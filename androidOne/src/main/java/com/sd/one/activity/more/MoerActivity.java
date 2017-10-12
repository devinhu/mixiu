/*
    ShengDao Android Client, MoerActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.activity.customer.CustomerActivity;

/**
 * [更多页面]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class MoerActivity extends BaseActivity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_layout);

		tv_title.setText(getString(R.string.menu_member_title));
		tv_title.setFocusable(true);
		tv_title.setFocusableInTouchMode(true);
		tv_title.requestFocus();

		btn_left.setVisibility(View.GONE);
		btn_right.setVisibility(View.GONE);

		//客户
		findViewById(R.id.my_album_layout).setOnClickListener(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return getParent().onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.my_album_layout:
				Intent intent = new Intent(mContext, CustomerActivity.class);
				startActivity(intent);
				break;
		}
	}
}
