package org.khould.tp.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.khould.tp.dao.CategorieRepository;
import org.khould.tp.dao.DemandeRepository;
import org.khould.tp.dao.EmailRepository;
import org.khould.tp.dao.EntrepriseRepository;
import org.khould.tp.dao.ReparationRepository;
import org.khould.tp.dao.ServiceRepository;
import org.khould.tp.dao.UsersRepository;
import org.khould.tp.entities.Categorie_services;
import org.khould.tp.entities.Email;
import org.khould.tp.entities.Entreprise;
import org.khould.tp.entities.Reparation;
import org.khould.tp.entities.Users;
import org.khould.tp.entities.demandes;
import org.khould.tp.entities.employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeService {
	public static Users userEmploye = new employe();
	public static String userName;
	public static Categorie_services serviceCat = new Categorie_services();
	public static Boolean isConnected = false ;
    @Autowired
	public UsersRepository usersRepository;
    @Autowired
   	public  EmailRepository emailRepository;
    @Autowired
    public EntrepriseRepository entrepriseRepository;
    @Autowired
    public ReparationRepository reparationRepository;
    @Autowired
    public ServiceRepository serviceRepository;
    @Autowired
    public  CategorieRepository categorieRepository;
    @Autowired
    public  DemandeRepository demandeRepository;
   
    @Transactional
    public Entreprise entrepriseCreation(Entreprise e) {
    	return entrepriseRepository.save(e);
    	 
    }
  /*  @Transactional
    public List<Entreprise> aLLEntreprise(String motCle) {
    	 List<Entreprise> et =entrepriseRepository.findAll();
    	List<Entreprise> Entreprise = new ArrayList<Entreprise>();
		for (Entreprise etr : et) {
		  if(etr.getNomSociate().equals(motCle)) {
			  Entreprise.add(etr);
		  }
		}
    	return Entreprise;
    	 
    }*/
    @Transactional
    public List<Entreprise> aLLEntreprise() {
    	 List<Entreprise> et =entrepriseRepository.findAll();
    	 return et;
    	 
    }
    @Transactional
    public Entreprise getEntreprise(Long id) {
    	return entrepriseRepository.getOne(id);
    }
    @Transactional
    public List<org.khould.tp.entities.Service> serviceEntreprise(Entreprise eb) {
    return	serviceRepository.findByEntreprises(eb);
    }
    @Transactional
    public Users employeInscription (employe user) {      
    	return  usersRepository.save(user);
    }
    @Transactional
	public Integer userExist(String username , String password) {
		Users user = this.usersRepository.findUserByEmailAndPassword(username, password);
		if(user.getClass().getSimpleName().equals("employe")){
			      isConnected = true;
			      userEmploye = user;
			      userName=  user.getUsername();
			return 1;  
		}
		else if (user.getClass().getSimpleName().equals("clients") ) {
			ClientService.isConnected = true;
			ClientService.userClient = user;
			 userName=  user.getUsername();
			return 2;
		}
		else if (user.getClass().getSimpleName().equals("admin") ) {
			AdminService.isConnected = true;
			AdminService.userAdmin = user;
			 userName=  user.getUsername();
			return 3;
		}
		else {
			return 0;
			}
	}
    public Users getCurrentUsers() {
    	
    		return   userEmploye ; 
	      
	 }
    @Transactional
    public Reparation addReparation(Reparation rp) {   
    	rp.setUser(userEmploye);
    	rp.setEntreprise(userEmploye.getEntreprise());
    	return  reparationRepository.save(rp);
    }
    @Transactional
    public  List<Reparation> listReparation(String motCle) {   
    	 List<Reparation>  lE = reparationRepository.findByTitre("%"+motCle+"%");
    	 List<Reparation> employeReparation = new ArrayList<Reparation>();
 		for (Reparation rep : lE) {	
 			if(rep.getUser().getUsername().equals(userEmploye.getUsername())) {
 				employeReparation.add(rep);
 			}
 		}
 		
    	 return  employeReparation ;
    }
    @Transactional
    public Reparation getReparation(Long id) {
    	Reparation rp = reparationRepository.getOne(id);
    	return  rp;
    }
    @Transactional
    public void deletReparation(Long id) {
    	 reparationRepository.deleteById(id);
    }
    @Transactional
    public Categorie_services addcategorie(Categorie_services cat) {
    	cat.setUser(userEmploye);
    	serviceCat =categorieRepository.save(cat);
     return	serviceCat;
    }
    @Transactional
    public org.khould.tp.entities.Service addService(org.khould.tp.entities.Service rp,Long idCat) {   
    	rp.setUser(userEmploye);
    	rp.setEntreprises(userEmploye.getEntreprise());
    	Categorie_services cat = categorieRepository.getOne(idCat);
    	rp.setCategorie_services(cat);
    	return  serviceRepository.save(rp);
    }
    @Transactional
    public  List<Categorie_services> listCategorie(String motCle) {   
    	 List<Categorie_services>  lE =categorieRepository.findByName("%"+motCle+"%");
    	 List<Categorie_services> employeReparation = new ArrayList<Categorie_services>();
  		for (Categorie_services rep : lE) {	
  			if(rep.getUser().getUsername().equals(userEmploye.getUsername())) {
  				employeReparation.add(rep);
  			}
  			}
    	 return  employeReparation;
    
  		}
    @Transactional
    public  List<Categorie_services> listALLCategorie() {   
    	 List<Categorie_services>  lE =categorieRepository.findAll();
    	 List<Categorie_services> catadmin = new ArrayList<Categorie_services>();
    	 for (Categorie_services rep : lE) {	
	  			if(rep.getUser().getUsername().equals(userEmploye.getUsername())) {
	  				catadmin.add(rep);
	  			}
	  			}
    	 return  catadmin ;
    }
    @Transactional
    public  List<org.khould.tp.entities.Service> listAllService() {   
    	 List<org.khould.tp.entities.Service>  lE =serviceRepository.findAll();
    	 return  lE ;
    }
    @Transactional
    public  List<org.khould.tp.entities.Service> ServiceAll(String motCle) {   
    	 List<org.khould.tp.entities.Service>  lE = serviceRepository.findByTitre("%"+motCle+"%");
    	 List<org.khould.tp.entities.Service> employeService = new ArrayList<org.khould.tp.entities.Service>();
 		for (org.khould.tp.entities.Service rep : lE) {	
 			if(rep.getUser().getUsername().equals(userEmploye.getUsername())) {
 				employeService.add(rep);
 			}
 		}
    	 return  employeService ;
    }
    @Transactional
    public void deletService(Long id) {
    	 serviceRepository.deleteById(id);
    }
    /*@Transactional
    public void deletCatService(Long id) {
    	 categorieRepository.deleteById(id);
    }*/
    @Transactional
    public org.khould.tp.entities.Service getService(Long id) {
    	return  serviceRepository.getOne(id);
    }
    @Transactional
    public List<demandes> allDemande() {
    	List<demandes>  lE = demandeRepository.findAll();
    	 List<demandes> demande = new ArrayList<demandes>();
    	 for (demandes demandes : lE) {
			if(demandes.getService().getEntreprises().getNomSociate().equals(userEmploye.getEntreprise().getNomSociate())) {
				demande.add(demandes);
			}
		}
    	return demande;
    }
    @Transactional
	public void validDemande(Long demandeID) {
		this.demandeRepository.getOne(demandeID).setStatus(true);
		
	}
	@Transactional
	public void rejectDemande(Long demandeID) {
		this.demandeRepository.getOne(demandeID).setStatus(false);
	}
	@Transactional
	public Email sendEmail(Email send) {
		send.setEmail(userEmploye.getUsername());
		send.setUser(userEmploye);
		return  this.emailRepository.save(send);
		
	}

		@Transactional
	    public void deletDemande(Long id) {
	    	demandeRepository.deleteById(id);
	 
	}
}
