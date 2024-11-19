package com.aditya.SpringProject.repository;

import com.aditya.SpringProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByBrand(String Brand);

    @Query("SELECT p FROM Product p WHERE "+
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "+
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "+
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "+
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);
}
