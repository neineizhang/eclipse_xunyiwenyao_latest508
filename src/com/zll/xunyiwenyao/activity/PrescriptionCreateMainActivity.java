package com.zll.xunyiwenyao.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.Patient;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Prescription_drugmap;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.ListViewUtils;
import com.zll.xunyiwenyao.view.PrescriptionCreateScrollView;
import com.zll.xunyiwenyao.view.PrescriptionTemplateScrollView;
import com.zll.xunyiwenyao.webservice.DrugWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionTemplateWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionCreateMainActivity extends Activity {

	private Button save, savetotemplate, commit, delete;
	private EditText patient_name_text, chufangmingcheng;
	private EditText prescription_data_et, doctor_name_et, checker_name_et, other_information_et, clinical_diagnosis_et;
	private RadioGroup radioGroupsex;
	private NumberPicker patient_age_text;
	int minAge = 1, maxAge = 100;
	private RadioButton radioman, radiowoman;
	private Button add_drug, dialog_ok_btn;
	private View view_custom;
	private Context mContext;
	private AlertDialog alert = null;
	private AlertDialog.Builder builder = null;
	private ExpandableListView add_drugs_lv;
	private MyExpandableListViewAdapter2 adapter;
	private AutoCompleteTextView add_drugs_autv;
	private Map<String, List<String>> dataset = new HashMap<String, List<String>>();
	private String[] parentList = new String[] { "first" };
	private static final String[] data = new String[] { "first", "second", "third", "forth", "fifth" };
	public int patient_sex, patient_age;
	private ListView drugs_lv;
	public HorizontalScrollView mTouchView;
	protected List<PrescriptionCreateScrollView> mHScrollViews = new ArrayList<PrescriptionCreateScrollView>();

	private int prescription_id = 0;
	private int modify = 0;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newprescription);

		patient_age_text = (NumberPicker) findViewById(R.id.patient_age_text);
		patient_age_text.setMinValue(minAge);
		patient_age_text.setMaxValue(maxAge);
		patient_age_text.setValue(1);
		patient_age_text.setOnValueChangedListener(new OnValueChangeListener() {

			public void onValueChange(NumberPicker packer, int oldVal, int newVal) {
				// TODO Auto-generated method stub
				patient_age = newVal;
			}
		});
		prescription_data_et = (EditText) findViewById(R.id.prescription_data_et);
		doctor_name_et = (EditText) findViewById(R.id.doctor_name_et);
		checker_name_et = (EditText) findViewById(R.id.checker_name_et);
		other_information_et = (EditText) findViewById(R.id.other_information_et);
		clinical_diagnosis_et = (EditText) findViewById(R.id.clinical_diagnosis_text);

		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		prescription_data_et.setText(date);
		prescription_data_et.setEnabled(false);
		doctor_name_et.setText(Utils.LOGIN_DOCTOR.getRealName());
		doctor_name_et.setEnabled(false);
		checker_name_et.setText("");
		checker_name_et.setEnabled(false);
		other_information_et.setText("");

		add_drug = (Button) findViewById(R.id.add_drug);

		initViews();
		builder = new AlertDialog.Builder(this);
		view_custom = View.inflate(this, R.layout.add_drugs_dialog, null);
		add_drug.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.show();
			}
		});

		add_drugs_lv = (ExpandableListView) view_custom.findViewById(R.id.add_drugs_lv);
		add_drugs_autv = (AutoCompleteTextView) view_custom.findViewById(R.id.add_drugs_autv);

		dialog_ok_btn = (Button) view_custom.findViewById(R.id.dialog_ok_btn);
		ArrayAdapter<String> autvadapter = new ArrayAdapter<String>(PrescriptionCreateMainActivity.this,
				android.R.layout.simple_dropdown_item_1line, data);
		add_drugs_autv.setAdapter(autvadapter);

		initialData();
		adapter = new MyExpandableListViewAdapter2();
		add_drugs_lv.setAdapter(adapter);
		add_drugs_lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView expandableListView, View view, int parentPos, int childPos,
					long l) {
				add_drugs_autv.setText(dataset.get(parentList[parentPos]).get(childPos));
				Toast.makeText(PrescriptionCreateMainActivity.this, dataset.get(parentList[parentPos]).get(childPos),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		builder.setView(view_custom);
		alert = builder.create();

		dialog_ok_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String drugname = add_drugs_autv.getText().toString();
				Drug drug = DrugWebService.getDrugByName(drugname);
				if (drug == null) {
					Toast.makeText(mContext, "不存在该药品", Toast.LENGTH_SHORT).show();
					alert.dismiss();
					return;
				}
                //判断药品是否已经存在

				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter) drugs_lv.getAdapter())
						.getData();

				boolean isExist = false;
				for(Map<String, String> item : datas){
					int tmpid = Integer.parseInt(item.get("title"));
					if(drug.getId() == tmpid){
						isExist = true;
						int count = Integer.parseInt(item.get("data_3"));
						item.put("data_3", ""+(count+1));
						break;
					}
				}

				if(isExist){
					((ScrollAdapter) drugs_lv.getAdapter()).setData(datas);
					((ScrollAdapter) drugs_lv.getAdapter()).notifyDataSetChanged();
					alert.dismiss();
					return;
				}


				Map<String, String> tempdata = new HashMap<String, String>();
				tempdata.put("id", drug.getId() + "");
				tempdata.put("title", String.valueOf(drug.getId()));
				tempdata.put("data_" + 1, drug.getName());
				tempdata.put("data_" + 2, drug.getSpecification());
				tempdata.put("data_" + 3, "1");
				tempdata.put("data_" + 4, drug.getDosage_form());
				tempdata.put("data_" + 5, "");
				datas.add(tempdata);
				((ScrollAdapter) drugs_lv.getAdapter()).setData(datas);
				((ScrollAdapter) drugs_lv.getAdapter()).notifyDataSetChanged();
				alert.dismiss();
			}
		});

		mContext = this;

		save = (Button) findViewById(R.id.save);
		savetotemplate = (Button) findViewById(R.id.savetotemplate);
		commit = (Button) findViewById(R.id.commit);
		delete = (Button) findViewById(R.id.delete);
		delete.setVisibility(View.INVISIBLE);
		chufangmingcheng = (EditText) findViewById(R.id.editText1);
		patient_name_text = (EditText) findViewById(R.id.patient_name_text);
		//如果是从chat跳转过来，自动填写pat_name
		if(Utils.isFromChat==true){
			patient_name_text.setText(Utils.PATIENT_NAME);
			Utils.isFromChat=false;
		}
		radioGroupsex = (RadioGroup) findViewById(R.id.radioGroupsex);

		radioman = (RadioButton) findViewById(R.id.radioman);
		radiowoman = (RadioButton) findViewById(R.id.radiowoman);
		radioGroupsex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton radbtn = (RadioButton) findViewById(checkedId);
				if (radbtn == radioman) {
					patient_sex = 0;
				} else if (radbtn == radiowoman) {
					patient_sex = 1;
				}
			}
		});
		///////////// add template data
		Bundle extras = getIntent().getExtras();
		String template_name = extras.getString("template_name");
		if (template_name != null) {
			if (!template_name.trim().equals("")) {
				PrescriptionTemplate prescriptionTemplate = PrescriptionTemplateWebService
						.getPrescriptionTemplateByName(template_name);
				if (prescriptionTemplate == null) {

				} else {
					// chufangmingcheng.setText(template_name);

					// Map<Drug, Integer> drugmap =
					// prescriptionTemplate.getDrugmap();
					List<Prescription_drugmap> druglist = prescriptionTemplate.getDruglist();

					List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter) drugs_lv
							.getAdapter()).getData();
					if (datas == null) {
						datas = new ArrayList<Map<String, String>>();
					}
					// for (Drug drug : drugmap.keySet()) {
					for (Prescription_drugmap drugitem : druglist) {
						Map<String, String> tempdata = new HashMap<String, String>();

						tempdata.put("title", String.valueOf(drugitem.getDrug().getId()));
						tempdata.put("data_" + 1, drugitem.getDrug().getName());
						tempdata.put("data_" + 2, drugitem.getDrug().getSpecification());
						tempdata.put("data_" + 3, drugitem.getCount() + "");
						tempdata.put("data_" + 4, drugitem.getDrug().getDosage_form());
						tempdata.put("data_" + 5, drugitem.getDescription());

						// tempdata.put("title", String.valueOf(drug.getId()));
						// tempdata.put("data_" + 1, drug.getName());
						// tempdata.put("data_" + 2, drug.getSpecification());
						// tempdata.put("data_" + 3, drugmap.get(drug) + "");
						// tempdata.put("data_" + 4, drug.getDosage_form());
						// tempdata.put("data_" + 5, "");
						datas.add(tempdata);
					}
					((ScrollAdapter) drugs_lv.getAdapter()).setData(datas);
					((ScrollAdapter) drugs_lv.getAdapter()).notifyDataSetChanged();
				}

			}
		}
		///////////// end add template data

		String prescription_name = extras.getString("prescription_name");
		if (prescription_name != null) {
			modify = 1;
			delete.setVisibility(View.VISIBLE);
			Prescription prescription = PrescriptionWebService.getPrescriptionByName(prescription_name);

			if (prescription == null) {
				Toast.makeText(mContext, "处方名称无效", Toast.LENGTH_SHORT).show();

			} else {
				//// debug do not modify other prescription
				if(prescription.getDoctor().getId() != Utils.LOGIN_DOCTOR.getId()){
					save.setVisibility(View.INVISIBLE);
					savetotemplate.setVisibility(View.INVISIBLE);
					commit.setVisibility(View.INVISIBLE);
					delete.setVisibility(View.INVISIBLE);
				}
				////
				
				prescription_id = prescription.getId();
				// chufangmingcheng.setText(template_name);
				String patient_name = prescription.getPatient().getName().toString();
				patient_age = prescription.getPatient().getAge();
				int patient_sex = prescription.getPatient().getSex();
				// String
				// doctor_name=prescription.getDoctor().getName().toString();
				// String prescription_date = prescription.getDate().toString();
				String clinical_diagnosis = prescription.getClinical_diagnosis().toString();

				chufangmingcheng.setText(prescription_name);
				patient_name_text.setText(patient_name);
				patient_age_text.setValue(patient_age);

				if (patient_sex == 0) {
					radioman.setChecked(true);
				} else
					radiowoman.setChecked(true);
				//
				// doctor_name_et.setText(doctor_name);
				// prescription_data_et.setText(prescription_date);
				clinical_diagnosis_et.setText(clinical_diagnosis);
				// Map<Drug, Integer> drugmap = prescription.getDrugmap();
				List<Prescription_drugmap> druglist = prescription.getDruglist();

				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter) drugs_lv.getAdapter())
						.getData();
				if (datas == null) {
					datas = new ArrayList<Map<String, String>>();
				}

				for (Prescription_drugmap drugitem : druglist) {
					Map<String, String> tempdata = new HashMap<String, String>();
					tempdata.put("title", String.valueOf(drugitem.getDrug().getId()));
					tempdata.put("data_" + 1, drugitem.getDrug().getName());
					tempdata.put("data_" + 2, drugitem.getDrug().getSpecification());
					tempdata.put("data_" + 3, drugitem.getCount() + "");
					tempdata.put("data_" + 4, drugitem.getDrug().getDosage_form());
					tempdata.put("data_" + 5, drugitem.getDescription());
					datas.add(tempdata);
				}

				((ScrollAdapter) drugs_lv.getAdapter()).setData(datas);
				((ScrollAdapter) drugs_lv.getAdapter()).notifyDataSetChanged();

				///////

			}

		}

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String prescription_name = chufangmingcheng.getText().toString();
				String patient_name = patient_name_text.getText().toString();
				String prescription_date = prescription_data_et.getText().toString();
				String clinical_diagnosis = clinical_diagnosis_et.getText().toString();

				Patient patient = new Patient();
				patient.setAge(patient_age);
				patient.setName(patient_name);
				patient.setSex(patient_sex);
				patient.setId(PrescriptionWebService.getUserIDByName(patient_name));
				// Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>();
				List<Prescription_drugmap> druglist = new ArrayList<Prescription_drugmap>();

				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter) drugs_lv.getAdapter())
						.getData();
				for (Map<String, String> item : datas) {
					Prescription_drugmap prescription_frug = new Prescription_drugmap();

					Drug drug = DrugWebService.getDrugByID(Integer.parseInt(item.get("title")));
					// drug.setId(Integer.parseInt(item.get("title")));
					// drug.setDescription(item.get("data_5"));
					// drug.setPrice(item.get("data_4"));
					// drug.setName(item.get("data_1"));
					// drug.setSpecification(item.get("data_2"));
					// int count = Integer.valueOf(item.get("data_3"));

					prescription_frug.setDrug(drug);
					prescription_frug.setCount(Integer.valueOf(item.get("data_3")));
					prescription_frug.setDescription(item.get("data_5"));
					Log.d("rxz", "001:" + prescription_frug.getDescription());
					Log.d("rxz", "002:" + prescription_frug.getDescription());
					druglist.add(prescription_frug);
					// drugmap.put(drug, count);
				}
				// List<String> list = new ArrayList<String>();
				// list = PrescriptionWebService.getAllPrescriptionName();
				// if (list.contains(prescription_name)) {
				// Toast.makeText(mContext, "该处方名称已存在",
				// Toast.LENGTH_SHORT).show();
				// } else {
				Prescription prescription = new Prescription();
				prescription.setId(prescription_id);
				prescription.setPatient(patient);
				prescription.setName(prescription_name);
				// prescription.setDrugmap(drugmap);
				prescription.setDruglist(druglist);
				prescription.setStatus(Utils.STATUS.SAVED.ordinal());
				prescription.setDate(prescription_date);
				prescription.setClinical_diagnosis(clinical_diagnosis);
				prescription.setDoctor(Utils.LOGIN_DOCTOR);

				Log.d("rxz", "003:" + prescription.getId());
				if(modify == 0){
//					PrescriptionWebService.AddPrescription(prescription);
					Map<String, String> map = PrescriptionWebService.AddPrescription(prescription);
					String result = map.get("result");
					String message = map.get("message");
					if(result.equals("1")){//注册成功
						Toast.makeText(PrescriptionCreateMainActivity.this, "保存成功！",
								Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(PrescriptionCreateMainActivity.this, message,
								Toast.LENGTH_SHORT).show();
					}
				}
				else{
	    			PrescriptionWebService.updatePrescription(prescription);
					Toast.makeText(PrescriptionCreateMainActivity.this, "保存成功！",	Toast.LENGTH_SHORT).show();
					finish();
//					String result = map.get("result");
//					String message = map.get("message");
//					if(result.equals("1")){//注册成功
//						Toast.makeText(PrescriptionCreateMainActivity.this, "处方更新成功！",
//								Toast.LENGTH_SHORT).show();
//						finish();
//					}else{
//						Toast.makeText(PrescriptionCreateMainActivity.this, message,
//								Toast.LENGTH_SHORT).show();
//					}
	     		};
				//PrescriptionWebService.AddPrescription(prescription);

//				Toast.makeText(mContext, "SAVE SUCCESS", Toast.LENGTH_SHORT).show();
//				finish();
				// }
			}
		});
		savetotemplate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String prescription_name = chufangmingcheng.getText().toString();
				// PrescriptionTemplate prescriptionTemplate_indb =
				// PrescriptionTemplateWebService.getPrescriptionTemplateByName(prescription_name);
				// if(prescriptionTemplate_indb != null){
				// Toast.makeText(mContext, "HAVED, SAVE FAILURE",
				// Toast.LENGTH_SHORT).show();
				// return;
				// }
				//
				// Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>();
				List<Prescription_drugmap> druglist = new ArrayList<Prescription_drugmap>();

				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter) drugs_lv.getAdapter())
						.getData();
				for (Map<String, String> item : datas) {
					Prescription_drugmap prescription_frug = new Prescription_drugmap();

					Drug drug = DrugWebService.getDrugByID(Integer.parseInt(item.get("title")));
					// drug.setId(Integer.parseInt(item.get("title")));
					// drug.setDescription(item.get("data_5"));
					// drug.setPrice(item.get("data_4"));
					// drug.setName(item.get("data_1"));
					// drug.setSpecification(item.get("data_2"));
					// int count = Integer.valueOf(item.get("data_3"));

					prescription_frug.setDrug(drug);
					prescription_frug.setCount(Integer.valueOf(item.get("data_3")));
					prescription_frug.setDescription(item.get("data_5"));

					druglist.add(prescription_frug);
					// drugmap.put(drug, count);
				}
				List<String> list = new ArrayList<String>();
				list = PrescriptionTemplateWebService.getAllTemplateName();
				if (list.contains(prescription_name)) {
					Toast.makeText(mContext, "该模板名称已存在", Toast.LENGTH_SHORT).show();
				} else {

					final PrescriptionTemplate prescriptionTemplate = new PrescriptionTemplate();
					final String[] departments = Utils.DEPARTMENT_ARRAY;

					prescriptionTemplate.setName(prescription_name);
					// prescriptionTemplate.setDrugmap(drugmap);
					prescriptionTemplate.setDruglist(druglist);
					
					AlertDialog.Builder builder = new AlertDialog.Builder(PrescriptionCreateMainActivity.this);
					builder.setTitle("请选择模板分类");
					/**
					 * 
					 * 1、public Builder setItems(int itemsId, final
					 * OnClickListener
					 * 
					 * listener) itemsId表示字符串数组的资源ID，该资源指定的数组会显示在列表中。 2、public
					 * Builder
					 * 
					 * setItems(CharSequence[] items, final OnClickListener
					 * listener)
					 * 
					 * items表示用于显示在列表中的字符串数组
					 * 
					 */
					builder.setSingleChoiceItems(departments, 1, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String select_item = departments[which].toString();
							Toast.makeText(PrescriptionCreateMainActivity.this, "选择了--->>" + select_item,
									Toast.LENGTH_SHORT).show();
							prescriptionTemplate.setDepartment(which);
							//PrescriptionTemplateWebService.addPrescriptionTemplate(prescriptionTemplate);
						}
					});
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							Map<String, String> map = PrescriptionTemplateWebService.addPrescriptionTemplate(prescriptionTemplate);
							String result = map.get("result");
							String message = map.get("message");
							if(result.equals("1")){//注册成功
								Toast.makeText(PrescriptionCreateMainActivity.this, "保存成功！",
										Toast.LENGTH_SHORT).show();
								finish();
							}else{
								Toast.makeText(PrescriptionCreateMainActivity.this, message,
										Toast.LENGTH_SHORT).show();
								finish();
							}
