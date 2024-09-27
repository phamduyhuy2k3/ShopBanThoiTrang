package com.ddk.asmsof306.service;

import com.ddk.asmsof306.model.Category;
import com.ddk.asmsof306.model.Product;
import com.ddk.asmsof306.repository.CategoryRepository;
import com.ddk.asmsof306.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }
    public Page<Product> findProductsByCategoryId(Integer categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }
}
