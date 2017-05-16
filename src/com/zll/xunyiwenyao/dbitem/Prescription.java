package com.zll.xunyiwenyao.dbitem;

import java.util.List;

/**
 * Created by rxz on 2017/3/21.
 */

public class Prescription {
    private int id;
    private String name;
    private int department;
    private Doctor doctor;
    private Doctor checker;
    private Patient patient;
    //private Map<Drug, Integer> drugmap;
    private List<Prescription_drugmap> druglist;
    private int status;
    private String date;
    private String clinical_diagnosis;
    

    public Prescription(){
    }
  

    public Prescription(int id, String name, int department, Doctor doctor, Doctor checker, Patient patient,
    		List<Prescription_drugmap> druglist, int status,String date,String clinical_diagnosis) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.doctor = doctor;
		this.checker = checker;
		this.patient = patient;
		this.druglist = druglist;
		this.status = status;
		this.date =date;
		this.clinical_diagnosis=clinical_diagnosis;
	}

    

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Prescription_drugmap> getDruglist() {
        return druglist;
    }

    public void setDruglist(List<Prescription_drugmap> druglist) {
        this.druglist = druglist;
    }



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getClinical_diagnosis() {
		return clinical_diagnosis;
	}



	public void setClinical_diagnosis(String clinical_diagnosis) {
		this.clinical_diagnosis = clinical_diagnosis;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public Doctor getChecker() {
		return checker;
	}



	public void setChecker(Doctor checker) {
		this.checker = checker;
	}

    
}