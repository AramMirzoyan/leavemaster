package com.leave.master.leavemaster.repository;

import com.leave.master.leavemaster.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

  boolean existsByEmail(String email);
}
