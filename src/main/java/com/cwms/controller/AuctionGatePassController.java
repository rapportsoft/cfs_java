package com.cwms.controller;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cwms.entities.AuctionDetail;
import com.cwms.entities.Branch;
import com.cwms.entities.Company;
import com.cwms.entities.GateOut;
import com.cwms.entities.ImportGatePass;
import com.cwms.entities.VehicleTrack;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.AuctionCrgRepo;
import com.cwms.repository.AuctionRecordingRepo;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.GateOutRepo;
import com.cwms.repository.ImportGatePassRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@CrossOrigin("*")
@RestController
@RequestMapping("/auctionGatePass")
public class AuctionGatePassController {

	@Autowired
	private VehicleTrackRepository vehicleTrackRepo;

	@Autowired
	private AuctionRecordingRepo auctionrecordingrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private ImportGatePassRepo importgatepassrepo;

	@Autowired
	private HelperMethods helperMethods;

	@Autowired
	private AuctionCrgRepo auctioncrgrepo;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private GateOutRepo gateoutrepo;
	
	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@GetMapping("/getVehicleData")
	public ResponseEntity<?> getVehicleData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "id", required = false) String id) {

		try {
			List<Object[]> data = vehicleTrackRepo.getEmptyVehGateIn2(cid, bid, id, "N00002");

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>(data, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchBeforeSaveGatePass")
	public ResponseEntity<?> searchBeforeSaveGatePass(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "id", required = false) String id) {

		try {

			List<Object[]> data = auctionrecordingrepo.searchBeforeSaveGatePass(cid, bid, id);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getSelectedBeforeSaveGatePass")
	public ResponseEntity<?> getSelectedBeforeSaveGatePass(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("transid") String transid, @RequestParam("igm") String igm,
			@RequestParam("lineNo") String lineNo) {

		try {

			Object data = auctionrecordingrepo.getSelectedDataBeforSaveGatePass(cid, bid, transid, igm, lineNo);

			if (data == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveAuctionGatePass")
	public ResponseEntity<?> saveAuctionGatePass(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody ImportGatePass pass) {

		try {

			if (pass == null) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			if (pass.getGatePassId() == null || pass.getGatePassId().isEmpty()) {
				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05126", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("ACGP%06d", nextNumericNextID1);

				AuctionDetail existing = auctioncrgrepo.getDataByIgmDtls(cid, bid, pass.getIgmTransId(),
						pass.getIgmNo(), pass.getIgmLineNo());

				if (existing == null) {
					return new ResponseEntity<>("Auction data not found", HttpStatus.CONFLICT);
				}

				pass.setCompanyId(cid);
				pass.setBranchId(bid);
				pass.setStatus("A");
				pass.setCreatedBy(user);
				pass.setCreatedDate(new Date());
				pass.setApprovedBy(user);
				pass.setApprovedDate(new Date());
				pass.setProfitcentreId("N00002");
				pass.setGatePassId(HoldNextIdD1);
				pass.setGatePassDate(new Date());
				pass.setFinYear(helperMethods.getFinancialYear());
				pass.setSrNo(1);
				pass.setTransType("Auction");
				pass.setDestuffId(existing.getNoticeId());

				importgatepassrepo.save(pass);

				processnextidrepo.updateAuditTrail(cid, bid, "P05126", HoldNextIdD1, "2024");

				existing.setQtyTakenOut(
						(existing.getQtyTakenOut() == null ? BigDecimal.ZERO : existing.getQtyTakenOut())
								.add(pass.getQtyTakenOut()));

				auctioncrgrepo.save(existing);

				if (!pass.getVehicleGatePassId().isEmpty()) {
					VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, pass.getVehicleNo(),
							pass.getVehicleGatePassId());

					if (track != null) {
						track.setGatePassNo(HoldNextIdD1);
						track.setIgmNo(pass.getIgmNo());
						track.setIgmTransId(pass.getIgmTransId());

						vehicleTrackRepo.save(track);
					}
				}

				Object result = importgatepassrepo.getDataBtIdAndSr(cid, bid, pass.getIgmNo(), pass.getIgmLineNo(),
						HoldNextIdD1, 1);

				if (result == null) {
					return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
				}

				return new ResponseEntity<>(result, HttpStatus.OK);

			} else {

				ImportGatePass existingPass = importgatepassrepo.getData2(cid, bid, pass.getIgmNo(),
						pass.getIgmLineNo(), pass.getGatePassId(), 1);

				if (existingPass != null) {

					if (!pass.getVehicleGatePassId().equals(existingPass.getVehicleGatePassId())) {
						VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, pass.getVehicleNo(),
								pass.getVehicleGatePassId());

						if (track != null) {
							track.setGatePassNo(pass.getGatePassId());
							track.setIgmNo(pass.getIgmNo());
							track.setIgmTransId(pass.getIgmTransId());

							vehicleTrackRepo.save(track);
						}

						VehicleTrack track1 = vehicleTrackRepo.getDataByVehicleNo(cid, bid, pass.getVehicleNo(),
								existingPass.getVehicleGatePassId());

						if (track != null) {
							track1.setGatePassNo("");
							track1.setIgmNo("");
							track1.setIgmTransId("");

							vehicleTrackRepo.save(track1);
						}
					}

					existingPass.setEditedBy(user);
					existingPass.setEditedDate(new Date());
					existingPass.setShift(pass.getShift());
					existingPass.setVehicleGatePassId(pass.getVehicleGatePassId());
					existingPass.setVehicleNo(pass.getVehicleNo());
					existingPass.setDriverName(pass.getDriverName());
					existingPass.setDoNo(pass.getDoNo());
					existingPass.setDoDate(pass.getDoDate());
					existingPass.setDoValidityDate(pass.getDoValidityDate());
					existingPass.setTransporterStatus(pass.getTransporterStatus());
					existingPass.setTransporter(pass.getTransporter());
					existingPass.setTransporterName(pass.getTransporterName());
					existingPass.setRemarks(pass.getRemarks());

					importgatepassrepo.save(existingPass);

				}

				Object result = importgatepassrepo.getDataBtIdAndSr(cid, bid, pass.getIgmNo(), pass.getIgmLineNo(),
						pass.getGatePassId(), 1);

				if (result == null) {
					return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
				}

				return new ResponseEntity<>(result, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchAuctionGatePassData")
	public ResponseEntity<?> searchAuctionGatePassData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "id", required = false) String id) {

		try {

			List<Object[]> data = importgatepassrepo.searchAuctionData(cid, bid, id);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getGatePassSelectedData")
	public ResponseEntity<?> getGatePassSelectedData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("lineNo") String lineNo, @RequestParam("id") String id) {

		try {

			Object result = importgatepassrepo.getDataBtIdAndSr(cid, bid, igm, lineNo, id, 1);

			if (result == null) {
				return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/importGatePassItemWiseReport")
	public ResponseEntity<?> importGatePassItemWiseReport(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm, @RequestParam("lineNo") String lineNo,
			@RequestParam("id") String id) throws DocumentException {

		Object result = importgatepassrepo.getDataBtIdAndSr1(cid, bid, igm, lineNo, id, 1);

		if (result == null) {
			return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
		}

		Object[] crgData = (Object[]) result;

		Company comp = companyRepo.findByCompany_Id(cid);

		if (comp == null) {
			return new ResponseEntity<>("Company data not found", HttpStatus.CONFLICT);
		}

		Branch branchAddress = branchRepo.findByBranchIdWithCompanyId(cid, bid);

		if (branchAddress == null) {
			return new ResponseEntity<>("Branch data not found", HttpStatus.CONFLICT);
		}

		String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				+ branchAddress.getAddress3();

		Context context = new Context();
		context.setVariable("companyname", comp.getCompany_name());
		context.setVariable("branchName", branchAddress.getBranchName());
		context.setVariable("address", branchAdd);
		context.setVariable("gatePassId", crgData[0] != null ? String.valueOf(crgData[0]) : "");
		context.setVariable("gatePassDate", crgData[1] != null ? String.valueOf(crgData[1]) : "");
		context.setVariable("igmNo", crgData[8] != null ? String.valueOf(crgData[8]) : "");
		context.setVariable("itemNo", crgData[10] != null ? String.valueOf(crgData[10]) : "");
		context.setVariable("invoiceNo", crgData[6] != null ? String.valueOf(crgData[6]) : "");
		context.setVariable("invoiceDate", crgData[7] != null ? String.valueOf(crgData[7]) : "");
		context.setVariable("doNo", crgData[16] != null ? String.valueOf(crgData[16]) : "");
		context.setVariable("doValDate", crgData[18] != null ? String.valueOf(crgData[18]) : "");
		context.setVariable("commodity", crgData[21] != null ? String.valueOf(crgData[21]) : "");
		context.setVariable("remarks", crgData[20] != null ? String.valueOf(crgData[20]) : "");
		context.setVariable("transporter", crgData[15] != null ? String.valueOf(crgData[15]) : "");
		context.setVariable("transporterStatus",
				crgData[19] != null ? "P".equals(String.valueOf(crgData[19])) ? "Private" : "Contractor" : "");
		context.setVariable("vehicle", crgData[12] != null ? String.valueOf(crgData[12]) : "");
		context.setVariable("driver", crgData[13] != null ? String.valueOf(crgData[13]) : "");
		context.setVariable("qtyTakenOut", crgData[24] != null ? String.valueOf(crgData[24]) : "");
		String htmlContent = templateEngine.process("AuctionGatePass", context);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(htmlContent);
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);

		byte[] pdfBytes = outputStream.toByteArray();

		String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(base64Pdf);
	}

	// Auction Gate Out

	@GetMapping("/getGateOutBeforeSaveData")
	public ResponseEntity<?> getGateOutBeforeSaveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "id", required = false) String id) {

		try {

			List<Object[]> data = importgatepassrepo.getBeforeSaveAuctionGateOutData(cid, bid, id);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getSelectedGateOutBeforeSaveData")
	public ResponseEntity<?> getSelectedGateOutBeforeSaveData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("id") String id) {

		try {

			Object data = importgatepassrepo.getSelectedBeforeSaveAuctionGateOutData(cid, bid, id, 1);

			if (data == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveAuctionGateOut")
	public ResponseEntity<?> saveAuctionGateOut(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody GateOut out) {

		try {

			if (out == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			if (out.getGateOutId() == null || out.getGateOutId().isEmpty()) {

				ImportGatePass gatePass = importgatepassrepo.getGatePassData(cid, bid, out.getGatePassNo(), 1);

				if (gatePass == null) {
					return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);

				}

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05127", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("ACGO%06d", nextNumericNextID1);

				out.setCompanyId(cid);
				out.setBranchId(bid);
				out.setStatus('A');
				out.setCreatedBy(user);
				out.setCreatedDate(new Date());
				out.setApprovedBy(user);
				out.setApprovedDate(new Date());
				out.setFinYear(helperMethods.getFinancialYear());
				out.setErpDocRefNo(gatePass.getIgmTransId());
				out.setSrNo("1");
				out.setProfitcentreId(gatePass.getProfitcentreId());
				out.setTransType("Auction");
				out.setGateOutId(HoldNextIdD1);
				out.setGateOutDate(new Date());
				out.setProcessId("P01408");
				out.setInvoiceNo(gatePass.getInvoiceNo());
				out.setInvoiceDate(gatePass.getInvoiceDate());
				out.setActualNoOfPackages(new BigDecimal(gatePass.getNoOfPackage()));
				out.setTransporter(gatePass.getTransporter());
				out.setTransporterName(gatePass.getTransporterName());
				out.setVehicleId(gatePass.getVehicleGatePassId());
				out.setCommodityDescription(gatePass.getCommodity());

				gateoutrepo.save(out);

				processnextidrepo.updateAuditTrail(cid, bid, "P05127", HoldNextIdD1, "2024");

				if (gatePass.getVehicleGatePassId() != null && !gatePass.getVehicleGatePassId().isEmpty()) {
					VehicleTrack vehTrack = vehicleTrackRepo.getByGateInId(cid, bid, gatePass.getVehicleGatePassId());

					if (vehTrack != null) {
						vehTrack.setGateOutId(HoldNextIdD1);
						vehTrack.setGateOutDate(new Date());

						vehicleTrackRepo.save(vehTrack);
					}
				}
				
				gatePass.setGateOutId(HoldNextIdD1);
				gatePass.setGateOutDate(new Date());
				gatePass.setGateOutQty(out.getQtyTakenOut().intValue());
				
				importgatepassrepo.save(gatePass);
				
				int updateId = cfigmcnrepo.updategateOutId1(cid, bid, gatePass.getIgmTransId(), gatePass.getIgmNo(),
						gatePass.getIgmLineNo(), HoldNextIdD1);


				Object result = gateoutrepo.getAfterSaveGateOutData(cid, bid, HoldNextIdD1, "1");

				if (result == null) {
					return new ResponseEntity<>("Gate out data not found", HttpStatus.CONFLICT);

				}

				return new ResponseEntity<>(result, HttpStatus.OK);

			} else {

				GateOut gate = gateoutrepo.getGateOutData(cid, bid, out.getGateOutId(), "1");

				if (gate != null) {
					gate.setEditedBy(user);
					gate.setEditedDate(new Date());
					gate.setShift(out.getShift());
					gate.setGateNoOut(out.getGateNoOut());
					gate.setComments(out.getComments());

					gateoutrepo.save(gate);
				}

				Object result = gateoutrepo.getAfterSaveGateOutData(cid, bid, out.getGateOutId(), "1");

				if (result == null) {
					return new ResponseEntity<>("Gate out data not found", HttpStatus.CONFLICT);

				}

				return new ResponseEntity<>(result, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@GetMapping("/searchAuctionGateOutData")
	public ResponseEntity<?> searchAuctionGateOutData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "id", required = false) String id) {

		try {

			List<Object[]> data = gateoutrepo.searchAuctionGateOutData(cid, bid, id);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchSelectedAuctionGateOutData")
	public ResponseEntity<?> searchSelectedAuctionGateOutData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("id") String id) {

		try {

			Object result = gateoutrepo.getAfterSaveGateOutData(cid, bid, id, "1");

			if (result == null) {
				return new ResponseEntity<>("Gate out data not found", HttpStatus.CONFLICT);

			}

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
