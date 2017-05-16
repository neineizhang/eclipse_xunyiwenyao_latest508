package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.adapter.PrescriptionExamineAdapter;
import com.zll.xunyiwenyao.adapter.PrescriptionQueryAdapter;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.webservice.DoctorWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

public class PrescriptionExamineRefusedActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<Prescription> examineprescriptionList = null;
    private ListView examine_lv;
    private PrescriptionExamineAdapter mPrescriptionExamineAdapter;
	private List<Prescription> prescriptionlist = null;

    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.prescription_examine_lv);
        examine_lv = (ListView) findViewById(R.id.examine_lv);
        mContext = PrescriptionExamineRefusedActivity.this;
        examineprescriptionList =new ArrayList<Prescription>();
        
        intialdata();
        mPrescriptionExamineAdapter = new PrescriptionExamineAdapter((ArrayList<Prescription>) examineprescriptionList, mContext);
        examine_lv.setAdapter(mPrescriptionExamineAdapter);
        examine_lv.setOnItemClickListener(this);
        
    }

    private  void  intialdata(){
        examineprescriptionList =new ArrayList<Prescription>();
    	prescriptionlist = PrescriptionWebService.getPrescriptionbyStatus(Utils.STATUS.REFUSED.ordinal());
    	for (Prescription item : prescriptionlist) {
    		examineprescriptionList.add(item);
		}
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i =new Intent(this,PrescriptionExamingPrescriptionActivity.class);
		String prescription_name = prescriptionlist.get(position).getName();
        i.putExtra("prescription_name", prescription_name); 
        i.putExtra("type", "refused"); 
		startActivity(i);
        finish();
    }
}



