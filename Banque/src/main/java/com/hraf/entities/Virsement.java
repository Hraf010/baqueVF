package com.hraf.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("V")
public class Virsement extends Operation{
    public Virsement() {
    }

    public Virsement(Date date, double montant, Compte compte) {
        super(date, montant, compte);
    }
}
