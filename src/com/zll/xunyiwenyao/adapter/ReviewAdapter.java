package com.zll.xunyiwenyao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kejund on 17/4/11.
 */

public class ReviewAdapter extends BaseAdapter {
    private List<Review> itemlist = new ArrayList<Review>();
    private Context mContext;

    public ReviewAdapter(List<Review> reviewlist, Context mContext)
    {   this.mContext = mContext;
        this.itemlist = reviewlist;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return itemlist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_listview, null);


        TextView list_name = (TextView) view
                .findViewById(R.id.list_name);
        TextView list_date = (TextView) view
                .findViewById(R.id.list_date);
        TextView list_doctor = (TextView) view
                .findViewById(R.id.list_doctor);

        list_name.setText(itemlist.get(position).getDrugName().toString());
        list_date.setText(itemlist.get(position).getDate().toString());
        list_doctor.setText(itemlist.get(position).getDoctorName().toString());
        return view;
    }
}
