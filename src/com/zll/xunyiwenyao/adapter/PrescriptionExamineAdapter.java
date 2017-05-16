package com.zll.xunyiwenyao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Prescription;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/28.
 */

public class PrescriptionExamineAdapter extends BaseAdapter {
    private ArrayList<Prescription> mPrescription;
    private Context mContext;


    public PrescriptionExamineAdapter(ArrayList<Prescription> mPrescription, Context mContext)
    {   this.mContext = mContext;
        this.mPrescription = mPrescription;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mPrescription.size();
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
        // TODO Auto-generated method stub
        convertView = LayoutInflater.from(mContext).inflate(R.layout.prescription_examine_lvitem,parent, false);
        ImageView prescription_query_item_picture = (ImageView) convertView.findViewById(R.id.prescription_examine_item_picture);
        TextView prescription_lvitem_id = (TextView) convertView.findViewById(R.id.examine_lvitem_id);
        TextView prescription_lvitem_name = (TextView) convertView.findViewById(R.id.examine_lvitem_name);
        TextView prescription_lvitem_date = (TextView) convertView.findViewById(R.id.examine_lvitem_date);

        prescription_query_item_picture.setBackgroundResource(R.drawable.item_picture);

        prescription_lvitem_id.setText(mPrescription.get(position).getId()+"");
		prescription_lvitem_name.setText(mPrescription.get(position).getName());
		prescription_lvitem_date.setText(mPrescription.get(position).getDate()+"");
        return convertView;

    }

}

