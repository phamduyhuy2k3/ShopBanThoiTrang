package com.ddk.asmsof306.restController;

import com.ddk.asmsof306.model.Category;
import com.ddk.asmsof306.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService service;
    @GetMapping("/findAll")
    public List<Category> findAll(){
        return service.findAll();
    }
}
