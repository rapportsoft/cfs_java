package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.entities.Branch;
import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.CfExBondGrid;
import com.cwms.entities.CfInBondGrid;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.CfexBondCrgDtl;
import com.cwms.entities.Company;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfExBondCrgDtlRepository;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfExBondGridRepository;
import com.cwms.repository.CfInBondGridRepository;
import com.cwms.repository.CfbondGatePassRepository;
import com.cwms.repository.CfbondnocRepository;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.GateInRepo;
import com.cwms.repository.PartyAddressRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.cwms.repository.YardBlockCellRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

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
	
	@Autowired
    private VehicleTrackRepository vehicleTrackRepository;
	
	@Autowired
	private CfExBondGridRepository cfExBondGridRepository;

	@Autowired
	public YardBlockCellRepository yardBlockCellRepository;
	
	@Autowired
	private CfInBondGridRepository cfInBondGridRepository;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private CfbondnocRepository cfbondnocRepository;

	

	
	
	
	
	
	@Transactional
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

					for (CFBondGatePass item : gatePassDtlList) 
					{
						CFBondGatePass gatePassDtl = new CFBondGatePass();

						gatePassDtl.setCompanyId(companyId);
						gatePassDtl.setBranchId(branchId);
						gatePassDtl.setFinYear("2025");
						gatePassDtl.setCreatedBy(user);
						gatePassDtl.setCreatedDate(new Date());
						gatePassDtl.setApprovedBy(user);
						gatePassDtl.setApprovedDate(new Date());
						gatePassDtl.setStatus("N");
						gatePassDtl.setAreaAllocated(item.getAreaAllocated());
						gatePassDtl.setVehGateInId(gatePass.getVehGateInId());
						
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
						gatePassDtl.setDeliveryOrderNo(gatePass.getDeliveryOrderNo());
						gatePassDtl.setDeliveryOrderDate(gatePass.getDeliveryOrderDate());
						gatePassDtl.setDoValidityDate(gatePass.getDoValidityDate());

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
			
						gatePassDtl.setBondingNo(item.getBondingNo());
						gatePassDtl.setCommodityDescription(item.getCommodityDescription());
						gatePassDtl.setSrNo(sr);

						gatePassDtl.setIgmLineNo(item.getIgmLineNo());
						gatePassDtl.setIgmNo(item.getIgmNo());
						gatePassDtl.setYardAreaReleased(item.getYardAreaReleased());
						gatePassDtl.setYardQtyTakenOut(item.getYardQtyTakenOut());
						saved = cfbondGatePassRepository.save(gatePassDtl);

						savedDtl.add(saved);

						sr++;
						if(saved!=null) 
						
						{
							CfexBondCrgDtl existing = cfExBondCrgDtlRepository.findExistingCfexbondCrgDtl(companyId, branchId, saved.getNocTransId(),saved.getNocNo(),
									saved.getCommodity(),saved.getInBondingId(),saved.getExBondingId());
							
							if(existing!=null)
							{
								 BigDecimal updatedQty = Optional.ofNullable(existing.getOutQty()).orElse(BigDecimal.ZERO)
								            .add(Optional.ofNullable(saved.getQtyTakenOut()).orElse(BigDecimal.ZERO));
								    
								int updateCfExbondcrgDtlAfterGatePass = cfExBondCrgDtlRepository.updateCfexbondDtlAfterGatePass(
										updatedQty,
										companyId, branchId, saved.getExBondingId(),
										saved.getExBondBeNo(), saved.getCommodity());

								System.out.println(
										"Update row count after exbond details is" + updateCfExbondcrgDtlAfterGatePass);
							}
							else
							{
								int updateCfExbondcrgDtlAfterGatePass = cfExBondCrgDtlRepository.updateCfexbondDtlAfterGatePass(
										saved.getQtyTakenOut(),
										companyId, branchId, saved.getExBondingId(),
										saved.getExBondBeNo(), saved.getCommodity());

								System.out.println(
										"Update row count after exbond details is" + updateCfExbondcrgDtlAfterGatePass);
							}
							
				List<CfExBondGrid> existList =cfExBondGridRepository.getDataForBondGatePassAfterExbond(companyId, branchId, saved.getExBondingId(),saved.getCommodity());
							

							System.out.println("existList________________________"+existList);
							
							if(existList!=null)
							{
								
								for (CfExBondGrid data : existList)
								{
									System.out.println("data________________________"+data);
									
									data.setQtyTakenOut(BigDecimal.ZERO);
									data.setAreaReleased(BigDecimal.ZERO);
									
									CfExBondGrid savedGrid =cfExBondGridRepository.save(data);

									
									if (savedGrid!=null)
									{
										CfExBondGrid toEditData1=cfExBondGridRepository.toEditData(companyId, branchId, saved.getExBondingId(), saved.getCommodity(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
										
										System.out.println("toEditData1_____________________________"+toEditData1);
										
										if(toEditData1!=null)
										{
											
											BigDecimal total =toEditData1.getQtyTakenOut().add(saved.getYardQtyTakenOut());
											
											
											
											int upadteExBondGrid =cfExBondGridRepository.updateExbondGridAfterGatePass(total,
													toEditData1.getAreaReleased().add(saved.getYardAreaReleased()),
															companyId, branchId, saved.getExBondingId(), 
													saved.getCommodity(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
											
											System.out.println("toEditData1_____________________________Row :"+upadteExBondGrid);
											
//											toEditData1.setQtyTakenOut(toEditData1.getQtyTakenOut()!=null ? toEditData1.getQtyTakenOut():BigDecimal.ZERO.add(saved.getQtyTakenOut()));
//											toEditData1.setAreaReleased(toEditData1.getAreaReleased()!=null ? toEditData1.getAreaReleased():BigDecimal.ZERO.add(saved.getAreaReleased()));
//											cfExBondGridRepository.save(toEditData1);
										}
										
										
										CfInBondGrid getExistingData=cfInBondGridRepository.toEditData(companyId, branchId, saved.getInBondingId(), saved.getCommodity(), saved.getYardLocation(),saved.getYardBlock(),saved.getBlockCellNo());
										System.out.println("getExistingData_____________________________"+getExistingData);
										
										if(getExistingData!=null)
										{
											BigDecimal exitspack = getExistingData.getQtyTakenOut()!=null ? getExistingData.getQtyTakenOut() :BigDecimal.ZERO;
											
											BigDecimal exitsarea = getExistingData.getAreaReleased()!=null ? getExistingData.getAreaReleased() :BigDecimal.ZERO;
											
											
											int upadteExBondGrid =cfInBondGridRepository.updateInbondGridAfterGatePass(exitspack.add(saved.getYardQtyTakenOut()), 
													exitsarea.add(saved.getYardAreaReleased()),
															companyId, branchId, saved.getInBondingId(), 
													saved.getCommodity(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
											
											System.out.println("getExistingData_____________________________Row :"+upadteExBondGrid);
											
											
//											getExistingData.setQtyTakenOut(getExistingData.getQtyTakenOut()!=null? getExistingData.getQtyTakenOut() :BigDecimal.ZERO.add(saved.getQtyTakenOut()));
//											getExistingData.setAreaReleased(getExistingData.getAreaReleased()!=null ? getExistingData.getAreaReleased():BigDecimal.ZERO.add(saved.getAreaReleased()));
//											
//											cfInBondGridRepository.save(getExistingData);
										}
										
							
										YardBlockCell getAllData=  yardBlockCellRepository.getAllData(companyId, branchId, saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
										System.out.println("getAllData_____________________________"+getAllData);
										if(getAllData!=null)
										{
											
											int updateYardBlockCell =yardBlockCellRepository.updateYardBlockCellAfterGatePass(getAllData.getCellAreaUsed().subtract(saved.getYardAreaReleased()),
													companyId, branchId, saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
											
											System.out.println("getAllData_____________________________updateYardBlockCell :"+updateYardBlockCell);
//											getAllData.setCellAreaUsed(getAllData.getCellAreaUsed() !=null ? getAllData.getCellAreaUsed() : BigDecimal.ZERO.subtract(saved.getAreaReleased()!=null ? saved.getAreaReleased() : BigDecimal.ZERO));
//											yardBlockCellRepository.save(getAllData);
										}
									}
								}
								
							}
							
			
						}

					}
					if (savedDtl.size() > 0)

					{
						CFBondGatePass firstSavedObject = savedDtl.get(0);
						
						BigDecimal totalOut =BigDecimal.ZERO;
						for (CFBondGatePass savedObject : savedDtl) {

							totalOut=totalOut.add(savedObject.getQtyTakenOut());
						}
						
						CfExBondCrg findExistingCfexbondCrg =cfExBondCrgRepository.findExistingCfexbondCrg(companyId, branchId, firstSavedObject.getNocTransId(), firstSavedObject.getNocNo(), firstSavedObject.getExBondingId());

						if(findExistingCfexbondCrg!=null)
						{
							 BigDecimal existingQtyTakenOut = Optional.ofNullable(findExistingCfexbondCrg.getQtyTakenOut()).orElse(BigDecimal.ZERO);
							 
							int updateExbondCrg = cfExBondCrgRepository.updateCfexbondAfterGatePass(
									existingQtyTakenOut.add(totalOut),	
									companyId,
									branchId, firstSavedObject.getExBondingId(), firstSavedObject.getExBondBeNo());
							
							System.out.println("Update row count after gate pass in exbond is____________"+updateExbondCrg);
						}
						
						
						int updateDataAfterBondGatePass=vehicleTrackRepository.updateDataAfterBondGatePass(firstSavedObject.getGatePassId(), user,companyId,branchId,firstSavedObject.getVehGateInId());

						System.out.println("Update row count after gate pass in updateDataAfterBondGatePass___________"+updateDataAfterBondGatePass);
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
							BigDecimal qty = gatePassDtl.getYardQtyTakenOut();

							BigDecimal area = gatePassDtl.getYardAreaReleased();
							
						
						gatePassDtl.setStatus("N");

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
						gatePassDtl.setVehGateInId(gatePass.getVehGateInId());
						gatePassDtl.setInBondingId(item.getInBondingId());
						gatePassDtl.setExBondingId(item.getExBondingId());
						gatePassDtl.setAreaAllocated(item.getAreaAllocated());
						gatePassDtl.setInBondPackages(item.getInBondPackages());
						gatePassDtl.setTransporterName(gatePass.getTransporterName());
						gatePassDtl.setTransporterStatus(gatePass.getTransporterStatus());
						gatePassDtl.setCha(gatePass.getCha());
						gatePassDtl.setDeliveryOrderNo(gatePass.getDeliveryOrderNo());
						gatePassDtl.setDeliveryOrderDate(gatePass.getDeliveryOrderDate());
						gatePassDtl.setDoValidityDate(gatePass.getDoValidityDate());
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
						gatePassDtl.setYardAreaReleased(item.getYardAreaReleased());
						gatePassDtl.setYardQtyTakenOut(item.getYardQtyTakenOut());
						gatePassDtl.setIgmLineNo(item.getIgmLineNo());
						gatePassDtl.setIgmNo(item.getIgmNo());
						saved = cfbondGatePassRepository.save(gatePassDtl);

//						int updateCfExbondcrgDtlAfterGatePass = cfExBondCrgDtlRepository.updateCfexbondDtlAfterGatePass(
//								saved.getQtyTakenOut(), companyId, branchId, saved.getExBondingId(),
//								saved.getExBondBeNo(), saved.getCommodity());
//						
//						System.out.println("Update row count after gate pass in exbond details is in edit ok ____________"+updateCfExbondcrgDtlAfterGatePass);
//						
						savedDtl.add(saved);
						
						if (saved!=null)
						{
							
							CfInBondGrid getExistingData=cfInBondGridRepository.toEditData(companyId, branchId, saved.getInBondingId(), saved.getCommodity(), saved.getYardLocation(),saved.getYardBlock(),saved.getBlockCellNo());
							
							System.out.println("getExistingData_____________________________"+getExistingData);
							if(getExistingData!=null)
							{
								
								
								
								getExistingData.setQtyTakenOut(getExistingData.getQtyTakenOut().add(saved.getYardQtyTakenOut()).subtract(qty));
								getExistingData.setAreaReleased(getExistingData.getAreaReleased().add(saved.getYardAreaReleased()).subtract(area));
								
								cfInBondGridRepository.save(getExistingData);
							}
							
							CfExBondGrid toEditData1=cfExBondGridRepository.toEditData(companyId, branchId, saved.getExBondingId(), saved.getCommodity(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
							System.out.println("toEditData1____________________________"+toEditData1);
							if(toEditData1!=null)
							{
								BigDecimal total =toEditData1.getQtyTakenOut().add(saved.getYardQtyTakenOut()).subtract(qty);
								
								System.out.println("total___________________________ :"+toEditData1.getQtyTakenOut());
								System.out.println("total___________________________ :"+saved.getYardQtyTakenOut());
								System.out.println("total________________exist___________ :"+qty);
								
								
								System.out.println("total___________________"+total);
								int upadteExBondGrid =cfExBondGridRepository.updateExbondGridAfterGatePass(total,
										toEditData1.getAreaReleased().add(saved.getYardAreaReleased()).subtract(area),
												companyId, branchId, saved.getExBondingId(), 
										saved.getCommodity(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
								
								System.out.println("toEditData1_____________________________Row :"+upadteExBondGrid);
								
//								toEditData1.setQtyTakenOut(toEditData1.getQtyTakenOut()!=null ? toEditData1.getQtyTakenOut():BigDecimal.ZERO.add(saved.getQtyTakenOut()).subtract(gatePassDtl.getQtyTakenOut()));
//								toEditData1.setAreaReleased(toEditData1.getAreaReleased()!=null ? toEditData1.getAreaReleased():BigDecimal.ZERO.add(saved.getAreaReleased()).subtract(gatePassDtl.getAreaReleased()));
//								cfExBondGridRepository.save(toEditData1);
							}
							
							YardBlockCell getAllData=  yardBlockCellRepository.getAllData(companyId, branchId, saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
							System.out.println("getAllData____________________________"+getAllData);
							if(getAllData!=null)
							{
								
								BigDecimal total =getAllData.getCellAreaUsed().subtract(saved.getYardAreaReleased()).add(area);
								
								
								int updateYardBlockCell =yardBlockCellRepository.updateYardBlockCellAfterGatePass(total,
										companyId, branchId, saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
								
								System.out.println("getAllData_____________________________updateYardBlockCell :"+updateYardBlockCell);
								
//								getAllData.setCellAreaUsed(getAllData.getCellAreaUsed() !=null ? getAllData.getCellAreaUsed() : BigDecimal.ZERO.subtract(saved.getAreaReleased()!=null ? saved.getAreaReleased() : BigDecimal.ZERO).add(gatePassDtl.getAreaReleased()));
//								
//								yardBlockCellRepository.save(getAllData);
							}
						}
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
							gatePassDtl1.setStatus("N");
							gatePassDtl1.setAreaAllocated(item.getAreaAllocated());
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
							gatePassDtl1.setVehGateInId(gatePass.getVehGateInId());
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
							gatePassDtl1.setDeliveryOrderNo(gatePass.getDeliveryOrderNo());
							gatePassDtl1.setDeliveryOrderDate(gatePass.getDeliveryOrderDate());
							gatePassDtl1.setDoValidityDate(gatePass.getDoValidityDate());
							gatePassDtl1.setYardAreaReleased(item.getYardAreaReleased());
							gatePassDtl1.setYardQtyTakenOut(item.getYardQtyTakenOut());
							gatePassDtl1.setIgmLineNo(item.getIgmLineNo());
							gatePassDtl1.setIgmNo(item.getIgmNo());
							saved = cfbondGatePassRepository.save(gatePassDtl1);

							savedDtl.add(saved);

							if (saved!=null)
							{
								List<CfExBondGrid> existList =cfExBondGridRepository.getDataForBondGatePassAfterExbond(companyId, branchId, saved.getExBondingId(),saved.getCommodity());
								

								System.out.println("existList________________________"+existList);
								
								if(existList!=null)
								{
									
									for (CfExBondGrid data : existList)
									{
										System.out.println("data________________________"+data);
										
										data.setQtyTakenOut(BigDecimal.ZERO);
										data.setAreaReleased(BigDecimal.ZERO);
										
										CfExBondGrid savedGrid =cfExBondGridRepository.save(data);

										
										if (savedGrid!=null)
										{
											CfExBondGrid toEditData1=cfExBondGridRepository.toEditData(companyId, branchId, saved.getExBondingId(), saved.getCommodity(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
											
											System.out.println("toEditData1_____________________________"+toEditData1);
											
											if(toEditData1!=null)
											{
												
												BigDecimal total =toEditData1.getQtyTakenOut().add(saved.getYardQtyTakenOut());
												
												System.out.println("total_________________ :"+total);
												int upadteExBondGrid =cfExBondGridRepository.updateExbondGridAfterGatePass(total,
														toEditData1.getAreaReleased().add(saved.getYardAreaReleased()),
																companyId, branchId, saved.getExBondingId(), 
														saved.getCommodity(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
												
												System.out.println("toEditData1_____________________________Row :"+upadteExBondGrid);
												
//												toEditData1.setQtyTakenOut(toEditData1.getQtyTakenOut()!=null ? toEditData1.getQtyTakenOut():BigDecimal.ZERO.add(saved.getQtyTakenOut()));
//												toEditData1.setAreaReleased(toEditData1.getAreaReleased()!=null ? toEditData1.getAreaReleased():BigDecimal.ZERO.add(saved.getAreaReleased()));
//												cfExBondGridRepository.save(toEditData1);
											}
											
											
											CfInBondGrid getExistingData=cfInBondGridRepository.toEditData(companyId, branchId, saved.getInBondingId(), saved.getCommodity(), saved.getYardLocation(),saved.getYardBlock(),saved.getBlockCellNo());
											System.out.println("getExistingData_____________________________"+getExistingData);
											if(getExistingData!=null)
											{
												
												int upadteExBondGrid =cfInBondGridRepository.updateInbondGridAfterGatePass(getExistingData.getQtyTakenOut().add(saved.getYardQtyTakenOut()), 
														getExistingData.getAreaReleased().add(saved.getYardAreaReleased()),
																companyId, branchId, saved.getInBondingId(), 
														saved.getCommodity(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
												
												System.out.println("getExistingData_____________________________Row :"+upadteExBondGrid);
												
												
//												getExistingData.setQtyTakenOut(getExistingData.getQtyTakenOut()!=null? getExistingData.getQtyTakenOut() :BigDecimal.ZERO.add(saved.getQtyTakenOut()));
//												getExistingData.setAreaReleased(getExistingData.getAreaReleased()!=null ? getExistingData.getAreaReleased():BigDecimal.ZERO.add(saved.getAreaReleased()));
//												
//												cfInBondGridRepository.save(getExistingData);
											}
											
								
											YardBlockCell getAllData=  yardBlockCellRepository.getAllData(companyId, branchId, saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
											System.out.println("getAllData_____________________________"+getAllData);
											if(getAllData!=null)
											{
												
												int updateYardBlockCell =yardBlockCellRepository.updateYardBlockCellAfterGatePass(getAllData.getCellAreaUsed().subtract(saved.getYardAreaReleased()),
														companyId, branchId, saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
												
												System.out.println("getAllData_____________________________updateYardBlockCell :"+updateYardBlockCell);
//												getAllData.setCellAreaUsed(getAllData.getCellAreaUsed() !=null ? getAllData.getCellAreaUsed() : BigDecimal.ZERO.subtract(saved.getAreaReleased()!=null ? saved.getAreaReleased() : BigDecimal.ZERO));
//												yardBlockCellRepository.save(getAllData);
											}
										}
									}
									
								}
							}
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
						
						
						int updateDataAfterBondGatePass=vehicleTrackRepository.updateDataAfterBondGatePass(firstSavedObject.getGatePassId(), user,companyId,branchId,firstSavedObject.getVehGateInId());
						
						System.out.println("Update row count after gate pass in exbond is in edit ok ____________"+updateExbondCrg);
						
						
						
						
						System.out.println("Update row count after gate pass in exbond is in edit updateDataAfterBondGatePass ____________"+updateDataAfterBondGatePass);
					}

				}

			}
		}

		System.out.println("gatePass_______________________" + saved);

		System.out.println("gatePassDtl_______________________" + gatePassDtlList);

		return new ResponseEntity<>(savedDtl, HttpStatus.OK);
	}
	
	
	
	@Transactional
	public ResponseEntity<?> approveDataOfInCFbondGrid(String companyId, String branchId, String flag, String user,
	        Map<String, Object> requestBody) {

	    ObjectMapper object = new ObjectMapper();
	    object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	    CFBondGatePass cfinbondcrg = object.convertValue(requestBody.get("gatePass"), CFBondGatePass.class);
	    
	    System.out.println("cfinbondcrg________________________"+cfinbondcrg.getInBondingId());
	    CFBondGatePass dataForPrint =null;
	    
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
		
		BigDecimal totalSum = BigDecimal.ZERO;

		if(gatePassDtlList != null) {
		    for(CFBondGatePass list : gatePassDtlList) {
		    	
		    	System.out.println("jhsdjhsfjsdjfgdsjfdgsjfh :"+list.getNocTransId());
		    	System.out.println("jhsdjhsfjsdjfgdsjfdgsjfh :"+list.getExBondingId());
		    	
		        BigDecimal sumOfInbondFormGrid = cfExBondGridRepository.getSumOfQtyTakenOutCommodityWise(
		            companyId, branchId, list.getExBondingId(),list.getCommodity()
		        );
		        

		        if (sumOfInbondFormGrid != null) {
		            totalSum = totalSum.add(sumOfInbondFormGrid);
		        }
		    }
		}

		System.out.println("Total Sum: " + totalSum);
		
		
	    
	    if (cfinbondcrg.getGatePassId()==null || cfinbondcrg.getGatePassId().isEmpty() || cfinbondcrg.getGatePassId().isBlank())
	    {
	    	return new ResponseEntity<>("Please First Save Data", HttpStatus.BAD_REQUEST);
	    }
	    
	    if (cfinbondcrg != null && cfinbondcrg.getGatePassId()!=null || !cfinbondcrg.getGatePassId().isEmpty() || !cfinbondcrg.getGatePassId().isBlank()) 	    	
	    {

	        BigDecimal sumOfInbondFromDtl = cfbondGatePassRepository.getSumOfQtyTakenOut(companyId, branchId,
	                cfinbondcrg.getGatePassId());


	        BigDecimal sumOfInbondFormGrid = cfExBondGridRepository.getSumOfQtyTakenOut(companyId, branchId,
	                cfinbondcrg.getExBondingId(), cfinbondcrg.getNocTransId());

	        System.out.println("sumOfInbondFromDtl: " + sumOfInbondFromDtl + " ______________ " + totalSum);
	        
	        if (sumOfInbondFromDtl == null || totalSum == null) {
	            return new ResponseEntity<>("One of the sum values is null. Please check the data.", HttpStatus.BAD_REQUEST);
	        }

	        if (totalSum.compareTo(sumOfInbondFromDtl) != 0) {
	            return new ResponseEntity<>("Qty Taken Out do not match in yard, please add packages in grid.", HttpStatus.BAD_REQUEST);
	        }
	        else
	        {
	        	 int updateAfterApprov =cfExBondCrgDtlRepository.updateAfterApprove("A", companyId, branchId, cfinbondcrg.getExBondingId(), cfinbondcrg.getNocNo(), cfinbondcrg.getNocTransId());
	        	 System.out.println("updateAfterApprov row count "+updateAfterApprov);
	        	 
	        	 
	        	
//	        	 List<CFBondGatePass> updateAfterApprove =cfbondGatePassRepository.updateAfterApprove(companyId, branchId, cfinbondcrg.getGatePassId(), cfinbondcrg.getInBondingId(), cfinbondcrg.getExBondingId());
	        	 List<CFBondGatePass> updateAfterApprove =cfbondGatePassRepository.updateAfterApprove(companyId, branchId, cfinbondcrg.getGatePassId());
	        	 if (updateAfterApprove!=null)
	        	 {
	     				// Process the firstResul
	     			
	        		 updateAfterApprove.forEach(data -> data.setStatus("A"));
	        		    // Batch save all records in one go
	        		 cfbondGatePassRepository.saveAll(updateAfterApprove);
	        		 
	        		 dataForPrint = updateAfterApprove.get(0);
	        		 
	        		 
	        		 System.out.println("dataForPrint_____________________"+dataForPrint);
	        		 return new ResponseEntity<>(dataForPrint, HttpStatus.OK);
	        	 }
	        }
	    }
	   
	    return new ResponseEntity<>(dataForPrint, HttpStatus.OK);
	}
	 public BigDecimal getSumOfQtyTakenOutForCommodity(String companyId, String branchId, String exBondingId,String gatePassId, String cfBondDtlId) {
	        return cfbondGatePassRepository.getSumOfQtyTakenOutForCommodity(companyId, branchId, exBondingId,gatePassId, cfBondDtlId);
	    }
	
	public List<CFBondGatePass> findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(String companyId, String branchId, String partyName) {
        return cfbondGatePassRepository.findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(companyId, branchId, partyName);
    }
	

	 public List<CFBondGatePass> getAllListOfGatePass(String companyId, String branchId, String gatePassId) {
	        return cfbondGatePassRepository.getAllListOfGatePass(companyId, branchId, gatePassId);
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
	 
	 public List<Object[]> getEmptyVehiclesForGatePass(String companyId, String branchId, String vehicleNo) {
	        return vehicleTrackRepository.getEmptyVehGateInForGatePass(companyId, branchId, vehicleNo);
	    }

	 
	 

		public ResponseEntity<String> printOfSurveyDetails( String companyId,
				String branchId,  String username,
				 String type,String companyname,
				 String branchname,  String gatePassId) throws DocumentException {
			
			Context context = new Context();

			// Cfbondnoc dataForPrint
			// =cfbondnocRepository.findCfbondnocByCompanyIdAndBranchIdAndNocTransIdAndNocNo(companyId,
			// branchId, nocTransId, nocNo);

			CFBondGatePass dataForPrint = null;

			List<CFBondGatePass> result = cfbondGatePassRepository.getDataForBondGateOutPass(companyId,
					branchId, gatePassId);
			if (!result.isEmpty()) {
				dataForPrint = result.get(0);
				// Process the firstResult
			}
			
			
			BigDecimal totalQtyTakenOut = result.stream()
				    .map(CFBondGatePass::getQtyTakenOut)           // Extract qtyTakenOut from each object
				    .filter(qty -> qty != null)               // Filter out null values
				    .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum the non-null values

				System.out.println("Total Qty Taken Out: " + totalQtyTakenOut);
				
				StringBuilder exBondBeNosBuilder = new StringBuilder();

				result.stream()
				    .map(CFBondGatePass::getExBondBeNo)  // Extract exBondBeNo from each object
				    .filter(Objects::nonNull)             // Filter out null values
				    .forEach(exBondBeNo -> {
				        if (exBondBeNosBuilder.length() > 0) {
				            exBondBeNosBuilder.append(","); // Add comma before the next value if it's not the first one
				        }
				        exBondBeNosBuilder.append(exBondBeNo); // Append the exBondBeNo
				    });

				
				// StringBuilder for boeNo, bondNo, and igmNo
				StringBuilder boeNosBuilder = new StringBuilder();
				StringBuilder bondNosBuilder = new StringBuilder();
				StringBuilder igmNosBuilder = new StringBuilder();

				// Variable for sum of inBondedPackages
			

				
				
				// Stream through the result list

				 BigDecimal totalInBondedPackages =BigDecimal.ZERO;
					for (CFBondGatePass item1 :result)
					{
						if (item1.getBoeNo() != null) {
					        if (boeNosBuilder.length() > 0) {
					            boeNosBuilder.append(","); // Add comma before the next value if it's not the first one
					        }
					        boeNosBuilder.append(item1.getBoeNo()); // Append boeNo
					    }
					    
					    // Collect bondNo
					    if (item1.getBondingNo() != null) {
					        if (bondNosBuilder.length() > 0) {
					            bondNosBuilder.append(","); // Add comma before the next value if it's not the first one
					        }
					        bondNosBuilder.append(item1.getBondingNo()); // Append bondNo
					    }
					    
					    // Collect igmNo
					    if (item1.getIgmNo() != null) {
					        if (igmNosBuilder.length() > 0) {
					            igmNosBuilder.append(","); // Add comma before the next value if it's not the first one
					        }
					        igmNosBuilder.append(item1.getIgmNo()); // Append igmNo
					    }
					     
					    // Sum the inBondedPackages
					    if (item1.getInBondPackages() != null) {
					    	
					    	
					        totalInBondedPackages = totalInBondedPackages.add(item1.getInBondPackages());
					        
					        System.out.println("Total In-Bonded Packages: " + totalInBondedPackages);
					    }
					}
				    // Collect boeNo
				// Print the results
				System.out.println("BOE Nos: " + boeNosBuilder.toString());
				System.out.println("Bond Nos: " + bondNosBuilder.toString());
				System.out.println("IGM Nos: " + igmNosBuilder.toString());
				

				System.out.println("Total In-Bonded Packages: " + totalInBondedPackages);
				String exBondBeNos = exBondBeNosBuilder.toString();
				System.out.println("Ex Bond Be Nos: " + exBondBeNos);
				

			System.out.println("gatePassdata____________________________________________");
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
			context.setVariable("gatePassId", dataForPrint.getGatePassId());
			context.setVariable("gatePassDate", dataForPrint.getGatePassDate());
			context.setVariable("ExBondboeNo", exBondBeNos);
//			context.setVariable("boeNo", dataForPrint.getBoeNo());
			context.setVariable("boeNo", boeNosBuilder.toString());
			context.setVariable("boeDate", dataForPrint.getInBondingDate());
			context.setVariable("exBondBoeDate", dataForPrint.getExBondBeDate());
//			context.setVariable("igmNo", dataForPrint.getIgmNo());
			
			context.setVariable("igmNo", igmNosBuilder.toString());
			
//			context.setVariable("itemNo", dataForPrint.getIgmLineNoi());
			context.setVariable("igmLineNo", dataForPrint.getIgmLineNo());
//			context.setVariable("bondingNo", dataForPrint.getBondingNo());
			context.setVariable("bondingNo", bondNosBuilder.toString());			
			context.setVariable("bondingDate", dataForPrint.getBondingDate());
//			context.setVariable("inBondPkgs", dataForPrint.getInBondPackages());
			
			context.setVariable("inBondPkgs", totalInBondedPackages);
			context.setVariable("inBondGrWeight", dataForPrint.getInBondedGw());
			
			context.setVariable("exBondGrWeight", dataForPrint.getExBondedGw());
			context.setVariable("consignee", dataForPrint.getImporterName());
			
			context.setVariable("typeOfPackages", dataForPrint.getTypeOfPackage());
			context.setVariable("cha", dataForPrint.getCha());
			context.setVariable("address", dataForPrint.getImporterAddress1() + " " + dataForPrint.getImporterAddress2()
					+ " " + dataForPrint.getImporterAddress3());
			
			context.setVariable("cargoDiscrpition", dataForPrint.getCommodityDescription());
			context.setVariable("exBondPkgs", totalQtyTakenOut);
			context.setVariable("trasporterName", dataForPrint.getTransporterName());
		
//				context.setVariable("noOfTw", dataForPrint.getNoOf20ft());
	
			context.setVariable("cargoDesc", dataForPrint.getCommodityDescription());
		
			context.setVariable("result", result);
			context.setVariable("c1", c1);
			context.setVariable("b1", b1);
			context.setVariable("u1", u1);
			context.setVariable("companyAdd", companyAdd);
			context.setVariable("branchAdd", branchAdd);
			context.setVariable("bondCode", bondCode);
			context.setVariable("city", city);

//				context.setVariable("gatePassdata", gatePassdata);

			String htmlContent = templateEngine.process("BondGatePass", context);

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
