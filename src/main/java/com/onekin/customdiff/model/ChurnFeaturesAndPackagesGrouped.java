package com.onekin.customdiff.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table(name = "churn_features_product_packages")
public class ChurnFeaturesAndPackagesGrouped {

		
		@Id String id;
		String idfeature; 
		String featurename;
		String  package_name;
		int idpackage;	
		int parentFeatureId;
		
		long  churn;

		
		public ChurnFeaturesAndPackagesGrouped(){}

		
		

		public ChurnFeaturesAndPackagesGrouped(String id, String idfeature, String featurename, String package_name,
				int idpackage, long churn) {
			super();
			this.id = id;
			this.idfeature = idfeature;
			this.featurename = featurename;
			this.package_name = package_name;
			this.idpackage = idpackage;
			this.churn = churn;
		}




		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getIdfeature() {
			return idfeature;
		}


		public void setIdfeature(String idfeature) {
			this.idfeature = idfeature;
		}


		public String getFeaturename() {
			return featurename;
		}


		public void setFeaturename(String featurename) {
			this.featurename = featurename;
		}




		public String getPackage_name() {
			return package_name;
		}


		public void setPackage_name(String package_name) {
			this.package_name = package_name;
		}


		public int getIdpackage() {
			return idpackage;
		}


		public void setIdpackage(int idpackage) {
			this.idpackage = idpackage;
		}




		public long getChurn() {
			return churn;
		}


		public void setChurn(long churn) {
			this.churn = churn;
		}




		public String getParentFeatureId() {
			return getParentFeatureId();
		}




		public void setParentFeatureId(int parentFeatureId) {
			this.parentFeatureId = parentFeatureId;
		}
		

	

}
