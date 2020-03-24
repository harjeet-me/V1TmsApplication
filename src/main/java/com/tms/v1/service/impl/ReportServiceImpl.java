package com.tms.v1.service.impl;

import com.tms.v1.service.InvoiceService;
import com.tms.v1.service.ReportService;
import com.tms.v1.domain.Invoice;
import com.tms.v1.domain.Report;
import com.tms.v1.domain.enumeration.ReportType;
import com.tms.v1.repository.ReportRepository;
import com.tms.v1.repository.search.ReportSearchRepository;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Report}.
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
    
    @Autowired
    public InvoiceService invoiceService;
    @Autowired
    InvoiceStmtByCustomerServiceImpl stmtByCustomerServiceImpl;

    private final ReportRepository reportRepository;

    private final ReportSearchRepository reportSearchRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ReportSearchRepository reportSearchRepository) {
        this.reportRepository = reportRepository;
        this.reportSearchRepository = reportSearchRepository;
    }

    /**
     * Save a report.
     *
     * @param report the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Report save(Report report) {
        log.debug("Request to save Report : {}", report);
        if(report.getReportType()!=null && report.getReportType()==ReportType.INV_STMT_OF_CUSTOMER) {
        	List<Invoice>  invoices=   invoiceService.findByCustomer_IdAndInvoiceDateBetween(1l, report.getFromDate(), report.getToDate());
        	try {
				report.setAttachment(stmtByCustomerServiceImpl.generateReport(invoices));
				report.setAttachmentContentType("application/pdf");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        }
     
        Report result = reportRepository.save(report);
        reportSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the reports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Report> findAll() {
        log.debug("Request to get all Reports");
        return reportRepository.findAll();
    }

    /**
     * Get one report by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Report> findOne(Long id) {
        log.debug("Request to get Report : {}", id);
        return reportRepository.findById(id);
    }

    /**
     * Delete the report by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Report : {}", id);
        reportRepository.deleteById(id);
        reportSearchRepository.deleteById(id);
    }

    /**
     * Search for the report corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Report> search(String query) {
        log.debug("Request to search Reports for query {}", query);
        return StreamSupport
            .stream(reportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
