package com.cwms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.ChildMenu;

import com.cwms.entities.ParentMenu;
import com.cwms.entities.UserRights;
import com.cwms.repository.ChildMenuRepository;
import com.cwms.repository.ParentMenuRepository;
import com.cwms.service.MenuServiceImpl;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ParentMenuController {

	@Autowired
	public ParentMenuRepository prepo;

    @Autowired
	public ChildMenuRepository crepo;
    
    @Autowired
    private MenuServiceImpl menuservice;
    
    @Autowired
    public  MenuServiceImpl menuService;
    
    @GetMapping("/parent-menus/{cid}/{bid}")
    public List<ParentMenu> getParentMenus(@PathVariable("cid") String cid,@PathVariable("bid") String bid) {
    
        return prepo.getAllData(cid,bid);
    }
    
    
//    Getting Parent Menus by its UserIds
    @GetMapping("/parent-menus/{cid}/{bid}/{userType}/byUserType")
    public List<ParentMenu> getParentMenusByUserIds(@PathVariable("cid") String cid,@PathVariable("bid") String bid,@PathVariable("userType")String userType) {
        	
//    	System.out.println("Calling UserIDs " + userType); 
//    	
//    	System.out.println("***********************************************************************************");
    	
    	List<UserRights> userRights = this.menuservice.getUserRightsByUserId(userType,cid,bid);
    	    	
    	List<String> processIds = userRights.stream()
    	        .map(UserRights::getProcess_Id)
    	        .collect(Collectors.toList());
    	
    	
    	List<ParentMenu> allByProcessIds = prepo.getAllByProcessIds(processIds, cid, bid);
    	      	
//    	List<ChildMenu> allByProcessIds = crepo.getAllByProcessIds(allowedProcessIds, companyid, branchId);
        return allByProcessIds;
    }
    
//  Getting Parent Menus by its UserIds External UserRights
//  @GetMapping("/parent-menus/{cid}/{bid}/{loginType}/loginType")
//  public List<ParentMenu> getParentMenusByUserIdsExternalUserRights(@PathVariable("cid") String cid,@PathVariable("bid") String bid,@PathVariable("loginType")String loginType) {
//      	
////  	System.out.println("Calling UserIDs " + userType); 
////  	
////  	System.out.println("***********************************************************************************");
//  	
//  	List<ExternalUserRights> userRights = this.menuservice.getExternalUserRightsByUserId(loginType,cid,bid);
//  	    	
//  	List<String> processIds = userRights.stream()
//  	        .map(ExternalUserRights::getProcess_Id)
//  	        .collect(Collectors.toList());
//  	
//  	
//  	List<ParentMenu> allByProcessIds = prepo.getAllByProcessIds(processIds, cid, bid);
//  	      	
////  	List<ChildMenu> allByProcessIds = crepo.getAllByProcessIds(allowedProcessIds, companyid, branchId);
//      return allByProcessIds;
//  }
    
   
  
//Getting Child Menus by its UserIds
//@GetMapping("/childMenus/{parentId}/{cid}/{bid}/{logintype}/byLoginTypeType")
//public List<ChildMenu> getChildMenusByLoginType(@PathVariable("cid") String cid,@PathVariable("parentId") String parentId,@PathVariable("bid") String bid,@PathVariable("logintype")String logintype) {
//    	
////	System.out.println("Calling UserIDs " + logintype); 
////	 	
//////	System.out.println("***********************************************************************************");
////	
////	System.out.println("Child Menus");
//	
//	List<ChildMenu> childByProcessid = crepo.getChildByProcessid(parentId, cid, bid);
//	
//	List<ExternalUserRights> userRights = this.menuservice.getExternalUserRightsByUserId(logintype,cid,bid);
//	    	
//	List<ChildMenu> filteredChildMenus = childByProcessid.stream()
//	        .filter(childMenu -> isProcessIdPresentExternal(childMenu.getProcessId(), userRights))
//	        .collect(Collectors.toList());
//	
//
//    return filteredChildMenus;
//}
// 
    
  
  
    
    
    
//  Getting Child Menus by its UserIds
  @GetMapping("/childMenus/{parentId}/{cid}/{bid}/{userType}/byUserType")
  public List<ChildMenu> getChildMenusByUserIds(@PathVariable("cid") String cid,@PathVariable("parentId") String parentId,@PathVariable("bid") String bid,@PathVariable("userType")String userType) {
      	
  	System.out.println("Calling UserIDs " + userType); 
  	 	
//  	System.out.println("***********************************************************************************");
  	
  	System.out.println("Child Menus");
  	
  	List<ChildMenu> childByProcessid = crepo.getChildByProcessid(parentId, cid, bid);
  	
  	List<UserRights> userRights = this.menuservice.getUserRightsByUserId(userType,cid,bid);
  	    	
  	List<ChildMenu> filteredChildMenus = childByProcessid.stream()
  	        .filter(childMenu -> isProcessIdPresent(childMenu.getProcessId(), userRights))
  	        .collect(Collectors.toList());
  	

      return filteredChildMenus;
  }
    
    
    
  private boolean isProcessIdPresent(String processId, List<UserRights> userRights) {	    
	  
	  return userRights.stream()
	            .anyMatch(userRight -> processId.equals(userRight.getProcess_Id()));
	}
  
  
  
  
  
// private boolean isProcessIdPresentExternal(String processId, List<ExternalUserRights> userRights) {	    
//	  
//	  return userRights.stream()
//	            .anyMatch(userRight -> processId.equals(userRight.getProcess_Id()));
//	}
  
  
  
  
    
    
    @GetMapping("/parentMenus")
    public List<ParentMenu> getAllParent(){
    	return prepo.getallparent();
    }
    
    @GetMapping("/child-menus/{cid}/{bid}")
    public List<ChildMenu> getChildmenus(@PathVariable("cid") String cid,@PathVariable("bid") String bid){
    	
    	return crepo.getAllData(cid, bid);
    }
    
    
    @GetMapping("/child-menus/{id}")
    public List<ChildMenu> getChildMenus(@PathVariable("id") String pmenuid) {
    
    	return crepo.getChildByPid(pmenuid);
    }
    
    
    @GetMapping("/pmenu")
    public List<String> getParentName(){
    	
    	return prepo.getparentname();
    	
    }
    
    @GetMapping("/cm/{id}/{cid}/{bid}")
    public List<ChildMenu> getChildMenusByProcess(@PathVariable("id") String pmenuid,@PathVariable("cid") String cid,@PathVariable("bid") String bid) {
    	
    	return crepo.getChildByProcessid(pmenuid,cid,bid);
    }
//    
//    @GetMapping("/all-menu")
//    public List<ParentMenu> getallmenu(){
//    	return prepo.getall();
//    }
    
    
    @GetMapping("/child/{id}/{cid}/{bid}")
    public ChildMenu getChildMenusByProcessID(@PathVariable("id") String pmenuid,@PathVariable("cid") String cid,@PathVariable("bid") String bid) {

    	return crepo.getChildByprocessid(pmenuid,cid,bid);
    }
    
    
    @PostMapping("/childTest/{cid}/{bid}")
    public List<ChildMenu> getChildMenusByProcessIDs(
    		@RequestBody List<String> allowedProcessIds,
            @PathVariable("cid") String companyid,
            @PathVariable("bid") String branchId) {
    	
//    	ArrayList<ChildMenu> childMenus = new ArrayList<>();
    	
    	
    	List<ChildMenu> allByProcessIds = crepo.getAllByProcessIds(allowedProcessIds, companyid, branchId);
    	
//    	for (String processId : allowedProcessIds)
//    	{
//    		
////    		System.out.println(processId);
//    		
//    		ChildMenu childByprocessid = crepo.getChildByprocessid(processId,companyid,branchId);
//    		
//    		 if (childByprocessid != null) {
//    		        childMenus.add(childByprocessid);
//    		    }
//    	}
    	

        return allByProcessIds;
    }
    
    
    @PostMapping("/parentTest/{cid}/{bid}")
    public List<ParentMenu> getParentMenusbyprocessIdTest(
    		@RequestBody List<String> allowedProcessIds,
    		@PathVariable("cid") String cid,@PathVariable("bid") String bid) {
    	
//    	ArrayList<ParentMenu> ParentMenus = new ArrayList<>();
    	
    	List<ParentMenu> allByProcessIds = prepo.getAllByProcessIds(allowedProcessIds, cid, bid);
    	
//    	 parentMenus = allByProcessIds.stream().filter(ParentMenu::nonNull).collect(Collectors.toList());
//    	for (String processId : allowedProcessIds)
//    	{
//    		System.out.println("Parent Process IDs ");
//    		System.out.println(processId);
//    		
//    		ParentMenu childByprocessid = prepo.getallbyprocessId(processId,cid,bid);
//    		
//    		 if (childByprocessid != null) {
//    			 ParentMenus.add(childByprocessid);
//    		    }
//    	}   	
    	
    	return allByProcessIds;
    }
    
    
    
    
    
    
    @GetMapping("/parent/{id}/{cid}/{bid}")
    public ParentMenu getParentMenusbyprocessId(@PathVariable("id") String id,@PathVariable("cid") String cid,@PathVariable("bid") String bid) {
    	
    	return prepo.getallbyprocessId(id,cid,bid);
    }
    
    
    
    
    
    
    
    
    @GetMapping("/alldata")
    public ResponseEntity<List<Map<String, Object>>> getAllParentAndChildMenus() {
        List<Map<String, Object>> menuData = menuService.getAllParentAndChildMenus();
        return ResponseEntity.ok(menuData);
    }
    
}
