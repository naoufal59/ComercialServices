package org.khould.tp.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.khould.tp.dao.CategorieRepository;
import org.khould.tp.dao.DemandeRepository;
import org.khould.tp.dao.EmailRepository;
import org.khould.tp.dao.EntrepriseRepository;
import org.khould.tp.dao.ReparationRepository;
import org.khould.tp.dao.ServiceRepository;
import org.khould.tp.dao.StatisticsDataRepository;
import org.khould.tp.dao.UsersRepository;
import org.khould.tp.entities.Categorie_services;
import org.khould.tp.entities.Email;
import org.khould.tp.entities.Entreprise;
import org.khould.tp.entities.Reparation;
import org.khould.tp.entities.StatisticsData;
import org.khould.tp.entities.StatisticsDto;
import org.khould.tp.entities.Users;
import org.khould.tp.entities.demandes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	public static boolean isConnected = false;
	public static Users userAdmin ;
	public static Categorie_services serviceCat = new Categorie_services();
    @Autowired
	public  EmailRepository emailRepository;   
	@Autowired
	public UsersRepository userRep ;
	@Autowired
	public StatisticsDataRepository statsRep;
	@Autowired
	public EntrepriseRepository entrepriseRepository;
    @Autowired
    public  DemandeRepository demandeRepository;
    @Autowired
    public ServiceRepository serviceRepository;
    @Autowired
    public  CategorieRepository categorieRepository;
    @Autowired
    public ReparationRepository reparationRepository;
	 public Users getCurrentUsers() {
 		return   userAdmin ; 
	 }
	 
	 @Transactional
	    public List<demandes> allDemande() {
	    	List<demandes>  lE = demandeRepository.findAll();
	    	 List<demandes> cLientDEmandea = new ArrayList<demandes>();
	 		for (demandes rep : lE) {	
	 			if(rep.getService().getEntreprises().getNomSociate().equals("amendis")) {
	 				cLientDEmandea.add(rep);
	 			}
	 		}
	 		
	    	return cLientDEmandea;
	    }
	 
	@Transactional
	public StatisticsDto getStatistics() {
		StatisticsDto stats = new StatisticsDto();
		StatisticsData statsData = this.statsRep.getOne((long) 1);
		stats.entrpriseview = statsData.getAnnounceVisitCount();
		stats.visitsCount = statsData.getVisitsCount();
		stats.numberOfClients = (long) userRep.findAll().size() - 1; 
		return stats;
	}
	@Transactional
	public void incrementSiteVisits(){
		StatisticsData stat = this.statsRep.findAll().get(0);
		long websiteisitCount = stat.getVisitsCount();
		stat.setVisitsCount(websiteisitCount + 1);
		this.statsRep.save(stat);
	}
	@Transactional
	public void incrementEntrpriseViews(){
		StatisticsData stat = this.statsRep.findAll().get(0);
		long announceVisitCount = stat.getAnnounceVisitCount();
		stat.setAnnounceVisitCount(announceVisitCount + 1 );
		this.statsRep.save(stat);
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
		public List<Entreprise> aLLEntreprise() {
			List<Entreprise> ety         = entrepriseRepository.findAll();
			List<Entreprise> entreprises = new ArrayList<>();
			for (Entreprise entreprise : ety) {
				if(!entreprise.getUsername().equals(userAdmin.getUsername())) {
					entreprises.add(entreprise);
				}
			}
			return entreprises;
		}

		public List<Users> aLLCleint() {
			List<Users>  lE = userRep.findAll();
	    	 List<Users> client = new ArrayList<Users>();
	 		for (Users rep : lE) {	
	 			if(rep.getClass().getSimpleName().equals("clients")) {
	 				client.add(rep);
	 			}
	 		}
	 		
	    	return client;
		}
		@Transactional
		public List<Email> aLLEmail() {
			
			return emailRepository.findAll();
		}
		  @Transactional
		    public Categorie_services addcategorie(Categorie_services cat) {
		    	cat.setUser(userAdmin);
		    	serviceCat =categorieRepository.save(cat);
		     return	serviceCat;
		    }
		    @Transactional
		    public org.khould.tp.entities.Service addService(org.khould.tp.entities.Service rp,Long idCat) {   
		    	rp.setUser(userAdmin);
		    	rp.setEntreprises(userAdmin.getEntreprise());
		    	Categorie_services cat = categorieRepository.getOne(idCat);
		    	rp.setCategorie_services(cat);
		    	return  serviceRepository.save(rp);
		    }
		    
		    
		    @Transactional
		    public  List<Categorie_services> listCategorie(String motCle) {   
		    	 List<Categorie_services>  lE =categorieRepository.findByName("%"+motCle+"%");
		    	 List<Categorie_services> employeReparation = new ArrayList<Categorie_services>();
		  		for (Categorie_services rep : lE) {	
		  			if(rep.getUser().getUsername().equals(userAdmin.getUsername())) {
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
			  			if(rep.getUser().getUsername().equals(userAdmin.getUsername())) {
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
		 			if(rep.getUser().getUsername().equals(userAdmin.getUsername())) {
		 				employeService.add(rep);
		 			}
		 		}    
		    	 return  employeService ;
		    }
		    @Transactional
		    public void deletService(Long id) {
		    	 serviceRepository.deleteById(id);
		    }
		    @Transactional
		    public void deletCatService(Long id) {
		    	 categorieRepository.deleteById(id);
		    }
		   /* @Transactional
		    public void deletCatEntrprise(Long id) {
		    	 Users user = userRep.getOne(id);
		    	user.getEntreprise().getCode()
		    }*/
		    @Transactional
		    public List<org.khould.tp.entities.Service> adminService() {
		    	 List<org.khould.tp.entities.Service>  lE = serviceRepository.findAll();
		    	 List<org.khould.tp.entities.Service> employeService = new ArrayList<org.khould.tp.entities.Service>();
		 		for (org.khould.tp.entities.Service rep : lE) {	
		 			if(rep.getUser().getClass().getSimpleName().equals("admin") ) {
		 				employeService.add(rep);
		 			}
		 		}    
		    	 return  employeService ;
		    }
		    
		    @Transactional
		    public  List<Reparation> listReparation(String motCle) {   
		    	 List<Reparation>  lE = reparationRepository.findByTitre("%"+motCle+"%");
		    	 List<Reparation> employeReparation = new ArrayList<Reparation>();
		 		for (Reparation rep : lE) {	
		 			if(rep.getUser().getUsername().equals(userAdmin.getUsername())) {
		 				employeReparation.add(rep);
		 			}
		 		}
		 		
		    	 return  employeReparation ;
		    }
		    
		    @Transactional
		    public Reparation addReparation(Reparation rp) {   
		    	rp.setUser(userAdmin);
		    	rp.setEntreprise(userAdmin.getEntreprise());
		    	return  reparationRepository.save(rp);
		    }
		    
		    @Transactional
		    public void deletReparation(Long id) {
		    	 reparationRepository.deleteById(id);
		    }
}
