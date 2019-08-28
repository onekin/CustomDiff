package com.onekin.customdiff.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="developers_in_customizations")
public class DevelopersInCustomizations {
	@Id int id;
	int id_customization;
	int id_developer;
	int name;
	int email;
	
	public DevelopersInCustomizations() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_customization() {
		return id_customization;
	}

	public void setId_customization(int id_customization) {
		this.id_customization = id_customization;
	}

	public int getId_developer() {
		return id_developer;
	}

	public void setId_developer(int id_developer) {
		this.id_developer = id_developer;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getEmail() {
		return email;
	}

	public void setEmail(int email) {
		this.email = email;
	}
	
	
	
}
