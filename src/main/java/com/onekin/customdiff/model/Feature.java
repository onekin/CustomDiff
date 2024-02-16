package com.onekin.customdiff.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="feature")
public class Feature {
	@Id String idfeature;
	String name;
	int isnew;
	int idparent;
	
	public Feature() {}

	public String getIdfeature() {
		return idfeature;
	}

	public void setIdfeature(String idfeature) {
		this.idfeature = idfeature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsNew() {
		return isnew;
	}

	public void setIsNew(int isNew) {
		this.isnew = isNew;
	}

	public int getIsnew() {
		return isnew;
	}

	public void setIsnew(int isnew) {
		this.isnew = isnew;
	}

	public int getIdparent() {
		return idparent;
	}

	public void setIdparent(int idparent) {
		this.idparent = idparent;
	}
	
	
	
}
