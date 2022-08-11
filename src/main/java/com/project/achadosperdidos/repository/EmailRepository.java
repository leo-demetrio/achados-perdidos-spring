package com.project.achadosperdidos.repository;

import com.project.achadosperdidos.service.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
