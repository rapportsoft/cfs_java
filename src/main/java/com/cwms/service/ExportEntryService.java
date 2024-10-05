package com.cwms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ExportCarting;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.GateIn;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.GateInRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExportEntryService {
	
	@Autowired
	private ObjectMapper objectMapper;	
	
	@Autowired
	private HelperMethods helperMethods;	
	
	@Autowired
	private ExportEntryRepo entryRepo;	
	
	@Autowired
	private ProcessNextIdService processService;
	
	@Autowired
	private ExportSbCargoEntryRepo entryCargoRepo;
	
	@Autowired
	private GateInRepository gateInRepo;	
	
	@Autowired
	private ExportCartingRepo cartingRepo;
	
	
	private List<Map<String, String>> convertToValueLabelList(List<String> data) {
	    return data.stream().map(obj -> {
	        Map<String, String> map = new HashMap<>();
	        map.put("value", obj);
	        map.put("label", obj);
	        return map;
	    }).collect(Collectors.toList());
	}
	
	
	
	public ResponseEntity<?> searchExportMain(String companyId, String branchId, String sbNo, String vehicleNo, String containerNo, String bookingNo, String pod)
	{		
		
		System.out.println(companyId +" companyId "+ branchId + " branchId "+ sbNo + " sbNo "+ vehicleNo + " containerNo "+ bookingNo + " bookingNo "+ pod + " pod" );
		
		List<String> allowedList = new ArrayList<>();
		Map<String, Object> dataMap = new HashMap<>();
		Map<String,Object> mainData = new HashMap<>();
		
		Pageable pageable = PageRequest.of(0, 100);
		List<GateIn> gateInList = gateInRepo.searchLatestExportMain(companyId, branchId, sbNo, vehicleNo, "EXP", pod, pageable);

		if (gateInList.isEmpty()) {		
			System.out.println("1");
			List<ExportSbEntry> existinSbEntry = entryRepo.searchLatestExportMain(companyId, branchId, pod, sbNo, pageable);
			System.out.println("2");
			if(existinSbEntry.isEmpty())
			{
				System.out.println("3");
				return ResponseEntity.badRequest().body("No Data Found");
				
			}
			else
			{
				ExportSbEntry exportSbEntry = existinSbEntry.get(0);
				
				dataMap.put("sbNo", exportSbEntry.getSbNo());
		        dataMap.put("sbTransId", exportSbEntry.getSbTransId());
		        dataMap.put("hsbSbTransId", exportSbEntry.gethSbTransId());
		        dataMap.put("gateInId", "");
		        dataMap.put("profitCenterId", exportSbEntry.getProfitcentreId());
		        
		        allowedList.add("P00216");
		        
				mainData.put("allowedList", allowedList);
				mainData.put("data", dataMap);
				
				return ResponseEntity.ok(mainData);
			}			
		}

		GateIn gateIn = gateInList.get(0);
		
		allowedList.add("P00216");
//		allowedList.add("P00217");
		
		boolean containsEmptyGateInId = gateInList.stream().anyMatch(c -> c.getGateInId() == null || c.getGateInId().isEmpty());
		boolean containsEmptyCartingInId = gateInList.stream().anyMatch(c -> c.getCartingTransId() == null || c.getCartingTransId().isEmpty());

		Optional<GateIn> firstValidCartingIn = gateInList.stream()
			    .filter(c -> c.getCartingTransId() != null && !c.getCartingTransId().isEmpty())
			    .findFirst();
		
		
		System.out.println("containsEmptyGateInId : "+containsEmptyGateInId);
		System.out.println("firstValidCartingId.get() : \n"+ firstValidCartingIn != null ? firstValidCartingIn.get() : "");
		
		System.out.println("EP1 : ");
		ExportSbEntry getsbNoAndPrimary = entryRepo.getsbNoAndPrimary(companyId, branchId, gateIn.getDocRefNo(), gateIn.getErpDocRefNo());
		System.out.println("EP2 : ");
		if (!containsEmptyGateInId) {
			
			if (!containsEmptyGateInId) {
				
				if (firstValidCartingIn != null) {
					System.out.println("EP3 : ");
					GateIn newGateIn = firstValidCartingIn.get();		
					System.out.println("EP4 : ");
					ExportCarting cartingsForMainSearch = cartingRepo.cartingsForMainSearch(companyId, branchId,newGateIn.getDocRefNo(), newGateIn.getCartingTransId());
					System.out.println("EP5 : ");
					allowedList.add("P00218");
					dataMap.put("cartingTransId", newGateIn.getCartingTransId());	
					dataMap.put("cartingLineId", cartingsForMainSearch.getCartingLineId());	
					dataMap.put("cartingprofitCentre", cartingsForMainSearch.getProfitcentreId());
					dataMap.put("cartingSbNo", cartingsForMainSearch.getSbNo());
				}
				
			}
			
			
				allowedList.add("P00217");
				dataMap.put("sbNo", getsbNoAndPrimary.getSbNo());
		        dataMap.put("sbTransId", getsbNoAndPrimary.getSbTransId());
		        dataMap.put("gateInId", gateIn.getGateInId());
		        dataMap.put("profitCenterId", gateIn.getProfitcentreId());
		        dataMap.put("hsbSbTransId", getsbNoAndPrimary.gethSbTransId());
//		        System.out.println("profitCenterId"+ gateIn.getProfitcentreId());
		        mainData.put("allowedList", allowedList);
				mainData.put("data", dataMap);
				return ResponseEntity.ok(mainData);
		}
		
		allowedList.add("P00217");
		dataMap.put("sbNo", getsbNoAndPrimary.getSbNo());
        dataMap.put("sbTransId", getsbNoAndPrimary.getSbTransId());
        dataMap.put("gateInId", gateIn.getGateInId());
        dataMap.put("profitCenterId", getsbNoAndPrimary.getProfitcentreId());
        dataMap.put("hsbSbTransId", getsbNoAndPrimary.gethSbTransId());
        dataMap.put("profitCenterId", gateIn.getProfitcentreId());
		
        System.out.println("profitCenterId"+ gateIn.getProfitcentreId());

        
        mainData.put("allowedList", allowedList);
		mainData.put("data", dataMap);
		return ResponseEntity.ok(mainData);
	}
	
	public ResponseEntity<?> searchSbNosToGateIn(String companyId, String branchId, String searchValue)
	{			
		List<String> sbNos = entryCargoRepo.searchSbNosToGateIn(companyId, branchId, searchValue);
		List<Map<String, String>> sbNoList = convertToValueLabelList(sbNos);		
		return ResponseEntity.ok(sbNoList);
	}
	
	
	public ExportSbEntry saveExportSbEntry(ExportSbEntry exportSbEntry)
	{			
		return entryRepo.save(exportSbEntry);	
	}
	
	
	public ExportSbCargoEntry saveExportSbCargoEntry(ExportSbCargoEntry exportSbCargoEntry)
	{			
		return entryCargoRepo.save(exportSbCargoEntry);
	}
	
	public ResponseEntity<?> getSbCargoGateIn(String companyId, String branchId, String sbNo, String gateInId)
	{			
		List<Object[]> sbCargoGateIn = entryCargoRepo.getSbCargoGateIn(companyId, branchId, sbNo, gateInId);		
		return ResponseEntity.ok(sbCargoGateIn);
	}
	
		
	
	public ExportSbEntry getExportSbEntry(String companyId, String branchId, String sbNo, String sbTransId)
	{			
		return entryRepo.getExportSbEntryBySbNoAndSbTransId(companyId, branchId, sbNo, sbTransId);		
	}
	
	
	public ExportSbCargoEntry getExportSbCargoEntry(String companyId, String branchId, String sbNo, String sbTransId)
	{			
		return entryCargoRepo.getExportSbEntryBySbNoAndSbTransId(companyId, branchId, sbNo, sbTransId);		
	}
	
	
	public List<Object[]> getSbEntriesToSelect(String companyId, String branchId, String searchValue)
	{
				
		return entryCargoRepo.getSbEntriesData(companyId, branchId, searchValue);
	}
	
	
	public ResponseEntity<?> getSelectedSbEntry(String companyId, String branchId, String sbTransId, String hsbTransId, String sbNo,String profitCenterId)
	{
		
		ExportSbEntry sbEntrySaved = entryRepo.getExportSbEntryBySbTransIdAndSbNo(companyId, branchId, profitCenterId, sbTransId, hsbTransId, sbNo);		
		List<ExportSbCargoEntry> cargoEntries = entryCargoRepo.getExportSbCargoEntryBySbTransIdAndSbNo(companyId, branchId, profitCenterId, hsbTransId);
		
		Map<String, Object> response = new HashMap<>();
		response.put("cargoEntries", cargoEntries);
		response.put("sbEntrySaved", sbEntrySaved);

		// Return the map wrapped in a ResponseEntity
		return ResponseEntity.ok(response);

	
	}
	
	
	
	public boolean checkDuplicateSbNo(String companyId, String branchId, String finYear, String profitCenterId, String sbTransId, String sbNo)
	{
		
		String updatedFinYear = (finYear != null && !finYear.trim().isEmpty()) 
                ? finYear 
                : helperMethods.getFinancialYear();
		
		return entryCargoRepo.existsBySbNo(companyId, branchId, updatedFinYear, profitCenterId,sbTransId, sbNo);
	}
	
	
	@Transactional
	public ResponseEntity<?> addExportSbEntry(String companyId, String branchId, Map<String, Object> requestData, String User)
	{
		Date currentDate = new Date();
		
		
		List<ExportSbCargoEntry> sbCargoEntry = objectMapper.convertValue(requestData.get("exportSbCargoEntry"),
				new TypeReference<List<ExportSbCargoEntry>>() {
				});
				
		
		ExportSbEntry sbEntry = objectMapper.convertValue(requestData.get("exportSbEntry"), ExportSbEntry.class);
			
		
//		String autoSBTransId = processService.autoSBTransId(companyId, branchId, "P00101");	
		// Check if any sbTransId is null, empty, or contains only whitespace
		String autoSBTransId = "";
		
		if(sbEntry.getSbNo() != null && !sbEntry.getSbNo().trim().isEmpty())
		{
			autoSBTransId = (sbEntry.getSbTransId() != null && !sbEntry.getSbTransId().trim().isEmpty()) 
	                ? sbEntry.gethSbTransId() 
	                : processService.autoSBTransId(companyId, branchId, "P00101"); 
		}
		
//		String autoSBTransId = (sbEntry.getSbNo() != null && !sbEntry.getSbNo().trim().isEmpty() &&
//                sbEntry.getSbTransId() != null && !sbEntry.getSbTransId().trim().isEmpty())
//				? sbEntry.gethSbTransId()
//				: processService.autoSBTransId(companyId, branchId, "P00101");


		
		String existingTransId = (sbEntry.gethSbTransId() != null && !sbEntry.gethSbTransId().trim().isEmpty()) 
                ? sbEntry.gethSbTransId() 
                : autoSBTransId;
		
		String existingSBTransId = (sbEntry.getSbTransId() != null && !sbEntry.getSbTransId().trim().isEmpty()) 
                ? sbEntry.getSbTransId() 
                : autoSBTransId;
		
		sbEntry.setSbTransId(existingSBTransId);
		sbEntry.sethSbTransId(existingTransId);
		
		sbEntry.setFinYear(helperMethods.getFinancialYear());
		
		
		System.out.println("helperMethods.getFinancialYear() \n"+helperMethods.getFinancialYear());
		if(!sbEntry.getStatus().equals("A"))
		{
			sbEntry.setStatus("A");
			sbEntry.setCreatedBy(User);
			sbEntry.setCreatedDate(currentDate);
			sbEntry.setApprovedBy(User);
			sbEntry.setApprovedDate(currentDate);
		}
		
		
		
		sbEntry.setEditedBy(User);
		sbEntry.setEditedDate(currentDate);
		
		
		
		System.out.println(sbEntry);
		for(ExportSbCargoEntry cargoEntry : sbCargoEntry)
		{
			boolean existsBySbNo = entryCargoRepo.existsBySbNo(cargoEntry.getCompanyId(), cargoEntry.getBranchId(), sbEntry.getFinYear(), cargoEntry.getProfitcentreId(),cargoEntry.getSbTransId(),cargoEntry.getSbNo());
			
			
			 if (existsBySbNo) {
		            String errorMessage = "Duplicate SB No found for SrNo: " + cargoEntry.getSrno() + " and SB No: " + cargoEntry.getSbNo();
		            return ResponseEntity.badRequest().body(errorMessage);
		        }
			
			ExportSbEntry sbEntryNew = entryRepo.getExportSbEntryBySbTransId(cargoEntry.getCompanyId(), cargoEntry.getBranchId(), cargoEntry.getProfitcentreId(), cargoEntry.getSbTransId());
//			if (sbEntryNew != null && !sbEntryNew.getSbNo().equals(cargoEntry.getSbNo())) {			
//			    int updateChangeSbNo = entryRepo.updateChangeSbNo(companyId, branchId, sbEntryNew.getSbNo(), sbEntryNew.getSbTransId(), cargoEntry.getSbNo(), cargoEntry.getSbDate());
//			    int updateChangeSbCargoNo = entryCargoRepo.updateChangeSbCargoNo(companyId, branchId, sbEntryNew.getSbNo(), sbEntryNew.getSbTransId(), cargoEntry.getSbNo(), cargoEntry.getSbDate());
//			    System.out.println("updateChangeSbNo: " + updateChangeSbNo + " updateChangeSbCargoNo "+updateChangeSbCargoNo);			    			
//			}
			
			if (sbEntryNew != null && 
				    (!sbEntryNew.getSbNo().equals(cargoEntry.getSbNo()) || 
				     sbEntryNew.getTotalPackages().compareTo(cargoEntry.getNoOfPackages()) != 0 || 
				     sbEntryNew.getTotalGrossWeight().compareTo(cargoEntry.getGrossWeight()) != 0)) {
				    
				    int updateChangeSbNo = entryRepo.updateChangeSbNo(companyId, branchId, sbEntryNew.getSbNo(), sbEntryNew.getSbTransId(), cargoEntry.getSbNo(), cargoEntry.getSbDate(),cargoEntry.getNoOfPackages(),cargoEntry.getGrossWeight());
				    int updateChangeSbCargoNo = entryCargoRepo.updateChangeSbCargoNo(companyId, branchId, sbEntryNew.getSbNo(), sbEntryNew.getSbTransId(), cargoEntry.getSbNo(), cargoEntry.getSbDate());
				    System.out.println("updateChangeSbNo: " + updateChangeSbNo + " updateChangeSbCargoNo "+updateChangeSbCargoNo);			    			
				}

			
			
			
			
			
			System.out.println("Before \n "+cargoEntry);
			String existingCargoTransId = (cargoEntry.getSbTransId() != null && !cargoEntry.getSbTransId().trim().isEmpty()) 
	                ? cargoEntry.getSbTransId() 
	                : autoSBTransId;
			
			System.out.println("Applied existingCargoTransId : "+existingCargoTransId);
			
			cargoEntry.setSbTransId(existingCargoTransId);
			
			
			cargoEntry.sethSbTransId(existingTransId);
			
			
			if(!cargoEntry.getStatus().equals("A"))
			{
				cargoEntry.setFinYear(helperMethods.getFinancialYear());				
				cargoEntry.setCreatedBy(User);
				cargoEntry.setCreatedDate(currentDate);	
				cargoEntry.setApprovedBy(User);
				cargoEntry.setApprovedDate(currentDate);
				cargoEntry.setStatus("A");
			}
			
				
			
			System.out.println("After \n"+cargoEntry);
			
		}
		List<ExportSbCargoEntry> cargoEntries = entryCargoRepo.saveAll(sbCargoEntry);
		ExportSbEntry sbEntrySaved = (sbEntry.getSbNo() != null && !sbEntry.getSbNo().isEmpty()) ? entryRepo.save(sbEntry) : sbEntry;
		
		Map<String, Object> response = new HashMap<>();
		response.put("cargoEntries", cargoEntries);
		response.put("sbEntrySaved", sbEntrySaved);

		// Return the map wrapped in a ResponseEntity
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	

}