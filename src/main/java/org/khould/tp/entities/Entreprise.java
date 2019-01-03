package org.khould.tp.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Entreprise implements Serializable {
	 @Id
     @GeneratedValue
	 private Long code;
	 private String mail;
	 private String gender;
	 private String adress;
	 private String Username;
	 private String password;
	 private String telephone;
	 private String description;
	 private String website;
	 private String photo;
	 @OneToMany(mappedBy="entr")
	 private Collection<Reparation> reparation;
	 @OneToMany(mappedBy="entreprises")
	 private Collection<Service> srv;
	 private String nomSociate;
	 
	public Entreprise(String mail, String gender, String adress, String username, String password, String telephone,
			String description, String website, String photo, String nomSociate) {
		super();
		this.mail = mail;
		this.gender = gender;
		this.adress = adress;
		this.Username = username;
		this.password = password;
		this.telephone = telephone;
		this.description = description;
		this.website = website;
		this.photo = photo;
		this.nomSociate = nomSociate;
	}
	public String getNomSociate() {
		return nomSociate;
	}
	public void setNomSociate(String nomSociate) {
		this.nomSociate = nomSociate;
	}
	public Entreprise( String mail, String gender, String adress, String username, String password,
			String telephone, String description, String website, String photo) {
		super();
	
		this.mail = mail;
		this.gender = gender;
		this.adress = adress;
		Username = username;
		this.password = password;
		this.telephone = telephone;
		this.description = description;
		this.website = website;
		this.photo = photo;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Collection<Reparation> getReparation() {
		return reparation;
	}
	public void setReparation(Collection<Reparation> reparation) {
		this.reparation = reparation;
	}
	
	public Entreprise(String mail, String gender, String adress, String username, String password, String telephone,
			String description, String website) {
		super();
		this.mail = mail;
		this.gender = gender;
		this.adress = adress;
		this.Username = username;
		this.password = password;
		this.telephone = telephone;
		this.description = description;
		this.website = website;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Collection<Service> getSrv() {
		return srv;
	}
	public void setSrv(Collection<Service> srv) {
		this.srv = srv;
	}
	
	public Entreprise() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 
}
