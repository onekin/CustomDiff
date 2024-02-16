package com.onekin.customdiff.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="features_in_variationpoints")
public class FeaturesInVariationpoints {
	@Id String id;
	String idfeature;
	int idvariationpoint;
	String expression;
	String featurename;
	int idcoreasset;
	String caname;
	
	public FeaturesInVariationpoints() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId_feature() {
		return idfeature;
	}

	public void setId_feature(String id_feature) {
		this.idfeature = id_feature;
	}

	public int getId_variationpoint() {
		return idvariationpoint;
	}

	public void setId_variationpoint(int id_variationpoint) {
		this.idvariationpoint = id_variationpoint;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getFeature_name() {
		return featurename;
	}

	public void setFeature_name(String feature_name) {
		this.featurename = feature_name;
	}

	public int getId_coreasset() {
		return idcoreasset;
	}

	public void setId_coreasset(int id_coreasset) {
		this.idcoreasset = id_coreasset;
	}

}
