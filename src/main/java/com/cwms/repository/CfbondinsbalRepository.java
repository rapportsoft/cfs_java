package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwms.entities.Cfbondinsbal;

@Repository
public interface CfbondinsbalRepository extends JpaRepository<Cfbondinsbal, String> {
    // You can add custom query methods here if needed
}

