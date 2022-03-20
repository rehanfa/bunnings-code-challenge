package com.code.challenge.controller;


import com.code.challenge.service.CompanyService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Getter
@Setter
@Service
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostConstruct
    public void init() {
        loadCompanyData("A");
        loadCompanyData("B");
        companyService.writeProducts("output/result_output.csv");

    }

    public void loadCompanyData(String companyName) {
        companyService.loadCatalog("input/catalog" + companyName + ".csv");
        companyService.loadBarcodes("input/barcodes" + companyName + ".csv");
        companyService.populateProducts(companyName);

    }

}
