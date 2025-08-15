package com.globaltechadvisor.simpleSpringBootProject.repository;

import com.globaltechadvisor.simpleSpringBootProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
