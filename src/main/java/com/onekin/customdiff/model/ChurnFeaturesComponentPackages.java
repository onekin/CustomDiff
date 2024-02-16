package com.onekin.customdiff.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "churn_features_product_packages")
public class ChurnFeaturesComponentPackages {
	
	@Id
	String id;
	String idfeature; 
	String featurename;
	int idparentfeature;
	int  idproductrelease;
	String pr_name;
	String  package_name;
	int idpackage;	
	int isroot;
	int added;
	int deleted;
	int  churn;

	
	public ChurnFeaturesComponentPackages(){}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getIdfeature() {
		return idfeature;
	}


	public void setIdfeature(String idfeature) {
		this.idfeature = idfeature;
	}


	public String getFeaturename() {
		return featurename;
	}


	public void setFeaturename(String featurename) {
		this.featurename = featurename;
	}


	public int getIdparentfeature() {
		return idparentfeature;
	}


	public void setIdparentfeature(int idparentfeature) {
		this.idparentfeature = idparentfeature;
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
