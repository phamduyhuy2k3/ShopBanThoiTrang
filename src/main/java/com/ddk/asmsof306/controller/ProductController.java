package com.ddk.asmsof306.controller;

import com.ddk.asmsof306.component.Breadcrumb;
import com.ddk.asmsof306.model.Category;
import com.ddk.asmsof306.model.Product;
import com.ddk.asmsof306.service.ProductService;
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
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final HttpSession session;
    @GetMapping("{id}")
    public String productDetail(@PathVariable("id") Integer id, Model model){
        Product product= productService.findById(id);
        List<Breadcrumb> breadcrumbs=new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home","/"));
        model.addAttribute("product",product);
        breadcrumbs.add(new Breadcrumb("Product detail","/product/"+product.getId()));
        session.setAttribute("breadcrumb",breadcrumbs);
        return "detail";
    }
    @GetMapping("/search")
    public String productSearchPage(@RequestParam("s") String searchText,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    Model model){
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home", "/"));
        Page<Product> productPage = productService.searchProductResult(searchText, page );
        model.addAttribute("productPage", productPage);
        model.addAttribute("searchText", searchText);
        breadcrumbs.add(new Breadcrumb("Search result: "+productPage.getContent().size(), ""));
        session.setAttribute("breadcrumb", breadcrumbs);
        return "SearchResults";
    }
}
