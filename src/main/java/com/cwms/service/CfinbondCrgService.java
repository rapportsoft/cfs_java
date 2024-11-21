package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.entities.Branch;
import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.CfInBondGrid;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.CfinbondCommdtlEdit;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.entities.CfinbondcrgHDR;
import com.cwms.entities.CfinbondcrgHDRDtl;
import com.cwms.entities.Company;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfBondNocDtlRepository;
import com.cwms.repository.CfInBondGridRepository;
import com.cwms.repository.CfbondGatePassRepository;
import com.cwms.repository.CfbondnocRepository;
import com.cwms.repository.CfinbondCrgHdrDtlRepo;
import com.cwms.repository.CfinbondCrgHdrRepo;
import com.cwms.repository.CfinbondcrgDtlRepo;
import com.cwms.repository.CfinbondcrgRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.YardBlockCellRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@Service
public class CfinbondCrgService {

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
	public YardBlockCellRepository yardBlockCellRepository;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	
	
	
	
	
	
	@Autowired
	public CfbondGatePassRepository cfbondGatePassRepository;

	
	public ResponseEntity<List<CfinbondcrgDtl>> findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
			String compnayId, String branchId, String inBondingId, String nocTrandId) {

		List<CfinbondcrgDtl> data = cfinbondcrgDtlRepo
				.getAfterSave(compnayId, branchId, inBondingId,
						nocTrandId);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
	@Transactional
	public ResponseEntity<?> saveDataOfCfInbondCrg(String companyId, String branchId, String user, String flag,
			Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Cfinbondcrg cfinbondcrg = object.convertValue(requestBody.get("inBond"), Cfinbondcrg.class);

		System.out.println("cfinbondcrg_______________________" + cfinbondcrg.getShortagePackages());

		System.out.println("cfinbondcrgDtl_______________________" + cfinbondcrg.getBreakage() + " "
				+ cfinbondcrg.getDamagedQty() + " " + requestBody.get("nocDtl"));

		Object nocDtlObj = requestBody.get("nocDtl");
		List<CfinbondcrgDtl> cfinbondcrgDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			// If nocDtl is a list, deserialize it directly
			cfinbondcrgDtlList = object.convertValue(nocDtlObj, new TypeReference<List<CfinbondcrgDtl>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			// If nocDtl is a map, convert each map entry to CfinbondcrgDtl
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				CfinbondcrgDtl cfinbondcrgDtl = object.convertValue(entry.getValue(), CfinbondcrgDtl.class);
				cfinbondcrgDtlList.add(cfinbondcrgDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		System.out.println("flag________________" + flag);

		System.out.println("cfinbondcrgDtlList________________" + cfinbondcrgDtlList);

		CfInBondGrid saved = null;

		if (cfinbondcrg != null) 
		{
			if ("add".equals(flag)) 
			{
				Boolean exists = cfinbondCrgHdrRepo.isDataExist(companyId, branchId, cfinbondcrg.getNocTransId(),
						cfinbondcrg.getNocNo());
				if (exists) 
				{
					CfinbondcrgHDR cfinbondHDR = cfinbondCrgHdrRepo.findCfInBondCrgHdrForUpdationg(companyId, branchId,
							cfinbondcrg.getNocTransId(), cfinbondcrg.getNocNo());

					BigDecimal[] totalCif = { BigDecimal.ZERO };
					BigDecimal[] totalCargo = { BigDecimal.ZERO };
					BigDecimal[] totalInsurance = { BigDecimal.ZERO };
					BigDecimal[] totalInGrossWeight = { BigDecimal.ZERO };

					BigDecimal[] totalInBondPackages = { BigDecimal.ZERO };

					cfinbondcrgDtlList.forEach(item -> 
					{
						BigDecimal cifValue = item.getInbondCifValue();
						
System.out.println("item.getInbondCifValue()______________________________________________"+item.getInbondCifValue());

						BigDecimal cargoValue = item.getInbondCargoDuty();
						BigDecimal insuranceValue = item.getInbondInsuranceValue();
						BigDecimal inGrossWeight = item.getInbondGrossWt();
						BigDecimal inBondPack = item.getInbondGrossWt();

						if (cifValue != null) {
							totalCif[0] = totalCif[0].add(cifValue);
						}

						if (inBondPack != null) {
							totalInBondPackages[0] = totalInBondPackages[0].add(inBondPack);
						}

						if (cargoValue != null) {
							totalCargo[0] = totalCargo[0].add(cargoValue);
						}
						if (insuranceValue != null) {
							totalInsurance[0] = totalInsurance[0].add(insuranceValue);
						}
						if (inGrossWeight != null) {
							totalInGrossWeight[0] = totalInGrossWeight[0].add(inGrossWeight);
						}
					});

					int updateCfInBondCrgHDR = cfinbondCrgHdrRepo.updateCfInbondHeader(
							cfinbondcrg.getInBondedPackages().add(cfinbondHDR.getInBondedPackages()),
							totalInsurance[0].add(cfinbondHDR.getInbondInsuranceValue()),
							totalInGrossWeight[0].add(cfinbondHDR.getInbondGrossWt()),
							totalCif[0].add(cfinbondHDR.getCifValue()), totalCargo[0].add(cfinbondHDR.getCargoDuty()),
							cfinbondcrg.getShortagePackages().add(cfinbondHDR.getShortagePackages()),
							cfinbondcrg.getDamagedQty().add(cfinbondHDR.getDamagedQty()),
							cfinbondcrg.getBreakage().add(cfinbondHDR.getBreakage()),
							cfinbondcrg.getAreaOccupied()
									.add(cfinbondHDR.getAreaOccupied() != null ? cfinbondHDR.getAreaOccupied()
											: BigDecimal.ZERO),
							companyId, branchId, cfinbondcrg.getNocTransId(), cfinbondcrg.getNocNo());

					System.out.println("updateCfInBondCrgHDR_______________________________row" + updateCfInBondCrgHDR);

					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P03205", "2242");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectInBondingId = String.format("INBN%06d", nextNumericNextID1);

					cfinbondcrg.setCreatedBy(user);
					cfinbondcrg.setCreatedDate(new Date());
					cfinbondcrg.setApprovedBy(user);
					cfinbondcrg.setApprovedDate(new Date());
					cfinbondcrg.setStatus("N");
					cfinbondcrg.setFinYear("2242");
					cfinbondcrg.setNumberOfMarks(
							cfinbondcrg.getNumberOfMarks() != null ? cfinbondcrg.getNumberOfMarks() : "0");
					cfinbondcrg.setInBondingId(nectInBondingId);
					cfinbondcrg.setInBondingDate(new Date());
					cfinbondcrg.setInBondingHdrId(cfinbondHDR.getInBondingHdrId());
					cfinbondcrg.setBondValidityDate(cfinbondcrg.getBondValidityDate());
					cfinbondcrg.setInbondGrossWt(totalInGrossWeight[0]);

					cfinbondcrg.setInbondInsuranceValue(totalInsurance[0]);
					
					
					cfinbondcrg.setCifValue(totalCif[0]);
					cfinbondcrg.setCargoDuty(totalCargo[0]);
					
					cfinbondcrgRepo.save(cfinbondcrg);

					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03205", nectInBondingId, "2242");

					// int updateCfInBondCrgHDRDtl=0;

					int[] updateCfInBondCrgHDRDtl = null;

					String holdId11 = processNextIdRepository.findAuditTrail(companyId, branchId, "P03207", "2244");

					int lastNextNumericId11 = Integer.parseInt(holdId11.substring(4));

					int nextNumericNextID11 = lastNextNumericId11 + 1;

					String nectInBondingDTLId = String.format("INBD%06d", nextNumericNextID11);

					BigDecimal totalInbondedd = BigDecimal.ZERO;
					BigDecimal totalCif1 = BigDecimal.ZERO;
					BigDecimal totalCargo1 = BigDecimal.ZERO;
					BigDecimal totalInsurance1 = BigDecimal.ZERO;
					BigDecimal totalWeight = BigDecimal.ZERO;
					BigDecimal totalArea = BigDecimal.ZERO;

					for (CfinbondcrgDtl item : cfinbondcrgDtlList) 
					{

						totalCif1 = totalCif1.add(item.getInbondCifValue());
						totalInbondedd = totalInbondedd.add(item.getInBondedPackages());
						totalCargo1 = totalCargo1.add(item.getInbondCargoDuty());
						totalInsurance1 = totalInsurance1.add(item.getInbondInsuranceValue());
						totalWeight = totalWeight.add(item.getInbondGrossWt());
						totalArea = totalArea
								.add(item.getAreaOccupied() != null ? item.getAreaOccupied() : BigDecimal.ZERO);

						CfinbondcrgDtl crgDetails = new CfinbondcrgDtl();

						crgDetails.setCreatedBy(user);
						crgDetails.setCreatedDate(new Date());
						crgDetails.setApprovedBy(user);
						crgDetails.setApprovedDate(new Date());
						crgDetails.setBoeNo(cfinbondcrg.getBoeNo());
						crgDetails.setBondingDate(cfinbondcrg.getBondingDate());
						crgDetails.setBondingNo(cfinbondcrg.getBondingNo());
						crgDetails.setBranchId(branchId);
						crgDetails.setCompanyId(companyId);
						crgDetails.setBreakage(item.getBreakage());
						crgDetails.setCargoDuty(item.getCargoDuty());
						crgDetails.setCfBondDtlId(item.getCfBondDtlId());
						crgDetails.setCifValue(item.getCifValue());
						crgDetails.setComments(cfinbondcrg.getComments());
						crgDetails.setCommodityDescription(item.getCommodityDescription());
						crgDetails.setDamagedQty(item.getDamagedQty());
						crgDetails.setFinYear("2242");
						crgDetails.setGrossWeight(item.getGrossWeight());
						crgDetails.setInbondCargoDuty(item.getInbondCargoDuty());
						crgDetails.setInsuranceValue(item.getInsuranceValue());
						
//						crgDetails.setInbondCifValue(item.getInBondedPackages());
						crgDetails.setInbondCifValue(item.getInbondCifValue());
						
						crgDetails.setInBondedPackages(item.getInBondedPackages());
						crgDetails.setInbondGrossWt(item.getInbondGrossWt());
						crgDetails.setInBondingDate(cfinbondcrg.getInBondingDate());
						crgDetails.setAreaOccupied(item.getAreaOccupied());
						crgDetails.setInBondingDtlId(nectInBondingDTLId);

						crgDetails.setInBondingId(cfinbondcrg.getInBondingId());

						crgDetails.setInbondInsuranceValue(item.getInbondInsuranceValue());
						crgDetails.setNocDate(cfinbondcrg.getNocDate());
						crgDetails.setNocNo(cfinbondcrg.getNocNo());
						crgDetails.setNocTransId(cfinbondcrg.getNocTransId());
						crgDetails.setNocTransDate(cfinbondcrg.getNocTransDate());
						crgDetails.setNocPackages(item.getNocPackages());
						crgDetails.setTypeOfPackage(item.getTypeOfPackage());
						crgDetails.setShortagePackages(item.getShortagePackages());
						crgDetails.setBreakage(item.getBreakage());
						crgDetails.setDamagedQty(item.getDamagedQty());
						crgDetails.setStatus("A");

						crgDetails.setCellArea(item.getCellArea());
						crgDetails.setCellAreaAllocated(item.getCellAreaAllocated());
						crgDetails.setYardPackages(item.getYardPackages());

						String yardLocationId = item.getEditedBy();
						String[] parts = yardLocationId.split("-");

						if (parts.length == 3) {
							String yardLocation = parts[0]; // EYARD01
							String blockId = parts[1]; // A
							String cellNoRow = parts[2]; // 2

							System.out.println("yardLocation_____________________" + yardLocation);
							System.out.println("blockId__________________________" + blockId);
							System.out.println("cellNoRow________________________" + cellNoRow);
							// Set extracted values to cfGateIn object

							crgDetails.setYardLocationId(yardLocation);
							crgDetails.setBlockId(blockId);
							crgDetails.setCellNoRow(cellNoRow);
						}

						CfinbondcrgDtl savedDtl = cfinbondcrgDtlRepo.save(crgDetails);

						// update cfinbond grid table

						if (savedDtl != null) {
							CfInBondGrid cf = new CfInBondGrid();
							Integer maxSrNo = cfInBondGridRepository.getMaxSrNo(companyId, branchId,
									savedDtl.getInBondingId(), savedDtl.getCfBondDtlId());

							cf.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
							cf.setCompanyId(companyId);
							cf.setBranchId(branchId);
							cf.setCreatedBy(user);
							cf.setCreatedDate(new Date());
							cf.setApprovedBy(user);
							cf.setApprovedDate(new Date());
							cf.setInBondingId(savedDtl.getInBondingId());
							cf.setCfBondDtlId(savedDtl.getCfBondDtlId());
							cf.setYardLocation(savedDtl.getYardLocationId());
							cf.setYardBlock(savedDtl.getBlockId());
							cf.setBlockCellNo(savedDtl.getCellNoRow());

							cf.setCellAreaAllocated(savedDtl.getCellAreaAllocated());

//								 String getCreatedByStr = item.getCreatedBy(); // Get the string value
//								    BigDecimal getCreatedByByDecimal = new BigDecimal(getCreatedByStr); // Convert to BigDecimal
//								  
							cf.setCellArea(savedDtl.getCellArea());
							cf.setCellAreaUsed(item.getInsuranceValue());

							cf.setNocTransId(savedDtl.getNocTransId());
							cf.setFinYear("2025");

							cf.setInBondPackages(savedDtl.getYardPackages());
							cf.setStatus("A");

							saved = cfInBondGridRepository.save(cf);

							if (saved != null) 
							{
								YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
										saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());

								if (existingCell != null) {
									existingCell.setCellAreaUsed(
											existingCell.getCellAreaUsed() != null ? existingCell.getCellAreaUsed()
													: BigDecimal.ZERO.add(saved.getCellAreaAllocated() != null
															? saved.getCellAreaAllocated()
															: BigDecimal.ZERO));
									yardBlockCellRepository.save(existingCell);
								}
							}
						}

						CfBondNocDtl getDataOfDtlId = cfBondNocDtlRepository.getDataOfDtlId(companyId, branchId,
								item.getCfBondDtlId(), item.getNocTransId(), item.getNocNo());

						if (getDataOfDtlId != null) 
						{

							int updateNoCDtl = cfBondNocDtlRepository.updateNocDtlFromInBonding(
									getDataOfDtlId.getInBondedPackages().add(item.getInBondedPackages()),
									getDataOfDtlId.getInbondGrossWt().add(item.getInbondGrossWt()),
									getDataOfDtlId.getInbondInsuranceValue().add(item.getInbondInsuranceValue()),
									getDataOfDtlId.getInbondCifValue().add(item.getInbondCifValue()),
									getDataOfDtlId.getInbondCargoDuty().add(item.getInbondCargoDuty()),
									getDataOfDtlId.getShortagePackages().add(item.getShortagePackages()),
									getDataOfDtlId.getDamagedQty().add(item.getDamagedQty()),
									getDataOfDtlId.getBreakage().add(item.getBreakage()), cfinbondcrg.getBondingNo(),
									companyId, branchId, item.getNocTransId(), item.getNocNo(), item.getCfBondDtlId());

							System.out.println("updateNoCDtl__________Row___In__EXits_NOC______________________Dtl"
									+ updateNoCDtl);
						}

						System.out.println("item___________" + item.getShortagePackages());

						System.out.println("item___________" + item.getDamagedQty());

						System.out.println("item___________" + item.getBreakage());

						CfinbondcrgHDRDtl existing = cfinbondCrgHdrDtlRepo.isDataExistCfinbondcrgHDRDtl(companyId,
								branchId, item.getNocTransId(), item.getNocNo(), item.getCfBondDtlId());

						if (existing != null)

						{
							int updateCfInBondCrgHdrDtl = cfinbondCrgHdrDtlRepo.updateCfInbondHeaderDtl(
									item.getInBondedPackages().add(existing.getInBondedPackages()),
									item.getInbondInsuranceValue().add(existing.getInbondInsuranceValue()),
									item.getInbondGrossWt().add(existing.getInbondGrossWt()),
									item.getInbondCifValue().add(existing.getInbondCifValue()),
									item.getInbondCargoDuty().add(existing.getInbondCargoDuty()),
									cfinbondcrg.getShortagePackages() != null
											? cfinbondcrg.getShortagePackages().add(existing.getShortagePackages())
											: BigDecimal.ZERO,
									cfinbondcrg.getDamagedQty() != null
											? cfinbondcrg.getDamagedQty().add(existing.getDamagedQty())
											: BigDecimal.ZERO,
									cfinbondcrg.getBreakage() != null
											? cfinbondcrg.getBreakage().add(existing.getBreakage())
											: BigDecimal.ZERO,
									cfinbondcrg.getAreaOccupied() != null
											? cfinbondcrg.getAreaOccupied().add(existing.getAreaOccupied())
											: BigDecimal.ZERO,
									companyId, branchId, item.getNocTransId(), item.getNocNo(), item.getCfBondDtlId());

							System.out.println("updateCfInBondCrgHdrDtl__________________" + updateCfInBondCrgHdrDtl);

						} else {
							CfinbondcrgHDRDtl cfinbondHDRDtl = new CfinbondcrgHDRDtl();

							cfinbondHDRDtl.setCreatedBy(user);
							cfinbondHDRDtl.setCreatedDate(new Date());
							cfinbondHDRDtl.setApprovedBy(user);
							cfinbondHDRDtl.setApprovedDate(new Date());
							cfinbondHDRDtl.setBoeNo(cfinbondcrg.getBoeNo());
							cfinbondHDRDtl.setBondingDate(cfinbondcrg.getInBondingDate());
							cfinbondHDRDtl.setBondingNo(cfinbondcrg.getInBondingId());
							cfinbondHDRDtl.setBranchId(branchId);
							cfinbondHDRDtl.setCompanyId(companyId);
							cfinbondHDRDtl.setBreakage(item.getBreakage());
							cfinbondHDRDtl.setCargoDuty(item.getCargoDuty());
							cfinbondHDRDtl.setCfBondDtlId(item.getCfBondDtlId());
							cfinbondHDRDtl.setCifValue(item.getCifValue());
							cfinbondHDRDtl.setComments(cfinbondcrg.getComments());
							cfinbondHDRDtl.setCommodityDescription(item.getCommodityDescription());
							cfinbondHDRDtl.setDamagedQty(item.getDamagedQty());
							cfinbondHDRDtl.setFinYear("2242");
							cfinbondHDRDtl.setGrossWeight(item.getGrossWeight());
							cfinbondHDRDtl.setInbondCargoDuty(item.getInbondCargoDuty());
							cfinbondHDRDtl.setInbondCifValue(item.getInBondedPackages());
							cfinbondHDRDtl.setInBondedPackages(item.getInBondedPackages());
							cfinbondHDRDtl.setInbondGrossWt(item.getInbondGrossWt());
							cfinbondHDRDtl.setInBondingDate(cfinbondcrg.getInBondingDate());
							cfinbondHDRDtl.setAreaOccupied(item.getAreaOccupied());
							cfinbondHDRDtl.setInBondingDtlId(nectInBondingDTLId);

							cfinbondHDRDtl.setInBondingId(cfinbondcrg.getInBondingHdrId());

							cfinbondHDRDtl.setInbondInsuranceValue(item.getInbondInsuranceValue());
							cfinbondHDRDtl.setNocDate(cfinbondcrg.getNocDate());
							cfinbondHDRDtl.setNocNo(cfinbondcrg.getNocNo());
							cfinbondHDRDtl.setNocTransId(cfinbondcrg.getNocTransId());
							cfinbondHDRDtl.setNocTransDate(cfinbondcrg.getNocTransDate());
							cfinbondHDRDtl.setNocPackages(item.getNocPackages());
							cfinbondHDRDtl.setTypeOfPackage(item.getTypeOfPackage());
							cfinbondHDRDtl.setShortagePackages(item.getShortagePackages());
							cfinbondHDRDtl.setBreakage(item.getBreakage());
							cfinbondHDRDtl.setDamagedQty(item.getDamagedQty());
							cfinbondHDRDtl.setStatus("A");

							cfinbondCrgHdrDtlRepo.save(cfinbondHDRDtl);
						}

						processNextIdRepository.updateAuditTrail(companyId, branchId, "P03207", nectInBondingDTLId,
								"2244");
					}

					Cfbondnoc findCfBondNocForUpdationg = cfbondnocRepository.findCfBondNocForUpdationg(companyId,
							branchId, cfinbondcrg.getNocTransId(), cfinbondcrg.getNocNo());
					
					
					System.out.println("findCfBondNocForUpdationg_______________________"+findCfBondNocForUpdationg);
					if (findCfBondNocForUpdationg != null) 
					{
						int updateInCfBondNoc = cfbondnocRepository.updateCfBondNocAfterInBonding(
								findCfBondNocForUpdationg.getInBondedPackages().add(totalInbondedd),
								findCfBondNocForUpdationg.getInbondGrossWt().add(totalWeight),
								findCfBondNocForUpdationg.getInbondInsuranceValue().add(totalInsurance1),
								findCfBondNocForUpdationg.getInbondCifValue().add(totalCif1),
								findCfBondNocForUpdationg.getInbondCargoDuty().add(totalCargo1),
								cfinbondcrg.getBondingNo(), cfinbondcrg.getBondingDate(),
								cfinbondcrg.getBondValidityDate(), companyId, branchId, cfinbondcrg.getNocTransId(),
								cfinbondcrg.getNocNo());

						System.out.println("Update noc after inbond in exist" + updateInCfBondNoc);
					}

				} 
				
				else
				{

					System.out.println("flag_jgjhjhvnvnbvbvbvbcgf_______________" + flag);
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P03205", "2242");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectInBondingId = String.format("INBN%06d", nextNumericNextID1);

					String holdId = processNextIdRepository.findAuditTrail(companyId, branchId, "P03206", "2243");

					int lastNextNumericId = Integer.parseInt(holdId.substring(4));

					int nextNumericNextID = lastNextNumericId + 1;

					String nectInBondingHDRId = String.format("INBH%06d", nextNumericNextID);

					BigDecimal[] totalCif = { BigDecimal.ZERO };
					BigDecimal[] totalCargo = { BigDecimal.ZERO };
					BigDecimal[] totalInsurance = { BigDecimal.ZERO };
					BigDecimal[] totalInGrossWeight = { BigDecimal.ZERO };

					cfinbondcrgDtlList.forEach(item -> {
						BigDecimal cifValue = item.getInbondCifValue();
						BigDecimal cargoValue = item.getInbondCargoDuty();
						BigDecimal insuranceValue = item.getInbondInsuranceValue();
						BigDecimal inGrossWeight = item.getInbondGrossWt();

						if (cifValue != null) {
							totalCif[0] = totalCif[0].add(cifValue);
						}
						if (cargoValue != null) {
							totalCargo[0] = totalCargo[0].add(cargoValue);
						}
						if (insuranceValue != null) {
							totalInsurance[0] = totalInsurance[0].add(insuranceValue);
						}
						if (inGrossWeight != null) {
							totalInGrossWeight[0] = totalInGrossWeight[0].add(inGrossWeight);
						}
					});

					System.out.println("Total CIF Value: " + totalCif[0]);
					System.out.println("Total Cargo Value: " + totalCargo[0]);
					System.out.println("Total Insurance Value: " + totalInsurance[0]);
					System.out.println("Total Insurance Value: " + totalInGrossWeight[0]);

					cfinbondcrg.setCreatedBy(user);
					cfinbondcrg.setCreatedDate(new Date());
					cfinbondcrg.setApprovedBy(user);
					cfinbondcrg.setApprovedDate(new Date());
					cfinbondcrg.setInBondingDate(new Date());
					cfinbondcrg.setStatus("N");
					cfinbondcrg.setFinYear("2242");
					cfinbondcrg.setNumberOfMarks(
							cfinbondcrg.getNumberOfMarks() != null ? cfinbondcrg.getNumberOfMarks() : "0");
					cfinbondcrg.setInBondingId(nectInBondingId);
					cfinbondcrg.setInBondingHdrId(nectInBondingHDRId);
					cfinbondcrg.setBondValidityDate(cfinbondcrg.getBondValidityDate());

					
					cfinbondcrg.setCifValue(totalCif[0]);
					cfinbondcrg.setCargoDuty(totalCargo[0]);
					
					CfinbondcrgHDR cfinbondHDR = new CfinbondcrgHDR();

					cfinbondHDR.setApprovedBy(user);
					cfinbondHDR.setFinYear("2242");
					cfinbondHDR.setApprovedDate(new Date());
					cfinbondHDR.setAreaAllocated(cfinbondcrg.getAreaAllocated());
					cfinbondHDR.setAreaOccupied(
							cfinbondcrg.getAreaOccupied() != null ? cfinbondcrg.getAreaOccupied() : BigDecimal.ZERO);
					cfinbondHDR.setBoeDate(cfinbondcrg.getBoeDate());
					cfinbondHDR.setBoeNo(cfinbondcrg.getBoeNo());
					cfinbondHDR.setBondingDate(cfinbondcrg.getInBondingDate());
					cfinbondHDR.setBondingNo(cfinbondcrg.getBondingNo());

					cfinbondHDR.setAreaOccupied(cfinbondcrg.getAreaOccupied());

					cfinbondHDR.setInBond20Ft(cfinbondcrg.getInBond20Ft());
					cfinbondHDR.setShift(cfinbondcrg.getShift() != null ? cfinbondcrg.getShift() : "");
					cfinbondHDR.setInBond40Ft(cfinbondcrg.getInBond40Ft());

					cfinbondHDR.setInBondingHdrId(nectInBondingHDRId);
					cfinbondHDR.setBondYard(cfinbondcrg.getBondYard());
					cfinbondHDR.setBranchId(branchId);
					cfinbondHDR.setCompanyId(companyId);
					cfinbondHDR.setStatus("A");
					cfinbondHDR.setBreakage(cfinbondcrg.getBreakage());
					cfinbondHDR.setCargoCondition(cfinbondcrg.getCargoCondition());

					cfinbondHDR.setNocFromDate(cfinbondcrg.getNocFromDate());
					cfinbondHDR.setCargoDuty(totalCargo[0]);
					cfinbondHDR.setCifValue(totalCif[0]);
					cfinbondHDR.setInsuranceValue(totalInsurance[0]);
					cfinbondHDR.setNocPackages(cfinbondcrg.getNocPackages());
					cfinbondHDR.setGateInPackages(null);
					cfinbondHDR.setInbondInsuranceValue(totalInsurance[0]);
					cfinbondHDR.setInbondGrossWt(totalInGrossWeight[0]);
					cfinbondHDR.setInBondedPackages(cfinbondcrg.getInBondedPackages());
					cfinbondHDR.setGrossWeight(null);

					cfinbondHDR.setCha(cfinbondcrg.getCha());
					cfinbondHDR.setChaCode(cfinbondcrg.getChaCode());
					cfinbondHDR.setComments(cfinbondcrg.getComments());
					cfinbondHDR.setCreatedBy(user);
					cfinbondHDR.setCreatedDate(new Date());
					cfinbondHDR.setDamagedQty(cfinbondcrg.getDamagedQty());
					cfinbondHDR.setBondValidityDate(cfinbondcrg.getBondValidityDate());

					cfinbondHDR.setUom(cfinbondcrg.getUom());
					cfinbondHDR.setShortagePackages(cfinbondcrg.getShortagePackages());
					cfinbondHDR.setShift(cfinbondcrg.getShift());
					cfinbondHDR.setSection49(cfinbondcrg.getSection49());
					cfinbondHDR.setProfitcentreId(cfinbondcrg.getProfitcentreId());
					cfinbondHDR.setOtlNo(cfinbondcrg.getOtlNo());
					cfinbondHDR.setNumberOfMarks(
							cfinbondcrg.getNumberOfMarks() != null ? cfinbondcrg.getNumberOfMarks() : "0");
					cfinbondHDR.setNocValidityDate(cfinbondcrg.getNocValidityDate());
					cfinbondHDR.setNocTransId(cfinbondcrg.getNocTransId());
					cfinbondHDR.setNocTransDate(cfinbondcrg.getNocTransDate());
					cfinbondHDR.setNocNo(cfinbondcrg.getNocNo());
					cfinbondHDR.setNocDate(cfinbondcrg.getNocDate());
					cfinbondHDR.setImporterAddress1(cfinbondcrg.getImporterAddress1());
					cfinbondHDR.setImporterAddress2(cfinbondcrg.getImporterAddress2());
					cfinbondHDR.setImporterAddress3(cfinbondcrg.getImporterAddress3());
					cfinbondHDR.setImporterId(cfinbondcrg.getImporterId());
					cfinbondHDR.setIgmDate(cfinbondcrg.getIgmDate());
					cfinbondHDR.setIgmNo(cfinbondcrg.getIgmNo());
					cfinbondHDR.setIgmLineNo(cfinbondcrg.getIgmLineNo());
					cfinbondHDR.setInBondingDate(cfinbondcrg.getInBondingDate());

//					cfinbondCrgHdrRepo.save(cfinbondHDR);
//					cfinbondcrgRepo.save(cfinbondcrg);
//	
//					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03205", nectInBondingId, "2242");
//					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03206", nectInBondingHDRId, "2243");
//					
					BigDecimal totalCifD = BigDecimal.ZERO;
					BigDecimal totalCargod = BigDecimal.ZERO;
					BigDecimal totalInsuranced = BigDecimal.ZERO;
					BigDecimal totalWeightd = BigDecimal.ZERO;
					BigDecimal totalInbonded = BigDecimal.ZERO;
					BigDecimal totalArea = BigDecimal.ZERO;

//					 cfinbondcrgDtlList.forEach(item -> {

					for (CfinbondcrgDtl item : cfinbondcrgDtlList) {

						totalCifD = totalCifD.add(item.getInbondCifValue());

						totalCargod = totalCargod.add(item.getInbondCargoDuty());
						totalInsuranced = totalInsuranced.add(item.getInbondInsuranceValue());
						totalWeightd = totalWeightd.add(item.getInbondGrossWt());
						totalInbonded = totalInbonded.add(item.getInBondedPackages());
						totalArea = totalArea
								.add(item.getAreaOccupied() != null ? item.getAreaOccupied() : BigDecimal.ZERO);

						String holdId11 = processNextIdRepository.findAuditTrail(companyId, branchId, "P03207", "2244");

						int lastNextNumericId11 = Integer.parseInt(holdId11.substring(4));

						int nextNumericNextID11 = lastNextNumericId11 + 1;

						String nectInBondingDTLId = String.format("INBD%06d", nextNumericNextID11);

						System.out.println("item___________________________________________" + item);

						CfinbondcrgDtl crgDetails = new CfinbondcrgDtl();

						crgDetails.setCreatedBy(user);
						crgDetails.setCreatedDate(new Date());
						crgDetails.setApprovedBy(user);
						crgDetails.setApprovedDate(new Date());
						crgDetails.setBoeNo(cfinbondcrg.getBoeNo());
						crgDetails.setBondingDate(cfinbondcrg.getBondingDate());
						crgDetails.setBondingNo(cfinbondcrg.getBondingNo());

						crgDetails.setBranchId(branchId);
						crgDetails.setCompanyId(companyId);
						crgDetails.setBreakage(item.getBreakage());
						crgDetails.setCargoDuty(item.getCargoDuty());
						crgDetails.setCfBondDtlId(item.getCfBondDtlId());
						crgDetails.setCifValue(item.getCifValue());
						crgDetails.setComments(cfinbondcrg.getComments());
						crgDetails.setCommodityDescription(item.getCommodityDescription());
						crgDetails.setDamagedQty(item.getDamagedQty());
						crgDetails.setFinYear("2242");
						crgDetails.setGrossWeight(item.getGrossWeight());
						crgDetails.setInbondCargoDuty(item.getInbondCargoDuty());
//						crgDetails.setInbondCifValue(item.getInBondedPackages());
						crgDetails.setInbondCifValue(item.getInbondCifValue());
						crgDetails.setInBondedPackages(item.getInBondedPackages());
						crgDetails.setInbondGrossWt(item.getInbondGrossWt());
						crgDetails.setInBondingDate(cfinbondcrg.getInBondingDate());
						crgDetails.setCellArea(item.getCellArea());
						crgDetails.setInBondingDtlId(nectInBondingDTLId);
						crgDetails.setInsuranceValue(item.getInsuranceValue());
						crgDetails.setInBondingId(cfinbondcrg.getInBondingId());

						crgDetails.setInbondInsuranceValue(item.getInbondInsuranceValue());
						crgDetails.setNocDate(cfinbondcrg.getNocDate());
						crgDetails.setNocNo(cfinbondcrg.getNocNo());
						crgDetails.setNocTransId(cfinbondcrg.getNocTransId());
						crgDetails.setNocTransDate(cfinbondcrg.getNocTransDate());
						crgDetails.setNocPackages(item.getNocPackages());
						crgDetails.setTypeOfPackage(item.getTypeOfPackage());
						crgDetails.setShortagePackages(item.getShortagePackages());
						crgDetails.setBreakage(item.getBreakage());
						crgDetails.setDamagedQty(item.getDamagedQty());
						crgDetails.setStatus("A");
						crgDetails.setAreaOccupied(item.getAreaOccupied());

						crgDetails.setCellAreaAllocated(item.getCellAreaAllocated());
						crgDetails.setYardPackages(item.getYardPackages());

						String yardLocationId = item.getEditedBy();
						String[] parts = yardLocationId.split("-");

						if (parts.length == 3) {
							String yardLocation = parts[0]; // EYARD01
							String blockId = parts[1]; // A
							String cellNoRow = parts[2]; // 2

							System.out.println("yardLocation_____________________" + yardLocation);
							System.out.println("blockId__________________________" + blockId);
							System.out.println("cellNoRow________________________" + cellNoRow);
							// Set extracted values to cfGateIn object

							crgDetails.setYardLocationId(yardLocation);
							crgDetails.setBlockId(blockId);
							crgDetails.setCellNoRow(cellNoRow);
						}

						CfinbondcrgDtl savedDtl = cfinbondcrgDtlRepo.save(crgDetails);

						if (savedDtl != null) {
							CfInBondGrid cf = new CfInBondGrid();
							Integer maxSrNo = cfInBondGridRepository.getMaxSrNo(companyId, branchId,
									savedDtl.getInBondingId(), savedDtl.getCfBondDtlId());

							cf.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
							cf.setCompanyId(companyId);
							cf.setBranchId(branchId);
							cf.setCreatedBy(user);
							cf.setCreatedDate(new Date());
							cf.setApprovedBy(user);
							cf.setApprovedDate(new Date());
							cf.setInBondingId(savedDtl.getInBondingId());
							cf.setCfBondDtlId(savedDtl.getCfBondDtlId());
							cf.setYardLocation(savedDtl.getYardLocationId());
							cf.setYardBlock(savedDtl.getBlockId());
							cf.setBlockCellNo(savedDtl.getCellNoRow());

//								 String approvedByStr = item.getApprovedBy(); // Get the string value
//								    BigDecimal approvedByDecimal = new BigDecimal(approvedByStr); // Convert to BigDecimal
//								    
//								cf.setCellArea(approvedByDecimal);
//								
//								
//								 String getCreatedByStr = item.getCreatedBy(); // Get the string value
//								    BigDecimal getCreatedByByDecimal = new BigDecimal(getCreatedByStr); // Convert to BigDecimal
//								    
//								cf.setCellAreaUsed(getCreatedByByDecimal);

							cf.setCellArea(savedDtl.getCellArea());
							cf.setCellAreaUsed(item.getInsuranceValue());
							cf.setCellAreaAllocated(savedDtl.getCellAreaAllocated());
							cf.setNocTransId(savedDtl.getNocTransId());
							cf.setFinYear("2025");

							cf.setInBondPackages(savedDtl.getYardPackages());
							cf.setStatus("A");

							saved = cfInBondGridRepository.save(cf);

							if (saved != null) {
								YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
										saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());

								if (existingCell != null) {
									existingCell.setCellAreaUsed(
											existingCell.getCellAreaUsed() != null ? existingCell.getCellAreaUsed()
													: BigDecimal.ZERO.add(saved.getCellAreaAllocated() != null
															? saved.getCellAreaAllocated()
															: BigDecimal.ZERO));
									yardBlockCellRepository.save(existingCell);
								}
							}
						}

						int updateNoCDtl = cfBondNocDtlRepository.updateNocDtlFromInBonding(item.getInBondedPackages(),
								item.getInbondGrossWt(), item.getInbondInsuranceValue(), item.getInbondCifValue(),
								item.getInbondCargoDuty(), item.getShortagePackages(), item.getDamagedQty(),
								item.getBreakage(), cfinbondcrg.getBondingNo(), companyId, branchId,
								item.getNocTransId(), item.getNocNo(), item.getCfBondDtlId());

						System.out
								.println("updateNoCDtl__________Row___In__NOC______________________Dtl" + updateNoCDtl);

						CfinbondcrgHDRDtl cfinbondHDRDtl = new CfinbondcrgHDRDtl();

						cfinbondHDRDtl.setCreatedBy(user);
						cfinbondHDRDtl.setCreatedDate(new Date());
						cfinbondHDRDtl.setApprovedBy(user);
						cfinbondHDRDtl.setApprovedDate(new Date());
						cfinbondHDRDtl.setBoeNo(cfinbondcrg.getBoeNo());
						cfinbondHDRDtl.setBondingDate(cfinbondcrg.getInBondingDate());
						cfinbondHDRDtl.setBondingNo(cfinbondcrg.getInBondingId());

						cfinbondHDRDtl.setBranchId(branchId);
						cfinbondHDRDtl.setCompanyId(companyId);
						cfinbondHDRDtl.setBreakage(item.getBreakage());
						cfinbondHDRDtl.setCargoDuty(item.getCargoDuty());
						cfinbondHDRDtl.setCfBondDtlId(item.getCfBondDtlId());
						cfinbondHDRDtl.setCifValue(item.getCifValue());
						cfinbondHDRDtl.setComments(cfinbondcrg.getComments());
						cfinbondHDRDtl.setCommodityDescription(item.getCommodityDescription());
						cfinbondHDRDtl.setDamagedQty(item.getDamagedQty());
						cfinbondHDRDtl.setFinYear("2242");
						cfinbondHDRDtl.setGrossWeight(item.getGrossWeight());
						cfinbondHDRDtl.setInbondCargoDuty(item.getInbondCargoDuty());
						cfinbondHDRDtl.setInbondCifValue(item.getInBondedPackages());
						cfinbondHDRDtl.setInBondedPackages(item.getInBondedPackages());
						cfinbondHDRDtl.setInbondGrossWt(item.getInbondGrossWt());
						cfinbondHDRDtl.setInBondingDate(cfinbondcrg.getInBondingDate());
						cfinbondHDRDtl.setAreaOccupied(item.getAreaOccupied());
						cfinbondHDRDtl.setInBondingDtlId(nectInBondingDTLId);

						cfinbondHDRDtl.setInBondingId(cfinbondcrg.getInBondingHdrId());

						cfinbondHDRDtl.setInbondInsuranceValue(item.getInbondInsuranceValue());
						cfinbondHDRDtl.setNocDate(cfinbondcrg.getNocDate());
						cfinbondHDRDtl.setNocNo(cfinbondcrg.getNocNo());
						cfinbondHDRDtl.setNocTransId(cfinbondcrg.getNocTransId());
						cfinbondHDRDtl.setNocTransDate(cfinbondcrg.getNocTransDate());
						cfinbondHDRDtl.setNocPackages(item.getNocPackages());
						cfinbondHDRDtl.setTypeOfPackage(item.getTypeOfPackage());
						cfinbondHDRDtl.setShortagePackages(item.getShortagePackages());
						cfinbondHDRDtl.setBreakage(item.getBreakage());
						cfinbondHDRDtl.setDamagedQty(item.getDamagedQty());

						cfinbondHDRDtl.setStatus("A");

						cfinbondCrgHdrDtlRepo.save(cfinbondHDRDtl);

//					    });
						processNextIdRepository.updateAuditTrail(companyId, branchId, "P03207", nectInBondingDTLId,
								"2244");
					}

					cfinbondcrg.setInbondGrossWt(totalWeightd);
					cfinbondcrg.setInbondInsuranceValue(totalInsuranced);
					
					
					cfinbondCrgHdrRepo.save(cfinbondHDR);
					cfinbondcrgRepo.save(cfinbondcrg);
					
					
					

					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03205", nectInBondingId, "2242");
					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03206", nectInBondingHDRId, "2243");

					
					
					
				
					
					
					int updateInCfBondNoc = cfbondnocRepository.updateCfBondNocAfterInBonding(totalInbonded,
							totalWeightd, totalInsuranced, totalCifD, totalCargod, cfinbondcrg.getBondingNo(),
							cfinbondcrg.getBondingDate(), cfinbondcrg.getBondValidityDate(), companyId, branchId,
							cfinbondcrg.getNocTransId(), cfinbondcrg.getNocNo());
					System.out.println("Update noc after inbond " + updateInCfBondNoc);

				}

			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			else {
				CfinbondcrgHDR findCfBondCrgHDRData = null;
				CfinbondcrgHDRDtl findCfBondCrgHDRDTLData = null;
				CfinbondcrgDtl findCfBondCrgDTLData = null;
				Cfinbondcrg findCfBondCrgData = cfinbondcrgRepo.findCfBondCrgData(companyId, branchId,
						cfinbondcrg.getNocTransId(), cfinbondcrg.getInBondingId(), cfinbondcrg.getNocNo());

				if (findCfBondCrgData != null) 
				{
					System.out.println("in edit");
					BigDecimal totalInbondedd = BigDecimal.ZERO;
					BigDecimal totalCif = BigDecimal.ZERO;
					BigDecimal totalCargo = BigDecimal.ZERO;
					BigDecimal totalInsurance = BigDecimal.ZERO;
					BigDecimal totalWeight = BigDecimal.ZERO;
					BigDecimal totalArea = BigDecimal.ZERO;

//					 cfinbondcrgDtlList.forEach(item -> {

					for (CfinbondcrgDtl item : cfinbondcrgDtlList) 
					{

						System.out.println("yardLocation_____________________cfinbondcrgDtlList :" + cfinbondcrgDtlList);

						System.out.println("findCfBondCrgDTLData__________________________"+ item.getInBondingId());
						totalCif = totalCif.add(item.getInbondCifValue());
						totalInbondedd = totalInbondedd.add(item.getInBondedPackages());
						totalCargo = totalCargo.add(item.getInbondCargoDuty());
						totalInsurance = totalInsurance.add(item.getInbondInsuranceValue());
						totalWeight = totalWeight.add(item.getInbondGrossWt());
						totalArea = totalArea.add(item.getAreaOccupied());

						String holdId11 = processNextIdRepository.findAuditTrail(companyId, branchId, "P03207", "2244");

						int lastNextNumericId11 = Integer.parseInt(holdId11.substring(4));

						int nextNumericNextID11 = lastNextNumericId11 + 1;

						String nectInBondingDTLId = String.format("INBD%06d", nextNumericNextID11);

						findCfBondCrgDTLData = cfinbondcrgDtlRepo.findCfBondCrgDTLData(companyId, branchId,item.getInBondingId(),
								item.getNocTransId(), item.getCfBondDtlId(), item.getNocNo());

						
						if (findCfBondCrgDTLData != null) 
						{

							findCfBondCrgHDRDTLData = cfinbondCrgHdrDtlRepo.findCfBondCrgHDRDTLData(companyId, branchId,
									findCfBondCrgDTLData.getNocTransId(), findCfBondCrgDTLData.getInBondingDtlId(),
									findCfBondCrgDTLData.getNocNo());

							System.out.println("findCfBondCrgHDRDTLData___________" + findCfBondCrgHDRDTLData);

							System.out.println(findCfBondCrgHDRDTLData.getInBondedPackages());

							System.out.println(findCfBondCrgDTLData.getInBondedPackages());

							System.out.println(item.getInBondedPackages());

							if (findCfBondCrgHDRDTLData != null) {
								findCfBondCrgHDRDTLData.setInBondedPackages(Optional
										.ofNullable(findCfBondCrgHDRDTLData.getInBondedPackages())
										.orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInBondedPackages())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getInBondedPackages()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRDTLData.setInbondCargoDuty(Optional
										.ofNullable(findCfBondCrgHDRDTLData.getInbondCargoDuty())
										.orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondCargoDuty())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getInbondCargoDuty()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRDTLData.setInbondCifValue(Optional
										.ofNullable(findCfBondCrgHDRDTLData.getInbondCifValue()).orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondCifValue())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getInbondCifValue()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRDTLData.setInbondInsuranceValue(Optional
										.ofNullable(findCfBondCrgHDRDTLData.getInbondInsuranceValue())
										.orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondInsuranceValue())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getInbondInsuranceValue())
												.orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRDTLData.setShortagePackages(Optional
										.ofNullable(findCfBondCrgHDRDTLData.getShortagePackages())
										.orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getShortagePackages())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getShortagePackages()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRDTLData.setBreakage(Optional
										.ofNullable(findCfBondCrgHDRDTLData.getBreakage()).orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getBreakage())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getBreakage()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRDTLData.setDamagedQty(Optional
										.ofNullable(findCfBondCrgHDRDTLData.getDamagedQty()).orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getDamagedQty())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getDamagedQty()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRDTLData.setAreaOccupied(Optional
										.ofNullable(findCfBondCrgHDRDTLData.getAreaOccupied()).orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getAreaOccupied())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getAreaOccupied()).orElse(BigDecimal.ZERO)));
							}

							findCfBondCrgHDRData = cfinbondCrgHdrRepo.findCfBondCrgHDRData(companyId, branchId,
									findCfBondCrgData.getNocTransId(), findCfBondCrgData.getInBondingHdrId(),
									findCfBondCrgData.getNocNo());

							System.out.println("findCfBondCrgHDRData___________" + findCfBondCrgHDRData);
							if (findCfBondCrgHDRData != null)

							{
//								findCfBondCrgHDRData.setInBondedPackages( findCfBondCrgHDRData.getInBondedPackages().subtract(findCfBondCrgDTLData.getInBondedPackages()).add(item.getInBondedPackages()) );
//								findCfBondCrgHDRData.setCargoDuty(findCfBondCrgHDRData.getCargoDuty().subtract(findCfBondCrgDTLData.getInbondCargoDuty()).add(item.getInbondCargoDuty()) );
//								findCfBondCrgHDRData.setCifValue(findCfBondCrgHDRData.getCifValue().subtract(findCfBondCrgDTLData.getInbondCifValue()).add(item.getInbondCifValue()) );
//								
//								findCfBondCrgHDRData.setInbondInsuranceValue(findCfBondCrgHDRData.getInbondInsuranceValue().subtract(findCfBondCrgDTLData.getInbondInsuranceValue()).add(item.getInbondInsuranceValue()) );
//								
//								findCfBondCrgHDRData.setShortagePackages(findCfBondCrgHDRData.getShortagePackages().subtract(findCfBondCrgDTLData.getShortagePackages()).add(item.getShortagePackages()) );
//								
//								findCfBondCrgHDRData.setBreakage(findCfBondCrgHDRData.getBreakage().subtract(findCfBondCrgDTLData.getBreakage()).add(item.getBreakage()!=null ? item.getBreakage() : BigDecimal.ZERO) );
//								
//								findCfBondCrgHDRData.setDamagedQty(findCfBondCrgHDRData.getDamagedQty().subtract(findCfBondCrgDTLData.getDamagedQty()).add(item.getDamagedQty()!=null ? item.getDamagedQty() :BigDecimal.ZERO));

								findCfBondCrgHDRData.setInBondedPackages(Optional
										.ofNullable(findCfBondCrgHDRData.getInBondedPackages()).orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInBondedPackages())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getInBondedPackages()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRData.setCargoDuty(Optional
										.ofNullable(findCfBondCrgHDRData.getCargoDuty()).orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondCargoDuty())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getInbondCargoDuty()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRData.setCifValue(Optional.ofNullable(findCfBondCrgHDRData.getCifValue())
										.orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondCifValue())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getInbondCifValue()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRData.setInbondInsuranceValue(Optional
										.ofNullable(findCfBondCrgHDRData.getInbondInsuranceValue())
										.orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getInbondInsuranceValue())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getInbondInsuranceValue())
												.orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRData.setShortagePackages(Optional
										.ofNullable(findCfBondCrgHDRData.getShortagePackages()).orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getShortagePackages())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getShortagePackages()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRData.setBreakage(
										Optional.ofNullable(findCfBondCrgHDRData.getBreakage()).orElse(BigDecimal.ZERO)
												.subtract(Optional.ofNullable(findCfBondCrgDTLData.getBreakage())
														.orElse(BigDecimal.ZERO))
												.add(Optional.ofNullable(item.getBreakage()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRData.setDamagedQty(Optional
										.ofNullable(findCfBondCrgHDRData.getDamagedQty()).orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getDamagedQty())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getDamagedQty()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRData.setAreaOccupied(Optional
										.ofNullable(findCfBondCrgHDRData.getAreaOccupied()).orElse(BigDecimal.ZERO)
										.subtract(Optional.ofNullable(findCfBondCrgDTLData.getAreaOccupied())
												.orElse(BigDecimal.ZERO))
										.add(Optional.ofNullable(item.getAreaOccupied()).orElse(BigDecimal.ZERO)));

								findCfBondCrgHDRData.setEditedBy(user);
								findCfBondCrgHDRData.setStatus("A");
								findCfBondCrgHDRData.setEditedDate(new Date());

							}

							
							findCfBondCrgData.setInBond20Ft(cfinbondcrg.getInBond20Ft());
							findCfBondCrgData.setInBond40Ft(cfinbondcrg.getInBond40Ft());
							findCfBondCrgData.setOtlNo(cfinbondcrg.getOtlNo());
							findCfBondCrgData.setBondYard(cfinbondcrg.getBondYard());
							findCfBondCrgData.setSection49(cfinbondcrg.getSection49());
							findCfBondCrgData.setInbondInsuranceValue(totalInsurance);
							findCfBondCrgData.setInbondGrossWt(totalWeight);
							findCfBondCrgData.setSection64(cfinbondcrg.getSection64());
							findCfBondCrgData.setSection60(cfinbondcrg.getSection60());
							findCfBondCrgData.setSourcePort(cfinbondcrg.getSourcePort());
							
							findCfBondCrgData.setExtenstionDate1(cfinbondcrg.getExtenstionDate1()!=null ? cfinbondcrg.getExtenstionDate1() :null);	
							findCfBondCrgData.setExtenstionDate2(cfinbondcrg.getExtenstionDate2()!=null ? cfinbondcrg.getExtenstionDate2() :null);		
							findCfBondCrgData.setExtenstionDate3(cfinbondcrg.getExtenstionDate3()!=null ? cfinbondcrg.getExtenstionDate3() :null);		
							
//							BigDecimal totalInbondedd = BigDecimal.ZERO;
//							BigDecimal totalCif = BigDecimal.ZERO;
//							BigDecimal totalCargo = BigDecimal.ZERO;
//							BigDecimal totalInsurance = BigDecimal.ZERO;
//							BigDecimal totalWeight = BigDecimal.ZERO;
//							BigDecimal totalArea = BigDecimal.ZERO;
							
							
							findCfBondCrgData.setInBondedPackages(totalInbondedd);
							findCfBondCrgData.setAreaOccupied(totalArea);

//							findCfBondCrgData.setInbondGrossWt(totalWeight);
//							findCfBondCrgData.setInbondInsuranceValue(totalInsurance);
//							findCfBondCrgData.setInBondedPackages(cfinbondcrg.getInBondedPackages());
//							findCfBondCrgData.setAreaOccupied(cfinbondcrg.getAreaOccupied());
							findCfBondCrgData.setShortagePackages(cfinbondcrg.getShortagePackages());
							findCfBondCrgData.setBreakage(cfinbondcrg.getBreakage());
							findCfBondCrgData.setDamagedQty(cfinbondcrg.getDamagedQty());

							findCfBondCrgData.setEditedBy(user);
							findCfBondCrgData.setStatus("N");
							findCfBondCrgData.setEditedDate(new Date());

							findCfBondCrgData.setCifValue(totalCif);
							findCfBondCrgData.setCargoDuty(totalCargo);
							
							String yardLocationId = item.getEditedBy();
							String[] parts = yardLocationId.split("-");

							if (parts.length == 3) {
								String yardLocation = parts[0]; // EYARD01
								String blockId = parts[1]; // A
								String cellNoRow = parts[2]; // 2

								System.out.println("yardLocation_____________________" + yardLocation);
								System.out.println("blockId__________________________" + blockId);
								System.out.println("cellNoRow________________________" + cellNoRow);
								// Set extracted values to cfGateIn object

								findCfBondCrgDTLData.setYardLocationId(yardLocation);
								findCfBondCrgDTLData.setBlockId(blockId);
								findCfBondCrgDTLData.setCellNoRow(cellNoRow);
							}

							findCfBondCrgDTLData.setInBondedPackages(item.getInBondedPackages());
							findCfBondCrgDTLData.setInbondCargoDuty(item.getInbondCargoDuty());
							findCfBondCrgDTLData.setInbondCifValue(item.getInbondCifValue());
							findCfBondCrgDTLData.setInbondInsuranceValue(item.getInbondInsuranceValue());
							findCfBondCrgDTLData.setShortagePackages(item.getShortagePackages());
							findCfBondCrgDTLData.setBreakage(item.getBreakage());
							findCfBondCrgDTLData.setDamagedQty(item.getDamagedQty());
							findCfBondCrgDTLData.setCellArea(item.getCellArea());
							findCfBondCrgDTLData.setYardPackages(item.getYardPackages());
							findCfBondCrgDTLData.setCellAreaAllocated(item.getCellAreaAllocated());
							findCfBondCrgDTLData.setInsuranceValue(item.getInsuranceValue());

							CfBondNocDtl getDataOfDtlId = cfBondNocDtlRepository.getDataOfDtlId(companyId, branchId,
									item.getCfBondDtlId(), item.getNocTransId(), item.getNocNo());

							if (getDataOfDtlId != null) {

								int updateNoCDtl = cfBondNocDtlRepository.updateNocDtlFromInBonding(
										item.getInBondedPackages(), item.getInbondGrossWt(),
										item.getInbondInsuranceValue(), item.getInbondCifValue(),
										item.getInbondCargoDuty(), item.getShortagePackages(), item.getDamagedQty(),
										item.getBreakage(), cfinbondcrg.getBondingNo(), companyId, branchId,
										item.getNocTransId(), item.getNocNo(), item.getCfBondDtlId());

								System.out.println("updateNoCDtl__________Row___In__EXits_NOC______________________Dtl"
										+ updateNoCDtl);
							}

//							cfinbondcrgDtlRepo.save(findCfBondCrgDTLData);
//							
//							cfinbondCrgHdrDtlRepo.save(findCfBondCrgHDRDTLData);
						}

						else {
							System.out.println("item___________________________________________" + item);

							System.out.println("yardLocation_____________________" + item.getEditedBy());

							CfinbondcrgDtl crgDetails = new CfinbondcrgDtl();

							crgDetails.setCreatedBy(user);
							crgDetails.setCreatedDate(new Date());
							crgDetails.setApprovedBy(user);
							crgDetails.setApprovedDate(new Date());
							crgDetails.setBoeNo(cfinbondcrg.getBoeNo());
							crgDetails.setBondingDate(cfinbondcrg.getBondingDate());
							crgDetails.setBondingNo(cfinbondcrg.getBondingNo());
							crgDetails.setAreaOccupied(item.getAreaOccupied());
							crgDetails.setBranchId(branchId);
							crgDetails.setCompanyId(companyId);
							crgDetails.setBreakage(item.getBreakage());
							crgDetails.setCargoDuty(item.getCargoDuty());
							crgDetails.setCfBondDtlId(item.getCfBondDtlId());
							crgDetails.setCifValue(item.getCifValue());
							crgDetails.setComments(cfinbondcrg.getComments());
							crgDetails.setCommodityDescription(item.getCommodityDescription());
							crgDetails.setDamagedQty(item.getDamagedQty());
							crgDetails.setFinYear("2242");
							crgDetails.setGrossWeight(item.getGrossWeight());
							crgDetails.setInbondCargoDuty(item.getInbondCargoDuty());
//							crgDetails.setInbondCifValue(item.getInBondedPackages());
							crgDetails.setInbondCifValue(item.getInbondCifValue());
							crgDetails.setInBondedPackages(item.getInBondedPackages());
							crgDetails.setInbondGrossWt(item.getInbondGrossWt());
							crgDetails.setInBondingDate(cfinbondcrg.getInBondingDate());
							crgDetails.setCellArea(item.getCellArea());
							crgDetails.setInBondingDtlId(nectInBondingDTLId);
							crgDetails.setInsuranceValue(item.getInsuranceValue());
							crgDetails.setInBondingId(cfinbondcrg.getInBondingId());

							crgDetails.setInbondInsuranceValue(item.getInbondInsuranceValue());
							crgDetails.setNocDate(cfinbondcrg.getNocDate());
							crgDetails.setNocNo(cfinbondcrg.getNocNo());
							crgDetails.setNocTransId(cfinbondcrg.getNocTransId());
							crgDetails.setNocTransDate(cfinbondcrg.getNocTransDate());
							crgDetails.setNocPackages(item.getNocPackages());
							crgDetails.setTypeOfPackage(item.getTypeOfPackage());
							crgDetails.setShortagePackages(item.getShortagePackages());
							crgDetails.setBreakage(item.getBreakage());
							crgDetails.setDamagedQty(item.getDamagedQty());
							crgDetails.setStatus("A");

							crgDetails.setCellAreaAllocated(item.getCellAreaAllocated());
							crgDetails.setYardPackages(item.getYardPackages());

							String yardLocationId = item.getEditedBy();
							String[] parts = yardLocationId.split("-");

							if (parts.length == 3) {
								String yardLocation = parts[0]; // EYARD01
								String blockId = parts[1]; // A
								String cellNoRow = parts[2]; // 2

								System.out.println("yardLocation_____________________" + yardLocation);
								System.out.println("blockId__________________________" + blockId);
								System.out.println("cellNoRow________________________" + cellNoRow);
								// Set extracted values to cfGateIn object

								crgDetails.setYardLocationId(yardLocation);
								crgDetails.setBlockId(blockId);
								crgDetails.setCellNoRow(cellNoRow);
							}
//					        cfinbondcrgDtlRepo.save(crgDetails);

							CfinbondcrgDtl savedDtl = cfinbondcrgDtlRepo.save(crgDetails);

							if (savedDtl != null) {
								CfInBondGrid cf = new CfInBondGrid();
								Integer maxSrNo = cfInBondGridRepository.getMaxSrNo(companyId, branchId,
										savedDtl.getInBondingId(), savedDtl.getCfBondDtlId());

								cf.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
								cf.setCompanyId(companyId);
								cf.setBranchId(branchId);
								cf.setCreatedBy(user);
								cf.setCreatedDate(new Date());
								cf.setApprovedBy(user);
								cf.setApprovedDate(new Date());
								cf.setInBondingId(savedDtl.getInBondingId());
								cf.setCfBondDtlId(savedDtl.getCfBondDtlId());
								cf.setYardLocation(savedDtl.getYardLocationId());
								cf.setYardBlock(savedDtl.getBlockId());
								cf.setBlockCellNo(savedDtl.getCellNoRow());

//								 String approvedByStr = item.getApprovedBy(); // Get the string value
//								    BigDecimal approvedByDecimal = new BigDecimal(approvedByStr); // Convert to BigDecimal
//								    
//								cf.setCellArea(approvedByDecimal);
//								cf.setCellAreaAllocated(savedDtl.getCellAreaAllocated());
//								
//								 String getCreatedByStr = item.getCreatedBy(); // Get the string value
//								    BigDecimal getCreatedByByDecimal = new BigDecimal(getCreatedByStr); // Convert to BigDecimal
//								    
//								cf.setCellAreaUsed(getCreatedByByDecimal);

								cf.setCellArea(savedDtl.getCellArea());
								cf.setCellAreaUsed(item.getInsuranceValue());
								cf.setCellAreaAllocated(savedDtl.getCellAreaAllocated());

								cf.setNocTransId(savedDtl.getNocTransId());
								cf.setFinYear("2025");

								cf.setInBondPackages(savedDtl.getYardPackages());
								cf.setStatus("A");

								saved = cfInBondGridRepository.save(cf);

								if (saved != null) {
									YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
											saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());

									if (existingCell != null) {
										existingCell.setCellAreaUsed(
												existingCell.getCellAreaUsed() != null ? existingCell.getCellAreaUsed()
														: BigDecimal.ZERO.add(saved.getCellAreaAllocated() != null
																? saved.getCellAreaAllocated()
																: BigDecimal.ZERO));
										yardBlockCellRepository.save(existingCell);
									}
								}
							}

							int updateNoCDtl = cfBondNocDtlRepository.updateNocDtlFromInBonding(
									item.getInBondedPackages(), item.getInbondGrossWt(), item.getInbondInsuranceValue(),
									item.getInbondCifValue(), item.getInbondCargoDuty(), item.getShortagePackages(),
									item.getDamagedQty(), item.getBreakage(), cfinbondcrg.getBondingNo(), companyId,
									branchId, item.getNocTransId(), item.getNocNo(), item.getCfBondDtlId());

							System.out.println(
									"updateNoCDtl__________Row___In__NOC______________________Dtl" + updateNoCDtl);

							CfinbondcrgHDRDtl cfinbondHDRDtl = new CfinbondcrgHDRDtl();

							cfinbondHDRDtl.setCreatedBy(user);
							cfinbondHDRDtl.setCreatedDate(new Date());
							cfinbondHDRDtl.setApprovedBy(user);
							cfinbondHDRDtl.setApprovedDate(new Date());
							cfinbondHDRDtl.setBoeNo(cfinbondcrg.getBoeNo());
							cfinbondHDRDtl.setBondingDate(cfinbondcrg.getInBondingDate());
							cfinbondHDRDtl.setBondingNo(cfinbondcrg.getInBondingId());
							cfinbondHDRDtl.setAreaOccupied(item.getAreaOccupied());
							cfinbondHDRDtl.setBranchId(branchId);
							cfinbondHDRDtl.setCompanyId(companyId);
							cfinbondHDRDtl.setBreakage(item.getBreakage());
							cfinbondHDRDtl.setCargoDuty(item.getCargoDuty());
							cfinbondHDRDtl.setCfBondDtlId(item.getCfBondDtlId());
							cfinbondHDRDtl.setCifValue(item.getCifValue());
							cfinbondHDRDtl.setComments(cfinbondcrg.getComments());
							cfinbondHDRDtl.setCommodityDescription(item.getCommodityDescription());
							cfinbondHDRDtl.setDamagedQty(item.getDamagedQty());
							cfinbondHDRDtl.setFinYear("2242");
							cfinbondHDRDtl.setGrossWeight(item.getGrossWeight());
							cfinbondHDRDtl.setInbondCargoDuty(item.getInbondCargoDuty());
							cfinbondHDRDtl.setInbondCifValue(item.getInBondedPackages());
							cfinbondHDRDtl.setInBondedPackages(item.getInBondedPackages());
							cfinbondHDRDtl.setInbondGrossWt(item.getInbondGrossWt());
							cfinbondHDRDtl.setInBondingDate(cfinbondcrg.getInBondingDate());

							cfinbondHDRDtl.setInBondingDtlId(nectInBondingDTLId);

							cfinbondHDRDtl.setInBondingId(cfinbondcrg.getInBondingHdrId());

							cfinbondHDRDtl.setInbondInsuranceValue(item.getInbondInsuranceValue());
							cfinbondHDRDtl.setNocDate(cfinbondcrg.getNocDate());
							cfinbondHDRDtl.setNocNo(cfinbondcrg.getNocNo());
							cfinbondHDRDtl.setNocTransId(cfinbondcrg.getNocTransId());
							cfinbondHDRDtl.setNocTransDate(cfinbondcrg.getNocTransDate());
							cfinbondHDRDtl.setNocPackages(item.getNocPackages());
							cfinbondHDRDtl.setTypeOfPackage(item.getTypeOfPackage());
							cfinbondHDRDtl.setShortagePackages(item.getShortagePackages());
							cfinbondHDRDtl.setBreakage(item.getBreakage());
							cfinbondHDRDtl.setDamagedQty(item.getDamagedQty());
							cfinbondHDRDtl.setStatus("A");

							cfinbondCrgHdrDtlRepo.save(cfinbondHDRDtl);

						}
						processNextIdRepository.updateAuditTrail(companyId, branchId, "P03207", nectInBondingDTLId,
								"2244");
					}

					CfinbondcrgDtl savedlist = cfinbondcrgDtlRepo.save(findCfBondCrgDTLData);

					if (savedlist != null) 
					{
						CfInBondGrid toEditData = cfInBondGridRepository.toEditData(companyId, branchId,
								savedlist.getInBondingId(), savedlist.getCfBondDtlId(), savedlist.getYardLocationId(),
								savedlist.getBlockId(), savedlist.getCellNoRow());

						if (toEditData != null) {
							toEditData.setInBondPackages(toEditData.getInBondPackages().add(savedlist.getYardPackages())
									.subtract(findCfBondCrgDTLData.getYardPackages()));

						
							CfInBondGrid savedGrid = cfInBondGridRepository.save(toEditData);

							if (savedGrid != null) {
								YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
										savedGrid.getYardLocation(), savedGrid.getYardBlock(),
										savedGrid.getBlockCellNo());

								if (existingCell != null) {
									existingCell.setCellAreaUsed(
											existingCell.getCellAreaUsed() != null ? existingCell.getCellAreaUsed()
													: BigDecimal.ZERO
															.add(savedGrid.getCellAreaAllocated() != null
																	? savedGrid.getCellAreaAllocated()
																	: BigDecimal.ZERO)
															.subtract(toEditData.getCellAreaAllocated()));
									yardBlockCellRepository.save(existingCell);
								}
							}
						}

					}

					cfinbondCrgHdrDtlRepo.save(findCfBondCrgHDRDTLData);

					cfinbondcrgRepo.save(findCfBondCrgData);

					cfinbondCrgHdrRepo.save(findCfBondCrgHDRData);

					
					Cfinbondcrg findCfBondCrgData1 = cfinbondcrgRepo.findCfBondCrgData(companyId, branchId,
					cfinbondcrg.getNocTransId(), cfinbondcrg.getInBondingId(), cfinbondcrg.getNocNo());
			
			
			if (findCfBondCrgData1!=null)
			{
				Cfbondnoc findCfBondNocForUpdationg = cfbondnocRepository.findCfBondNocForUpdationg(companyId,
						branchId, cfinbondcrg.getNocTransId(), cfinbondcrg.getNocNo());
				if (findCfBondNocForUpdationg != null) 
				{
					int updateInCfBondNoc = cfbondnocRepository.updateCfBondNocAfterInBonding(
							findCfBondNocForUpdationg.getInBondedPackages().add(totalInbondedd).subtract(findCfBondCrgData1.getInBondedPackages()),
							findCfBondNocForUpdationg.getInbondGrossWt().add(totalWeight).subtract(findCfBondCrgData1.getInbondGrossWt()),
							findCfBondNocForUpdationg.getInbondInsuranceValue().add(totalInsurance).subtract(findCfBondCrgData1.getInbondInsuranceValue()),
							findCfBondNocForUpdationg.getInbondCifValue().add(totalCif).subtract(findCfBondCrgData1.getCifValue()),
							findCfBondNocForUpdationg.getInbondCargoDuty().add(totalCargo).subtract(findCfBondCrgData1.getCargoDuty()),
							cfinbondcrg.getBondingNo(), cfinbondcrg.getBondingDate(),
							cfinbondcrg.getBondValidityDate(), companyId, branchId, cfinbondcrg.getNocTransId(),
							cfinbondcrg.getNocNo());

					System.out.println("Update noc after inbond in exist" + updateInCfBondNoc);
				}
			}
//					int updateInCfBondNoc = cfbondnocRepository.updateCfBondNocAfterInBonding(totalInbondedd,
//							totalWeight, totalInsurance, totalCif, totalCargo, cfinbondcrg.getBondingNo(),
//							cfinbondcrg.getBondingDate(), cfinbondcrg.getBondValidityDate(), companyId, branchId,
//							cfinbondcrg.getNocTransId(), cfinbondcrg.getNocNo());
//
//					System.out.println("Update noc after inbond " + updateInCfBondNoc);
				}

			}

		}

		System.out.println("cfinbondcrg_______________________" + cfinbondcrg);

		System.out.println("cfinbondcrgDtl_______________________" + cfinbondcrgDtlList);

		return new ResponseEntity<>(cfinbondcrg, HttpStatus.OK);
	}

	
	
	
	
	
	
	
	@Transactional
	public ResponseEntity<?> approveDataOfInCFbondGrid(String companyId, String branchId, String flag, String user,
	        Map<String, Object> requestBody) {

	    ObjectMapper object = new ObjectMapper();
	    object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	    Cfinbondcrg cfinbondcrg = object.convertValue(requestBody.get("inBond"), Cfinbondcrg.class);
	    
	    System.out.println("cfinbondcrg________________________"+cfinbondcrg.getInBondingId());
	    
	    if (cfinbondcrg.getInBondingId()==null || cfinbondcrg.getInBondingId().isEmpty() || cfinbondcrg.getInBondingId().isBlank())
	    {
	    	return new ResponseEntity<>("Please First Save Data", HttpStatus.BAD_REQUEST);
	    }
	    
	    if (cfinbondcrg != null && cfinbondcrg.getInBondingId()!=null || !cfinbondcrg.getInBondingId().isEmpty() || !cfinbondcrg.getInBondingId().isBlank()) 	    	
	    {

	        BigDecimal sumOfInbondFromDtl = cfinbondcrgDtlRepo.getSumOfInBondPackages(companyId, branchId,
	                cfinbondcrg.getInBondingId(), cfinbondcrg.getNocTransId());


	        BigDecimal sumOfInbondFormGrid = cfInBondGridRepository.getSumOfInBondPackages(companyId, branchId,
	                cfinbondcrg.getInBondingId(), cfinbondcrg.getNocTransId());

	        System.out.println("sumOfInbondFromDtl: " + sumOfInbondFromDtl + " ______________ " + sumOfInbondFormGrid);
	        
	        if (sumOfInbondFromDtl == null || sumOfInbondFormGrid == null) {
	            return new ResponseEntity<>("One of the sum values is null. Please check the data.", HttpStatus.BAD_REQUEST);
	        }

//	        if (sumOfInbondFormGrid.compareTo(sumOfInbondFromDtl) < 0) {
	        if (sumOfInbondFormGrid.compareTo(sumOfInbondFromDtl) != 0) {
	        	
	            return new ResponseEntity<>("InbondPackages do not match in yard, please add packages in grid.", HttpStatus.BAD_REQUEST);
	        }
	        else
	        {
	        	 int updateAfterApprov =cfinbondcrgRepo.updateAfterApprove("A", companyId, branchId, cfinbondcrg.getInBondingId(), cfinbondcrg.getNocNo(), cfinbondcrg.getNocTransId());
	        	 System.out.println("updateAfterApprov row count "+updateAfterApprov);
	        }
	    }
	   
	    return new ResponseEntity<>("", HttpStatus.OK);
	}

	 public BigDecimal getSumOfInBondPackagesForCommodity(String companyId, String branchId, String inBondingId, String cfBondDtlId, String nocTransId) {
	        return cfinbondcrgDtlRepo.getSumOfInBondPackagesForCommodity(companyId, branchId, inBondingId, cfBondDtlId, nocTransId);
	    }

	public List<Cfinbondcrg> findAllCfinbondCrgIn(String companyId, String branchId, String search) {
		return cfinbondcrgRepo.findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(companyId, branchId, search);
	}

	public Cfinbondcrg getCfInbondCrgDataByidOrSearch(String companyId, String branchId, String transId,
			String inBondingId, String nocNo) {
		return cfinbondcrgRepo.findCfBondCrgData(companyId, branchId, transId, inBondingId, nocNo);
	}

	public List<Cfinbondcrg> findAllCfinbondCrgHDR(String companyId, String branchId, String search) {
		return cfinbondCrgHdrRepo.findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(companyId, branchId, search);
	}

