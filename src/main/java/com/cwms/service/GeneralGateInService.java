package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.GateIn;
import com.cwms.entities.GeneralGateIn;
import com.cwms.entities.GeneralJobOrderEntryDetails;
import com.cwms.entities.GenerelJobEntry;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.GeneralGateInRepo;
import com.cwms.repository.GeneralJobEntryDetailsRepository;
import com.cwms.repository.GeneralJobEntryRepository;
import com.cwms.repository.PartyAddressRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeneralGateInService {

	@Autowired
	public GeneralGateInRepo generalGateInRepo;
	
	@Autowired
	private ProcessNextIdRepository processNextIdRepository;
	
	@Autowired
	private VehicleTrackRepository vehicleTrackRepository;
	
	@Autowired
	private GeneralJobEntryDetailsRepository generalJobEntryDetailsRepository;
	
	@Autowired
	private GeneralJobEntryRepository generalJobEntryRepository;
	
	public List<Object[]> getAllBoeNoFromJobEntry(String cid, String bid, String val) {
		return generalGateInRepo.getAllBoeNoFromJobEntry(cid, bid, val);
	}
	
	public List<Object[]> getAllNocDtl(String cid, String bid, String boe, String noctransId, String val) {
		return generalGateInRepo.getCommodityDtlFromNocDtl(cid, bid, boe, noctransId, val);
	}
	
	public List<GeneralGateIn> findAllCargoGateIn(String companyId, String branchId, String search) {
		return generalGateInRepo.findGateInByCriteria(companyId, branchId, search);
	}
	
	
	
	
	
	
	
	
	public List<GeneralGateIn> getDataByGateInId(String companyId, String branchId, String gateInId) {
		return generalGateInRepo.getDataOfRowUsingGateInId(companyId, branchId, gateInId);
	}
	
	@Transactional
	public ResponseEntity<?> saveDataOfGateIn(String cid, String bid, String user, String flag,
			Map<String, Object> requestBody) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		GeneralGateIn cfGateIn = objectMapper.convertValue(requestBody.get("gateIn"), GeneralGateIn.class);

		Object nocDtlObj = requestBody.get("rowData");

		List<GeneralGateIn> bondnocDtl = new ArrayList();

		if (nocDtlObj instanceof List) {
			bondnocDtl = objectMapper.convertValue(nocDtlObj, new TypeReference<List<GeneralGateIn>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				GeneralGateIn gatePassDtl = objectMapper.convertValue(entry.getValue(), GeneralGateIn.class);
				bondnocDtl.add(gatePassDtl);
			}
		} else {
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		System.out.println("gateIn__________________________________________" + cfGateIn);

		System.out.println("bondnocDtl______________________________________" + bondnocDtl);

		GeneralGateIn firstSavedGateIn = null;

		List<GeneralGateIn> savedGatein = null;
		if (cfGateIn != null) {
			if ("add".equals(flag)) {

				if (bondnocDtl != null) {

					BigDecimal totalQtyTakenIn = bondnocDtl.stream()
							.map(item -> item.getNoOfPackages() != null ? item.getNoOfPackages() : BigDecimal.ZERO)
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					// Sum weightTakenIn
					BigDecimal totalWeightTakenIn = bondnocDtl.stream()
							.map(item -> item.getGrossWeight() != null ? item.getGrossWeight() : BigDecimal.ZERO)
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					// Print the results
					System.out.println("Total Qty Taken In: " + totalQtyTakenIn);
					System.out.println("Total Weight Taken In: " + totalWeightTakenIn);

					List<GeneralGateIn> gateInList = new ArrayList<>();

					int srNo = 1; // Initialize srNo

					String gateInNextId = null;
					String holdId1 = processNextIdRepository.findAuditTrail(cid, bid, "P09090", "2025");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					gateInNextId = String.format("GGIN%06d", nextNumericNextID1);

					for (GeneralGateIn item : bondnocDtl) { // Replace ItemType with the actual type of items in bondnocDtl
						GeneralGateIn gateIn = new GeneralGateIn();
						gateIn.setCompanyId(cid);
						gateIn.setBranchId(bid);
						gateIn.setSrNo(srNo);
						gateIn.setJobNo(item.getJobNo());
						gateIn.setJobTransId(item.getJobTransId());
						gateIn.setJobDate(item.getJobDate());
						gateIn.setJobTransDate(item.getJobTransDate());
						gateIn.setGateInId(gateInNextId);
						gateIn.setGateInDate(new Date());
						gateIn.setBoeNo(item.getBoeNo());
						gateIn.setProfitcentreId("N00008");
						gateIn.setBoeDate(item.getBoeDate());
						gateIn.setGrossWeight(item.getGrossWeight());
						gateIn.setNoOfPackages(item.getNoOfPackages());
						gateIn.setCha(item.getCha());
						gateIn.setCommodityDescription(item.getCommodityDescription());
						gateIn.setJobNop(item.getJobNop());
						gateIn.setJobGwt(item.getJobGwt());
						gateIn.setImporterId(item.getImporterId());
						gateIn.setCommodityId(item.getCommodityId());
						gateIn.setImporterName(item.getImporterName());
						gateIn.setAddress1(item.getAddress1());
						gateIn.setAddress2(item.getAddress2());
						gateIn.setAddress3(item.getAddress3());
						gateIn.setTypeOfPackage(item.getTypeOfPackage());
						gateIn.setActCommodityId(item.getActCommodityId());
						gateIn.setCreatedBy(user);
						gateIn.setTransporterName(item.getTransporterName());
						gateIn.setApprovedBy(user);
						gateIn.setCreatedDate(new Date());
						gateIn.setApprovedDate(new Date());
						gateIn.setLrNo(item.getLrNo());
						gateIn.setStatus("A");
						gateIn.setProfitcentreId(cfGateIn.getProfitcentreId());
						gateIn.setTransporterName(cfGateIn.getTransporterName());
						gateIn.setVehicleNo(cfGateIn.getVehicleNo());
						gateIn.setContainerNo(cfGateIn.getContainerNo());
						gateIn.setIsoCode(cfGateIn.getIsoCode());
						gateIn.setContainerSize(cfGateIn.getContainerSize());
						gateIn.setContainerType(cfGateIn.getContainerType());
						gateIn.setGateInPackages(item.getNoOfPackages());
						gateIn.setTareWeight(cfGateIn.getTareWeight());
						gateInList.add(gateIn);

						GeneralGateIn saved = generalGateInRepo.save(gateIn);

						if (saved != null) {

							GeneralJobOrderEntryDetails existingDetail = generalGateInRepo.findCfBondDetails(cid, bid,
									saved.getJobTransId(), saved.getCommodityId(), saved.getJobNo());

							GenerelJobEntry existingNoc = generalGateInRepo.findCfBondNoc(cid, bid, saved.getJobTransId(),
									saved.getJobNo());

							if (existingDetail != null) {

								BigDecimal newQtyTakenIn = (existingDetail.getGateInPackages() != null
										? existingDetail.getGateInPackages()
										: BigDecimal.ZERO)
										.add(saved.getNoOfPackages() != null ? saved.getNoOfPackages() : BigDecimal.ZERO);
								
								BigDecimal newWeightTakenIn = existingDetail.getWeightTakenIn().add(
										saved.getGrossWeight() != null ? saved.getGrossWeight() : BigDecimal.ZERO);

								// Update the database with new values
								int updateDetail = generalGateInRepo.updateCfBondDetails(newQtyTakenIn,
										newWeightTakenIn, cid, bid, saved.getJobTransId(), saved.getCommodityId(),
										saved.getJobNo());

								System.out.println("Successfully updated item with updateDetail: " + updateDetail);

							}

							// Check if the existing NOC record is found
							if (existingNoc != null) {
								System.out.println("existingNoc.getGateInPackages()____________________"
										+ existingNoc.getGateInPackages());

								// Update the GateInPackages
								BigDecimal updatedGateInPackages = (existingNoc.getGateInPackages() != null
										? existingNoc.getGateInPackages()
										: BigDecimal.ZERO)
										.add(saved.getNoOfPackages() != null ? saved.getNoOfPackages() : BigDecimal.ZERO);

								System.out.println("updatedGateInPackages____________________" + updatedGateInPackages);
								existingNoc.setGateInPackages(updatedGateInPackages);
								generalJobEntryRepository.save(existingNoc);
							}

						}

						// Increment srNo after setting it
						srNo++;
					}

					savedGatein = gateInList;

					if (!savedGatein.isEmpty()) {
						VehicleTrack vtrac = new VehicleTrack();
						vtrac.setCompanyId(cid);
						vtrac.setBranchId(bid);
						vtrac.setCreatedBy(user);
						vtrac.setCreatedDate(new Date());
						vtrac.setApprovedBy(user);
						vtrac.setApprovedDate(new Date());
						vtrac.setVehicleNo(cfGateIn.getVehicleNo());
						vtrac.setDriverName(cfGateIn.getDriverName());
						vtrac.setTransporterName(cfGateIn.getTransporterName());
//						vtrac.setTransporterStatus(cfGateIn.getTransporterStatus().charAt(0));
						vtrac.setFinYear("2025");
						vtrac.setContainerNo(cfGateIn.getContainerNo() != null ? cfGateIn.getContainerNo() : "");
						vtrac.setContainerSize(cfGateIn.getContainerSize() != null ? cfGateIn.getContainerSize() : "");
						vtrac.setContainerType(cfGateIn.getContainerType() != null ? cfGateIn.getContainerType() : "");
						vtrac.setStatus('A');
						vtrac.setProfitcentreId(cfGateIn.getProfitcentreId());
						vtrac.setSrNo(1);
//						vtrac.setShiftIn(cfGateIn.getShift());
						vtrac.setGateInId(gateInNextId);
						vtrac.setGateInDate(cfGateIn.getGateInDate());
						vtrac.setVehicleStatus('E');
						vehicleTrackRepository.save(vtrac);

						
						processNextIdRepository.updateAuditTrail(cid, bid, "P09090", gateInNextId, "2025");

					} 
					else {
						return new ResponseEntity<>("Gate In data not found", HttpStatus.BAD_REQUEST);
					}

				}
				return new ResponseEntity<>(savedGatein, HttpStatus.OK);

			}

			else

			{

				List<GeneralGateIn> updatedData = new ArrayList<>();

				cfGateIn.setEditedBy(user);
				cfGateIn.setEditedDate(new Date());

				cfGateIn.setIsoCode(cfGateIn.getIsoCode());
				cfGateIn.setContainerNo(cfGateIn.getContainerNo());
				cfGateIn.setContainerSize(cfGateIn.getContainerSize());
				cfGateIn.setContainerType(cfGateIn.getContainerType());
				cfGateIn.setVehicleNo(cfGateIn.getVehicleNo());
				cfGateIn.setTareWeight(cfGateIn.getTareWeight());
				cfGateIn.setTransporterName(cfGateIn.getTransporterName());
				cfGateIn.setApprovedDate(new Date());
				cfGateIn.setApprovedBy(user);
				cfGateIn.setGateInDate(cfGateIn.getGateInDate());
				cfGateIn.setProfitcentreId("N00008");
				cfGateIn.setStatus("A");
				cfGateIn.setAddress1(cfGateIn.getAddress1());
				cfGateIn.setAddress2(cfGateIn.getAddress2());
				cfGateIn.setAddress3(cfGateIn.getAddress3());
				
				generalGateInRepo.save(cfGateIn);

				
//				int updateDataBondGateInVehicle = vehicleTrackRepository.updateDataBondGateInVehicle(
//						cfGateIn.getVehicleNo(), statusChar, cfGateIn.getTransporterName(), cfGateIn.getDriverName(),
//						'E', cfGateIn.getShift(), cfGateIn.getContainerNo(), cfGateIn.getContainerSize(),
//						cfGateIn.getContainerType(), user, cid, bid, cfGateIn.getGateInId());

//				System.out.println("updateDataBondGateInVehicle updated row count is " + updateDataBondGateInVehicle);

				bondnocDtl.forEach(item1 -> {


					GeneralGateIn item = generalGateInRepo.findAllRecordsByCriteria(cid, bid, item1.getJobNo(),
							item1.getJobTransId(), cfGateIn.getGateInId(), item1.getCommodityId());

					System.out.println("item____________________" + item);

					GeneralJobOrderEntryDetails existingDetail = generalGateInRepo.findCfBondDetails(cid, bid,
							item1.getJobTransId(), item1.getCommodityId(), item1.getJobNo());

					System.out.println("existingDetail_____________________________________" + existingDetail);

					if (existingDetail != null) {

						if (item != null) {

							System.out.println("existingDetail_____________________________________"
									+ existingDetail.getGateInPackages());

							System.out.println(
									"getQtyTakenIn_____________________________________" + item1.getNoOfPackages());

							System.out.println("item.getQtyTakenIn_________________" + item.getNoOfPackages());

							BigDecimal newQtyTakenIn = (existingDetail.getGateInPackages() != null
									? existingDetail.getGateInPackages()
									: BigDecimal.ZERO)
									.add(item1.getNoOfPackages() != null ? item1.getNoOfPackages() : BigDecimal.ZERO)
									.subtract(item.getNoOfPackages());

							System.out.println("newQtyTakenIn+++++++++++++++++" + newQtyTakenIn);

							BigDecimal newWeightTakenIn = existingDetail.getWeightTakenIn()
									.add(item1.getGrossWeight() != null ? item1.getGrossWeight() : BigDecimal.ZERO)
									.subtract(item.getGrossWeight());

							System.out
									.println("itemGate.getWeightTakenIn()+++++++++++++++++" + item.getGrossWeight());

							System.out.println("newWeightTakenIn+++++++++++++++++" + newWeightTakenIn);


							existingDetail.setGateInPackages(newQtyTakenIn);
							existingDetail.setWeightTakenIn(newWeightTakenIn);

							generalJobEntryDetailsRepository.save(existingDetail);

							System.out.println("Successfully updated item with updateDetail: " + existingDetail);
						}

					}

					else {
						System.out.println("No existing record found for CfBondDtlId: " + item1.getCommodityId());
					}

					if (item != null) {

//						item.setGateInType(cfGateIn.getGateInType());
						item.setGateInDate(cfGateIn.getGateInDate());
						item.setEditedBy(user);
						item.setEditedDate(new Date());
						item.setStatus("A");
						item.setProfitcentreId(cfGateIn.getProfitcentreId());
						item.setTransporterName(cfGateIn.getTransporterName());
						item.setVehicleNo(cfGateIn.getVehicleNo());
						item.setContainerNo(cfGateIn.getContainerNo());
						item.setIsoCode(cfGateIn.getIsoCode());
						item.setContainerSize(cfGateIn.getContainerSize());
						item.setContainerType(cfGateIn.getContainerType());
						item.setTareWeight(cfGateIn.getTareWeight());
						item.setLrNo(item1.getLrNo());
						item.setActCommodityId(item1.getActCommodityId());
						item.setJobDate(item1.getJobDate());
						
						
						item.setJobNo(item1.getJobNo());
						item.setJobTransId(item1.getJobTransId());
						item.setJobDate(item1.getJobDate());
						item.setJobTransDate(item1.getJobTransDate());
						item.setBoeNo(item1.getBoeNo());
						item.setProfitcentreId("N00008");
						item.setBoeDate(item1.getBoeDate());
						item.setCha(item1.getCha());
						item.setCommodityDescription(item1.getCommodityDescription());
						item.setJobNop(item1.getJobNop());
						item.setJobGwt(item1.getJobGwt());
						item.setImporterId(item1.getImporterId());
						item.setCommodityId(item1.getCommodityId());
						item.setImporterName(item1.getImporterName());
//						item.setAddress1(item1.getAddress1());
//						item.setAddress2(item1.getAddress2());
//						item.setAddress3(item1.getAddress3());
						item.setTypeOfPackage(item1.getTypeOfPackage());
						item.setActCommodityId(item1.getActCommodityId());
					

						GenerelJobEntry existingNoc = generalGateInRepo.findCfBondNoc(cid, bid, item1.getJobTransId(),
								item1.getJobNo());



						BigDecimal updatedGateInPackages = (existingNoc.getGateInPackages() != null
								? existingNoc.getGateInPackages()
								: BigDecimal.ZERO)
								.add(item1.getNoOfPackages() != null ? item1.getNoOfPackages() : BigDecimal.ZERO)
								.subtract(item.getNoOfPackages());

						existingNoc.setGateInPackages(updatedGateInPackages);
						generalJobEntryRepository.save(existingNoc);

						item.setNoOfPackages(item1.getNoOfPackages());
						item.setGateInPackages(item1.getNoOfPackages());
						item.setGrossWeight(item1.getGrossWeight());
					}
					GeneralGateIn saved = generalGateInRepo.save(item);
					updatedData.add(saved);
				});

				// Save the updated records
				List<GeneralGateIn> allData = generalGateInRepo.saveAll(updatedData);

				// Return the first object from the updated list
				if (!allData.isEmpty()) {

					return new ResponseEntity<>(allData, HttpStatus.OK);
				}

				else

				{
					return new ResponseEntity<>("No records updated", HttpStatus.NOT_FOUND);
				}

			}
		}

		else {
			return new ResponseEntity<>("Gate In data not found", HttpStatus.BAD_REQUEST);
		}

	}

}
