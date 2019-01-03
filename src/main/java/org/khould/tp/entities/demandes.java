package org.khould.tp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class demandes implements Serializable {
	 @Id @GeneratedValue
	private Long DemandeId;
	@ManyToOne(cascade=CascadeType.DETACH,fetch= FetchType.EAGER)
	private Service service;
	private Boolean status;
	private String plan;
	private double prix;
	private String instructions ;
	private double nbHeureDay; 
	private double nbEmployeDommande; 
	
	private String demandeDate ;
	@ManyToOne(fetch=FetchType.EAGER)
	private Users user;
	
	public demandes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public demandes(Service service,Users user, Boolean status, String plan, double prix, String instructions, double nbHeureDay,
			double nbEmployeDommande, String demandeDate) {
		super();
		this.service = service;
		this.status = status;
		this.plan = plan;
		this.prix = prix;
		this.instructions = instructions;
		this.nbHeureDay = nbHeureDay;
		this.nbEmployeDommande = nbEmployeDommande;
		this.demandeDate = demandeDate;
		this.user = user;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public double getNbHeureDay() {
		return nbHeureDay;
	}

	public void setNbHeureDay(double nbHeureDay) {
		this.nbHeureDay = nbHeureDay;
	}

	public double getNbEmployeDommande() {
		return nbEmployeDommande;
	}

	public void setNbEmployeDommande(double nbEmployeDommande) {
		this.nbEmployeDommande = nbEmployeDommande;
	}

	public Long getDemandeId() {
		return DemandeId;
	}
	public void setDemandeId(Long demandeId) {
		DemandeId = demandeId;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getDemandeDate() {
		return demandeDate;
	}
	public void setDemandeDate(String demandeDate) {
		this.demandeDate = demandeDate;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	
}
