package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Port;

public interface PortRepository extends JpaRepository<Port, String> {
    
	
	 @Query("SELECT NEW com.cwms.entities.Port(i.portCode, i.portName) " +
		        "FROM Port i " +
		        "WHERE " +		       
		        "i.companyId = :companyId " +
		        "AND i.branchId = :branchId " +	
		        "AND i.localPort = :prefix " +	
		        "ORDER BY i.portName DESC")
		List<Port> getPortIdListNew(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId  ,
		    @Param("prefix") String prefix
		   );
	
	
	@Query("SELECT NEW com.cwms.entities.Port(i.portCode, i.portName) " +
		       "FROM Port i " +
		       "WHERE " +       
		       "i.companyId = :companyId " +
		       "AND i.branchId = :branchId " +
		       "AND (:searchValue IS NULL OR :searchValue = '' OR i.portCode LIKE %:searchValue% OR i.portName LIKE %:searchValue%) " +
		       "ORDER BY i.portName DESC")
		List<Port> searchPortData(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("searchValue") String searchValue  
		);

	
	
	@Query(value="select p.portCode,p.portName from Port p where p.companyId=:cid and p.branchId=:bid and p.status != 'D'")
	List<Object[]> getData(@Param("cid") String cid,@Param("bid") String bid);
	
	@Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM Port e WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId AND e.portCode = :portCode AND e.status <> 'D') " +
		       "THEN true ELSE false END")
		boolean existsByCompanyIdAndBranchIdAndPortCodeAndStatusNotD(
		       @Param("companyId") String companyId,
		       @Param("branchId") String branchId,
		       @Param("portCode") String portCode);

	
	 @Query("SELECT NEW com.cwms.entities.Port(i.portCode, i.portName) " +
		        "FROM Port i " +
		        "WHERE " +		       
		        "i.companyId = :companyId " +
		        "AND i.branchId = :branchId AND i.jobOrderFormat != '' " +			        		       
		        "ORDER BY i.portName DESC")
		List<Port> getPortsForGateIn(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId  
		   );
	
//	Port Search
	 @Query("SELECT NEW com.cwms.entities.Port(i.companyId, i.branchId, i.portTransId, i.portCode, i.isoPortCode, i.portName, i.portType, i.agentCode) " +
		        "FROM Port i " +
		        "WHERE " +		       
		        "i.companyId = :companyId " +
		        "AND i.branchId = :branchId " +
		        "AND (:portCode IS NULL OR :portCode = '' OR i.portCode = :portCode) " +
		        "AND (:portName IS NULL OR :portName = '' OR i.portName = :portName) " +
		        "AND (:portType IS NULL OR :portType = '' OR i.portType = :portType) " +
		        "AND i.status != 'D' " +		       
		        "ORDER BY i.portTransId DESC")
		List<Port> searchPorts(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("portCode") String portCode,
		    @Param("portType") String portType,
		    @Param("portName") String portName		   
		   );
	 

	 @Query("SELECT i " +
		        "FROM Port i " +
		        "WHERE " +		       
		        "i.companyId = :companyId " +
		        "AND i.branchId = :branchId " +
		        "AND i.portCode = :portCode " +
		        "AND i.portTransId = :portTransId " +
		        "AND i.status != 'D'")	       
		Port getPortByPortCodeAndTransId(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("portCode") String portCode,		  
		    @Param("portTransId") String portTransId
		   );
	 
	 @Query("SELECT i " +
		        "FROM Port i " +
		        "WHERE " +		       
		        "i.companyId = :companyId " +
		        "AND i.branchId = :branchId " +
		        "AND i.portCode = :portCode " +
		        "AND i.status != 'D'")	       
		Port getPortByPortCode(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("portCode") String portCode
		   );
	 
	 
//		Port Search
		 @Query("SELECT NEW com.cwms.entities.Port(i.portCode, i.portName) " +
			        "FROM Port i " +
			        "WHERE " +		       
			        "i.companyId = :companyId " +
			        "AND i.branchId = :branchId " +			        		       
			        "ORDER BY i.portName DESC")
			List<Port> getSelectTags(
			    @Param("companyId") String companyId,
			    @Param("branchId") String branchId  
			   );
		 
		 
//		 @Query(value="select p.portCode,p.portName from Port p where p.companyId=:cid and p.branchId=:bid and p.country='India' "
//	 		+ "and p.status != 'D' and (:val is null OR :val = '' OR p.portName LIKE CONCAT(:val,'%') OR p.portCode LIKE CONCAT(:val,'%'))")
//		List<Object[]> getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);

@Query(value="select p.portCode,p.portName from Port p where p.companyId=:cid and p.branchId=:bid and p.localPort='Local' "
	 		+ "and p.status != 'D' and (:val is null OR :val = '' OR p.portName LIKE CONCAT(:val,'%') OR p.portCode LIKE CONCAT(:val,'%'))")
		List<Object[]> getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
			
//			@Query(value="select p.portCode from Port p where p.companyId=:cid and p.branchId=:bid and p.jobOrderPrefix = :prefix and p.status != 'D'")
//			List<String> getPortIdList(@Param("cid") String cid, @Param("bid") String bid, @Param("prefix") String prefix);
			
			
			@Query(value="select p.portCode from Port p where p.companyId=:cid and p.branchId=:bid and p.localPort = :prefix and p.status != 'D'")
			List<String> getPortIdList(@Param("cid") String cid, @Param("bid") String bid, @Param("prefix") String prefix);
			
			
			
}
