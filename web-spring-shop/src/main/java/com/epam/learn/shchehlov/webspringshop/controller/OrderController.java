package com.epam.learn.shchehlov.webspringshop.controller;

import com.epam.learn.shchehlov.webspringshop.entity.Order;
import com.epam.learn.shchehlov.webspringshop.entity.OrderedProduct;
import com.epam.learn.shchehlov.webspringshop.entity.Product;
import com.epam.learn.shchehlov.webspringshop.entity.User;
import com.epam.learn.shchehlov.webspringshop.entity.attribute.OrderStatus;
import com.epam.learn.shchehlov.webspringshop.service.OrderService;
import com.epam.learn.shchehlov.webspringshop.service.ProductService;
import com.epam.learn.shchehlov.webspringshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private static final String CART = "cart";

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(ProductService productService, OrderService orderService, UserService userService) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping(value = "/addToCart/{id}")
    public String addToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Product product = productService.getProduct(id);

        Map<Product, Integer> cart;
        if (session.getAttribute(CART) == null) {
            logger.debug("==> cart is empty");
            cart = new HashMap<>();
        } else {
            cart = (HashMap<Product, Integer>) session.getAttribute(CART);
        }
        int count = 0;
        for (Map.Entry<Product, Integer> cartEntry : cart.entrySet()) {
            if (cartEntry.getKey().equals(product)) {
                count = cartEntry.getValue();
            }
        }
        cart.put(product, ++count);

        session.setAttribute(CART, cart);
        logger.debug(String.format("Cart : %s", cart));
        return "redirect" + request.getHeader("Referer");
    }

    @GetMapping(value = "/cart")
    public ResponseEntity<?> viewCart(HttpSession session) {
        Map<Product, Integer> cart;
        if (session.getAttribute(CART) == null) {
            cart = new HashMap<>();
        } else {
            cart = (HashMap<Product, Integer>) session.getAttribute(CART);
        }
        logger.debug(String.format("==> cart : %s", cart));
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping(value = "/order")
    public ResponseEntity<?> createOrder(@RequestBody Order order, BindingResult bindingResult, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<Product, Integer> cart;

        if (session.getAttribute(CART) == null) {
            bindingResult.rejectValue(CART, "cart.null", "Cart isn't created");
        }

        cart = (HashMap<Product, Integer>) session.getAttribute("cart");
        if (cart.isEmpty()) {
            bindingResult.rejectValue(CART, "cart.empty", "Cart is empty");
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            User user = userService.findUserByLogin(getCurrentUsername());
            order.setUser(user);
            order.setDateTime(LocalDateTime.now());
            order.setOrderStatus(OrderStatus.ACCEPTED);
            Order createdOrder = orderService.addOrder(order);

            for (Map.Entry<Product, Integer> productEntry : cart.entrySet()) {
                OrderedProduct orderedProduct = new OrderedProduct();
                orderedProduct.setOrder(createdOrder);
                orderedProduct.setProduct(productEntry.getKey());
                orderedProduct.setProductsAmount(productEntry.getValue());
                orderedProduct.setProductPrice(productEntry.getKey().getPrice());
                orderService.addOrderedProduct(orderedProduct);
            }

            cart.clear();
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        }
    }

    private String getCurrentUsername() {
        SecurityContext securityContext= SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getName();
    }
}
