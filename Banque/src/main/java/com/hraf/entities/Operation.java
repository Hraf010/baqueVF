package com.hraf.entities;

import javax.persistence.*;
import java.util.Date;

@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Op_type",discriminatorType = DiscriminatorType.STRING , length = 1)
@Entity
public class Operation {
    @Id
    @GeneratedValue
    private long num;
    private Date date;
    private double montant;
    @ManyToOne(cascade = { CascadeType.MERGE , CascadeType.DETACH , CascadeType.REFRESH})
    @JoinColumn(name = "com_id")
    private Compte compte;

    public Operation() {
    }

    public Operation(Date date, double montant , Compte compte) {
        this.date = date;
        this.montant = montant;
        this.compte = compte;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
