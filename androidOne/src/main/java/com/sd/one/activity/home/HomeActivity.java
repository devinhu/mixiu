/*
    ShengDao Android Client, HomeActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.activity.adapter.PlanAdapter;
import com.sd.one.activity.adapter.TabPagerAdapter;
import com.sd.one.activity.adapter.ViewPagerAdapter;
import com.sd.one.common.async.HttpException;
import com.sd.one.utils.db.entity.Order;
import com.sd.one.widget.AdImageView;
import com.sd.one.widget.NoScrollerListView;
import com.sd.one.widget.PagerSlidingTabStrip;
import com.sd.one.widget.autopager.AutoScrollViewPager;
import com.sd.one.widget.autopager.CirclePageIndicator;
import com.sd.one.widget.dialog.LoadDialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class HomeActivity extends BaseActivity implements View.OnClickListener {

	public static final int GET_PLAN_LIST = 1002;
	public static final int GET_FINISH_LIST = 1003;

	private AutoScrollViewPager adViewpager;
	private CirclePageIndicator indicator;

	private TextView tv_plan, tv_finish;
	private PlanAdapter planAdapter, finishAdapter;
	private NoScrollerListView plan_listview, finish_listview;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);

		tv_title.setText(getString(R.string.menu_order_title));
		tv_title.setFocusable(true);
		tv_title.setFocusableInTouchMode(true);
		tv_title.requestFocus();

		setBtnLeft(R.drawable.nav_back);
		btn_left.setVisibility(View.GONE);
		btn_left.setOnClickListener(this);
		setBtnRight(R.drawable.nav_back);
		btn_right.setVisibility(View.GONE);
		btn_right.setOnClickListener(this);

		adViewpager = getViewById(R.id.adViewpager);
		indicator = getViewById(R.id.indicator);

		List<String> list = new ArrayList<>();
		list.add("http://ad.qulover.com/o_1b3i202l0kgn1afu1ohi4lqroh9");
		list.add("http://ad.qulover.com/o_1b3i30rdrhd21vc9ebn1tb5j0h4d");
		initAdViews(list);

		tv_plan = getViewById(R.id.tv_plan);
		tv_plan.setOnClickListener(this);
		planAdapter = new PlanAdapter(mContext);
		plan_listview = getViewById(R.id.plan_listview);
		plan_listview.setAdapter(planAdapter);

		tv_finish = getViewById(R.id.tv_finish);
		tv_finish.setOnClickListener(this);
		finishAdapter = new PlanAdapter(mContext);
		finish_listview = getViewById(R.id.finish_listview);
		finish_listview.setAdapter(finishAdapter);

		LoadDialog.show(mContext);
		request(GET_PLAN_LIST);
		request(GET_FINISH_LIST);
	}

	@Override
	public Object doInBackground(int requestCode) throws HttpException {
		switch (requestCode) {
			case GET_PLAN_LIST:
				return action.getPlanList(true);

			case GET_FINISH_LIST:
				return action.getPlanList(false);
		}
		return null;
	}

	@Override
	public void onSuccess(int requestCode, Object result) {
		switch (requestCode) {
			case GET_PLAN_LIST:
				LoadDialog.dismiss(mContext);
				if (result != null) {
					List<Order> list = (ArrayList<Order>) result;
					planAdapter.setDataSet(list);
					planAdapter.notifyDataSetChanged();
				}
				break;

			case GET_FINISH_LIST:
				LoadDialog.dismiss(mContext);
				if (result != null) {
					List<Order> list = (ArrayList<Order>) result;
					finishAdapter.setDataSet(list);
					finishAdapter.notifyDataSetChanged();
				}
				break;
		}
	}

	@Override
	public void onFailure(int requestCode, int state, Object result) {
		super.onFailure(requestCode, state, result);
		switch (requestCode) {
			case GET_PLAN_LIST:
				LoadDialog.dismiss(mContext);
				break;
		}
	}

	/**
	 * 初始化广告
	 * @param adlist
	 */
	private void initAdViews(List<String> adlist) {
		AdImageView adImageView = null;
		ArrayList<View> pageViews = new ArrayList<View>();
		if (adlist != null && adlist.size() > 0) {
			for (String url : adlist) {
				adImageView = new AdImageView(mContext);
				adImageView.setUrl(url);
				pageViews.add(adImageView);
			}
		}

		adViewpager.setAdapter(new ViewPagerAdapter(pageViews));
		indicator.setViewPager(adViewpager);
		adViewpager.setInterval(5000);
		adViewpager.startAutoScroll();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return getParent().onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tv_plan:
				plan_listview.setVisibility(View.VISIBLE);
				finish_listview.setVisibility(View.GONE);
				break;

			case R.id.tv_finish:
				plan_listview.setVisibility(View.GONE);
				finish_listview.setVisibility(View.VISIBLE);
				break;
		}
	}

}
