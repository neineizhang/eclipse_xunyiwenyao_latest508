package com.zll.xunyiwenyao.dbitem;

/**
 * Created by rxz on 2017/3/21.
 */

public class Utils {

    public static enum SEX {
        MAN, WOMAN
    };

    public static enum DOCTOR_TYPE{
        DOCTOR,ACCESSOR
    };

    public static enum DEPARTMENT {
        EMPTY, NEIKE, WAIKE, FUCHANKE, ERKE, WUGUANKE, PIFUKE
    }; ///// 直接改这里

    public static String[] DEPARTMENT_ARRAY = new String[]{ "内科", "外科", "妇产科", "儿科", "眼耳鼻咽喉科 ","皮肤病与性病","精神卫生","职业病","医学影像和放射治疗","医学检验、病理","全科医学","急救医学","康复医学","预防保健","特种医学与军事医学","计划生育技术服务","其他专业"};

//    public static enum DEPARTMENT {
//        NEIKE, WAIKE, ERKE, FUCHANKE, WUGUANKE, PIFUKE
//    }; 
//
//    public static String[] DEPARTMENT_ARRAY = new String[]{"NEI KE", "WAI KE", "ER KE", "FU CHAN KE", "WU GUANKE", "PI FU KE"};
    
    public static enum STATUS{
    	SAVED,COMMITED,APPROVED,REFUSED
    };

    public static enum INSPECTION_STATUS{
        UNCOMMITED, COMMITED
    };

    public static Doctor LOGIN_DOCTOR;

    public static enum REPORT_LEVEL{
        NEW, SEVERE, GENERAL
    };

    public static String PATIENT_NAME ="";
    public static boolean isFromChat = false;


}
