package com.zll.xunyiwenyao.dbitem;

/**
 * Created by rxz on 2017/3/21.
 */

public class Drug {
    private int id;
    private String name;
    private String specification;
    //private String price;
    private String py_name;
    private String dosage_form;

    public Drug(){

    }

    public Drug(int id, String name, String specification, String py_name, String dosage_form) {
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.py_name = py_name;
        this.dosage_form = dosage_form;
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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
    
	public String getPy_name() {
		return py_name;
	}

	public void setPy_name(String py_name) {
		this.py_name = py_name;
	}

	public String getDosage_form() {
		return dosage_form;
	}

	public void setDosage_form(String dosage_form) {
		this.dosage_form = dosage_form;
	}
}