//							Toast.makeText(mContext, "SAVE SUCCESS", Toast.LENGTH_SHORT).show();
//							finish();
						}
					});
					builder.create().show();

				}
			}
		});

		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String prescription_name = chufangmingcheng.getText().toString();
				String patient_name = patient_name_text.getText().toString();
				String prescription_date = prescription_data_et.getText().toString();
				String clinical_diagnosis = clinical_diagnosis_et.getText().toString();

				Patient patient = new Patient();
				patient.setAge(patient_age);
				patient.setName(patient_name);
				patient.setSex(patient_sex);
				patient.setId(PrescriptionWebService.getUserIDByName(patient_name));
				List<String> list = new ArrayList<String>();
				list = PrescriptionWebService.getAllPrescriptionName();
				// 信息完整性及处方名称唯一性验证
				if (patient_name.equals("")) {
					Toast.makeText(mContext, "病人信息填写不完整", Toast.LENGTH_SHORT).show();
				} else if (clinical_diagnosis.equals("")) {
					Toast.makeText(mContext, "病人信息填写不完整", Toast.LENGTH_SHORT).show();
				} else if (prescription_name.equals("")) {
					Toast.makeText(mContext, "请填写处方名称", Toast.LENGTH_SHORT).show();
//				} else if (list.contains(prescription_name)) {
//					Toast.makeText(mContext, "该处方名称已存在", Toast.LENGTH_SHORT).show();
				}

				else {
					// Map<Drug, Integer> drugmap = new HashMap<Drug,
					// Integer>();
					List<Prescription_drugmap> druglist = new ArrayList<Prescription_drugmap>();
					// List<Drug> druglt = new ArrayList<Drug>();
					List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter) drugs_lv
							.getAdapter()).getData();
					for (Map<String, String> item : datas) {
						// Drug drug = new Drug();
						// drug.setId(Integer.parseInt(item.get("title")));
						// drug.setDescription(item.get("data_5"));
						// drug.setPrice(item.get("data_4"));
						// drug.setName(item.get("data_1"));
						// drug.setSpecification(item.get("data_2"));
						// int count = Integer.valueOf(item.get("data_3"));
						// drugmap.put(drug, count);

						Prescription_drugmap prescription_frug = new Prescription_drugmap();

						Drug drug = DrugWebService.getDrugByID(Integer.parseInt(item.get("title")));
						// drug.setId(Integer.parseInt(item.get("title")));
						// drug.setDescription(item.get("data_5"));
						// drug.setPrice(item.get("data_4"));
						// drug.setName(item.get("data_1"));
						// drug.setSpecification(item.get("data_2"));
						// int count = Integer.valueOf(item.get("data_3"));

						prescription_frug.setDrug(drug);
						prescription_frug.setCount(Integer.valueOf(item.get("data_3")));
						prescription_frug.setDescription(item.get("data_5"));

						druglist.add(prescription_frug);
						// drugmap.put(drug, count);
					}

					Prescription prescription = new Prescription();
					prescription.setId(prescription_id);
					prescription.setPatient(patient);
					prescription.setName(prescription_name);
					// prescription.setDrugmap(drugmap);
					prescription.setDruglist(druglist);
					prescription.setStatus(Utils.STATUS.COMMITED.ordinal());
					prescription.setDate(prescription_date);
					prescription.setClinical_diagnosis(clinical_diagnosis);
					prescription.setDoctor(Utils.LOGIN_DOCTOR);

					System.out.println("rxz++++++++++"+modify+"-"+prescription.getStatus());
					if(modify == 0){
						Map<String, String> map = PrescriptionWebService.AddPrescription(prescription);
						String result = map.get("result");
						String message = map.get("message");
						if(result.equals("1")){//
							Prescription prescription_new = PrescriptionWebService.getPrescriptionByName(prescription_name);
							prescription_new.setStatus(Utils.STATUS.COMMITED.ordinal());
							PrescriptionWebService.updatePrescription(prescription_new);
							Toast.makeText(PrescriptionCreateMainActivity.this, "处方提交成功！",
									Toast.LENGTH_SHORT).show();
							finish();
						}else{
							Toast.makeText(PrescriptionCreateMainActivity.this, message,
									Toast.LENGTH_SHORT).show();
						}



					}else{
		    			PrescriptionWebService.updatePrescription(prescription);
						Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
						finish();
		     		};
					//PrescriptionWebService.AddPrescription(prescription);

