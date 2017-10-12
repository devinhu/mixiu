package com.sd.one.activity.product;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sd.one.R;
import com.sd.one.activity.BaseActivity;
import com.sd.one.activity.customer.CustomerActivity;
import com.sd.one.common.From;
import com.sd.one.common.async.HttpException;
import com.sd.one.common.parse.JsonMananger;
import com.sd.one.utils.NToast;
import com.sd.one.utils.StringUtils;
import com.sd.one.utils.db.entity.Customer;
import com.sd.one.widget.dialog.LoadDialog;


/**
 * [添加订单]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2016-5-4
 *
 **/
public class OderAddActivity extends BaseActivity implements View.OnClickListener{

    private final int ADD_ORDER = 7601;
    public static final int ADD_CUSTOMER = 601;

    private LinearLayout layout_customer;
    private LinearLayout layout_receiver;
    private LinearLayout layout_product;
    private TextView tv_customer;
    private EditText edit_receiver, edit_phone, edit_address;
    private EditText edit_product, edit_baseprice, edit_number, edit_desc;
    private ImageView img_product;
    private ToggleButton btn_planflag;
    private TextView save_txt;

    private  Customer customer;
    private String receiverName, receiverPhone, receiverAddress;
    private String productName, productBaseprice, productNumber, productImage, productDesc;
    private boolean planflag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_layout);
        initView();
    }

    private void initView() {
        tv_title.setText("添加订单");
        tv_customer = getViewById(R.id.tv_customer);
        edit_receiver = getViewById(R.id.edit_receiver);
        edit_phone = getViewById(R.id.edit_phone);
        edit_address = getViewById(R.id.edit_address);
        edit_product = getViewById(R.id.edit_product);
        edit_baseprice = getViewById(R.id.edit_baseprice);
        img_product = getViewById(R.id.img_product);
        edit_number = getViewById(R.id.edit_number);
        edit_desc = getViewById(R.id.edit_desc);
        btn_planflag = getViewById(R.id.btn_planflag);
        btn_planflag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                planflag = isChecked;
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                if(isChecked){
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    btn_planflag.setChecked(true);
                }else{
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    btn_planflag.setChecked(false);
                }
            }
        });

        layout_customer = getViewById(R.id.layout_customer);
        layout_customer.setOnClickListener(this);

        layout_product = getViewById(R.id.layout_product);
        layout_product.setOnClickListener(this);

        layout_receiver = getViewById(R.id.layout_receiver);
        layout_receiver.setOnClickListener(this);

        save_txt = getViewById(R.id.save_txt);
        save_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.layout_customer:
                Intent intent = new Intent(mContext, CustomerActivity.class);
                intent.putExtra(From.class.getSimpleName(), OderAddActivity.class.getSimpleName());
                startActivityForResult(intent, ADD_CUSTOMER);
                break;

            case R.id.layout_receiver:

                break;

            case R.id.layout_product:

                break;

            case R.id.save_txt:
                String customerInfo = tv_customer.getText().toString();
                if(StringUtils.isEmpty(customerInfo)){
                    NToast.shortToast(mContext, "客户不能为空");
                    return;
                }


                productName = edit_product.getText().toString();
                if(StringUtils.isEmpty(productName)){
                    NToast.shortToast(mContext, "产品不能为空");
                    return;
                }

                productBaseprice = edit_baseprice.getText().toString();
                productNumber = edit_number.getText().toString();
                productDesc = edit_desc.getText().toString();

                LoadDialog.show(mContext);
                request(ADD_ORDER);
                break;
        }
    }

    @Override
    public Object doInBackground(int requestCode) throws HttpException {
        switch (requestCode) {
            case ADD_ORDER:
                return action.addOder(customer, productName, productNumber, receiverName, receiverPhone, receiverAddress, productBaseprice, productDesc, productImage, planflag);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case ADD_ORDER:
                LoadDialog.dismiss(mContext);
                if (result != null) {
                    NToast.shortToast(OderAddActivity.this,"添加成功");
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    NToast.shortToast(OderAddActivity.this,"添加失败");
                }
                break;
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result) {
        super.onFailure(requestCode, state, result);
        switch (requestCode) {
            case ADD_ORDER:
                LoadDialog.dismiss(mContext);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case OderAddActivity.ADD_CUSTOMER:
                try {
                    String json = data.getStringExtra(Customer.class.getSimpleName());
                    customer = JsonMananger.jsonToBean(json, Customer.class);
                    tv_customer.setText(customer.getName() + " " + customer.getPhone());
                } catch (HttpException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
