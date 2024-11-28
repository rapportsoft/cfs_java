package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.EquipmentActivity;

import jakarta.transaction.Transactional;

public interface EquipmentActivityRepository extends JpaRepository<EquipmentActivity, String> {

	@Query("SELECT E FROM EquipmentActivity E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitCenterId = :profitcentreId " 
	        + "AND E.erpDocRefNo = :erpDocRefNo "
	        + "AND E.docRefNo = :docRefNo "
	        + "AND E.deStuffId = :deStuffId "	
	        + "AND E.status <> 'D'")
	List<EquipmentActivity> getAllEquipmentTransfer(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("profitcentreId") String profitcentreId,
	                              @Param("erpDocRefNo") String erpDocRefNo, @Param("docRefNo") String docRefNo, @Param("deStuffId") String deStuffId);

	
	@Query("SELECT E FROM EquipmentActivity E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitCenterId = :profitcentreId " 	        
	        + "AND E.processId = :processId "	
	        + "AND E.deStuffId = :deStuffId "	
	        + "AND E.status <> 'D'")
	List<EquipmentActivity> getAllEquipmentsCommon(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("profitcentreId") String profitcentreId, @Param("processId") String processId, @Param("deStuffId") String deStuffId);

	
	@Query(value="SELECT COALESCE(MAX(e.srNo), 0) FROM EquipmentActivity e WHERE e.companyId = :cid AND e.branchId = :bid AND e.erpDocRefNo = :erp AND e.docRefNo = :doc AND e.status <> 'D'")
	int getHighestSrNo(@Param("cid") String cid, @Param("bid") String bid, @Param("erp") String erp, @Param("doc") String doc);
	
	@Query("SELECT E FROM EquipmentActivity E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitCenterId = :profitcentreId " 	        
	        + "AND E.deStuffId IN (:deStuffId, :cartingTransId) "	
	        + "AND E.status <> 'D'")
	List<EquipmentActivity> getAllEquipmentsCommonCarting(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("profitcentreId") String profitcentreId, @Param("cartingTransId") String cartingTransId, @Param("deStuffId") String deStuffId);
	
	@Query("SELECT E FROM EquipmentActivity E "
	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitCenterId = :profitcentreId " 
	        + "AND E.erpDocRefNo = :erpDocRefNo "
	        + "AND E.docRefNo = :docRefNo "
	        + "AND E.deStuffId IN (:deStuffId1, :cartingId) "	
	        + "AND E.status <> 'D'")
	List<EquipmentActivity> getAllEquipmentsCarting(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("profitcentreId") String profitcentreId,
	    @Param("erpDocRefNo") String erpDocRefNo,
	    @Param("docRefNo") String docRefNo,
	    @Param("deStuffId1") String deStuffId1,
	    @Param("cartingId") String cartingId);
	
	
	
	
	@Query(value="select COUNT(e) from EquipmentActivity e where e.companyId=:cid and e.branchId=:bid and e.erpDocRefNo=:erp and "
			+ "e.docRefNo=:doc and e.subDocRefNo=:subdoc")
	int getCount(@Param("cid") String cid,@Param("bid") String bid,@Param("erp") String erp,@Param("doc") String doc,@Param("subdoc") String subdoc);

    @Query(value="select j.jar_dtl_desc,p.party_name,COUNT(e.container_no),e.equipment,e.vendor_id,GROUP_CONCAT(e.container_no SEPARATOR ',') AS containerNos from cfequipmentactivity e "
    		+ "LEFT OUTER JOIN jar_detail j ON e.company_id=j.company_id and e.equipment=j.jar_Dtl_Id "
    		+ "LEFT OUTER JOIN Party p ON e.company_id=p.company_id and e.branch_id=p.branch_id and e.vendor_Id = p.party_Id "
    		+ "where e.company_id=:cid and e.branch_id=:bid and e.erp_Doc_Ref_No=:erp and "
    		+ "e.doc_Ref_No=:doc and e.sub_Doc_Ref_No=:subdoc and e.status != 'D' group by j.jar_Dtl_Desc,p.party_Name,e.equipment,e.vendor_Id",nativeQuery = true)
    List<Object[]> getData(@Param("cid") String cid,@Param("bid") String bid,@Param("erp") String erp,@Param("doc") String doc,@Param("subdoc") String subdoc);
    