//					Toast.makeText(mContext, "SAVE SUCCESS", Toast.LENGTH_SHORT).show();
//					finish();
				}
			}
		});

		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Prescription prescription = new Prescription();
				prescription.setId(prescription_id);
				PrescriptionWebService.delPrescription(prescription);
				Toast.makeText(mContext, "DEL SUCCESS", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

	private void initialData() {
		List<String> namelt = new ArrayList<String>();
		List<Drug> resultDruglt = DrugWebService.getAllDrug();
		// System.out.println(resultDruglt.size());
		for (Drug item : resultDruglt) {
			namelt.add(item.getName());
		}
		dataset.put("first", namelt);
	}

	public class MyExpandableListViewAdapter2 extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int parentPos, int childPos) {
			return dataset.get(parentList[parentPos]).get(childPos);
		}

		@Override
		public int getGroupCount() {
			return dataset.size();
		}

		@Override
		public int getChildrenCount(int parentPos) {
			return dataset.get(parentList[parentPos]).size();
		}

		@Override
		public Object getGroup(int parentPos) {
			return dataset.get(parentList[parentPos]);
		}

		@Override
		public long getGroupId(int parentPos) {
			return parentPos;
		}

		@Override
		public long getChildId(int parentPos, int childPos) {
			return childPos;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) PrescriptionCreateMainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.add_drugs_dialog_item, null);
			}
			view.setTag(R.layout.add_drugs_dialog_list, parentPos);
			view.setTag(R.layout.add_drugs_dialog_item, childPos);
			TextView text = (TextView) view.findViewById(R.id.add_drugs_dialog_item);
			text.setText(dataset.get(parentList[parentPos]).get(childPos));
			return view;
		}

		@Override
		public boolean isChildSelectable(int i, int i1) {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolderGroup groupHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(PrescriptionCreateMainActivity.this)
						.inflate(R.layout.item_exlist_group, parent, false);
				groupHolder = new ViewHolderGroup();
				groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
				convertView.setTag(groupHolder);
			} else {
				groupHolder = (ViewHolderGroup) convertView.getTag();
			}
			groupHolder.tv_group_name.setText(parentList[0]);
			return convertView;
		}

		private class ViewHolderGroup {
			private TextView tv_group_name;
		}
	}

	private void initViews() {
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		PrescriptionCreateScrollView headerScroll = (PrescriptionCreateScrollView) findViewById(R.id.item_scroll_title);

		mHScrollViews.add(headerScroll);
		drugs_lv = (ListView) findViewById(R.id.drugs_lv);

		// 重写listview解决listview和scollview不兼容的问题
		// ListViewUtils.setListViewHeightBasedOnChildren(drugs_lv);

		ScrollAdapter adapter = new ScrollAdapter(this, datas, R.layout.scroll_item,
				new String[] { "title", "data_1", "data_2", "data_3", "data_4", "data_5" }, new int[] { R.id.item_title,
						R.id.item_data1, R.id.item_data2, R.id.item_data3, R.id.item_data4, R.id.item_data5 });
		drugs_lv.setAdapter(adapter);
		

	}

	public void addHViews(final PrescriptionCreateScrollView hScrollView) {
		if (!mHScrollViews.isEmpty()) {
			int size = mHScrollViews.size();
			PrescriptionCreateScrollView scrollView = mHScrollViews.get(size - 1);

			final int scrollX = scrollView.getScrollX();
			if (scrollX != 0) {
				drugs_lv.post(new Runnable() {
					@Override
					public void run() {
						hScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		mHScrollViews.add(hScrollView);
	}

	public void onScrollChanged(int l, int t, int oldl, int oldt) {
		for (PrescriptionCreateScrollView scrollView : mHScrollViews) {
			if (mTouchView != scrollView)
				scrollView.smoothScrollTo(l, t);
		}
	}

	class ScrollAdapter extends SimpleAdapter implements OnClickListener {

		private List<? extends Map<String, ?>> datas;
		private int res;
		private String[] from;
		private int[] to;
		private Context context;

		public ScrollAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from,
				int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.datas = data;
			this.res = resource;
			this.from = from;
			this.to = to;
		}

		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
			List<View> view_lt = ListViewUtils.setListViewHeightBasedOnChildren(drugs_lv);
		}

		public List<? extends Map<String, ?>> getData() {
			return datas;
		}

		public void setData(List<? extends Map<String, ?>> new_data) {
			if (new_data == null) {
				datas = new ArrayList<Map<String, ?>>();
			} else {
				datas = new_data;
			}
			notifyDataSetChanged();
		}

		public void removeItem(int position) {
			if (position < 0 || position > datas.size()) {
				return;
			}
			datas.remove(position);
			notifyDataSetChanged();
		}

		public void removeItem(Map<String, ?> obj) {
			datas.remove(obj);
			notifyDataSetChanged();
		}

		public class ListTextWatcher extends SimpleTextWatcher {
			private EditText mEt;
			private int pos;

			public ListTextWatcher(EditText et, int pos) {
				mEt = et;
				this.pos = pos;
			}
			public void setPosition(int pos){
				this.pos = pos;
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO 自动生成的方法存根
				
				if (mEt.getId() == R.id.item_data3) {
					try {
						if (s == null || s.length() == 0) {
							return;
						}
						Integer num = Integer.valueOf(s.toString());
						@SuppressWarnings("unchecked")
						Map<String,String> data = (Map<String, String>) datas.get(pos);
						data.put("data_3",num.toString());
						Log.d("rxz", "data_3:" + num.toString());
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (mEt.getId() == R.id.item_data5) {
					// 改备注
					@SuppressWarnings("unchecked")
					Map<String,String> data = (Map<String, String>) datas.get(pos);
					data.put("data_5",s==null?"":s.toString());
					Log.d("rxz", "data_5:" + s.toString());
				}
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			View[] holders  = null;
			if (v == null) {
				v = LayoutInflater.from(context).inflate(res, null);
				addHViews((PrescriptionCreateScrollView) v.findViewById(R.id.item_scroll));
				holders = new View[to.length];
				for (int i = 0; i < to.length; i++) {
					View tv = v.findViewById(to[i]);
					if (tv instanceof EditText){
						EditText et =(EditText) tv;
						ListTextWatcher watcher = new ListTextWatcher(et, position);
					    et.addTextChangedListener(watcher);
					    et.setTag(watcher);
					}
					holders[i] = tv;
				}
				ImageView mDeleteBtn = (ImageView) v.findViewById(R.id.item_delete_btn);
				mDeleteBtn.setOnClickListener(this);
				mDeleteBtn.setTag(position);
				v.setTag(holders);
			}else{
				holders= (View[]) v.getTag();
			}
			int len = holders.length;
			for (int i = 0; i < len; i++) {
				if(holders[i] instanceof EditText){
					EditText et = (EditText) holders[i];
					ListTextWatcher watcher = (ListTextWatcher) et.getTag();
					if(watcher != null){
						watcher.setPosition(position);
					}
				}
				Map<String,Object> map =(Map<String, Object>) this.datas.get(position);
				Object obj =map.get(from[i]);
				((TextView) holders[i]).setText(obj.toString());
			}
			return v;
		}

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.item_delete_btn) {
				if (v.getTag() instanceof Integer) {
					Integer pos = (Integer) v.getTag();
					removeItem(pos);
				}
			}
		}
	}

//	protected View.OnClickListener clickListener = new View.OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			// Toast.makeText(PrescriptionCreateMainActivity.this, ((TextView)
//			// v).getText(), Toast.LENGTH_SHORT).show();
//		}
//	};

}
