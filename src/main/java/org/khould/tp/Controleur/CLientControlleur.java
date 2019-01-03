package org.khould.tp.Controleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.khould.tp.Service.AdminService;
import org.khould.tp.Service.ClientService;
import org.khould.tp.Service.EmployeService;
import org.khould.tp.entities.Email;
import org.khould.tp.entities.Entreprise;
import org.khould.tp.entities.Service;
import org.khould.tp.entities.clients;
import org.khould.tp.entities.demandes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping(value="/Client")
public class CLientControlleur {
	@Value("${dir.Service}")
    private String Service;
	 @Autowired
	 private AdminService adminService;
	@Value("${dir.Entreprise}")
    private String Entreprise;
	private static  Long idCurrent;
	 Service serviceAdmin;
	 private static  Long idService;
	 @Autowired 
	 public EmployeService employeService;
	 @Autowired 
	 public ClientService clientService;
	 @RequestMapping(value="/cLientInscription")
	 public String cLientInscription() {
		 return "ClientForm";
	 }
	 @RequestMapping(value="/index")
	 public String index(Model model) {
		 List<Entreprise> allentreprise = clientService.aLLEntreprise();
		 List<Entreprise> lastEntreprise = new ArrayList<Entreprise>();
			for (int i=allentreprise.size()-2;i<allentreprise.size();i++) {
			  lastEntreprise.add(allentreprise.get(i));	
			   System.out.println("lastEntreprise["+i+"]"+allentreprise.get(i));
			}
		 model.addAttribute("isConnected", ClientService.isConnected);
		 model.addAttribute("currentUser", clientService.getCurrentUsers());
		 model.addAttribute("lastEntreprise", lastEntreprise);
		 model.addAttribute("adminService", adminService.adminService());
		 return "indexClient";
	 }
	  @RequestMapping(value="/SendEmail")
	   public String SendEmail( Model md) {
		 md.addAttribute("isConnected", ClientService.isConnected);
	     md.addAttribute("currentUser", clientService.getCurrentUsers());
		 return "sendEmail";
	 }
	  @RequestMapping(value="/Send")
	   public String Send(@RequestParam("subject")  String subject, @RequestParam("message")  String message, Model md) {
		  if(ClientService.isConnected) {
				 Email  email = new Email();
				 email.setObjet(subject);
				 email.setMessage(message);
				 this.clientService.sendEmail(email);
				 return  "redirect:/Client/SendEmail";
		  }else return  "redirect:/Employe/login";
	 }
	   @RequestMapping(value="/logout", method=RequestMethod.GET)
		public String logout(Model model) {	
	    	model.addAttribute("isConnected",  ClientService.isConnected=false);
			return  "redirect:/Client/index";
		}
	   @RequestMapping(value="/allEntreprise")
	   public String allEntrerpise(Long id , Model md) {
	     md.addAttribute("isConnected", ClientService.isConnected);
		 md.addAttribute("currentUser", clientService.getCurrentUsers());
	     md.addAttribute("allEntreprise", clientService.aLLEntreprise());
		 return "allEntreprise";
	 }
	   @RequestMapping(value="/supprimerDemande")
	    public String supprimerDemande(Long id,Model model) {
		clientService.deletDemande(id);
  	    return "redirect:/Client/clientDemande";
		   }
	   @RequestMapping(value="/getphoto",produces=MediaType.IMAGE_JPEG_VALUE)
	    @ResponseBody
	    public byte[] getphoto(Long id) throws FileNotFoundException, IOException {
		 File i = new File(Entreprise+id);
		 return IOUtils.toByteArray(new FileInputStream(i));
		 
	 }
	   @RequestMapping(value="/viewContentEntreprise", method=RequestMethod.GET)
	   public String viewContentEntreprise(Model model,Long id ) {
		   idCurrent=id;
		  Entreprise societe = this.clientService.getEntreprise(id);
		  List<Service> src = this.clientService.serviceEntreprise(societe);
		  model.addAttribute("societe",  societe);
		  model.addAttribute("serviceEntreprise",  src);
		  model.addAttribute("listAllService", clientService.listAllService());
		  model.addAttribute("isConnected", ClientService.isConnected);
		  model.addAttribute("currentUser", clientService.getCurrentUsers());
		 return "detailEntreprise";
		}
	   @RequestMapping(value="/ServiceContent", method=RequestMethod.GET)
	   public String ServiceContent(Model model,Long id ) {
		   idCurrent=id;
		  Service srv = this.clientService.getService(id);
		  model.addAttribute("Service",srv);
		  model.addAttribute("isConnected", ClientService.isConnected);
		  model.addAttribute("currentUser", clientService.getCurrentUsers());
		 return "serviceContent";
		}
	       
