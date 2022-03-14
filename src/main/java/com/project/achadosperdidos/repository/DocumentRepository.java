package com.project.achadosperdidos.repository;

import com.project.achadosperdidos.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
