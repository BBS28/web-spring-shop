package com.epam.learn.shchehlov.webspringshop.repository;

import com.epam.learn.shchehlov.webspringshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainsAndPriceBetween(String name,
                                                    int minPrice,
                                                    int maxPrice,
                                                    Pageable pageable);

}
