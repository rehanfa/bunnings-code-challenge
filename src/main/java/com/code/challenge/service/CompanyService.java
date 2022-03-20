package com.code.challenge.service;

import com.code.challenge.exception.ServiceException;
import com.code.challenge.model.Product;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Getter
@Setter
@Service
public class CompanyService {
    Logger logger = LoggerFactory.getLogger(CompanyService.class);
    private HashSet<Product> consolidatedProducts;
    private HashMap<String, String> productDescription;
    private HashMap<String, List<String>> productBarcodes;

    public CompanyService() {
        consolidatedProducts = new HashSet<>();
    }

    public void loadCatalog(String catalogPath) {
        productDescription = new HashMap<>();
        try {
            try (CSVReader csvReader = new CSVReader(new FileReader(catalogPath))) {
                String[] values;
                csvReader.readNext();
                while ((values = csvReader.readNext()) != null) {
                    productDescription.put(values[0], values[1]);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            throw new ServiceException(e.getMessage());
        }
    }

    public void loadBarcodes(String barcodePath) {
        productBarcodes = new HashMap<>();
        try {
            try (CSVReader csvReader = new CSVReader(new FileReader(barcodePath))) {
                String[] values;
                csvReader.readNext();
                while ((values = csvReader.readNext()) != null) {
                    if (productBarcodes.get(values[1]) == null) {
                        productBarcodes.put(values[1], new ArrayList<>());
                    }
                    productBarcodes.get(values[1]).add(values[2]);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            throw new ServiceException(e.getMessage());
        }

    }

    public void populateProducts(String source) {
        if(this.productBarcodes != null) {
            this.productBarcodes.forEach((sku, barcodes) -> {
                Product product = new Product();
                product.setSource(source);
                product.setBarcodes(barcodes);
                product.setSku(sku);
                product.setDescription(productDescription.get(sku));
                this.consolidatedProducts.add(product);

            });
        }
    }

    public void writeProducts(String path) {
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(path));
            List<String[]> data = toStringArray();
            csvWriter.writeAll(data);
            csvWriter.close();
        } catch (Exception e) {
            logger.error(e.toString());
            throw new ServiceException(e.getMessage());
        }

    }

    private List<String[]> toStringArray() {
        List<String[]> records = new ArrayList<String[]>();

        // adding header record
        records.add(new String[]{"SKU", "Description", "Source"});
        this.consolidatedProducts.forEach(product -> {
            records.add(new String[]{product.getSku(), product.getDescription(), product.getSource()});
        });

        return records;
    }

}
