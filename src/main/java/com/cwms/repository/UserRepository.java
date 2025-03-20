package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.User;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, String> {
	
	
//	User Select
	@Query(value="select NEW com.cwms.entities.User(i.autoId,i.User_Name) from User i "
			+ "where i.Company_Id=:cid and i.Branch_Id=:bid "			
			+ "AND i.Status <> 'D'"
			+ "And i.User_Type <> 'ROLE_ADMIN' ")
	List<User> getUserSelectData(@Param("cid") String cid,@Param("bid") String bid);
	
	
	
	@Transactional
	 @Modifying
	 @Query(value = "UPDATE User e " +
	            "SET e.User_Name = :User_Name , e.User_Id = :email , e.User_Email = :email ,e.mobile = :mobile " +
	            "WHERE e.Company_Id = :companyId " +
	            "AND e.Branch_Id = :branchId " +
	            "AND e.logintypeid = :loginId " +	            
	            "AND e.Status <>'D'")
	    int updateUser(	           
	            @Param("companyId") String companyId,
	            @Param("branchId") String branchId,
	            @Param("User_Name") String User_Name,
	            @Param("email") String email,
	            @Param("mobile") String mobile,
	            @Param("loginId") String loginId
	           );	
	
	@Transactional
	 @Modifying
	 @Query(value = "UPDATE User e " +
	            "SET e.User_Name = :User_Name , e.User_Id = :userId , e.User_Email = :email ,e.mobile = :mobile " +
	            "WHERE e.Company_Id = :companyId " +
	            "AND e.Branch_Id = :branchId " +
	            "AND e.logintypeid = :loginId " +	            
	            "AND e.Status <>'D'")
	    int updateExternalUser(	           
	            @Param("companyId") String companyId,
	            @Param("branchId") String branchId,
	            @Param("User_Name") String User_Name,
	            @Param("email") String email,
	            @Param("userId") String userId,
	            @Param("mobile") String mobile,
	            @Param("loginId") String loginId
	           );	

 
	@Query(value="select * from userinfo as u where u.Login_type_Id=:uid and u.branch_id = :branchId And u.company_id = :companyId", nativeQuery=true)
   public User findByLoginTypeId(@Param("uid") String userId,@Param("branchId") String branchId,@Param("companyId") String companyId);
   

  
    
    @Query(value="select * from userinfo as u where u.user_id=:uid", nativeQuery=true)
    public User findByUser_Id(@Param("uid") String userId);
    
    @Query(value="select * from userinfo as u where u.user_id=:uid and u.company_id=:cid and u.branch_id=:bid", nativeQuery=true)
    public User findByUserId(@Param("uid") String userId,@Param("cid") String cid,@Param("bid") String bid);
    
    
    @Query(value="select * from userinfo as u where u.branch_id=:bid",nativeQuery=true)
	public List<User> findBybranchid(@Param("bid") String bid);
    
    @Query(value="SELECT DISTINCT u.* FROM userinfo u " +
    	       "LEFT JOIN userrights ur ON u.branch_id = ur.branch_id " +
    	       "LEFT JOIN pmenu p ON ur.process_id = p.process_id " +
    	       "LEFT JOIN cmenu c ON p.process_id = c.pprocess_id " +
    	       "WHERE u.branch_id = :uid  and u.role = 'ROLE_USER' and u.status <> 'D' and u.status='A' and u.company_id=:cid and u.branch_id=:bid", nativeQuery=true)
    	public List<User> findbyuid(@Param("uid") String uid,@Param("cid") String cid,@Param("bid") String bid);

    @Query(value="select mobile from userinfo where branch_id=:bid and user_id=:uid",nativeQuery=true)
   public String getmobileno(@Param("bid") String bid,@Param("uid") String uid);
    
    @Query(value="select * from userinfo as u where u.user_id=:uid and u.branch_id=:bid", nativeQuery=true)
    public User findByUser_Idandbranch(@Param("uid") String userId,@Param("bid") String bid);
    
    public User findByLogintypeid(String partyId);

	
	 @Transactional
	    void deleteByLogintypeid(String partyId);
	 @Query(value="select * from userinfo as u where u.user_id=:uid and u.branch_id=:bid and u.company_id=:cid", nativeQuery=true)
	    public User findByUser_IdandbranchAndCompany(@Param("uid") String userId,@Param("bid") String bid,@Param("cid") String cid);
	 

	public User getByLogintypeid(String partyId);
	
	
	
	@Query(value="select New com.cwms.entities.User(u.User_Id, u.User_Name) from User u where u.Company_Id=:cid and u.Branch_Id=:bid and u.Status != 'D' and u.role != 'ROLE_ADMIN'")
	public List<User> getAllData(@Param("cid") String userId,@Param("bid") String bid);
	
	@Query(value="select u from User u where u.Company_Id=:cid and u.Branch_Id=:bid and u.autoId =:auto  and u.Status != 'D'")
	public User getDataById(@Param("cid") String userId,@Param("bid") String bid,@Param("auto") String auto);
	
	
	@Query(value="select u from User u where u.Company_Id=:cid and u.Branch_Id=:bid and u.User_Id=:user and u.autoId != :auto and u.Status != 'D'")
	public User getDataById1(@Param("cid") String userId,@Param("bid") String bid,@Param("user") String user,@Param("auto") String auto);
	
	
	 @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.User_Email = :email AND u.Company_Id = :companyId AND u.Branch_Id = :branchId")
	    boolean existsByEmailAndCompanyIdAndBranchId(@Param("email") String email, @Param("companyId") String companyId, @Param("branchId") String branchId);
	 
	 @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.User_Email = :email AND u.Company_Id = :companyId AND u.Branch_Id = :branchId AND u.autoId != :autoId")
	 boolean existsByEmailAndCompanyIdAndBranchIdAndNotAutoId(@Param("email") String email, @Param("companyId") String companyId, @Param("branchId") String branchId, @Param("autoId") String autoId);

	 @Query(value="select New com.cwms.entities.User(u.User_Id,u.autoId,u.User_Name,u.User_Type,u.User_Password,u.Mapped_User,u.User_Email,u.Stop_Trans,u.Comments,u.Status) from User u LEFT JOIN User u1 ON u.Company_Id = u1.Company_Id "
	 		+ "AND u.Branch_Id = u1.Branch_Id AND u.User_Id = u1.User_Id where u.Company_Id=:cid "
	 		+ "AND u.Branch_Id=:bid and u.role != 'ROLE_ADMIN' and u.Status!='D' "
	 		+ "AND ((:searchValue IS NULL OR :searchValue = '') OR "
	 		+ "(u.User_Id like CONCAT('%', :searchValue, '%') OR u.User_Name like CONCAT('%', :searchValue, '%')))")
	 public List<User> getAllUserData(@Param("cid") String userId,@Param("bid") String bid,@Param("searchValue") String searchValue);
	 
	 @Query(value="SELECT User_Email FROM userinfo WHERE company_id=:cid AND branch_id=:bid AND user_id=:uid AND status='A'", nativeQuery=true)
	    public String getEmail(@Param("cid") String cid, @Param("bid") String bid, @Param("uid") String uid);
}
