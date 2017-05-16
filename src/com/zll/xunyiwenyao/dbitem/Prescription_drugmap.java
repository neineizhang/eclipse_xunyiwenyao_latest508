package com.zll.xunyiwenyao.dbitem;

public class Prescription_drugmap {
	private Drug drug;
	private Integer count;
	private String description;
	public Prescription_drugmap(Drug drug, Integer count, String description) {
		super();
		this.drug = drug;
		this.count = count;
		this.description = description;
	}
	public Prescription_drugmap(){
		
	}
	public Drug getDrug() {
		return drug;
	}
	public void setDrug(Drug drug) {
		this.drug = drug;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
