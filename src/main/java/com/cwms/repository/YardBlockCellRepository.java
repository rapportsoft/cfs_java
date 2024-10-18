package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.YardBlockCell;

import jakarta.transaction.Transactional;

public interface YardBlockCellRepository extends JpaRepository<YardBlockCell, String> {
	
	@Query("SELECT E FROM YardBlockCell E "
		 	+ "WHERE E.companyId = :companyId AND E.yardId = :branchId " 
           	+ "AND E.yardLocationId = :yardLocationId " 
	        + "AND E.blockId = :blockId "
	        + "AND E.cellNoRow = :cellNoRow "	            
	        + "AND E.status <> 'D'")
	YardBlockCell getYardCellByCellNo(@Param("companyId") String companyId, @Param("branchId") String branchId,
            @Param("yardLocationId") String yardLocationId, @Param("blockId") String blockId, @Param("cellNoRow") String cellNoRow);


	@Query(value = "SELECT p.yardLocationId, p.blockId, p.cellNoRow, p.cellArea, p.cellAreaUsed "
			+ "FROM YardBlockCell p " + "WHERE p.companyId = :companyId AND p.yardId = :branchId AND p.status != 'D' "
			+ "AND p.yardLocationId LIKE CONCAT(:searchValue, '%') "
			+ "ORDER BY p.yardLocationId, p.blockId, p.cellNoRow")
	List<Object[]> searchYardCells(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("searchValue") String searchValue);
	
	
	@Query(value = "SELECT * FROM yardblockcell WHERE Company_Id = :cid AND  yard_Id = :bid  AND yard_location_id = :yardLocationId AND block_Id=:blockId AND Cell_No_Row=:cellNoRow  AND status <> 'D'", nativeQuery = true)
	YardBlockCell getAllData(@Param("cid") String cid, @Param("bid") String bid,@Param("yardLocationId") String yardLocationId, @Param("blockId") String blockId,
			@Param("cellNoRow") String cellNoRow);
	
	
	

	@Query(value = "select * from yardblockcell where company_id=:cid and Yard_Id=:bid and status != 'D' ORDER BY Created_Date DESC LIMIT 1000", nativeQuery = true)
	List<YardBlockCell> getalldata(@Param("cid") String cid, @Param("bid") String bid);
	
	
	
	@Query("SELECT new com.cwms.entities.YardBlockCell(" +
            "y.companyId, y.yardId, y.yardLocationId, y.blockId, y.cellNoRow, " +
            "y.cellArea, y.cellAreaUsed, y.locationCategory, y.cellStatus, y.status) " +
            "FROM YardBlockCell y " +
            "WHERE y.companyId = :companyId " +
            "AND y.yardId = :yardId " +
            "AND y.locationCategory = 'O' " +
            "AND y.cellStatus = 'N' " +
            "AND y.status ='A'")
List<YardBlockCell> findYardBlockCell(@Param("companyId") String companyId,
                                      @Param("yardId") String yardId);
	
	
	@Query(value = "SELECT COUNT(y)>0 FROM YardBlockCell y WHERE y.companyId = :cid AND  y.yardId = :bid  AND y.yardLocationId = :yardLocationId AND y.blockId=:blockId AND y.cellNoRow=:cellNoRow  AND y.status <> 'D'"
			+ " and y.yardTransId != :yard ")
	Boolean getAllData1(@Param("cid") String cid, @Param("bid") String bid,@Param("yardLocationId") String yardLocationId, @Param("blockId") String blockId,
			@Param("cellNoRow") String cellNoRow,@Param("yard") String yard);
	
	
	
	@Query(value="select New com.cwms.entities.YardBlockCell(y.yardLocationId, y.blockId, y.cellNoRow, y.yardLocationDesc) from YardBlockCell y "
			+ "where y.companyId=:cid  and y.status != 'D' "
			+ "AND ((:val is null OR :val = '' OR y.yardLocationId like CONCAT ('%',:val,'%')) OR "
			+ "(:val is null OR :val = '' OR y.blockId like CONCAT ('%',:val,'%')) OR "
			+ "(:val is null OR :val = '' OR y.cellNoRow like CONCAT ('%',:val,'%')) OR "
			+ "(:val is null OR :val = '' OR y.yardLocationDesc like CONCAT ('%',:val,'%')))")
	List<YardBlockCell> getAll(@Param("cid") String cid,@Param("val") String val);
	
	@Query("SELECT new com.cwms.entities.YardBlockCell(" +
		       "y.companyId, y.yardId, y.yardLocationId, y.blockId, y.cellNoRow, " +
		       "y.cellArea, y.cellAreaUsed, y.locationCategory, y.cellStatus, y.status) " +
		       "FROM YardBlockCell y " +
		       "WHERE y.companyId = :companyId " +
		       "AND y.yardId = :branchId " +
		       "AND y.cellStatus = 'N' " +
		       "AND y.status = 'A' " +
		       "AND ((:search IS NULL OR :search = '' OR y.yardLocationId LIKE CONCAT(:search, '%')) " +
		       "OR (:search IS NULL OR :search = '' OR y.blockId LIKE CONCAT(:search, '%')) " +
		       "OR (:search IS NULL OR :search = '' OR y.cellNoRow LIKE CONCAT(:search, '%'))) ")
		List<YardBlockCell> findAllYardBlockCell(@Param("companyId") String companyId,
		                                         @Param("branchId") String branchId,
		                                         @Param("search") String search);
	
	
	@Query(value="select New com.cwms.entities.YardBlockCell(y.yardLocationId, y.blockId, y.cellNoRow, y.yardLocationDesc) from YardBlockCell y "
			+ "where y.companyId=:cid  and y.status != 'D' "
			+ "AND ((:val is null OR :val = '' OR y.yardLocationId like CONCAT ('%',:val,'%')) AND "
			+ "(:val1 is null OR :val1 = '' OR y.blockId like CONCAT ('%',:val1,'%')) AND "
			+ "(:val2 is null OR :val2 = '' OR y.cellNoRow like CONCAT ('%',:val2,'%')))")
	List<YardBlockCell> getAll1(@Param("cid") String cid,@Param("val") String val,@Param("val1") String val1,@Param("val2") String val2);
	
	

	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE YardBlockCell c SET c.cellAreaUsed = :cellAreaUsed " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.yardId = :yardId " +
	       "AND c.yardLocationId = :yardLocationId " +
	       "AND c.blockId = :blockId " +
	       "AND c.cellNoRow = :cellNoRow")
	int updateYardBlockCellAfterGatePass(
	        @Param("cellAreaUsed") BigDecimal cellAreaUsed,
	        @Param("companyId") String companyId,
	        @Param("yardId") String yardId,
	        @Param("yardLocationId") String yardLocationId,
	        @Param("blockId") String blockId,
	        @Param("cellNoRow") String cellNoRow);

}
