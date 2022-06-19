package com.epam.learn.shchehlov.webspringshop.controller;

import com.epam.learn.shchehlov.webspringshop.entity.Order;
import com.epam.learn.shchehlov.webspringshop.entity.Product;
import com.epam.learn.shchehlov.webspringshop.entity.User;
import com.epam.learn.shchehlov.webspringshop.entity.attribute.OrderStatus;
import com.epam.learn.shchehlov.webspringshop.service.OrderService;
import com.epam.learn.shchehlov.webspringshop.service.ProductService;
import com.epam.learn.shchehlov.webspringshop.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private ProductService productService;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @Test
    void shouldAddToCart() {
        Product product = new Product();
        product.setId(10L);
        product.setName("Drill");
        Map<Product, Integer> cart = new HashMap<>();
        when(session.getAttribute("cart")).thenReturn(cart);
        when(request.getSession()).thenReturn(session);
        when(productService.getProduct(anyLong())).thenReturn(product);

        orderController.addToCart(10L, request);

        assertTrue(cart.containsKey(product));
    }

    @Test
    void shouldViewCart() {
        Map<Product, Integer> cart = new HashMap<>();
        when(session.getAttribute("cart")).thenReturn(cart);

        assertThat(orderController.viewCart(session)).isEqualTo(new ResponseEntity<>(cart, HttpStatus.OK));
    }

    @Test
    void shouldCreateOrder() {
        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = Mockito.mockStatic(SecurityContextHolder.class)) {
            Map<Product, Integer> cart = new HashMap<>();
            User user = new User();
            user.setId(5L);
            Order order = new Order();
            order.setUser(user);
            order.setDateTime(LocalDateTime.now());
            order.setOrderStatus(OrderStatus.ACCEPTED);

            when(session.getAttribute("cart")).thenReturn(cart);
            when(request.getSession()).thenReturn(session);
            when(bindingResult.hasErrors()).thenReturn(false);
            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("Login");
            when(userService.findUserByLogin(anyString())).thenReturn(user);
            when(orderService.addOrder(any(Order.class))).thenReturn(order);

            assertThat(orderController.createOrder(new Order(), bindingResult, request).getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }
    }
}
