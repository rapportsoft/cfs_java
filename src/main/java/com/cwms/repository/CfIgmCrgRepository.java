package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Cfigmcrg;

import jakarta.transaction.Transactional;

public interface CfIgmCrgRepository extends JpaRepository<Cfigmcrg, String> {

	@Query(value="select COUNT(c)>0 from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.blNo=:bl and c.status != 'D'")
	Boolean isExistRecord(@Param("cid") String cid,@Param("bid") String bid,@Param("bl") String bl);
	
	@Query(value="select COUNT(c)>1 from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.blNo=:bl and c.igmCrgTransId != :crg and c.status != 'D'")
	Boolean isExistRecord1(@Param("cid") String cid,@Param("bid") String bid,@Param("bl") String bl,@Param("crg") String crg);
	
	
//	@Query(value="select COUNT(c)>0 from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.igmLineNo=:bl and c.status != 'D'")
//	Boolean isExistLineRecord(@Param("cid") String cid,@Param("bid") String bid,@Param("bl") String bl);
	
	@Query(value="select COUNT(c)>0 from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.igmLineNo=:bl and c.status != 'D' "
			+ "and c.igmNo=:igm")
	Boolean isExistLineRecord(@Param("cid") String cid,@Param("bid") String bid,@Param("bl") String bl,@Param("igm") String igm);
	
//	@Query(value="select COUNT(c)>0 from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.igmLineNo=:bl and c.igmCrgTransId != :crg and c.status != 'D'")
//	Boolean isExistLineRecord1(@Param("cid") String cid,@Param("bid") String bid,@Param("bl") String bl,@Param("crg") String crg);
	
	@Query(value="select COUNT(c)>0 from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.igmLineNo=:bl and c.igmCrgTransId != :crg "
			+ "and c.status != 'D' and c.igmNo=:igm ")
	Boolean isExistLineRecord1(@Param("cid") String cid,@Param("bid") String bid,@Param("bl") String bl,@Param("crg") String crg,@Param("igm") String igm);
	
	@Query(value="select c from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.igmTransId =:igmtrans and "
			+ "c.igmNo=:igm and c.igmCrgTransId=:igmCrgTransId and c.status != 'D'")
	Cfigmcrg getData(@Param("cid") String cid,@Param("bid") String bid,@Param("igmtrans") String igmtrans,
			@Param("igm") String igm,@Param("igmCrgTransId") String igmLineNo);
	
	
	@Query(value="select c from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.igmTransId =:igmtrans and "
			+ "c.igmNo=:igm and c.igmLineNo=:igmCrgTransId and c.status != 'D'")
	Cfigmcrg getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("igmtrans") String igmtrans,
			@Param("igm") String igm,@Param("igmCrgTransId") String igmLineNo);
	
	
	@Query("select distinct new com.cwms.entities.Cfigmcrg(c.igmTransId, c.igmCrgTransId, c.profitcentreId, c.igmLineNo, c.igmNo, " +
		       "c.cycle, c.viaNo, c.blNo, c.blDate, c.importerName, c.commodityDescription, c.cargoValue, c.cargoDuty, " +
		       "c.beNo, c.beDate, c.chaCode, c.chaName, c.mobileNo, c.sealCuttingType, c.sealCuttingRemarks, c.blType, " +
		       "c.beWt, c.notifyPartyName, p.partyName) " +
		       "from Cfigmcrg c " +
		       "LEFT JOIN CFIgm f ON c.companyId = f.companyId AND c.branchId = f.branchId AND c.igmNo = f.igmNo AND c.igmTransId = f.igmTransId " +
		       "LEFT JOIN Party p ON f.companyId = p.companyId AND f.branchId = p.branchId AND f.shippingLine = p.partyId " +
		       "where c.companyId = :cid " +
		       "and c.branchId = :bid " +
		       "and c.igmNo = :igm " +
		       "and c.igmLineNo = :igmCrgTransId " +
		       "and c.status != 'D'")
		Cfigmcrg getData2(@Param("cid") String cid,
		                  @Param("bid") String bid,
		                  @Param("igm") String igm,
		                  @Param("igmCrgTransId") String igmLineNo);
	
