package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.Company;
import com.cwms.entities.GateOut;
import com.cwms.entities.GeneralDeliveryCrg;
import com.cwms.entities.GeneralDeliveryDetails;
import com.cwms.entities.GeneralDeliveryGrid;
import com.cwms.entities.GeneralGateIn;
import com.cwms.entities.GeneralGatePassCargo;
import com.cwms.entities.GeneralGatePassGrid;
import com.cwms.entities.GeneralReceivingCrg;
import com.cwms.entities.GeneralReceivingGrid;
import com.cwms.entities.GenerelJobEntry;
import com.cwms.entities.JarDetail;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.GeneralDeliveryCrgRepo;
import com.cwms.repository.GeneralDeliveryDetailsRepo;
import com.cwms.repository.GeneralDeliveryGridRepo;
import com.cwms.repository.GeneralGatePassGridRepo;
import com.cwms.repository.GeneralGatePassRepository;
import com.cwms.repository.GeneralReceivingGateInDtlRepo;
import com.cwms.repository.GeneralReceivingGridRepo;
import com.cwms.repository.JarDetailRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.YardBlockCellRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@Service
public class GeneralGatePassService {

	@Autowired
	private GeneralGatePassRepository generalGatePassRepository;

	@Autowired
	private GeneralDeliveryDetailsRepo generalDeliveryDetailsRepo;

	@Autowired
	private ProcessNextIdRepository processNextIdRepository;

	@Autowired
	private GeneralDeliveryGridRepo generalDeliveryGridRepo;

	@Autowired
	private GeneralDeliveryCrgRepo generalDeliveryCrgRepo;

	@Autowired
	public YardBlockCellRepository yardBlockCellRepository;

	@Autowired
	private GeneralReceivingGateInDtlRepo generalReceivingGateInDtlRepo;

	@Autowired
	private GeneralReceivingGridRepo generalReceivingGridRepo;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private GeneralGatePassGridRepo generalGatePassGridRepo;
	
	@Autowired
	JarDetailRepository jarDetailRepository;

	public List<Object[]> getAllDataFormDeliveryDetails(String cid, String bid, String val) {
		return generalGatePassRepository.getAllDataFormDeliveryDetails(cid, bid, val);
	}

	public List<Object[]> getDataDeliveryCargo(String cid, String bid, String exbondBeNo, String val) {
		return generalGatePassRepository.generalGatePassRepository(cid, bid, exbondBeNo, val);
	}

	public ResponseEntity<List<GeneralDeliveryGrid>> getDataAfterSave(String companyId, String branchId,
			String exBondingId, String cfBondDtlId) {
		List<GeneralDeliveryGrid> list = generalDeliveryDetailsRepo.getDataAfterSave(companyId, branchId, exBondingId,
				cfBondDtlId);

		return new ResponseEntity<List<GeneralDeliveryGrid>>(list, HttpStatus.OK);
	}

	public List<GeneralGatePassCargo> findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(String companyId,
			String branchId, String partyName) {
		return generalGatePassRepository.findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(companyId, branchId,
				partyName);
	}

	public List<GeneralGatePassCargo> getAllListOfGatePass(String companyId, String branchId, String gatePassId) {
		return generalGatePassRepository.getAllListOfGatePass(companyId, branchId, gatePassId);
	}

