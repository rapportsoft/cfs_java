package com.cwms.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Branch;
import com.cwms.entities.ChildMenu;
import com.cwms.entities.Company;
import com.cwms.entities.JwtRequest;
import com.cwms.entities.JwtResponse;
import com.cwms.entities.ParentMenu;
import com.cwms.entities.User;
import com.cwms.entities.UserRights;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.ChildMenuRepository;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.ParentMenuRepository;
import com.cwms.repository.UserRepository;
import com.cwms.repository.UserRightsrepo;
import com.cwms.security.JwtHelper;
import com.cwms.service.EmailService;
import com.cwms.service.UserServiceImpl;

@CrossOrigin("*")
@RequestMapping("/auth")
@RestController
public class AuthController {
	
	@Autowired
	private BCryptPasswordEncoder passwordencode;
	
    @Autowired
    private UserRepository urepo;

	
	@Autowired
	public CompanyRepo companyRepo;
	
	@Autowired
	public EmailService EmailService;
	
	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	
	
	@Value("${spring.from.mail}")
    private String from;
	
	@Autowired
	private BranchRepo brepo;
	
	  @Autowired
	    private CompanyRepo crepo;

	   

	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserServiceImpl uservice;
	
	@Autowired
	public UserRepository repo;

	@Autowired
	private JwtHelper jwtHelper;
	
	
	@Autowired
	private ParentMenuRepository pmenu;

	@Autowired
	private ChildMenuRepository cmenu;

	@Autowired
	private UserRightsrepo userrightsrepo;


