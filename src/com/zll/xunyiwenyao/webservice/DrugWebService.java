package com.zll.xunyiwenyao.webservice;

import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.util.HttpHelper;
import com.zll.xunyiwenyao.util.JsonHelper;
import com.zll.xunyiwenyao.webitem.ResponseItem;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rxz on 2017/3/21.
 */

public class DrugWebService {

    private static List<Drug> druglist;

    public static void initDB() throws JSONException{
		String url = "http://222.29.100.155/b2b2c/api/mobile/drug/getDrug.do";
		
		String s = HttpHelper.sendGet(url, "");
        Map m = JsonHelper.toMap(s);
        ResponseItem responditem = new  ResponseItem();
        responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
        System.out.println(JsonHelper.toJSON(responditem));
        System.out.println("___________");
        
        
        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("data");
        System.out.println(ja.length());

        ////////

        Drug drug = null;
        druglist = new ArrayList<Drug>();
        for(int i = 0; i < ja.length(); i++){
        	JSONObject jsonobj = (JSONObject) ja.get(i);
        	drug = new Drug(jsonobj.getInt("drug_id"), 
        			jsonobj.getString("appro_name"), 
        			jsonobj.getString("py_name"),
        			jsonobj.getString("dosage_form"), 
        			jsonobj.getString("specification"));
        	druglist.add(drug);
        	System.out.println("success add:"+JsonHelper.toJSON(drug));
        }
    }
    
    public static void main(String[] args) {
		try {
			DrugWebService.initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public static List<Drug> getAllDrug() {

        return druglist;

    }


    public static Drug getDrugByName(String name){
    	for(Drug item : druglist){
    		if(item.getName().equals(name)){
    			return item;
    		}
    	}
    	return null;
    }
    
    public static Drug getDrugByID(int id){
    	for(Drug item : druglist){
    		if(item.getId() == id){
    			return item;
    		}
    	}
    	return null;
    }
}
