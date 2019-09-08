package com.onekin.customdiff.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "churn_parent_features_productportfolio")
public class ChurnParentFeaturesProductPortfolio {

	@Id
	private String id;
	@Column(name = "id_parentfeature")
	private int idParentFeature;
	@Column(name = "parentfeaturename")
	private String parentFeatureName;
	@Column(name = "id_pr")
	private int productId;
	@Column(name="pr_name")
	private String productName;
	private long churn;

	public ChurnParentFeaturesProductPortfolio(String id, int idParentFeature, String parentFeatureName,
			int productId, String productName, long churn) {
		super();
		this.id = id;
		this.idParentFeature = idParentFeature;
		this.parentFeatureName = parentFeatureName;
		this.productId = productId;
		this.churn = churn;
		this.productName = productName;
	}
	
	

	public ChurnParentFeaturesProductPortfolio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdParentFeature() {
		return idParentFeature;
	}

	public void setIdParentFeature(int idParentFeature) {
		this.idParentFeature = idParentFeature;
	}

	public String getParentFeatureName() {
		return parentFeatureName;
	}

	public void setParentFeatureName(String parentFeatureName) {
		this.parentFeatureName = parentFeatureName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public long getChurn() {
		return churn;
	}

	public void setChurn(long churn) {
		this.churn = churn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
