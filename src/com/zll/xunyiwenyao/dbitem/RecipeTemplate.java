package com.zll.xunyiwenyao.dbitem;

import java.util.List;

public class RecipeTemplate {
	private Integer recipeTemplate_id;	//模板Id
	private String template_name;//模板名称
	private Integer creator_id;//创建者ID
	private String department;//科室
	private String details_json;//模板明细串
	private Long create_time; //创建时间
	
	//非数据库字段
	private String create_time_text;//字符型创建时间

	//非数据库字段，模板明细列表
	private List<Prescription_drugmap> detailList;//模板明细列表

	public Integer getRecipeTemplate_id() {
		return recipeTemplate_id;
	}

	public void setRecipeTemplate_id(Integer recipeTemplate_id) {
		this.recipeTemplate_id = recipeTemplate_id;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public Integer getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(Integer creator_id) {
		this.creator_id = creator_id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDetails_json() {
		return details_json;
	}

	public void setDetails_json(String details_json) {
		this.details_json = details_json;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

	public String getCreate_time_text() {
		return create_time_text;
	}

	public void setCreate_time_text(String create_time_text) {
		this.create_time_text = create_time_text;
	}

	public List<Prescription_drugmap> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<Prescription_drugmap> detailList) {
		this.detailList = detailList;
	}

}
