package com.cwms.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.entities.Branch;
import com.cwms.entities.ExportCarting;
import com.cwms.entities.ExportInventory;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportStuffRequest;
import com.cwms.entities.ExportStuffTally;
import com.cwms.entities.ExportTransfer;
import com.cwms.entities.GateIn;
import com.cwms.entities.GateOut;
import com.cwms.entities.HubDocument;
import com.cwms.entities.HubGatePass;
import com.cwms.entities.StuffRequestHub;
import com.cwms.entities.VehicleTrack;
import com.cwms.entities.Vessel;
import com.cwms.entities.Voyage;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.GateOutRepo;
import com.cwms.repository.HubDocumentRepo;
import com.cwms.repository.HubGatePassRepo;
import com.cwms.repository.HubStuffRequestRepo;
import com.cwms.repository.VehicleTrackRepository;
import com.cwms.repository.VesselRepository;
import com.cwms.repository.VoyageRepository;

@Service
public class HubService {

	@Autowired
	private HubDocumentRepo hubRepo;

	@Autowired
	private HelperMethods helperMethods;

	@Autowired
	private ProcessNextIdService processService;

	@Autowired
	private GateInRepository gateInRepo;

	@Autowired
	private VehicleTrackRepository vehicleRepo;

	@Autowired
	private VoyageRepository voyagerepo;

	@Autowired
	private VesselRepository vesselRepo;

	@Autowired
	private HubStuffRequestRepo stuffingRepo;

	@Autowired
	private HubGatePassRepo gatePassRepo;

	@Autowired
	private GateOutRepo gateOutRepo;
	
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private BranchRepo branchRepo;
	
	
	
	

