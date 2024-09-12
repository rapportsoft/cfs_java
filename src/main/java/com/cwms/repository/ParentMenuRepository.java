 package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.*;
@EnableJpaRepositories
public interface ParentMenuRepository extends JpaRepository<ParentMenu, String> {
	
	
	
	@Query(value = "SELECT NEW com.cwms.entities.ParentMenu(i.processId, i.PMenu_Name, i.Child_Menu_Status, i.page_status, i.picons,i.parent_page_links) FROM ParentMenu i INNER JOIN UserRights ur ON i.processId = ur.process_Id WHERE i.Company_Id = :cid AND i.Branch_Id = :bid AND ur.user_Id = :userId AND (ur.allow_Read = 'Y' OR ur.allow_Create = 'Y' OR ur.allow_Update = 'Y' OR ur.allow_Delete = 'Y')")
	List<ParentMenu> getParentMenuByRights(@Param("cid") String cid, @Param("bid") String bid, @Param("userId") String userId);

	@Query(value = "SELECT NEW com.cwms.entities.ParentMenu(i.processId, i.PMenu_Name, i.Child_Menu_Status, i.page_status, i.picons,i.parent_page_links) FROM ParentMenu i WHERE i.Company_Id = :cid AND i.Branch_Id = :bid ")
	List<ParentMenu> getParentAdmin(@Param("cid") String cid, @Param("bid") String bid);

	
	

	@Query(value="select NEW com.cwms.entities.ParentMenu(i.Company_Id,i.Branch_Id, i.PMenu_Id, i.processId, i.PMenu_Name) from ParentMenu i "
			+ "where i.Company_Id=:cid and i.Branch_Id=:bid ")
	List<ParentMenu> getAllParentMenu(@Param("cid") String cid,@Param("bid") String bid);
	
	
	
	@Query("SELECT pm FROM ParentMenu pm WHERE pm.processId IN :allowedProcessIds AND pm.Company_Id = :companyId AND pm.Branch_Id = :branchId AND pm IS NOT NULL")
	List<ParentMenu> getAllByProcessIds(
	        @Param("allowedProcessIds") List<String> allowedProcessIds,
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId);
	
	
	@Query(value="select p.* from pmenu p where p.company_id=:cid and p.branch_id=:bid",nativeQuery=true)
	public List<ParentMenu> getAllData(@Param("cid") String cid,@Param("bid") String bid);

	
	@Query(value = "select pmenu_name from pmenu",nativeQuery = true)
	public List<String> getparentname();
	
	@Query(value="select p.*,c.* from pmenu p LEFT JOIN cmenu c ON p.process_id = c.pprocess_id where p.process_id=:id OR c.process_id=:id",nativeQuery=true)	
	public List<Object[]> getall(@Param("id") String id);
	
	@Query(value="select * from pmenu p where p.process_id=:id and p.company_id=:cid and p.branch_id=:bid",nativeQuery=true)
	public ParentMenu getallbyprocessId(@Param("id") String id,@Param("cid") String cid,@Param("bid") String bid);
	
	@Query(value="select * from pmenu where child_menu_status <> 'Y'",nativeQuery=true)
	public List<ParentMenu> getallparent();
	
    @Query(value = "select * from pmenu p where p.child_menu_status = 'Y'",nativeQuery=true)
	public List<String> getParentMenuIdsWithChildStatusY();
    
    
    @Query(value="select p.process_id,p.pmenu_name from pmenu p union select c.process_id,c.child_menu_name from cmenu c",nativeQuery=true)
    public List<Object[]> getalldata();
	  
	  
	  
}
