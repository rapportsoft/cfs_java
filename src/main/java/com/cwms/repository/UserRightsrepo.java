package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.UserRights;

@EnableJpaRepositories
public interface UserRightsrepo extends JpaRepository<UserRights, String> {
	
	
	@Query(value = "SELECT NEW com.cwms.entities.UserRights(i.company_Id, i.branch_Id, i.user_Id, i.process_Id, i.allow_Read, i.allow_Create, i.allow_Update, i.allow_Delete, " +
            "(CASE " +
            "WHEN cm.Child_Menu_Name IS NOT NULL THEN cm.Child_Menu_Name " +
            "ELSE pm.PMenu_Name " +
            "END) AS status, " +
            "i.created_By, i.created_Date, i.edited_By, i.edited_Date, i.approved_By, i.approved_Date) " +
            "FROM UserRights i " +
            "LEFT JOIN ChildMenu cm ON i.process_Id = cm.processId " +
            "LEFT JOIN ParentMenu pm ON i.process_Id = pm.processId " +
            "WHERE i.company_Id = :cid " +
            "AND i.branch_Id = :bid " +
            "AND (COALESCE(:userId, '') = '' OR i.user_Id = :userId) " +
            "AND i.status <> 'D'")
List<UserRights> getUserRights(@Param("cid") String cid, 
                            @Param("bid") String bid, 
                            @Param("userId") String userId);


	

	@Query("SELECT NEW com.cwms.entities.UserRights(cm.process_Id, cm.allow_Read, cm.allow_Create, cm.allow_Update, cm.allow_Delete) FROM UserRights cm WHERE cm.company_Id = :cid AND cm.branch_Id = :bid AND cm.user_Id = :userId ")
	List<UserRights> getUserRightsByUserId(@Param("cid") String cid, @Param("bid") String bid, @Param("userId") String userId);

	
	
	
	
	
	
	

	@Query(value = "select * from userrights as u where u.user_id=:uid and u.company_id=:cid and u.branch_id=:bid", nativeQuery = true)
	public List<UserRights> findByUserId(@Param("uid") String uid,@Param("cid") String cid,@Param("bid") String bid);

	@Query(value = "select * from userrights as u where u.user_id=:uid", nativeQuery = true)
	public UserRights findByUserId2(@Param("uid") String uid);

	@Query(value = "select * from userrights as u where u.branch_id=:bid", nativeQuery = true)
	public List<UserRights> findBybid(@Param("bid") String bid);

	@Modifying
	@Query(value = "UPDATE userrights ur SET ur.allow_approve = :allowapprove, ur.allow_create = :allowcreate, ur.allow_delete = :allowdelete, ur.allow_read = :allowread, ur.allow_update = :allowupdate WHERE ur.user_id = :userid AND ur.process_id = :processid", nativeQuery = true)
	void updateUserrights(@Param("allowapprove") String allowapprove, @Param("allowcreate") String allowcreate,
			@Param("allowdelete") String allowdelete, @Param("allowread") String allowread,
			@Param("allowupdate") String allowupdate, @Param("userid") String userid,
			@Param("processid") String processid
			);

	 @Query(value = "SELECT * FROM userrights AS u WHERE u.user_id = :userId AND u.process_id = :processId ", nativeQuery = true)
	    public List<UserRights> findByUserIdAndProcessId(@Param("userId") String userId, @Param("processId") String processId);

	 @Query(value = "SELECT * FROM userrights AS u WHERE u.user_id = :userId AND u.process_id = :processId and u.company_id=:cid and u.branch_id=:bid", nativeQuery = true)
	    public List<UserRights> findByUserIdAndProcessIds(@Param("userId") String userId, @Param("processId") String processId,@Param("cid") String cid,@Param("bid") String bid);
	 
	 
	 @Query(value="Select ur.* from userrights ur LEFT JOIN pmenu p ON ur.process_id=p.process_id LEFT JOIN cmenu c ON p.process_id = c.pprocess_id where ur.user_id=:id",nativeQuery=true)
	 public List<UserRights> getMenuDataUsingUserRihts(@Param("id") String id);

	 @Query(value="select  * from userrights where user_id=:id limit 1",nativeQuery=true)
	 public UserRights getStatus(@Param("id") String id);
	
}
