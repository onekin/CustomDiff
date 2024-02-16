package com.onekin.customdiff.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "churn_productportfolio_features")
public class ChurnProductPortfolioAndFeatures {

	@Id
	String id;
	@Column(name = "id_feature")
	String idFeature;
	String featuremodified;
	// int id_product;
	int id_pr;
	String pr_name;
	int added;
	int deleted;
	long churn;
	@Column(name = "id_parent_feature")
	int parentFeatureId;

	public ChurnProductPortfolioAndFeatures() {

	}

	public ChurnProductPortfolioAndFeatures(String id, String idFeature, String featuremodified, int id_pr,
			String pr_name, int added, int deleted, long churn, int parentFeatureid) {
		super();
		this.id = id;
		this.idFeature = idFeature;
		this.featuremodified = featuremodified;
		this.id_pr = id_pr;
		this.pr_name = pr_name;
		this.added = added;
		this.deleted = deleted;
		this.churn = churn;
		this.parentFeatureId = parentFeatureid;
	}

	public ChurnProductPortfolioAndFeatures(int id_pr, String pr_name, long churn) {
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

	public int getProductId() {
		return id_pr;
	}

	public void setId_pr(int id_pr) {
		this.id_pr = id_pr;
	}

	public String getProductName() {
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

	public int getParentFeatureId() {
		return parentFeatureId;
	}

	public void setParentFeatureId(int parentFeatureId) {
		this.parentFeatureId = parentFeatureId;
	}

	public String toString() {
		return "Customization for product " + this.pr_name + " and feature " + this.featuremodified + " and churn "
				+ this.churn;
	}

}
