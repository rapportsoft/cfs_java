package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.GateOut;

@Repository
public interface GateOutRepo extends JpaRepository<GateOut, String>{


	@Query(value="select g.gateOutId, DATE_FORMAT(g.gateOutDate, '%d %M %y'), g.gatePassNo, DATE_FORMAT(g.gatePassDate, '%d %M %y'), "
            + "g.containerNo, g.vehicleNo "
            + "from GateOut g where g.companyId = :cid and g.branchId = :bid and g.status != 'D' and g.processId = 'P00223' "
            + "and (:val is null OR :val = '' OR g.gateOutId LIKE CONCAT('%', :val, '%') OR g.gatePassNo LIKE CONCAT('%', :val, '%') "
            + "OR g.containerNo LIKE CONCAT('%', :val, '%') OR g.vehicleNo LIKE CONCAT('%', :val, '%')) "
            + "ORDER BY g.gateOutDate desc")
List<Object[]> searchExportGateOut(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);

	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GateOut(c.companyId, c.branchId, c.finYear, c.gateOutId, " +
	        "c.erpDocRefNo, c.docRefNo, c.srNo, c.gateOutDate, c.shift, c.gatePassNo, " +
	        "c.commodityDescription, c.grossWt, c.vehicleNo, c.driverName, " +
	        "c.deliveryOrderNo, c.deliveryOrderDate, c.doValidityDate, c.exBondBeNo, " +
	        "c.comments, c.transporterStatus, c.transporter, c.transporterName, c.tripType, " +
	        "c.gatePassDate, c.gateNoIn, c.createdBy, c.approvedBy, c.status,c.profitcentreId,c.driverContactNo,c.licenceNo) " +
	    "FROM GateOut c " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.processId='P00500' "+
	    "AND c.status != 'D' " +
	    "AND ((:partyName IS NULL OR :partyName = '' OR c.gateOutId LIKE concat(:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.gatePassNo LIKE concat(:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.erpDocRefNo LIKE concat(:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.docRefNo LIKE concat(:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.exBondBeNo LIKE concat(:partyName, '%'))) " +
	        "GROUP BY c.gateOutId " +
	    "ORDER BY c.gateOutId DESC")
	List<GateOut> findDataOfGateOutDetails(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId,
	    @Param("partyName") String partyName);

	@Query("select c from GateOut c where c.companyId=:companyId and c.branchId=:branchId and c.gateOutId =:gateOutId and c.commodity =:commodity and c.srNo =:srNo and c.status !='D'")
	GateOut getExistingDataOfGateOut(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("gateOutId") String gateOutId, @Param("commodity") String commodity, @Param("srNo") String srNo);
	
	
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GateOut(c.companyId, c.branchId, c.finYear, c.gateOutId, " +
	        "c.erpDocRefNo, c.docRefNo, c.srNo, c.gateOutDate, c.shift, c.gatePassNo, " +
	        "c.commodityDescription, c.grossWt, c.vehicleNo, c.driverName, " +
	        "c.deliveryOrderNo, c.deliveryOrderDate, c.doValidityDate, c.exBondBeNo, " +
	        "c.comments, c.transporterStatus, c.transporter, c.transporterName, c.tripType, " +
	        "c.gatePassDate, c.gateNoIn, c.createdBy, c.approvedBy, c.status,c.profitcentreId,c.driverContactNo,c.licenceNo) " +
	    "FROM GateOut c " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.gateOutId = :gateOutId " +
	    "AND c.exBondBeNo = :exBondBeNo " +
	    "AND c.status != 'D' " +
	    "ORDER BY c.gateOutId DESC")
	List<GateOut> getAllListOfGateOut(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId,
	    @Param("gateOutId") String gateOutId,
	    @Param("exBondBeNo") String exBondBeNo);
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GateOut(c.companyId, c.branchId, c.finYear, c.gateOutId, " +
	        "c.erpDocRefNo, c.docRefNo, c.srNo, c.igmLineNo, c.grossWt, c.natureOfCargo, c.actualNoOfPackages, " +
	        "c.qtyTakenOut, c.gatePassNo,c.commodityDescription) " +
	    "FROM GateOut c " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.gateOutId = :gateOutId " +
	    "AND c.status != 'D' " +
	    "AND c.vehicleNo = :vehicleNo " +
	    "ORDER BY c.gateOutId DESC")
	List<GateOut> getCommodityDetailsOfVehicleNo(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId, 
	    @Param("gateOutId") String gateOutId, 
	    @Param("vehicleNo") String vehicleNo);

	
	
