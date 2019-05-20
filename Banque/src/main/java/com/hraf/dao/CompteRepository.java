package com.hraf.dao;

import com.hraf.entities.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompteRepository extends JpaRepository<Compte,Long> {
    @Query("select c from Compte c where c.client.code = :x")
    public Page<Compte> chercherComptes(Pageable pageable , @Param("x") long code );
}
