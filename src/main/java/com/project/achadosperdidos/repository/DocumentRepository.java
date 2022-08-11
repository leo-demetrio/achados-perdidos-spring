package com.project.achadosperdidos.repository;

import com.project.achadosperdidos.domain.ObjectInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<ObjectInput, Long> {
    List<ObjectInput> findByUserId(Long user_id);
    ObjectInput findByNumberDocument(String numberDocument);
}
