package com.iam.gestionpersonnel.model;


/*
 * 
 * @author Matar THIOYE
 * 
 */

public class Personnel {

	protected int id;
	protected String nom;
	protected String email;
	protected String pays;
	
	public Personnel() {
	}
	
	public Personnel(String nom, String email, String pays) {
		super();
		this.nom = nom;
		this.email = email;
		this.pays = pays;
	}
	
	public Personnel(int id, String nom, String email, String pays) {
		super();
		this.id = id;
		this.nom = nom;
		this.email = email;
		this.pays = pays;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPays() {
		return pays;
	}
	
	public void setPays(String pays) {
		this.pays = pays;
	}
	
}
