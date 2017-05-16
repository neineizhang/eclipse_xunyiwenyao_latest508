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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.Review;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.TopBarView;
import com.zll.xunyiwenyao.util.TopBarView.onTitleBarClickListener;
import com.zll.xunyiwenyao.webservice.DrugWebService;
import com.zll.xunyiwenyao.webservice.ReviewWebService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReviewCreateActivity extends Activity implements onTitleBarClickListener {

	private TopBarView topbar;
	private EditText review_name, drug_name, review_content, review_date, doctor_name, review_commet;
	private Button btn_commit;
	private Calendar calendar;
	private DatePickerDialog datePD;
	private AlertDialog alert = null;
	private AlertDialog.Builder builder = null;
	private Button btn_drug_choose,dialog_ok_btn;
	private View view_custom;
	private ExpandableListView add_drugs_lv;
	private AutoCompleteTextView add_drugs_autv;
	private static final String[] data = new String[] { "first", "second", "third", "forth", "fifth" };
	private Map<String, List<String>> dataset = new HashMap<String, List<String>>();
	private ListView drugs_lv;
	private MyExpandableListViewAdapter2 adapter;
	private String[] parentList = new String[] { "first" };
	private int drug_id;






	private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
			// TODO Auto-generated method stub
			review_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.review_create);
		
		topbar = (TopBarView)findViewById(R.id.topbar);

		topbar.setClickListener(this);

		review_name = (EditText)findViewById(R.id.review_name);
		drug_name = (EditText)findViewById(R.id.drug_text);
		review_content = (EditText)findViewById(R.id.review_content);
		review_date = (EditText)findViewById(R.id.date_text);
		review_commet =(EditText)findViewById(R.id.comment_text);
		doctor_name = (EditText)findViewById(R.id.doctor_text);



		btn_commit = (Button)findViewById(R.id.button_commit);

		//药品选择
		btn_drug_choose = (Button)findViewById(R.id.drug_choose);
		builder = new AlertDialog.Builder(this);
		view_custom = View.inflate(this, R.layout.add_drugs_dialog, null);

		btn_drug_choose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.show();
			}
		});
		add_drugs_lv = (ExpandableListView) view_custom.findViewById(R.id.add_drugs_lv);
		add_drugs_autv = (AutoCompleteTextView) view_custom.findViewById(R.id.add_drugs_autv);

		dialog_ok_btn = (Button) view_custom.findViewById(R.id.dialog_ok_btn);
		ArrayAdapter<String> autvadapter = new ArrayAdapter<String>(ReviewCreateActivity.this,
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
				Toast.makeText(ReviewCreateActivity.this, dataset.get(parentList[parentPos]).get(childPos),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		builder.setView(view_custom);
		alert = builder.create();
		dialog_ok_btn.setOnClickListener(new View.OnClickListener() {

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
				drug_name.setText(add_drugs_autv.getText().toString());
				drug_id = drug.getId();
			}
		});

		//自动填写医生
		doctor_name.setText(Utils.LOGIN_DOCTOR.getRealName().toString());
		//日期选择按钮
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		review_date.setText(date);
		review_date.setEnabled(false);

		//提交按钮
		btn_commit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				commitReview();

			}
		});

	}
	public void commitReview(){
		if(review_name.getText().toString().equals("")
				||drug_name.getText().toString().equals("")
				||review_content.getText().toString().equals("")){
			Toast.makeText(ReviewCreateActivity.this, "您输入的信息不完整！",
					Toast.LENGTH_SHORT).show();
		}else{
			Review review = new Review();
			review.setReviewID(0);
			review.setName(review_name.getText().toString());
			review.setDrugName(drug_name.getText().toString());
			review.setDrugID(drug_id);
			review.setContent(review_content.getText().toString());
			review.setDate(review_date.getText().toString());
			review.setDoctorID(Utils.LOGIN_DOCTOR.getId());
			review.setDoctorName(Utils.LOGIN_DOCTOR.getRealName());
			review.setComment(review_commet.getText().toString());
			ReviewWebService.addReview(review);
			Toast.makeText(ReviewCreateActivity.this, "药品评价提交成功！", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	@Override
	public void onBackClick() {
		ReviewCreateActivity.this.finish();
	}
	@Override
	public void onRightClick() {
//		Toast.makeText(ReviewCreateActivity.this, "你点击了右侧按钮", Toast.LENGTH_SHORT).show();
		
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
				LayoutInflater inflater = (LayoutInflater) ReviewCreateActivity.this
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
				convertView = LayoutInflater.from(ReviewCreateActivity.this)
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
		// childrenList1.add(parentList[0] + "-" + "first");
		// childrenList1.add(parentList[0] + "-" + "second");
		// childrenList1.add(parentList[0] + "-" + "third");
		// childrenList2.add(parentList[1] + "-" + "first");
		// childrenList2.add(parentList[1] + "-" + "second");
		// childrenList2.add(parentList[1] + "-" + "third");
		// childrenList3.add(parentList[2] + "-" + "first");
		// childrenList3.add(parentList[2] + "-" + "second");
		// childrenList3.add(parentList[2] + "-" + "third");
		// dataset.put(parentList[0], childrenList1);
		// dataset.put(parentList[1], childrenList2);
		// dataset.put(parentList[2], childrenList3);

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
			Toast.makeText(ReviewCreateActivity.this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
		}
	};
	

	
	

}
