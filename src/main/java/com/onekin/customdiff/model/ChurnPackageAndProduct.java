package com.onekin.customdiff.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "churn_packages_and_product")
public class ChurnPackageAndProduct {

	@Id
	private String id;

	private int idPackage;
	private int idProductRelease;
	private String prName;
	private String packageName;
	private int churn;

	public ChurnPackageAndProduct(String id, int idPackage, int idProductRelease, String prName, String packageName,
			int churn) {
		super();
		this.id = id;
		this.idPackage = idPackage;
		this.idProductRelease = idProductRelease;
		this.prName = prName;
		this.packageName = packageName;
		this.churn = churn;
	}

	public ChurnPackageAndProduct() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdPackage() {
		return idPackage;
	}

	public void setIdPackage(int packageId) {
		this.idPackage = packageId;
	}

	public int getIdProductRelease() {
		return idProductRelease;
	}

	public void setIdProductRelease(int idProductRelease) {
		this.idProductRelease = idProductRelease;
	}

	public String getPrName() {
		return prName;
	}

	public void setPrName(String prName) {
		this.prName = prName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getChurn() {
		return churn;
	}

	public void setChurn(int churn) {
		this.churn = churn;
	}

	@Override
	public String toString() {
		return "ChurnPackageAndProduct [id=" + id + ", idPackage=" + idPackage + ", idProductRelease="
				+ idProductRelease + ", prName=" + prName + ", packageName=" + packageName + ", churn=" + churn + "]";
	}

}
