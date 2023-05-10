package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.repositoriesForClasses.ProductRepository;
import jakarta.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository jpaRepository;
    private List<Product>list;

    @Autowired
    public ProductService(ProductRepository jpaRepository, List<Product> list) {
        this.jpaRepository = jpaRepository;
        this.list = list;
    }

    public List<Product>getAllProducts(){
        return jpaRepository.findAll();
    }

    public Product productById(long id){
        return jpaRepository.findById(id);
    }

    @Transactional
    public boolean purchaseProduct(long id) {
        Product product = jpaRepository.findById(id);
        if (product == null) {
            return false;
        }
        int newQuantity = product.getQuantity() - 1;
        if (newQuantity < 0) {
            return false;
        }
        product.setQuantity(newQuantity);
        jpaRepository.save(product);
        return true;
    }
}
