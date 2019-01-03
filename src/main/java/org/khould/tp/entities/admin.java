package org.khould.tp.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class admin extends Users{
private String gender;



public admin(String username, String password, Entreprise entr, String gender) {
	super(username, password, entr);
	this.gender = gender;
}

public admin() {
	super();
	// TODO Auto-generated constructor stub
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

}
