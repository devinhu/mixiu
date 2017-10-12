package com.sd.one.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.sd.one.R;
import com.sd.one.widget.verticalbanner.BaseBannerAdapter;
import com.sd.one.widget.verticalbanner.VerticalBannerView;

/**
 * Created by Administrator on 2016/3/25.
 */
public class VerticalBannerAdapter extends BaseBannerAdapter<String> {


    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_banner_item,null);
    }

    @Override
    public void setItem(final View view, final String data) {
        if(view != null){
            TextView tv = (TextView) view.findViewById(R.id.title);
            tv.setText(data);
            TextView tag = (TextView) view.findViewById(R.id.tag);
//            tag.setText(data);
        }
    }
}
