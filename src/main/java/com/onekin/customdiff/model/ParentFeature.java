package com.onekin.customdiff.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

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
