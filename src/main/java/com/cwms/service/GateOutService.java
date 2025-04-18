package com.cwms.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.GateOut;
import com.cwms.entities.GateOutSpl;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.CfbondGatePassRepository;
import com.cwms.repository.GateOutRepo;
import com.cwms.repository.GateOutSplRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GateOutService {

	@Autowired
	public GateOutRepo gateOutRepo;
	
	@Autowired
	public ProcessNextIdRepository processNextIdRepository; 
	
	@Autowired
	public CfbondGatePassRepository cfbondGatePassRepository;
	
	@Autowired
    private VehicleTrackRepository vehicleTrackRepository;
	
	@Autowired
	private GateOutSplRepo splRepo;
	
	@Autowired
	private HelperMethods helper;
	
	@Autowired
	private ProcessNextIdService processService;
	
	
	

	public ResponseEntity<?> getSelectedSplOutEntry(String companyId, String branchId, String profitCenterId, String gateOutId)
	{		
		List<GateOutSpl> getSelectedSplOutEntry = splRepo.getSelectedSplOutEntry(companyId, branchId, gateOutId, profitCenterId);
		return ResponseEntity.ok(getSelectedSplOutEntry);	
	}
	
	
	public List<Object[]> getSplOutToSelect(String companyId, String branchId, String searchValue)
	{				
		return splRepo.getSplOutToSelect(companyId, branchId, searchValue);
	}
	
	
	
	

	@Transactional
	public ResponseEntity<?> saveGateOutSPLImport(String companyId, String branchId, String user,
			List<GateOutSpl> gateOutList) {
		Date currentDate = new Date();
		List<GateOutSpl> listToSend = new ArrayList<>();
		try {


			Optional<String> firstValidStuffReqId = gateOutList.stream().map(GateOutSpl::getGateOutId)
					.filter(stuffReqId -> stuffReqId != null && !stuffReqId.trim().isEmpty()).findFirst();

			String autoGateOutId = (firstValidStuffReqId.isPresent() && !firstValidStuffReqId.get().trim().isEmpty())
					? firstValidStuffReqId.get()
					: processService.autoExportStuffingId(companyId, branchId, "P00219");
			
			GateOutSpl gateOutSpl = gateOutList.get(0);

			for (GateOutSpl out : gateOutList) {

				if (out.getGateOutId() == null || out.getGateOutId().isEmpty()) {

					out.setGateOutId(autoGateOutId);
					out.setCompanyId(companyId);
					out.setBranchId(branchId);
					out.setCreatedBy(user);
					out.setCreatedDate(currentDate);
					
					out.setFinYear(helper.getFinancialYear());

					out.setEditedBy(user);
					out.setEditedDate(currentDate);

					out.setStatus("A");
					out.setApprovedBy(user);
					out.setApprovedDate(currentDate);
					splRepo.save(out);
					
					int updateSplOutGatePass = splRepo.updateSplOutGatePass(companyId, branchId, out.getContainerNo(),
							out.getGatePassNo());
					int updateSplOutVehicleTrack = splRepo.updateSplOutVehicleTrack(companyId, branchId, out.getVehicleNo(),
							out.getGateOutId(), out.getGateOutDate());

					System.out.println("updateSplOutGatePass : " + updateSplOutGatePass + " \nupdateSplOutVehicleTrack : "
							+ updateSplOutVehicleTrack);
					
					

				} else {
					
					
					int updateSplOutGateOut = splRepo.updateSplOutGateOut(companyId, branchId, out.getContainerNo(), out.getGateOutId(), 
						        out.getShift(), out.getGateNo(), out.getDestination(), out.getTransporter(), out.getTransporterName(), 
						        out.getOutBookingType(), out.getMovementBy(), out.getShipperName(),	out.getComments()
						    );
					
					System.out.println("updateSplOutGateOut : " + updateSplOutGateOut );

				}

			
				
				System.out.println(" out \n" + out);

			}
			List<GateOutSpl> getSelectedSplOutEntry = splRepo.getSelectedSplOutEntry(companyId, branchId, autoGateOutId, gateOutSpl.getProfitcentreId());

			return ResponseEntity.ok(getSelectedSplOutEntry);

		} catch (Exception e) {
			System.out.println(
					"An error occurred while addingImport GateOut Special Gate Out entries: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while addingImport GateOut Special Gate Out entries.");
		}
	}

	public ResponseEntity<?> searchGatePassNoForSPLImport(String companyId, String branchId, String searchValue,
			String profitcentreId) {

		List<String> result = splRepo.searchGatePassNoForSPLImport(companyId, branchId, searchValue, profitcentreId);

		List<Map<String, Object>> containerList = result.stream().map(row -> {
			Map<String, Object> map = new HashMap<>();
			map.put("value", row);
			map.put("label", row);
			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(containerList);

	}

	public ResponseEntity<?> selectGatePassNoForSPLImport(String companyId, String branchId, String gatePassNo,
			String profitcentreId) {

		return ResponseEntity.ok(splRepo.selectGatePassNoForSPLImport(companyId, branchId, gatePassNo, profitcentreId));

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ResponseEntity<?> saveDataOfExbondGateOut(String companyId, String branchId, String user,
			String flag, Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		System.out.println("requestBody_________________________" + requestBody);

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		GateOut gatePass = object.convertValue(requestBody.get("gateOut"), GateOut.class);

		Object nocDtlObj = requestBody.get("dtl");
		List<CFBondGatePass> gateOutDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			// If nocDtl is a list, deserialize it directly
			gateOutDtlList = object.convertValue(nocDtlObj, new TypeReference<List<CFBondGatePass>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				CFBondGatePass gateOutDtl = object.convertValue(entry.getValue(), CFBondGatePass.class);
				gateOutDtlList.add(gateOutDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		System.out.println("gateOutDtlList________________" + gateOutDtlList);

		System.out.println("flag________________" + flag);

		GateOut saved = null;

		List<GateOut> savedDtl = new ArrayList<>();
		
		
		if (gatePass != null) {
			if ("add".equals(flag)) {

				if (gateOutDtlList != null) {
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P03211", "2247");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectExBondingId = String.format("GOBX%06d", nextNumericNextID1);

					String sr = "1";

					for (CFBondGatePass item : gateOutDtlList) 
					{
						GateOut gateOutDtl = new GateOut();

						gateOutDtl.setCompanyId(companyId);
						gateOutDtl.setBranchId(branchId);
						gateOutDtl.setFinYear("2025");
						gateOutDtl.setProcessId("P00500");
						gateOutDtl.setCreatedBy(user);
						gateOutDtl.setCreatedDate(new Date());
						gateOutDtl.setApprovedBy(user);
						gateOutDtl.setApprovedDate(new Date());
						gateOutDtl.setStatus('A');
						gateOutDtl.setGateOutId(nectExBondingId);
						gateOutDtl.setDocRefNo(item.getNocNo());
						gateOutDtl.setErpDocRefNo(item.getNocTransId()!=null ? item.getNocTransId() : "");
						gateOutDtl.setNatureOfCargo(item.getApprovedBy());
						gateOutDtl.setQtyTakenOut(item.getQtyTakenOut());
						gateOutDtl.setGrossWt(item.getGrossWt());
						gateOutDtl.setActualNoOfPackages(item.getNoOfPackage());
						gateOutDtl.setIgmLineNo(item.getIgmLineNo());
						gateOutDtl.setCommodityDescription(item.getCommodityDescription());
						gateOutDtl.setDeliveryOrderDate(gatePass.getDeliveryOrderDate());
						gateOutDtl.setDeliveryOrderNo(gatePass.getDeliveryOrderNo());
						gateOutDtl.setDoValidityDate(gatePass.getDoValidityDate());
						gateOutDtl.setGateNoIn(gatePass.getGateNoIn());
						gateOutDtl.setShift(gatePass.getShift());
						gateOutDtl.setGateOutDate(gatePass.getGateOutDate());
						gateOutDtl.setVehicleNo(gatePass.getVehicleNo());
						gateOutDtl.setExBondBeNo(gatePass.getExBondBeNo());
						gateOutDtl.setCommodity(item.getCommodity());
						gateOutDtl.setGatePassNo(gatePass.getGatePassNo());
						gateOutDtl.setGatePassDate(gatePass.getGatePassDate());
						gateOutDtl.setShift(gatePass.getShift());
						gateOutDtl.setProfitcentreId(gatePass.getProfitcentreId());
						gateOutDtl.setVehicalGateIn(gatePass.getVehicalGateIn());
						
						gateOutDtl.setCha(gatePass.getCha());
						gateOutDtl.setDriverContactNo(gatePass.getDriverContactNo());
						gateOutDtl.setDriverName(gatePass.getDriverName());
						gateOutDtl.setTransporterStatus(gatePass.getTransporterStatus());
						gateOutDtl.setImporterName(gatePass.getImporterName());
						
						gateOutDtl.setLicenceNo(gatePass.getLicenceNo());
						gateOutDtl.setTransporterName(gatePass.getTransporterName());
						gateOutDtl.setComments(gatePass.getComments());
						gateOutDtl.setProcessId(gatePass.getProcessId());
					
				
						gateOutDtl.setTransporterStatus(gatePass.getTransporterStatus());
						gateOutDtl.setCha(gatePass.getCha());

						gateOutDtl.setTransType(gatePass.getTransType()); 
						gateOutDtl.setSrNo(sr);

						saved = gateOutRepo.save(gateOutDtl);

						
						System.out.println("Saved__________________________________________"+saved);
						
						savedDtl.add(saved);

						int srInt = Integer.parseInt(sr);  // Convert to int
					    srInt++;  // Increment the int
					    sr = String.valueOf(srInt);  // Convert back to String
					    
					    if (saved!=null)
					    {
					    	int updateGatePassAfterGateOut = cfbondGatePassRepository.updateGatePassAfterGateOut(
									saved.getGateOutId(), companyId, branchId, saved.getGatePassNo(),
									saved.getCommodity());

							System.out.println(
									"Update row count after exbond details is" + updateGatePassAfterGateOut);
							
							
							int updateVehicleTrackAfterGateOut = vehicleTrackRepository.updateDataAfterBondGateOut(
									saved.getGateOutId(),saved.getGateOutDate(),user, companyId, branchId,
									saved.getVehicalGateIn());

							System.out.println(
									"Update row count after exbond details updateVehicleTrackAfterGateOut" + updateVehicleTrackAfterGateOut);
							
					    }
					
					}
//					if (savedDtl.size() > 0)
//
//					{
//						CFBondGatePass firstSavedObject = savedDtl.get(0);
//						
//						BigDecimal totalOut =BigDecimal.ZERO;
//						for (CFBondGatePass savedObject : savedDtl) {
//
//							totalOut=totalOut.add(savedObject.getQtyTakenOut());
//						}
//
//						int updateExbondCrg = cfExBondCrgRepository.updateCfexbondAfterGatePass(totalOut, companyId,
//								branchId, firstSavedObject.getExBondingId(), firstSavedObject.getExBondBeNo());
//						
//						System.out.println("Update row count after gate pass in exbond is____________"+updateExbondCrg);
//					}
					

					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03211", nectExBondingId, "2247");
				}

			}

			else {
				System.out.println("In Edit loop ");
				if (gateOutDtlList != null) 
				{

					for (CFBondGatePass item : gateOutDtlList) 
					{
						GateOut gateOutDtl = gateOutRepo.getExistingDataOfGateOut(companyId,
								branchId, gatePass.getGateOutId(), item.getCommodity(),String.valueOf(item.getSrNo()));

						if(gateOutDtl!=null)
						{
							
						
					    gateOutDtl.setStatus('A');

						gateOutDtl.setEditedBy(user);
						gateOutDtl.setEditedDate(new Date());
						gateOutDtl.setProcessId("P00500");
						gateOutDtl.setDeliveryOrderDate(gatePass.getDeliveryOrderDate());
						gateOutDtl.setDeliveryOrderNo(gatePass.getDeliveryOrderNo());
						gateOutDtl.setDoValidityDate(gatePass.getDoValidityDate());
						gateOutDtl.setGateNoIn(gatePass.getGateNoIn());
						gateOutDtl.setShift(gatePass.getShift());
						gateOutDtl.setGateOutDate(gatePass.getGateOutDate());
						gateOutDtl.setVehicleNo(gatePass.getVehicleNo());
						gateOutDtl.setExBondBeNo(gatePass.getExBondBeNo());
						gateOutDtl.setGatePassNo(gatePass.getGatePassNo());
						gateOutDtl.setGatePassDate(gatePass.getGatePassDate());
						gateOutDtl.setShift(gatePass.getShift());
						gateOutDtl.setProfitcentreId(gatePass.getProfitcentreId());
						gateOutDtl.setVehicalGateIn(gatePass.getVehicalGateIn());
						gateOutDtl.setCha(gatePass.getCha());
						gateOutDtl.setProcessId(gatePass.getProcessId());
						gateOutDtl.setDriverName(gatePass.getDriverName());
						gateOutDtl.setDriverContactNo(gatePass.getDriverContactNo());
						gateOutDtl.setTransporterStatus(gatePass.getTransporterStatus());
						gateOutDtl.setImporterName(gatePass.getImporterName());
						
						gateOutDtl.setLicenceNo(gatePass.getLicenceNo());
						gateOutDtl.setTransporterName(gatePass.getTransporterName());
						gateOutDtl.setComments(gatePass.getComments());

					
				
						gateOutDtl.setTransporterStatus(gatePass.getTransporterStatus());
						gateOutDtl.setCha(gatePass.getCha());

						gateOutDtl.setTransType(gatePass.getTransType()); 
						
						saved = gateOutRepo.save(gateOutDtl);
						
						
						savedDtl.add(saved);
						
						   if (saved!=null)
						    {
						    	int updateGatePassAfterGateOut = cfbondGatePassRepository.updateGatePassAfterGateOut(
										saved.getGateOutId(), companyId, branchId, saved.getGatePassNo(),
										saved.getCommodity());

								System.out.println(
										"Update row count after exbond details is" + updateGatePassAfterGateOut);
								

								int updateVehicleTrackAfterGateOut = vehicleTrackRepository.updateDataAfterBondGateOut(
										saved.getGateOutId(),saved.getGateOutDate(),user, companyId, branchId,
										saved.getVehicalGateIn());

								System.out.println(
										"Update row count after exbond details updateVehicleTrackAfterGateOut" + updateVehicleTrackAfterGateOut);
								
						    }
						
						}

					}
				
					
					if (savedDtl.size() > 0)

					{
//						CFBondGatePass firstSavedObject = savedDtl.get(0);
//						
//						BigDecimal totalOut =BigDecimal.ZERO;
//						for (CFBondGatePass savedObject : savedDtl) {
//
//							totalOut=totalOut.add(savedObject.getQtyTakenOut());
//							
//							int updateCfExbondcrgDtlAfterGatePass = cfExBondCrgDtlRepository.updateCfexbondDtlAfterGatePass(
//									savedObject.getQtyTakenOut(), companyId, branchId, savedObject.getExBondingId(),
//									savedObject.getExBondBeNo(), savedObject.getCommodity());
//							
//							System.out.println("Update row count after gate pass in exbond details is in edit ok ____________"+updateCfExbondcrgDtlAfterGatePass);
//							
//						}
//
//						int updateExbondCrg = cfExBondCrgRepository.updateCfexbondAfterGatePass(totalOut, companyId,
//								branchId, firstSavedObject.getExBondingId(), firstSavedObject.getExBondBeNo());
//						
//						System.out.println("Update row count after gate pass in exbond is in edit ok ____________"+updateExbondCrg);
					}

				}

			}
		}

		System.out.println("gatePass_______________________" + saved);

		System.out.println("gateOutDtl_______________________" + gateOutDtlList);

		return new ResponseEntity<>(savedDtl, HttpStatus.OK);
	}
	
	
	
	public List<GateOut> findDataOfGateOutDetails(String companyId, String branchId, String partyName) 
	{
        return gateOutRepo.findDataOfGateOutDetails(companyId, branchId, partyName);
    }
	

	
	 public List<GateOut> getAllListOfGateOut(String companyId, String branchId, String gateOutId, String exBondBeNo) {
		 
		 System.out.println("in the service of you");
	        return gateOutRepo.getAllListOfGateOut(companyId, branchId, gateOutId, exBondBeNo);
	    }
	 
	 public List<GateOut> getDataOfGateOutDetails(String companyId, String branchId,String gateOutId, String vehicleNo) {
		    return gateOutRepo.getCommodityDetailsOfVehicleNo(companyId, branchId,gateOutId, vehicleNo);
		}
	
}

