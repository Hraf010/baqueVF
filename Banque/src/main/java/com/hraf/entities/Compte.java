package com.hraf.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="comptes")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name="TYPE_CPTE",
        discriminatorType= DiscriminatorType.STRING,length=2)
public class Compte implements Serializable {
    @Id
    @GeneratedValue
    private Long code;
    private double solde;
    private java.util.Date dateCreation;
    @ManyToOne(cascade = {CascadeType.MERGE , CascadeType.DETACH , CascadeType.REFRESH})
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "compte" , fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST , CascadeType.MERGE , CascadeType.DETACH , CascadeType.REFRESH})
    private List<Operation> operations;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public Compte() {
    }

    public Compte(double solde, Date dateCreation , Client client) {
        this.solde = solde;
        this.dateCreation = dateCreation;
        this.client = client;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public java.util.Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(java.util.Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
