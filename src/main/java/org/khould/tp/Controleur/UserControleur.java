package org.khould.tp.Controleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.khould.tp.Service.AdminService;
import org.khould.tp.Service.ClientService;
import org.khould.tp.Service.EmployeService;
import org.khould.tp.entities.Categorie_services;
import org.khould.tp.entities.Email;
import org.khould.tp.entities.Entreprise;
import org.khould.tp.entities.Reparation;
import org.khould.tp.entities.Service;
import org.khould.tp.entities.employe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/Employe")
public class UserControleur {
	@Value("${dir.Entreprise}")
    private String Entreprise;
	@Value("${dir.Service}")
    private String Service;
	 @Autowired
	 private AdminService adminService;
	
	 @Autowired 
	 public EmployeService employeService;
	 @Autowired 
	 public ClientService clientService;
	 public static Boolean isConnected = false;
	 
	 @RequestMapping(value="/Entrepise")
	 public String CreationEntreprise() {
		 return "EntrepriseForm";
	 }
	 @RequestMapping(value="/login")
	 public String login() {
		 return "login";
	 }    
	 @RequestMapping(value="/index")
    public String index(Model model) {
		 List<Entreprise> allentreprise = employeService.aLLEntreprise();
		 List<Entreprise> lastEntreprise = new ArrayList<Entreprise>();
			for (int i=allentreprise.size()-3;i<allentreprise.size();i++) {
			  lastEntreprise.add(allentreprise.get(i));	
			   System.out.println("lastEntreprise["+i+"]"+allentreprise.get(i));
			}
		 this.adminService.incrementSiteVisits();
		 model.addAttribute("isConnected", EmployeService.isConnected);
		 model.addAttribute("currentUser", employeService.getCurrentUsers());
		 model.addAttribute("lastEntreprise", lastEntreprise);
		 model.addAttribute("adminService", adminService.adminService());
		 return "index";
	 }   
	  @RequestMapping(value="/SendEmail")
	   public String SendEmail( Model md) {
		 md.addAttribute("isConnected", EmployeService.isConnected);
	     md.addAttribute("currentUser", employeService.getCurrentUsers());
		 return "sendEmail";
	 }
	  @RequestMapping(value="/Send")
	   public String Send(@RequestParam("subject")  String subject, @RequestParam("message")  String message, Model md) {
		  if(EmployeService.isConnected) {
				 Email  email = new Email();
				 email.setObjet(subject);
				 email.setMessage(message);
				 this.employeService.sendEmail(email);
				 return  "redirect:/Employe/SendEmail";
		  }else return  "redirect:/Employe/login";
	 }
	  @RequestMapping(value="/checkLogin", method=RequestMethod.POST)
	  public String checkLogin(@RequestParam("username") String username , @RequestParam("password") String password,Model model) {
				Integer userTypeIndex = this.employeService.userExist(username,password);
				System.out.println(employeService.userExist(username,password));
				if(userTypeIndex==1) {
					model.addAttribute("isConnected", EmployeService.isConnected);
					System.out.println(EmployeService.isConnected);
					return  "redirect:/Employe/index";
				} 
				else if(userTypeIndex==2) {
					model.addAttribute("isConnected", ClientService.isConnected);
					System.out.println(ClientService.isConnected);
					return  "redirect:/Client/index";
				} 
				else if(userTypeIndex==3) {
					model.addAttribute("isConnected", AdminService.isConnected);
					return  "redirect:/Admin/index";
				} 
				else return "login";   
			}
	   @RequestMapping(value="/ServiceContent", method=RequestMethod.GET)
	   public String ServiceContent(Model model,Long id ) {
		
		  Service srv = this.clientService.getService(id);
		  model.addAttribute("Service",srv);
		  model.addAttribute("isConnected", EmployeService.isConnected);
		  model.addAttribute("currentUser", employeService.getCurrentUsers());
		 return "serviceContent";
		}
	 @RequestMapping(value="/Reparation")
	 public String Reparation(Model model ,@RequestParam(name="motCle",defaultValue="") String motCle,
		      @RequestParam(name="page",defaultValue="0") int page ,
		      @RequestParam(name="size",defaultValue="4") int size)  {
		 model.addAttribute("isConnected", EmployeService.isConnected);
		 List<Reparation> rep =employeService.listReparation(motCle);
		//System.out.println(rep.getContent().toArray());
		model.addAttribute("listReparation", rep);
		model.addAttribute("currentUser", employeService.getCurrentUsers());
		 return "reparation";
	 }
	 @RequestMapping(value="/editReparation")
	   public String edit(Long id , Model md) {
		md.addAttribute("isConnected", EmployeService.isConnected);
	    md.addAttribute("Reparation", this.employeService.getReparation(id))	;
	    md.addAttribute("currentUser", employeService.getCurrentUsers());
		 return "reparationEpdate";
	 }
	 @RequestMapping(value="/formUpdateReparation")
	 public String formUpdateReparation(Reparation rp,Model model) {
    	if(EmployeService.isConnected) {
		 this.employeService.addReparation(rp);
		 model.addAttribute("currentUser", employeService.getCurrentUsers());
		 return "redirect:/Employe/Reparation";
		 }
    	else return  "redirect:/Employe/login";
	 }
		@RequestMapping(value="/Supprimer")
	    public String Supprimer(Long id,Model model) {
		employeService.deletReparation(id);
   	    return "redirect:/Employe/Reparation";
		   }
	/* @RequestMapping(value="/ViewContent", method=RequestMethod.GET)
	   public String viewContent(Model model,Long id ) {
		  model.addAttribute("Rep",  this.employeService.getReparation(id));
		 return "redirect:/Employe/Reparation";
		}*/
	    @RequestMapping(value="/logout", method=RequestMethod.GET)
		public String logout(Model model) {	
	    	model.addAttribute("isConnected",  EmployeService.isConnected=false);
			return  "redirect:/Employe/index";
		}
	    @RequestMapping("/EntrepriseAdd")
		public String EntrepriseAdd(@RequestParam("website") String website,@RequestParam("nomSociete")  String nomSociete,
		@RequestParam("description") String description,@RequestParam("telephone") String telephone,
		@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("passwordConf") String passwordConf,
		@RequestParam("gender") String gender,@RequestParam("adress") String adress,@RequestParam(name="EntrepriseImage")MultipartFile tu,
		@RequestParam("mail") String mail,Model model) throws IllegalStateException, IOException {
			 if(password.equals(passwordConf)) {
		     Entreprise e = new Entreprise(mail, gender, adress, username, password, telephone, description, website);
		     if (!(tu.isEmpty())) {e.setPhoto(tu.getOriginalFilename());}
		     e.setNomSociate(nomSociete);
			 this.employeService.entrepriseCreation(e);
			 employe emp = new employe();
			 emp.setEntreprise(e);
			 emp.setGender(gender);
			 emp.setUsername(username);
			 emp.setPassword(password);
			 emp.setNomSociete(nomSociete);
			 emp.setWebsite(website);
			 this.employeService.employeInscription(emp);
			 if (!(tu.isEmpty())) {
				 e.setPhoto(tu.getOriginalFilename());
				tu.transferTo(new File(Entreprise+e.getCode()));
			}
			 return "redirect:/Employe/login";
			 }
			 else return "redirect:/Employe/Entrepise";
			
		}       
	 @RequestMapping(value="/getphoto",produces=MediaType.IMAGE_JPEG_VALUE)
	    @ResponseBody
	    public byte[] getphoto(Long id) throws FileNotFoundException, IOException {
		 File i = new File(Entreprise+id);
		 return IOUtils.toByteArray(new FileInputStream(i));
		 
	 }
	    @RequestMapping(value="/formSaveReparation")
		 public String formSaveReparation(@RequestParam("titre") String titre , @RequestParam("description") String description, 
				 @RequestParam("city") String city,Model model) {
	    	if(EmployeService.isConnected) {
			Reparation rp = new Reparation();
			rp.setCity(city);
			rp.setDesciption(description);
			rp.setTitre(titre);
			this.employeService.addReparation(rp);
			 return "redirect:/Employe/Reparation";}
	    	else return  "redirect:/Employe/login";
		 }
	    @RequestMapping(value="/Service")
		 public String service(Model model,@RequestParam(name="motCle",defaultValue="") String motCle,
			      @RequestParam(name="page",defaultValue="0") int page ,
			      @RequestParam(name="size",defaultValue="4") int size) {
			 model.addAttribute("isConnected", EmployeService.isConnected);
			 List<Categorie_services> rep =employeService.listCategorie(motCle);   
			 model.addAttribute("listCategorie", rep);
			 model.addAttribute("listALLCategorie", employeService.listALLCategorie());
			 List<Service> ser = this.employeService.ServiceAll(motCle);
			 model.addAttribute("listService", ser);
			 model.addAttribute("currentUser", employeService.getCurrentUsers());
			 return "service";
		 }
	   
