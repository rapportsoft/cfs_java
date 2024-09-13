package com.cwms.controller;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.entities.Party;
import com.cwms.entities.PartyAddress;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfbondnocRepository;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.GateInRepo;
import com.cwms.repository.PartyAddressRepository;
import com.cwms.entities.Branch;
import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Company;
import com.cwms.entities.GateIn;
import com.cwms.service.CfbondnocService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cfbondnoc")
@CrossOrigin("*")
public class CfbondnocController {

	@Autowired
	private CfbondnocService cfbondnocService;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private CfbondnocRepository cfbondnocRepository;

	@Autowired
	private GateInRepo gateInRepo;

	@Autowired
	private PartyAddressRepository partyAddressRepository;

	@GetMapping
	public ResponseEntity<List<Cfbondnoc>> getAll() {
		return new ResponseEntity<>(cfbondnocService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/search")
	public List<Party> getParties(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,
			@RequestParam(name = "partyName", required = false) String partyName) {
		return cfbondnocService.findParties(companyId, branchId, partyName);
	}

	@GetMapping("/searchCragoGateIn")
	public List<GateIn> searchCragoGateIn(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam(name = "search", required = false) String search) {
		return cfbondnocService.findAllCargoGateIn(companyId, branchId, search);
	}

	@GetMapping("/getDataBYGateInId")
	public List<GateIn> getDataBYGateInId(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("gateInId") String gateInId) {
		return cfbondnocService.getDataByGateInId(companyId, branchId, gateInId);
	}

	@GetMapping("/searchImporters")
	public List<Party> getImporetres(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,
			@RequestParam(name = "partyName", required = false) String partyName) {
		return cfbondnocService.findAllImporter(companyId, branchId, partyName);
	}
	
	@GetMapping("/searchImportersAddress")
	public List<PartyAddress> getImporetresAddress(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,
			@RequestParam(name = "partyId", required = false) String partyId) {
		return cfbondnocService.findAllImporterAddress(companyId, branchId, partyId);
	}


	@GetMapping("/getALLCfbondNocDtl")
	public List<CfBondNocDtl> getALLCfbondNocDtl(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("nocTransId") String nocTransId,
			@RequestParam("nocNo") String nocNo) {
		return cfbondnocService.findAllCfBondNocDtl(companyId, branchId, nocTransId, nocNo);
	}

	@GetMapping("/getDataByPartyIdAndGstNo")
	public Party getDataByTransId(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("partyId") String partyId,
			@RequestParam("gstNo") String gstNo, @RequestParam("sr") String sr) {
		return cfbondnocService.getDataByPartyIdAndGstNo(companyId, branchId, partyId, gstNo, sr);
	}

	@GetMapping("/getDataByPartyIdAndGstNoForImporter")
	public Party getDataByPartyIdAndGstNo(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("partyId") String partyId,
			@RequestParam("gstNo") String gstNo, @RequestParam("sr") String sr) {
		return cfbondnocService.getDataByPartyIdAndGstNoForImporter(companyId, branchId, partyId, gstNo, sr);
	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteByCompanyIdAndBranchIdAndPartyId(@RequestParam String companyId,
			@RequestParam String branchId, @RequestParam String dtlid, @RequestParam String trasnId,
			@RequestParam String nocId) {
		try {
			cfbondnocService.deleteByCompanyIdAndBranchIdAndPartyId(companyId, branchId, dtlid, trasnId, nocId);
			return ResponseEntity.ok("Record marked as deleted successfully.");
		} catch (Exception e) {
			// Log the exception
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to mark record as deleted.");
		}
	}

	@GetMapping("/getDataCfBondNocDtl")
	public CfBondNocDtl getDataCfBondNocDtl(@RequestParam String companyId, @RequestParam String branchId,
			@RequestParam String dtlid, @RequestParam String trasnId, @RequestParam String nocId) {
		return cfbondnocService.getDataCfBondDtlById(companyId, branchId, dtlid, trasnId, nocId);
	}

	@PostMapping("/saveNoc")
	public ResponseEntity<?> saveData(@RequestParam String cid, @RequestParam String bid, @RequestParam String user,
			@RequestParam String flag, @RequestBody Map<String, Object> requestBody) {

		// Call the saveData method from the service
		return cfbondnocService.saveData(cid, bid, user, flag, requestBody);
	}

	@PostMapping("/saveCfBondNoc")
	public ResponseEntity<?> saveCfBondNoc(@RequestParam String cid, @RequestParam String bid,
			@RequestParam String user, @RequestParam String flag, @RequestBody Cfbondnoc requestBody) {

		// Call the saveData method from the service
		return cfbondnocService.saveDataOfCfBondNoc(cid, bid, user, flag, requestBody);
	}

	@GetMapping("/dataAllDataOfCfBondNoc")
	public ResponseEntity<List<Cfbondnoc>> getCfbondnocData(@RequestParam String companyId,
			@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
		List<Cfbondnoc> data = cfbondnocService.getCfbondnocData(companyId, branchId, partyName);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/dataAllDataOfCfBondNocForInbondScreen")
	public ResponseEntity<List<Cfbondnoc>> dataAllDataOfCfBondNocForInbondScreen(@RequestParam String companyId,
			@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
		List<Cfbondnoc> data = cfbondnocService.getCfbondnocDataForInBondScreen(companyId, branchId, partyName);
		return ResponseEntity.ok(data);
	}

	
	@GetMapping("/dataOfBoeNoForNeeEntry")
	public ResponseEntity<?> dataOfBoeNoForNeeEntry(@RequestParam String companyId,
			@RequestParam String branchId, @RequestParam String nocTransId,@RequestParam String nocNo,
			@RequestParam String boeNo) {
		Cfbondnoc data = cfbondnocService.dataOfBoeNoForNeeEntry(companyId, branchId, nocTransId,nocNo,boeNo);
		return ResponseEntity.ok(data);
	}
	
	@GetMapping("/getCfbondnocDataForCfBondGateIn")
	public ResponseEntity<List<Cfbondnoc>> getCfbondnocDataForCfBondGateIn(@RequestParam String companyId,
			@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
		List<Cfbondnoc> data = cfbondnocService.getCfbondnocDataForCfBondGateIn(companyId, branchId, partyName);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/getDataByTransIdANDNocID")
	public Cfbondnoc getDataByTransIdANDNocID(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("nocTransID") String nocTransID,
			@RequestParam("nocNo") String nocNo) {
		return cfbondnocService.getCfbondnocDataByidOrSearch(companyId, branchId, nocTransID, nocNo);
	}

	@GetMapping("/getNocCerificatePrint")
	public ResponseEntity<String> printOfSurveyDetails(@RequestParam(name = "companyId") String companyId,
			@RequestParam(name = "branchId") String branchId, @RequestParam(name = "uname") String username,
			@RequestParam(name = "type") String type, @RequestParam(name = "cname") String companyname,
			@RequestParam(name = "bname") String branchname, @RequestParam(name = "nocTransId") String nocTransId,
			@RequestParam(name = "nocNo") String nocNo) throws DocumentException {

		Context context = new Context();

		// Cfbondnoc dataForPrint
		// =cfbondnocRepository.findCfbondnocByCompanyIdAndBranchIdAndNocTransIdAndNocNo(companyId,
		// branchId, nocTransId, nocNo);

		Cfbondnoc dataForPrint = null;

		List<Cfbondnoc> result = cfbondnocRepository.findCfbondnocByCompanyIdAndBranchIdAndNocTransIdAndNocNo(companyId,
				branchId, nocTransId, nocNo);
		if (!result.isEmpty()) {
			dataForPrint = result.get(0);
			// Process the firstResult
		}

		System.out.println("gatePassdata____________________________________________");

		String c1 = username;
		String b1 = companyname;
		String u1 = branchname;

		Company companyAddress = companyRepo.findByCompany_Id(companyId);

		Branch branchAddress = branchRepo.findByBranchId(branchId);

		String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
				+ companyAddress.getAddress_3() + companyAddress.getCity();

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress1() + " "
				+ branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		String city = companyAddress.getCity();

		String bondCode = branchAddress.getBondCode();
		context.setVariable("nocNo", dataForPrint.getNocNo());
		context.setVariable("nocDate", dataForPrint.getNocDate());
		context.setVariable("boeNo", dataForPrint.getBoeNo());
		context.setVariable("boeDate", dataForPrint.getBoeDate());
		context.setVariable("igmNo", dataForPrint.getIgmNo());
		context.setVariable("igmLineNo", dataForPrint.getIgmLineNo());
		context.setVariable("nocQty", dataForPrint.getNocPackages());
		context.setVariable("weight", dataForPrint.getGrossWeight());
		context.setVariable("value", dataForPrint.getCifValue());
		context.setVariable("duty", dataForPrint.getCargoDuty());
		context.setVariable("consignee", dataForPrint.getImporterName());
		context.setVariable("cha", dataForPrint.getCha());
		context.setVariable("address", dataForPrint.getImporterAddress1() + " " + dataForPrint.getImporterAddress2()
				+ " " + dataForPrint.getImporterAddress3());
		context.setVariable("gateInType", dataForPrint.getGateInType());
		context.setVariable("packageType", dataForPrint.getTypeOfPackage());
		context.setVariable("nocArea", dataForPrint.getArea());
		context.setVariable("spaceType", dataForPrint.getSpaceType());
		context.setVariable("storageWeek", dataForPrint.getNocWeek());
		context.setVariable("nocFrom", dataForPrint.getNocFromDate());
		context.setVariable("nocTo", dataForPrint.getNocValidityDate());
//			context.setVariable("noOfTw", dataForPrint.getNoOf20ft());
		context.setVariable("noOfTw",
				dataForPrint.getNoOf20ft() != null ? dataForPrint.getNoOf20ft() : BigDecimal.ZERO);

		context.setVariable("noOfFw",
				dataForPrint.getNoOf40ft() != null ? dataForPrint.getNoOf40ft() : BigDecimal.ZERO);
		context.setVariable("cargoDesc", dataForPrint.getCommodityDescription());
		context.setVariable("haz", dataForPrint.getHaz());
		context.setVariable("storedDuty", dataForPrint.getStoredCragoDuty());
		context.setVariable("cusAppCrgoDuty", dataForPrint.getInsuranceValue());
		context.setVariable("c1", c1);
		context.setVariable("b1", b1);
		context.setVariable("u1", u1);
		context.setVariable("companyAdd", companyAdd);
		context.setVariable("branchAdd", branchAdd);
		context.setVariable("bondCode", bondCode);
		context.setVariable("city", city);

//			context.setVariable("gatePassdata", gatePassdata);

		String htmlContent = templateEngine.process("NocCertificate", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}

	@PostMapping("/saveGateInAndUpdateCfBondNocAndDtl")
	public ResponseEntity<?> saveGateInAndUpdateCfBondNocAndDtl(@RequestParam String cid, @RequestParam String bid,
			@RequestParam String user, @RequestParam String flag, @RequestBody Map<String, Object> requestBody) {

		// Call the saveData method from the service
		return cfbondnocService.saveDataOfGateIn(cid, bid, user, flag, requestBody);
	}

//	@GetMapping("/printOfGateInCargoDetails")
//	public ResponseEntity<String> printOfGateInCargoDetails(@RequestParam(name = "companyId") String companyId,
//			@RequestParam(name = "branchId") String branchId, @RequestParam(name = "uname") String username,
//			@RequestParam(name = "type") String type, @RequestParam(name = "cname") String companyname,
//			@RequestParam(name = "bname") String branchname, @RequestParam(name = "gateInId") String gateInId,
//			@RequestParam(name = "erpDocRefNo") String erpDocRefNo) throws DocumentException {
//
//		Context context = new Context();
//
//		// Cfbondnoc dataForPrint
//		// =cfbondnocRepository.findCfbondnocByCompanyIdAndBranchIdAndNocTransIdAndNocNo(companyId,
//		// branchId, nocTransId, nocNo);
//
//		GateIn dataForPrint = null;
//
//		BigDecimal totalOty = BigDecimal.ZERO;
//		BigDecimal totalGrossWeight = BigDecimal.ZERO;
//		List<GateIn> getAllDataOfGateInBond = gateInRepo.getAllDataOfGateInBond(companyId, branchId, gateInId);
//
//		if (getAllDataOfGateInBond != null) {
//
//			Set<String> distinctDocRefNos = getAllDataOfGateInBond.stream().map(GateIn::getDocRefNo)
//					.filter(Objects::nonNull).collect(Collectors.toSet());
//
//			// Get distinct erpDocRef
//			Set<String> distinctErpDocRefs = getAllDataOfGateInBond.stream().map(GateIn::getErpDocRefNo)
//					.filter(Objects::nonNull).collect(Collectors.toSet());
//
//			Set<String> distinctBoeNo = getAllDataOfGateInBond.stream().map(GateIn::getBoeNo).filter(Objects::nonNull)
//					.collect(Collectors.toSet());
//
//			Set<Integer> distinctSrNo = getAllDataOfGateInBond.stream().map(GateIn::getSrNo)
//					.collect(Collectors.toSet());
//
//			// You can print or use these distinct sets as needed
//			System.out.println("Distinct DocRefNos: " + distinctSrNo);
//			System.out.println("Distinct ErpDocRefs: " + distinctErpDocRefs);
//
//			System.out.println("Distinct distinctBoeNo: " + distinctBoeNo);
//
//				for (String erpDocRefNo1 : distinctErpDocRefs) 
//				{
//					for (String boeNo : distinctBoeNo) {
//
//						
//
//							System.out.println("Distinct erpDocRefNo1: " + erpDocRefNo1);
//							System.out.println("Distinct boeNo: " + boeNo);
////						System.out.println("Distinct DocRefNos: " + DocRefNos);
//
//							List<GateIn> result = gateInRepo.findDataForGettingPrint(companyId, branchId, gateInId,
//									erpDocRefNo1, boeNo);
//
//							List<GateIn> resultForDetails = gateInRepo.findDataForGettingPrintDetails(companyId,
//									branchId, gateInId, erpDocRefNo1, boeNo);
//
//							System.out.println("result______________" + result);
//							System.out.println("result______________" + result.size());
//							if (!result.isEmpty()) {
//
//								dataForPrint = result.get(0);
//
//							}
//
//							String c1 = username;
//							String b1 = companyname;
//							String u1 = branchname;
//
//							Company companyAddress = companyRepo.findByCompany_Id(companyId);
//
//							Branch branchAddress = branchRepo.findByBranchId(branchId);
//
//							String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
//									+ companyAddress.getAddress_3() + companyAddress.getCity();
//
//							String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress1() + " "
//									+ branchAddress.getAddress3() + " " + branchAddress.getCity() + " "
//									+ branchAddress.getPin();
//
//							String city = companyAddress.getCity();
//
//							String bondCode = branchAddress.getBondCode();
//							context.setVariable("gatePassNo", dataForPrint.getGateInId());
//							context.setVariable("gateInDate", dataForPrint.getInGateInDate());
//							context.setVariable("nocNo", dataForPrint.getDocRefNo());
//							context.setVariable("nocDate", dataForPrint.getDocRefDate());
//							context.setVariable("boeNo", dataForPrint.getBoeNo());
//							context.setVariable("boeDate", dataForPrint.getBoeDate());
//							context.setVariable("lineNo", dataForPrint.getLineNo());
//							context.setVariable("nocQty", dataForPrint.getActualNoOfPackages());
//							context.setVariable("totalGrossWeight", dataForPrint.getGrossWeight());
//							context.setVariable("imp", dataForPrint.getImporterName());
//							context.setVariable("cha", dataForPrint.getCha());
//							context.setVariable("impAddress", dataForPrint.getCreatedBy() + " "
//									+ dataForPrint.getEditedBy() + " " + dataForPrint.getApprovedBy());
//							context.setVariable("c1", c1);
//							context.setVariable("b1", b1);
//							context.setVariable("u1", u1);
//							context.setVariable("companyAdd", companyAdd);
//							context.setVariable("branchAdd", branchAdd);
//							context.setVariable("bondCode", bondCode);
//							context.setVariable("city", city);
//
//							context.setVariable("getAllDataOfGateInBond", getAllDataOfGateInBond);
//
//							context.setVariable("result", resultForDetails);
//
//							String htmlContent = templateEngine.process("customeBondGatePass", context);
//
//							ITextRenderer renderer = new ITextRenderer();
//
//							renderer.setDocumentFromString(htmlContent);
//							renderer.layout();
//
//							ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//							renderer.createPDF(outputStream);
//
//							byte[] pdfBytes = outputStream.toByteArray();
//
//							String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);
//
//							return ResponseEntity.ok()
//									.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
//						}
//					}
//				}
//		return null;
//	}

	@GetMapping("/printOfGateInCargoDetails")
	public ResponseEntity<String> printOfGateInCargoDetails(@RequestParam(name = "companyId") String companyId,
			@RequestParam(name = "branchId") String branchId, @RequestParam(name = "uname") String username,
			@RequestParam(name = "type") String type, @RequestParam(name = "cname") String companyname,
			@RequestParam(name = "bname") String branchname, @RequestParam(name = "gateInId") String gateInId,
			@RequestParam(name = "erpDocRefNo") String erpDocRefNo) throws DocumentException {
		
		List<GateIn> getDistinct = gateInRepo.getDistinct(companyId, branchId, gateInId);

		PDFMergerUtility pdfMerger = new PDFMergerUtility();

		for (GateIn d : getDistinct) {

			byte[] result = printGateInBondPrint(username, companyname, branchname, companyId, branchId, gateInId,
					d.getErpDocRefNo(), d.getBoeNo());
			ByteArrayInputStream bb = new ByteArrayInputStream(result);

			pdfMerger.addSource(bb);

		}

		// Finalize the PDF merger and obtain the merged document
		ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
		pdfMerger.setDestinationStream(outputStream1);
		try {
			pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


//
//	        byte[] pdfBytes = outputStream.toByteArray();
		String base64Pdf = Base64.getEncoder().encodeToString(outputStream1.toByteArray());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);

	}

	public byte[] printGateInBondPrint(String username,

			String companyname, String branchname, String companyId, String branchId, String gateInId,
			String erpDocRefNo1, String boeNo) {
		try {
			System.out.println("Processing erpDocRefNo1: " + erpDocRefNo1);
			System.out.println("Processing boeNo: " + boeNo);
			Context context = new Context();

			List<GateIn> result = gateInRepo.findDataForGettingPrint(companyId, branchId, gateInId, erpDocRefNo1,
					boeNo);

			System.out.println("Processing result: " + result);
			List<GateIn> resultForDetails = gateInRepo.findDataForGettingPrintDetails(companyId, branchId, gateInId,
					erpDocRefNo1, boeNo);

			System.out.println("Processing resultForDetails: " + resultForDetails);
			if (!result.isEmpty()) {

				GateIn dataForPrint = result.get(0);

				Company companyAddress = companyRepo.findByCompany_Id(companyId);
				Branch branchAddress = branchRepo.findByBranchId(branchId);

				String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
						+ companyAddress.getAddress_3() + companyAddress.getCity();

				String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
						+ branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

				String city = companyAddress.getCity();
				String bondCode = branchAddress.getBondCode();

				context.setVariable("gatePassNo", dataForPrint.getGateInId());
				context.setVariable("gateInDate", dataForPrint.getInGateInDate());
				context.setVariable("nocNo", dataForPrint.getDocRefNo());
				context.setVariable("nocDate", dataForPrint.getDocRefDate());
				context.setVariable("boeNo", dataForPrint.getBoeNo());
				context.setVariable("boeDate", dataForPrint.getBoeDate());
				context.setVariable("lineNo", dataForPrint.getLineNo());
				context.setVariable("igmNo", dataForPrint.getNocNo());
				context.setVariable("nocQty", dataForPrint.getActualNoOfPackages());
				context.setVariable("totalGrossWeight", dataForPrint.getGrossWeight());

				context.setVariable("imp", dataForPrint.getImporterName());
				context.setVariable("cha", dataForPrint.getCha());
				context.setVariable("impAddress", dataForPrint.getCreatedBy() + " " + dataForPrint.getEditedBy() + " "
						+ dataForPrint.getApprovedBy());
				context.setVariable("c1", username);
				context.setVariable("b1", companyname);
				context.setVariable("u1", branchname);
				context.setVariable("companyAdd", companyAdd);
				context.setVariable("branchAdd", branchAdd);
				context.setVariable("bondCode", bondCode);
				context.setVariable("city", city);

				context.setVariable("result", resultForDetails);

				String htmlContent = templateEngine.process("customeBondGatePass", context);

				ITextRenderer renderer = new ITextRenderer();

				renderer.setDocumentFromString(htmlContent);
				renderer.layout();

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				renderer.createPDF(outputStream);

				byte[] pdfBytes = outputStream.toByteArray();

				return pdfBytes;
			}
			
			
		}
		
		catch (Exception e) 
		{
			System.out.println(e);

		}
		return null;

	}

	

	  @GetMapping("/getAllBoeNoFromNoc")
	    public List<Object[]> getAllBoeNoFromNoc(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(value = "value", required = false) String value) {
	        return cfbondnocService.getAllBoeNoFromNoc(companyId, branchId, value);
	    }
	    
	    
		  @GetMapping("/getAllBondingNoForBondingSearch")
		    public List<Object[]> getAllBondingNoForBondingSearch(
		            @RequestParam("companyId") String companyId,
		            @RequestParam("branchId") String branchId,
		            @RequestParam(value = "value", required = false) String value) {
		        return cfbondnocService.getAllBondingNoForBondingSearch(companyId, branchId, value);
		    }
		    
	    
		  @GetMapping("/getAllNocDtl")
		    public List<Object[]> getAllNocDtl(
		            @RequestParam("companyId") String companyId,
		            @RequestParam("branchId") String branchId,
		            @RequestParam("boeNo") String boeNo,
		            @RequestParam("nocTransId") String nocTransId,
		            @RequestParam(value = "value", required = false) String value) {
		        return cfbondnocService.getAllNocDtl(companyId, branchId, boeNo,nocTransId,value);
		    }
		    
		    
		    @GetMapping("/getNocNoForBondingSearch")
		    public List<Object[]> getDataOfNocNoForCFbondinGSearch(
		    		 @RequestParam("companyId") String companyId,
			            @RequestParam("branchId") String branchId,
			            @RequestParam(value = "value", required = false) String value) {
		    	
		    	return cfbondnocService.getAllNocNoFromNoc(companyId, branchId, value);
			}
		    
		    @GetMapping("/getDataForMainBondingSearch")
		    public ResponseEntity<?> getDataForMainSearch(
		    		@RequestParam ("companyId") String companyId,
		    		@RequestParam ("branchId") String branchId,
		    		@RequestParam (value ="nocNo", required =false ) String nocNo ,
		    		@RequestParam(value ="boeNo",required = false ) String boeNo,
		    		@RequestParam(value ="bondingNo",required = false) String bondingNo
		    		) {
				return cfbondnocService.getForMainBondingSearch(companyId, branchId, nocNo, boeNo, bondingNo);
			}
		    
		    
		    @GetMapping("/getForMainSearchGateInBond")
		    public ResponseEntity<List<CfBondNocDtl>> getCfBondNocDtl(
		            @RequestParam("companyId") String companyId,
		            @RequestParam("branchId") String branchId,
		            @RequestParam("nocTransId") String nocTransId,
		            @RequestParam("nocNo") String nocNo) {
		        return cfbondnocService.getCfBondNocDtl(companyId, branchId, nocTransId, nocNo);
		    }
}
