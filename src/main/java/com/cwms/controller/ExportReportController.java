package com.cwms.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.entities.Branch;
import com.cwms.entities.Company;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.ExportAuditRepo;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.ExportGatePassRepo;
import com.cwms.repository.ExportStuffRequestRepo;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.GateInRepository;
import com.cwms.service.ExportReportService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/exportReport")
@CrossOrigin("*")
public class ExportReportController {
	
	@Autowired
	private ExportCartingRepo exportCartingRepo;
	
	@Autowired
	private GateInRepository gateInRepo;
	
	@Autowired
	private ExportStuffRequestRepo exportStuffReqRepo;
	
	@Autowired
	private ExportGatePassRepo exportGatePassRepo;
	
	@Autowired
	private ExportStuffTallyRepo exportStuffTallyRepo;
	
	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private TemplateEngine templateEngine;
	
	
	
	@Autowired
	private ExportReportService exportReportService;

	
	
	@Autowired
	private ExportAuditRepo auditRepo;

	@GetMapping("/searchValuesInputSearch")
	public ResponseEntity<?> searchValuesInputSearch(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("searchValue") String searchValue,
			@RequestParam(name = "type", required = false) String type) {
		List<Map<String, Object>> toSendGetParties = new ArrayList<>();

		if ("containerNo".equals(type)) {
			List<Object[]> getContainerNoList = auditRepo.getContainerNoList(companyId, branchId, searchValue);
			toSendGetParties = getContainerNoList.stream().map(row -> {
				Map<String, Object> map = new HashMap<>();
				map.put("value", row[1]);
				map.put("label", row[0]);
				return map;
			}).collect(Collectors.toList());

		} else if ("gatePassNo".equals(type)) {
			List<Object[]> getGatePassNoList = auditRepo.getGatePassNoList(companyId, branchId, searchValue);
			toSendGetParties = getGatePassNoList.stream().map(row -> {
				Map<String, Object> map = new HashMap<>();
				map.put("value", row[0]);
				map.put("label", row[0]);
				return map;
			}).collect(Collectors.toList());

		} else if ("containerNo2".equals(type)) {

		}
		return ResponseEntity.ok(toSendGetParties);
	}

// Export AuditTrail...

	@PostMapping("/saveExporAudit")
	public ResponseEntity<?> saveExporAudit(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam(value = "sbNo", required = false) String sbNo,
			@RequestParam(value = "containerNo", required = false) String containerNo,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user,
			@RequestParam("type") String type) {

		ResponseEntity<?> addExportSbEntry = exportReportService.saveExporAudit(companyId, branchId, requestData, user,
				type, sbNo, containerNo);

		return addExportSbEntry;
	}

	@GetMapping(value = "/exportAuditTrailSearh")
	public ResponseEntity<?> exportAuditTrailSearh(@RequestParam("companyId") String companyid,
			@RequestParam("branchId") String branchId, @RequestParam(value = "sbNo", required = false) String sbNo,
			@RequestParam(value = "containerNo", required = false) String containerNo,
			@RequestParam("profitCenterId") String profitCenterId) {
		System.out.println(companyid + " " + branchId + " sbNo " + sbNo + " " + " profitCenterId " + profitCenterId
				+ " containerNo " + containerNo);

		try {

			ResponseEntity<?> exportAuditTrailSearh = exportReportService.exportAuditTrailSearh(companyid, branchId,
					profitCenterId, sbNo, containerNo);

			return exportAuditTrailSearh;

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while export auditTrail Search ");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	GATE IN PASS - EXPORT CARGO CARTING
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@GetMapping(value = "/downLoadExportGateInReport")
	public ResponseEntity<?> getExportTruckWiseGateInReport(
	        @RequestParam("companyId") String companyid,
	        @RequestParam("branchId") String branchId,
	        @RequestParam("gateInId") String gateInId,
	        @RequestParam("profitCenterId") String profitCenterId,
	        @RequestParam("type") String type,
	        @RequestParam("userId") String userId) {
	    
		System.out.println(companyid + " "  + branchId + " " + gateInId + " " + " profitCenterId " + " "+ type + " " + userId);
		
	    byte[] gateInReport = null; 

	    try {
	        // Check if the type is "GateIn" and fetch the report
	        if ("GateIn".equals(type)) {
	            gateInReport = exportReportService.getExportTruckWiseGateInReport(companyid, branchId, profitCenterId, gateInId, userId);
	        } else {
	            gateInReport = exportReportService.getExportTruckWiseJobOrderReport(companyid, branchId, profitCenterId, gateInId, userId);
	        }

	        // If no report is generated or returned, return a bad request with a message
	        if (gateInReport == null || gateInReport.length == 0) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT)
	                    .body("No report available for the provided parameters.");
	        }

	        // Prepare the headers for the response
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=getExportTruckWiseGateInReport.pdf");

	        // Return the report as a response
	        return ResponseEntity.ok().headers(headers).body(gateInReport);

	    } catch (Exception e) {
	        // Log the exception (ensure to use a logger in real code)
	        e.printStackTrace();  // Replace with proper logging (e.g., log.error(e.getMessage(), e))

	        // Return an internal server error response with an error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while generating the report: ");
	    }
	}

	
	

