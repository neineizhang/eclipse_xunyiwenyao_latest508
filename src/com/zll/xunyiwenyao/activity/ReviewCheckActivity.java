package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Review;
import com.zll.xunyiwenyao.util.TopBarView;
import com.zll.xunyiwenyao.webservice.ReviewWebService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kejund on 17/4/11.
 */

public class ReviewCheckActivity extends Activity implements TopBarView.onTitleBarClickListener {
    private TopBarView topbar;
    private EditText review_name, drug_name, review_content, review_date, doctor_name, review_commet;
    private List<Review> reviewList = new ArrayList<Review>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.review_check);

        topbar = (TopBarView) findViewById(R.id.topbar);
        topbar.setClickListener(this);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        final int temp = bundle.getInt("position");

        review_name = (EditText)findViewById(R.id.review_name);
        drug_name = (EditText)findViewById(R.id.drug_text);
        review_content = (EditText)findViewById(R.id.review_content);
        review_date = (EditText)findViewById(R.id.date_text);
        review_commet =(EditText)findViewById(R.id.comment_text);
        doctor_name = (EditText)findViewById(R.id.doctor_text);

        reviewList= ReviewWebService.getAllReview();
        review_name.setText(reviewList.get(temp).getName().toString());
        drug_name.setText(reviewList.get(temp).getDrugName().toString());
        review_content.setText(reviewList.get(temp).getContent().toString());
        review_date.setText(reviewList.get(temp).getDate().toString());
        review_commet.setText(reviewList.get(temp).getComment().toString());
        doctor_name.setText(reviewList.get(temp).getDoctorName().toString());
    }
    @Override
    public void onBackClick() {
        ReviewCheckActivity.this.finish();
    }
    @Override
    public void onRightClick() {

//        Toast.makeText(ReviewCheckActivity.this, "点击右侧按钮", Toast.LENGTH_SHORT).show();

    }
}
