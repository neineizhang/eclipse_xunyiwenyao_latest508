package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Inspection;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.TopBarView;
import com.zll.xunyiwenyao.webservice.InspectionWebService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kejund on 17/4/6.
 */

public class InspectionCheckActivity extends Activity implements TopBarView.onTitleBarClickListener {
    private TopBarView topbar;
    private Button btn_update, btn_ok;
    private EditText i_name, p_name, p_age, history, location, date, comment, doctor_name,type;
//    private RadioGroup sex_rg;
//    private RadioButton sex_r1, sex_r2;
    private Button date_choose;
    private Calendar calendar;
    private DatePickerDialog datePD;
    private List<Inspection> inspectionList = new ArrayList<Inspection>();
    int sex = Utils.SEX.MAN.ordinal();
    int state = Utils.INSPECTION_STATUS.UNCOMMITED.ordinal();
    int currDoctorID;
    private Button btn_delete, btn_commit;
    private EditText sex_text;



    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.inspection_check);
        topbar = (TopBarView) findViewById(R.id.topbar);
        topbar.setClickListener(this);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        final int temp = bundle.getInt("position");

        inspectionList = InspectionWebService.getAllInspection();

        btn_update = (Button)findViewById(R.id.button_update);
        btn_ok = (Button)findViewById(R.id.button_ok);
        btn_delete = (Button)findViewById(R.id.button_delete);
        btn_commit = (Button)findViewById(R.id.button_commit);

        date_choose = (Button)findViewById(R.id.date_choose);

        i_name = (EditText)findViewById(R.id.editText1);
        p_name = (EditText)findViewById(R.id.name_text);
        p_age = (EditText)findViewById(R.id.age_text);
        history = (EditText)findViewById(R.id.history_text);
        location = (EditText)findViewById(R.id.inspection_location_text);
        date = (EditText)findViewById(R.id.date_text);
        comment = (EditText)findViewById(R.id.comment_text);
        doctor_name = (EditText)findViewById(R.id.doctor_text);
        type = (EditText)findViewById(R.id.type_text);

        sex_text = (EditText)findViewById(R.id.sex_text);

        i_name.setText(inspectionList.get(temp).getInspectionName().toString());
        p_name.setText(inspectionList.get(temp).getPatientName().toString());

// !!!!       p_age.setText(inspectionList.get(temp).getPatientAge().toString());
        p_age.setText(String.valueOf(inspectionList.get(temp).getPatientAge()));

        history.setText(inspectionList.get(temp).getPatientHistory().toString());
        location.setText(inspectionList.get(temp).getInspectionLoaction().toString());
        date.setText(inspectionList.get(temp).getInspectionDate().toString());
        comment.setText(inspectionList.get(temp).getInspectionComment().toString());
        doctor_name.setText(inspectionList.get(temp).getDoctorName());
        currDoctorID = inspectionList.get(temp).getDoctorID();
        type.setText(inspectionList.get(temp).getType().toString());

        sex_text.setText(inspectionList.get(temp).getPatientSex()==0?"男":"女");



