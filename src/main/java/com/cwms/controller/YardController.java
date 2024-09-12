package com.cwms.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.YardBlockCell;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.YardBlockCellRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/yardblockcells")
public class YardController {

	@Autowired
	private YardBlockCellRepository yardBlockCellRepository;

	@Autowired
	private ProcessNextIdRepository processNextIdRepository;

	@GetMapping
	public List<YardBlockCell> getAllYardBlockCells() {
		return yardBlockCellRepository.findAll();
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostMapping("/addLocations/{companyId}/{branchId}/{userId}")
	public ResponseEntity<?> createYardBlockCell(@RequestBody YardBlockCell yardBlockCell,
			@PathVariable String companyId, @PathVariable String branchId, @PathVariable String userId) {

		System.out.println("yardBlockCell_______________________" + yardBlockCell);

		// Check if yardBlockCell is null
		if (yardBlockCell == null) {
			return ResponseEntity.badRequest().body("YardBlockCell object is null.");
		}

		// Check if the record already exists
		YardBlockCell existing = yardBlockCellRepository.getAllData(companyId, branchId,
				yardBlockCell.getYardLocationId(), yardBlockCell.getBlockId(), yardBlockCell.getCellNoRow());

		System.out.println("existing_______________" + existing);
		if (existing != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate record exists.");
		} else {
			String IMPTransId = processNextIdRepository.findNextID(companyId, branchId, "2024", "P05058");

			int lastNextNumericId = Integer.parseInt(IMPTransId.substring(2));

			int nextNumericNextID = lastNextNumericId + 1;
			String id = IMPTransId.substring(0, 2);

			String NextimpTransId = String.format(id + "%06d", nextNumericNextID);

			yardBlockCell.setYardTransId(NextimpTransId);
			yardBlockCell.setCompanyId(companyId);
			yardBlockCell.setYardId(branchId);
			yardBlockCell.setStatus("A");
			yardBlockCell.setCreatedDate(new Date());
			yardBlockCell.setApprovedDate(new Date());
			yardBlockCell.setApprovedBy(userId);
			yardBlockCell.setCreatedBy(userId);
			yardBlockCell.setCellStatus("N");

			// If record does not exist, save the new record
			YardBlockCell savedYardBlockCell = yardBlockCellRepository.save(yardBlockCell);
			processNextIdRepository.updateNextID(companyId, branchId, "2024", "P05058", NextimpTransId);
			return ResponseEntity.ok(savedYardBlockCell);
		}
	}

	@GetMapping("/getAlldataOfLocations/{cid}/{bid}")
	public List<YardBlockCell> getAllParties1(@PathVariable("cid") String cid, @PathVariable("bid") String bid) {
		List<YardBlockCell> list = yardBlockCellRepository.getalldata(cid, bid);
		return list;
	}

	@PutMapping("/updateLocations/{companyId}/{branchId}/{yardLocationId}/{blockId}/{cellNoRow}")
	public ResponseEntity<?> updateYardBlockCell(@PathVariable String companyId, @PathVariable String branchId,
			@PathVariable String yardLocationId, @PathVariable String blockId, @PathVariable String cellNoRow,
			@RequestBody YardBlockCell yardBlockCellDetails) {
		System.out.println("yardBlockCellDetails________________" + yardBlockCellDetails);

		if (yardBlockCellDetails != null) {
			Boolean duplicate = yardBlockCellRepository.getAllData1(companyId, branchId,
					yardBlockCellDetails.getYardLocationId(), yardBlockCellDetails.getBlockId(),
					yardBlockCellDetails.getCellNoRow(),yardBlockCellDetails.getYardTransId());

			if (duplicate) {
//	                    return ResponseEntity.status(409).body("Duplicate entry found for the provided details.");

				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("Duplicate entry found for the provided details.");
			}
			
			
			YardBlockCell existingYardBlockCell = yardBlockCellRepository.getAllData(companyId, branchId,
					yardLocationId, blockId, cellNoRow);

			if (existingYardBlockCell != null) {

				

				// Update the existingYardBlockCell with new details
				existingYardBlockCell.setCellNoRow(yardBlockCellDetails.getCellNoRow());
				existingYardBlockCell.setBlockId(yardBlockCellDetails.getBlockId());
				existingYardBlockCell.setYardLocationDesc(yardBlockCellDetails.getYardLocationDesc());
				existingYardBlockCell.setYardLocationId(yardBlockCellDetails.getYardLocationId());
				existingYardBlockCell.setCellArea(yardBlockCellDetails.getCellArea());
				existingYardBlockCell.setCellAreaUsed(yardBlockCellDetails.getCellAreaUsed());
				existingYardBlockCell.setLocationCategory(yardBlockCellDetails.getLocationCategory());
				existingYardBlockCell.setMovementId(yardBlockCellDetails.getMovementId());
				existingYardBlockCell.setCellStatus(yardBlockCellDetails.getCellStatus());
				existingYardBlockCell.setCreatedBy(yardBlockCellDetails.getCreatedBy());
				existingYardBlockCell.setCreatedDate(yardBlockCellDetails.getCreatedDate());
				existingYardBlockCell.setApprovedBy(yardBlockCellDetails.getApprovedBy());
				existingYardBlockCell.setApprovedDate(yardBlockCellDetails.getApprovedDate());
				existingYardBlockCell.setStatus(yardBlockCellDetails.getStatus());

				// Execute the update query using JdbcTemplate
				String sql = "UPDATE yardblockcell SET cell_no_row = ?, block_id = ?, yard_location_desc = ?, yard_location_id = ?, cell_area = ?, cell_area_used = ?, location_category = ?, movement_id = ?, cell_status = ?, created_by = ?, created_date = ?, approved_by = ?, approved_date = ?, status = ? WHERE company_id = ? AND yard_id = ? AND yard_location_id = ? AND block_id = ? AND cell_no_row = ?";

				int rowsUpdated = jdbcTemplate.update(sql, existingYardBlockCell.getCellNoRow(),
						existingYardBlockCell.getBlockId(), existingYardBlockCell.getYardLocationDesc(),
						existingYardBlockCell.getYardLocationId(), existingYardBlockCell.getCellArea(),
						existingYardBlockCell.getCellAreaUsed(), existingYardBlockCell.getLocationCategory(),
						existingYardBlockCell.getMovementId(), existingYardBlockCell.getCellStatus(),
						existingYardBlockCell.getCreatedBy(), existingYardBlockCell.getCreatedDate(),
						existingYardBlockCell.getApprovedBy(), existingYardBlockCell.getApprovedDate(),
						existingYardBlockCell.getStatus(), companyId, branchId, yardLocationId, blockId, cellNoRow);

				if (rowsUpdated > 0) {
					return ResponseEntity.ok("YardBlockCell updated successfully.");
				} else {
					return ResponseEntity.notFound().build();
				}
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.badRequest().body("Invalid YardBlockCell details provided.");
		}
	}

	@DeleteMapping("/deleteLocations/{companyId}/{branchId}/{yardLocationId}/{blockId}/{cellNoRow}")
	public ResponseEntity<Void> deleteYardBlockCell(@PathVariable String companyId, @PathVariable String branchId,
			@PathVariable String yardLocationId, @PathVariable String blockId, @PathVariable String cellNoRow) {

		YardBlockCell yardBlockCell = yardBlockCellRepository.getAllData(companyId, branchId, yardLocationId, blockId,
				cellNoRow);

		if (yardBlockCell != null) {
			yardBlockCell.setStatus("D"); // Assuming 'D' means deleted or inactive
			yardBlockCellRepository.save(yardBlockCell);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@GetMapping("/getData/{companyId}/{branchId}/{yardLocationId}/{blockId}/{cellNoRow}")
	public YardBlockCell getData(@PathVariable String companyId, @PathVariable String branchId,
			@PathVariable String yardLocationId, @PathVariable String blockId, @PathVariable String cellNoRow) {

		YardBlockCell yardBlockCell = yardBlockCellRepository.getAllData(companyId, branchId, yardLocationId, blockId,
				cellNoRow);

		return yardBlockCell;
	}

	@GetMapping("/getLocations/{companyId}/{yardId}")
	public List<YardBlockCell> getYardBlockCells(@PathVariable String companyId, @PathVariable String yardId) {
		return yardBlockCellRepository.findYardBlockCell(companyId, yardId);
	}
	
	
	@GetMapping("/getAllRecords")
	public List<YardBlockCell> getAll(@RequestParam("cid") String cid,@RequestParam("val") String val){
		return yardBlockCellRepository.getAll(cid,val);
	}
	
	@GetMapping("/getLocationsAllYardCell")
	public List<YardBlockCell> getLocationsAllYardCell(
	        @RequestParam("companyId") String companyId,
	        @RequestParam("branchId") String branchId,
	        @RequestParam(name = "search", required = false) String search) {
	    return yardBlockCellRepository.findAllYardBlockCell(companyId, branchId, search);
	}
	
	@GetMapping("/getAllYard")
	public ResponseEntity<?> getAllYard(
	        @RequestParam("companyId") String companyId,
	        @RequestParam("branchId") String branchId,
	        @RequestParam(name = "search", required = false) String search) {

	    String partA = search;  // Default to the whole search string
	    String partB = null;
	    String partC = null;

	    // Check if search contains "-"
	    if (search != null && search.contains("-")) {
	        String[] parts = search.split("-");

	        if (parts.length > 0) {
	            partA = parts[0];
	        }
	        if (parts.length > 1) {
	            partB = parts[1];
	        }
	        if (parts.length > 2) {
	            partC = parts[2];
	        }
	    }

	    List<YardBlockCell> data = yardBlockCellRepository.getAll1(companyId, partA, partB, partC);

	    if (data.isEmpty()) {
	        return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
	    } else {
	        return new ResponseEntity<>(data, HttpStatus.OK);
	    }
	}

}
