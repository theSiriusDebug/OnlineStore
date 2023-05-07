package com.example.OnlineStore.services;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.PurchaseRequest;
import com.example.OnlineStore.repositoriesForClasses.ProductRepository;
import jakarta.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceForProducts {
    private ProductRepository jpaRepository;
    private static final Logger logger = Logger.getLogger(ServiceForProducts.class);

    @Autowired
    public ServiceForProducts(ProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
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
