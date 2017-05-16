package com.zll.xunyiwenyao.test;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.zll.xunyiwenyao.util.HttpHelper;
import com.zll.xunyiwenyao.util.JsonHelper;
import com.zll.xunyiwenyao.webitem.ResponseItem;

/**
 * 
 * http://222.29.100.155/b2b2c/api/mobile/doctor/getAllDoctor.do
 * 
 * @author rxz
 *
 */
public class RealWebServiceTest {

	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		
		String url = "http://222.29.100.155/b2b2c/api/mobile/doctor/getAllDoctor.do";
		Gson gson = new Gson();
		
		//发送 GET 请求
        String s = HttpHelper.sendGet(url, "");
        
        Map m = JsonHelper.toMap(s);
        ResponseItem responditem = new  ResponseItem();
        responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
        System.out.println(JsonHelper.toJSON(responditem));
        System.out.println("___________");
        
        
        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("data");
        System.out.println(ja.length());
        
        //发送 POST 请求
        //String sr = HttpHelper.sendPost(url, "");
        //System.out.println(sr);
	}

}
