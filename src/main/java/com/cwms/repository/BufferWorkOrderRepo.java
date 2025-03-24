package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.BufferWorkOrder;

public interface BufferWorkOrderRepo extends JpaRepository<BufferWorkOrder, String> {

	@Query(value="select COUNT(e.containerNo)>0 from ExportInventory e where e.companyId=:cid and e.branchId=:bid and e.status='A' and "
			+ "e.containerNo=:con and (e.gateOutId is not null and e.gateOutId != '')")
	boolean checkContainerNoAlreadyInInventory(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
	
	@Query(value="select b.woNo,b.woDate,b.cha,p1.partyName,b.onAccountOf,p2.partyName,b.status,b.createdBy,b.shippingLine,p3.partyName,"
			+ "b.shipper,b.bookingNo,b.moveFrom,b.containerStatus,b.containerNo,b.iso,b.containerSize,b.containerType,b.weight,"
			+ "b.commodity,b.movementType "
			+ "from BufferWorkOrder b "
			+ "LEFT OUTER JOIN Party p1 ON b.companyId=p1.companyId and b.branchId=p1.branchId and b.cha=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON b.companyId=p2.companyId and b.branchId=p2.branchId and b.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON b.companyId=p3.companyId and b.branchId=p3.branchId and b.shippingLine=p3.partyId "
			+ "where b.companyId=:cid and b.branchId=:bid and b.woNo=:wo and b.status='A'")
	List<Object[]> getDataByWoNo(@Param("cid") String cid,@Param("bid") String bid,@Param("wo") String wo);
	
	
	@Query(value="select b.woNo,DATE_FORMAT(b.woDate,'%d/%m/%Y %H:%i'),p1.partyName,b.bookingNo,b.containerNo "
			+ "from BufferWorkOrder b "
			+ "LEFT OUTER JOIN Party p1 ON b.companyId=p1.companyId and b.branchId=p1.branchId and b.onAccountOf=p1.partyId "
			+ "where b.companyId=:cid and b.branchId=:bid and b.status='A' and (:val is null OR :val = '' OR b.woNo LIKE "
			+ "CONCAT(:val,'%') OR p1.partyName LIKE CONCAT(:val,'%') OR b.bookingNo LIKE CONCAT(:val,'%') OR "
			+ "b.containerNo LIKE CONCAT(:val,'%')) order by b.woDate desc")
	List<Object[]> searchData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select b.woNo,b.containerNo "
			+ "from BufferWorkOrder b "
			+ "where b.companyId=:cid and b.branchId=:bid and b.status='A' and (:val is null OR :val = '' OR b.containerNo LIKE "
			+ "CONCAT(:val,'%')) and (b.gateInId is null OR b.gateInId = '')")
	List<Object[]> searchContainer(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select b.woNo,b.woDate,b.cha,p1.partyName,b.onAccountOf,p2.partyName,b.status,b.createdBy,b.shippingLine,p3.partyName,"
			+ "b.shipper,b.bookingNo,b.moveFrom,b.containerStatus,b.containerNo,b.iso,b.containerSize,b.containerType,b.weight,"
			+ "b.commodity,b.movementType "
			+ "from BufferWorkOrder b "
			+ "LEFT OUTER JOIN Party p1 ON b.companyId=p1.companyId and b.branchId=p1.branchId and b.cha=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON b.companyId=p2.companyId and b.branchId=p2.branchId and b.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON b.companyId=p3.companyId and b.branchId=p3.branchId and b.shippingLine=p3.partyId "
			+ "where b.companyId=:cid and b.branchId=:bid and b.woNo=:wo and b.status='A' and b.containerNo=:con")
	Object getDataByWoNoAndContainerNo(@Param("cid") String cid,@Param("bid") String bid,@Param("wo") String wo,
			@Param("con") String containerNo);
	
	@Transactional
	@Modifying
	@Query(value="Update BufferWorkOrder b SET b.gateInId=:id "
			+ "where b.companyId=:cid and b.branchId=:bid and b.woNo=:wo and b.status='A' and b.containerNo=:con")
	int updateGateInId(@Param("cid") String cid,@Param("bid") String bid,@Param("wo") String wo,
			@Param("con") String containerNo,@Param("id") String id);
}
