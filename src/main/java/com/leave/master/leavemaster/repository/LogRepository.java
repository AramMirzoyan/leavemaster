package com.leave.master.leavemaster.repository;

import com.leave.master.leavemaster.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Logs,String> {
}