	@PostMapping("/login/{otp}")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest,
			@RequestHeader("React-Page-Name") String reactPageName, @PathVariable("otp") String otp) throws Exception {
		MDC.put("reactPageName", reactPageName);
		try {
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

			User user = (User) this.uservice.loadUserByUsername(jwtRequest.getUsername());

			if (user.getStop_Trans() == 'Y') {
				throw new Exception("User is not active");
			}

			if (user.getStatus() == "D") {
				throw new Exception("User not exist");
			}

			if (!user.getStatus().equals("A")) {
				throw new Exception("User not exist");
			}

			if (!user.getBranch_Id().equals(jwtRequest.getBranchid())) {
				throw new Exception("Invalid branch for the user");
			}

			String userType = user.getUser_Type();

			if ("Party".equals(userType) || "CHA".equals(userType) || "Carting Agent".equals(userType)) {
				System.out.println("User type iS Not User ");

				if (user.getOTP().equals(otp)) {
					// OTP matches either user's OTP or default OTP, allow login
					// Retrieve the company ID and branch ID from the user
					String userId = user.getUser_Name();
					String companyId = user.getCompany_Id();
					String branchId = user.getBranch_Id();
					String role = user.getRole();

					Company company = companyRepo.findByCompany_Id(companyId);
					Branch branch = brepo.findByBranchId(branchId);

					String companyname = company.getCompany_name();
					String branchname = branch.getBranchName();

					User userDetails = (User) this.uservice.loadUserByUsername(jwtRequest.getUsername());

					String token = this.jwtHelper.generateToken(userDetails);

					List<ParentMenu> parentMenuByRights = pmenu.getParentMenuByRights(companyId, branchId, userId);
					List<ChildMenu> findByChildMenus = cmenu.findByChildMenus(companyId, branchId, userId);
					List<ChildMenu> findByChildTabs = cmenu.findByChildTabs(companyId, branchId, userId);

					List<UserRights> userRightsByUserId = userrightsrepo.getUserRightsByUserId(companyId, branchId,
							userId);

					JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername(), userId, branchId,
							companyId, role, companyname, branchname, userDetails.getLogintype(),
							userDetails.getLogintypeid(), user.getUser_Type(), userRightsByUserId, parentMenuByRights,
							findByChildMenus, findByChildTabs);

					return ResponseEntity.ok(jwtResponse);
				} else {
					return ResponseEntity.status(400).body("Please enter correct otp..");
				}

			} else {

//	        	System.out.println("User type iS User ");
				if (user.getOTP().equals(otp) || user.getDefaultotp().equals(otp)) {
					// OTP matches either user's OTP or default OTP, allow login
					// Retrieve the company ID and branch ID from the user
					String userId = user.getUser_Name();
					String companyId = user.getCompany_Id();
					String branchId = user.getBranch_Id();
					String role = user.getRole();

					Company company = companyRepo.findByCompany_Id(companyId);
					Branch branch = brepo.findByBranchId(branchId);

					String companyname = company.getCompany_name();
					String branchname = branch.getBranchName();

					User userDetails = (User) this.uservice.loadUserByUsername(jwtRequest.getUsername());

					String token = this.jwtHelper.generateToken(userDetails);

					List<ParentMenu> parentMenuByRights = null;
					List<ChildMenu> findByChildMenus = null;
					List<ChildMenu> findByChildTabs = null;

					
					if ("ROLE_ADMIN".equals(role)) {						
						parentMenuByRights = pmenu.getParentAdmin(companyId, branchId);
						findByChildMenus = cmenu.findByChildAdmin(companyId, branchId);
						findByChildTabs = cmenu.findByChildTabsAdmin(companyId, branchId);

					} else {

						parentMenuByRights = pmenu.getParentMenuByRights(companyId, branchId, user.getAutoId());
						findByChildMenus = cmenu.findByChildMenus(companyId, branchId, user.getAutoId());
						findByChildTabs = cmenu.findByChildTabs(companyId, branchId, user.getAutoId());
					}
					List<UserRights> userRightsByUserId = userrightsrepo.getUserRightsByUserId(companyId, branchId,
							user.getAutoId());
					
					
					

					JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername(), userId, branchId,
							companyId, role, companyname, branchname, userDetails.getLogintype(),
							userDetails.getLogintypeid(), user.getUser_Type(), userRightsByUserId, parentMenuByRights,
							findByChildMenus, findByChildTabs);

					return ResponseEntity.ok(jwtResponse);
				} else {
					return ResponseEntity.status(400).body("Please enter correct otp..");
				}

			}

		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User Not Found");
		}
	}

	
	
	
	

	
	@GetMapping("/getAllCompanies")
	public List<Company> getAllCompanies()
	{
		return companyRepo.getAllCompanies();
	}
	
	@GetMapping("/getBranchByCompany")
	public List<Branch> getBranchesByCompany(@RequestParam("companyId") String companyId)
	{
		return brepo.getBranchesByCompany(companyId);
	}
	
	
	 @GetMapping("/number/{bid}/{uid}")
	    public ResponseEntity<String> sms(@PathVariable("bid") String bid, @PathVariable("uid") String uid) {
	        String otp = generateOTP(); // Make sure this matches the OTP you want to send
	        String num = repo.getmobileno(bid, uid);
	        User user = repo.findByUser_Idandbranch(uid, bid);
	        
	        if (user == null)
	        {
	        	return ResponseEntity.ok("User not found with provided User Id");
	        }
	        if (num == null)
	        {
	        	return ResponseEntity.ok("Mobile Number not found for the enterd User");
	        }
	        
	        user.setOTP(otp);
	        this.repo.save(user);

	        try {
	            String apiKey = "apikey=" + URLEncoder.encode("N2E2ZjU4NmU1OTY5Njg2YjczNjI3OTMxNjg3MjQ4NjM=", "UTF-8");
	            String message = "Dear Sir, Please find your OTP " + otp + " for DGDC E-Custodian login.";
	            String sender = "sender=" + URLEncoder.encode("DGDCSZ", "UTF-8");
	            String numbers = "numbers=" + URLEncoder.encode("91" + num, "UTF-8");

	            // Send data
	            String data = "https://api.textlocal.in/send/?" + apiKey + "&" + numbers + "&message=" + URLEncoder.encode(message, "UTF-8") + "&" + sender;
	            URL url = new URL(data);
	            URLConnection conn = url.openConnection();
	            conn.setDoOutput(true);

	            // Get the response
	            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line;
	            StringBuilder sResult = new StringBuilder();
	            while ((line = rd.readLine()) != null) {
	                sResult.append(line).append(" ");
	            }
	            rd.close();

	            // Return success message
	            return ResponseEntity.ok("OTP sent successfully");

	        } catch (Exception e) {
	            // Log error
	            System.out.println("Error sending OTP: " + e.getMessage());
	            // Return error message
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending OTP: " + e.getMessage());
	        }
	    }

	
	
	
