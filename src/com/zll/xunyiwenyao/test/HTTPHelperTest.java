package com.zll.xunyiwenyao.test;

import com.zll.xunyiwenyao.util.HttpHelper;

public class HTTPHelperTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String loginurl = "http://222.29.100.155/b2b2c/api/mobile/doctor/doctorLogin.do";
		String loginparam = "reg_name=10001&password=111111";
		String loginresult = HttpHelper.sendPost(loginurl, loginparam);
		System.out.println(loginresult);
		
		//String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/addRecipeTemplate.do";
		//String param = "template_name=template2&creator_id=30&department=0&details_json=[{\"how_to_use\":\"hello\",\"amount\":995,\"drug_id\":1}]";

		String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/addRecipe.do";
		//String param = "recipe_name=recepict4&creator_id=1&user_name=&user_age=0&user_sex=0&content=&status=1&create_time_text=2017-05-02 09:29:29&details_json=[{\"amount\":5,\"how_to_use\":\"zll\",\"drug_id\":1}]";
		String param = "recipe_name=recepict4&creator_id=1&user_name=&user_age=0&user_sex=0&content=&status=1&details_json=[{\"amount\":5,\"how_to_use\":\"zll\",\"drug_id\":1}]";
		

		//String url = "http://222.29.100.155/b2b2c/api/mobile/recipe/updateRecpice.do";
		//String param = "recipe_name=recepict4&creator_id=1&user_name=&user_age=0&user_sex=0&content=&status=1&details_json=[{\"amount\":5,\"how_to_use\":\"zll\",\"drug_id\":1}]";
		
		//String param = "recipe_name=recepict4&creator_id=1&user_name=&user_age=0&user_sex=0&content=&status=1&details_json=1";
		//param += "details_json=[{\"recipedetail_id\":1234,\"drug_id\":1,\"drug_name\":\"六味地黄丸\",\"\":9,\"how_to_use\":\"口服\",\"per_count\":\"per_count\"}]";
		//String param = "template={\"fields\":{},\"recipeTemplate_id\":1,\"template_name\":\"template\",\"creator_id\":1,\"department\":\"1\",\"details_json\":\"[{recipeTemplateDetail_id:1,recipeTemplate_id:1,drug_id:1,drug_name:\"fgdf\",count:5,per_count:1,how_to_use:\"ttt\"}]\",\"create_time\":0,\"create_time_text\":\"\",\"detailList\":[{\"fields\":{},\"templateDetail_id\":null,\"template_id\":null,\"drug_id\":1,\"drug_name\":\"fgdf\",\"count\":5,\"how_to_use\":\"ttt\",\"per_count\":1}]}";
		String result = HttpHelper.sendPost(url, param);
		System.out.println(result);
	}

}
