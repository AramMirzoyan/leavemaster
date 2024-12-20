package com.leave.master.leavemaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.master.leavemaster.model.LeaveBalanced;

@Repository
public interface LeaveBalancedRepository extends JpaRepository<LeaveBalanced, String> {}
