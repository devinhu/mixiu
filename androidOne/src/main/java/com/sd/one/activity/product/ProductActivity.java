/*
    ShengDao Android Client, CategoryActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.activity.adapter.OrderAdapter;
import com.sd.one.common.async.HttpException;
import com.sd.one.utils.StringUtils;
import com.sd.one.utils.db.entity.Order;
import com.sd.one.widget.dialog.LoadDialog;
import com.sd.one.widget.pulltorefresh.PullToRefreshBase;
import com.sd.one.widget.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * [下单页面]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class ProductActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener<ListView>{

	private final int GET_ORDER = 8601;
	private PullToRefreshListView refreshcrollview;
	private OrderAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_listview);

		tv_title.setText(getString(R.string.menu_product_title));
		tv_title.setFocusable(true);
		tv_title.setFocusableInTouchMode(true);
		tv_title.requestFocus();

		btn_left.setVisibility(View.GONE);
		btn_right.setVisibility(View.VISIBLE);
		btn_right.setOnClickListener(this);
		btn_right.setText("添加");

		refreshcrollview = getViewById(R.id.refreshlistview);
		refreshcrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		refreshcrollview.setOnRefreshListener(this);

		mAdapter = new OrderAdapter(this);
		View splitView = LayoutInflater.from(this).inflate(R.layout.split_view, null);
		refreshcrollview.getRefreshableView().addHeaderView(splitView);
		refreshcrollview.getRefreshableView().setAdapter(mAdapter);
		refreshcrollview.getRefreshableView().setOnItemClickListener(this);

		LoadDialog.show(this);
		request(GET_ORDER);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return getParent().onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_right:
				Intent intent = new Intent(mContext, OderAddActivity.class);
				getParent().startActivityForResult(intent, GET_ORDER);
				break;
		}
	}

	@Override
	public Object doInBackground(int requestCode) throws HttpException {
		switch (requestCode) {
			case GET_ORDER:
				return action.getOrderList();
		}
		return null;
	}

	@Override
	public void onSuccess(int requestCode, Object result) {
		switch (requestCode) {
			case GET_ORDER:
				LoadDialog.dismiss(mContext);
				if (result != null) {
					List<Order> list = (ArrayList<Order>) result;
					if (!StringUtils.isEmpty(list)) {
						mAdapter.removeAll();
						mAdapter.setDataSet(list);
						mAdapter.notifyDataSetChanged();
					}
				}
				refreshcrollview.onRefreshComplete();
				break;
		}
	}

	@Override
	public void onFailure(int requestCode, int state, Object result) {
		super.onFailure(requestCode, state, result);
		switch (requestCode) {
			case GET_ORDER:
				LoadDialog.dismiss(mContext);
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		LoadDialog.show(this);
		request(GET_ORDER);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
			case RESULT_OK:
				LoadDialog.show(this);
				request(GET_ORDER);
				break;
		}
	}
}
