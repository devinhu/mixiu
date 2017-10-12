/*
    ShengDao Android Client, CategoryActivity
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.activity.adapter.ServiceItemAdater;
import com.sd.one.activity.product.OderAddActivity;
import com.sd.one.common.From;
import com.sd.one.common.async.HttpException;
import com.sd.one.common.parse.JsonMananger;
import com.sd.one.utils.StringUtils;
import com.sd.one.utils.db.entity.Customer;
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
public class CustomerActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener<ListView>{

	public static final int GET_CUSTOMER_LIST = 6600;
	private PullToRefreshListView refreshcrollview;
	private ServiceItemAdater mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_listview);
		//初始化控件
		initView();
	}

	private void initView() {
		tv_title.setText("客户管理");
		setBtnRight("添加");
		btn_right.setOnClickListener(this);

		String from = getIntent().getStringExtra(From.class.getSimpleName());
		if(OderAddActivity.class.getSimpleName().equals(from)) {
			btn_left.setVisibility(View.VISIBLE);
		}else{
			btn_left.setVisibility(View.GONE);
		}

		refreshcrollview = getViewById(R.id.refreshlistview);
		refreshcrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		refreshcrollview.setOnRefreshListener(this);

		mAdapter = new ServiceItemAdater(this);
		View splitView = LayoutInflater.from(this).inflate(R.layout.split_view, null);
		refreshcrollview.getRefreshableView().addHeaderView(splitView);
		refreshcrollview.getRefreshableView().setAdapter(mAdapter);
		refreshcrollview.getRefreshableView().setOnItemClickListener(this);

		LoadDialog.show(this);
		request(GET_CUSTOMER_LIST);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_right:
				Intent intent = getIntent();
				intent.setClass(mContext, CustomerAddActivity.class);

				String from = intent.getStringExtra(From.class.getSimpleName());
				if(OderAddActivity.class.getSimpleName().equals(from)) {
					startActivityForResult(intent, OderAddActivity.ADD_CUSTOMER);
				}else{
					startActivityForResult(intent, CustomerActivity.GET_CUSTOMER_LIST);
				}
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
			case CustomerActivity.GET_CUSTOMER_LIST:
				LoadDialog.show(this);
				request(GET_CUSTOMER_LIST);
				break;

			case OderAddActivity.ADD_CUSTOMER:
				setResult(OderAddActivity.ADD_CUSTOMER, data);
				finish();
				break;
		}
	}


	@Override
	public Object doInBackground(int requestCode) throws HttpException {
		switch (requestCode) {
			case GET_CUSTOMER_LIST:
				return action.getCustomerList();

		}
		return null;
	}

	@Override
	public void onSuccess(int requestCode, Object result) {
		switch (requestCode) {
			case GET_CUSTOMER_LIST:
				LoadDialog.dismiss(mContext);
				if (result != null) {
					List<Customer> list = (ArrayList<Customer>) result;
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
			case GET_CUSTOMER_LIST:
				LoadDialog.dismiss(mContext);
				refreshcrollview.onRefreshComplete();
				break;
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		LoadDialog.show(this);
		request(GET_CUSTOMER_LIST);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		try {
			String from = getIntent().getStringExtra(From.class.getSimpleName());
			if(OderAddActivity.class.getSimpleName().equals(from)) {
				Customer model = mAdapter.getDataSet().get(position-2);
				Intent intent = getIntent();
				intent.putExtra(Customer.class.getSimpleName(), JsonMananger.beanToJson(model));
				setResult(OderAddActivity.ADD_CUSTOMER, intent);
				finish();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, final int mPosition, long id) {
//        ServiceModel model = mListData.get(mPosition-1);
//        mServiceId = model.getId();
//        if(model.getType()==0){
//        }else{
//            messgeDialog = new MessgeDialog(this, "您确定要删除该服务吗?", new OnAlertItemClickListener() {
//                @Override
//                public void onItemClick(Object o, int position) {
//                    if (position == 0) {
//                        //删除服务
//                        mDelPosition = mPosition;
//                        LoadDialog.show(CustomerActivity.this);
//                        request(DELETE_SERVICE);
//                    }
//                }
//            });
//            messgeDialog.setOnDismissListener(new OnDismissListener() {
//                @Override
//                public void onDismiss(Object o) {}
//            });
//            messgeDialog.setCancelable(true);
//            messgeDialog.show();
//        }
//        return true;
//    }
}

