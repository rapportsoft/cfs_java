package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.UserInfoCRM;

public interface UserInfoCRMRepo extends JpaRepository<UserInfoCRM, String> {

	@Query(value="select COUNT(u)>0 from UserInfoCRM u where u.Company_Id=:cid and u.Branch_Id=:bid and u.User_Id=:id and u.Status='A'")
	boolean checkDataByUserId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}
