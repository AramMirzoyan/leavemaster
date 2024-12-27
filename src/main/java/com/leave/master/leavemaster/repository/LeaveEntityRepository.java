package com.leave.master.leavemaster.repository;

import com.leave.master.leavemaster.model.LeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveEntityRepository extends JpaRepository<LeaveEntity, String> {}