//	
//	@Query("select new com.cwms.entities.Cfigmcrg(c.igmTransId, c.igmCrgTransId, c.profitcentreId, c.igmLineNo, c.igmNo, " +
//		       "c.cycle, c.viaNo, c.blNo, c.blDate, c.importerName, c.commodityDescription, c.cargoValue, c.cargoDuty, " +
//		       "c.beNo, c.beDate, c.chaCode, c.chaName, c.mobileNo, c.sealCuttingType, c.sealCuttingRemarks, c.blType, " +
//		       "c.beWt, c.notifyPartyName, p.partyName, c.examinationRemarks) " +
//		       "from Cfigmcrg c " +
//		       "LEFT JOIN CFIgm f ON c.companyId = f.companyId AND c.branchId = f.branchId AND c.igmNo = f.igmNo " +
//		       "LEFT JOIN Party p ON f.companyId = p.companyId AND f.branchId = p.branchId AND f.shippingLine = p.partyId " +
//		       "where c.companyId = :cid " +
//		       "and c.branchId = :bid " +
//		       "and c.igmNo = :igm " +
//		       "and c.igmLineNo = :igmCrgTransId " +
//		       "and c.status != 'D'")
//		Cfigmcrg getData3(@Param("cid") String cid,
//		                  @Param("bid") String bid,
//		                  @Param("igm") String igm,
//		                  @Param("igmCrgTransId") String igmLineNo);
//	
	

	@Query("select distinct new com.cwms.entities.Cfigmcrg(c.igmTransId, c.igmCrgTransId, c.profitcentreId, c.igmLineNo, c.igmNo, " +
		       "c.cycle, c.viaNo, c.blNo, c.blDate, c.importerName, c.commodityDescription, c.cargoValue, c.cargoDuty, " +
		       "c.beNo, c.beDate, c.chaCode, c.chaName, c.mobileNo, c.sealCuttingType, c.sealCuttingRemarks, c.blType, " +
		       "c.beWt, c.notifyPartyName, p.partyName, c.examinationRemarks) " +
		       "from Cfigmcrg c " +
		       "LEFT JOIN CFIgm f ON c.companyId = f.companyId AND c.branchId = f.branchId AND c.igmNo = f.igmNo AND c.igmTransId = f.igmTransId " +
		       "LEFT JOIN Party p ON f.companyId = p.companyId AND f.branchId = p.branchId AND f.shippingLine = p.partyId " +
		       "where c.companyId = :cid " +
		       "and c.branchId = :bid " +
		       "and c.igmNo = :igm " +
		       "and c.igmLineNo = :igmCrgTransId " +
		       "and c.status != 'D'")
		Cfigmcrg getData3(@Param("cid") String cid,
		                  @Param("bid") String bid,
		                  @Param("igm") String igm,
		                  @Param("igmCrgTransId") String igmLineNo);
	
	@Query("select c " +
		       "from Cfigmcrg c " +
		       "where c.companyId = :cid " +
		       "and c.branchId = :bid " +
		       "and c.igmNo = :igm " +
		       "and c.igmLineNo = :igmCrgTransId " +
		       "and c.status != 'D'")
		Cfigmcrg getData4(@Param("cid") String cid,
		                  @Param("bid") String bid,
		                  @Param("igm") String igm,
		                  @Param("igmCrgTransId") String igmLineNo);
	
	@Query("select c " +
		       "from Cfigmcrg c " +
		       "where c.companyId = :cid " +
		       "and c.branchId = :bid " +
		       "and c.igmNo = :igm " +
		       "and c.igmLineNo = :igmCrgTransId " +
		       "and c.status != 'D' and (c.destuffId is not null and c.destuffId != '')")
		Cfigmcrg getData5(@Param("cid") String cid,
		                  @Param("bid") String bid,
		                  @Param("igm") String igm,
		                  @Param("igmCrgTransId") String igmLineNo);
	



	
	
