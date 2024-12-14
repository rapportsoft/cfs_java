package com.cwms.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.entities.Branch;
import com.cwms.entities.Company;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.DestuffCrgRepository;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.ImportGateOutRepository;
import com.cwms.repository.ImportGatePassRepo;
import com.cwms.repository.ImportInventoryRepository;
import com.cwms.repository.ManualContainerGateInRepo;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@CrossOrigin("*")
@RestController
@RequestMapping("/importReports")
public class ImportReportsController {

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	private GateInRepository gateinrepo;

	@Autowired
	private ImportInventoryRepository importinventoryrepo;

	@Autowired
	private ManualContainerGateInRepo manualcontainergateinrepo;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	@Autowired
	private DestuffCrgRepository destuffCrgRepo;
	
	@Autowired
	private ImportGatePassRepo importGatePassRepo;

	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private ImportGateOutRepository gateOutRepo;
	
	


	@PostMapping("/importGateInReport")
	public ResponseEntity<?> importGateInReport(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("gateInId") String gateInId, @RequestParam("erp") String erp, @RequestParam("doc") String doc,
			@RequestParam("companyname") String companyname) throws DocumentException {

		Object gate = gateinrepo.getExistingDataForReports(cid, bid, erp, doc, gateInId);

		if (gate == null) {
			return new ResponseEntity<>("Gate in data not found!!", HttpStatus.CONFLICT);
		}

		// Cast the Object to Object[] to access individual column values
		Object[] gateData = (Object[]) gate;

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
		context.setVariable("city", branchAddress.getCity());
		context.setVariable("state", branchAddress.getState());
		context.setVariable("pin", branchAddress.getPin());
		context.setVariable("country", branchAddress.getCountry());
		context.setVariable("gateId", String.valueOf(gateData[0]));
		context.setVariable("gateInDate", String.valueOf(gateData[1]));
		context.setVariable("portName", String.valueOf(gateData[2]));
		context.setVariable("portExitDate", String.valueOf(gateData[3]));
		context.setVariable("containerNo", String.valueOf(gateData[4]));
		context.setVariable("containerSize", String.valueOf(gateData[5]));
		context.setVariable("containerType", String.valueOf(gateData[6]));
		context.setVariable("iso", String.valueOf(gateData[7]));
		context.setVariable("vehicleNo", String.valueOf(gateData[8]));
		context.setVariable("transporterName", String.valueOf(gateData[9]));
		context.setVariable("vesselName", String.valueOf(gateData[10]));
		context.setVariable("viaNo", String.valueOf(gateData[11]));
		context.setVariable("blNo", String.valueOf(gateData[12]));
		context.setVariable("containerSealNo", String.valueOf(gateData[13]));
		context.setVariable("blDate", String.valueOf(gateData[14]));
		context.setVariable("actualSealNo", String.valueOf(gateData[15]));
		context.setVariable("igmNo", String.valueOf(gateData[16]));
		context.setVariable("lineNo", String.valueOf(gateData[17]));
		context.setVariable("scanningDoneStatus", String.valueOf(gateData[18]));
		context.setVariable("sl", String.valueOf(gateData[19]));
		context.setVariable("impName", String.valueOf(gateData[20]));
		context.setVariable("remark", String.valueOf(gateData[21]));
		context.setVariable("conHealth", String.valueOf(gateData[22]));
	

		String htmlContent = templateEngine.process("CFSImportGateInReport", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);

	}

