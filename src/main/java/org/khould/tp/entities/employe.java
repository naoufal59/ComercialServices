package org.khould.tp.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EMPLOYE")
public class employe extends Users{
	 private String website;
     private String nomSociete;
     private String gender;
	public employe() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}


	public employe(String username, String password, Entreprise entr, String website, String nomSociete,
			String gender) {
		super(username, password, entr);
		this.website = website;
		this.nomSociete = nomSociete;
		this.gender = gender;
	}



	public String getNomSociete() {
		return nomSociete;
	}



	public void setNomSociete(String nomSociete) {
		this.nomSociete = nomSociete;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	 
}
