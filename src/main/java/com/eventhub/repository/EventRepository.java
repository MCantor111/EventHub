package com.eventhub.repository;

import com.eventhub.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByCategory_NameIgnoreCase(String category, Pageable pageable);

    Page<Event> findByTicketPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Event> findByEventDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Event> findByCategory_NameIgnoreCaseAndTicketPriceBetweenAndEventDateBetween(
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}