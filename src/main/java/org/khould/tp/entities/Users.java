package org.khould.tp.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Role",discriminatorType=DiscriminatorType.STRING,length=8)
public class Users implements Serializable {
	 @Id @GeneratedValue
	 private Long id_User;
	 private String username;
	 private String password;
	 @ManyToOne(cascade=CascadeType.DETACH)
	 @JoinColumn(name="code_entreprise")
	 private Entreprise entreprise;
	 
	public Entreprise getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(Entreprise entr) {
		this.entreprise = entr;
	}
	public Long getIduser() {
		return id_User;
	}
	public void setIduser(Long id_User) {
		this.id_User = id_User;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Users(String username, String password, Entreprise entr) {
		super();
		this.username = username;
		this.password = password;
		this.entreprise = entr;
	}
	
	 
}
