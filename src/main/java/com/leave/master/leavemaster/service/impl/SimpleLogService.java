package com.leave.master.leavemaster.service.impl;

import com.leave.master.leavemaster.model.Logs;
import com.leave.master.leavemaster.repository.LogRepository;
import com.leave.master.leavemaster.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleLogService implements LogService {
   private  final LogRepository logRepository;


    @Override
    public void save(final Logs logs) {
        logRepository.save(logs);
    }
}
