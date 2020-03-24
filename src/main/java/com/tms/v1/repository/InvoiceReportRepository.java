package com.tms.v1.repository;

import com.tms.v1.domain.InvoiceReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the InvoiceReport entity.
 */
@Repository
public interface InvoiceReportRepository extends JpaRepository<InvoiceReport, Long> {

    @Query(value = "select distinct invoiceReport from InvoiceReport invoiceReport left join fetch invoiceReport.invoices",
        countQuery = "select count(distinct invoiceReport) from InvoiceReport invoiceReport")
    Page<InvoiceReport> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct invoiceReport from InvoiceReport invoiceReport left join fetch invoiceReport.invoices")
    List<InvoiceReport> findAllWithEagerRelationships();

    @Query("select invoiceReport from InvoiceReport invoiceReport left join fetch invoiceReport.invoices where invoiceReport.id =:id")
    Optional<InvoiceReport> findOneWithEagerRelationships(@Param("id") Long id);
}