//	SendOtpFor PasswordChange
	@GetMapping("/passwordChange")
	public boolean PasswordChange(@RequestParam(name = "companyId", required = false) String companyId,
			@RequestParam(name = "branchId", required = false) String branchId,
			@RequestParam(name = "userId", required = false) String userId,
			@RequestParam(name = "mobileNo", required = false) String mobileNo) {

	    String otp = generateOTP(); // Make sure this matches the OTP you want to send


	    
	    User user = repo.findByUser_IdandbranchAndCompany(userId, branchId, companyId);
	    
    
	    user.setPasswordChangeOTP(otp);
	    this.repo.save(user);

	    try {

	    	String apiKey = "apikey=" + URLEncoder.encode("N2E2ZjU4NmU1OTY5Njg2YjczNjI3OTMxNjg3MjQ4NjM=", "UTF-8");
	         String message = "Dear Sir, Please find the OTP " + otp + " for DGDC E-Custodian Forget Password.";
	         String sender = "sender=" + URLEncoder.encode("DGDCSZ", "UTF-8");
	         String numbers = "numbers=" + URLEncoder.encode("91" + mobileNo, "UTF-8");
	         String templateId = "template_id=1007414657477770423"; // Replace with your template ID
	         String header = "header=" + URLEncoder.encode("YourHeaderHere", "UTF-8"); // Replace with your header

	         String data = "https://api.textlocal.in/send/?" + apiKey + "&" + numbers + "&message=" + URLEncoder.encode(message, "UTF-8") + "&" + sender + "&" + templateId + "&" + header;

	         URL url = new URL(data);
	         URLConnection conn = url.openConnection();
	         conn.setDoOutput(true);

	        
	        String subject = "Your OTP for Password Change (User ID: " + userId + ")";

//	        String EmailMessage = "Dear Sir/Madam, Your OTP for password change (User ID: <strong>" + userId + "</strong>) is: <strong>" + otp + "</strong>. Please use this OTP to complete the password change process.";
	        String EmailMessage = "Dear Sir/Madam, <br /> <br />Your OTP for password change (User ID: <strong>" + userId + "</strong>) is: <strong>" + otp + "</strong>. Please use this OTP to complete the password change process.<br /><br />Regards,<br /><br />DGDC Team";

	        
	        if (user.getUser_Email()!= null && !user.getUser_Email().isEmpty())
	        {
	        	 	        	
	        	executorService.schedule(() -> {
	        		EmailService.sendEmailWithHtmlContent(subject,EmailMessage,user.getUser_Email(),from,"Tukaram8805@gmail.com");
		        	}, 2, TimeUnit.SECONDS);
	        }
	               
	        

	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        StringBuilder sResult = new StringBuilder();
	        while ((line = rd.readLine()) != null) {
	            sResult.append(line).append(" ");
	        }
	        rd.close();
	       sResult.toString();
	        
//	        System.out.println("Sent Messege  " + string);
	        return true;
	    } catch (Exception e) {
	        System.out.println("Error SMS " + e);
	        return false;
	    }
	    

	    
	    
	    
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String generateOTP() {
	    Random random = new Random();
	    int otp = random.nextInt(9000) + 1000; // Generates a random number between 1000 and 9999
	    return String.valueOf(otp);
	}
	

	
	
//	@PostMapping("/login/{otp}")
//	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest, @RequestHeader("React-Page-Name") String reactPageName, @PathVariable("otp") String otp) throws Exception {
//	    MDC.put("reactPageName", reactPageName);
//	    try {
//	        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
//
//	        User user = (User) this.uservice.loadUserByUsername(jwtRequest.getUsername());
//
//	        if (user.getStop_Trans() == 'Y') {
//	            throw new Exception("User is not active");
//	        }
//
//	        if (user.getStatus() == "D") {
//	            throw new Exception("User not exist");
//	        }
//
//	        if (!user.getStatus().equals("A")) {
//	            throw new Exception("User not exist");
//	        }
//
//	        if (!user.getBranch_Id().equals(jwtRequest.getBranchid())) {
//	            throw new Exception("Invalid branch for the user");
//	        }
//
//	        String userType = user.getUser_Type();
//
//	        if ("Party".equals(userType) || "CHA".equals(userType) || "Carting Agent".equals(userType)) 
//	        {
//	        	System.out.println("User type iS Not User ");
//	        	
//	        	 if (user.getOTP().equals(otp)) {
//	 	            // OTP matches either user's OTP or default OTP, allow login
//	 	            // Retrieve the company ID and branch ID from the user
//	 	            String userId = user.getUser_Name();
//	 	            String companyId = user.getCompany_Id();
//	 	            String branchId = user.getBranch_Id();
//	 	            String role = user.getRole();
//
//	 	            Company company = companyRepo.findByCompany_Id(companyId);
//	 	            Branch branch = brepo.findByBranchId(branchId);
//
//	 	            String companyname = company.getCompany_name();
//	 	            String branchname = branch.getBranchName();
//	 	            
//
//	 	            User userDetails = (User) this.uservice.loadUserByUsername(jwtRequest.getUsername());
//
//	 	            String token = this.jwtHelper.generateToken(userDetails);
//
//	 	            JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername(), userId, branchId, companyId, role, companyname, branchname,userDetails.getLogintype(),userDetails.getLogintypeid(),user.getUser_Type());
//
//	 	            return ResponseEntity.ok(jwtResponse);
//	 	        } else {
//	 	            return ResponseEntity.status(400).body("Please enter correct otp..");
//	 	        }    	
//	        	
//	           
//	        } 
//	        else 
//	        {
//	        	
////	        	System.out.println("User type iS User ");
//	        	 if (user.getOTP().equals(otp) || user.getDefaultotp().equals(otp)) {
//	 	            // OTP matches either user's OTP or default OTP, allow login
//	 	            // Retrieve the company ID and branch ID from the user
//	 	            String userId = user.getUser_Name();
//	 	            String companyId = user.getCompany_Id();
//	 	            String branchId = user.getBranch_Id();
//	 	            String role = user.getRole();
//
//	 	            Company company = companyRepo.findByCompany_Id(companyId);
//	 	            Branch branch = brepo.findByBranchId(branchId);
//
//	 	            String companyname = company.getCompany_name();
//	 	            String branchname = branch.getBranchName();
//	 	            
//
//	 	            User userDetails = (User) this.uservice.loadUserByUsername(jwtRequest.getUsername());
//
//	 	            String token = this.jwtHelper.generateToken(userDetails);
//
//	 	            JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername(), userId, branchId, companyId, role, companyname, branchname,userDetails.getLogintype(),userDetails.getLogintypeid(),user.getUser_Type());
//
//	 	            return ResponseEntity.ok(jwtResponse);
//	 	        } else {
//	 	            return ResponseEntity.status(400).body("Please enter correct otp..");
//	 	        }     	       	
//
//
//	        }       
//	        
//	        
//	       
//	    } catch (UsernameNotFoundException e) {
//	        e.printStackTrace();
//	        throw new Exception("User Not Found");
//	    }
//	}
	
	

	private void authenticate(String username, String password) throws Exception {

		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (DisabledException e) {

			throw new Exception("User Disable" + e.getMessage());

		} catch (BadCredentialsException e) {

			throw new Exception("Invalide credential" + e.getMessage());
		}

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	}
	
	
	  @GetMapping("/UpdatePassword")
	    public boolean sendOtp(
	        @RequestParam(name = "companyId", required = false) String companyId,
	        @RequestParam(name = "branchId", required = false) String branchId,
	        @RequestParam(name = "userId", required = false) String userId,
	        @RequestParam(name = "password", required = false) String password
	    ) {
	        try {
	            User userToUpdate = urepo.findByUser_IdandbranchAndCompany(userId, branchId, companyId);
	            if (userToUpdate != null) {
	                userToUpdate.setUser_Password(passwordencode.encode(password));
	                urepo.save(userToUpdate);
	                
	                if (userToUpdate.getUser_Email()!= null && !userToUpdate.getUser_Email().isEmpty())
	    	        {
	                	
	                	
	                	String subject = "Your OTP for Password Change (User ID: " + userId + ")";

	                	String emailMessage = "Dear Sir/Madam,<br/><br/>Your password for User ID: <strong>" + userId + "</strong> has been successfully changed.<br/><br/>	Your New Password is <strong>"+ password +" </strong><br/><br/>If you did not make this change, please contact us immediately.";

	                	
	    	        	executorService.schedule(() -> {
	    	        		EmailService.sendEmailWithHtmlContent(subject,emailMessage,userToUpdate.getUser_Email(),from,"Tukaram8805@gmail.com");
	    		        	}, 2, TimeUnit.SECONDS);
	    	        }
	                
	                userToUpdate.setPasswordChangeOTP("");
	                urepo.save(userToUpdate);
	                return true;	
	            } else {               
	                return false;
	            }
	        } catch (Exception e) {
	           
	            e.printStackTrace(); 
	            return false; 
	        }
	    }


	    @GetMapping("/checkOtpForPasswordChange")
	    public boolean checkOtpForPasswordChange(
	        @RequestParam(name = "companyId", required = false) String companyId,
	        @RequestParam(name = "branchId", required = false) String branchId,
	        @RequestParam(name = "userId", required = false) String userId,
	        @RequestParam(name = "otp", required = false) String otp
	    ) {
	        try {
	            User userToUpdate = urepo.findByUser_IdandbranchAndCompany(userId, branchId, companyId);
	            if (userToUpdate.getPasswordChangeOTP().equals(otp.trim())) {       	      	
	                return true;	
	            } else {               
	                return false;
	            }
	        } catch (Exception e) {
	           
	            e.printStackTrace(); 
	            return false; 
	        }
	    }
	

	    @GetMapping("/company")
	    public List<Company> getCompanies(@RequestHeader("React-Page-Name") String reactPageName) {
	    	MDC.put("reactPageName", reactPageName);
	    	
	    	 LocalDate currentDate = LocalDate.now();

	         // Define the desired date format
	         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	         // Format the current date using the formatter
	         String formattedDate = currentDate.format(formatter);
	         System.setProperty("formattedDate", formattedDate);
	         MDC.put("formattedDate", formattedDate);
	         System.out.println(formattedDate);
	    
	    	System.out.println(reactPageName);
	        List<Company> companies = this.crepo.findAll();
	       
	        return companies;
	    }

	    @GetMapping("/branch")
	    public List<Branch> getBranchesByCompanyId(@RequestHeader("React-Page-Name") String reactPageName) {
	    	MDC.put("reactPageName", reactPageName);
	    	System.out.println(reactPageName);
	    	 try{
	        List<Branch> branches = brepo.findAll();
	      
	        return branches;
	    	 }
	    	 finally {
				MDC.remove("controller");
			}
	    }
	
	    @GetMapping("/{compId}/{bid}/{userId}/findByUserId")
	    public User findByUserId(@PathVariable("compId") String cid,@PathVariable("bid") String bid,@PathVariable("userId") String userId) {
	    	return urepo.findByUser_IdandbranchAndCompany(userId,bid,cid);
	    }
	
}
