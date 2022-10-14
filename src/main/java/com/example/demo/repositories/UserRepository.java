package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.UserPrincipal;

@Repository
public interface UserRepository extends JpaRepository<UserPrincipal, Integer>{

	Optional<UserPrincipal> findByUsername(String username);
}
