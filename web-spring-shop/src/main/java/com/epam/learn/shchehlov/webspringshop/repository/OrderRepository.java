package com.epam.learn.shchehlov.webspringshop.repository;

import com.epam.learn.shchehlov.webspringshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
