package com.epam.learn.shchehlov.webspringshop.service.impl;

import com.epam.learn.shchehlov.webspringshop.entity.Category;
import com.epam.learn.shchehlov.webspringshop.entity.Manufacturer;
import com.epam.learn.shchehlov.webspringshop.entity.Product;
import com.epam.learn.shchehlov.webspringshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplJunitTest {

    private Product product1;
    private Product product2;

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Category category;

    @Mock
    private Manufacturer manufacturer;

    @BeforeEach
    public void setUp() {
        product1 = new Product();
        product1.setId(1L);
        product1.setName("Drill");
        product1.setDescription("Drill description");
        product1.setCategory(category);
        product1.setManufacturer(manufacturer);
        product1.setPrice(4000);

        product2 = new Product();
        product2.setName("Jigsaw");
        product2.setDescription("Jigsaw description");
        product2.setCategory(category);
        product2.setManufacturer(manufacturer);
        product2.setPrice(2000);

    }

    @Test
    void shouldCreateProduct() {
        Product productData = new Product();
        productData.setName("Drill");
        productData.setPrice(4000);
        productData.setDescription("Drill description");
        productData.setCategory(category);
        productData.setManufacturer(manufacturer);

        when(manufacturer.getName()).thenReturn("Bosch");
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product1);

        Product createdProduct = productService.addProduct(productData);

        assertNotNull(createdProduct);
        assertEquals(1L, createdProduct.getId());
        assertEquals("Bosch", createdProduct.getManufacturer().getName());
    }

    @Test
    void shouldGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Product foundProduct = productService.getProduct(1L);

        assertNotNull(foundProduct);
        assertEquals("Drill", foundProduct.getName());
    }

    @Test
    void shouldFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name"));

        when(productRepository.findByNameContainsAndPriceBetween(anyString(), anyInt(), anyInt(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(productList.subList(0, 2), pageable, productList.size()));

        Page<Product> productPage = productService.findAllProducts(
                "",
                0,
                10000,
                pageable);

        List<Product> productsFromDB = productPage.toList();

        assertNotNull(productsFromDB);
        assertEquals(2, productsFromDB.size());
    }
}
