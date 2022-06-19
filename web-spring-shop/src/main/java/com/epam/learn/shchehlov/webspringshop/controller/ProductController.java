package com.epam.learn.shchehlov.webspringshop.controller;

import com.epam.learn.shchehlov.webspringshop.entity.Product;
import com.epam.learn.shchehlov.webspringshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = {"/", "/home", "/products"})
    public Page<Product> productFilter(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "minPrice", required = false, defaultValue = "0") int minPrice,
            @RequestParam(value = "maxPrice", required = false, defaultValue = "100000") int maxPrice,
            @RequestParam(value = "size", required = false, defaultValue = "6") int size,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return productService.findAllProducts(
                name,
                minPrice,
                maxPrice,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name")));
    }

    @GetMapping(value = "/products/{id}")
    public Product viewProduct(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @GetMapping(value = "/products/categories")
    public ResponseEntity<?> viewCategories() {
        try {
            return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Can't get all categories");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/products/manufacturers")
    public ResponseEntity<?> viewManufacturers() {
        try {
            return new ResponseEntity<>(productService.getAllManufacturers(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Can't get all manufacturers");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
