package com.cwms.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cwms.entities.Tax;
import com.cwms.repository.TaxRepository;

@RestController
@RequestMapping("/api/tax")
@CrossOrigin("*")
public class TaxController {

    @Autowired
    private TaxRepository taxRepository;

    @GetMapping("/getALlTaxes")
    public List<Tax> getAllTaxes(@RequestParam ("compnayId") String compnayId,@RequestParam ("branchId") String branchId) {
        return taxRepository.getALLTaxes(compnayId,branchId);
    }
}
