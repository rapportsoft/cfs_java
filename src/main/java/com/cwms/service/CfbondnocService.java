package com.cwms.service;

import org.apache.poi.hssf.record.aggregates.CFRecordsAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfbondinsbal;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.GateIn;
import com.cwms.entities.GateOut;
import com.cwms.entities.Party;
import com.cwms.entities.PartyAddress;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.CfbondnocRepository;
import com.cwms.repository.GateInRepo;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.cwms.repository.CfBondNocDtlRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CfbondnocService {

	@Autowired
	private CfbondnocRepository repository;

	@Autowired
	private CfbondnocRepository cfbondnocRepository;

	@Autowired
	private CfBondNocDtlRepository cfbondnocDtlRepository;

	@Autowired
	private ProcessNextIdRepository processNextIdRepository;

	@Autowired
	private GateInRepo gateInRepo;

  @Autowired
  private VehicleTrackRepository vehicleTrackRepository;

	public List<Cfbondnoc> findAll() {
		return repository.findAll();
	}

	public List<Party> findParties(String companyId, String branchId, String partyName) {
		return cfbondnocRepository.findPartyDTOByPartyId(companyId, branchId, partyName);
	}

	public List<GateIn> findAllCargoGateIn(String companyId, String branchId, String search) {
		return gateInRepo.findGateInByCriteria(companyId, branchId, search);
	}

	public List<GateIn> getDataByGateInId(String companyId, String branchId, String gateInId) {

		System.out.println(gateInRepo.getDataOfRowUsingGateInId(companyId, branchId, gateInId));
		return gateInRepo.getDataOfRowUsingGateInId(companyId, branchId, gateInId);
	}

	public Party getDataByPartyIdAndGstNo(String companyId, String branchId, String partyId, String gstNo, String sr) {
		return cfbondnocRepository.getDataByPartyIdAndGstNo1(companyId, branchId, partyId, gstNo, sr);
	}

	public List<Party> findAllImporter(String companyId, String branchId, String partyName) {
		return cfbondnocRepository.findImporterDataByPartyId(companyId, branchId, partyName);
	}

	public List<PartyAddress> findAllImporterAddress(String companyId, String branchId, String partyId) {
		return cfbondnocRepository.findPartyAddress(companyId, branchId, partyId);
	}

	public List<CfBondNocDtl> findAllCfBondNocDtl(String companyId, String branchId, String nocTransId, String nocNo) {
		return cfbondnocDtlRepository.getCfBondNocDtl(companyId, branchId, nocTransId, nocNo);
	}

	public List<CfBondNocDtl> getCfBondNocDtlForNocScreen(String companyId, String branchId, String nocTransId, String nocNo) {
		return cfbondnocDtlRepository.getCfBondNocDtlForNocScreen(companyId, branchId, nocTransId, nocNo);
	}
	
	public Party getDataByPartyIdAndGstNoForImporter(String companyId, String branchId, String partyId, String gstNo,
			String sr) {
		return cfbondnocRepository.getDataOfImporter1(companyId, branchId, partyId, gstNo, sr);
	}

	public void deleteByCompanyIdAndBranchIdAndPartyId(String companyId, String branchId, String dtlid, String trasnId,
			String nocId) {
		CfBondNocDtl dtl = cfbondnocDtlRepository.getDataOfDtlId(companyId, branchId, dtlid, trasnId, nocId);
		if (dtl != null) {
			dtl.setStatus("D");
			cfbondnocDtlRepository.save(dtl);
		}

	}

	public CfBondNocDtl getDataCfBondDtlById(String companyId, String branchId, String dtlid, String trasnId,
			String nocId) {
		return cfbondnocDtlRepository.getDataOfDtlId(companyId, branchId, dtlid, trasnId, nocId);
	}

	public List<Cfbondnoc> getCfbondnocData(String companyId, String branchId, String partyName) {
		return cfbondnocRepository.findCfbondnocByCompanyIdAndBranchId(companyId, branchId, partyName);
	}

	public List<Cfbondnoc> getCfbondnocDataForInBondScreen(String companyId, String branchId, String partyName) {
		return cfbondnocRepository.findCfbondnocByCompanyIdAndBranchIdForInbondScreen(companyId, branchId, partyName);
	}

	public Cfbondnoc dataOfBoeNoForNeeEntry(String companyId, String branchId, String nocTransId, String nocNo,
			String boeNo) {
		return cfbondnocRepository.dataOfBoeNoForNeeEntry(companyId, branchId, nocTransId, nocNo, boeNo);
	}

	public List<Cfbondnoc> getCfbondnocDataForCfBondGateIn(String companyId, String branchId, String partyName) {
		return cfbondnocRepository.findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(companyId, branchId, partyName);
	}

	public Cfbondnoc getCfbondnocDataByidOrSearch(String companyId, String branchId, String transId, String nocNo) {
		return cfbondnocRepository.findCfbondnocByCompanyIdAndBranchIdOrSerach(companyId, branchId, transId, nocNo);
	}

	
	
	
	
	
	@Transactional
	public ResponseEntity<?> saveDataOfCfBondNoc(String cid, String bid, String user, String flag,
			Cfbondnoc cfBondNoc) {

		if (cfBondNoc != null) {
			if ("add".equals(flag)) {
				Boolean isExist = cfbondnocRepository.isDataExist(cid, bid, cfBondNoc.getBoeNo());

				if (isExist) {
					return new ResponseEntity<>("Duplicate Bill of entry no", HttpStatus.BAD_REQUEST);
				}

				Cfbondinsbal validate = cfbondnocDtlRepository.getDataOfCfBondCifForValidation(cid, bid);

				if (validate != null) {
					BigDecimal addValue = validate.getCusAppArea().subtract(validate.getInbondAres())
							.add(validate.getExbondArea());

					// Compare using compareTo method
//   				    if (addValue.compareTo(cfBondNoc.getArea()) < 0) {
//   				    	return new ResponseEntity<>("Area validation failed", HttpStatus.BAD_REQUEST);
//   				    }
					if (cfBondNoc.getArea().compareTo(addValue) > 0) {
						// If cfBondNoc.getArea() is greater than addValue
						return new ResponseEntity<>("Area should be less than :"+addValue, HttpStatus.BAD_REQUEST);
					}

					// Proceed with your logic if cfBondNoc.getArea() is less than or equal to
					// addValue

				}

				String holdId1 = processNextIdRepository.findAuditTrail(cid, bid, "P02025", "2025");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("NODC%06d", nextNumericNextID1);

				String lastValue = processNextIdRepository.findAuditTrail(cid, bid, "P03203", "2240");

				// Get the current date
				LocalDate currentDate = LocalDate.now();

				// Determine the year part based on the current date
				String yearPart = determineYearPart(currentDate);

				// Increment the last value
				String nextValue = incrementTPNo(lastValue, yearPart);
				System.out.println("nextValue______________________" + nextValue);

				cfBondNoc.setCompanyId(cid);
				cfBondNoc.setNocNo(nextValue);
				cfBondNoc.setNocTransId(HoldNextIdD1);
				cfBondNoc.setBranchId(bid);
				cfBondNoc.setStatus("A");
				cfBondNoc.setCreatedBy(user);
				cfBondNoc.setCreatedDate(new Date());
				cfBondNoc.setApprovedBy(user);
				cfBondNoc.setApprovedDate(new Date());
				cfbondnocRepository.save(cfBondNoc);

				processNextIdRepository.updateAuditTrail(cid, bid, "P02025", HoldNextIdD1, "2025");

				processNextIdRepository.updateAuditTrail(cid, bid, "P03203", nextValue, "2240");

				return new ResponseEntity<>(cfBondNoc, HttpStatus.OK);

			} else {

				Boolean isExist = cfbondnocRepository.isDataExist1(cid, bid, cfBondNoc.getBoeNo(),
						cfBondNoc.getNocTransId());

				if (isExist) {
					return new ResponseEntity<>("Duplicate Bill of entry no", HttpStatus.BAD_REQUEST);
				}

				Cfbondinsbal validate = cfbondnocDtlRepository.getDataOfCfBondCifForValidation(cid, bid);
				Cfbondnoc existingCfBondNoc = cfbondnocRepository.getDataOfDtlId(cid, bid, cfBondNoc.getNocTransId(),
						cfBondNoc.getNocNo());
				if (validate != null) {
					BigDecimal addValue = validate.getCusAppArea().subtract(validate.getInbondAres())
							.add(validate.getExbondArea().add(existingCfBondNoc.getArea()));

					// Compare using compareTo method
//   				    if (addValue.compareTo(cfBondNoc.getArea()) < 0) {
//   				    	return new ResponseEntity<>("Area validation failed", HttpStatus.BAD_REQUEST);
//   				    }

					if (cfBondNoc.getArea().compareTo(addValue) > 0) {
						// If cfBondNoc.getArea() is greater than addValue
						return new ResponseEntity<>("Area should be less than :"+addValue, HttpStatus.BAD_REQUEST);
					}

					// Proceed with your logic if cfBondNoc.getArea() is less than or equal to
					// addValue

				}
				cfBondNoc.setEditedBy(user);
				cfBondNoc.setEditedDate(new Date());
				cfbondnocRepository.save(cfBondNoc);

				return new ResponseEntity<>(cfBondNoc, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("cfBondNoc data not found", HttpStatus.BAD_REQUEST);
		}

	}

	private String incrementTPNo(String lastValue, String yearPart) {
		if (lastValue == null || lastValue.isEmpty()) {
			// Handle the case where the last value is not found or empty
			// You can start with the initial value, e.g., "000000/23-24"
			return "000000/" + yearPart;
		}

		// Split the last value into parts
		String[] parts = lastValue.split("/");

		if (parts.length != 2) {
			// Handle the case where the last value is not in the expected format
			// You can throw an exception or handle it as needed
			throw new IllegalArgumentException("Invalid last value format: " + lastValue);
		}

		int numericPart = Integer.parseInt(parts[0]);

		// Increment the numeric part
		numericPart++;

		// Format the incremented value
		String incrementedValue = String.format("%d/%s", numericPart, yearPart);

		return incrementedValue;
	}

	private String determineYearPart(LocalDate currentDate) {
		LocalDate april1 = LocalDate.of(currentDate.getYear(), Month.APRIL, 1);

		if (currentDate.isAfter(april1) || currentDate.isEqual(april1)) {
			// If the current date is on or after April 1, use the current year and the next
			// year
			return (currentDate.getYear() % 100) + "-" + ((currentDate.getYear() % 100) + 1);
		} else {
			// If the current date is before April 1, use the previous year and the current
			// year
			return ((currentDate.getYear() - 1) % 100) + "-" + (currentDate.getYear() % 100);
		}
	}

	
	@Transactional
	public ResponseEntity<?> saveData(String cid, String bid, String user, String flag,
			Map<String, Object> requestBody) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Cfbondnoc cfBondNoc = objectMapper.convertValue(requestBody.get("noc"), Cfbondnoc.class);

		CfBondNocDtl bondnocDtl = objectMapper.convertValue(requestBody.get("nocDtl"), CfBondNocDtl.class);

//		String HoldNextIdD1=null;
		System.out.println("bondnocDtl______________________________________" + bondnocDtl);

		if (cfBondNoc != null) {
			if ("add".equals(flag)) {

				Cfbondinsbal validate = cfbondnocDtlRepository.getDataOfCfBondCifForValidation(cid, bid);

				Boolean isExist = cfbondnocRepository.isDataExist(cid, bid, cfBondNoc.getBoeNo());
//   		    Boolean isExist = cfbondnocRepository.isDataExist1(cid, bid, cfBondNoc.getBoeNo(), cfBondNoc.getNocTransId());

				if (isExist) {
					return new ResponseEntity<>("Duplicate Bill of entry no", HttpStatus.BAD_REQUEST);
				}

				if (validate != null) {
					BigDecimal addValue = validate.getCusAppArea().subtract(validate.getInbondAres())
							.add(validate.getExbondArea() != null ? validate.getExbondArea() : BigDecimal.ZERO);

					// Compare using compareTo method
//   				    if (addValue.compareTo(cfBondNoc.getArea()) < 0) {
//   				    	return new ResponseEntity<>("Area validation failed", HttpStatus.BAD_REQUEST);
//   				    }

					if (cfBondNoc.getArea().compareTo(addValue) > 0) {
						// If cfBondNoc.getArea() is greater than addValue
						return new ResponseEntity<>("Area should be less than :"+addValue, HttpStatus.BAD_REQUEST);
					}

					// Proceed with your logic if cfBondNoc.getArea() is less than or equal to
					// addValue

				}

				if (validate != null) {
					BigDecimal addValue = validate.getCusAppCifValue().subtract(validate.getInbondCifValue())
							.add(validate.getExbondCifValue());

					// Compare using compareTo method
//   				    if (addValue.compareTo(bondnocDtl.getCifValue()) < 0) {
//   				    	return new ResponseEntity<>("Cif Value validation failed", HttpStatus.BAD_REQUEST);
//   				    }

					if (bondnocDtl.getCifValue().compareTo(addValue) > 0) {
						// If cfBondNoc.getArea() is greater than addValue
						return new ResponseEntity<>("Cif should be less than :"+addValue, HttpStatus.BAD_REQUEST);
					}

					// Proceed with your logic if cfBondNoc.getArea() is less than or equal to
					// addValue

				}

				if (validate != null) {
					BigDecimal addValue = validate.getCusAppCargoDuty().subtract(validate.getInbondCargoDuty())
							.add(validate.getExbondCargoDuty());
//   				    if (addValue.compareTo(bondnocDtl.getCargoDuty()) < 0) {
//   				    	return new ResponseEntity<>("Crago Duty value validation failed", HttpStatus.BAD_REQUEST);
//   				    }
					if (bondnocDtl.getCargoDuty().compareTo(addValue) > 0) {
						// If cfBondNoc.getArea() is greater than addValue
						return new ResponseEntity<>("Crago should be less than :"+addValue, HttpStatus.BAD_REQUEST);
					}
				}

				String holdId1 = processNextIdRepository.findAuditTrail(cid, bid, "P02025", "2025");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("NODC%06d", nextNumericNextID1);

				String lastValue = processNextIdRepository.findAuditTrail(cid, bid, "P03203", "2240");

				// Get the current date
				LocalDate currentDate = LocalDate.now();

				// Determine the year part based on the current date
				String yearPart = determineYearPart(currentDate);

				// Increment the last value
				String nextValue = incrementTPNo(lastValue, yearPart);
				System.out.println("nextValue______________________" + nextValue);

				cfBondNoc.setBalCargoDuty((validate.getCusAppCargoDuty()
						.subtract(validate.getInbondCargoDuty().add(bondnocDtl.getCargoDuty())))
						.add(validate.getExbondCargoDuty()));
				cfBondNoc.setBalCifValue((validate.getCusAppCifValue()
						.subtract(validate.getInbondCifValue().add(bondnocDtl.getCifValue())))
						.add(validate.getExbondCifValue()));

				cfBondNoc.setStoredCragoDuty((validate.getInbondCargoDuty().add(bondnocDtl.getCargoDuty()))
						.subtract(validate.getExbondCargoDuty()));

				cfBondNoc.setStoredCifValue((validate.getInbondCifValue().add(bondnocDtl.getCifValue()))
						.subtract(validate.getExbondCifValue()));

				cfBondNoc.setCompanyId(cid);
				cfBondNoc.setNocNo(nextValue);
				cfBondNoc.setNocTransId(HoldNextIdD1);
				cfBondNoc.setBranchId(bid);
				cfBondNoc.setStatus("A");
				cfBondNoc.setCreatedBy(user);
				cfBondNoc.setCreatedDate(new Date());
				cfBondNoc.setApprovedBy(user);
				cfBondNoc.setApprovedDate(new Date());

				if (bondnocDtl != null) {
					cfBondNoc.setCifValue(bondnocDtl.getCifValue());
					cfBondNoc.setCargoDuty(bondnocDtl.getCargoDuty());
					cfBondNoc.setInsuranceValue(bondnocDtl.getInsuranceValue());
					cfBondNoc.setNocPackages(bondnocDtl.getNocPackages());
					cfBondNoc.setGrossWeight(bondnocDtl.getGrossWeight());

				}

				cfbondnocRepository.save(cfBondNoc);

				int updatedRow = cfbondnocDtlRepository.updateCfbondinsbal(
						validate.getInbondAres().add(cfBondNoc.getArea()),
						validate.getInbondCargoDuty().add(bondnocDtl.getCargoDuty()),
						validate.getInbondCifValue().add(bondnocDtl.getCifValue()), cid, bid);

				processNextIdRepository.updateAuditTrail(cid, bid, "P02025", HoldNextIdD1, "2025");

				processNextIdRepository.updateAuditTrail(cid, bid, "P03203", nextValue, "2240");

				String holdId = processNextIdRepository.findAuditTrail(cid, bid, "P02232", "2232");

				int lastNextNumericId = Integer.parseInt(holdId.substring(4));

				int nextNumericNextID = lastNextNumericId + 1;

				String HoldNextIdD = String.format("D%05d", nextNumericNextID);

				bondnocDtl.setCompanyId(cid);
				bondnocDtl.setBoeNo(cfBondNoc.getBoeNo());
				bondnocDtl.setNocTransId(cfBondNoc.getNocTransId());
				bondnocDtl.setCfBondDtlId(HoldNextIdD);
				bondnocDtl.setNocNo(cfBondNoc.getNocNo());
				bondnocDtl.setBranchId(bid);
				bondnocDtl.setStatus("A");
				bondnocDtl.setCreatedBy(user);
				bondnocDtl.setCreatedDate(new Date());
				bondnocDtl.setApprovedBy(user);
				bondnocDtl.setCfbondDetailDate(new Date());
				bondnocDtl.setApprovedDate(new Date());
				cfbondnocDtlRepository.save(bondnocDtl);
				processNextIdRepository.updateAuditTrail(cid, bid, "P02232", HoldNextIdD, "2232");

				return new ResponseEntity<>(cfBondNoc, HttpStatus.OK);

			} else

			{

				Boolean isExist = cfbondnocRepository.isDataExist1(cid, bid, cfBondNoc.getBoeNo(),
						cfBondNoc.getNocTransId());

				CfBondNocDtl existing = cfbondnocDtlRepository.getDataOfDtlId(cid, bid, bondnocDtl.getCfBondDtlId(),
						bondnocDtl.getNocTransId(), bondnocDtl.getNocNo());

				Cfbondnoc existingCfBondNoc = cfbondnocRepository.getDataOfDtlId(cid, bid, cfBondNoc.getNocTransId(),
						cfBondNoc.getNocNo());
				
				if (isExist) {
					return new ResponseEntity<>("Duplicate Bill of entry no", HttpStatus.BAD_REQUEST);
				}

				
//   				if (validate != null && existing != null) 
//   				{
//   				    BigDecimal addValue = validate.getCusAppArea()
//   				                                 .subtract(validate.getInbondAres())
//   				                                 .add(validate.getExbondArea().add(existingCfBondNoc.getArea()));   				    
//   				 if (cfBondNoc.getArea().compareTo(addValue) > 0) {
//    				    // If cfBondNoc.getArea() is greater than addValue
//    				    return new ResponseEntity<>("Area validation failed", HttpStatus.BAD_REQUEST);
//    				}
//
//   				}
//   				
//   				if (validate != null && existing != null) 
//   				{
//   				    BigDecimal addValue = validate.getCusAppCifValue()
//   				                                 .subtract(validate.getInbondCifValue())
//   				                                 .add(validate.getExbondCifValue().add(existing.getCifValue()));
//   				    
//   				 if (bondnocDtl.getCifValue().compareTo(addValue) > 0) {
//    				    // If cfBondNoc.getArea() is greater than addValue
//    				    return new ResponseEntity<>("Cif Value validation failed", HttpStatus.BAD_REQUEST);
//    				}
//
//   				}
//   				
//   				
//   				if (validate != null && existing != null) 
//   				{
//   				    BigDecimal addValue = validate.getCusAppCargoDuty()
//   				                                 .subtract(validate.getInbondCargoDuty())
//   				                                 .add(validate.getExbondCargoDuty().add(existing.getCargoDuty()));
//   				    
//   				 if (bondnocDtl.getCargoDuty().compareTo(addValue) > 0) {
// 				    // If cfBondNoc.getArea() is greater than addValue
// 				    return new ResponseEntity<>("Crago Duty value validation failed", HttpStatus.BAD_REQUEST);
// 				}
//
//   				}

				cfBondNoc.setCifValue(cfBondNoc.getCifValue().add(bondnocDtl.getCifValue()));
				cfBondNoc.setCargoDuty(cfBondNoc.getCargoDuty().add(bondnocDtl.getCargoDuty()));
				cfBondNoc.setInsuranceValue(cfBondNoc.getInsuranceValue().add(bondnocDtl.getInsuranceValue()));
				cfBondNoc.setNocPackages(cfBondNoc.getNocPackages().add(bondnocDtl.getNocPackages()));
				cfBondNoc.setGrossWeight(cfBondNoc.getGrossWeight().add(bondnocDtl.getGrossWeight()));
				cfBondNoc.setEditedBy(user);
				cfBondNoc.setEditedDate(new Date());
				cfBondNoc.setVesselId(cfBondNoc.getVesselId());
				cfbondnocRepository.save(cfBondNoc);

//   				CfBondNocDtl existing =cfbondnocDtlRepository.getDataOfDtlId(cid, bid, bondnocDtl.getCfBondDtlId(), bondnocDtl.getNocTransId(), bondnocDtl.getNocNo());
				if (existing != null) {
					existing.setCargoDuty(bondnocDtl.getCargoDuty());
					existing.setCifValue(bondnocDtl.getCifValue());
					existing.setInsuranceValue(bondnocDtl.getInsuranceValue());
					existing.setTypeOfPackage(bondnocDtl.getTypeOfPackage());
					existing.setCommodityDescription(bondnocDtl.getCommodityDescription());
					existing.setNocPackages(bondnocDtl.getNocPackages());
					existing.setGrossWeight(bondnocDtl.getGrossWeight());
					existing.setEditedBy(user);
					existing.setEditedDate(new Date());
					cfbondnocDtlRepository.save(existing);
				} else {
					
					Cfbondinsbal validate = cfbondnocDtlRepository.getDataOfCfBondCifForValidation(cid, bid);

					if (validate != null) {
						BigDecimal addValue = validate.getCusAppArea().subtract(validate.getInbondAres())
								.add(validate.getExbondArea() != null ? validate.getExbondArea() : BigDecimal.ZERO);

						if (cfBondNoc.getArea().compareTo(addValue) > 0) {
							// If cfBondNoc.getArea() is greater than addValue
							return new ResponseEntity<>("Area should be less than :"+addValue, HttpStatus.BAD_REQUEST);
						}
					}

					if (validate != null) {
						BigDecimal addValue = validate.getCusAppCifValue().subtract(validate.getInbondCifValue())
								.add(validate.getExbondCifValue());

						if (bondnocDtl.getCifValue().compareTo(addValue) > 0) {
							// If cfBondNoc.getArea() is greater than addValue
							return new ResponseEntity<>("Cif should be less than :"+addValue, HttpStatus.BAD_REQUEST);
						}
					}

					if (validate != null) {
						BigDecimal addValue = validate.getCusAppCargoDuty().subtract(validate.getInbondCargoDuty())
								.add(validate.getExbondCargoDuty());
						if (bondnocDtl.getCargoDuty().compareTo(addValue) > 0) {
							// If cfBondNoc.getArea() is greater than addValue
							return new ResponseEntity<>("Crago should be less than :"+addValue, HttpStatus.BAD_REQUEST);
						}
					}
					CfBondNocDtl bondnocDtl1 = new CfBondNocDtl();

					String holdId = processNextIdRepository.findAuditTrail(cid, bid, "P02232", "2232");

					int lastNextNumericId = Integer.parseInt(holdId.substring(4));

					int nextNumericNextID = lastNextNumericId + 1;

					String HoldNextIdD = String.format("D%05d", nextNumericNextID);

					bondnocDtl1.setCompanyId(cid);
					bondnocDtl1.setBoeNo(cfBondNoc.getBoeNo());
					bondnocDtl1.setNocTransId(cfBondNoc.getNocTransId());
					bondnocDtl1.setCfBondDtlId(HoldNextIdD);
					bondnocDtl1.setNocNo(cfBondNoc.getNocNo());
					bondnocDtl1.setBranchId(bid);
					bondnocDtl1.setCargoDuty(bondnocDtl.getCargoDuty());
					bondnocDtl1.setCifValue(bondnocDtl.getCifValue());
					bondnocDtl1.setInsuranceValue(bondnocDtl.getInsuranceValue());
					bondnocDtl1.setTypeOfPackage(bondnocDtl.getTypeOfPackage());
					bondnocDtl1.setCommodityDescription(bondnocDtl.getCommodityDescription());
					bondnocDtl1.setNocPackages(bondnocDtl.getNocPackages());
					bondnocDtl1.setGrossWeight(bondnocDtl.getGrossWeight());
					bondnocDtl1.setStatus("A");
					bondnocDtl1.setCreatedBy(user);
					bondnocDtl1.setCreatedDate(new Date());
					bondnocDtl1.setApprovedBy(user);
					bondnocDtl1.setCfbondDetailDate(new Date());
					bondnocDtl1.setApprovedDate(new Date());
					cfbondnocDtlRepository.save(bondnocDtl1);
					processNextIdRepository.updateAuditTrail(cid, bid, "P02232", HoldNextIdD, "2232");
				}

//   			int updatedRow = cfbondnocDtlRepository.updateCfbondinsbal(validate.getInbondAres().add(cfBondNoc.getArea()),validate.getInbondCargoDuty().add(bondnocDtl.getCargoDuty()),
//						validate.getInbondCifValue().add(bondnocDtl.getCifValue()),cid,bid);
				return new ResponseEntity<>(cfBondNoc, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("cfBondNoc data not found", HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<?> saveDataOfGateIn(String cid, String bid, String user, String flag,
			Map<String, Object> requestBody) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		GateIn cfGateIn = objectMapper.convertValue(requestBody.get("gateIn"), GateIn.class);
//
//		List<GateIn> bondnocDtl = objectMapper.convertValue(requestBody.get("rowData"),
//				new TypeReference<List<GateIn>>() {
//				});

		Object nocDtlObj = requestBody.get("rowData");

		List<GateIn> bondnocDtl = new ArrayList();

		if (nocDtlObj instanceof List) {
			// If nocDtl is a list, deserialize it directly
			bondnocDtl = objectMapper.convertValue(nocDtlObj, new TypeReference<List<GateIn>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			// If nocDtl is a map, convert each map entry to CfinbondcrgDtl
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				GateIn gatePassDtl = objectMapper.convertValue(entry.getValue(), GateIn.class);
				bondnocDtl.add(gatePassDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		System.out.println("gateIn__________________________________________" + cfGateIn);

		System.out.println("bondnocDtl______________________________________" + bondnocDtl);

		GateIn firstSavedGateIn = null;

		List<GateIn> savedGatein = null;
		if (cfGateIn != null) {
			if ("add".equals(flag)) {

				if (bondnocDtl != null) {

					BigDecimal totalQtyTakenIn = bondnocDtl.stream()
							.map(item -> item.getQtyTakenIn() != null ? item.getQtyTakenIn() : BigDecimal.ZERO)
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					// Sum weightTakenIn
					BigDecimal totalWeightTakenIn = bondnocDtl.stream()
							.map(item -> item.getWeightTakenIn() != null ? item.getWeightTakenIn() : BigDecimal.ZERO)
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					// Print the results
					System.out.println("Total Qty Taken In: " + totalQtyTakenIn);
					System.out.println("Total Weight Taken In: " + totalWeightTakenIn);

					List<GateIn> gateInList = new ArrayList<>();

					int srNo = 1; // Initialize srNo

					String gateInNextId = null;
					String holdId1 = processNextIdRepository.findAuditTrail(cid, bid, "P03204", "2241");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					gateInNextId = String.format("GIBM%06d", nextNumericNextID1);

					for (GateIn item : bondnocDtl) 
					{ // Replace ItemType with the actual type of items in bondnocDtl
						GateIn gateIn = new GateIn();
						gateIn.setCompanyId(cid);
						gateIn.setBranchId(bid);
						gateIn.setFinYear("2241");
						gateIn.setSrNo(srNo);
						gateIn.setErpDocRefNo(item.getErpDocRefNo());
						gateIn.setDocRefNo(item.getDocRefNo());
						gateIn.setGateInId(gateInNextId);
						gateIn.setDocRefDate(item.getDocRefDate());
						gateIn.setBoeNo(item.getBoeNo());
						gateIn.setBoeDate(item.getBoeDate());
						gateIn.setGrossWeight(item.getGrossWeight());
						gateIn.setCha(item.getCha());
						gateIn.setCommodityDescription(item.getCommodityDescription());
						gateIn.setActualNoOfPackages(item.getActualNoOfPackages());
						gateIn.setQtyTakenIn(item.getQtyTakenIn());
						gateIn.setWeightTakenIn(item.getWeightTakenIn());
						gateIn.setLineNo(item.getLineNo());
						gateIn.setCommodity(item.getCommodity());
						gateIn.setImporterName(item.getImporterName());
						gateIn.setNocNo(item.getNocNo());
						gateIn.setGateInType(cfGateIn.getGateInType());
						gateIn.setInGateInDate(cfGateIn.getInGateInDate());
						gateIn.setTypeOfPackage(item.getTypeOfPackage());
						gateIn.setCreatedBy(user);
						gateIn.setApprovedBy(user);
						gateIn.setCreatedDate(new Date());
						gateIn.setApprovedDate(new Date());
						gateIn.setStatus("A");
						gateIn.setShift(cfGateIn.getShift());
						gateIn.setProfitcentreId(cfGateIn.getProfitcentreId());
						gateIn.setTransporterStatus(cfGateIn.getTransporterStatus());
						gateIn.setTransporterName(cfGateIn.getTransporterName());
						gateIn.setVehicleNo(cfGateIn.getVehicleNo());
						gateIn.setContainerNo(cfGateIn.getContainerNo());
						gateIn.setIsoCode(cfGateIn.getIsoCode());
						gateIn.setContainerSize(cfGateIn.getContainerSize());
						gateIn.setContainerType(cfGateIn.getContainerType());

						gateInList.add(gateIn);

						GateIn saved = 	gateInRepo.save(gateIn);
						
						if (saved!=null)
						{
							
							CfBondNocDtl existingDetail = cfbondnocDtlRepository.findCfBondDetails(cid, bid,
									saved.getErpDocRefNo(), saved.getCommodity(), saved.getDocRefNo());

							
							Cfbondnoc existingNoc = cfbondnocRepository.findCfBondNoc(cid, bid, saved.getErpDocRefNo(),
									saved.getDocRefNo());
							
							if (existingDetail != null) {

								BigDecimal newQtyTakenIn = (existingDetail.getGateInPackages() != null
										? existingDetail.getGateInPackages()
										: BigDecimal.ZERO)
										.add(saved.getQtyTakenIn() != null ? saved.getQtyTakenIn() : BigDecimal.ZERO);
								BigDecimal newWeightTakenIn = existingDetail.getWeightTakenIn().add(
										saved.getWeightTakenIn() != null ? saved.getWeightTakenIn() : BigDecimal.ZERO);

								// Update the database with new values
								int updateDetail = cfbondnocDtlRepository.updateCfBondDetails(newQtyTakenIn,
										newWeightTakenIn, cid, bid, saved.getErpDocRefNo(), saved.getCommodity(),
										saved.getDocRefNo());

								System.out.println("Successfully updated item with updateDetail: " + updateDetail);

							}

							

							// Check if the existing NOC record is found
							if (existingNoc != null) 
							{
								System.out.println("existingNoc.getGateInPackages()____________________"
										+ existingNoc.getGateInPackages());

								// Update the GateInPackages
								BigDecimal updatedGateInPackages = (existingNoc.getGateInPackages() != null
										? existingNoc.getGateInPackages()
										: BigDecimal.ZERO)
										.add(saved.getQtyTakenIn() != null ? saved.getQtyTakenIn() : BigDecimal.ZERO);

								System.out.println("updatedGateInPackages____________________" + updatedGateInPackages);
								existingNoc.setGateInPackages(updatedGateInPackages);
								cfbondnocRepository.save(existingNoc);
	//
//						        // Update the NOC record in the database
//						        int updateCfBondNoc = cfbondnocRepository.updateCfBondNoc(updatedGateInPackages, cid, bid, item.getErpDocRefNo(), item.getDocRefNo());
//						        System.out.println("updateCfBondNoc____________________________________" + updateCfBondNoc);
							}

						}
						
			
			
						// Increment srNo after setting it
						srNo++;
					}
					
					

					savedGatein = gateInList;

					if (!savedGatein.isEmpty()) 
					{
						VehicleTrack vtrac = new VehicleTrack();
						vtrac.setCompanyId(cid);
						vtrac.setBranchId(bid);
						vtrac.setCreatedBy(user);
						 vtrac.setCreatedDate(new Date());
						 vtrac.setApprovedBy(user);
						 vtrac.setApprovedDate(new Date ());
						 vtrac.setVehicleNo(cfGateIn.getVehicleNo());
						 vtrac.setDriverName(cfGateIn.getDriverName());
						 vtrac.setTransporterName(cfGateIn.getTransporterName());
						 vtrac.setTransporterStatus(cfGateIn.getTransporterStatus().charAt(0));
						 vtrac.setFinYear("2025");
						 vtrac.setContainerNo(cfGateIn.getContainerNo()!=null ? cfGateIn.getContainerNo() : "");
						 vtrac.setContainerSize(cfGateIn.getContainerSize()!=null ? cfGateIn.getContainerSize() :"");
						 vtrac.setContainerType(cfGateIn.getContainerType()!=null ? cfGateIn.getContainerType() : "");
						 vtrac.setStatus('A');
						 vtrac.setProfitcentreId(cfGateIn.getProfitcentreId());
						 vtrac.setSrNo(1);
						 vtrac.setShiftIn(cfGateIn.getShift());
						 vtrac.setGateInId(gateInNextId);
						 vtrac.setGateInDate(cfGateIn.getInGateInDate());
						 vtrac.setVehicleStatus('E');
                         vehicleTrackRepository.save(vtrac);

						processNextIdRepository.updateAuditTrail(cid, bid, "P03204", gateInNextId, "2241");

//						bondnocDtl.forEach(item -> {
//
//							CfBondNocDtl existingDetail = cfbondnocDtlRepository.findCfBondDetails(cid, bid,
//									item.getErpDocRefNo(), item.getCommodity(), item.getDocRefNo());
//
//							if (existingDetail != null) {
//
//								BigDecimal newQtyTakenIn = (existingDetail.getGateInPackages() != null
//										? existingDetail.getGateInPackages()
//										: BigDecimal.ZERO)
//										.add(item.getQtyTakenIn() != null ? item.getQtyTakenIn() : BigDecimal.ZERO);
//								BigDecimal newWeightTakenIn = existingDetail.getWeightTakenIn().add(
//										item.getWeightTakenIn() != null ? item.getWeightTakenIn() : BigDecimal.ZERO);
//
//								// Update the database with new values
//								int updateDetail = cfbondnocDtlRepository.updateCfBondDetails(newQtyTakenIn,
//										newWeightTakenIn, cid, bid, item.getErpDocRefNo(), item.getCommodity(),
//										item.getDocRefNo());
//
//								System.out.println("Successfully updated item with updateDetail: " + updateDetail);
//
//							}
//
//							else {
//								System.out.println("No existing record found for CfBondDtlId: " + item.getCommodity());
//							}
//
//						});


					} else {
						return new ResponseEntity<>("Gate In data not found", HttpStatus.BAD_REQUEST);
					}

				}
				return new ResponseEntity<>(savedGatein, HttpStatus.OK);

			}

			else

			{

				List<GateIn> updatedData = new ArrayList<>();

				cfGateIn.setEditedBy(user);
				cfGateIn.setEditedDate(new Date());

				cfGateIn.setIsoCode(cfGateIn.getIsoCode());
				cfGateIn.setContainerNo(cfGateIn.getContainerNo());
				cfGateIn.setContainerSize(cfGateIn.getContainerSize());
				cfGateIn.setContainerType(cfGateIn.getContainerType());
				cfGateIn.setVehicleNo(cfGateIn.getVehicleNo());
				cfGateIn.setTransporterName(cfGateIn.getTransporterName());

				gateInRepo.save(cfGateIn);
				
				
				String transporterStatus = cfGateIn.getTransporterStatus();
				char statusChar = (transporterStatus != null && !transporterStatus.isEmpty()) ? transporterStatus.charAt(0) : ' ';
				
				int updateDataBondGateInVehicle=vehicleTrackRepository.updateDataBondGateInVehicle(cfGateIn.getVehicleNo(),statusChar,
						cfGateIn.getTransporterName(),cfGateIn.getDriverName(),'E',cfGateIn.getShift(),
						cfGateIn.getContainerNo(),cfGateIn.getContainerSize(),cfGateIn.getContainerType(),
						user,
						cid,bid,cfGateIn.getGateInId());
				
				System.out.println("updateDataBondGateInVehicle updated row count is "+updateDataBondGateInVehicle);

				bondnocDtl.forEach(item1 -> {

					System.out.println("item1diecre no" + item1.getDocRefNo());

					System.out.println("item____________________" + item1.getDocRefNo());

					System.out.println("item____________________" + item1.getErpDocRefNo());

					System.out.println("item____________________" + cfGateIn.getGateInId());

					System.out.println("item____________________" + item1.getCommodity());

					GateIn item = gateInRepo.findAllRecordsByCriteria(cid, bid, item1.getDocRefNo(),
							item1.getErpDocRefNo(), cfGateIn.getGateInId(), item1.getCommodity());

					System.out.println("item____________________" + item);

					CfBondNocDtl existingDetail = cfbondnocDtlRepository.findCfBondDetails(cid, bid,
							item1.getErpDocRefNo(), item1.getCommodity(), item1.getDocRefNo());

					System.out.println("existingDetail_____________________________________" + existingDetail);

					if (existingDetail != null) {

						if (item != null) {

							System.out.println("existingDetail_____________________________________"
									+ existingDetail.getGateInPackages());

							System.out.println(
									"getQtyTakenIn_____________________________________" + item1.getQtyTakenIn());

							System.out.println("item.getQtyTakenIn_________________" + item.getQtyTakenIn());

							BigDecimal newQtyTakenIn = (existingDetail.getGateInPackages() != null
									? existingDetail.getGateInPackages()
									: BigDecimal.ZERO)
									.add(item1.getQtyTakenIn() != null ? item1.getQtyTakenIn() : BigDecimal.ZERO)
									.subtract(item.getQtyTakenIn());

							System.out.println("newQtyTakenIn+++++++++++++++++" + newQtyTakenIn);

							BigDecimal newWeightTakenIn = existingDetail.getWeightTakenIn()
									.add(item1.getWeightTakenIn() != null ? item1.getWeightTakenIn() : BigDecimal.ZERO)
									.subtract(item.getWeightTakenIn());

							System.out
									.println("itemGate.getWeightTakenIn()+++++++++++++++++" + item.getWeightTakenIn());

							System.out.println("newWeightTakenIn+++++++++++++++++" + newWeightTakenIn);

							// Update the database with new values
//							int updateDetail = cfbondnocDtlRepository.updateCfBondDetails(newQtyTakenIn,
//									newWeightTakenIn, cid, bid, item1.getErpDocRefNo(), item1.getCommodity(),
//									item1.getDocRefNo());

							existingDetail.setGateInPackages(newQtyTakenIn);
							existingDetail.setWeightTakenIn(newWeightTakenIn);

							cfbondnocDtlRepository.save(existingDetail);

							System.out.println("Successfully updated item with updateDetail: " + existingDetail);
						}

					}

					else {
						System.out.println("No existing record found for CfBondDtlId: " + item1.getCommodity());
					}

					if (item != null) {

						item.setGateInType(cfGateIn.getGateInType());
						item.setInGateInDate(cfGateIn.getInGateInDate());
						item.setEditedBy(user);
						item.setEditedDate(new Date());
						item.setStatus("A");
						item.setShift(cfGateIn.getShift());
						item.setProfitcentreId(cfGateIn.getProfitcentreId());
						item.setTransporterStatus(cfGateIn.getTransporterStatus());
						item.setTransporterName(cfGateIn.getTransporterName());
						item.setVehicleNo(cfGateIn.getVehicleNo());
						item.setContainerNo(cfGateIn.getContainerNo());
						item.setIsoCode(cfGateIn.getIsoCode());
						item.setContainerSize(cfGateIn.getContainerSize());
						item.setContainerType(cfGateIn.getContainerType());

						Cfbondnoc existingNoc = cfbondnocRepository.findCfBondNoc(cid, bid, item1.getErpDocRefNo(),
								item1.getDocRefNo());

						System.out.println("existingNoc_______________________" + existingNoc);

						System.out.println("existingNoc.getGateInPackages_______________________"
								+ existingNoc.getGateInPackages());

						System.out.println("item1.getQtyTakenIn()_______________________" + item1.getQtyTakenIn());

						System.out.println("item.getQtyTakenIn()_______________________" + item.getQtyTakenIn());

						BigDecimal updatedGateInPackages = (existingNoc.getGateInPackages() != null
								? existingNoc.getGateInPackages()
								: BigDecimal.ZERO)
								.add(item1.getQtyTakenIn() != null ? item1.getQtyTakenIn() : BigDecimal.ZERO)
								.subtract(item.getQtyTakenIn());

						System.out.println("updatedGateInPackages_______________________" + updatedGateInPackages);

						existingNoc.setGateInPackages(updatedGateInPackages);
						cfbondnocRepository.save(existingNoc);

						item.setQtyTakenIn(item1.getQtyTakenIn());
						item.setWeightTakenIn(item1.getWeightTakenIn());
					}
					GateIn saved = gateInRepo.save(item);
					updatedData.add(saved);
				});

				// Save the updated records
				List<GateIn> allData = gateInRepo.saveAll(updatedData);

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

	public List<Object[]> getAllBoeNoFromNoc(String cid, String bid, String val) {
		return repository.getAllBoeNoFromNoc(cid, bid, val);
	}

	public List<Object[]> getAllNocDtl(String cid, String bid, String boe, String noctransId, String val) {
		return repository.getCommodityDtlFromNocDtl(cid, bid, boe, noctransId, val);
	}

	public List<Object[]> getAllNocNoFromNoc(String cid, String bid, String val) {
		return repository.getAllNocNoForBondingSearch(cid, bid, val);
	}

	public List<Object[]> getAllBondingNoForBondingSearch(String cid, String bid, String val) {
		return repository.getAllBondingNoForBondingSearch(cid, bid, val);
	}

//	public ResponseEntity<?> searchExportMain(String companyId, String branchId, String sbNo, String vehicleNo, String containerNo, String bookingNo, String pod)
//	{		
//		
//		System.out.println(companyId +" companyId "+ branchId + " branchId "+ sbNo + " sbNo "+ vehicleNo + " containerNo "+ bookingNo + " bookingNo "+ pod + " pod" );
//		
//		List<String> allowedList = new ArrayList<>();
//		Map<String, Object> dataMap = new HashMap<>();
//		Map<String,Object> mainData = new HashMap<>();
//		
//		Pageable pageable = PageRequest.of(0, 100);
//		
//		
//		
//		List<GateIn> gateInList = gateInRepo.searchLatestExportMain(companyId, branchId, sbNo, vehicleNo, "EXP", pod, pageable);
//
//		if (gateInList.isEmpty()) {		
//			System.out.println("1");
//			List<ExportSbEntry> existinSbEntry = entryRepo.searchLatestExportMain(companyId, branchId, pod, sbNo, pageable);
//			System.out.println("2");
//			if(existinSbEntry.isEmpty())
//			{
//				System.out.println("3");
//				return ResponseEntity.badRequest().body("No Data Found");
//				
//			}
//			else
//			{
//				ExportSbEntry exportSbEntry = existinSbEntry.get(0);
//				
//				dataMap.put("sbNo", exportSbEntry.getSbNo());
//		        dataMap.put("sbTransId", exportSbEntry.getSbTransId());
//		        dataMap.put("hsbSbTransId", exportSbEntry.gethSbTransId());
//		        dataMap.put("gateInId", "");
//		        dataMap.put("profitCenterId", exportSbEntry.getProfitcentreId());
//		        
//		        allowedList.add("P00216");
//		        
//				mainData.put("allowedList", allowedList);
//				mainData.put("data", dataMap);
//				
//				return ResponseEntity.ok(mainData);
//			}			
//		}
//
//		GateIn gateIn = gateInList.get(0);
//		
//		allowedList.add("P00216");
////		allowedList.add("P00217");
//		
//		boolean containsEmptyGateInId = gateInList.stream().anyMatch(c -> c.getGateInId() == null || c.getGateInId().isEmpty());
//
//		System.out.println("containsEmptyGateInId : "+containsEmptyGateInId);
//		
//		ExportSbEntry getsbNoAndPrimary = entryRepo.getsbNoAndPrimary(companyId, branchId, gateIn.getDocRefNo(), gateIn.getErpDocRefNo());
//		if (!containsEmptyGateInId) {
//				allowedList.add("P00217");
//				dataMap.put("sbNo", getsbNoAndPrimary.getSbNo());
//		        dataMap.put("sbTransId", getsbNoAndPrimary.getSbTransId());
//		        dataMap.put("gateInId", gateIn.getGateInId());
//		        dataMap.put("profitCenterId", gateIn.getProfitcentreId());
//		        dataMap.put("hsbSbTransId", getsbNoAndPrimary.gethSbTransId());
//		        System.out.println("profitCenterId"+ gateIn.getProfitcentreId());
//		        mainData.put("allowedList", allowedList);
//				mainData.put("data", dataMap);
//				return ResponseEntity.ok(mainData);
//		}
//		
//		allowedList.add("P00217");
//		dataMap.put("sbNo", getsbNoAndPrimary.getSbNo());
//        dataMap.put("sbTransId", getsbNoAndPrimary.getSbTransId());
//        dataMap.put("gateInId", gateIn.getGateInId());
//        dataMap.put("profitCenterId", getsbNoAndPrimary.getProfitcentreId());
//        dataMap.put("hsbSbTransId", getsbNoAndPrimary.gethSbTransId());
//        dataMap.put("profitCenterId", gateIn.getProfitcentreId());
//		
//        System.out.println("profitCenterId"+ gateIn.getProfitcentreId());
//
//        
//        mainData.put("allowedList", allowedList);
//		mainData.put("data", dataMap);
//		return ResponseEntity.ok(mainData);
//	}
//	
//	
//	public ResponseEntity<?> getForMainBondingSearch(String cid, String bid, String nocNo, String boeNo,
//			String bondingNo) {
//
//		List<String> idList = new ArrayList<>();
//		Cfinbondcrg firstInbond = null;
//		CfExBondCrg firstExBond = null;
//
//		Cfbondnoc list = repository.getForBondingSearch(cid, bid, nocNo, boeNo, bondingNo);
//
//		if (list == null) {
//			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
//		}
//
//		idList.add("P00248");
//
//		Boolean check = cfbondnocDtlRepository.checkGateInPackages(cid, bid, list.getNocNo(), list.getNocTransId());
//
//		System.out.println("check______________________________________" + check);
//		if (check) 
//		{
//			idList.add("P00249");
//			Map<String, Object> myMap = new HashMap<>();
//
//			myMap.put("list", list);
//			myMap.put("idList", idList);
//		
//	        //return  new ResponseEntity<>(myMap,HttpStatus.OK) ;  
//		}
//
//		Boolean check1 = cfbondnocDtlRepository.checkInBondedPackages(cid, bid, list.getNocNo(), list.getNocTransId());
//
//		System.out.println("check1______________________________________" + check1);
//		if (check1) 
//		{
//			idList.add("P00249");
//			idList.add("P00250");
//			
//
//			Map<String, Object> myMap = new HashMap<>();
//
//			myMap.put("list", list);
//			myMap.put("idList", idList);
//			//return new ResponseEntity<>(myMap, HttpStatus.OK);
//		}
//
//		Boolean check2 = cfbondnocDtlRepository.checkInBondedPackagesAllDone(cid, bid, list.getNocNo(),
//				list.getNocTransId());
//		
//		System.out.println("check2______________________________________" + check2);
//		if (check2) 
//		{
//			idList.add("P00249");
//			idList.add("P00250");
//			idList.add("P00251");
//
//			//Cfinbondcrg firstInbond = null;
//
//			List<Cfinbondcrg> results = cfbondnocDtlRepository.getInBondingIdAndBoeNo(cid, bid, list.getNocTransId(),
//					list.getNocNo());
//			if (!results.isEmpty())
//
//			{
//				firstInbond = results.get(0);
//			} else {
//				return null; // Handle cases where no records are found
//			}
//
//			Map<String, Object> myMap = new HashMap<>();
//
//			System.out.println("inbondind________________________________" + firstInbond);
//			if (firstInbond != null) {
//				myMap.put("inBondId", firstInbond.getInBondingId());
//			}
//
//			myMap.put("list", list);
//			myMap.put("idList", idList);
//			myMap.put("firstInbond", firstInbond);
//
//			//return new ResponseEntity<>(myMap, HttpStatus.OK);
//		}
//
//		
//		
//		
//		
//		
//		
//		Boolean check3 = cfbondnocDtlRepository.checkInBondedHdeDtl(cid, bid, list.getNocNo(), list.getNocTransId());
//
//		System.out.println("check3______________________________________" + check3);
//
//		if (check3) {
//			idList.add("P00249");
//			idList.add("P00250");
//			idList.add("P00251");
//
//		//Cfinbondcrg firstInbond = null;
//		
//		Map<String, Object> myMap = new HashMap<>();
//
//			List<Cfinbondcrg> results = cfbondnocDtlRepository.getInBondingIdAndBoeNo(cid, bid, list.getNocTransId(),
//					list.getNocNo());
//			if (!results.isEmpty())
//
//			{
//				firstInbond = results.get(0);
//			} 
//			else 
//			{
//				return null; // Handle cases where no records are found
//			}
//
//			
//
//			System.out.println("inbondind________________________________" + firstInbond);
//			if (firstInbond != null) {
//				myMap.put("inBondId", firstInbond.getInBondingId());
//			}
//
//			myMap.put("list", list);
//			myMap.put("idList", idList);
//			myMap.put("firstInbond", firstInbond);
//
//			//return new ResponseEntity<>(myMap, HttpStatus.OK);
//		}
//
//		
//		
//		Boolean check4 = cfbondnocDtlRepository.checkInForGatePass(cid, bid, list.getNocNo(), list.getNocTransId());
//
//		System.out.println("check4______________________________________" + check4);
//
//		if (check4) 
//		{
//			idList.add("P00249");
//			idList.add("P00250");
//			idList.add("P00251");
//			idList.add("P00252");
//
//			Map<String, Object> myMap = new HashMap<>();
//
//			List<CfExBondCrg> results = cfbondnocDtlRepository.getExbondIdAndInbondId(cid, bid, list.getNocTransId(),
//					list.getNocNo());
//			if (!results.isEmpty())
//
//			{
//				firstExBond = results.get(0);
//			} 
//			else 
//			{
//				return null; // Handle cases where no records are found
//			}
//
//			
//
//			System.out.println("firstExBond________________________________" + firstExBond);
//		
//			myMap.put("list", list);
//			myMap.put("idList", idList);
//			myMap.put("firstInbond", firstInbond);
//			myMap.put("firstExBond", firstExBond);
//
//			//return new ResponseEntity<>(myMap, HttpStatus.OK);
//		}
//
//		Map<String, Object> myMap = new HashMap<>();
//
//		myMap.put("list", list);
//		myMap.put("idList", idList);
//		myMap.put("firstInbond", firstInbond);
//		myMap.put("firstExBond", firstExBond);
//
//		return new ResponseEntity<>(myMap, HttpStatus.OK);
//	}

	public ResponseEntity<List<CfBondNocDtl>> getCfBondNocDtl(String companyId, String branchId, String nocTransId,
			String nocNo) {
		List<CfBondNocDtl> result = cfbondnocDtlRepository.forMainSearhcOfGateInBondScreen(companyId, branchId,
				nocTransId, nocNo);
		if (result.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(result);
		}
	}

	public ResponseEntity<?> getForMainBondingSearch(String cid, String bid, String nocNo, String boeNo,
			String bondingNo) {

		List<String> idList = new ArrayList<>();
		Cfinbondcrg firstInbond = null;
		CfExBondCrg firstExBond = null;
		GateOut firstGateOut = null;
		CFBondGatePass firstGatePass = null;
		Map<String, Object> myMap = new HashMap<>();
		Cfbondnoc list = repository.getForBondingSearch(cid, bid, nocNo, boeNo, bondingNo);

		if (list == null) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		idList.add("P00248");

		Boolean check = cfbondnocDtlRepository.checkGateInPackages(cid, bid, list.getNocNo(), list.getNocTransId());

		System.out.println("check______________________________________" + check);
		if (check) {
			idList.add("P00249");

			myMap.put("list", list);
			myMap.put("idList", idList);

			// return new ResponseEntity<>(myMap,HttpStatus.OK) ;
		}

		Boolean check9 = cfbondnocDtlRepository.checkInBondedPackages(cid, bid, list.getNocNo(), list.getNocTransId());
		//
		System.out.println("check9______________________________________" + check9);
		if (check9) {
			idList.add("P00249");
			idList.add("P00250");

			myMap.put("list", list);
			myMap.put("idList", idList);
			// return new ResponseEntity<>(myMap, HttpStatus.OK);
		}

		Boolean check2 = cfbondnocDtlRepository.checkInBondedPackagesAllDone(cid, bid, list.getNocNo(),
				list.getNocTransId());

		System.out.println("check2______________________________________" + check2);
		if (check2) {
			idList.add("P00249");
			idList.add("P00250");

			myMap.put("list", list);
			myMap.put("idList", idList);

			// return new ResponseEntity<>(myMap, HttpStatus.OK);
		}

		
		Boolean check3 = cfbondnocDtlRepository.checkInBondedHdeDtl(cid, bid, list.getNocNo(), list.getNocTransId());

		System.out.println("check3______________________________________" + check3);

		if (check3) 
		{
			idList.add("P00249");
			idList.add("P00250");
			idList.add("P00251");

			Boolean check5 = cfbondnocDtlRepository.checkInForGatePass(cid, bid, list.getNocNo(), list.getNocTransId());

			System.out.println("check3 check5______________________________________" + check5);
			if (check5) {
				idList.add("P00252");
				idList.add("P00253");
				
			List<CFBondGatePass> result =cfbondnocDtlRepository.getCfBondGatePassList(cid, bid,list.getNocTransId(),list.getNocNo());
				 
			if (!result.isEmpty())

			{
				firstGatePass = result.get(0);
			} else {
				return null; // Handle cases where no records are found
			}

				

			}
			
			Boolean checkForQtyOut = cfbondnocDtlRepository.checkInForALLGatePass(cid, bid, list.getNocNo(),
					list.getNocTransId());

			if (checkForQtyOut) {
				idList.add("P00252");
				idList.add("P00253");
				
				List<CFBondGatePass> result =cfbondnocDtlRepository.getCfBondGatePassList(cid, bid,list.getNocTransId(),list.getNocNo());
				 
				if (!result.isEmpty())

				{
					firstGatePass = result.get(0);
				} else 
				{
					return null; // Handle cases where no records are found
				}

					 myMap.put("firstGatePass", firstGatePass);
				
			}

			List<Cfinbondcrg> results = cfbondnocDtlRepository.getInBondingIdAndBoeNo(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results.isEmpty())

			{
				firstInbond = results.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			

			List<CfExBondCrg> results1 = cfbondnocDtlRepository.getExbondIdAndInbondId(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results1.isEmpty())

			{
				firstExBond = results1.get(0);
			} else {
				return null; // Handle cases where no records are found
			}

			System.out.println("firstExBond________________________________" + firstExBond);

			myMap.put("list", list);
			myMap.put("idList", idList);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstExBond", firstExBond);
		    myMap.put("firstGatePass", firstGatePass);
			// return new ResponseEntity<>(myMap, HttpStatus.OK);
		}

		
		
		
		Boolean check4 = cfbondnocDtlRepository.checkExBondedPackagesAllDone(cid, bid, list.getNocNo(),
				list.getNocTransId());

		System.out.println("check4______________________________________" + check4);

		if (check4) 
		{
			idList.add("P00249");
			idList.add("P00250");
			idList.add("P00251");
			idList.add("P00252");

			List<CfExBondCrg> results = cfbondnocDtlRepository.getExbondIdAndInbondId(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results.isEmpty())

			{
				firstExBond = results.get(0);
			} else {
				return null; // Handle cases where no records are found
			}

			List<Cfinbondcrg> results1 = cfbondnocDtlRepository.getInBondingIdAndBoeNo(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results1.isEmpty())

			{
				firstInbond = results1.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			System.out.println("firstExBond________________________________" + firstExBond);
			
			
			
			myMap.put("list", list);
			myMap.put("idList", idList);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstExBond", firstExBond);

		
		}

	// check letter its alredy prsent in check3 loop 	
		
		Boolean check5 = cfbondnocDtlRepository.checkInForGatePass(cid, bid, list.getNocNo(), list.getNocTransId());

		System.out.println("check45______________________________________" + check5);

		if (check5) {
			idList.add("P00249");
			idList.add("P00250");
			idList.add("P00251");
			idList.add("P00252");
			
			
			List<CfExBondCrg> results = cfbondnocDtlRepository.getExbondIdAndInbondId(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results.isEmpty())

			{
				firstExBond = results.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			
			List<Cfinbondcrg> results1 = cfbondnocDtlRepository.getInBondingIdAndBoeNo(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results1.isEmpty())

			{
				firstInbond = results1.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			System.out.println("firstExBond________________________________" + firstExBond);
			
			

		
			myMap.put("list", list);
			myMap.put("idList", idList);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstExBond", firstExBond);
			// return new ResponseEntity<>(myMap, HttpStatus.OK);
		}
		
		
		
		Boolean check54 = cfbondnocDtlRepository.checkInForGatePassOut(cid, bid, list.getNocNo(), list.getNocTransId());

		System.out.println("check454______________________________________" + check54);

		if (check54) {
			idList.add("P00249");
			idList.add("P00250");
			idList.add("P00251");
			idList.add("P00252");
			idList.add("P00253");
			List<CfExBondCrg> results = cfbondnocDtlRepository.getExbondIdAndInbondId(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results.isEmpty())

			{
				firstExBond = results.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			
			List<Cfinbondcrg> results1 = cfbondnocDtlRepository.getInBondingIdAndBoeNo(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results1.isEmpty())

			{
				firstInbond = results1.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			System.out.println("firstExBond________________________________" + firstExBond);
			
			List<CFBondGatePass> result =cfbondnocDtlRepository.getCfBondGatePassList(cid, bid,list.getNocTransId(),list.getNocNo());
			 
			if (!result.isEmpty())

			{
				firstGatePass = result.get(0);
			} else {
				return null; // Handle cases where no records are found
			}


		
			myMap.put("list", list);
			myMap.put("idList", idList);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstExBond", firstExBond);
			myMap.put("firstGatePass", firstGatePass);
			// return new ResponseEntity<>(myMap, HttpStatus.OK);
		}
		
		Boolean checkPass = cfbondnocDtlRepository.checkInForALLGatePassOut(cid, bid, list.getNocNo(), list.getNocTransId());

		System.out.println("checkPass______________________________________" + checkPass);

		if (checkPass) {
			idList.add("P00249");
			idList.add("P00250");
			idList.add("P00251");
			idList.add("P00252");
			idList.add("P00253");
			List<CfExBondCrg> results = cfbondnocDtlRepository.getExbondIdAndInbondId(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results.isEmpty())

			{
				firstExBond = results.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			
			List<Cfinbondcrg> results1 = cfbondnocDtlRepository.getInBondingIdAndBoeNo(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results1.isEmpty())

			{
				firstInbond = results1.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			System.out.println("firstExBond________________________________" + firstExBond);
			
			List<CFBondGatePass> result =cfbondnocDtlRepository.getCfBondGatePassList(cid, bid,list.getNocTransId(),list.getNocNo());
			 
			if (!result.isEmpty())

			{
				firstGatePass = result.get(0);
			} else {
				return null; // Handle cases where no records are found
			}


		
			myMap.put("list", list);
			myMap.put("idList", idList);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstExBond", firstExBond);
			myMap.put("firstGatePass", firstGatePass);
			// return new ResponseEntity<>(myMap, HttpStatus.OK);
		}
		
		
		
		Boolean check55 = cfbondnocDtlRepository.checkInForGateGateOut(cid, bid, list.getNocNo(), list.getNocTransId());

		System.out.println("ccheck55______________________________________" + check55);

		if (check55) {
			idList.add("P00249");
			idList.add("P00250");
			idList.add("P00251");
			idList.add("P00252");
			idList.add("P00253");
			
			List<Cfinbondcrg> results1 = cfbondnocDtlRepository.getInBondingIdAndBoeNo(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results1.isEmpty())

			{
				firstInbond = results1.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			
			List<CfExBondCrg> results111 = cfbondnocDtlRepository.getExbondIdAndInbondId(cid, bid, list.getNocTransId(),
					list.getNocNo());
			if (!results111.isEmpty())

			{
				firstExBond = results111.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			
			List<CFBondGatePass> result =cfbondnocDtlRepository.getCfBondGatePassList(cid, bid,list.getNocTransId(),list.getNocNo());
			 
			if (!result.isEmpty())

			{
				firstGatePass = result.get(0);
			} else {
				return null; // Handle cases where no records are found
			}

			
			List<GateOut> results11 = cfbondnocDtlRepository.getDetailsInMainSearch(cid, bid, 
					list.getNocNo(),list.getNocTransId());
			if (!results11.isEmpty())

			{
				firstGateOut = results11.get(0);
			} else {
				return null; // Handle cases where no records are found
			}


			System.out.println("firstExBond________________________________" + firstGateOut);

			myMap.put("list", list);
			myMap.put("idList", idList);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstExBond", firstExBond);
			myMap.put("firstGatePass", firstGatePass);
			myMap.put("firstGateOut", firstGateOut);
			// return new ResponseEntity<>(myMap, HttpStatus.OK);
		}
		return new ResponseEntity<>(myMap, HttpStatus.OK);
	}
}
