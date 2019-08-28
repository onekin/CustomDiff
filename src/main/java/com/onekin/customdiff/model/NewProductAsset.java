package com.onekin.customdiff.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="new_assets_in_products")
public class NewProductAsset {
	@Id String id; 
	int idasset;
	String name;
	String path;
	String content;
	int size;
	int pr_id;
	String pr_name;

	
	public NewProductAsset() {
		
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public int getId_asset() {
		return idasset;
	}


	public void setId_asset(int id_asset) {
		this.idasset = id_asset;
	}


	public String getAsset_name() {
		return name;
	}


	public void setAsset_name(String asset_name) {
		this.name = asset_name;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public int getPr_id() {
		return pr_id;
	}


	public void setPr_id(int pr_id) {
		this.pr_id = pr_id;
	}


	public String getPr_name() {
		return pr_name;
	}


	public void setPr_name(String pr_name) {
		this.pr_name = pr_name;
	}


	
	
}
