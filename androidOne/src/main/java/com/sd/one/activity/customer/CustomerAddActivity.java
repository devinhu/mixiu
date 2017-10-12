package com.sd.one.activity.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.activity.product.OderAddActivity;
import com.sd.one.common.From;
import com.sd.one.common.async.HttpException;
import com.sd.one.common.parse.JsonMananger;
import com.sd.one.utils.NToast;
import com.sd.one.utils.StringUtils;
import com.sd.one.utils.db.entity.Customer;
import com.sd.one.widget.dialog.LoadDialog;


/**
 * [添加客户]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2016-5-4
 *
 **/
public class CustomerAddActivity extends BaseActivity implements View.OnClickListener{

    private EditText tv_name, tv_phone;
    private final int ADD_SERVICE = 6601;
    private TextView save_txt;
    private String name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service_layout);
        initView();
    }

    private void initView() {
        tv_title.setText("添加客户");
        tv_name = getViewById(R.id.service_project_edt);
        tv_phone = getViewById(R.id.service_timing_edt);
        save_txt = getViewById(R.id.save_txt);
        save_txt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save_txt:
                name = tv_name.getText().toString();
                if(StringUtils.isEmpty(name)){
                    NToast.shortToast(mContext, "姓名不能为空");
                    return;
                }

                phone = tv_phone.getText().toString();
                if(StringUtils.isEmpty(phone)){
                    NToast.shortToast(mContext, "电话不能为空");
                    return;
                }

                LoadDialog.show(mContext);
                request(ADD_SERVICE);
                break;
        }
    }

    @Override
    public Object doInBackground(int requestCode) throws HttpException {
        switch (requestCode) {
            case ADD_SERVICE:
                return action.addCustomer(name, phone);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case ADD_SERVICE:
                LoadDialog.dismiss(mContext);
                if (result != null) {
                    NToast.shortToast(CustomerAddActivity.this,"添加成功");

                    try {
                        Customer bean = (Customer)result;
                        Intent intent = getIntent();
                        intent.putExtra(Customer.class.getSimpleName(), JsonMananger.beanToJson(bean));

                        String from = intent.getStringExtra(From.class.getSimpleName());
                        if(OderAddActivity.class.getSimpleName().equals(from)) {
                            setResult(OderAddActivity.ADD_CUSTOMER, intent);
                        }else{
                            setResult(CustomerActivity.GET_CUSTOMER_LIST, intent);
                        }
                        finish();

                    } catch (HttpException e) {
                        e.printStackTrace();
                    }

                }else{
                    NToast.shortToast(CustomerAddActivity.this,"添加失败");
                }
                break;
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
        switch (requestCode) {
            case ADD_SERVICE:
                LoadDialog.dismiss(mContext);
                break;
        }
    }
}
