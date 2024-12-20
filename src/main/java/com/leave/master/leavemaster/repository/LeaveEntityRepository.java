package com.leave.master.leavemaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.master.leavemaster.model.LeaveEntity;

@Repository
public interface LeaveEntityRepository extends JpaRepository<LeaveEntity, String> {}
