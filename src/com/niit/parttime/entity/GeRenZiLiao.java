package com.niit.parttime.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeRenZiLiao implements Serializable  {

	public int user_ID;
	public String user_name;
	public String user_phone;
	public String school;
	public Double user_height;

	public Double user_weight;
	

	public GeRenZiLiao(int user_ID) {
		super();
		this.user_ID = user_ID;
	}
	
	public GeRenZiLiao() {
		super();
	}

	public int getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(int user_ID) {
		this.user_ID = user_ID;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Double getUser_height() {
		return user_height;
	}

	public void setUser_height(Double user_height) {
		this.user_height = user_height;
	}
	
	public Double getUser_weight() {
		return user_weight;
	}

	public void setUser_weight(Double user_weight) {
		this.user_weight = user_weight;
	}
}
