package com.leave.master.leavemaster.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.master.leavemaster.model.UserEntity;

/**
 * Repository interface for managing {@link UserEntity} persistence.
 *
 * <p>This interface provides methods for common CRUD operations and custom queries on the {@link
 * UserEntity}.
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

  /**
   * Checks if a user exists by their email address.
   *
   * @param email the email address to check for existence.
   * @return {@code true} if a user with the specified email exists, {@code false} otherwise.
   */
  boolean existsByEmail(String email);

  Optional<UserEntity> findById(String id);
}
