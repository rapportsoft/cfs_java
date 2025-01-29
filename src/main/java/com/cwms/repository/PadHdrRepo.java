package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.Pdahdr;

public interface PadHdrRepo extends JpaRepository<Pdahdr, String> {

	@Query(value="select p.transDate,ROUND(p.closingBal,2),ROUND(p.creditBal,2) "
			+ "from Pdahdr p "
			+ "where p.companyId=:cid and p.branchId=:bid and p.partyId=:id and p.status = 'A' and "
			+ "Date(p.transDate) = (select MAX(p1.transDate) from Pdahdr p1 where p1.companyId=:cid and p1.branchId=:bid and p1.partyId=:id and p1.status='A')")
	Object checkOldDayData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select p.transDate,ROUND(p.closingBal,2),ROUND(p.creditBal,2) "
			+ "from Pdahdr p "
			+ "where p.companyId=:cid and p.branchId=:bid and p.partyId=:id and p.status = 'A' and "
			+ "Date(p.transDate) =:date")
	Object checkCurrentDayData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("date") Date date);
	
	@Modifying
	@Transactional
	@Query(value="Update Pdahdr p SET p.creditBal=:bal where p.companyId=:cid and p.branchId=:bid and p.status='A' and "
			+ "p.partyId=:id and Date(p.transDate) =:date")
	int updateData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("date") Date date,
			@Param("bal") BigDecimal bal);
}
