package com.cwms.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import org.springframework.web.multipart.MultipartFile;

import com.cwms.entities.CFIgm;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.ImportGatePass;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.ImportGatePassRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.coobird.thumbnailator.Thumbnails;

@RestController
@CrossOrigin("*")
@RequestMapping("/importGatePass")
public class ImportGatePassController {

	@Autowired
	private ImportGatePassRepo importgatepassrepo;

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;
	
	@Value("${file.upload.import}")
    private String filePath;

	@GetMapping("/getItemWiseData")
	public ResponseEntity<?> getItemData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("item") String item) {

		List<Cfigmcn> data = cfigmcnrepo.getDataForGatePass(cid, bid, igm, item);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		Cfigmcrg crg = cfigmcrgrepo.getData4(cid, bid, igm, item);

		if (crg == null) {
			return new ResponseEntity<>("Item data not found", HttpStatus.CONFLICT);
		}

		Map<String, Object> con = new HashedMap<>();
		con.put("crg", crg);
		con.put("container", data);

		return new ResponseEntity<>(con, HttpStatus.OK);
	}

	@PostMapping("/saveItemwiseData")
	public ResponseEntity<?> saveItemwiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("vehicleStatus") String vehicleStatus,
			@RequestBody Map<String, Object> data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		ImportGatePass crg = mapper.readValue(mapper.writeValueAsString(data.get("crg")), ImportGatePass.class);

		if (crg == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}
		CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());

		List<ImportGatePass> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
				new TypeReference<List<ImportGatePass>>() {
				});
		if (container.isEmpty()) {
			return new ResponseEntity<>("Container Data not found", HttpStatus.CONFLICT);
		}
		
		int sr = 1;
		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05068", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("IMG%07d", nextNumericNextID1);
		
		for (ImportGatePass i : container) {
			if (i.getGatePassId().isEmpty()) {

				Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(), i.getContainerNo());
				Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, i.getIgmNo(), i.getIgmLineNo());
				i.setGatePassId(HoldNextIdD1);
				i.setVehicleGatePassId(HoldNextIdD1);
				i.setVehStatus(vehicleStatus);
				i.setSrNo(sr);
				
				if("N".equals(vehicleStatus)) {
					i.setVehicleNo("");
					i.setDriverName("");
				}
				
				i.setCompanyId(cid);
				i.setBranchId(bid);
				i.setSl(igm.getShippingLine());
				i.setStatus("A");
				i.setShift(crg.getShift());
				i.setGrnNo(crg.getGrnNo());
				i.setGrnDate(crg.getGrnDate());
				i.setCreatedBy(user);
				i.setCreatedDate(new Date());
				i.setApprovedBy(user);
				i.setApprovedDate(new Date());
				i.setComments(crg.getComments());
				i.setStampDuty(crg.getStampDuty());
				i.setCinNo(crg.getCinNo());
				i.setCinDate(crg.getCinDate());
				i.setDoNo(crg.getDoNo());
				i.setOocNo(crg.getOocNo());
				i.setOocDate(crg.getOocDate());
				i.setDoDate(crg.getDoDate());
				i.setDoValidityDate(crg.getDoValidityDate());
				i.setMtyYardLocation(crg.getMtyYardLocation());
				i.setCha(cr.getChaCode());
				
				importgatepassrepo.save(i);
				
				
				
				if(cn != null) {
					cn.setGatePassNo(HoldNextIdD1);
					cn.setShift(i.getShift());
					cn.setGrnNo(i.getGrnNo());
					cn.setGrnDate(i.getGrnDate());
					cn.setStampDuty(i.getStampDuty());
					cn.setCinNo(i.getCinNo());
					cn.setCinDate(crg.getCinDate());
					cn.setDoNo(crg.getDoNo());
					cn.setOocNo(crg.getOocNo());
					cn.setOocDate(crg.getOocDate());
					cn.setDoDate(crg.getDoDate());
					cn.setDoValidityDate(crg.getDoValidityDate());
					
					
					cfigmcnrepo.save(cn);
				}

				processnextidrepo.updateAuditTrail(cid, bid, "P05068", HoldNextIdD1, "2024");
				sr++;
			} else {
                ImportGatePass existing = importgatepassrepo.getSingleData(cid, bid, i.getIgmNo(), i.getIgmLineNo(), i.getContainerNo(), 
                		i.getGatePassId(), i.getSrNo());
                
                if(existing != null) {
                	existing.setEditedBy(user);
                	existing.setEditedDate(new Date());
                	existing.setShift(crg.getShift());
    				existing.setGrnNo(crg.getGrnNo());
    				existing.setGrnDate(crg.getGrnDate());
    				existing.setComments(crg.getComments());
    				existing.setStampDuty(crg.getStampDuty());
    				existing.setCinNo(crg.getCinNo());
    				existing.setCinDate(crg.getCinDate());
    				existing.setDoNo(crg.getDoNo());
    				existing.setOocNo(crg.getOocNo());
    				existing.setOocDate(crg.getOocDate());
    				existing.setDoDate(crg.getDoDate());
    				existing.setDoValidityDate(crg.getDoValidityDate());
    				existing.setMtyYardLocation(crg.getMtyYardLocation());
    				existing.setVehStatus(vehicleStatus);
    				if("N".equals(vehicleStatus)) {
    					existing.setVehicleNo("");
    					existing.setDriverName("");
    				}
    				else {
    					existing.setVehicleNo(i.getVehicleNo());
    					existing.setDriverName(i.getDriverName());
    				}
    				
    				importgatepassrepo.save(existing);
    				
    				Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(), i.getContainerNo());
    				
    				if(cn != null) {
    					
    					cn.setShift(i.getShift());
    					cn.setGrnNo(i.getGrnNo());
    					cn.setGrnDate(i.getGrnDate());
    					cn.setStampDuty(i.getStampDuty());
    					cn.setCinNo(i.getCinNo());
    					cn.setCinDate(crg.getCinDate());
    					cn.setDoNo(crg.getDoNo());
    					cn.setOocNo(crg.getOocNo());
    					cn.setOocDate(crg.getOocDate());
    					cn.setDoDate(crg.getDoDate());
    					cn.setDoValidityDate(crg.getDoValidityDate());
    					
    					
    					cfigmcnrepo.save(cn);
    				}
                }
			}
		}
		
		if(crg.getGatePassId() == null || crg.getGatePassId().isEmpty()) {
			List<ImportGatePass> gatepass = importgatepassrepo.getData(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(), HoldNextIdD1);
			
			if(gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found",HttpStatus.CONFLICT);
			}
			
			return new ResponseEntity<>(gatepass,HttpStatus.OK);
		}
		else {
			List<ImportGatePass> gatepass = importgatepassrepo.getData(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(), crg.getGatePassId());
			
			if(gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found",HttpStatus.CONFLICT);
			}
			
			return new ResponseEntity<>(gatepass,HttpStatus.OK);
		}

	}
	
	@GetMapping("/searchByItemwise")
	public ResponseEntity<?> searchByItemwise(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name="val",required = false) String val){
		
		List<Object[]> data = importgatepassrepo.searchForItemWise(cid, bid, val);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		else {
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	
	@GetMapping("/selectSearchedItemWiseData")
	public ResponseEntity<?> selectSearchedItemWiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmline") String igmline,@RequestParam("gatepassid") String gatepassid){
		List<ImportGatePass> gatepass = importgatepassrepo.getData(cid, bid, igm, igmline, gatepassid);
		
		if(gatepass.isEmpty()) {
			return new ResponseEntity<>("Gate Pass data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(gatepass,HttpStatus.OK);
	}

	
	@PostMapping("/uploadItemwiseImage")
	public ResponseEntity<String> uploadImage(
	        @RequestParam("cid") String cid,
	        @RequestParam("bid") String bid,
	        @RequestParam("igm") String igm,
	        @RequestParam("igmline") String igmline,
	        @RequestParam("gatePassId") String gatepassid,
	        @RequestParam("file") MultipartFile file) {

	    if (file.isEmpty()) {
	        return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
	    }

	    try {
	        // Define the path to save the file
	        String uploadDir = filePath;
	        File directory = new File(uploadDir);
	        if (!directory.exists()) {
	            directory.mkdir();
	        }

	        // Define the compressed file path
	        String compressedFilePath = uploadDir + gatepassid + ".png";
	        File compressedFile = new File(compressedFilePath);

	        // Initialize compression parameters
	        double quality = 0.9; // Start with near lossless compression
	        int width = 800;      // Initial width
	        int height = 600;     // Initial height
	        long maxFileSize = 200 * 1024; // Max file size 200KB

	        // Compress and check file size
	        do {
	            // Compress the image
	            Thumbnails.of(file.getInputStream())
	                      .size(width, height)      // Resize image
	                      .outputQuality(quality)   // Adjust compression quality
	                      .toFile(compressedFile);

	            // Check the file size
	            long fileSize = compressedFile.length();
	            
	            // If the file is larger than 200KB, reduce quality or dimensions
	            if (fileSize > maxFileSize) {
	                quality -= 0.05; // Reduce quality by 5% in each iteration
	                width -= 100;    // Reduce width by 100px
	                height -= 75;    // Reduce height proportionally
	            }

	            // Break if file is smaller than 200KB or quality is too low
	        } while (compressedFile.length() > maxFileSize && quality > 0.1 && width > 100 && height > 100);

	        // Update the database with the new file path
	        List<ImportGatePass> gatePass = importgatepassrepo.getData1(cid, bid, igm, igmline, gatepassid);
	        if (!gatePass.isEmpty()) {
	            gatePass.forEach(g -> {
	                g.setWebCamPath(compressedFilePath);
	                importgatepassrepo.save(g);
	            });
	        }

	        return new ResponseEntity<>("File uploaded successfully: " + compressedFilePath, HttpStatus.OK);
	    } catch (IOException e) {
	        return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
	@PostMapping("/uploadConwiseImage")
	public ResponseEntity<String> uploadImage1(
	        @RequestParam("cid") String cid,
	        @RequestParam("bid") String bid,
	        @RequestParam("igm") String igm,
	        @RequestParam("gatePassId") String gatepassid,
	        @RequestParam("file") MultipartFile file) {

	    if (file.isEmpty()) {
	        return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
	    }

	    try {
	        // Define the path to save the file
	        String uploadDir = filePath;
	        File directory = new File(uploadDir);
	        if (!directory.exists()) {
	            directory.mkdir();
	        }

	        // Define the compressed file path
	        String compressedFilePath = uploadDir + gatepassid + ".png";
	        File compressedFile = new File(compressedFilePath);

	        // Initialize compression parameters
	        double quality = 0.9; // Start with near lossless compression
	        int width = 800;      // Initial width
	        int height = 600;     // Initial height
	        long maxFileSize = 200 * 1024; // Max file size 200KB

	        // Compress and check file size
	        do {
	            // Compress the image
	            Thumbnails.of(file.getInputStream())
	                      .size(width, height)      // Resize image
	                      .outputQuality(quality)   // Adjust compression quality
	                      .toFile(compressedFile);

	            // Check the file size
	            long fileSize = compressedFile.length();
	            
	            // If the file is larger than 200KB, reduce quality or dimensions
	            if (fileSize > maxFileSize) {
	                quality -= 0.05; // Reduce quality by 5% in each iteration
	                width -= 100;    // Reduce width by 100px
	                height -= 75;    // Reduce height proportionally
	            }

	            // Break if file is smaller than 200KB or quality is too low
	        } while (compressedFile.length() > maxFileSize && quality > 0.1 && width > 100 && height > 100);

	        // Update the database with the new file path
	        List<ImportGatePass> gatePass = importgatepassrepo.getData3(cid, bid, igm, gatepassid);
	        if (!gatePass.isEmpty()) {
	            gatePass.forEach(g -> {
	                g.setWebCamPath(compressedFilePath);
	                importgatepassrepo.save(g);
	            });
	        }

	        return new ResponseEntity<>("File uploaded successfully: " + compressedFilePath, HttpStatus.OK);
	    } catch (IOException e) {
	        return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
	 @GetMapping("/getItemwiseImage")
	    public ResponseEntity<?> getImage( @RequestParam("cid") String cid,
		        @RequestParam("bid") String bid,
		        @RequestParam("igm") String igm,
		        @RequestParam("igmline") String igmline,
		        @RequestParam("gatePassId") String gatepassid,
		        @RequestParam("sr") int sr) {
	        try {
	        	
	        	
	        	ImportGatePass data = importgatepassrepo.getData2(cid, bid, igm, igmline, gatepassid, sr);
	            // Construct the file path
	            
	        	if(data != null) {
	        		 String filePath = data.getWebCamPath();
	 	            File file = new File(filePath);

	 	            // Check if the file exists
	 	            if (!file.exists()) {
	 	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 	            }

	 	            // Serve the file
	 	            Path path = Paths.get(file.getAbsolutePath());
	 	            Resource resource = new UrlResource(path.toUri());

	 	            return ResponseEntity.ok()
	 	                    .contentType(MediaType.IMAGE_PNG) // Adjust according to your image type
	 	                    .body(resource);
	        	}
	        	else {
	        		return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
	        	}

	        } catch (MalformedURLException e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	 
	 
		@GetMapping("/getContWiseData")
		public ResponseEntity<?> getContWiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("igm") String igm, @RequestParam("cont") String cont) {

			List<Cfigmcn> data = cfigmcnrepo.getDataForGatePass1(cid, bid, igm, cont);

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			List<Cfigmcrg> crg = new ArrayList<>();

			data.stream().forEach(c->{
	        	Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, c.getIgmNo(), c.getIgmLineNo());
	        	
	        	System.out.println("cr "+cr);
	        	
	        	if(cr != null) {
	        		crg.add(cr);
	        	}
	        });
			
			Map<String, Object> con = new HashedMap<>();
			con.put("crg", crg);
			con.put("container", data);

			return new ResponseEntity<>(con, HttpStatus.OK);
		}

		
		
		@PostMapping("/saveContainerwiseData")
		public ResponseEntity<?> saveContainerwiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("user") String user, 
				@RequestBody Map<String, Object> data) throws JsonMappingException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();

			ImportGatePass crg = mapper.readValue(mapper.writeValueAsString(data.get("crg")), ImportGatePass.class);

			if (crg == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}
			CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());

			List<ImportGatePass> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
					new TypeReference<List<ImportGatePass>>() {
					});
			if (container.isEmpty()) {
				return new ResponseEntity<>("Container Data not found", HttpStatus.CONFLICT);
			}
			
			int sr = 1;
			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05068", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("IMG%07d", nextNumericNextID1);
			
			for (ImportGatePass i : container) {
				if (i.getGatePassId().isEmpty()) {

					Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(), i.getContainerNo());
					Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, i.getIgmNo(), i.getIgmLineNo());

					i.setGatePassId(HoldNextIdD1);
					i.setVehicleGatePassId(HoldNextIdD1);
					i.setVehStatus("Y");
					i.setSrNo(sr);
					i.setGatePassType("CON");
					
//					if("N".equals(vehicleStatus)) {
//						i.setVehicleNo("");
//						i.setDriverName("");
//					}
					i.setVehicleNo(crg.getVehicleNo());
					i.setDriverName(crg.getDriverName());
					i.setCompanyId(cid);
					i.setBranchId(bid);
					i.setDpdFlag(crg.getDpdFlag());
					i.setSl(igm.getShippingLine());
					i.setStatus("A");
					i.setShift(crg.getShift());
					//i.setGrnNo(crg.getGrnNo());
					//i.setGrnDate(crg.getGrnDate());
					i.setCreatedBy(user);
					i.setCreatedDate(new Date());
					i.setApprovedBy(user);
					i.setApprovedDate(new Date());
					i.setComments(crg.getComments());
					//i.setStampDuty(crg.getStampDuty());
					//i.setCinNo(crg.getCinNo());
					//i.setCinDate(crg.getCinDate());
					i.setDoNo(crg.getDoNo());
					i.setOocNo(crg.getOocNo());
					i.setOocDate(crg.getOocDate());
					i.setDoDate(crg.getDoDate());
					i.setDoValidityDate(crg.getDoValidityDate());
					i.setMtyYardLocation(crg.getMtyYardLocation());
					i.setCha(cr.getChaCode());
					
					importgatepassrepo.save(i);
					
					
					
					if(cn != null) {
						cn.setGatePassNo(HoldNextIdD1);
						cn.setShift(i.getShift());
						cn.setGrnNo(i.getGrnNo());
						cn.setGrnDate(i.getGrnDate());
						cn.setStampDuty(i.getStampDuty());
						cn.setCinNo(i.getCinNo());
						cn.setCinDate(i.getCinDate());
						cn.setDoNo(crg.getDoNo());
						cn.setOocNo(crg.getOocNo());
						cn.setOocDate(crg.getOocDate());
						cn.setDoDate(crg.getDoDate());
						cn.setDoValidityDate(crg.getDoValidityDate());
						
						
						cfigmcnrepo.save(cn);
					}

					processnextidrepo.updateAuditTrail(cid, bid, "P05068", HoldNextIdD1, "2024");
					sr++;
				} else {
	                ImportGatePass existing = importgatepassrepo.getSingleData(cid, bid, i.getIgmNo(), i.getIgmLineNo(), i.getContainerNo(), 
	                		i.getGatePassId(), i.getSrNo());
	                
	                if(existing != null) {
	                	existing.setEditedBy(user);
	                	existing.setEditedDate(new Date());
	                	existing.setShift(crg.getShift());
	    				existing.setGrnNo(i.getGrnNo());
	    				existing.setGrnDate(i.getGrnDate());
	    				existing.setComments(crg.getComments());
	    				existing.setStampDuty(i.getStampDuty());
	    				existing.setCinNo(i.getCinNo());
	    				existing.setCinDate(i.getCinDate());
	    				existing.setDoNo(crg.getDoNo());
	    				existing.setOocNo(crg.getOocNo());
	    				existing.setOocDate(crg.getOocDate());
	    				existing.setDoDate(crg.getDoDate());
	    				existing.setDoValidityDate(crg.getDoValidityDate());
	    				existing.setMtyYardLocation(crg.getMtyYardLocation());
	    				existing.setVehicleNo(crg.getVehicleNo());
	    				existing.setDriverName(crg.getDriverName());
	    				importgatepassrepo.save(existing);
	    				
	    				Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(), i.getContainerNo());
	    				
	    				if(cn != null) {
	    					
	    					cn.setShift(i.getShift());
	    					cn.setGrnNo(i.getGrnNo());
	    					cn.setGrnDate(i.getGrnDate());
	    					cn.setStampDuty(i.getStampDuty());
	    					cn.setCinNo(i.getCinNo());
	    					cn.setCinDate(i.getCinDate());
	    					cn.setDoNo(crg.getDoNo());
	    					cn.setOocNo(crg.getOocNo());
	    					cn.setOocDate(crg.getOocDate());
	    					cn.setDoDate(crg.getDoDate());
	    					cn.setDoValidityDate(crg.getDoValidityDate());
	    					
	    					
	    					cfigmcnrepo.save(cn);
	    				}
	                }
				}
			}
			System.out.println("cid, bid, crg.getIgmNo(),  HoldNextIdD1 "+cid+" "+ bid+" "+ crg.getIgmNo()+" "+  HoldNextIdD1);
			if(crg.getGatePassId() == null || crg.getGatePassId().isEmpty()) {
				List<ImportGatePass> gatepass = importgatepassrepo.getData2(cid, bid, crg.getIgmNo(),  HoldNextIdD1);
				System.out.println(gatepass.size());
				
				if(gatepass.isEmpty()) {
					return new ResponseEntity<>("Gate Pass data not found",HttpStatus.CONFLICT);
				}
				
				return new ResponseEntity<>(gatepass,HttpStatus.OK);
			}
			else {
				List<ImportGatePass> gatepass = importgatepassrepo.getData2(cid, bid, crg.getIgmNo(),  crg.getGatePassId());
				System.out.println(gatepass.size());
				if(gatepass.isEmpty()) {
					return new ResponseEntity<>("Gate Pass data not found",HttpStatus.CONFLICT);
				}
				
				return new ResponseEntity<>(gatepass,HttpStatus.OK);
			}

		}
		
		@GetMapping("/searchByContwise")
		public ResponseEntity<?> searchByContwise(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam(name="val",required = false) String val){
			
			List<Object[]> data = importgatepassrepo.searchForContWise(cid, bid, val);
			
			if(data.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			else {
				return new ResponseEntity<>(data,HttpStatus.OK);
			}
		}
		
		@GetMapping("/selectSearchedContWiseData")
		public ResponseEntity<?> selectSearchedContWiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("igm") String igm,@RequestParam("gatepassid") String gatepassid){
			List<ImportGatePass> gatepass = importgatepassrepo.getData2(cid, bid, igm,  gatepassid);
			
			if(gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found",HttpStatus.CONFLICT);
			}
			
			return new ResponseEntity<>(gatepass,HttpStatus.OK);
		}
		
		@GetMapping("/getExistingItemWiseData")
		public ResponseEntity<?> getExistingItemWiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("igm") String igm,@RequestParam("item") String item){
			Cfigmcn i = cfigmcnrepo.getSingleDataFoeGatePass(cid, bid, igm, item);
			
			if(i == null) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			
			if(i.getGatePassNo().isEmpty() || i.getGatePassNo() == null) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			
			return new ResponseEntity<>(i.getGatePassNo(),HttpStatus.OK);
			
		}
		
		
		@GetMapping("/getExistingContWiseData")
		public ResponseEntity<?> getExistingContWiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("igm") String igm,@RequestParam("con") String con){
			Cfigmcn i = cfigmcnrepo.getSingleDataFoeGatePass2(cid, bid, igm, con);
			
			if(i == null) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			
			if(i.getGatePassNo().isEmpty() || i.getGatePassNo() == null) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			
			return new ResponseEntity<>(i.getGatePassNo(),HttpStatus.OK);
			
		}
		
		@GetMapping("/getItems")
		public ResponseEntity<?> getItems(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("igm") String igm){
			List<String> i = cfigmcnrepo.getItems(cid, bid, igm);
			
			if(i.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			
			
			
			return new ResponseEntity<>(i,HttpStatus.OK);
			
		}
		
		@GetMapping("/getContainers")
		public ResponseEntity<?> getContainers(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("igm") String igm){
			List<String> i = cfigmcnrepo.getContainerss(cid, bid, igm);
			
			if(i.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			
			
			
			return new ResponseEntity<>(i,HttpStatus.OK);
			
		}
		
		
}
