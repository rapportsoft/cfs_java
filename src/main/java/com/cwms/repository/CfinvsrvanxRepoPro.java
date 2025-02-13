package com.cwms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.CfinvsrvanxPro;

public interface CfinvsrvanxRepoPro extends JpaRepository<CfinvsrvanxPro, String>{
	
	@Query(value="select a from CfinvsrvanxPro a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.processTransId=:id")
	List<CfinvsrvanxPro> getDataByProcessTransId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);

}
