package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.SplitTallyPkg;

public interface SplitTallyPkgRepo extends JpaRepository<SplitTallyPkg, String> {

	@Query(value="select s.stuffTallyId,s.sbTransId,s.sbNo,s.containerNo,s.fromPkg,s.toPkg,s.qty "
			+ "from SplitTallyPkg s "
			+ "where s.companyId=:cid and s.branchId=:bid and s.stuffTallyId=:tally and s.sbTransId=:trans and s.sbNo=:sb and "
			+ "s.containerNo=:con and s.status = 'A'")
	List<Object[]> getData(@Param("cid") String cid,@Param("bid") String bid,@Param("tally") String tally,@Param("trans") String trans,
			@Param("sb") String sb,@Param("con") String con);
}
