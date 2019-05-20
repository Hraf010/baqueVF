package com.hraf.controllers;

import com.hraf.dao.ClientRepository;
import com.hraf.dao.CompteRepository;
import com.hraf.entities.*;
import com.hraf.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
public class BanqueController {
    @Autowired
    private IBanqueMetier banqueMetier;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompteRepository compteRepository;
    @RequestMapping("/operations")
    public String getOperations(){
        return "operations";
    }



    @RequestMapping("/consultercompte")
    public String ConsulterCompte(Model model, @RequestParam("codeCompte") String code , @RequestParam (value = "page",defaultValue = "0") int page){
        System.out.println(code);
        Long c = Long.parseLong(code);
        System.out.println(c);
        Compte compte = banqueMetier.consulterCompte(c);
        model.addAttribute("compte",compte);

        Page<Operation> operations = banqueMetier.listOperation(c,page,2);
        model.addAttribute("operations",operations);

        int[] pages = new int[operations.getTotalPages()];
        for(int i = 0; i<operations.getTotalPages();i++){
            pages[i] = i;
        }

        model.addAttribute("pages",pages);
        return "operations";
    }

    //*********************Client Controller *******************************************

    @GetMapping("/getClientForm")
    public String getClinetForm(Model model){
        model.addAttribute("client",new Client());
        model.addAttribute("compteEpargne",new CompteEpargne());
        model.addAttribute("compteCourant", new CompteCourant());
        return "clientform";
    }
    @PostMapping("/saveClient")
    public void SaveClient(Model model ,long solde, @ModelAttribute Client c , @ModelAttribute CompteCourant compteCourant , @ModelAttribute CompteEpargne compteEpargne , HttpServletResponse response){
        System.out.println(c.getCode());
        System.out.println(c.getNom());
        System.out.println(solde);
        if(compteEpargne.getTaux()>0){
            compteEpargne.setDateCreation(new Date());
            compteEpargne.setSolde(solde);
            clientRepository.save(c);
            compteRepository.save(new CompteEpargne(compteEpargne.getSolde(),compteEpargne.getDateCreation(),c,compteEpargne.getTaux()));
        }
        else{
            System.out.println("********************************");
            compteCourant.setSolde(solde);
            compteCourant.setDateCreation(new Date());
            clientRepository.save(c);
            compteRepository.save(new CompteCourant(compteCourant.getSolde(),compteCourant.getDateCreation(),c,compteCourant.getDecouvert()));
        }

        try {
            response.sendRedirect("/clients");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/saveOperation")
    public String saveOperation(Model model, String codeCompte , double montant , String typeOperation , String codeCompte2){
        Long code = Long.parseLong(codeCompte);
        if(typeOperation.equals("VERS")){
           // Long code2 = Long.parseLong(codeCompte2);
            banqueMetier.verser(code,montant);
        }
        else if(typeOperation.equals("RETR")){
            banqueMetier.retirer(code, montant);
        }
        else{
            Long code2 = Long.parseLong(codeCompte2);
            banqueMetier.virement(code,code2,montant);
        }
        return "redirect:/consultercompte?codeCompte="+codeCompte;
    }
    @GetMapping("/clients")
    public String getClients(Model model){
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients",clients);
        return "clients";
    }

    @PostMapping("/deleteClient")
    public void  deleteClient(Model model , @RequestParam("code") long code , HttpServletResponse response) throws IOException{
        Client client = clientRepository.getOne(code);
        System.out.println(client);
        for(Compte compte : client.getComptes()){
            compteRepository.delete(compte);
            System.out.println("1");
        }



        clientRepository.delete(client);


        response.sendRedirect("/clients");
    }


    //********************************* Comptes Controller *****************************************
    @GetMapping("/comptes")
    public String getComptes(Model model,@RequestParam(value = "page",defaultValue = "0") int page ){
        Page<Compte> comptes = compteRepository.findAll(PageRequest.of(page,8));
        int[] pages = new int[comptes.getTotalPages()];
        for(int i = 0; i<comptes.getTotalPages();i++){
            pages[i] = i;
        }
        model.addAttribute("comptes",comptes);
        model.addAttribute("pages",pages);
        return "comptes";
    }



    @GetMapping("/comptesClient")
    public String getComptesClient(Model model , @RequestParam (value = "page",defaultValue = "0") int page , @RequestParam(value = "code") long code){
        Page<Compte> comptesPage = compteRepository.chercherComptes(PageRequest.of(page,4), code);
        Client client = clientRepository.getOne(code);
        model.addAttribute("client",client);
        int[] pages = new int[comptesPage.getTotalPages()];
        for(int i = 0 ; i< comptesPage.getTotalPages();i++)
            pages[i] = i;

        model.addAttribute("comptesPage",comptesPage);
        model.addAttribute("code", code);
        model.addAttribute("pageCourant",page);
        model.addAttribute("pages",pages);
        return "comptesClient";
    }

    @GetMapping("/compteForm")
    public String getCompteForm(Model model,@RequestParam("code") long code ){
        Client client = clientRepository.getOne(code);
        model.addAttribute("client",client);
        model.addAttribute("compteCourant",new CompteCourant());
        model.addAttribute("compteEpargne",new CompteEpargne());
        return  "compteForm";
    }

    @PostMapping("/saveCompte")
    public void SaveCompte(Model model ,long solde ,@RequestParam("code") long code, @ModelAttribute CompteCourant compteCourant , @ModelAttribute CompteEpargne compteEpargne , HttpServletResponse response) throws IOException {
        Client c = clientRepository.getOne(code);
        System.out.println(c.getNom() + " " +c.getCode());
        if(compteEpargne.getTaux()>0){
            compteEpargne.setDateCreation(new Date());
            compteEpargne.setSolde(solde);
            compteRepository.save(new CompteEpargne(compteEpargne.getSolde(),compteEpargne.getDateCreation(),c,compteEpargne.getTaux()));
        }
        else{
            System.out.println("********************************");
            compteCourant.setSolde(solde);
            compteCourant.setDateCreation(new Date());
            compteRepository.save(new CompteCourant(compteCourant.getSolde(),compteCourant.getDateCreation(),c,compteCourant.getDecouvert()));
        }

        try {
            response.sendRedirect("/comptes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/deleteForm")
    public void DeleteCompte(Model model , @RequestParam("code") long code , HttpServletResponse response ){
        Compte compte  = compteRepository.getOne(code);
        compteRepository.delete(compte);
        long codeClient = compte.getClient().getCode();
        try {
            response.sendRedirect("/comptes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //*************************************Sec Controller*********************************
    @GetMapping("/")
    public String home(){
        return "redirect:/operations";
    }

    @GetMapping("/403")
    public String get403(){
        return "403";
    }




}
