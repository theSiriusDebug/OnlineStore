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
    private static final Logger logger = Logger.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository jpaRepository, List<Product> list) {
        this.jpaRepository = jpaRepository;
        this.list = list;
    }

    public List<Product>getAllProducts(){
        logger.info("was found all products");
        return jpaRepository.findAll();
    }

    public Product productById(long id){
        logger.info(String.format("was found product by id %s", id));
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
        logger.info(String.format("now quantity = %s", newQuantity));
        jpaRepository.save(product);
        return true;
    }

}
