package com.onekin.customdiff.model;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="product_release")
public class ProductRelease {

	@Id int idproductrelease;
	String name;//tag
	Date date;
	String commits_set;
	
	public ProductRelease() {}

	
	
	public ProductRelease(int id_productrelease, String name, Date date, String commits_set) {
		super();
		this.idproductrelease = id_productrelease;
		this.name = name;
		this.date = date;
		this.commits_set = commits_set;
		//this.idproduct = id_product;
	}



	public int getIdproductrelease() {
		return idproductrelease;
	}

	public void setIdproductrelease(int id_productrelease) {
		this.idproductrelease = id_productrelease;
	}

	public String getName() {
		return name;
	}
	
	public String getNameFormated(String split) {
		String pname = this.name;
		if(this.name.contains(split))
			 pname=this.name.split(split)[0];
		
		 return pname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCommits_set() {
		return commits_set;
	}

	public void setCommits_set(String commits_set) {
		this.commits_set = commits_set;
	}

}
