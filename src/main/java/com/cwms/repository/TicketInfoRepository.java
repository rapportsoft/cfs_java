package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.TicketInfo;

public interface TicketInfoRepository extends JpaRepository<TicketInfo, String> {

	@Query(value = "select NEW com.cwms.entities.TicketInfo(t.ticketNo, t.ticketStatus, t.requester, a.assigneeName, t.priority,"
			+ "t.subject, t.createdBy, t.createdDate) from TicketInfo t "
			+ "LEFT OUTER JOIN AssigneeMaster a ON t.companyId=a.companyId and t.branchId=a.branchId and t.assignee=a.assigneeId "
			+ "where t.companyId=:cid and t.branchId=:bid and t.status != 'D' "
			+ "and (t.createdBy=:id OR t.approvedBy=:id ) order by t.createdDate desc")
	List<TicketInfo> getData(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);

	@Query(value = "select NEW com.cwms.entities.TicketInfo(t.ticketNo, t.ticketStatus, t.requester, a.assigneeName, t.priority,"
			+ "t.subject, t.createdBy, t.createdDate) from TicketInfo t "
			+ "LEFT OUTER JOIN AssigneeMaster a ON t.companyId=a.companyId and t.branchId=a.branchId and t.assignee=a.assigneeId "
			+ "LEFT OUTER JOIN ApproverMaster m ON t.companyId=m.companyId and t.branchId=m.branchId and t.approver=m.approverId "
			+ "where t.companyId=:cid and t.branchId=:bid and t.status = 'N' "
			+ "and m.userId=:id order by t.createdDate desc")
	List<TicketInfo> getPendingData(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
	
	
	@Query(value = "select NEW com.cwms.entities.TicketInfo(t.ticketNo, t.ticketStatus, t.requester, a.assigneeName, t.priority,"
			+ "t.subject, t.createdBy, t.createdDate) from TicketInfo t "
			+ "LEFT OUTER JOIN AssigneeMaster a ON t.companyId=a.companyId and t.branchId=a.branchId and t.assignee=a.assigneeId "
			+ "LEFT OUTER JOIN ApproverMaster m ON t.companyId=m.companyId and t.branchId=m.branchId and t.approver=m.approverId "
			+ "where t.companyId=:cid and t.branchId=:bid and t.status != 'D' order by t.createdDate desc")
	List<TicketInfo> getAllTickets(@Param("cid") String cid, @Param("bid") String bid);
	
	@Query(value = "select NEW com.cwms.entities.TicketInfo(t.ticketNo, t.ticketStatus, t.requester, a.assigneeName, t.priority,"
			+ "t.subject, t.createdBy, t.createdDate) from TicketInfo t "
			+ "LEFT OUTER JOIN AssigneeMaster a ON t.companyId=a.companyId and t.branchId=a.branchId and t.assignee=a.assigneeId "
			+ "LEFT OUTER JOIN ApproverMaster m ON t.companyId=m.companyId and t.branchId=m.branchId and t.approver=m.approverId "
			+ "where t.companyId=:cid and t.branchId=:bid and t.status = 'A' and t.ticketStatus = 'Solved' order by t.createdDate desc")
	List<TicketInfo> getResolvedTickets(@Param("cid") String cid, @Param("bid") String bid);

	@Query(value = "select t from TicketInfo t "
			+ "where t.companyId=:cid and t.branchId=:bid and t.status != 'D' and t.ticketNo=:id")
	TicketInfo getDataByTicketId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
}
