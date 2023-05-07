package com.example.OnlineStore.repositoriesForClasses;

import com.example.OnlineStore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
}
