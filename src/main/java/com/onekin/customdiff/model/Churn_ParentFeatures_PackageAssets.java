package com.onekin.customdiff.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table(name = "churn_parent_features_package_assets")
public class Churn_ParentFeatures_PackageAssets {
	
	@Id String id;
	int id_parentfeature; 
	String parentfeaturename;
	int idproductrelease;
	String pr_name;
	String  package_name;
	int  idpackage;
	int isroot;
	String caname;
	int idcoreasset;
	String capath;
	int added;
	int deleted;
	int  churn;
	
	public Churn_ParentFeatures_PackageAssets() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getId_parentfeature() {
		return id_parentfeature;
	}

	public void setId_parentfeature(int id_parentfeature) {
		this.id_parentfeature = id_parentfeature;
	}

	public String getParentfeaturename() {
		return parentfeaturename;
	}

	public void setParentfeaturename(String parentfeaturename) {
		this.parentfeaturename = parentfeaturename;
	}

	public int getIdproductrelease() {
		return idproductrelease;
	}

	public void setIdproductrelease(int idproductrelease) {
		this.idproductrelease = idproductrelease;
	}

	public String getPr_name() {
		return pr_name;
	}

	public void setPr_name(String pr_name) {
		this.pr_name = pr_name;
	}

	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public int getIdpackage() {
		return idpackage;
	}

	public void setIdpackage(int idpackage) {
		this.idpackage = idpackage;
	}

	public int getIsroot() {
		return isroot;
	}

	public void setIsroot(int isroot) {
		this.isroot = isroot;
	}

	public String getCaname() {
		return caname;
	}

	public void setCaname(String caname) {
		this.caname = caname;
	}

	public int getIdcoreasset() {
		return idcoreasset;
	}

	public void setIdcoreasset(int idcoreasset) {
		this.idcoreasset = idcoreasset;
	}

	public String getCapath() {
		return capath;
	}

	public void setCapath(String capath) {
		this.capath = capath;
	}

	public int getAdded() {
		return added;
	}

	public void setAdded(int added) {
		this.added = added;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public int getChurn() {
		return churn;
	}

	public void setChurn(int churn) {
		this.churn = churn;
	}
	

}
