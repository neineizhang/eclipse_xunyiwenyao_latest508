package com.zll.xunyiwenyao.dbitem;

/**
 * Created by kejund on 17/4/20.
 */

public class Inspection{
    private int inspection_id;     //系统自增的唯一id
    private String inspection_name;//检查单名称，必填项
    private String inspection_type;//检查单类别，枚举类型，目前包括：心电图、X光、B超，以后可以添加
    private String pname;//患者名称，必填项
    private int psex;//患者性别
    private String psex_text;//性别名称 !!!
    private int page;//患者年龄
    private int pid;//患者id
    private String history;//病史摘要
    private String location;//申请检查部位，必填项
    private long create_date;//开具日期 !!!
    private String create_date_text;//日期字串
    private String comment;//备注信息
    private int status;//状态，未提交，已提交
    private String status_text;//日期字串 !!!
    private int doctor_id;
    private String doctor_name;

    public Inspection(){};
    public Inspection(int id, String inspection_name, String inspection_type, String pname,
                      int psex, int page, int pid, String history, String location, long create_date,
                      String create_date_text, String comment, int status, int doctor_id,
                      String doctor_name){
        super();
        this.inspection_id=id;
        this.inspection_name=inspection_name;
        this.inspection_type=inspection_type;
        this.pname=pname;
        this.psex=psex;
        this.page=page;
        this.history=history;
        this.location=location;
        this.create_date=create_date;
        this.create_date_text=create_date_text;
        this.comment=comment;
        this.status=status;
        this.doctor_id=doctor_id;
        this.doctor_name=doctor_name;
    }

    public int getInspectionID(){return this.inspection_id;}
    public void setInspectionID(int id){this.inspection_id=id;}

    public String getInspectionName() {
        return inspection_name;
    }
    public void setInspectionName(String ins_name) {
        this.inspection_name = ins_name;
    }

    public String getType(){return inspection_type;}
    public void setType(String type){this.inspection_type=type;}

    public String getPatientName() {
        return pname;
    }
    public void setPatientName(String pat_name) {
        this.pname = pat_name;
    }

    public int getPatientSex() {
        return psex;
    }
    public void setPatientSex(int sex) {
        this.psex = sex;
    }

    public int getPatientAge() {
        return page;
    }
    public void setPatientAge(int age) {
        this.page = age;
    }

    public int getPatientId() {return pid;}
    public void setPatientId(int id){this.pid=id;}

    public String getPatientHistory() {
        return history;
    }
    public void setPatientHistory(String history) {
        this.history = history;
    }

    public String getInspectionLoaction() {
        return location;
    }
    public void setInspectionLocation(String location) {
        this.location = location;
    }

    public long getDateLong(){return create_date;}
    public void setDateLong(long date){this.create_date=date;}

    public String getInspectionDate() {
        return this.create_date_text;
    }
    public void setInspectionDate(String ins_date) {
        this.create_date_text = ins_date;
    }

    public String getInspectionComment() {
        return comment;
    }
    public void setInspectionComment(String comment) {
        this.comment = comment;
    }

    public int getInspectionState() {
        return status;
    }
    public void setInspectionState(int state) {
        this.status = state;
    }

    public int getDoctorID() {return this.doctor_id;}
    public void setDoctorID(int id){this.doctor_id=id;}

    public String getDoctorName() {return this.doctor_name;}
    public void setDoctorName(String name){this.doctor_name=name;}



}
