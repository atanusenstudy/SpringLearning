package com.globaltechadvisor.simpleSpringBootProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Product {

    @Id
    private int prodId;
    private String productName;
    private int productCost;

    public Product(){}

    public Product(int prodId, String productName, int productCost) {
        this.prodId = prodId;
        this.productName = productName;
        this.productCost = productCost;
    }

    public int getProdId() {return prodId;}
    public String getProductName() {return productName;}
    public int getProductCost() {return productCost;}

    public void setProdId(int prodId) {this.prodId = prodId;}
    public void setProductName(String productName) {this.productName = productName;}
    public void setProductCost(int productCost) {this.productCost = productCost;}
}
