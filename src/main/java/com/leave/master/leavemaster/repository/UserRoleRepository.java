package com.leave.master.leavemaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.master.leavemaster.model.UserRole;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
  Optional<UserRole> findByName(UserRoleEnum name);
}
