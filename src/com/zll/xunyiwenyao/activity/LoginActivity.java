package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Doctor;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.webservice.DoctorWebService;
import com.zll.xunyiwenyao.webservice.DrugWebService;
import com.zll.xunyiwenyao.webservice.InspectionWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionTemplateWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;
import com.zll.xunyiwenyao.webservice.ReportWebService;
import com.zll.xunyiwenyao.webservice.ReviewWebService;

import org.json.JSONException;

import java.math.BigInteger;
import java.security.MessageDigest;


public class LoginActivity extends Activity {

	private RadioGroup select_doctor;
	private RadioButton doctor_rb1, doctor_rb2;
	private Button login_entrylog;
	private EditText login_name, login_pwd;
	private Button btn_register;
	private SharedPreferences sp;
	private SharedPreferences.Editor ed;
    private int ischecked;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			DoctorWebService.initDB();
			DrugWebService.initDB();
			InspectionWebService.initDB();
			ReviewWebService.initDB();
			ReportWebService.initDB();
			PrescriptionWebService.initDB();
			PrescriptionTemplateWebService.initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		select_doctor = (RadioGroup) findViewById(R.id.login_doctor_select);
		login_entrylog = (Button) findViewById(R.id.login_entrylog);
		login_name = (EditText) findViewById(R.id.login_name);
		login_pwd = (EditText) findViewById(R.id.login_pwd);
		btn_register = (Button)findViewById(R.id.register_button);
		doctor_rb1 = (RadioButton)findViewById(R.id.login_doctor_select1);
		doctor_rb2 = (RadioButton)findViewById(R.id.login_doctor_select2);

		//创建SharedPreferences对象
		sp = this.getSharedPreferences("info", MODE_PRIVATE);
		ed = sp.edit();
		//获得保存在SharedPredPreferences中的用户名和密码
		String username = sp.getString("username", null);
		String password = sp.getString("password", null);
		int sex = sp.getInt("sex",11);//缺省值为11
		System.out.println("test:username"+username);
		if(username!=null && password!=null && sex!=11){
			//在用户名和密码的输入框中显示用户名和密码
			login_name.setText(username);
			login_pwd.setText(password);
			if(sex==Utils.DOCTOR_TYPE.DOCTOR.ordinal())
				doctor_rb1.setChecked(true);
			else
				doctor_rb2.setChecked(true);
		}
		Intent i_getvalue = getIntent();
		String action = i_getvalue.getAction();
		//从聊天软件接收数据
		if(Intent.ACTION_VIEW.equals(action)) {
			Uri myUri = getIntent().getData();//by kj
			String doctor_name = myUri.getQueryParameter("doctor_name");
			String patient_name = myUri.getQueryParameter("patient_name");
			String type = myUri.getQueryParameter("type");
			if(doctor_name!=null&&username!=null&&doctor_name.equals(username)){//从聊天软件接受了参数，并且曾经登陆过
				if(type.equals("2")){//类别为开检查单
					Utils.isFromChat = true;
					Utils.PATIENT_NAME=patient_name;
					Doctor islogin = DoctorWebService.isSuccessLogin(username, password, sex);
					if (islogin != null){
						Utils.LOGIN_DOCTOR=islogin;
//						Intent i=new Intent(LoginActivity.this,PrescriptionCreateActivity.class);
						Intent i=new Intent(LoginActivity.this,InspectionCreateActivity.class);
						startActivity(i);
						finish();
					}
				}else{//其他状态默认为开处方
					Utils.isFromChat = true;
					Utils.PATIENT_NAME=patient_name;
					Doctor islogin = DoctorWebService.isSuccessLogin(username, password, sex);
					if (islogin != null){
						Utils.LOGIN_DOCTOR=islogin;
						Intent i=new Intent(LoginActivity.this,PrescriptionCreateActivity.class);
//						Intent i=new Intent(LoginActivity.this,InspectionCreateActivity.class);
						startActivity(i);
						finish();
					}
				}
			}
		}


		//娉ㄥ唽
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i1=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(i1);
			}
		});

		login_entrylog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int doctor_id = select_doctor.getCheckedRadioButtonId();
				String name = login_name.getText().toString();
				String pwd = login_pwd.getText().toString();
				
				if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
					Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
				} else {
					switch (doctor_id) {
					case R.id.login_doctor_select1: {
						
						Doctor islogin = DoctorWebService.isSuccessLogin(name, pwd, Utils.DOCTOR_TYPE.DOCTOR.ordinal());
						
						if (islogin != null) {
							Utils.LOGIN_DOCTOR = islogin;
							ischecked =islogin.getProfState() ;
							if (ischecked ==0){Toast.makeText(LoginActivity.this, "管理员正在审核您的身份，请耐心等待", Toast.LENGTH_SHORT).show();}
							else if(ischecked ==1)
							{
							ed.putString("username", name);
							ed.putString("password", pwd);
							ed.putInt("sex",Utils.DOCTOR_TYPE.DOCTOR.ordinal());
							//提交用户名和密码
							ed.commit();
							Intent i=new Intent(LoginActivity.this,MainActivity.class);
							startActivity(i);
							finish();}
							else if(ischecked==2)
							{Toast.makeText(LoginActivity.this,"对不起，管理员没有通过您的身份验证", Toast.LENGTH_SHORT).show();}
						} else {
							Toast.makeText(LoginActivity.this, "登陆失败！", Toast.LENGTH_SHORT).show();
							login_name.setText(null);
							login_pwd.setText(null);
						}
						
						break;
					}
					case R.id.login_doctor_select2: {
						
						Doctor islogin = DoctorWebService.isSuccessLogin(name, pwd, Utils.DOCTOR_TYPE.ACCESSOR.ordinal());
						
						if (islogin != null) {
							Utils.LOGIN_DOCTOR = islogin;
							ischecked =islogin.getProfState() ;
							if (ischecked ==0){Toast.makeText(LoginActivity.this, "管理员正在审核您的身份，请耐心等待", Toast.LENGTH_SHORT).show();}
							else if(ischecked ==1) {
								//以键值对的显示将用户名和密码保存到sp中
								ed.putString("username", name);
								ed.putString("password", pwd);
								ed.putInt("sex", Utils.DOCTOR_TYPE.ACCESSOR.ordinal());
								//提交用户名和密码
								ed.commit();
								Intent i = new Intent(LoginActivity.this, AccessorMainActivity.class);
								startActivity(i);
								finish();
							}
							else if(ischecked==2)
							{Toast.makeText(LoginActivity.this,"对不起，管理员没有通过您的身份验证", Toast.LENGTH_SHORT).show();}
					} else {
							Toast.makeText(LoginActivity.this, "登陆失败！", Toast.LENGTH_SHORT).show();
							login_name.setText(null);
							login_pwd.setText(null);
						}
						
						break;
					}

					default:
						Toast.makeText(LoginActivity.this, "请选择医生类别！", Toast.LENGTH_SHORT).show();
						break;
					}
				}
			}
		});

	}//onCreate



	// MD5加密
	public static String MD5(String string) {
		return encodeMD5String(string);
	}

	public static String encodeMD5String(String str) {
		return encode(str, "MD5");
	}

	private static String encode(String str, String method) {
		MessageDigest md = null;
		String dstr = null;
		try {
			md = MessageDigest.getInstance(method);
			md.update(str.getBytes());
			dstr = new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dstr;
	}

}
