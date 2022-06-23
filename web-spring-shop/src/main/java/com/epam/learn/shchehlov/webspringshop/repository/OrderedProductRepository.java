package com.epam.learn.shchehlov.webspringshop.repository;

import com.epam.learn.shchehlov.webspringshop.entity.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
}
