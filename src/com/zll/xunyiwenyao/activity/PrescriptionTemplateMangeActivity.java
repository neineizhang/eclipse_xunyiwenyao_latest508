package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.activity.PrescriptionCreateMainActivity.ScrollAdapter;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Prescription_drugmap;
import com.zll.xunyiwenyao.view.PrescriptionTemplateScrollView;
import com.zll.xunyiwenyao.webservice.PrescriptionTemplateWebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrescriptionTemplateMangeActivity extends Activity implements OnClickListener {
	
	private ListView template_drugs_lv;
	private Button template_manage_save,template_manage_delete;
	private TextView template_manage_name_et;
	public HorizontalScrollView templateTouchView;
	protected List<PrescriptionTemplateScrollView> templateHScrollViews =new ArrayList<PrescriptionTemplateScrollView>();
	PrescriptionTemplate prescriptionTemplate = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.templatemanage);
		template_manage_name_et = (TextView) findViewById(R.id.template_manage_name_et);
		template_manage_save = (Button) findViewById(R.id.template_manage_save);
		template_manage_delete = (Button) findViewById(R.id.template_manage_delete);
		
		initViews();
		template_manage_save.setOnClickListener(this);
		template_manage_delete.setOnClickListener(this);
	}
	
	private void initData(){
		///////////// add template data
		Bundle extras = getIntent().getExtras(); 
		String template_name = extras.getString("template_name");
		template_manage_name_et.setText(template_name);
		template_manage_name_et.setEnabled(false);
		if(!template_name.trim().equals("")){
			prescriptionTemplate = PrescriptionTemplateWebService.getPrescriptionTemplateByName(template_name);
			if(prescriptionTemplate == null){
				Toast.makeText(PrescriptionTemplateMangeActivity.this, "NAME ERROR", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				//chufangmingcheng.setText(template_name);
				
				//Map<Drug, Integer> drugmap = prescriptionTemplate.getDrugmap();
			    List<Prescription_drugmap> druglist = prescriptionTemplate.getDruglist();
				List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter2)template_drugs_lv.getAdapter()).getData();
				if(datas == null){
					datas = new ArrayList<Map<String,String>>();
				}
				//for(Drug drug : drugmap.keySet()){
				for(Prescription_drugmap drugitem : druglist){
					Map<String, String> tempdata = new HashMap<String, String>();
//					tempdata.put("title", String.valueOf(drug.getId()));
//					tempdata.put("data_" + 1, drug.getName());
//					tempdata.put("data_" + 2, drug.getSpecification());
//					tempdata.put("data_" + 3, drugmap.get(drug)+"");
//					tempdata.put("data_" + 4, drug.getPrice());
//					tempdata.put("data_" + 5, drug.getDescription());
					tempdata.put("title", String.valueOf(drugitem.getDrug().getId()));
					tempdata.put("data_" + 1, drugitem.getDrug().getName());
					tempdata.put("data_" + 2, drugitem.getDrug().getSpecification());
					tempdata.put("data_" + 3, drugitem.getCount()+"");
					tempdata.put("data_" + 4, drugitem.getDrug().getDosage_form());
					tempdata.put("data_" + 5, drugitem.getDescription());
					datas.add(tempdata);
				}
				((ScrollAdapter2)template_drugs_lv.getAdapter()).setData(datas);
				((ScrollAdapter2)template_drugs_lv.getAdapter()).notifyDataSetChanged();
			}
			
		}
		///////////// end add template data
		else{
			Toast.makeText(PrescriptionTemplateMangeActivity.this, "NAME IS EMPTY", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
  
	private void initViews() {
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
		//Map<String, String> data = null;
		PrescriptionTemplateScrollView headerScroll = (PrescriptionTemplateScrollView) findViewById(R.id.template_item_scroll_title);
		
		templateHScrollViews.add(headerScroll);
		template_drugs_lv = (ListView) findViewById(R.id.template_drugs_lv);

		SimpleAdapter templatemanadapter = new ScrollAdapter2(this, datas, R.layout.templatemanage_list
							, new String[] { "title", "data_1", "data_2", "data_3", "data_4", "data_5", "data_6", }
							, new int[] { R.id.template_item_title
										, R.id.template_item_data1
										, R.id.template_item_data2
										, R.id.template_item_data3
										, R.id.template_item_data4
										, R.id.template_item_data5
										});
		template_drugs_lv.setAdapter(templatemanadapter);

		///////////// add template data
		initData();
	}
		
	
	public void addHViews(final PrescriptionTemplateScrollView hScrollView) {
		if(!templateHScrollViews.isEmpty()) {
			int size = templateHScrollViews.size();
			PrescriptionTemplateScrollView scrollView = templateHScrollViews.get(size - 1);
			final int scrollX = scrollView.getScrollX();
			
			if(scrollX != 0) {
				template_drugs_lv.post(new Runnable() {
					@Override
					public void run() {
						//锟斤拷listView刷锟斤拷锟斤拷锟街拷螅迅锟斤拷锟斤拷贫锟斤拷锟斤拷锟斤拷锟轿伙拷锟�
						hScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		templateHScrollViews.add(hScrollView);
	}
	
	public void onScrollChanged(int l, int t, int oldl, int oldt){
		for(PrescriptionTemplateScrollView scrollView : templateHScrollViews) {
			//锟斤拷止锟截革拷锟斤拷锟斤拷
			if(templateTouchView != scrollView)
				scrollView.smoothScrollTo(l, t);
		}
	}
	
	class ScrollAdapter2 extends SimpleAdapter {

		private List<? extends Map<String, ?>> datas;
		private int res;
		private String[] from;
		private int[] to;
		private Context context;
		private List<View[]> holders_lt = new ArrayList<View[]>();
		
		public ScrollAdapter2(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			this.context = context;
			this.datas = data;
			this.res = resource;
			this.from = from;
			this.to = to;
			for(int i = 0; i < data.size(); i++){
				holders_lt.add(null);
			}
		}
		
		public List<? extends Map<String, ?>> getData(){
			for(int position = 0; position < holders_lt.size(); position++){
				View[] holders = holders_lt.get(position);
				int len = holders.length;
				for(int i = 0 ; i < len; i++) {
					//Log.d("rxz", "get-i:"+position+":"+i+":"+((TextView)holders[i]).getText().toString());
					String value = ((TextView)holders[i]).getText().toString();
					//Log.d("rxz", "get:"+position+":"+value+":"+this.datas.get(position).get(from[3]).toString());
					((Map<String, String>)this.datas.get(position)).put(from[i], value);
				}
			}
			return datas;
		}
		
		public void  setData(List<? extends Map<String, ?>> new_data){
			datas = new_data;
			for(int i = 0; i < new_data.size(); i++){
				holders_lt.add(null);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if(v == null) {
				v = LayoutInflater.from(context).inflate(res, null);
				//锟斤拷一锟轿筹拷始锟斤拷锟斤拷时锟斤拷装锟斤拷锟斤拷
				addHViews((PrescriptionTemplateScrollView) v.findViewById(R.id.template_item_scroll));
				View[] views = new View[to.length];
				for(int i = 0; i < to.length; i++) {
					View tv = v.findViewById(to[i]);;
					tv.setOnClickListener(clickListener);
					views[i] = tv;
				}
				v.setTag(views);
				if(holders_lt.get(position) == null){
					holders_lt.set(position, views);

				}
			}
			View[] holders = (View[]) v.getTag();
			int len = holders.length;
			for(int i = 0 ; i < len; i++) {
				((TextView)holders[i]).setText(this.datas.get(position).get(from[i]).toString());
			}
			return v;
		}
	}
	
	//锟斤拷锟皆碉拷锟斤拷锟斤拷录锟� 
	protected View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//Toast.makeText(PrescriptionTemplateMangeActivity.this, ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.template_manage_save:
			//// get update info
			List<Map<String, String>> datas = (List<Map<String, String>>) ((ScrollAdapter2)template_drugs_lv.getAdapter()).getData();
			//for(Drug drug : prescriptionTemplate.getDrugmap().keySet()){
			//for(Prescription_drugmap drugitem : prescriptionTemplate.getDruglist()){
			List<Prescription_drugmap> druglist = prescriptionTemplate.getDruglist();
			for(int i = 0; i < druglist.size(); i++){
				Prescription_drugmap drugitem = druglist.get(i);
				for(Map<String, String> data_map : datas){
					if(drugitem.getDrug().getId() == Integer.parseInt(data_map.get("title"))){
						drugitem.setCount(Integer.parseInt(data_map.get("data_3")));
						drugitem.setDescription(data_map.get("data_5"));
						prescriptionTemplate.getDruglist().set(i, drugitem);
					}
				}
			}
			//// end update info 
			PrescriptionTemplateWebService.updatePrescriptionTemplate(prescriptionTemplate);
			Toast.makeText(PrescriptionTemplateMangeActivity.this, "UPDATE SUCCESS", Toast.LENGTH_SHORT).show();
			break;
		case R.id.template_manage_delete:
			PrescriptionTemplateWebService.deletePrescriptionTemplate(prescriptionTemplate);
			Toast.makeText(PrescriptionTemplateMangeActivity.this, "DELETE SUCCESS", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		finish();
	}
}


	
	
	
	
		
        
		