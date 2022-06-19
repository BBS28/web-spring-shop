package com.epam.learn.shchehlov.webspringshop.service;

import com.epam.learn.shchehlov.webspringshop.entity.Category;
import com.epam.learn.shchehlov.webspringshop.entity.Manufacturer;
import com.epam.learn.shchehlov.webspringshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<Product> findAllProducts(String name,
                                  int minPrice,
                                  int maxPrice,
                                  Pageable pageable);

    Product addProduct(Product product);

    Product getProduct(long id);

    List<Category> getAllCategories();

    List<Manufacturer> getAllManufacturers();

    void deleteAllProducts();
}

