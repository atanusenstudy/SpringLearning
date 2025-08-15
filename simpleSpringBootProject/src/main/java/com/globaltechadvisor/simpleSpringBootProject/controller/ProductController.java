package com.globaltechadvisor.simpleSpringBootProject.controller;

import com.globaltechadvisor.simpleSpringBootProject.model.Product;
import com.globaltechadvisor.simpleSpringBootProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService service;

    @RequestMapping("/products")
    public List<Product> getProducts(){
        return service.getProducts();
    }

    @GetMapping("/products/{prodId}")
    public Product getProducr(@PathVariable int prodId){
        return service.getProduct(prodId);
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product prod){
        return service.addProduct(prod);
    }

    @PutMapping("/products")
    public Product updateProduct(@RequestBody Product prod){
        return service.updateProduct(prod);
    }

    @DeleteMapping("/products/{prodId}")
    public void deleteProducr(@PathVariable int prodId){
        service.deleteProduct(prodId);
    }
}
