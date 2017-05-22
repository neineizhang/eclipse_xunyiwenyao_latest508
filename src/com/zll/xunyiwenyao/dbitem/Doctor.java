package com.zll.xunyiwenyao.dbitem;

/**
 * Created by rxz on 2017/3/21.
 */

public class Doctor {
    private int id;//ϵͳ����
    private String user_name;//�û�����ע��ʱ���Ψһ����
    private String real_name;//��ʵ����
    private int sex;//�Ա�Enumд��utils�ļ���
    private int type;//���ͣ�Enumд��utils�ļ���
    private String passwd;//����

    private String photo;//ҽ����Ƭ·��
    private String license;//ִҵ֤��·��

    private String title;//ְλ
    private String hospital;//ҽԺ
    private String department;//����
    private String goodat;//ɾ��
    private String profile;//��������
    private String register_time;//ע��ʱ��
    private int  profState;//(0-新注册,1-通过审核,2-未通过审核)

    public int getProfState() {
        return profState;
    }

    public void setProfState(int profState) {
        this.profState = profState;
    }

    public Doctor() {
    }

    public Doctor(int id, String name, int type, String hospital, String username, String passwd) {
        super();
        this.id = id;
        this.real_name = name;
        this.type = type;
        this.hospital = hospital;
        this.user_name = username;
        this.passwd = passwd;
    }
    public Doctor(int id, String name, int type, String hospital, String username, String passwd,
                  int sex, String title, String department, String goodat, String profile) {
        super();
        this.id = id;
        this.real_name = name;
        this.type = type;
        this.hospital = hospital;
        this.user_name = username;
        this.passwd = passwd;
        this.sex=sex;
        this.title=title;
        this.department=department;
        this.goodat=goodat;
        this.profile=profile;
    }
    public Doctor(int id, String name, int type, String hospital, String username, String passwd,
                  int sex, String title, String department, String goodat, String profile,int profState
) {
        super();
        this.id = id;
        this.real_name = name;
        this.type = type;
        this.hospital = hospital;
        this.user_name = username;
        this.passwd = passwd;
        this.sex=sex;
        this.title=title;
        this.department=department;
        this.goodat=goodat;
        this.profile=profile;
        this.profState=profState;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return user_name;
    }

    public void setUsername(String username) {
        this.user_name = username;
    }

    public String getRealName() {
        return real_name;
    }

    public void setRealName(String name) {
        this.real_name = name;
    }

    public int getSex() {return sex;}

    public void setSex(int s) {this.sex=s;}

    public int getType() {return type;}

    public void setType(int t) {this.type=t;}

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getTitle() {return title;}

    public void setTitle(String t){this.title=t;}

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment(){return department;}

    public void setDepartment(String d){this.department=d;}

    public String getGoodat(){return goodat;}

    public void setGoodat(String g){this.goodat=g;}

    public String getProfile(){return profile;}

    public void setProfile (String p) {this.profile=p;}


}
