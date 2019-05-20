package com.hraf;

import com.hraf.dao.ClientRepository;
import com.hraf.dao.CompteRepository;
import com.hraf.dao.OperationRepository;
import com.hraf.entities.*;
import com.hraf.metier.IBanqueMetier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext context =  SpringApplication.run(DemoApplication.class, args);
        CompteRepository compteRepository = context.getBean(CompteRepository.class);
        Compte C1 = compteRepository.getOne(4L);
//        clientRepository.save(new Client("Achraf"));
//        clientRepository.save(new Client("hamid"));
//        clientRepository.save(new Client("omar"));
        IBanqueMetier iBanqueMetier = context.getBean(IBanqueMetier.class);
//        operationRepository.save(new Retrait(new Date(),1000,C1));
//        operationRepository.save(new Virsement(new Date(),2000,C1));
       // iBanqueMetier.retirer(4L,2000);
        Compte compte = new CompteCourant();
        System.out.println(compte.getClass().getSimpleName());
        System.out.println(compte.getClass().getSimpleName().equals("CompteCourant"));




    }

}
