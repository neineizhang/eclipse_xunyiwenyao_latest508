package com.zll.xunyiwenyao.webservice;

import com.zll.xunyiwenyao.dbitem.Inspection;
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
 * Created by kejund on 17/4/20.
 */

public class InspectionWebService {
    private static List<Inspection> inspectionList;
    private static List<String> typeList;


    public static void initDB() throws JSONException {
        String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/getInspection.do?";

        String s = HttpHelper.sendGet(url, "");
        Map m = JsonHelper.toMap(s);
        ResponseItem responditem = new ResponseItem();
        responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
        System.out.println(JsonHelper.toJSON(responditem));
        System.out.println("___________");


        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("data");
        System.out.println(ja.length());

        Inspection inspection;
        inspectionList = new ArrayList<Inspection>();
        for(int i = 0; i < ja.length(); i++){
            JSONObject jsonobj = (JSONObject) ja.get(i);
            int pid=0;
            if(!jsonobj.get("user_id").toString().equals("null"))
                pid=jsonobj.getInt("user_id");
            inspection = new Inspection(jsonobj.getInt("inspection_id"), jsonobj.getString("inspection_name"),
                    jsonobj.getString("inspection_type"), jsonobj.getString("pname"),
                    jsonobj.getInt("psex"),  jsonobj.getInt("page"), pid,
                    jsonobj.getString("history"), jsonobj.getString("location"),
                    jsonobj.getLong("create_date"),
                    jsonobj.getString("create_date_text"), jsonobj.getString("comment"),
                            jsonobj.getInt("status"), jsonobj.getInt("doctor_id"),
                    jsonobj.getString("doctor_name"));
            inspectionList.add(inspection);
            System.out.println("success add:"+ JsonHelper.toJSON(inspection));
        }


        //获取检查单的所有类别
        String listURL = "http://222.29.100.155/b2b2c/api/mobile/recipe/listAllInspectionType.do?";

        typeList = new ArrayList<String>();
        s = HttpHelper.sendGet(listURL, "");
        m = JsonHelper.toMap(s);
        responditem = new ResponseItem();
        responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
        System.out.println(JsonHelper.toJSON(responditem));
        System.out.println("___________");


        jo = new JSONObject(s);
        ja = jo.getJSONArray("data");
        System.out.println(ja.length());

        for(int i = 0; i < ja.length(); i++){
            JSONObject jsonobj = (JSONObject) ja.get(i);
            typeList.add(jsonobj.getString("type_name"));
//            arrs_type[i]=jsonobj.getString("type_name");
            System.out.println("success add:"+jsonobj.getString("type_name"));
        }


    }
    public static void main(String[] args) {
        try {
            InspectionWebService.initDB();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static List<Inspection> getAllInspection() {
        try {
            initDB();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inspectionList;
    };

    public static void addInspection(Inspection item){

        //远程数据库添加
        try {
            String jsString = getJsonString(item);
            String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/addInspection.do";
            System.out.println(url+"?"+jsString);
//            jsString = URLEncoder.encode(jsString,"UTF-8");
//            String s = HttpHelper.sendGet(url, jsString);
            String s = HttpHelper.sendPost(url,jsString);
            System.out.println(s);

            //更新本地list
            initDB();

/*            String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/addRecipeTemplate.do";
            String jsString = "template_name=test&creator_id=1&department=1&details_json" +
                    "=[{\"recipedetail_id\":1234,\"drug_id\":1,\"drug_name\":\"六味地黄丸\",\"count\":9," +
                    "\"how_to_use\":\"口服\",\"per_count\":2}]";
            System.out.println(url+"?"+jsString);
//            jsString = URLEncoder.encode(jsString,"UTF-8");
//            String s = HttpHelper.sendGet(url, jsString);
            String s = HttpHelper.sendPost(url,jsString);
            System.out.println(s);*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }




    public static void updateInspection(Inspection item){

        try {
            //远程更新
            String jsString = getJsonString(item);
            String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/updateInspection.do";
            System.out.println(url+"?"+jsString);
            String s = HttpHelper.sendPost(url, jsString);
            System.out.println(s);

            //更新本地list
            initDB();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void deleteInspection(Inspection item){
        inspectionList.remove(item);
        try {
            //远程删除
            String jsString = getJsonString(item);
            String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/deleteInspection.do";
            System.out.println(url+"?"+jsString);
            String s = HttpHelper.sendPost(url, jsString);
            System.out.println(s);

            //更新本地list
            initDB();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String[] listAllInspectionType(){
        String[] arrs_type =  new String[typeList.size()];
        typeList.toArray(arrs_type);
        return arrs_type;
    }
    public static int getUserIDByName(String username){
        int user_id=0;
        try {
            String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/getUser.do";
            String s = HttpHelper.sendGet(url, "uname="+username);
            Map m = JsonHelper.toMap(s);
            ResponseItem responditem = new ResponseItem();
            responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
            JSONObject jo = new JSONObject(s);
            JSONArray ja = jo.getJSONArray("data");
            System.out.println("test:---getUser:"+ja.toString());
            if(ja.length()!=0){//如果存在该用户
                JSONObject jsonobj = (JSONObject) ja.get(0);
                user_id=jsonobj.getInt("member_id");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user_id;
    }

    /*
    * jsonobj.getInt("inspection_id"), jsonobj.getString("inspection_name"),
                    jsonobj.getString("inspection_type"), jsonobj.getString("pname"),
                    jsonobj.getInt("psex"),  jsonobj.getInt("page"),
                    jsonobj.getString("history"), jsonobj.getString("location"),
                    jsonobj.getString("create_date_text"), jsonobj.getString("comment"),
                            jsonobj.getInt("status"), jsonobj.getInt("doctor_id"),
                    jsonobj.getString("doctor_name"
    * */

    public static JSONObject getJsonData(Inspection item){
        JSONObject js=new JSONObject();
        try {
            js.put("inspection_id",item.getInspectionID());
            js.put("inspection_name",item.getInspectionName());
            js.put("inspection_type",item.getType());
            js.put("pname",item.getPatientName());
            js.put("psex",item.getPatientSex());
            js.put("page",item.getPatientAge());
            js.put("history",item.getPatientHistory());
            js.put("location",item.getInspectionLoaction());
            js.put("create_date_text",item.getInspectionDate());
            js.put("comment",item.getInspectionComment());
            js.put("status",item.getInspectionState());
            js.put("doctor_id",item.getDoctorID());
            js.put("doctor_name",item.getDoctorName());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return js;
    }


    public static String getJsonString(Inspection item){
        String jsonString = "inspection_id="+item.getInspectionID()+"&inspection_name="+item.getInspectionName()
                +"&inspection_type="+item.getType()+"&pname="+item.getPatientName()
                +"&psex="+item.getPatientSex()+"&page="+item.getPatientAge()
                +"&user_id="+item.getPatientId()
                +"&history="+item.getPatientHistory()
                +"&location="+item.getInspectionLoaction()+"&create_date="+item.getDateLong()
                +"&create_date_text="+item.getInspectionDate()
                +"&comment="+item.getInspectionComment()+"&status="+item.getInspectionState()
                +"&doctor_id="+item.getDoctorID()+"&doctor_name="+item.getDoctorName();
        return jsonString;
    }

}
