package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cwms.entities.ExportAudit;

public interface ExportAuditRepo extends JpaRepository<ExportAudit, String> {

}
