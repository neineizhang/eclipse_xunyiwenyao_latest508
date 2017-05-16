package com.zll.xunyiwenyao.webservice;

import com.zll.xunyiwenyao.dbitem.Review;
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
 * Created by kejund on 17/3/30.
 */

public class ReviewWebService {
    private static List<Review> reviewlist;

//    static{
//        reviewlist = new ArrayList<Review>();
//        Review review = null;
//
//        review = new Review("药物过期评价","阿司匹林","过期","2015-2-2", Utils.LOGIN_DOCTOR,"无");
//        reviewlist.add(review);
//
//        review = new Review("药物无效评价","Drug ","经多个用户反映，该药物无效","2016-9-22", Utils.LOGIN_DOCTOR,"无");
//        reviewlist.add(review);
//    }

    public static void initDB() throws JSONException {
        String url = "http://222.29.100.155/b2b2c/api/mobile/drug/getDrugReview.do?";
        String s = HttpHelper.sendGet(url, "");
        Map m = JsonHelper.toMap(s);
        ResponseItem responditem = new ResponseItem();
        responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);


        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("data");

        Review review = new Review();
        reviewlist = new ArrayList<Review>();
        for(int i = 0; i < ja.length(); i++){
            JSONObject jsonobj = (JSONObject) ja.get(i);
            review= new Review(jsonobj.getInt("drugReview_id"),jsonobj.getString("name"),jsonobj.getInt("drug_id"),
                    jsonobj.getString("drug_name"), jsonobj.getString("content"),
                    jsonobj.getString("date_text"), jsonobj.getInt("doctor_id"),
                    jsonobj.getString("doctor_name"), jsonobj.getString("comment"));
            reviewlist.add(review);
            System.out.println("success add:"+ JsonHelper.toJSON(review));
        }

    }

    public static void addReview(Review item){

        try {
            //远程添加
            String jsString = getJsonString(item);
            String url = "http://222.29.100.155/b2b2c/api/mobile/drug/addDrugReview.do";
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

    public static List<Review> getAllReview(){
        try {
            initDB();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reviewlist;
    }
    public static String getJsonString(Review item){
        String jsonString = "drugReview_id="+item.getReviewID()+"&name="+item.getName()
                +"&drug_id="+item.getDrugID()
                +"&drug_name="+item.getDrugName()+"&content="+item.getContent()
                +"&date_text="+item.getDate()+"&doctor_id="+item.getDoctorID()
                +"&doctor_name="+item.getDoctorName()+"&comment="+item.getComment();
        return  jsonString;
    }

}
