package org.khould.tp.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Service implements Serializable{
	 @Id
     @GeneratedValue
	 private Long id;
	 private String titre;
	 @Lob
	 private String description;
	    @ManyToOne
	    @JoinColumn(name="Entreprise_Service")
	    private Entreprise entreprises;
	     @ManyToOne
		 @JoinColumn(name="Categorie_services")
		 private Categorie_services srv;
	     @ManyToOne(cascade=CascadeType.DETACH)
		 @JoinColumn(name="code_Users")
		 private Users user;
	     private String photo;
	     
		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public Users getUser() {
			return user;
		}

		public void setUser(Users user) {
			this.user = user;
		}

		public Service() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		public Service(String titre, String description, Entreprise entreprises, Categorie_services srv, Users user,
				String photo) {
			super();
			this.titre = titre;
			this.description = description;
			this.entreprises = entreprises;
			this.srv = srv;
			this.user = user;
			this.photo = photo;
		}

		public Service(String titre, String description, Entreprise entreprises, Categorie_services srv, Users user) {
			super();
			this.titre = titre;
			this.description = description;
			this.entreprises = entreprises;
			this.srv = srv;
			this.user = user;
		}

		public Service(String titre, String description, Entreprise entreprises, Categorie_services srv) {
			super();
			this.titre = titre;
			this.description = description;
			this.entreprises = entreprises;
			this.srv = srv;
		}

		public Service(String titre, String description) {
			super();
			this.titre = titre;
			this.description = description;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTitre() {
			return titre;
		}
		public void setTitre(String titre) {
			this.titre = titre;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Entreprise getEntreprises() {
			return entreprises;
		}
		public void setEntreprises(Entreprise entreprises) {
			this.entreprises = entreprises;
		}
		public Categorie_services getCategorie_services() {
			return srv;
		}
		public void setCategorie_services(Categorie_services srv) {
			this.srv = srv;
		}
	     
}
