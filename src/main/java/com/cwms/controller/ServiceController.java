package com.cwms.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Services;
import com.cwms.repository.SerViceRepositary;
import com.cwms.repository.UserRepository;
import com.cwms.service.ProcessNextIdService;

@RestController
@RequestMapping("/service")
@CrossOrigin("*")
public class ServiceController {
	@Autowired
	public UserRepository UserRepository;
//	@Autowired
//	private ServicesInterface serviceOfService;
	@Autowired
	public ProcessNextIdService proccessNextIdService;
	@Autowired
	public SerViceRepositary serViceRepositary;

//	@PostMapping("/{cid}/{bid}/{currentUser}")
//	public ResponseEntity<?> addServices(@PathVariable("cid") String cid, @PathVariable("bid") String bid,
//			@PathVariable("currentUser") String currentUser, @RequestBody Services service) {
//		service.setBranchId(bid);
//		service.setCreatedBy(currentUser);
//		service.setCreatedDate(new Date());
//		service.setStatus("N");
//		
//		
//		service.setCompanyId(cid);
//		Services addService = serviceOfService.addService(service);
//
//		return ResponseEntity.ok(addService);
//	}
//
//	@GetMapping("/{cid}/{bid}")
//	public ResponseEntity<?> getServicess(@PathVariable("cid") String cid, @PathVariable("bid") String bid) {
//		return ResponseEntity.ok(serviceOfService.getServices(cid, bid));
//	}
//
//	@GetMapping("/{cid}/{bid}/{sid}")
//	public Services getServicesById(@PathVariable("sid") String sid, @PathVariable("cid") String cid,
//			@PathVariable("bid") String bid) {
//		return serviceOfService.getServicesById(cid, bid, sid);
//	}
//
//	@DeleteMapping("/{cid}/{bid}/{sid}")
//	public void deleteServiceById(@PathVariable("sid") String sid, @PathVariable("cid") String cid,
//			@PathVariable("bid") String bid
//
//	) {
//		Services servicesById = serviceOfService.getServicesById(cid, bid, sid);
//		servicesById.setStatus("D");
//		serviceOfService.updateService(servicesById);
//	}
//
//	@GetMapping("/{cid}/{bid}/diffservice")
//	public List<Services> getPartiesNotInIds(@RequestParam("excludedserviceIds") List<String> excludedserviceIds,
//			@PathVariable("cid") String cid, @PathVariable("bid") String bid) {
//		List<Services> excludedServices = serviceOfService.findByService_IdNotIn(cid, bid, excludedserviceIds);
//
//		if (excludedserviceIds == null || excludedserviceIds.isEmpty()) {
//			return serviceOfService.getServices(cid, bid); // Fetch all services
//		}
//
//		return excludedServices;
//	}
	
	
	@GetMapping("/{cid}/{bid}")
	public ResponseEntity<?> getServicess(@PathVariable("cid") String cid, @PathVariable("bid") String bid) {
		return ResponseEntity.ok(serViceRepositary.getActiveServices(cid, bid));
	}
	
	@PostMapping("/saveService")
	public ResponseEntity<?> savePartyWTAdd(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("flag") String flag, @RequestParam("user") String user, @RequestBody Services services) {
		System.out.println("Party " + services);

		if ("add".equals(flag)) {

			if (services != null) {

				String autoIncrementServiceId = proccessNextIdService.autoIncrementServiceNextId(cid,bid);

				services.setCompanyId(cid);
				services.setBranchId(bid);
				services.setServiceId(autoIncrementServiceId);
				services.setStatus("A");
				services.setCreatedBy(user);
				services.setCreatedDate(new Date());

				services.setApprovedBy(user);
				services.setApprovedDate(new Date());

				serViceRepositary.save(services);
				;

				return new ResponseEntity<>(services, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("Party data not found.", HttpStatus.BAD_REQUEST);
			}

		} else {

			if (services != null) {
				

				services.setEditedBy(user);
				services.setEditedDate(new Date());
				
				serViceRepositary.save(services);

				return new ResponseEntity<>(services, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Party data not found.", HttpStatus.BAD_REQUEST);
			}

		}

	}

	@GetMapping("/getServiceData")
	public Services getDataByPartyId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("serviceId") String serviceId) {
		return serViceRepositary.getDataById(cid, bid, serviceId);
	}
	
	 @PostMapping("/deleteService")
		public String deleteById(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
				@RequestParam("serviceId") String serviceId) {
	    	int data =serViceRepositary.updateStatusToD(companyId, branchId, serviceId);
			return "success";
		}
	 
	 
	 @GetMapping("/getServiceDataforMapping")
	 public List<Object[]> getServiceDataforMapping(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam(name = "val", required = false) String val) {
			List<Object[]> getServiceList = serViceRepositary.getServiceDataforMapping(cid, bid, val);

			return getServiceList;
		}

}