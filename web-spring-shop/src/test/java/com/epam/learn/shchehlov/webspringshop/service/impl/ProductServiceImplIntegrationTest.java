package com.epam.learn.shchehlov.webspringshop.service.impl;

import com.epam.learn.shchehlov.webspringshop.entity.Category;
import com.epam.learn.shchehlov.webspringshop.entity.Manufacturer;
import com.epam.learn.shchehlov.webspringshop.entity.Product;
import com.epam.learn.shchehlov.webspringshop.repository.CategoryRepository;
import com.epam.learn.shchehlov.webspringshop.repository.ManufacturerRepository;
import com.epam.learn.shchehlov.webspringshop.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductServiceImplIntegrationTest {

    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @BeforeEach
    public void setUp() {
        Category category1 = new Category();
        category1.setName("Cordless");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Corded");
        categoryRepository.save(category2);

        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Bosch");
        manufacturerRepository.save(manufacturer1);

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Makita");
        manufacturerRepository.save(manufacturer2);

        product1 = new Product();
        product1.setName("Drill");
        product1.setDescription("Drill description");
        product1.setCategory(category1);
        product1.setManufacturer(manufacturer1);
        product1.setPrice(4000);

        product2 = new Product();
        product2.setName("Jigsaw");
        product2.setDescription("Jigsaw description");
        product2.setCategory(category1);
        product2.setManufacturer(manufacturer2);
        product2.setPrice(2000);

        product3 = new Product();
        product3.setName("Screwdriver");
        product3.setDescription("Screwdriver description");
        product3.setCategory(category2);
        product3.setManufacturer(manufacturer1);
        product3.setPrice(3000);

        product4 = new Product();
        product4.setName("Drill");
        product4.setDescription("Planer description");
        product4.setCategory(category2);
        product4.setManufacturer(manufacturer2);
        product4.setPrice(5000);
    }

    @AfterEach
    public void afterEach() {
        productService.deleteAllProducts();
        categoryRepository.deleteAll();
        manufacturerRepository.deleteAll();
    }

    @Test
    void shouldGetProductById() {
        Product createdProduct = productService.addProduct(product1);
        long id = createdProduct.getId();

        Product productFromDB = productService.getProduct(id);

        assertNotNull(productFromDB);
        assertEquals(id, productFromDB.getId());

    }

    @Test
    void shouldFindAllProducts() {
        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);
        productService.addProduct(product4);

        Page<Product> productPage = productService.findAllProducts(
                "",
                0,
                100000,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name")));

        assertEquals(4, productPage.getTotalElements());
    }

    @Test
    void shouldFindTwoProductsByPrice() {
        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);
        productService.addProduct(product4);

        Page<Product> productPage = productService.findAllProducts(
                "",
                2500,
                4500,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name")));

        assertEquals(2, productPage.getTotalElements());
    }

    @Test
    void shouldFindTwoProductsByName() {
        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);
        productService.addProduct(product4);

        Page<Product> productPage = productService.findAllProducts(
                "Drill",
                0,
                100000,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name")));

        assertEquals(2, productPage.getTotalElements());
    }

    @Test
    void shouldFindAllCategories() {
        assertEquals(2, productService.getAllCategories().size());
    }

    @Test
    void shouldFindAllManufacturers() {
        assertEquals(2, productService.getAllManufacturers().size());
    }

    @Test
    void shouldDeleteAllProducts() {
        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);
        productService.addProduct(product4);

        productService.deleteAllProducts();
        Page<Product> productPage = productService.findAllProducts(
                "",
                0,
                100000,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name")));

        assertTrue(productPage.isEmpty());
    }
}
