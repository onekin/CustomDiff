package com.onekin.customdiff.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table (name="parent_feature")
public class ParentFeature {
	@Id int idparentfeature;
	String name;
	String responsible;	
	
	public ParentFeature() {}


	public int getIdparentFeature() {
		return idparentfeature;
	}


	public void setIdparentFeature(int idparentFeature) {
		this.idparentfeature = idparentFeature;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getResponsible() {
		return responsible;
	}


	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	
	
}
