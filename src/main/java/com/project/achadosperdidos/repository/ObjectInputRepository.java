package com.project.achadosperdidos.repository;

import com.project.achadosperdidos.service.domain.ObjectInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ObjectInputRepository extends JpaRepository<ObjectInput, UUID> {
    List<ObjectInput> findByUserId(UUID user_id);
    ObjectInput findByNumberDocument(String numberDocument);
}
