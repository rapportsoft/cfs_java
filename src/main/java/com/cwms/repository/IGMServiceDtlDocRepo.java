package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.IgmDocumentUpload;
import com.cwms.entities.IgmServiceDtlDoc;

public interface IGMServiceDtlDocRepo extends JpaRepository<IgmServiceDtlDoc, String> {

	@Query(value = "select COUNT(i) from IgmServiceDtlDoc i where i.companyId=:cid and i.branchId=:bid and i.igmNo=:igm and "
			+ "i.igmTransId=:trans and i.igmLineNo=:line")
	int getLatestSrNo(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
			@Param("trans") String trans, @Param("line") String line);

	@Query(value = "select i.igmNo,i.igmTransId,i.igmLineNo,i.srNo,i.docPath "
			+ "from IgmServiceDtlDoc i where i.companyId=:cid and i.branchId=:bid and i.igmNo=:igm and "
			+ "i.igmTransId=:trans and i.igmLineNo=:line")
	List<Object[]> getDataByIgmDtls(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
			@Param("trans") String trans, @Param("line") String line);
	
	@Query(value="select i from IgmServiceDtlDoc i where i.companyId=:cid and i.branchId=:bid and "
			+ "i.status = 'A' and i.igmTransId=:trans and i.igmNo=:igm and i.igmLineNo=:line and i.srNo=:sr")
	IgmServiceDtlDoc getSingleData1(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String line,@Param("sr") int srNO);
	
	
	@Query(value = "select i.docPath "
			+ "from IgmServiceDtlDoc i where i.companyId=:cid and i.branchId=:bid and i.igmNo=:igm and "
			+ "i.igmTransId=:trans and i.igmLineNo=:line and i.status = 'A' and i.srNo=:sr")
	String getDataBySrNo(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
			@Param("trans") String trans, @Param("line") String line,@Param("sr") int srNO);
	
	@Query(value = "select i.igmNo,i.igmTransId,i.igmLineNo,i.srNo,i.docPath "
			+ "from IgmServiceDtlDoc i where i.companyId=:cid and i.branchId=:bid and i.igmNo=:igm and "
			+ "i.igmTransId=:trans")
	List<Object[]> getDataByNocDtls(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
			@Param("trans") String trans);
	
}
