package com.project.achadosperdidos.repository;

import com.project.achadosperdidos.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
