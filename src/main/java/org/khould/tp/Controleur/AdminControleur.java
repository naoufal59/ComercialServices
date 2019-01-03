package org.khould.tp.Controleur;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.io.IOUtils;
import org.khould.tp.Service.AdminService;
import org.khould.tp.Service.EmployeService;
import org.khould.tp.Service.mailsender;
import org.khould.tp.entities.Categorie_services;
import org.khould.tp.entities.Reparation;
import org.khould.tp.entities.Service;
import org.khould.tp.entities.StatisticsDto;
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
@RequestMapping(value="/Admin")
public class AdminControleur {
	@Value("${dir.Entreprise}")
    private String Entreprise;
	@Value("${dir.Service}")
    private String Service;
	 @Autowired
	 private mailsender ls;
	@Autowired
	private AdminService adminService;
	 @RequestMapping(value="/index")
	 public String index(Model model) {
		 StatisticsDto statisticsDto = this.adminService.getStatistics();
		 model.addAttribute("admin", adminService.getCurrentUsers());
		 model.addAttribute("listDemande", adminService.allDemande());
		 model.addAttribute("stats",statisticsDto);
		 return "indexAdmin";
	 }
	 @RequestMapping(value="/inbox")
	 public String inbox(Model model) {
		 model.addAttribute("email", adminService.aLLEmail());
		 model.addAttribute("admin", adminService.getCurrentUsers());
		 
		 return "inbox";
	 }
	 @RequestMapping(value="/inboxEmail")
	 public String inboxEmail(Model model) {
		
		 model.addAttribute("admin", adminService.getCurrentUsers());
		 
		 return "inboxEmail";
	 }
	 @RequestMapping(value="/emailClient")
	 public String emailClient(Model model,@RequestParam("Subject")  String subject, @RequestParam("Message")  String message,
			 @RequestParam("to")  String to ) throws MessagingException {
		
		 model.addAttribute("admin", adminService.getCurrentUsers());
		 this.ls.send(to, subject, message);
		 return "redirect:/Admin/inboxEmail";
	 }
	  @RequestMapping(value="/ValiderDemande")
		public String validCommande( Long id) {
			this.adminService.validDemande(id);
			return "redirect:/Admin/index";
		}
		@RequestMapping(value="/rejetDemande")
		public String rejectCommande(Long id) {
			this.adminService.rejectDemande(id);
			return "redirect:/Admin/index";
		}
		 @RequestMapping(value="/logout", method=RequestMethod.GET)
			public String logout(Model model) {	
		    	model.addAttribute("isConnected",  AdminService.isConnected=false);
				return  "redirect:/Employe/login";
			}
		 
		 @RequestMapping(value="/getphoto",produces=MediaType.IMAGE_JPEG_VALUE)
		    @ResponseBody
		    public byte[] getphoto(Long id) throws FileNotFoundException, IOException {
			 File i = new File(Entreprise+id);
			 return IOUtils.toByteArray(new FileInputStream(i));
			 
		 }
		 @RequestMapping(value="/allEntrprise")
		 public String allEntrprise(Model model) {
			 model.addAttribute("isConnected", AdminService.isConnected);
			 model.addAttribute("admin", adminService.getCurrentUsers());
			 model.addAttribute("allEntreprise", adminService.aLLEntreprise());
			 return "adminAllEntrprise";
		 }     
		 @RequestMapping(value="/allClient")
		 public String allClient(Model model) {
			 model.addAttribute("isConnected", AdminService.isConnected);
			 model.addAttribute("admin", adminService.getCurrentUsers());
			 model.addAttribute("allClient", adminService.aLLCleint());
			 return "allClient";
		 }
		        
		 @RequestMapping(value="/Service")
		 public String service(Model model,@RequestParam(name="motCle",defaultValue="") String motCle,
			      @RequestParam(name="page",defaultValue="0") int page ,
			      @RequestParam(name="size",defaultValue="4") int size) {
			 model.addAttribute("isConnected", EmployeService.isConnected);
			 List<Categorie_services> rep =adminService.listCategorie(motCle);   
			 model.addAttribute("listCategorie", rep);
			 model.addAttribute("listALLCategorie", adminService.listALLCategorie());
			 List<Service> ser = this.adminService.ServiceAll(motCle);
			 model.addAttribute("listService", ser);
			 model.addAttribute("admin", adminService.getCurrentUsers());
			 return "serviceAdmin";
		 }
	    @RequestMapping(value="/formSaveCategore")
		 public String formSaveCategore(@RequestParam("name") String titre , @RequestParam("description") String description, 
				Model model) {
	    	if(AdminService.isConnected) {
			Categorie_services cat = new Categorie_services();
			cat.setName(titre);
			cat.setDescription(description);
			this.adminService.addcategorie(cat);
			 return "redirect:/Admin/Service";}
	    	else return  "redirect:/Employe/login";
		 }
	    @RequestMapping(value="/formSaveService")
			 public String formSaveService(@RequestParam("titre") String titre , @RequestParam("description") String description, 
					 @RequestParam(name="serviceImage")MultipartFile tu,
					 @RequestParam("id_categorie") Long id_categorie,Model model) throws IllegalStateException, IOException {
		    	if(AdminService.isConnected) {
				Service src = new Service();
				  if (!(tu.isEmpty())) {src.setPhoto(tu.getOriginalFilename());}
				src.setDescription(description);
				src.setTitre(titre);
				this.adminService.addService(src, id_categorie);
				 if (!(tu.isEmpty())) {
					 src.setPhoto(tu.getOriginalFilename());
					tu.transferTo(new File(Service+src.getId()));
				}
				 return "redirect:/Admin/Service";}
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
		adminService.deletService(id);
   	    return "redirect:/Admin/Service";
		   }
	    @RequestMapping(value="/supprimerCatService")     
	    public String supprimerCatService(Long id,Model model) {
	     adminService.deletCatService(id);
   	    return "redirect:/Admin/Service";
		   }
	   /* @RequestMapping(value="/supprimerEntrprise")     
	    public String supprimerEntrprise(Long id,Model model) {
	     adminService.deletCatEntrprise(id);
   	    return "redirect:/Admin/allEntrprise";
		   }*/
	    
	    @RequestMapping(value="/Reparation")
		 public String reparationAdmin(Model model ,@RequestParam(name="motCle",defaultValue="") String motCle,
			      @RequestParam(name="page",defaultValue="0") int page ,
			      @RequestParam(name="size",defaultValue="4") int size)  {
	    		model.addAttribute("isConnected", AdminService.isConnected);
			 	List<Reparation> rep =adminService.listReparation(motCle);
				model.addAttribute("listReparation", rep);
				model.addAttribute("admin", adminService.getCurrentUsers());
			 return "raparationAdmin";
		 }
	     @RequestMapping(value="/formSaveReparation")
		 public String formSaveReparation(@RequestParam("titre") String titre , @RequestParam("description") String description, 
				 @RequestParam("city") String city,Model model) {
	    	if(AdminService.isConnected) {
			Reparation rp = new Reparation();
			rp.setCity(city);
			rp.setDesciption(description);
			rp.setTitre(titre);
			this.adminService.addReparation(rp);
			 return "redirect:/Admin/Reparation";}
	    	else return  "redirect:/Employe/login";
		 }
	     @RequestMapping(value="/Supprimer")
		    public String Supprimer(Long id,Model model) {
	    	 adminService.deletReparation(id);
	   	    return "redirect:/Admin/Reparation";
			   }
}
