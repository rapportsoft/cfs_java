//package com.cwms.controller;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cwms.entities.CFIgm;
//import com.cwms.entities.CFSTariffService;
//import com.cwms.entities.Cfigmcn;
//import com.cwms.entities.Cfigmcrg;
//import com.cwms.entities.SSRDtl;
//import com.cwms.entities.SSRDto;
//import com.cwms.entities.Services;
//import com.cwms.repository.CFSTarrifServiceRepository;
//import com.cwms.repository.CfIgmCnRepository;
//import com.cwms.repository.CfIgmCrgRepository;
//import com.cwms.repository.CfIgmRepository;
//import com.cwms.repository.ProcessNextIdRepository;
//import com.cwms.repository.SSRDtlRepository;
//import com.cwms.repository.SerViceRepositary;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@CrossOrigin("*")
//@RestController
//@RequestMapping("/ssr")
//public class SSRDtlController {
//
//	@Autowired
//	private CfIgmRepository cfigmrepo;
//
//	@Autowired
//	private ProcessNextIdRepository processnextidrepo;
//
//	@Autowired
//	private CfIgmCrgRepository cfigmcrgrepo;
//
//	@Autowired
//	private CfIgmCnRepository cfigmcnrepo;
//
//	@Autowired
//	private SerViceRepositary serViceRepositary;
//
//	@Autowired
//	private SSRDtlRepository ssrdtlrepo;
//
//	@Autowired
//	private CFSTarrifServiceRepository cfstarrifservicerepo;
//
//	@GetMapping("/search")
//	public ResponseEntity<?> searchSsr(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam(name = "igm", required = false) String igm,
//			@RequestParam(name = "item", required = false) String item,
//			@RequestParam(name = "container", required = false) String container) {
//
//		Map<String, Object> result = new HashMap<>();
//
//		if (container == null || container.isEmpty()) {
//
//			Cfigmcrg crg = cfigmcrgrepo.getDataforSSR(cid, bid, igm, item);
//			if (crg == null) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//
//			List<Cfigmcn> cn = cfigmcnrepo.searchSSr(cid, bid, crg.getIgmNo(), crg.getIgmTransId(), crg.getIgmLineNo());
//			if (cn.isEmpty()) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//
//			result.put("igmCrg", crg);
//			result.put("con", cn);
//
//			return new ResponseEntity<>(result, HttpStatus.OK);
//
//		} else {
//
//			List<Cfigmcn> data = cfigmcnrepo.searchSSrContainer(cid, bid, container);
//			if (data.isEmpty()) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//
//			// Fetch cargo data based on the container
//			Cfigmcrg crg = cfigmcrgrepo.getDataforSSR1(cid, bid, data.get(0).getIgmTransId(), data.get(0).getIgmNo(),
//					data.get(0).getIgmLineNo());
//			if (crg == null) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//
//			List<Cfigmcn> cn = cfigmcnrepo.searchSSr(cid, bid, crg.getIgmNo(), crg.getIgmTransId(), crg.getIgmLineNo());
//			if (cn.isEmpty()) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//
//			result.put("igmCrg", crg);
//			result.put("con", data);
//
//			return new ResponseEntity<>(result, HttpStatus.OK);
//		}
//	}
//
//	@GetMapping("getServices")
//	public ResponseEntity<?> getAllServices(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {
//
//		List<Services> data = serViceRepositary.getActiveServices1(cid, bid);
//
//		if (data.isEmpty()) {
//			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//		}
//		
//		List<CFSTariffService> service = cfstarrifservicerepo.getGeneralTarrifData(cid, bid);
//		
//		
//		
//		if (!service.isEmpty()) {
//		    data.stream().forEach(c -> {
//		        // Find the matching CFSTariffService object for the current Service
//		        BigDecimal rate = service.stream()
//		            .filter(s -> s.getServiceId().equals(c.getServiceId())) // Match by ServiceId
//		            .map(CFSTariffService::getRate) // Get the rate for matched service
//		            .findFirst() // Get the first match (if any)
//		            .orElse(null); // Set rate to zero if no match found
//		        
//		        // Set the rate to the service object (assuming Services class has a setRate method)
//		        
//		        c.setCfsBaseRate(rate);
//		    });
//		}
//		return new ResponseEntity<>(data, HttpStatus.OK);
//	}
//
//	@PostMapping("/saveData")
//	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
//			throws JsonMappingException, JsonProcessingException {
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		SSRDtl ssr = mapper.readValue(mapper.writeValueAsString(data.get("ssr")), SSRDtl.class);
//
//		List<Cfigmcn> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
//				new TypeReference<List<Cfigmcn>>() {
//				});
//
//		List<SSRDto> service = mapper.readValue(mapper.writeValueAsString(data.get("service")),
//				new TypeReference<List<SSRDto>>() {
//				});
//
//		if (ssr == null) {
//			return new ResponseEntity<>("SSR data not found.", HttpStatus.CONFLICT);
//		}
//
//		if (container.isEmpty()) {
//			return new ResponseEntity<>("Container data not found.", HttpStatus.CONFLICT);
//		}
//
//		if (service.isEmpty()) {
//			return new ResponseEntity<>("Service data not found.", HttpStatus.CONFLICT);
//		}
//
//		String id = "";
//
//		if (ssr.getTransId() == null || ssr.getTransId().isEmpty()) {
//
//			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05080", "2024");
//
//			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));
//
//			int nextNumericNextID1 = lastNextNumericId1 + 1;
//
//			String HoldNextIdD1 = String.format("ISSR%06d", nextNumericNextID1);
//
//			service.stream().forEach(s -> {
//
//				Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());
//
//				if (ser != null) {
//
//					String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05081", "2024");
//
//					String[] parts = lastValue.split("/");
//					String baseId = parts[0];
//					String baseId1 = parts[1];
//					String financialYear = parts[2];
//
//					// Increment the numerical part
//					int newVal = Integer.parseInt(baseId1) + 1;
//
//					// Format newVal to maintain leading zeros (e.g., 0001)
//					String formattedNewVal = String.format("%04d", newVal);
//
//					// Get the current financial year
//					String currentFinancialYear = getCurrentFinancialYear();
//
//					// Construct the new ID
//					String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;
//
//					CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo());
//
//					Cfigmcrg crg = cfigmcrgrepo.getDataByIgmAndLine(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo(),
//							ssr.getIgmLineNo());
//
//					container.stream().forEach(con -> {
//
//						Cfigmcn cn = cfigmcnrepo.getSingleData6(cid, bid, con.getIgmTransId(), con.getIgmNo(),
//								con.getIgmLineNo(), con.getContainerNo(), HoldNextIdD1);
//
//						System.out.println("crg " + crg.getAccountHolderId() + " " + crg.getImporterId());
//
//						if (igm != null && crg != null && cn != null) {
//
//							SSRDtl newSsr = new SSRDtl();
//
//							newSsr.setApprovedBy(user);
//							newSsr.setApprovedDate(new Date());
//							newSsr.setBeDate(crg.getBeDate());
//							newSsr.setBeNo(crg.getBeNo());
//							newSsr.setBlDate(crg.getBlDate());
//							newSsr.setBlNo(crg.getBlNo());
//							newSsr.setBranchId(bid);
//							newSsr.setCargoWt(cn.getCargoWt());
//							newSsr.setCha(con.getCha());
//							newSsr.setCommodityDescription(crg.getCommodityDescription());
//							newSsr.setCompanyId(cid);
//							newSsr.setContainerNo(con.getContainerNo());
//							newSsr.setContainerSize(con.getContainerSize());
//							newSsr.setContainerType(con.getContainerType());
//							newSsr.setCreatedBy(user);
//							newSsr.setCreatedDate(new Date());
//							newSsr.setDocRefDate(igm.getIgmDate());
//							newSsr.setDocRefNo(con.getIgmNo());
//							newSsr.setErpDocRefNo(con.getIgmTransId());
//							newSsr.setExecutionUnit(s.getExecutionUnit());
//							newSsr.setGateOutType(con.getGateOutType());
//							newSsr.setIgmLineNo(con.getIgmLineNo());
//							newSsr.setNoOfPackages(con.getNoOfPackages());
//							newSsr.setProfitcentreId(con.getProfitcentreId());
//							newSsr.setRate(s.getRate());
//							newSsr.setSa(igm.getShippingAgent());
//							newSsr.setServiceId(s.getServiceId());
//							newSsr.setServiceUnit(s.getServiceUnit());
//							newSsr.setServiceUnit1(ser.getServiceUnit1());
//							newSsr.setSl(igm.getShippingLine());
//							newSsr.setSrNo(new BigDecimal(0));
//							newSsr.setSsrModeFor(ssr.getSsrModeFor());
//							newSsr.setSsrRefNo(newId);
//							newSsr.setStatus('A');
//							newSsr.setTotalRate(s.getTotalRate());
//							newSsr.setTransDate(new Date());
//							newSsr.setTransId(HoldNextIdD1);
//							newSsr.setTransLineNo(new BigDecimal(1));
//							newSsr.setAccId(crg.getAccountHolderId());
//							newSsr.setImpId(crg.getImporterId());
//
//							ssrdtlrepo.save(newSsr);
//
//							cn.setSsrTransId(HoldNextIdD1);
//							cfigmcnrepo.save(cn);
//
//							processnextidrepo.updateAuditTrail(cid, bid, "P05080", HoldNextIdD1, "2024");
//							processnextidrepo.updateAuditTrail(cid, bid, "P05081", newId, "2024");
//
//							ssr.setTransId(HoldNextIdD1);
//						}
//
//					});
//				}
//
//			});
//
//		} else {
//
//			service.stream().forEach(s -> {
//
//				Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());
//
//				if (ser != null) {
//
//					String oldRefNo = ssrdtlrepo.getSSRReferenceNo(cid, bid, ssr.getTransId(), s.getServiceId());
//
//					if (oldRefNo != null && !oldRefNo.isEmpty()) {
//
//						CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo());
//
//						Cfigmcrg crg = cfigmcrgrepo.getDataByIgmAndLine(cid, bid, ssr.getErpDocRefNo(),
//								ssr.getDocRefNo(), ssr.getIgmLineNo());
//
//						container.stream().forEach(con -> {
//
//							Cfigmcn cn = cfigmcnrepo.getSingleData6(cid, bid, con.getIgmTransId(), con.getIgmNo(),
//									con.getIgmLineNo(), con.getContainerNo(), ssr.getTransId());
//
//							Boolean checkServiceConAlreadyExist = ssrdtlrepo.checkSSRReferenceNo(cid, bid,
//									ssr.getTransId(), s.getServiceId(), con.getContainerNo());
//							System.out.println("crg " + crg.getAccountHolderId() + " " + crg.getImporterId());
//
//							if (igm != null && crg != null && cn != null && !checkServiceConAlreadyExist) {
//
//								SSRDtl newSsr = new SSRDtl();
//
//								newSsr.setApprovedBy(user);
//								newSsr.setApprovedDate(new Date());
//								newSsr.setBeDate(crg.getBeDate());
//								newSsr.setBeNo(crg.getBeNo());
//								newSsr.setBlDate(crg.getBlDate());
//								newSsr.setBlNo(crg.getBlNo());
//								newSsr.setBranchId(bid);
//								newSsr.setCargoWt(cn.getCargoWt());
//								newSsr.setCha(con.getCha());
//								newSsr.setCommodityDescription(crg.getCommodityDescription());
//								newSsr.setCompanyId(cid);
//								newSsr.setContainerNo(con.getContainerNo());
//								newSsr.setContainerSize(con.getContainerSize());
//								newSsr.setContainerType(con.getContainerType());
//								newSsr.setCreatedBy(user);
//								newSsr.setCreatedDate(new Date());
//								newSsr.setDocRefDate(igm.getIgmDate());
//								newSsr.setDocRefNo(con.getIgmNo());
//								newSsr.setErpDocRefNo(con.getIgmTransId());
//								newSsr.setExecutionUnit(s.getExecutionUnit());
//								newSsr.setGateOutType(con.getGateOutType());
//								newSsr.setIgmLineNo(con.getIgmLineNo());
//								newSsr.setNoOfPackages(con.getNoOfPackages());
//								newSsr.setProfitcentreId(con.getProfitcentreId());
//								newSsr.setRate(s.getRate());
//								newSsr.setSa(igm.getShippingAgent());
//								newSsr.setServiceId(s.getServiceId());
//								newSsr.setServiceUnit(s.getServiceUnit());
//								newSsr.setServiceUnit1(ser.getServiceUnit1());
//								newSsr.setSl(igm.getShippingLine());
//								newSsr.setSrNo(new BigDecimal(0));
//								newSsr.setSsrModeFor(ssr.getSsrModeFor());
//								newSsr.setSsrRefNo(oldRefNo);
//								newSsr.setStatus('A');
//								newSsr.setTotalRate(s.getTotalRate());
//								newSsr.setTransDate(ssr.getTransDate());
//								newSsr.setTransId(ssr.getTransId());
//								newSsr.setTransLineNo(new BigDecimal(1));
//								newSsr.setAccId(crg.getAccountHolderId());
//								newSsr.setImpId(crg.getImporterId());
//
//								ssrdtlrepo.save(newSsr);
//
//								cn.setSsrTransId(ssr.getTransId());
//								cfigmcnrepo.save(cn);
//							}
//
//						});
//					} else {
//						String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05081", "2024");
//
//						String[] parts = lastValue.split("/");
//						String baseId = parts[0];
//						String baseId1 = parts[1];
//						String financialYear = parts[2];
//
//						// Increment the numerical part
//						int newVal = Integer.parseInt(baseId1) + 1;
//
//						// Format newVal to maintain leading zeros (e.g., 0001)
//						String formattedNewVal = String.format("%04d", newVal);
//
//						// Get the current financial year
//						String currentFinancialYear = getCurrentFinancialYear();
//
//						// Construct the new ID
//						String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;
//
//						CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo());
//
//						Cfigmcrg crg = cfigmcrgrepo.getDataByIgmAndLine(cid, bid, ssr.getErpDocRefNo(),
//								ssr.getDocRefNo(), ssr.getIgmLineNo());
//
//						container.stream().forEach(con -> {
//
//							Cfigmcn cn = cfigmcnrepo.getSingleData6(cid, bid, con.getIgmTransId(), con.getIgmNo(),
//									con.getIgmLineNo(), con.getContainerNo(), ssr.getTransId());
//
//							System.out.println("crg " + crg.getAccountHolderId() + " " + crg.getImporterId());
//
//							if (igm != null && crg != null && cn != null) {
//
//								SSRDtl newSsr = new SSRDtl();
//
//								newSsr.setApprovedBy(user);
//								newSsr.setApprovedDate(new Date());
//								newSsr.setBeDate(crg.getBeDate());
//								newSsr.setBeNo(crg.getBeNo());
//								newSsr.setBlDate(crg.getBlDate());
//								newSsr.setBlNo(crg.getBlNo());
//								newSsr.setBranchId(bid);
//								newSsr.setCargoWt(cn.getCargoWt());
//								newSsr.setCha(con.getCha());
//								newSsr.setCommodityDescription(crg.getCommodityDescription());
//								newSsr.setCompanyId(cid);
//								newSsr.setContainerNo(con.getContainerNo());
//								newSsr.setContainerSize(con.getContainerSize());
//								newSsr.setContainerType(con.getContainerType());
//								newSsr.setCreatedBy(user);
//								newSsr.setCreatedDate(new Date());
//								newSsr.setDocRefDate(igm.getIgmDate());
//								newSsr.setDocRefNo(con.getIgmNo());
//								newSsr.setErpDocRefNo(con.getIgmTransId());
//								newSsr.setExecutionUnit(s.getExecutionUnit());
//								newSsr.setGateOutType(con.getGateOutType());
//								newSsr.setIgmLineNo(con.getIgmLineNo());
//								newSsr.setNoOfPackages(con.getNoOfPackages());
//								newSsr.setProfitcentreId(con.getProfitcentreId());
//								newSsr.setRate(s.getRate());
//								newSsr.setSa(igm.getShippingAgent());
//								newSsr.setServiceId(s.getServiceId());
//								newSsr.setServiceUnit(s.getServiceUnit());
//								newSsr.setServiceUnit1(ser.getServiceUnit1());
//								newSsr.setSl(igm.getShippingLine());
//								newSsr.setSrNo(new BigDecimal(0));
//								newSsr.setSsrModeFor(ssr.getSsrModeFor());
//								newSsr.setSsrRefNo(newId);
//								newSsr.setStatus('A');
//								newSsr.setTotalRate(s.getTotalRate());
//								newSsr.setTransDate(ssr.getTransDate());
//								newSsr.setTransId(ssr.getTransId());
//								newSsr.setTransLineNo(new BigDecimal(1));
//								newSsr.setAccId(crg.getAccountHolderId());
//								newSsr.setImpId(crg.getImporterId());
//
//								ssrdtlrepo.save(newSsr);
//
//								cn.setSsrTransId(ssr.getTransId());
//								cfigmcnrepo.save(cn);
//
//								processnextidrepo.updateAuditTrail(cid, bid, "P05081", newId, "2024");
//
//							}
//
//						});
//					}
//
//				}
//
//			});
//		}
//
//		Map<String, Object> result = new HashMap<>();
//
//		List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, ssr.getTransId());
//
//		List<Cfigmcn> getCnData = cfigmcnrepo.getLatestSSRData(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo(),
//				ssr.getIgmLineNo(), ssr.getTransId());
//
//		List<SSRDtl> ssrData = ssrdtlrepo.getSingleData(cid, bid, ssr.getTransId());
//
//		result.put("refNoData", ssrRefNoData);
//		result.put("con", getCnData);
//		result.put("ssr", ssrData.get(0));
//
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
//
//	private static String getCurrentFinancialYear() {
//		LocalDate now = LocalDate.now();
//		int year = now.getYear();
//		int month = now.getMonthValue();
//		int day = now.getDayOfMonth();
//
//		// Check if the current date is before or after April 1st
//		if (month < 4 || (month == 4 && day < 1)) {
//			year--; // If before April 1st, consider the previous year
//		}
//
//		// Calculate financial year start and end
//		int financialYearStart = year % 100; // Current year for start
//		int financialYearEnd = (year + 1) % 100; // Next year for end
//
//		// Format as YY-YY
//		return String.format("%02d-%02d", financialYearStart, financialYearEnd);
//	}
//
//	@GetMapping("/searchSSR")
//	public ResponseEntity<?> searchSSR(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam(name = "val", required = false) String val) {
//
//		List<Object[]> data = ssrdtlrepo.searchSSR(cid, bid, val);
//
//		if (data.isEmpty()) {
//			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
//		}
//
//		return new ResponseEntity<>(data, HttpStatus.OK);
//	}
//
//	@GetMapping("/getDataByTransId")
//	public ResponseEntity<?> getDataByTransId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam("transid") String transid, @RequestParam("igmtrans") String igmtrans,
//			@RequestParam("igmno") String igmno, @RequestParam("line") String line) {
//
//		Map<String, Object> result = new HashMap<>();
//
//		List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, transid);
//
//		if (ssrRefNoData.isEmpty()) {
//			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
//		}
//
//		List<Cfigmcn> getCnData = cfigmcnrepo.getLatestSSRData(cid, bid, igmtrans, igmno, line, transid);
//
//		if (getCnData.isEmpty()) {
//			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
//		}
//
//		List<SSRDtl> ssrData = ssrdtlrepo.getSingleData(cid, bid, transid);
//
//		if (ssrData.isEmpty()) {
//			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
//		}
//
//		result.put("refNoData", ssrRefNoData);
//		result.put("con", getCnData);
//		result.put("ssr", ssrData.get(0));
//
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
//}




