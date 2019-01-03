package org.khould.tp.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENTS")
public class clients extends Users{
 private Long Telephon ;
private String gender;



public clients(String username, String password, Entreprise entr, Long telephon, String gender) {
	super(username, password, entr);
	Telephon = telephon;
	this.gender = gender;
}


public String getGender() {
	return gender;
}


public void setGender(String gender) {
	this.gender = gender;
}


public clients() {
	super();
	// TODO Auto-generated constructor stub
}

public Long getTelephon() {
	return Telephon;
}

public void setTelephon(Long telephon) {
	Telephon = telephon;
}
 
}
