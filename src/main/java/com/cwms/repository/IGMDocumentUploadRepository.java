package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.IgmDocumentUpload;

public interface IGMDocumentUploadRepository extends JpaRepository<IgmDocumentUpload, String>  {

	@Query(value="select COALESCE(COUNT(i),0) from IgmDocumentUpload i where i.companyId=:cid and i.branchId=:bid and "
			+ "i.status != 'D' and i.igmTransId=:trans and i.igmNo=:igm and i.igmLineNo=:line")
	int srNo(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String line);
	
	@Query(value="select i from IgmDocumentUpload i where i.companyId=:cid and i.branchId=:bid and "
			+ "i.status != 'D' and i.igmTransId=:trans and i.igmNo=:igm and i.igmLineNo=:line")
	List<IgmDocumentUpload> getData(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String line);
	
	@Query(value="select i from IgmDocumentUpload i where i.companyId=:cid and i.branchId=:bid and "
			+ "i.status != 'D' and i.igmTransId=:trans and i.igmNo=:igm and i.igmLineNo=:line and i.srNo=:sr")
	IgmDocumentUpload getSingleData(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String line,@Param("sr") int srNO);
}
