package com.cwms.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ExportBackToTown;

public interface ExportBackToTownRepo extends JpaRepository<ExportBackToTown, String> {

	
	@Query("SELECT E.backToTownTransId " +
		       "FROM ExportBackToTown E " +
		       "WHERE E.companyId = :companyId AND E.branchId = :branchId " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.sbTransId = :sbTransId " +
		       "AND E.sbNo = :sbNo " +
		       "AND E.status <> 'D' " +
		       "ORDER BY E.createdDate DESC")
	List<String> getDataForExportMainSearchBackToTown(
		        @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("profitcentreId") String profitcentreId,
		        @Param("sbTransId") String sbTransId,
		        @Param("sbNo") String sbNo,
		        Pageable pageable);
	
	
	@Query(value="select e from ExportBackToTown e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.backToTownTransId=:id and e.sbNo=:sb and e.sbTransId=:trans")
	ExportBackToTown getDataByIdAndSbNoAndSbTrans(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
			@Param("sb") String sb,@Param("trans") String trans);
	
	
	
	@Query(value="select NEW com.cwms.entities.ExportBackToTown(e.backToTownTransId, e.backToTownLineId, e.backToTownTransDate,"
			+ "e.sbTransId, e.sbNo, e.sbDate, e.sbTransDate, e.numberOfMarks, sb.exporterName,"
			+ "e.onAccountOf, e.requestReferenceNo, e.requestReferenceDate, e.actualNoOfPackages,"
			+ "e.backToTownPackages, e.balancePackages, e.grossWeight,"
			+ "e.backToTownWeight, e.commodity, e.status, e.createdBy, e.typeOfContainer,"
			+ "CONCAT(sb.exporterAddress1,' ',sb.exporterAddress2,' ',sb.exporterAddress3), p.partyName) "
			+ "from ExportBackToTown e "
			+ "LEFT OUTER JOIN ExportSbEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo and e.sbTransId=sb.sbTransId "
			+ "LEFT OUTER JOIN Party p ON sb.companyId=p.companyId and sb.branchId=p.branchId and sb.onAccountOf=p.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.backToTownTransId=:id and e.sbNo=:sb and e.sbTransId=:trans")
	ExportBackToTown getDataByIdAndSbNoAndSbTrans1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
			@Param("sb") String sb,@Param("trans") String trans);
	
	
	@Query(value="select e.backToTownTransId,DATE_FORMAT(e.backToTownTransDate,'%d %M %y'),e.sbNo,e.sbTransId,e.requestReferenceNo "
			+ "from ExportBackToTown e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(:val is null OR :val = '' OR e.backToTownTransId LIKE CONCAT('%',:val,'%') OR e.sbNo LIKE CONCAT('%',:val,'%') OR "
			+ "e.sbTransId LIKE CONCAT('%',:val,'%') OR e.requestReferenceNo LIKE CONCAT('%',:val,'%')) order by e.backToTownTransId desc")
	List<Object[]> searchBacktotownData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select e.backToTownTransId,e.sbNo,e.sbTransId from ExportBackToTown e where e.companyId=:cid and "
			+ "e.branchId=:bid and e.status != 'D' and (e.gatePassId is null OR e.gatePassId = '') and "
			+ "(:val is null OR :val = '' OR e.sbNo LIKE CONCAT('%',:val,'%'))")
	List<Object[]> getDataForGatePass(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select NEW com.cwms.entities.ExportBackToTown(e.backToTownTransId, e.sbTransId, e.sbNo, e.sbDate, p.partyName,"
			+ "e.backToTownPackages, e.commodity) "
			+ "from ExportBackToTown e "
			+ "LEFT OUTER JOIN Party p ON e.companyId=p.companyId and e.branchId=p.branchId and e.importerId=p.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.backToTownTransId=:id and e.sbNo=:sb and e.sbTransId=:trans")
	ExportBackToTown getSingleDataForGatePass(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
			@Param("sb") String sb,@Param("trans") String trans);
}