//	@Transactional
//	@Modifying
//	@Query(value="update Cfigmcrg c set c.cycle=:cycle,c.igmLineNo=:igmlineno,c.blNo=:blno,c.blDate=:bldate,c.commodityDescription=:commodity,"
//			+ "c.cargoMovement=:cargomovement,c.grossWeight=:gross,c.unitOfWeight=:unitOfwt,c.noOfPackages=:nop,c.typeOfPackage=:top,"
//			+ "c.accountHolderId=:holdingagent,c.accountHolderName=:holdingAgentName,c.marksOfNumbers=:marksOfNumbers,c.importerId=:importerId,"
//			+ "c.importerName=:importerName,c.importerAddress1=:importerAddress1,c.importerAddress2=:importerAddress2,c.importerAddress3=:importerAddress3,"
//			+ "c.notifyPartyId=:notifyPartyId,c.notifyPartyName=:notifyPartyName,c.notifiedAddress1=:notifiedAddress1,c.notifiedAddress2=:notifiedAddress2,"
//			+ "c.notifiedAddress3=:notifiedAddress3,c.destination=:destination,c.cargoType=:cargoType,c.imoCode=:imoCode,c.unNo=:unNo,c.hazReeferRemarks=:hazReeferRemarks,"
//			+ "c.editedBy=:editedBy,c.editedDate=:editedDate "
//			+ "where c.companyId=:cid and c.branchId=:bid and c.igmTransId =:igmtrans and "
//			+ "c.igmNo=:igm and c.igmCrgTransId=:crgtrans and c.status != 'D'")
//	int updateData(@Param("cid") String cid, @Param("bid") String bid, @Param("igmtrans") String igmtrans, @Param("igm") String igm, @Param("crgtrans") String crgtrans,
//            @Param("cycle") String cycle, @Param("igmlineno") String igmlineno, @Param("blno") String blNo, @Param("bldate") Date blDate,
//            @Param("commodity") String commodity, @Param("cargomovement") String cargoMovement, @Param("gross") BigDecimal grossWeight,
//            @Param("unitOfwt") String unitOfWeight, @Param("nop") BigDecimal noOfPackages, @Param("top") String typeOfPackage,
//            @Param("holdingagent") String holdingAgen, @Param("holdingAgentName") String holdingAgentName,
//            @Param("marksOfNumbers") String marksOfNumbers, @Param("importerId") String importerId, @Param("importerName") String importerName,
//            @Param("importerAddress1") String importerAddress1, @Param("importerAddress2") String importerAddress2, @Param("importerAddress3") String importerAddress3,
//            @Param("notifyPartyId") String notifyPartyId, @Param("notifyPartyName") String notifyPartyName, @Param("notifiedAddress1") String notifiedAddress1,
//            @Param("notifiedAddress2") String notifiedAddress2, @Param("notifiedAddress3") String notifiedAddress3, @Param("destination") String destination,
//            @Param("cargoType") String cargoType, @Param("imoCode") String imoCode, @Param("unNo") String unNo, @Param("hazReeferRemarks") String hazReeferRemarks,
//            @Param("editedBy") String editedby,@Param("editedDate") Date editedDate);
	
	
	
	@Transactional
	@Modifying
	@Query(value="update Cfigmcrg c set c.cycle=:cycle,c.igmLineNo=:igmlineno,c.blNo=:blno,c.blDate=:bldate,c.commodityDescription=:commodity,"
			+ "c.cargoMovement=:cargomovement,c.grossWeight=:gross,c.unitOfWeight=:unitOfwt,c.noOfPackages=:nop,c.typeOfPackage=:top,"
			+ "c.accountHolderId=:holdingagent,c.accountHolderName=:holdingAgentName,c.marksOfNumbers=:marksOfNumbers,c.importerId=:importerId,"
			+ "c.importerName=:importerName,c.importerAddress1=:importerAddress1,c.importerAddress2=:importerAddress2,c.importerAddress3=:importerAddress3,"
			+ "c.notifyPartyId=:notifyPartyId,c.notifyPartyName=:notifyPartyName,c.notifiedAddress1=:notifiedAddress1,c.notifiedAddress2=:notifiedAddress2,"
			+ "c.notifiedAddress3=:notifiedAddress3,c.destination=:destination,c.cargoType=:cargoType,c.imoCode=:imoCode,c.unNo=:unNo,c.hazReeferRemarks=:hazReeferRemarks,"
			+ "c.editedBy=:editedBy,c.editedDate=:editedDate,c.hsnCode=:hsn "
			+ "where c.companyId=:cid and c.branchId=:bid and c.igmTransId =:igmtrans and "
			+ "c.igmNo=:igm and c.igmCrgTransId=:crgtrans and c.status != 'D'")
	int updateData(@Param("cid") String cid, @Param("bid") String bid, @Param("igmtrans") String igmtrans, @Param("igm") String igm, @Param("crgtrans") String crgtrans,
            @Param("cycle") String cycle, @Param("igmlineno") String igmlineno, @Param("blno") String blNo, @Param("bldate") Date blDate,
            @Param("commodity") String commodity, @Param("cargomovement") String cargoMovement, @Param("gross") BigDecimal grossWeight,
            @Param("unitOfwt") String unitOfWeight, @Param("nop") BigDecimal noOfPackages, @Param("top") String typeOfPackage,
            @Param("holdingagent") String holdingAgen, @Param("holdingAgentName") String holdingAgentName,
            @Param("marksOfNumbers") String marksOfNumbers, @Param("importerId") String importerId, @Param("importerName") String importerName,
            @Param("importerAddress1") String importerAddress1, @Param("importerAddress2") String importerAddress2, @Param("importerAddress3") String importerAddress3,
            @Param("notifyPartyId") String notifyPartyId, @Param("notifyPartyName") String notifyPartyName, @Param("notifiedAddress1") String notifiedAddress1,
            @Param("notifiedAddress2") String notifiedAddress2, @Param("notifiedAddress3") String notifiedAddress3, @Param("destination") String destination,
            @Param("cargoType") String cargoType, @Param("imoCode") String imoCode, @Param("unNo") String unNo, @Param("hazReeferRemarks") String hazReeferRemarks,
            @Param("editedBy") String editedby,@Param("editedDate") Date editedDate,@Param("hsn") String hsn);


	 @Query(value="select c from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:trans and c.igmNo=:igm and c.status != 'D'")
	 List<Cfigmcrg> getDataByIgm(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String igmtrans, @Param("igm") String igm);


	 @Transactional
	 @Modifying
	 @Query(value="update Cfigmcrg c SET c.igmNo=:newigm where c.companyId=:cid and c.branchId=:bid and c.igmTransId =:igmtrans and c.igmNo=:igm "
	 		+ "and c.viaNo=:via and c.status != 'D'")
	 int updateIgmNo(@Param("cid") String cid, @Param("bid") String bid, @Param("igmtrans") String igmtrans, @Param("igm") String igm,
			 @Param("newigm") String newigm,@Param("via") String via);

	 
	 
