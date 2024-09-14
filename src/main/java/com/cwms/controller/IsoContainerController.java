//package com.cwms.controller;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cwms.entities.IsoContainer;
//import com.cwms.repository.IsoContainerRepository;
//
//@RestController
//@CrossOrigin("*")
//@RequestMapping("/IsoContainer")
//public class IsoContainerController {
//
//	@Autowired
//	private IsoContainerRepository isoContainerRepository;
//
//	@PostMapping(value = "/add")
//	public IsoContainer addIsoContainer(@RequestBody IsoContainer entity) {
//		entity.setCreatedate();
//		System.out.println(entity.getTareWeight());
//		String value = entity.getTareWeight().toString();
//		BigDecimal roundedValue = new BigDecimal(value).setScale(3, RoundingMode.HALF_UP);
//		System.out.println("Rounded Value: " + roundedValue);	       
//		entity.setTareWeight(roundedValue);
//		
//		if (entity.getIsoCode().equals(null) || entity.getTareWeight() == null) {
//			entity.setCreatedate();
//			System.out.println(entity);
//
//			return isoContainerRepository.save(entity);
//		} else {
//			BigDecimal bigDecimal = entity.getTareWeight().setScale(3);
//			entity.setTareWeight(bigDecimal);
//			System.out.println(entity);
//			return isoContainerRepository.save(entity);
//
//		}
//
//	}
//
//	@PostMapping(value = "/delete")
//	public IsoContainer deleteIsoContainer(@RequestBody IsoContainer entity) {
//		entity.setStatus("D");
//		System.out.println(entity);
//
//		return isoContainerRepository.save(entity);
//	}
//
//	@GetMapping(value = "/list/{companyId}")
//	public List<IsoContainer> getIsoContainers(@PathVariable("companyId") String companyId) {
//		List<IsoContainer> result = isoContainerRepository.findByCompanyIdAndStatusNot(companyId, "D",
//				Sort.by(Sort.Order.desc("isoCode")));
//		return result;
////		return isoContainerRepository.findByCompanyIdAndStatusNot(companyId,"D");
//	}
//
//	
//	@GetMapping(value = "/searchQuery/{companyId}")
//	public List<IsoContainer> getListSearch(@PathVariable("companyId") String companyId,
//			@RequestParam(value = "isoCodeSearch", required = false) String isocode,
//			@RequestParam(value = "containerSizeSearch", required = false) String csize,
//			@RequestParam(value = "containerTypeSearch", required = false) String ctype) {
//
//		System.out.println(companyId + "\t" + isocode + "\t" + csize + "\t" + ctype + "\t");
//
//		// Check for 'undefined' and treat it as null
//		isocode = "undefined".equals(isocode) ? null : isocode;
//		csize = "undefined".equals(csize) ? null : csize;
//		ctype = "undefined".equals(ctype) ? null : ctype;
//
//		List<IsoContainer> isoContainersSearch = isoContainerRepository.findIsoContainersByCriteria(companyId,
//				isocode != null && isocode.trim().isEmpty() ? null : isocode,
//				csize != null && csize.trim().isEmpty() ? null : csize,
//				ctype != null && ctype.trim().isEmpty() ? null : ctype);
//
//		return isoContainersSearch;
//	}
//
//	
//	
//}
package com.cwms.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Cfigmcn;
import com.cwms.entities.IsoContainer;
import com.cwms.repository.IsoContainerRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/IsoContainer")
public class IsoContainerController {

	@Autowired
	private IsoContainerRepository isoContainerRepository;

	@PostMapping(value = "/add")
	public IsoContainer addIsoContainer(@RequestBody IsoContainer entity) {
		entity.setCreatedate();
		System.out.println(entity.getTareWeight());
		String value = entity.getTareWeight().toString();
		BigDecimal roundedValue = new BigDecimal(value).setScale(3, RoundingMode.HALF_UP);
		System.out.println("Rounded Value: " + roundedValue);	       
		entity.setTareWeight(roundedValue);
		
		if (entity.getIsoCode().equals(null) || entity.getTareWeight() == null) {
			entity.setCreatedate();
			System.out.println(entity);

			return isoContainerRepository.save(entity);
		} else {
			BigDecimal bigDecimal = entity.getTareWeight().setScale(3);
			entity.setTareWeight(bigDecimal);
			System.out.println(entity);
			return isoContainerRepository.save(entity);

		}

	}

	@PostMapping(value = "/delete")
	public IsoContainer deleteIsoContainer(@RequestBody IsoContainer entity) {
		entity.setStatus("D");
		System.out.println(entity);

		return isoContainerRepository.save(entity);
	}

	@GetMapping(value = "/list/{companyId}")
	public List<IsoContainer> getIsoContainers(@PathVariable("companyId") String companyId) {
		List<IsoContainer> result = isoContainerRepository.findByCompanyIdAndStatusNot(companyId, "D",
				Sort.by(Sort.Order.desc("isoCode")));
		return result;
//		return isoContainerRepository.findByCompanyIdAndStatusNot(companyId,"D");
	}

	
	@GetMapping(value = "/searchQuery/{companyId}")
	public List<IsoContainer> getListSearch(@PathVariable("companyId") String companyId,
			@RequestParam(value = "isoCodeSearch", required = false) String isocode,
			@RequestParam(value = "containerSizeSearch", required = false) String csize,
			@RequestParam(value = "containerTypeSearch", required = false) String ctype) {

		System.out.println(companyId + "\t" + isocode + "\t" + csize + "\t" + ctype + "\t");

		// Check for 'undefined' and treat it as null
		isocode = "undefined".equals(isocode) ? null : isocode;
		csize = "undefined".equals(csize) ? null : csize;
		ctype = "undefined".equals(ctype) ? null : ctype;

		List<IsoContainer> isoContainersSearch = isoContainerRepository.findIsoContainersByCriteria(companyId,
				isocode != null && isocode.trim().isEmpty() ? null : isocode,
				csize != null && csize.trim().isEmpty() ? null : csize,
				ctype != null && ctype.trim().isEmpty() ? null : ctype);

		return isoContainersSearch;
	}

	@GetMapping("/getIso")
	public ResponseEntity<?> getData(@RequestParam("cid") String cid,@RequestParam("val") String val){
		IsoContainer iso = isoContainerRepository.getDataByIsoCode(cid, val);
		
		if(iso != null) {
			return new ResponseEntity<>(iso,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("not found",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/getObj")
	public void getObj(@RequestBody Cfigmcn cn) {
		System.out.println("cncncncncn "+cn);
	}
	
	
	@GetMapping("/getIsoByID")
	public IsoContainer getDataByTransId(@RequestParam("companyId") String companyId,@RequestParam("isoCode") String isoCode){
		return isoContainerRepository.getIsoById(companyId, isoCode);
	}
	
	
	
	
	@GetMapping("/search")
    public List<IsoContainer> getParties(
            @RequestParam("companyId") String companyId,
            @RequestParam(name = "partyName", required = false) String partyName) {
        return isoContainerRepository.findIsoContainersByCriteria(companyId,partyName);
    }
	
	@GetMapping("/searchByIsoCode")
    public List<IsoContainer> getIsoCodes(
            @RequestParam("companyId") String companyId) {
        return isoContainerRepository.findIsoContainersByCriteria1(companyId);
    }
	
}
