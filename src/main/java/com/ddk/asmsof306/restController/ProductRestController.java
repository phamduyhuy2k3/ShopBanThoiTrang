package com.ddk.asmsof306.restController;

import com.ddk.asmsof306.model.Product;
import com.ddk.asmsof306.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    @GetMapping("/findAll")
    public List<Product> findAll(){
        return productService.findAll();
    }
    @GetMapping("/findById/{id}")
    public Product findById(@PathVariable Integer id){
        return productService.findById(id);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Product> save(@RequestBody @Valid Product product, Errors result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productService.save(product));
    }
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody @Valid Product product, Errors result){
        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok(productService.update(product));

        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Integer id){
        try {
            productService.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/search")
    public List<Product> searchProductResult(@RequestParam("s") String searchText){
        return productService.searchProductResult(searchText,0).getContent();
    }
}
