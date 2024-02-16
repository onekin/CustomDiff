package com.onekin.customdiff.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;


@Entity
@Table(name = "churn_parent_features_product_packages")
public class ChurnParentFeaturesProductComponents {
	@Id String id;
	int id_parentfeature; 
	String parentfeaturename;
	int idproductrelease;
	String pr_name;
	String  package_name;
	int  idpackage;
	int isroot;
	int added;
	int deleted;
	long  churn;
	
	public ChurnParentFeaturesProductComponents() {}

	
	
	public ChurnParentFeaturesProductComponents(String id, int id_parentfeature, String parentfeaturename,
			String package_name, int idpackage, long churn) {
		super();
		this.id = id;
		this.id_parentfeature = id_parentfeature;
		this.parentfeaturename = parentfeaturename;
		this.package_name = package_name;
		this.idpackage = idpackage;
		this.churn = churn;
	}



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

	public long getChurn() {
		return churn;
	}

	public void setChurn(long churn) {
		this.churn = churn;
	}

	public int getIdpackage() {
		return idpackage;
	}

	public void setIdpackage(int idpackage) {
		this.idpackage = idpackage;
	}

}
