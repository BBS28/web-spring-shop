package com.epam.learn.shchehlov.webspringshop.repository;

import com.epam.learn.shchehlov.webspringshop.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}

