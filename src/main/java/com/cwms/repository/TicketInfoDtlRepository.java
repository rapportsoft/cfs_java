package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.TicketInfoDtl;

public interface TicketInfoDtlRepository extends JpaRepository<TicketInfoDtl, String> {

	@Query(value="select a.assigneeId,a.assigneeName from AssigneeMaster a "
			+ "WHERE a.companyId=:cid and a.branchId=:bid and a.status='A'")
	List<Object[]> getAssigneeData(@Param("cid") String cid,@Param("bid") String bid);
	
	
	@Query(value="select a.approverId,a.approverName from ApproverMaster a "
			+ "WHERE a.companyId=:cid and a.branchId=:bid and a.status='A'")
	List<Object[]> getApproverData(@Param("cid") String cid,@Param("bid") String bid);
	
	
	@Query(value="select a.User_Id,a.User_Name from User a "
			+ "WHERE a.Company_Id=:cid and a.Branch_Id=:bid and a.Status='A'")
	List<Object[]> getFollowers(@Param("cid") String cid,@Param("bid") String bid);
	
	@Query(value="select COUNT(a.ticketNo) from TicketInfoDtl a where a.companyId=:cid and a.branchId=:bid and a.ticketNo=:id")
	int totalSrNo(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select New com.cwms.entities.TicketInfoDtl(t.ticketNo, t.srNo, t.ticketStatus, t.messageFrom, t.message,"
			+ "t.attachedFiles, t.status, t.createdBy, t.createdDate) from TicketInfoDtl t "
			+ "where t.companyId=:cid and t.branchId=:bid and t.ticketNo=:id and t.status != 'D' order by t.createdDate desc")
	List<TicketInfoDtl> getSavedData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}
