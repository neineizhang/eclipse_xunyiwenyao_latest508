package com.zll.xunyiwenyao.webservice;

import com.zll.xunyiwenyao.dbitem.Report;
import com.zll.xunyiwenyao.util.HttpHelper;
import com.zll.xunyiwenyao.util.JsonHelper;
import com.zll.xunyiwenyao.webitem.ResponseItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by kejund on 17/4/7.
 */

public class ReportWebService {
    private static List<Report> reportList;

//    static{
//        reportList=new ArrayList<Report>();
//    }

    public static void initDB() throws JSONException {
        String url = "http://222.29.100.155/b2b2c/api/mobile/drug/getDrugReaction.do";

        String s = HttpHelper.sendGet(url, "");
        Map m = JsonHelper.toMap(s);
        ResponseItem responditem = new ResponseItem();
        responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
        System.out.println(JsonHelper.toJSON(responditem));
        System.out.println("___________");


        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("data");
        System.out.println(ja.length());



        Report report;
        reportList=new ArrayList<Report>();
        for(int i = 0; i < ja.length(); i++){
            JSONObject jsonobj = (JSONObject) ja.get(i);

            //level从int转换为string
            String levelString = "新的";//默认为新的，level=0；
            if(jsonobj.getInt("level")==1)
                levelString = "严重";
            else if(jsonobj.getInt("level")==2)
                levelString = "一般";

            //将drugIdList_json，从String转换为List<Int>,[1,2,3]
            String idList = jsonobj.getString("drugIdList_json");
            idList = idList.substring(1,idList.length()-1);//去掉前后方括号
            String[] arrayId = idList.split(", ");
            List<Integer> drugIdList = new ArrayList<Integer>();
            for(String str:arrayId)
                drugIdList.add(Integer.parseInt(str));
            System.out.println("test:"+drugIdList);

            //将drugNameList_json解析为List<String>
            String nameList = jsonobj.getString("drugNameList_json");
            nameList=nameList.substring(1,nameList.length()-1);
            String[] arrayName = nameList.split(", ");
            List<String> drugNameList = new ArrayList<String>();
            for(String str:arrayName)
                drugNameList.add(str);
            System.out.println("test:"+drugNameList);

            System.out.println("test:"+jsonobj.getLong("event_date"));

 /*
        * int id, String name, String feature, List<Integer> drugIDList,
                  List<String> drugNameList, String lever, long eventDateLong,
                  String event_date, long reportDateLong, String report_date, int doctor_id,
                  String doctor_name, String comment)*/
            report = new Report(jsonobj.getInt("drugReaction_id"),jsonobj.getString("report_name"),jsonobj.getString("feature"),
                    drugIdList, drugNameList, levelString,
                    jsonobj.getLong("event_date"),
                    jsonobj.getString("event_date_text"),
                    jsonobj.getLong("report_date"),
                    jsonobj.getString("report_date_text"),
                    jsonobj.getInt("doctor_id"),jsonobj.getString("doctor_name"),jsonobj.getString("comment"));
            reportList.add(report);
            System.out.println("success add:"+ JsonHelper.toJSON(report));
        }

    }


    public static void addReport(Report item){


       try {
            String url = "http://222.29.100.155/b2b2c/api/mobile/drug/addDrugReaction.do";
            String jsString = getJsonString(item);
            System.out.println(url+"?"+jsString);
            String s = HttpHelper.sendPost(url,jsString);
            System.out.println(s);
            //更新本地list
           initDB();
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static List<Report> getAllReport(){

        try {
            initDB();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reportList;
    }

    /*
        * int id, String name, String feature, List<Integer> drugIDList,
                  List<String> drugNameList, String lever, long eventDateLong,
                  String event_date, long reportDateLong, String report_date, int doctor_id,
                  String doctor_name, String comment)*/
    public static String getJsonString(Report item){
        //level转换
        int report_level=0;//默认为0，表示"新的"
        if(item.getLever().equals("严重"))
            report_level = 1;
        else if(item.getLever().equals("一般"))
            report_level=2;


        String jsonString = "drugReaction_id="+item.getId()+"&report_name="+item.getName()
                +"&feature="+item.getFeature()
                +"&drugIdList_json="+item.getDrugIDList()+"&drugNameList_json="+item.getDrugNameList()
                +"&level="+report_level
                +"&event_date_text="+item.getEvent_Date()+"&event_date="+item.getEventDateLong()
                +"&report_date_text="+item.getReportDate()+"&report_date="+item.getReportDateLong()
                +"&doctor_id="+item.getDoctorID()+"&doctor_name="+item.getDoctorName()
                +"&comment="+item.getComment();
        return jsonString;
    }



    }
