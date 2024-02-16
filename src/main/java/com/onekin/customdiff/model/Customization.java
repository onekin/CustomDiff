package com.onekin.customdiff.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customization_fact")
public class Customization {
	
	@Id int idcustomization;
	int lines_added;
	int lines_deleted;
	String custom_diff;
	String commit_set;
	String message_set;
	String greater_diff;
	int idvariationpoint;
	int idproductrelease;
	int id_developer_group;
	
		
	public Customization () {}

	public int getId_customization() {
		return idcustomization;
	}

	public void setId_customization(int id_customization) {
		this.idcustomization = id_customization;
	}

	public int getLines_added() {
		return lines_added;
	}

	public void setLines_added(int lines_added) {
		this.lines_added = lines_added;
	}

	public int getLines_deleted() {
		return lines_deleted;
	}

	public void setLines_deleted(int lines_deleted) {
		this.lines_deleted = lines_deleted;
	}

	public String getCustom_diff() {
		return custom_diff;
	}

	public void setCustom_diff(String custom_diff) {
		this.custom_diff = custom_diff;
	}

	public String getCommit_set() {
		return commit_set;
	}

	public void setCommit_set(String commit_set) {
		this.commit_set = commit_set;
	}

	public String getMessage_set() {
		return message_set;
	}

	public void setMessage_set(String message_set) {
		this.message_set = message_set;
	}

	public String getGreater_diff() {
		return greater_diff;
	}

	public void setGreater_diff(String greater_diff) {
		this.greater_diff = greater_diff;
	}

	public int getId_variationpoint() {
		return idvariationpoint;
	}

	public void setId_variationpoint(int id_variationpoint) {
		this.idvariationpoint = id_variationpoint;
	}
	
	public int getId_developer_group() {
		return id_developer_group;
	}

	public void setId_developer_group(int id_developer_group) {
		this.id_developer_group = id_developer_group;
	}

	public int getId_productrelease() {
		return idproductrelease;
	}


	public void setId_productrelease(int id_productrelease) {
		this.idproductrelease = id_productrelease;
	}
	
}
