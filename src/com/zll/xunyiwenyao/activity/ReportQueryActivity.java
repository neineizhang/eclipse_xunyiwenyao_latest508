package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.adapter.ReportAdapter;
import com.zll.xunyiwenyao.dbitem.Report;
import com.zll.xunyiwenyao.util.TopBarView;
import com.zll.xunyiwenyao.webservice.ReportWebService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kejund on 17/4/12.
 */

public class ReportQueryActivity extends Activity implements TopBarView.onTitleBarClickListener {
    private TopBarView topbar;
    private ListView listview;
    private ReportAdapter myAdapter;
    private List<Report> reportList = new ArrayList<Report>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_query);

        topbar = (TopBarView) findViewById(R.id.topbar);
        topbar.setClickListener(this);

        reportList = ReportWebService.getAllReport();
        myAdapter = new ReportAdapter(reportList, ReportQueryActivity.this);
        listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(myAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                showInformationPage(arg2);

            }
        });
    }
    @Override
    public void onBackClick() {
        ReportQueryActivity.this.finish();
    }
    @Override
    public void onRightClick() {
//        Intent i=new Intent(ReviewQueryActivity.this, ReviewCreateActivity.class);
//        startActivity(i);
    }

    public void showInformationPage(final int temp){
        Intent intent =new Intent(ReportQueryActivity.this,ReportCheckActivity.class);
        //用Bundle携带数据
        Bundle bundle=new Bundle();
        //传递name参数为tinyphp
        bundle.putInt("position",temp);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
