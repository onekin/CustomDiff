package com.onekin.customdiff.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="developer")
public class Developer {
	@Id int iddeveloper;
	String name;
	String email;
	
	public Developer() {}
	
	public int getId_developer() {
		return iddeveloper;
	}
	public void setId_developer(int id_developer) {
		this.iddeveloper = id_developer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