package com.cwms.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.CFIgm;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.ExportSSRDto;
import com.cwms.entities.SSRDtl;
import com.cwms.entities.SSRDto;
import com.cwms.entities.Services;
import com.cwms.repository.CFSTarrifServiceRepository;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.CfbondnocRepository;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportStuffRequestRepo;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.SSRDtlRepository;
import com.cwms.repository.SerViceRepositary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
@RequestMapping("/ssr")
public class SSRDtlController {

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private SerViceRepositary serViceRepositary;

	@Autowired
	private SSRDtlRepository ssrdtlrepo;

	@Autowired
	private CFSTarrifServiceRepository cfstarrifservicerepo;
	
	@Autowired
	private ExportEntryRepo exportsbrepo; 
	
	@Autowired
	private ExportStuffRequestRepo exportStuffReqRepo;
	
	
	@Autowired
	private ExportStuffTallyRepo exportStuffTallyRepo;
	
	@Autowired
	private CfbondnocRepository cfbondnocrepo;

	@GetMapping("/search")
	public ResponseEntity<?> searchSsr(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "igm", required = false) String igm,
			@RequestParam(name = "item", required = false) String item,
			@RequestParam(name = "container", required = false) String container) {

		Map<String, Object> result = new HashMap<>();

		if (container == null || container.isEmpty()) {

			Cfigmcrg crg = cfigmcrgrepo.getDataforSSR(cid, bid, igm, item);
			if (crg == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			List<Cfigmcn> cn = cfigmcnrepo.searchSSr(cid, bid, crg.getIgmNo(), crg.getIgmTransId(), crg.getIgmLineNo());
			if (cn.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			result.put("igmCrg", crg);
			result.put("con", cn);

			return new ResponseEntity<>(result, HttpStatus.OK);

		} else {

			List<Cfigmcn> data = cfigmcnrepo.searchSSrContainer(cid, bid, container);
			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			// Fetch cargo data based on the container
			Cfigmcrg crg = cfigmcrgrepo.getDataforSSR1(cid, bid, data.get(0).getIgmTransId(), data.get(0).getIgmNo(),
					data.get(0).getIgmLineNo());
			if (crg == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			List<Cfigmcn> cn = cfigmcnrepo.searchSSr(cid, bid, crg.getIgmNo(), crg.getIgmTransId(), crg.getIgmLineNo());
			if (cn.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			result.put("igmCrg", crg);
			result.put("con", data);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@GetMapping("getServices")
	public ResponseEntity<?> getAllServices(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

		List<Object[]> serviceList = cfstarrifservicerepo.getGeneralTarrifData1(cid, bid);
		if (serviceList.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(serviceList, HttpStatus.OK);
	}

	@PostMapping("/saveData")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		SSRDtl ssr = mapper.readValue(mapper.writeValueAsString(data.get("ssr")), SSRDtl.class);

		List<Cfigmcn> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
				new TypeReference<List<Cfigmcn>>() {
				});

		List<SSRDto> service = mapper.readValue(mapper.writeValueAsString(data.get("service")),
				new TypeReference<List<SSRDto>>() {
				});

		if (ssr == null) {
			return new ResponseEntity<>("SSR data not found.", HttpStatus.CONFLICT);
		}

		if (container.isEmpty()) {
			return new ResponseEntity<>("Container data not found.", HttpStatus.CONFLICT);
		}

		if (service.isEmpty()) {
			return new ResponseEntity<>("Service data not found.", HttpStatus.CONFLICT);
		}

		String id = "";

		if (ssr.getTransId() == null || ssr.getTransId().isEmpty()) {

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05080", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("ISSR%06d", nextNumericNextID1);

			service.stream().forEach(s -> {

				Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());

				if (ser != null) {

					String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05081", "2024");

					String[] parts = lastValue.split("/");
					String baseId = parts[0];
					String baseId1 = parts[1];
					String financialYear = parts[2];

					// Increment the numerical part
					int newVal = Integer.parseInt(baseId1) + 1;

					// Format newVal to maintain leading zeros (e.g., 0001)
					String formattedNewVal = String.format("%04d", newVal);

					// Get the current financial year
					String currentFinancialYear = getCurrentFinancialYear();

					// Construct the new ID
					String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;

					CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo());

					Cfigmcrg crg = cfigmcrgrepo.getDataByIgmAndLine(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo(),
							ssr.getIgmLineNo());

					container.stream().forEach(con -> {

						Cfigmcn cn = cfigmcnrepo.getSingleData6(cid, bid, con.getIgmTransId(), con.getIgmNo(),
								con.getIgmLineNo(), con.getContainerNo(), HoldNextIdD1);

						System.out.println("crg " + crg.getAccountHolderId() + " " + crg.getImporterId());

						if (igm != null && crg != null && cn != null) {

							SSRDtl newSsr = new SSRDtl();

							newSsr.setApprovedBy(user);
							newSsr.setApprovedDate(new Date());
							newSsr.setBeDate(crg.getBeDate());
							newSsr.setBeNo(crg.getBeNo());
							newSsr.setBlDate(crg.getBlDate());
							newSsr.setBlNo(crg.getBlNo());
							newSsr.setBranchId(bid);
							newSsr.setCargoWt(cn.getCargoWt());
							newSsr.setCha(con.getCha());
							newSsr.setCommodityDescription(crg.getCommodityDescription());
							newSsr.setCompanyId(cid);
							newSsr.setContainerNo(con.getContainerNo());
							newSsr.setContainerSize(con.getContainerSize());
							newSsr.setContainerType(con.getContainerType());
							newSsr.setCreatedBy(user);
							newSsr.setCreatedDate(new Date());
							newSsr.setDocRefDate(igm.getIgmDate());
							newSsr.setDocRefNo(con.getIgmNo());
							newSsr.setErpDocRefNo(con.getIgmTransId());
							newSsr.setExecutionUnit(s.getExecutionUnit());
							newSsr.setGateOutType(con.getGateOutType());
							newSsr.setIgmLineNo(con.getIgmLineNo());
							newSsr.setNoOfPackages(con.getNoOfPackages());
							newSsr.setProfitcentreId(con.getProfitcentreId());
							newSsr.setRate(s.getRate());
							newSsr.setSa(igm.getShippingAgent());
							newSsr.setServiceId(s.getServiceId());
							newSsr.setServiceUnit(s.getServiceUnit());
							newSsr.setServiceUnit1(ser.getServiceUnit1());
							newSsr.setSl(igm.getShippingLine());
							newSsr.setSrNo(new BigDecimal(0));
							newSsr.setSsrModeFor(ssr.getSsrModeFor());
							newSsr.setSsrRefNo(newId);
							newSsr.setStatus('A');
							newSsr.setTotalRate(s.getTotalRate());
							newSsr.setTransDate(new Date());
							newSsr.setTransId(HoldNextIdD1);
							newSsr.setTransLineNo(new BigDecimal(1));
							newSsr.setAccId(crg.getAccountHolderId());
							newSsr.setImpId(crg.getImporterId());

							ssrdtlrepo.save(newSsr);

							cn.setSsrTransId(HoldNextIdD1);
							cfigmcnrepo.save(cn);

							processnextidrepo.updateAuditTrail(cid, bid, "P05080", HoldNextIdD1, "2024");
							processnextidrepo.updateAuditTrail(cid, bid, "P05081", newId, "2024");

							ssr.setTransId(HoldNextIdD1);
						}

					});
				}

			});

		} else {

			service.stream().forEach(s -> {

				Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());

				if (ser != null) {

					String oldRefNo = ssrdtlrepo.getSSRReferenceNo(cid, bid, ssr.getTransId(), s.getServiceId());

					if (oldRefNo != null && !oldRefNo.isEmpty()) {

						CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo());

						Cfigmcrg crg = cfigmcrgrepo.getDataByIgmAndLine(cid, bid, ssr.getErpDocRefNo(),
								ssr.getDocRefNo(), ssr.getIgmLineNo());

						container.stream().forEach(con -> {

							Cfigmcn cn = cfigmcnrepo.getSingleData6(cid, bid, con.getIgmTransId(), con.getIgmNo(),
									con.getIgmLineNo(), con.getContainerNo(), ssr.getTransId());

							Boolean checkServiceConAlreadyExist = ssrdtlrepo.checkSSRReferenceNo(cid, bid,
									ssr.getTransId(), s.getServiceId(), con.getContainerNo());
							System.out.println("crg " + crg.getAccountHolderId() + " " + crg.getImporterId());

							if (igm != null && crg != null && cn != null && !checkServiceConAlreadyExist) {

								SSRDtl newSsr = new SSRDtl();

								newSsr.setApprovedBy(user);
								newSsr.setApprovedDate(new Date());
								newSsr.setBeDate(crg.getBeDate());
								newSsr.setBeNo(crg.getBeNo());
								newSsr.setBlDate(crg.getBlDate());
								newSsr.setBlNo(crg.getBlNo());
								newSsr.setBranchId(bid);
								newSsr.setCargoWt(cn.getCargoWt());
								newSsr.setCha(con.getCha());
								newSsr.setCommodityDescription(crg.getCommodityDescription());
								newSsr.setCompanyId(cid);
								newSsr.setContainerNo(con.getContainerNo());
								newSsr.setContainerSize(con.getContainerSize());
								newSsr.setContainerType(con.getContainerType());
								newSsr.setCreatedBy(user);
								newSsr.setCreatedDate(new Date());
								newSsr.setDocRefDate(igm.getIgmDate());
								newSsr.setDocRefNo(con.getIgmNo());
								newSsr.setErpDocRefNo(con.getIgmTransId());
								newSsr.setExecutionUnit(s.getExecutionUnit());
								newSsr.setGateOutType(con.getGateOutType());
								newSsr.setIgmLineNo(con.getIgmLineNo());
								newSsr.setNoOfPackages(con.getNoOfPackages());
								newSsr.setProfitcentreId(con.getProfitcentreId());
								newSsr.setRate(s.getRate());
								newSsr.setSa(igm.getShippingAgent());
								newSsr.setServiceId(s.getServiceId());
								newSsr.setServiceUnit(s.getServiceUnit());
								newSsr.setServiceUnit1(ser.getServiceUnit1());
								newSsr.setSl(igm.getShippingLine());
								newSsr.setSrNo(new BigDecimal(0));
								newSsr.setSsrModeFor(ssr.getSsrModeFor());
								newSsr.setSsrRefNo(oldRefNo);
								newSsr.setStatus('A');
								newSsr.setTotalRate(s.getTotalRate());
								newSsr.setTransDate(ssr.getTransDate());
								newSsr.setTransId(ssr.getTransId());
								newSsr.setTransLineNo(new BigDecimal(1));
								newSsr.setAccId(crg.getAccountHolderId());
								newSsr.setImpId(crg.getImporterId());

								ssrdtlrepo.save(newSsr);

								cn.setSsrTransId(ssr.getTransId());
								cfigmcnrepo.save(cn);
							}

						});
					} else {
						String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05081", "2024");

						String[] parts = lastValue.split("/");
						String baseId = parts[0];
						String baseId1 = parts[1];
						String financialYear = parts[2];

						// Increment the numerical part
						int newVal = Integer.parseInt(baseId1) + 1;

						// Format newVal to maintain leading zeros (e.g., 0001)
						String formattedNewVal = String.format("%04d", newVal);

						// Get the current financial year
						String currentFinancialYear = getCurrentFinancialYear();

						// Construct the new ID
						String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;

						CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo());

						Cfigmcrg crg = cfigmcrgrepo.getDataByIgmAndLine(cid, bid, ssr.getErpDocRefNo(),
								ssr.getDocRefNo(), ssr.getIgmLineNo());

						container.stream().forEach(con -> {

							Cfigmcn cn = cfigmcnrepo.getSingleData6(cid, bid, con.getIgmTransId(), con.getIgmNo(),
									con.getIgmLineNo(), con.getContainerNo(), ssr.getTransId());

							System.out.println("crg " + crg.getAccountHolderId() + " " + crg.getImporterId());

							if (igm != null && crg != null && cn != null) {

								SSRDtl newSsr = new SSRDtl();

								newSsr.setApprovedBy(user);
								newSsr.setApprovedDate(new Date());
								newSsr.setBeDate(crg.getBeDate());
								newSsr.setBeNo(crg.getBeNo());
								newSsr.setBlDate(crg.getBlDate());
								newSsr.setBlNo(crg.getBlNo());
								newSsr.setBranchId(bid);
								newSsr.setCargoWt(cn.getCargoWt());
								newSsr.setCha(con.getCha());
								newSsr.setCommodityDescription(crg.getCommodityDescription());
								newSsr.setCompanyId(cid);
								newSsr.setContainerNo(con.getContainerNo());
								newSsr.setContainerSize(con.getContainerSize());
								newSsr.setContainerType(con.getContainerType());
								newSsr.setCreatedBy(user);
								newSsr.setCreatedDate(new Date());
								newSsr.setDocRefDate(igm.getIgmDate());
								newSsr.setDocRefNo(con.getIgmNo());
								newSsr.setErpDocRefNo(con.getIgmTransId());
								newSsr.setExecutionUnit(s.getExecutionUnit());
								newSsr.setGateOutType(con.getGateOutType());
								newSsr.setIgmLineNo(con.getIgmLineNo());
								newSsr.setNoOfPackages(con.getNoOfPackages());
								newSsr.setProfitcentreId(con.getProfitcentreId());
								newSsr.setRate(s.getRate());
								newSsr.setSa(igm.getShippingAgent());
								newSsr.setServiceId(s.getServiceId());
								newSsr.setServiceUnit(s.getServiceUnit());
								newSsr.setServiceUnit1(ser.getServiceUnit1());
								newSsr.setSl(igm.getShippingLine());
								newSsr.setSrNo(new BigDecimal(0));
								newSsr.setSsrModeFor(ssr.getSsrModeFor());
								newSsr.setSsrRefNo(newId);
								newSsr.setStatus('A');
								newSsr.setTotalRate(s.getTotalRate());
								newSsr.setTransDate(ssr.getTransDate());
								newSsr.setTransId(ssr.getTransId());
								newSsr.setTransLineNo(new BigDecimal(1));
								newSsr.setAccId(crg.getAccountHolderId());
								newSsr.setImpId(crg.getImporterId());

								ssrdtlrepo.save(newSsr);

								cn.setSsrTransId(ssr.getTransId());
								cfigmcnrepo.save(cn);

								processnextidrepo.updateAuditTrail(cid, bid, "P05081", newId, "2024");

							}

						});
					}

				}

			});
		}

		Map<String, Object> result = new HashMap<>();

		List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, ssr.getTransId());

		List<Cfigmcn> getCnData = cfigmcnrepo.getLatestSSRData(cid, bid, ssr.getErpDocRefNo(), ssr.getDocRefNo(),
				ssr.getIgmLineNo(), ssr.getTransId());

		List<SSRDtl> ssrData = ssrdtlrepo.getSingleData(cid, bid, ssr.getTransId());

		result.put("refNoData", ssrRefNoData);
		result.put("con", getCnData);
		result.put("ssr", ssrData.get(0));

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	private static String getCurrentFinancialYear() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();

		// Check if the current date is before or after April 1st
		if (month < 4 || (month == 4 && day < 1)) {
			year--; // If before April 1st, consider the previous year
		}

		// Calculate financial year start and end
		int financialYearStart = year % 100; // Current year for start
		int financialYearEnd = (year + 1) % 100; // Next year for end

		// Format as YY-YY
		return String.format("%02d-%02d", financialYearStart, financialYearEnd);
	}

	@GetMapping("/searchSSR")
	public ResponseEntity<?> searchSSR(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = ssrdtlrepo.searchSSR(cid, bid, val,"N00002");

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getDataByTransId")
	public ResponseEntity<?> getDataByTransId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("transid") String transid, @RequestParam("igmtrans") String igmtrans,
			@RequestParam("igmno") String igmno, @RequestParam("line") String line) {

		Map<String, Object> result = new HashMap<>();

		List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, transid);

		if (ssrRefNoData.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		List<Cfigmcn> getCnData = cfigmcnrepo.getLatestSSRData(cid, bid, igmtrans, igmno, line, transid);

		if (getCnData.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		List<SSRDtl> ssrData = ssrdtlrepo.getSingleData(cid, bid, transid);

		if (ssrData.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		result.put("refNoData", ssrRefNoData);
		result.put("con", getCnData);
		result.put("ssr", ssrData.get(0));

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/searchBySbNo")
	public ResponseEntity<?> searchBySbNo(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("sb") String sb) {

		List<Object[]> data = ssrdtlrepo.getBeforeSaveDataFromSbNo(cid, bid, sb);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveExportSSR")
	public ResponseEntity<?> saveExportData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		SSRDtl ssr = mapper.readValue(mapper.writeValueAsString(data.get("ssr")), SSRDtl.class);

		List<ExportSSRDto> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
				new TypeReference<List<ExportSSRDto>>() {
				});

		List<SSRDto> service = mapper.readValue(mapper.writeValueAsString(data.get("service")),
				new TypeReference<List<SSRDto>>() {
				});

		if (ssr == null) {
			return new ResponseEntity<>("SSR data not found.", HttpStatus.CONFLICT);
		}

		if (container.isEmpty()) {
			return new ResponseEntity<>("Container data not found.", HttpStatus.CONFLICT);
		}

		if (service.isEmpty()) {
			return new ResponseEntity<>("Service data not found.", HttpStatus.CONFLICT);
		}


		if (ssr.getTransId() == null || ssr.getTransId().isEmpty()) {

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05102", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("ESSR%06d", nextNumericNextID1);

			service.stream().forEach(s -> {

				Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());

				if (ser != null) {

					String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05081", "2024");

					String[] parts = lastValue.split("/");
					String baseId = parts[0];
					String baseId1 = parts[1];
					String financialYear = parts[2];

					// Increment the numerical part
					int newVal = Integer.parseInt(baseId1) + 1;

					// Format newVal to maintain leading zeros (e.g., 0001)
					String formattedNewVal = String.format("%04d", newVal);

					// Get the current financial year
					String currentFinancialYear = getCurrentFinancialYear();

					// Construct the new ID
					String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;

					container.stream().forEach(con -> {

						SSRDtl newSsr = new SSRDtl();

						newSsr.setApprovedBy(user);
						newSsr.setApprovedDate(new Date());
						newSsr.setBeDate(null);
						newSsr.setBeNo("");
						newSsr.setBlDate(null);
						newSsr.setBlNo("");
						newSsr.setBranchId(bid);
						newSsr.setCargoWt(con.getCargoWt());
						newSsr.setCha(con.getCha());
						newSsr.setCommodityDescription(ssr.getCommodityDescription());
						newSsr.setCompanyId(cid);
						newSsr.setContainerNo(con.getContainerNo());
						newSsr.setContainerSize(con.getContainerSize());
						newSsr.setContainerType(con.getContainerType());
						newSsr.setCreatedBy(user);
						newSsr.setCreatedDate(new Date());
						newSsr.setDocRefDate(ssr.getDocRefDate());
						newSsr.setDocRefNo(ssr.getDocRefNo());
						newSsr.setErpDocRefNo(ssr.getErpDocRefNo());
						newSsr.setExecutionUnit(s.getExecutionUnit());
						newSsr.setGateOutType(con.getGateOutType());
						newSsr.setIgmLineNo("");
						newSsr.setNoOfPackages(con.getNoOfPackages());
						newSsr.setProfitcentreId(ssr.getProfitcentreId());
						newSsr.setRate(s.getRate());
						newSsr.setSa(con.getSa());
						newSsr.setServiceId(s.getServiceId());
						newSsr.setServiceUnit(s.getServiceUnit());
						newSsr.setServiceUnit1(ser.getServiceUnit1());
						newSsr.setSl(con.getSl());
						newSsr.setSrNo(new BigDecimal(0));
						newSsr.setSsrModeFor(ssr.getSsrModeFor());
						newSsr.setSsrRefNo(newId);
						newSsr.setStatus('A');
						newSsr.setTotalRate(s.getTotalRate());
						newSsr.setTransDate(new Date());
						newSsr.setTransId(HoldNextIdD1);
						newSsr.setTransLineNo(new BigDecimal(1));
						newSsr.setAccId(con.getOnAccountOf());
						newSsr.setImpId(con.getImpId());

						ssrdtlrepo.save(newSsr);

						processnextidrepo.updateAuditTrail(cid, bid, "P05102", HoldNextIdD1, "2024");
						processnextidrepo.updateAuditTrail(cid, bid, "P05081", newId, "2024");

						ssr.setTransId(HoldNextIdD1);
						
						
						int updateExportStuffTallySSR = exportStuffTallyRepo.updateSSRData(cid, bid, ssr.getDocRefNo(), ssr.getErpDocRefNo(), con.getContainerNo(),HoldNextIdD1);


					});
				}

			});
			
			int updateSbSSR = exportsbrepo.updateSSRData(cid, bid, ssr.getDocRefNo(), ssr.getErpDocRefNo(), HoldNextIdD1);
			
			int updateExportStuffReqSSR = exportStuffReqRepo.updateSSRData(cid, bid, ssr.getDocRefNo(), ssr.getErpDocRefNo(), HoldNextIdD1);

		} else {

			service.stream().forEach(s -> {

				Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());
				

				if (ser != null) {

					String oldRefNo = ssrdtlrepo.getSSRReferenceNo(cid, bid, ssr.getTransId(), s.getServiceId());

					if (oldRefNo != null && !oldRefNo.isEmpty()) {

						container.stream().forEach(con -> {

							Boolean checkServiceConAlreadyExist = ssrdtlrepo.checkSSRReferenceNo(cid, bid,
									ssr.getTransId(), s.getServiceId(), con.getContainerNo());

							if (!checkServiceConAlreadyExist) {

								SSRDtl newSsr = new SSRDtl();

								newSsr.setApprovedBy(user);
								newSsr.setApprovedDate(new Date());
								newSsr.setBeDate(null);
								newSsr.setBeNo("");
								newSsr.setBlDate(null);
								newSsr.setBlNo("");
								newSsr.setBranchId(bid);
								newSsr.setCargoWt(con.getCargoWt());
								newSsr.setCha(con.getCha());
								newSsr.setCommodityDescription(ssr.getCommodityDescription());
								newSsr.setCompanyId(cid);
								newSsr.setContainerNo(con.getContainerNo());
								newSsr.setContainerSize(con.getContainerSize());
								newSsr.setContainerType(con.getContainerType());
								newSsr.setCreatedBy(user);
								newSsr.setCreatedDate(new Date());
								newSsr.setDocRefDate(ssr.getDocRefDate());
								newSsr.setDocRefNo(ssr.getDocRefNo());
								newSsr.setErpDocRefNo(ssr.getErpDocRefNo());
								newSsr.setExecutionUnit(s.getExecutionUnit());
								newSsr.setGateOutType(con.getGateOutType());
								newSsr.setIgmLineNo("");
								newSsr.setNoOfPackages(con.getNoOfPackages());
								newSsr.setProfitcentreId(ssr.getProfitcentreId());
								newSsr.setRate(s.getRate());
								newSsr.setSa(con.getSa());
								newSsr.setServiceId(s.getServiceId());
								newSsr.setServiceUnit(s.getServiceUnit());
								newSsr.setServiceUnit1(ser.getServiceUnit1());
								newSsr.setSl(con.getSl());
								newSsr.setSrNo(new BigDecimal(0));
								newSsr.setSsrModeFor(ssr.getSsrModeFor());
								newSsr.setSsrRefNo(oldRefNo);
								newSsr.setStatus('A');
								newSsr.setTotalRate(s.getTotalRate());
								newSsr.setTransDate(new Date());
								newSsr.setTransId(ssr.getTransId());
								newSsr.setTransLineNo(new BigDecimal(1));
								newSsr.setAccId(con.getOnAccountOf());
								newSsr.setImpId(con.getImpId());

								ssrdtlrepo.save(newSsr);
							}

						});
					} else {
						String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05081", "2024");

						String[] parts = lastValue.split("/");
						String baseId = parts[0];
						String baseId1 = parts[1];
						String financialYear = parts[2];

						// Increment the numerical part
						int newVal = Integer.parseInt(baseId1) + 1;

						// Format newVal to maintain leading zeros (e.g., 0001)
						String formattedNewVal = String.format("%04d", newVal);

						// Get the current financial year
						String currentFinancialYear = getCurrentFinancialYear();

						// Construct the new ID
						String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;

						container.stream().forEach(con -> {
							SSRDtl newSsr = new SSRDtl();

							newSsr.setApprovedBy(user);
							newSsr.setApprovedDate(new Date());
							newSsr.setBeDate(null);
							newSsr.setBeNo("");
							newSsr.setBlDate(null);
							newSsr.setBlNo("");
							newSsr.setBranchId(bid);
							newSsr.setCargoWt(con.getCargoWt());
							newSsr.setCha(con.getCha());
							newSsr.setCommodityDescription(ssr.getCommodityDescription());
							newSsr.setCompanyId(cid);
							newSsr.setContainerNo(con.getContainerNo());
							newSsr.setContainerSize(con.getContainerSize());
							newSsr.setContainerType(con.getContainerType());
							newSsr.setCreatedBy(user);
							newSsr.setCreatedDate(new Date());
							newSsr.setDocRefDate(ssr.getDocRefDate());
							newSsr.setDocRefNo(ssr.getDocRefNo());
							newSsr.setErpDocRefNo(ssr.getErpDocRefNo());
							newSsr.setExecutionUnit(s.getExecutionUnit());
							newSsr.setGateOutType(con.getGateOutType());
							newSsr.setIgmLineNo("");
							newSsr.setNoOfPackages(con.getNoOfPackages());
							newSsr.setProfitcentreId(ssr.getProfitcentreId());
							newSsr.setRate(s.getRate());
							newSsr.setSa(con.getSa());
							newSsr.setServiceId(s.getServiceId());
							newSsr.setServiceUnit(s.getServiceUnit());
							newSsr.setServiceUnit1(ser.getServiceUnit1());
							newSsr.setSl(con.getSl());
							newSsr.setSrNo(new BigDecimal(0));
							newSsr.setSsrModeFor(ssr.getSsrModeFor());
							newSsr.setSsrRefNo(newId);
							newSsr.setStatus('A');
							newSsr.setTotalRate(s.getTotalRate());
							newSsr.setTransDate(new Date());
							newSsr.setTransId(ssr.getTransId());
							newSsr.setTransLineNo(new BigDecimal(1));
							newSsr.setAccId(con.getOnAccountOf());
							newSsr.setImpId(con.getImpId());

							ssrdtlrepo.save(newSsr);

							processnextidrepo.updateAuditTrail(cid, bid, "P05081", newId, "2024");

						});
					}

				}

			});
		}

		Map<String, Object> result = new HashMap<>();

		List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, ssr.getTransId());

		List<Object[]> getCnData = ssrdtlrepo.getBeforeSaveDataFromSbNo1(cid, bid, ssr.getDocRefNo());

		List<SSRDtl> ssrData = ssrdtlrepo.getSingleData(cid, bid, ssr.getTransId());

		result.put("refNoData", ssrRefNoData);
		result.put("con", getCnData);
		result.put("ssr", ssrData.get(0));

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@GetMapping("/searchExportSSR")
	public ResponseEntity<?> searchExportSSR(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = ssrdtlrepo.searchSSR(cid, bid, val,"N00004");

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getExportDataByTransId")
	public ResponseEntity<?> getExportDataByTransId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("transid") String transid,@RequestParam("sb") String sb) {

		Map<String, Object> result = new HashMap<>();

		List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, transid);

		if (ssrRefNoData.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		List<Object[]> getCnData = ssrdtlrepo.getBeforeSaveDataFromSbNo1(cid, bid, sb);

		if (getCnData.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		List<SSRDtl> ssrData = ssrdtlrepo.getSingleData(cid, bid, transid);

		if (ssrData.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		result.put("refNoData", ssrRefNoData);
		result.put("con", getCnData);
		result.put("ssr", ssrData.get(0));

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@GetMapping("/getDataForNOCSSR")
	public ResponseEntity<?> getDataForNOCSSR(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("trans") String trans, @RequestParam("boe") String boe) {

		Object data = cfbondnocrepo.getDataForNOCSSR(cid, bid, trans, boe);

		if (data == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveBondNOCSSR")
	public ResponseEntity<?> saveBondNOCSSR(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("type") String type, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		SSRDtl ssr = mapper.readValue(mapper.writeValueAsString(data.get("ssr")), SSRDtl.class);

		List<SSRDto> service = mapper.readValue(mapper.writeValueAsString(data.get("service")),
				new TypeReference<List<SSRDto>>() {
				});

		if (ssr == null) {
			return new ResponseEntity<>("SSR data not found.", HttpStatus.CONFLICT);
		}

		if (service.isEmpty()) {
			return new ResponseEntity<>("Service data not found.", HttpStatus.CONFLICT);
		}

		if ("NOC".equals(type)) {
			Object nocData = cfbondnocrepo.getDataForNOCSSR(cid, bid, ssr.getErpDocRefNo(), ssr.getBeNo());
			if (nocData == null) {
				return new ResponseEntity<>("NOC data not found.", HttpStatus.CONFLICT);
			}

			Object[] noc = (Object[]) nocData;

			if (ssr.getTransId() == null || ssr.getTransId().isEmpty()) {

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05104", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("BSSR%06d", nextNumericNextID1);

				service.stream().forEach(s -> {

					Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());

					if (ser != null) {

						String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05103", "2024");

						String[] parts = lastValue.split("/");
						String baseId = parts[0];
						String baseId1 = parts[1];
						String financialYear = parts[2];

						// Increment the numerical part
						int newVal = Integer.parseInt(baseId1) + 1;

						// Format newVal to maintain leading zeros (e.g., 0001)
						String formattedNewVal = String.format("%04d", newVal);

						// Get the current financial year
						String currentFinancialYear = getCurrentFinancialYear();

						// Construct the new ID
						String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;

						SSRDtl newSsr = new SSRDtl();

						newSsr.setApprovedBy(user);
						newSsr.setApprovedDate(new Date());
						newSsr.setBeDate(ssr.getBeDate());
						newSsr.setBeNo(ssr.getBeNo());
						newSsr.setBlDate(null);
						newSsr.setBlNo("");
						newSsr.setBranchId(bid);
						newSsr.setCargoWt(BigDecimal.ZERO);
						newSsr.setCha(String.valueOf(noc[7]));
						newSsr.setCommodityDescription(ssr.getCommodityDescription());
						newSsr.setCompanyId(cid);
						newSsr.setCreatedBy(user);
						newSsr.setCreatedDate(new Date());
						newSsr.setDocRefDate(ssr.getDocRefDate());
						newSsr.setDocRefNo(ssr.getDocRefNo());
						newSsr.setErpDocRefNo(ssr.getErpDocRefNo());
						newSsr.setExecutionUnit(s.getExecutionUnit());
						newSsr.setSsrModeFor(ssr.getSsrModeFor());
						newSsr.setIgmLineNo(ssr.getIgmLineNo());
						newSsr.setNoOfPackages(0);
						newSsr.setProfitcentreId(ssr.getProfitcentreId());
						newSsr.setRate(s.getRate());
						newSsr.setServiceId(s.getServiceId());
						newSsr.setServiceUnit(s.getServiceUnit());
						newSsr.setServiceUnit1(ser.getServiceUnit1());
						newSsr.setSrNo(new BigDecimal(0));
						newSsr.setSsrModeFor(ssr.getSsrModeFor());
						newSsr.setSsrType("NOC");
						newSsr.setSsrRefNo(newId);
						newSsr.setStatus('A');
						newSsr.setTotalRate(s.getTotalRate());
						newSsr.setTransDate(new Date());
						newSsr.setTransId(HoldNextIdD1);
						newSsr.setTransLineNo(new BigDecimal(1));
						newSsr.setAccId(String.valueOf(noc[9]));
						newSsr.setImpId(ssr.getImpId());
						newSsr.setInBondId(ssr.getInBondId());
						newSsr.setInBondDate(ssr.getInBondDate());
						newSsr.setNocNo(ssr.getNocNo());
						newSsr.setNocDate(ssr.getNocDate());
						newSsr.setContainerNo("");

						ssrdtlrepo.save(newSsr);

						processnextidrepo.updateAuditTrail(cid, bid, "P05104", HoldNextIdD1, "2024");
						processnextidrepo.updateAuditTrail(cid, bid, "P05103", newId, "2024");

						ssr.setTransId(HoldNextIdD1);

					}

				});

				int updateSSr = cfbondnocrepo.updateSSRData(cid, bid, ssr.getErpDocRefNo(), ssr.getNocNo(),
						HoldNextIdD1);

			} else {

				service.stream().forEach(s -> {

					Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());

					if (ser != null) {

						String oldRefNo = ssrdtlrepo.getSSRReferenceNo(cid, bid, ssr.getTransId(), s.getServiceId());

						if (oldRefNo != null && !oldRefNo.isEmpty()) {

							Boolean checkServiceConAlreadyExist = ssrdtlrepo.checkSSRReferenceNo1(cid, bid,
									ssr.getTransId(), s.getServiceId(), ssr.getNocNo());

							if (!checkServiceConAlreadyExist) {

								SSRDtl newSsr = new SSRDtl();

								newSsr.setApprovedBy(user);
								newSsr.setApprovedDate(new Date());
								newSsr.setBeDate(ssr.getBeDate());
								newSsr.setBeNo(ssr.getBeNo());
								newSsr.setBlDate(null);
								newSsr.setBlNo("");
								newSsr.setBranchId(bid);
								newSsr.setCargoWt(BigDecimal.ZERO);
								newSsr.setCha(String.valueOf(noc[7]));
								newSsr.setCommodityDescription(ssr.getCommodityDescription());
								newSsr.setCompanyId(cid);
								newSsr.setCreatedBy(user);
								newSsr.setCreatedDate(new Date());
								newSsr.setDocRefDate(ssr.getDocRefDate());
								newSsr.setDocRefNo(ssr.getDocRefNo());
								newSsr.setErpDocRefNo(ssr.getErpDocRefNo());
								newSsr.setExecutionUnit(s.getExecutionUnit());
								newSsr.setSsrModeFor(ssr.getSsrModeFor());
								newSsr.setSsrType("NOC");
								newSsr.setIgmLineNo(ssr.getIgmLineNo());
								newSsr.setNoOfPackages(0);
								newSsr.setProfitcentreId(ssr.getProfitcentreId());
								newSsr.setRate(s.getRate());
								newSsr.setServiceId(s.getServiceId());
								newSsr.setServiceUnit(s.getServiceUnit());
								newSsr.setServiceUnit1(ser.getServiceUnit1());
								newSsr.setSrNo(new BigDecimal(0));
								newSsr.setSsrModeFor(ssr.getSsrModeFor());
								newSsr.setSsrRefNo(oldRefNo);
								newSsr.setStatus('A');
								newSsr.setTotalRate(s.getTotalRate());
								newSsr.setTransDate(new Date());
								newSsr.setTransId(ssr.getTransId());
								newSsr.setTransLineNo(new BigDecimal(1));
								newSsr.setAccId(String.valueOf(noc[9]));
								newSsr.setImpId(ssr.getImpId());
								newSsr.setInBondId(ssr.getInBondId());
								newSsr.setInBondDate(ssr.getInBondDate());
								newSsr.setNocNo(ssr.getNocNo());
								newSsr.setNocDate(ssr.getNocDate());
								newSsr.setContainerNo("");

								ssrdtlrepo.save(newSsr);
							}

						} else {
							String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05103", "2024");

							String[] parts = lastValue.split("/");
							String baseId = parts[0];
							String baseId1 = parts[1];
							String financialYear = parts[2];

							// Increment the numerical part
							int newVal = Integer.parseInt(baseId1) + 1;

							// Format newVal to maintain leading zeros (e.g., 0001)
							String formattedNewVal = String.format("%04d", newVal);

							// Get the current financial year
							String currentFinancialYear = getCurrentFinancialYear();

							// Construct the new ID
							String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;

							SSRDtl newSsr = new SSRDtl();

							newSsr.setApprovedBy(user);
							newSsr.setApprovedDate(new Date());
							newSsr.setBeDate(ssr.getBeDate());
							newSsr.setBeNo(ssr.getBeNo());
							newSsr.setBlDate(null);
							newSsr.setBlNo("");
							newSsr.setBranchId(bid);
							newSsr.setCargoWt(BigDecimal.ZERO);
							newSsr.setCha(String.valueOf(noc[7]));
							newSsr.setCommodityDescription(ssr.getCommodityDescription());
							newSsr.setCompanyId(cid);
							newSsr.setCreatedBy(user);
							newSsr.setCreatedDate(new Date());
							newSsr.setDocRefDate(ssr.getDocRefDate());
							newSsr.setDocRefNo(ssr.getDocRefNo());
							newSsr.setErpDocRefNo(ssr.getErpDocRefNo());
							newSsr.setExecutionUnit(s.getExecutionUnit());
							newSsr.setSsrModeFor(ssr.getSsrModeFor());
							newSsr.setSsrType("NOC");
							newSsr.setIgmLineNo(ssr.getIgmLineNo());
							newSsr.setNoOfPackages(0);
							newSsr.setProfitcentreId(ssr.getProfitcentreId());
							newSsr.setRate(s.getRate());
							newSsr.setServiceId(s.getServiceId());
							newSsr.setServiceUnit(s.getServiceUnit());
							newSsr.setServiceUnit1(ser.getServiceUnit1());
							newSsr.setSrNo(new BigDecimal(0));
							newSsr.setSsrModeFor(ssr.getSsrModeFor());
							newSsr.setSsrRefNo(newId);
							newSsr.setStatus('A');
							newSsr.setTotalRate(s.getTotalRate());
							newSsr.setTransDate(new Date());
							newSsr.setTransId(ssr.getTransId());
							newSsr.setTransLineNo(new BigDecimal(1));
							newSsr.setAccId(String.valueOf(noc[9]));
							newSsr.setImpId(ssr.getImpId());
							newSsr.setInBondId(ssr.getInBondId());
							newSsr.setInBondDate(ssr.getInBondDate());
							newSsr.setNocNo(ssr.getNocNo());
							newSsr.setNocDate(ssr.getNocDate());
							newSsr.setContainerNo("");

							ssrdtlrepo.save(newSsr);

							processnextidrepo.updateAuditTrail(cid, bid, "P05103", newId, "2024");

						}

					}

				});
			}

			Map<String, Object> result = new HashMap<>();

			List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, ssr.getTransId());

			List<Object[]> ssrData = ssrdtlrepo.getSingleData1(cid, bid, ssr.getTransId());

			result.put("refNoData", ssrRefNoData);
			result.put("ssr", ssrData.get(0));

			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			Object nocData = cfbondnocrepo.getDataForExbondSSR(cid, bid, ssr.getExBondBeId());
			if (nocData == null) {
				return new ResponseEntity<>("Exbond data not found.", HttpStatus.CONFLICT);
			}

			Object[] noc = (Object[]) nocData;

			if (ssr.getTransId() == null || ssr.getTransId().isEmpty()) {

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05104", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("BSSR%06d", nextNumericNextID1);

				service.stream().forEach(s -> {

					Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());

					if (ser != null) {

						String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05103", "2024");

						String[] parts = lastValue.split("/");
						String baseId = parts[0];
						String baseId1 = parts[1];
						String financialYear = parts[2];

						// Increment the numerical part
						int newVal = Integer.parseInt(baseId1) + 1;

						// Format newVal to maintain leading zeros (e.g., 0001)
						String formattedNewVal = String.format("%04d", newVal);

						// Get the current financial year
						String currentFinancialYear = getCurrentFinancialYear();

						// Construct the new ID
						String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;

						SSRDtl newSsr = new SSRDtl();

						newSsr.setApprovedBy(user);
						newSsr.setApprovedDate(new Date());
						newSsr.setBeDate(ssr.getBeDate());
						newSsr.setBeNo(ssr.getBeNo());
						newSsr.setBlDate(null);
						newSsr.setBlNo("");
						newSsr.setBranchId(bid);
						newSsr.setCargoWt(BigDecimal.ZERO);
						newSsr.setCha(String.valueOf(noc[7]));
						newSsr.setCommodityDescription(ssr.getCommodityDescription());
						newSsr.setCompanyId(cid);
						newSsr.setCreatedBy(user);
						newSsr.setCreatedDate(new Date());
						newSsr.setDocRefDate(ssr.getDocRefDate());
						newSsr.setDocRefNo(ssr.getDocRefNo());
						newSsr.setErpDocRefNo(ssr.getErpDocRefNo());
						newSsr.setExecutionUnit(s.getExecutionUnit());
						newSsr.setSsrModeFor(ssr.getSsrModeFor());
						newSsr.setIgmLineNo(ssr.getIgmLineNo());
						newSsr.setNoOfPackages(0);
						newSsr.setProfitcentreId(ssr.getProfitcentreId());
						newSsr.setRate(s.getRate());
						newSsr.setServiceId(s.getServiceId());
						newSsr.setServiceUnit(s.getServiceUnit());
						newSsr.setServiceUnit1(ser.getServiceUnit1());
						newSsr.setSrNo(new BigDecimal(0));
						newSsr.setSsrModeFor(ssr.getSsrModeFor());
						newSsr.setSsrType("EXBOND");
						newSsr.setSsrRefNo(newId);
						newSsr.setStatus('A');
						newSsr.setTotalRate(s.getTotalRate());
						newSsr.setTransDate(new Date());
						newSsr.setTransId(HoldNextIdD1);
						newSsr.setTransLineNo(new BigDecimal(1));
						newSsr.setAccId(String.valueOf(noc[9]));
						newSsr.setImpId(ssr.getImpId());
						newSsr.setExBondId(ssr.getExBondId());
						newSsr.setExBondDate(ssr.getExBondDate());
						newSsr.setExBondBeDate(ssr.getExBondBeDate());
						newSsr.setExBondBeId(ssr.getExBondBeId());
						newSsr.setNocNo(ssr.getNocNo());
						newSsr.setNocDate(ssr.getNocDate());
						newSsr.setContainerNo("");

						ssrdtlrepo.save(newSsr);

						processnextidrepo.updateAuditTrail(cid, bid, "P05104", HoldNextIdD1, "2024");
						processnextidrepo.updateAuditTrail(cid, bid, "P05103", newId, "2024");

						ssr.setTransId(HoldNextIdD1);

					}

				});

				int updateSSr = cfbondnocrepo.updateSSRData1(cid, bid, ssr.getErpDocRefNo(), ssr.getNocNo(),ssr.getExBondId(),
						HoldNextIdD1);

			} else {

				service.stream().forEach(s -> {

					Services ser = serViceRepositary.getDataById(cid, bid, s.getServiceId());

					if (ser != null) {

						String oldRefNo = ssrdtlrepo.getSSRReferenceNo(cid, bid, ssr.getTransId(), s.getServiceId());

						if (oldRefNo != null && !oldRefNo.isEmpty()) {

							Boolean checkServiceConAlreadyExist = ssrdtlrepo.checkSSRReferenceNo1(cid, bid,
									ssr.getTransId(), s.getServiceId(), ssr.getNocNo());

							if (!checkServiceConAlreadyExist) {

								SSRDtl newSsr = new SSRDtl();

								newSsr.setApprovedBy(user);
								newSsr.setApprovedDate(new Date());
								newSsr.setBeDate(ssr.getBeDate());
								newSsr.setBeNo(ssr.getBeNo());
								newSsr.setBlDate(null);
								newSsr.setBlNo("");
								newSsr.setBranchId(bid);
								newSsr.setCargoWt(BigDecimal.ZERO);
								newSsr.setCha(String.valueOf(noc[7]));
								newSsr.setCommodityDescription(ssr.getCommodityDescription());
								newSsr.setCompanyId(cid);
								newSsr.setCreatedBy(user);
								newSsr.setCreatedDate(new Date());
								newSsr.setDocRefDate(ssr.getDocRefDate());
								newSsr.setDocRefNo(ssr.getDocRefNo());
								newSsr.setErpDocRefNo(ssr.getErpDocRefNo());
								newSsr.setExecutionUnit(s.getExecutionUnit());
								newSsr.setSsrModeFor(ssr.getSsrModeFor());
								newSsr.setSsrType("EXBOND");
								newSsr.setIgmLineNo(ssr.getIgmLineNo());
								newSsr.setNoOfPackages(0);
								newSsr.setProfitcentreId(ssr.getProfitcentreId());
								newSsr.setRate(s.getRate());
								newSsr.setServiceId(s.getServiceId());
								newSsr.setServiceUnit(s.getServiceUnit());
								newSsr.setServiceUnit1(ser.getServiceUnit1());
								newSsr.setSrNo(new BigDecimal(0));
								newSsr.setSsrModeFor(ssr.getSsrModeFor());
								newSsr.setSsrRefNo(oldRefNo);
								newSsr.setStatus('A');
								newSsr.setTotalRate(s.getTotalRate());
								newSsr.setTransDate(new Date());
								newSsr.setTransId(ssr.getTransId());
								newSsr.setTransLineNo(new BigDecimal(1));
								newSsr.setAccId(String.valueOf(noc[9]));
								newSsr.setImpId(ssr.getImpId());
								newSsr.setExBondId(ssr.getExBondId());
								newSsr.setExBondDate(ssr.getExBondDate());
								newSsr.setExBondBeDate(ssr.getExBondBeDate());
								newSsr.setExBondBeId(ssr.getExBondBeId());
								newSsr.setNocNo(ssr.getNocNo());
								newSsr.setNocDate(ssr.getNocDate());
								newSsr.setContainerNo("");

								ssrdtlrepo.save(newSsr);
							}

						} else {
							String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05103", "2024");

							String[] parts = lastValue.split("/");
							String baseId = parts[0];
							String baseId1 = parts[1];
							String financialYear = parts[2];

							// Increment the numerical part
							int newVal = Integer.parseInt(baseId1) + 1;

							// Format newVal to maintain leading zeros (e.g., 0001)
							String formattedNewVal = String.format("%04d", newVal);

							// Get the current financial year
							String currentFinancialYear = getCurrentFinancialYear();

							// Construct the new ID
							String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;

							SSRDtl newSsr = new SSRDtl();

							newSsr.setApprovedBy(user);
							newSsr.setApprovedDate(new Date());
							newSsr.setBeDate(ssr.getBeDate());
							newSsr.setBeNo(ssr.getBeNo());
							newSsr.setBlDate(null);
							newSsr.setBlNo("");
							newSsr.setBranchId(bid);
							newSsr.setCargoWt(BigDecimal.ZERO);
							newSsr.setCha(String.valueOf(noc[7]));
							newSsr.setCommodityDescription(ssr.getCommodityDescription());
							newSsr.setCompanyId(cid);
							newSsr.setCreatedBy(user);
							newSsr.setCreatedDate(new Date());
							newSsr.setDocRefDate(ssr.getDocRefDate());
							newSsr.setDocRefNo(ssr.getDocRefNo());
							newSsr.setErpDocRefNo(ssr.getErpDocRefNo());
							newSsr.setExecutionUnit(s.getExecutionUnit());
							newSsr.setSsrModeFor(ssr.getSsrModeFor());
							newSsr.setSsrType("EXBOND");
							newSsr.setIgmLineNo(ssr.getIgmLineNo());
							newSsr.setNoOfPackages(0);
							newSsr.setProfitcentreId(ssr.getProfitcentreId());
							newSsr.setRate(s.getRate());
							newSsr.setServiceId(s.getServiceId());
							newSsr.setServiceUnit(s.getServiceUnit());
							newSsr.setServiceUnit1(ser.getServiceUnit1());
							newSsr.setSrNo(new BigDecimal(0));
							newSsr.setSsrModeFor(ssr.getSsrModeFor());
							newSsr.setSsrRefNo(newId);
							newSsr.setStatus('A');
							newSsr.setTotalRate(s.getTotalRate());
							newSsr.setTransDate(new Date());
							newSsr.setTransId(ssr.getTransId());
							newSsr.setTransLineNo(new BigDecimal(1));
							newSsr.setAccId(String.valueOf(noc[9]));
							newSsr.setImpId(ssr.getImpId());
							newSsr.setExBondId(ssr.getExBondId());
							newSsr.setExBondDate(ssr.getExBondDate());
							newSsr.setExBondBeDate(ssr.getExBondBeDate());
							newSsr.setExBondBeId(ssr.getExBondBeId());
							newSsr.setNocNo(ssr.getNocNo());
							newSsr.setNocDate(ssr.getNocDate());
							newSsr.setContainerNo("");

							ssrdtlrepo.save(newSsr);

							processnextidrepo.updateAuditTrail(cid, bid, "P05103", newId, "2024");

						}

					}

				});
			}

			Map<String, Object> result = new HashMap<>();

			List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, ssr.getTransId());

			List<Object[]> ssrData = ssrdtlrepo.getSingleData2(cid, bid, ssr.getTransId());

			result.put("refNoData", ssrRefNoData);
			result.put("ssr", ssrData.get(0));

			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@GetMapping("/getDataForExbondSSR")
	public ResponseEntity<?> getDataForExbondSSR(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("boe") String boe) {

		Object data = cfbondnocrepo.getDataForExbondSSR(cid, bid, boe);

		if (data == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
	@GetMapping("/searchBondSSR")
	public ResponseEntity<?> searchBondSSR(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = ssrdtlrepo.searchBondSSR(cid, bid, val, "N00003");

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
	@GetMapping("/getBondDataByTransId")
	public ResponseEntity<?> getBondDataByTransId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("transid") String transid, @RequestParam("type") String type) {
		
		if("NOC".equals(type)) {
			Map<String, Object> result = new HashMap<>();

			List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, transid);

			List<Object[]> ssrData = ssrdtlrepo.getSingleData1(cid, bid, transid);

			result.put("refNoData", ssrRefNoData);
			result.put("ssr", ssrData.get(0));

			return new ResponseEntity<>(result, HttpStatus.OK);
		}

		else {
			Map<String, Object> result = new HashMap<>();

			List<SSRDtl> ssrRefNoData = ssrdtlrepo.getRefNoData(cid, bid, transid);

			List<Object[]> ssrData = ssrdtlrepo.getSingleData2(cid, bid, transid);

			result.put("refNoData", ssrRefNoData);
			result.put("ssr", ssrData.get(0));

			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}



