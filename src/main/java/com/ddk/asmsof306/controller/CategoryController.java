package com.ddk.asmsof306.controller;

import com.ddk.asmsof306.component.Breadcrumb;
import com.ddk.asmsof306.model.Category;
import com.ddk.asmsof306.model.Product;
import com.ddk.asmsof306.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final HttpSession session;
    @GetMapping("/{id}")
    public String getCategoryById(
            @PathVariable("id") Integer id,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model
    ) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home", "/"));
        Category category = categoryService.findById(id);
        breadcrumbs.add(new Breadcrumb(category.getName(), "/product/category/" + category.getId()));
        session.setAttribute("breadcrumb", breadcrumbs);
        model.addAttribute("title", category.getName());

        // Get paginated list of products for the category
        Page<Product> productPage = categoryService.findProductsByCategoryId(id, PageRequest.of(page - 1, size));

        model.addAttribute("category", category);
        model.addAttribute("productPage", productPage);

        return "shop";
    }
}
