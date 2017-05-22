package com.zll.xunyiwenyao.activity;

import java.util.ArrayList;
import java.util.List;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.adapter.PrescriptionExamineAdapter;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PrescriptionExamineApprovedActivity extends Activity implements OnItemClickListener {
	
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
	        mContext = PrescriptionExamineApprovedActivity .this;
	        examineprescriptionList =new ArrayList<Prescription>();
	        
	        intialdata();
	        mPrescriptionExamineAdapter = new PrescriptionExamineAdapter((ArrayList<Prescription>) examineprescriptionList, mContext);
	        examine_lv.setAdapter(mPrescriptionExamineAdapter);
	        examine_lv.setOnItemClickListener(this);
	        
	    }

	    private  void  intialdata(){
	        examineprescriptionList =new ArrayList<Prescription>();
	    	prescriptionlist = PrescriptionWebService.getPrescriptionbyStatus(Utils.STATUS.APPROVED.ordinal());
	    	for (Prescription item : prescriptionlist) {
	    		examineprescriptionList.add(item);
			}
	    }

	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        Intent i =new Intent(this,PrescriptionExamingPrescriptionActivity.class);
			String prescription_name = prescriptionlist.get(position).getName();
	        i.putExtra("prescription_name", prescription_name); 
	        i.putExtra("type", "approved"); 
			startActivity(i);
	        finish();
	    }
	}
