package com.cwms.service;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.util.SystemPropertiesUtil;

import com.cwms.entities.Branch;
import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.CfExBondGrid;
import com.cwms.entities.CfInBondGrid;
import com.cwms.entities.Cfbondinsbal;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.CfexBondCrgDtl;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.entities.CfinbondcrgHDR;
import com.cwms.entities.CfinbondcrgHDRDtl;
import com.cwms.entities.Company;
import com.cwms.entities.Party;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfBondNocDtlRepository;
import com.cwms.repository.CfExBondCrgDtlRepository;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfExBondGridRepository;
import com.cwms.repository.CfInBondGridRepository;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CfExBondCrgService {

	@Autowired
	private CfExBondCrgRepository cfExBondCrgRepository;

	@Autowired
	private CfbondnocRepository cfbondnocRepository;

	@Autowired
	private CfinbondcrgDtlRepo cfexbondcrgDtlRepo;

	@Autowired
	private CfExBondCrgDtlRepository cfExBondCrgDtlRepository;

	@Autowired
	private ProcessNextIdRepository processNextIdRepository;

	@Autowired
	private CfinbondcrgRepo cfinbondcrgRepo;

	@Autowired
	private CfinbondCrgHdrRepo cfinbondCrgHdrRepo;

	@Autowired
	private CfBondNocDtlRepository cfbondnocDtlRepository;

	@Autowired
	private CfinbondCrgHdrDtlRepo cfinbondCrgHdrDtlRepo;
	
	
	@Autowired
	private CfExBondGridRepository cfExBondGridRepository;

	@Autowired
	public YardBlockCellRepository yardBlockCellRepository;
	
	@Autowired
	public CfInBondGridRepository cfInBondGridRepository;
	
	
	
	
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;

	public List<CfinbondcrgDtl> findAllCfBondNocDtl(String companyId, String branchId, String nocTransId, String nocNo,
			String inBondingId, String boeNo) {
		return cfexbondcrgDtlRepo.getCfBondInBondDTLData(companyId, branchId, nocTransId, nocNo, inBondingId, boeNo);
	}

	public List<Party> findAllParty(String companyId, String branchId, String partyName) {
		return cfbondnocRepository.getAllPartyAsFoeworder(companyId, branchId, partyName);
	}

	public Object[] findForworderData(String companyId, String branchId, String partyId) {

		System.out.println("cfbondnocRepository.getForworderData(companyId, branchId, partyId)"
				+ cfbondnocRepository.getForworderData(companyId, branchId, partyId));
		return cfbondnocRepository.getForworderData(companyId, branchId, partyId);
	}

	private int safeParseInt(String value) {
		return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : 0;
	}

	public ResponseEntity<?> saveDataOfCfexbondCrgAndExbondCrgDtl(String companyId, String branchId, String user,
			String flag, Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		CfExBondCrg cfexbondcrg = object.convertValue(requestBody.get("exBond"), CfExBondCrg.class);

		System.out.println("cfexbondcrg_________________________________________________________"+cfexbondcrg.getSpaceType() +""+cfexbondcrg.getGateInType());
		
		Object nocDtlObj = requestBody.get("exbondDtl");
		List<CfinbondcrgDtl> cfexbondcrgDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			// If nocDtl is a list, deserialize it directly
			cfexbondcrgDtlList = object.convertValue(nocDtlObj, new TypeReference<List<CfinbondcrgDtl>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			// If nocDtl is a map, convert each map entry to CfinbondcrgDtl
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				CfinbondcrgDtl cfexbondcrgDtl = object.convertValue(entry.getValue(), CfinbondcrgDtl.class);
				cfexbondcrgDtlList.add(cfexbondcrgDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		System.out.println("flag________________" + flag);

		
		if (cfexbondcrg != null) {
			if ("add".equals(flag)) {
				String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P03208", "2245");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String nectExBondingId = String.format("EXBL%06d", nextNumericNextID1);

				cfexbondcrg.setCompanyId(companyId);
				cfexbondcrg.setSpaceType(cfexbondcrg.getSpaceType());
				cfexbondcrg.setGateInType(cfexbondcrg.getGateInType());
				cfexbondcrg.setBranchId(branchId);
				cfexbondcrg.setCreatedBy(user);
				cfexbondcrg.setCreatedDate(new Date());
				cfexbondcrg.setStatus("N");
				cfexbondcrg.setApprovedBy(user);
				cfexbondcrg.setApprovedDate(new Date());
				cfexbondcrg.setExBondingId(nectExBondingId);
				cfexbondcrg.setFinYear("2025");

				cfexbondcrg.setBalancedPackages(cfexbondcrg.getRemainingPackages()
						.subtract(Optional.ofNullable(cfexbondcrg.getExBondedPackages()).orElse(BigDecimal.ZERO)));

				cfexbondcrg.setBalanceCif(cfexbondcrg.getRemainingCif()
						.subtract(Optional.ofNullable(cfexbondcrg.getExBondedCif()).orElse(BigDecimal.ZERO)));

				cfexbondcrg.setBalanceCargoDuty(cfexbondcrg.getRemainingCargoDuty()
						.subtract(Optional.ofNullable(cfexbondcrg.getExBondedCargoDuty()).orElse(BigDecimal.ZERO)));

				cfexbondcrg.setBalanceInsurance(cfexbondcrg.getRemainingInsurance()
						.subtract(Optional.ofNullable(cfexbondcrg.getExBondedInsurance()).orElse(BigDecimal.ZERO)));

				cfexbondcrg.setBalanceGw(cfexbondcrg.getRemainingGw()
						.subtract(Optional.ofNullable(cfexbondcrg.getExBondedGw()).orElse(BigDecimal.ZERO)));

				cfexbondcrg.setAreaBalanced(cfexbondcrg.getAreaRemaining()
						.subtract(Optional.ofNullable(cfexbondcrg.getAreaBalanced()).orElse(BigDecimal.ZERO)));

				cfexbondcrg.setBalancedQty(cfexbondcrg.getRemainingPackages()
						.subtract(Optional.ofNullable(cfexbondcrg.getExBondedPackages()).orElse(BigDecimal.ZERO)));

				cfExBondCrgRepository.save(cfexbondcrg);

				int updateCfBalance = cfExBondCrgRepository.updateCfbondinsbalAfterExbond(cfexbondcrg.getAreaReleased(),
						cfexbondcrg.getExBondedCargoDuty(), cfexbondcrg.getExBondedCif(), companyId, branchId);

				System.out.println("updateCfBalance row is " + updateCfBalance);

				int updateCfinbondcrgAfterExbond = cfinbondcrgRepo.updateCfinbondCrgAfterExbond(
						cfexbondcrg.getExBondedPackages(), cfexbondcrg.getExBondedCargoDuty(),
						cfexbondcrg.getExBondedCif(), cfexbondcrg.getExBondedInsurance(), cfexbondcrg.getExBondedGw(),
						cfexbondcrg.getNoOf20Ft(), cfexbondcrg.getNoOf40Ft(), companyId, branchId,
						cfexbondcrg.getInBondingId(), cfexbondcrg.getNocNo(), cfexbondcrg.getNocTransId(),
						cfexbondcrg.getBoeNo());

				System.out.println("Update row count after exbond is" + updateCfinbondcrgAfterExbond);

				Cfinbondcrg existing = cfinbondcrgRepo.findCfinbondHdr(companyId, branchId, cfexbondcrg.getNocTransId(),
						cfexbondcrg.getInBondingId(), cfexbondcrg.getNocNo());

				if (existing != null) {

					System.out.println(existing.getInBondingHdrId());
					System.out.println(cfexbondcrg.getNocNo());

					System.out.println(cfexbondcrg.getBoeNo());
					System.out.println(cfexbondcrg.getNocTransId());

					int updateCfInbondCrdHDR = cfinbondCrgHdrRepo.updateCfInbondHeaderAfterExbond(
							cfexbondcrg.getExBondedPackages(), cfexbondcrg.getExBondedCargoDuty(),
							cfexbondcrg.getExBondedInsurance(), cfexbondcrg.getExBondedCif(),
							cfexbondcrg.getExBondedGw(), companyId, branchId, cfexbondcrg.getNocTransId(),
							cfexbondcrg.getNocNo(), existing.getInBondingHdrId(), cfexbondcrg.getBoeNo());

					System.out.println("Update row count after exbond hdr " + updateCfInbondCrdHDR);
				}

				processNextIdRepository.updateAuditTrail(companyId, branchId, "P03208", nectExBondingId, "2245");

				if (cfexbondcrgDtlList != null) {

					for (CfinbondcrgDtl item : cfexbondcrgDtlList) 
					
					{
						CfexBondCrgDtl exBondDtl = new CfexBondCrgDtl();
						
						exBondDtl.setCreatedBy(user);
						exBondDtl.setCreatedDate(new Date());
						exBondDtl.setApprovedBy(user);
						exBondDtl.setApprovedDate(new Date());
						exBondDtl.setFinYear("2025");
						exBondDtl.setInBondedPackages(item.getInBondedPackages());
						exBondDtl.setInbondCifValue(item.getInbondCifValue());
						exBondDtl.setInbondCargoDuty(item.getInbondCargoDuty());
						exBondDtl.setInbondInsuranceValue(item.getInbondInsuranceValue());
						exBondDtl.setInbondGrossWt(item.getInbondGrossWt());
						exBondDtl.setExBondedPackages(item.getExBondedPackages());
						exBondDtl.setExBondedCIF(item.getExBondedCIF());
						exBondDtl.setExBondedCargoDuty(item.getExBondedCargoDuty());
						exBondDtl.setExBondedInsurance(item.getExBondedInsurance());
						exBondDtl.setExBondedGW(item.getExBondedGW());
						exBondDtl.setNocNo(item.getNocNo());
						exBondDtl.setNocTransId(item.getNocTransId());
						exBondDtl.setNocPackages(item.getNocPackages());
						exBondDtl.setCommodityDescription(item.getCommodityDescription());
						exBondDtl.setBoeNo(item.getBoeNo());
						exBondDtl.setInBondingId(item.getInBondingId());
						exBondDtl.setCompanyId(companyId);
						exBondDtl.setBranchId(branchId);
						exBondDtl.setStatus('N');
						exBondDtl.setTypeOfPackage(item.getTypeOfPackage());
						exBondDtl.setBondingDate(cfexbondcrg.getBondingDate());
						exBondDtl.setBondingNo(cfexbondcrg.getBondingNo());
						exBondDtl.setExBondBeNo(cfexbondcrg.getExBondBeNo());
						exBondDtl.setExBondingId(cfexbondcrg.getExBondingId());
						exBondDtl.setCfBondDtlId(item.getCfBondDtlId());
						exBondDtl.setYardLocationId(item.getYardLocationId());
						exBondDtl.setBlockId(item.getBlockId());
						exBondDtl.setCellNoRow(item.getCellNoRow());
						exBondDtl.setIgmNo(cfexbondcrg.getIgmNo());
						exBondDtl.setIgmLineNo(cfexbondcrg.getIgmLineNo());
						exBondDtl.setExBondyardPackages(item.getExBondyardPackages());
						exBondDtl.setExBondGridArea(item.getExBondGridArea());
						
						
			
						CfexBondCrgDtl saved=	cfExBondCrgDtlRepository.save(exBondDtl);
						
						if (saved!=null)
						{
							
							List<CfInBondGrid> existList =cfInBondGridRepository.getDataForExbondGrid(companyId, branchId, item.getInBondingId(), item.getNocTransId(), item.getCfBondDtlId());
							

							System.out.println("existList________________________"+existList);
							
							if(existList!=null)
							{
								
								for (CfInBondGrid data : existList)
								{
									System.out.println("data________________________"+data);
									CfExBondGrid cf1 = new CfExBondGrid();
									
									
									Integer maxSrNo = cfExBondGridRepository.getMaxSrNo(companyId, branchId, saved.getExBondingId(),
											saved.getCfBondDtlId());
									
									
//									cf1.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
									cf1.setSrNo(data.getSrNo());
									cf1.setCompanyId(companyId);
									cf1.setBranchId(branchId);
									cf1.setCreatedBy(user);
									cf1.setCreatedDate(new Date());
									cf1.setApprovedBy(user);
									cf1.setApprovedDate(new Date());
									cf1.setInBondingId(data.getInBondingId());
									cf1.setExBondingId(saved.getExBondingId());
									cf1.setCfBondDtlId(data.getCfBondDtlId());
									
									cf1.setYardLocation(data.getYardLocation());
									cf1.setYardBlock(data.getYardBlock());
									cf1.setBlockCellNo(data.getBlockCellNo());
//									cf1.setGridReleased(saved.getExBondGridArea());
									cf1.setExCellAreaAllocated(BigDecimal.ZERO);
									cf1.setExBondPackages(BigDecimal.ZERO);
									
									cf1.setInBondPackages(data.getInBondPackages());
									cf1.setCellAreaAllocated(data.getCellAreaAllocated());
								
									cf1.setNocTransId(data.getNocTransId());
									cf1.setFinYear("2025");
									cf1.setStatus("A");

									CfExBondGrid savedGrid =cfExBondGridRepository.save(cf1);

									
									if (savedGrid!=null)
									{
										 CfExBondGrid toEditData =cfExBondGridRepository.toEditData(companyId, branchId, saved.getExBondingId(), saved.getCfBondDtlId(), 
			                                		saved.getYardLocationId(), saved.getBlockId(), saved.getCellNoRow());
											
											if (toEditData!=null)
											{
												int updateGrid =cfExBondGridRepository.updateCfexBondDtlAfterExBondGrid(saved.getExBondyardPackages(),saved.getExBondGridArea(),
														companyId,branchId,saved.getExBondingId(), saved.getCfBondDtlId(), saved.getYardLocationId(), saved.getBlockId(), saved.getCellNoRow());

												System.out.println("updated row in cfexbond grid :"+updateGrid);
											}
									}
								}
								
							}
							
//							CfExBondGrid cf1 = new CfExBondGrid();
//							
//							Integer maxSrNo = cfExBondGridRepository.getMaxSrNo(companyId, branchId, saved.getExBondingId(),
//									saved.getCfBondDtlId());
//
//							cf1.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
//							cf1.setCompanyId(companyId);
//							cf1.setBranchId(branchId);
//							cf1.setCreatedBy(user);
//							cf1.setCreatedDate(new Date());
//							cf1.setApprovedBy(user);
//							cf1.setApprovedDate(new Date());
//							cf1.setInBondingId(saved.getInBondingId());
//							cf1.setExBondingId(saved.getExBondingId());
//							cf1.setCfBondDtlId(saved.getCfBondDtlId());
//							
//							cf1.setYardLocation(saved.getYardLocationId());
//							cf1.setYardBlock(saved.getBlockId());
//							cf1.setBlockCellNo(saved.getCellNoRow());
////							cf1.setGridReleased(saved.getExBondGridArea());
//							cf1.setExCellAreaAllocated(saved.getExBondGridArea());
//							cf1.setExBondPackages(saved.getExBondyardPackages());
//							cf1.setInBondPackages(saved.getInBondedPackages());
//							cf1.setCellAreaAllocated(cfexbondcrg.getAreaOccupied());
//						
//							cf1.setNocTransId(saved.getNocTransId());
//							cf1.setFinYear("2025");
//						
//							cf1.setInBondPackages(saved.getInBondedPackages());
//							cf1.setStatus("A");
//
//							CfExBondGrid  saved1 = cfExBondGridRepository.save(cf1);
						}

						CfinbondcrgDtl toUpdateInBondCrgDtl = cfexbondcrgDtlRepo.toUpdateInBondCrgDtl(companyId,
								branchId, item.getNocTransId(), item.getCfBondDtlId(), item.getNocNo(),
								item.getInBondingId());

						if (toUpdateInBondCrgDtl != null) 
						{
							int updateCfinbondcrgDtlAfterExbond = cfexbondcrgDtlRepo.updateCfinbondCrgDtlAfterExbond(
									toUpdateInBondCrgDtl.getExBondedPackages()!=null ? toUpdateInBondCrgDtl.getExBondedPackages():BigDecimal.ZERO.add(item.getExBondedPackages()), 
									toUpdateInBondCrgDtl.getExBondedCargoDuty()!=null ? toUpdateInBondCrgDtl.getExBondedCargoDuty():BigDecimal.ZERO.add(item.getExBondedCargoDuty()),
									toUpdateInBondCrgDtl.getExBondedCIF()!=null ?toUpdateInBondCrgDtl.getExBondedCIF():BigDecimal.ZERO.add(item.getExBondedCIF()),
									toUpdateInBondCrgDtl.getExBondedInsurance()!=null ? toUpdateInBondCrgDtl.getExBondedInsurance(): BigDecimal.ZERO.add(item.getExBondedInsurance()), 
									toUpdateInBondCrgDtl.getExBondedGW()!=null?toUpdateInBondCrgDtl.getExBondedGW():BigDecimal.ZERO.add(item.getExBondedGW()), 
									companyId, branchId,
									item.getInBondingId(), item.getNocNo(), item.getNocTransId(), item.getBoeNo(),
									item.getCfBondDtlId());
							System.out
							.println("Update row count after exbond details is" + updateCfinbondcrgDtlAfterExbond);
						}
						
						else
						{
							int updateCfinbondcrgDtlAfterExbond = cfexbondcrgDtlRepo.updateCfinbondCrgDtlAfterExbond(
									item.getExBondedPackages(), item.getExBondedCargoDuty(), item.getExBondedCIF(),
									item.getExBondedInsurance(), item.getExBondedGW(), companyId, branchId,
									item.getInBondingId(), item.getNocNo(), item.getNocTransId(), item.getBoeNo(),
									item.getCfBondDtlId());

							System.out
							.println("Update row count after exbond details is in else " + updateCfinbondcrgDtlAfterExbond);
						}

						
						
//
						CfinbondcrgHDRDtl findExistingHdrdtl = cfinbondCrgHdrDtlRepo.findExistingHdrdtl(companyId,
								branchId, item.getNocTransId(), item.getNocNo(), item.getInBondingId(),
								item.getCfBondDtlId());

						System.out.println(item.getNocTransId());
						System.out.println(item.getNocNo());
						System.out.println(item.getInBondingId());
						System.out.println(item.getCfBondDtlId());
						System.out.println(companyId);
						System.out.println(branchId);
						System.out.println("findExistingHdrdtl________________________________________________"
								+ findExistingHdrdtl);
						if (findExistingHdrdtl != null) {
							int updateCfInbondCrgHdrDtl = cfinbondCrgHdrDtlRepo.updateCfinbondCrgDtlAfterExbond(
									findExistingHdrdtl.getExBondedPackages() != null
											? findExistingHdrdtl.getExBondedPackages()
											: BigDecimal.ZERO.add(item.getExBondedPackages()),
									companyId, branchId, item.getInBondingId(), item.getNocNo(), item.getNocTransId(),
									item.getBoeNo(), item.getCfBondDtlId());

							System.out.println("Update row count after exbond details is" + updateCfInbondCrgHdrDtl);

						} else 
						{
							int updateCfInbondCrgHdrDtl = cfinbondCrgHdrDtlRepo.updateCfinbondCrgDtlAfterExbond(
									item.getExBondedPackages(), companyId, branchId, item.getInBondingId(),
									item.getNocNo(), item.getNocTransId(), item.getBoeNo(), item.getCfBondDtlId());

							System.out.println("Update row count after exbond details is in hder dtl " + updateCfInbondCrgHdrDtl);

						}

					}
				}

			}

			else {

				System.out.println("In Edit loop ");

				CfExBondCrg existingExBond = cfExBondCrgRepository.findExistingCfexbondCrg(companyId, branchId,
						cfexbondcrg.getNocTransId(), cfexbondcrg.getNocNo(), cfexbondcrg.getExBondingId());

				if (existingExBond != null) {
					existingExBond.setAreaRemaining(cfexbondcrg.getAreaRemaining());
					existingExBond.setAreaReleased(cfexbondcrg.getAreaReleased());
					existingExBond.setGateInType(cfexbondcrg.getGateInType());
					existingExBond.setSpaceType(cfexbondcrg.getSpaceType());
					existingExBond.setExBondedPackages(cfexbondcrg.getExBondedPackages());
					existingExBond.setExBondedCif(cfexbondcrg.getExBondedCif());
					existingExBond.setExBondedCargoDuty(cfexbondcrg.getExBondedCargoDuty());
					existingExBond.setExBondedInsurance(cfexbondcrg.getExBondedInsurance());
					existingExBond.setExBondedGw(cfexbondcrg.getExBondedGw());

					existingExBond.setNoOf20Ft(cfexbondcrg.getNoOf20Ft());
					existingExBond.setNoOf40Ft(cfexbondcrg.getNoOf40Ft());
					existingExBond.setStatus("N");
					existingExBond.setEditedBy(user);
					existingExBond.setEditedDate(new Date());

					existingExBond.setBalancedPackages(cfexbondcrg.getRemainingPackages()
							.subtract(Optional.ofNullable(cfexbondcrg.getExBondedPackages()).orElse(BigDecimal.ZERO)));

					existingExBond.setBalanceCif(cfexbondcrg.getRemainingCif()
							.subtract(Optional.ofNullable(cfexbondcrg.getExBondedCif()).orElse(BigDecimal.ZERO)));

					existingExBond.setBalanceCargoDuty(cfexbondcrg.getRemainingCargoDuty()
							.subtract(Optional.ofNullable(cfexbondcrg.getExBondedCargoDuty()).orElse(BigDecimal.ZERO)));

					existingExBond.setBalanceInsurance(cfexbondcrg.getRemainingInsurance()
							.subtract(Optional.ofNullable(cfexbondcrg.getExBondedInsurance()).orElse(BigDecimal.ZERO)));

					existingExBond.setBalanceGw(cfexbondcrg.getRemainingGw()
							.subtract(Optional.ofNullable(cfexbondcrg.getExBondedGw()).orElse(BigDecimal.ZERO)));

					existingExBond.setAreaBalanced(cfexbondcrg.getAreaRemaining()
							.subtract(Optional.ofNullable(cfexbondcrg.getAreaBalanced()).orElse(BigDecimal.ZERO)));

					existingExBond.setBalancedQty(cfexbondcrg.getRemainingPackages()
							.subtract(Optional.ofNullable(cfexbondcrg.getExBondedPackages()).orElse(BigDecimal.ZERO)));

					cfExBondCrgRepository.save(existingExBond);

					Cfbondinsbal existingBalance = cfbondnocDtlRepository.getDataOfCfBondCifForValidation(companyId,
							branchId);

					if (existingBalance != null) {
						int updateCfBalance = cfExBondCrgRepository
								.updateCfbondinsbalAfterExbond(
										existingBalance.getExbondArea()
												.add(cfexbondcrg.getAreaReleased()
														.subtract(existingExBond.getAreaReleased())),
										existingBalance.getExbondCargoDuty().add(cfexbondcrg.getExBondedCargoDuty())
												.subtract(existingExBond.getExBondedCargoDuty()),
										existingBalance.getExbondCifValue().add(cfexbondcrg.getExBondedCif())
												.subtract(existingExBond.getExBondedCif()),
										companyId, branchId);

						System.out.println("updateCfBalance row is after edit" + updateCfBalance);
					}

					Cfinbondcrg findCfinbondCrg = cfinbondcrgRepo.findCfinbondCrg(companyId, branchId,
							existingExBond.getNocTransId(), existingExBond.getInBondingId(), existingExBond.getNocNo());

					if (findCfinbondCrg != null) {
						int exBond20FtSum = safeParseInt(findCfinbondCrg.getExBond20Ft())
								+ safeParseInt(cfexbondcrg.getNoOf20Ft()) - safeParseInt(existingExBond.getNoOf20Ft());

						int exBond40FtSum = safeParseInt(findCfinbondCrg.getExBond40Ft())
								+ safeParseInt(cfexbondcrg.getNoOf40Ft()) - safeParseInt(existingExBond.getNoOf40Ft());

						int updateCfinbondcrgAfterExbond = cfinbondcrgRepo.updateCfinbondCrgAfterExbond(
								findCfinbondCrg.getExBondedPackages().add(cfexbondcrg.getExBondedPackages())
										.subtract(existingExBond.getExBondedPackages()),
								findCfinbondCrg.getExBondedCargoDuty().add(cfexbondcrg.getExBondedCargoDuty())
										.subtract(existingExBond.getExBondedCargoDuty()),
								findCfinbondCrg.getExBondedCif().add(cfexbondcrg.getExBondedCif())
										.subtract(existingExBond.getExBondedCif()),
								findCfinbondCrg.getExBondedInsurance().add(cfexbondcrg.getExBondedInsurance())
										.subtract(existingExBond.getExBondedInsurance()),
								findCfinbondCrg.getExBondedGw().add(cfexbondcrg.getExBondedGw())
										.subtract(existingExBond.getExBondedGw()),
								String.valueOf(exBond20FtSum), // Convert the sum back to String
								String.valueOf(exBond40FtSum), // Convert the sum back to String
								companyId, branchId, cfexbondcrg.getInBondingId(), cfexbondcrg.getNocNo(),
								cfexbondcrg.getNocTransId(), cfexbondcrg.getBoeNo());

						System.out.println("Update row count after exbond is" + updateCfinbondcrgAfterExbond);
					}

					Cfinbondcrg existing = cfinbondcrgRepo.findCfinbondHdr(companyId, branchId,
							cfexbondcrg.getNocTransId(), cfexbondcrg.getInBondingId(), cfexbondcrg.getNocNo());

					if (existing != null) {

						System.out.println(existing.getInBondingHdrId());
						System.out.println(cfexbondcrg.getNocNo());

						System.out.println(cfexbondcrg.getBoeNo());
						System.out.println(cfexbondcrg.getNocTransId());

						CfinbondcrgHDR findCfBondCrgHDRData = cfinbondCrgHdrRepo.findCfBondCrgHDRData(companyId,
								branchId, existing.getNocTransId(), existing.getInBondingHdrId(), existing.getNocNo());

						if (findCfBondCrgHDRData != null) {
							int updateCfInbondCrdHDR = cfinbondCrgHdrRepo.updateCfInbondHeaderAfterExbond(
									findCfBondCrgHDRData.getExBondedPackages().add(cfexbondcrg.getExBondedPackages())
											.subtract(existingExBond.getInBondedPackages()),
									findCfBondCrgHDRData.getExBondedCargoDuty().add(cfexbondcrg.getExBondedCargoDuty())
											.subtract(existingExBond.getExBondedCargoDuty()),
									findCfBondCrgHDRData.getExBondedInsurance().add(cfexbondcrg.getExBondedInsurance())
											.subtract(existingExBond.getExBondedInsurance()),
									findCfBondCrgHDRData.getExBondedCif().add(cfexbondcrg.getExBondedCif())
											.subtract(existingExBond.getExBondedCif()),
									findCfBondCrgHDRData.getExBondedGw().add(cfexbondcrg.getExBondedGw())
											.subtract(existingExBond.getExBondedGw()),
									companyId, branchId, cfexbondcrg.getNocTransId(), cfexbondcrg.getNocNo(),
									existing.getInBondingHdrId(), cfexbondcrg.getBoeNo());

							System.out.println("Update row count after exbond hdr in edit " + updateCfInbondCrdHDR);
						}

					}

					for (CfinbondcrgDtl item : cfexbondcrgDtlList) {

						CfexBondCrgDtl findExistingCfexbondCrgDtl = cfExBondCrgDtlRepository.findExistingCfexbondCrgDtl(
								companyId, branchId, item.getNocTransId(), item.getNocNo(), item.getCfBondDtlId(),
								existingExBond.getInBondingId(), existingExBond.getExBondingId());
						

						if (findExistingCfexbondCrgDtl != null) 
						{
							
							int editedRowInCfEcbondCrgDtl = cfExBondCrgDtlRepository.updateExbondCrgDetail(user,
									new Date(), item.getExBondedPackages(), item.getExBondedCIF(),
									item.getExBondedCargoDuty(), item.getExBondedInsurance(), item.getExBondedGW(),item.getExBondyardPackages(),item.getExBondGridArea(),'N',
									companyId, branchId, item.getCfBondDtlId(), item.getNocTransId(), item.getNocNo(),
									existingExBond.getInBondingId(), existingExBond.getExBondingId());

							System.out.println(
									"editedRowInCfEcbondCrgDtl_____________________________________________________________"
											+ editedRowInCfEcbondCrgDtl);

							if (editedRowInCfEcbondCrgDtl > 0) {

								CfExBondGrid toEditData =cfExBondGridRepository.toEditData(companyId, branchId, existingExBond.getExBondingId(), item.getCfBondDtlId(), item.getYardLocationId(), item.getBlockId(), item.getCellNoRow());
								
								if (toEditData!=null)
								{
									int updateGrid =cfExBondGridRepository.updateCfexBondDtlAfterExBondGrid(item.getExBondyardPackages(),item.getExBondGridArea(),
											companyId,branchId,existingExBond.getExBondingId(), item.getCfBondDtlId(), item.getYardLocationId(), item.getBlockId(), item.getCellNoRow());
									
									System.out.println("updated row in cfexbond grid :"+updateGrid);
								}
								
								

								CfinbondcrgDtl toUpdateInBondCrgDtl = cfexbondcrgDtlRepo.toUpdateInBondCrgDtl(companyId,
										branchId, item.getNocTransId(), item.getCfBondDtlId(), item.getNocNo(),
										existingExBond.getInBondingId());

								if (toUpdateInBondCrgDtl != null) {
									int updateCfinbondcrgDtlAfterExbond = cfexbondcrgDtlRepo
											.updateCfinbondCrgDtlAfterExbond(
													toUpdateInBondCrgDtl.getExBondedPackages()
															.add(item.getExBondedPackages())
															.subtract(findExistingCfexbondCrgDtl.getExBondedPackages()),
													toUpdateInBondCrgDtl.getExBondedCargoDuty()
															.add(item.getExBondedCargoDuty()).subtract(
																	findExistingCfexbondCrgDtl.getExBondedCargoDuty()),
													toUpdateInBondCrgDtl.getExBondedCIF().add(item.getExBondedCIF())
															.subtract(findExistingCfexbondCrgDtl.getExBondedCIF()),
													toUpdateInBondCrgDtl.getExBondedInsurance()
															.add(item.getExBondedInsurance()).subtract(
																	findExistingCfexbondCrgDtl.getExBondedInsurance()),
													toUpdateInBondCrgDtl.getExBondedGW().add(item.getExBondedGW())
															.subtract(findExistingCfexbondCrgDtl.getExBondedGW()),
													companyId, branchId, item.getInBondingId(), item.getNocNo(),
													item.getNocTransId(), item.getBoeNo(), item.getCfBondDtlId());

									System.out.println("Update row count after exbond details after edit in details is"
											+ updateCfinbondcrgDtlAfterExbond);
								}

								CfinbondcrgHDRDtl findExistingHdrdtl = cfinbondCrgHdrDtlRepo.findExistingHdrdtl(
										companyId, branchId, item.getNocTransId(), item.getNocNo(),
										item.getInBondingId(), item.getCfBondDtlId());

								if (findExistingHdrdtl != null) {
									int updateCfInbondCrgHdrDtl = cfinbondCrgHdrDtlRepo.updateCfinbondCrgDtlAfterExbond(
											findExistingHdrdtl.getExBondedPackages() != null
													? findExistingHdrdtl.getExBondedPackages()
													: BigDecimal.ZERO.add(item.getExBondedPackages().subtract(
															findExistingCfexbondCrgDtl.getExBondedPackages())),
											companyId, branchId, item.getInBondingId(), item.getNocNo(),
											item.getNocTransId(), item.getBoeNo(), item.getCfBondDtlId());

									System.out.println(
											"Update row count after exbond details is" + updateCfInbondCrgHdrDtl);

								}

							}

						}

						else 
						{
							CfexBondCrgDtl exBondDtl = new CfexBondCrgDtl();

							exBondDtl.setCreatedBy(user);
							exBondDtl.setCreatedDate(new Date());
							exBondDtl.setApprovedBy(user);
							exBondDtl.setApprovedDate(new Date());
							exBondDtl.setFinYear("2025");
							exBondDtl.setInBondedPackages(item.getInBondedPackages());
							exBondDtl.setInbondCifValue(item.getInbondCifValue());
							exBondDtl.setInbondCargoDuty(item.getInbondCargoDuty());
							exBondDtl.setInbondInsuranceValue(item.getInbondInsuranceValue());
							exBondDtl.setInbondGrossWt(item.getInbondGrossWt());
							exBondDtl.setExBondedPackages(item.getExBondedPackages());
							exBondDtl.setExBondedCIF(item.getExBondedCIF());
							exBondDtl.setExBondedCargoDuty(item.getExBondedCargoDuty());
							exBondDtl.setExBondedInsurance(item.getExBondedInsurance());
							exBondDtl.setExBondedGW(item.getExBondedGW());
							exBondDtl.setNocNo(item.getNocNo());
							exBondDtl.setNocTransId(item.getNocTransId());
							exBondDtl.setNocPackages(item.getNocPackages());
							exBondDtl.setCommodityDescription(item.getCommodityDescription());
							exBondDtl.setBoeNo(item.getBoeNo());
							exBondDtl.setInBondingId(item.getInBondingId());
							exBondDtl.setCompanyId(companyId);
							exBondDtl.setBranchId(branchId);
							exBondDtl.setStatus('N');
							exBondDtl.setTypeOfPackage(item.getTypeOfPackage());
							exBondDtl.setBondingDate(existingExBond.getBondingDate());
							exBondDtl.setBondingNo(existingExBond.getBondingNo());
							exBondDtl.setExBondBeNo(existingExBond.getExBondBeNo());
							exBondDtl.setExBondingId(existingExBond.getExBondingId());
							exBondDtl.setCfBondDtlId(item.getCfBondDtlId());
							exBondDtl.setYardLocationId(item.getYardLocationId());
							exBondDtl.setBlockId(item.getBlockId());
							exBondDtl.setCellNoRow(item.getCellNoRow());

							exBondDtl.setIgmNo(cfexbondcrg.getIgmNo());
							exBondDtl.setIgmLineNo(cfexbondcrg.getIgmLineNo());
							exBondDtl.setExBondyardPackages(item.getExBondyardPackages());
							exBondDtl.setExBondGridArea(item.getExBondGridArea());

							CfexBondCrgDtl saved=	cfExBondCrgDtlRepository.save(exBondDtl);
							
//							CfexBondCrgDtl saved=	cfExBondCrgDtlRepository.save(exBondDtl);
							
							if (saved!=null)
							{
								
								List<CfInBondGrid> existList =cfInBondGridRepository.getDataForExbondGrid(companyId, branchId, item.getInBondingId(), item.getNocTransId(), item.getCfBondDtlId());
								

								if(existList!=null)
								{
									existList.forEach(data -> {
										
										CfExBondGrid cf1 = new CfExBondGrid();
										
										
										Integer maxSrNo = cfExBondGridRepository.getMaxSrNo(companyId, branchId, saved.getExBondingId(),
												saved.getCfBondDtlId());
										
										
//										cf1.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
										cf1.setSrNo(data.getSrNo());
										cf1.setCompanyId(companyId);
										cf1.setBranchId(branchId);
										cf1.setCreatedBy(user);
										cf1.setCreatedDate(new Date());
										cf1.setApprovedBy(user);
										cf1.setApprovedDate(new Date());
										cf1.setInBondingId(data.getInBondingId());
										cf1.setExBondingId(saved.getExBondingId());
										cf1.setCfBondDtlId(data.getCfBondDtlId());
										
										cf1.setYardLocation(data.getYardLocation());
										cf1.setYardBlock(data.getYardBlock());
										cf1.setBlockCellNo(data.getBlockCellNo());
//										cf1.setGridReleased(saved.getExBondGridArea());
										cf1.setExCellAreaAllocated(BigDecimal.ZERO);
										cf1.setExBondPackages(BigDecimal.ZERO);
										
										cf1.setInBondPackages(data.getInBondPackages());
										cf1.setCellAreaAllocated(data.getCellAreaAllocated());
									
										cf1.setNocTransId(data.getNocTransId());
										cf1.setFinYear("2025");
										cf1.setStatus("A");

										CfExBondGrid savedGrid =cfExBondGridRepository.save(cf1);
										
										
										if (savedGrid!=null)
										{
											 CfExBondGrid toEditData =cfExBondGridRepository.toEditData(companyId, branchId, saved.getExBondingId(), saved.getCfBondDtlId(), 
				                                		saved.getYardLocationId(), saved.getBlockId(), saved.getCellNoRow());
												
												if (toEditData!=null)
												{
													int updateGrid =cfExBondGridRepository.updateCfexBondDtlAfterExBondGrid(saved.getExBondyardPackages(),saved.getExBondGridArea(),
															companyId,branchId,saved.getExBondingId(), saved.getCfBondDtlId(), saved.getYardLocationId(), saved.getBlockId(), saved.getCellNoRow());

													System.out.println("updated row in cfexbond grid :"+updateGrid);
												}
										}
										
									});	
									
								
								}
								
							}
//							if (saved!=null)
//							{
//								CfExBondGrid cf1 = new CfExBondGrid();
//								
//								Integer maxSrNo = cfExBondGridRepository.getMaxSrNo(companyId, branchId, saved.getExBondingId(),
//										saved.getCfBondDtlId());
//
//								cf1.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
//								cf1.setCompanyId(companyId);
//								cf1.setBranchId(branchId);
//								cf1.setCreatedBy(user);
//								cf1.setCreatedDate(new Date());
//								cf1.setApprovedBy(user);
//								cf1.setApprovedDate(new Date());
//								cf1.setInBondingId(saved.getInBondingId());
//								cf1.setExBondingId(saved.getExBondingId());
//								cf1.setCfBondDtlId(saved.getCfBondDtlId());
//								
//								cf1.setYardLocation(saved.getYardLocationId());
//								cf1.setYardBlock(saved.getBlockId());
//								cf1.setBlockCellNo(saved.getCellNoRow());
////								cf1.setGridReleased(saved.getExBondGridArea());
//								cf1.setExCellAreaAllocated(saved.getExBondGridArea());
//								cf1.setExBondPackages(saved.getExBondyardPackages());
//								cf1.setInBondPackages(saved.getInBondedPackages());
////								cf1.setCellAreaAllocated(saved.getCellAreaAllocated());
//								cf1.setCellAreaAllocated(cfexbondcrg.getAreaOccupied());
//								cf1.setNocTransId(saved.getNocTransId());
//								cf1.setFinYear("2025");
//							
//								cf1.setInBondPackages(saved.getInBondedPackages());
//								cf1.setStatus("A");
//
//								CfExBondGrid  saved1 = cfExBondGridRepository.save(cf1);
//								
//							
//							}
							
		
							
							CfinbondcrgDtl toUpdateInBondCrgDtl = cfexbondcrgDtlRepo.toUpdateInBondCrgDtl(companyId,
									branchId, item.getNocTransId(), item.getCfBondDtlId(), item.getNocNo(),
									item.getInBondingId());

							if (toUpdateInBondCrgDtl != null) {
								
//								int updateCfinbondcrgDtlAfterExbond = cfexbondcrgDtlRepo.updateCfinbondCrgDtlAfterExbond(
//										toUpdateInBondCrgDtl.getExBondedPackages().add(item.getExBondedPackages()), 
//										toUpdateInBondCrgDtl.getExBondedCargoDuty().add(item.getExBondedCargoDuty()),
//										toUpdateInBondCrgDtl.getExBondedCIF().add(item.getExBondedCIF()),
//										toUpdateInBondCrgDtl.getExBondedInsurance().add(item.getExBondedInsurance()), 
//										toUpdateInBondCrgDtl.getExBondedGW().add(item.getExBondedGW()), 
//										companyId, branchId,
//										item.getInBondingId(), item.getNocNo(), item.getNocTransId(), item.getBoeNo(),
//										item.getCfBondDtlId());
//								System.out
//								.println("Update row count after exbond details is" + updateCfinbondcrgDtlAfterExbond);
								
								int updateCfinbondcrgDtlAfterExbond = cfexbondcrgDtlRepo.updateCfinbondCrgDtlAfterExbond(
										toUpdateInBondCrgDtl.getExBondedPackages()!=null ? toUpdateInBondCrgDtl.getExBondedPackages():BigDecimal.ZERO.add(item.getExBondedPackages()), 
										toUpdateInBondCrgDtl.getExBondedCargoDuty()!=null ? toUpdateInBondCrgDtl.getExBondedCargoDuty():BigDecimal.ZERO.add(item.getExBondedCargoDuty()),
										toUpdateInBondCrgDtl.getExBondedCIF()!=null ?toUpdateInBondCrgDtl.getExBondedCIF():BigDecimal.ZERO.add(item.getExBondedCIF()),
										toUpdateInBondCrgDtl.getExBondedInsurance()!=null ? toUpdateInBondCrgDtl.getExBondedInsurance(): BigDecimal.ZERO.add(item.getExBondedInsurance()), 
										toUpdateInBondCrgDtl.getExBondedGW()!=null?toUpdateInBondCrgDtl.getExBondedGW():BigDecimal.ZERO.add(item.getExBondedGW()), 
										companyId, branchId,
										item.getInBondingId(), item.getNocNo(), item.getNocTransId(), item.getBoeNo(),
										item.getCfBondDtlId());
								System.out
								.println("Update row count after exbond details is" + updateCfinbondcrgDtlAfterExbond);
							}
							
							else
							{
								int updateCfinbondcrgDtlAfterExbond = cfexbondcrgDtlRepo.updateCfinbondCrgDtlAfterExbond(
										item.getExBondedPackages(), item.getExBondedCargoDuty(), item.getExBondedCIF(),
										item.getExBondedInsurance(), item.getExBondedGW(), companyId, branchId,
										item.getInBondingId(), item.getNocNo(), item.getNocTransId(), item.getBoeNo(),
										item.getCfBondDtlId());

								System.out
								.println("Update row count after exbond details is" + updateCfinbondcrgDtlAfterExbond);
							}
							

							CfinbondcrgHDRDtl findExistingHdrdtl = cfinbondCrgHdrDtlRepo.findExistingHdrdtl(companyId,
									branchId, item.getNocTransId(), item.getNocNo(), item.getInBondingId(),
									item.getCfBondDtlId());

							if (findExistingHdrdtl != null) 
							{
								int updateCfInbondCrgHdrDtl = cfinbondCrgHdrDtlRepo.updateCfinbondCrgDtlAfterExbond(
										findExistingHdrdtl.getExBondedPackages() != null
												? findExistingHdrdtl.getExBondedPackages()
												: BigDecimal.ZERO.add(item.getExBondedPackages()),
										companyId, branchId, item.getInBondingId(), item.getNocNo(),
										item.getNocTransId(), item.getBoeNo(), item.getCfBondDtlId());

								System.out
										.println("Update row count after exbond details is" + updateCfInbondCrgHdrDtl);

							} else 
								
							{
								int updateCfInbondCrgHdrDtl = cfinbondCrgHdrDtlRepo.updateCfinbondCrgDtlAfterExbond(
										item.getExBondedPackages(), companyId, branchId, item.getInBondingId(),
										item.getNocNo(), item.getNocTransId(), item.getBoeNo(), item.getCfBondDtlId());

								System.out.println("Update row count after exbond details is in hder dtl in edit " + updateCfInbondCrgHdrDtl);

							}

						}

					}

				}

			}
		}

		System.out.println("cfexbondcrg_______________________" + cfexbondcrg);

		System.out.println("cfexbondcrgDtl_______________________" + cfexbondcrgDtlList);

		return new ResponseEntity<>(cfexbondcrg, HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<?> approveDataOfInCFbondGrid(String companyId, String branchId, String flag, String user,
	        Map<String, Object> requestBody) {

	    ObjectMapper object = new ObjectMapper();
	    object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	    CfExBondCrg cfinbondcrg = object.convertValue(requestBody.get("exBond"), CfExBondCrg.class);
	    
	    CfexBondCrgDtl dataForPrint =null;
	    
	    System.out.println("cfinbondcrg________________________"+cfinbondcrg);
	    
	    if (cfinbondcrg.getExBondingId()==null || cfinbondcrg.getExBondingId().isEmpty() || cfinbondcrg.getExBondingId().isBlank())
	    {
	    	return new ResponseEntity<>("Please First Save Data", HttpStatus.BAD_REQUEST);
	    }
	    
	    if (cfinbondcrg != null && cfinbondcrg.getExBondingId()!=null || !cfinbondcrg.getExBondingId().isEmpty() || !cfinbondcrg.getExBondingId().isBlank()) 	    	
	    {

	        BigDecimal sumOfInbondFromDtl = cfExBondCrgDtlRepository.getSumOfInBondPackages(companyId, branchId,
	                cfinbondcrg.getExBondingId(), cfinbondcrg.getNocTransId());


	        BigDecimal sumOfInbondFormGrid = cfExBondGridRepository.getSumOfInBondPackages(companyId, branchId,
	                cfinbondcrg.getExBondingId(), cfinbondcrg.getNocTransId());

	        System.out.println("sumOfInbondFromDtl: " + sumOfInbondFromDtl + " ______________ " + sumOfInbondFormGrid);
	        
	        if (sumOfInbondFromDtl == null || sumOfInbondFormGrid == null) {
	            return new ResponseEntity<>("One of the sum values is null. Please check the data.", HttpStatus.BAD_REQUEST);
	        }

//	        if (sumOfInbondFormGrid.compareTo(sumOfInbondFromDtl) < 0) {
//	        	
//	            return new ResponseEntity<>("ExbondPackages do not match in yard, please add packages in grid.", HttpStatus.BAD_REQUEST);
//	        }
	        if (sumOfInbondFormGrid.compareTo(sumOfInbondFromDtl) != 0) {
	            return new ResponseEntity<>("ExbondPackages do not match in yard, please add packages in grid.", HttpStatus.BAD_REQUEST);
	        }
	        else
	        {
	        	 int updateAfterApprov =cfExBondCrgDtlRepository.updateAfterApprove("A", companyId, branchId, cfinbondcrg.getExBondingId(), cfinbondcrg.getNocNo(), cfinbondcrg.getNocTransId());
	        	 System.out.println("updateAfterApprov row count "+updateAfterApprov);
	        	 
	        	 
	        	 List<CfexBondCrgDtl> updateAfterApprove =cfExBondCrgDtlRepository.updateAfterApprove(companyId, branchId, cfinbondcrg.getNocTransId(), cfinbondcrg.getNocNo(), cfinbondcrg.getInBondingId(), cfinbondcrg.getBoeNo(), cfinbondcrg.getExBondingId());
	        	 if (updateAfterApprove!=null)
	        	 {
	        		 updateAfterApprove.forEach(data -> data.setStatus('A'));

	        		    // Batch save all records in one go
	        		    cfExBondCrgDtlRepository.saveAll(updateAfterApprove);
	        		    
	        		    
	        		    dataForPrint = updateAfterApprove.get(0);
		        		 
		        		 
		        		 System.out.println("dataForPrint_____________________"+dataForPrint);
		        		 return new ResponseEntity<>(dataForPrint, HttpStatus.OK);
	        	 }
	        }
	    }
	   
	    return new ResponseEntity<>(dataForPrint, HttpStatus.OK);
	}


	 public BigDecimal getSumOfInBondPackagesForCommodity(String companyId, String branchId, String exBondingId, String cfBondDtlId, String nocTransId) {
	        return cfExBondCrgDtlRepository.getSumOfInBondPackagesForCommodity(companyId, branchId, exBondingId, cfBondDtlId, nocTransId);
	    }
	 
	 
	public List<CfExBondCrg> getAllSavedDataOfExbondcrg(String compnayId, String branchId, String partyName) {
		return cfExBondCrgRepository.findCfinbondcrgByCompanyIdAndBranchIdForExbondScreen(compnayId, branchId,
				partyName);
	}

	public CfExBondCrg getDataOfExbond(String companyId, String branchId, String nocTransId, String nocNo,
			String exBondingId, String inBondingId) {
		return cfExBondCrgRepository.getDataOfExbond(companyId, branchId, nocTransId, nocNo, exBondingId, inBondingId);
	}

	public List<CfexBondCrgDtl> getCfBondInBondDTLData(String companyId, String branchId, String nocTransId,
			String nocNo, String inBondingId, String boeNo, String exBondingId) {
		return cfExBondCrgDtlRepository.getCfBondInBondDTLData(companyId, branchId, nocTransId, nocNo, inBondingId,
				boeNo, exBondingId);
	}

	public List<Object[]> getData1(String cid, String bid, String val) {
		return cfExBondCrgRepository.getData1(cid, bid, val);
	}
//	 public List<Object[]> getDataOfImporter(String cid, String bid, String cha,String val) {
//	        return cfExBondCrgRepository.getDataOfImporterOfCha(cid, bid,cha, val);
//	    }

	public List<Object[]> getDataOfImporter(String cid, String bid, String cha) {
		return cfExBondCrgRepository.getDataOfImporterOfCha(cid, bid, cha);
	}

//	 public List<Object[]> getDataOfImporter(String cid, String bid, String cha,String val) {
//	        return cfExBondCrgRepository.getDataOfImporterOfCha(cid, bid,cha,val);
//	    }

	public List<Object[]> getAddressOfImporter(String cid, String bid, String cha) {
		List<Object[]> addressData = cfExBondCrgRepository.getAddressOfImporterOfCha(cid, bid, cha);

		// Print the data to the console
		System.out.println("Address Data:");
		for (Object[] row : addressData) {
			// Check if row length matches expected length
			if (row.length >= 4) {
				System.out.println("srNo: " + row[0] + ", Address1: " + row[1] + ", Address2: " + row[2]
						+ ", Address3: " + row[3]);
			} else {
				System.out
						.println("Unexpected row length: " + row.length + ", Data: " + java.util.Arrays.toString(row));
			}
		}

		return addressData;
	}

	public List<Object[]> getDataOfExbondBeNo(String cid, String bid, String val) {
		return cfExBondCrgDtlRepository.getAllExbondBeNoFromExbondDtl(cid, bid, val);
	}

	public List<Object[]> getDataOfExbondBeNoCargo(String cid, String bid, String exbondBeNo, String val) {
		return cfExBondCrgDtlRepository.getAllExbondCargoFromExbondDtl(cid, bid, exbondBeNo, val);
	}
	
	
	
	
	
	
	
	
	public ResponseEntity<String> getPrintOfCutomesBondExBondCargo( String companyId,
			String branchId,  String username,
			 String type,String companyname,
			 String branchname,  String exBondingId) throws DocumentException {
		
		Context context = new Context();

		CfExBondCrg dataForPrint = null;
		
		
		List<CfExBondCrg> result1 = cfExBondCrgRepository.getDataForCustomsBondExbond(companyId,
				branchId, exBondingId);

		List<CfExBondCrg> result = cfExBondCrgRepository.getDataForCustomsBondExbondReport(companyId,
				branchId, exBondingId);
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
		
		context.setVariable("exBondingId", dataForPrint.getExBondingId());
		context.setVariable("exBondingIdDate", dataForPrint.getExBondingDate());
		
		context.setVariable("inBondingId", dataForPrint.getInBondingId());
		context.setVariable("inBondingIdDate", dataForPrint.getInBondingDate());
		
		context.setVariable("exboeNo", dataForPrint.getExBondBeNo());
		context.setVariable("exBondBeDate", dataForPrint.getExBondBeDate());
		
		context.setVariable("boeNo", dataForPrint.getBoeNo());
//		context.setVariable("boeNo", boeNosBuilder.toString());
		context.setVariable("boeDate", dataForPrint.getInBondingDate());
		context.setVariable("igmNo", dataForPrint.getIgmNo());
		
		context.setVariable("nocNo", dataForPrint.getNocNo());

		context.setVariable("igmLineNo", dataForPrint.getIgmLineNo());
		context.setVariable("bondingNo", dataForPrint.getBondingNo());
		
		context.setVariable("bondingDate", dataForPrint.getBondingDate());

		context.setVariable("inBondPackages", dataForPrint.getInBondedPackages());
		context.setVariable("inBondWt", dataForPrint.getInbondGrossWt());
		
	
		context.setVariable("consignee", dataForPrint.getGiTransporterName());
		
		context.setVariable("area", dataForPrint.getAreaOccupied());
		
		context.setVariable("gateInType", dataForPrint.getGateInType());
		
		context.setVariable("spaceType", dataForPrint.getSpaceType());
		context.setVariable("exBondGrWeight", dataForPrint.getExBondedGw());
		context.setVariable("consignee", dataForPrint.getImporterName());
		
		context.setVariable("typeOfPackages", dataForPrint.getTypeOfPackage());
		context.setVariable("cha", dataForPrint.getCha());
		context.setVariable("address", dataForPrint.getImporterAddress1() + " " + dataForPrint.getImporterAddress2()
				+ " " + dataForPrint.getImporterAddress3());
		
		context.setVariable("cargoDiscrpition", dataForPrint.getCommodityDescription());

		context.setVariable("cargoDesc", dataForPrint.getCommodityDescription());
	
		context.setVariable("result", result1);
		context.setVariable("c1", c1);
		context.setVariable("b1", b1);
		context.setVariable("u1", u1);
		context.setVariable("companyAdd", companyAdd);
		context.setVariable("branchAdd", branchAdd);
		context.setVariable("bondCode", bondCode);
		context.setVariable("city", city);

		String htmlContent = templateEngine.process("CustomsBondExbond", context);

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