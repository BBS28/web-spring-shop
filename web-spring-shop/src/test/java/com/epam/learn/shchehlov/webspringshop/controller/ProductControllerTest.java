package com.epam.learn.shchehlov.webspringshop.controller;

import com.epam.learn.shchehlov.webspringshop.entity.Product;
import com.epam.learn.shchehlov.webspringshop.service.ProductService;
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
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    void shouldFindProductsByParameters() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name"));
        when(productService.findAllProducts(anyString(), anyInt(), anyInt(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(productList.subList(0, 2), pageable, productList.size()));

        Page<Product> productPage = productController.productFilter("", 0, 10000, 10, 0);

        List<Product> productsFromDB = productPage.toList();

        assertNotNull(productsFromDB);
        assertEquals(2, productsFromDB.size());
    }

    @Test
    void shouldViewProductById() {
        Product product = new Product();
        product.setName("Drill");
        when((productService.getProduct(anyLong()))).thenReturn(product);

        assertThat(productController.viewProduct(5L)).isEqualTo(product);
    }

    @Test
    void shouldReturnAllCategories() {
        when(productService.getAllCategories()).thenReturn(new ArrayList<>()).thenThrow(new NullPointerException());

        assertThat(productController.viewCategories().getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productController.viewCategories().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturnAllManufacturers() {
        when(productService.getAllManufacturers()).thenReturn(new ArrayList<>()).thenThrow(new NullPointerException());

        assertThat(productController.viewManufacturers().getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productController.viewManufacturers().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