	@SuppressWarnings("unused")
	@Transactional
	public ResponseEntity<?> saveDataOfGatePassAndGatePassDtl(String companyId, String branchId, String user,
			String flag, Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		System.out.println("requestBody_________________________" + requestBody);

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		GeneralGatePassCargo gatePass = object.convertValue(requestBody.get("gatePass"), GeneralGatePassCargo.class);

		Object nocDtlObj = requestBody.get("dtl");
		List<GeneralGatePassCargo> gatePassDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			gatePassDtlList = object.convertValue(nocDtlObj, new TypeReference<List<GeneralGatePassCargo>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				GeneralGatePassCargo gatePassDtl = object.convertValue(entry.getValue(), GeneralGatePassCargo.class);
				gatePassDtlList.add(gatePassDtl);
			}
		} else {
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		System.out.println("gatePassDtlList________________" + gatePassDtlList);

		System.out.println("flag________________" + flag);

		GeneralGatePassCargo saved = null;

		List<GeneralGatePassCargo> savedDtl = new ArrayList<>();
		if (gatePass != null) {
			if ("add".equals(flag)) {

				if (gatePassDtlList != null) {
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "PGG414", "2025");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectExBondingId = String.format("GGPN%06d", nextNumericNextID1);

					int sr = 1;

					for (GeneralGatePassCargo item : gatePassDtlList) {

						GeneralGatePassCargo gatePassDtl = new GeneralGatePassCargo();

						List<String> importerNames = generalGatePassRepository.getImporterName(companyId, branchId,
								item.getDeliveryId(), item.getReceivingId());

						System.out.println("importerNames   " + importerNames);

						System.out.println("importerNames   " + importerNames.get(0));

						if (!importerNames.isEmpty()) {
							gatePassDtl.setImporterName(importerNames.get(0));
						} else {
							gatePassDtl.setImporterName(null);
						}

						gatePassDtl.setCompanyId(companyId);
						gatePassDtl.setBranchId(branchId);
						gatePassDtl.setCreatedBy(user);
						gatePassDtl.setCreatedDate(new Date());
						gatePassDtl.setApprovedBy(user);
						gatePassDtl.setApprovedDate(new Date());
						gatePassDtl.setStatus("N");
						gatePassDtl.setComments(gatePass.getComments());
						gatePassDtl.setGatePassId(nectExBondingId);
						gatePassDtl.setGatePassDate(gatePass.getGatePassDate());
						gatePassDtl.setShift(gatePass.getShift());
						gatePassDtl.setProfitCentreId(gatePass.getProfitCentreId());

						gatePassDtl.setHandlingPerson(gatePass.getHandlingPerson());
						gatePassDtl.setVehicleNo(gatePass.getVehicleNo());
						gatePassDtl.setDriverName(gatePass.getDriverName());
						gatePassDtl.setDepositNo(item.getDepositNo());
						gatePassDtl.setTransporterName(gatePass.getTransporterName());
						gatePassDtl.setComments(gatePass.getComments());

						gatePassDtl.setReceivingId(item.getReceivingId());
						gatePassDtl.setReceivingPackages(item.getReceivingPackages());
						gatePassDtl.setReceivingWeight(item.getReceivingWeight());

						gatePassDtl.setActCommodityId(item.getActCommodityId());
						gatePassDtl.setTypeOfPackage(item.getTypeOfPackage());
						gatePassDtl.setDeliveredPackages(item.getDeliveredPackages());
						gatePassDtl.setDeliveryId(item.getDeliveryId());
						gatePassDtl.setDeliveredWeight(item.getDeliveredWeight());
						gatePassDtl.setTransporterName(gatePass.getTransporterName());
						gatePassDtl.setContainerNo(item.getContainerNo());

						gatePassDtl.setTransType(gatePass.getTransType());
						gatePassDtl.setJobTransId(item.getJobTransId());
						gatePassDtl.setJobNo(item.getJobNo());

						gatePassDtl.setBoeNo(item.getBoeNo());
						gatePassDtl.setBoeDate(item.getBoeDate());

						gatePassDtl.setCommodityId(item.getCommodityId());

						gatePassDtl.setCommodityDescription(item.getCommodityDescription());
						gatePassDtl.setSrNo(sr);

						gatePassDtl.setGatePassPackages(item.getGatePassPackages());
						gatePassDtl.setGatePassWeight(item.getGatePassWeight());

						saved = generalGatePassRepository.save(gatePassDtl);

						savedDtl.add(saved);
						sr++;
						if (saved != null) {
							GeneralDeliveryDetails existing = generalDeliveryDetailsRepo.getAfterSaveForEdit(companyId,
									branchId, saved.getDeliveryId(), saved.getReceivingId(), saved.getDepositNo(),
									saved.getCommodityId());

							System.out.println("existing" + existing);

							if (existing != null) {
								existing.setGatePassPackages(
										existing.getGatePassPackages().add(saved.getGatePassPackages()));
								existing.setGatePassWeight(existing.getGatePassWeight().add(saved.getGatePassWeight()));

								generalDeliveryDetailsRepo.save(existing);

								System.out.println("Saved after upadte :" + generalDeliveryDetailsRepo.save(existing));
							}

						}
					}
					if (savedDtl.size() > 0)

					{
						GeneralGatePassCargo firstSavedObject = savedDtl.get(0);

						BigDecimal totalOut = BigDecimal.ZERO;
						for (GeneralGatePassCargo savedObject : savedDtl) {

							totalOut = totalOut.add(savedObject.getGatePassPackages());
						}

						GeneralDeliveryCrg findExistingCfexbondCrg = generalDeliveryCrgRepo.findSavedGeneralDelivery(
								companyId, branchId, firstSavedObject.getDeliveryId(), firstSavedObject.getDepositNo(),
								firstSavedObject.getReceivingId());

						if (findExistingCfexbondCrg != null) {
							BigDecimal existingQtyTakenOut = Optional
									.ofNullable(findExistingCfexbondCrg.getQtyTakenOut()).orElse(BigDecimal.ZERO);

							findExistingCfexbondCrg.setQtyTakenOut(existingQtyTakenOut.add(totalOut));

							generalDeliveryCrgRepo.save(findExistingCfexbondCrg);
							System.out.println("Update row count after gate pass in delivery crg is____________"
									+ findExistingCfexbondCrg);
						}

//						int updateDataAfterBondGatePass=vehicleTrackRepository.updateDataAfterBondGatePass(firstSavedObject.getGatePassId(), user,companyId,branchId,firstSavedObject.getVehGateInId());

//						System.out.println("Update row count after gate pass in updateDataAfterBondGatePass___________"+updateDataAfterBondGatePass);
					}

					processNextIdRepository.updateAuditTrail(companyId, branchId, "PGG414", nectExBondingId, "2025");
				}

			}

			else

			{
				System.out.println("In Edit loop ");

				if (gatePassDtlList != null) {
					int sr = 1;

					BigDecimal totalGateInPackages = gatePassDtlList.stream()
							.map(item -> generalGatePassRepository.toUpdateGatePassCargo(companyId, branchId,
									gatePass.getGatePassId(), item.getCommodityId(), item.getSrNo()))
							.filter(Objects::nonNull) // Ensure no null objects
							.map(GeneralGatePassCargo::getGatePassPackages) // Extract `gatePassPackages`
							.filter(Objects::nonNull) // Ensure no null values
							.reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up all packages

//						System.out.println("Total Gate In Packages: " + totalGateInPackages);

					for (GeneralGatePassCargo item : gatePassDtlList) {
						GeneralGatePassCargo gatePassDtl = generalGatePassRepository.toUpdateGatePassCargo(companyId,
								branchId, gatePass.getGatePassId(), item.getCommodityId(), item.getSrNo());

						System.out.println("gate pass packageseeee " + gatePassDtl.getGatePassPackages());
						if (gatePassDtl != null) {

							List<String> importerNames = generalGatePassRepository.getImporterName(companyId, branchId,
									item.getDeliveryId(), item.getReceivingId());

							if (!importerNames.isEmpty()) {
								gatePassDtl.setImporterName(importerNames.get(0));
							} else {
								gatePassDtl.setImporterName(null);
							}

							gatePassDtl.setStatus("N");

							gatePassDtl.setEditedBy(user);
							gatePassDtl.setEditedDate(new Date());
							gatePassDtl.setComments(gatePass.getComments());
							gatePassDtl.setGatePassDate(gatePass.getGatePassDate());
							gatePassDtl.setShift(gatePass.getShift());
							gatePassDtl.setProfitCentreId(gatePass.getProfitCentreId());
							gatePassDtl.setHandlingPerson(gatePass.getHandlingPerson());
							gatePassDtl.setVehicleNo(gatePass.getVehicleNo());
							gatePassDtl.setDriverName(gatePass.getDriverName());
							gatePassDtl.setTransporterName(gatePass.getTransporterName());
							gatePassDtl.setComments(gatePass.getComments());

							gatePassDtl.setReceivingId(item.getReceivingId());
							gatePassDtl.setReceivingPackages(item.getReceivingPackages());
							gatePassDtl.setReceivingWeight(item.getReceivingWeight());

							gatePassDtl.setActCommodityId(item.getActCommodityId());
							gatePassDtl.setTypeOfPackage(item.getTypeOfPackage());
							gatePassDtl.setDeliveredPackages(item.getDeliveredPackages());
							gatePassDtl.setDeliveryId(item.getDeliveryId());
							gatePassDtl.setDeliveredWeight(item.getDeliveredWeight());
							gatePassDtl.setTransporterName(gatePass.getTransporterName());
							gatePassDtl.setContainerNo(item.getContainerNo());

							gatePassDtl.setTransType(gatePass.getTransType());
							gatePassDtl.setJobTransId(item.getJobTransId());
							gatePassDtl.setJobNo(item.getJobNo());

							gatePassDtl.setBoeNo(item.getBoeNo());
							gatePassDtl.setBoeDate(item.getBoeDate());

							gatePassDtl.setCommodityId(item.getCommodityId());

							gatePassDtl.setCommodityDescription(item.getCommodityDescription());

							GeneralDeliveryDetails existing = generalDeliveryDetailsRepo.getAfterSaveForEdit(companyId,
									branchId, item.getDeliveryId(), item.getReceivingId(), item.getDepositNo(),
									item.getCommodityId());

							if (existing != null) {
								existing.setGatePassPackages(existing.getGatePassPackages()
										.add(item.getGatePassPackages()).subtract(gatePassDtl.getGatePassPackages()));
								existing.setGatePassWeight(existing.getGatePassWeight().add(item.getGatePassWeight())
										.subtract(gatePassDtl.getGatePassWeight()));

								generalDeliveryDetailsRepo.save(existing);

								System.out.println("Saved after upadte :" + generalDeliveryDetailsRepo.save(existing));
							}

							gatePassDtl.setGatePassPackages(item.getGatePassPackages());
							gatePassDtl.setGatePassWeight(item.getGatePassWeight());

							saved = generalGatePassRepository.save(gatePassDtl);

							savedDtl.add(saved);

						}

						else {
							GeneralGatePassCargo gatePassDtl1 = new GeneralGatePassCargo();

							List<String> importerNames = generalGatePassRepository.getImporterName(companyId, branchId,
									item.getDeliveryId(), item.getReceivingId());

							if (!importerNames.isEmpty()) {
								gatePassDtl1.setImporterName(importerNames.get(0));
							} else {
								gatePassDtl1.setImporterName(null);
							}

							gatePassDtl1.setCompanyId(companyId);
							gatePassDtl1.setBranchId(branchId);
							gatePassDtl1.setCreatedBy(user);
							gatePassDtl1.setCreatedDate(new Date());
							gatePassDtl1.setApprovedBy(user);
							gatePassDtl1.setApprovedDate(new Date());
							gatePassDtl1.setStatus("N");
							gatePassDtl1.setComments(gatePass.getComments());
							gatePassDtl1.setGatePassId(gatePass.getGatePassId());
							gatePassDtl1.setGatePassDate(gatePass.getGatePassDate());
							gatePassDtl1.setShift(gatePass.getShift());
							gatePassDtl1.setProfitCentreId(gatePass.getProfitCentreId());
							gatePassDtl1.setHandlingPerson(gatePass.getHandlingPerson());
							gatePassDtl1.setVehicleNo(gatePass.getVehicleNo());
							gatePassDtl1.setDriverName(gatePass.getDriverName());
							gatePassDtl1.setDepositNo(item.getDepositNo());
							gatePassDtl1.setTransporterName(gatePass.getTransporterName());
							gatePassDtl1.setComments(gatePass.getComments());

							gatePassDtl1.setReceivingId(item.getReceivingId());
							gatePassDtl1.setReceivingPackages(item.getReceivingPackages());
							gatePassDtl1.setReceivingWeight(item.getReceivingWeight());

							gatePassDtl1.setActCommodityId(item.getActCommodityId());
							gatePassDtl1.setTypeOfPackage(item.getTypeOfPackage());
							gatePassDtl1.setDeliveredPackages(item.getDeliveredPackages());
							gatePassDtl1.setDeliveryId(item.getDeliveryId());
							gatePassDtl1.setDeliveredWeight(item.getDeliveredWeight());
							gatePassDtl1.setTransporterName(gatePass.getTransporterName());
							gatePassDtl1.setContainerNo(item.getContainerNo());

							gatePassDtl1.setTransType(gatePass.getTransType());
							gatePassDtl1.setJobTransId(item.getJobTransId());
							gatePassDtl1.setJobNo(item.getJobNo());

							gatePassDtl1.setBoeNo(item.getBoeNo());
							gatePassDtl1.setBoeDate(item.getBoeDate());

							gatePassDtl1.setCommodityId(item.getCommodityId());

							gatePassDtl1.setCommodityDescription(item.getCommodityDescription());
							gatePassDtl1.setSrNo(sr);

							gatePassDtl1.setGatePassPackages(item.getGatePassPackages());
							gatePassDtl1.setGatePassWeight(item.getGatePassWeight());

							saved = generalGatePassRepository.save(gatePassDtl1);

							savedDtl.add(saved);

							if (saved != null) {

								GeneralDeliveryDetails existing = generalDeliveryDetailsRepo.getAfterSaveForEdit(
										companyId, branchId, saved.getDeliveryId(), saved.getReceivingId(),
										saved.getDepositNo(), saved.getCommodityId());

								System.out.println("existing" + existing);

								if (existing != null) {
									existing.setGatePassPackages(
											existing.getGatePassPackages().add(saved.getGatePassPackages()));
									existing.setGatePassWeight(
											existing.getGatePassWeight().add(saved.getGatePassWeight()));

									generalDeliveryDetailsRepo.save(existing);

									System.out.println(
											"Saved after upadte :" + generalDeliveryDetailsRepo.save(existing));
								}

							}
						}
					}

					if (savedDtl.size() > 0)

					{

						GeneralGatePassCargo firstSavedObject = savedDtl.get(0);

						BigDecimal totalOut = BigDecimal.ZERO;
						for (GeneralGatePassCargo savedObject : savedDtl) {

							totalOut = totalOut.add(savedObject.getGatePassPackages());
						}

						GeneralDeliveryCrg findExistingCfexbondCrg = generalDeliveryCrgRepo.findSavedGeneralDelivery(
								companyId, branchId, firstSavedObject.getDeliveryId(), firstSavedObject.getDepositNo(),
								firstSavedObject.getReceivingId());

						if (findExistingCfexbondCrg != null) {
							BigDecimal existingQtyTakenOut = Optional
									.ofNullable(findExistingCfexbondCrg.getQtyTakenOut()).orElse(BigDecimal.ZERO);

							findExistingCfexbondCrg
									.setQtyTakenOut(existingQtyTakenOut.add(totalOut).subtract(totalGateInPackages));

							generalDeliveryCrgRepo.save(findExistingCfexbondCrg);
							System.out.println("Update row count after gate pass in delivery crg is____________"
									+ findExistingCfexbondCrg);
						}

//						int updateDataAfterBondGatePass=vehicleTrackRepository.updateDataAfterBondGatePass(firstSavedObject.getGatePassId(), user,companyId,branchId,firstSavedObject.getVehGateInId());

					}

				}

			}
		}

//		System.out.println("gatePass_______________________" + saved);

//		System.out.println("gatePassDtl_______________________" + gatePassDtlList);

