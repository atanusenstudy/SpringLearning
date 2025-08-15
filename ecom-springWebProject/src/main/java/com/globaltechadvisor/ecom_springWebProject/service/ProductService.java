package com.globaltechadvisor.ecom_springWebProject.service;

import com.globaltechadvisor.ecom_springWebProject.model.Product;
import com.globaltechadvisor.ecom_springWebProject.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo prodRepo;

    public List<Product> getProducts() {return prodRepo.findAll();}

    public Product getProduct(int prodId){
        return prodRepo.findById(prodId).orElse(null);
    }

    public Product addProduct(Product prod, MultipartFile imageFile) throws IOException {
        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());
        prod.setImageData(imageFile.getBytes());
        return prodRepo.save(prod);
    }

    public Product updateProduct(int prodId, Product prod, MultipartFile imageFile) throws IOException {
        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());
        prod.setImageData(imageFile.getBytes());
        return prodRepo.save(prod);
    }

    public void deleteProduct(int prodId){
        prodRepo.deleteById(prodId);
    }

    public byte[] getImageByProductId(int productId) {
        Product product = getProduct( productId);
        byte[] imageFile = product.getImageData();

        return  imageFile;
    }

    public List<Product> searchProducts(String keyword){
        return prodRepo.searchProducts(keyword);
    }
}
