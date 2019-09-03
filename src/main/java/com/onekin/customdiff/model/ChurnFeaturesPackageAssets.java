package com.onekin.customdiff.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table(name = "churn_features_package_assets")
public class ChurnFeaturesPackageAssets {
	
	@Id String id;
	@Column(name="idfeature")
	String featureId; 
	String featurename;
	int idparentfeature;
	int  idproductrelease;
	String pr_name;
	String  package_name;
	int idcoreasset;	
	String  caname;
	String  capath;
	@Column(name="idpackage")
	int packageId;	
	int isroot;
	int added;
	int deleted;
	long  churn;
	

	
	public ChurnFeaturesPackageAssets(String id, String featureId, String featurename, int idcoreasset, String caname,
			String capath, int packageId, long churn) {
		super();
		this.id = id;
		this.featureId = featureId;
		this.featurename = featurename;
		this.idcoreasset = idcoreasset;
		this.caname = caname;
		this.capath = capath;
		this.packageId = packageId;
		this.churn = churn;
	}


	public ChurnFeaturesPackageAssets(){}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFeatureId() {
		return featureId;
	}


	public void setFeatureId(String idfeature) {
		this.featureId = idfeature;
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


	public int getIdcoreasset() {
		return idcoreasset;
	}


	public void setIdcoreasset(int idcoreasset) {
		this.idcoreasset = idcoreasset;
	}


	public String getCaname() {
		return caname;
	}


	public void setCaname(String caname) {
		this.caname = caname;
	}


	public String getCapath() {
		return capath;
	}


	public void setCapath(String capath) {
		this.capath = capath;
	}


	public int getPackageId() {
		return packageId;
	}


	public void setPackageId(int packageId) {
		this.packageId = packageId;
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


	
	
}
