package com.cwms.service;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.entities.Branch;
import com.cwms.entities.ExportAudit;
import com.cwms.entities.GateIn;
import com.cwms.exportAuditTrail.BackToTownDTO;
import com.cwms.exportAuditTrail.BackToTownOutDTO;
import com.cwms.exportAuditTrail.CartingTallyDTO;
import com.cwms.exportAuditTrail.ContainerGateIn;
import com.cwms.exportAuditTrail.ContainerGateOut;
import com.cwms.exportAuditTrail.GateInJoDTO;
import com.cwms.exportAuditTrail.GateInJoDetailDTO;
import com.cwms.exportAuditTrail.ShippingBillDTO;
import com.cwms.exportAuditTrail.StuffTally;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.ExportAuditRepo;
import com.cwms.repository.ExportReportRepositary;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class ExportReportService {
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private ExportReportRepositary reportRepo;

	@Autowired
	private ExportAuditRepo auditRepo;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ProcessNextIdService processService;
	
	@Autowired
	private ExportSbCargoEntryRepo entryCargoRepo;

	private static final String profitCentreIdExport = "N00004";

	public List<GateInJoDetailDTO> getDistinctSummedDetailsThirdTable(List<GateInJoDetailDTO> gateInJoDetail) {
		return gateInJoDetail.stream().collect(Collectors.toMap(
				dto -> dto.getCartingTransId() + "-" + dto.getCartingLineId(), // Group by cartingTransId &
																				// cartingLineId
				dto -> new GateInJoDetailDTO(dto.getSbTransId(), dto.getSbNo(), dto.getGateInId(),
						dto.getCartingTransId(), dto.getNoOfPackages(), dto.getGrossWeight(), dto.getNewCartedpPkg(), // Initial
																														// value
						dto.getNewCartedWt(), // Initial value
						dto.getCartingLineId(), dto.getSubSrNo()),
				(existing, newEntry) -> { // Merge function for duplicate cartingId & cartingLineId
					existing.setNewCartedpPkg(existing.getNewCartedpPkg().add(newEntry.getNewCartedpPkg()));
					existing.setNewCartedWt(existing.getNewCartedWt().add(newEntry.getNewCartedWt()));
					return existing;
				})).values().stream().toList();
	}

	public List<CartingTallyDTO> getDistinctSummedDetailsFourthTable(List<CartingTallyDTO> gateInJoDetail) {
		return gateInJoDetail.stream()
				.collect(Collectors.toMap(dto -> dto.getCartingTransId() + "-" + dto.getCartingLineId(), // Group by
																											// cartingTransId
																											// &
																											// cartingLineId
						dto -> new CartingTallyDTO(dto.getSbTransId(), dto.getSbNo(), dto.getGateInId(),
								dto.getCartingTransId(), dto.getNewCellAreaAllocated(), dto.getNewCartedpPkg(), // Initial
																												// value
								dto.getCartingLineId(), dto.getSubSrNo()),
						(existing, newEntry) -> { // Merge function for duplicate cartingId & cartingLineId
							existing.setNewCellAreaAllocated(
									existing.getNewCellAreaAllocated().add(newEntry.getNewCellAreaAllocated()));
							return existing;
						}))
				.values().stream().toList();
	}

//	Export AuditTrail

	@Transactional
	public ResponseEntity<?> saveExporAudit(String companyId, String branchId, Map<String, Object> requestData,
			String user, String type, String sbNo, String containerNo) {

		try {
			Date currentDate = new Date();
			ShippingBillDTO shippingBillDetail = null;
			List<GateInJoDTO> gateInJO = null;
			List<GateInJoDetailDTO> gateInJoDetail = null;
			List<CartingTallyDTO> cartigTally = null;
			ContainerGateIn containerGateIn = null;
			ContainerGateOut containerGateOut = null;
			List<StuffTally> stuffTally = null;
			
			List<BackToTownDTO> backToTown = null;
			List<BackToTownOutDTO> backToTownOut = null;
			

			String updatedSbNo = sbNo;
			String updatedContainerNo = containerNo;

			int recordsUpdated = 0;

			if ("sb".equals(type)) {
				shippingBillDetail = objectMapper.convertValue(requestData.get("shippingBillDetail"),
						ShippingBillDTO.class);

				Map<String, Object> result = updateShippingBillDetails(shippingBillDetail, companyId, branchId, user,
						currentDate);

				if (result.containsKey("error")) {
					 String errorMessage = "Duplicate SB No found for SrNo: " + 1 + " and SB No: " + shippingBillDetail.getNewSbNo();
			            return ResponseEntity.badRequest().body(errorMessage);
				}
				
				recordsUpdated = (int) result.get("updatedFieldsCount");
				updatedSbNo = (String) result.get("sbNo");

			} else if ("gateInJO".equals(type)) {
				gateInJO = objectMapper.convertValue(requestData.get("gateInJO"),
						new TypeReference<List<GateInJoDTO>>() {
						});

				for (GateInJoDTO jo : gateInJO) {
					Map<String, Object> result = updateGateInJO(jo, companyId, branchId, user, currentDate, sbNo);
					updatedSbNo = sbNo;
					recordsUpdated += (int) result.get("updatedFieldsCount");
					;
				}

			} else if ("gateInJoDtl".equals(type)) {
				gateInJoDetail = objectMapper.convertValue(requestData.get("gateInJoDetail"),
						new TypeReference<List<GateInJoDetailDTO>>() {
						});

				for (GateInJoDetailDTO jo : gateInJoDetail) {
					Map<String, Object> result = updateGateInJODetail(jo, companyId, branchId, user, currentDate, sbNo);
					updatedSbNo = sbNo;
					recordsUpdated += (int) result.get("updatedFieldsCount");
					;
				}

				List<GateInJoDetailDTO> distinctSummedDetailsThirdTable = getDistinctSummedDetailsThirdTable(
						gateInJoDetail);

				if (recordsUpdated > 0) {
					for (GateInJoDetailDTO jo : distinctSummedDetailsThirdTable) {
						updateGateInJODetailThirdTableCarting("cfcrtg", "Actual_No_Of_Packages", jo.getNewCartedpPkg(),
								jo.getSbTransId(), companyId, branchId, jo.getGateInId(), jo.getCartingTransId(),
								jo.getCartingLineId());
						updateGateInJODetailThirdTableCarting("cfcrtg", "Actual_No_Of_Weight", jo.getNewCartedWt(),
								jo.getSbTransId(), companyId, branchId, jo.getGateInId(), jo.getCartingTransId(),
								jo.getCartingLineId());
						updateGateInJODetailThirdTableCarting("cfcrtg", "Yard_Packages", jo.getNewCartedpPkg(),
								jo.getSbTransId(), companyId, branchId, jo.getGateInId(), jo.getCartingTransId(),
								jo.getCartingLineId());

					}
				}

			} else if ("cartingTally".equals(type)) {
				cartigTally = objectMapper.convertValue(requestData.get("cartigTally"),
						new TypeReference<List<CartingTallyDTO>>() {
						});

				for (CartingTallyDTO jo : cartigTally) {
					Map<String, Object> result = updateCartingTally(jo, companyId, branchId, user, currentDate, sbNo);
					updatedSbNo = sbNo;
					recordsUpdated += (int) result.get("updatedFieldsCount");
					;
				}

				List<CartingTallyDTO> distinctSummedDetailsThirdTable = getDistinctSummedDetailsFourthTable(
						cartigTally);

				if (recordsUpdated > 0) {
					for (CartingTallyDTO jo : distinctSummedDetailsThirdTable) {
						updateGateInJODetailThirdTableCarting("cfcrtg", "Area_Occupied", jo.getNewCellAreaAllocated(),
								jo.getSbTransId(), companyId, branchId, jo.getGateInId(), jo.getCartingTransId(),
								jo.getCartingLineId());

					}
				}

			} else if ("containerGateIn".equals(type)) {
				containerGateIn = objectMapper.convertValue(requestData.get("containerGateIn"), ContainerGateIn.class);

				Map<String, Object> result = updateContainerGateIn(containerGateIn, companyId, branchId, user,
						currentDate, sbNo, containerNo);
				
				
				if (result.containsKey("error")) {
					 String errorMessage = "Updated Container No is already in Inventory: "+ containerGateIn.getNewContainerNo();
			            return ResponseEntity.badRequest().body(errorMessage);
				}
				

				recordsUpdated = (int) result.get("updatedFieldsCount");
				updatedSbNo = (String) result.get("sbNo");
				updatedContainerNo = (String) result.get("containerNo");
			} else if ("containerGateOut".equals(type)) {
				containerGateOut = objectMapper.convertValue(requestData.get("containerGateOut"),
						ContainerGateOut.class);

				Map<String, Object> result = updateContainerGateOutDetails(containerGateOut, companyId, branchId, user,
						currentDate, sbNo);

				recordsUpdated = (int) result.get("updatedFieldsCount");
				updatedSbNo = (String) result.get("sbNo");
				updatedContainerNo = containerNo;

			} else if ("stuffTally".equals(type)) {
				stuffTally = objectMapper.convertValue(requestData.get("stuffTally"),
						new TypeReference<List<StuffTally>>() {
						});

				for (StuffTally jo : stuffTally) {
				Map<String, Object> result = updateStuffTallyDetails(jo, companyId, branchId, user, currentDate, sbNo);
				
				if (result.containsKey("error")) {
					 String errorMessage = "Updated Container No is already in Inventory: "+ jo.getNewContainerNo();
			            return ResponseEntity.badRequest().body(errorMessage);
				}
				
				updatedSbNo = sbNo;
				updatedContainerNo = (String) result.get("containerNo");
				recordsUpdated += (int) result.get("updatedFieldsCount");
				}				

			}else if ("backToTown".equals(type)) {
				backToTown = objectMapper.convertValue(requestData.get("backToTown"),
						new TypeReference<List<BackToTownDTO>>() {
						});

				for (BackToTownDTO jo : backToTown) {
				Map<String, Object> result = updateBackToTown(jo, companyId, branchId, user, currentDate, sbNo);
				updatedSbNo = sbNo;
				updatedContainerNo = containerNo;
				recordsUpdated += (int) result.get("updatedFieldsCount");
				}				

			}else if ("backToTownOut".equals(type)) {
				backToTownOut = objectMapper.convertValue(requestData.get("backToTownOut"),
						new TypeReference<List<BackToTownOutDTO>>() {
						});

				for (BackToTownOutDTO jo : backToTownOut) {
				Map<String, Object> result = updateBackToTownOut(jo, companyId, branchId, user, currentDate, sbNo);
				updatedSbNo = sbNo;
				updatedContainerNo = containerNo;
				recordsUpdated += (int) result.get("updatedFieldsCount");
				}
			}
			
			String messege = recordsUpdated > 0 ? "Record updated succesfully" : "No Records are updated";

			ResponseEntity<?> exportAuditTrailSearh = exportAuditTrailSearh(companyId, branchId, profitCentreIdExport,
					updatedSbNo, updatedContainerNo);

			Map<String, Object> searchValues = new HashMap<>();
			searchValues.put("sbNo", updatedSbNo);
			searchValues.put("containerNo", updatedContainerNo);

			Map<String, Object> result = new HashMap<>();
			result.put("messege", messege);
			result.put("search", exportAuditTrailSearh);
			result.put("searchValues", searchValues);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			System.out.println("Error in main" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the Audit.");
		}
	}
	
	
	

	
	
	

	@Transactional
	public Map<String, Object> updateContainerGateIn(ContainerGateIn shippingBillDTO, String companyId, String branchId,
			String userId, Date currentDate, String sbNoS, String containerNos) {
		// Extract common parameters
		String containerNo = shippingBillDTO.getOldContainerNo();
		String oldSbNo = sbNoS;
		AtomicInteger updatedFieldsCount = new AtomicInteger(0);
		AtomicReference<String> newSbNo = new AtomicReference<>(oldSbNo);
		AtomicReference<String> newContainerNo = new AtomicReference<>(containerNo);
		Map<String, Object> result = new HashMap<>();
		

		if (shippingBillDTO.getNewContainerNo() != null && !shippingBillDTO.getNewContainerNo().equals(shippingBillDTO.getOldContainerNo())) {
		        boolean existsBySbNo = auditRepo.checkContainerInInventory(companyId, branchId, shippingBillDTO.getNewContainerNo(),
		        								shippingBillDTO.getGateInId());

		        if (existsBySbNo) {
		        	result.put("updatedFieldsCount", updatedFieldsCount.get());
		    		result.put("sbNo", newSbNo.get());
		            result.put("error", "Duplicate Container No: " + shippingBillDTO.getNewContainerNo());
		            return result;
		        }
		    }
		
		
		
		

		Map<String, String> tableWhereConditionMap = new HashMap<>();

		// Define fields and their corresponding tables and columns
		Map<String, Map<String, String>> fieldTableMap = new HashMap<>();


		List<String> sbTransIdTables = List.of("cfgatein", "cfstufftally", "cfstuffrq", "cfexpinventory");

		// Assign WHERE conditions for SB_Trans_Id-based tables
		sbTransIdTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE Gate_In_Id = :gateInId AND Company_Id = :companyId "
						+ "AND Branch_Id = :branchId "
						+ "AND Container_No = :containerNoOld"));	

		
		tableWhereConditionMap.put("cfequipmentactivity",
			    "WHERE Company_Id = :companyId AND Branch_Id = :branchId " +
			    "AND (Erp_Doc_ref_no = :stuffTallyId OR Erp_Doc_ref_no = :stuffReqId) " +
			    "AND Container_No = :containerNoOld");

		
		tableWhereConditionMap.put("cfexpmovementreq",
				"WHERE Company_Id = :companyId AND Branch_Id = :branchId "
						+ "AND Movement_Req_Id = :movementReqId AND Container_No = :containerNoOld");

		tableWhereConditionMap.put("cfexportgatepass",
				"WHERE Company_Id = :companyId AND Branch_Id = :branchId "
						+ "AND Gate_Pass_Id = :gatePassNo "
						+ "AND Container_No = :containerNoOld");
		
		
		tableWhereConditionMap.put("cfgateout",
				"WHERE Company_Id = :companyId AND Branch_Id = :branchId "
						+ "AND Gate_Pass_No = :gatePassNo AND Gate_Out_Id = :gateOutId "
						+ "AND Container_No = :containerNoOld");
		
		
		// newGateInDate
				Map<String, String> newGateInMap = new HashMap<>();
				newGateInMap.put("cfgatein", "In_Gate_In_Date");
				newGateInMap.put("cfstuffrq", "Period_From");
				newGateInMap.put("cfstufftally", "Period_From");
				newGateInMap.put("cfexpinventory", "Gate_In_Date");				
				fieldTableMap.put("newGateInDate", newGateInMap);	
		
		

		// newContainerType
		Map<String, String> newContainerTypeMap = new HashMap<>();
		newContainerTypeMap.put("cfgatein", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfstuffrq", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfstufftally", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfexpmovementreq", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfexportgatepass", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfgateout", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfexpinventory", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfequipmentactivity", "CONTAINER_TYPE");
		fieldTableMap.put("newContainerType", newContainerTypeMap);
		
		
	
				// newContainerSize
				Map<String, String> newContainerSizeMap = new HashMap<>();
				newContainerSizeMap.put("cfgatein", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfstuffrq", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfstufftally", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfexpmovementreq", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfexportgatepass", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfgateout", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfexpinventory", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfequipmentactivity", "CONTAINER_SIZE");
				fieldTableMap.put("newContainerSize", newContainerSizeMap);

		// newContainerNo
		Map<String, String> newContainerNoMap = new HashMap<>();
		newContainerNoMap.put("cfgatein", "CONTAINER_NO");
		newContainerNoMap.put("cfstuffrq", "CONTAINER_NO");
		newContainerNoMap.put("cfstufftally", "CONTAINER_NO");
		newContainerNoMap.put("cfexpmovementreq", "CONTAINER_NO");
		newContainerNoMap.put("cfexportgatepass", "CONTAINER_NO");
		newContainerNoMap.put("cfgateout", "CONTAINER_NO");
		newContainerNoMap.put("cfexpinventory", "CONTAINER_NO");
		newContainerNoMap.put("cfequipmentactivity", "CONTAINER_NO");
		fieldTableMap.put("newContainerNo", newContainerNoMap);
		
		
//		TareWight
		Map<String, String> newTareWeightMap = new HashMap<>();
		newTareWeightMap.put("cfstuffrq", "Tare_Weight");
		newTareWeightMap.put("cfstufftally", "Tare_Weight");
		newTareWeightMap.put("cfexpmovementreq", "Tare_Weight");
		newTareWeightMap.put("cfgatein", "Tare_Weight");
		fieldTableMap.put("newTareWeight", newTareWeightMap);
		

		
//		newIsoCode
		Map<String, String> newIsoCodeMap = new HashMap<>();
		newIsoCodeMap.put("cfexportgatepass", "ISO_Code");
		newIsoCodeMap.put("cfgateout", "ISO_Code");
		newIsoCodeMap.put("cfgatein", "ISO_Code");
		newIsoCodeMap.put("cfexpinventory", "ISO");
		fieldTableMap.put("newIsoCode", newIsoCodeMap);
		
		//newExporter
		Map<String, String> newExporterMap = new HashMap<>();
		newExporterMap.put("cfexportgatepass", "Importer_Name");
		newExporterMap.put("cfgateout", "Importer_Name");
		newExporterMap.put("cfgatein", "Importer_Name");
		fieldTableMap.put("newExporter", newExporterMap);
		
//newGrossWeight
		Map<String, String> newGrossWeightMap = new HashMap<>();
//		newGrossWeightMap.put("cfstuffrq", "Gross_Weight");
		newGrossWeightMap.put("cfstufftally", "Gross_Weight");
		newGrossWeightMap.put("cfexpmovementreq", "Gross_Weight");
		newGrossWeightMap.put("cfgatein", "Gross_Weight");
		fieldTableMap.put("newGrossWeight", newGrossWeightMap);
		
		
		// newCustomsSealNo
				Map<String, String> newCustomsSealNoMap = new HashMap<>();
				newCustomsSealNoMap.put("cfstufftally", "CUSTOMS_SEAL_NO");
				newCustomsSealNoMap.put("cfexpmovementreq", "CUSTOMS_SEAL_NO");
				newCustomsSealNoMap.put("cfgatein", "Customs_Seal_No");
				newCustomsSealNoMap.put("cfexportgatepass", "CUSTOMS_SEAL_NO");
				fieldTableMap.put("newCustomSealNo", newCustomsSealNoMap);

				// newAgentSealNo
				Map<String, String> newAgentSealNoMap = new HashMap<>();
				newAgentSealNoMap.put("cfstuffrq", "AGENT_SEAL_NO");
				newAgentSealNoMap.put("cfstufftally", "AGENT_SEAL_NO");
				newAgentSealNoMap.put("cfexpmovementreq", "AGENT_SEAL_NO");
				newAgentSealNoMap.put("cfgatein", "Container_Seal_No");
				newAgentSealNoMap.put("cfexportgatepass", "AGENT_SEAL_NO");
				fieldTableMap.put("newAgentSealNo", newAgentSealNoMap);
				
				
				
				// newTransporter
				fieldTableMap.put("newTransporter", Map.of("cfexportgatepass", "TRANSPORTER", "cfgateout", "TRANSPORTER", "cfgatein", "TRANSPORTER"));

				// newTransporterName
				fieldTableMap.put("newTransporterName",
						Map.of("cfexportgatepass", "TRANSPORTER_NAME", "cfgateout", "TRANSPORTER_NAME", "cfgatein", "TRANSPORTER_NAME"));

				// newDamageDetails
					fieldTableMap.put("newDamageDetails", Map.of( "cfgatein", "Damage_Details"));

					// newMovementBy
					fieldTableMap.put("newMovementBy", Map.of( "cfgatein", "Created_By"));

				

		
		// newCHA
		Map<String, String> newCHAMap = new HashMap<>();	
		newCHAMap.put("cfexportgatepass", "CHA");
		newCHAMap.put("cfexpinventory", "CHA");
		newCHAMap.put("cfgateout", "CHA");
		newCHAMap.put("cfgatein", "CHA");
		
		fieldTableMap.put("newCHA", newCHAMap);
		
		
		// newShippingLine
				Map<String, String> newShippingLineMap = new HashMap<>();				
				newShippingLineMap.put("cfexpmovementreq", "Shipping_Line");
				newShippingLineMap.put("cfexportgatepass", "SL");
				newShippingLineMap.put("cfexpinventory", "SL");
				newShippingLineMap.put("cfgateout", "SL");
				newShippingLineMap.put("cfgatein", "SL");
				fieldTableMap.put("newShippingLine", newShippingLineMap);
				
				
				// newShippingAgent
				Map<String, String> newShippingAgentMap = new HashMap<>();				
				newShippingAgentMap.put("cfexpmovementreq", "Shipping_Agent");
				newShippingAgentMap.put("cfexportgatepass", "SA");
				newShippingAgentMap.put("cfexpinventory", "SA");
				newShippingAgentMap.put("cfgateout", "SA");
				newShippingAgentMap.put("cfgatein", "SA");
				fieldTableMap.put("newShippingAgent", newShippingAgentMap);
		
		

		// Iterate through fields and update tables if values have changed
		fieldTableMap.forEach((field, tableColumnMap) -> {
			try {
				// Use reflection to get the new and old values dynamically
				Field newField = ContainerGateIn.class.getDeclaredField(field);
				Field oldField = ContainerGateIn.class.getDeclaredField(field.replace("new", "old"));
				newField.setAccessible(true);
				oldField.setAccessible(true);

				Object newValue = newField.get(shippingBillDTO);
				Object oldValue = oldField.get(shippingBillDTO);
				AtomicReference<Object> updatedValue = new AtomicReference<>(newValue); 
				if (newValue != null && !newValue.equals(oldValue)) {
					
					System.out.println( "  newValue " + newValue + " oldValue " + oldValue + " field " + field);

					updatedFieldsCount.incrementAndGet();

					String autoIncrementAuditId = processService.autoExportStuffingId(companyId, branchId, "P05085");

					ExportAudit audit = new ExportAudit();
					audit.setCompanyId(companyId);
					audit.setBranchId(branchId);
					audit.setApprovedBy(userId);
					audit.setApprovedDate(currentDate);
					audit.setCreatedBy(userId);
					audit.setCreatedDate(currentDate);
					audit.setEditedBy(userId);
					audit.setEditedDate(currentDate);
					audit.setAuditId(autoIncrementAuditId);
					audit.setAuditDate(currentDate);
					audit.setProfitcentreId(profitCentreIdExport);
					audit.setSbNo(shippingBillDTO.getSbNo());
					audit.setContainerNo(shippingBillDTO.getOldContainerNo());
					audit.setAuditRemark(shippingBillDTO.getAuditremarks());
					audit.setField(removePrefix(field));
					audit.setOldValue(oldValue != null ? oldValue.toString() : "");
					audit.setNewValue(newValue != null ? newValue.toString() : "");
					audit.setStatus("A");
					audit.setTableName("Container Gate In Details");

					auditRepo.save(audit);

					if ("containerNo".equalsIgnoreCase(removePrefix(field))) {
						newContainerNo.set((String) newValue);

					}

					System.out.println( "  tableColumnMap " + tableColumnMap);
					tableColumnMap.forEach((table, column) -> {						
						
						updateContainerGateIn5thTable(table, column, updatedValue.get(), oldValue, companyId, branchId, 
								shippingBillDTO.getSbNo(), tableWhereConditionMap, shippingBillDTO.getGateOutId(), shippingBillDTO.getGatePassNo(), shippingBillDTO.getOldContainerNo(), 
								shippingBillDTO.getGateInId(), shippingBillDTO.getStuffTallyId(), shippingBillDTO.getMovementReqId(), shippingBillDTO.getStuffReqId());
						
					});
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				
				System.out.println( "  IllegalAccessException e " + e);
				e.printStackTrace();
			}
		});

		
		result.put("updatedFieldsCount", updatedFieldsCount.get());
		result.put("sbNo", newSbNo.get());
		result.put("containerNo", newContainerNo.get());

		return result;
	}

	private void updateContainerGateIn5thTable(String tableName, String columnName, Object newValue, Object oldValue,
			String companyId, String branchId, String sbNoOld,
			Map<String, String> tableWhereConditionMap, String gateOutId, String gatePassNo, String containerNo,
			String gateInId, String stuffTallyId, String movementReqId, String stuffReqId) {
		try {
// Retrieve the WHERE clause from the map
			String whereClause = tableWhereConditionMap.get(tableName);

			if (whereClause == null || whereClause.isEmpty()) {
				throw new IllegalArgumentException("Where clause is missing for table: " + tableName);
			}

// Construct the query string
			String queryStr = "UPDATE " + tableName + " SET " + columnName + " = :newValue " + whereClause;
			System.out.println("Final Query: " + queryStr);
			System.out.println("gatePassNo: " + gatePassNo);

			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter("newValue", newValue);

// Set dynamic parameters based on whereClause
			if (whereClause.contains(":companyId")) {
				query.setParameter("companyId", companyId);
			}
			if (whereClause.contains(":branchId")) {
				query.setParameter("branchId", branchId);
			}
			
			if (whereClause.contains(":gateOutId")) {
				query.setParameter("gateOutId", gateOutId);
			}
			if (whereClause.contains(":containerNoOld")) {
				query.setParameter("containerNoOld", containerNo);
			}
			
			if (whereClause.contains(":stuffReqId")) {
				query.setParameter("stuffReqId", stuffReqId);
			}
			
			if (whereClause.contains(":gatePassNo")) {
				query.setParameter("gatePassNo", gatePassNo);
			}
			if (whereClause.contains(":gateInId")) {
				query.setParameter("gateInId", gateInId);
			}
			
			if (whereClause.contains(":stuffTallyId")) {
				query.setParameter("stuffTallyId", stuffTallyId);
			}
			if (whereClause.contains(":movementReqId")) {
				query.setParameter("movementReqId", movementReqId);
			}

// Execute update query
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error in Query GateOut: " + e);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Map<String, Object> updateBackToTownOut(BackToTownOutDTO shippingBillDTO, String companyId,
			String branchId, String userId, Date currentDate, String oldSbNo) {
		// Extract common parameters
		

		AtomicInteger updatedFieldsCount = new AtomicInteger(0);
		AtomicReference<String> newSbNo = new AtomicReference<>(oldSbNo);

		Map<String, String> tableWhereConditionMap = new HashMap<>();

		// Define fields and their corresponding tables and columns
		Map<String, Map<String, String>> fieldTableMap = new HashMap<>();
		List<String> sbTransIdTables = List.of("cfexportgatepass");

		List<String> erpDocrefIdTables = List.of("cfgateout");

		// Assign the correct WHERE conditions
		sbTransIdTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE SB_Trans_Id = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND SB_no = :sbNo AND Gate_Pass_Id = :gatePassNo"));

		
		// Assign the correct WHERE conditions
		erpDocrefIdTables.forEach(table -> tableWhereConditionMap.put(table,
						"WHERE ERP_Doc_Ref_No = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND Gate_Out_Id = :gateOutId"));

		
		
		// newGateOutDate
		fieldTableMap.put("newGateOutDate", Map.of("cfexportgatepass", "GATE_PASS_DATE",
				"cfgateout", "GATE_OUT_DATE"));

		// newTransporterName
		fieldTableMap.put("newTruckNo",
				Map.of("cfexportgatepass", "VEHICLE_NO",
						"cfgateout", "VEHICLE_NO"));

		// newTruckNo
		fieldTableMap.put("newQtyTakenOut", Map.of("cfgateout", "Qty_Taken_Out"));

		// Iterate through fields and update tables if values have changed
		fieldTableMap.forEach((field, tableColumnMap) -> {
			try {
				// Use reflection to get the new and old values dynamically
				Field newField = BackToTownOutDTO.class.getDeclaredField(field);
				Field oldField = BackToTownOutDTO.class.getDeclaredField(field.replace("new", "old"));
				newField.setAccessible(true);
				oldField.setAccessible(true);

				Object newValue = newField.get(shippingBillDTO);
				Object oldValue = oldField.get(shippingBillDTO);

				if (newValue != null && !newValue.equals(oldValue)) {

					updatedFieldsCount.incrementAndGet();

					String autoIncrementAuditId = processService.autoExportStuffingId(companyId, branchId, "P05085");

					ExportAudit audit = new ExportAudit();
					audit.setCompanyId(companyId);
					audit.setBranchId(branchId);
					audit.setApprovedBy(userId);
					audit.setApprovedDate(currentDate);
					audit.setCreatedBy(userId);
					audit.setCreatedDate(currentDate);
					audit.setEditedBy(userId);
					audit.setEditedDate(currentDate);
					audit.setAuditId(autoIncrementAuditId);
					audit.setAuditDate(currentDate);
					audit.setProfitcentreId(profitCentreIdExport);
					audit.setSbNo(shippingBillDTO.getSbNo());
					audit.setContainerNo(shippingBillDTO.getBackToTownTransId());
					audit.setAuditRemark(shippingBillDTO.getAuditremarks());
					audit.setField(removePrefix(field));
					audit.setOldValue(oldValue != null ? oldValue.toString() : "");
					audit.setNewValue(newValue != null ? newValue.toString() : "");
					audit.setStatus("A");
					audit.setTableName("Back To Town Cargo-Gateout Details");

					auditRepo.save(audit);

					tableColumnMap.forEach((table, column) -> {
						updateBackToTownOut9thTable(table, column, newValue, oldValue, shippingBillDTO.getSbTransId(), companyId, branchId,
								shippingBillDTO.getSbNo(), tableWhereConditionMap, shippingBillDTO.getBackToTownTransId(), shippingBillDTO.getGatePassNo(), shippingBillDTO.getGateOutId());
					});
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		Map<String, Object> result = new HashMap<>();
		result.put("updatedFieldsCount", updatedFieldsCount.get());
		result.put("sbNo", newSbNo.get());

		return result;
	}

	private void updateBackToTownOut9thTable(String tableName, String columnName, Object newValue, Object oldValue,
			String sbTransId, String companyId, String branchId, String sbNo,
			Map<String, String> tableWhereConditionMap, String bakToTownTransId, String gatePassNo, String gateOutId) {
		try {
			String whereClause = tableWhereConditionMap.get(tableName);

			if (whereClause == null || whereClause.isEmpty()) {
				throw new IllegalArgumentException("whereClause is missing for table: " + tableName);
			}

			String queryStr = "UPDATE " + tableName + " SET " + columnName + " = :newValue " + whereClause;
			System.out.println("Final Query: " + queryStr);

			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter("newValue", newValue);
			query.setParameter("companyId", companyId);
			query.setParameter("branchId", branchId);
			query.setParameter("sbTransId", sbTransId);
			
			if (whereClause.contains(":sbNo")) {
				query.setParameter("sbNo", sbNo);
			}
			
			if (whereClause.contains(":gatePassNo")) {
				query.setParameter("gatePassNo", gatePassNo);
			}
			
			if (whereClause.contains(":gateOutId")) {
				query.setParameter("gateOutId", gateOutId);
			}
			
			

			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error in Query GateOut: " + e);
		}
	}

	
	
	
	
	
	
	
	public Map<String, Object> updateBackToTown(BackToTownDTO shippingBillDTO, String companyId,
			String branchId, String userId, Date currentDate, String oldSbNo) {
		// Extract common parameters
		

		AtomicInteger updatedFieldsCount = new AtomicInteger(0);
		AtomicReference<String> newSbNo = new AtomicReference<>(oldSbNo);

		Map<String, String> tableWhereConditionMap = new HashMap<>();

		// Define fields and their corresponding tables and columns
		Map<String, Map<String, String>> fieldTableMap = new HashMap<>();
		List<String> sbTransIdTables = List.of("cfbacktotown");

	

		// Assign the correct WHERE conditions
		sbTransIdTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE SB_Trans_Id = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND SB_no = :sbNo AND Back_To_Town_Trans_Id = :bakToTownTransId"));

		
		// newTransporter
		fieldTableMap.put("newBackToTownPackages", Map.of("cfbacktotown", "BACK_TO_TOWN_PACKAGES"));

		// newTransporterName
		fieldTableMap.put("newBackToTownTransDate",
				Map.of("cfbacktotown", "BACK_TO_TOWN_TRANS_DATE"));

		// newTruckNo
		fieldTableMap.put("newBackToTownWeight", Map.of("cfbacktotown", "BACK_TO_TOWN_WEIGHT"));

		// Iterate through fields and update tables if values have changed
		fieldTableMap.forEach((field, tableColumnMap) -> {
			try {
				// Use reflection to get the new and old values dynamically
				Field newField = BackToTownDTO.class.getDeclaredField(field);
				Field oldField = BackToTownDTO.class.getDeclaredField(field.replace("new", "old"));
				newField.setAccessible(true);
				oldField.setAccessible(true);

				Object newValue = newField.get(shippingBillDTO);
				Object oldValue = oldField.get(shippingBillDTO);

				if (newValue != null && !newValue.equals(oldValue)) {

					updatedFieldsCount.incrementAndGet();

					String autoIncrementAuditId = processService.autoExportStuffingId(companyId, branchId, "P05085");

					ExportAudit audit = new ExportAudit();
					audit.setCompanyId(companyId);
					audit.setBranchId(branchId);
					audit.setApprovedBy(userId);
					audit.setApprovedDate(currentDate);
					audit.setCreatedBy(userId);
					audit.setCreatedDate(currentDate);
					audit.setEditedBy(userId);
					audit.setEditedDate(currentDate);
					audit.setAuditId(autoIncrementAuditId);
					audit.setAuditDate(currentDate);
					audit.setProfitcentreId(profitCentreIdExport);
					audit.setSbNo(shippingBillDTO.getSbNo());
					audit.setContainerNo(shippingBillDTO.getBackToTownTransId());
					audit.setAuditRemark(shippingBillDTO.getAuditremarks());
					audit.setField(removePrefix(field));
					audit.setOldValue(oldValue != null ? oldValue.toString() : "");
					audit.setNewValue(newValue != null ? newValue.toString() : "");
					audit.setStatus("A");
					audit.setTableName("Back To Town Cargo-WO Details");

					auditRepo.save(audit);

					tableColumnMap.forEach((table, column) -> {
						updateBackToTown8thTable(table, column, newValue, oldValue, shippingBillDTO.getSbTransId(), companyId, branchId,
								shippingBillDTO.getSbNo(), tableWhereConditionMap, shippingBillDTO.getBackToTownTransId());
					});
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		Map<String, Object> result = new HashMap<>();
		result.put("updatedFieldsCount", updatedFieldsCount.get());
		result.put("sbNo", newSbNo.get());

		return result;
	}

	private void updateBackToTown8thTable(String tableName, String columnName, Object newValue, Object oldValue,
			String sbTransId, String companyId, String branchId, String sbNo,
			Map<String, String> tableWhereConditionMap, String bakToTownTransId) {
		try {
			String whereClause = tableWhereConditionMap.get(tableName);

			if (whereClause == null || whereClause.isEmpty()) {
				throw new IllegalArgumentException("whereClause is missing for table: " + tableName);
			}

			String queryStr = "UPDATE " + tableName + " SET " + columnName + " = :newValue " + whereClause;
			System.out.println("Final Query: " + queryStr);

			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter("newValue", newValue);
			query.setParameter("companyId", companyId);
			query.setParameter("branchId", branchId);
			query.setParameter("sbTransId", sbTransId);
			query.setParameter("sbNo", sbNo);
			query.setParameter("bakToTownTransId", bakToTownTransId);

		

			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error in Query GateOut: " + e);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Transactional
	public Map<String, Object> updateShippingBillDetails(ShippingBillDTO shippingBillDTO, String companyId,
			String branchId, String userId, Date currentDate) {
		
		 Map<String, Object> result = new HashMap<>();
		// Extract common parameters
		String sbTransId = shippingBillDTO.getSbtransid();
		String oldSbNo = shippingBillDTO.getOldSbNo();
		AtomicInteger updatedFieldsCount = new AtomicInteger(0);
		AtomicReference<String> newSbNo = new AtomicReference<>(oldSbNo);

		
		if (shippingBillDTO.getNewSbNo() != null && !shippingBillDTO.getNewSbNo().equals(shippingBillDTO.getOldSbNo())) {
		        boolean existsBySbNo = entryCargoRepo.existsBySbNo(companyId, branchId, shippingBillDTO.getFinYear(),
		                profitCentreIdExport, shippingBillDTO.getSbtransid(), shippingBillDTO.getNewSbNo());

		        if (existsBySbNo) {
		        	result.put("updatedFieldsCount", updatedFieldsCount.get());
		    		result.put("sbNo", newSbNo.get());
		            result.put("error", "Duplicate SB No found: " + shippingBillDTO.getNewSbNo());
		            return result; // 🔴 Stop execution immediately, no updates will happen
		        }
		    }
		
		
		
		
		
		
		
		
		
		
		
		
		
		Map<String, String> tableWhereConditionMap = new HashMap<>();

		// Define fields and their corresponding tables and columns
		Map<String, Map<String, String>> fieldTableMap = new HashMap<>();
		List<String> sbTransIdTables = List.of("cfsb", "cfsbcrg", "cfcrtg", "cfstuffrq", "cfstufftally",
				"cfexpmovementreq", "cfexportgatepass", "cfexpinventory", "cfbacktotown");

		List<String> erpDocRefNoTables = List.of("cfgatein", "cfgateout", "cfssrdtl");

		// Assign the correct WHERE conditions
		sbTransIdTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE SB_Trans_Id = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND SB_No = :sbNoOld"));

		erpDocRefNoTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE ERP_Doc_Ref_no = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND Doc_Ref_no = :sbNoOld"));

		// newSbNo
		Map<String, String> newSbNoMap = new HashMap<>();
		newSbNoMap.put("cfsb", "SB_No");
		newSbNoMap.put("cfsbcrg", "SB_No");
		newSbNoMap.put("cfgatein", "Doc_Ref_No");
		newSbNoMap.put("cfcrtg", "SB_No");
		newSbNoMap.put("cfstuffrq", "SB_No");
		newSbNoMap.put("cfstufftally", "SB_No");
		newSbNoMap.put("cfexpmovementreq", "SB_No");
		newSbNoMap.put("cfexportgatepass", "SB_No");
		newSbNoMap.put("cfgateout", "Doc_Ref_No");
		newSbNoMap.put("cfexpinventory", "SB_No");
		newSbNoMap.put("cfssrdtl", "Doc_ref_no");
		newSbNoMap.put("cfbacktotown", "SB_No");
		fieldTableMap.put("newSbNo", newSbNoMap);

		// newSbDate
		Map<String, String> newSbDateMap = new HashMap<>();
		newSbDateMap.put("cfsb", "SB_Date");
		newSbDateMap.put("cfsbcrg", "SB_Date");
		newSbDateMap.put("cfgatein", "Doc_Ref_Date");
		newSbDateMap.put("cfcrtg", "SB_Date");
		newSbDateMap.put("cfstuffrq", "SB_Date");
		newSbDateMap.put("cfstufftally", "SB_Date");
		newSbDateMap.put("cfexpmovementreq", "SB_Date");
		newSbDateMap.put("cfexportgatepass", "SB_Date");
		newSbDateMap.put("cfgateout", "Doc_Ref_Date");
		newSbDateMap.put("cfssrdtl", "Doc_Ref_Date");
		newSbDateMap.put("cfbacktotown", "SB_Date");
		fieldTableMap.put("newSbDate", newSbDateMap);

		// newNoOfPackages
		fieldTableMap.put("newNoOfPackages", Map.of("cfsb", "Total_Packages", "cfsbcrg", "No_Of_Packages", "cfgatein",
				"Actual_No_Of_Packages", "cfstuffrq", "No_Of_Packages"));

		// newGrossWeight
		fieldTableMap.put("newGrossWeight",
				Map.of("cfsb", "Total_Gross_Weight", "cfsbcrg", "Gross_Weight", "cfgatein", "Gross_Weight"));

		// newPackagesType
		fieldTableMap.put("newPackagesType", Map.of("cfsbcrg", "Type_Of_Package"));

		// newExporter
		Map<String, String> newExporterMap = new HashMap<>();
		newExporterMap.put("cfsb", "Exporter_Name");
		newExporterMap.put("cfstuffrq", "Exporter_Name");
		newExporterMap.put("cfstufftally", "Exporter_Name");
		newExporterMap.put("cfexportgatepass", "Importer_Name");
		fieldTableMap.put("newExporter", newExporterMap);

		// newExporter
		Map<String, String> newExporterMapId = new HashMap<>();
		newExporterMapId.put("cfsb", "Exporter_Id");
		fieldTableMap.put("newExporterId", newExporterMapId);

		// newExporter
		Map<String, String> newExporterAddress1Map = new HashMap<>();
		newExporterAddress1Map.put("cfsb", "Exporter_Address1");
		newExporterAddress1Map.put("cfexportgatepass", "importer_address1");
		fieldTableMap.put("oldExporterAddress1", newExporterAddress1Map);

		// newExporter
		Map<String, String> newExporterAddress2Map = new HashMap<>();
		newExporterAddress2Map.put("cfsb", "Exporter_Address2");
		newExporterAddress2Map.put("cfexportgatepass", "importer_address2");
		fieldTableMap.put("oldExporterAddress2", newExporterAddress2Map);

		// newExporter
		Map<String, String> newExporterAddress3Map = new HashMap<>();
		newExporterAddress3Map.put("cfsb", "Exporter_Address3");
		newExporterAddress3Map.put("cfexportgatepass", "importer_address3");
		fieldTableMap.put("oldExporterAddress3", newExporterAddress3Map);

		// newExporter
		Map<String, String> newExporterSRNoMap = new HashMap<>();
		newExporterSRNoMap.put("cfsb", "Sr_No");
		fieldTableMap.put("newSrNo", newExporterSRNoMap);

		// newExporter
		Map<String, String> newExporterGstNoMap = new HashMap<>();
		newExporterGstNoMap.put("cfsb", "GST_No");
		fieldTableMap.put("newGstNo", newExporterGstNoMap);

		// newExporter
		Map<String, String> newExporterStateMap = new HashMap<>();
		newExporterStateMap.put("cfsb", "State");
		fieldTableMap.put("newState", newExporterStateMap);

		// newExporter
		Map<String, String> newExporterIECCodeMap = new HashMap<>();
		newExporterIECCodeMap.put("cfsb", "IEC_Code");
		fieldTableMap.put("newIecCode", newExporterIECCodeMap);

		// newCargoType
		fieldTableMap.put("newCargoType", Map.of("cfsb", "sb_type", "cfsbcrg", "sb_type"));

		// newFOB
		fieldTableMap.put("newFOB", Map.of("cfsbcrg", "FOB", "cfstufftally", "FOB"));

		// newCommodityId
		Map<String, String> newCommodityIdMap = new HashMap<>();
		newCommodityIdMap.put("cfsbcrg", "Commodity");
		newCommodityIdMap.put("cfgatein", "Commodity_Description");
		newCommodityIdMap.put("cfcrtg", "Commodity");
		newCommodityIdMap.put("cfstuffrq", "Cargo_Description");
		newCommodityIdMap.put("cfstufftally", "Commodity");
		newCommodityIdMap.put("cfbacktotown", "Commodity");
		fieldTableMap.put("newCommodityId", newCommodityIdMap);

		// newNoOfMarks
		fieldTableMap.put("newNoOfMarks", Map.of("cfsbcrg", "Number_Of_Marks"));

		// newConsigneeName
		fieldTableMap.put("newConsigneeName", Map.of("cfsb", "Consignee_Name", "cfstufftally", "Consignee"));

		// newCHA
		Map<String, String> newCHAMap = new HashMap<>();
		newCHAMap.put("cfsb", "CHA");
		newCHAMap.put("cfgatein", "CHA");
		newCHAMap.put("cfssrdtl", "CHA");
		fieldTableMap.put("newCHA", newCHAMap);

		// newOnAcc
		Map<String, String> newOnAccMap = new HashMap<>();
		newOnAccMap.put("cfsb", "On_Account_Of");
		newOnAccMap.put("cfgatein", "On_Account_Of");
		newOnAccMap.put("cfcrtg", "On_Account_Of");
		newOnAccMap.put("cfstufftally", "On_Account_Of");
		newOnAccMap.put("cfexpmovementreq", "On_Account_Of");
		newOnAccMap.put("cfssrdtl", "Acc_Id");
		newOnAccMap.put("cfbacktotown", "On_Account_Of");
		fieldTableMap.put("newOnAcc", newOnAccMap);

		// Iterate through fields and update tables if values have changed
		fieldTableMap.forEach((field, tableColumnMap) -> {
			try {
				// Use reflection to get the new and old values dynamically
				Field newField = ShippingBillDTO.class.getDeclaredField(field);
				Field oldField = ShippingBillDTO.class.getDeclaredField(field.replace("new", "old"));
				newField.setAccessible(true);
				oldField.setAccessible(true);

				Object newValue = newField.get(shippingBillDTO);
				Object oldValue = oldField.get(shippingBillDTO);

				if (newValue != null && !newValue.equals(oldValue)) {

					updatedFieldsCount.incrementAndGet();

					String autoIncrementAuditId = processService.autoExportStuffingId(companyId, branchId, "P05085");

					ExportAudit audit = new ExportAudit();
					audit.setCompanyId(companyId);
					audit.setBranchId(branchId);
					audit.setApprovedBy(userId);
					audit.setApprovedDate(currentDate);
					audit.setCreatedBy(userId);
					audit.setCreatedDate(currentDate);
					audit.setEditedBy(userId);
					audit.setEditedDate(currentDate);
					audit.setAuditId(autoIncrementAuditId);
					audit.setAuditDate(currentDate);
					audit.setProfitcentreId(profitCentreIdExport);
					audit.setSbNo(oldSbNo);
					audit.setAuditRemark(shippingBillDTO.getAuditremarks());
					audit.setField(removePrefix(field));
					audit.setOldValue(oldValue != null ? oldValue.toString() : "");
					audit.setNewValue(newValue != null ? newValue.toString() : "");
					audit.setStatus("A");
					audit.setTableName("Shipping Bill Details");

					auditRepo.save(audit);

					if ("sbNo".equalsIgnoreCase(removePrefix(field))) {
						newSbNo.set((String) newValue);
					
					}

					tableColumnMap.forEach((table, column) -> {
						updateSBDetailFirstTable(table, column, newValue, oldValue, sbTransId, companyId, branchId,
								oldSbNo, tableWhereConditionMap);
					});
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		result.put("updatedFieldsCount", updatedFieldsCount.get());
		result.put("sbNo", newSbNo.get());

		return result;
	}

	@Transactional
	private void updateSBDetailFirstTable(String tableName, String columnName, Object newValue, Object oldValue,
			String sbTransId, String companyId, String branchId, String sbNoOld,
			Map<String, String> tableWhereConditionMap) {
		String whereClause = tableWhereConditionMap.getOrDefault(tableName,
				"WHERE SB_Trans_Id = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND SB_No = :sbNoOld");

		String queryStr = "UPDATE " + tableName + " SET " + columnName + " = :newValue " + whereClause;

		Query query = entityManager.createNativeQuery(queryStr);
		query.setParameter("newValue", newValue);
		query.setParameter("sbTransId", sbTransId);
		query.setParameter("companyId", companyId);
		query.setParameter("branchId", branchId);
		query.setParameter("sbNoOld", sbNoOld);

		query.executeUpdate();
	}

//	Gate In Jo

	@Transactional
	public Map<String, Object> updateGateInJO(GateInJoDTO gateInJoDTO, String companyId, String branchId, String userId,
			Date currentDate, String oldSbNo) {
		// Extract common parameterTable
		String sbTransId = gateInJoDTO.getSbtransId();
		String gateInId = gateInJoDTO.getGateInId();
		AtomicInteger updatedFieldsCount = new AtomicInteger(0);
		AtomicReference<String> newSbNo = new AtomicReference<>(oldSbNo);

		Map<String, String> tableWhereConditionMap = new HashMap<>();

		// Define fields and their corresponding tables and columns
		Map<String, Map<String, String>> fieldTableMap = new HashMap<>();
		List<String> sbTransIdTables = List.of("cfcrtg");

		List<String> erpDocRefNoTables = List.of("cfgatein");

		// Assign the correct WHERE conditions
		sbTransIdTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE SB_Trans_Id = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND Gate_In_Id = :gateInId"));

		erpDocRefNoTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE ERP_Doc_Ref_no = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND Gate_In_Id = :gateInId"));

		// vehicleNo
		fieldTableMap.put("newVehicleNo", Map.of("cfgatein", "Vehicle_No", "cfcrtg", "Vehicle_No"

		));

		// gateInDate
		fieldTableMap.put("newGateInDate", Map.of("cfgatein", "In_Gate_In_Date", "cfcrtg", "Gate_In_Date"));

		// cartingTransDate
		fieldTableMap.put("newCartingTransDate", Map.of("cfcrtg", "CARTING_TRANS_DATE"));

		// Iterate through fields and update tables if values have changed
		fieldTableMap.forEach((field, tableColumnMap) -> {
			try {
				// Use reflection to get the new and old values dynamically
				Field newField = GateInJoDTO.class.getDeclaredField(field);
				Field oldField = GateInJoDTO.class.getDeclaredField(field.replace("new", "old"));
				newField.setAccessible(true);
				oldField.setAccessible(true);

				Object newValue = newField.get(gateInJoDTO);
				Object oldValue = oldField.get(gateInJoDTO);

				if (newValue != null && !newValue.equals(oldValue)) {

					updatedFieldsCount.incrementAndGet();

					String autoIncrementAuditId = processService.autoExportStuffingId(companyId, branchId, "P05085");

					ExportAudit audit = new ExportAudit();
					audit.setCompanyId(companyId);
					audit.setBranchId(branchId);
					audit.setApprovedBy(userId);
					audit.setApprovedDate(currentDate);
					audit.setCreatedBy(userId);
					audit.setCreatedDate(currentDate);
					audit.setEditedBy(userId);
					audit.setEditedDate(currentDate);
					audit.setAuditId(autoIncrementAuditId);
					audit.setAuditDate(currentDate);
					audit.setProfitcentreId(profitCentreIdExport);
					audit.setSbNo(oldSbNo);
					audit.setAuditRemark(gateInJoDTO.getAuditremarks());
					audit.setField(removePrefix(field));
					audit.setOldValue(oldValue != null ? oldValue.toString() : "");
					audit.setNewValue(newValue != null ? newValue.toString() : "");
					audit.setStatus("A");
					audit.setTableName("Gate In Jo");

					auditRepo.save(audit);

					tableColumnMap.forEach((table, column) -> {
						updateGateInJOSecondTable(table, column, newValue, oldValue, sbTransId, companyId, branchId,
								gateInId, tableWhereConditionMap);
					});
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		Map<String, Object> result = new HashMap<>();
		result.put("updatedFieldsCount", updatedFieldsCount.get());
		result.put("sbNo", newSbNo.get());

		return result;
	}

	@Transactional
	private void updateGateInJOSecondTable(String tableName, String columnName, Object newValue, Object oldValue,
			String sbTransId, String companyId, String branchId, String gateInId,
			Map<String, String> tableWhereConditionMap) {
		String whereClause = tableWhereConditionMap.getOrDefault(tableName,
				"WHERE SB_Trans_Id = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND Gate_In_Id = :gateInId");

		String queryStr = "UPDATE " + tableName + " SET " + columnName + " = :newValue " + whereClause;

		Query query = entityManager.createNativeQuery(queryStr);
		query.setParameter("newValue", newValue);
		query.setParameter("sbTransId", sbTransId);
		query.setParameter("companyId", companyId);
		query.setParameter("branchId", branchId);
		query.setParameter("gateInId", gateInId);

		query.executeUpdate();
	}

//	Gate In JO Detail
	@Transactional
	public Map<String, Object> updateGateInJODetail(GateInJoDetailDTO gateInJoDTO, String companyId, String branchId,
			String userId, Date currentDate, String oldSbNo) {
		// Extract common parameterTable
		String sbTransId = gateInJoDTO.getSbTransId();
		String gateInId = gateInJoDTO.getGateInId();
		String cartingTransId = gateInJoDTO.getCartingTransId();
		String cartingLineId = gateInJoDTO.getCartingLineId();
		int subSrNo = gateInJoDTO.getSubSrNo();

		AtomicInteger updatedFieldsCount = new AtomicInteger(0);
		AtomicReference<String> newSbNo = new AtomicReference<>(oldSbNo);

		Map<String, String> tableWhereConditionMap = new HashMap<>();

		// Define fields and their corresponding tables and columns
		Map<String, Map<String, String>> fieldTableMap = new HashMap<>();

		List<String> erpDocRefNoTables = List.of("cfimpexpgrid");

		erpDocRefNoTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE Company_Id = :companyId AND Branch_Id = :branchId AND Process_Trans_Id = :cartingTransId AND Line_No = :cartingLineId AND Sub_Sr_No = :subSrNo"));

		// newCartedpPkg
		fieldTableMap.put("newCartedpPkg", Map.of("cfimpexpgrid", "Yard_Packages"));

		// newCartedWt
		fieldTableMap.put("newCartedWt", Map.of("cfimpexpgrid", "Yard_Weight"));

		// Iterate through fields and update tables if values have changed
		fieldTableMap.forEach((field, tableColumnMap) -> {
			try {
				// Use reflection to get the new and old values dynamically
				Field newField = GateInJoDetailDTO.class.getDeclaredField(field);
				Field oldField = GateInJoDetailDTO.class.getDeclaredField(field.replace("new", "old"));
				newField.setAccessible(true);
				oldField.setAccessible(true);

				Object newValue = newField.get(gateInJoDTO);
				Object oldValue = oldField.get(gateInJoDTO);

				if (newValue != null && !newValue.equals(oldValue)) {

					updatedFieldsCount.incrementAndGet();

					String autoIncrementAuditId = processService.autoExportStuffingId(companyId, branchId, "P05085");

					ExportAudit audit = new ExportAudit();
					audit.setCompanyId(companyId);
					audit.setBranchId(branchId);
					audit.setApprovedBy(userId);
					audit.setApprovedDate(currentDate);
					audit.setCreatedBy(userId);
					audit.setCreatedDate(currentDate);
					audit.setEditedBy(userId);
					audit.setEditedDate(currentDate);
					audit.setAuditId(autoIncrementAuditId);
					audit.setAuditDate(currentDate);
					audit.setProfitcentreId(profitCentreIdExport);
					audit.setSbNo(oldSbNo);
					audit.setAuditRemark(gateInJoDTO.getAuditremarks());
					audit.setField(removePrefix(field));
					audit.setOldValue(oldValue != null ? oldValue.toString() : "");
					audit.setNewValue(newValue != null ? newValue.toString() : "");
					audit.setStatus("A");
					audit.setTableName("Gate In Jo Detail");

					auditRepo.save(audit);

					System.out.println(" tableColumnMap 3 : \n" + tableColumnMap + " \n updatedFieldsCount.get() "
							+ updatedFieldsCount.get());
					tableColumnMap.forEach((table, column) -> {

						System.out.println(" 101 " + table + " table " + column + " column" + " newValue" + newValue
								+ " oldValue " + oldValue);
						updateGateInJODetailThirdTable(table, column, newValue, oldValue, sbTransId, companyId,
								branchId, gateInId, tableWhereConditionMap, cartingTransId, cartingLineId, subSrNo);
					});
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				System.out.println(" errro 3 : \n" + e);
				e.printStackTrace();
			}
		});

		Map<String, Object> result = new HashMap<>();
		result.put("updatedFieldsCount", updatedFieldsCount.get());
		result.put("sbNo", newSbNo.get());

		return result;
	}

	@Transactional
	private void updateGateInJODetailThirdTable(String tableName, String columnName, Object newValue, Object oldValue,
			String sbTransId, String companyId, String branchId, String gateInId,
			Map<String, String> tableWhereConditionMap, String cartingTransId, String cartingLineId, int subSrNo) {

		try {

			String whereClause = tableWhereConditionMap.getOrDefault(tableName,
					"WHERE Company_Id = :companyId AND Branch_Id = :branchId AND Process_Trans_Id = :cartingTransId AND Line_No = :cartingLineId AND Sub_Sr_No = :subSrNo");

			String queryStr = "UPDATE " + tableName + " SET " + columnName + " = :newValue " + whereClause;

			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter("newValue", newValue);
			query.setParameter("companyId", companyId);
			query.setParameter("branchId", branchId);

			query.setParameter("cartingTransId", cartingTransId);
			query.setParameter("cartingLineId", cartingLineId);
			query.setParameter("subSrNo", subSrNo);

			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(" errro in update 3 " + e);
		}
	}

//	Gate In JO Detail
	@Transactional
	public Map<String, Object> updateCartingTally(CartingTallyDTO gateInJoDTO, String companyId, String branchId,
			String userId, Date currentDate, String oldSbNo) {
		// Extract common parameterTable
		String sbTransId = gateInJoDTO.getSbTransId();
		String gateInId = gateInJoDTO.getGateInId();
		String cartingTransId = gateInJoDTO.getCartingTransId();
		String cartingLineId = gateInJoDTO.getCartingLineId();
		int subSrNo = gateInJoDTO.getSubSrNo();

		AtomicInteger updatedFieldsCount = new AtomicInteger(0);
		AtomicReference<String> newSbNo = new AtomicReference<>(oldSbNo);

		Map<String, String> tableWhereConditionMap = new HashMap<>();

		// Define fields and their corresponding tables and columns
		Map<String, Map<String, String>> fieldTableMap = new HashMap<>();

		List<String> erpDocRefNoTables = List.of("cfimpexpgrid");

		erpDocRefNoTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE Company_Id = :companyId AND Branch_Id = :branchId AND Process_Trans_Id = :cartingTransId AND Line_No = :cartingLineId AND Sub_Sr_No = :subSrNo"));

		// newBlock
		fieldTableMap.put("newBlock", Map.of("cfimpexpgrid", "Yard_Block"));

		// newYardLocation
		fieldTableMap.put("newYardLocation", Map.of("cfimpexpgrid", "Yard_Location"));

		// newCell
		fieldTableMap.put("newCell", Map.of("cfimpexpgrid", "Block_Cell_No"));

		// newCellAreaAllocated
		fieldTableMap.put("newCellAreaAllocated", Map.of("cfimpexpgrid", "Cell_Area_Allocated"));

		// Iterate through fields and update tables if values have changed
		fieldTableMap.forEach((field, tableColumnMap) -> {
			try {
				// Use reflection to get the new and old values dynamically
				Field newField = CartingTallyDTO.class.getDeclaredField(field);
				Field oldField = CartingTallyDTO.class.getDeclaredField(field.replace("new", "old"));
				newField.setAccessible(true);
				oldField.setAccessible(true);

				Object newValue = newField.get(gateInJoDTO);
				Object oldValue = oldField.get(gateInJoDTO);

				if (newValue != null && !newValue.equals(oldValue)) {

					updatedFieldsCount.incrementAndGet();

					String autoIncrementAuditId = processService.autoExportStuffingId(companyId, branchId, "P05085");

					ExportAudit audit = new ExportAudit();
					audit.setCompanyId(companyId);
					audit.setBranchId(branchId);
					audit.setApprovedBy(userId);
					audit.setApprovedDate(currentDate);
					audit.setCreatedBy(userId);
					audit.setCreatedDate(currentDate);
					audit.setEditedBy(userId);
					audit.setEditedDate(currentDate);
					audit.setAuditId(autoIncrementAuditId);
					audit.setAuditDate(currentDate);
					audit.setProfitcentreId(profitCentreIdExport);
					audit.setSbNo(oldSbNo);
					audit.setAuditRemark(gateInJoDTO.getAuditremarks());
					audit.setField(removePrefix(field));
					audit.setOldValue(oldValue != null ? oldValue.toString() : "");
					audit.setNewValue(newValue != null ? newValue.toString() : "");
					audit.setStatus("A");
					audit.setTableName("Carting Tally");

					auditRepo.save(audit);

					System.out.println(" tableColumnMap 3 : \n" + tableColumnMap + " \n updatedFieldsCount.get() "
							+ updatedFieldsCount.get());
					tableColumnMap.forEach((table, column) -> {

						if (subSrNo == 1) {
							if ("Yard_Location".equalsIgnoreCase(column)) {
								updateGateInJODetailThirdTableCarting("cfcrtg", "Grid_Loc", newValue, sbTransId,
										companyId, branchId, gateInId, cartingTransId, cartingLineId);
							}
							if ("Yard_Block".equalsIgnoreCase(column)) {
								updateGateInJODetailThirdTableCarting("cfcrtg", "Grid_Block", newValue, sbTransId,
										companyId, branchId, gateInId, cartingTransId, cartingLineId);
							}
							if ("Block_Cell_No".equalsIgnoreCase(column)) {
								updateGateInJODetailThirdTableCarting("cfcrtg", "Grid_Cell_No", newValue, sbTransId,
										companyId, branchId, gateInId, cartingTransId, cartingLineId);
							}

						}

						System.out.println(" 101 " + table + " table " + column + " column" + " newValue" + newValue
								+ " oldValue " + oldValue);
						updateGateInJODetailThirdTable(table, column, newValue, oldValue, sbTransId, companyId,
								branchId, gateInId, tableWhereConditionMap, cartingTransId, cartingLineId, subSrNo);
					});
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				System.out.println(" errro 3 : \n" + e);
				e.printStackTrace();
			}
		});

		Map<String, Object> result = new HashMap<>();
		result.put("updatedFieldsCount", updatedFieldsCount.get());
		result.put("sbNo", newSbNo.get());

		return result;
	}

	@Transactional
	private void updateGateInJODetailThirdTableCarting(String tableName, String columnName, Object newValue,
			String sbTransId, String companyId, String branchId, String gateInId, String cartingTransId,
			String cartingLineId) {

		String whereClause = "WHERE SB_Trans_Id = :sbTransId AND Company_Id = :companyId AND Branch_Id = :branchId AND Gate_In_Id = :gateInId AND Carting_Trans_Id = :cartingTransId  AND Carting_Line_Id = :cartingLineId";
		String queryStr = "UPDATE " + tableName + " SET " + columnName + " = :newValue " + whereClause;

		Query query = entityManager.createNativeQuery(queryStr);
		query.setParameter("newValue", newValue);
		query.setParameter("sbTransId", sbTransId);
		query.setParameter("companyId", companyId);
		query.setParameter("branchId", branchId);
		query.setParameter("gateInId", gateInId);

		query.setParameter("cartingTransId", cartingTransId);
		query.setParameter("cartingLineId", cartingLineId);

		query.executeUpdate();
	}

	public Map<String, Object> updateContainerGateOutDetails(ContainerGateOut shippingBillDTO, String companyId,
			String branchId, String userId, Date currentDate, String oldSbNo) {
		// Extract common parameters
		String gateInId = shippingBillDTO.getGateInId();
		String gatePassNo = shippingBillDTO.getGatePassNo();
		String containerNo = shippingBillDTO.getContainerNo();
		String gateOutId = shippingBillDTO.getGateOutId();
		String sbTransId = shippingBillDTO.getSbTransId();

		AtomicInteger updatedFieldsCount = new AtomicInteger(0);
		AtomicReference<String> newSbNo = new AtomicReference<>(oldSbNo);

		Map<String, String> tableWhereConditionMap = new HashMap<>();

		// Define fields and their corresponding tables and columns
		Map<String, Map<String, String>> fieldTableMap = new HashMap<>();
		List<String> sbTransIdTables = List.of("cfstufftally", "cfexpmovementreq");

		List<String> erpDocRefNoTables = List.of("cfgateout");

		List<String> gatePassTables = List.of("cfexportgatepass");

		// Assign the correct WHERE conditions
		sbTransIdTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE Gate_Out_Id = :gateOutId AND Company_Id = :companyId AND Branch_Id = :branchId AND Container_No = :containerNo AND Gate_Pass_No = :gatePassNo"));

		erpDocRefNoTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE Container_No = :containerNo AND Company_Id = :companyId AND Branch_Id = :branchId AND Gate_Out_Id = :gateOutId"));

		gatePassTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE Gate_Out_Id = :gateOutId AND Company_Id = :companyId AND Branch_Id = :branchId AND Container_No = :containerNo AND Gate_Pass_Id = :gatePassNo"));

		// newGateOutDate
		fieldTableMap.put("newGateOutDate", Map.of("cfstufftally", "GATE_OUT_DATE", "cfexpmovementreq", "GATE_OUT_DATE",
				"cfgateout", "GATE_OUT_DATE"));

		// newTransporter
		fieldTableMap.put("newTransporter", Map.of("cfexportgatepass", "TRANSPORTER", "cfgateout", "TRANSPORTER"));

		// newTransporterName
		fieldTableMap.put("newTransporterName",
				Map.of("cfexportgatepass", "TRANSPORTER_NAME", "cfgateout", "TRANSPORTER_NAME"));

		// newTruckNo
		fieldTableMap.put("newTruckNo", Map.of("cfexportgatepass", "VEHICLE_NO", "cfgateout", "VEHICLE_NO"));

		// Iterate through fields and update tables if values have changed
		fieldTableMap.forEach((field, tableColumnMap) -> {
			try {
				// Use reflection to get the new and old values dynamically
				Field newField = ContainerGateOut.class.getDeclaredField(field);
				Field oldField = ContainerGateOut.class.getDeclaredField(field.replace("new", "old"));
				newField.setAccessible(true);
				oldField.setAccessible(true);

				Object newValue = newField.get(shippingBillDTO);
				Object oldValue = oldField.get(shippingBillDTO);

				if (newValue != null && !newValue.equals(oldValue)) {

					updatedFieldsCount.incrementAndGet();

					String autoIncrementAuditId = processService.autoExportStuffingId(companyId, branchId, "P05085");

					ExportAudit audit = new ExportAudit();
					audit.setCompanyId(companyId);
					audit.setBranchId(branchId);
					audit.setApprovedBy(userId);
					audit.setApprovedDate(currentDate);
					audit.setCreatedBy(userId);
					audit.setCreatedDate(currentDate);
					audit.setEditedBy(userId);
					audit.setEditedDate(currentDate);
					audit.setAuditId(autoIncrementAuditId);
					audit.setAuditDate(currentDate);
					audit.setProfitcentreId(profitCentreIdExport);
					audit.setSbNo(shippingBillDTO.getSbNo());
					audit.setContainerNo(shippingBillDTO.getContainerNo());
					audit.setAuditRemark(shippingBillDTO.getAuditremarks());
					audit.setField(removePrefix(field));
					audit.setOldValue(oldValue != null ? oldValue.toString() : "");
					audit.setNewValue(newValue != null ? newValue.toString() : "");
					audit.setStatus("A");
					audit.setTableName("Export Gateout - Container Details");

					auditRepo.save(audit);

					tableColumnMap.forEach((table, column) -> {
						updateContainerGateOutTable(table, column, newValue, oldValue, sbTransId, companyId, branchId,
								oldSbNo, tableWhereConditionMap, gateOutId, gatePassNo, containerNo, gateInId);
					});
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		Map<String, Object> result = new HashMap<>();
		result.put("updatedFieldsCount", updatedFieldsCount.get());
		result.put("sbNo", newSbNo.get());

		return result;
	}

	private void updateContainerGateOutTable(String tableName, String columnName, Object newValue, Object oldValue,
			String sbTransId, String companyId, String branchId, String sbNoOld,
			Map<String, String> tableWhereConditionMap, String gateOutId, String gatePassNo, String containerNo,
			String gateInId) {
		try {
			String whereClause = tableWhereConditionMap.get(tableName);

			if (whereClause == null || whereClause.isEmpty()) {
				throw new IllegalArgumentException("whereClause is missing for table: " + tableName);
			}

			String queryStr = "UPDATE " + tableName + " SET " + columnName + " = :newValue " + whereClause;
			System.out.println("Final Query: " + queryStr);
			System.out.println("gatePassNo: " + gatePassNo);

			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter("newValue", newValue);
			query.setParameter("companyId", companyId);
			query.setParameter("branchId", branchId);
			query.setParameter("gateOutId", gateOutId);
			query.setParameter("containerNo", containerNo);

			if (whereClause.contains(":gatePassNo")) {
				query.setParameter("gatePassNo", gatePassNo);
			}

			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error in Query GateOut: " + e);
		}
	}

	@Transactional
	public Map<String, Object> updateStuffTallyDetails(StuffTally shippingBillDTO, String companyId, String branchId,
			String userId, Date currentDate, String sbNoS) {
		// Extract common parameters
		String containerNo = shippingBillDTO.getOldContainerNo();
		String oldSbNo = sbNoS;
		AtomicInteger updatedFieldsCount = new AtomicInteger(0);
		AtomicReference<String> newSbNo = new AtomicReference<>(oldSbNo);
		AtomicReference<String> newContainerNo = new AtomicReference<>(containerNo);
		
		Map<String, Object> result = new HashMap<>();
		
		
		if (shippingBillDTO.getNewContainerNo() != null && !shippingBillDTO.getNewContainerNo().equals(shippingBillDTO.getOldContainerNo())) {
	        boolean existsBySbNo = auditRepo.checkContainerInInventory(companyId, branchId, shippingBillDTO.getNewContainerNo(),
	        								shippingBillDTO.getGateInId());

	        
	        if (existsBySbNo) {
	        	result.put("updatedFieldsCount", updatedFieldsCount.get());
	    		result.put("sbNo", newSbNo.get());
	            result.put("error", "Duplicate Container No: " + shippingBillDTO.getNewContainerNo());
	            return result;
	        }
	    }
		
		
		

		Map<String, String> tableWhereConditionMap = new HashMap<>();

		// Define fields and their corresponding tables and columns
		Map<String, Map<String, String>> fieldTableMap = new HashMap<>();

		// Define tables grouped by key field
		List<String> sbTransIdTables = List.of("cfstuffrq", "cfstufftally", "cfexportgatepass",
				"cfexpinventory");

		List<String> erpDocRefNoTables = List.of("cfgatein", "cfgateout", "cfssrdtl", "cfequipmentactivity");

		// Assign WHERE conditions for SB_Trans_Id-based tables
		sbTransIdTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE SB_Trans_Id = :sbTransId AND Company_Id = :companyId "
						+ "AND Branch_Id = :branchId "
						+ "AND SB_No = :sbNoOld AND Container_No = :containerNoOld"));

		// Assign WHERE conditions for ERP_Doc_Ref_no-based tables
		erpDocRefNoTables.forEach(table -> tableWhereConditionMap.put(table,
				"WHERE ERP_Doc_Ref_no = :sbTransId AND Company_Id = :companyId "
						+ "AND Branch_Id = :branchId "
						+ "AND Doc_Ref_no = :sbNoOld AND Container_No = :containerNoOld"));

		// Special handling for cfexpcontainerdetails (uses Stuff_Tally_Id and
		// Movement_Req_Id)
		tableWhereConditionMap.put("cfexpinventory",
				"WHERE Company_Id = :companyId AND Branch_Id = :branchId "
						+ "AND Stuff_Tally_Id = :stuffTallyId "
						+ "AND Movement_Req_Id = :movementReqId AND Container_No = :containerNoOld");
		
		
		tableWhereConditionMap.put("cfexpmovementreq",
				"WHERE Company_Id = :companyId AND Branch_Id = :branchId "
						+ "AND Stuff_Tally_Id = :stuffTallyId "
						+ "AND Movement_Req_Id = :movementReqId AND Container_No = :containerNoOld");

		tableWhereConditionMap.put("cfsbcrg",
				"WHERE Company_Id = :companyId AND Branch_Id = :branchId "
						+ "AND SB_Trans_Id = :sbTransId "
						+ "AND SB_No = :sbNoOld");
		
		

		// newContainerType
		Map<String, String> newContainerTypeMap = new HashMap<>();
		newContainerTypeMap.put("cfgatein", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfstuffrq", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfstufftally", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfexpmovementreq", "CONTAINER_TYPE");
//		newContainerTypeMap.put("cfexpcontainerdetails", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfexportgatepass", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfgateout", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfexpinventory", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfssrdtl", "CONTAINER_TYPE");
		newContainerTypeMap.put("cfequipmentactivity", "CONTAINER_TYPE");
		fieldTableMap.put("newContainerType", newContainerTypeMap);
		
		
	
				// newContainerSize
				Map<String, String> newContainerSizeMap = new HashMap<>();
				newContainerSizeMap.put("cfgatein", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfstuffrq", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfstufftally", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfexpmovementreq", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfexportgatepass", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfgateout", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfexpinventory", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfssrdtl", "CONTAINER_SIZE");
				newContainerSizeMap.put("cfequipmentactivity", "CONTAINER_SIZE");
				fieldTableMap.put("newContainerSize", newContainerSizeMap);

		// newContainerNo
		Map<String, String> newContainerNoMap = new HashMap<>();
		newContainerNoMap.put("cfgatein", "CONTAINER_NO");
		newContainerNoMap.put("cfstuffrq", "CONTAINER_NO");
		newContainerNoMap.put("cfstufftally", "CONTAINER_NO");
		newContainerNoMap.put("cfexpmovementreq", "CONTAINER_NO");
		newContainerNoMap.put("cfexportgatepass", "CONTAINER_NO");
		newContainerNoMap.put("cfgateout", "CONTAINER_NO");
		newContainerNoMap.put("cfexpinventory", "CONTAINER_NO");
		newContainerNoMap.put("cfssrdtl", "CONTAINER_NO");
		newContainerNoMap.put("cfequipmentactivity", "CONTAINER_NO");
		fieldTableMap.put("newContainerNo", newContainerNoMap);

		
//		TareWight
		Map<String, String> newTareWeightMap = new HashMap<>();
		newTareWeightMap.put("cfstuffrq", "Tare_Weight");
		newTareWeightMap.put("cfstufftally", "Tare_Weight");
		newTareWeightMap.put("cfexpmovementreq", "Tare_Weight");
		fieldTableMap.put("newTareWeight", newTareWeightMap);
		
		
		Map<String, String> newStuffQtyMap = new HashMap<>();
		newStuffQtyMap.put("cfsbcrg", "Stuffed_Qty");
		newStuffQtyMap.put("cfstufftally", "Stuffed_Qty");
		fieldTableMap.put("newStuffQty", newStuffQtyMap);
		
		
		
		Map<String, String> newStuffQtyWeightMap = new HashMap<>();
		newStuffQtyWeightMap.put("cfstufftally", "Cargo_weight");
		fieldTableMap.put("newStuffQtyWeight", newStuffQtyWeightMap);
		
		
		
		// newPOD
		Map<String, String> newPODMap = new HashMap<>();
		newPODMap.put("cfstuffrq", "POD");
		newPODMap.put("cfstufftally", "POD");
		newPODMap.put("cfexpmovementreq", "POD");
		newPODMap.put("cfexportgatepass", "POD");
		fieldTableMap.put("newPod", newPODMap);

		// newVoyageNo
		Map<String, String> newVoyageNoMap = new HashMap<>();
		newVoyageNoMap.put("cfstuffrq", "VOYAGE_NO");
		newVoyageNoMap.put("cfstufftally", "VOYAGE_NO");
		newVoyageNoMap.put("cfexpmovementreq", "VOYAGE_NO");
		fieldTableMap.put("newvoyageNo", newVoyageNoMap);

		// newVesselId
		Map<String, String> newVesselIdMap = new HashMap<>();
		newVesselIdMap.put("cfstuffrq", "VESSEL_ID");
		newVesselIdMap.put("cfstufftally", "VESSEL_ID");
		newVesselIdMap.put("cfexpmovementreq", "VESSEL_ID");
		newVesselIdMap.put("cfexportgatepass", "VESSEL_ID");
		fieldTableMap.put("newVesselId", newVesselIdMap);

		// newViaNo
		Map<String, String> newViaNoMap = new HashMap<>();
		newViaNoMap.put("cfstuffrq", "VIA_NO");
		newViaNoMap.put("cfstufftally", "VIA_NO");
		newViaNoMap.put("cfexpmovementreq", "VIA_NO");
		fieldTableMap.put("newvcnNo", newViaNoMap);

		// newStuffTallyDate
		Map<String, String> newStuffTallyDateMap = new HashMap<>();
		newStuffTallyDateMap.put("cfstufftally", "STUFF_TALLY_DATE");
		newStuffTallyDateMap.put("cfstufftally", "Stuff_Date");
		newStuffTallyDateMap.put("cfexpmovementreq", "STUFF_TALLY_DATE");
		newStuffTallyDateMap.put("cfexpinventory", "STUFF_TALLY_DATE");
		
		newStuffTallyDateMap.put("cfexpinventory", "Stuff_Req_Date");
		
		newStuffTallyDateMap.put("cfstuffrq", "STUFF_REQ_DATE");
		fieldTableMap.put("newStuffDate", newStuffTallyDateMap);

		// newMovementReqDate
		Map<String, String> newMovementReqDateMap = new HashMap<>();
		newMovementReqDateMap.put("cfexpmovementreq", "MOVEMENT_REQ_DATE");
		newMovementReqDateMap.put("cfexpinventory", "Movement_req_Date");
		fieldTableMap.put("newMovementRqDate", newMovementReqDateMap);

		// newRotationNo
		Map<String, String> newRotationNoMap = new HashMap<>();
		newRotationNoMap.put("cfstufftally", "ROTATION_NO");
		newRotationNoMap.put("cfexpmovementreq", "ROTATION_NO");
		fieldTableMap.put("newRotataionNo", newRotationNoMap);

		// newCustomsSealNo
		Map<String, String> newCustomsSealNoMap = new HashMap<>();
		newCustomsSealNoMap.put("cfstufftally", "CUSTOMS_SEAL_NO");
		newCustomsSealNoMap.put("cfexpmovementreq", "CUSTOMS_SEAL_NO");
		newCustomsSealNoMap.put("cfexportgatepass", "CUSTOMS_SEAL_NO");
		fieldTableMap.put("newCustomSealNo", newCustomsSealNoMap);

		// newAgentSealNo
		Map<String, String> newAgentSealNoMap = new HashMap<>();
		newAgentSealNoMap.put("cfstuffrq", "AGENT_SEAL_NO");
		newAgentSealNoMap.put("cfstufftally", "AGENT_SEAL_NO");
		newAgentSealNoMap.put("cfexpmovementreq", "AGENT_SEAL_NO");
		newAgentSealNoMap.put("cfexportgatepass", "AGENT_SEAL_NO");
		fieldTableMap.put("newAgentSealNo", newAgentSealNoMap);

		// newFinalPOD
		Map<String, String> newFinalPODMap = new HashMap<>();
		newFinalPODMap.put("cfstufftally", "FINAL_POD");
		fieldTableMap.put("newFinalPod", newFinalPODMap);

		// newPOL
		Map<String, String> newPOLMap = new HashMap<>();
		newPOLMap.put("cfstufftally", "POL");
		newPOLMap.put("cfexpmovementreq", "POL");
		fieldTableMap.put("newPol", newPOLMap);
		

		// Iterate through fields and update tables if values have changed
		fieldTableMap.forEach((field, tableColumnMap) -> {
			try {
				// Use reflection to get the new and old values dynamically
				Field newField = StuffTally.class.getDeclaredField(field);
				Field oldField = StuffTally.class.getDeclaredField(field.replace("new", "old"));
				newField.setAccessible(true);
				oldField.setAccessible(true);

				Object newValue = newField.get(shippingBillDTO);
				Object oldValue = oldField.get(shippingBillDTO);
				AtomicReference<Object> updatedValue = new AtomicReference<>(newValue); 
				if (newValue != null && !newValue.equals(oldValue)) {
					
					System.out.println( "  newValue " + newValue + " oldValue " + oldValue + " field " + field);

					updatedFieldsCount.incrementAndGet();

					String autoIncrementAuditId = processService.autoExportStuffingId(companyId, branchId, "P05085");

					ExportAudit audit = new ExportAudit();
					audit.setCompanyId(companyId);
					audit.setBranchId(branchId);
					audit.setApprovedBy(userId);
					audit.setApprovedDate(currentDate);
					audit.setCreatedBy(userId);
					audit.setCreatedDate(currentDate);
					audit.setEditedBy(userId);
					audit.setEditedDate(currentDate);
					audit.setAuditId(autoIncrementAuditId);
					audit.setAuditDate(currentDate);
					audit.setProfitcentreId(profitCentreIdExport);
					audit.setSbNo(shippingBillDTO.getSbNo());
					audit.setContainerNo(shippingBillDTO.getNewContainerNo());
					audit.setAuditRemark(shippingBillDTO.getAuditremarks());
					audit.setField(removePrefix(field));
					audit.setOldValue(oldValue != null ? oldValue.toString() : "");
					audit.setNewValue(newValue != null ? newValue.toString() : "");
					audit.setStatus("A");
					audit.setTableName("Stuffing Job Order - Container Details");

					auditRepo.save(audit);

					if ("containerNo".equalsIgnoreCase(removePrefix(field))) {
						newContainerNo.set((String) newValue);

					}

					System.out.println( "  tableColumnMap " + tableColumnMap);
					tableColumnMap.forEach((table, column) -> {
						
						if(column.equals("Stuffed_Qty") && table.equals("cfsbcrg"))
						{
							
							
							BigDecimal oldStuffQty = shippingBillDTO.getOldStuffQty();
							BigDecimal newStuffQty = shippingBillDTO.getNewStuffQty();
							BigDecimal sbStuffQty = shippingBillDTO.getSbStuffedQty();
							
							 // Calculate the difference
						    BigDecimal difference = newStuffQty.subtract(oldStuffQty);

						    // Apply the difference to sbStuffQty
						    sbStuffQty = sbStuffQty.add(difference);

						    // Update the newValue to reflect the updated sbStuffQty
						    updatedValue.set(sbStuffQty);			
						}
						
						
						updateStuffTallyTable(table, column, updatedValue.get(), oldValue, shippingBillDTO.getSbTransId(), companyId, branchId, 
								shippingBillDTO.getSbNo(), tableWhereConditionMap, shippingBillDTO.getGateOutId(), shippingBillDTO.getGatePassId(), shippingBillDTO.getOldContainerNo(), 
								shippingBillDTO.getGateInId(), shippingBillDTO.getStuffTallyId(), shippingBillDTO.getMovementId());

					
						
					});
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				
				System.out.println( "  IllegalAccessException e " + e);
				e.printStackTrace();
			}
		});

		
		result.put("updatedFieldsCount", updatedFieldsCount.get());
		result.put("sbNo", newSbNo.get());
		result.put("containerNo", newContainerNo.get());

		return result;
	}

	private void updateStuffTallyTable(String tableName, String columnName, Object newValue, Object oldValue,
			String sbTransId, String companyId, String branchId, String sbNoOld,
			Map<String, String> tableWhereConditionMap, String gateOutId, String gatePassNo, String containerNo,
			String gateInId, String stuffTallyId, String movementReqId) {
		try {
// Retrieve the WHERE clause from the map
			String whereClause = tableWhereConditionMap.get(tableName);

			if (whereClause == null || whereClause.isEmpty()) {
				throw new IllegalArgumentException("Where clause is missing for table: " + tableName);
			}

// Construct the query string
			String queryStr = "UPDATE " + tableName + " SET " + columnName + " = :newValue " + whereClause;
			System.out.println("Final Query: " + queryStr);
			System.out.println("gatePassNo: " + gatePassNo);

			Query query = entityManager.createNativeQuery(queryStr);
			query.setParameter("newValue", newValue);

// Set dynamic parameters based on whereClause
			if (whereClause.contains(":companyId")) {
				query.setParameter("companyId", companyId);
			}
			if (whereClause.contains(":branchId")) {
				query.setParameter("branchId", branchId);
			}
			if (whereClause.contains(":sbTransId")) {
				query.setParameter("sbTransId", sbTransId);
			}
			if (whereClause.contains(":sbNoOld")) {
				query.setParameter("sbNoOld", sbNoOld);
			}
			if (whereClause.contains(":gateOutId")) {
				query.setParameter("gateOutId", gateOutId);
			}
			if (whereClause.contains(":containerNoOld")) {
				query.setParameter("containerNoOld", containerNo);
			}
			if (whereClause.contains(":gatePassNo")) {
				query.setParameter("gatePassNo", gatePassNo);
			}
			if (whereClause.contains(":gateInId")) {
				query.setParameter("gateInId", gateInId);
			}
			
			if (whereClause.contains(":stuffTallyId")) {
				query.setParameter("stuffTallyId", stuffTallyId);
			}
			if (whereClause.contains(":movementReqId")) {
				query.setParameter("movementReqId", movementReqId);
			}

// Execute update query
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error in Query GateOut: " + e);
		}
	}

	public String removePrefix(String fieldName) {
		if (fieldName == null || fieldName.trim().isEmpty()) {
			return "";
		}
		return fieldName.replaceAll("^(new|old)", "").trim();
	}

	public ResponseEntity<?> exportAuditTrailSearh(String companyid, String branchId, String profitCenterId,
			String sbNo, String containerNo) {
		ShippingBillDTO sbDetail = null;
		List<GateInJoDTO> gateinJo = new ArrayList<>();
		List<GateInJoDetailDTO> gateinJoDtl = new ArrayList<>();
		List<CartingTallyDTO> cartingTally = new ArrayList<>();
		ContainerGateIn containerGateIn = null;
		ContainerGateOut containerGateOut = null;
		List<StuffTally> stuffTally = new ArrayList<>();
		
		List<BackToTownDTO> backToTown = null;
		List<BackToTownOutDTO> backToTownOut = null;

		System.out.println("1");
		
		try {
			List<ShippingBillDTO> sbDetailList = auditRepo.getShippingBill1StTable(companyid, branchId, profitCenterId, sbNo, PageRequest.of(0,1));
			
			sbDetail = sbDetailList.isEmpty() ? null : sbDetailList.get(0);
		} catch (Exception e) {
		    System.err.println("Error fetching sb Detail data: " + e.getMessage());
		}
		
		System.out.println("2");
		
		try {
			gateinJo = auditRepo.getGateInJO2ndTable(companyid, branchId, sbNo);
		} catch (Exception e) {
		    System.err.println("Error fetching gatein Jo data: " + e.getMessage());
		}
		
		
		System.out.println("3");
		
		try {
			gateinJoDtl = auditRepo.getGateInJODTL3rdTable(companyid, branchId, sbNo);
		} catch (Exception e) {
		    System.err.println("Error fetching gatein Jo Dtl data: " + e.getMessage());
		}
		
		
		System.out.println("4");
		try {
		    cartingTally = auditRepo.getCartingTallyDTO4thTable(companyid, branchId, sbNo);
		} catch (Exception e) {
		    System.err.println("Error fetching carting tally data: " + e.getMessage());		   
		}
		
		System.out.println("5");
	
		
		try {
			List<ContainerGateIn> containerGateIns = auditRepo.getContainerGateIn(companyid, branchId, sbNo, containerNo, PageRequest.of(0,1));
			containerGateIn = containerGateIns.isEmpty() ? null : containerGateIns.get(0);
			
		} catch (Exception e) {
		    System.err.println("Error fetching container GateIn data: " + e.getMessage());		   
		}
		
		
		System.out.println("6");
		List<ContainerGateOut> containerGateOuts = auditRepo.getContainerGateOut(companyid, branchId, sbNo, containerNo, PageRequest.of(0,1));
		containerGateOut = containerGateOuts.isEmpty() ? null : containerGateOuts.get(0);
		
		System.out.println("7");
		try {
			stuffTally = auditRepo.getContainerStuffTally(companyid, branchId, sbNo, containerNo);
		} catch (Exception e) {
		    System.err.println("Error fetching stuffTally data: " + e.getMessage());		   
		}
		
		System.out.println("8");
		
		
		try {
			backToTown = auditRepo.getBackToTownDTO(companyid, branchId, sbNo);
		} catch (Exception e) {
		    System.err.println("Error fetching backToTown data: " + e.getMessage());		   
		}
		
		
		
		
		System.out.println("9");
		
		
		try {
			backToTownOut = auditRepo.getBackToTownOutDTO(companyid, branchId, sbNo);
		} catch (Exception e) {
		    System.err.println("Error fetching backToTownOut: " + e.getMessage());		   
		}
		
		
		
		
		System.out.println("10");
		
		
		
		

		Map<String, Object> response = new HashMap<>();
		response.put("sbDetail", sbDetail);
		response.put("gateinJo", gateinJo);
		response.put("gateinJoDtl", gateinJoDtl);
		response.put("carting", cartingTally);

		response.put("containerGateIn", containerGateIn);
		response.put("containerGateOut", containerGateOut);
		response.put("stuffTally", stuffTally);
		
		response.put("backToTown", backToTown);
		response.put("backToTownOut", backToTownOut);

		return ResponseEntity.ok(response);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] getExportTruckWiseGateInReport(String companyId, String branchId, String profitCenterId, String gateInId, String userId)
	{
		try
		{
			 Context context = new Context();
		        Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);
		        
			BigDecimal totalPackage = BigDecimal.ZERO;
			BigDecimal totalWeight = BigDecimal.ZERO;
			
		        List<Object[]> exportTruckWiseGateInReport = reportRepo.getExportTruckWiseGateInReport(companyId, branchId, gateInId);
			
		        
		        System.out.println("exportTruckWiseGateInReport : " + exportTruckWiseGateInReport.size());
		        Object[] firstRecord = exportTruckWiseGateInReport.get(0);
		        
		        
		        if (exportTruckWiseGateInReport != null && !exportTruckWiseGateInReport.isEmpty()) {
		            // Iterate through each record
		            for (Object[] record : exportTruckWiseGateInReport) {
		                // Ensure there are enough columns (at least 13 values: index 11 and 12 should exist)
		                if (record.length > 12) {
		                    // Access the values at index 11 (for package) and index 12 (for weight)
		                    BigDecimal valueAt11 = (record[11] instanceof BigDecimal) ? (BigDecimal) record[11] : BigDecimal.ZERO; // Package
		                    BigDecimal valueAt12 = (record[12] instanceof BigDecimal) ? (BigDecimal) record[12] : BigDecimal.ZERO; // Weight

		                    // Add the values to the respective totals
		                    totalPackage = totalPackage.add(valueAt11);
		                    totalWeight = totalWeight.add(valueAt12);
		                }
		            }
		        }
		        
		        
		        System.out.println("firstRecord : ");
		        for (Object element : firstRecord) {
		            System.out.println(element);
		        }		        
		        
		        String branchAdd = branch.getAddress1() + " " + branch.getAddress2() + " "
						+ branch.getAddress3();


		        context.setVariable("address", branchAdd);
				context.setVariable("branchName", branch.getBranchName());
		        context.setVariable("gateInId", (String) firstRecord[0]);
		        context.setVariable("gateInDate", (String) firstRecord[1]);
		        context.setVariable("truckNo", (String) firstRecord[4]);
		        context.setVariable("onAccountOf", (String) firstRecord[19]);
		        context.setVariable("cha", (String) firstRecord[6]);
		        context.setVariable("receiptNo", (String) firstRecord[0]);
		        context.setVariable("totalPackage", totalPackage);
		        context.setVariable("totalWeight", totalWeight);
		        context.setVariable("createdBy", (String) firstRecord[14]);
		       
		        
		        
		       
		        
		        
		        
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportTruckWiseGateInReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("ExportTruckWiseGateIn", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	public byte[] getExportTruckWiseJobOrderReport(String companyId, String branchId, String profitCenterId, String gateInId, String userId)
	{
		try
		{
			 Context context = new Context();
		     Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);
		        
			BigDecimal totalPackage = BigDecimal.ZERO;
			BigDecimal totalWeight = BigDecimal.ZERO;
			
		        List<Object[]> exportTruckWiseGateInReport = reportRepo.getExportTruckWiseGateInReport(companyId, branchId, gateInId);
			
		        
		        Object[] firstRecord = exportTruckWiseGateInReport.get(0);
		        
		        
		        if (exportTruckWiseGateInReport != null && !exportTruckWiseGateInReport.isEmpty()) {
		            // Iterate through each record
		            for (Object[] record : exportTruckWiseGateInReport) {
		                // Ensure there are enough columns (at least 13 values: index 11 and 12 should exist)
		                if (record.length > 12) {
		                    // Access the values at index 11 (for package) and index 12 (for weight)
		                    BigDecimal valueAt11 = (record[11] instanceof BigDecimal) ? (BigDecimal) record[11] : BigDecimal.ZERO; // Package
		                    BigDecimal valueAt12 = (record[12] instanceof BigDecimal) ? (BigDecimal) record[12] : BigDecimal.ZERO; // Weight

		                    // Add the values to the respective totals
		                    totalPackage = totalPackage.add(valueAt11);
		                    totalWeight = totalWeight.add(valueAt12);
		                }
		            }
		        }
		        
		        
		        String branchAdd = branch.getAddress1() + " " + branch.getAddress2() + " "
						+ branch.getAddress3();


		        context.setVariable("address", branchAdd);
				context.setVariable("branchName", branch.getBranchName());
		        context.setVariable("gateInId", (String) firstRecord[0]);
		        context.setVariable("gateInDate", (String) firstRecord[1]);
		        context.setVariable("jobDate", (String) firstRecord[18]);
		        context.setVariable("jobOrderId", (String) firstRecord[17]);
		        context.setVariable("onAccountOf", (String) firstRecord[19]);
		        context.setVariable("cha", (String) firstRecord[6]);
		        context.setVariable("totalPackage", totalPackage);
		        context.setVariable("totalWeight", totalWeight);
		        context.setVariable("createdBy", (String) firstRecord[14]);
		       
		        
		        
		       
		        
		        
		        
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportTruckWiseGateInReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("ExportTruckWiseJobOrder", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	
	public byte[] getExportBackToTownReport(String companyId, String branchId, String profitCenterId, String backToTownTransId, String userId)
	{
		try
		{
				Context context = new Context();
		        Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);
		        
		
			
		        List<Object[]> exportTruckWiseGateInReport = reportRepo.getExportBackToTownReport(companyId, branchId, "N00004",backToTownTransId);
			
		        
		        System.out.println("exportTruckWiseGateInReport : " + exportTruckWiseGateInReport.size());
		        Object[] firstRecord = exportTruckWiseGateInReport.get(0);
		        
		        String branchAdd = branch.getAddress1() + " " + branch.getAddress2() + " "
							+ branch.getAddress3();
		     
		        
		        System.out.println("firstRecord : ");
		        for (Object element : firstRecord) {
		            System.out.println(element);
		        }		        
		        context.setVariable("workOrderId", (String) firstRecord[4]);
		        context.setVariable("workOrderDate", (String) firstRecord[5]);
		        context.setVariable("truckNo", (String) firstRecord[2]);
		        context.setVariable("lineAgent", (String) firstRecord[11]);
		        context.setVariable("cha", (String) firstRecord[12]);
	


		        context.setVariable("address", branchAdd);
				context.setVariable("branchName", branch.getBranchName());
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportTruckWiseGateInReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("ExportBackToTown", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	
	
	
	
	
	
	public byte[] downLoadExportPortReturnReport(String companyId, String branchId, String profitCenterId, String gateInId, String userId)
	{
		try
		{
				Context context = new Context();
		        Branch branch = branchRepo.getCompleteCompanyAndBranch(companyId, branchId);     
		
			
		        List<GateIn> exportPortReturnReportList = reportRepo.getExportPortReturnReport(companyId, branchId, "N00004",gateInId);
			
		        GateIn  exportPortReturnReport =exportPortReturnReportList.get(0);
		        String branchAdd = branch.getAddress1() + " " + branch.getAddress2() + " "
								+ branch.getAddress3();


				        context.setVariable("address", branchAdd);
						context.setVariable("branchName", branch.getBranchName());
		        
		        context.setVariable("branch", branch);
		        context.setVariable("reportData", exportPortReturnReport);
			
		        // Process the Thymeleaf template with dynamic values
		        String htmlContent = templateEngine.process("ExportPortReturn", context);

		        // Create an ITextRenderer instance
		        ITextRenderer renderer = new ITextRenderer();

		        // Set the PDF page size and margins
		        renderer.setDocumentFromString(htmlContent);
		        renderer.layout();

		        // Generate PDF content
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        renderer.createPDF(outputStream);

		        // Get the PDF bytes
		        byte[] pdfBytes = outputStream.toByteArray();
		        context.clearVariables();

		        return pdfBytes;
		    } catch (Exception e) {
		        System.out.println(e);
		        return null;
		    }
		}
	
	
	
	
	
	
	
	
}
