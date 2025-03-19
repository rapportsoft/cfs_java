package com.cwms.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.cwms.entities.GateIn;
import com.cwms.entities.GeneralGateIn;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.GeneralGateInRepo;
import com.cwms.service.GeneralGateInService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/api/generalgatein")
@CrossOrigin("*")
public class GeneralGateInController {

	@Autowired
	public GeneralGateInService generalGateInService;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	@Autowired
	public GeneralGateInRepo generalGateInRepo;
	
	 @GetMapping("/getAllBoeNoFromJobEntry")
	    public List<Object[]> getAllBoeNoFromJobEntry(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(value = "value", required = false) String value) {
	        return generalGateInService.getAllBoeNoFromJobEntry(companyId, branchId, value);
	    }
	    
	    
	    
	    
	    
	    
	    
	    @GetMapping("/getAllNocDtl")
	    public List<Object[]> getAllNocDtl(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("boeNo") String boeNo,
	            @RequestParam("nocTransId") String nocTransId,
	            @RequestParam(value = "value", required = false) String value) {
	        return generalGateInService.getAllNocDtl(companyId, branchId, boeNo,nocTransId,value);
	    }
	    @PostMapping("/saveGateInAndUpdateCfBondNocAndDtl")
		public ResponseEntity<?> saveGateInAndUpdateCfBondNocAndDtl(@RequestParam String cid, @RequestParam String bid,
				@RequestParam String user, @RequestParam String flag, @RequestBody Map<String, Object> requestBody) {

			// Call the saveData method from the service
			return generalGateInService.saveDataOfGateIn(cid, bid, user, flag, requestBody);
		}
	    
	    
	    
	    
	    
	    @GetMapping("/searchCragoGateIn")
		public List<GeneralGateIn> searchCragoGateIn(@RequestParam("companyId") String companyId,
				@RequestParam("branchId") String branchId, @RequestParam(name = "search", required = false) String search) {
			return generalGateInService.findAllCargoGateIn(companyId, branchId, search);
		}
	    
	    
	    
	    
	    
	    @GetMapping("/getDataBYGateInId")
		public List<GeneralGateIn> getDataBYGateInId(@RequestParam("companyId") String companyId,
				@RequestParam("branchId") String branchId, @RequestParam("gateInId") String gateInId) {
			return generalGateInService.getDataByGateInId(companyId, branchId, gateInId);
		}
	    
	    
	    
		@GetMapping("/printOfGateInCargoDetails")
		public ResponseEntity<String> printOfGateInCargoDetails(@RequestParam(name = "companyId") String companyId,
				@RequestParam(name = "branchId") String branchId, @RequestParam(name = "uname") String username,
				@RequestParam(name = "type") String type, @RequestParam(name = "cname") String companyname,
				@RequestParam(name = "bname") String branchname, @RequestParam(name = "gateInId") String gateInId,
				@RequestParam(name = "erpDocRefNo") String erpDocRefNo) throws DocumentException {
			
			List<GeneralGateIn> getDistinct = generalGateInRepo.getDistinct(companyId, branchId, gateInId);

			PDFMergerUtility pdfMerger = new PDFMergerUtility();

			for (GeneralGateIn d : getDistinct) {

				byte[] result = printGateInBondPrint(username, companyname, branchname, companyId, branchId, gateInId,
						d.getJobTransId(), d.getBoeNo());
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
//		        byte[] pdfBytes = outputStream.toByteArray();
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

				List<GeneralGateIn> result = generalGateInRepo.findDataForGettingPrint(companyId, branchId, gateInId, erpDocRefNo1,
						boeNo);

				BigDecimal totalJobNop = result.stream().map(GeneralGateIn::getJobNop).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
				
				BigDecimal totalJobWeight =result.stream().map(GeneralGateIn::getJobGwt).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
				System.out.println("Processing result: " + result);
//				List<GateIn> resultForDetails = gateInRepo.findDataForGettingPrintDetails(companyId, branchId, gateInId,
//						erpDocRefNo1, boeNo);

//				System.out.println("Processing resultForDetails: " + resultForDetails);
				if (!result.isEmpty()) {

					GeneralGateIn dataForPrint = result.get(0);

					Company companyAddress = companyRepo.findByCompany_Id(companyId);
					Branch branchAddress = branchRepo.findByBranchId(branchId);

					String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
							+ companyAddress.getAddress_3() + companyAddress.getCity();

					String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
							+ branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

					String city = companyAddress.getCity();
					String bondCode = branchAddress.getBondCode();

					context.setVariable("gatePassNo", dataForPrint.getGateInId());
					context.setVariable("gateInDate", dataForPrint.getGateInDate());
					context.setVariable("nocNo", dataForPrint.getJobNo());
					context.setVariable("nocDate", dataForPrint.getJobDate());
					context.setVariable("boeNo", dataForPrint.getBoeNo());
					context.setVariable("boeDate", dataForPrint.getBoeDate());
//					context.setVariable("lineNo", dataForPrint.getLineNo());
//					context.setVariable("igmNo", dataForPrint.getNocNo());
					context.setVariable("nocQty", totalJobNop);
					context.setVariable("totalGrossWeight", totalJobWeight);

					context.setVariable("imp", dataForPrint.getImporterName());
					context.setVariable("cha", dataForPrint.getCreatedBy());
//					context.setVariable("impAddress", dataForPrint.getCreatedBy() + " " + dataForPrint.getEditedBy() + " "
//							+ dataForPrint.getApprovedBy());
					context.setVariable("c1", username);
					context.setVariable("b1", companyname);
					context.setVariable("u1", branchname);
					context.setVariable("companyAdd", companyAdd);
					context.setVariable("branchAdd", branchAdd);
					context.setVariable("bondCode", bondCode);
					context.setVariable("city", city);

					context.setVariable("result", result);

					String htmlContent = templateEngine.process("GeneralCargoGateInReport", context);

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

}
