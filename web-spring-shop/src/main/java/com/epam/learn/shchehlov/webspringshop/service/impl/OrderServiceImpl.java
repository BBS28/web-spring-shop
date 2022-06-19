package com.epam.learn.shchehlov.webspringshop.service.impl;

import com.epam.learn.shchehlov.webspringshop.entity.Order;
import com.epam.learn.shchehlov.webspringshop.entity.OrderedProduct;
import com.epam.learn.shchehlov.webspringshop.repository.OrderRepository;
import com.epam.learn.shchehlov.webspringshop.repository.OrderedProductRepository;
import com.epam.learn.shchehlov.webspringshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderedProductRepository orderedProductRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderedProductRepository orderedProductRepository) {
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
    }

    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public OrderedProduct addOrderedProduct(OrderedProduct orderedProduct) {
        return orderedProductRepository.save(orderedProduct);
    }

}
