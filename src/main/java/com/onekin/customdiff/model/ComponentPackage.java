package com.onekin.customdiff.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="component_package")
public class ComponentPackage {
	
	@Id int idpackage;
	String name;
	int isroot;
	
	public ComponentPackage() {}

	public int getIdpackage() {
		return idpackage;
	}

	public void setIdpackage(int idpackage) {
		this.idpackage = idpackage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsroot() {
		return isroot;
	}

	public void setIsroot(int isroot) {
		this.isroot = isroot;
	}

	
}
