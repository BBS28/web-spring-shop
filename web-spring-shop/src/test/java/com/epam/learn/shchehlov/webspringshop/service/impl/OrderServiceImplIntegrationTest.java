package com.epam.learn.shchehlov.webspringshop.service.impl;

import com.epam.learn.shchehlov.webspringshop.entity.Category;
import com.epam.learn.shchehlov.webspringshop.entity.Manufacturer;
import com.epam.learn.shchehlov.webspringshop.entity.Order;
import com.epam.learn.shchehlov.webspringshop.entity.OrderedProduct;
import com.epam.learn.shchehlov.webspringshop.entity.Product;
import com.epam.learn.shchehlov.webspringshop.entity.User;
import com.epam.learn.shchehlov.webspringshop.entity.attribute.DeliveryPayment;
import com.epam.learn.shchehlov.webspringshop.entity.attribute.OrderStatus;
import com.epam.learn.shchehlov.webspringshop.repository.CategoryRepository;
import com.epam.learn.shchehlov.webspringshop.repository.ManufacturerRepository;
import com.epam.learn.shchehlov.webspringshop.repository.OrderRepository;
import com.epam.learn.shchehlov.webspringshop.repository.OrderedProductRepository;
import com.epam.learn.shchehlov.webspringshop.service.OrderService;
import com.epam.learn.shchehlov.webspringshop.service.ProductService;
import com.epam.learn.shchehlov.webspringshop.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderServiceImplIntegrationTest {

    private User createdUser;
    private Order order;
    private Product createdProduct;
    private OrderedProduct orderedProduct;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private OrderedProductRepository orderedProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setFirstName("name");
        user.setLastName("Lastname");
        user.setEmail("email@email.exem");
        user.setLogin("Login");
        user.setPassword("Password123");
        user.setMailing(true);


        Category category = new Category();
        category.setName("Corded");
        Category createdCategory = categoryRepository.save(category);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Bosch");
        Manufacturer createdManufacturer = manufacturerRepository.save(manufacturer);

        Product product = new Product();
        product.setName("Drill");
        product.setDescription("Drill description");
        product.setCategory(createdCategory);
        product.setManufacturer(manufacturer);
        product.setPrice(4000);
        createdProduct = productService.addProduct(product);

    }

    @AfterEach
    public void afterEach() {
        orderedProductRepository.deleteAll();
        orderRepository.deleteAll();
        userService.deleteAllUsers();
        productService.deleteAllProducts();
        categoryRepository.deleteAll();
        manufacturerRepository.deleteAll();
    }

    @Test
    void shouldCreateOrder() {
        LocalDateTime time = LocalDateTime.now();
        order = new Order();
        order.setStateDetail("state details");
        order.setPaymentDetails("payment details");
        order.setUser(createdUser);
        order.setDateTime(time);
        order.setOrderStatus(OrderStatus.ACCEPTED);
        order.setDeliveryPayment(DeliveryPayment.SHOP_CASH);

        Order createdOrder = orderService.addOrder(order);

        assertNotNull(createdOrder);
        assertEquals(time, createdOrder.getDateTime());
    }

    @Test
    void shouldCreateOrderedProduct() {
        order = new Order();
        order.setStateDetail("state details");
        order.setPaymentDetails("payment details");
        order.setUser(createdUser);
        order.setDateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ACCEPTED);
        order.setDeliveryPayment(DeliveryPayment.SHOP_CASH);
        Order createdOrder = orderService.addOrder(order);

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setProduct(createdProduct);
        orderedProduct.setProductPrice(createdProduct.getPrice());
        orderedProduct.setProductsAmount(2);
        orderedProduct.setOrder(createdOrder);

        OrderedProduct createdOrderedProduct= orderService.addOrderedProduct(orderedProduct);

        assertNotNull(createdOrderedProduct);
        assertEquals(2, createdOrderedProduct.getProductsAmount());
        assertEquals(createdProduct, createdOrderedProduct.getProduct());
    }
}
