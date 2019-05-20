package com.hraf.metier;

import com.hraf.entities.Compte;
import com.hraf.entities.Operation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBanqueMetier {
    public Compte consulterCompte(Long id);
    public void  verser(Long id , double montant);
    public void  retirer(Long id , double montant);
    public void  virement(Long id , Long id2 , double montant);
    public Page<Operation> listOperation(Long id, int page , int size);

}
