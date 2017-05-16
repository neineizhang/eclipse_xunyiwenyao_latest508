package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Inspection;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.TopBarView;
import com.zll.xunyiwenyao.util.TopBarView.onTitleBarClickListener;
import com.zll.xunyiwenyao.webservice.InspectionWebService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InspectionCreateActivity extends Activity implements onTitleBarClickListener {

	private TopBarView topbar;
	private EditText ins_name, ins_location, ins_date, ins_comment;
	private EditText pat_name,   history, doctor_name;
	private Button btn_save, btn_commit;
	private RadioGroup sex_rg;
	private RadioButton sex_rb1, sex_rb2;
	private Calendar calendar;
	private DatePickerDialog datePD;
	private int sex= Utils.SEX.MAN.ordinal();
	private int age = 0;
	private Spinner spinner_type;
	private String type;
	private String arrs_type[];
	private NumberPicker pat_age;
	int minAge = 1, maxAge = 100;

	private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
			// TODO Auto-generated method stub
			ins_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.inspection_create);
		
		topbar = (TopBarView)findViewById(R.id.topbar);
		topbar.setClickListener(this);


		//获取控件信息
		ins_name = (EditText)findViewById(R.id.editText1);
		pat_name = (EditText)findViewById(R.id.name_text);
		//如果是从chat跳转过来，自动填写pat_name
		if(Utils.isFromChat==true){
			pat_name.setText(Utils.PATIENT_NAME);
			Utils.isFromChat=false;
		}

		spinner_type = (Spinner)findViewById(R.id.spinner_type);

//		arrs_type = getResources().getStringArray(R.array.listInspectionTypeArr);
		arrs_type = InspectionWebService.listAllInspectionType();

		ArrayAdapter<String> arrsTitleAdapter = new ArrayAdapter<String>(
				InspectionCreateActivity.this, android.R.layout.simple_list_item_1,arrs_type);
		spinner_type.setAdapter(arrsTitleAdapter);
		spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
				// TODO Auto-generated method stub
				type = arrs_type[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});


		sex_rg = (RadioGroup) findViewById(R.id.sex_rg);
		sex_rb1 = (RadioButton)findViewById(R.id.sex_rb1);
		sex_rb2 = (RadioButton)findViewById(R.id.sex_rb2);
		sex_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if (arg1 == sex_rb1.getId()) {
					sex = Utils.SEX.MAN.ordinal();
				} else {
					sex = Utils.SEX.WOMAN.ordinal();
				}
			}
		});

		pat_age = (NumberPicker)findViewById(R.id.age_text);
		pat_age.setMinValue(minAge);
		pat_age.setMaxValue(maxAge);
		pat_age.setValue(1);
		pat_age.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

			public void onValueChange(NumberPicker packer, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				age = newVal;
			}
		});

//		pat_age = (EditText)findViewById(R.id.age_text);
//		pat_age.setText("0");
		history = (EditText)findViewById(R.id.history_text);
		ins_location = (EditText)findViewById(R.id.inspection_location_text);
		doctor_name = (EditText)findViewById(R.id.doctor_text);
		//自动填写doctor姓名
		doctor_name.setText(Utils.LOGIN_DOCTOR.getRealName());

		ins_date = (EditText)findViewById(R.id.date_text);
		calendar = Calendar.getInstance();
		int year = calendar.get(calendar.YEAR);
		int month = calendar.get(calendar.MONTH);
		int day = calendar.get(calendar.DAY_OF_MONTH);
		datePD = new DatePickerDialog(InspectionCreateActivity.this,
				listener, year, month, day);


		ins_comment = (EditText)findViewById(R.id.comment_text);

		btn_save = (Button)findViewById(R.id.button_save);
		btn_commit = (Button)findViewById(R.id.button_update);

		//日期选择按钮
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		ins_date.setText(date);
		ins_date.setEnabled(false);
		//保存按钮
		btn_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addInspectionByWebService();

			}
		});
		//更新按钮
		btn_commit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				commitInspectionByWebService();

			}
		});
	}
	//导航信息
	@Override
	public void onBackClick() {
		InspectionCreateActivity.this.finish();
	}
	@Override
	public void onRightClick() {
//		Toast.makeText(InspectionCreateActivity.this, "你点击了右侧按钮", Toast.LENGTH_SHORT).show();
		
	}
	
	public void addInspectionByWebService(){
		if(ins_name.getText().toString().equals("")
				||pat_name.getText().toString().equals("")
				||ins_location.getText().toString().equals("")){
			Toast.makeText(InspectionCreateActivity.this, "您输入的信息不完整！",
					Toast.LENGTH_SHORT).show();
		}else{
			Inspection inspection = new Inspection();

			inspection.setInspectionID(0);//ID为后台系统自增，与前端无关

			inspection.setInspectionName(ins_name.getText().toString());
			inspection.setType(type);
			inspection.setInspectionLocation(ins_location.getText().toString());
			inspection.setInspectionDate(ins_date.getText().toString());
			inspection.setInspectionComment(ins_comment.getText().toString());

			inspection.setPatientName(pat_name.getText().toString());
			inspection.setPatientSex(sex);
			inspection.setPatientHistory(history.getText().toString());
//			inspection.setPatientAge(Integer.parseInt(pat_age.getText().toString()));
			inspection.setPatientAge(age);
			inspection.setPatientId(InspectionWebService.getUserIDByName(pat_name.getText().toString()));
			inspection.setDoctorID(Utils.LOGIN_DOCTOR.getId());
			inspection.setDoctorName(Utils.LOGIN_DOCTOR.getRealName());


			inspection.setInspectionState(Utils.INSPECTION_STATUS.UNCOMMITED.ordinal());


			InspectionWebService.addInspection(inspection);
			Toast.makeText(InspectionCreateActivity.this, "检查单保存成功", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	public void commitInspectionByWebService(){
		if(ins_name.getText().toString().equals("")
				||pat_name.getText().toString().equals("")
				||ins_location.getText().toString().equals("")){
			Toast.makeText(InspectionCreateActivity.this, "您输入的信息不完整！",
					Toast.LENGTH_SHORT).show();
		}else{
			Inspection inspection = new Inspection();

			inspection.setInspectionID(0);//ID为后台系统自增，与前端无关

			inspection.setType(type);
			inspection.setInspectionName(ins_name.getText().toString());
			inspection.setInspectionLocation(ins_location.getText().toString());
			inspection.setInspectionDate(ins_date.getText().toString());
			inspection.setInspectionComment(ins_date.getText().toString());

			inspection.setPatientName(pat_name.getText().toString());
			inspection.setPatientSex(sex);

//	！！！		inspection.setPatientAge(pat_age.getText().toString());

			inspection.setPatientAge(age);
			inspection.setPatientId(InspectionWebService.getUserIDByName(pat_name.getText().toString()));
			inspection.setPatientHistory(history.getText().toString());
			inspection.setDoctorID(Utils.LOGIN_DOCTOR.getId());
			inspection.setDoctorName(Utils.LOGIN_DOCTOR.getRealName());

			inspection.setInspectionState(Utils.INSPECTION_STATUS.COMMITED.ordinal());

			InspectionWebService.addInspection(inspection);

			Toast.makeText(InspectionCreateActivity.this, "检查单已提交", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

}
