package com.example.user.repository;

import com.example.user.model.CustomerMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerMessageRepository extends JpaRepository<CustomerMessage, Long> {
    boolean existsByMessageId(String messageId);
}

