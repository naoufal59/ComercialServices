package org.khould.tp.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Categorie_services implements Serializable {
   
	 @Id @GeneratedValue    
	  private Long id_categorie;
	  private String name;
	  private String description;
	     @ManyToOne(cascade=CascadeType.DETACH)
		 @JoinColumn(name="code_Users")
		 private Users user;
	  @OneToMany(mappedBy="srv")
	  private Collection<Service> services;
	  
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Categorie_services(String name, String description, Users user) {
		super();
		this.name = name;
		this.description = description;
		this.user = user;
	}
	public Long getId_categorie() {
		return id_categorie;
	}
	public void setId_categorie(Long id_categorie) {
		this.id_categorie = id_categorie;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Collection<Service> getServices() {
		return services;
	}
	public void setServices(Collection<Service> services) {
		this.services = services;
	}
	public Categorie_services(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	public Categorie_services(String name) {
		super();
		this.name = name;
	}
	public Categorie_services() {
		super();
		// TODO Auto-generated constructor stub
	}
	  
}
