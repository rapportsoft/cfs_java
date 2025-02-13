package com.cwms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.AssessmentSheetPro;

public interface AssessmentSheetRepoPro extends JpaRepository<AssessmentSheetPro, String>{
		
	@Query(value="select a from AssessmentSheetPro a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id "
			+ "and (a.invoiceNo = '' OR a.invoiceNo is null)")
	List<AssessmentSheetPro> getDataByAssessmentId1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	

}
