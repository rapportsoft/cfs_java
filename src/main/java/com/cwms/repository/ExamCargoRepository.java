package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ExamCrg;

public interface ExamCargoRepository extends JpaRepository<ExamCrg, String> {

	@Query(value="select e from ExamCrg e where e.companyId=:cid and e.branchId=:bid and e.examTallyId=:examId and e.examTallyLineId=:examLineId "
			+ "and e.status != 'D'")
	ExamCrg getSingleData(@Param("cid") String cid,@Param("bid") String bid,@Param("examId") String examId,@Param("examLineId") String examLineId);
	
	
	@Query(value="select e from ExamCrg e where e.companyId=:cid and e.branchId=:bid and e.igmNo=:igm and e.igmTransId=:trans "
			+ "and e.igmLineNo=:line and e.examTallyId=:examId and e.status != 'D'")
	List<ExamCrg> getData(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
			@Param("line") String line,@Param("examId") String examId);
	

	@Query(value="select e from ExamCrg e where e.companyId=:cid and e.branchId=:bid and e.igmNo=:igm and e.igmTransId=:trans "
			+ "and e.igmLineNo=:line  and e.status != 'D'")
	List<ExamCrg> getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
			@Param("line") String line);
	
	@Query(value="select e from ExamCrg e where e.companyId=:cid and e.branchId=:bid and e.igmNo=:igm and e.igmTransId=:trans "
			+ "and e.examTallyId=:exam  and e.status != 'D'")
	List<ExamCrg> getData2(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
			@Param("exam") String exam);
	
	@Query(value="select distinct e.examTallyId,DATE_FORMAT(e.examTallyDate,'%d %M %Y'),e.igmNo,e.igmTransId,p.profitcentreDesc,"
			+ "e.beNo ,DATE_FORMAT(e.beDate,'%d %M %Y') "
			+ "from ExamCrg e LEFT OUTER JOIN Profitcentre p ON e.companyId=p.companyId and e.branchId=p.branchId "
			+ "and e.profitCentreId=p.profitcentreId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(:val is null OR :val = '' OR e.examTallyId LIKE CONCAT ('%',:val,'%') OR e.igmNo LIKE CONCAT ('%',:val,'%') "
			+ "OR e.igmTransId LIKE CONCAT ('%',:val,'%') OR e.beNo LIKE CONCAT ('%',:val,'%')) order by e.examTallyId desc")
	List<Object[]> search(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
}
