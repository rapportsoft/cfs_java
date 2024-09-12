package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CFIgm;

public interface CfIgmRepository extends JpaRepository<CFIgm, String> {

	@Query(value="select COUNT(c)>0 from CFIgm c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.status !='D'")
	Boolean isDataExist(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm);
	
	
	@Query(value="select COUNT(c)>0 from CFIgm c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm "
			+ "and c.igmTransId!=:trans and c.status !='D'")
	Boolean isDataExist1(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans);
	
	
	@Query(value="select c.igmTransId,DATE_FORMAT(c.docDate,'%d/%M/%Y'),c.igmNo,DATE_FORMAT(c.igmDate,'%d/%M/%Y'), "
			+ "pc.profitcentreDesc,c.viaNo,v.vesselName,c.voyageNo,DATE_FORMAT(c.vesselArvDate,'%d/%M/%Y'),c.port, "
			+ "po.portName,c.status,p.partyName,p.customerCode,p1.partyName,p1.customerCode from CFIgm c LEFT JOIN Profitcentre pc ON c.companyId = pc.companyId and "
			+ "c.branchId = pc.branchId and c.profitcentreId = pc.profitcentreId LEFT JOIN Vessel v ON c.companyId = v.companyId "
			+ "and c.branchId=v.branchId and c.vesselId = v.vesselId LEFT JOIN Port po ON c.companyId = po.companyId and "
			+ "c.branchId = po.branchId and c.port = po.portCode LEFT JOIN Party p ON c.companyId = p.companyId and "
			+ "c.branchId=p.branchId and c.shippingLine = p.partyId LEFT JOIN Party p1 ON c.companyId = p1.companyId and "
			+ "c.branchId=p1.branchId and c.shippingAgent = p1.partyId where c.companyId=:cid and c.branchId=:bid and "
			+ "((:search is null OR :search = ''OR c.igmTransId like CONCAT('%',:search,'%')) "
			+ "OR (:search is null OR :search = ''OR c.igmNo like CONCAT('%',:search,'%'))) order by c.igmTransId desc")
	List<Object[]> getSearchData(@Param("cid") String cid,@Param("bid") String bid,@Param("search") String search);
	
	
	@Query(value="select c from CFIgm c where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:transId "
			+ "and c.igmNo=:igmNo and c.status != 'D'")
	CFIgm getDataByIgmNoAndtrans(@Param("cid") String cid,@Param("bid") String bid,@Param("transId") String transId,
			@Param("igmNo") String igmNo);
	
	@Query(value="select New com.cwms.entities.CFIgm(c.igmTransId, p.profitcentreDesc, c.igmNo, c.igmDate, c.viaNo, c.voyageNo,"
			+ "	c.shippingLine, c.shippingAgent) from CFIgm c "
			+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
			+ "where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:transId "
			+ "and c.igmNo=:igmNo and c.status != 'D'")
	CFIgm getDataByIgmNoAndtrans1(@Param("cid") String cid,@Param("bid") String bid,@Param("transId") String transId,
			@Param("igmNo") String igmNo);
	
	
	@Query(value="select c from CFIgm c where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:transId "
			+ "and c.status != 'D'")
	CFIgm getDataByIgmNo(@Param("cid") String cid,@Param("bid") String bid,@Param("transId") String transId);
	
	@Query(value="select c from CFIgm c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:transId "
			+ "and c.status != 'D' order by c.createdDate desc limit 1")
	CFIgm getDataByIgmNo2(@Param("cid") String cid,@Param("bid") String bid,@Param("transId") String transId);
	
	@Query(value="select c from CFIgm c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm "
			+ "and c.status != 'D'")
	CFIgm getDataByIgmNo1(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm);
	
