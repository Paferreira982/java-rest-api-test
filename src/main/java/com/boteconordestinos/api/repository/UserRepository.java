package com.boteconordestinos.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.boteconordestinos.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
}
