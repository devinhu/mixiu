package com.sd.one.widget.verticalbanner;


import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Alan.zhou
 * @version 1.0
 * @date 2016-3-25
 */
@SuppressWarnings("unused")
public abstract class BaseBannerAdapter<T> {
    private List<T> mDatas;
    private OnDataChangedListener mOnDataChangedListener;

    public BaseBannerAdapter() {
        mDatas = new ArrayList<>();
    }

    public BaseBannerAdapter(T[] datas) {
        mDatas = new ArrayList<>(Arrays.asList(datas));
    }

    public void setData(List<T> datas) {
        if(datas != null){
            this.mDatas = datas;
            notifyDataChanged();
        }
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    void notifyDataChanged() {
        if(mOnDataChangedListener != null){
            mOnDataChangedListener.onChanged();
        }
    }

    public T getItem(int position) {
        if(mDatas != null){
            return mDatas.get(position);
        }
        return null;
    }


    public abstract View getView(VerticalBannerView parent);


    public abstract void setItem(View view, T data);


    interface OnDataChangedListener {
        void onChanged();
    }
}
