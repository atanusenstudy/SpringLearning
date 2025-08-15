package com.globaltechadvisor.simpleSpringBootProject.service;

import com.globaltechadvisor.simpleSpringBootProject.model.Product;
import com.globaltechadvisor.simpleSpringBootProject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    ProductRepository prodRepo;

    /*
    public List<Product> dummyProduct = new ArrayList<>(Arrays.asList(
            new Product(101,"Phone", 11999),
            new Product(102,"Laptop", 99999),
            new Product(103,"Desktop", 59999)));

    private int getIndex(int prodId){
        for(int i=0;i<dummyProduct.size();i++)
            if(dummyProduct.get(i).getProdId() == prodId)
                return i;
        return -1;
    }
    */

    public List<Product> getProducts(){
        //return dummyProduct;
        return prodRepo.findAll();
    }

    public Product getProduct(int prodId){
        /*
        return dummyProduct.stream()
                .filter(p -> p.getProdId() == prodId)
                .findFirst().orElse(null);
         */

        return prodRepo.findById(prodId).orElse(null);
    }

    public Product addProduct(Product prod){
        /*
        int maxId = dummyProduct.stream().max(Comparator.comparing(v -> v.getProdId())).get().getProdId() +1;
        prod.setProdId(maxId);
        dummyProduct.add(prod);
        return prod;
         */
        return prodRepo.save(prod);
    }


    public Product updateProduct(Product prod) {
        /*
        int index = getIndex(prod.getProdId());
        dummyProduct.set(index,prod);
        return dummyProduct.get(index);
        */
        /*
        Product ele =  getProduct( prod.getProdId());

        if(ele == null){
            return null;
        }
        ele.setProductName(prod.getProductName());
        ele.setProductCost(prod.getProductCost());
        return ele;
        */

        return prodRepo.save(prod);
    }

    public void deleteProduct(int prodId){
        /*
        int index = getIndex(prodId);
        if(index ==-1)
            return null;
        var p = dummyProduct.get(index);
        dummyProduct.remove(index);
        return p;
        */

        prodRepo.deleteById(prodId);
    }
}
