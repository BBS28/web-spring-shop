package com.epam.learn.shchehlov.webspringshop.repository;

import com.epam.learn.shchehlov.webspringshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