	    @RequestMapping(value="/formSaveCategore")
		 public String formSaveCategore(@RequestParam("name") String titre , @RequestParam("description") String description, 
				Model model) {
	    	if(EmployeService.isConnected) {
			Categorie_services cat = new Categorie_services();
			cat.setName(titre);
			cat.setDescription(description);
			this.employeService.addcategorie(cat);
			 return "redirect:/Employe/Service";}
	    	else return  "redirect:/Employe/login";
		 }
	    @RequestMapping(value="/formSaveService")
		 public String formSaveService(@RequestParam("titre") String titre , @RequestParam("description") String description, 
				 @RequestParam(name="serviceImage")MultipartFile tu,
				 @RequestParam("id_categorie") Long id_categorie,Model model) throws IllegalStateException, IOException {
	    	if(EmployeService.isConnected) {
			Service src = new Service();
			  if (!(tu.isEmpty())) {src.setPhoto(tu.getOriginalFilename());}
			src.setDescription(description);
			src.setTitre(titre);
			this.employeService.addService(src, id_categorie);
			 if (!(tu.isEmpty())) {
				 src.setPhoto(tu.getOriginalFilename());
				tu.transferTo(new File(Service+src.getId()));
			}
			 return "redirect:/Employe/Service";}
	    	else return  "redirect:/Employe/login";
		 }
	    @RequestMapping(value="/getphotoService",produces=MediaType.IMAGE_JPEG_VALUE)
	    @ResponseBody
	    public byte[] getphotoService(Long id) throws FileNotFoundException, IOException {
		 File i = new File(Service+id);
		 return IOUtils.toByteArray(new FileInputStream(i));
		 
	 }
	    @RequestMapping(value="/supprimerService")
	    public String supprimerService(Long id,Model model) {
		employeService.deletService(id);
   	    return "redirect:/Employe/Service";
		   }
	    @RequestMapping(value="/editService")
		   public String editService(Long id , Model md) {
			 md.addAttribute("isConnected", EmployeService.isConnected);
		     md.addAttribute("Service", this.employeService.getService(id))	;
		     md.addAttribute("listALLCategorie", employeService.listALLCategorie());
		     md.addAttribute("currentUser", employeService.getCurrentUsers());
			 return "serviceUpdate";
		 }
	  /*  @RequestMapping(value="/supprimerCatService")     
	    public String supprimerCatService(Long id,Model model) {
		employeService.deletCatService(id);
   	    return "redirect:/Employe/Service";
		   }*/   
		 @RequestMapping(value="/formUpdateService")
		 public String formUpdateService(Service rp,Model model,@RequestParam("id_categorie") Long id_categorie, @RequestParam(name="serviceImage")MultipartFile tu) throws IllegalStateException, IOException {
	    	if(EmployeService.isConnected) {
	    		if (!(tu.isEmpty())) {rp.setPhoto(tu.getOriginalFilename());}
			 this.employeService.addService(rp, id_categorie);
			 if (!(tu.isEmpty())) {
				 rp.setPhoto(tu.getOriginalFilename());
				 tu.transferTo(new File(Service+rp.getId()));
			}
			 return "redirect:/Employe/Service";
			 }
	    	else return  "redirect:/Employe/login";
		 }
		 @RequestMapping(value="/allEntreprise")
		   public String allEntrerpise(Long id , Model md) {
			 md.addAttribute("isConnected", EmployeService.isConnected);
			 md.addAttribute("currentUser", employeService.getCurrentUsers()); 
			 List<Entreprise>  et =employeService.aLLEntreprise();
		     md.addAttribute("allEntreprise", et);
			 return "allEntreprise";
		 }
		 @RequestMapping(value="/viewContentEntreprise", method=RequestMethod.GET)
		   public String viewContentEntreprise(Model model,Long id ) {
			  Entreprise societe = this.employeService.getEntreprise(id);
			  List<Service> src = this.employeService.serviceEntreprise(societe);
			  model.addAttribute("societe",  societe);
			  model.addAttribute("serviceEntreprise",  src);
			  model.addAttribute("listAllService", employeService.listAllService());
			  model.addAttribute("isConnected", EmployeService.isConnected);
			  this.adminService.incrementEntrpriseViews();
			  model.addAttribute("currentUser", employeService.getCurrentUsers());
			 return "detailEntreprise";
			}
		   @RequestMapping(value="/clientDemande")
		   public String clientDemnade(Long id , Model md) {
			 md.addAttribute("listDemande", employeService.allDemande());
			 md.addAttribute("isConnected", EmployeService.isConnected);
			 md.addAttribute("currentUser", employeService.getCurrentUsers());
			 return "clientDemande";
		 }
		    @RequestMapping(value="/ValiderDemande")
			public String validCommande( Long id) {
				this.employeService.validDemande(id);
				return "redirect:/Employe/clientDemande";
			}
			@RequestMapping(value="/rejetDemande")
			public String rejectCommande(Long id) {
				this.employeService.rejectDemande(id);
				return "redirect:/Employe/clientDemande";
			}
			@RequestMapping(value="/supprimerDemande")
		    public String supprimerDemande(Long id,Model model) {
				this.employeService.deletDemande(id);
	  	    return "redirect:/Employe/clientDemande";
			   }
}
