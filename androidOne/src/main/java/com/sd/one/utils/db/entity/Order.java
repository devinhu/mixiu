package com.sd.one.utils.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by devin on 17/8/29.
 */
@Entity
public class Order {

    @Id(autoincrement = true)
    private Long orderId;

    @NotNull
    private Long customerId;
    private String customerName;
    private String customerPhone;

    private Long addressId;

    private String porductName;
    private Long productId;
    //最后价格
    private String finalPrice;
    //底价港币
    private String basePrice;
    //邮费 0为包邮
    private String postagePrice;
    private int number;
    private String desc;
    private String creteTime;
    //OrderStatus 0:为付款 1:已付款 2:已发货
    private String orderStatus;
    //采购状态 true已完成 false为完成
    private boolean planStatus;
    //是否放入本周计划 true是 false否
    private boolean planflag;


    @Generated(hash = 1863094589)
    public Order(Long orderId, @NotNull Long customerId, String customerName,
            String customerPhone, Long addressId, String porductName,
            Long productId, String finalPrice, String basePrice,
            String postagePrice, int number, String desc, String creteTime,
            String orderStatus, boolean planStatus, boolean planflag) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.addressId = addressId;
        this.porductName = porductName;
        this.productId = productId;
        this.finalPrice = finalPrice;
        this.basePrice = basePrice;
        this.postagePrice = postagePrice;
        this.number = number;
        this.desc = desc;
        this.creteTime = creteTime;
        this.orderStatus = orderStatus;
        this.planStatus = planStatus;
        this.planflag = planflag;
    }
    @Generated(hash = 1105174599)
    public Order() {
    }


    public boolean getPlanflag() {
        return this.planflag;
    }
    public void setPlanflag(boolean planflag) {
        this.planflag = planflag;
    }
    public boolean getPlanStatus() {
        return this.planStatus;
    }
    public void setPlanStatus(boolean planStatus) {
        this.planStatus = planStatus;
    }
    public String getOrderStatus() {
        return this.orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getCreteTime() {
        return this.creteTime;
    }
    public void setCreteTime(String creteTime) {
        this.creteTime = creteTime;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getNumber() {
        return this.number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getFinalPrice() {
        return this.finalPrice;
    }
    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }
    public Long getProductId() {
        return this.productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getPorductName() {
        return this.porductName;
    }
    public void setPorductName(String porductName) {
        this.porductName = porductName;
    }
    public Long getAddressId() {
        return this.addressId;
    }
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    public String getCustomerPhone() {
        return this.customerPhone;
    }
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    public String getCustomerName() {
        return this.customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public Long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getOrderId() {
        return this.orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getPostagePrice() {
        return this.postagePrice;
    }
    public void setPostagePrice(String postagePrice) {
        this.postagePrice = postagePrice;
    }
    public String getBasePrice() {
        return this.basePrice;
    }
    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }
  


    
}
