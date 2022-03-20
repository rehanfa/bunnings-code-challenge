package com.code.challenge.service;

import com.code.challenge.exception.ServiceException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CompanyServiceTest {
    private CompanyService companyService = new CompanyService();

    @Test
    public void shouldLoadCatalog() {
        companyService.loadCatalog("input/catalogA.csv");
        assertFalse(companyService.getProductDescription().isEmpty());

    }

    @Test(expected = ServiceException.class)
    public void shouldNotLoadCatalog() {
        companyService.loadCatalog("input/tmp.csv");
        assertTrue(companyService.getProductDescription().isEmpty());

    }

    @Test
    public void shouldLoadBarcodes() {
        companyService.loadBarcodes("input/barcodesA.csv");
        assertFalse(companyService.getProductBarcodes().isEmpty());

    }

    @Test(expected = ServiceException.class)
    public void shouldNotLoadBarcodes() {
        companyService.loadBarcodes("input/tmp.csv");
        assertTrue(companyService.getProductBarcodes().isEmpty());

    }

    @Test
    public void shouldPopulateProducts() {
        companyService.loadCatalog("input/catalogA.csv");
        companyService.loadBarcodes("input/barcodesA.csv");
        companyService.populateProducts("A");
        assertFalse(companyService.getConsolidatedProducts().isEmpty());

    }

    @Test
    public void shouldNotPopulateProducts() {
        companyService.populateProducts("B");
        assertTrue(companyService.getConsolidatedProducts().isEmpty());

    }
}
