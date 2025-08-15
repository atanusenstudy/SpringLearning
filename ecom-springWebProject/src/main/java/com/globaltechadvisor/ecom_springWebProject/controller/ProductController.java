package com.globaltechadvisor.ecom_springWebProject.controller;

import com.globaltechadvisor.ecom_springWebProject.model.Product;
import com.globaltechadvisor.ecom_springWebProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @RequestMapping("/")
    public String greet(){
        return "Hello worls";
    }

    @Autowired
    ProductService service;

    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProducr(@PathVariable int productId){
        Product product = service.getProduct(productId);
        if(product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                      @RequestPart MultipartFile imageFile){
        try{
            Product savedProduct = service.addProduct(product,imageFile);
            if(savedProduct != null)
                return new ResponseEntity<>(savedProduct, HttpStatus.OK);
            else
                return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId,@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        try{
            Product updatedProduct = service.updateProduct(productId, product,imageFile);
            if(updatedProduct != null)
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            else
                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Boolean> deleteProducr(@PathVariable int productId) {
        Product prod = service.getProduct(productId);
        if(prod != null){
            service.deleteProduct(productId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product = service.getProduct(productId);
        byte[] image= product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(image);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> gearchProduct(@RequestParam String keyword){
        System.out.println("Searching with : " + keyword);
        List<Product> product = service.searchProducts(keyword);
        return new ResponseEntity<>(product,HttpStatus.OK);

    }
}
