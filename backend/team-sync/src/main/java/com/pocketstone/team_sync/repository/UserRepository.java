package com.pocketstone.team_sync.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketstone.team_sync.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByLoginId(String longinId);
    //Optional<User> findByEmail(String email);
    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);

}
