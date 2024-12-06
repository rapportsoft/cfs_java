package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Voyage;

import jakarta.transaction.Transactional;

public interface VoyageRepository extends JpaRepository<Voyage, String> {
	

	@Query(value="select NEW com.cwms.entities.Voyage(v.vesselCode, v.voyageNo, v.viaNo, v.gateOpenDate, v.berthDate,"
			+ "v.rotationNo, v.rotationNoDate, v1.vesselName) from Voyage v "
			+ "LEFT OUTER JOIN Vessel v1 ON v.companyId=v1.companyId and v.branchId=v1.branchId and v.vesselCode=v1.vesselId "
			+ "where v.companyId=:cid and v.branchId=:bid and v.status != 'D' and (:search is null OR :search = '' OR "
			+ "v.voyageNo LIKE CONCAT('%',:search,'%') OR v.viaNo LIKE CONCAT('%',:search,'%'))")
	List<Voyage> searchData(@Param("cid") String cid, 
            @Param("bid") String bid, 
            @Param("search") String search);
	
	
	
	

//	@Query(value = "select p.portName, p1.portName, v.vesselCode, v.voyageNo, v.viaNo, DATE_FORMAT(v.eta,'%d/%m/%Y'),"
//			+ "DATE_FORMAT(v.gateOpenDate,'%d/%m/%Y'),DATE_FORMAT(v.atb,'%d/%m/%Y'), DATE_FORMAT(v.atd,'%d/%m/%Y'), "
//			+ "DATE_FORMAT(v.cutOffDateTime,'%d/%m/%Y'), v.status, v.srNo from Voyage v LEFT JOIN Port p ON v.companyId=p.companyId and "
//			+ "v.branchId=p.branchId and v.pol = p.portCode LEFT JOIN Port p1 ON v.companyId=p1.companyId and "
//			+ "v.branchId=p1.branchId and v.pod = p1.portCode where v.companyId=:cid and v.branchId=:bid and v.status != 'D' "
//			+ "AND v.vesselCode=:code AND (:voyage is null OR :voyage = '' OR v.voyageNo like CONCAT('%', :voyage ,'%')) and p.status !='D' and p1.status !='D'")
//	List<Object[]> search(@Param("cid") String cid, @Param("bid") String bid, @Param("code") String code,
//			@Param("voyage") String voyage);
	
	@Query(value = "select p.portName, p1.portName, v.vesselCode, v.voyageNo, v.viaNo, DATE_FORMAT(v.eta,'%d/%m/%Y'),"
			+ "DATE_FORMAT(v.gateOpenDate,'%d/%m/%Y'),DATE_FORMAT(v.atb,'%d/%m/%Y'), DATE_FORMAT(v.atd,'%d/%m/%Y'), "
			+ "DATE_FORMAT(v.cutOffDateTime,'%d/%m/%Y'), v.status, v.srNo from Voyage v LEFT JOIN Port p ON v.companyId=p.companyId and "
			+ "v.branchId=p.branchId and v.pol = p.portCode LEFT JOIN Port p1 ON v.companyId=p1.companyId and "
			+ "v.branchId=p1.branchId and v.pod = p1.portCode where v.companyId=:cid and v.branchId=:bid and v.status != 'D' "
			+ "AND v.vesselCode=:code AND (:voyage is null OR :voyage = '' OR v.voyageNo like CONCAT('%', :voyage ,'%'))")
	List<Object[]> search(@Param("cid") String cid, @Param("bid") String bid, @Param("code") String code,
			@Param("voyage") String voyage);

	@Query(value = "select COUNT(v)>0 from Voyage v where v.companyId=:cid and v.branchId=:bid and v.vesselCode=:code "
			+ "and v.voyageNo =:voyage and v.status != 'D'")
	Boolean checkDuplicate(@Param("cid") String cid, @Param("bid") String bid, @Param("code") String code,
			@Param("voyage") String voyage);

	@Query(value = "select COUNT(v)>0 from Voyage v where v.companyId=:cid and v.branchId=:bid and v.vesselCode=:code "
			+ "and v.voyageNo =:voyage and v.srNo != :sr  and v.status != 'D'")
	Boolean checkDuplicate1(@Param("cid") String cid, @Param("bid") String bid, @Param("code") String code,
			@Param("voyage") String voyage,@Param("sr") int sr);

	@Query(value = "select v from Voyage v where v.companyId=:cid and v.branchId=:bid and v.vesselCode=:code and "
			+ "v.srNo = :sr and v.status != 'D'")
	Voyage getVoyageData(@Param("cid") String cid, @Param("bid") String bid,@Param("code") String code,@Param("sr") int sr);
	
	@Query(value = "select v from Voyage v where v.companyId=:cid and v.branchId=:bid and v.vesselCode=:code and "
			+ "v.voyageNo =:voyage and v.status != 'D'")
	Voyage getVoyageData1(@Param("cid") String cid, @Param("bid") String bid,@Param("code") String code,@Param("voyage") String sr);
	
	@Transactional
	@Modifying
	@Query("UPDATE Voyage v SET v.voyageNo = :voyage, v.pol = :pol, v.pod = :pod, v.viaNo = :via, v.eta = :eta, "
	     + "v.gateOpenDate = :gateDate, v.igmNo = :igm, v.igmDate = :igmDate, v.rotationNo = :rotationNo, v.rotationNoDate = :rotationDate, "
	     + "v.atb = :atb, v.viaNoDate = :viaNoDate, v.atd = :atd, v.cutOffDateTime = :cutOffDateTime, v.editedBy=:edit, v.editedDate=:edate "
	     + "WHERE v.companyId = :cid AND v.branchId = :bid AND v.vesselCode = :code AND v.srNo = :sr AND v.status != 'D'")
	int updateRecord(@Param("cid") String cid, 
	                 @Param("bid") String bid, 
	                 @Param("code") String code, 
	                 @Param("sr") int sr, 
	                 @Param("voyage") String voyage, 
	                 @Param("pol") String pol, 
	                 @Param("pod") String pod, 
	                 @Param("via") String via, 
	                 @Param("eta") Date eta, 
	                 @Param("gateDate") Date gateDate, 
	                 @Param("igm") String igm, 
	                 @Param("igmDate") Date igmDate, 
	                 @Param("rotationNo") String rotationNo, 
	                 @Param("rotationDate") Date rotationDate, 
	                 @Param("atb") Date atb, 
	                 @Param("viaNoDate") Date viaNoDate, 
	                 @Param("atd") Date atd, 
	                 @Param("cutOffDateTime") Date cutOffDateTime,
	                 @Param("edit") String edit,
	                 @Param("edate") Date edate);

}
