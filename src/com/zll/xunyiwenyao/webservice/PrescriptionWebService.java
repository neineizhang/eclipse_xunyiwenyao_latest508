package com.zll.xunyiwenyao.webservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zll.xunyiwenyao.dbitem.Doctor;
import com.zll.xunyiwenyao.dbitem.Drug;
import com.zll.xunyiwenyao.dbitem.Patient;
import com.zll.xunyiwenyao.dbitem.Prescription;
import com.zll.xunyiwenyao.dbitem.Prescription_drugmap;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.HttpHelper;
import com.zll.xunyiwenyao.util.JsonHelper;
import com.zll.xunyiwenyao.webitem.ResponseItem;

/**
 * 
 * @author rxz
 * 
 * change
 * AddPrescription
 * updatePrescription
 *
 */
public class PrescriptionWebService {
	
	private static List<Prescription> prescriptionlist;
	private static int MAX_ID = 1;

//	static {
//		prescriptionlist = new ArrayList<Prescription>();
//		
//		Doctor doctor = null;
//		Patient patient = null;
//		Prescription  prescription = null;
//		String date = "2017-04-04 10:40:40";
//		//doctor = new Doctor(1, "doctor A", Utils.DOCTOR_TYPE.DOCTOR.ordinal(), "Hospital 1");
//		doctor = DoctorWebService.getAllDoctor().get(0);
//		patient = PatientWebService.getAllPatient().get(1);
//
//		Map<Drug, Integer> drugmap = new HashMap<Drug, Integer>();
//		drugmap.put(DrugWebService.getAllDrug().get(0), 1);
//		drugmap.put(DrugWebService.getAllDrug().get(1), 3);
//		drugmap.put(DrugWebService.getAllDrug().get(2), 4);
//		drugmap.put(DrugWebService.getAllDrug().get(4), 2);
//		drugmap.put(DrugWebService.getAllDrug().get(3), 1);
//		
//		prescription= new Prescription(1,"coach", Utils.DEPARTMENT.NEIKE.ordinal(), 
//				doctor, patient, drugmap, Utils.STATUS.APPROVED.ordinal(), date,"头疼发热无过敏历史");
//		prescriptionlist.add(prescription);
//		
//		doctor = DoctorWebService.getAllDoctor().get(1);
//		patient = PatientWebService.getAllPatient().get(0);
//
//		Map<Drug, Integer> drugmap1 = new HashMap<Drug, Integer>();
//		drugmap1.put(DrugWebService.getAllDrug().get(0), 1);
//		drugmap1.put(DrugWebService.getAllDrug().get(1), 3);
//		drugmap1.put(DrugWebService.getAllDrug().get(2), 4);
//		
//		prescription= new Prescription(2,"toothache", Utils.DEPARTMENT.WAIKE.ordinal(), 
//				doctor,patient, drugmap, Utils.STATUS.COMMITED.ordinal(),date,"青霉素过敏");
//		prescriptionlist.add(prescription);
//		MAX_ID = 3;
//	}
	 public static void main(String[] args) {
			try {
				DoctorWebService.initDB();
				DrugWebService.initDB();
				PrescriptionWebService.initDB();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	public static void initDB() throws JSONException{
		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/getRecipe.do";
		
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
        Prescription  prescription = null;
        prescriptionlist = new ArrayList<Prescription>();
        for(int i = 0; i < ja.length(); i++){
        	JSONObject jsonobj = (JSONObject) ja.get(i);
        	
        	int id = jsonobj.getInt("recipe_id");
        	String name = jsonobj.getString("recipe_name"); 
        	int department = 0;//////?
        	Doctor doctor = DoctorWebService.getDoctorByID(jsonobj.getInt("creator_id"));
        	Doctor checker = null;
//			Doctor checker = DoctorWebService.getDoctorByID(jsonobj.getInt("reviewer_id"));
        	if(!jsonobj.get("reviewer_id").toString().equals("null")){
        		checker = DoctorWebService.getDoctorByID(jsonobj.getInt("reviewer_id"));
        	}
        	//String patient_name =jsonobj.getString("user_name");
        	Patient patient = new Patient();
        	patient.setName(jsonobj.getString("user_name"));
        	patient.setAge(jsonobj.getInt("user_age"));
        	patient.setSex(jsonobj.getInt("user_sex"));
        	if(!jsonobj.get("user_id").toString().equals("null"))
	        	patient.setId(jsonobj.getInt("user_id"));
			else
				patient.setId(0);
        	//patient.setId(jsonobj.getInt("user_id"));
        	
        	List<Prescription_drugmap> drugmaps=new ArrayList<Prescription_drugmap>();
        	JSONArray jsonarray = jsonobj.getJSONArray("detailList");
        	for(int j = 0; j < jsonarray.length(); j++){
        		JSONObject tmpobj = (JSONObject) jsonarray.get(j);
        		System.out.println(tmpobj.toString());
        		Drug tmpdrug = DrugWebService.getDrugByID(tmpobj.getInt("drug_id")); 
        		int cnt = tmpobj.getInt("amount");
        		String description=tmpobj.getString("how_to_use");
        		drugmaps.add(new Prescription_drugmap(tmpdrug, cnt, description));
        	}
        	
        	int status = jsonobj.getInt("status");
        	String date = jsonobj.getString("create_time_text"); 
        	String clinical_diagnosis = jsonobj.getString("content");; //////?
        	
        	prescription = new Prescription(
        			id, name, department, doctor, checker, patient,
        			drugmaps, status, date, clinical_diagnosis);

			/// add reason
			prescription.setReview_opinion(jsonobj.get("review_opinion").toString());
        	prescriptionlist.add(prescription);
//        	System.out.println("success add:"+JsonHelper.toJSON(prescription));
        }
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

    
	public static Map<String, String> AddPrescription(Prescription item){
//		下面一行为新增加
		Map<String, String> map = new HashMap<String, String>();

    	String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/addRecipe.do";
    	StringBuilder itemStr = new StringBuilder();
    	itemStr.append("recipe_name="+item.getName());
    	itemStr.append("&creator_id="+Utils.LOGIN_DOCTOR.getId());
    	itemStr.append("&user_name="+item.getPatient().getName());
    	itemStr.append("&user_age="+item.getPatient().getAge());
    	itemStr.append("&user_sex="+item.getPatient().getSex());
    	itemStr.append("&user_id="+item.getPatient().getId());

    	itemStr.append("&content="+item.getClinical_diagnosis());
    	itemStr.append("&status="+item.getStatus());
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String timestr = df.format(new Date());
    	itemStr.append("&create_time_text="+timestr);

    	JSONArray jsonArray = new JSONArray();
    	for(Prescription_drugmap drugmap : item.getDruglist()){
    		JSONObject jsonObject = new JSONObject();
    		try {
				jsonObject.put("amount", drugmap.getCount());
	    		jsonObject.put("how_to_use", drugmap.getDescription());
	    		jsonObject.put("drug_id", drugmap.getDrug().getId());
	    		jsonObject.put("drug_name", drugmap.getDrug().getName());
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


//		获取后台返回信息
    	/// 
    	try {
//			Map m = JsonHelper.toMap(result);
//			ResponseItem responditem = new ResponseItem();
//			responditem = (ResponseItem) JsonHelper.toJavaBean(responditem, m);
			JSONObject jo = new JSONObject(result);
			String retresult = jo.getString("result");
			map.put("result",retresult);
			String message = jo.getString("message");
			map.put("message",message);
			initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Prescription prescription_inDB = getPrescriptionByName(item.getName());
//
//		if(prescription_inDB == null){
//			item.setId(MAX_ID);
//	    	MAX_ID++;
//			prescriptionlist.add(item);
//		}else{
//			prescriptionlist.set(prescriptionlist.indexOf(prescription_inDB), item);
//		}
		return  map;
	}

	public static void reviewPrescription(Prescription item){
		Map<String, String> map = new HashMap<String, String>();
//		System.out.println("checker_id :hahahahahahah"+item.getChecker().getId());
		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/reviewRecpice.do";
		StringBuilder itemStr = new StringBuilder();
		itemStr.append("recipe_id="+item.getId());
		itemStr.append("&recipe_name="+item.getName());

		itemStr.append("&creator_id="+item.getDoctor().getId());
		if(item.getChecker()!= null){
			itemStr.append("&reviewer_id="+item.getChecker().getId());
		}
		itemStr.append("&user_name="+item.getPatient().getName());
		itemStr.append("&user_age="+item.getPatient().getAge());
		itemStr.append("&user_sex="+item.getPatient().getSex());
		itemStr.append("&user_id="+item.getPatient().getId());
		itemStr.append("&content="+item.getClinical_diagnosis());
		itemStr.append("&status="+item.getStatus());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestr = df.format(new Date());
		itemStr.append("&create_time_text="+timestr);
		itemStr.append("&review_opinion="+item.getReview_opinion());

		JSONArray jsonArray = new JSONArray();
		for(Prescription_drugmap drugmap : item.getDruglist()){
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("amount", drugmap.getCount());
				jsonObject.put("how_to_use", drugmap.getDescription());
				jsonObject.put("drug_id", drugmap.getDrug().getId());
				jsonObject.put("drug_name", drugmap.getDrug().getName());
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
		//获取注册未审核功能


		///
		try {
			initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updatePrescription(Prescription item){
		Map<String, String> map = new HashMap<String, String>();
//		System.out.println("checker_id :hahahahahahah"+item.getChecker().getId());
		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/updateRecpice.do";
    	StringBuilder itemStr = new StringBuilder();
    	itemStr.append("recipe_id="+item.getId());
    	itemStr.append("&recipe_name="+item.getName());

    	itemStr.append("&creator_id="+item.getDoctor().getId());
		System.out.println("lalala123->");
		System.out.println("lalala567->"+item.getChecker()== null);
		System.out.println("lalalatyu->");
		if(item.getChecker()!= null){
			itemStr.append("&reviewer_id="+item.getChecker().getId());
			System.out.println("test518"+item.getChecker().getId());
		}
    	itemStr.append("&user_name="+item.getPatient().getName());
    	itemStr.append("&user_age="+item.getPatient().getAge());
    	itemStr.append("&user_sex="+item.getPatient().getSex());
    	itemStr.append("&user_id="+item.getPatient().getId());
    	itemStr.append("&content="+item.getClinical_diagnosis());
    	itemStr.append("&status="+item.getStatus());
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String timestr = df.format(new Date());
    	itemStr.append("&create_time_text="+timestr);

    	JSONArray jsonArray = new JSONArray();
    	for(Prescription_drugmap drugmap : item.getDruglist()){
    		JSONObject jsonObject = new JSONObject();
    		try {
				jsonObject.put("amount", drugmap.getCount());
	    		jsonObject.put("how_to_use", drugmap.getDescription());
	    		jsonObject.put("drug_id", drugmap.getDrug().getId());
	    		jsonObject.put("drug_name", drugmap.getDrug().getName());
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
		//获取注册未审核功能

    	
    	/// 
    	try {
			initDB();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
//		Prescription presciption = getPrescriptionByName(item.getName());
//    	int index = prescriptionlist.indexOf(presciption);
//    	prescriptionlist.set(index, item);
//		return  map;
	}

	public static void delPrescription(Prescription item){
//		for(Prescription tmp : prescriptionlist){
//			if(tmp.getId() == item.getId()){
//				prescriptionlist.remove(tmp);
//				break;
//			}
//		}
//		for(Prescription tmp : prescriptionlist) {
//			if(tmp.getId() == item.getId()){
//				prescriptionlist.remove(tmp);
//				break;
//			}
//		}
			String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/deleteRecipe.do?";
			StringBuilder itemStr = new StringBuilder();
			itemStr.append("recipe_id=" + item.getId());
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

	
	public static List<Prescription> getAllPrescription()
	{
		return prescriptionlist;
	}
	
	public static List<Prescription> getPrescriptionbyStatus(int status)
	{   
		//List<Prescription> prescriptionsaved,prescriptioncommited,prescriptionapproved,prescriptionrefused; 
		List<Prescription> prescription_result_lt = new ArrayList<Prescription>(); 
		List<Prescription> prescriptionlist = getAllPrescription();
		for(Prescription item :prescriptionlist){
			
			if(item.getStatus() == status){
				prescription_result_lt.add(item);
			}
			
		}
		
		return prescription_result_lt;
	
	}
	
	public static Prescription getPrescriptionByName(String name){
		for(Prescription item :prescriptionlist){
			if(item.getName()!=null&&item.getName().equals(name)){
				return item;
			}
		}
		return null;
	}
	
	public static List<String> getAllPrescriptionName(){
    	List<String> namelist = new ArrayList<String>();
    	for(Prescription item : prescriptionlist){
    		namelist.add(item.getName());
    	}
    	return namelist;
    }
    
	
	
}
