package com.eventhub.repository;

import com.eventhub.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByUserId(Long userId);

    List<Registration> findByRegistrationDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
        SELECT DISTINCT r FROM Registration r
        JOIN FETCH r.user
        LEFT JOIN FETCH r.registrationItems ri
        LEFT JOIN FETCH ri.event
        WHERE r.id = :id
    """)
    Optional<Registration> findDetailedById(@Param("id") Long id);
}