    @Query(value="select j.jar_dtl_desc,p.party_name,e.equipment,e.vendor_id from cfequipmentactivity e "
    		+ "LEFT OUTER JOIN jar_detail j ON e.company_id=j.company_id and e.equipment=j.jar_Dtl_Id "
    		+ "LEFT OUTER JOIN Party p ON e.company_id=p.company_id and e.branch_id=p.branch_id and e.vendor_Id = p.party_Id "
    		+ "where e.company_id=:cid and e.branch_id=:bid and e.erp_Doc_Ref_No=:erp and e.container_no=:contain and "
    		+ "e.doc_Ref_No=:doc and e.sub_Doc_Ref_No=:subdoc and e.status != 'D' group by j.jar_Dtl_Desc,p.party_Name,e.equipment,e.vendor_Id",nativeQuery = true)
    List<Object[]> getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("erp") String erp,@Param("doc") String doc,
    		@Param("subdoc") String subdoc,@Param("contain") String contain);


    @Modifying
    @Transactional
    @Query(value = "UPDATE EquipmentActivity e SET e.status = 'D', e.editedBy = :edit, e.editedDate = :edate "
            + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.erpDocRefNo = :erp AND e.docRefNo = :doc "
            + "AND e.subDocRefNo = :subdoc AND e.equipment = :equip AND e.vendorId = :vendor AND e.status != 'D'")
    int deleteData(@Param("cid") String cid,
                   @Param("bid") String bid,
                   @Param("erp") String erp,
                   @Param("doc") String doc,
                   @Param("subdoc") String subdoc,
                   @Param("equip") String equip,
                   @Param("vendor") String vendor,
                   @Param("edit") String edit,
                   @Param("edate") Date edate);

    
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE EquipmentActivity e SET e.status = 'D', e.editedBy = :edit, e.editedDate = :edate "
            + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.erpDocRefNo = :erp AND e.docRefNo = :doc "
            + "AND e.subDocRefNo = :subdoc AND e.equipment = :equip AND e.vendorId = :vendor AND e.status != 'D' and e.containerNo=:con")
    int deleteData1(@Param("cid") String cid,
                   @Param("bid") String bid,
                   @Param("erp") String erp,
                   @Param("doc") String doc,
                   @Param("subdoc") String subdoc,
                   @Param("equip") String equip,
                   @Param("vendor") String vendor,
                   @Param("edit") String edit,
                   @Param("edate") Date edate,
                   @Param("con") String con);

    
    @Query("SELECT CASE WHEN COUNT(E) > 0 THEN true ELSE false END FROM EquipmentActivity E " +
	           "WHERE E.companyId = :companyId AND E.branchId = :branchId " 
	           	+ "AND E.profitCenterId = :profitcentreId " 
		        + "AND E.erpDocRefNo = :erpDocRefNo "
		        + "AND E.docRefNo = :docRefNo "
		        + "AND E.processId = :processId "	
		        + "AND E.deStuffId = :deStuffId "
		        + "AND E.equipment = :equipment "
		        + "AND E.srNo <> :srNo "
		        + "AND E.status <> 'D'")
	    boolean existsByEquipMent(@Param("companyId") String companyId, @Param("branchId") String branchId,
             @Param("profitcentreId") String profitcentreId, @Param("processId") String processId,
             @Param("erpDocRefNo") String erpDocRefNo, @Param("docRefNo") String docRefNo, 
             @Param("deStuffId") String deStuffId, @Param("equipment") String equipment, @Param("srNo") int srNo);
	 
	
	 
	 @Query("SELECT E FROM EquipmentActivity E "
				+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
		        + "AND E.profitCenterId = :profitcentreId " 
		        + "AND E.erpDocRefNo = :erpDocRefNo "
		        + "AND E.docRefNo = :docRefNo "
		        + "AND E.processId = :processId "	
		        + "AND E.deStuffId = :deStuffId "
		        + "AND E.equipment = :equipment "
		        + "AND E.srNo = :srNo "
		        + "AND E.status <> 'D'")
		EquipmentActivity getAllEquipmentsWithEquipmentSrNo(@Param("companyId") String companyId, @Param("branchId") String branchId,
		                              @Param("profitcentreId") String profitcentreId, @Param("processId") String processId,
		                              @Param("erpDocRefNo") String erpDocRefNo, @Param("docRefNo") String docRefNo, 
		                              @Param("deStuffId") String deStuffId, @Param("equipment") String equipment , @Param("srNo") int srNo);

	 
	 
	 
	
	@Query("SELECT E FROM EquipmentActivity E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitCenterId = :profitcentreId " 
	        + "AND E.erpDocRefNo = :erpDocRefNo "
	        + "AND E.docRefNo = :docRefNo "
	        + "AND E.processId = :processId "	
	        + "AND E.deStuffId = :deStuffId "
	        + "AND E.equipment = :equipment "
	        + "AND E.status <> 'D'")
	EquipmentActivity getAllEquipmentsWithEquipment(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("profitcentreId") String profitcentreId, @Param("processId") String processId,
	                              @Param("erpDocRefNo") String erpDocRefNo, @Param("docRefNo") String docRefNo, 
	                              @Param("deStuffId") String deStuffId, @Param("equipment") String equipment);

	
	
	@Query("SELECT E FROM EquipmentActivity E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitCenterId = :profitcentreId " 
	        + "AND E.erpDocRefNo = :erpDocRefNo "
	        + "AND E.docRefNo = :docRefNo "
	        + "AND E.processId = :processId "	
	        + "AND E.deStuffId = :deStuffId "	
	        + "AND E.status <> 'D'")
	List<EquipmentActivity> getAllEquipments(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("profitcentreId") String profitcentreId, @Param("processId") String processId,
	                              @Param("erpDocRefNo") String erpDocRefNo, @Param("docRefNo") String docRefNo, @Param("deStuffId") String deStuffId);

	
	
	@Query(value = "SELECT j.jar_dtl_desc, p.party_name, e.equipment, e.vendor_id, e.de_stuff_id "
	        + "FROM cfequipmentactivity e "
	        + "LEFT OUTER JOIN jar_detail j ON e.company_id = j.company_id AND e.equipment = j.jar_dtl_id "
	        + "LEFT OUTER JOIN Party p ON e.company_id = p.company_id AND e.branch_id = p.branch_id AND e.vendor_id = p.party_id "
	        + "WHERE e.company_id = :cid AND e.branch_id = :bid "
	        + "AND (:contain IS NULL OR e.container_no = :contain) "
	        + "AND e.status != 'D' "
	        + "AND e.de_stuff_id IN (:id1, :id2) "
	        + "GROUP BY j.jar_dtl_desc, p.party_name, e.equipment, e.vendor_id, e.de_stuff_id",
	        nativeQuery = true)
	List<Object[]> getDataByContainerNo1(
	        @Param("cid") String cid,
	        @Param("bid") String bid,
	        @Param("contain") String contain,
	        @Param("id1") String id1,
	        @Param("id2") String id2);
	
	
    @Modifying
    @Transactional
    @Query(value = "UPDATE EquipmentActivity e SET e.status = 'D', e.editedBy = :edit, e.editedDate = :edate "
            + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.deStuffId = :deStuffId "
            + "AND e.equipment = :equip AND e.vendorId = :vendor AND e.status != 'D' and e.containerNo=:con")
    int deleteData2(@Param("cid") String cid,
                   @Param("bid") String bid,
                   @Param("deStuffId") String deStuffId,
                   @Param("equip") String equip,
                   @Param("vendor") String vendor,
                   @Param("edit") String edit,
                   @Param("edate") Date edate,
                   @Param("con") String con);
}
