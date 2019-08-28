package com.onekin.customdiff.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
@Entity
@Table (name="variation_point")
public class VariationPoint {
	
	@Id int idvariationpoint;
	String expression;//concated if nested
	int idcoreasset;
	int id_feature_group;
	
	
	public VariationPoint() {}
	
	public VariationPoint(int id_variationpoint, String expression, int id_coreasset, int id_feature_group) {
		super();
		this.idvariationpoint = id_variationpoint;
		this.expression = expression;
		this.idcoreasset = id_coreasset;
		this.id_feature_group = id_feature_group;
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
	public int getId_coreasset() {
		return idcoreasset;
	}
	public void setId_coreasset(int id_coreasset) {
		this.idcoreasset = id_coreasset;
	}
	public int getId_feature_group() {
		return id_feature_group;
	}
	public void setId_feature_group(int id_feature_group) {
		this.id_feature_group = id_feature_group;
	}

}
