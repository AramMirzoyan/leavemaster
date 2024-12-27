package com.leave.master.leavemaster.repository;

import com.leave.master.leavemaster.model.LeaveBalanced;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveBalancedRepository extends JpaRepository<LeaveBalanced, String> {}
