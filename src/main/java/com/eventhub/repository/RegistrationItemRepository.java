package com.eventhub.repository;

import com.eventhub.model.RegistrationItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationItemRepository extends JpaRepository<RegistrationItem, Long> {

    List<RegistrationItem> findByRegistrationId(Long registrationId);

    List<RegistrationItem> findByEventId(Long eventId);
}