	   			@RequestMapping("/DemandeAdd")
				public String demandeAdd(@RequestParam("nbHeureDay") double nbHeureDay,@RequestParam("nbEmployeDommande") double nbEmployeDommande,
				@RequestParam("instructions") String instructions,@RequestParam("demandeDate") String demandeDate,
				@RequestParam("plan") String plan,@RequestParam("id") Long id,@RequestParam("prix") double prix,
				Model model) {
				 if(ClientService.isConnected) {
					demandes dmd = new demandes();
					dmd.setDemandeDate(demandeDate);
					dmd.setInstructions(instructions);
					dmd.setNbHeureDay(nbHeureDay);
					dmd.setPlan(plan);
					dmd.setPrix(prix);
					dmd.setNbEmployeDommande(nbEmployeDommande);
					this.clientService.addDemandes(dmd, id);
					return "redirect:/Client/viewContentEntreprise?id="+idCurrent;	
				 }
				 else return "redirect:/Employe/login";
					
				}
	   		 @RequestMapping("/CLientAdd")
	 		public String EntrepriseAdd(@RequestParam("telephone") Long telephon,
	 		@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("passwordConf") String passwordConf,
	 		@RequestParam("gender") String gender,Model model) throws IllegalStateException, IOException {
	 			 if(password.equals(passwordConf)) {
	 			 clients emp = new clients();
	 			 emp.setTelephon(telephon);
	 			 emp.setGender(gender);
	 			 emp.setUsername(username);
	 			 emp.setPassword(password);
	 			
	 			 this.clientService.clientInscription(emp);
	 			 
	 			 return "redirect:/Employe/login";
	 			 }
	 			 else return "redirect:/Client/cLientInscription";
	 			
	 		}   
	   		@RequestMapping("/clientDemande")
	   		public String clientDemande(Model model) {
	           model.addAttribute("listDemande", clientService.allDemande());
	           model.addAttribute("isConnected", ClientService.isConnected);
	 		   model.addAttribute("currentUser", clientService.getCurrentUsers());
	   			return "vosDemande";
	   		}
	   		@RequestMapping("/pdfDemande")
	   		public @ResponseBody String pdfDemande(Model model,Long id,HttpServletRequest request, HttpServletResponse response) {
	   			if(ClientService.isConnected) {
	   			try{
	   	    		Document document = new Document();
	   	    		PdfWriter.getInstance(document, response.getOutputStream());
	   	    		document.open();
	   	    		   		
	   	    		Font font1 = new Font(Font.FontFamily.HELVETICA  , 25, Font.BOLD);
	   	    		Font font2 = new Font(Font.FontFamily.COURIER    , 18,
	   	    		Font.ITALIC | Font.UNDERLINE);
	   	    		Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);
	   	    		demandes demande = clientService.getDemande(id);
	   	    		Paragraph paragraph = new Paragraph(demande.getService().getEntreprises().getNomSociate()+" Service comercial",font1);
	   	    		paragraph.setSpacingAfter(25);
	   	            paragraph.setSpacingBefore(25);
	   	        
	   	            paragraph.setIndentationLeft(50);
	   	            paragraph.setIndentationRight(50);
	   	            document.add(paragraph);
	   	    		
	   	    		Paragraph paragraph2 = new Paragraph("Facture de la demande",font3);
	   	    		paragraph2.setSpacingAfter(25);
	   	            paragraph2.setSpacingBefore(25);
	   	            paragraph2.setIndentationLeft(50);
	   	            paragraph2.setIndentationRight(50);
	   	            document.add(paragraph2);
	   	            
	   	            
	   	            Paragraph pp = new Paragraph("Les informations de la demande",font2);  
	   	          
	   	            
	   	    		PdfPTable table1 = new PdfPTable(2); // 5 columns.

	   	    		table1.addCell(new PdfPCell(new Paragraph("code demandes")));
	   	    		table1.addCell(new PdfPCell(new Paragraph(""+demande.getDemandeId())));
	   	    		table1.addCell(new PdfPCell(new Paragraph("la service demande")));
	   	    		table1.addCell(new PdfPCell(new Paragraph(""+demande.getService().getTitre())));
	   	    		table1.addCell(new PdfPCell(new Paragraph("nom de socite")));
	   	    		table1.addCell(new PdfPCell(new Paragraph(demande.getService().getEntreprises().getNomSociate())));
	   	    		table1.addCell(new PdfPCell(new Paragraph("prix de demande")));
	   	    		table1.addCell(new PdfPCell(new Paragraph(""+demande.getPrix())));
	   	    		table1.addCell(new PdfPCell(new Paragraph("nombre employe demnade ")));
	   	    		table1.addCell(new PdfPCell(new Paragraph(""+demande.getNbEmployeDommande())));
	   	    		
	   	    		    		
	   	    		table1.setSpacingBefore(50);
	   	    		table1.setSpacingAfter(50);
	   	    		table1.setWidthPercentage(70);
	   	    		 
	   	    		/*Paragraph paragraph3 = new Paragraph("signture societe "+demande.getService().getEntreprises().getNomSociate(),font3);
	   	    		paragraph3.setSpacingAfter(25);
	   	    		paragraph3.setSpacingBefore(25);
	   	            paragraph3.setIndentationLeft(50);
	   	            paragraph3.setIndentationRight(50);
	   	            document.add(paragraph3);
	   	            
	   	            Paragraph paragraph4 = new Paragraph("signture client "+demande.getUser().getUsername(),font3);
	   	            paragraph4.setSpacingAfter(25);
	   	            paragraph4.setSpacingBefore(25);
	   	            paragraph4.setIndentationLeft(150);
	   	            paragraph4.setIndentationRight(150);
	   	            document.add(paragraph4);*/
	   	    		
	   	    		document.add(pp);
	   	    		document.add(new Paragraph(" "));
	   	    		document.add(table1);
	   	    		document.close();}
	   	    		catch(Exception e){} 
	   			}
	   			
	   			
	           model.addAttribute("isConnected", ClientService.isConnected);
	   			return "vosDemande";
	   		}
	   	    @RequestMapping(value="/getphotoService",produces=MediaType.IMAGE_JPEG_VALUE)
		    @ResponseBody
		    public byte[] getphotoService(Long id) throws FileNotFoundException, IOException {
			 File i = new File(Service+id);
			 return IOUtils.toByteArray(new FileInputStream(i));
			 
		 }
	   	 @RequestMapping(value="/demanderService")
		 public String demanderService(Long id , Model model) {
	   		 model.addAttribute("currentUser", clientService.getCurrentUsers());
	   		 model.addAttribute("isConnected", ClientService.isConnected);
	   		 serviceAdmin=clientService.getService(id);
	   		 idService=serviceAdmin.getId();
	   		 System.out.println(idService);
	   		 model.addAttribute("service", serviceAdmin);
			 return "demanderService";
		 }
	   	@RequestMapping("/demandeServiceAdmin")
		public String demandeServiceAdmin(@RequestParam("nbHeureDay") double nbHeureDay,@RequestParam("nbEmployeDommande") double nbEmployeDommande,
		@RequestParam("instructions") String instructions,@RequestParam("demandeDate") String demandeDate,
		@RequestParam("plan") String plan,@RequestParam("prix") double prix,
		Model model) {
		 if(ClientService.isConnected) {
			demandes dmd = new demandes();
			dmd.setDemandeDate(demandeDate);
			dmd.setInstructions(instructions);
			dmd.setNbHeureDay(nbHeureDay);
			dmd.setPlan(plan);
			dmd.setPrix(prix);
			dmd.setNbEmployeDommande(nbEmployeDommande);
			this.clientService.addDemandes(dmd, idService);
			return "redirect:/Client/clientDemande";	
		 }
		 else return "redirect:/Employe/login";
			
		}
}
