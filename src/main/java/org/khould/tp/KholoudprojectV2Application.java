package org.khould.tp;

import org.khould.tp.dao.CategorieRepository;
import org.khould.tp.dao.EntrepriseRepository;
import org.khould.tp.dao.ReparationRepository;
import org.khould.tp.dao.ServiceRepository;
import org.khould.tp.dao.StatisticsDataRepository;
import org.khould.tp.dao.UsersRepository;
import org.khould.tp.entities.Categorie_services;
import org.khould.tp.entities.Entreprise;
import org.khould.tp.entities.Reparation;
import org.khould.tp.entities.Service;
import org.khould.tp.entities.StatisticsData;
import org.khould.tp.entities.admin;
import org.khould.tp.entities.clients;
import org.khould.tp.entities.employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KholoudprojectV2Application implements CommandLineRunner{
  @Autowired
  public EntrepriseRepository entrepriseRepository;
  @Autowired
  public UsersRepository usersRepository;
  @Autowired
  public ServiceRepository serviceRepository;
  @Autowired
  public  CategorieRepository categorieRepository;
  @Autowired
  public ReparationRepository reparationRepository;
  @Autowired
  public StatisticsDataRepository statisticsDataRepository;
	public static void main(String[] args) {
		SpringApplication.run(KholoudprojectV2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*statisticsDataRepository.save(new StatisticsData(1L,1L,1L));   
		Entreprise e1 = entrepriseRepository.save(new Entreprise("said@amendis.com", "femme", "tetouan", "khoulod95",
				"123456789", "0336565651", "tres grandes societe offre plusieurs services", "www.amendis.com","amandis.jpg","senda"));
		Entreprise e2 = entrepriseRepository.save(new Entreprise("somia@senda.com", "femme", "tetouan", "soumia95",
				"123456", "0336565651", "tres grandes societe offre plusieurs services", "www.senda.com","amandis.jpg","amandis"));
		Entreprise e3 = entrepriseRepository.save(new Entreprise("som@senda.com", "femme", "tetouan", "soum95",
				"123456", "0336565651", "tres grandes societe offre plusieurs services", "www.senda.com","amandis.jpg","sqli"));
	
		employe em = new employe();
		em.setGender("femme"); em.setEntreprise(e2); em.setNomSociete("amandis");em.setUsername("soumia95");em.setPassword("123456");
		employe eme = new employe();
		eme.setGender("femme"); eme.setEntreprise(e1); eme.setNomSociete("senda");eme.setUsername("soum");eme.setPassword("123456");
		usersRepository.save(new admin("khoulod95", "123456789", e1, "femme"));
		usersRepository.save(em);
		usersRepository.save(eme);
		usersRepository.save(new clients("said", "123", null , (long)063656541, "homme"));
		Categorie_services cat = categorieRepository.save(new Categorie_services("L'eau", "bon service",em));
	    serviceRepository.save(new Service("nadafa", "good service", e2, cat,em,"nadafa.jpg"));
	    reparationRepository.save(new Reparation("tetouan", "test", "nouveau reparation", em.getEntreprise(), em));*/
	}
}
/*La société Espace Propreté a été fondée en 1998 par des professionnels du nettoyage industriel à Montpellier. Elle garantit aujourd’hui une expérience éprouvée en la matière et vous fait bénéficier d’une expertise de plus de 15 ans en la matière.
*/
/*Spécialiste reconnu dans le domaine du nettoyage professionnel, « Winbest Nettoyage Casablanca » attache une grande importance à l’image de vos locaux.
Notre engagement de qualité se reflète dans l’image propre et sérieuse de vos locaux. Pour être toujours à votre écoute, nos agences de proximité sont à votre disposition à Casablanca et dans sa région. Pour des locaux à l’image de votre entreprise, un seul prestataire, Winbest Nettoyage Casablanca.
*/