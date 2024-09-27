package com.ddk.asmsof306.service;

import com.ddk.asmsof306.model.Product;
import com.ddk.asmsof306.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product findById(Integer id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
    }
    @Transactional(readOnly = true)
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Product save(Product product){

        return productRepository.save(product);
    }
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Product update(Product product) throws SQLException {
        productRepository.findById(product.getId()).orElseThrow(()->new SQLException("Product not found"));
        return productRepository.save(product);
    }
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public void deleteById(Integer id) throws SQLException {
        productRepository.findById(id).orElseThrow(()->new SQLException("Product not found"));
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Product> searchProductResult(String searchText, int page){
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, 9, sort);
        return productRepository.searchProduct(searchText,pageable);
    }
    @Transactional(readOnly = true)
    public List<Product> findAllByIds(List<Integer> productIds) {
        return productRepository.findAllByIds(productIds);
    }
    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }
}
