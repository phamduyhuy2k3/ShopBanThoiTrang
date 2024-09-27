package com.ddk.asmsof306.repository;

import com.ddk.asmsof306.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.category " +
            "WHERE p.name LIKE %?1% OR p.category.name LIKE %?1% or CAST(p.price AS String) like %?1% or CAST(p.stock AS String) like %?1% or p.description like %?1%")
    Page<Product> searchProduct(String search, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findAllByIds(@Param("productIds") List<Integer> productIds);
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
}
