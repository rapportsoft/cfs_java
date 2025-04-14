package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.EmptyInventory;

public interface EmptyInventoryRepo extends JpaRepository<EmptyInventory, String> {

	

	@Query(value="select e from EmptyInventory e where e.companyId=:cid and e.branchId=:bid and e.erpDocRefNo=:erp and e.docRefNo=:doc and "
	        + "e.subDocRefNo=:sub and e.gateInId=:gatein and e.containerNo=:cont and e.status != 'D'")
	EmptyInventory getById(@Param("cid") String cid, 
	                       @Param("bid") String bid, 
	                       @Param("erp") String erp, 
	                       @Param("doc") String doc, 
	                       @Param("sub") String sub, 
	                       @Param("gatein") String gatein, 
	                       @Param("cont") String cont);
	
	@Query(value="select e from EmptyInventory e where e.companyId=:cid and e.branchId=:bid and e.erpDocRefNo=:erp and e.docRefNo=:doc and "
	        + "e.gateInId=:gatein and e.containerNo=:cont and e.status != 'D'")
	EmptyInventory getById2(@Param("cid") String cid, 
	                       @Param("bid") String bid, 
	                       @Param("erp") String erp, 
	                       @Param("doc") String doc, 
	                       @Param("gatein") String gatein, 
	                       @Param("cont") String cont);
	
	
	
	@Query(value="select e from EmptyInventory e where e.companyId=:cid and e.branchId=:bid and e.erpDocRefNo=:erp and e.docRefNo=:doc and "
	        + "e.gateInId=:gatein and e.containerNo=:cont and e.status != 'D' and (e.emptyJobOrder = '' OR e.emptyJobOrder is null)")
	EmptyInventory getById1(@Param("cid") String cid, 
	                       @Param("bid") String bid, 
	                       @Param("erp") String erp, 
	                       @Param("doc") String doc, 
	                       @Param("gatein") String gatein, 
	                       @Param("cont") String cont);
	
	

	
   @Query(value="select e.containerNo,e.containerSize,e.containerType,e.isoCode,e.erpDocRefNo,e.docRefNo,p.partyName,e.gateInId,e.gateInDate,"
   		+ "crg.destination,e.deStuffId,cn.containerStatus,iso.tareWeight,cn.igmDate,e.deStuffId,e.movementCode from EmptyInventory e "
   		+ "LEFT OUTER JOIN Destuff cn ON e.companyId=cn.companyId and e.branchId=cn.branchId and "
   		+ "e.erpDocRefNo=cn.igmTransId  and e.deStuffId=cn.deStuffId "
   		+ "LEFT OUTER JOIN Party p ON e.companyId=p.companyId and e.branchId=p.branchId and e.sa = p.partyId "
   		+ "LEFT OUTER JOIN IsoContainer iso ON e.companyId=iso.companyId and e.isoCode = iso.isoCode "
   		+ "LEFT OUTER JOIN Cfigmcrg crg ON e.companyId=crg.companyId and e.branchId=crg.branchId and e.erpDocRefNo=crg.igmTransId "
   		+ "and e.docRefNo=crg.igmNo and e.subDocRefNo=crg.igmLineNo "
   		+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
   		+ "(e.emptyJobOrder = '' OR e.emptyJobOrder is null) and (:val is null OR :val = '' OR e.sl = :val OR e.cha = :val)")	
   List<Object[]> getInventoryData(@Param("cid") String cid, 
           @Param("bid") String bid, 
           @Param("val") String val);

}
