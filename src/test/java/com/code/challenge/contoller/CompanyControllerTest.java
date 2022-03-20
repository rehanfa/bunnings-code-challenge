package com.code.challenge.contoller;

import com.code.challenge.controller.CompanyController;
import com.code.challenge.exception.ServiceException;
import com.code.challenge.service.CompanyService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CompanyControllerTest {

    private CompanyController companyController;
    private CompanyService companyService = new CompanyService();

    @Before
    public void init() {
        companyController = new CompanyController(companyService);
    }

    @Test
    public void shouldLoadCompanyData() {
        companyController.loadCompanyData("A");
        assertTrue(!companyService.getConsolidatedProducts().isEmpty()
         && !companyService.getProductDescription().isEmpty()
        && !companyService.getProductBarcodes().isEmpty() );
    }

    @Test(expected = ServiceException.class)
    public void shouldNotLoadCompanyData() {
        companyController.loadCompanyData("C");
        assertTrue(companyService.getConsolidatedProducts().isEmpty()
                && companyService.getProductDescription().isEmpty()
                && companyService.getProductBarcodes().isEmpty() );
    }
}
