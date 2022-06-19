package com.epam.learn.shchehlov.webspringshop.service;

import com.epam.learn.shchehlov.webspringshop.entity.Order;
import com.epam.learn.shchehlov.webspringshop.entity.OrderedProduct;

public interface OrderService {

    Order addOrder(Order order);

    OrderedProduct addOrderedProduct(OrderedProduct orderedProduct);
}
