package com.onekin.customdiff.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customs_by_feature_and_coreasset")
public class CustomsByFeatureAndCoreAsset {
	@Id String id;
	int idcustomization;
	String idfeature;
	int idcoreasset;
	String capath;
	String caname;
	int idproductrelease;
	String prname;
	int added;
	int deleted;
	String custom_diff;
	String messages;
	String commits;
	String maindiff;
	int idparentfeature;
	String parentfeaturename;
	
	public  CustomsByFeatureAndCoreAsset() {}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public int getIdcustomization() {
		return idcustomization;
	}


	public void setIdcustomization(int idcustomization) {
		this.idcustomization = idcustomization;
	}


	public int getCoreassetid() {
		return idcoreasset;
	}


	public void setCoreassetid(int coreassetid) {
		this.idcoreasset = coreassetid;
	}


	public String getIdfeature() {
		return idfeature;
	}


	public void setIdfeature(String idfeature) {
		this.idfeature = idfeature;
	}


	public int getIdproductrelease() {
		return idproductrelease;
	}


	public void setIdproductrelease(int idproductrelease) {
		this.idproductrelease = idproductrelease;
	}


	public String getCapath() {
		return capath;
	}


	public void setCapath(String capath) {
		this.capath = capath;
	}


	public String getCaname() {
		return caname;
	}


	public void setCaname(String caname) {
		this.caname = caname;
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


	public int getIdcoreasset() {
		return idcoreasset;
	}


	public void setIdcoreasset(int idcoreasset) {
		this.idcoreasset = idcoreasset;
	}


	public String getPrname() {
		return prname;
	}


	public void setPrname(String prname) {
		this.prname = prname;
	}

	public String getCustom_diff() {
		return custom_diff;
	}


	public void setCustom_diff(String custom_diff) {
		this.custom_diff = custom_diff;
	}


	public String getMessages() {
		return messages;
	}


	public void setMessages(String messages) {
		this.messages = messages;
	}


	public String getCommits() {
		return commits;
	}


	public void setCommits(String commits) {
		this.commits = commits;
	}


	public String getMaindiff() {
		return maindiff;
	}


	public void setMaindiff(String maindiff) {
		this.maindiff = maindiff;
	}


	public int getIdparentfeature() {
		return idparentfeature;
	}


	public void setIdparentfeature(int idparentfeature) {
		this.idparentfeature = idparentfeature;
	}


	public String getParentfeaturename() {
		return parentfeaturename;
	}


	public void setParentfeaturename(String parentfeaturename) {
		this.parentfeaturename = parentfeaturename;
	}
	
}
