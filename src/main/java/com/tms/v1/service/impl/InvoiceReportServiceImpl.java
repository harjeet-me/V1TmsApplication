package com.tms.v1.service.impl;

import com.tms.v1.service.InvoiceReportService;
import com.tms.v1.service.InvoiceService;
import com.tms.v1.domain.Invoice;
import com.tms.v1.domain.InvoiceReport;
import com.tms.v1.domain.enumeration.ReportType;
import com.tms.v1.repository.InvoiceReportRepository;
import com.tms.v1.repository.search.InvoiceReportSearchRepository;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link InvoiceReport}.
 */
@Service
@Transactional
public class InvoiceReportServiceImpl implements InvoiceReportService {

    private final Logger log = LoggerFactory.getLogger(InvoiceReportServiceImpl.class);
    
    @Autowired
    public InvoiceService invoiceService;
    @Autowired
    InvoiceStmtByCustomerServiceImpl stmtByCustomerServiceImpl;

    private final InvoiceReportRepository invoiceReportRepository;

    private final InvoiceReportSearchRepository invoiceReportSearchRepository;

    public InvoiceReportServiceImpl(InvoiceReportRepository invoiceReportRepository, InvoiceReportSearchRepository invoiceReportSearchRepository) {
        this.invoiceReportRepository = invoiceReportRepository;
        this.invoiceReportSearchRepository = invoiceReportSearchRepository;
    }

    /**
     * Save a invoiceReport.
     *
     * @param invoiceReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoiceReport save(InvoiceReport invoiceReport) {
        log.debug("Request to save InvoiceReport : {}", invoiceReport);
       
        	List<Invoice>  invoices=   invoiceService.findByCustomer_IdAndInvoiceDateBetween(1l, invoiceReport.getFromDate(), invoiceReport.getToDate());
        	
        	try {
        		invoiceReport.setInvoiceReport(stmtByCustomerServiceImpl.generateReport(invoices));
        		invoiceReport.setInvoiceReportContentType("application/pdf");
        		invoiceReport.setInvoices(new HashSet<>(invoices));
			} catch (Exception e) {
				Log.error("jasper report gen exception",e);
			}
        
        
        InvoiceReport result = invoiceReportRepository.save(invoiceReport);
        invoiceReportSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the invoiceReports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceReport> findAll() {
        log.debug("Request to get all InvoiceReports");
        return invoiceReportRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the invoiceReports with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<InvoiceReport> findAllWithEagerRelationships(Pageable pageable) {
        return invoiceReportRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one invoiceReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceReport> findOne(Long id) {
        log.debug("Request to get InvoiceReport : {}", id);
        return invoiceReportRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the invoiceReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceReport : {}", id);
        invoiceReportRepository.deleteById(id);
        invoiceReportSearchRepository.deleteById(id);
    }

    /**
     * Search for the invoiceReport corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceReport> search(String query) {
        log.debug("Request to search InvoiceReports for query {}", query);
        return StreamSupport
            .stream(invoiceReportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
