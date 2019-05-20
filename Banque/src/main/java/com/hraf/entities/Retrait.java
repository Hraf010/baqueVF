package com.hraf.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("R")
public class Retrait extends Operation {
    public Retrait() {
    }

    public Retrait(Date date, double montant, Compte compte) {
        super(date, montant, compte);
    }
}
