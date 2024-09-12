	package com.cwms.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Branch;
import com.cwms.entities.Company;
import com.cwms.entities.UpdateUserRights;
import com.cwms.entities.User;
import com.cwms.entities.UserRights;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.UserRepository;
import com.cwms.repository.UserRightsrepo;
import com.cwms.service.EmailService;
import com.cwms.service.MenuServiceImpl;
import com.cwms.service.UserRightsService;

import jakarta.transaction.Transactional;


@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@ComponentScan("com.repo.all")
public class UserController {
	

	@Autowired
	public EmailService EmailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordencode;
	
	@Value("${spring.from.mail}")
    private String from;
	
	@Autowired(required = true)
	public ProcessNextIdRepository processNextIdRepository;
	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	
	 private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CompanyRepo crepo;

    @Autowired
    private BranchRepo brepo;

    @Autowired
    private UserRepository urepo;

    @Autowired
    private UserRightsrepo urights;

    @Autowired
    private UserRightsService uservice;

    @Autowired
    private MenuServiceImpl menuservice;

 
    
    
    

    @GetMapping("/getAllUsersSelect")
    public List<User> parentRepo(@RequestParam("branchId") String branchId,@RequestParam("companyId") String companyId){
		
    	return urepo.getUserSelectData(companyId,branchId);
    	
    }
    
    
    @GetMapping("/getAllUsersRights")
    public ResponseEntity<?> getAllUsersRights(@RequestParam("branchId") String branchId,
                                               @RequestParam("companyId") String companyId,
                                               @RequestParam(value="userId", required = false) String userId) {
        List<UserRights> userRights = uservice.getUserRights(companyId, branchId, userId);       
        return ResponseEntity.ok(userRights);
    }

    
    @PostMapping("/saveUserRights")
    public ResponseEntity<?> saveUserRights(@RequestParam("branchId") String branchId,@RequestParam("companyId") String companyId,
    		@RequestParam("userId") String userId, @RequestParam("rightsUserId") String rightsUserId,@RequestBody List<UserRights> userRights){
		    	
    	
    	 List<UserRights> processedUserRights = userRights.stream()
    	            .peek(userRight -> {
    	                userRight.setCompany_Id(companyId);
    	                userRight.setBranch_Id(branchId);
    	                userRight.setStatus("A");
    	                userRight.setCreated_By(userRight.getCreated_By() == null ? userId : userRight.getCreated_By());
    	                userRight.setCreated_Date(userRight.getCreated_Date() == null ? new Date() : userRight.getCreated_Date());    	                
    	                userRight.setEdited_By(userId);
    	                userRight.setEdited_Date(new Date());
    	                userRight.setApproved_By(userRight.getApproved_By() == null ? userId : userRight.getApproved_By());
    	                userRight.setApproved_Date(userRight.getApproved_Date() == null ? new Date() : userRight.getApproved_Date());
    	            })
    	            .collect(Collectors.toList());

    	        // Save all the processed user rights
    	      urights.saveAll(processedUserRights);    
    	      
    	      List<UserRights> userRightsNew = uservice.getUserRights(companyId, branchId, rightsUserId); 
    	
    	return ResponseEntity.ok(userRightsNew);    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    @GetMapping("/get-User/{id}/{cid}/{bid}")
    public List<UserRights> getUserrights(@PathVariable("id") String id,@PathVariable("cid") String cid,@PathVariable("bid") String bid){
		return urights.findByUserId(id,cid,bid);
    	
    }

    @GetMapping("/getuser/{id}/{cid}/{bid}")
    public List<UserRights> getUserRightsbyId(@PathVariable("id") String id,@PathVariable("cid") String cid,@PathVariable("bid") String bid) {
    	
    	  List<UserRights> getuserrights = urights.findByUserId(id,cid,bid);
          
          // Format and log the SQL query with the parameter value
          String sqlQuery = "SELECT DISTINCT u.* FROM userinfo u LEFT JOIN userrights ur ON u.branch_id = ur.branch_id LEFT JOIN pmenu p ON ur.process_id = p.process_id LEFT JOIN cmenu c ON p.process_id = c.pprocess_id WHERE u.branch_id = '%s' AND role = 'ROLE_USER'";
          String formattedQuery = String.format(sqlQuery, id);
       
          System.out.println(getuserrights);
          return getuserrights;
    }

    @GetMapping("/mybranch/{id}")
    public List<UserRights> getUserByBranchId(@PathVariable("id") String id,@RequestHeader("React-Page-Name") String reactPageName) {
    	 MDC.put("reactPageName", reactPageName);
        List<UserRights> userrights = this.urights.findBybid(id);
      
        return userrights;
    }

    @GetMapping("ubranch/{id}/{cid}/{bid}")
    public ResponseEntity<List<User>> getUserByBranchId2(@PathVariable("id") String id,@RequestHeader("React-Page-Name") String reactPageName,@PathVariable("cid") String cid,@PathVariable("bid") String bid) {
    	 MDC.put("reactPageName", reactPageName);
        List<User> users = (List<User>) uservice.getUser(id,cid,bid);

        if (users != null && !users.isEmpty()) {
          
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/u/{id}/{cid}/{bid}")
    public List<User> getUsersWithUserRightsByBranchId(@PathVariable("id") String id,@PathVariable("cid") String cid,@PathVariable("bid") String bid) {
    	
        List<User> user = this.urepo.findbyuid(id,cid,bid);
        
        return user;
    }
  
    
    
    @GetMapping("/{cid}/branch")
    public List<Branch> getBranchesOfCompany(@PathVariable("cid") String companyId ,@RequestHeader("React-Page-Name") String reactPageName) {
    	MDC.put("reactPageName", reactPageName);
    	
    	 LocalDate currentDate = LocalDate.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


         String formattedDate = currentDate.format(formatter);

         MDC.put("formattedDate", formattedDate);

    	 List<Branch> branches = brepo.findByCompanyId(companyId);
       
        return branches;
    }
    
    
    @PutMapping("/update-rights")
    @Transactional
    public void updateuserrights(@RequestBody UpdateUserRights request,@RequestHeader("React-Page-Name") String reactPageName) {
    	 MDC.put("reactPageName", reactPageName);
        uservice.updateUserrights(
            request.getApprove(), 
            request.getCreate(), 
            request.getDeleteRight(),
            request.getRead(), 
            request.getUpdate(), 
            request.getUserId(), 
            request.getProcessId()
        );
        
    }

    @PutMapping("/update-rights2")
    @Transactional
    public void updateuserrights2(@RequestBody List<UpdateUserRights> requests,@RequestHeader("React-Page-Name") String reactPageName) {
    	 MDC.put("reactPageName", reactPageName);
        for (UpdateUserRights request : requests) {
            uservice.updateUserrights(
                request.getApprove(), 
                request.getCreate(), 
                request.getDeleteRight(),
                request.getRead(), 
                request.getUpdate(), 
                request.getUserId(), 
                request.getProcessId()
            );
         
        }
    }

    @GetMapping("/get-user/{id}/{cid}/{bid}")
    public User getUserByUserId(@PathVariable("id") String id,@PathVariable("cid") String cid,@PathVariable("bid") String bid) {
    	 
        User user = urepo.findByUserId(id,cid,bid);
 
        return user;
    }

    @PostMapping("/addRights")
    public List<UserRights> insertByUid(@RequestBody List<UserRights> userRightsList,@RequestHeader("React-Page-Name") String reactPageName) {
    	 MDC.put("reactPageName", reactPageName);
        List<UserRights> userrights = urights.saveAll(userRightsList);

        return userrights;
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUserRights(@RequestBody List<UserRights> userRights, @PathVariable("userId") String userId) {
        try {
            uservice.updateUserRights(userRights, userId);
            return ResponseEntity.ok("User rights updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user rights!");
        }
    }

    @PostMapping("/insert/{userId}")
    public ResponseEntity<String> insertUserRights(@RequestBody List<UserRights> userRights, @PathVariable("userId") String userId) {
        try {
            uservice.insertUserRights(userRights, userId);
            return ResponseEntity.ok("User rights inserted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inserting user rights!");
        }
    }

    @GetMapping("/getmenu/{id}")
    public List<UserRights> getmenudatafromuserrights(@PathVariable("id") String id,@RequestHeader("React-Page-Name") String reactPageName) {
    	 MDC.put("reactPageName", reactPageName);
        List<UserRights> user = this.urights.getMenuDataUsingUserRihts(id);
  
        return user;
    }

    @GetMapping("/getallmenu/{userId}/{cid}/{bid}")
    public List<UserRights> getUserRights(@PathVariable String userId,@PathVariable("cid") String cid,@PathVariable("bid") String bid) {
 
        List<UserRights> user = this.menuservice.getUserRightsByUserId(userId,cid,bid);

        return user;
    }

    @GetMapping("/combined-data/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAllCombinedData(@PathVariable("id") String id) {
    	
        List<Map<String, Object>> combinedData = menuservice.getAllParentAndChildMenus(id);
  
        return ResponseEntity.ok(combinedData);
    }

    @GetMapping("/status/{id}")
    public UserRights getStatus(@PathVariable("id") String id) {
    	return urights.getStatus(id);
    }
    
  
    
    
    @GetMapping("/changePassword")
    public String changePassword(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("user") String userid,@RequestParam("old") String oldpassword,@RequestParam("new") String newpassword,@RequestParam("confirm") String confirmpassword)   {
    	User user = urepo.findByUser_IdandbranchAndCompany(userid, bid, cid);
    	
    	if(user != null) {
    		if(passwordencode.matches(oldpassword, user.getUser_Password())) {
    			if(passwordencode.matches(newpassword, user.getUser_Password())) {
    				return "do not enter same old password";
    			}
    			else {
    				if(newpassword.equals(confirmpassword)) {
        				String pass = passwordencode.encode(confirmpassword);
        				user.setUser_Password(pass);
        				urepo.save(user);
        				return "success";
        			}
        			else {
        				return "wrong confirm password";
        			}
    			}
    		}
    		else {
    			return "wrong old password";
    		}
    	}
    	else {
    		return "user not found";
    	}
    }
    
    
    
    @GetMapping("/getMappedData")
	public List<User> getData(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {
		System.out.println("cid " + cid);
		return urepo.getAllData(cid, bid);
	}

    @PostMapping("/saveUser")
	public ResponseEntity<?> saveUser(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("userId") String userId, @RequestParam("flag") String flag, @RequestBody User user) {
           System.out.println("Hiiiiiiiiiii");
		if ("add".equals(flag)) {
			User existUser = urepo.findByUser_IdandbranchAndCompany(user.getUser_Id(), bid, cid);
			if (existUser == null) {

				String IMPTransId = processNextIdRepository.findNextID(cid, bid, "2233", "P05056");

				int lastNextNumericId = Integer.parseInt(IMPTransId.substring(1));

				int nextNumericNextID = lastNextNumericId + 1;
				String id = IMPTransId.substring(0, 1);

				String NextimpTransId = String.format(id + "%05d", nextNumericNextID);
				user.setAutoId(NextimpTransId);
				user.setCreated_Date(new Date());
				user.setCreated_By(userId);
				user.setApproved_By(userId);
				user.setApproved_Date(new Date());
				user.setStatus("A");
				user.setDefaultotp("1000");
				user.setOTP("");
				if (user.getUser_Type() == null || user.getUser_Type().isEmpty()) {
					user.setUser_Type(user.getUser_Id());
					user.setMapped_User(user.getUser_Name());
				}
				user.setUser_Password(passwordencode.encode(user.getUser_Password()));

				urepo.save(user);
				processNextIdRepository.updateNextID(cid, bid, "2233", "P05056", NextimpTransId);
				return ResponseEntity.ok(user);
			} else {

				return ResponseEntity.status(HttpStatus.CONFLICT).body("already exist");
			}
		} else {
			System.out.println("user.getAutoId() " + user.getAutoId());
			User existUser = urepo.getDataById1(cid, bid, user.getUser_Id(), user.getAutoId());
			System.out.println("existUser " + existUser);
			if (existUser == null) {
				User euser1 = urepo.getDataById(cid, bid, user.getAutoId());
				
				User euser = euser1;
				
				
				if (euser != null) {
					urepo.delete(euser1);
					euser.setEdited_Date(new Date());
					euser.setEdited_By(userId);
					euser.setUser_Id(user.getUser_Id());
					euser.setUser_Name(user.getUser_Name());
					euser.setUser_Email(user.getUser_Email());
					euser.setStop_Trans(user.getStop_Trans());
					euser.setComments(user.getComments());

					if (user.getUser_Type() == null || user.getUser_Type().isEmpty()) {
						euser.setUser_Type(euser.getUser_Id());
						euser.setMapped_User(euser.getUser_Name());
					} else {
						euser.setUser_Type(user.getUser_Type());
						euser.setMapped_User(user.getMapped_User());
					}
					if (euser.getUser_Password().equals(user.getUser_Password())) {
						euser.setUser_Password(euser.getUser_Password());
					} else {
						euser.setUser_Password(passwordencode.encode(user.getUser_Password()));
					}
					
					urepo.save(euser);
					return ResponseEntity.ok(euser);
				} else {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("not exist");
				}
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("already exist");
			}

		}

	}
    
    
    
    
	@GetMapping("/getAllUser")
	public List<User> getAllUser(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "search", required = false) String user) {
		System.out.println(cid);
		return urepo.getAllUserData(cid, bid, user);
	}
    
    @PostMapping("/deleteUser")
     public String deleteData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,@RequestParam("id") String id) {
    	  User user = urepo.getDataById(cid, bid, id);
    	  user.setStatus("D");
    	  
    	  urepo.save(user);
    	  return "success";
     }
}