//	 
//	 @Query("SELECT crg.igmNo,crg.igmTransId,crg.igmLineNo,crg.importerName,crg.blNo,crg.blDate,crg.noOfPackages,crg.grossWeight, "
//	 		    + "crg.beNo,crg.beDate,crg.cargoValue,crg.cargoDuty,crg.blType,crg.commodityDescription,crg.chaCode,crg.chaName "
//	 		    + "FROM Cfigmcrg crg "
//		        + "LEFT JOIN Cfigmcn cn ON crg.companyId = cn.companyId "
//		        + "AND crg.branchId = cn.branchId "
//		        + "AND crg.igmNo = cn.igmNo "
//		        + "AND crg.igmTransId = cn.igmTransId "
//		        + "AND crg.igmLineNo = cn.igmLineNo "
//		        + "WHERE crg.companyId = :cid "
//		        + "AND crg.branchId = :bid "
//		        + "AND crg.igmNo = :igm "
//		        + "AND cn.containerNo = :container")
//		List<Object[]> getCrgData(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("container") String container);
	 
	 @Query("SELECT crg.igmNo,crg.igmTransId,crg.igmLineNo,crg.importerName,crg.blNo,crg.blDate,cn.noOfPackages,cn.grossWt, "
	 		    + "crg.beNo,crg.beDate,crg.cargoValue,crg.cargoDuty,crg.blType,crg.commodityDescription,crg.chaCode,crg.chaName "
	 		    + "FROM Cfigmcrg crg "
		        + "LEFT JOIN Cfigmcn cn ON crg.companyId = cn.companyId "
		        + "AND crg.branchId = cn.branchId "
		        + "AND crg.igmNo = cn.igmNo "
		        + "AND crg.igmTransId = cn.igmTransId "
		        + "AND crg.igmLineNo = cn.igmLineNo "
		        + "WHERE crg.companyId = :cid "
		        + "AND crg.branchId = :bid "
		        + "AND crg.igmNo = :igm "
		        + "AND cn.containerNo = :container")
		List<Object[]> getCrgData(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("container") String container);
		
		


		@Query(value="select c from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:trans and c.igmNo=:igm and c.igmLineNo=:line and c.status != 'D'")
		Cfigmcrg getDataByIgmAndLine(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String igmtrans, @Param("igm") String igm,@Param("line") String line);

