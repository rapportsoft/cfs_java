package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.CfExBondGrid;
import com.cwms.entities.CfInBondGrid;
import com.cwms.entities.Cfbondinsbal;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.CfexBondCrgDtl;
import com.cwms.entities.CfexbondcrgEdit;
import com.cwms.entities.CfinbondCommdtlEdit;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.entities.CfinbondcrgHDR;
import com.cwms.entities.CfinbondcrgHDRDtl;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.CfBondNocDtlRepository;
import com.cwms.repository.CfExBondCrgDtlRepository;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfExBondGridRepository;
import com.cwms.repository.CfInBondGridRepository;
import com.cwms.repository.CfbondnocRepository;
import com.cwms.repository.CfexbondcrgEditRepository;
import com.cwms.repository.CfinbondCommdtlEditRepository;
import com.cwms.repository.CfinbondCrgHdrDtlRepo;
import com.cwms.repository.CfinbondCrgHdrRepo;
import com.cwms.repository.CfinbondcrgDtlRepo;
import com.cwms.repository.CfinbondcrgRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.YardBlockCellRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CfexbondcrgEditService {

	@Autowired
	public CfexbondcrgEditRepository cfexbondcrgEditRepository;

	@Autowired
	public CfinbondcrgRepo cfinbondcrgRepo;

	@Autowired
	public CfinbondcrgDtlRepo cfinbondcrgDtlRepo;

	@Autowired
	private ProcessNextIdRepository processNextIdRepository;

	@Autowired
	private CfinbondCrgHdrRepo cfinbondCrgHdrRepo;

	@Autowired
	private CfinbondCrgHdrDtlRepo cfinbondCrgHdrDtlRepo;

	@Autowired
	private CfBondNocDtlRepository cfBondNocDtlRepository;

	@Autowired
	private CfbondnocRepository cfbondnocRepository;

	@Autowired
	public CfInBondGridRepository cfInBondGridRepository;

	@Autowired
	public CfinbondCommdtlEditRepository cfinbondCommdtlEditRepository;

	@Autowired
	public YardBlockCellRepository yardBlockCellRepository;
	

	@Autowired
	private CfinbondcrgDtlRepo cfexbondcrgDtlRepo;

	@Autowired
	private CfExBondCrgDtlRepository cfExBondCrgDtlRepository;
	
	@Autowired
	private CfExBondGridRepository cfExBondGridRepository;
	
	
	@Autowired
	private CfExBondCrgRepository cfExBondCrgRepository;
	
	
	@Autowired
	private CfBondNocDtlRepository cfbondnocDtlRepository;
	
	
	
	
	
	
	
	


	public ResponseEntity<List<Cfinbondcrg>> getDataForInBondAuditTrailScreen(String compnayId, String branchId,
			String boeNo) {

		List<Cfinbondcrg> data = cfexbondcrgEditRepository.getDataUsingBoeNoForInBondAuditTrailScreen(compnayId,
				branchId, boeNo);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public ResponseEntity<List<CfExBondCrg>> getDataForExBondAuditTrailScreen(String companyId, String branchId,
			String boeNo) {
		List<CfExBondCrg> data = cfexbondcrgEditRepository.getDataForExBondAuditTrailScreen(companyId, branchId, boeNo);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public ResponseEntity<List<CfinbondcrgDtl>> getDataFromCfInBondCrgDtl(String compnayId, String branchId,
			String inBondingId, String nocTrandId) {

		List<CfinbondcrgDtl> data = cfexbondcrgEditRepository.getDataOfCfInBodnCrgDtl(compnayId, branchId, inBondingId,
				nocTrandId);

		System.out.println("data_________________________________" + data);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public ResponseEntity<List<CfinbondCommdtlEdit>> findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
			String compnayId, String branchId, String inBondingId, String nocTrandId) {

		List<CfinbondCommdtlEdit> data = cfexbondcrgEditRepository
				.findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(compnayId, branchId, inBondingId,
						nocTrandId);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	public ResponseEntity<List<CfinbondCommdtlEdit>> getForExBondDtl(
			String compnayId, String branchId, String inBondingId, String nocTrandId) {

		List<CfinbondCommdtlEdit> data = cfexbondcrgEditRepository
				.getForExBondDtl(compnayId, branchId, inBondingId,
						nocTrandId);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	

	public ResponseEntity<List<CfexBondCrgDtl>> getFromCfExBondCrgDtlToChange(String compnayId, String branchId,
			String nocTrandId, String inBondingId, String exBondBeNo, String exBondingId) {

		List<CfexBondCrgDtl> data = cfexbondcrgEditRepository.getDataForExBondAuditTrailToUpdate(compnayId, branchId,
				nocTrandId, inBondingId, exBondBeNo, exBondingId);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public CfexbondcrgEdit getCfInbondCrgDataByidOrSearch(String companyId, String branchId, Long srNo, String transId,
			String inBondingId, String nocNo) {
		return cfexbondcrgEditRepository.getSavedData(companyId, branchId, srNo, transId, inBondingId, nocNo);
	}

	
	public CfexbondcrgEdit getBySelectingRadioButton(String companyId, String branchId, Long srNo, String transId,
			String exBondingId, String nocNo) {
		return cfexbondcrgEditRepository.getBySelectingRadioButton(companyId, branchId, srNo, transId, exBondingId, nocNo);
	}
	
	
	public List<CfexbondcrgEdit> findAllCfinbondCrgIn(String companyId, String branchId, String search) {
		return cfexbondcrgEditRepository.findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(companyId, branchId,
				search);
	}
	
	
	public List<CfexbondcrgEdit> getForExBondAuditTrailScreen(String companyId, String branchId, String search) {
		return cfexbondcrgEditRepository.getDataAfterSaveForExBondAuditTrailScreen(companyId, branchId,
				search);
	}

	public BigDecimal getSumOfInBondPackagesForCommodity(String companyId, String branchId, String inBondingId,
			String cfBondDtlId, String nocTransId) {
		return cfexbondcrgEditRepository.getSumOfInBondPackagesForCommodity(companyId, branchId, inBondingId,
				cfBondDtlId, nocTransId);
	}

	@Transactional
	public ResponseEntity<?> saveDataOfCfInbondCrgEditAuditTrail(String companyId, String branchId, String user,
			String flag, Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		CfexbondcrgEdit cfinbondcrg = object.convertValue(requestBody.get("inBond"), CfexbondcrgEdit.class);

		Object nocDtlObj = requestBody.get("nocDtl");
		List<CfinbondCommdtlEdit> cfinbondcrgDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			cfinbondcrgDtlList = object.convertValue(nocDtlObj, new TypeReference<List<CfinbondCommdtlEdit>>() {
			});

		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				CfinbondCommdtlEdit cfinbondcrgDtl = object.convertValue(entry.getValue(), CfinbondCommdtlEdit.class);
				cfinbondcrgDtlList.add(cfinbondcrgDtl);
			}
		} else {
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		System.out.println("cfinbondcrg________________" + cfinbondcrg);

		System.out.println("cfinbondcrgDtlList________________" + cfinbondcrgDtlList);

		CfInBondGrid saved = null;

		CfexbondcrgEdit savedEdit = null;
		
		if(cfinbondcrg.getInBondingId()==null || cfinbondcrg.getInBondingId().isEmpty() || cfinbondcrg.getInBondingId().isEmpty())
		{
			return new ResponseEntity<>("Please Select Boe No to save data.....",HttpStatus.BAD_REQUEST);
		}

		if (cfinbondcrg != null)
		{
			cfinbondcrg.setApprovedBy(user);
			cfinbondcrg.setCreatedBy(user);
			cfinbondcrg.setCreatedDate(new Date());
			cfinbondcrg.setApprovedDate(new Date());
			
			if(cfinbondcrgDtlList == null || cfinbondcrgDtlList.isEmpty() )
			{
				cfinbondcrg.setStatus("A");
			}else
			{
				cfinbondcrg.setStatus("N");
			}

			savedEdit = cfexbondcrgEditRepository.save(cfinbondcrg);
			
			if(savedEdit!=null)
			{
				String boeNo9 = getValidString(savedEdit.getBoeNo(), savedEdit.getBoeNoOld());
	
				String bondingNo9 = getValidString(savedEdit.getBondingNo(), savedEdit.getBondingNoOld());
				
				Date bondingDate9 = getValidDate(savedEdit.getBondingDate(), savedEdit.getBondingDateOld());
				
				int updateNocDtl = cfBondNocDtlRepository.updateNocDtlAfterAuditTrailHeaderChange(bondingNo9,
						boeNo9,
						companyId,branchId,savedEdit.getNocTransId(),savedEdit.getNocNo());
				
				System.out.println("updateNocDtl________________________________"+updateNocDtl);
				
				
				int updateCfInBondCrgDtl = cfinbondcrgDtlRepo.updateCfinbondCrgDtlAfterInBondAuditTrailHeaderChange(boeNo9,bondingNo9,
						bondingDate9,
						companyId,branchId,savedEdit.getInBondingId(),savedEdit.getNocNo(),savedEdit.getNocTransId());
				
				System.out.println("updateCfInBondCrgDtl________________________________"+updateCfInBondCrgDtl);
				
				
				CfinbondcrgHDR findCfBondCrgHDRData = null;
				CfinbondcrgHDRDtl findCfBondCrgHDRDTLData = null;
				CfinbondcrgDtl findCfBondCrgDTLData = null;
				Cfinbondcrg findCfBondCrgData = cfinbondcrgRepo.findCfinbondCrg(companyId, branchId,
						cfinbondcrg.getNocTransId(), cfinbondcrg.getInBondingId(), cfinbondcrg.getNocNo());

				System.out.println("in edit");
				BigDecimal totalInbondedd = BigDecimal.ZERO;
				BigDecimal totalCif = BigDecimal.ZERO;
				BigDecimal totalCargo = BigDecimal.ZERO;
				BigDecimal totalInsurance = BigDecimal.ZERO;
				BigDecimal totalWeight = BigDecimal.ZERO;
				BigDecimal totalArea = BigDecimal.ZERO;
				
				
				
				if(cfinbondcrgDtlList!=null)
				{
					for (CfinbondCommdtlEdit item : cfinbondcrgDtlList) 
					{
						String boeNoq = getValidString(savedEdit.getBoeNo(), savedEdit.getBoeNoOld());
						
						totalCif = totalCif.add(item.getNewBondCifValue());
						totalInbondedd = totalInbondedd.add(item.getNewBondPackages());
						totalCargo = totalCargo.add(item.getNewBondCargoDuty());
						totalInsurance = totalInsurance.add(item.getNewInsuranceValue());
						totalWeight = totalWeight.add(item.getNewBondGrossWt());
						totalArea = totalArea.add(item.getNewAreaOccupied());

						findCfBondCrgDTLData = cfinbondcrgDtlRepo.findCfBondCrgDTLData(companyId, branchId,
								item.getCommonBondingId(), item.getNocTransId(), item.getCommodityId(), item.getNocNo());

						CfinbondCommdtlEdit crgDetails = new CfinbondCommdtlEdit();

						crgDetails.setSrNo(savedEdit.getSrNo());
						crgDetails.setCreatedBy(user);
						crgDetails.setCreatedDate(new Date());
						crgDetails.setApprovedBy(user);
						crgDetails.setApprovedDate(new Date());
						crgDetails.setBoeNo(boeNoq);
						crgDetails.setBranchId(branchId);
						crgDetails.setCompanyId(companyId);
						crgDetails.setCommonBondingId(savedEdit.getInBondingId());
						crgDetails.setStatus("A");

						crgDetails.setCommodityId(item.getCommodityId());

						crgDetails.setNewBreakage(item.getNewBreakage());
						crgDetails.setNewDamagedQty(item.getNewDamagedQty());
						crgDetails.setNewShortagePackages(item.getNewShortagePackages());

						crgDetails.setOldDamagedQty(item.getOldDamagedQty());
						crgDetails.setOldBreakage(item.getOldBreakage());
						crgDetails.setOldShortagePackages(item.getOldShortagePackages());

						crgDetails.setOldAreaOccupied(item.getOldAreaOccupied());
						crgDetails.setNewAreaOccupied(item.getNewAreaOccupied());

						crgDetails.setYardLocationId(item.getYardLocationId());
						crgDetails.setBlockId(item.getBlockId());
						crgDetails.setCellNoRow(item.getCellNoRow());

						crgDetails.setOldYardPackages(item.getOldYardPackages());
						crgDetails.setNewYardPackages(item.getNewYardPackages());

						crgDetails.setNewBondCargoDuty(item.getNewBondCargoDuty());
						crgDetails.setNewBondCifValue(item.getNewBondCifValue());
						crgDetails.setNewBondPackages(item.getNewBondPackages());
						crgDetails.setNewBondGrossWt(item.getNewBondGrossWt());
						crgDetails.setNewInsuranceValue(item.getNewInsuranceValue());

						crgDetails.setOldBondCifValue(item.getOldBondCifValue());
						crgDetails.setOldBondCargoDuty(item.getOldBondCargoDuty());
						crgDetails.setOldInsuranceValue(item.getOldInsuranceValue());
						crgDetails.setOldBondGrossWt(item.getOldBondGrossWt());
						crgDetails.setOldBondPackages(item.getOldBondPackages());
						crgDetails.setCommodityId(item.getCommodityId());
						crgDetails.setNewCommodityDescription(item.getOldCommodityDescription());
						crgDetails.setOldCommodityDescription(item.getOldCommodityDescription());

						crgDetails.setOldTypeOfPackage(item.getOldTypeOfPackage());
						crgDetails.setNewTypeOfPackage(item.getOldTypeOfPackage());

						crgDetails.setCellAreaAllocated(item.getCellAreaAllocated());
						crgDetails.setNocNo(savedEdit.getNocNo());
						crgDetails.setNocTransId(savedEdit.getNocTransId());

						if (savedEdit.getBoeNo().isEmpty() || savedEdit.getBoeNo().isBlank() || savedEdit.getBoeNo() == null) {
							crgDetails.setBoeNo(savedEdit.getBoeNoOld());
						} else {
							crgDetails.setBoeNo(savedEdit.getBoeNo());
						}

						if (savedEdit.getBondingNo().isEmpty() || savedEdit.getBondingNo().isBlank()
								|| savedEdit.getBondingNo() == null) {
							crgDetails.setBondingNo(savedEdit.getBondingNoOld());
						} else {
							crgDetails.setBondingNo(savedEdit.getBondingNo());
						}

						crgDetails.setType("INBOND");

						CfinbondCommdtlEdit savedDtl = cfinbondCommdtlEditRepository.save(crgDetails);

						if (savedDtl != null) 
						{

							CfBondNocDtl getDataOfDtlId = cfBondNocDtlRepository.getDataOfDtlId(companyId, branchId,
									item.getCommodityId(), item.getNocTransId(), item.getNocNo());

							if (getDataOfDtlId != null) {
								int updateNoCDtl = cfBondNocDtlRepository.updateNocDtlAfterAuditTrail(
										getDataOfDtlId.getInBondedPackages()
												.add(savedDtl.getNewBondPackages().subtract(savedDtl.getOldBondPackages())),

										getDataOfDtlId.getInbondGrossWt()
												.add(savedDtl.getNewBondGrossWt().subtract(savedDtl.getOldBondGrossWt())),
										getDataOfDtlId.getInbondInsuranceValue()
												.add(savedDtl.getNewInsuranceValue().subtract(savedDtl.getOldInsuranceValue())),

										getDataOfDtlId.getInbondCifValue()
												.add(savedDtl.getNewBondCifValue().subtract(savedDtl.getOldBondCifValue())),

										getDataOfDtlId.getInbondCargoDuty()
												.add(savedDtl.getNewBondCargoDuty().subtract(savedDtl.getOldBondCargoDuty())),

										getDataOfDtlId.getShortagePackages().add(
												savedDtl.getNewShortagePackages().subtract(savedDtl.getOldShortagePackages())),
										getDataOfDtlId.getDamagedQty()
												.add(savedDtl.getNewDamagedQty().subtract(savedDtl.getOldDamagedQty())),
										getDataOfDtlId.getBreakage()
												.add(savedDtl.getNewBreakage().subtract(savedDtl.getOldBreakage())),
										savedDtl.getBondingNo(), savedDtl.getBoeNo(), companyId, branchId,
										savedDtl.getNocTransId(), savedDtl.getNocNo(), savedDtl.getCommodityId());

								System.out.println(
										"updateNoCDtl__________Row___In__EXits_NOC______________________Dtl" + updateNoCDtl);
							}

							CfInBondGrid toEditData = cfInBondGridRepository.toEditData(companyId, branchId,
									savedDtl.getCommonBondingId(), savedDtl.getCommodityId(), savedDtl.getYardLocationId(),
									savedDtl.getBlockId(), savedDtl.getCellNoRow());

							if (toEditData != null) {
								toEditData.setInBondPackages(toEditData.getInBondPackages().add(savedDtl.getNewYardPackages())
										.subtract(savedDtl.getOldYardPackages()));
								toEditData.setCellAreaAllocated(
										toEditData.getCellAreaAllocated().add(savedDtl.getCellAreaAllocated())
												.subtract(findCfBondCrgDTLData.getCellAreaAllocated()));

								CfInBondGrid savedGrid = cfInBondGridRepository.save(toEditData);

								if (savedGrid != null) {
									YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
											savedGrid.getYardLocation(), savedGrid.getYardBlock(), savedGrid.getBlockCellNo());

									if (existingCell != null) {
										existingCell.setCellAreaUsed(existingCell.getCellAreaUsed() != null
												? existingCell.getCellAreaUsed()
												: BigDecimal.ZERO.add(savedGrid.getCellAreaAllocated() != null
														? savedGrid.getCellAreaAllocated()
														: BigDecimal.ZERO).subtract(toEditData.getCellAreaAllocated()));
										yardBlockCellRepository.save(existingCell);
									}
								}
							}

							if (findCfBondCrgDTLData != null) {
								
								BigDecimal yardPackages = getValidBigDecimal(savedDtl.getNewYardPackages(), savedDtl.getOldYardPackages());
								
								findCfBondCrgDTLData.setInBondedPackages(savedDtl.getNewBondPackages());
								findCfBondCrgDTLData.setInbondCargoDuty(savedDtl.getNewBondCargoDuty());
								findCfBondCrgDTLData.setInbondCifValue(savedDtl.getNewBondCifValue());
								findCfBondCrgDTLData.setInbondInsuranceValue(savedDtl.getNewInsuranceValue());
								findCfBondCrgDTLData.setShortagePackages(savedDtl.getNewShortagePackages());
								findCfBondCrgDTLData.setBreakage(savedDtl.getNewBreakage());
								findCfBondCrgDTLData.setDamagedQty(savedDtl.getNewDamagedQty());

								findCfBondCrgDTLData.setYardPackages(yardPackages);
								findCfBondCrgDTLData.setCellAreaAllocated(savedDtl.getCellAreaAllocated());

								if (savedEdit.getBoeNo().isEmpty() || savedEdit.getBoeNo().isBlank()
										|| savedEdit.getBoeNo() == null) {
									findCfBondCrgDTLData.setBoeNo(savedEdit.getBoeNoOld());
								} else {
									findCfBondCrgDTLData.setBoeNo(savedEdit.getBoeNo());
								}

								if (savedEdit.getBondingNo().isEmpty() || savedEdit.getBondingNo().isBlank()
										|| savedEdit.getBondingNo() == null) {
									findCfBondCrgDTLData.setBondingNo(savedEdit.getBondingNoOld());
								} else {
									findCfBondCrgDTLData.setBondingNo(savedEdit.getBondingNo());
								}

								if (savedEdit.getBondingDate() == null) {
									findCfBondCrgDTLData.setBondingDate(savedEdit.getBondingDateOld());
								} else

								{
									findCfBondCrgDTLData.setBondingDate(savedEdit.getBondingDate());
								}

								
								findCfBondCrgHDRDTLData = cfinbondCrgHdrDtlRepo.findCfBondCrgHDRDTLData(companyId, branchId,
										findCfBondCrgDTLData.getNocTransId(), findCfBondCrgDTLData.getInBondingDtlId(),
										findCfBondCrgDTLData.getNocNo());

								System.out
										.println("findCfBondCrgDTLData.getNocTransId()" + findCfBondCrgDTLData.getNocTransId());
								System.out.println(
										"findCfBondCrgDTLData.getInBondingDtlId()" + findCfBondCrgDTLData.getInBondingDtlId());
								System.out.println("findCfBondCrgDTLData.getNocNo()" + findCfBondCrgDTLData.getNocNo());
								System.out
										.println("findCfBondCrgDTLData.getNocTransId()" + findCfBondCrgDTLData.getNocTransId());
								if (findCfBondCrgHDRDTLData != null) {

									String boeNo = getValidString(savedEdit.getBoeNo(), savedEdit.getBoeNoOld());
									Date bondingDate = getValidDate(savedEdit.getBondingDate(), savedEdit.getBondingDateOld());
									String bondingNo = getValidString(savedEdit.getBondingNo(), savedEdit.getBondingNoOld());

									findCfBondCrgHDRDTLData.setBondingNo(bondingNo);
									findCfBondCrgHDRDTLData.setBondingDate(bondingDate);
									findCfBondCrgHDRDTLData.setBoeNo(boeNo);

									findCfBondCrgHDRDTLData.setInBondedPackages(Optional
											.ofNullable(findCfBondCrgHDRDTLData.getInBondedPackages()).orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInBondedPackages())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewBondPackages()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRDTLData.setInbondCargoDuty(Optional
											.ofNullable(findCfBondCrgHDRDTLData.getInbondCargoDuty()).orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondCargoDuty())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewBondCargoDuty()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRDTLData.setInbondCifValue(Optional
											.ofNullable(findCfBondCrgHDRDTLData.getInbondCifValue()).orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondCifValue())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewBondCifValue()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRDTLData.setInbondInsuranceValue(Optional
											.ofNullable(findCfBondCrgHDRDTLData.getInbondInsuranceValue())
											.orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondInsuranceValue())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewInsuranceValue()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRDTLData.setShortagePackages(Optional
											.ofNullable(findCfBondCrgHDRDTLData.getShortagePackages()).orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getShortagePackages())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewShortagePackages()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRDTLData.setBreakage(
											Optional.ofNullable(findCfBondCrgHDRDTLData.getBreakage()).orElse(BigDecimal.ZERO)
													.subtract(Optional.ofNullable(findCfBondCrgDTLData.getBreakage())
															.orElse(BigDecimal.ZERO))
													.add(Optional.ofNullable(item.getNewBreakage()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRDTLData.setDamagedQty(
											Optional.ofNullable(findCfBondCrgHDRDTLData.getDamagedQty()).orElse(BigDecimal.ZERO)
													.subtract(Optional.ofNullable(findCfBondCrgDTLData.getDamagedQty())
															.orElse(BigDecimal.ZERO))
													.add(Optional.ofNullable(item.getNewDamagedQty()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRDTLData.setAreaOccupied(Optional
											.ofNullable(findCfBondCrgHDRDTLData.getAreaOccupied()).orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getAreaOccupied())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewAreaOccupied()).orElse(BigDecimal.ZERO)));

									cfinbondCrgHdrDtlRepo.save(findCfBondCrgHDRDTLData);
								}

								findCfBondCrgHDRData = cfinbondCrgHdrRepo.findCfBondCrgHDRData(companyId, branchId,
										findCfBondCrgData.getNocTransId(), findCfBondCrgData.getInBondingHdrId(),
										findCfBondCrgData.getNocNo());

								System.out.println("findCfBondCrgHDRData___________" + findCfBondCrgHDRData);
								if (findCfBondCrgHDRData != null)

								{

									String cha = getValidString(savedEdit.getCha(), savedEdit.getChaOld());
									String chaCode = getValidString(savedEdit.getNewChaCode(),
											findCfBondCrgHDRData.getChaCode());
									String importerId = getValidString(savedEdit.getImporterId(), savedEdit.getImporterIdOld());
									String importerName = getValidString(savedEdit.getImporterName(),
											savedEdit.getImporterNameOld());
									String add1 = getValidString(savedEdit.getImporterAddress1(),
											findCfBondCrgHDRData.getImporterAddress1());
									String add2 = getValidString(savedEdit.getImporterAddress2(),
											findCfBondCrgHDRData.getImporterAddress2());
									String add3 = getValidString(savedEdit.getImporterAddress3(),
											findCfBondCrgHDRData.getImporterAddress3());
									String boeNo = getValidString(savedEdit.getBoeNo(), savedEdit.getBoeNoOld());
									Date boeDate = getValidDate(savedEdit.getBoeDate(), savedEdit.getBoeDateOld());

									Date bondingDate = getValidDate(savedEdit.getBondingDate(), savedEdit.getBondingDateOld());
									Date bondValidityDate = getValidDate(savedEdit.getBondValidityDate(),
											savedEdit.getBondValidityDateOld());
									String bondingNo = getValidString(savedEdit.getBondingNo(), savedEdit.getBondingNoOld());

									findCfBondCrgHDRData.setBondingNo(bondingNo);
									findCfBondCrgHDRData.setBondingDate(bondingDate);
									findCfBondCrgHDRData.setBondValidityDate(bondValidityDate);
									findCfBondCrgHDRData.setBoeDate(boeDate);
									findCfBondCrgHDRData.setBoeNo(boeNo);
									findCfBondCrgHDRData.setCha(cha);
									findCfBondCrgHDRData.setChaCode(chaCode);
									findCfBondCrgHDRData.setImporterId(importerId);
									findCfBondCrgHDRData.setImporterName(importerName);
									findCfBondCrgHDRData.setImporterAddress1(add1);
									findCfBondCrgHDRData.setImporterAddress2(add2);
									findCfBondCrgHDRData.setImporterAddress3(add3);

									findCfBondCrgHDRData.setInBondedPackages(Optional
											.ofNullable(findCfBondCrgHDRData.getInBondedPackages()).orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInBondedPackages())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewBondPackages()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRData.setCargoDuty(Optional.ofNullable(findCfBondCrgHDRData.getCargoDuty())
											.orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondCargoDuty())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewBondCargoDuty()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRData.setCifValue(Optional.ofNullable(findCfBondCrgHDRData.getCifValue())
											.orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondCifValue())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewBondCifValue()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRData.setInbondInsuranceValue(Optional
											.ofNullable(findCfBondCrgHDRData.getInbondInsuranceValue()).orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondInsuranceValue())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewInsuranceValue()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRData.setShortagePackages(Optional
											.ofNullable(findCfBondCrgHDRData.getShortagePackages()).orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getShortagePackages())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewShortagePackages()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRData.setBreakage(
											Optional.ofNullable(findCfBondCrgHDRData.getBreakage()).orElse(BigDecimal.ZERO)
													.subtract(Optional.ofNullable(findCfBondCrgDTLData.getBreakage())
															.orElse(BigDecimal.ZERO))
													.add(Optional.ofNullable(item.getNewBreakage()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRData.setDamagedQty(
											Optional.ofNullable(findCfBondCrgHDRData.getDamagedQty()).orElse(BigDecimal.ZERO)
													.subtract(Optional.ofNullable(findCfBondCrgDTLData.getDamagedQty())
															.orElse(BigDecimal.ZERO))
													.add(Optional.ofNullable(item.getNewDamagedQty()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRData.setAreaOccupied(Optional
											.ofNullable(findCfBondCrgHDRData.getAreaOccupied()).orElse(BigDecimal.ZERO)
											.subtract(Optional.ofNullable(findCfBondCrgDTLData.getAreaOccupied())
													.orElse(BigDecimal.ZERO))
											.add(Optional.ofNullable(item.getNewAreaOccupied()).orElse(BigDecimal.ZERO)));

									findCfBondCrgHDRData.setEditedBy(user);
									findCfBondCrgHDRData.setStatus("A");
									findCfBondCrgHDRData.setEditedDate(new Date());

								}

								cfinbondcrgDtlRepo.save(findCfBondCrgDTLData);

//										cfinbondCrgHdrDtlRepo.save(findCfBondCrgHDRDTLData);

								CfinbondcrgDtl savedlist = cfinbondcrgDtlRepo.save(findCfBondCrgDTLData);
							}

						}

					}
				}



				Cfbondnoc findCfBondNocForUpdationg = cfbondnocRepository.findCfBondNocForUpdationg(companyId, branchId,
						cfinbondcrg.getNocTransId(), cfinbondcrg.getNocNo());
				if (findCfBondNocForUpdationg != null) 
				{

					Date bondingDate = getValidDate(savedEdit.getBondingDate(), savedEdit.getBondingDateOld());
					Date bondValidityDate = getValidDate(savedEdit.getBondValidityDate(),
							savedEdit.getBondValidityDateOld());
					Date boeDate = getValidDate(savedEdit.getBoeDate(), savedEdit.getBoeDateOld());

					// Setting the values with fallback to old values if null or blank
					String bondingNo = getValidString(savedEdit.getBondingNo(), savedEdit.getBondingNoOld());

					String boeNo = getValidString(savedEdit.getBoeNo(), savedEdit.getBoeNoOld());

					String cha = getValidString(savedEdit.getCha(), savedEdit.getChaOld());
					String chaCode = getValidString(savedEdit.getNewChaCode(), findCfBondNocForUpdationg.getChaCode());
					String importerId = getValidString(savedEdit.getImporterId(), savedEdit.getImporterIdOld());
					String importerName = getValidString(savedEdit.getImporterName(), savedEdit.getImporterNameOld());
					String add1 = getValidString(savedEdit.getImporterAddress1(),
							findCfBondNocForUpdationg.getImporterAddress1());
					String add2 = getValidString(savedEdit.getImporterAddress2(),
							findCfBondNocForUpdationg.getImporterAddress2());
					String add3 = getValidString(savedEdit.getImporterAddress3(),
							findCfBondNocForUpdationg.getImporterAddress3());

					int updateInCfBondNoc = cfbondnocRepository.updateCfBondNocAfterAuditTrail(
							findCfBondNocForUpdationg.getInBondedPackages().add(totalInbondedd)
									.subtract(savedEdit.getInBondedPackagesOld()),
									
							findCfBondNocForUpdationg.getInbondGrossWt().add(totalWeight)
									.subtract(savedEdit.getInBondedGwOld()),
							findCfBondNocForUpdationg.getInbondInsuranceValue().add(totalInsurance)
									.subtract(savedEdit.getInBondedInsuranceOld() != null
											? savedEdit.getInBondedInsuranceOld()
											: BigDecimal.ZERO),
							findCfBondNocForUpdationg.getInbondCifValue().add(totalCif)
									.subtract(savedEdit.getInBondedCifOld()),
							findCfBondNocForUpdationg.getInbondCargoDuty().add(totalCargo)
									.subtract(savedEdit.getInBondedCargoDutyOld()),

							bondingNo, bondingDate, bondValidityDate, boeNo, boeDate, cha, chaCode, importerId,
							importerName, add1, add2, add3, companyId, branchId, savedEdit.getNocTransId(),
							savedEdit.getNocNo());

					System.out.println("Update noc after inbond in exist" + updateInCfBondNoc);
				}

				if (findCfBondCrgData != null) 
				{

					String cha = getValidString(savedEdit.getCha(), savedEdit.getChaOld());
					String chaCode = getValidString(savedEdit.getNewChaCode(), findCfBondCrgData.getChaCode());
					String importerId = getValidString(savedEdit.getImporterId(), savedEdit.getImporterIdOld());
					String importerName = getValidString(savedEdit.getImporterName(), savedEdit.getImporterNameOld());
					String add1 = getValidString(savedEdit.getImporterAddress1(), findCfBondCrgData.getImporterAddress1());
					String add2 = getValidString(savedEdit.getImporterAddress2(), findCfBondCrgData.getImporterAddress2());
					String add3 = getValidString(savedEdit.getImporterAddress3(), findCfBondCrgData.getImporterAddress3());

					findCfBondCrgData.setCha(cha);
					findCfBondCrgData.setChaCode(chaCode);
					findCfBondCrgData.setImporterId(importerId);
					findCfBondCrgData.setImporterName(importerName);
					findCfBondCrgData.setImporterAddress1(add1);
					findCfBondCrgData.setImporterAddress2(add2);
					findCfBondCrgData.setImporterAddress3(add3);

					findCfBondCrgData.setInbondInsuranceValue(
						    Optional.ofNullable(findCfBondCrgData.getInbondInsuranceValue()).orElse(BigDecimal.ZERO)
						        .add(Optional.ofNullable(savedEdit.getInBondedInsurance()).orElse(BigDecimal.ZERO)
						             .subtract(Optional.ofNullable(savedEdit.getInBondedInsuranceOld()).orElse(BigDecimal.ZERO))));

						findCfBondCrgData.setInbondGrossWt(
						    Optional.ofNullable(findCfBondCrgData.getInbondGrossWt()).orElse(BigDecimal.ZERO)
						        .add(Optional.ofNullable(savedEdit.getInBondedGw()).orElse(BigDecimal.ZERO)
						             .subtract(Optional.ofNullable(savedEdit.getInBondedGwOld()).orElse(BigDecimal.ZERO))));

						findCfBondCrgData.setInBondedPackages(
						    Optional.ofNullable(findCfBondCrgData.getInBondedPackages()).orElse(BigDecimal.ZERO)
						        .add(Optional.ofNullable(savedEdit.getInBondedPackages()).orElse(BigDecimal.ZERO)
						             .subtract(Optional.ofNullable(savedEdit.getInBondedPackagesOld()).orElse(BigDecimal.ZERO))));

						findCfBondCrgData.setCifValue(
						    Optional.ofNullable(findCfBondCrgData.getCifValue()).orElse(BigDecimal.ZERO)
						        .add(Optional.ofNullable(savedEdit.getInBondedCif()).orElse(BigDecimal.ZERO)
						             .subtract(Optional.ofNullable(savedEdit.getInBondedCifOld()).orElse(BigDecimal.ZERO))));

						findCfBondCrgData.setCargoDuty(
						    Optional.ofNullable(findCfBondCrgData.getCargoDuty()).orElse(BigDecimal.ZERO)
						        .add(Optional.ofNullable(savedEdit.getInBondedCargoDuty()).orElse(BigDecimal.ZERO)
						             .subtract(Optional.ofNullable(savedEdit.getInBondedCargoDutyOld()).orElse(BigDecimal.ZERO))));

					
//					findCfBondCrgData.setInbondInsuranceValue(findCfBondCrgData.getInbondInsuranceValue().add(savedEdit.getInBondedInsurance().subtract(savedEdit.getInBondedInsuranceOld())));
//					findCfBondCrgData.setInbondGrossWt(findCfBondCrgData.getInbondGrossWt().add(savedEdit.getInBondedGw().subtract(savedEdit.getInBondedGwOld())));
//					findCfBondCrgData.setInBondedPackages(findCfBondCrgData.getInBondedPackages().add(savedEdit.getInBondedPackages().subtract(savedEdit.getInBondedPackagesOld())));
//					findCfBondCrgData.setCifValue(findCfBondCrgData.getCifValue().add(savedEdit.getInBondedCif().subtract(savedEdit.getInBondedCifOld())));
//					findCfBondCrgData.setCargoDuty(findCfBondCrgData.getCargoDuty().add(savedEdit.getInBondedCargoDuty().subtract(savedEdit.getInBondedCargoDutyOld())));
					
					findCfBondCrgData.setAreaOccupied(totalArea);
					findCfBondCrgData.setEditedBy(user);
					findCfBondCrgData.setStatus("A");
					findCfBondCrgData.setEditedDate(new Date());

					

					if (savedEdit.getBoeNo().isEmpty() || savedEdit.getBoeNo().isBlank() || savedEdit.getBoeNo() == null) {
						findCfBondCrgData.setBoeNo(savedEdit.getBoeNoOld());
					} else {
						findCfBondCrgData.setBoeNo(savedEdit.getBoeNo());
					}

					if (savedEdit.getBondingNo().isEmpty() || savedEdit.getBondingNo().isBlank()
							|| savedEdit.getBondingNo() == null) {
						findCfBondCrgData.setBondingNo(savedEdit.getBondingNoOld());
					} else {
						findCfBondCrgData.setBondingNo(savedEdit.getBondingNo());
					}

					// Set Bonding Date
					if (savedEdit.getBondingDate() == null) {
						findCfBondCrgData.setBondingDate(savedEdit.getBondingDateOld());
					} else {
						findCfBondCrgData.setBondingDate(savedEdit.getBondingDate());
					}

					// Set Bond Validity Date
					if (savedEdit.getBondValidityDate() == null) {
						findCfBondCrgData.setBondValidityDate(savedEdit.getBondValidityDateOld());
					} else {
						findCfBondCrgData.setBondValidityDate(savedEdit.getBondValidityDate());
					}

				}

				cfinbondcrgRepo.save(findCfBondCrgData);

			}
			
			
			
		}

		System.out.println(
				"savededit______________________________________________________________________________________________________________________________________________");

		System.out.println(savedEdit);
		return new ResponseEntity<>(savedEdit, HttpStatus.OK);
	}

	private Date getValidDate(Date currentDate, Date oldDate) {
		return (currentDate == null) ? oldDate : currentDate;
	}

	private String getValidString(String currentValue, String oldValue) {
		return (currentValue == null || currentValue.isBlank() || currentValue.isEmpty()) ? oldValue : currentValue;
	}
	
	private BigDecimal getValidBigDecimal(BigDecimal currentValue, BigDecimal oldValue) {
	    return (currentValue == null || currentValue.compareTo(BigDecimal.ZERO) == 0) ? oldValue : currentValue;
	}

	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	public ResponseEntity<?> approveDataOfInCFbondGrid(String companyId, String branchId, String flag, String user,
			Map<String, Object> requestBody) {

		ObjectMapper object = new ObjectMapper();
		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		CfexbondcrgEdit cfinbondcrg = object.convertValue(requestBody.get("inBond"), CfexbondcrgEdit.class);

		System.out.println("cfinbondcrg________________________" + cfinbondcrg.getInBondingId());

		if (cfinbondcrg.getInBondingId() == null || cfinbondcrg.getInBondingId().isEmpty() || cfinbondcrg.getSrNo() == null
				|| cfinbondcrg.getInBondingId().isBlank()) {
			return new ResponseEntity<>("Please First Save Data", HttpStatus.BAD_REQUEST);
		}

		if (cfinbondcrg != null && cfinbondcrg.getInBondingId() != null || !cfinbondcrg.getInBondingId().isEmpty()
				|| !cfinbondcrg.getInBondingId().isBlank()) {

			BigDecimal sumOfInbondFromDtl = cfexbondcrgEditRepository.getSumOfInBondPackages(companyId, branchId,
					cfinbondcrg.getInBondingId(), cfinbondcrg.getNocTransId());

			BigDecimal sumOfInbondFormGrid = cfInBondGridRepository.getSumOfInBondPackages(companyId, branchId,
					cfinbondcrg.getInBondingId(), cfinbondcrg.getNocTransId());

			System.out.println("sumOfInbondFromDtl: " + sumOfInbondFromDtl + " ______________ " + sumOfInbondFormGrid);

			if (sumOfInbondFromDtl == null || sumOfInbondFormGrid == null) {
				return new ResponseEntity<>("One of the sum values is null. Please check the data.",
						HttpStatus.BAD_REQUEST);
			}

			if (sumOfInbondFormGrid.compareTo(sumOfInbondFromDtl) != 0) {

				return new ResponseEntity<>("InbondPackages do not match in yard, please add packages in grid.",
						HttpStatus.BAD_REQUEST);
			} else {
				int updateAfterApprov = cfexbondcrgEditRepository.updateAfterApprove("A", companyId, branchId,
						cfinbondcrg.getInBondingId(), cfinbondcrg.getNocNo(), cfinbondcrg.getNocTransId());
				System.out.println("updateAfterApprov row count " + updateAfterApprov);
			}
		}

		return new ResponseEntity<>("", HttpStatus.OK);
	}

	
	@Transactional
	public ResponseEntity<?> saveCfExBondDataFromExBondAuditTrailScreen(String companyId, String branchId, String user,
			String flag, Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		CfexbondcrgEdit cfinbondcrg = object.convertValue(requestBody.get("inBond"), CfexbondcrgEdit.class);

		Object nocDtlObj = requestBody.get("nocDtl");
		List<CfinbondCommdtlEdit> cfinbondcrgDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			cfinbondcrgDtlList = object.convertValue(nocDtlObj, new TypeReference<List<CfinbondCommdtlEdit>>() {
			});

		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				CfinbondCommdtlEdit cfinbondcrgDtl = object.convertValue(entry.getValue(), CfinbondCommdtlEdit.class);
				cfinbondcrgDtlList.add(cfinbondcrgDtl);
			}
		} else {
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		System.out.println("cfinbondcrg________________" + cfinbondcrg);

		System.out.println("cfinbondcrgDtlList________________" + cfinbondcrgDtlList);

		CfexbondcrgEdit savedEdit = null;

		if(cfinbondcrg.getExBondBeNoOld()==null || cfinbondcrg.getExBondBeNoOld().isEmpty() || cfinbondcrg.getExBondBeNoOld().isEmpty())
		{
			return new ResponseEntity<>("please select exbondbe no to save data.....",HttpStatus.BAD_REQUEST);
		}
		if (cfinbondcrg != null) 
		{
			cfinbondcrg.setApprovedBy(user);
			cfinbondcrg.setCreatedBy(user);
			cfinbondcrg.setCreatedDate(new Date());
			cfinbondcrg.setApprovedDate(new Date());
			
			if(cfinbondcrgDtlList == null || cfinbondcrgDtlList.isEmpty() )
			{
				cfinbondcrg.setStatus("A");
			}else
			{
				cfinbondcrg.setStatus("N");
			}
			
			
			cfinbondcrg.setBoeNo(cfinbondcrg.getBoeNoOld());

			cfinbondcrg.setBalancedPackagesNew(cfinbondcrg.getRemainingPackages()
					.subtract(Optional.ofNullable(cfinbondcrg.getExBondedPackages()).orElse(BigDecimal.ZERO)));

			cfinbondcrg.setBalanceCif(cfinbondcrg.getRemainingCif()
					.subtract(Optional.ofNullable(cfinbondcrg.getExBondedCif()).orElse(BigDecimal.ZERO)));

			cfinbondcrg.setBalanceCargoDuty(cfinbondcrg.getRemainingCargoDuty()
					.subtract(Optional.ofNullable(cfinbondcrg.getExBondedCargoDuty()).orElse(BigDecimal.ZERO)));

			cfinbondcrg.setBalanceInsurance(cfinbondcrg.getRemainingInsurance()
					.subtract(Optional.ofNullable(cfinbondcrg.getExBondedInsurance()).orElse(BigDecimal.ZERO)));

			cfinbondcrg.setBalanceGw(cfinbondcrg.getRemainingGw()
					.subtract(Optional.ofNullable(cfinbondcrg.getExBondedGw()).orElse(BigDecimal.ZERO)));

			cfinbondcrg.setBalancedQtyNew(cfinbondcrg.getRemainingPackages()
					.subtract(Optional.ofNullable(cfinbondcrg.getExBondedPackages()).orElse(BigDecimal.ZERO)));

			savedEdit = cfexbondcrgEditRepository.save(cfinbondcrg);

			if (savedEdit != null) 
			{
				
				String exBondBe = getValidString(savedEdit.getExBondBeNo(), savedEdit.getExBondBeNoOld());
				
					int editedRowInCfEcbondCrgDtlHeader = cfExBondCrgDtlRepository.updateExbondCrgDetailAfterExBondAuditTrailHeaderChange(user,
							new Date(), 
							exBondBe,'A',
							companyId, branchId,savedEdit.getNocTransId(),
							savedEdit.getInBondingId(), savedEdit.getExBondingId());

					System.out.println(
							"editedRowInCfEcbondCrgDtlHeader_____________________________________________________________"
									+ editedRowInCfEcbondCrgDtlHeader);

				CfExBondCrg existingExBond = cfExBondCrgRepository.getCfExBondCrgAfterExBondAuditTrail(companyId, branchId,
						savedEdit.getNocTransId(), savedEdit.getInBondingId(), savedEdit.getNocNo(),savedEdit.getExBondingId());
				
				if (existingExBond != null) 
				{

					String exBondBeNo = getValidString(savedEdit.getExBondBeNo(), savedEdit.getExBondBeNoOld());
					
					Date exBondBeDate = getValidDate(savedEdit.getExBondBeDate(), savedEdit.getExBondBeDateOld());
					
					Date sbDate = getValidDate(savedEdit.getSbDate(), savedEdit.getSbDateOld());
					
					Date transferDate = getValidDate(savedEdit.getTransferBondDate(), savedEdit.getTransferBondDateOld());
				
					String sbNo = getValidString(savedEdit.getSbNo(), savedEdit.getSbNoOld());
					
					String transferBondNo = getValidString(savedEdit.getTransferBondNo(), savedEdit.getTransferBondNoOld());
				
					existingExBond.setExBondBeDate(exBondBeDate);
					existingExBond.setExBondBeNo(exBondBeNo);
					
				if("BTB".equals(savedEdit.getExBondType()))
				{
					existingExBond.setTrnsferBondDate(transferDate);
					existingExBond.setTrnsferBondNo(transferBondNo);
				}

				if("EXP".equals(savedEdit.getExBondType()))
				{
					existingExBond.setSbDate(sbDate);
					existingExBond.setSbNo(sbNo);
					existingExBond.setSbValue(
						    Optional.ofNullable(existingExBond.getSbValue()).orElse(BigDecimal.ZERO)
						        .add(Optional.ofNullable(savedEdit.getSbValueNew()).orElse(BigDecimal.ZERO)
						             .subtract(Optional.ofNullable(savedEdit.getSbValueOld()).orElse(BigDecimal.ZERO))));

						existingExBond.setSbDuty(
						    Optional.ofNullable(existingExBond.getSbDuty()).orElse(BigDecimal.ZERO)
						        .add(Optional.ofNullable(savedEdit.getSbDutyNew()).orElse(BigDecimal.ZERO)
						             .subtract(Optional.ofNullable(savedEdit.getSbDutyOld()).orElse(BigDecimal.ZERO))));

						existingExBond.setSbQty(
						    Optional.ofNullable(existingExBond.getSbQty()).orElse(BigDecimal.ZERO)
						        .add(Optional.ofNullable(savedEdit.getSbQtyNew()).orElse(BigDecimal.ZERO)
						             .subtract(Optional.ofNullable(savedEdit.getSbQtyOld()).orElse(BigDecimal.ZERO))));

//					existingExBond.setSbValue(existingExBond.getSbValue()!=null ? existingExBond.getSbValue() : BigDecimal.ZERO.add(savedEdit.getSbValueNew().subtract(savedEdit.getSbValueOld())));
//					existingExBond.setSbValue(existingExBond.getSbDuty()!=null ? existingExBond.getSbDuty() : BigDecimal.ZERO.add(savedEdit.getSbDutyNew().subtract(savedEdit.getSbDutyOld())));
//					existingExBond.setSbQty(existingExBond.getSbQty()!=null ? existingExBond.getSbQty() : BigDecimal.ZERO.add(savedEdit.getSbQtyNew().subtract(savedEdit.getSbQtyOld())));
				} 
				
//					existingExBond.setRemainingCargoDuty(existingExBond.getRemainingCargoDuty().add(savedEdit.getRemainingCargoDuty().subtract(savedEdit.getRemainingCargoDutyOld())));
//					existingExBond.setRemainingCif(existingExBond.getRemainingCif().add(savedEdit.getRemainingCif().subtract(savedEdit.getRemainingCifOld())));
//					existingExBond.setRemainingInsurance(existingExBond.getRemainingInsurance().add(savedEdit.getRemainingInsurance().subtract(savedEdit.getRemainingInsuranceOld())));
//					existingExBond.setRemainingGw(existingExBond.getRemainingGw().add(savedEdit.getRemainingGw().subtract(savedEdit.getRemainingGwOld())));
//					existingExBond.setRemainingPackages(existingExBond.getRemainingPackages().add(savedEdit.getRemainingPackages().subtract(savedEdit.getRemainingPackagesOld())));
//					
//					existingExBond.setBalanceCargoDuty(existingExBond.getBalanceCargoDuty().add(savedEdit.getBalanceCargoDuty().subtract(savedEdit.getBalanceCargoDutyOld())));
//					existingExBond.setBalanceCif(existingExBond.getBalanceCif().add(savedEdit.getBalanceCif().subtract(savedEdit.getBalanceCifOld())));
//					existingExBond.setBalancedPackages(existingExBond.getBalancedPackages().add(savedEdit.getBalancedQtyNew().subtract(savedEdit.getBalancedQty())));
//					existingExBond.setBalancedQty(existingExBond.getBalancedPackages().add(savedEdit.getBalancedQtyNew().subtract(savedEdit.getBalancedQty())));
//					existingExBond.setBalanceGw(existingExBond.getBalanceGw().add(savedEdit.getBalanceGw().subtract(savedEdit.getBalanceGwOld())));
//					existingExBond.setBalanceInsurance(existingExBond.getBalanceInsurance().add(savedEdit.getBalanceInsurance().subtract(savedEdit.getBalanceInsuranceOld())));
//					
//					existingExBond.setExBondedPackages(existingExBond.getExBondedPackages().add(savedEdit.getExBondedPackages().subtract(savedEdit.getExBondedPackagesOld())));
//					existingExBond.setExBondedCif(existingExBond.getExBondedCif().add(savedEdit.getExBondedCif().subtract(savedEdit.getExBondedCifOld())));
//					existingExBond.setExBondedCargoDuty(existingExBond.getExBondedCargoDuty().add(savedEdit.getExBondedCargoDuty().subtract(savedEdit.getExBondedCargoDutyOld())));
//					existingExBond.setExBondedInsurance(existingExBond.getExBondedInsurance().add(savedEdit.getExBondedInsurance().subtract(savedEdit.getExBondedInsuranceOld())));
//					existingExBond.setExBondedGw(existingExBond.getExBondedGw().add(savedEdit.getExBondedGw().subtract(savedEdit.getExBondedGwOld())));
				existingExBond.setRemainingCargoDuty(
					    Optional.ofNullable(existingExBond.getRemainingCargoDuty()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getRemainingCargoDuty()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getRemainingCargoDutyOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setRemainingCif(
					    Optional.ofNullable(existingExBond.getRemainingCif()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getRemainingCif()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getRemainingCifOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setRemainingInsurance(
					    Optional.ofNullable(existingExBond.getRemainingInsurance()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getRemainingInsurance()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getRemainingInsuranceOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setRemainingGw(
					    Optional.ofNullable(existingExBond.getRemainingGw()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getRemainingGw()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getRemainingGwOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setRemainingPackages(
					    Optional.ofNullable(existingExBond.getRemainingPackages()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getRemainingPackages()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getRemainingPackagesOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setBalanceCargoDuty(
					    Optional.ofNullable(existingExBond.getBalanceCargoDuty()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getBalanceCargoDuty()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getBalanceCargoDutyOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setBalanceCif(
					    Optional.ofNullable(existingExBond.getBalanceCif()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getBalanceCif()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getBalanceCifOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setBalancedPackages(
					    Optional.ofNullable(existingExBond.getBalancedPackages()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getBalancedQtyNew()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getBalancedQty()).orElse(BigDecimal.ZERO))));

					existingExBond.setBalancedQty(
					    Optional.ofNullable(existingExBond.getBalancedQty()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getBalancedQtyNew()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getBalancedQty()).orElse(BigDecimal.ZERO))));

					existingExBond.setBalanceGw(
					    Optional.ofNullable(existingExBond.getBalanceGw()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getBalanceGw()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getBalanceGwOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setBalanceInsurance(
					    Optional.ofNullable(existingExBond.getBalanceInsurance()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getBalanceInsurance()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getBalanceInsuranceOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setExBondedPackages(
					    Optional.ofNullable(existingExBond.getExBondedPackages()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getExBondedPackages()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getExBondedPackagesOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setExBondedCif(
					    Optional.ofNullable(existingExBond.getExBondedCif()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getExBondedCif()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getExBondedCifOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setExBondedCargoDuty(
					    Optional.ofNullable(existingExBond.getExBondedCargoDuty()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getExBondedCargoDuty()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getExBondedCargoDutyOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setExBondedInsurance(
					    Optional.ofNullable(existingExBond.getExBondedInsurance()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getExBondedInsurance()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getExBondedInsuranceOld()).orElse(BigDecimal.ZERO))));

					existingExBond.setExBondedGw(
					    Optional.ofNullable(existingExBond.getExBondedGw()).orElse(BigDecimal.ZERO)
					        .add(Optional.ofNullable(savedEdit.getExBondedGw()).orElse(BigDecimal.ZERO)
					             .subtract(Optional.ofNullable(savedEdit.getExBondedGwOld()).orElse(BigDecimal.ZERO))));

					
					existingExBond.setEditedBy(user);
					existingExBond.setStatus("A");
					existingExBond.setEditedDate(new Date());

					


					cfExBondCrgRepository.save(existingExBond);
				}


				Cfbondinsbal existingBalance = cfbondnocDtlRepository.getDataOfCfBondCifForValidation(companyId,
						branchId);

				if (existingBalance != null) {
					int updateCfBalance = cfExBondCrgRepository
							.updateCfbondinsbalAfterExbondAuditTrail(
									existingBalance.getExbondCargoDuty().add(savedEdit.getExBondedCargoDuty())
											.subtract(savedEdit.getExBondedCargoDutyOld()),
									existingBalance.getExbondCifValue().add(savedEdit.getExBondedCif())
											.subtract(savedEdit.getExBondedCifOld()),
									companyId, branchId);

					System.out.println("updateCfBalance row is after edit" + updateCfBalance);
				}
				
				Cfinbondcrg findCfinbondCrg = cfinbondcrgRepo.findCfinbondCrg(companyId, branchId,
						savedEdit.getNocTransId(), savedEdit.getInBondingId(), savedEdit.getNocNo());

				if (findCfinbondCrg != null) {
					
					int updateCfinbondcrgAfterExbond = cfinbondcrgRepo.updateCfInBondCrgAfterExBondAuditTrail(
							findCfinbondCrg.getExBondedPackages().add(savedEdit.getExBondedPackages())
									.subtract(savedEdit.getExBondedPackagesOld()),
									
							findCfinbondCrg.getExBondedCargoDuty().add(savedEdit.getExBondedCargoDuty())
									.subtract(savedEdit.getExBondedCargoDutyOld()),
									
							findCfinbondCrg.getExBondedCif().add(savedEdit.getExBondedCif())
									.subtract(savedEdit.getExBondedCifOld()),
									
							findCfinbondCrg.getExBondedInsurance().add(savedEdit.getExBondedInsurance())
									.subtract(savedEdit.getExBondedInsuranceOld()),
									
							findCfinbondCrg.getExBondedGw().add(savedEdit.getExBondedGw())
									.subtract(savedEdit.getExBondedGwOld()),
							companyId, branchId, savedEdit.getInBondingId(), savedEdit.getNocNo(),
							savedEdit.getNocTransId(), savedEdit.getBoeNo());
					

					System.out.println("Update row count after exbond is" + updateCfinbondcrgAfterExbond);
				}
				
				Cfinbondcrg existing = cfinbondcrgRepo.findCfinbondHdr(companyId, branchId,
						savedEdit.getNocTransId(), savedEdit.getInBondingId(), savedEdit.getNocNo());

				if (existing != null) {

	

					CfinbondcrgHDR findCfBondCrgHDRData = cfinbondCrgHdrRepo.findCfBondCrgHDRData(companyId,
							branchId, existing.getNocTransId(), existing.getInBondingHdrId(), existing.getNocNo());

					if (findCfBondCrgHDRData != null) {
						int updateCfInbondCrdHDR = cfinbondCrgHdrRepo.updateCfInbondHeaderAfterExbond(
								findCfBondCrgHDRData.getExBondedPackages().add(savedEdit.getExBondedPackages())
										.subtract(savedEdit.getExBondedPackagesOld()),
										
								findCfBondCrgHDRData.getExBondedCargoDuty().add(savedEdit.getExBondedCargoDuty())
										.subtract(savedEdit.getExBondedCargoDutyOld()),
										
								findCfBondCrgHDRData.getExBondedInsurance().add(savedEdit.getExBondedInsurance())
										.subtract(savedEdit.getExBondedInsuranceOld()),
										
								findCfBondCrgHDRData.getExBondedCif().add(savedEdit.getExBondedCif())
										.subtract(savedEdit.getExBondedCifOld()),
										
								findCfBondCrgHDRData.getExBondedGw().add(savedEdit.getExBondedGw())
										.subtract(savedEdit.getExBondedGwOld()),
										
								companyId, branchId, savedEdit.getNocTransId(), savedEdit.getNocNo(),
								existing.getInBondingHdrId(), savedEdit.getBoeNo());

						System.out.println("Update row count after exbond hdr in edit " + updateCfInbondCrdHDR);
					}
					

				}
				
				if (cfinbondcrgDtlList != null && !cfinbondcrgDtlList.isEmpty()) {
					System.out.println("in edit");
					BigDecimal totalInbondedd = BigDecimal.ZERO;
					BigDecimal totalCif = BigDecimal.ZERO;
					BigDecimal totalCargo = BigDecimal.ZERO;
					BigDecimal totalInsurance = BigDecimal.ZERO;
					BigDecimal totalWeight = BigDecimal.ZERO;
					BigDecimal totalArea = BigDecimal.ZERO;

					for (CfinbondCommdtlEdit item : cfinbondcrgDtlList) 
					{
						totalCif = totalCif.add(item.getNewBondCifValue());
						totalInbondedd = totalInbondedd.add(item.getNewBondPackages());
						totalCargo = totalCargo.add(item.getNewBondCargoDuty());
						totalInsurance = totalInsurance.add(item.getNewInsuranceValue());
						totalWeight = totalWeight.add(item.getNewBondGrossWt());
//						totalArea = totalArea.add(item.getNewAreaOccupied());
						
						CfinbondCommdtlEdit crgDetails = new CfinbondCommdtlEdit();

						crgDetails.setSrNo(savedEdit.getSrNo());
						crgDetails.setCreatedBy(user);
						crgDetails.setCreatedDate(new Date());
						crgDetails.setApprovedBy(user);
						crgDetails.setApprovedDate(new Date());
						crgDetails.setBoeNo(cfinbondcrg.getBoeNoOld());
						crgDetails.setBranchId(branchId);
						crgDetails.setCompanyId(companyId);
						crgDetails.setCommonBondingId(savedEdit.getExBondingId());
						crgDetails.setStatus("A");

						crgDetails.setCommodityId(item.getCommodityId());


						crgDetails.setYardLocationId(item.getYardLocationId());
						crgDetails.setBlockId(item.getBlockId());
						crgDetails.setCellNoRow(item.getCellNoRow());

						crgDetails.setOldYardPackages(item.getOldYardPackages());
						crgDetails.setNewYardPackages(item.getNewYardPackages());

						crgDetails.setNewBondCargoDuty(item.getNewBondCargoDuty());
						crgDetails.setNewBondCifValue(item.getNewBondCifValue());
						crgDetails.setNewBondPackages(item.getNewBondPackages());
						crgDetails.setNewBondGrossWt(item.getNewBondGrossWt());
						crgDetails.setNewInsuranceValue(item.getNewInsuranceValue());

						crgDetails.setOldBondCifValue(item.getOldBondCifValue());
						crgDetails.setOldBondCargoDuty(item.getOldBondCargoDuty());
						crgDetails.setOldInsuranceValue(item.getOldInsuranceValue());
						crgDetails.setOldBondGrossWt(item.getOldBondGrossWt());
						crgDetails.setOldBondPackages(item.getOldBondPackages());
						crgDetails.setCommodityId(item.getCommodityId());
						crgDetails.setNewCommodityDescription(item.getOldCommodityDescription());
						crgDetails.setOldCommodityDescription(item.getOldCommodityDescription());

						crgDetails.setOldTypeOfPackage(item.getOldTypeOfPackage());
						crgDetails.setNewTypeOfPackage(item.getOldTypeOfPackage());

						crgDetails.setCellAreaAllocated(item.getCellAreaAllocated());
						crgDetails.setNocNo(savedEdit.getNocNo());
						crgDetails.setNocTransId(savedEdit.getNocTransId());
						crgDetails.setBoeNo(savedEdit.getBoeNo());
						crgDetails.setType("EXBOND");

						CfinbondCommdtlEdit savedDtl = cfinbondCommdtlEditRepository.save(crgDetails);

						if (savedDtl != null) 
						{
							
							String exBondBeNooooooooo = getValidString(cfinbondcrg.getExBondBeNo(), cfinbondcrg.getExBondBeNoOld());
							
							BigDecimal packages  = getValidBigDecimal(savedDtl.getNewBondPackages(), savedDtl.getOldBondPackages());
							
							BigDecimal cif  = getValidBigDecimal(savedDtl.getNewBondCifValue(), savedDtl.getOldBondCifValue());
							
							BigDecimal duty  = getValidBigDecimal(savedDtl.getNewBondCargoDuty(), savedDtl.getOldBondCargoDuty());
							
							BigDecimal insurance  = getValidBigDecimal(savedDtl.getNewInsuranceValue(), savedDtl.getOldInsuranceValue());
							
							BigDecimal weight  = getValidBigDecimal(savedDtl.getNewBondGrossWt(), savedDtl.getOldBondGrossWt());
							
							BigDecimal yardPackages  = getValidBigDecimal(savedDtl.getNewYardPackages(), savedDtl.getOldYardPackages());
							
							BigDecimal area  = getValidBigDecimal(savedDtl.getCellAreaAllocated(), savedDtl.getCellAreaAllocated());
							
							
								int editedRowInCfEcbondCrgDtl = cfExBondCrgDtlRepository.updateExbondCrgDetailAfterExBondAuditTrail(user,
										new Date(), 
										packages,
										cif,
										duty,
										insurance, 
										weight,
										yardPackages,
										area,
										exBondBeNooooooooo,'A',
										companyId, branchId, savedDtl.getCommodityId(), savedDtl.getNocTransId(), savedDtl.getNocNo(),
										savedEdit.getInBondingId(), savedDtl.getCommonBondingId());

								System.out.println(
										"editedRowInCfEcbondCrgDtl_____________________________________________________________"
												+ editedRowInCfEcbondCrgDtl);

								if (editedRowInCfEcbondCrgDtl > 0) 
								{
										int updateGrid =cfExBondGridRepository.updateCfexBondDtlAfterExBondGrid(yardPackages,
												area,
												companyId,branchId,
												savedDtl.getCommonBondingId(), savedDtl.getCommodityId(),
												savedDtl.getYardLocationId(), savedDtl.getBlockId(), savedDtl.getCellNoRow());
										
										System.out.println("updated row in cfexbond grid :"+updateGrid);
								
									
										CfinbondcrgDtl toUpdateInBondCrgDtl = cfexbondcrgDtlRepo.toUpdateInBondCrgDtl(companyId,
												branchId, item.getNocTransId(), savedDtl.getCommodityId(), savedDtl.getNocNo(),
												savedEdit.getInBondingId());

										if (toUpdateInBondCrgDtl != null) {
											int updateCfinbondcrgDtlAfterExbond = cfexbondcrgDtlRepo
													.updateCfinbondCrgDtlAfterExbond(
															toUpdateInBondCrgDtl.getExBondedPackages()
																	.add(savedDtl.getNewBondPackages())
																	.subtract(savedDtl.getOldBondPackages()),
																	
															toUpdateInBondCrgDtl.getExBondedCargoDuty()
																	.add(savedDtl.getNewBondCargoDuty()).subtract(
																			savedDtl.getOldBondCargoDuty()),
																	
															toUpdateInBondCrgDtl.getExBondedCIF().add(savedDtl.getNewBondCifValue())
																	.subtract(savedDtl.getOldBondCifValue()),
																	
															toUpdateInBondCrgDtl.getExBondedInsurance()
																	.add(savedDtl.getNewInsuranceValue()).subtract(
																			savedDtl.getOldInsuranceValue()),
																	
															toUpdateInBondCrgDtl.getExBondedGW().add(savedDtl.getNewBondGrossWt())
																	.subtract(savedDtl.getOldBondGrossWt()),
																	
															companyId, branchId, savedEdit.getInBondingId(), savedDtl.getNocNo(),
															savedDtl.getNocTransId(), savedDtl.getBoeNo(), savedDtl.getCommodityId());

											System.out.println("Update row count after exbond details after edit in details is"
													+ updateCfinbondcrgDtlAfterExbond);
										}
							
										

									CfinbondcrgHDRDtl findExistingHdrdtl = cfinbondCrgHdrDtlRepo.findExistingHdrdtl(
											companyId, branchId, savedDtl.getNocTransId(), savedDtl.getNocNo(),
											savedEdit.getInBondingId(), savedDtl.getCommodityId());
//
//									if (findExistingHdrdtl != null) {
//										int updateCfInbondCrgHdrDtl = cfinbondCrgHdrDtlRepo.updateCfinbondCrgDtlAfterExbond(
//												findExistingHdrdtl.getExBondedPackages() != null
//														? findExistingHdrdtl.getExBondedPackages()
//														: BigDecimal.ZERO.add(savedDtl.getNewBondPackages().subtract(
//																savedDtl.getOldBondPackages())),
//														
//												companyId, branchId, savedEdit.getInBondingId(), savedDtl.getNocNo(),
//												savedDtl.getNocTransId(), savedDtl.getBoeNo(), savedDtl.getCommodityId());
//
//										System.out.println(
//												"Update row count after exbond details is" + updateCfInbondCrgHdrDtl);
//
//									}
									
									if (findExistingHdrdtl != null) {
									    int updateCfInbondCrgHdrDtl = cfinbondCrgHdrDtlRepo.updateCfinbondCrgDtlAfterExbond(
									        Optional.ofNullable(findExistingHdrdtl.getExBondedPackages())
									            .orElse(BigDecimal.ZERO)
									            .add(savedDtl.getNewBondPackages().subtract(savedDtl.getOldBondPackages())),
									        
									        companyId, 
									        branchId, 
									        savedEdit.getInBondingId(), 
									        savedDtl.getNocNo(), 
									        savedDtl.getNocTransId(), 
									        savedDtl.getBoeNo(), 
									        savedDtl.getCommodityId()
									    );

									    System.out.println("Update row count after exbond details is " + updateCfInbondCrgHdrDtl);
									}


								}

							
						}

					}
					
					
				}


			}

		}

		System.out.println(
				"savededit______________________________________________________________________________________________________________________________________________");

		System.out.println(savedEdit);
		return new ResponseEntity<>(savedEdit, HttpStatus.OK);
	}

	
	
	
	
	
	
	
	
	
	
	@Transactional
	public ResponseEntity<?> approveDataFromExBondAuditTrailScreen(String companyId, String branchId, String flag, String user,
			Map<String, Object> requestBody) {

		ObjectMapper object = new ObjectMapper();
		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		CfexbondcrgEdit cfinbondcrg = object.convertValue(requestBody.get("inBond"), CfexbondcrgEdit.class);

		System.out.println("cfinbondcrg________________________" + cfinbondcrg.getInBondingId());

		if (cfinbondcrg.getInBondingId() == null || cfinbondcrg.getInBondingId().isEmpty() || cfinbondcrg.getSrNo()==null
				|| cfinbondcrg.getInBondingId().isBlank()) {
			return new ResponseEntity<>("Please First Save Data", HttpStatus.BAD_REQUEST);
		}

		if (cfinbondcrg != null && cfinbondcrg.getInBondingId() != null || !cfinbondcrg.getInBondingId().isEmpty()
				|| !cfinbondcrg.getInBondingId().isBlank()) {

			BigDecimal sumOfInbondFromDtl = cfexbondcrgEditRepository.getSumOfInBondPackages(companyId, branchId,
					cfinbondcrg.getExBondingId(), cfinbondcrg.getNocTransId());

			BigDecimal sumOfInbondFormGrid = cfExBondGridRepository.getSumOfInBondPackages(companyId, branchId,
					cfinbondcrg.getExBondingId(), cfinbondcrg.getNocTransId());

			System.out.println("sumOfInbondFromDtl: " + sumOfInbondFromDtl + " ______________ " + sumOfInbondFormGrid);

			if (sumOfInbondFromDtl == null || sumOfInbondFormGrid == null) {
				return new ResponseEntity<>("One of the sum values is null. Please check the data.",
						HttpStatus.BAD_REQUEST);
			}

			if (sumOfInbondFormGrid.compareTo(sumOfInbondFromDtl) != 0) {

				return new ResponseEntity<>("ExbondPackages do not match in yard, please add packages in grid.",
						HttpStatus.BAD_REQUEST);
			} else {
				int updateAfterApprov = cfexbondcrgEditRepository.updateApproveAfterExBondAuditTrail("A", companyId, branchId,
						cfinbondcrg.getInBondingId(),cfinbondcrg.getExBondingId(), cfinbondcrg.getNocNo(), cfinbondcrg.getNocTransId());
				System.out.println("updateAfterApprov row count " + updateAfterApprov);
			}
		}

		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
