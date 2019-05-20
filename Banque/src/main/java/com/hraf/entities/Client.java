package com.hraf.entities;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "code_cli")
    private long code;
    @Column(name = "nom_cli")
    private String nom ;
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "client", cascade = {CascadeType.PERSIST , CascadeType.MERGE , CascadeType.DETACH , CascadeType.REFRESH})
    private List<Compte> comptes;

    public Client() {
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public void AddCompte(Compte compte){
        comptes.add(compte);
    }
    public Client(String nom) {
        this.nom = nom;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getNbrComptes(){
        return comptes.size();
    }
    public long getTotalSolde(){
        long total = 0;
        for(Compte C : comptes)
            total+=C.getSolde();

        return total;
    }
}
