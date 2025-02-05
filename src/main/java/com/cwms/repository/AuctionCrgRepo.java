package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cwms.entities.AuctionDetail;

public interface AuctionCrgRepo extends JpaRepository<AuctionDetail, String>{

}
