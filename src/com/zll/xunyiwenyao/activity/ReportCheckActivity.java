package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Report;
import com.zll.xunyiwenyao.util.TopBarView;
import com.zll.xunyiwenyao.webservice.ReportWebService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kejund on 17/4/12.
 */

public class ReportCheckActivity extends Activity implements TopBarView.onTitleBarClickListener{
    private TopBarView topbar;
    private EditText report_name, feature, event_date,report_date, doctor_name, comment, level;
    private List<Report> reportList = new ArrayList<Report>();
    private ListView drugs_lv;
    private DrugListAdapter drugListAdapter;
    private List<Integer> drug_id_list = new ArrayList<Integer>();
    private List<String> drug_name_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_check);

        topbar = (TopBarView) findViewById(R.id.topbar);
        topbar.setClickListener(this);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        final int temp = bundle.getInt("position");

        report_name = (EditText)findViewById(R.id.name_text);
        feature = (EditText)findViewById(R.id.manifestation_text);
        event_date = (EditText)findViewById(R.id.event_date_text);
        report_date = (EditText)findViewById(R.id.report_date_text);
        doctor_name = (EditText)findViewById(R.id.doctor_text);
        comment=(EditText)findViewById(R.id.comment_text);
        level = (EditText)findViewById(R.id.level_text);

        reportList = ReportWebService.getAllReport();

        report_name.setText(reportList.get(temp).getName().toString());
        level.setText(reportList.get(temp).getLever().toString());
        feature.setText(reportList.get(temp).getFeature().toString());
        event_date.setText(reportList.get(temp).getEvent_Date().toString().substring(0,10));
        report_date.setText(reportList.get(temp).getReportDate().toString());
        doctor_name.setText(reportList.get(temp).getDoctorName().toString());
        comment.setText(reportList.get(temp).getComment().toString());

        drug_id_list = reportList.get(temp).getDrugIDList();
        drug_name_list = reportList.get(temp).getDrugNameList();

        drugs_lv = (ListView) findViewById(R.id.drugs_lv);
        drugListAdapter = new DrugListAdapter(drug_id_list,drug_name_list, ReportCheckActivity.this);
        drugs_lv.setAdapter(drugListAdapter);
        fixListViewHeight(drugs_lv);


    }
    @Override
    public void onBackClick() {
        ReportCheckActivity.this.finish();
    }
    @Override
    public void onRightClick() {

//        Toast.makeText(ReviewCheckActivity.this, "点击右侧按钮", Toast.LENGTH_SHORT).show();

    }

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
