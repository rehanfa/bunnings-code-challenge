package com.code.challenge.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Product {
    private String sku;
    private String description;
    private String source;
    private List<String> barcodes;

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        boolean retVal = false;
        Product supplierProductBarcode;
        if (o != null) {
            supplierProductBarcode = (Product) o;
            if (this.barcodes != null && !this.barcodes.isEmpty() && supplierProductBarcode.getBarcodes() != null
                    && !supplierProductBarcode.getBarcodes().isEmpty()) {

                if (this.barcodes.equals(supplierProductBarcode.getBarcodes())) {
                    retVal = true;
                } else if (this.barcodes.containsAll(supplierProductBarcode.getBarcodes())) {
                    retVal = true;
                    supplierProductBarcode.setSku(this.sku);
                    supplierProductBarcode.setDescription(this.description);
                    supplierProductBarcode.setBarcodes(this.barcodes);
                    supplierProductBarcode.setSource(this.source);
                } else if (supplierProductBarcode.getBarcodes().containsAll(this.barcodes)) {

                    retVal = true;
                }

            }
        }
        return retVal;
    }


}
