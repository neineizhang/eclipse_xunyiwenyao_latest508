package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.content.Context;
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
import com.zll.xunyiwenyao.adapter.PrescriptionQueryAdapter;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.webservice.DoctorWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

/**
 * Created by admin on 2017/3/23.
 */

public class PrescriptionQueryAllPrescriptionActivity extends Activity implements OnItemClickListener {

	private ListView allprescription_lv;
	private ArrayList<Prescription> mPrescription = null;
	private Context mContext;
	private PrescriptionQueryAdapter mPrescriptionQueryAdapter = null;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.prescriptionqueryall);

		allprescription_lv = (ListView) findViewById(R.id.allprescription_lv);

		mContext = PrescriptionQueryAllPrescriptionActivity.this;
		mPrescription = new ArrayList<Prescription>();
		intialData();
		mPrescriptionQueryAdapter = new PrescriptionQueryAdapter((ArrayList<Prescription>) mPrescription, mContext);
		allprescription_lv.setAdapter(mPrescriptionQueryAdapter);
		allprescription_lv.setOnItemClickListener(this);
	}

	private void intialData() {
		// TODO Auto-generated method stub
		// Prescription onedata =
		// PrescriptionWebService.getAllPrescription().get(0);
		List<Prescription> prescriptionlist = PrescriptionWebService.getAllPrescription();
		for (Prescription item : prescriptionlist) {
			mPrescription.add(item);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		// Toast.makeText(mContext,"�����˵�" + position +
		// "��",Toast.LENGTH_SHORT).show();
	}
}
