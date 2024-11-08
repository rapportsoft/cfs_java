package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Impexpgrid;

public interface Impexpgridrepo extends JpaRepository<Impexpgrid, String> {
	
	
	@Query("SELECT COALESCE(MAX(E.subSrNo), 0) FROM Impexpgrid E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.processTransId = :processTransId "
			+ "AND E.lineNo = :lineNo")
	int getMaxSubSrNo(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("processTransId") String processTransId, @Param("lineNo") int lineNo);

	@Query("SELECT E FROM Impexpgrid E " + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.processTransId = :processTransId " + "AND E.yardBlock = :yardBlock "
			+ "AND E.yardLocation = :yardLocation " + "AND E.blockCellNo = :blockCellNo " + "AND E.lineNo = :lineNo "
			+ "AND E.subSrNo = :subSrNo " + "AND E.status <> 'D'")
	Impexpgrid getImpexpgridSubLineNo(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("yardLocation") String yardLocation, @Param("yardBlock") String yardBlock,
			@Param("blockCellNo") String blockCellNo, @Param("processTransId") String processTransId,
			@Param("lineNo") int lineNo, @Param("subSrNo") int subSrNo);

	@Query("SELECT CASE WHEN COUNT(E) > 0 THEN true ELSE false END FROM Impexpgrid E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.processTransId = :processTransId "
			+ "AND E.yardBlock = :yardBlock " + "AND E.yardLocation = :yardLocation "
			+ "AND E.blockCellNo = :blockCellNo " + "AND E.lineNo = :lineNo " + "AND E.subSrNo <> :subSrNo "
			+ "AND E.status <> 'D'")
	boolean existsByYardGrid(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("yardLocation") String yardLocation, @Param("yardBlock") String yardBlock,
			@Param("blockCellNo") String blockCellNo, @Param("processTransId") String processTransId,
			@Param("lineNo") int lineNo, @Param("subSrNo") int subSrNo);

	@Query("SELECT E FROM Impexpgrid E " + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.processTransId = :cartingTransId " + "AND E.lineNo = :cartingLineId " + "AND E.transType = :type "
			+ "AND E.status <> 'D'")
	List<Impexpgrid> getAllImpExpGridCarting(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("cartingTransId") String cartingTransId, @Param("cartingLineId") String cartingLineId,
			@Param("type") String type);

	@Query("SELECT new com.cwms.entities.Impexpgrid("
			+ "E.companyId, E.finYear, E.branchId, E.processTransId, E.lineNo, "
			+ "E.subSrNo, E.yardLocation, E.yardBlock, E.blockCellNo, E.yardPackages, y.cellArea, (COALESCE(y.cellAreaUsed, 0) - COALESCE(E.cellAreaUsed, 0)), E.cellAreaAllocated, E.qtyTakenOut, E.areaReleased, E.transType, E.stuffReqQty, E.createdBy, E.createdDate, E.editedBy, E.editedDate, E.approvedBy, E.approvedDate, E.status) "
			+ "FROM Impexpgrid E " 
			+ "Left join YardBlockCell y on E.companyId = y.companyId and E.branchId = y.yardId and E.yardLocation =y.yardLocationId and E.yardBlock = y.blockId and  E.blockCellNo = y.cellNoRow "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.processTransId = :cartingTransId " + "AND E.lineNo = :cartingLineId " + "AND E.subSrNo = :subLine "
			+ "AND E.transType = :type " + "AND E.status <> 'D'")
	Impexpgrid getAllEquipmentsCommonCartingSubLine(@Param("companyId") String companyId,
			@Param("branchId") String branchId, @Param("cartingTransId") String cartingTransId,
			@Param("cartingLineId") String cartingLineId, @Param("subLine") String subLine, @Param("type") String type);

	
	
	
	
	
	
	

	@Query(value="select i from Impexpgrid i where i.companyId=:cid and i.branchId=:bid and i.processTransId=:id and i.status != 'D' and "
			+ "i.transType='IMP'")
	Impexpgrid getDataById(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select i from Impexpgrid i where i.companyId=:cid and i.branchId=:bid and i.processTransId=:id and i.status != 'D' and "
			+ "i.transType='IMP' and i.lineNo=:line")
	Impexpgrid getDataByIdAndLineId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("line") String line);
	
	
	@Query(value = "select i from Impexpgrid i where i.companyId = :cid and i.branchId = :bid and i.processTransId = :id " +
            "and i.status != 'D' and i.lineNo = :line and (COALESCE(i.yardPackages, 0) - COALESCE(i.qtyTakenOut, 0)) > 0 " +
            "order by (COALESCE(i.yardPackages, 0) - COALESCE(i.qtyTakenOut, 0)) asc")
List<Impexpgrid> getDataForTally(@Param("cid") String cid, @Param("bid") String bid, 
                              @Param("id") String id, @Param("line") String line);
	
	@Query(value = "select i from Impexpgrid i where i.companyId = :cid and i.branchId = :bid and i.processTransId = :id " +
            "and i.status != 'D' and i.lineNo = :line " +
            "order by (COALESCE(i.yardPackages, 0) - COALESCE(i.qtyTakenOut, 0)) asc")
List<Impexpgrid> getDataForTally1(@Param("cid") String cid, @Param("bid") String bid, 
                              @Param("id") String id, @Param("line") String line);
}
