package com.example.revenueshare.core.aop.repository;

import com.example.revenueshare.core.aop.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

}
