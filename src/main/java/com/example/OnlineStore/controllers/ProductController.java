package com.example.OnlineStore.controllers;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.services.ServiceForProducts;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ServiceForProducts service;
    private static final Logger logger = Logger.getLogger(ProductController.class);
    @Autowired
    public ProductController(ServiceForProducts service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Product>allProducts(){
        return service.getAllProducts();
    }
    @GetMapping("/{id}")
    public Product productById(@PathVariable long id){
        return service.productById(id);
    }
    @PostMapping("/{id}/buy")
    public ResponseEntity<String> buyProduct(@PathVariable long id) {
        boolean success = service.purchaseProduct(id);
        if (success) {
            return ResponseEntity.ok(String.format("Product purchased successfully, quantity %s", productById(id).getQuantity()));
        } else {
            return ResponseEntity.badRequest().body("Product is out of stock");
        }
    }
}
