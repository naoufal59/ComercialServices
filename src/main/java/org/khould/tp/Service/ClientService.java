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
import org.khould.tp.entities.Email;
import org.khould.tp.entities.Entreprise;
import org.khould.tp.entities.Reparation;
import org.khould.tp.entities.Users;
import org.khould.tp.entities.clients;
import org.khould.tp.entities.demandes;
import org.khould.tp.entities.employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
			public static boolean isConnected = false;
			public static Users userClient ;
		    @Autowired
			public UsersRepository usersRepository;
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
		    @Autowired
		   	public  EmailRepository emailRepository;
	    @Transactional
	    public List<Entreprise> aLLEntreprise() {
	    	List<Entreprise> ety         = entrepriseRepository.findAll();
			List<Entreprise> entreprises = new ArrayList<>();
			for (Entreprise entreprise : ety) {
				if(!entreprise.getNomSociate().equals("amandis")) {
					entreprises.add(entreprise);
				}
			}
			return entreprises;
	    	 
	    }
	    @Transactional
	    public Users clientInscription (clients user) {      
	    	return  usersRepository.save(user);
	    }
	    public Users getCurrentUsers() {
	    	
    		return   userClient ; 
	      
	 }
	    @Transactional
	    public Entreprise getEntreprise(Long id) {
	    	return entrepriseRepository.getOne(id);
	    }
	    @Transactional
	    public org.khould.tp.entities.Service getService(Long id) {
	    	return serviceRepository.getOne(id);
	    }
	    @Transactional
	    public List<org.khould.tp.entities.Service> serviceEntreprise(Entreprise eb) {
	    return	serviceRepository.findByEntreprises(eb);
	    }
	    @Transactional
	    public  List<org.khould.tp.entities.Service> listAllService() {   
	    	 List<org.khould.tp.entities.Service>  lE =serviceRepository.findAll();
	    	 return  lE ;
	    }
	    @Transactional
	    public demandes addDemandes(demandes rp,Long idService) {   
	    	rp.setStatus(false);
	    	rp.setUser(ClientService.userClient);
	    	org.khould.tp.entities.Service service = serviceRepository.getOne(idService);
	    	rp.setService(service);
	    	return  demandeRepository.save(rp);
	    }
	   
	    @Transactional
	    public List<demandes> allDemande() {
	    	List<demandes>  lE = demandeRepository.findAll();
	    	 List<demandes> cLientDEmandea = new ArrayList<demandes>();
	 		for (demandes rep : lE) {	
	 			if(rep.getUser().getUsername().equals(ClientService.userClient.getUsername())) {
	 				cLientDEmandea.add(rep);
	 			}
	 		}
	 		
	    	return cLientDEmandea;
	    }
	    @Transactional
	    public void deletDemande(Long id) {
	    	demandeRepository.deleteById(id);
	    }
	    @Transactional
	    public demandes getDemande(Long id) {
	    	return  demandeRepository.getOne(id);
	    }
	    @Transactional
		public Email sendEmail(Email send) {
			send.setEmail(userClient.getUsername());
			send.setUser(userClient);
			return  this.emailRepository.save(send);
			
		}
	    
}
