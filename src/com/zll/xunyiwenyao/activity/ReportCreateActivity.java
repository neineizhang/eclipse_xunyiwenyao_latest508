package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.Report;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.TopBarView;
import com.zll.xunyiwenyao.util.TopBarView.onTitleBarClickListener;
import com.zll.xunyiwenyao.webservice.DrugWebService;
import com.zll.xunyiwenyao.webservice.ReportWebService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportCreateActivity extends Activity implements onTitleBarClickListener {
	private TopBarView topbar;
	private EditText report_name, feature, event_date,report_date, doctor_name, comment;
	private Button btn_event_choose,  btn_commit;
	private Calendar calendar;
	private DatePickerDialog eventDatePD,reportDatePD;
	private Button btn_drug_add;//添加药品按钮
	private View view_custom;
	private ExpandableListView add_drugs_lv;
	private AutoCompleteTextView add_drugs_autv;
	private static final String[] data = new String[] { "first", "second", "third", "forth", "fifth" };
	private Map<String, List<String>> dataset = new HashMap<String, List<String>>();
	private ListView drugs_lv;
	private AlertDialog alert = null;
	private AlertDialog.Builder builder = null;
	private MyExpandableListViewAdapter2 adapter;
	private String[] parentList = new String[] { "first" };
	private DrugListAdapter drugListAdapter;
	private List<Integer> drug_id_list = new ArrayList<Integer>();
	private List<String> drug_name_list = new ArrayList<String>();
	private Spinner spinner_level;
    private String level;
	private String arrs_level[]={"新的","严重","一般"};


//	public HorizontalScrollView mTouchView;
//	protected List<PrescriptionCreateScrollView> mHScrollViews = new ArrayList<PrescriptionCreateScrollView>();



	private DatePickerDialog.OnDateSetListener eventDatelistener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
			// TODO Auto-generated method stub
			event_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
		}
	};
	private DatePickerDialog.OnDateSetListener reportDatelistener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
			// TODO Auto-generated method stub
			report_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.report_create);
		
		topbar = (TopBarView)findViewById(R.id.topbar);
		
		topbar.setClickListener(this);

		report_name = (EditText)findViewById(R.id.name_text);
		feature = (EditText)findViewById(R.id.manifestation_text);
		event_date = (EditText)findViewById(R.id.event_date_text);
		report_date = (EditText)findViewById(R.id.report_date_text);
		doctor_name = (EditText)findViewById(R.id.doctor_text);
		comment=(EditText)findViewById(R.id.comment_text);

		btn_event_choose=(Button)findViewById(R.id.event_date_choose);

		btn_commit=(Button)findViewById(R.id.button_commit);

		btn_drug_add = (Button)findViewById(R.id.add_drug);
		//重要程度
		spinner_level = (Spinner)findViewById(R.id.spinner_level);


		ArrayAdapter<String> arrsAdapter = new ArrayAdapter<String>(ReportCreateActivity.this, android.R.layout.simple_list_item_1,arrs_level);
		spinner_level.setAdapter(arrsAdapter);
		spinner_level.setAdapter(arrsAdapter);
		spinner_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
				// TODO Auto-generated method stub
				level = arrs_level[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		//添加药品按钮
		initViews();
		builder = new AlertDialog.Builder(this);
		view_custom = View.inflate(this, R.layout.add_drugs_dialog, null);

		btn_drug_add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.show();
			}
		});
		add_drugs_lv = (ExpandableListView) view_custom.findViewById(R.id.add_drugs_lv);
		add_drugs_autv = (AutoCompleteTextView) view_custom.findViewById(R.id.add_drugs_autv);

		btn_drug_add = (Button) view_custom.findViewById(R.id.dialog_ok_btn);
		ArrayAdapter<String> autvadapter = new ArrayAdapter<String>(ReportCreateActivity.this,
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
				Toast.makeText(ReportCreateActivity.this, dataset.get(parentList[parentPos]).get(childPos),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		builder.setView(view_custom);
		alert = builder.create();
		btn_drug_add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String drugname = add_drugs_autv.getText().toString();
				Drug drug = DrugWebService.getDrugByName(drugname);
				if(drug == null){
					/////// !!1!
					alert.dismiss();
					return;
				}
				alert.dismiss();
				drug_id_list.add(drug.getId());
				drug_name_list.add(drug.getName());
				((DrugListAdapter)drugs_lv.getAdapter()).setData(drug_id_list,drug_name_list);
				((DrugListAdapter)drugs_lv.getAdapter()).notifyDataSetChanged();
				fixListViewHeight(drugs_lv);

			}
		});


		//自动填写医生
		doctor_name.setText(Utils.LOGIN_DOCTOR.getRealName().toString());

		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		report_date.setText(date);
		report_date.setEnabled(false);
		//默认填写event事件
		SimpleDateFormat sDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		String date2 = sDateFormat2.format(new java.util.Date());
		event_date.setText(date2);

		//日期选择按钮
		calendar = Calendar.getInstance();
		int year = calendar.get(calendar.YEAR);
		int month = calendar.get(calendar.MONTH);
		int day = calendar.get(calendar.DAY_OF_MONTH);
		eventDatePD = new DatePickerDialog(ReportCreateActivity.this, eventDatelistener, year, month, day);
		btn_event_choose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				eventDatePD.show();
			}
		});



		//提交按钮
		btn_commit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				commitReport();

			}
		});

	}

	public void commitReport(){
		if(report_name.getText().toString().equals("")
				||feature.getText().toString().equals("")){
			Toast.makeText(ReportCreateActivity.this, "您输入的信息不完整！",
					Toast.LENGTH_SHORT).show();
		}else{
			Report report = new Report();
			report.setName(report_name.getText().toString());
			report.setLever(level);
			report.setFeature(feature.getText().toString());
			if(event_date.getText().toString().equals(""))
				report.setEventDate("0000-00-00 00:00");
			else{
				report.setEventDate(event_date.getText().toString()+" 00:00");
			}
			report.setReportDate(report_date.getText().toString());
			report.setDoctor(Utils.LOGIN_DOCTOR);
			report.setDoctorID(Utils.LOGIN_DOCTOR.getId());
			report.setDoctorName(Utils.LOGIN_DOCTOR.getRealName());
			report.setComment(comment.getText().toString());
			report.setDrugIDList(drug_id_list);
			report.setDrugNameList(drug_name_list);
			ReportWebService.addReport(report);
			Toast.makeText(ReportCreateActivity.this, "药品不良反应报告提交成功！", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	@Override
	public void onBackClick() {
		ReportCreateActivity.this.finish();
	
	}
	@Override
	public void onRightClick() {
//		Toast.makeText(ReportCreateActivity.this, "你点击了右侧按钮", Toast.LENGTH_SHORT).show();
		
	}

	private void initViews() {
		drugs_lv = (ListView) findViewById(R.id.drugs_lv);
		drugListAdapter = new DrugListAdapter(drug_id_list,drug_name_list,ReportCreateActivity.this);
		drugs_lv.setAdapter(drugListAdapter);

	}

	private class MyExpandableListViewAdapter2 extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int parentPos, int childPos) {
			return dataset.get(parentList[parentPos]).get(childPos);
		}

		@Override
		public int getGroupCount() {
			return dataset.size();
			// return 0;
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
				LayoutInflater inflater = (LayoutInflater) ReportCreateActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.add_drugs_dialog_item, null);
			}
			view.setTag(R.layout.add_drugs_dialog_list, parentPos);
			view.setTag(R.layout.add_drugs_dialog_item, childPos);
			TextView text = (TextView) view.findViewById(R.id.add_drugs_dialog_item);
			text.setText(dataset.get(parentList[parentPos]).get(childPos));
			// text.setOnClickListener(new View.OnClickListener() {
			// @Override
			// public void onClick(View view) {
			// Toast.makeText(New_prescription.this, "閻愮懓鍩屾禍鍡楀敶缂冾喚娈憈extview",
			// Toast.LENGTH_SHORT).show();
			// }
			// });
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
				convertView = LayoutInflater.from(ReportCreateActivity.this)
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

	private void initialData() {


		List<String> namelt = new ArrayList<String>();
		List<Drug> resultDruglt = DrugWebService.getAllDrug();
		// System.out.println(resultDruglt.size());
		for (Drug item : resultDruglt) {
			namelt.add(item.getName());
		}
		dataset.put("first", namelt);
	}


	protected View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(ReportCreateActivity.this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
		}
	};

	public class DrugListAdapter extends BaseAdapter {
		private List<Integer> id_list = new ArrayList<Integer>();
		private List<String> name_list = new ArrayList<String>();
		private Context mContext;

		public DrugListAdapter(List<Integer> idlist, List<String> namelist, Context mContext)
		{   this.mContext = mContext;
			this.id_list=idlist;
			this.name_list=namelist;
		}

		public void setData(List<Integer> idlist, List<String> namelist){this.id_list=idlist;this.name_list=namelist;}
		public List<Integer> getDataID(){return id_list;}
		public List<String> getDataName(){return name_list;}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return id_list.size();
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
			View view = LayoutInflater.from(mContext).inflate(R.layout.drug_listview_report, null);
			TextView drug_list_id = (TextView)view.findViewById(R.id.drug_list_id);
			TextView drug_list_name = (TextView)view.findViewById(R.id.drug_list_name);

			drug_list_id.setText(id_list.get(position).toString());
			drug_list_name.setText(name_list.get(position));
			return view;
		}
	}

	public void fixListViewHeight(ListView listView) {
		// 如果没有设置数据适配器，则ListView没有子项，返回。
		ListAdapter listAdapter = listView.getAdapter();
		int totalHeight = 0;
		if (listAdapter == null) {
			return;
		}
		for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
			View listViewItem = listAdapter.getView(index , null, listView);
			// 计算子项View 的宽高
			listViewItem.measure(0, 0);
			// 计算所有子项的高度和
			totalHeight += listViewItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// listView.getDividerHeight()获取子项间分隔符的高度
		// params.height设置ListView完全显示需要的高度
		params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