//		@Query(value="select c.igmNo,c.igmTransId,c.igmLineNo,i.shippingLine,i.shippingAgent,p.partyName,c.chaName,c.importerName,c.importerAddress1,"
//				+ "c.importerAddress2,c.importerAddress3,c.commodityDescription,i.viaNo,i.igmDate,c.marksOfNumbers,c.grossWeight,"
//				+ "c.typeOfPackage,c.noOfPackages,c.yardLocation,c.yardBlock,c.blockCellNo,c.cargoType,c.cargoMovement from Cfigmcrg c "
//				+ "LEFT JOIN CFIgm i ON c.companyId = i.companyId AND c.branchId = i.branchId AND c.igmNo = i.igmNo AND c.igmTransId = i.igmTransId "
//				+ "LEFT JOIN Party p ON i.companyId = p.companyId AND i.branchId = p.branchId AND i.shippingLine = p.partyId "
//				+ "where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.igmLineNo=:line "
//				+ "and c.status != 'D'")
//		Object[] getDataByIgmAndLineForDestuff(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,@Param("line") String line);
		
		@Query(value="select c.igmNo,c.igmTransId,c.igmLineNo,i.shippingLine,i.shippingAgent,p.partyName,c.chaName,c.importerName,c.importerAddress1,"
				+ "c.importerAddress2,c.importerAddress3,c.commodityDescription,i.viaNo,i.igmDate,c.marksOfNumbers,c.grossWeight,"
				+ "c.typeOfPackage,c.noOfPackages,c.yardLocation,c.yardBlock,c.blockCellNo,c.cargoType,c.cargoMovement,"
				+ "crg.typeOfCargo,crg.odcType,crg.length,crg.height,crg.weight from Cfigmcrg c "
				+ "LEFT JOIN CFIgm i ON c.companyId = i.companyId AND c.branchId = i.branchId AND c.igmNo = i.igmNo AND c.igmTransId = i.igmTransId "
				+ "LEFT JOIN Party p ON i.companyId = p.companyId AND i.branchId = p.branchId AND i.shippingLine = p.partyId "
				+ "LEFT OUTER JOIN DestuffCrg crg ON c.companyId = crg.companyId AND c.branchId = crg.branchId AND c.igmNo = crg.igmNo AND c.igmTransId = crg.igmTransId and c.igmLineNo = crg.igmLineNo "
				+ "where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.igmLineNo=:line "
				+ "and c.status != 'D'")
		Object[] getDataByIgmAndLineForDestuff(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,@Param("line") String line);

		@Query(value="select COUNT(c)>0 from Cfigmcrg c where c.companyId=:cid and c.branchId=:bid and "
				+ "((c.igmNo!=:igm and c.igmLineNo!=:line) OR (c.igmNo=:igm and c.igmLineNo!=:line)) "
				+ "and c.beNo=:be and c.status != 'D'")
		Boolean isExistBENo(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,@Param("line") String line,
				@Param("be") String be);
		
		@Query("select NEW com.cwms.entities.Cfigmcrg(c.companyId, c.branchId, c.igmTransId, c.igmCrgTransId, "
			      + " c.profitcentreId, c.igmLineNo, c.igmNo, c.blNo, c.blDate, c.importerName, "
			      + " c.commodityDescription, c.accountHolderName, c.beNo, c.beDate, c.chaName, "
			      + " i.igmDate, py1.partyName, py2.partyName) from Cfigmcrg c "
			      + " LEFT OUTER JOIN CFIgm i ON c.companyId = i.companyId and c.branchId = i.branchId and c.igmTransId = i.igmTransId and c.igmNo = i.igmNo "
			      + " LEFT OUTER JOIN Party py1 ON i.companyId = py1.companyId AND i.branchId = py1.branchId AND i.shippingAgent = py1.partyId "
			      + " LEFT OUTER JOIN Party py2 ON i.companyId = py2.companyId AND i.branchId = py2.branchId AND i.shippingLine = py2.partyId "
			      + " and c.igmNo = i.igmNo "  // Added space at the end
			      + " where c.companyId = :cid "
			      + " and c.branchId = :bid "
			      + " and c.igmNo = :igm "
			      + " and c.igmLineNo = :igmCrgTransId "
			      + " and c.status != 'D'")
			Cfigmcrg getDataforSSR(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
			                       @Param("igmCrgTransId") String igmLineNo);
		
		
		@Query("select NEW com.cwms.entities.Cfigmcrg(c.companyId, c.branchId, c.igmTransId, c.igmCrgTransId, "
			      + " c.profitcentreId, c.igmLineNo, c.igmNo, c.blNo, c.blDate, c.importerName, "
			      + " c.commodityDescription, c.accountHolderName, c.beNo, c.beDate, c.chaName, "
			      + " i.igmDate, py1.partyName, py2.partyName) from Cfigmcrg c "
			      + " LEFT OUTER JOIN CFIgm i ON c.companyId = i.companyId and c.branchId = i.branchId and c.igmTransId = i.igmTransId and c.igmNo = i.igmNo "
			      + " LEFT OUTER JOIN Party py1 ON i.companyId = py1.companyId AND i.branchId = py1.branchId AND i.shippingAgent = py1.partyId "
			      + " LEFT OUTER JOIN Party py2 ON i.companyId = py2.companyId AND i.branchId = py2.branchId AND i.shippingLine = py2.partyId "
			      + " and c.igmNo = i.igmNo "  // Added space at the end
				  + "where c.companyId=:cid and c.branchId=:bid and c.igmTransId =:igmtrans and "
				  + "c.igmNo=:igm and c.igmLineNo=:igmCrgTransId and c.status != 'D'")
		Cfigmcrg getDataforSSR1(@Param("cid") String cid, @Param("bid") String bid, @Param("igmtrans") String igmtrans,
				@Param("igm") String igm, @Param("igmCrgTransId") String igmLineNo);
		
		
		
		
		@Query("select c.igmNo,DATE_FORMAT(igm.igmDate,'%d/%m/%Y %H:%i'),c.igmLineNo,v.vesselName,c.typeOfPackage,c.chaName," +
		           "c.importerName,p1.partyName,c.mobileNo " +
			       "from Cfigmcrg c " +
				   "LEFT OUTER JOIN CFIgm igm ON c.companyId=igm.companyId and c.branchId=igm.branchId and c.igmNo=igm.igmNo and c.igmTransId=igm.igmTransId " +
			       "LEFT OUTER JOIN Vessel v ON igm.companyId=v.companyId and igm.branchId=v.branchId and igm.vesselId=v.vesselId " +
				   "LEFT OUTER JOIN Party p1 ON igm.companyId=p1.companyId and igm.branchId=p1.branchId and igm.shippingAgent=p1.partyId " +
				   "where c.companyId = :cid " +
			       "and c.branchId = :bid " +
			       "and c.igmNo = :igm " +
			       "and c.igmLineNo = :line " +
			       "and c.status != 'D'")
			Object getDataForSealCuttingItemwiseReport(@Param("cid") String cid,
			                  @Param("bid") String bid,
			                  @Param("igm") String igm,
			                  @Param("line") String igmLineNo);
		
		
		@Modifying
		@Transactional
		@Query(value="Update Cfigmcrg c "
				+ "SET c.cargoValue=:val,c.cargoDuty=:duty where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and "
				+ "c.igmTransId=:trans and c.igmLineNo=:line and c.status = 'A'")  
		int updateAssessmentData(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm,
                @Param("trans") String trans,
                @Param("line") String igmLineNo,@Param("val") BigDecimal val,@Param("duty") BigDecimal duty);
		
		@Query(value="select DISTINCT COALESCE(c.invoiceAssesed,'N') from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and "
				+ "c.igmTransId=:trans and c.igmNo=:igm and c.igmLineNo=:line and c.status='A' and c.invoiceAssesed='Y'")
		String getInvoiceDoneStatus(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("trans") String trans,
                @Param("igm") String igm,
                @Param("line") String igmLineNo);
		
}
