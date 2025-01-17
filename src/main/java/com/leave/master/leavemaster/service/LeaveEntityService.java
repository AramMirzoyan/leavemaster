package com.leave.master.leavemaster.service;

import com.leave.master.leavemaster.dto.leave.LeaveRequestDto;

/**
 * Service interface for managing leave entities.
 *
 * <p>This interface defines the contract for handling leave-related operations, including the
 * creation of leave entries. Implementing classes must provide the concrete behavior for these
 * operations.
 */
public interface LeaveEntityService {
  /**
   * Creates a new leave entry based on the provided request data.
   *
   * <p>Implementations of this method should handle any necessary validation and persist the leave
   * data to the appropriate repository. It is the responsibility of the implementation to ensure
   * thread-safety and transaction management if required.
   *
   * @param requestDto the leave request data.
   * @return {@code true} if the leave entry was created successfully; {@code false} otherwise.
   */
  boolean create(LeaveRequestDto requestDto);
}