		return new ResponseEntity<>(savedDtl, HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<?> updateDataInExbondGridAfterBondGatePass(String companyId, String branchId, String flag,
			String user, Map<String, Object> dataToSave) {

		System.out.println("flag________________________________" + flag);

		List<GeneralDeliveryGrid> existingData = null;

		List<GeneralGatePassGrid> savedList = new ArrayList<>();

		GeneralGatePassGrid saved = null;

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		GeneralGatePassGrid gatePass = objectMapper.convertValue(dataToSave.get("modal"), GeneralGatePassGrid.class);

		Object nocDtlObj = dataToSave.get("grid");
		List<GeneralGatePassGrid> cfinbondcrgDtlList = new ArrayList<>();

		if (nocDtlObj instanceof List) {
			cfinbondcrgDtlList = objectMapper.convertValue(nocDtlObj, new TypeReference<List<GeneralGatePassGrid>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				GeneralGatePassGrid cfinbondcrgDtl = objectMapper.convertValue(entry.getValue(),
						GeneralGatePassGrid.class);
				cfinbondcrgDtlList.add(cfinbondcrgDtl);
			}
		} else {
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		if (cfinbondcrgDtlList != null) {
			int sr = 1;

			if("add".equals(flag))
			{
				
				for (GeneralGatePassGrid grid : cfinbondcrgDtlList) {
				

					GeneralGatePassGrid data = new GeneralGatePassGrid();
					System.out.println("Grid please check each cloumn :" + grid);

					data.setDeliveryId(grid.getDeliveryId());
					data.setGatePassId(gatePass.getGatePassId());
					data.setBoeNo(grid.getBoeNo());
					data.setSrNo(sr);
					data.setYardLocation(grid.getYardLocation());
					data.setYardBlock(grid.getYardBlock());
					data.setBlockCellNo(grid.getBlockCellNo());
					data.setCompanyId(companyId);
					data.setBranchId(branchId);
					data.setReceivingId(grid.getReceivingId());
					data.setCellAreaAllocated(grid.getCellAreaAllocated());
					data.setCellAreaReleased(grid.getCellAreaReleased());
					data.setDeliveryPkgs(grid.getDeliveryPkgs());
					data.setStatus("A");
					data.setCreatedBy(user);
					data.setApprovedBy(user);
					data.setCreatedDate(new Date());
					data.setApprovedDate(new Date());
					data.setQtyTakenOut(grid.getQtyTakenOut());
					data.setWeightTakenOut(grid.getWeightTakenOut());
					data.setGateCellAreaReleased(grid.getGateCellAreaReleased());

					data.setReceivedPackages(grid.getReceivedPackages());

					saved = generalGatePassGridRepo.save(data);
					savedList.add(saved);
					sr++;
					
					if (saved != null) {
						
						GeneralDeliveryGrid cf = generalDeliveryDetailsRepo.getSavedGrid(companyId, branchId,
								saved.getDeliveryId(), saved.getReceivingId(), saved.getYardLocation(), saved.getYardBlock(),
								saved.getBlockCellNo());

						if (cf != null) {

							BigDecimal exitsQty = cf.getQtyTakenOut() != null ? cf.getQtyTakenOut() : BigDecimal.ZERO;
							BigDecimal existArea = cf.getGateCellAreaReleased() != null ? cf.getGateCellAreaReleased()
									: BigDecimal.ZERO;

							BigDecimal qtyOut = saved.getQtyTakenOut() != null ? saved.getQtyTakenOut() : BigDecimal.ZERO;

							BigDecimal gateAreaReleased = saved.getGateCellAreaReleased() != null
									? saved.getGateCellAreaReleased()
									: BigDecimal.ZERO;

							BigDecimal exitsAreaReleased = cf.getCellAreaReleased() != null ? cf.getCellAreaReleased()
									: BigDecimal.ZERO;

							System.out.println("exitsQty______________________" + exitsQty);

							cf.setQtyTakenOut(qtyOut.add(exitsQty));

							cf.setStatus("A");
							cf.setEditedBy(user);
							cf.setEditedDate(new Date());

//									if (saved != null) {

							YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
									saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());

							if (existingCell != null) {
								BigDecimal existingCellAreaUsed = existingCell.getCellAreaUsed() != null
										? existingCell.getCellAreaUsed()
										: BigDecimal.ZERO;
								BigDecimal gridCellAreaReleased = saved.getGateCellAreaReleased() != null
										? saved.getGateCellAreaReleased()
										: BigDecimal.ZERO;

								if (existingCell.getCellAreaUsed().compareTo(BigDecimal.ZERO) == 0) {
									existingCell.setCellAreaUsed(gridCellAreaReleased); // Set to released area if it's zero
																						// or null
								} else {
									existingCell.setCellAreaUsed(existingCellAreaUsed.subtract(gridCellAreaReleased)); // Otherwise,
																														// subtract
								}

//										    existingCell.setCellAreaUsed(existingCellAreaUsed.subtract(gridCellAreaReleased));
								yardBlockCellRepository.save(existingCell);
							}

							GeneralReceivingGrid getExistingData = generalReceivingGateInDtlRepo
									.getSavedGeneralReceivingGrid(companyId, branchId, saved.getReceivingId(),
											saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());

							if (getExistingData != null) {
								BigDecimal existingQtyTakenOut = getExistingData.getQtyTakenOut() != null
										? getExistingData.getQtyTakenOut()
										: BigDecimal.ZERO;
								BigDecimal savedQtyTakenOut = saved.getQtyTakenOut() != null ? saved.getQtyTakenOut()
										: BigDecimal.ZERO;

								getExistingData
										.setQtyTakenOut(existingQtyTakenOut.add(savedQtyTakenOut).subtract(exitsQty));

								BigDecimal existingAreaReleased = getExistingData.getAreaReleased() != null
										? getExistingData.getAreaReleased()
										: BigDecimal.ZERO;
								BigDecimal savedAreaReleased = saved.getGateCellAreaReleased() != null
										? saved.getGateCellAreaReleased()
										: BigDecimal.ZERO;

								getExistingData.setAreaReleased(existingAreaReleased.add(savedAreaReleased));

								if (getExistingData.getCellAreaUsed().compareTo(BigDecimal.ZERO) == 0) {
									getExistingData.setCellAreaUsed(savedAreaReleased); // Set to released area if it's zero
																						// or null
								} else {
									getExistingData
											.setCellAreaUsed(getExistingData.getCellAreaUsed().subtract(savedAreaReleased));
								}

								if (getExistingData.getCellAreaAllocated().compareTo(BigDecimal.ZERO) == 0) {
									getExistingData.setCellAreaAllocated(savedAreaReleased); // Set to released area if it's
																								// zero or null
								} else {
									getExistingData.setCellAreaAllocated(
											getExistingData.getCellAreaAllocated().subtract(savedAreaReleased));
								}

								generalReceivingGridRepo.save(getExistingData);

								cf.setCellAreaReleased(exitsAreaReleased.add(saved.getGateCellAreaReleased()));
								cf.setGateCellAreaReleased(existArea.add(gateAreaReleased));

								generalDeliveryGridRepo.save(cf);

							}

						}

					}


				}
			}
			else 
			{
				for (GeneralGatePassGrid grid : cfinbondcrgDtlList) {
				
				GeneralGatePassGrid update = generalGatePassGridRepo.getToUpdateData(companyId, branchId, grid.getDeliveryId(),gatePass.getGatePassId(),grid.getSrNo());
				
				
				if(update!=null)
				{
					
					
					

					
					GeneralDeliveryGrid cf = generalDeliveryDetailsRepo.getSavedGrid(companyId, branchId,
							grid.getDeliveryId(), grid.getReceivingId(), grid.getYardLocation(), grid.getYardBlock(),
							grid.getBlockCellNo());

					if (cf != null) {

						BigDecimal exitsQty = cf.getQtyTakenOut() != null ? cf.getQtyTakenOut() : BigDecimal.ZERO;
						BigDecimal existArea = cf.getGateCellAreaReleased() != null ? cf.getGateCellAreaReleased()
								: BigDecimal.ZERO;

						BigDecimal qtyOut = grid.getQtyTakenOut() != null ? grid.getQtyTakenOut() : BigDecimal.ZERO;

						BigDecimal gateAreaReleased = grid.getGateCellAreaReleased() != null
								? grid.getGateCellAreaReleased()
								: BigDecimal.ZERO;

						BigDecimal exitsAreaReleased = cf.getCellAreaReleased() != null ? cf.getCellAreaReleased()
								: BigDecimal.ZERO;

						System.out.println("exitsQty______________________" + exitsQty);

						cf.setQtyTakenOut(qtyOut.add(exitsQty).subtract(update.getQtyTakenOut()));

						cf.setStatus("A");
						cf.setEditedBy(user);
						cf.setEditedDate(new Date());

//								if (saved != null) {

						YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
								grid.getYardLocation(), grid.getYardBlock(), grid.getBlockCellNo());

						if (existingCell != null) {
							BigDecimal existingCellAreaUsed = existingCell.getCellAreaUsed() != null
									? existingCell.getCellAreaUsed()
									: BigDecimal.ZERO;
							BigDecimal gridCellAreaReleased = grid.getGateCellAreaReleased() != null
									? grid.getGateCellAreaReleased()
									: BigDecimal.ZERO;

							if (existingCell.getCellAreaUsed().compareTo(BigDecimal.ZERO) == 0) {
								existingCell.setCellAreaUsed(gridCellAreaReleased); // Set to released area if it's zero
																					// or null
							} else {
								existingCell.setCellAreaUsed(existingCellAreaUsed.subtract(gridCellAreaReleased).subtract(update.getGateCellAreaReleased())); // Otherwise,
																													// subtract
							}

//									    existingCell.setCellAreaUsed(existingCellAreaUsed.subtract(gridCellAreaReleased));
							yardBlockCellRepository.save(existingCell);
						}

						GeneralReceivingGrid getExistingData = generalReceivingGateInDtlRepo
								.getSavedGeneralReceivingGrid(companyId, branchId, grid.getReceivingId(),
										grid.getYardLocation(), grid.getYardBlock(), grid.getBlockCellNo());

						if (getExistingData != null) {
							BigDecimal existingQtyTakenOut = getExistingData.getQtyTakenOut() != null
									? getExistingData.getQtyTakenOut()
									: BigDecimal.ZERO;
							BigDecimal savedQtyTakenOut = grid.getQtyTakenOut() != null ? grid.getQtyTakenOut()
									: BigDecimal.ZERO;

							getExistingData
									.setQtyTakenOut(existingQtyTakenOut.add(savedQtyTakenOut).subtract(exitsQty).add(update.getQtyTakenOut()));

							BigDecimal existingAreaReleased = getExistingData.getAreaReleased() != null
									? getExistingData.getAreaReleased()
									: BigDecimal.ZERO;
							BigDecimal savedAreaReleased = grid.getGateCellAreaReleased() != null
									? grid.getGateCellAreaReleased()
									: BigDecimal.ZERO;

							getExistingData.setAreaReleased(existingAreaReleased.add(savedAreaReleased).subtract(update.getGateCellAreaReleased()));

							if (getExistingData.getCellAreaUsed().compareTo(BigDecimal.ZERO) == 0) {
								getExistingData.setCellAreaUsed(savedAreaReleased); // Set to released area if it's zero
																					// or null
							} else {
								getExistingData
										.setCellAreaUsed(getExistingData.getCellAreaUsed().subtract(savedAreaReleased).add(update.getGateCellAreaReleased()));
							}

							if (getExistingData.getCellAreaAllocated().compareTo(BigDecimal.ZERO) == 0) {
								getExistingData.setCellAreaAllocated(savedAreaReleased); // Set to released area if it's
																							// zero or null
							} else {
								getExistingData.setCellAreaAllocated(
										getExistingData.getCellAreaAllocated().subtract(savedAreaReleased).add(update.getGateCellAreaReleased()));
							}

							generalReceivingGridRepo.save(getExistingData);

							cf.setCellAreaReleased(exitsAreaReleased.add(grid.getGateCellAreaReleased()).subtract(update.getGateCellAreaReleased()));
							cf.setGateCellAreaReleased(existArea.add(gateAreaReleased).subtract(update.getGateCellAreaReleased()));

							generalDeliveryGridRepo.save(cf);

						}

					}
					
					
					update.setQtyTakenOut(grid.getQtyTakenOut());
					update.setGateCellAreaReleased(grid.getGateCellAreaReleased());
					
					 saved =generalGatePassGridRepo.save(update);
					 
					 savedList.add(saved);

				
					
				}
				
				}
				
			}
			


//				}

		}

		return new ResponseEntity<>(savedList, HttpStatus.OK);
	}

