package com.iychay.be.report.repository;

import com.iychay.be.report.model.IaReport;
import com.iychay.be.report.model.ReportScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IaReportRepository extends JpaRepository<IaReport, Long> {
    Page<IaReport> findByScope(ReportScope scope, Pageable pageable);

    Page<IaReport> findByScopeAndRefId(ReportScope scope, Long refId, Pageable pageable);

    Page<IaReport> findByScopeAndRefIdAndPeriodoContainingIgnoreCase(ReportScope scope, Long refId, String periodo, Pageable pageable);
}
