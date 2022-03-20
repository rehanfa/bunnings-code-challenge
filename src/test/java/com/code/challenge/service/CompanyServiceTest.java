package com.code.challenge.service;

import com.code.challenge.exception.ServiceException;
import com.code.challenge.model.Product;
import org.junit.Test;

import java.util.Arrays;

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

    @Test
    public void shouldNotAddSimilarProducts() {
        Product product = new Product();
        String[] barcodeArray =  new String[] {"4", "7", "9" ,"12"};
        product.setBarcodes(Arrays.asList(barcodeArray));
        companyService.getConsolidatedProducts().add(product);

        product = new Product();
        barcodeArray = new String[] { "12" ,"9"};
        product.setBarcodes(Arrays.asList(barcodeArray));
        companyService.getConsolidatedProducts().add(product);

        product = new Product();
        barcodeArray =  new String[] {"4", "7", "9" ,"12"};
        product.setBarcodes(Arrays.asList(barcodeArray));
        companyService.getConsolidatedProducts().add(product);


        product = new Product();
        barcodeArray =  new String[] {"4", "7", "9" ,"12", "14"};
        product.setBarcodes(Arrays.asList(barcodeArray));
        companyService.getConsolidatedProducts().add(product);

        assertTrue(companyService.getConsolidatedProducts().size() == 1);

    }
}
