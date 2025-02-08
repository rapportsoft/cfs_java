package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.internal.build.AllowSysOut;
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

import com.cwms.entities.Auction;
import com.cwms.entities.AuctionDetail;
import com.cwms.entities.Branch;
import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.Company;
import com.cwms.entities.ContainerDTO;
import com.cwms.entities.GateOut;
import com.cwms.repository.AuctionCrgRepo;
import com.cwms.repository.AuctionREpo;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@Service
public class AuctionService {

	@Autowired
	private AuctionREpo autionRepo;

	@Autowired
	private AuctionCrgRepo auctionCrgRepo;

	@Autowired
	private ProcessNextIdRepository processNextIdRepository;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;

	public ResponseEntity<List<Object[]>> getDistinctIgmTransId(String compnayId, String branchId, String igmTransId) {

		List<Object[]> getData = autionRepo.findDistinctIGMDetails(compnayId, branchId, igmTransId);

		return new ResponseEntity<List<Object[]>>(getData, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	public ResponseEntity<List<Object[]>> getDistinctIgmTransIdSecondNotice(String compnayId, String branchId, String igmTransId) {

		List<Object[]> getData = autionRepo.findDistinctIGMDetailsForSecondNotice(compnayId, branchId, igmTransId);

		return new ResponseEntity<List<Object[]>>(getData, HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<List<Object[]>> findDistinctIGMDetailsForFinalNotice(String compnayId, String branchId, String igmTransId) {

		List<Object[]> getData = autionRepo.findDistinctIGMDetailsForFinalNotice(compnayId, branchId, igmTransId);

		return new ResponseEntity<List<Object[]>>(getData, HttpStatus.OK);
	}

	public ResponseEntity<List<Object[]>> getIgmTransIdDetails(String compnayId, String branchId, String igmLineNo,
			String igmNo, String igmTransId) {

		List<Object[]> getData = autionRepo.findIgmDetails(compnayId, branchId, igmLineNo, igmNo, igmTransId);

		return new ResponseEntity<List<Object[]>>(getData, HttpStatus.OK);
	}

	public ResponseEntity<List<ContainerDTO>> getContainerDetails(String compnayId, String branchId, String igmNo,
			String igmTransId, String igmLineNo) {

		List<ContainerDTO> getData = autionRepo.findDistinctContainers(compnayId, branchId, igmNo, igmTransId,
				igmLineNo);

		return new ResponseEntity<List<ContainerDTO>>(getData, HttpStatus.OK);
	}

	public ResponseEntity<List<Auction>> getContainerDetailsAfterSave(String compnayId, String branchId,
			String noticeId, String ammendNo) {

		List<Auction> getData = autionRepo.findDistinctContainersAfterSave(compnayId, branchId, noticeId,
				ammendNo);

		return new ResponseEntity<List<Auction>>(getData, HttpStatus.OK);
	}

	public List<AuctionDetail> findAuctionDetails(String companyId, String branchId, String partyName) {
		return autionRepo.findAuctionDetails(companyId, branchId, partyName);
	}
	public List<AuctionDetail> findAuctionDetailsSecond(String companyId, String branchId, String partyName) {
		return autionRepo.findAuctionDetailsSecond(companyId, branchId, partyName);
	}
	
	
	
	
	
	
	public List<AuctionDetail> findAuctionDetailsFinal(String companyId, String branchId, String partyName) {
		return autionRepo.findAuctionDetailsFinal(companyId, branchId, partyName);
	}
	
	
	
	
	public AuctionDetail getAuctionOnScreenData(String companyId, String branchId, String noticeId,String noticeAmndNo, String igmNo, String igmLineNo,String igmTransId) {
		return autionRepo.findDistinctAuctionDetailsAfterSave(companyId, branchId, noticeId,noticeAmndNo,igmNo,igmLineNo,igmTransId);
	}

//public List<AuctionDetail> findDataOfGateOutDetails(String companyId, String branchId, String partyName) 
//{
//    return gateOutRepo.findDataOfGateOutDetails(companyId, branchId, partyName);
//}

	@Transactional
	public ResponseEntity<?> saveDataOfAuctionNotice(String companyId, String branchId, String user, String flag,
			Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		AuctionDetail auctionCrg = object.convertValue(requestBody.get("auction"), AuctionDetail.class);
		List<ContainerDTO> gateOutDtlList = new ArrayList<>();

		Object nocDtlObj = requestBody.get("dtl");

		if (nocDtlObj instanceof List) {
			// If nocDtl is a list, deserialize it directly
			gateOutDtlList = object.convertValue(nocDtlObj, new TypeReference<List<ContainerDTO>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				ContainerDTO gateOutDtl = object.convertValue(entry.getValue(), ContainerDTO.class);
				gateOutDtlList.add(gateOutDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		AuctionDetail savedCrg = null;

		List<Auction> savedDtl = new ArrayList<>();

		if (auctionCrg != null) {
			if ("add".equals(flag)) {

				if (auctionCrg != null) {
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P02925", "2025");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectExBondingId = String.format("AUNU%06d", nextNumericNextID1);

					AuctionDetail auc = new AuctionDetail();
					auc.setCompanyId(companyId);
					auc.setBranchId(branchId);
					auc.setProfitcentreId(auctionCrg.getProfitcentreId());
					auc.setNoticeId(nectExBondingId);
					auc.setNoticeAmndNo("000");
					auc.setFinalNoticeId(nectExBondingId);
					auc.setAuctionType(auctionCrg.getAuctionType());
					auc.setNoticeType("P");
					auc.setTransType(auctionCrg.getTransType());
					auc.setNoticeDate(new Date());
					auc.setIgmTransId(auctionCrg.getIgmTransId());
					auc.setIgmTransDate(auctionCrg.getIgmTransDate());
					auc.setIgmNo(auctionCrg.getIgmNo());
					auc.setIgmDate(auctionCrg.getIgmDate());
					auc.setIgmLineNo(auctionCrg.getIgmLineNo());
					auc.setViaNo(auctionCrg.getViaNo());
					auc.setShift(auctionCrg.getShift());
					auc.setSource(flag);
					auc.setBoeNo(auctionCrg.getBoeNo());
					auc.setBoeDate(auctionCrg.getBoeDate());
					auc.setVessel(auctionCrg.getVessel());
					auc.setSa(auctionCrg.getSa());
					auc.setImporterName(auctionCrg.getImporterName());
					auc.setImporterAddress1(auctionCrg.getImporterAddress1());
					auc.setImporterAddress2(auctionCrg.getImporterAddress2());
					auc.setImporterAddress3(auctionCrg.getImporterAddress3());
					auc.setNotifyParty(auctionCrg.getNotifyParty());
					auc.setNotifyPartyAddress1(auctionCrg.getNotifyPartyAddress1());
					auc.setNotifyPartyAddress2(auctionCrg.getNotifyPartyAddress2());
					auc.setNotifyPartyAddress3(auctionCrg.getNotifyPartyAddress3());
					auc.setCommodityDescription(auctionCrg.getCommodityDescription());
					auc.setNoOfPackages(auctionCrg.getNoOfPackages());
					auc.setTypeOfPackage(auctionCrg.getTypeOfPackage());
					auc.setGrossWt(auctionCrg.getGrossWt());
					auc.setUom(auctionCrg.getUom());
					auc.setBlNo(auctionCrg.getBlNo());
					auc.setBlDate(auctionCrg.getBlDate());
					auc.setAssessiableAvailable(auctionCrg.getAssessiableAvailable());
					auc.setAccessableValueAsValuation(auctionCrg.getAccessableValueAsValuation());
					auc.setRateOfDuty(auctionCrg.getRateOfDuty());
					auc.setAmtOfDuty(auctionCrg.getAmtOfDuty());
					auc.setDuty(auctionCrg.getDuty());
					auc.setMop(auctionCrg.getMop());
					auc.setPmv(auctionCrg.getPmv());
					auc.setFairValueOfGoods(auctionCrg.getFairValueOfGoods());
					auc.setBidId(auctionCrg.getBidId());
					auc.setBidDate(auctionCrg.getBidDate());
					auc.setComments(auctionCrg.getComments());
					auc.setCvStatus(auctionCrg.getCvStatus());
					auc.setCvCreatedBy(user);
					auc.setCvCreatedDate(new Date());
					auc.setCvApprovedBy(user);
					auc.setCvApprovedDate(new Date());
					auc.setCreatedBy(user);
					auc.setCreatedDate(new Date());
//				auc.setEditedBy(auctionCrg.getEditedBy());
//				auc.setEditedDate(auctionCrg.getEditedDate());
					auc.setApprovedBy(user);
					auc.setApprovedDate(new Date());
					auc.setStatus("A");
					auc.setPol(auctionCrg.getPol());
					auc.setFileNo(auctionCrg.getFileNo());
					auc.setLotNo(auctionCrg.getLotNo());
					auc.setHsnNo(auctionCrg.getHsnNo());
					auc.setAuctionStatus(auctionCrg.getAuctionStatus());
					auc.setFileStatus(auctionCrg.getFileStatus());
					auc.setTcs(auctionCrg.getTcs());
					auc.setIgst(auctionCrg.getIgst());
					auc.setSgst(auctionCrg.getSgst());
					auc.setCgst(auctionCrg.getCgst());
					auc.setAuctionType(auctionCrg.getAuctionType());
					auc.setBidAmt(auctionCrg.getBidAmt());
					auc.setStcStatus(auctionCrg.getStcStatus());
					auc.setAcceptRejectStatus(auctionCrg.getAcceptRejectStatus());
					auc.setGstApprovedDate(auctionCrg.getGstApprovedDate());
					auc.setCmdApprovedDate(auctionCrg.getCmdApprovedDate());
					auc.setBidamtApprovedDate(auctionCrg.getBidamtApprovedDate());
					auc.setStcApprovedDate(auctionCrg.getStcApprovedDate());
					auc.setCustomeAcceptRejectDate(auctionCrg.getCustomeAcceptRejectDate());
					auc.setCustomeOutOfChargeDate(auctionCrg.getCustomeOutOfChargeDate());

					savedCrg = auctionCrgRepo.save(auc);
					if (savedCrg != null) {

						AuctionDetail crg = savedCrg;

						gateOutDtlList.forEach(item -> {

							System.out.println("itemitemitemitemitemitemitemitemitemitem :" + item);
							Auction aucDtl = new Auction();

							aucDtl.setCompanyId(companyId);
							aucDtl.setBranchId(branchId);
							aucDtl.setNoticeId(nectExBondingId);
							aucDtl.setNoticeAmndNo(crg.getNoticeAmndNo());
							aucDtl.setFinalNoticeId(crg.getFinalNoticeId());
							aucDtl.setProfitcentreId(crg.getProfitcentreId());
							aucDtl.setContainerSize(item.getContainerSize());
							aucDtl.setContainerType(item.getContainerType());
							aucDtl.setGateInId(item.getGateInId());
							aucDtl.setGateInDate(item.getGateInDate());
							aucDtl.setNoOfPackages(BigDecimal.valueOf(item.getNoOfPackages()));
							aucDtl.setGrossWt(item.getContainerWeight());
							aucDtl.setNoticeType("P");
							aucDtl.setCreatedBy(user);
							aucDtl.setCreatedDate(new Date());
							aucDtl.setApprovedBy(user);
							aucDtl.setApprovedDate(new Date());
							aucDtl.setStatus("A");
							aucDtl.setContainerStatus("");

							aucDtl.setAuctionStatus("A");
							aucDtl.setContainerNo(item.getContainerNo());

							Auction save = autionRepo.save(aucDtl);

							if (save != null) {
								int updateInCfigmCN = autionRepo.updateCfigmCnAfterAuctionNotice('P',
										save.getNoticeId(), crg.getNoticeDate(), companyId, branchId, crg.getIgmNo(),
										crg.getIgmTransId(), crg.getIgmLineNo(), save.getContainerNo());

								System.out.println("updateInCfigmCN :" + updateInCfigmCN);
							}

						});

						processNextIdRepository.updateAuditTrail(companyId, branchId, "P02925", nectExBondingId,
								"2025");

					}

					

				}
				return new ResponseEntity<>(savedCrg, HttpStatus.OK);

			}

			return new ResponseEntity<>(savedCrg, HttpStatus.OK);
		}

		return new ResponseEntity<>(savedCrg, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	@Transactional
	public ResponseEntity<?> saveDataOfAuctionSecondNotice(String companyId, String branchId, String user, String flag,
			Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		AuctionDetail auctionCrg = object.convertValue(requestBody.get("auction"), AuctionDetail.class);
		List<ContainerDTO> gateOutDtlList = new ArrayList<>();

		Object nocDtlObj = requestBody.get("dtl");

		if (nocDtlObj instanceof List) {
			// If nocDtl is a list, deserialize it directly
			gateOutDtlList = object.convertValue(nocDtlObj, new TypeReference<List<ContainerDTO>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				ContainerDTO gateOutDtl = object.convertValue(entry.getValue(), ContainerDTO.class);
				gateOutDtlList.add(gateOutDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		AuctionDetail savedCrg = null;

		List<Auction> savedDtl = new ArrayList<>();

		if (auctionCrg != null) {
			if ("add".equals(flag)) {

				if (auctionCrg != null) {
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P02945", "2025");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectExBondingId = String.format("AUSU%06d", nextNumericNextID1);

					AuctionDetail auc = new AuctionDetail();
					auc.setCompanyId(companyId);
					auc.setBranchId(branchId);
					auc.setProfitcentreId(auctionCrg.getProfitcentreId());
					auc.setNoticeId(nectExBondingId);
					auc.setNoticeAmndNo("000");
					auc.setFinalNoticeId(nectExBondingId);
					auc.setAuctionType(auctionCrg.getAuctionType());
					auc.setNoticeType("S");
					auc.setTransType(auctionCrg.getTransType());
					auc.setNoticeDate(new Date());
					auc.setIgmTransId(auctionCrg.getIgmTransId());
					auc.setIgmTransDate(auctionCrg.getIgmTransDate());
					auc.setIgmNo(auctionCrg.getIgmNo());
					auc.setIgmDate(auctionCrg.getIgmDate());
					auc.setIgmLineNo(auctionCrg.getIgmLineNo());
					auc.setViaNo(auctionCrg.getViaNo());
					auc.setShift(auctionCrg.getShift());
					auc.setSource(flag);
					auc.setBoeNo(auctionCrg.getBoeNo());
					auc.setBoeDate(auctionCrg.getBoeDate());
					auc.setVessel(auctionCrg.getVessel());
					auc.setSa(auctionCrg.getSa());
					auc.setImporterName(auctionCrg.getImporterName());
					auc.setImporterAddress1(auctionCrg.getImporterAddress1());
					auc.setImporterAddress2(auctionCrg.getImporterAddress2());
					auc.setImporterAddress3(auctionCrg.getImporterAddress3());
					auc.setNotifyParty(auctionCrg.getNotifyParty());
					auc.setNotifyPartyAddress1(auctionCrg.getNotifyPartyAddress1());
					auc.setNotifyPartyAddress2(auctionCrg.getNotifyPartyAddress2());
					auc.setNotifyPartyAddress3(auctionCrg.getNotifyPartyAddress3());
					auc.setCommodityDescription(auctionCrg.getCommodityDescription());
					auc.setNoOfPackages(auctionCrg.getNoOfPackages());
					auc.setTypeOfPackage(auctionCrg.getTypeOfPackage());
					auc.setGrossWt(auctionCrg.getGrossWt());
					auc.setUom(auctionCrg.getUom());
					auc.setBlNo(auctionCrg.getBlNo());
					auc.setBlDate(auctionCrg.getBlDate());
					auc.setAssessiableAvailable(auctionCrg.getAssessiableAvailable());
					auc.setAccessableValueAsValuation(auctionCrg.getAccessableValueAsValuation());
					auc.setRateOfDuty(auctionCrg.getRateOfDuty());
					auc.setAmtOfDuty(auctionCrg.getAmtOfDuty());
					auc.setDuty(auctionCrg.getDuty());
					auc.setMop(auctionCrg.getMop());
					auc.setPmv(auctionCrg.getPmv());
					auc.setFairValueOfGoods(auctionCrg.getFairValueOfGoods());
					auc.setBidId(auctionCrg.getBidId());
					auc.setBidDate(auctionCrg.getBidDate());
					auc.setComments(auctionCrg.getComments());
					auc.setCvStatus(auctionCrg.getCvStatus());
					auc.setCvCreatedBy(user);
					auc.setCvCreatedDate(new Date());
					auc.setCvApprovedBy(user);
					auc.setCvApprovedDate(new Date());
					auc.setCreatedBy(user);
					auc.setCreatedDate(new Date());
//				auc.setEditedBy(auctionCrg.getEditedBy());
//				auc.setEditedDate(auctionCrg.getEditedDate());
					auc.setApprovedBy(user);
					auc.setApprovedDate(new Date());
					auc.setStatus("A");
					auc.setPol(auctionCrg.getPol());
					auc.setFileNo(auctionCrg.getFileNo());
					auc.setLotNo(auctionCrg.getLotNo());
					auc.setHsnNo(auctionCrg.getHsnNo());
					auc.setAuctionStatus(auctionCrg.getAuctionStatus());
					auc.setFileStatus(auctionCrg.getFileStatus());
					auc.setTcs(auctionCrg.getTcs());
					auc.setIgst(auctionCrg.getIgst());
					auc.setSgst(auctionCrg.getSgst());
					auc.setCgst(auctionCrg.getCgst());
					auc.setAuctionType(auctionCrg.getAuctionType());
					auc.setBidAmt(auctionCrg.getBidAmt());
					auc.setStcStatus(auctionCrg.getStcStatus());
					auc.setAcceptRejectStatus(auctionCrg.getAcceptRejectStatus());
					auc.setGstApprovedDate(auctionCrg.getGstApprovedDate());
					auc.setCmdApprovedDate(auctionCrg.getCmdApprovedDate());
					auc.setBidamtApprovedDate(auctionCrg.getBidamtApprovedDate());
					auc.setStcApprovedDate(auctionCrg.getStcApprovedDate());
					auc.setCustomeAcceptRejectDate(auctionCrg.getCustomeAcceptRejectDate());
					auc.setCustomeOutOfChargeDate(auctionCrg.getCustomeOutOfChargeDate());

					savedCrg = auctionCrgRepo.save(auc);
					if (savedCrg != null) {

						AuctionDetail crg = savedCrg;

						gateOutDtlList.forEach(item -> {

							System.out.println("itemitemitemitemitemitemitemitemitemitem :" + item);
							Auction aucDtl = new Auction();

							aucDtl.setCompanyId(companyId);
							aucDtl.setBranchId(branchId);
							aucDtl.setNoticeId(nectExBondingId);
							aucDtl.setNoticeAmndNo(crg.getNoticeAmndNo());
							aucDtl.setFinalNoticeId(crg.getFinalNoticeId());
							aucDtl.setProfitcentreId(crg.getProfitcentreId());
							aucDtl.setContainerSize(item.getContainerSize());
							aucDtl.setContainerType(item.getContainerType());
							aucDtl.setGateInId(item.getGateInId());
							aucDtl.setGateInDate(item.getGateInDate());
							aucDtl.setNoOfPackages(BigDecimal.valueOf(item.getNoOfPackages()));
							aucDtl.setGrossWt(item.getContainerWeight());
							aucDtl.setNoticeType("S");
							aucDtl.setCreatedBy(user);
							aucDtl.setCreatedDate(new Date());
							aucDtl.setApprovedBy(user);
							aucDtl.setApprovedDate(new Date());
							aucDtl.setStatus("A");
							aucDtl.setContainerStatus("");

							aucDtl.setAuctionStatus("A");
							aucDtl.setContainerNo(item.getContainerNo());

							Auction save = autionRepo.save(aucDtl);

							if (save != null) {
								int updateInCfigmCN = autionRepo.updateCfigmCnAfterAuctionNotice2('S',
										save.getNoticeId(), crg.getNoticeDate(), companyId, branchId, crg.getIgmNo(),
										crg.getIgmTransId(), crg.getIgmLineNo(), save.getContainerNo());

								System.out.println("updateInCfigmCN :" + updateInCfigmCN);
							}

						});

						processNextIdRepository.updateAuditTrail(companyId, branchId, "P02945", nectExBondingId,
								"2025");

					}

					

				}
				return new ResponseEntity<>(savedCrg, HttpStatus.OK);

			}

			return new ResponseEntity<>(savedCrg, HttpStatus.OK);
		}

		return new ResponseEntity<>(savedCrg, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	public ResponseEntity<?> saveDataOfAuctionFinalNotice(String companyId, String branchId, String user, String flag,
			Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		AuctionDetail auctionCrg = object.convertValue(requestBody.get("auction"), AuctionDetail.class);
		List<ContainerDTO> gateOutDtlList = new ArrayList<>();

		Object nocDtlObj = requestBody.get("dtl");

		if (nocDtlObj instanceof List) {
			// If nocDtl is a list, deserialize it directly
			gateOutDtlList = object.convertValue(nocDtlObj, new TypeReference<List<ContainerDTO>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				ContainerDTO gateOutDtl = object.convertValue(entry.getValue(), ContainerDTO.class);
				gateOutDtlList.add(gateOutDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		AuctionDetail savedCrg = null;

		List<Auction> savedDtl = new ArrayList<>();

		if (auctionCrg != null) {
			if ("add".equals(flag)) {

				if (auctionCrg != null) {
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P02954", "2025");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectExBondingId = String.format("AUFU%06d", nextNumericNextID1);

					AuctionDetail auc = new AuctionDetail();
					auc.setCompanyId(companyId);
					auc.setBranchId(branchId);
					auc.setProfitcentreId(auctionCrg.getProfitcentreId());
					auc.setNoticeId(nectExBondingId);
					auc.setNoticeAmndNo("000");
					auc.setFinalNoticeId(nectExBondingId);
					auc.setAuctionType(auctionCrg.getAuctionType());
					auc.setNoticeType("F");
					auc.setTransType(auctionCrg.getTransType());
					auc.setNoticeDate(new Date());
					auc.setIgmTransId(auctionCrg.getIgmTransId());
					auc.setIgmTransDate(auctionCrg.getIgmTransDate());
					auc.setIgmNo(auctionCrg.getIgmNo());
					auc.setIgmDate(auctionCrg.getIgmDate());
					auc.setIgmLineNo(auctionCrg.getIgmLineNo());
					auc.setViaNo(auctionCrg.getViaNo());
					auc.setShift(auctionCrg.getShift());
					auc.setSource("");
					auc.setBoeNo(auctionCrg.getBoeNo());
					auc.setBoeDate(auctionCrg.getBoeDate());
					auc.setVessel(auctionCrg.getVessel());
					auc.setSa(auctionCrg.getSa());
					auc.setImporterName(auctionCrg.getImporterName());
					auc.setImporterAddress1(auctionCrg.getImporterAddress1());
					auc.setImporterAddress2(auctionCrg.getImporterAddress2());
					auc.setImporterAddress3(auctionCrg.getImporterAddress3());
					auc.setNotifyParty(auctionCrg.getNotifyParty());
					auc.setNotifyPartyAddress1(auctionCrg.getNotifyPartyAddress1());
					auc.setNotifyPartyAddress2(auctionCrg.getNotifyPartyAddress2());
					auc.setNotifyPartyAddress3(auctionCrg.getNotifyPartyAddress3());
					auc.setCommodityDescription(auctionCrg.getCommodityDescription());
					auc.setNoOfPackages(auctionCrg.getNoOfPackages());
					auc.setTypeOfPackage(auctionCrg.getTypeOfPackage());
					auc.setGrossWt(auctionCrg.getGrossWt());
					auc.setUom(auctionCrg.getUom());
					auc.setBlNo(auctionCrg.getBlNo());
					auc.setBlDate(auctionCrg.getBlDate());
					auc.setAssessiableAvailable(auctionCrg.getAssessiableAvailable());
					auc.setAccessableValueAsValuation(auctionCrg.getAccessableValueAsValuation());
					auc.setRateOfDuty(auctionCrg.getRateOfDuty());
					auc.setAmtOfDuty(auctionCrg.getAmtOfDuty());
					auc.setDuty(auctionCrg.getDuty());
					auc.setMop(auctionCrg.getMop());
					auc.setPmv(auctionCrg.getPmv());
					auc.setFairValueOfGoods(auctionCrg.getFairValueOfGoods());
					auc.setBidId(auctionCrg.getBidId());
					auc.setBidDate(auctionCrg.getBidDate());
					auc.setComments(auctionCrg.getComments());
					auc.setCvStatus(auctionCrg.getCvStatus());
					auc.setCvCreatedBy(user);
					auc.setCvCreatedDate(new Date());
					auc.setCvApprovedBy(user);
					auc.setCvApprovedDate(new Date());
					auc.setCreatedBy(user);
					auc.setCreatedDate(new Date());
//				auc.setEditedBy(auctionCrg.getEditedBy());
//				auc.setEditedDate(auctionCrg.getEditedDate());
					auc.setApprovedBy(user);
					auc.setApprovedDate(new Date());
					auc.setStatus("A");
					auc.setPol(auctionCrg.getPol());
					auc.setFileNo(auctionCrg.getFileNo());
					auc.setLotNo(auctionCrg.getLotNo());
					auc.setHsnNo(auctionCrg.getHsnNo());
					auc.setAuctionStatus(auctionCrg.getAuctionStatus());
					auc.setFileStatus(auctionCrg.getFileStatus());
					auc.setTcs(auctionCrg.getTcs());
					auc.setIgst(auctionCrg.getIgst());
					auc.setSgst(auctionCrg.getSgst());
					auc.setCgst(auctionCrg.getCgst());
					auc.setAuctionType(auctionCrg.getAuctionType());
					auc.setBidAmt(auctionCrg.getBidAmt());
					auc.setStcStatus(auctionCrg.getStcStatus());
					auc.setAcceptRejectStatus(auctionCrg.getAcceptRejectStatus());
					auc.setGstApprovedDate(auctionCrg.getGstApprovedDate());
					auc.setCmdApprovedDate(auctionCrg.getCmdApprovedDate());
					auc.setBidamtApprovedDate(auctionCrg.getBidamtApprovedDate());
					auc.setStcApprovedDate(auctionCrg.getStcApprovedDate());
					auc.setCustomeAcceptRejectDate(auctionCrg.getCustomeAcceptRejectDate());
					auc.setCustomeOutOfChargeDate(auctionCrg.getCustomeOutOfChargeDate());

					savedCrg = auctionCrgRepo.save(auc);
					if (savedCrg != null) {

						AuctionDetail crg = savedCrg;

						gateOutDtlList.forEach(item -> {

							System.out.println("itemitemitemitemitemitemitemitemitemitem :" + item);
							Auction aucDtl = new Auction();

							aucDtl.setCompanyId(companyId);
							aucDtl.setBranchId(branchId);
							aucDtl.setNoticeId(nectExBondingId);
							aucDtl.setNoticeAmndNo(crg.getNoticeAmndNo());
							aucDtl.setFinalNoticeId(crg.getFinalNoticeId());
							aucDtl.setProfitcentreId(crg.getProfitcentreId());
							aucDtl.setContainerSize(item.getContainerSize());
							aucDtl.setContainerType(item.getContainerType());
							aucDtl.setGateInId(item.getGateInId());
							aucDtl.setGateInDate(item.getGateInDate());
							aucDtl.setNoOfPackages(BigDecimal.valueOf(item.getNoOfPackages()));
							aucDtl.setGrossWt(item.getContainerWeight());
							aucDtl.setNoticeType("F");
							aucDtl.setCreatedBy(user);
							aucDtl.setCreatedDate(new Date());
							aucDtl.setApprovedBy(user);
							aucDtl.setApprovedDate(new Date());
							aucDtl.setStatus("A");
							aucDtl.setContainerStatus("");

							aucDtl.setAuctionStatus("A");
							aucDtl.setContainerNo(item.getContainerNo());

							Auction save = autionRepo.save(aucDtl);

							if (save != null) {
								int updateInCfigmCN = autionRepo.updateCfigmCnAfterAuctionNotice3('F',
										save.getNoticeId(), crg.getNoticeDate(), companyId, branchId, crg.getIgmNo(),
										crg.getIgmTransId(), crg.getIgmLineNo(), save.getContainerNo());

								System.out.println("updateInCfigmCN :" + updateInCfigmCN);
							}

							
							
							int updateAuctionCrg =autionRepo.updateActionCrgAfterFinalNotice(crg.getNoticeId(), companyId, branchId, crg.getIgmNo(),
									crg.getIgmTransId(), crg.getIgmLineNo());
							
							System.out.println("updateAuctionCrg :" + updateAuctionCrg);
							
						});

						processNextIdRepository.updateAuditTrail(companyId, branchId, "P02954", nectExBondingId,
								"2025");

					}

					

				}
				return new ResponseEntity<>(savedCrg, HttpStatus.OK);

			}

			return new ResponseEntity<>(savedCrg, HttpStatus.OK);
		}

		return new ResponseEntity<>(savedCrg, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ResponseEntity<String> getPrintOfFirstNotice( String companyId,
			String branchId,  String username,
			 String type,String companyname,
			 String branchname,  String igmNo,String igmLineNo ,String noticeId) throws DocumentException {
		
		Context context = new Context();

		Object[] dataForPrint = null;
		Object[] slname = null;

		List<Object[]> result = autionRepo.findContainerDetailsFirstNoticePrint(companyId,
				branchId, igmNo,igmLineNo,noticeId);
		
		if (!result.isEmpty()) {
			slname = result.get(0);
			// Process the firstResult
		}
		
		
		List<Object[]> resultHead = autionRepo.firstNoticePrintHead(companyId,
				branchId, igmNo,igmLineNo,noticeId);
		if (!resultHead.isEmpty()) {
			dataForPrint = resultHead.get(0);
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

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		String city = companyAddress.getCity();

		String bondCode = branchAddress.getBondCode();
		context.setVariable("noticeId", dataForPrint[0]);
		context.setVariable("noticeDate", dataForPrint[1]);
		context.setVariable("importerName", dataForPrint[2]);
//		context.setVariable("boeNo", boeNosBuilder.toString());
		context.setVariable("importerAdd", dataForPrint[3] +" "+dataForPrint[4] + " "+dataForPrint[5]);
		context.setVariable("notifyParty", dataForPrint[6]);
		
		context.setVariable("notifyPartyAdd", dataForPrint[7] +" "+dataForPrint[8] + " "+dataForPrint[9]);
		
		context.setVariable("sl", slname[11]);
		context.setVariable("result", result);
		context.setVariable("c1", c1);
		context.setVariable("b1", b1);
		context.setVariable("u1", u1);
		context.setVariable("companyAdd", companyAdd);
		context.setVariable("branchAdd", branchAdd);
		context.setVariable("bondCode", bondCode);
		context.setVariable("city", city);

		String htmlContent = templateEngine.process("firstNotice", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
	
	
	
	
	
	
	
	
	
	
	
	public ResponseEntity<String> getPrintOfSecondNotice( String companyId,
			String branchId,  String username,
			 String type,String companyname,
			 String branchname,  String igmNo,String igmLineNo ,String noticeId) throws DocumentException {
		
		Context context = new Context();

		Object[] dataForPrint = null;
		Object[] slname = null;
		List<Object[]> result = autionRepo.findContainerDetailsSecondNoticePrint(companyId,
				branchId, igmNo,igmLineNo,noticeId);
		
		if (!result.isEmpty()) {
			slname = result.get(0);
			// Process the firstResult
		}
		
		List<Object[]> resultHead = autionRepo.firstNoticePrintHead(companyId,
				branchId, igmNo,igmLineNo,noticeId);
		if (!resultHead.isEmpty()) {
			dataForPrint = resultHead.get(0);
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

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		String city = companyAddress.getCity();

		String bondCode = branchAddress.getBondCode();
		context.setVariable("noticeId", dataForPrint[0]);
		context.setVariable("noticeDate", dataForPrint[1]);
		context.setVariable("importerName", dataForPrint[2]);
//		context.setVariable("boeNo", boeNosBuilder.toString());
		context.setVariable("importerAdd", dataForPrint[3] +" "+dataForPrint[4] + " "+dataForPrint[5]);
		context.setVariable("notifyParty", dataForPrint[6]);
		
		context.setVariable("notifyPartyAdd", dataForPrint[7] +" "+dataForPrint[8] + " "+dataForPrint[9]);
		
		context.setVariable("sl", slname[11]);
		context.setVariable("result", result);
		context.setVariable("c1", c1);
		context.setVariable("b1", b1);
		context.setVariable("u1", u1);
		context.setVariable("companyAdd", companyAdd);
		context.setVariable("branchAdd", branchAdd);
		context.setVariable("bondCode", bondCode);
		context.setVariable("city", city);

		String htmlContent = templateEngine.process("AuctionSecondNotice", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
	
	
	
	
	
	
	public ResponseEntity<String> getPrintOfFinalNotice( String companyId,
			String branchId,  String username,
			 String type,String companyname,
			 String branchname,  String igmNo,String igmLineNo ,String noticeId) throws DocumentException {
		
		Context context = new Context();

		Object[] dataForPrint = null;
		Object[] slname = null;

		List<Object[]> result = autionRepo.findContainerDetailsFinalNoticePrint(companyId,
				branchId, igmNo,igmLineNo,noticeId);
		if (!result.isEmpty()) {
			slname = result.get(0);
			// Process the firstResult
		}
		
		List<Object[]> resultHead = autionRepo.firstNoticePrintHead(companyId,
				branchId, igmNo,igmLineNo,noticeId);
		if (!resultHead.isEmpty()) {
			dataForPrint = resultHead.get(0);
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

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		String city = companyAddress.getCity();

		String bondCode = branchAddress.getBondCode();
		context.setVariable("noticeId", dataForPrint[0]);
		context.setVariable("noticeDate", dataForPrint[1]);
		context.setVariable("importerName", dataForPrint[2]);
//		context.setVariable("boeNo", boeNosBuilder.toString());
		context.setVariable("importerAdd", dataForPrint[3] +" "+dataForPrint[4] + " "+dataForPrint[5]);
		context.setVariable("notifyParty", dataForPrint[6]);
		
		context.setVariable("notifyPartyAdd", dataForPrint[7] +" "+dataForPrint[8] + " "+dataForPrint[9]);
		
		context.setVariable("sl", slname[11]);
		context.setVariable("result", result);
		context.setVariable("c1", c1);
		context.setVariable("b1", b1);
		context.setVariable("u1", u1);
		context.setVariable("companyAdd", companyAdd);
		context.setVariable("branchAdd", branchAdd);
		context.setVariable("bondCode", bondCode);
		context.setVariable("city", city);

		String htmlContent = templateEngine.process("AuctionFinalNotice", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}
	
	
	
	
	
	
	public ResponseEntity<?> getDataForMainAuctionSearch(String cid, String bid, String igmTransId, String igmNo,
			String igmLineNo,String blNo) {

		List<String> idList = new ArrayList<>();
		Cfinbondcrg firstInbond = null;
		CfExBondCrg firstExBond = null;
		GateOut firstGateOut = null;
		CFBondGatePass firstGatePass = null;
		Map<String, Object> myMap = new HashMap<>();
		
		
		List<Object[]> mainList = autionRepo.getForMainAuctionSearch(cid, bid, igmTransId, igmNo, igmLineNo,blNo);

		if (mainList == null || mainList.isEmpty()) {
	        return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
	    }

		
		Object[] list = mainList.get(0);

		char type = (char) list[0];
		String igm = (String) list[2];
		String item = (String) list[3];
		String con = (String) list[4];
		String bl = (String) list[5];
		String n1 = (String) list[6];
		String n2 = (String) list[7];
		String n3 = (String) list[8];
	    // Assign IDs based on conditions
		
		String typeN =String.valueOf(type);
		
	    if ("P".equals(typeN)) {
	    	System.out.println(" in p ");
	    	idList.add("P01401");
	        idList.add("P01402");
	    } else if ("S".equals(typeN)) {
	    	System.out.println(" in s ");
	    	idList.add("P01401");
	        idList.add("P01402");
	        idList.add("P01403");
	    } else if ("F".equals(typeN)) {
	    	System.out.println(" in f ");
	    	idList.add("P01401");
	        idList.add("P01402");
	        idList.add("P01403");
	    }else
	    {
	    	idList.add("P01401");
	    }

	    // Prepare response data
	    myMap.put("list", list);
	    myMap.put("idList", idList);
	    myMap.put("firstNotice", firstInbond);
	    myMap.put("secondNotice", firstExBond);
	    myMap.put("finalNotice", firstGatePass);

		return new ResponseEntity<>(myMap, HttpStatus.OK);
	}
}
