package com.hraf.metier;

import com.hraf.dao.CompteRepository;
import com.hraf.dao.OperationRepository;
import com.hraf.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Override
    public Compte consulterCompte(Long id) {
        return compteRepository.getOne(id);
    }

    @Override
    public void verser(Long id, double montant) {
       Compte compte = compteRepository.getOne(id);
       Operation operation = new Virsement(new Date(),montant,compte);
       operationRepository.save(operation);
       compte.setSolde(compte.getSolde()+montant);
       compteRepository.save(compte);
    }

    @Override
    public void retirer(Long id, double montant) {
        Compte compte = compteRepository.getOne(id);
        double extramoney = 0;
        if(compte instanceof CompteCourant)
            extramoney = ((CompteCourant)compte).getDecouvert();

        if(montant > compte.getSolde()+extramoney)
            throw new RuntimeException("Solde insuffisant");
        Operation operation = new Retrait(new Date(), montant , compte);
        operationRepository.save(operation);
        compte.setSolde(compte.getSolde()- montant);
        compteRepository.save(compte);
    }

    @Override
    public void virement(Long id, Long id2, double montant) {
      verser(id2,montant);
      retirer(id,montant);
    }

    @Override
    public Page<Operation> listOperation(Long id , int page , int size) {
        Compte compte = consulterCompte(id);
        System.out.println(compte.getCode());
        return operationRepository.findCompte(id , new PageRequest(page, size));
    }
}
