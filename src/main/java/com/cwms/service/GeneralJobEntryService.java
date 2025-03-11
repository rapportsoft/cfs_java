package com.cwms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.Cfbondinsbal;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.GeneralJobOrderEntryDetails;
import com.cwms.entities.GenerelJobEntry;
import com.cwms.entities.Party;
import com.cwms.repository.GeneralJobEntryDetailsRepository;
import com.cwms.repository.GeneralJobEntryRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeneralJobEntryService {
	
	@Autowired
	private GeneralJobEntryRepository jobEntryRepo;
	
	@Autowired
	private GeneralJobEntryDetailsRepository jobEntryDetailsRepo;
	
	@Autowired
	private ProcessNextIdRepository processNextIdRepository;
	
	public List<Party> findParties(String companyId, String branchId, String partyName) {
		return jobEntryRepo.findPartyDTOByPartyId(companyId, branchId, partyName);
	}

	public List<GenerelJobEntry> getCfbondnocData(String companyId, String branchId, String partyName) {
		return jobEntryRepo.findCfbondnocByCompanyIdAndBranchId(companyId, branchId, partyName);
	}
	
	public List<GeneralJobOrderEntryDetails> getCfBondNocDtlForNocScreen(String companyId, String branchId, String nocTransId,
			String nocNo) {
		return jobEntryRepo.getCfBondNocDtlForNocScreen(companyId, branchId, nocTransId, nocNo);
	}
	
	public GenerelJobEntry getCfbondnocDataByidOrSearch(String companyId, String branchId, String transId, String nocNo) {
		return jobEntryRepo.findCfbondnocByCompanyIdAndBranchIdOrSerach(companyId, branchId, transId, nocNo);
	}
	
	
	
	
	
	
	public GeneralJobOrderEntryDetails getDataCfBondDtlById(String companyId, String branchId, String dtlid, String trasnId,
			String nocId) {
		return jobEntryRepo.getDataOfDtlIdEdit(companyId, branchId, dtlid, trasnId, nocId);
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

		GenerelJobEntry cfBondNoc = objectMapper.convertValue(requestBody.get("noc"), GenerelJobEntry.class);

		GeneralJobOrderEntryDetails bondnocDtl = objectMapper.convertValue(requestBody.get("nocDtl"), GeneralJobOrderEntryDetails.class);

//		String HoldNextIdD1=null;
		System.out.println("bondnocDtl______________________________________" + bondnocDtl);

		if (cfBondNoc != null) {
			if ("add".equals(flag)) {
				Boolean isExist = jobEntryRepo.isDataExist(cid, bid, cfBondNoc.getJobNo());

				if (isExist) {
					return new ResponseEntity<>("Duplicate Job entry no", HttpStatus.BAD_REQUEST);
				}

				String holdId1 = processNextIdRepository.findAuditTrail(cid, bid, "PJOB91", "2025");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("JOBE%06d", nextNumericNextID1);

				String lastValue = processNextIdRepository.findAuditTrail(cid, bid, "PJOB92", "2025");

				// Get the current date
				LocalDate currentDate = LocalDate.now();

				// Determine the year part based on the current date
				String yearPart = determineYearPart(currentDate);

				// Increment the last value
				String nextValue = incrementTPNo(lastValue, yearPart);
				System.out.println("nextValue______________________" + nextValue);

				cfBondNoc.setCompanyId(cid);
				cfBondNoc.setJobNo(nextValue);
				cfBondNoc.setJobTransId(HoldNextIdD1);
				cfBondNoc.setBranchId(bid);
				cfBondNoc.setStatus("A");
				cfBondNoc.setCreatedBy(user);
				cfBondNoc.setCreatedDate(new Date());
				cfBondNoc.setApprovedBy(user);
				cfBondNoc.setApprovedDate(new Date());

				if (bondnocDtl != null) {
					cfBondNoc.setNoOfPackages(bondnocDtl.getNoOfPackages());
					cfBondNoc.setGrossWeight(bondnocDtl.getGrossWeight());
					cfBondNoc.setArea(bondnocDtl.getAreaOccupied());
				}

			GenerelJobEntry saved=jobEntryRepo.save(cfBondNoc);

			if(saved!=null)
			{
				processNextIdRepository.updateAuditTrail(cid, bid, "PJOB91", HoldNextIdD1, "2025");

				processNextIdRepository.updateAuditTrail(cid, bid, "PJOB92", nextValue, "2025");
			}
				
				

				String holdId = processNextIdRepository.findAuditTrail(cid, bid, "PJOB90", "2025");

				int lastNextNumericId = Integer.parseInt(holdId.substring(4));

				int nextNumericNextID = lastNextNumericId + 1;

				String HoldNextIdD = String.format("J%05d", nextNumericNextID);

				bondnocDtl.setCompanyId(cid);
				bondnocDtl.setBoeNo(cfBondNoc.getBoeNo());
				bondnocDtl.setJobTransId(cfBondNoc.getJobTransId());
				bondnocDtl.setJobDtlTransId(HoldNextIdD);
				bondnocDtl.setJobNo(cfBondNoc.getJobNo());
				bondnocDtl.setCommodityId(bondnocDtl.getCommodityId());
				bondnocDtl.setBranchId(bid);
				bondnocDtl.setStatus("A");
				bondnocDtl.setCreatedBy(user);
				bondnocDtl.setCreatedDate(new Date());
				bondnocDtl.setApprovedBy(user);
				bondnocDtl.setJobTransDate(new Date());
				bondnocDtl.setApprovedDate(new Date());
				jobEntryDetailsRepo.save(bondnocDtl);
				processNextIdRepository.updateAuditTrail(cid, bid, "PJOB90", HoldNextIdD, "2025");

				return new ResponseEntity<>(cfBondNoc, HttpStatus.OK);

			} else

			{

				Boolean isExist = jobEntryRepo.isDataExist1(cid, bid, cfBondNoc.getJobNo(),
						cfBondNoc.getJobTransId());

				GeneralJobOrderEntryDetails existing = jobEntryRepo.getDataOfDtlId(cid, bid, bondnocDtl.getJobDtlTransId(),
						bondnocDtl.getJobTransId(), bondnocDtl.getJobNo());


				if (isExist) {
					return new ResponseEntity<>("Duplicate Job of entry no", HttpStatus.BAD_REQUEST);
				}

				cfBondNoc.setArea(cfBondNoc.getArea().add(bondnocDtl.getAreaOccupied()));
				cfBondNoc.setNoOfPackages(cfBondNoc.getNoOfPackages().add(bondnocDtl.getNoOfPackages()));
				cfBondNoc.setGrossWeight(cfBondNoc.getGrossWeight().add(bondnocDtl.getGrossWeight()));
				cfBondNoc.setEditedBy(user);
				cfBondNoc.setEditedDate(new Date());

				jobEntryRepo.save(cfBondNoc);
				
				if (existing != null) {

					existing.setTypeOfPackage(bondnocDtl.getTypeOfPackage());
					existing.setCommodityDescription(bondnocDtl.getCommodityDescription());
					existing.setNoOfPackages(bondnocDtl.getNoOfPackages());
					existing.setGrossWeight(bondnocDtl.getGrossWeight());
					existing.setCommodityId(bondnocDtl.getCommodityId());
					existing.setEditedBy(user);
					existing.setEditedDate(new Date());
					jobEntryDetailsRepo.save(existing);
				} 
				
				else 
				
				{
					GeneralJobOrderEntryDetails bondnocDtl1 = new GeneralJobOrderEntryDetails();

					String holdId = processNextIdRepository.findAuditTrail(cid, bid, "PJOB90", "2025");

					int lastNextNumericId = Integer.parseInt(holdId.substring(4));

					int nextNumericNextID = lastNextNumericId + 1;

					String HoldNextIdD = String.format("J%05d", nextNumericNextID);

					bondnocDtl1.setCompanyId(cid);
					bondnocDtl1.setBoeNo(cfBondNoc.getBoeNo());
					bondnocDtl1.setJobTransId(cfBondNoc.getJobTransId());
					bondnocDtl1.setJobDtlTransId(HoldNextIdD);
					bondnocDtl1.setJobNo(cfBondNoc.getJobNo());
					bondnocDtl1.setBranchId(bid);
					bondnocDtl1.setAreaOccupied(bondnocDtl.getAreaOccupied());
					bondnocDtl1.setCommodityId(bondnocDtl.getCommodityId());
					bondnocDtl1.setTypeOfPackage(bondnocDtl.getTypeOfPackage());
					bondnocDtl1.setCommodityDescription(bondnocDtl.getCommodityDescription());
					bondnocDtl1.setNoOfPackages(bondnocDtl.getNoOfPackages());
					bondnocDtl1.setGrossWeight(bondnocDtl.getGrossWeight());
					bondnocDtl1.setStatus("A");
					bondnocDtl1.setCreatedBy(user);
					bondnocDtl1.setCreatedDate(new Date());
					bondnocDtl1.setApprovedBy(user);
					bondnocDtl1.setJobTransDate(new Date());
					bondnocDtl1.setApprovedDate(new Date());
					jobEntryDetailsRepo.save(bondnocDtl1);
					processNextIdRepository.updateAuditTrail(cid, bid, "PJOB90", HoldNextIdD, "2025");
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
	public ResponseEntity<?> saveDataOfCfBondNoc(String cid, String bid, String user, String flag,
			GenerelJobEntry cfBondNoc) {
		if (cfBondNoc != null) {
			   Boolean isExist = jobEntryRepo.isDataExist1(cid, bid, cfBondNoc.getJobNo(),
					cfBondNoc.getJobTransId());
				if (isExist) {
					return new ResponseEntity<>("Duplicate Job of entry no", HttpStatus.BAD_REQUEST);
				}


//				Cfbondnoc existingCfBondNoc = cfbondnocRepository.getDataOfDtlId(cid, bid, cfBondNoc.getNocTransId(),
//						cfBondNoc.getNocNo());

				cfBondNoc.setEditedBy(user);
				cfBondNoc.setEditedDate(new Date());
				jobEntryRepo.save(cfBondNoc);

				return new ResponseEntity<>(cfBondNoc, HttpStatus.OK);

		} else {
			return new ResponseEntity<>("cfBondNoc data not found", HttpStatus.BAD_REQUEST);
		}

	}
}
