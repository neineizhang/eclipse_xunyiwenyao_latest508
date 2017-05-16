package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.adapter.MyBaseExpandableListAdapter;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.Group;
import com.zll.xunyiwenyao.util.Item;
import com.zll.xunyiwenyao.webservice.PrescriptionTemplateWebService;
import com.zll.xunyiwenyao.webservice.PrescriptionWebService;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionCreateActivity extends Activity   {

	private AutoCompleteTextView prescription_create_search_text ;
	private Button prescription_create_search_button;
	private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private Context mContext;
    private ExpandableListView exlist_lol;
    private MyBaseExpandableListAdapter myAdapter = null;
    private String[] data;

    /**
     * template data init
     */
    private void initData(){
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();

        for(String item : Utils.DEPARTMENT_ARRAY) {
            gData.add(new Group(item));
            iData.add(new ArrayList<Item>());
        }

        List<PrescriptionTemplate> templatelt = PrescriptionTemplateWebService.getAllTemplate();
        for(PrescriptionTemplate item : templatelt){
            iData.get(item.getDepartment()).add(new Item(R.drawable.item_picture, item.getName()));
        }
        // auto-complete
        List<String> namelt = PrescriptionTemplateWebService.getAllTemplateName();
        data = new String[namelt.size()];
        for(int i = 0; i < namelt.size(); i++){
        	data[i] = namelt.get(i);
        }
    }

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.prescription_create);

		prescription_create_search_text = (AutoCompleteTextView) findViewById(R.id.prescription_create_search_text);
		prescription_create_search_button = (Button) findViewById(R.id.prescription_create_search_button);
		

        initData();
        
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(PrescriptionCreateActivity. this, android.R.layout.simple_dropdown_item_1line, data);

        prescription_create_search_text.setAdapter(adapter);
	    mContext = PrescriptionCreateActivity.this;
	    exlist_lol = (ExpandableListView) findViewById(R.id.exlist_lol);

        myAdapter = new MyBaseExpandableListAdapter(gData,iData,mContext);
        exlist_lol.setAdapter(myAdapter);

       //为列表设置点击事件
       exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
           @Override
           public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

               prescription_create_search_text.setText(iData.get(groupPosition).get(childPosition).getiName());
               return true;
           }

       });

       //为返回按钮添加监听事件
//       prescription_create_return.setOnClickListener(new OnClickListener() {
//
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Intent i = new  Intent(PrescriptionCreateActivity.this,MainActivity.class);
//			startActivity(i);
//			finish();
//		}
//	});


       //为确定按钮添加监听事件
       prescription_create_search_button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			List<String> list = new ArrayList<String>();
		    list = PrescriptionTemplateWebService.getAllTemplateName();
			String template_name = prescription_create_search_text.getText().toString();
			if(!list.contains(template_name) && !(template_name.equals("")))
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(PrescriptionCreateActivity.this);
				builder.setTitle("提示");
				builder.setMessage("您输入的模板名不存在。是否新建模板？");
				builder.setIcon(R.drawable.ic_launcher);
			    builder.setCancelable(false);
			    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent i2=new Intent(PrescriptionCreateActivity.this,PrescriptionCreateMainActivity.class);
						i2.putExtra("template_name", ""); 
						startActivity(i2);
						finish();
					}
				});
			    builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
			    	 
			     @Override
			          public void onClick(DialogInterface dialog, int which) {
			    	  // TODO Auto-generated method stub
			              dialog.dismiss();
			      }
			      });
			    builder.create().show();
			}
			else{
			Intent i2=new Intent(PrescriptionCreateActivity.this,PrescriptionCreateMainActivity.class);
			i2.putExtra("template_name", template_name); 
			startActivity(i2);
			finish();
		}
		}
	});
	
	}

}