	@Query("SELECT NEW com.cwms.entities.GateOut(c.gateOutId, c.transType, c.gateOutDate, c.gateNoOut, p.partyName," +
		    "c.transporterStatus, c.transporterName, c.vehicleNo, c.driverName, c.gatePassNo," +
	        "c.gatePassDate, c.comments, c.status, c.createdBy) " +
		    "FROM GateOut c " +
	        "LEFT OUTER JOIN Party p ON c.companyId=p.companyId and c.branchId=p.branchId and c.sl=p.partyId " +
		    "WHERE c.companyId = :companyId " +
		    "AND c.branchId = :branchId " +
		    "AND c.gateOutId = :gateOutId " +
		    "AND c.status != 'D' ")
		List<GateOut> getEportGateOutData(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("gateOutId") String gateOutId);

		
//		@Query(value="select g.gateOutId,DATE_FORMAT(g.gateOutDate,'%d %M %y'),g.gatePassNo,DATE_FORMAT(g.gatePassDate,'%d %M %y'),"
//				+ "g.containerNo,g.vehicleNo "
//				+ "from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and g.processId='P00223' "
//				+ "and (:val is null OR :val = '' OR g.gateOutId LIKE CONCAT('%',:val,'%') OR g.gatePassNo LIKE CONCAT('%',:val,'%') "
//				+ "OR g.containerNo LIKE CONCAT('%',:val,'%') OR g.vehicleNo LIKE CONCAT('%',:val,'%'))")
//		List<Object[]> searchExportGateOut(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select distinct g.containerNo from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and "
			+ "(g.containerNo != '' AND g.containerNo is not null) and g.processId='P00223' and (g.portReturnFlag is null OR "
			+ "g.portReturnFlag = '' OR g.portReturnFlag = 'N') "
			+ "and (:val is null OR :val = '' OR g.containerNo LIKE CONCAT('%',:val,'%'))")
	List<String> getContainerNosForPortReturn(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select NEW com.cwms.entities.GateOut(g.gateOutId, g.erpDocRefNo, g.docRefNo, g.onAccountOf, g.containerNo,"
			+ "g.containerSize, g.containerType, g.containerStatus, g.isoCode, g.sa,"
			+ "g.sl, g.commodityDescription, g.grossWt, g.transporterStatus, g.transporter,"
			+ "g.transporterName, g.deliveryOrderNo, g.deliveryOrderDate, g.doValidityDate,"
			+ "p3.partyName, p1.partyName, p2.partyName) "
			+ "from GateOut g "
			+ "LEFT OUTER JOIN Party p1 ON g.companyId=p1.companyId and g.branchId=p1.branchId and g.sa=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON g.companyId=p2.companyId and g.branchId=p2.branchId and g.sl=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON g.companyId=p3.companyId and g.branchId=p3.branchId and g.onAccountOf=p3.partyId "
			+ "where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and "
			+ "(g.containerNo != '' AND g.containerNo is not null) and g.processId='P00223' and (g.portReturnFlag is null OR "
			+ "g.portReturnFlag = '' OR g.portReturnFlag = 'N') and g.containerNo=:con order by g.createdDate desc LIMIT 1")
	GateOut getSingleContainerNosForPortReturn(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
	
	
	@Query(value="select g "
			+ "from GateOut g "
			+ "where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and "
			+ "(g.containerNo != '' AND g.containerNo is not null) and g.processId='P00223' and (g.portReturnFlag is null OR "
			+ "g.portReturnFlag = '' OR g.portReturnFlag = 'N') and g.containerNo=:con order by g.createdDate desc LIMIT 1")
	GateOut getSingleContainerNosForPortReturn1(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
	
	@Query("select c from GateOut c "
			+ "where c.companyId=:companyId and c.branchId=:branchId and c.gateOutId =:gateOutId and c.srNo =:srNo and c.status = 'A'")
	GateOut getGateOutData(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("gateOutId") String gateOutId, @Param("srNo") String srNo);
	
	
	@Query("select c.gateOutId,c.gateOutDate,c.shift,c.gateNoOut,c.status,c.createdBy,c.gatePassNo,c.gatePassDate,c.docRefNo,c.igmLineNo,"
			+ "c.transporterName,p.profitcentreDesc,c.deliveryOrderNo,c.deliveryOrderDate,c.doValidityDate,c.vehicleNo,c.driverName,"
			+ "c.qtyTakenOut,c.comments "
			+ "from GateOut c "
			+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
			+ "where c.companyId=:companyId and c.branchId=:branchId and c.gateOutId =:gateOutId and c.srNo =:srNo and c.status = 'A'")
	Object getAfterSaveGateOutData(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("gateOutId") String gateOutId, @Param("srNo") String srNo);
	
	@Query(value="select i.gateOutId,DATE_FORMAT(i.gateOutDate,'%d/%m/%Y %H:%i'),i.gatePassNo,DATE_FORMAT(i.gatePassDate,'%d/%m/%Y %H:%i'),"
			+ "i.docRefNo,i.igmLineNo,i.transporterName,i.vehicleNo,i.driverName "
			+ "from GateOut i "
			+ "where i.companyId=:cid and i.branchId=:bid and i.status = 'A' and (:id is null OR :id = '' OR "
			+ "i.gateOutId LIKE CONCAT(:id,'%') OR i.gatePassNo LIKE CONCAT(:id,'%') OR i.docRefNo LIKE CONCAT(:id,'%') "
			+ "OR i.igmLineNo LIKE CONCAT(:id,'%') OR i.transporterName LIKE CONCAT(:id,'%') OR i.vehicleNo LIKE CONCAT(:id,'%') "
			+ "OR i.driverName LIKE CONCAT(:id,'%')) and i.processId='P01408' order by i.gateOutDate desc")
	List<Object[]> searchAuctionGateOutData(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
	
	
	@Query(value="select g.gateOutId,g.containerNo,g.docRefNo,g.srNo "
			+ "from GateOut g "
			+ "where g.companyId=:cid and g.branchId=:bid and g.status = 'A' and (g.eirNo is null OR g.eirNo = '') and "
			+ "g.processId='P00223' and g.profitcentreId='N00004' and (:id is null OR :id = '' OR "
			+ "g.gateOutId LIKE CONCAT(:id,'%') OR g.containerNo LIKE CONCAT(:id,'%') OR g.docRefNo LIKE CONCAT(:id,'%')) order by g.gateOutId")
	List<Object[]> getDataBeforeSavePortEIR(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
	
	@Query(value="select g.eirNo,g.eirDate,g.gateNoOut,p.profitcentreDesc,g.eirStatus,g.eirCreatedBy,g.gateOutId,g.gateOutDate,g.srNo,"
			+ "g.docRefNo,g.erpDocRefNo,c.partyName,g.containerNo,g.containerSize,g.containerType,g.transporterStatus,g.transporterName,"
			+ "g.vehicleNo,g.driverName "
			+ "from GateOut g "
			+ "LEFT OUTER JOIN Profitcentre p ON g.companyId=p.companyId and g.branchId=p.branchId and g.profitcentreId=p.profitcentreId "
			+ "LEFT OUTER JOIN Party c ON g.companyId=c.companyId and g.branchId=c.branchId and g.cha=c.partyId "
			+ "where g.companyId=:cid and g.branchId=:bid and g.status = 'A' and g.gateOutId=:id and g.srNo=:sr")
	Object gateOutDataFOrEirENtry(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id, @Param("sr") String sr);
	
	@Query(value="select g "
			+ "from GateOut g "
			+ "where g.companyId=:cid and g.branchId=:bid and g.status = 'A' and g.gateOutId=:id and g.srNo=:sr")
	GateOut gateOutDataFOrEirENtry1(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id, @Param("sr") String sr);
	
	@Query(value="select g.eirNo,DATE_FORMAT(g.eirDate,'%d/%m/%Y'),g.gateOutId,DATE_FORMAT(g.gateOutDate,'%d/%m/%Y'),g.srNo,"
			+ "g.gatePassNo,g.containerNo,g.containerSize,g.containerType,g.transporterName,g.vehicleNo,g.driverName,g.eirApprovedDate "
			+ "from GateOut g "
			+ "where g.companyId=:cid and g.branchId=:bid and g.status = 'A' and (g.eirNo is not null OR g.eirNo != '') and "
			+ "g.processId='P00223' and g.profitcentreId='N00004' and (:id is null OR :id = '' OR "
			+ "g.gateOutId LIKE CONCAT(:id,'%') OR g.containerNo LIKE CONCAT(:id,'%') OR g.docRefNo LIKE CONCAT(:id,'%') "
			+ "OR g.eirNo LIKE CONCAT(:id,'%') OR g.containerSize LIKE CONCAT(:id,'%') OR g.containerType LIKE CONCAT(:id,'%') "
			+ "OR g.transporterName LIKE CONCAT(:id,'%') OR g.vehicleNo LIKE CONCAT(:id,'%') OR g.driverName LIKE CONCAT(:id,'%')) "
			+ "order by g.eirApprovedDate desc")
	List<Object[]> searchData(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
}