	public byte[] downLoadHubReportGatePass(String companyId, String branchId, String profitCenterId, String gatePassId, String userId)
	{
		try
		{
			 Context context = new Context();
		     Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);
		        
			BigDecimal totalPackage = BigDecimal.ZERO;
			BigDecimal totalWeight = BigDecimal.ZERO;
			
		        List<Object[]> exportTruckWiseGateInReport = gatePassRepo.getHubGatePassReport(companyId, branchId, gatePassId);
			
		        
		        Object[] firstRecord = exportTruckWiseGateInReport.get(0);
		        
		        
		        if (exportTruckWiseGateInReport != null && !exportTruckWiseGateInReport.isEmpty()) {
		            // Iterate through each record
		            for (Object[] record : exportTruckWiseGateInReport) {
		                // Ensure there are enough columns (at least 13 values: index 11 and 12 should exist)
		                if (record.length > 20) {
		                    // Access the values at index 11 (for package) and index 12 (for weight)
		                	BigDecimal valueAt11 = (record[20] instanceof BigDecimal) 
		                		    ? (BigDecimal) record[20] 
		                		    : new BigDecimal(record[20].toString());
		                    BigDecimal valueAt12 = (record[21] instanceof BigDecimal) ? (BigDecimal) record[21] : BigDecimal.ZERO; // Weight

		                    
		                    System.out.println(" valueAt11 " + valueAt11 + "valueAt11 " + valueAt12);
		                    
		                    // Add the values to the respective totals
		                    totalPackage = totalPackage.add(valueAt11);
		                    totalWeight = totalWeight.add(valueAt12);
		                }
		            }
		        }
		        
		        
		        String branchAdd = branch.getAddress1() + " " + branch.getAddress2() + " "
						+ branch.getAddress3();


		        context.setVariable("address", branchAdd);
				context.setVariable("branchName", branch.getBranchName());
				
				
					context.setVariable("gatepassId", firstRecord[0] != null ? (String) firstRecord[0] : "");
				    context.setVariable("gatepassDate", firstRecord[1] != null ? (String) firstRecord[1] : "");
				    context.setVariable("vehicleNo", firstRecord[2] != null ? (String) firstRecord[2] : "");
				    context.setVariable("partyNamePo", firstRecord[4] != null ? (String) firstRecord[4] : "");
				    context.setVariable("partyNamePsl", firstRecord[5] != null ? (String) firstRecord[5] : "");
				    context.setVariable("containerNo", firstRecord[6] != null ? (String) firstRecord[6] : "");
				    context.setVariable("containerSize", firstRecord[7] != null ? (String) firstRecord[7] : "");
				    context.setVariable("containerType", firstRecord[8] != null ? (String) firstRecord[8] : "");
				    context.setVariable("cargoType", firstRecord[9] != null ? (String) firstRecord[9] : "");
				    context.setVariable("icdDestination", firstRecord[10] != null ? (String) firstRecord[10] : "");
				    context.setVariable("customSealNo", firstRecord[11] != null ? (String) firstRecord[11] : "");
				    context.setVariable("agentSealNo", firstRecord[12] != null ? (String) firstRecord[12] : "");
				    context.setVariable("movementType", firstRecord[13] != null ? (String) firstRecord[13] : "");
				    context.setVariable("destination", firstRecord[14] != null ? (String) firstRecord[14] : "");
				    context.setVariable("comments", firstRecord[15] != null ? (String) firstRecord[15] : "");
				    context.setVariable("driver", firstRecord[22] != null ? (String) firstRecord[22] : "");
				    context.setVariable("transporter", firstRecord[23] != null ? (String) firstRecord[23] : "");

       
		        context.setVariable("totalPackage", totalPackage);
		        context.setVariable("totalWeight", totalWeight);
		       
		        
		        context.setVariable("pass", firstRecord);
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportTruckWiseGateInReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("HubGatePassReport", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	
	
	
	
	
	
	
	

	public byte[] downLoadHubReportGateIn(String companyId, String branchId, String profitCenterId, String gateInId, String userId)
	{
		try
		{
			 Context context = new Context();
		     Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);
		        
			BigDecimal totalPackage = BigDecimal.ZERO;
			BigDecimal totalWeight = BigDecimal.ZERO;
			
		        List<Object[]> exportTruckWiseGateInReport = gatePassRepo.getHubGateInReport(companyId, branchId, gateInId);
			
		        
		        Object[] firstRecord = exportTruckWiseGateInReport.get(0);
		        
		        
		        if (exportTruckWiseGateInReport != null && !exportTruckWiseGateInReport.isEmpty()) {
		            // Iterate through each record
		            for (Object[] record : exportTruckWiseGateInReport) {
		                // Ensure there are enough columns (at least 13 values: index 11 and 12 should exist)
		                if (record.length > 9) {
		                    // Access the values at index 11 (for package) and index 12 (for weight)
		                    BigDecimal valueAt11 = (record[7] instanceof BigDecimal) ? (BigDecimal) record[7] : BigDecimal.ZERO; // Package
		                    BigDecimal valueAt12 = (record[8] instanceof BigDecimal) ? (BigDecimal) record[8] : BigDecimal.ZERO; // Weight

		                    
		                    System.out.println(" valueAt11 " + valueAt11 + "valueAt11 " + valueAt12);
		                    
		                    // Add the values to the respective totals
		                    totalPackage = totalPackage.add(valueAt11);
		                    totalWeight = totalWeight.add(valueAt12);
		                }
		            }
		        }
		        
		        
		        String branchAdd = branch.getAddress1() + " " + branch.getAddress2() + " "
						+ branch.getAddress3();


		        context.setVariable("address", branchAdd);
				context.setVariable("branchName", branch.getBranchName());
		        context.setVariable("gateInId", (String) firstRecord[0]);
		        context.setVariable("vehicleNo", (String) firstRecord[3]);
		        context.setVariable("transporter", (String) firstRecord[9]);
		        context.setVariable("gateInDate", (String) firstRecord[1]);		       
		        context.setVariable("totalPackage", totalPackage);
		        context.setVariable("totalWeight", totalWeight);
		       
		        
		        
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportTruckWiseGateInReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("HubGateInReport", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public ResponseEntity<?> searchHubMain(String companyId, String branchId, String sbNo, String sbLineNo) {

		try {

			System.out.println(
					companyId + " companyId " + branchId + " branchId " + sbNo + " sbNo " + sbLineNo + " sbLineNo ");

			List<String> allowedList = new ArrayList<>();
			Map<String, Object> dataMap = new HashMap<>();
			Map<String, Object> mainData = new HashMap<>();

			Pageable pageable = PageRequest.of(0, 1);

			System.out.println("In sbSearch Null 1 ");
			Page<HubDocument> result = gatePassRepo.getDataForHubMainSearchHubDocument(companyId, branchId, sbNo,
					sbLineNo, pageable);

			HubDocument existinSbCargoEntry = null;
			System.out.println("sbSearched Null 1/2 ");
			if (!result.hasContent()) {
				System.out.println("sbSearch Null 2 ");
				return ResponseEntity.badRequest().body("No Data Found");
			}
			existinSbCargoEntry = result.getContent().get(0);

			String mainLineNo = existinSbCargoEntry.getIgmLineNo();
			String mainSbNo = existinSbCargoEntry.getIgmNo();
			String mainSbTransId = existinSbCargoEntry.getHubTransId();
			String profitCenter = existinSbCargoEntry.getProfitCentreId();

			dataMap.put("sbNo", mainSbNo);
			dataMap.put("sbLineNo", mainLineNo);
			dataMap.put("sbTransId", mainSbTransId);
			dataMap.put("profitCenterId", profitCenter);

			allowedList.add("P00101");

			System.out
					.println(" mainLineNo " + mainLineNo + " mainSbNo " + mainSbNo + " mainSbTransId " + mainSbTransId);

			System.out.println(" checking stuffing quantity 1 : " + existinSbCargoEntry.getStuffReqQty());
			if (existinSbCargoEntry.getStuffReqQty() > 0) {
				System.out.println(" In stuffing Search : 2 ");

				Page<StuffRequestHub> stuffingResult = gatePassRepo.getDataForHubMainSearchStuff(companyId, branchId,
						mainSbNo, mainSbTransId, mainLineNo, pageable);

				StuffRequestHub existinStuffingEntry = null;
				if (stuffingResult.hasContent()) {
					existinStuffingEntry = stuffingResult.getContent().get(0);
				}

				List<String> resultGateIn = gatePassRepo.getDataForHubMainSearchGateInNormal(companyId, branchId,
						profitCenter, mainSbTransId, mainSbNo, "HUB", pageable);
				String sbGateInId = resultGateIn.isEmpty() ? null : resultGateIn.get(0);

				dataMap.put("gateInId", sbGateInId);
				allowedList.add("P00102"); // sbGateIn

				dataMap.put("stuffReqId", existinStuffingEntry.getStuffReqId());
				dataMap.put("containerNo", existinStuffingEntry.getContainerNo());
				dataMap.put("containerGateInId", existinStuffingEntry.getGateInId());

				allowedList.add("P00103"); // Stuffing

				String gatePassNo = existinStuffingEntry.getGatePassId();

				if (gatePassNo != null && !gatePassNo.trim().isEmpty()) {
					dataMap.put("gatepassId", gatePassNo);
				}
				allowedList.add("P00104"); // gate pass

			} else {
				System.out.println(" In gate in : " + existinSbCargoEntry.getGateInPackages());

				if (existinSbCargoEntry.getGateInPackages() != null
						&& existinSbCargoEntry.getGateInPackages().compareTo(BigDecimal.ZERO) > 0) {

					List<String> resultSbGateInId = gatePassRepo.getDataForHubMainSearchGateInNormal(companyId,
							branchId, profitCenter, mainSbTransId, mainSbNo, "HUB", pageable);

					String sbGateInId = resultSbGateInId.isEmpty() ? null : resultSbGateInId.get(0);

					dataMap.put("gateInId", sbGateInId);

					allowedList.add("P00103");

				}
				allowedList.add("P00102"); // sbGateIn
			}

			mainData.put("allowedList", allowedList);
			mainData.put("data", dataMap);
			return ResponseEntity.ok(mainData);

		}

		catch (Exception e) {
			System.out.println(" error in a export search : " + e);
			return ResponseEntity.badRequest().body("Oops something went wrong!!!");
		}
	}

	public ResponseEntity<?> getSelectedGatePassEntry(String companyId, String branchId, String stuffingReqId) {
		List<HubGatePass> selectedGateInEntry = gatePassRepo.getSelectedGatePassEntry(companyId, branchId,
				stuffingReqId);
		return ResponseEntity.ok(selectedGateInEntry);
	}

	public List<Object[]> getGatePassEntriesToSelect(String companyId, String branchId, String searchValue,
			String profitCenterId) {
		return gatePassRepo.getGatePassEntriesToSelect(companyId, branchId, searchValue, profitCenterId);
	}

	@Transactional
	public ResponseEntity<?> saveHubGatePass(String companyId, String branchId, List<HubGatePass> stuffRequestHub,
			String user) {
		Date currentDate = new Date();
		List<HubGatePass> listToSend = new ArrayList<>();
		String financialYear = helperMethods.getFinancialYear();
		try {

			Optional<String> firstValidStuffReqId = stuffRequestHub.stream().map(HubGatePass::getGatepassId) // Extract
																												// the
																												// stuffReqId
																												// from
																												// each
																												// record
					.filter(gatepassId -> gatepassId != null && !gatepassId.trim().isEmpty()) // Filter non-null,
																								// non-empty strings
					.findFirst();

			String autoStuffingId = (firstValidStuffReqId.isPresent() && !firstValidStuffReqId.get().trim().isEmpty())
					? firstValidStuffReqId.get()
					: processService.autoExportStuffingId(companyId, branchId, "P00204");

			for (HubGatePass exportStuffLoop : stuffRequestHub) {

				if (exportStuffLoop.getGatepassId() == null || exportStuffLoop.getGatepassId().isEmpty()) {
					System.out.println("Save");

					exportStuffLoop.setCompanyId(companyId);
					exportStuffLoop.setBranchId(branchId);
					exportStuffLoop.setGatepassId(autoStuffingId);
					exportStuffLoop.setFinYear(financialYear);
					exportStuffLoop.setCreatedBy(user);
					exportStuffLoop.setCreatedDate(currentDate);
					exportStuffLoop.setEditedBy(user);
					exportStuffLoop.setEditedDate(currentDate);
					exportStuffLoop.setApprovedBy(user);
					exportStuffLoop.setApprovedDate(currentDate);
					exportStuffLoop.setStatus('A');

					HubGatePass savedHubGatePass = gatePassRepo.save(exportStuffLoop);

					GateOut gateOut = new GateOut();
					gateOut.setIgmLineNo(savedHubGatePass.getSbLineNo());
					gateOut.setCreatedDate(currentDate);
					gateOut.setCreatedBy(user);
					gateOut.setGateOutDate(currentDate);
					gateOut.setStatus('A'); // Assuming new entries start with "Pending" status

					gateOut.setContainerSize(savedHubGatePass.getContainerSize());
					gateOut.setExporterName(savedHubGatePass.getExporterName());
					gateOut.setGateOutId(autoStuffingId); // Generate new GateOut ID
					gateOut.setShift(savedHubGatePass.getShift());
					gateOut.setComments(savedHubGatePass.getComments());
					gateOut.setCommodityDescription(savedHubGatePass.getCargoDescription());
					gateOut.setWeightTakenOut(savedHubGatePass.getCargoWeight());
					gateOut.setBranchId(savedHubGatePass.getBranchId());
					gateOut.setQtyTakenOut(BigDecimal.valueOf(savedHubGatePass.getNoOfPackagesStuffed()));
					gateOut.setApprovedDate(currentDate);
					gateOut.setFinYear(savedHubGatePass.getFinYear());
					gateOut.setContainerHealth(savedHubGatePass.getContainerHealth());
					gateOut.setSl(savedHubGatePass.getShippingLine());
					gateOut.setDocRefDate(savedHubGatePass.getSbDate());
					gateOut.setGrossWt(savedHubGatePass.getGrossWeight());
					gateOut.setApprovedBy(user);
					gateOut.setVehicleNo(savedHubGatePass.getVehicleNo());
					gateOut.setContainerType(savedHubGatePass.getContainerType());
					gateOut.setContainerNo(savedHubGatePass.getContainerNo());
					gateOut.setGatePassNo(savedHubGatePass.getGatepassId());
					gateOut.setCompanyId(savedHubGatePass.getCompanyId());
					gateOut.setSrNo(String.valueOf(savedHubGatePass.getStuffReqLineId()));
					gateOut.setSa(savedHubGatePass.getShippingAgent());
					gateOut.setProfitcentreId(savedHubGatePass.getProfitcentreId());
					gateOut.setActualNoOfPackages(BigDecimal.valueOf(savedHubGatePass.getNoOfPackages()));
					gateOut.setErpDocRefNo(savedHubGatePass.getSbTransId());
					gateOut.setTransporterName(savedHubGatePass.getTransporterName());
					gateOut.setDocRefNo(savedHubGatePass.getSbNo());

					// Save the new GateOut entry
					gateOutRepo.save(gateOut);

					listToSend.add(savedHubGatePass);
					System.out.println("savedHubGatePass : " + savedHubGatePass);

				} else {
					// Update Queries for Gate Pass nd Gate Out

					int updateGatePassEdit = gatePassRepo.updateGatePassEdit(companyId, branchId,
							exportStuffLoop.getContainerNo(), exportStuffLoop.getTransporterName(),
							exportStuffLoop.getIcdDestination(), exportStuffLoop.getComments(),
							exportStuffLoop.getGatepassId(), user, currentDate);

					int updateGatePassGateOutEdit = gatePassRepo.updateGatePassGateOutEdit(companyId, branchId,
							exportStuffLoop.getContainerNo(), exportStuffLoop.getGatepassId(),
							exportStuffLoop.getTransporterName(), exportStuffLoop.getComments());

					listToSend.add(exportStuffLoop);
				}
			}
			HubGatePass savedStuffRequest = listToSend.get(0);

			int updateHubInventoryGatePass = gatePassRepo.updateHubInventoryGatePass(savedStuffRequest.getGatepassId(),
					user, currentDate, savedStuffRequest.getGateInId(), companyId, branchId);

			int updateGatePassStuffingReqUpdate = gatePassRepo.updateGatePassStuffingReqUpdate(companyId, branchId,
					savedStuffRequest.getContainerNo(), savedStuffRequest.getGateInId(),
					savedStuffRequest.getGatepassId());

			System.out.println("updateHubInventoryGatePass : " + updateHubInventoryGatePass
					+ " updateGatePassStuffingReqUpdate : " + updateGatePassStuffingReqUpdate);

			List<HubGatePass> result2 = gatePassRepo.getSelectedGatePassEntry(companyId, branchId, autoStuffingId);

			return ResponseEntity.ok(result2);
		} catch (Exception e) {
			// Log the exception (consider using a logging framework like SLF4J)
			System.out.println("An error occurred while adding export stuufing entries: " + e.getMessage());
			// Return a ResponseEntity with the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the gate-in entries.");
		}
	}

	public ResponseEntity<?> searchContainerNoForHubGatePass(String companyId, String branchId, String containerNo,
			String profitcentreId) {
		List<Object[]> result = stuffingRepo.searchContainerNoForHubGatePassOnly(companyId, branchId, containerNo,
				profitcentreId);

		List<Map<String, Object>> containerMap = result.stream().map(row -> {
			Map<String, Object> map = new HashMap<>();

			map.put("value", row[0]);
			map.put("label", row[0]);

			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(containerMap);
	}

	public ResponseEntity<?> searchContainerNoForHubGatePassSelect(String companyId, String branchId,
			String containerNo, String profitcentreId) {
		List<Object[]> result = stuffingRepo.searchContainerNoForHubGatePass(companyId, branchId, containerNo,
				profitcentreId);

		List<Map<String, Object>> containerMap = result.stream().map(row -> {
			Map<String, Object> map = new HashMap<>();

			map.put("stuffReqLineId", row[0]);
			map.put("sbTransId", row[1]);
			map.put("sbLineNo", row[2]);
			map.put("sbNo", row[3]);
			map.put("sbDate", row[4]);
			map.put("exporterName", row[5]);
			map.put("cargoDescription", row[6]);
			map.put("noOfPackages", row[7]);
			map.put("pod", row[8]);
			map.put("podDesc", row[9]);
			map.put("stuffReqQty", row[10]);

			map.put("noOfPackagesStuffed", row[11]);
			map.put("cargoWeight", row[12]);
			map.put("grossWeight", row[13]);
			map.put("cha", row[14]);
			map.put("shipingAgent", row[15]);
			map.put("shippingLine", row[16]);
			map.put("shippingLineName", row[17]);
			map.put("onAccountOf", row[18]);
			map.put("tareWeight", row[19]);
			map.put("gateInId", row[20]);
			map.put("containerHealth", row[21]);

			map.put("terminal", row[22]);
			map.put("vesselId", row[23]);
			map.put("viaNo", row[24]);
			map.put("containerNo", row[25]);
			map.put("containerSize", row[26]);
			map.put("containerType", row[27]);
			map.put("periodFrom", row[28]);
			
			map.put("customSealNo", row[29]);
			map.put("agentSealNo", row[30]);

			map.put("value", row[25]);
			map.put("label", row[25]);

			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(containerMap);
	}

	public ResponseEntity<?> getSelectedStuffingEntry(String companyId, String branchId, String stuffingReqId) {
		List<StuffRequestHub> selectedGateInEntry = stuffingRepo.searchStuffingContainerWiseSaved(companyId, branchId,
				stuffingReqId);
		return ResponseEntity.ok(selectedGateInEntry);
	}

	public List<Object[]> getStuffingEntriesToSelect(String companyId, String branchId, String searchValue,
			String profitCenterId) {
		return stuffingRepo.getStuffingEntriesToSelect(companyId, branchId, searchValue, profitCenterId);
	}

	@Transactional
	public ResponseEntity<?> saveHubStuffRequestContainer(String companyId, String branchId,
			List<StuffRequestHub> stuffRequestHub, String user) {
		Date currentDate = new Date();
		Date zeroDate = new Date(0);
		List<StuffRequestHub> listToSend = new ArrayList<>();
		Integer stuffQuantity = 0;
		String financialYear = helperMethods.getFinancialYear();
		String autoIncrementVesselId = "";
		int lineId = 1;
		try {

			StuffRequestHub earlyStuffRequest = stuffRequestHub.get(0);

			Optional<String> firstValidStuffReqId = stuffRequestHub.stream().map(StuffRequestHub::getStuffReqId) // Extract
																													// the
																													// stuffReqId
																													// from
																													// each
																													// record
					.filter(stuffReqId -> stuffReqId != null && !stuffReqId.trim().isEmpty()) // Filter non-null,
																								// non-empty strings
					.findFirst();

			String autoStuffingId = (firstValidStuffReqId.isPresent() && !firstValidStuffReqId.get().trim().isEmpty())
					? firstValidStuffReqId.get()
					: processService.autoExportStuffingId(companyId, branchId, "P00203");

//	    	Checking for a vessel entry	    	
			if (earlyStuffRequest.getVesselId() == null || earlyStuffRequest.getVesselId().isEmpty()) {
				autoIncrementVesselId = processService.autoIncrementVesselId(companyId, branchId, "P03202");

				Vessel newVessel = new Vessel(companyId, branchId, autoIncrementVesselId,
						earlyStuffRequest.getVesselName(), "P00103", earlyStuffRequest.getTerminal(), "Y", "Y",
						earlyStuffRequest.getVesselName(), user, currentDate, user, currentDate);

				Voyage newVoyage = new Voyage(companyId, branchId, earlyStuffRequest.getPod(), " ",
						autoIncrementVesselId, earlyStuffRequest.getVoyageNo(), earlyStuffRequest.getViaNo(), " ", " ",
						" ", 1, zeroDate, zeroDate, earlyStuffRequest.getGateOpenDate(), zeroDate, zeroDate, zeroDate,
						zeroDate, zeroDate, zeroDate, 0, 0, "", earlyStuffRequest.getVesselName(), "",
						earlyStuffRequest.getBerthingDate(), earlyStuffRequest.getRotationNo(),
						earlyStuffRequest.getRotationDate(), zeroDate, " ", zeroDate, " ", zeroDate,
						new BigDecimal("0"), user, currentDate, user, currentDate, user, currentDate, "A");

				vesselRepo.save(newVessel);
				voyagerepo.save(newVoyage);
			}

			for (StuffRequestHub exportStuffLoop : stuffRequestHub) {

				boolean existsBySbNoForstuffing = hubRepo.existsByIgmNoForstuffing(companyId, branchId,
						exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(), exportStuffLoop.getProfitCentreId(),
						autoStuffingId, exportStuffLoop.getStuffReqLineId(), exportStuffLoop.getContainerNo());

				if (existsBySbNoForstuffing) {
					String errorMessage = "Duplicate IGM NO found for SrNo: " + exportStuffLoop.getStuffReqLineId()
							+ " and IGM No: " + exportStuffLoop.getSbNo();
					return ResponseEntity.badRequest().body(errorMessage);
				}

				if (exportStuffLoop.getStuffReqId() == null || exportStuffLoop.getStuffReqId().isEmpty()) {
					System.out.println("Save");

					int updateExportInventoryStuffingRequest = hubRepo.updateHubInventoryStuffingRequest(
							exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(), exportStuffLoop.getVesselId(),
							exportStuffLoop.getViaNo(), autoStuffingId, exportStuffLoop.getStuffReqDate(), user,
							currentDate, exportStuffLoop.getGateInId(), companyId, branchId);

					System.out.println("updateExportInventoryStuffingRequest " + updateExportInventoryStuffingRequest);

					exportStuffLoop.setStuffReqId(autoStuffingId);
					exportStuffLoop.setFinYear(financialYear);
					exportStuffLoop.setCreatedBy(user);
					exportStuffLoop.setCreatedDate(currentDate);
					exportStuffLoop.setEditedBy(user);
					exportStuffLoop.setEditedDate(currentDate);
					exportStuffLoop.setApprovedBy(user);
					exportStuffLoop.setApprovedDate(currentDate);
					exportStuffLoop.setStatus("A");

					int maxSubSrNo = stuffingRepo.getMaxLineId(companyId, branchId, exportStuffLoop.getProfitCentreId(),
							autoStuffingId);

					exportStuffLoop.setStuffReqLineId(maxSubSrNo + 1);
					lineId++;

					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
						exportStuffLoop.setVesselId(autoIncrementVesselId);
					}

					System.out.println(exportStuffLoop);

					StuffRequestHub save = stuffingRepo.save(exportStuffLoop);

					listToSend.add(save);
					stuffQuantity += save.getNoOfPackagesStuffed().intValue();

					int updateGateInStuffRequest = hubRepo.updateHubGateInContainerStuffingRequest(companyId, branchId,
							autoStuffingId, exportStuffLoop.getGateInId());

					int updateStuffRequest = hubRepo.updateHubGateInStuffingRequest(companyId, branchId,
							exportStuffLoop.getNoOfPackagesStuffed().intValue(), exportStuffLoop.getTotalCargoWeight(),
							exportStuffLoop.getSbTransId(), exportStuffLoop.getSbNo(), exportStuffLoop.getSbLineNo());

					System.out.println("updateStuffRequest : " + updateStuffRequest);

				} else {

					StuffRequestHub dataForUpdateEntry = stuffingRepo.getDataForUpdateEntry(
							exportStuffLoop.getCompanyId(), exportStuffLoop.getBranchId(), exportStuffLoop.getSbNo(),
							exportStuffLoop.getSbTransId(), exportStuffLoop.getStuffReqId(),
							exportStuffLoop.getStuffReqLineId());

					int updateStuffRequest = stuffingRepo.updateStuffReqQtyUpdate(
							dataForUpdateEntry.getNoOfPackagesStuffed().intValue(),
							exportStuffLoop.getNoOfPackagesStuffed().intValue(),
							dataForUpdateEntry.getTotalCargoWeight(), exportStuffLoop.getTotalCargoWeight(), companyId,
							branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbLineNo(),
							exportStuffLoop.getSbTransId());

					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
						dataForUpdateEntry.setVesselId(autoIncrementVesselId);
					}

					dataForUpdateEntry.setViaNo(exportStuffLoop.getViaNo());
					dataForUpdateEntry.setRotationNo(exportStuffLoop.getRotationNo());
					dataForUpdateEntry.setRotationDate(exportStuffLoop.getRotationDate());
					dataForUpdateEntry.setVesselName(exportStuffLoop.getVesselName());
					dataForUpdateEntry.setBerthingDate(exportStuffLoop.getBerthingDate());
					dataForUpdateEntry.setGateOpenDate(exportStuffLoop.getGateOpenDate());
					dataForUpdateEntry.setNoOfPackagesStuffed(exportStuffLoop.getNoOfPackagesStuffed());
					dataForUpdateEntry.setTotalCargoWeight(exportStuffLoop.getTotalCargoWeight());
					dataForUpdateEntry.setVoyageNo(exportStuffLoop.getVoyageNo());
					dataForUpdateEntry.setShift(exportStuffLoop.getShift());
					dataForUpdateEntry.setContainerHealth(exportStuffLoop.getContainerHealth());
					dataForUpdateEntry.setPod(exportStuffLoop.getPod());
					dataForUpdateEntry.setPodDesc(exportStuffLoop.getPodDesc());
					dataForUpdateEntry.setOnAccountOf(exportStuffLoop.getOnAccountOf());
					dataForUpdateEntry.setDestination(exportStuffLoop.getDestination());

					dataForUpdateEntry.setCustomSealNo(exportStuffLoop.getCustomSealNo());
					dataForUpdateEntry.setAgentSealNo(exportStuffLoop.getAgentSealNo());
					dataForUpdateEntry.setMovementOrderDate(exportStuffLoop.getMovementOrderDate());

					dataForUpdateEntry.setPlacementDate(exportStuffLoop.getPlacementDate());
					dataForUpdateEntry.setStuffDate(exportStuffLoop.getStuffDate());
					dataForUpdateEntry.setBeginDateTime(exportStuffLoop.getBeginDateTime());
					dataForUpdateEntry.setEndDateTime(exportStuffLoop.getEndDateTime());
					dataForUpdateEntry.setMovementOrderDate(exportStuffLoop.getMovementOrderDate());
					dataForUpdateEntry.setMovementOrderDate(exportStuffLoop.getMovementOrderDate());
					dataForUpdateEntry.setMtyCountWt(exportStuffLoop.getMtyCountWt());

					System.out.println("dataForUpdateEntry \n" + dataForUpdateEntry);

					StuffRequestHub save = stuffingRepo.save(dataForUpdateEntry);
					listToSend.add(save);

				}
			}
			StuffRequestHub savedStuffRequest = listToSend.get(0);

			int updateExportInventoryStuffingRequest = hubRepo.updateHubInventoryStuffingRequestShort(
					savedStuffRequest.getVesselId(), savedStuffRequest.getViaNo(), user, currentDate,
					savedStuffRequest.getGateInId(), companyId, branchId);

			List<StuffRequestHub> result2 = stuffingRepo.searchStuffingContainerWiseSaved(companyId, branchId,
					autoStuffingId);

			return ResponseEntity.ok(result2);
		} catch (Exception e) {
			// Log the exception (consider using a logging framework like SLF4J)
			System.out.println("An error occurred while adding export stuufing entries: " + e.getMessage());
			// Return a ResponseEntity with the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the gate-in entries.");
		}
	}

	public ResponseEntity<?> searchIgmNoForStuffing(String companyId, String branchId, String searchValue,
			String profitcentreId, String stuffReqId) {

		List<Object[]> result = hubRepo.searchIgmNoForStuffing(companyId, branchId, searchValue, profitcentreId,
				stuffReqId);

		List<Map<String, Object>> containerList = result.stream().map(row -> {
			Map<String, Object> map = new HashMap<>();
			map.put("value", row[0]);
			map.put("label", row[0]);
			map.put("igmNo", row[0]);
			map.put("igmLineNo", row[1]);
			map.put("hubTransDate", row[2]);
			map.put("importerName", row[3]);
			map.put("cargoDescription", row[7]);
			map.put("noOfPackages", row[4]);
			map.put("cargoWt", row[6]);
			map.put("grossWeight", row[5]);
			map.put("hubTransId", row[8]);
			map.put("balanceQuantity", row[9]);
			map.put("balanceWeight", row[10]);
			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(containerList);

	}

	public ResponseEntity<?> searchContainerNoForHubCLP(String companyId, String branchId, String containerNo,
			String profitcentreId) {
		List<Object[]> result = hubRepo.searchContainerNoForHubCLP(companyId, branchId, containerNo, profitcentreId);

		List<Map<String, Object>> containerMap = result.stream().map(row -> {
			Map<String, Object> map = new HashMap<>();

			map.put("value", row[0] + "" + row[8]);
			map.put("label", row[0]);
			map.put("containerNo", row[0]);
			map.put("containerSize", row[1]);
			map.put("containerType", row[2]);
			map.put("sa", row[3]);
			map.put("shippingAgentName", row[4]);
			map.put("sl", row[5]);
			map.put("shippingLineName", row[6]);
			map.put("onAccountOf", row[7]);
			map.put("tareWeight", row[8]);
			map.put("inGateInDate", row[9]);
			map.put("deliveryOrderNo", row[10]);
			map.put("gateInId", row[11]);
			map.put("containerHealth", row[12]);
			map.put("onAccountName", row[13]);
			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(containerMap);
	}

	public ResponseEntity<?> getSelectedGateInEntry(String companyId, String branchId, String igmNo, String gateInId,
			String hubTransId, String profitCenterId) {
		List<GateIn> selectedGateInEntry = hubRepo.getSelectedGateInEntry(companyId, branchId, profitCenterId, gateInId,
				"HUB");
		return ResponseEntity.ok(selectedGateInEntry);
	}

	public List<Object[]> getGateInEntriesToSelect(String companyId, String branchId, String searchValue,
			String processId, String profitCenterId) {
		return hubRepo.getGateInEntriesData(companyId, branchId, searchValue, processId, "HUB", profitCenterId);
	}

	@Transactional
	public ResponseEntity<?> addHubGateIn(String companyId, String branchId, List<GateIn> gateIn, String user) {
		Date currentDate = new Date();
		List<GateIn> listToSend = new ArrayList<>();
		String financialYear = helperMethods.getFinancialYear();
		try {
			for (GateIn gateInLoop : gateIn) {
				if (gateInLoop.getGateInId() != null && !gateInLoop.getGateInId().trim().isEmpty()) {

					System.out.println("Here for : " + gateInLoop.getGateInId() + " gateInLoop.getDocRefNo() "
							+ gateInLoop.getDocRefNo());

					GateIn gateInByIds = gateInRepo.getGateInByIds(companyId, branchId, gateInLoop.getProfitcentreId(),
							gateInLoop.getGateInId(), gateInLoop.getErpDocRefNo(), gateInLoop.getDocRefNo(), "HUB");
					System.out.println("gateInByIds : \n" + gateInByIds);
					HubDocument exportSbEntry = hubRepo.getSinleHubEntry(companyId, branchId,
							gateInLoop.getProfitcentreId(), gateInLoop.getErpDocRefNo(), gateInLoop.getDocRefNo());

					if (gateInByIds != null) {

						BigDecimal previousPackages = gateInByIds.getQtyTakenIn();
						BigDecimal currentPackages = gateInLoop.getQtyTakenIn();
						BigDecimal difference = currentPackages.subtract(previousPackages);

						BigDecimal previousWeight = gateInByIds.getWeightTakenIn();
						BigDecimal currentWeight = gateInLoop.getWeightTakenIn();
						BigDecimal differenceWeight = currentWeight.subtract(previousWeight);

						if (difference.compareTo(BigDecimal.ZERO) != 0) {

							// Update entries with the difference
							exportSbEntry.setGateInPackages(exportSbEntry.getGateInPackages().add(difference));
							exportSbEntry.setGateInWt(exportSbEntry.getGateInWt().add(differenceWeight));

							hubRepo.save(exportSbEntry);
						}

						gateInByIds.setQtyTakenIn(gateInLoop.getQtyTakenIn());
						gateInByIds.setGateInPackages(gateInLoop.getQtyTakenIn());
						gateInByIds.setWeightTakenIn(gateInLoop.getWeightTakenIn());
						gateInByIds.setRemarks(gateInLoop.getRemarks());
						gateInByIds.setFob(new BigDecimal("0"));
						GateIn save = gateInRepo.save(gateInByIds);

						System.out.println("GateIn save : \n" + save);
						listToSend.add(save);

					} else {
						System.out.println("Here for :  }else\r\n" + "	            	{ " + gateInLoop.getGateInId()
								+ " gateInLoop.getDocRefNo() " + gateInLoop.getDocRefNo());

						gateInLoop.setStatus("A");
						gateInLoop.setCreatedBy(user);
						gateInLoop.setCreatedDate(currentDate);
						gateInLoop.setEditedBy(user);
						gateInLoop.setEditedDate(currentDate);
						gateInLoop.setApprovedBy(user);
						gateInLoop.setApprovedDate(currentDate);
						gateInLoop.setGateInType("HUB");

						gateInLoop.setGateInPackages(gateInLoop.getGateInPackages());
						gateInLoop.setFinYear(financialYear);

						exportSbEntry
								.setGateInPackages(exportSbEntry.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
						exportSbEntry.setGateInWt(exportSbEntry.getGateInWt().add(gateInLoop.getWeightTakenIn()));

						hubRepo.save(exportSbEntry);

						gateInLoop.setFob(new BigDecimal("0"));
						GateIn save = gateInRepo.save(gateInLoop);
						listToSend.add(save);

					}

				}

//	            First Save
				else {

					String autoGateInId = processService.autoExportGateInId(companyId, branchId, "P00202");

					HubDocument exportSbEntry = hubRepo.getSinleHubEntry(companyId, branchId,
							gateInLoop.getProfitcentreId(), gateInLoop.getErpDocRefNo(), gateInLoop.getDocRefNo());
					exportSbEntry.setGateInPackages(exportSbEntry.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
					exportSbEntry.setGateInWt(exportSbEntry.getGateInWt().add(gateInLoop.getWeightTakenIn()));

					hubRepo.save(exportSbEntry);

					gateInLoop.setGateInPackages(gateInLoop.getQtyTakenIn());
					gateInLoop.setGateInId(autoGateInId);
					gateInLoop.setStatus("A");
					gateInLoop.setGateInType("HUB");
					gateInLoop.setCreatedBy(user);
					gateInLoop.setCreatedDate(currentDate);
					gateInLoop.setEditedBy(user);
					gateInLoop.setEditedDate(currentDate);
					gateInLoop.setApprovedBy(user);
					gateInLoop.setApprovedDate(currentDate);
					gateInLoop.setFob(new BigDecimal("0"));
					gateInLoop.setFinYear(financialYear);
					GateIn save = gateInRepo.save(gateInLoop);
					listToSend.add(save);

					VehicleTrack v = new VehicleTrack();
					v.setCompanyId(companyId);
					v.setBranchId(branchId);
					v.setFinYear(gateInLoop.getFinYear());
					v.setVehicleNo(gateInLoop.getVehicleNo());
					v.setProfitcentreId(gateInLoop.getProfitcentreId());
					v.setSrNo(1);
					v.setTransporterStatus(gateInLoop.getTransporterStatus().charAt(0));
					v.setTransporterName(gateInLoop.getTransporterName());
					v.setTransporter(gateInLoop.getTransporter());
					v.setDriverName(gateInLoop.getDriverName());
					v.setVehicleStatus('L');
					v.setGateInId(gateInLoop.getGateInId());
					v.setGateInDate(new Date());
					v.setGateNoIn("Gate01");
					v.setShiftIn(gateInLoop.getShift());
					v.setStatus('A');
					v.setCreatedBy(user);
					v.setCreatedDate(new Date());
					v.setApprovedBy(user);
					v.setApprovedDate(new Date());

					vehicleRepo.save(v);

				}
			}

			GateIn firstGateIn = listToSend.get(0);

			List<GateIn> selectedGateInEntry = hubRepo.getSelectedGateInEntry(companyId, branchId,
					firstGateIn.getProfitcentreId(), firstGateIn.getGateInId(), "HUB");

			return ResponseEntity.ok(selectedGateInEntry);
		} catch (Exception e) {
			// Log the exception (consider using a logging framework like SLF4J)
			System.out.println("An error occurred while adding export gate-in entries: " + e.getMessage());

			// Return a ResponseEntity with the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the gate-in entries.");
		}
	}

	public ResponseEntity<?> getIgmCargoGateIn(String companyId, String branchId, String hubTransId, String gateInId,
			String igmNo, String profitCentreId) {
		List<Object[]> igmCargoGateIn = hubRepo.getIgmCargoGateIn(companyId, branchId, hubTransId, gateInId, igmNo,
				profitCentreId);
		return ResponseEntity.ok(igmCargoGateIn);
	}

	private List<Map<String, String>> convertToValueLabelList(List<Object[]> data) {
		return data.stream().map(obj -> {
			Map<String, String> map = new HashMap<>();
			map.put("value", (String) obj[1]);
			map.put("label", (String) obj[0]);
			map.put("igmLine", (String) obj[2]);
			return map;
		}).collect(Collectors.toList());
	}

	public ResponseEntity<?> searchIgmNosToGateIn(String companyId, String branchId, String profitCentreId,
			String searchValue, String gateInId) {
		List<Object[]> igmNos = hubRepo.searchIgmNosToGateIn(companyId, branchId, profitCentreId, searchValue,
				gateInId);
		List<Map<String, String>> igmNoList = convertToValueLabelList(igmNos);
		return ResponseEntity.ok(igmNoList);
	}

	@Transactional
	public ResponseEntity<?> addHubDocumentEntry(String companyId, String branchId, HubDocument hubEntry, String user) {
		Date currentDate = new Date();
		String financialYear = helperMethods.getFinancialYear();
		try {

			boolean existsBySbNo = hubRepo.existsByIGMAndLineNo(companyId, branchId, hubEntry.getProfitCentreId(),
					hubEntry.getIgmNo(), hubEntry.getIgmLineNo(), hubEntry.getHubTransId());

			if (existsBySbNo) {
				String errorMessage = "Duplicate IGM  " + hubEntry.getIgmNo() + " and ITEM No: "
						+ hubEntry.getIgmLineNo();
				return ResponseEntity.badRequest().body(errorMessage);
			}

			if (hubEntry.getHubTransId() == null || hubEntry.getHubTransId().trim().equals("")) {
				hubEntry.setCompanyId(companyId);
				hubEntry.setBranchId(branchId);
				hubEntry.setCreatedBy(user);
				hubEntry.setFinYear(financialYear);
				hubEntry.setCreatedDate(currentDate);
				hubEntry.setApprovedBy(user);
				hubEntry.setApprovedDate(currentDate);
				hubEntry.setStatus('A');

				String transId = processService.autoSBTransId(companyId, branchId, "P00201");
				hubEntry.setHubTransId(transId);

				hubRepo.save(hubEntry);
			} else {

				// Call the update query from the repository
				int updatedRows = hubRepo.updateHubDocumentEntry(companyId, branchId, hubEntry.getProfitCentreId(),
						hubEntry.getHubTransId(), user, hubEntry.getHubTransDate(), hubEntry.getIgmNo(),
						hubEntry.getIgmLineNo(), hubEntry.getNoOfPackages(), hubEntry.getGrossWt(),
						hubEntry.getCargoWt(), hubEntry.getCargoDescription(), hubEntry.getCargoType(),
						hubEntry.getArea(), hubEntry.getImporterName(), hubEntry.getImporterAddress());

				if (updatedRows == 0) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("No record found to update for given HubTransId.");
				}

			}

			HubDocument selectedHubEntry = hubRepo.getSelectedHubEntry(companyId, branchId,
					hubEntry.getProfitCentreId(), hubEntry.getHubTransId(), hubEntry.getIgmNo());

			return ResponseEntity.ok(selectedHubEntry);

		} catch (Exception e) {
			System.out.println("An error occurred while adding Hub Document entry: " + e.getMessage());
			// Return a ResponseEntity with the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the Hub Document Entry");
		}
	}

	public List<HubDocument> getHubEntriesToSelect(String companyId, String branchId, String searchValue,
			String profitCenterId) {
		return hubRepo.getHubEntriesToSelect(companyId, branchId, profitCenterId, searchValue);
	}

	public ResponseEntity<?> getSelectedHubEntry(String companyId, String branchId, String transId, String igmNo,
			String profitCenterId) {
		HubDocument selectedHubEntry = hubRepo.getSelectedHubEntry(companyId, branchId, profitCenterId, transId, igmNo);
		return ResponseEntity.ok(selectedHubEntry);
	}

}
