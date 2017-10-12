package com.sd.one.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sd.one.R;
import com.sd.one.utils.db.entity.Customer;


/**
 * Created by Administrator on 2016/5/4.
 */
public class ServiceItemAdater extends BaseAdapter<Customer> {

    private ViewHolder holder;

    /**
     * @param context
     */
    public ServiceItemAdater(Context context) {
        super(context);
    }

    class ViewHolder {
        TextView service_title;
        TextView service_status;
        View split_view;
        View line_view;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.service_item_layout, null);
            holder.service_title = getViewById(convertView, R.id.service_title);
            holder.service_status = getViewById(convertView, R.id.service_status);
            holder.split_view = getViewById(convertView,R.id.split_view);
            holder.line_view = getViewById(convertView,R.id.line_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Customer bean = dataSet.get(position);
        holder.service_title.setText(bean.getName()+"\n"+bean.getPhone());

        if(position==dataSet.size()-1){
            holder.split_view.setVisibility(View.GONE);
            holder.line_view.setVisibility(View.VISIBLE);
        }else{
            holder.split_view.setVisibility(View.VISIBLE);
            holder.line_view.setVisibility(View.GONE);
        }
        return convertView;
    }
}