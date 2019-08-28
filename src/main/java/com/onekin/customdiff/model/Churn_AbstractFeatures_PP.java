package com.onekin.customdiff.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
//

@Entity
@Table(name = "churn_parent_features_productportfolio")
public class Churn_AbstractFeatures_PP {
	@Id String id;
	int id_parentfeature;
	String parentfeaturename;
	int id_pr;
	String pr_name;
	int added;
	int deleted;
	int churn;
	
	public Churn_AbstractFeatures_PP() {}

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
	
	
}
