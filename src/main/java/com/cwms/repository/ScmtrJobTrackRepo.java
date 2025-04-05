package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ScmtrJobTrack;

public interface ScmtrJobTrackRepo extends JpaRepository<ScmtrJobTrack, String> {

	@Query(value="select s from ScmtrJobTrack s "
			+ "where s.companyId=:cid and s.branchId=:bid and s.status='A' and s.sbTransId=:transId and s.sbNo=:sb and s.stuffTallyId=:id "
			+ "and s.containerNo=:con and s.messageId=:messageId")
	ScmtrJobTrack getDataById(@Param("cid") String cid,@Param("bid") String bid,@Param("transId") String transId,@Param("sb") String sb,
			@Param("id") String id,@Param("con") String con,@Param("messageId") String messageId);
	
	@Transactional
	@Modifying
	@Query(value="Update ScmtrJobTrack e SET e.isAck=:isAck, e.ackStatus=:ackSf where e.companyId=:cid and e.branchId=:bid and "
			+ "e.status='A' and e.jobNo=:id and e.stuffTallyId=:tallyId and e.messageId=:messageId")
	int updateSFStatus(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("tallyId") String tallyId,
			@Param("isAck") String isAck,@Param("ackSf") String ackSf, @Param("messageId") String messageId);
	
	
}
