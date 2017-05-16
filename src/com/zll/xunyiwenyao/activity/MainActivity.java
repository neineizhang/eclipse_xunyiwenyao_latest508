package com.zll.xunyiwenyao.activity;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button prescription_create, prescription_query, prescription_template, prescription_examine;
	private Button inspection_create, inspection_template, inspection_query, review, report;
	private Button information_manage,review_query, report_query;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.maininterface);

//		Uri myUri = getIntent().getData();//by kj
//		String doctor_name = myUri.getQueryParameter("doctor_name");
//		String patient_name = myUri.getQueryParameter("patient_name");;
//		Toast.makeText(MainActivity.this, "doctor_name:"+doctor_name+"   patient_name:"+patient_name, Toast.LENGTH_SHORT).show();

		prescription_create = (Button) findViewById(R.id.prescription_create);
		prescription_query = (Button) findViewById(R.id.prescription_query);
		prescription_template = (Button) findViewById(R.id.prescription_template);
		//prescription_examine = (Button) findViewById(R.id.prescription_examine);

		inspection_create = (Button) findViewById(R.id.inspection_create);
	
		inspection_query = (Button) findViewById(R.id.inspection_query);

		review = (Button) findViewById(R.id.review);
		report = (Button) findViewById(R.id.report);
		information_manage = (Button)findViewById(R.id.information_manage);
		review_query = (Button)findViewById(R.id.review_query);
		report_query = (Button)findViewById(R.id.report_query);
		
		prescription_create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Utils.LOGIN_DOCTOR.getType() == Utils.DOCTOR_TYPE.ACCESSOR.ordinal()) {
					Toast.makeText(MainActivity.this, "Permission Deined", Toast.LENGTH_SHORT).show();
					return;
				}

				Intent i1 = new Intent(MainActivity.this, PrescriptionCreateActivity.class);
				startActivity(i1);
			}
		});
		prescription_template.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Utils.LOGIN_DOCTOR.getType() == Utils.DOCTOR_TYPE.ACCESSOR.ordinal()) {
					Toast.makeText(MainActivity.this, "Permission Deined", Toast.LENGTH_SHORT).show();
					return;
				}

				Intent i2 = new Intent(MainActivity.this, PrescriptionTemplateActivity.class);
				startActivity(i2);
			}
		});

		prescription_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Utils.LOGIN_DOCTOR.getType() == Utils.DOCTOR_TYPE.ACCESSOR.ordinal()) {
					Toast.makeText(MainActivity.this, "Permission Deined", Toast.LENGTH_SHORT).show();
					return;
				}

				Intent i3 = new Intent(MainActivity.this, PrescriptionQueryActivity.class);
				startActivity(i3);
			}
		});

/*	prescription_examine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Utils.LOGIN_DOCTOR.getType() == Utils.DOCTOR_TYPE.DOCTOR.ordinal()) {
					Toast.makeText(MainActivity.this, "Permission Deined", Toast.LENGTH_SHORT).show();
					return;
				}

				Intent i4 = new Intent(MainActivity.this, PrescriptionExamineActivity.class);
				startActivity(i4);
			}
		});*/

		inspection_create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent i5=new Intent(MainActivity.this, InspectionCreateActivity.class);
				startActivity(i5);
			}

		});



		inspection_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i8=new Intent(MainActivity.this,InspectionQueryActivity.class);
				startActivity(i8);
			}
		});

		review.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i9=new Intent(MainActivity.this,ReviewCreateActivity.class);
				startActivity(i9);
			}
		});

		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i10=new Intent(MainActivity.this,ReportCreateActivity.class);
				startActivity(i10);
			}
		});
		report_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,ReportQueryActivity.class);
				startActivity(i);
			}
		});
		information_manage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i11=new Intent(MainActivity.this,DoctorInformationManageActivity.class);
				startActivity(i11);
			}
		});
		review_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i9=new Intent(MainActivity.this,ReviewQueryActivity.class);
				startActivity(i9);
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            isExit.setTitle("系统提示");  
            isExit.setMessage("确定要退出吗"); 
            isExit.setButton("确定", is_exit_listener);  
            isExit.setButton2("取消", is_exit_listener);  
            isExit.show();  
        }  
          
        return false;  
          
    }  
	
	/**监听对话框里面的button点击事件*/  
    DialogInterface.OnClickListener is_exit_listener = new DialogInterface.OnClickListener()  
    {  
        public void onClick(DialogInterface dialog, int which)  
        {  
            switch (which)  
            {  
            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
                finish();
//				Utils.LOGIN_DOCTOR=null;
                break;
            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
                break;  
            default:  
                break;  
            }  
        }  
    };
}
