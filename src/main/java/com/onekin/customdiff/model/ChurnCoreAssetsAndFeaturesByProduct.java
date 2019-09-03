package com.onekin.customdiff.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "churn_coreassets_and_features_by_pr")
public class ChurnCoreAssetsAndFeaturesByProduct {
	@Id String id;//
	int idproductrelease;
	@Column(name="idfeature")
	String featureId;
	int idcoreasset;
	String pr_name;
	String ca_name;
	String ca_path;
	int packageId;
	long churn;
	



	public ChurnCoreAssetsAndFeaturesByProduct() {}

	
	
	public ChurnCoreAssetsAndFeaturesByProduct(String id, int idproductrelease, String featureId, int idcoreasset,
			String pr_name, String ca_name, String ca_path, int packageId, long churn) {
		super();
		this.id = id;
		this.idproductrelease = idproductrelease;
		this.featureId = featureId;
		this.idcoreasset = idcoreasset;
		this.pr_name = pr_name;
		this.ca_name = ca_name;
		this.ca_path = ca_path;
		this.packageId = packageId;
		this.churn = churn;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdcoreasset() {
		return idcoreasset;
	}

	public void setIdcoreasset(int idcoreasset) {
		this.idcoreasset = idcoreasset;
	}


	public int getId_coreasset() {
		return idcoreasset;
	}

	public void setId_coreasset(int id_coreasset) {
		this.idcoreasset = id_coreasset;
	}

	public String getPr_name() {
		return pr_name;
	}

	public void setPr_name(String pr_name) {
		this.pr_name = pr_name;
	}

	public String getCa_name() {
		return ca_name;
	}

	public void setCa_name(String ca_name) {
		this.ca_name = ca_name;
	}

	public String getCa_path() {
		return ca_path;
	}

	public void setCa_path(String ca_path) {
		this.ca_path = ca_path;
	}

	public long getChurn() {
		return churn;
	}

	public void setChurn(long churn) {
		this.churn = churn;
	}

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String idfeature) {
		this.featureId = idfeature;
	}

	public int getIdproductrelease() {
		return idproductrelease;
	}

	public void setIdproductrelease(int idproductrelease) {
		this.idproductrelease = idproductrelease;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	
	
	
}
