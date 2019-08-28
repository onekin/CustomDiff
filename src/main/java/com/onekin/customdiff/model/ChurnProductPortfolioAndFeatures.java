package com.onekin.customdiff.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "churn_productportfolio_features")
public class ChurnProductPortfolioAndFeatures{

	@Id  String id;
	String id_feature;
	String featuremodified;
	//int id_product;
	int id_pr;
	String pr_name;
	int added;
	int deleted;
	int churn;
	
	public ChurnProductPortfolioAndFeatures() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getId_feature() {
		return id_feature;
	}

	public void setId_feature(String id_feature) {
		this.id_feature = id_feature;
	}

	public String getFeaturemodified() {
		return featuremodified;
	}

	public void setFeaturemodified(String featuremodified) {
		this.featuremodified = featuremodified;
	}

	public int getId_pr() {
		return id_pr;
	}

	public void setId_pr(int id_pr) {
		this.id_pr = id_pr;
	}

	public String getPr_name() {
		return pr_name;
	}

	public void setPr_name(String pr_name) {
		this.pr_name = pr_name;
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

	public String toString() {
		return "Customization for product "+this.pr_name+" and feature "+this.featuremodified+ " and churn "+this.churn;
	}

	
	
}
