package com.onekin.customdiff.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "churn_parent_features_package_assets")
public class ChurnParentFeaturesAndCoreAssets {

	@Id
	private String id;

	@Column(name = "id_parentfeature")
	private int parentFeatureId;
	
	@Column(name="parentfeaturename")
	private String parentFeatureName;
	
	@Column(name="idpackage")
	private int packageId;
	
	@Column(name="package_name")
	private String packageName;
	
	@Column(name="idcoreasset")
	private int coreAssetId;
	
	@Column(name="caname")
	private String coreAssetName;
	
	@Column(name="capath")
	private String coreAssetPath;
	
	private long churn;

	public ChurnParentFeaturesAndCoreAssets(String id, int parentFeatureId, String parentFeatureName, int packageId,
			String packageName, int coreAssetId, String coreAssetName, String coreAssetPath, long churn) {
		super();
		this.id = id;
		this.parentFeatureId = parentFeatureId;
		this.parentFeatureName = parentFeatureName;
		this.packageId = packageId;
		this.packageName = packageName;
		this.coreAssetId = coreAssetId;
		this.coreAssetName = coreAssetName;
		this.coreAssetPath = coreAssetPath;
		this.churn = churn;
	}

	public ChurnParentFeaturesAndCoreAssets() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getParentFeatureId() {
		return parentFeatureId;
	}

	public void setParentFeatureId(int parentFeatureId) {
		this.parentFeatureId = parentFeatureId;
	}

	public String getParentFeatureName() {
		return parentFeatureName;
	}

	public void setParentFeatureName(String parentFeatureName) {
		this.parentFeatureName = parentFeatureName;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getCoreAssetId() {
		return coreAssetId;
	}

	public void setCoreAssetId(int coreAssetId) {
		this.coreAssetId = coreAssetId;
	}

	public String getCoreAssetName() {
		return coreAssetName;
	}

	public void setCoreAssetName(String coreAssetName) {
		this.coreAssetName = coreAssetName;
	}

	public String getCoreAssetPath() {
		return coreAssetPath;
	}

	public void setCoreAssetPath(String coreAssetPath) {
		this.coreAssetPath = coreAssetPath;
	}

	public long getChurn() {
		return churn;
	}

	public void setChurn(long churn) {
		this.churn = churn;
	}
	
	
	

}
