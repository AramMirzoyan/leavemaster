package com.leave.master.leavemaster.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.leave.master.leavemaster.dto.GenResponse;
import com.leave.master.leavemaster.dto.leave.LeaveRequestDto;
import com.leave.master.leavemaster.model.LeaveTypeEntity;
import com.leave.master.leavemaster.service.LeaveEntityService;
import com.leave.master.leavemaster.service.LeaveTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for managing leave-related operations in the LeaveMaster application.
 *
 * <p>This class provides endpoints for creating leave requests and fetching leave types based on
 * various criteria such as name or ID. It interacts with the {@link LeaveTypeService} and {@link
 * LeaveEntityService} to perform the necessary operations.
 *
 * <p>Endpoints:
 *
 * <ul>
 *   <li><code>POST /leave/create</code> - Creates a new leave request.
 *   <li><code>GET /leave/type</code> - Fetches all leave types.
 *   <li><code>GET /leave/type/name/{name}</code> - Fetches a leave type by name.
 *   <li><code>GET /leave/type/id/{id}</code> - Fetches a leave type by ID.
 * </ul>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
@Slf4j
@Tag(name = "${leave.tag.name}", description = "${leave.tag.dsc}")
public class LeaveController {

  private final LeaveTypeService leaveTypeService;
  private final LeaveEntityService leaveEntityService;

  /**
   * Creates a new leave request.
   *
   * @param requestDto the request data for creating a leave
   * @return a success response with a boolean indicating the operation's result
   */
  @Operation(
      summary = "${leave.create.summary.create}",
      description = "${leave.create.dsc}",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              content = @Content(schema = @Schema(implementation = LeaveRequestDto.class)),
              useParameterTypeSchema = true,
              required = true),
      method = "${leave.create.method}")
  @PostMapping("/create")
  public GenResponse<Boolean> createLeave(@RequestBody final LeaveRequestDto requestDto) {
    return GenResponse.success(leaveEntityService.create(requestDto));
  }

  /**
   * fetch all type of leave type entity.
   *
   * @return a list of leave type entity
   */
  @Operation(
      summary = "${leave.getall.summary.create}",
      description = "${leave.getall.dsc}",
      method = "${leave.getall.method}")
  @GetMapping("/type")
  public GenResponse<List<LeaveTypeEntity>> getAll() {
    return GenResponse.success(leaveTypeService.getAll());
  }

  /**
   * fetch leave type entity by name.
   *
   * @param name leave type ('Annual Leave','Sick Leave','Unpaid Leave')
   * @return leave type entity
   */
  @Operation(
      summary = "${leave.get.summary.create}",
      description = "${leave.get.dsc}",
      parameters = {
        @Parameter(
            name = "name",
            description = "Get Leave Entity by leave type name",
            required = true,
            example = "Annual Leave, Sick Leave, Unpaid Leave")
      },
      method = "${leave.get.method}")
  @GetMapping("/type/name/{name}")
  public GenResponse<LeaveTypeEntity> getLeaveTypeByName(@PathVariable final String name) {
    return GenResponse.success(leaveTypeService.getByName(name));
  }

  /**
   * fetch leave type entity by id.
   *
   * @param id leave type.
   * @return leave type entity
   */
  @Operation(
      summary = "${leave.getid.summary.create}",
      description = "${leave.getid.dsc}",
      parameters = {
        @Parameter(name = "id", description = "Get Leave Entity by id", required = true)
      },
      method = "${leave.get.method}")
  @GetMapping("/type/id/{id}")
  public GenResponse<LeaveTypeEntity> getLeaveTypeById(@PathVariable final String id) {
    return GenResponse.success(leaveTypeService.getById(id));
  }
}
