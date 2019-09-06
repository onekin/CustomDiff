package com.onekin.customdiff.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "churn_productportfolio_features")
public class ChurnProductPortfolioAndFeatures{

	@Id  String id;
	@Column(name="id_feature")
	String idFeature;
	String featuremodified;
	//int id_product;
	int id_pr;
	String pr_name;
	int added;
	int deleted;
	long churn;
	
	public ChurnProductPortfolioAndFeatures() {
		
	}

	public ChurnProductPortfolioAndFeatures( int id_pr,
			String pr_name, long churn) {
		super();
		this.id_pr = id_pr;
		this.pr_name = pr_name;
		this.churn = churn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getIdFeature() {
		return idFeature;
	}

	public void setIdFeature(String idFeature) {
		this.idFeature = idFeature;
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

	public long getChurn() {
		return churn;
	}

	public void setChurn(long churn) {
		this.churn = churn;
	}

	public String toString() {
		return "Customization for product "+this.pr_name+" and feature "+this.featuremodified+ " and churn "+this.churn;
	}

	
	
}
