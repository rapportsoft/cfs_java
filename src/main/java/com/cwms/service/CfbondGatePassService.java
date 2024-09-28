package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cwms.entities.CFBondGatePass;
import com.cwms.repository.CfExBondCrgDtlRepository;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfbondGatePassRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CfbondGatePassService {

	@Autowired
	public CfbondGatePassRepository cfbondGatePassRepository;

	@Autowired
	private ProcessNextIdRepository processNextIdRepository;

	@Autowired
	private CfExBondCrgRepository cfExBondCrgRepository;

	@Autowired
	private CfExBondCrgDtlRepository cfExBondCrgDtlRepository;

	public ResponseEntity<?> saveDataOfGatePassAndGatePassDtl(String companyId, String branchId, String user,
			String flag, Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		System.out.println("requestBody_________________________" + requestBody);

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		CFBondGatePass gatePass = object.convertValue(requestBody.get("gatePass"), CFBondGatePass.class);

		Object nocDtlObj = requestBody.get("dtl");
		List<CFBondGatePass> gatePassDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			// If nocDtl is a list, deserialize it directly
			gatePassDtlList = object.convertValue(nocDtlObj, new TypeReference<List<CFBondGatePass>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			// If nocDtl is a map, convert each map entry to CfinbondcrgDtl
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				CFBondGatePass gatePassDtl = object.convertValue(entry.getValue(), CFBondGatePass.class);
				gatePassDtlList.add(gatePassDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		System.out.println("gatePassDtlList________________" + gatePassDtlList);

		System.out.println("flag________________" + flag);

		CFBondGatePass saved = null;

		List<CFBondGatePass> savedDtl = new ArrayList<>();
		if (gatePass != null) {
			if ("add".equals(flag)) {

				if (gatePassDtlList != null) {
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P03209", "2246");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectExBondingId = String.format("BOG1%06d", nextNumericNextID1);

					int sr = 1;

					for (CFBondGatePass item : gatePassDtlList) {
						CFBondGatePass gatePassDtl = new CFBondGatePass();

						gatePassDtl.setCompanyId(companyId);
						gatePassDtl.setBranchId(branchId);
						gatePassDtl.setFinYear("2025");
						gatePassDtl.setCreatedBy(user);
						gatePassDtl.setCreatedDate(new Date());
						gatePassDtl.setApprovedBy(user);
						gatePassDtl.setApprovedDate(new Date());
						gatePassDtl.setStatus("A");
						
						gatePassDtl.setGatePassId(nectExBondingId);
						gatePassDtl.setGatePassDate(gatePass.getGatePassDate());
						gatePassDtl.setShift(gatePass.getShift());
						gatePassDtl.setProfitcentreId(gatePass.getProfitcentreId());
						gatePassDtl.setContactNo(gatePass.getContactNo());
						gatePassDtl.setCha(gatePass.getCha());
						gatePassDtl.setVehicleNo(gatePass.getVehicleNo());
						gatePassDtl.setDriverName(gatePass.getDriverName());
						gatePassDtl.setTransporterStatus(gatePass.getTransporterStatus());
						gatePassDtl.setImporterName(gatePass.getImporterName());
						gatePassDtl.setImporterAddress1(gatePass.getImporterAddress1());
						gatePassDtl.setImporterAddress2(gatePass.getImporterAddress2());
						gatePassDtl.setImporterAddress3(gatePass.getImporterAddress3());
						gatePassDtl.setLicenceNo(gatePass.getLicenceNo());
						gatePassDtl.setTransporterName(gatePass.getTransporterName());
						gatePassDtl.setComments(gatePass.getComments());

						gatePassDtl.setInBondingId(item.getInBondingId());
						gatePassDtl.setExBondingId(item.getExBondingId());
						gatePassDtl.setInBondPackages(item.getInBondPackages());
						gatePassDtl.setTransporterName(gatePass.getTransporterName());
						gatePassDtl.setTransporterStatus(gatePass.getTransporterStatus());
						gatePassDtl.setCha(gatePass.getCha());

						gatePassDtl.setTransType(gatePass.getTransType());
						gatePassDtl.setNocNo(item.getNocNo());
						gatePassDtl.setNocTransId(item.getNocTransId());
						gatePassDtl.setExBondBeNo(item.getExBondBeNo());
						gatePassDtl.setBoeNo(item.getBoeNo());
						gatePassDtl.setCommodity(item.getCommodity());
						gatePassDtl.setYardLocation(item.getYardLocation());
						gatePassDtl.setYardBlock(item.getYardBlock());
						gatePassDtl.setBlockCellNo(item.getBlockCellNo());
						gatePassDtl.setAreaAllocated(item.getAreaAllocated());
						gatePassDtl.setAreaReleased(item.getAreaReleased());
						gatePassDtl.setGrossWt(item.getGrossWt());
						gatePassDtl.setExBondedPackages(item.getExBondedPackages());
						gatePassDtl.setNoOfPackage(item.getNoOfPackage());
						gatePassDtl.setQtyTakenOut(item.getQtyTakenOut());
						gatePassDtl.setNocTransId(item.getNocTransId());
						gatePassDtl.setBondingNo(item.getBondingNo());
						gatePassDtl.setCommodityDescription(item.getCommodityDescription());
						gatePassDtl.setSrNo(sr);

						saved = cfbondGatePassRepository.save(gatePassDtl);

						savedDtl.add(saved);

						sr++;

						int updateCfExbondcrgDtlAfterGatePass = cfExBondCrgDtlRepository.updateCfexbondDtlAfterGatePass(
								saved.getQtyTakenOut(), companyId, branchId, saved.getExBondingId(),
								saved.getExBondBeNo(), saved.getCommodity());

						System.out.println(
								"Update row count after exbond details is" + updateCfExbondcrgDtlAfterGatePass);
					}
					if (savedDtl.size() > 0)

					{
						CFBondGatePass firstSavedObject = savedDtl.get(0);
						
						BigDecimal totalOut =BigDecimal.ZERO;
						for (CFBondGatePass savedObject : savedDtl) {

							totalOut=totalOut.add(savedObject.getQtyTakenOut());
						}

						int updateExbondCrg = cfExBondCrgRepository.updateCfexbondAfterGatePass(totalOut, companyId,
								branchId, firstSavedObject.getExBondingId(), firstSavedObject.getExBondBeNo());
						
						System.out.println("Update row count after gate pass in exbond is____________"+updateExbondCrg);
					}
//					

					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03209", nectExBondingId, "2246");
				}

			}

			else {
				System.out.println("In Edit loop ");

				if (gatePassDtlList != null) 
				{
					int sr = 1;
					
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P03209", "2246");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectExBondingId = String.format("BOG1%06d", nextNumericNextID1);

					for (CFBondGatePass item : gatePassDtlList) 
					{
						CFBondGatePass gatePassDtl = cfbondGatePassRepository.getExistingDataOfGatePass(companyId,
								branchId, gatePass.getGatePassId(), item.getCommodity(), item.getSrNo());

						if(gatePassDtl!=null)
						{
							
						
						gatePassDtl.setStatus("A");

						gatePassDtl.setEditedBy(user);
						gatePassDtl.setEditedDate(new Date());

						gatePassDtl.setGatePassDate(gatePass.getGatePassDate());
						gatePassDtl.setShift(gatePass.getShift());
						gatePassDtl.setProfitcentreId(gatePass.getProfitcentreId());
						gatePassDtl.setContactNo(gatePass.getContactNo());
						gatePassDtl.setCha(gatePass.getCha());
						gatePassDtl.setVehicleNo(gatePass.getVehicleNo());
						gatePassDtl.setDriverName(gatePass.getDriverName());
						gatePassDtl.setTransporterStatus(gatePass.getTransporterStatus());
						gatePassDtl.setImporterName(gatePass.getImporterName());
						gatePassDtl.setImporterAddress1(gatePass.getImporterAddress1());
						gatePassDtl.setImporterAddress2(gatePass.getImporterAddress2());
						gatePassDtl.setImporterAddress3(gatePass.getImporterAddress3());
						gatePassDtl.setLicenceNo(gatePass.getLicenceNo());
						gatePassDtl.setTransporterName(gatePass.getTransporterName());
						gatePassDtl.setComments(gatePass.getComments());

						gatePassDtl.setInBondingId(item.getInBondingId());
						gatePassDtl.setExBondingId(item.getExBondingId());
						gatePassDtl.setInBondPackages(item.getInBondPackages());
						gatePassDtl.setTransporterName(gatePass.getTransporterName());
						gatePassDtl.setTransporterStatus(gatePass.getTransporterStatus());
						gatePassDtl.setCha(gatePass.getCha());

						gatePassDtl.setTransType(gatePass.getTransType());
						gatePassDtl.setNocNo(item.getNocNo());
						gatePassDtl.setExBondBeNo(item.getExBondBeNo());
						gatePassDtl.setBoeNo(item.getBoeNo());
						gatePassDtl.setCommodity(item.getCommodity());
						gatePassDtl.setYardLocation(item.getYardLocation());
						gatePassDtl.setYardBlock(item.getYardBlock());
						gatePassDtl.setBlockCellNo(item.getBlockCellNo());
						gatePassDtl.setAreaAllocated(item.getAreaAllocated());
						gatePassDtl.setAreaReleased(item.getAreaReleased());
						gatePassDtl.setGrossWt(item.getGrossWt());
						gatePassDtl.setNocTransId(item.getNocTransId());
						gatePassDtl.setExBondedPackages(item.getExBondedPackages());
						gatePassDtl.setNoOfPackage(item.getNoOfPackage());
						gatePassDtl.setQtyTakenOut(item.getQtyTakenOut());
						gatePassDtl.setBondingNo(item.getBondingNo());
						gatePassDtl.setCommodityDescription(item.getCommodityDescription());

						saved = cfbondGatePassRepository.save(gatePassDtl);

//						int updateCfExbondcrgDtlAfterGatePass = cfExBondCrgDtlRepository.updateCfexbondDtlAfterGatePass(
//								saved.getQtyTakenOut(), companyId, branchId, saved.getExBondingId(),
//								saved.getExBondBeNo(), saved.getCommodity());
//						
//						System.out.println("Update row count after gate pass in exbond details is in edit ok ____________"+updateCfExbondcrgDtlAfterGatePass);
//						
						savedDtl.add(saved);
						
						}
						else
						{
							CFBondGatePass gatePassDtl1 = new CFBondGatePass();

							gatePassDtl1.setCompanyId(companyId);
							gatePassDtl1.setBranchId(branchId);
							gatePassDtl1.setFinYear("2025");
							gatePassDtl1.setCreatedBy(user);
							gatePassDtl1.setCreatedDate(new Date());
							gatePassDtl1.setApprovedBy(user);
							gatePassDtl1.setApprovedDate(new Date());
							gatePassDtl1.setStatus("A");

							gatePassDtl1.setGatePassId(gatePass.getGatePassId());
							gatePassDtl1.setGatePassDate(gatePass.getGatePassDate());
							gatePassDtl1.setShift(gatePass.getShift());
							gatePassDtl1.setProfitcentreId(gatePass.getProfitcentreId());
							gatePassDtl1.setContactNo(gatePass.getContactNo());
							gatePassDtl1.setCha(gatePass.getCha());
							gatePassDtl1.setVehicleNo(gatePass.getVehicleNo());
							gatePassDtl1.setDriverName(gatePass.getDriverName());
							gatePassDtl1.setTransporterStatus(gatePass.getTransporterStatus());
							gatePassDtl1.setImporterName(gatePass.getImporterName());
							gatePassDtl1.setImporterAddress1(gatePass.getImporterAddress1());
							gatePassDtl1.setImporterAddress2(gatePass.getImporterAddress2());
							gatePassDtl1.setImporterAddress3(gatePass.getImporterAddress3());
							gatePassDtl1.setLicenceNo(gatePass.getLicenceNo());
							gatePassDtl1.setTransporterName(gatePass.getTransporterName());
							gatePassDtl1.setComments(gatePass.getComments());

							gatePassDtl1.setInBondingId(item.getInBondingId());
							gatePassDtl1.setExBondingId(item.getExBondingId());
							gatePassDtl1.setInBondPackages(item.getInBondPackages());
							gatePassDtl1.setTransporterName(gatePass.getTransporterName());
							gatePassDtl1.setTransporterStatus(gatePass.getTransporterStatus());
							gatePassDtl1.setCha(gatePass.getCha());
							gatePassDtl1.setNocTransId(item.getNocTransId());
							gatePassDtl1.setTransType(gatePass.getTransType());
							gatePassDtl1.setNocNo(item.getNocNo());
							gatePassDtl1.setExBondBeNo(item.getExBondBeNo());
							gatePassDtl1.setBoeNo(item.getBoeNo());
							gatePassDtl1.setCommodity(item.getCommodity());
							gatePassDtl1.setYardLocation(item.getYardLocation());
							gatePassDtl1.setYardBlock(item.getYardBlock());
							gatePassDtl1.setBlockCellNo(item.getBlockCellNo());
							gatePassDtl1.setAreaAllocated(item.getAreaAllocated());
							gatePassDtl1.setAreaReleased(item.getAreaReleased());
							gatePassDtl1.setGrossWt(item.getGrossWt());
							gatePassDtl1.setExBondedPackages(item.getExBondedPackages());
							gatePassDtl1.setNoOfPackage(item.getNoOfPackage());
							gatePassDtl1.setQtyTakenOut(item.getQtyTakenOut());
							gatePassDtl1.setBondingNo(item.getBondingNo());
							gatePassDtl1.setCommodityDescription(item.getCommodityDescription());
							gatePassDtl1.setSrNo(item.getSrNo()+1);

							saved = cfbondGatePassRepository.save(gatePassDtl1);

							savedDtl.add(saved);

							
						}
					}
					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03209", nectExBondingId, "2246");
					
					if (savedDtl.size() > 0)

					{
						CFBondGatePass firstSavedObject = savedDtl.get(0);
						
						BigDecimal totalOut =BigDecimal.ZERO;
						for (CFBondGatePass savedObject : savedDtl) {

							totalOut=totalOut.add(savedObject.getQtyTakenOut());
							
							int updateCfExbondcrgDtlAfterGatePass = cfExBondCrgDtlRepository.updateCfexbondDtlAfterGatePass(
									savedObject.getQtyTakenOut(), companyId, branchId, savedObject.getExBondingId(),
									savedObject.getExBondBeNo(), savedObject.getCommodity());
							
							System.out.println("Update row count after gate pass in exbond details is in edit ok ____________"+updateCfExbondcrgDtlAfterGatePass);
							
						}

						int updateExbondCrg = cfExBondCrgRepository.updateCfexbondAfterGatePass(totalOut, companyId,
								branchId, firstSavedObject.getExBondingId(), firstSavedObject.getExBondBeNo());
						
						System.out.println("Update row count after gate pass in exbond is in edit ok ____________"+updateExbondCrg);
					}

				}

			}
		}

		System.out.println("gatePass_______________________" + saved);

		System.out.println("gatePassDtl_______________________" + gatePassDtlList);

		return new ResponseEntity<>(savedDtl, HttpStatus.OK);
	}
	
	
	
	public List<CFBondGatePass> findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(String companyId, String branchId, String partyName) {
        return cfbondGatePassRepository.findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(companyId, branchId, partyName);
    }
	
	
	
	
	 public List<CFBondGatePass> getAllListOfGatePass(String companyId, String branchId, String gatePassId, String exBondBeNo) {
	        return cfbondGatePassRepository.getAllListOfGatePass(companyId, branchId, gatePassId, exBondBeNo);
	    }
	 
	 public List<Object[]> getDataOfExbondBeNo(String cid, String bid, String val) {
			return cfbondGatePassRepository.getAllExbondBeNoFromGatePass(cid, bid, val);
		}
	 
	 public List<Object[]> getVehicleNoOfExbondBeNoFromGatePass(String cid, String bid,String exBondBeNo, String val) {
			return cfbondGatePassRepository.getVehicleNoOfExbondBeNoFromGatePass(cid, bid, exBondBeNo,val);
		}
	 
	
	 
	 
	 
	 
	 
	 
	 public List<Object[]> getDataOfVehicleNo(String cid, String bid, String val) {
			return cfbondGatePassRepository.getVehicleNo(cid, bid, val);
		}
		
	 
	 public List<CFBondGatePass> getDataOfCommodityDetailsByVehicleNo(String companyId, String branchId, String vehicleNo) {
		    return cfbondGatePassRepository.getCommodityDetailsOfVehicleNo(companyId, branchId, vehicleNo);
		}

}
