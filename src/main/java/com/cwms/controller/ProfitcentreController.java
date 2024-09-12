package com.cwms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Profitcentre;
import com.cwms.repository.ProfitcentreRepository;
import com.cwms.service.ProfitcentreService;

@RestController
@RequestMapping("/api/profitcentres")
@CrossOrigin("*")
public class ProfitcentreController {

	
	@Autowired
    private ProfitcentreService profitcentreService;
	
	@Autowired
	private ProfitcentreRepository profitcenterrepo;
    
    @GetMapping("/getProfitcenterAllData")
    public List<Profitcentre> getAllProfitcentres( @RequestParam ("companyId")String companyId,@RequestParam ("branchId") String branchId,
    		@RequestParam (name = "vesselMandatory", required = false) String vesselMandatory,
    		@RequestParam (name = "joMandatory", required = false) String joMandatory,
    		@RequestParam (name = "containerMandatory", required = false) String containerMandatory,
    		@RequestParam (name = "desc", required = false) String desc) {
        return profitcentreService.findAll(companyId,branchId,vesselMandatory,joMandatory,containerMandatory,desc);
    }

    
    @GetMapping("/getDataById")
    public Profitcentre getProfitCenterDataById( @RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
    		@RequestParam("profitcentreId") String profitcentreId) {
        return profitcentreService.getDataById(companyId,branchId,profitcentreId);
    }



    @PostMapping("/saveProfitCenter")
	public ResponseEntity<?> savePartyWTAdd(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("flag") String flag, @RequestParam("user") String user, @RequestBody Profitcentre profitcenter) {
		System.out.println("Party " + profitcenter);
		
		
		return profitcentreService.saveProfitCenter(cid, bid, flag, user, profitcenter);

	}
    
    @PostMapping("/deleteProfitCenter")
	public String deleteById(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestParam("profitcentreId") String profitcentreId) {
    	int data =profitcentreService.deleteProfitCenterById(companyId, branchId, profitcentreId);
		return "success";
	}

//    @PutMapping("/{companyId}/{profitcentreId}")
//    public ResponseEntity<Profitcentre> updateProfitcentre(
//            @PathVariable String companyId,
//            @PathVariable String profitcentreId,
//            @RequestBody Profitcentre profitcentre) {
//        ProfitcentreId id = new ProfitcentreId();
//        if (!profitcentreService.findById(id).isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//        profitcentre.setCompanyId(companyId);
//        profitcentre.setProfitcentreId(profitcentreId);
//        Profitcentre updatedProfitcentre = profitcentreService.save(profitcentre);
//        return ResponseEntity.ok(updatedProfitcentre);
//    }

    
    
    @GetMapping("/getDataByProfitCenterId")
    public Profitcentre getProfitCenterDataById1( @RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId) {
        return profitcenterrepo.getAllDataByID(companyId, branchId, "N00002");
    }
    
    @GetMapping("/getDataByProfitCenterId1")
    public Profitcentre getProfitCenterDataById11( @RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
    		@RequestParam("profit") String profit) {
        return profitcenterrepo.getAllDataByID(companyId, branchId, profit);
    }
    
    @GetMapping("/getProgitCenters")
    public List<Map<String, String>> getProgitCenters( @RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId) {
        return profitcentreService.getProfitcenters(companyId,branchId);
    }
    
    @GetMapping("/getAllProfitCenters")
    public List<Profitcentre> getProfitCenters(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId){
    	return profitcenterrepo.getProfitcenters(companyId, branchId);
    }
}