	@GetMapping(value = "/downLoadExportBackToTownReport")
	public ResponseEntity<?> getExportBackToTownReport(
	        @RequestParam("companyId") String companyid,
	        @RequestParam("branchId") String branchId,
	        @RequestParam("backToTownTransId") String backToTownTransId,
	        @RequestParam("profitCenterId") String profitCenterId,
	        @RequestParam("userId") String userId) {
	    
		System.out.println(companyid + " "  + branchId + " " + backToTownTransId + " " +  profitCenterId   + " " + userId);
		   try {  
	    byte[] gateInReport  = exportReportService.getExportBackToTownReport(companyid, branchId, profitCenterId, backToTownTransId, userId);
	        
	        // If no report is generated or returned, return a bad request with a message
	        if (gateInReport == null || gateInReport.length == 0) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT)
	                    .body("No report available for the provided parameters.");
	        }

	        // Prepare the headers for the response
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=downLoadExportBackToTownReport.pdf");

	        // Return the report as a response
	        return ResponseEntity.ok().headers(headers).body(gateInReport);

	    } catch (Exception e) {
	        // Log the exception (ensure to use a logger in real code)
	        e.printStackTrace();  // Replace with proper logging (e.g., log.error(e.getMessage(), e))

	        // Return an internal server error response with an error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while generating the report: ");
	    }
	}
	
	

	@GetMapping(value = "/downLoadExportPortReturnReport")
	public ResponseEntity<?> getExportPortReturnReport(
	        @RequestParam("companyId") String companyid,
	        @RequestParam("branchId") String branchId,
	        @RequestParam("gateInId") String gateInId,
	        @RequestParam("profitCenterId") String profitCenterId,
	        @RequestParam("userId") String userId) {
	    
		System.out.println(companyid + " "  + branchId + " " + gateInId + " " +  profitCenterId   + " " + userId);
		   try {  
	    byte[] gateInReport  = exportReportService.downLoadExportPortReturnReport(companyid, branchId, profitCenterId, gateInId, userId);
	        
	        // If no report is generated or returned, return a bad request with a message
	        if (gateInReport == null || gateInReport.length == 0) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT)
	                    .body("No report available for the provided parameters.");
	        }

	        // Prepare the headers for the response
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=downLoadExportBackToTownReport.pdf");

	        // Return the report as a response
	        return ResponseEntity.ok().headers(headers).body(gateInReport);

	    } catch (Exception e) {
	        // Log the exception (ensure to use a logger in real code)
	        e.printStackTrace();  // Replace with proper logging (e.g., log.error(e.getMessage(), e))

	        // Return an internal server error response with an error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while generating the report: ");
	    }
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/exportCartingReport")
	public ResponseEntity<?> exportCartingReport(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id) throws DocumentException{
		
		List<Object[]> data = exportCartingRepo.getDataForCartingreport(cid, bid, id);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Carting data not found",HttpStatus.CONFLICT);
		}
		
		Object[] singleData = data.get(0);
		
		Context context = new Context();

		Company comp = companyRepo.findByCompany_Id(cid);

		if (comp == null) {
			return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
		}

		Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

		if (branchAddress == null) {
			return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
		}

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3();


		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("cartingId", singleData[0] != null ? String.valueOf(singleData[0]) : "");
		context.setVariable("cartingDate", singleData[1] != null ? String.valueOf(singleData[1]) : "");
		context.setVariable("cartingData", data);

		String htmlContent = templateEngine.process("CFSExportCartingReport", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		
	}
	
	
	
	@PostMapping("/exportEmptyContReport")
	public ResponseEntity<?> exportEmptyContReport(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id) throws DocumentException{
		
		Object data = gateInRepo.getExportMtyContainerGateInReport(cid, bid, id);
		
		if(data == null) {
			return new ResponseEntity<>("Container data not found",HttpStatus.CONFLICT);
		}
		
		Object[] singleData = (Object[]) data;
		
		Context context = new Context();

		Company comp = companyRepo.findByCompany_Id(cid);

		if (comp == null) {
			return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
		}

		Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

		if (branchAddress == null) {
			return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
		}

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3();

		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("gateInId", singleData[0] != null ? String.valueOf(singleData[0]) : "");
		context.setVariable("gateInDate", singleData[1] != null ? String.valueOf(singleData[1]) : "");
		context.setVariable("containerNo", singleData[2] != null ? String.valueOf(singleData[2]) : "");
		context.setVariable("containerSize", singleData[3] != null ? String.valueOf(singleData[3]) : "");
		context.setVariable("containerType", singleData[4] != null ? String.valueOf(singleData[4]) : "");
		context.setVariable("vehicleNo", singleData[5] != null ? String.valueOf(singleData[5]) : "");
		context.setVariable("transporter", singleData[6] != null ? String.valueOf(singleData[6]) : "");
		context.setVariable("sa", singleData[7] != null ? String.valueOf(singleData[7]) : "");
		context.setVariable("sl", singleData[8] != null ? String.valueOf(singleData[8]) : "");
		context.setVariable("onAccountOf", singleData[9] != null ? String.valueOf(singleData[9]) : "");
		context.setVariable("condition", singleData[10] != null ? String.valueOf(singleData[10]) : "");
		context.setVariable("remark", singleData[11] != null ? String.valueOf(singleData[11]) : "");
		context.setVariable("createdBy", singleData[12] != null ? String.valueOf(singleData[12]) : "");
		context.setVariable("origin", singleData[13] != null ? String.valueOf(singleData[13]) : "");
		context.setVariable("doNo", singleData[14] != null ? String.valueOf(singleData[14]) : "");
		context.setVariable("dovalidityDate", singleData[15] != null ? String.valueOf(singleData[15]) : "");

		String htmlContent = templateEngine.process("CFSExportMtyContReport", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		
	}
	
	
	
	@PostMapping("/exportContWiseStuffreqReport")
	public ResponseEntity<?> exportContWiseStuffreqReport(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id,@RequestParam("con") String con) throws DocumentException{
		
		List<Object[]> data = exportStuffReqRepo.getContWiseReportDate(cid, bid, id, con);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Stuff Req data not found",HttpStatus.CONFLICT);
		}
		
		BigDecimal totalPkgs = data.stream()
		        .map(record -> record[13]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values

		BigDecimal totalWt = data.stream()
		        .map(record -> record[14]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values
		
		Object[] singleData = data.get(0);
		
		Context context = new Context();

		Company comp = companyRepo.findByCompany_Id(cid);

		if (comp == null) {
			return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
		}

		Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

		if (branchAddress == null) {
			return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
		}

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3();

		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("stuffReqId", singleData[0] != null ? String.valueOf(singleData[0]) : "");
		context.setVariable("stuffReqDate", singleData[1] != null ? String.valueOf(singleData[1]) : "");
		context.setVariable("sa", singleData[2] != null ? String.valueOf(singleData[2]) : "");
		context.setVariable("sl", singleData[3] != null ? String.valueOf(singleData[3]) : "");
		context.setVariable("vessel", singleData[4] != null ? String.valueOf(singleData[4]) : "");
		context.setVariable("containerNo", singleData[5] != null ? String.valueOf(singleData[5]) : "");
		context.setVariable("containerSize", singleData[6] != null ? String.valueOf(singleData[6]) : "");
		context.setVariable("ContainerType", singleData[7] != null ? String.valueOf(singleData[7]) : "");
		context.setVariable("totalPkg", totalPkgs != null ? String.valueOf(totalPkgs) : "");
		context.setVariable("totalWt", totalWt != null ? String.valueOf(totalWt) : "");
		context.setVariable("stuffData", data);

		String htmlContent = templateEngine.process("CFSExportConStuffReq", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		
	}
	
	
	@PostMapping("/exportSBWiseStuffreqReport")
	public ResponseEntity<?> exportSBWiseStuffreqReport(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id,@RequestParam("sb") String sb) throws DocumentException{
		
		List<Object[]> data = exportStuffReqRepo.getSBWiseReportDate(cid, bid,  sb);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Stuff Req data not found",HttpStatus.CONFLICT);
		}
		
		BigDecimal totalPkgs = data.stream()
		        .map(record -> record[13]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values

		BigDecimal totalWt = data.stream()
		        .map(record -> record[14]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values
		
		Object[] singleData = data.get(0);
		
		Context context = new Context();

		Company comp = companyRepo.findByCompany_Id(cid);

		if (comp == null) {
			return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
		}

		Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

		if (branchAddress == null) {
			return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
		}

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3();

		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("stuffReqId", singleData[0] != null ? String.valueOf(singleData[0]) : "");
		context.setVariable("stuffReqDate", singleData[1] != null ? String.valueOf(singleData[1]) : "");
		context.setVariable("sa", singleData[2] != null ? String.valueOf(singleData[2]) : "");
		context.setVariable("sl", singleData[3] != null ? String.valueOf(singleData[3]) : "");
		context.setVariable("sbNo", singleData[8] != null ? String.valueOf(singleData[8]) : "");
		context.setVariable("sbDate", singleData[9] != null ? String.valueOf(singleData[9]) : "");
		context.setVariable("exporter", singleData[10] != null ? String.valueOf(singleData[10]) : "");
		context.setVariable("cargo", singleData[11] != null ? String.valueOf(singleData[11]) : "");
		context.setVariable("type", singleData[12] != null ? String.valueOf(singleData[12]) : "");
		context.setVariable("vessel", singleData[4] != null ? String.valueOf(singleData[4]) : "");
		context.setVariable("totalPkg", totalPkgs != null ? String.valueOf(totalPkgs) : "");
		context.setVariable("totalWt", totalWt != null ? String.valueOf(totalWt) : "");
		context.setVariable("stuffData", data);

		String htmlContent = templateEngine.process("CFSExportSbStuffReq", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		
	}
	
	
	@PostMapping("/exportContWiseStuffTallyReport")
	public ResponseEntity<?> exportContWiseStuffTallyReport(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id,@RequestParam("con") String con) throws DocumentException{
		
		List<Object[]> data = exportStuffTallyRepo.getDataForContWiseReport(cid, bid, id, con);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Stuff tally data not found",HttpStatus.CONFLICT);
		}
		
		BigDecimal totalPkgs = data.stream()
		        .map(record -> record[28]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values

		BigDecimal totalWt = data.stream()
		        .map(record -> record[29]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values
		
		
		BigDecimal totalFob = data.stream()
		        .map(record -> record[30]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values
		
		Object[] singleData = data.get(0);

		
		BigDecimal tareWt = new BigDecimal(String.valueOf(singleData[31]));
		
		BigDecimal totalGwt = tareWt.add(totalWt);
		
		Context context = new Context();

		Company comp = companyRepo.findByCompany_Id(cid);

		if (comp == null) {
			return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
		}

		Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

		if (branchAddress == null) {
			return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
		}

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3();

		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("stuffReqId", singleData[0] != null ? String.valueOf(singleData[0]) : "");
		context.setVariable("tallyDate", singleData[1] != null ? String.valueOf(singleData[1]) : "");
		context.setVariable("containerNo", singleData[2] != null ? String.valueOf(singleData[2]) : "");
		context.setVariable("containerSize", singleData[3] != null ? String.valueOf(singleData[3]) : "");
		context.setVariable("containerType", singleData[4] != null ? String.valueOf(singleData[4]) : "");
		context.setVariable("terminal", singleData[5] != null ? String.valueOf(singleData[5]) : "");
		context.setVariable("pod", singleData[6] != null ? String.valueOf(singleData[6]) : "");
		context.setVariable("finalPod", singleData[7] != null ? String.valueOf(singleData[7]) : "");
		context.setVariable("viaNo", singleData[8] != null ? String.valueOf(singleData[8]) : "");
		context.setVariable("voyageNo", singleData[9] != null ? String.valueOf(singleData[9]) : "");
		context.setVariable("vessel", singleData[10] != null ? String.valueOf(singleData[10]) : "");
		context.setVariable("rotationNo", singleData[11] != null ? String.valueOf(singleData[11]) : "");
		context.setVariable("rotationDate", singleData[12] != null ? String.valueOf(singleData[12]) : "");
		context.setVariable("cha", singleData[13] != null ? String.valueOf(singleData[13]) : "");
		context.setVariable("sa", singleData[14] != null ? String.valueOf(singleData[14]) : "");
		context.setVariable("sl", singleData[15] != null ? String.valueOf(singleData[15]) : "");
		context.setVariable("agentSeal", singleData[16] != null ? String.valueOf(singleData[16]) : "");
		context.setVariable("customSeal", singleData[17] != null ? String.valueOf(singleData[17]) : "");
		context.setVariable("workOrder", singleData[18] != null ? String.valueOf(singleData[18]) : "");
		context.setVariable("workOrderDate", singleData[19] != null ? String.valueOf(singleData[19]) : "");
		context.setVariable("totalStuffPkg", totalPkgs != null ? String.valueOf(totalPkgs) : "");
		context.setVariable("totalStuffWt", totalWt != null ? String.valueOf(totalWt) : "");
		context.setVariable("totalFob", totalFob != null ? String.valueOf(totalFob) : "");
		context.setVariable("tareWt", tareWt != null ? String.valueOf(tareWt) : "");
		context.setVariable("totalGrossWt", totalGwt != null ? String.valueOf(totalGwt) : "");
		context.setVariable("stuffData", data);

		String htmlContent = templateEngine.process("CFSExportConStuffTally", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		
	}
	
	
	@PostMapping("/exportSBWiseStuffTallyReport")
	public ResponseEntity<?> exportSBWiseStuffTallyReport(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id,@RequestParam("sb") String sb) throws DocumentException{
		
		List<Object[]> data = exportStuffTallyRepo.getDataForSBWiseReport(cid, bid, id, sb);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Stuff tally data not found",HttpStatus.CONFLICT);
		}
		

		Map<String, Integer> countOdConSize = new HashMap<>();
		Set<String> uniqueContainers = new HashSet<>();

		data.stream().forEach(c -> {
		    String key = String.valueOf(c[24]);
		    String uniqueIdentifier = String.valueOf(c[23]); // Combine c[10] and c[11] to ensure uniqueness

		    if (!uniqueContainers.contains(uniqueIdentifier)) {
		        uniqueContainers.add(uniqueIdentifier); // Track unique c[10] for the given c[11]
		        countOdConSize.put(key, countOdConSize.getOrDefault(key, 0) + 1);
		    }
		});
		
		
		Object[] singleData = data.get(0);

		
		Context context = new Context();

		Company comp = companyRepo.findByCompany_Id(cid);

		if (comp == null) {
			return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
		}

		Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

		if (branchAddress == null) {
			return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
		}

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3();

		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("sbNo", singleData[0] != null ? String.valueOf(singleData[0]) : "");
		context.setVariable("viaNo", singleData[1] != null ? String.valueOf(singleData[1]) : "");
		context.setVariable("sbDate", singleData[2] != null ? String.valueOf(singleData[2]) : "");
		context.setVariable("onAccounOf", singleData[3] != null ? String.valueOf(singleData[3]) : "");
		context.setVariable("vessel", singleData[4] != null ? String.valueOf(singleData[4]) : "");
		context.setVariable("nop", singleData[5] != null ? String.valueOf(singleData[5]) : "");
		context.setVariable("sl", singleData[6] != null ? String.valueOf(singleData[6]) : "");
		context.setVariable("voyageNo", singleData[7] != null ? String.valueOf(singleData[7]) : "");
		context.setVariable("wt", singleData[8] != null ? String.valueOf(singleData[8]) : "");
		context.setVariable("consignee", singleData[9] != null ? String.valueOf(singleData[9]) : "");
		context.setVariable("pod", singleData[10] != null ? String.valueOf(singleData[10]) : "");
		context.setVariable("cargoType", singleData[11] != null ? String.valueOf(singleData[11]) : "");
		context.setVariable("cha", singleData[12] != null ? String.valueOf(singleData[12]) : "");
		context.setVariable("terminal", singleData[13] != null ? String.valueOf(singleData[13]) : "");
		context.setVariable("commodity", singleData[14] != null ? String.valueOf(singleData[14]) : "");
		context.setVariable("stuffTallyDate", singleData[15] != null ? String.valueOf(singleData[15]) : "");
		context.setVariable("rotationNo", singleData[16] != null ? String.valueOf(singleData[16]) : "");
		context.setVariable("top", singleData[17] != null ? String.valueOf(singleData[17]) : "");
		context.setVariable("yardName", singleData[18] != null ? String.valueOf(singleData[18])+"-"+String.valueOf(singleData[19])+"-"+String.valueOf(singleData[20]) : "");
		context.setVariable("workOrderNo", singleData[21] != null ? String.valueOf(singleData[21]) : "");
		context.setVariable("workOrderDate", singleData[22] != null ? String.valueOf(singleData[22]) : "");
		context.setVariable("exporter", singleData[31] != null ? String.valueOf(singleData[31]) : "");
		context.setVariable("countOdConSize", countOdConSize != null ? countOdConSize : "");
		context.setVariable("hsnCode", singleData[33] != null ? String.valueOf(singleData[33]) : "");
		context.setVariable("pickUpYard", singleData[34] != null ? String.valueOf(singleData[34]) : "");

		context.setVariable("stuffData", data);

		String htmlContent = templateEngine.process("CFSExportSBStuffTally", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		
	}
	
	
	@PostMapping("/exportContGatePassReport")
	public ResponseEntity<?> exportContGatePassReport(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id,@RequestParam("type") String type) throws DocumentException{
		
		if("CRG".equals(type)) {
			List<Object[]> data = exportGatePassRepo.getCRGGatepassDataForReport(cid, bid, id);
			
			if(data.isEmpty()) {
				return new ResponseEntity<>("Gate pass data not found",HttpStatus.CONFLICT);
			}
			
			

			Context context = new Context();

			Company comp = companyRepo.findByCompany_Id(cid);

			if (comp == null) {
				return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
			}

			Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

			if (branchAddress == null) {
				return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
			}

    		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
    				+ branchAddress.getAddress3();

 
    		context.setVariable("companyname", comp.getCompany_name());
    		context.setVariable("branchName", branchAddress.getBranchName());
			context.setVariable("address", branchAdd);
			context.setVariable("gatePassData", data);

			String htmlContent = templateEngine.process("CFSExportCRGGatePass", context);

			ITextRenderer renderer = new ITextRenderer();

			renderer.setDocumentFromString(htmlContent);
			renderer.layout();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			renderer.createPDF(outputStream);

			byte[] pdfBytes = outputStream.toByteArray();

			String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		}
		else {
			List<Object[]> data = exportGatePassRepo.getGatepassDataForReport(cid, bid, id);
			
			if(data.isEmpty()) {
				return new ResponseEntity<>("Gate pass data not found",HttpStatus.CONFLICT);
			}
			
			

			Context context = new Context();

			Company comp = companyRepo.findByCompany_Id(cid);

			if (comp == null) {
				return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
			}

			Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

			if (branchAddress == null) {
				return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
			}

    		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
    				+ branchAddress.getAddress3();

 
    		context.setVariable("companyname", comp.getCompany_name());
    		context.setVariable("branchName", branchAddress.getBranchName());
			context.setVariable("address", branchAdd);
			context.setVariable("gatePassData", data);

			String htmlContent = templateEngine.process("CFSExportContGatePass", context);

			ITextRenderer renderer = new ITextRenderer();

			renderer.setDocumentFromString(htmlContent);
			renderer.layout();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			renderer.createPDF(outputStream);

			byte[] pdfBytes = outputStream.toByteArray();

			String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		}
		
	}
	
	
	

	@PostMapping("/exportBufferGateInReport")
	public ResponseEntity<?> exportBufferGateInReport(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id) throws DocumentException{
		
			Object data = gateInRepo.getBufferContainerGateInReport(cid, bid, id);
			
			if(data == null) {
				return new ResponseEntity<>("Buffer container data not found",HttpStatus.CONFLICT);
			}
			
			Object[] gateInData = (Object[]) data;
			


			Context context = new Context();

			Company comp = companyRepo.findByCompany_Id(cid);

			if (comp == null) {
				return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
			}

			Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

			if (branchAddress == null) {
				return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
			}

			String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
    				+ branchAddress.getAddress3();

 
    		context.setVariable("companyname", comp.getCompany_name());
    		context.setVariable("branchName", branchAddress.getBranchName());
			context.setVariable("address", branchAdd);
			context.setVariable("gateInId", gateInData[0] != null ? String.valueOf(gateInData[0]) : "");
			context.setVariable("gateInDate", gateInData[1] != null ? String.valueOf(gateInData[1]) : "");
			context.setVariable("containerNo", gateInData[2] != null ? String.valueOf(gateInData[2]) : "");
			context.setVariable("containerSize", gateInData[3] != null ? String.valueOf(gateInData[3]) : "");
			context.setVariable("containerType", gateInData[4] != null ? String.valueOf(gateInData[4]) : "");
			context.setVariable("containerSealNo", gateInData[5] != null ? String.valueOf(gateInData[5]) : "");
			context.setVariable("vehicleNo", gateInData[6] != null ? String.valueOf(gateInData[6]) : "");
			context.setVariable("transporter", gateInData[7] != null ? String.valueOf(gateInData[7]) : "");
			context.setVariable("gateInType", gateInData[8] != null ? String.valueOf(gateInData[8]) : "");
			context.setVariable("shipper", gateInData[9] != null ? String.valueOf(gateInData[9]) : "");
			context.setVariable("cha", gateInData[10] != null ? String.valueOf(gateInData[10]) : "");
			context.setVariable("onAccountOf", gateInData[11] != null ? String.valueOf(gateInData[11]) : "");
			context.setVariable("sl", gateInData[12] != null ? String.valueOf(gateInData[12]) : "");
			context.setVariable("condition", gateInData[13] != null ? String.valueOf(gateInData[13]) : "");
			context.setVariable("commodity", gateInData[14] != null ? String.valueOf(gateInData[14]) : "");
			context.setVariable("remark", gateInData[15] != null ? String.valueOf(gateInData[15]) : "");
			
			String htmlContent = templateEngine.process("CFSExportBufferGateIn", context);

			ITextRenderer renderer = new ITextRenderer();

			renderer.setDocumentFromString(htmlContent);
			renderer.layout();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			renderer.createPDF(outputStream);

			byte[] pdfBytes = outputStream.toByteArray();

			String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		
		
	}
	
	
	
	@PostMapping("/exportContWiseBufferTallyReport")
	public ResponseEntity<?> exportContWiseBufferTallyReport(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id,@RequestParam("con") String con) throws DocumentException{
		
		List<Object[]> data = exportStuffTallyRepo.getDataForBufferContWiseReport(cid, bid, id, con);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Stuff tally data not found",HttpStatus.CONFLICT);
		}
		
		BigDecimal totalPkgs = data.stream()
		        .map(record -> record[25]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values

		BigDecimal totalWt = data.stream()
		        .map(record -> record[26]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values
		
		
		BigDecimal totalFob = data.stream()
		        .map(record -> record[27]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values
		
		Object[] singleData = data.get(0);

		
		BigDecimal tareWt = new BigDecimal(String.valueOf(singleData[28]));
		
		BigDecimal totalGwt = tareWt.add(totalWt);
		
		Context context = new Context();

		Company comp = companyRepo.findByCompany_Id(cid);

		if (comp == null) {
			return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
		}

		Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

		if (branchAddress == null) {
			return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
		}

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3();


		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("stuffReqId", singleData[0] != null ? String.valueOf(singleData[0]) : "");
		context.setVariable("tallyDate", singleData[1] != null ? String.valueOf(singleData[1]) : "");
		context.setVariable("containerNo", singleData[2] != null ? String.valueOf(singleData[2]) : "");
		context.setVariable("containerSize", singleData[3] != null ? String.valueOf(singleData[3]) : "");
		context.setVariable("containerType", singleData[4] != null ? String.valueOf(singleData[4]) : "");
		context.setVariable("pol", singleData[5] != null ? String.valueOf(singleData[5]) : "");
		context.setVariable("pod", singleData[6] != null ? String.valueOf(singleData[6]) : "");
		context.setVariable("finalPod", singleData[7] != null ? String.valueOf(singleData[7]) : "");
		context.setVariable("viaNo", singleData[8] != null ? String.valueOf(singleData[8]) : "");
		context.setVariable("voyageNo", singleData[9] != null ? String.valueOf(singleData[9]) : "");
		context.setVariable("vessel", singleData[10] != null ? String.valueOf(singleData[10]) : "");
		context.setVariable("rotationNo", singleData[11] != null ? String.valueOf(singleData[11]) : "");
		context.setVariable("rotationDate", singleData[12] != null ? String.valueOf(singleData[12]) : "");
		context.setVariable("cha", singleData[13] != null ? String.valueOf(singleData[13]) : "");
//		context.setVariable("sa", singleData[14] != null ? String.valueOf(singleData[14]) : "");
		context.setVariable("sl", singleData[14] != null ? String.valueOf(singleData[14]) : "");
		context.setVariable("agentSeal", singleData[15] != null ? String.valueOf(singleData[15]) : "");
		context.setVariable("customSeal", singleData[16] != null ? String.valueOf(singleData[16]) : "");
//		context.setVariable("workOrder", singleData[18] != null ? String.valueOf(singleData[18]) : "");
//		context.setVariable("workOrderDate", singleData[19] != null ? String.valueOf(singleData[19]) : "");
		context.setVariable("onAccountOf", singleData[17] != null ? String.valueOf(singleData[17]) : "");
		context.setVariable("totalStuffPkg", totalPkgs != null ? String.valueOf(totalPkgs) : "");
		context.setVariable("totalStuffWt", totalWt != null ? String.valueOf(totalWt) : "");
		context.setVariable("totalFob", totalFob != null ? String.valueOf(totalFob) : "");
		context.setVariable("tareWt", tareWt != null ? String.valueOf(tareWt) : "");
		context.setVariable("totalGrossWt", totalGwt != null ? String.valueOf(totalGwt) : "");
		context.setVariable("stuffData", data);

		String htmlContent = templateEngine.process("CFSExportBufferConStuffTally", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
		
	}
}