package com.cwms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.JarDetail;
import com.cwms.repository.JarDetailRepository;
import com.cwms.service.JarDetailService;
import com.cwms.service.ProcessNextIdService;

import jakarta.transaction.Transactional;

@CrossOrigin("*")
@RequestMapping("/jardetail")
@RestController
public class JarDetailController {

	@Autowired
	JarDetailService jarDetailService;

	@Autowired
	JarDetailRepository jarDetailRepository;

	@Autowired
	public ProcessNextIdService processSerrvice;
	

	@PostMapping(value = "/add/{cid}/{id}")
	public JarDetail saveJarDetail(@RequestBody JarDetail entity,@PathVariable("cid") String cid,@PathVariable("id") String id) {
		entity.setStatus("N");
		entity.setCompanyId(cid);
		entity.setJarId(id);
		entity.setCurrentDate();
		if (entity.getJarDtlId() == null) {
			entity = jarDetailService.saveJarDetail(entity);
			System.out.println(entity);
			return entity;
		} else
			return jarDetailService.saveJarDetail(entity);
	}
	 @PostMapping(value = "/addUpdateStatus")
	    public JarDetail saveJarDetailApp(@RequestBody JarDetail entity) {
	        if (entity.getStatus().equals("N")) {
	            entity.setStatus("U");
	        } else if (entity.getStatus().equals("A")) {
	            // If status is already "A", don't allow changing to "U"
	            entity.setStatus("A");
	        }

	        return jarDetailService.saveJarDetail(entity);
	    }

	@GetMapping(value = "/list/{cid}")
	public List<JarDetail> getMethodName(@PathVariable("cid") String cid) {
		return jarDetailService.list(cid);

	}

	@GetMapping(value = "/jarIdList/{jarId}/{cid}")
	public List<JarDetail> getjarDtl(@PathVariable("jarId") String jarId,@PathVariable("cid") String cid) {
		System.out.println(jarId);

		List<JarDetail> jarDetails = jarDetailService.listByJarId(jarId,cid);
		for (JarDetail j : jarDetails) {

			jarDetailRepository.save(j);
		}

		return jarDetailService.listByJarId(jarId,cid);
	}

    @GetMapping(value = "/jarIdListUStatus/{jarId}/{cid}")
    public List<JarDetail> getjarDtlUpdateStatus(@PathVariable String jarId,@PathVariable("cid") String cid) {
        System.out.println(jarId);

        List<JarDetail> jarDetails = jarDetailService.listByJarId(jarId,cid);
        System.out.println(jarDetails);
        for (JarDetail entity : jarDetails) {
            if (entity.getStatus() == null || entity.getStatus().isEmpty()) {
                entity.setStatus("N");
            } else {
                entity.setStatus("A");
            }
            jarDetailRepository.save(entity);
        }
        System.out.println(jarDetails);

        List<JarDetail> list = jarDetailService.listByJarId(jarId,cid);

        return list;
    }

	@DeleteMapping(value = "/delete/{id}/{cid}")
	@Transactional
	public void getjaJarDetailList(@PathVariable("id") String param,@PathVariable("cid") String cid) {
		System.out.println(param);
		JarDetail entity = jarDetailService.findJarDetail(param,cid);
		entity.setStatus("D");
		jarDetailRepository.save(entity);
	}
	

	@GetMapping("/dgdcstatus/{cid}")
	public List<JarDetail> getDGDCdata(@PathVariable("cid") String cid){
		return jarDetailRepository.findDataByID(cid);
	}
	
	@GetMapping("/dgdcStatus/{cid}")
	public List<JarDetail> getDGDCdataforsub(@PathVariable("cid") String cid){
		return jarDetailRepository.findDataByIDForSub(cid);
	}
	
	
	@GetMapping("/nsdlexpstatus/{cid}")
	public List<JarDetail> getNSDLExpdata(@PathVariable("cid") String cid){
		return jarDetailRepository.findNSDLExpDataByID(cid);
	}
	
	@GetMapping("/nsdlimpstatus/{cid}")
	public List<JarDetail> getNSDLIMPdata(@PathVariable("cid") String cid){
		return jarDetailRepository.findNSDLIMPDataByID(cid);
	}
	
	@GetMapping("/internaluser/{cid}")
	public List<JarDetail> getInternalUserData(@PathVariable("cid") String cid){
		return jarDetailRepository.findInternalUser(cid);
	}
	
	
	@GetMapping("/partyTypes")
	public List<JarDetail> getDataForPartyTypes(@RequestParam("cid") String cid){
		return jarDetailRepository.getDataByJarId(cid,"J00023");
	}
	
	
	@GetMapping("/vendorTypes")
	public List<JarDetail> getDataForVendorTypes(@RequestParam("cid") String cid){
		return jarDetailRepository.getDataByJarId(cid,"J00032");
	}

	
	@GetMapping("/getCountry")
	public List<JarDetail> getCountry(@RequestParam("cid") String cid){
		return jarDetailRepository.getDataByJarId(cid,"J00002");
	}
	
	@GetMapping("/getBranch")
	public List<JarDetail> getBranch(@RequestParam("cid") String cid){
		return jarDetailRepository.getDataByJarId(cid,"J00026");
	}

	
	@GetMapping("/getTermId")
	public List<JarDetail> getTermId(@RequestParam("cid") String cid){
		return jarDetailRepository.getDataByJarId(cid,"J00056");
	}
	
	@GetMapping("/getScanDoc")
	public List<JarDetail> getScanDoc(@RequestParam("cid") String cid){
		return jarDetailRepository.getDataByJarId(cid,"J00062");
	}
	
	@GetMapping("/getJarDetail")
	public ResponseEntity<?> getAllPartiesByCompanyAndBranch(@RequestParam("companyId") String companyId,
			@RequestParam("jarId") String jarId) {
		List<Object[]> jars = jarDetailRepository.getJarDtlById(companyId, jarId);

		return ResponseEntity.ok(jars);
	}
	
	
	@GetMapping("/getCityStateCountry")
	public ResponseEntity<?> getCityStateCountry(@RequestParam("companyId") String companyId) {
	    List<Object[]> city = jarDetailRepository.getJarDtlById(companyId, "J00007");
	    List<Object[]> state = jarDetailRepository.getJarDtlById(companyId, "J00026");
	    List<Object[]> country = jarDetailRepository.getJarDtlById(companyId, "J00002");
	    
	    List<Map<String, String>> cityList = convertToValueLabelList(city);
	    List<Map<String, String>> stateList = convertToValueLabelList(state);
	    List<Map<String, String>> countryList = convertToValueLabelList(country);

	    Map<String, List<Map<String, String>>> response = new HashMap<>();
	    response.put("city", cityList);
	    response.put("state", stateList);
	    response.put("country", countryList);
	    return ResponseEntity.ok(response);
	}

	private List<Map<String, String>> convertToValueLabelList(List<Object[]> data) {
	    return data.stream().map(obj -> {
	        Map<String, String> map = new HashMap<>();
	        map.put("value", obj[0].toString());
	        map.put("label", obj[1].toString());
	        return map;
	    }).collect(Collectors.toList());
	}
	

}