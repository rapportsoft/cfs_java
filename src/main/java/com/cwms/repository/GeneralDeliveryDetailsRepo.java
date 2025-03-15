package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GeneralDeliveryDetails;
import com.cwms.entities.GeneralDeliveryGrid;
import com.cwms.entities.GeneralReceivingGateInDtl;
import com.cwms.entities.GeneralReceivingGrid;

public interface GeneralDeliveryDetailsRepo extends JpaRepository<GeneralDeliveryDetails, String>{

	@Query("SELECT c FROM GeneralDeliveryDetails c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.deliveryId = :deliveryId " +
		       "AND c.receivingId = :receivingId " +
		       "AND c.depositNo = :depositNo " +
		       "AND c.commodityId = :commodityId " +
		       "AND c.status != 'D' ")
		GeneralDeliveryDetails getAfterSaveForEdit( 
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("deliveryId") String deliveryId,
		    @Param("receivingId") String receivingId,
		    @Param("depositNo") String depositNo,
		    @Param("commodityId") String commodityId);

		@Query("SELECT c FROM GeneralDeliveryGrid c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.deliveryId = :deliveryId " +
		       "AND c.yardLocation = :yardLocation " +
		       "AND c.yardBlock = :yardBlock " +
		       "AND c.blockCellNo = :blockCellNo " +
		       "AND c.status = 'A'")
		GeneralDeliveryGrid getSavedGeneralDeliveryGrid( 
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("deliveryId") String deliveryId,
		    @Param("yardLocation") String yardLocation,
		    @Param("yardBlock") String yardBlock,
		    @Param("blockCellNo") String blockCellNo);
		
		
		@Query("SELECT c FROM GeneralDeliveryGrid c " +
			       "WHERE c.companyId = :companyId " +
			       "AND c.branchId = :branchId " +
			       "AND c.deliveryId = :deliveryId " +
			       "AND c.receivingId = :receivingId " +
			       "AND c.yardLocation = :yardLocation " +
			       "AND c.yardBlock = :yardBlock " +
			       "AND c.blockCellNo = :blockCellNo " +
			       "AND c.status = 'A'")
			GeneralDeliveryGrid getSavedGrid( 
			    @Param("companyId") String companyId, 
			    @Param("branchId") String branchId, 
			    @Param("deliveryId") String deliveryId,
			    @Param("receivingId") String receivingId,
			    @Param("yardLocation") String yardLocation, 
			    @Param("yardBlock") String yardBlock,
			    @Param("blockCellNo") String blockCellNo);
		
		
		
		
		
		
		@Query("SELECT c FROM GeneralDeliveryDetails c " +
			       "WHERE c.companyId = :companyId " +
			       "AND c.branchId = :branchId " +
			       "AND c.deliveryId = :deliveryId " +
			       "AND c.receivingId = :receivingId " +
			       "AND c.depositNo = :depositNo " +
			       "AND c.status = 'A'")
			List<GeneralDeliveryDetails> getAfterSave( 
			    @Param("companyId") String companyId, 
			    @Param("branchId") String branchId, 
			    @Param("deliveryId") String deliveryId,
			    @Param("receivingId") String receivingId,
			    @Param("depositNo") String depositNo);
		
		
		
		
		
		
		
		@Query("SELECT c from GeneralDeliveryGrid c where c.companyId =:companyId and c.branchId=:branchId and c.deliveryId=:deliveryId  AND  c.receivingId=:receivingId and c.status !='D'")
		List<GeneralDeliveryGrid> getDataAfterSave(@Param ("companyId") String companyId,
				@Param ("branchId") String branchId ,
				@Param ("deliveryId") String deliveryId,
				@Param ("receivingId") String receivingId);

}
