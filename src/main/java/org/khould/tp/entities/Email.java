package org.khould.tp.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Email {
	@Id
	@GeneratedValue
	private Long emailId;
	private String email;
	private String objet;
	private String message;
	@ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name="code_Users")
     private Users user;
	
	public Email() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Email(String email, String objet, String message, Users user) {
		super();
		this.email = email;
		this.objet = objet;
		this.message = message;
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Long getEmailId() {
		return emailId;
	}
	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}
	public String getObjet() {
		return objet;
	}
	public void setObjet(String objet) {
		this.objet = objet;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
