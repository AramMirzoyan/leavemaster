package com.leave.master.leavemaster.repository;

import com.leave.master.leavemaster.model.UserRole;
import com.leave.master.leavemaster.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
  Optional<UserRole> findByName(UserRoleEnum name);
}
