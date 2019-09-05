package com.onekin.customdiff.model;



public class ChurnPackageAndProduct {

	private String id;
	private int idPackage;
	private int idProductRelease;
	private String prName;
	private String packageName;
	private long churn;

	public ChurnPackageAndProduct(String id, int idPackage, int idProductRelease, String prName, String packageName,
			long churn) {
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

	public long getChurn() {
		return churn;
	}

	public void setChurn(long churn) {
		this.churn = churn;
	}

	@Override
	public String toString() {
		return "ChurnPackageAndProduct [id=" + id + ", idPackage=" + idPackage + ", idProductRelease="
				+ idProductRelease + ", prName=" + prName + ", packageName=" + packageName + ", churn=" + churn + "]";
	}

}
