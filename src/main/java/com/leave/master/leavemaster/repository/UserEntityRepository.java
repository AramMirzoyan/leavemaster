package com.leave.master.leavemaster.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.master.leavemaster.model.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

  boolean existsByEmail(String email);
}
