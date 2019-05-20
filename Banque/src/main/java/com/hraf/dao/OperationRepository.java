package com.hraf.dao;

import com.hraf.entities.Compte;
import com.hraf.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
   @Query("select o from Operation o where o.compte.code = :x")
   public Page<Operation> findCompte(@Param("x") Long code , Pageable pageable );

}
