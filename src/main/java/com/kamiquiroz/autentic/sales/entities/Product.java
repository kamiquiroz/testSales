package com.kamiquiroz.autentic.sales.entities;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Product {
    @Id
    private Long productId;
    private Long unitValue;
    private String productName;
    private String productLocation;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(Long unitValue) {
        this.unitValue = unitValue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }
}
