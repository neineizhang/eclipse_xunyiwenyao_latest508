package com.zll.xunyiwenyao.webservice;

import com.zll.xunyiwenyao.dbitem.Doctor;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.PrescriptionTemplate;
import com.zll.xunyiwenyao.dbitem.Prescription_drugmap;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.HttpHelper;
import com.zll.xunyiwenyao.util.JsonHelper;
import com.zll.xunyiwenyao.webitem.ResponseItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rxz on 2017/3/22.
 *
 * change : 
 * addPrescriptionTemplate
 * updatePrescriptionTemplate
 * deletePrescriptionTemplate
 *
 */

public class PrescriptionTemplateWebService {

	public static List<PrescriptionTemplate> templatelt = new ArrayList<PrescriptionTemplate>();

	public static void initDB() throws JSONException{
		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/getAllRecipeTemplate.do" ;

		String s = HttpHelper.sendGet(url, "");
		Map m = JsonHelper.toMap(s);
		ResponseItem responditem = new ResponseItem();
		responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
		System.out.println(JsonHelper.toJSON(responditem));
		System.out.println("___________");


		JSONObject jo = new JSONObject(s);
		JSONArray ja = jo.getJSONArray("data");
		System.out.println(ja.length());

		////////
		PrescriptionTemplate prescriptiontemplate = null;
		templatelt = new ArrayList<PrescriptionTemplate>();
		for(int i = 0; i < ja.length(); i++){
			JSONObject jsonobj = (JSONObject) ja.get(i);

			int id = jsonobj.getInt("recipeTemplate_id");
			String name = jsonobj.getString("template_name");
			int department = jsonobj.getInt("department");
			Doctor doctor = DoctorWebService.getDoctorByID(jsonobj.getInt("creator_id"));
			Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>();
			JSONArray jsonarray = jsonobj.getJSONArray("detailList");
			List<Prescription_drugmap> drugmaps=new ArrayList<Prescription_drugmap>();
			for(int j = 0; j < jsonarray.length(); j++){
				JSONObject tmpobj = (JSONObject) jsonarray.get(j);
				System.out.println(tmpobj.toString());
				Drug tmpdrug = DrugWebService.getDrugByID(tmpobj.getInt("drug_id"));
				int cnt = tmpobj.getInt("amount");
				String description=tmpobj.getString("how_to_use");
				drugmaps.add(new Prescription_drugmap(tmpdrug, cnt, description));
				drugmap.put(tmpdrug, cnt);
			}

			prescriptiontemplate = new PrescriptionTemplate(id, name, department,drugmaps, doctor);
			templatelt.add(prescriptiontemplate);
		}
	}
	public static void main(String[] args) {
		try {
			DoctorWebService.initDB();
			DrugWebService.initDB();
			//PrescriptionWebService.initDB();
			PrescriptionTemplateWebService.initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<PrescriptionTemplate> getAllTemplate(){
		return templatelt;
	}


	public static List<String> getAllTemplateName(){
		List<String> namelist = new ArrayList<String>();
		for(PrescriptionTemplate item : templatelt){
			namelist.add(item.getName());
		}
		return namelist;
	}


	public static void addPrescriptionTemplate(PrescriptionTemplate item){
		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/addRecipeTemplate.do";
		StringBuilder itemStr = new StringBuilder();
		itemStr.append("template_name="+item.getName());
		itemStr.append("&creator_id="+ Utils.LOGIN_DOCTOR.getId());
		itemStr.append("&department="+item.getDepartment());

		JSONArray jsonArray = new JSONArray();
		for(Prescription_drugmap drugmap : item.getDruglist()){
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("amount", drugmap.getCount());
				jsonObject.put("how_to_use", drugmap.getDescription());
				jsonObject.put("drug_id", drugmap.getDrug().getId());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonArray.put(jsonObject);
		}
		itemStr.append("&details_json="+jsonArray.toString());
		System.out.println(itemStr.toString());
		String result = HttpHelper.sendPost(url, itemStr.toString());
		System.out.println(result);

		///
		try {
			initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updatePrescriptionTemplate(PrescriptionTemplate item){
//    	PrescriptionTemplate presciption = getPrescriptionTemplateByName(item.getName());
//    	int index = templatelt.indexOf(presciption);
//    	//presciption.setDrugmap(item.getDrugmap());
//    	presciption.setDruglist(item.getDruglist());
//    	templatelt.set(index, presciption);

		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/updateRecipeTemplate.do";
		StringBuilder itemStr = new StringBuilder();
		itemStr.append("recipeTemplate_id="+item.getId());
		itemStr.append("&template_name="+item.getName());
		itemStr.append("&creator_id="+ Utils.LOGIN_DOCTOR.getId());
		itemStr.append("&department="+item.getDepartment());

		JSONArray jsonArray = new JSONArray();
		for(Prescription_drugmap drugmap : item.getDruglist()){
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("amount", drugmap.getCount());
				jsonObject.put("how_to_use", drugmap.getDescription());
				jsonObject.put("drug_id", drugmap.getDrug().getId());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonArray.put(jsonObject);
		}
		itemStr.append("&details_json="+jsonArray.toString());
		System.out.println(itemStr.toString());
		String result = HttpHelper.sendPost(url, itemStr.toString());
		System.out.println(result);

		///
		try {
			initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void deletePrescriptionTemplate(PrescriptionTemplate item){

		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/deleteRecipeTemplate.do";
		StringBuilder itemStr = new StringBuilder();
		itemStr.append("recipeTemplate_id="+item.getId());
//    	itemStr.append("&template_name="+item.getName());
//    	itemStr.append("&creator_id="+Utils.LOGIN_DOCTOR.getId());
//    	itemStr.append("&department="+item.getDepartment());
//
//    	JSONArray jsonArray = new JSONArray();
//    	for(Prescription_drugmap drugmap : item.getDruglist()){
//    		JSONObject jsonObject = new JSONObject();
//    		try {
//				jsonObject.put("count", drugmap.getCount());
//	    		jsonObject.put("how_to_use", drugmap.getDescription());
//	    		jsonObject.put("drug_id", drugmap.getDrug().getId());
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		jsonArray.put(jsonObject);
//    	}
//    	itemStr.append("&details_json="+jsonArray.toString());
		System.out.println(itemStr.toString());
		String result = HttpHelper.sendPost(url, itemStr.toString());
		System.out.println(result);

		///
		try {
			initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//    	PrescriptionTemplate presciption = getPrescriptionTemplateByName(item.getName());
//    	int index = templatelt.indexOf(presciption);
//    	templatelt.remove(index);
	}

	public static PrescriptionTemplate getPrescriptionTemplateByName(String name){
		for(PrescriptionTemplate item : templatelt){
			if(item.getName().equals(name)){
				return item;
			}
		}
		return null;
	}

}