	@PostMapping("/importSealCuttingItemwiseReport")
	public ResponseEntity<?> importSealCuttingItemwiseReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm, @RequestParam("item") String item,
			@RequestParam("jobOrder") String jobOrder)
			throws DocumentException {

		Object cargo = cfigmcrgrepo.getDataForSealCuttingItemwiseReport(cid, bid, igm, item);

		if (cargo == null) {
			return new ResponseEntity<>("Cargo data not found", HttpStatus.CONFLICT);
		}
		
		Object[] crgData = (Object[]) cargo;

		List<Object[]> conData = cfigmcnrepo.getDataForSealCuttingItemWiseReport(cid, bid, igm, item, jobOrder);

		if (conData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
		}

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
		
		


		
		BigDecimal totalPkgs = conData.stream()
		        .map(record -> record[8]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values

		BigDecimal totalWt = conData.stream()
		        .map(record -> record[9]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values

		// Set variables in the Thymeleaf context
	

		Context context = new Context();
		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("jobOrderId", String.valueOf(conData.get(0)[0]));
		context.setVariable("jobOrderDate", String.valueOf(conData.get(0)[1]));
		context.setVariable("igmNo", String.valueOf(crgData[0]));
		context.setVariable("igmDate", String.valueOf(crgData[1]));
		context.setVariable("itemNo", String.valueOf(crgData[2]));
		context.setVariable("vessel", String.valueOf(crgData[3]));
		context.setVariable("top", String.valueOf(crgData[4]));
		context.setVariable("cha", String.valueOf(crgData[5]));
		context.setVariable("imp", String.valueOf(crgData[6]));
		context.setVariable("sa", String.valueOf(crgData[7]));
		context.setVariable("mobile", String.valueOf(crgData[8]));
		context.setVariable("conData", conData);
		context.setVariable("totalPkgs", totalPkgs);
		context.setVariable("totalWt", totalWt);
		String htmlContent = templateEngine.process("CFSImportItemSealCut", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}

	
	@PostMapping("/importSealCuttingContainerwiseReport")
	public ResponseEntity<?> importSealCuttingContainerwiseReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm, @RequestParam("con") String con,
			@RequestParam("jobOrder") String jobOrder)
			throws DocumentException {

		List<Object[]> conData = cfigmcnrepo.getDataForSealCuttingConWiseReport(cid, bid, igm, con, jobOrder);
		
		if(conData.isEmpty()) {
	 	   return new ResponseEntity<>("Container data not found.",HttpStatus.CONFLICT);	
		}
		
		Object[] data = conData.get(0);
		
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
		
	
		Context context = new Context();
		
		BigDecimal totalPkgs = conData.stream()
		        .map(record -> record[16]) // Renamed lambda parameter to "record"
		        .filter(value -> value != null) // Filter out null values
		        .map(value -> new BigDecimal(value.toString())) // Convert to BigDecimal
		        .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all values
			
		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("jobOrderId", String.valueOf(data[0]));
		context.setVariable("jobOrderDate", String.valueOf(data[1]));
		context.setVariable("cha", String.valueOf(data[2]));
		context.setVariable("imp", String.valueOf(data[3]));
		context.setVariable("igmNo", String.valueOf(data[4]));
		context.setVariable("sl", String.valueOf(data[5]));
		context.setVariable("sealNo", String.valueOf(data[6]));
		context.setVariable("grossWt", String.valueOf(data[7]));
		context.setVariable("mobile", String.valueOf(data[8]));
		context.setVariable("commodity", String.valueOf(data[9]));
		context.setVariable("gateInDate", String.valueOf(data[18]));
		context.setVariable("sealCutDate", String.valueOf(data[19]));
		context.setVariable("conData", conData);
		context.setVariable("totalPkgs", totalPkgs);
	
		String htmlContent = templateEngine.process("CFSImportContainerSealCut", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
	
	
	@PostMapping("/importExaminationItemwiseReport")
	public ResponseEntity<?> importExaminationItemwiseReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm, @RequestParam("item") String item,
			@RequestParam("jobOrder") String jobOrder,@RequestParam("trans") String trans)
			throws DocumentException {




		List<Object[]> conData = cfigmcnrepo.getDataForExaminationItemWiseReport(cid, bid, igm, item, jobOrder,trans);

		if (conData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
		}
		
		Object[] crgData = conData.get(0);

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

		Context context = new Context();
		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("jobOrderId", String.valueOf(crgData[0]));
		context.setVariable("jobOrderDate", String.valueOf(crgData[1]));
		context.setVariable("igmNo", String.valueOf(crgData[2]));
		context.setVariable("itemNo", String.valueOf(crgData[3]));
		context.setVariable("cha", String.valueOf(crgData[4]));
		context.setVariable("imp", String.valueOf(crgData[5]));
		context.setVariable("sl", String.valueOf(crgData[6]));
		context.setVariable("createdBy", String.valueOf(crgData[16]));
		context.setVariable("commodity", String.valueOf(crgData[7]));
		context.setVariable("conData", conData);
		String htmlContent = templateEngine.process("CFSImportItemExamination", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
	
	
	
	@PostMapping("/importExaminationContainerwiseReport")
	public ResponseEntity<?> importExaminationContainerwiseReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm, @RequestParam("con") String con,
			@RequestParam("trans") String trans)
			throws DocumentException {


		List<Object[]> conData = cfigmcnrepo.getDataForExaminationContainerWiseReport(cid, bid, igm, con, trans);

		if (conData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
		}
		
		Object[] crgData = conData.get(0);

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

		Context context = new Context();
		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("jobOrderId", String.valueOf(crgData[0]));
		context.setVariable("jobOrderDate", String.valueOf(crgData[1]));
		context.setVariable("igmNo", String.valueOf(crgData[2]));
		context.setVariable("itemNo", String.valueOf(crgData[3]));
		context.setVariable("cha", String.valueOf(crgData[4]));
		context.setVariable("imp", String.valueOf(crgData[5]));
		context.setVariable("sl", String.valueOf(crgData[6]));
		context.setVariable("createdBy", String.valueOf(crgData[16]));
		context.setVariable("commodity", String.valueOf(crgData[7]));
		context.setVariable("conData", conData);
		String htmlContent = templateEngine.process("CFSImportItemExamination", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
	
	
	
	
	@PostMapping("/importDestuffItemwiseReport")
	public ResponseEntity<?> importDestuffItemwiseReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm, @RequestParam("line") String line,
			@RequestParam("trans") String trans)
			throws DocumentException {




		List<Object[]> conData = destuffCrgRepo.getImportReportsDataByDestuffIgmWise(cid, bid, igm,trans,line);

		if (conData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
		}
		
		Map<String, Integer> countOdConSize = new HashMap<>();

		conData.stream().forEach(c -> {
		    String key = "Total " + c[11] + "FT Containers : ";
		    countOdConSize.put(key, countOdConSize.getOrDefault(key, 0) + 1);
		});


		
		Object[] crgData = conData.get(0);

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

		Context context = new Context();
		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("jobOrderId", String.valueOf(crgData[0]));
		context.setVariable("jobOrderDate", String.valueOf(crgData[1]));
		context.setVariable("igmNo", String.valueOf(crgData[3]));
		context.setVariable("itemNo", String.valueOf(crgData[2]));
		context.setVariable("boeNo", String.valueOf(crgData[4]));
		context.setVariable("boeDate", String.valueOf(crgData[5]));
		context.setVariable("cha", String.valueOf(crgData[6]));
		context.setVariable("imp", String.valueOf(crgData[7]));
		context.setVariable("sl", String.valueOf(crgData[8]));
		context.setVariable("commodity", String.valueOf(crgData[9]));
		context.setVariable("conData", conData);
		context.setVariable("countOdConSize", countOdConSize);
		String htmlContent = templateEngine.process("CFSImportItemDestuff", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
	
	
	
	@PostMapping("/importDestuffContwiseFCLReport")
	public ResponseEntity<?> importDestuffContwiseFCLReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm, @RequestParam("con") String con,
			@RequestParam("trans") String trans,@RequestParam("transType") String transType)
			throws DocumentException {


    		List<Object[]> conData = destuffCrgRepo.getImportReportsDataByDestuffConWiseFCL(cid, bid, igm,trans,con);

    		if (conData.isEmpty()) {
    			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
    		}
    		
    		
    		Map<String, Integer> countOdConSize = new HashMap<>();
    		Set<String> uniqueContainers = new HashSet<>();

    		conData.stream().forEach(c -> {
    		    String key = "Total " + c[11] + "FT Containers : ";
    		    String uniqueIdentifier = c[10] + "|" + c[11]; // Combine c[10] and c[11] to ensure uniqueness

    		    if (!uniqueContainers.contains(uniqueIdentifier)) {
    		        uniqueContainers.add(uniqueIdentifier); // Track unique c[10] for the given c[11]
    		        countOdConSize.put(key, countOdConSize.getOrDefault(key, 0) + 1);
    		    }
    		});


    		
    		Object[] crgData = conData.get(0);

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

    		Context context = new Context();
    		context.setVariable("companyname", comp.getCompany_name());
    		context.setVariable("branchName", branchAddress.getBranchName());
    		context.setVariable("address", branchAdd);
    		context.setVariable("jobOrderId", crgData[0] != null ? String.valueOf(crgData[0]) : "");
    		context.setVariable("jobOrderDate", crgData[1] != null ? String.valueOf(crgData[1]) : "");
    		context.setVariable("igmNo", crgData[3] != null ? String.valueOf(crgData[3]) : "");
    		context.setVariable("itemNo", crgData[2] != null ? String.valueOf(crgData[2]) : "");
    		context.setVariable("boeNo", crgData[4] != null ? String.valueOf(crgData[4]) : "");
    		context.setVariable("boeDate", crgData[5] != null ? String.valueOf(crgData[5]) : "");
    		context.setVariable("cha", crgData[6] != null ? String.valueOf(crgData[6]) : "");
    		context.setVariable("imp", crgData[7] != null ? String.valueOf(crgData[7]) : "");
    		context.setVariable("sl", crgData[8] != null ? String.valueOf(crgData[8]) : "");
    		context.setVariable("commodity", crgData[9] != null ? String.valueOf(crgData[9]) : "");
    		context.setVariable("conData", conData);
    		context.setVariable("countOdConSize", countOdConSize);
    		context.setVariable("transType", transType);
    		String htmlContent = templateEngine.process("CFSImportFCLContDestuff", context);

    		ITextRenderer renderer = new ITextRenderer();

    		renderer.setDocumentFromString(htmlContent);
    		renderer.layout();

    		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    		renderer.createPDF(outputStream);

    		byte[] pdfBytes = outputStream.toByteArray();

    		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

    		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
        }
	
	
	
	
	@PostMapping("/importGatePassItemWiseReport")
	public ResponseEntity<?> importGatePassItemWiseReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("gate") String gate)
			throws DocumentException {


    		List<Object[]> conData = importGatePassRepo.getDataForImportGatePassItemWiseReport(cid, bid, gate);

    		if (conData.isEmpty()) {
    			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
    		}
    		
    		
    		Object[] crgData = conData.get(0);

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

    		Context context = new Context();
    		context.setVariable("companyname", comp.getCompany_name());
    		context.setVariable("branchName", branchAddress.getBranchName());
    		context.setVariable("address", branchAdd);
    		context.setVariable("gatePassId", crgData[0] != null ? String.valueOf(crgData[0]) : "");
    		context.setVariable("gatePassDate", crgData[1] != null ? String.valueOf(crgData[1]) : "");
    		context.setVariable("igmNo", crgData[2] != null ? String.valueOf(crgData[2]) : "");
    		context.setVariable("itemNo", crgData[3] != null ? String.valueOf(crgData[3]) : "");
    		context.setVariable("blNo", crgData[4] != null ? String.valueOf(crgData[4]) : "");
    		context.setVariable("blDate", crgData[5] != null ? String.valueOf(crgData[5]) : "");
    		context.setVariable("boeNo", crgData[6] != null ? String.valueOf(crgData[6]) : "");
    		context.setVariable("boeDate", crgData[7] != null ? String.valueOf(crgData[7]) : "");
    		context.setVariable("doNo", crgData[8] != null ? String.valueOf(crgData[8]) : "");
    		context.setVariable("doValDate", crgData[9] != null ? String.valueOf(crgData[9]) : "");
    		context.setVariable("cha", crgData[10] != null ? String.valueOf(crgData[10]) : "");
    		context.setVariable("imp", crgData[11] != null ? String.valueOf(crgData[11]) : "");
    		context.setVariable("sl", crgData[12] != null ? String.valueOf(crgData[12]) : "");
    		context.setVariable("vessel", crgData[13] != null ? String.valueOf(crgData[13]) : "");
    		context.setVariable("via", crgData[14] != null ? String.valueOf(crgData[14]) : "");
    		context.setVariable("commodity", crgData[15] != null ? String.valueOf(crgData[15]) : "");
    		context.setVariable("remarks", crgData[16] != null ? String.valueOf(crgData[16]) : "");
    		context.setVariable("conData", conData);
    		String htmlContent = templateEngine.process("CFSImportItemwiseGatePass", context);

    		ITextRenderer renderer = new ITextRenderer();

    		renderer.setDocumentFromString(htmlContent);
    		renderer.layout();

    		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    		renderer.createPDF(outputStream);

    		byte[] pdfBytes = outputStream.toByteArray();

    		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

    		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
        }
	
	
	@PostMapping("/importGatePassItemWiseLCLReport")
	public ResponseEntity<?> importGatePassItemWiseLCLReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("gate") String gate)
			throws DocumentException {


    		List<Object[]> conData = importGatePassRepo.getDataForImportGatePassItemWiseLCLReport(cid, bid, gate);

    		if (conData.isEmpty()) {
    			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
    		}
    		
    		
    		Object[] crgData = conData.get(0);

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

    		Context context = new Context();
    		context.setVariable("companyname", comp.getCompany_name());
    		context.setVariable("branchName", branchAddress.getBranchName());
    		context.setVariable("address", branchAdd);
    		context.setVariable("igmNo", crgData[0] != null ? String.valueOf(crgData[0]) : "");
    		context.setVariable("itemNo", crgData[1] != null ? String.valueOf(crgData[1]) : "");
    		context.setVariable("gatePassId", crgData[2] != null ? String.valueOf(crgData[2]) : "");
    		context.setVariable("igmDate", crgData[3] != null ? String.valueOf(crgData[3]) : "");
    		context.setVariable("gatePassDate", crgData[4] != null ? String.valueOf(crgData[4]) : "");
    		context.setVariable("boeNo", crgData[5] != null ? String.valueOf(crgData[5]) : "");
    		context.setVariable("boeDate", crgData[6] != null ? String.valueOf(crgData[6]) : "");
    		context.setVariable("blNo", crgData[7] != null ? String.valueOf(crgData[7]) : "");
    		context.setVariable("vessel", crgData[8] != null ? String.valueOf(crgData[8]) : "");
    		context.setVariable("voyageNo", crgData[9] != null ? String.valueOf(crgData[9]) : "");
    		context.setVariable("con", crgData[10] != null ? String.valueOf(crgData[10]) : "");
    		context.setVariable("cha", crgData[11] != null ? String.valueOf(crgData[11]) : "");
    		context.setVariable("size", crgData[12] != null ? String.valueOf(crgData[12]) : "");
    		context.setVariable("imp", crgData[13] != null ? String.valueOf(crgData[13]) : "");
    		context.setVariable("sa", crgData[14] != null ? String.valueOf(crgData[14]) : "");
    		context.setVariable("qty", crgData[15] != null ? String.valueOf(crgData[15]) : "");
    		context.setVariable("validity", crgData[16] != null ? String.valueOf(crgData[16]) : "");
    		context.setVariable("wt", crgData[17] != null ? String.valueOf(crgData[17]) : "");
    		context.setVariable("remark", crgData[18] != null ? String.valueOf(crgData[18]) : "");
    		context.setVariable("conData", conData);
    		String htmlContent = templateEngine.process("CFSImportItemwiseLCLGatePass", context);

    		ITextRenderer renderer = new ITextRenderer();

    		renderer.setDocumentFromString(htmlContent);
    		renderer.layout();

    		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    		renderer.createPDF(outputStream);

    		byte[] pdfBytes = outputStream.toByteArray();

    		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

    		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
        }
       
	
	@PostMapping("/importGatePassItemWiseDestuffReport")
	public ResponseEntity<?> importGatePassItemWiseDestuffReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("gate") String gate)
			throws DocumentException {


    		List<Object[]> conData = importGatePassRepo.getDataForImportGatePassItemWiseLCLReport(cid, bid, gate);

    		if (conData.isEmpty()) {
    			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
    		}
    		
    		
    		Object[] crgData = conData.get(0);

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

    		Context context = new Context();
    		context.setVariable("companyname", comp.getCompany_name());
    		context.setVariable("branchName", branchAddress.getBranchName());
    		context.setVariable("address", branchAdd);
    		context.setVariable("igmNo", crgData[0] != null ? String.valueOf(crgData[0]) : "");
    		context.setVariable("itemNo", crgData[1] != null ? String.valueOf(crgData[1]) : "");
    		context.setVariable("gatePassId", crgData[2] != null ? String.valueOf(crgData[2]) : "");
    		context.setVariable("igmDate", crgData[3] != null ? String.valueOf(crgData[3]) : "");
    		context.setVariable("gatePassDate", crgData[4] != null ? String.valueOf(crgData[4]) : "");
    		context.setVariable("boeNo", crgData[5] != null ? String.valueOf(crgData[5]) : "");
    		context.setVariable("boeDate", crgData[6] != null ? String.valueOf(crgData[6]) : "");
    		context.setVariable("blNo", crgData[7] != null ? String.valueOf(crgData[7]) : "");
    		context.setVariable("vessel", crgData[8] != null ? String.valueOf(crgData[8]) : "");
    		context.setVariable("voyageNo", crgData[9] != null ? String.valueOf(crgData[9]) : "");
    		context.setVariable("con", crgData[10] != null ? String.valueOf(crgData[10]) : "");
    		context.setVariable("cha", crgData[11] != null ? String.valueOf(crgData[11]) : "");
    		context.setVariable("size", crgData[12] != null ? String.valueOf(crgData[12]) : "");
    		context.setVariable("imp", crgData[13] != null ? String.valueOf(crgData[13]) : "");
    		context.setVariable("sa", crgData[14] != null ? String.valueOf(crgData[14]) : "");
    		context.setVariable("qty", crgData[15] != null ? String.valueOf(crgData[15]) : "");
    		context.setVariable("validity", crgData[16] != null ? String.valueOf(crgData[16]) : "");
    		context.setVariable("wt", crgData[17] != null ? String.valueOf(crgData[17]) : "");
    		context.setVariable("remark", crgData[18] != null ? String.valueOf(crgData[18]) : "");
    		context.setVariable("conData", conData);
    		String htmlContent = templateEngine.process("CFSImportItemwiseDestuffGatePass", context);

    		ITextRenderer renderer = new ITextRenderer();

    		renderer.setDocumentFromString(htmlContent);
    		renderer.layout();

    		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    		renderer.createPDF(outputStream);

    		byte[] pdfBytes = outputStream.toByteArray();

    		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

    		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
        }
       
	
	
	
	@PostMapping("/importGateOutReport")
	public ResponseEntity<?> importGateOutReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("gate") String gate)
			throws DocumentException {


    		List<Object[]> conData = gateOutRepo.getImportGateOutData(cid, bid, gate);

    		if (conData.isEmpty()) {
    			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
    		}
    		
    		
    		Object[] crgData = conData.get(0);

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

    		Context context = new Context();
    		context.setVariable("companyname", comp.getCompany_name());
    		context.setVariable("branchName", branchAddress.getBranchName());
    		context.setVariable("address", branchAdd);
    		context.setVariable("gateOutId", crgData[0] != null ? String.valueOf(crgData[0]) : "");
    		context.setVariable("gateOutDate", crgData[1] != null ? String.valueOf(crgData[1]) : "");
    		context.setVariable("containerNo", crgData[2] != null ? String.valueOf(crgData[2]) : "");
    		context.setVariable("containerSize", crgData[3] != null ? String.valueOf(crgData[3]) : "");
    		context.setVariable("containerType", crgData[4] != null ? String.valueOf(crgData[4]) : "");
    		context.setVariable("iso", crgData[5] != null ? String.valueOf(crgData[5]) : "");
    		context.setVariable("vehicleNo", crgData[6] != null ? String.valueOf(crgData[6]) : "");
    		context.setVariable("transporterName", crgData[7] != null ? String.valueOf(crgData[7]) : "");
    		context.setVariable("vessel", crgData[8] != null ? String.valueOf(crgData[8]) : "");
    		context.setVariable("via", crgData[9] != null ? String.valueOf(crgData[9]) : "");
    		context.setVariable("sealNo", crgData[10] != null ? String.valueOf(crgData[10]): "");
    		context.setVariable("blNo", crgData[11] != null ? String.valueOf(crgData[11]) : "");
    		context.setVariable("blDate", crgData[12] != null ? String.valueOf(crgData[12]) : "");
    		context.setVariable("doNo", crgData[13] != null ? String.valueOf(crgData[13]) : "");
    		context.setVariable("beNo", crgData[14] != null ? String.valueOf(crgData[14]) : "");
    		context.setVariable("beDate", crgData[15] != null ? String.valueOf(crgData[15]) : "");
    		context.setVariable("validity", crgData[16] != null ? String.valueOf(crgData[16]) : "");
    		context.setVariable("igmNo", crgData[17] != null ? String.valueOf(crgData[17]) : "");
    		context.setVariable("itemNo", crgData[18] != null ? String.valueOf(crgData[18]) : "");
    		context.setVariable("scanStatus", (crgData[19] != null && !String.valueOf(crgData[19]).isEmpty()) ? "Yes" : "No");
    		context.setVariable("sl", crgData[20] != null ? String.valueOf(crgData[20]) : "");
    		context.setVariable("imp", crgData[21] != null ? String.valueOf(crgData[21]) : "");
    		context.setVariable("cha", crgData[22] != null ? String.valueOf(crgData[22]) : "");
    		context.setVariable("condition", crgData[23] != null ? String.valueOf(crgData[23]) : "");
    		context.setVariable("remark", crgData[24] != null ? String.valueOf(crgData[24]) : "");
    		String htmlContent = templateEngine.process("CFSImportGateOut", context);

    		ITextRenderer renderer = new ITextRenderer();

    		renderer.setDocumentFromString(htmlContent);
    		renderer.layout();

    		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    		renderer.createPDF(outputStream);

    		byte[] pdfBytes = outputStream.toByteArray();

    		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

    		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
        }
	
	
	
	@PostMapping("/importManualGateInReport")
	public ResponseEntity<?> importManualGateInReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("gate") String gate)
			throws DocumentException {


    		Object conData = manualcontainergateinrepo.getDataForImportReport(cid, bid, gate);

    		if (conData == null) {
    			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
    		}
    		
    		
    		Object[] crgData = (Object[])conData;

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

    		Context context = new Context();
    		context.setVariable("companyname", comp.getCompany_name());
    		context.setVariable("branchName", branchAddress.getBranchName());
    		context.setVariable("address", branchAdd);
    		context.setVariable("gateInId", crgData[0] != null ? String.valueOf(crgData[0]) : "");
    		context.setVariable("gateInDate", crgData[1] != null ? String.valueOf(crgData[1]) : "");
    		context.setVariable("portExitNo", crgData[2] != null ? String.valueOf(crgData[2]) : "");
    		context.setVariable("portExitDate", crgData[3] != null ? String.valueOf(crgData[3]) : "");
    		context.setVariable("containerNo", crgData[4] != null ? String.valueOf(crgData[4]) : "");
    		context.setVariable("iso", crgData[5] != null ? String.valueOf(crgData[5]) : "");
    		context.setVariable("containerSize", crgData[6] != null ? String.valueOf(crgData[6]) : "");
    		context.setVariable("containerType", crgData[7] != null ? String.valueOf(crgData[7]) : "");
    		context.setVariable("vehicleNo", crgData[8] != null ? String.valueOf(crgData[8]) : "");
    		context.setVariable("transporter", crgData[9] != null ? String.valueOf(crgData[9]) : "");
    		context.setVariable("scanStatus", (crgData[10] != null && !String.valueOf(crgData[10]).isEmpty()) ? "Yes" : "No");
    		context.setVariable("viaNo", crgData[11] != null ? String.valueOf(crgData[11]) : "");
    		context.setVariable("sealNo", crgData[12] != null ? String.valueOf(crgData[12]) : "");
    		context.setVariable("sl", crgData[13] != null ? String.valueOf(crgData[13]) : "");
    		context.setVariable("imp", crgData[14] != null ? String.valueOf(crgData[14]) : "");
    		context.setVariable("condition", crgData[15] != null ? String.valueOf(crgData[15]) : "");
    		context.setVariable("remark", crgData[16] != null ? String.valueOf(crgData[16]) : "");
    		
    		String htmlContent = templateEngine.process("CFSImportManualGateIn", context);

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
