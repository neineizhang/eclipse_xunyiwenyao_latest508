package com.zll.xunyiwenyao.dbitem;

/**
 * Created by kejund on 17/3/30.
 */

public class Review {
    private int id;     //系统自增的唯一id
    private String name;//名称，必填项
    private int drug_id;//评价的药品id
    private String drug_name;// 评价的药品name，必填项
    private String content;//评价的内容，必填项
    private String date;//提交的时间
    private int doctor_id;
    private String doctor_name;
    private String comment;//备注

    public Review(){}

    public Review(int id, String name, int drug_id, String drug_name, String content, String date, int doctor_id, String doctor_name, String comment){
        this.id=id;
        this.name=name;
        this.drug_id =drug_id;
        this.drug_name=drug_name;
        this.content=content;
        this.date=date;
        this.doctor_id=doctor_id;
        this.doctor_name=doctor_name;
        this.comment=comment;
    }

    public int getReviewID(){return id;};
    public void setReviewID(int id){this.id=id;}

    public String getName(){
        return this.name;
    }
    public void setName(String name){this.name=name;}

    public String getContent(){return this.content;}
    public void setContent(String content){this.content=content;}

    public int getDrugID(){return drug_id;}
    public void setDrugID(int id){this.drug_id=id;}

    public String getDrugName(){return  drug_name;}
    public void setDrugName(String dname){this.drug_name=dname;}

    public String getDate(){return date;}
    public void setDate(String date){this.date=date;}

    public String getComment(){return comment;}
    public void setComment(String c){this.comment=c;}

    public int getDoctorID(){return doctor_id;}
    public void setDoctorID(int id){this.doctor_id=id;}

    public String getDoctorName(){return this.doctor_name;}
    public void setDoctorName(String name){this.doctor_name=name;}
}