//	public Cfinbondcrg getFindAllCfinbondCrgHDR(String companyId, String branchId, String transId,String inBondingId, String nocNo) {
//		return cfinbondCrgHdrRepo.findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(companyId, branchId, transId,inBondingId, nocNo);
//	}

	public List<Cfinbondcrg> getFindAllCfinbondCrgHDR(String companyId, String branchId, String inBondingId) {
		return cfinbondCrgHdrRepo.findCfinbondcrgByCompanyIdAndBranchIdForInbond(companyId, branchId, inBondingId);
	}

	public Cfinbondcrg getDataOfBoeNoForEntryInExbond(String companyId, String branchId, String nocTransId,
			String nocNo, String boeNo) {
		return cfinbondCrgHdrRepo.getDataOfBoeNoForEntryInExbond(companyId, branchId, nocTransId, nocNo, boeNo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ResponseEntity<String> getPrintOfCutomesBondInBondCargo( String companyId,
			String branchId,  String username,
			 String type,String companyname,
			 String branchname,  String inBondingId) throws DocumentException {
		
		Context context = new Context();

		Cfinbondcrg dataForPrint = null;

		List<Cfinbondcrg> result = cfinbondcrgRepo.getDataForCustomesBondInBondPrint(companyId,
				branchId, inBondingId);
		if (!result.isEmpty()) {
			dataForPrint = result.get(0);
			// Process the firstResult
		}
		
			System.out.println("gatePassdata____________________________________________"+result);
			
			
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
		context.setVariable("inBondingId", dataForPrint.getInBondingId());
		context.setVariable("inBondingIdDate", dataForPrint.getInBondingDate());
		context.setVariable("boeNo", dataForPrint.getBoeNo());
//		context.setVariable("boeNo", boeNosBuilder.toString());
		context.setVariable("boeDate", dataForPrint.getBoeDate());
		context.setVariable("igmNo", dataForPrint.getIgmNo());
		
		context.setVariable("nocNo", dataForPrint.getNocNo());
		context.setVariable("nocDate", dataForPrint.getNocDate());

		context.setVariable("igmLineNo", dataForPrint.getIgmLineNo());
		context.setVariable("bondingNo", dataForPrint.getBondingNo());
		
		context.setVariable("bondingDate", dataForPrint.getBondingDate());

		context.setVariable("inBondPackages", dataForPrint.getInBondedPackages());
		context.setVariable("inBondWt", dataForPrint.getInbondGrossWt());
		
	
		context.setVariable("consignee", dataForPrint.getImporterName());
		
		context.setVariable("section", dataForPrint.getSection49().equals("Y") ? "YES" :"NO");
		
		context.setVariable("area", dataForPrint.getAreaAllocated());
		context.setVariable("week", dataForPrint.getNocWeek());
		
		
		context.setVariable("ftinbond", dataForPrint.getInBond20Ft());
		context.setVariable("ft", dataForPrint.getInBond40Ft());
		context.setVariable("spaceType", dataForPrint.getSpaceType());
		
		
		context.setVariable("exBondGrWeight", dataForPrint.getExBondedGw());
		context.setVariable("consignee", dataForPrint.getImporterName());
		
		context.setVariable("typeOfPackages", dataForPrint.getTypeOfPackage());
		context.setVariable("cha", dataForPrint.getCha());
		context.setVariable("address", dataForPrint.getImporterAddress1() + " " + dataForPrint.getImporterAddress2()
				+ " " + dataForPrint.getImporterAddress3());
		
		context.setVariable("cargoDiscrpition", dataForPrint.getCommodityDescription());

		context.setVariable("cargoDesc", dataForPrint.getCommodityDescription());
	
		context.setVariable("result", result);
		context.setVariable("c1", c1);
		context.setVariable("b1", b1);
		context.setVariable("u1", u1);
		context.setVariable("companyAdd", companyAdd);
		context.setVariable("branchAdd", branchAdd);
		context.setVariable("bondCode", bondCode);
		context.setVariable("city", city);

		String htmlContent = templateEngine.process("CustomeBondInbond", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
}
