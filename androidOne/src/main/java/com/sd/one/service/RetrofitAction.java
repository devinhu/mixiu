package com.sd.one.service;

import android.content.Context;
import android.text.TextUtils;

import com.sd.one.common.Constants;
import com.sd.one.model.base.BaseResponse;
import com.sd.one.model.response.ConfigData;
import com.sd.one.utils.DateUtils;
import com.sd.one.utils.db.DBManager;
import com.sd.one.utils.db.entity.Address;
import com.sd.one.utils.db.entity.Customer;
import com.sd.one.utils.db.entity.Order;
import com.sd.one.utils.db.entity.Product;
import com.sd.one.utils.db.gen.OrderDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;

/**
 * [一句话简单描述]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2016/12/14
 */
public class RetrofitAction extends RetrofitManager {

    public static final MediaType JSON;
    /**
     * 构造方法
     * @param mContext
     */
    public RetrofitAction(Context mContext) {
        super(mContext);
    }

    static {
        JSON = MediaType.parse("application/json; charset=utf-8");
    }

    private RequestBody convertParamsMapToRequestBody(Map<String, Object> paramsMap) {
        if (paramsMap == null || paramsMap.size() <= 0) {
            return null;
        }
        return RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(paramsMap));
    }

    /**
     * 获取配置信息接口
     * @return
     */
    public Call<BaseResponse> getAppConfig() {
        HashMap params = new HashMap();
        params.put("versionCode", "103");
        params.put("deviceType", "1");
        return apiService.executePost("http://119.23.40.79/version/getAppConfig", convertParamsMapToRequestBody(params));
    }

    /**
     * 获取配置信息接口
     * @return
     */
    public Call<BaseResponse> categorylist() {
        HashMap params = new HashMap();
        return apiService.executePost("http://119.23.40.79/category/list", convertParamsMapToRequestBody(params));
    }

    /**
     * 获取配置信息接口
     * @return
     */
    public Call<BaseResponse> recommend() {
        HashMap params = new HashMap();
        params.put("pageNum", "1");
        params.put("pageSize", "20");
        return apiService.executePost("http://119.23.40.79/post/recommend", convertParamsMapToRequestBody(params));
    }


    /**
     * 获取配置信息接口
     * @return
     */
    public Call<BaseResponse> video() {
        HashMap params = new HashMap();
        params.put("pageSize", "20");
        return apiService.executePost("http://119.23.40.79/post/video", convertParamsMapToRequestBody(params));
    }

    /**
     * 获取配置信息接口
     * @return
     */
    public Call<BaseResponse> list() {
        HashMap params = new HashMap();
        params.put("categoryId", "1");
        params.put("pageNum", "1");
        params.put("pageSize", "20");
        return apiService.executePost("http://119.23.40.79/post/list", convertParamsMapToRequestBody(params));
    }


    /**
     * 查询客户列表
     * @return
     */
    public List<Customer> getCustomerList(){
        return DBManager.getInstance(mContext).getDaoSession().getCustomerDao().loadAll();
    }

    /**
     * 添加客户
     * @param name
     * @param phone
     */
    public Customer addCustomer(String name, String phone){
        Customer bean = new Customer();
        bean.setName(name);
        bean.setPhone(phone);
        bean.setCreteTime(DateUtils.currentDateTime());
        long rowId = DBManager.getInstance(mContext).getDaoSession().getCustomerDao().insert(bean);
        if(rowId > 0){
            return DBManager.getInstance(mContext).getDaoSession().getCustomerDao().loadByRowId(rowId);
        }
        return null;
    }


    /**
     * 添加订单
     * @param customer
     * @param productName
     * @param number
     * @param baseprice
     * @param desc
     * @param image
     * @param planflag
     * @return
     */
    public Long addOder(Customer customer, String productName, String number, String receiverName, String receiverPhone, String receiverAddress, String baseprice, String desc, String image, boolean planflag){
        Order order = new Order();

        //将收货人信息入库
        if(!TextUtils.isEmpty(receiverAddress) && !TextUtils.isEmpty(receiverName) && !TextUtils.isEmpty(receiverPhone)){
            Address address = new Address();
            address.setAddress(receiverAddress);
            address.setReceiver(receiverName);
            address.setMobile(receiverPhone);
            long rowId = DBManager.getInstance(mContext).getDaoSession().getAddressDao().insert(address);

            //关联收获人信息
            Address addressBean = DBManager.getInstance(mContext).getDaoSession().getAddressDao().loadByRowId(rowId);
            order.setAddressId(addressBean.getAddressId());
        }


        //将产品信息入库
        Product product = new Product();
        product.setPorductName(productName);
        product.setBasePrice(baseprice);
        product.setImage(image);
        product.setDesc(desc);
        long rowId = DBManager.getInstance(mContext).getDaoSession().getProductDao().insert(product);

        //获取产品id
        Product pro = DBManager.getInstance(mContext).getDaoSession().getProductDao().loadByRowId(rowId);
        order.setProductId(pro.getProductId());
        order.setPorductName(pro.getPorductName());
        order.setBasePrice(pro.getBasePrice());
        order.setDesc(pro.getDesc());
        order.setNumber(Integer.parseInt(number));
        order.setCreteTime(DateUtils.currentDateTime());

        //OrderStatus 0:为付款 1:已付款 2:已发货
        order.setOrderStatus("0");
        order.setPlanflag(planflag);
        order.setPlanStatus(false);

        order.setCustomerId(customer.getCustomerId());
        order.setCustomerName(customer.getName());
        order.setCustomerPhone(customer.getPhone());

        return DBManager.getInstance(mContext).getDaoSession().getOrderDao().insert(order);
    }

    /**
     * 查询订单列表
     * @return
     */
    public List<Order> getOrderList(){
        return DBManager.getInstance(mContext).getDaoSession().getOrderDao().loadAll();
    }

    /**
     * 查询订单列表
     * @return
     */
    public List<Order> getPlanList(boolean planStatus){
        List<Order> resultList = new ArrayList<>();
        HashSet<String> hashSet = new HashSet<>();

        QueryBuilder<Order> qb = DBManager.getInstance(mContext).getDaoSession().getOrderDao().queryBuilder();
        qb.where(OrderDao.Properties.Planflag.eq(true), OrderDao.Properties.PlanStatus.eq(planStatus));
        List<Order> list = qb.list();
        if(list != null && list.size() > 0){
            for(Order order : list){
                hashSet.add(order.getPorductName());
            }

            for(String productName : hashSet){
                Order bean = new Order();

                for(Order order : list){
                    if(order.getPorductName().equals(productName)){
                        bean.setOrderId(order.getOrderId());

                        bean.setCustomerId(order.getCustomerId());
                        bean.setCustomerName(order.getCustomerName());
                        bean.setCustomerPhone(order.getCustomerPhone());
                        bean.setAddressId(order.getAddressId());

                        bean.setPorductName(order.getPorductName());
                        bean.setBasePrice(order.getBasePrice());
                        bean.setFinalPrice(order.getFinalPrice());
                        bean.setDesc(order.getDesc());
                        bean.setNumber(bean.getNumber() + order.getNumber());

                        bean.setPlanflag(order.getPlanflag());
                        bean.setOrderStatus(order.getOrderStatus());
                        bean.setCreteTime(order.getCreteTime());
                    }
                }

                resultList.add(bean);
            }
        }

        return resultList;
    }
}