	@Query(value="select c.igmTransId,DATE_FORMAT(c.docDate,'%d/%M/%Y'),c.igmNo,DATE_FORMAT(c.igmDate,'%d/%M/%Y'), "
			+ "pc.profitcentreDesc,c.viaNo,v.vesselName,c.voyageNo,DATE_FORMAT(c.vesselArvDate,'%d/%M/%Y'),c.port, "
			+ "po.portName,c.status,p.partyName,p.customerCode,p1.partyName,p1.customerCode from CFIgm c LEFT JOIN Profitcentre pc ON c.companyId = pc.companyId and "
			+ "c.branchId = pc.branchId and c.profitcentreId = pc.profitcentreId LEFT JOIN Vessel v ON c.companyId = v.companyId "
			+ "and c.branchId=v.branchId and c.vesselId = v.vesselId LEFT JOIN Port po ON c.companyId = po.companyId and "
			+ "c.branchId = po.branchId and c.port = po.portCode LEFT JOIN Party p ON c.companyId = p.companyId and "
			+ "c.branchId=p.branchId and c.shippingLine = p.partyId LEFT JOIN Party p1 ON c.companyId = p1.companyId and "
			+ "c.branchId=p1.branchId and c.shippingAgent = p1.partyId where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:igm and c.status !=''")
	Object[] getSearchData2(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm);
	
	@Query(value="select c.igmTransId,DATE_FORMAT(c.docDate,'%d/%M/%Y'),c.igmNo,DATE_FORMAT(c.igmDate,'%d/%M/%Y'), "
			+ "pc.profitcentreDesc,c.viaNo,v.vesselName,c.voyageNo,DATE_FORMAT(c.vesselArvDate,'%d/%M/%Y'),c.port, "
			+ "po.portName,c.status,p.partyName,p.customerCode,p1.partyName,p1.customerCode from CFIgm c LEFT JOIN Profitcentre pc ON c.companyId = pc.companyId and "
			+ "c.branchId = pc.branchId and c.profitcentreId = pc.profitcentreId LEFT JOIN Vessel v ON c.companyId = v.companyId "
			+ "and c.branchId=v.branchId and c.vesselId = v.vesselId LEFT JOIN Port po ON c.companyId = po.companyId and "
			+ "c.branchId = po.branchId and c.port = po.portCode LEFT JOIN Party p ON c.companyId = p.companyId and "
			+ "c.branchId=p.branchId and c.shippingLine = p.partyId LEFT JOIN Party p1 ON c.companyId = p1.companyId and "
			+ "c.branchId=p1.branchId and c.shippingAgent = p1.partyId where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.status !=''")
	Object[] getSearchData1(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm);
	
	@Query(value="select c.igmTransId,DATE_FORMAT(c.docDate,'%d/%M/%Y'),c.igmNo,DATE_FORMAT(c.igmDate,'%d/%M/%Y'), "
			+ "pc.profitcentreDesc,c.viaNo,v.vesselName,c.voyageNo,DATE_FORMAT(c.vesselArvDate,'%d/%M/%Y'),c.port, "
			+ "po.portName,c.status,p.partyName,p.customerCode,p1.partyName,p1.customerCode from CFIgm c LEFT JOIN Profitcentre pc ON c.companyId = pc.companyId and "
			+ "c.branchId = pc.branchId and c.profitcentreId = pc.profitcentreId LEFT JOIN Vessel v ON c.companyId = v.companyId "
			+ "and c.branchId=v.branchId and c.vesselId = v.vesselId LEFT JOIN Port po ON c.companyId = po.companyId and "
			+ "c.branchId = po.branchId and c.port = po.portCode LEFT JOIN Party p ON c.companyId = p.companyId and "
			+ "c.branchId=p.branchId and c.shippingLine = p.partyId LEFT JOIN Party p1 ON c.companyId = p1.companyId and "
			+ "c.branchId=p1.branchId and c.shippingAgent = p1.partyId LEFT JOIN Cfigmcn cn ON c.companyId = cn.companyId and c.branchId=cn.branchId "
			+ "and c.igmNo=cn.igmNo and c.igmTransId=cn.igmTransId where cn.companyId=:cid and cn.branchId=:bid and "
			+ "cn.containerNo=:container and cn.status != 'D' and c.status !='D' order by cn.createdDate desc limit 1")
	Object[] getDataByContainer(@Param("cid") String cid,@Param("bid") String bid,@Param("container") String container);
}
