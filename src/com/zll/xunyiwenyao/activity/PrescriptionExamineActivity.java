package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.view.Menu;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionExamineActivity extends TabActivity  {

	private ArrayList<Prescription> examineprescriptionList = null;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.prescription_examine);
		addTab();
	}
	/**
	 * ���Tab
	 */
	private void addTab() {
		TabHost examine_tabhost =getTabHost();
		LayoutInflater.from(this).inflate(R.layout.prescription_examine,examine_tabhost.getTabContentView(),true);
		//�õ���ǰActivity��TabHost��,���TabActivity�Ĳ���ͨ��������������
		//����TabHost.TabSpec,������������һҳ
		Intent remoteIntent1 = new Intent();
		remoteIntent1.setClass(this,PrescriptionExamineAllActivity.class);
		TabHost.TabSpec tab1 = examine_tabhost.newTabSpec("text1").setIndicator("COMMITED").setContent(remoteIntent1);
		Intent remoteIntent2 = new Intent();
		remoteIntent2.setClass(this,PrescriptionExamineApprovedActivity.class);
	    TabHost.TabSpec tab2 = examine_tabhost.newTabSpec("text2").setIndicator("Approved").setContent(remoteIntent2);
	    Intent remoteIntent3 = new Intent();
		remoteIntent3.setClass(this,PrescriptionExamineRefusedActivity.class);
	    TabHost.TabSpec tab3 = examine_tabhost.newTabSpec("text3").setIndicator("Refused").setContent(remoteIntent3);
       
		examine_tabhost.addTab(tab1);
		examine_tabhost.addTab(tab2);
		examine_tabhost.addTab(tab3);
	}
	 


	}


