package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.*;


@EnableJpaRepositories
public interface ChildMenuRepository extends JpaRepository<ChildMenu, String> {
	
	
	
	@Query("SELECT NEW com.cwms.entities.ChildMenu(cm.Child_Menu_Id, cm.processId, cm.Child_Menu_Name, cm.cicons, cm.child_page_links,cm.pprocess_Id) FROM ChildMenu cm INNER JOIN UserRights ur ON cm.processId = ur.process_Id WHERE cm.Company_Id = :cid AND cm.Branch_Id = :bid AND ur.user_Id = :userId AND cm.tabStatus = 'N' AND (ur.allow_Read = 'Y' OR ur.allow_Create = 'Y' OR ur.allow_Update = 'Y' OR ur.allow_Delete = 'Y')")
	List<ChildMenu> findByChildMenus(@Param("cid") String cid, @Param("bid") String bid, @Param("userId") String userId);

	  
	  
	@Query("SELECT NEW com.cwms.entities.ChildMenu(cm.Child_Menu_Id, cm.processId, cm.Child_Menu_Name, cm.cicons, cm.child_page_links,cm.pprocess_Id ) FROM ChildMenu cm INNER JOIN UserRights ur ON cm.processId = ur.process_Id WHERE cm.Company_Id = :cid AND cm.Branch_Id = :bid AND ur.user_Id = :userId AND cm.tabStatus = 'Y' ")
	List<ChildMenu> findByChildTabs(@Param("cid") String cid, @Param("bid") String bid, @Param("userId") String userId);

	  
	
	@Query("SELECT NEW com.cwms.entities.ChildMenu(cm.Child_Menu_Id, cm.processId, cm.Child_Menu_Name, cm.cicons, cm.child_page_links,cm.pprocess_Id) FROM ChildMenu cm WHERE cm.Company_Id = :cid AND cm.Branch_Id = :bid AND cm.tabStatus = 'N'")
	List<ChildMenu> findByChildAdmin(@Param("cid") String cid, @Param("bid") String bid);

	  
	  
	@Query("SELECT NEW com.cwms.entities.ChildMenu(cm.Child_Menu_Id, cm.processId, cm.Child_Menu_Name, cm.cicons, cm.child_page_links ,cm.pprocess_Id) FROM ChildMenu cm WHERE cm.Company_Id = :cid AND cm.Branch_Id = :bid AND cm.tabStatus = 'Y' ")
	List<ChildMenu> findByChildTabsAdmin(@Param("cid") String cid, @Param("bid") String bid);

	
	
	
	@Query(value="select NEW com.cwms.entities.ChildMenu(i.Company_Id,i.Branch_Id, i.Child_Menu_Id, i.processId, i.Child_Menu_Name) from ChildMenu i "
			+ "where i.Company_Id=:cid and i.Branch_Id=:bid ")
	List<ChildMenu> getAllChildMenu(@Param("cid") String cid,@Param("bid") String bid);
	
	
	
	
	
	@Query("SELECT pm FROM ChildMenu pm WHERE pm.processId IN :allowedProcessIds AND pm.Company_Id = :companyId AND pm.Branch_Id = :branchId AND pm IS NOT NULL")
	List<ChildMenu> getAllByProcessIds(
	        @Param("allowedProcessIds") List<String> allowedProcessIds,
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId);

	
	
	
	
	@Query(value = "select child_menu_id,child_menu_name,child_page_links,cicons,pmenu_id_pmenu_id from cmenu as c where c.pmenu_id_pmenu_id=:pmenuid", nativeQuery = true)
	public List<ChildMenu> getChildByPid(@Param("pmenuid") String pmenuid);
	
	@Query(value = "select * from cmenu as c where c.pprocess_id=:pmenuid  and c.company_id=:cid and c.branch_id=:bid", nativeQuery = true)
	public List<ChildMenu> getChildByProcessid(@Param("pmenuid") String pmenuid,@Param("cid") String cid,@Param("bid") String bid);
	
	@Query(value = "select * from cmenu as c where c.process_id=:pmenuid and c.company_id=:cid and c.branch_id=:bid", nativeQuery = true)
	public ChildMenu getChildByprocessid(@Param("pmenuid") String pmenuid,@Param("cid") String cid,@Param("bid") String bid);
	

	@Query(value="select p.* from cmenu p where p.company_id=:cid and p.branch_id=:bid",nativeQuery=true)
	public List<ChildMenu> getAllData(@Param("cid") String cid,@Param("bid") String bid);
	
}
