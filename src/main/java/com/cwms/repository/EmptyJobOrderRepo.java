package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.EmptyJobOrder;

public interface EmptyJobOrderRepo extends JpaRepository<EmptyJobOrder, String> {

	@Query(value="select e from EmptyJobOrder e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.jobTransId=:job and e.erpDocRefNo=:erp and e.docRefNo=:doc and e.srNo=:sr")
	EmptyJobOrder getSingleData(@Param("cid") String cid,@Param("bid") String bid,@Param("job") String job,@Param("erp") String erp,
			@Param("doc") String doc,@Param("sr") int sr);
	
	@Query(value="select e from EmptyJobOrder e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.jobTransId=:job and e.srNo=:sr")
	EmptyJobOrder getSingleData1(@Param("cid") String cid,@Param("bid") String bid,@Param("job") String job,@Param("sr") int sr);
	
	@Query(value="select e.jobTransId,e.jobTransDate,e.sl,p1.partyName,e.onAccountOf,p2.partyName,e.bookingNo,e.shipper,p3.partyName,"
			+ "e.cha,p4.partyName,e.doNo,e.doDate,e.doValidityDate,e.toLocation,e.profitcentreId,e.containerHealth,e.containerType,"
			+ "e.containerNo,e.containerSize,e.containerType,e.iso,e.docRefDate,e.sa,p5.partyName,e.fromLocation,e.gateInId,"
			+ "e.gateInDate,e.tareWt,e.erpDocRefNo,e.docRefNo,e.srNo, e.movementType from EmptyJobOrder e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shipper=p3.partyId "
			+ "LEFT OUTER JOIN Party p4 ON e.companyId=p4.companyId and e.branchId=p4.branchId and e.cha=p4.partyId "
			+ "LEFT OUTER JOIN Party p5 ON e.companyId=p5.companyId and e.branchId=p5.branchId and e.sa=p5.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.jobTransId=:job")
	List<Object[]> getData(@Param("cid") String cid,@Param("bid") String bid,@Param("job") String job);
	
	@Query(value="select distinct e.jobTransId,DATE_FORMAT(e.jobTransDate,'%d %M %y'),e.containerNo,p1.partyName,p2.partyName,e.bookingNo,e.doNo "
			+ "from EmptyJobOrder e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.cha=p2.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(:val is null OR :val = '' OR e.jobTransId LIKE CONCAT ('%',:val,'%') "
			+ "OR e.bookingNo LIKE CONCAT ('%',:val,'%') OR e.doNo LIKE CONCAT ('%',:val,'%') "
			+ "OR p1.partyName LIKE CONCAT ('%',:val,'%') OR e.containerNo LIKE CONCAT ('%',:val,'%') OR p2.partyName LIKE CONCAT ('%',:val,'%')) order by e.jobTransId desc")
	List<Object[]> search(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select COUNT(e)>0 from EmptyJobOrder e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.bookingNo=:bookingNo")
	Boolean isExistBookingNo(@Param("cid") String cid,@Param("bid") String bid,@Param("bookingNo") String bookingNo);
	
	@Query(value="select COUNT(e)>0 from EmptyJobOrder e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
			+ "and e.bookingNo=:bookingNo and e.jobTransId != :job")
	Boolean isExistBookingNo1(@Param("cid") String cid,@Param("bid") String bid,@Param("bookingNo") String bookingNo,@Param("job") String job);
	
	@Query(value="select distinct e.jobTransId, e.bookingNo from EmptyJobOrder e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
			+ "and (e.gatePassId is null OR e.gatePassId = '') and (:val is null OR :val = '' OR e.bookingNo LIKE CONCAT ('%',:val,'%'))")
	List<Object[]> getDataForGatePass(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select e.jobTransId,e.jobTransDate,e.sl,p1.partyName,e.onAccountOf,p2.partyName,e.bookingNo,e.shipper,p3.partyName,"
			+ "e.cha,p4.partyName,e.doNo,e.doDate,e.doValidityDate,e.toLocation,e.profitcentreId,e.containerHealth,e.containerType,"
			+ "e.containerNo,e.containerSize,e.containerType,e.iso,e.docRefDate,e.sa,p5.partyName,e.fromLocation,e.gateInId,"
			+ "e.gateInDate,e.tareWt,e.erpDocRefNo,e.docRefNo,e.srNo, e.movementType from EmptyJobOrder e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shipper=p3.partyId "
			+ "LEFT OUTER JOIN Party p4 ON e.companyId=p4.companyId and e.branchId=p4.branchId and e.cha=p4.partyId "
			+ "LEFT OUTER JOIN Party p5 ON e.companyId=p5.companyId and e.branchId=p5.branchId and e.sa=p5.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.jobTransId=:job and (e.gatePassId = '' OR e.gatePassId is null)")
	List<Object[]> getSelectedDataForGatePass(@Param("cid") String cid,@Param("bid") String bid,@Param("job") String val);
}