//        sex_rg = (RadioGroup)findViewById(R.id.sex_rg);
//        sex_r1 = (RadioButton)findViewById(R.id.sex_rb1);
//        sex_r2 = (RadioButton)findViewById(R.id.sex_rb2);
//        sex = inspectionList.get(temp).getPatientSex();
//        if (sex == Utils.SEX.MAN.ordinal()) {
//            sex_r1.setChecked(true);
//        } else {
//            sex_r2.setChecked(true);
//        }

        state = inspectionList.get(temp).getInspectionState();

        //修改日期
        calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);
        datePD = new DatePickerDialog(InspectionCheckActivity.this, listener, year, month, day);
        //日期选择按钮
        date_choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datePD.show();
            }
        });

        //点击修改button
        //更新按钮
        btn_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(state== Utils.INSPECTION_STATUS.UNCOMMITED.ordinal()){
                    if(currDoctorID== Utils.LOGIN_DOCTOR.getId()){
                        p_name.setEnabled(true);
//                        sex_r1.setEnabled(true);
//                        sex_r2.setEnabled(true);
                        sex_text.setEnabled(true);
                        p_age.setEnabled(true);
                        history.setEnabled(true);
                        location.setEnabled(true);
                        date.setEnabled(true);
                        comment.setEnabled(true);
                    }
                    else{
                            Toast.makeText(InspectionCheckActivity.this, "您没有权利修改此检查单内容！", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(InspectionCheckActivity.this, "已提交的检查单不能修改！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //点击确定button
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //获得修改后的性别
                if(state== Utils.INSPECTION_STATUS.UNCOMMITED.ordinal()){
                    if(currDoctorID== Utils.LOGIN_DOCTOR.getId()){
//                        sex_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//                            @Override
//                            public void onCheckedChanged(RadioGroup arg0, int arg1) {
//                                // TODO Auto-generated method stub
//                                if (arg1 == sex_r1.getId()) {
//                                    sex = Utils.SEX.MAN.ordinal();
//                                } else {
//                                    sex = Utils.SEX.WOMAN.ordinal();
//                                }
//                            }
//                        });


                        Inspection newins = new Inspection();
                        newins = inspectionList.get(temp);

                        newins.setInspectionName(i_name.getText().toString());
                        newins.setType(type.getText().toString());

                        newins.setPatientName(p_name.getText().toString());

//                        newins.setPatientSex(sex);
                        newins.setPatientSex(sex_text.getText().toString()=="男"?0:1);
//                        newins.setPatientAge(p_age.getText().toString());
                        if(p_age.getText().toString().equals(""))
                            newins.setPatientAge(0);
                        else
                            newins.setPatientAge(Integer.parseInt(p_age.getText().toString()));

                        newins.setPatientId(InspectionWebService.getUserIDByName(p_name.getText().toString()));
                        newins.setPatientHistory(history.getText().toString());
                        newins.setInspectionLocation(location.getText().toString());
                        newins.setInspectionDate(date.getText().toString());
                        newins.setInspectionComment(comment.getText().toString());

                        newins.setInspectionState(Utils.INSPECTION_STATUS.UNCOMMITED.ordinal());
                        newins.setDoctorID(currDoctorID);

                        InspectionWebService.updateInspection(newins);
//                        InspectionWebService.updateInspection(newins);
                        Toast.makeText(InspectionCheckActivity.this, "检查单修改成功！", Toast.LENGTH_SHORT).show();

                        finish();
                    }else{
                        Toast.makeText(InspectionCheckActivity.this, "您没有权利保存此检查单！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //删除按钮
        btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(state== Utils.INSPECTION_STATUS.UNCOMMITED.ordinal()){
                    if(currDoctorID== Utils.LOGIN_DOCTOR.getId()){


//                        sex_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//                            @Override
//                            public void onCheckedChanged(RadioGroup arg0, int arg1) {
//                                // TODO Auto-generated method stub
//                                if (arg1 == sex_r1.getId()) {
//                                    sex = Utils.SEX.MAN.ordinal();
//                                } else {
//                                    sex = Utils.SEX.WOMAN.ordinal();
//                                }
//                            }
//                        });
                        Inspection newins = new Inspection();
                        newins = inspectionList.get(temp);

                        newins.setInspectionName(i_name.getText().toString());
                        newins.setType(type.getText().toString());

                        newins.setPatientName(p_name.getText().toString());

//                        newins.setPatientSex(sex);
                        newins.setPatientSex(sex_text.getText().toString()=="男"?0:1);
//                        newins.setPatientAge(p_age.getText().toString());
                        if(p_age.getText().toString().equals(""))
                            newins.setPatientAge(0);
                        else
                            newins.setPatientAge(Integer.parseInt(p_age.getText().toString()));

                        newins.setPatientId(InspectionWebService.getUserIDByName(p_name.getText().toString()));
                        newins.setPatientHistory(history.getText().toString());
                        newins.setInspectionLocation(location.getText().toString());
                        newins.setInspectionDate(date.getText().toString());
                        newins.setInspectionComment(comment.getText().toString());

                        newins.setInspectionState(Utils.INSPECTION_STATUS.UNCOMMITED.ordinal());
                        newins.setDoctorID(currDoctorID);
                        InspectionWebService.deleteInspection(newins);
//                        InspectionWebService.deleteInspection(newins);
                        Toast.makeText(InspectionCheckActivity.this, "检查单删除成功！", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                    else{
                        Toast.makeText(InspectionCheckActivity.this, "您没有权利删除此检查单！", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(InspectionCheckActivity.this, "此检查单状态为已提交，不能删除！", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //提交按钮
        btn_commit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(state== Utils.INSPECTION_STATUS.UNCOMMITED.ordinal()){
                    if(currDoctorID== Utils.LOGIN_DOCTOR.getId()){
                       Inspection updateins = new Inspection();
                        updateins=inspectionList.get(temp);
                        updateins.setInspectionName(i_name.getText().toString());
                        updateins.setType(type.getText().toString());
                        updateins.setPatientName(p_name.getText().toString());
                        updateins.setPatientSex(sex_text.getText().toString()=="男"?0:1);
//                        newins.setPatientAge(p_age.getText().toString());
                        if(p_age.getText().toString().equals(""))
                            updateins.setPatientAge(0);
                        else
                            updateins.setPatientAge(Integer.parseInt(p_age.getText().toString()));

                        updateins.setPatientId(InspectionWebService.getUserIDByName(p_name.getText().toString()));
                        updateins.setPatientHistory(history.getText().toString());
                        updateins.setInspectionLocation(location.getText().toString());
                        updateins.setInspectionDate(date.getText().toString());
                        updateins.setInspectionComment(comment.getText().toString());

                        updateins.setInspectionState(Utils.INSPECTION_STATUS.COMMITED.ordinal());
                        InspectionWebService.updateInspection(updateins);
//                        InspectionWebService.updateInspectionByPosition(temp,updateins);
                        Toast.makeText(InspectionCheckActivity.this, "检查单提交成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(InspectionCheckActivity.this, "您没有权利提交此检查单！", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(InspectionCheckActivity.this, "此检查单状态为已提交，不能重复提交！", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    @Override
    public void onBackClick() {
        InspectionCheckActivity.this.finish();
    }
    @Override
    public void onRightClick() {
//        Toast.makeText(InspectionCheckActivity.this, "你点击了右侧按钮", Toast.LENGTH_SHORT).show();

    }
}
