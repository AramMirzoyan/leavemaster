package com.leave.master.leavemaster.service.impl;

import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import com.leave.master.leavemaster.model.LeaveTypeEntity;
import com.leave.master.leavemaster.repository.LeaveTypeRepository;
import com.leave.master.leavemaster.service.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public final class DefaultLeaveTypeService implements LeaveTypeService {
  private final LeaveTypeRepository leaveTypeRepository;

  /**
   * Retrieves all leave type entities from the repository.
   *
   * @return a list of {@link LeaveTypeEntity} objects.
   */
  @Override
  public List<LeaveTypeEntity> getAll() {
    log.info("start to get all data");
    return leaveTypeRepository.findAll();
  }

  /**
   * Retrieves a leave type entity by its ID.
   *
   * @param id the ID of the leave type entity to retrieve.
   * @return the {@link LeaveTypeEntity} corresponding to the given ID.
   * @throws IllegalArgumentException if the ID is null or empty.
   * @throws ServiceException if no leave type entity is found for the given ID.
   */
  @Override
  public LeaveTypeEntity getById(final String id) {
    Assert.notNull(id, "The id parameter can not be empty or null");
    return Optional.ofNullable(id)
        .filter(Predicate.not(String::isBlank))
        .flatMap(leaveTypeRepository::findById)
        .orElseThrow(
            () ->
                new ServiceException(
                    ServiceErrorCode.CAN_NOT_FOUND_DATA, () -> "by name %s".formatted(id)));
  }

  /**
   * Retrieves a leave type entity by its name.
   *
   * @param name the name of the leave type entity to retrieve.
   * @return the {@link LeaveTypeEntity} corresponding to the given name.
   * @throws IllegalArgumentException if the name is null or empty.
   * @throws ServiceException if no leave type entity is found for the given name.
   */
  @Override
  public LeaveTypeEntity getByName(final String name) {
    Assert.notNull(name, "The name parameter can not be empty or null");
    return Optional.of(name)
        .filter(Predicate.not(String::isBlank))
        .flatMap(leaveTypeRepository::findByName)
        .orElseThrow(
            () ->
                new ServiceException(
                    ServiceErrorCode.CAN_NOT_FOUND_DATA, () -> "by name %s".formatted(name)));
  }
}
