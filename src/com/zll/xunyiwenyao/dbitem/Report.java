package com.zll.xunyiwenyao.dbitem;

import java.util.List;

/**
 * Created by kejund on 17/4/6.
 */

public class Report {
    private int id;     //系统自增的唯一id
    private String name;//报告名称，必填项
    private String feature;//临床表现，必填项
    private List<Integer> drugIDList;//涉及的药品id，必填项
    private List<String> drugNameList;//涉及的药品名称，必填项
    private String lever;//严重程度
    private long event_date_long;
    private String event_date;//事件发生的时间
    private long report_date_long;
    private String report_date;//提交的时间
    private Doctor doctor;//提交的医生，必填项
    private int doctor_id;
    private String doctor_name;
    private String comment;//备注

    public Report(int id, String name, String feature, List<Integer> drugIDList,
                  List<String> drugNameList, String lever, long eventDateLong,
                  String event_date, long reportDateLong, String report_date, int doctor_id,
                  String doctor_name, String comment){
        this.id=id;
        this.name=name;
        this.feature=feature;
        this.drugIDList=drugIDList;
        this.drugNameList=drugNameList;
        this.lever=lever;
        this.event_date_long=eventDateLong;
        this.event_date=event_date;
        this.report_date_long=reportDateLong;
        this.report_date=report_date;
        this.doctor_id=doctor_id;
        this.doctor_name=doctor_name;
        this.comment=comment;
    }

    public Report(){}

    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    public String getName(){
        return this.name;
    }
    public void setName(String name){this.name=name;}

    public String getFeature(){return this.feature;}
    public void setFeature(String feature){this.feature=feature;}


    public String getLever(){return this.lever;}
    public void setLever(String lever){this.lever=lever;}

    public long getEventDateLong(){return event_date_long;}
    public void setEventDateLong(long date){event_date_long=date;}

    public String getEvent_Date(){return event_date;}
    public void setEventDate(String date){this.event_date=date;}

    public long getReportDateLong(){return report_date_long;}
    public void setReportDateLong(long date){this.report_date_long=date;}

    public String getReportDate(){return report_date;}
    public void setReportDate(String date){this.report_date=date;}

    public Doctor getDoctor(){return doctor;}
    public void setDoctor(Doctor d){this.doctor=d;}

    public int getDoctorID(){return doctor_id;}
    public void setDoctorID(int id){this.doctor_id=id;}

    public String getDoctorName(){return doctor_name;}
    public void setDoctorName(String name){this.doctor_name=name;}

    public String getComment(){return comment;}
    public void setComment(String c){this.comment=c;}

    public List<Integer> getDrugIDList(){return drugIDList;}
    public void setDrugIDList(List<Integer> drug_id_list){this.drugIDList=drug_id_list;}

    public List<String> getDrugNameList(){return  drugNameList;}
    public void setDrugNameList(List<String> drug_name_list){this.drugNameList=drug_name_list;}
}
