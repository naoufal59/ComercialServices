package org.khould.tp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Reparation implements Serializable{
	 @Id
     @GeneratedValue
	 private Long id;
	 private String city;
	 private String titre;
	 private String desciption;
	 @ManyToOne
	 @JoinColumn(name="code_entreprise")
	 private Entreprise entr;
	 @ManyToOne
	 @JoinColumn(name="code_User")
	 private Users user;
	 
	public Reparation(String city, String titre, String desciption, Entreprise entr, Users user) {
		super();
		this.city = city;
		this.titre = titre;
		this.desciption = desciption;
		this.entr = entr;
		this.user = user;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public Reparation(String city, Entreprise entr, Users user) {
		super();
		this.city = city;
		this.entr = entr;
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Entreprise getEntreprise() {
		return entr;
	}
	public void setEntreprise(Entreprise entr) {
		this.entr = entr;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Reparation(String city) {
		super();
		this.city = city;
	}
	public Reparation() {
		super();
	}
}