	public BigDecimal getSumOfQtyTakenOutForCommodity(String companyId, String branchId, String exBondingId,
			String gatePassId, String cfBondDtlId) {
		return generalGatePassRepository.getSumOfQtyTakenOutForCommodity(companyId, branchId, exBondingId, gatePassId,
				cfBondDtlId);
	}

	@Transactional
	public ResponseEntity<?> approveDataOfInCFbondGrid(String companyId, String branchId, String flag, String user,
			Map<String, Object> requestBody) {

		ObjectMapper object = new ObjectMapper();
		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		GeneralGatePassCargo cfinbondcrg = object.convertValue(requestBody.get("gatePass"), GeneralGatePassCargo.class);

		GeneralGatePassCargo dataForPrint = null;

		Object nocDtlObj = requestBody.get("dtl");
		List<GeneralGatePassCargo> gatePassDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			gatePassDtlList = object.convertValue(nocDtlObj, new TypeReference<List<GeneralGatePassCargo>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				GeneralGatePassCargo gatePassDtl = object.convertValue(entry.getValue(), GeneralGatePassCargo.class);
				gatePassDtlList.add(gatePassDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		BigDecimal totalSum = BigDecimal.ZERO;

//		if (gatePassDtlList != null) {
//			for (GeneralGatePassCargo list : gatePassDtlList) {
//
//				System.out.println("jhsdjhsfjsdjfgdsjfdgsjfh :" + list.getDeliveryId());
//				System.out.println("jhsdjhsfjsdjfgdsjfdgsjfh :" + list.getGatePassId());
//
//				BigDecimal sumOfInbondFormGrid = generalGatePassRepository.getSumOfQtyTakenOutCommodityWise(companyId,
//						branchId, list.getDeliveryId(), cfinbondcrg.getGatePassId());
//
//				if (sumOfInbondFormGrid != null) {
//					totalSum = totalSum.add(sumOfInbondFormGrid);
//				}
//			}
//		}

			Set<String> uniqueIds = new HashSet<>();

			if (gatePassDtlList != null) {
			    for (GeneralGatePassCargo item : gatePassDtlList) {
			        String uniqueKey = item.getDeliveryId() + "-" + item.getGatePassId(); // Create a unique key
			        if (uniqueIds.add(uniqueKey)) { // Ensure distinct values
			            BigDecimal sum = generalGatePassRepository.getSumOfQtyTakenOutCommodityWise(
			                companyId, branchId, item.getDeliveryId(), item.getGatePassId()
			            );
			            if (sum != null) {
			                totalSum = totalSum.add(sum);
			            }
			        }
			    }
			}

		System.out.println("Total Sum: " + totalSum);

		if (cfinbondcrg.getGatePassId() == null || cfinbondcrg.getGatePassId().isEmpty()
				|| cfinbondcrg.getGatePassId().isBlank()) {
			return new ResponseEntity<>("Please First Save Data", HttpStatus.BAD_REQUEST);
		}

		if (cfinbondcrg != null && cfinbondcrg.getGatePassId() != null || !cfinbondcrg.getGatePassId().isEmpty()
				|| !cfinbondcrg.getGatePassId().isBlank()) {

			BigDecimal sumOfInbondFromDtl = generalGatePassRepository.getSUmOfGatePassQuntity(companyId, branchId,
					cfinbondcrg.getGatePassId());

			System.out.println("sumOfInbondFromDtl: " + sumOfInbondFromDtl + " ______________ " + totalSum);

			if (sumOfInbondFromDtl == null || totalSum == null) {
				return new ResponseEntity<>("One of the sum values is null. Please check the data.",
						HttpStatus.BAD_REQUEST);
			}

			if (totalSum.compareTo(sumOfInbondFromDtl) != 0) {
				return new ResponseEntity<>("Qty Taken Out do not match in yard, please add packages in grid.",
						HttpStatus.BAD_REQUEST);
			} else {

				List<GeneralGatePassCargo> updateAfterApprove = generalGatePassRepository.updateAfterApprove(companyId,
						branchId, cfinbondcrg.getGatePassId());
				if (updateAfterApprove != null) {
					// Process the firstResul

					updateAfterApprove.forEach(data -> data.setStatus("A"));
					generalGatePassRepository.saveAll(updateAfterApprove);

					dataForPrint = updateAfterApprove.get(0);

					System.out.println("dataForPrint_____________________" + dataForPrint);
					return new ResponseEntity<>(dataForPrint, HttpStatus.OK);
				}
			}
		}

		return new ResponseEntity<>(dataForPrint, HttpStatus.OK);
	}

	public ResponseEntity<String> getGeneralGatePassPrint(String companyId, String branchId, String username,
			String type, String companyname, String branchname, String gatePassId) throws DocumentException {

		Context context = new Context();

		// Cfbondnoc dataForPrint
		// =cfbondnocRepository.findCfbondnocByCompanyIdAndBranchIdAndNocTransIdAndNocNo(companyId,
		// branchId, nocTransId, nocNo);

		GeneralGatePassCargo dataForPrint = null;

		List<GeneralGatePassCargo> result = generalGatePassRepository.updateAfterApprove(companyId, branchId,
				gatePassId);
		if (!result.isEmpty()) {
			dataForPrint = result.get(0);
			// Process the firstResult
		}

		BigDecimal totalQtyTakenOut = result.stream().map(GeneralGatePassCargo::getGatePassPackages) // Extract
																										// qtyTakenOut
																										// from each
																										// object
				.filter(qty -> qty != null) // Filter out null values
				.reduce(BigDecimal.ZERO, BigDecimal::add); // Sum the non-null values

		System.out.println("Total Qty Taken Out: " + totalQtyTakenOut);

		BigDecimal totalWeightOut = result.stream().map(GeneralGatePassCargo::getGatePassWeight) // Extract qtyTakenOut
																									// from each object
				.filter(qty -> qty != null) // Filter out null values
				.reduce(BigDecimal.ZERO, BigDecimal::add); // Sum the non-null values

		System.out.println("Total totalWeightOut Taken Out: " + totalWeightOut);

		StringBuilder deliveryIdNoNos = new StringBuilder();

		result.stream().map(GeneralGatePassCargo::getDeliveryId) // Extract exBondBeNo from each object
				.filter(Objects::nonNull) // Filter out null values
				.forEach(exBondBeNo -> {
					if (deliveryIdNoNos.length() > 0) {
						deliveryIdNoNos.append(","); // Add comma before the next value if it's not the first one
					}
					deliveryIdNoNos.append(exBondBeNo); // Append the exBondBeNo
				});

		// StringBuilder for boeNo, bondNo, and igmNo
		StringBuilder boeNosBuilder = new StringBuilder();
		StringBuilder containerNosBuilder = new StringBuilder();
		StringBuilder igmNosBuilder = new StringBuilder();

		for (GeneralGatePassCargo item1 : result) {
			if (item1.getBoeNo() != null) {
				if (boeNosBuilder.length() > 0) {
					boeNosBuilder.append(","); // Add comma before the next value if it's not the first one
				}
				boeNosBuilder.append(item1.getBoeNo()); // Append boeNo
			}

			// Collect bondNo
			if (item1.getContainerNo() != null) {
				if (containerNosBuilder.length() > 0) {
					containerNosBuilder.append(","); // Add comma before the next value if it's not the first one
				}
				containerNosBuilder.append(item1.getContainerNo()); // Append bondNo
			}

		}
		// Collect boeNo
		// Print the results
		System.out.println("BOE Nos: " + boeNosBuilder.toString());
		System.out.println("Bond Nos: " + containerNosBuilder.toString());

		String deliveryId = deliveryIdNoNos.toString();
		System.out.println("Ex Bond Be Nos: " + deliveryId);

		System.out.println("gatePassdata____________________________________________");
		String c1 = username;
		String b1 = companyname;
		String u1 = branchname;

		Company companyAddress = companyRepo.findByCompany_Id(companyId);

		Branch branchAddress = branchRepo.findByBranchId(branchId);

		String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
				+ companyAddress.getAddress_3() + companyAddress.getCity();

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		String city = companyAddress.getCity();

		String bondCode = branchAddress.getBondCode();
		context.setVariable("gatePassId", dataForPrint.getGatePassId());
		context.setVariable("gatePassDate", dataForPrint.getGatePassDate());
		context.setVariable("deliveryId", deliveryId);
//			context.setVariable("boeNo", dataForPrint.getBoeNo());
		context.setVariable("boeNo", boeNosBuilder.toString());
		context.setVariable("boeDate", dataForPrint.getBoeDate());

		context.setVariable("goDownNo", dataForPrint.getBoeDate());
		context.setVariable("depositNo", dataForPrint.getDepositNo());

//			context.setVariable("igmNo", dataForPrint.getIgmNo());

		context.setVariable("impName", dataForPrint.getImporterName());
//			context.setVariable("inBondPkgs", dataForPrint.getInBondPackages());

		context.setVariable("totalQtyTakenOut", totalQtyTakenOut);
		context.setVariable("totalWeightOut", totalWeightOut);
		context.setVariable("remark", dataForPrint.getComments());

		context.setVariable("trasporterName", dataForPrint.getTransporterName());
		context.setVariable("truckNo", dataForPrint.getVehicleNo());

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

		String htmlContent = templateEngine.process("GeneralCargoGatePass", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
	
	
	
	
	public ResponseEntity<?> getForMainBondingSearch(String cid, String bid, String nocNo, String boeNo,
			String bondingNo) {

		List<String> idList = new ArrayList<>();
		GeneralReceivingCrg firstInbond = null;
		GeneralGateIn firstGateIn = null;
		GeneralDeliveryDetails firstExBond = null;
//		GateOut firstGateOut = null;
		GeneralGatePassCargo firstGatePass = null;
		Map<String, Object> myMap = new HashMap<>();
		GenerelJobEntry list = generalGatePassRepository.getForBondingSearch(cid, bid, nocNo, boeNo);

		if (list == null) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		idList.add("P01801");

		Boolean check = generalGatePassRepository.checkGateInPackages(cid, bid, list.getJobNo(), list.getJobTransId());

		System.out.println("check______________________________________" + check);
		if (check) {
			idList.add("P01802");
			myMap.put("list", list);
			myMap.put("idList", idList);

//			 return new ResponseEntity<>(myMap,HttpStatus.OK) ;
		}
		
		
		Boolean checkGateIn = generalGatePassRepository.checkGateInPackagesGeneralGateIn(cid, bid, list.getJobNo(), list.getJobTransId());

		System.out.println("checkGateIn______________________________________" + checkGateIn);
		if (checkGateIn) {
			idList.add("P01802");
			idList.add("P01803");
			
			List<GeneralGateIn> results = generalGatePassRepository.getSavedData(cid, bid,list.getJobNo(), list.getJobTransId()
					);
			if (!results.isEmpty())

			{
				firstGateIn = results.get(0);
			} else {
				return null;
			}
			
			myMap.put("list", list);
			myMap.put("idList", idList);
			myMap.put("firstGateIn", firstGateIn);
//			 return new ResponseEntity<>(myMap,HttpStatus.OK) ;
		}
		
		
		Boolean checkGateInAll = generalGatePassRepository.checkGateInPackagesAllGeneralGateIn(cid, bid, list.getJobNo(), list.getJobTransId());

		System.out.println("checkGateInAll______________________________________" + checkGateInAll);
		if (checkGateInAll) {
			idList.add("P01802");
			idList.add("P01803");
			
			
			List<GeneralGateIn> results = generalGatePassRepository.getSavedData(cid, bid,list.getJobNo(), list.getJobTransId()
					);
			if (!results.isEmpty())

			{
				firstGateIn = results.get(0);
			} else {
				return null;
			}
			
			myMap.put("firstGateIn", firstGateIn);
			myMap.put("list", list);
			myMap.put("idList", idList);

//			 return new ResponseEntity<>(myMap,HttpStatus.OK) ;
		}
		
		
		Boolean checkAll9 = generalGatePassRepository.checkGateInPackagesAll(cid, bid, list.getJobNo(), list.getJobTransId());
		//
		System.out.println("check9______________________________________" + checkAll9);
		if (checkAll9) {
			idList.add("P01802");
			idList.add("P01803");
			myMap.put("list", list);
			myMap.put("idList", idList);
			myMap.put("firstGateIn", firstGateIn);
			// return new ResponseEntity<>(myMap, HttpStatus.OK);
		}

		Boolean check9 = generalGatePassRepository.checkInBondedPackages(cid, bid, list.getJobNo(), list.getJobTransId());
		//
		System.out.println("check9______________________________________" + check9);
		if (check9) {
			idList.add("P01802");
			idList.add("P01803");
			myMap.put("firstGateIn", firstGateIn);
			myMap.put("list", list);
			myMap.put("idList", idList);
			// return new ResponseEntity<>(myMap, HttpStatus.OK);
		}
		
		
		
		
		Boolean checkDelivery = generalGatePassRepository.checkInForDelivery(cid, bid, list.getJobNo(),
				list.getJobTransId());

		System.out.println("checkDelivery______________________________________" + checkDelivery);
		if (checkDelivery) {
			idList.add("P01802");
			idList.add("P01803");
			idList.add("P01804");
			
			
			List<GeneralGateIn> results = generalGatePassRepository.getSavedData(cid, bid,list.getJobNo(), list.getJobTransId()
					);
			if (!results.isEmpty())

			{
				firstGateIn = results.get(0);
			} else {
				return null;
			}
			
			List<GeneralReceivingCrg> results1 = generalGatePassRepository.getInBondingIdAndBoeNo(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results1.isEmpty())

			{
				firstInbond = results1.get(0);
			} else {
				return null;
			}

			myMap.put("firstInbond", firstInbond);
			myMap.put("firstGateIn", firstGateIn);
			myMap.put("list", list);
			myMap.put("idList", idList);

//		 return new ResponseEntity<>(myMap, HttpStatus.OK);
		}
		
		

		Boolean check2 = generalGatePassRepository.checkInBondedPackagesAllDone(cid, bid, list.getJobNo(),
				list.getJobTransId());

		System.out.println("check2______________________________________" + check2);
		if (check2) {
			idList.add("P01802");
			idList.add("P01803");
			idList.add("P01804");
			
			
			
			List<GeneralReceivingCrg> results = generalGatePassRepository.getInBondingIdAndBoeNo(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results.isEmpty())

			{
				firstInbond = results.get(0);
			} else {
				return null;
			}
			
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstGateIn", firstGateIn);
			myMap.put("list", list);
			myMap.put("idList", idList);

//		 return new ResponseEntity<>(myMap, HttpStatus.OK);
		}


		
		
		Boolean checkForQtyOutParse = generalGatePassRepository.checkInForPartialGatePassAll(cid, bid, list.getJobNo(),
				list.getJobTransId());
		
		System.out.println("checkForQtyOutParse   ----------------------------------"+checkForQtyOutParse);

		if (checkForQtyOutParse) {
			
			idList.add("P01802");
			idList.add("P01803");
			idList.add("P01804");
			
			idList.add("P01805");

			System.out.println("checkForQtyOutParse   ----------------------------------"+checkForQtyOutParse);

			
			List<GeneralReceivingCrg> results = generalGatePassRepository.getInBondingIdAndBoeNo(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results.isEmpty())

			{
				firstInbond = results.get(0);
			} else {
				return null;
			}

			List<GeneralDeliveryDetails> results11 = generalGatePassRepository.getExbondIdAndInbondId(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results11.isEmpty())

			{
				firstExBond = results11.get(0);
			} else {
				return null;
			}
			System.out.println("checkForQtyOutParse   ----------------------------------"+checkForQtyOutParse);

			
			myMap.put("firstGatePass", firstGatePass);
			myMap.put("firstGateIn", firstGateIn);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstExBond", firstExBond);
			myMap.put("idList", idList);
			myMap.put("list", list);

		}
		
		
		
		Boolean checkForQtyOut = generalGatePassRepository.checkInForALLGatePassAll(cid, bid, list.getJobNo(),
				list.getJobTransId());

		
		System.out.println("checkForQtyOut   ----------------------------------"+checkForQtyOut);
		
		if (checkForQtyOut) {
			
			
			System.out.println("checkForQtyOut   ----------------------------------"+checkForQtyOut);
			
			
			idList.add("P01802");
			idList.add("P01803");
			idList.add("P01804");
			
			idList.add("P01805");

			List<GeneralReceivingCrg> results = generalGatePassRepository.getInBondingIdAndBoeNo(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results.isEmpty())

			{
				firstInbond = results.get(0);
			} else {
				return null;
			}

			List<GeneralDeliveryDetails> results11 = generalGatePassRepository.getExbondIdAndInbondId(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results11.isEmpty())

			{
				firstExBond = results11.get(0);
			} else {
				return null;
			}
			
			System.out.println("checkForQtyOut   ----------------------------------"+checkForQtyOut);
			
			myMap.put("firstGatePass", firstGatePass);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstGateIn", firstGateIn);
			myMap.put("firstExBond", firstExBond);
			myMap.put("idList", idList);
			myMap.put("list", list);

			return new ResponseEntity<>(myMap, HttpStatus.OK);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		Boolean checkForQtyOutParseFromGatePass = generalGatePassRepository.checkInFromGatePass(cid, bid, list.getJobNo(),
				list.getJobTransId());
		
		System.out.println("checkForQtyOutParseFromGatePass   ----------------------------------"+checkForQtyOutParseFromGatePass);

		if (checkForQtyOutParseFromGatePass) {
			
			idList.add("P01802");
			idList.add("P01803");
			idList.add("P01804");
			
			idList.add("P01805");

			System.out.println("checkForQtyOutParseFromGatePass   ----------------------------------"+checkForQtyOutParseFromGatePass);

			
			List<GeneralReceivingCrg> results = generalGatePassRepository.getInBondingIdAndBoeNo(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results.isEmpty())

			{
				firstInbond = results.get(0);
			} else {
				return null;
			}
			
			System.out.println("checkForQtyOutParseFromGatePass   ----------------------------------"+checkForQtyOutParseFromGatePass);

			List<GeneralGatePassCargo> result = generalGatePassRepository.getCfBondGatePassList(cid, bid,
					list.getJobTransId(), list.getJobNo());

			if (!result.isEmpty())

			{
				firstGatePass = result.get(0);
			} else {
				return null; // Handle cases where no records are found
			}

			System.out.println("checkForQtyOutParseFromGatePass   ----------------------------------"+checkForQtyOutParseFromGatePass);

			List<GeneralDeliveryDetails> results11 = generalGatePassRepository.getExbondIdAndInbondId(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results11.isEmpty())

			{
				firstExBond = results11.get(0);
			} else {
				return null;
			}
			System.out.println("checkForQtyOutParseFromGatePass   ----------------------------------"+checkForQtyOutParseFromGatePass);

			
			myMap.put("firstGatePass", firstGatePass);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstExBond", firstExBond);
			myMap.put("firstGateIn", firstGateIn);
			myMap.put("idList", idList);
			myMap.put("list", list);

		}
		
		
		
		Boolean checkForQtyOutFromGatePass = generalGatePassRepository.checkInForFromGatePut(cid, bid, list.getJobNo(),
				list.getJobTransId());

		
		System.out.println("checkForQtyOutFromGatePass   ----------------------------------"+checkForQtyOutFromGatePass);
		
		if (checkForQtyOutFromGatePass) {
			
			
			System.out.println("checkForQtyOutFromGatePass   ----------------------------------"+checkForQtyOutFromGatePass);
			
			
			idList.add("P01802");
			idList.add("P01803");
			idList.add("P01804");
			
			idList.add("P01805");

			List<GeneralReceivingCrg> results = generalGatePassRepository.getInBondingIdAndBoeNo(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results.isEmpty())

			{
				firstInbond = results.get(0);
			} else {
				return null;
			}

			System.out.println("checkForQtyOutFromGatePass   ----------------------------------"+checkForQtyOutFromGatePass);
			
			
			List<GeneralGatePassCargo> result = generalGatePassRepository.getCfBondGatePassList(cid, bid,
					list.getJobTransId(), list.getJobNo());

			if (!result.isEmpty())

			{
				firstGatePass = result.get(0);
			} else {
				return null; // Handle cases where no records are found
			}
			
			System.out.println("checkForQtyOutFromGatePass   ----------------------------------"+checkForQtyOutFromGatePass);

			List<GeneralDeliveryDetails> results11 = generalGatePassRepository.getExbondIdAndInbondId(cid, bid, list.getJobTransId(),
					list.getJobNo());
			if (!results11.isEmpty())

			{
				firstExBond = results11.get(0);
			} else {
				return null;
			}
			
			System.out.println("checkForQtyOutFromGatePass   ----------------------------------"+checkForQtyOutFromGatePass);
			
			myMap.put("firstGatePass", firstGatePass);
			myMap.put("firstInbond", firstInbond);
			myMap.put("firstExBond", firstExBond);
			myMap.put("idList", idList);
			myMap.put("firstGateIn", firstGateIn);
			myMap.put("list", list);

			return new ResponseEntity<>(myMap, HttpStatus.OK);
		}
		
		
		
		
		
		
		
		
		
		return new ResponseEntity<>(myMap, HttpStatus.OK);
	}
	

	public List<JarDetail> listByJarId(String jarId,String cid) {
		// TODO Auto-generated method stub
		return jarDetailRepository.findByID(jarId,cid);
	}
}
