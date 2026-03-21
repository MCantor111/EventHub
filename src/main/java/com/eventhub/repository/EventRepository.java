package com.eventhub.repository;

import com.eventhub.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByCategory_NameIgnoreCase(String category, Pageable pageable);

    Page<Event> findByTicketPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Event> findByEventDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Event> findByActiveTrue(Pageable pageable);

    Page<Event> findByCategory_NameIgnoreCaseAndTicketPriceBetweenAndEventDateBetween(
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    @Query("""
        SELECT e FROM Event e
        JOIN e.category c
        WHERE (:keyword IS NULL OR LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:categoryName IS NULL OR LOWER(c.name) = LOWER(:categoryName))
    """)
    Page<Event> searchEvents(
            @Param("keyword") String keyword,
            @Param("categoryName") String categoryName,
            Pageable pageable
    );

    @Query("""
        SELECT COUNT(ri) FROM RegistrationItem ri
        WHERE ri.event.id = :eventId
    """)
    Long countRegistrationsForEvent(@Param("eventId") Long eventId);

    @Query("""
        SELECT COALESCE(SUM(ri.ticketPrice * ri.quantity), 0)
        FROM RegistrationItem ri
        WHERE ri.event.id = :eventId
    """)
    BigDecimal totalRevenueForEvent(@Param("eventId") Long eventId);
}