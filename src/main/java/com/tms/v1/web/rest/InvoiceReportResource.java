package com.tms.v1.web.rest;

import com.tms.v1.domain.InvoiceReport;
import com.tms.v1.service.InvoiceReportService;
import com.tms.v1.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.tms.v1.domain.InvoiceReport}.
 */
@RestController
@RequestMapping("/api")
public class InvoiceReportResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceReportResource.class);

    private static final String ENTITY_NAME = "invoiceReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceReportService invoiceReportService;

    public InvoiceReportResource(InvoiceReportService invoiceReportService) {
        this.invoiceReportService = invoiceReportService;
    }

    /**
     * {@code POST  /invoice-reports} : Create a new invoiceReport.
     *
     * @param invoiceReport the invoiceReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceReport, or with status {@code 400 (Bad Request)} if the invoiceReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-reports")
    public ResponseEntity<InvoiceReport> createInvoiceReport(@RequestBody InvoiceReport invoiceReport) throws URISyntaxException {
        log.debug("REST request to save InvoiceReport : {}", invoiceReport);
        if (invoiceReport.getId() != null) {
            throw new BadRequestAlertException("A new invoiceReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceReport result = invoiceReportService.save(invoiceReport);
        return ResponseEntity.created(new URI("/api/invoice-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-reports} : Updates an existing invoiceReport.
     *
     * @param invoiceReport the invoiceReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceReport,
     * or with status {@code 400 (Bad Request)} if the invoiceReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-reports")
    public ResponseEntity<InvoiceReport> updateInvoiceReport(@RequestBody InvoiceReport invoiceReport) throws URISyntaxException {
        log.debug("REST request to update InvoiceReport : {}", invoiceReport);
        if (invoiceReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceReport result = invoiceReportService.save(invoiceReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoice-reports} : get all the invoiceReports.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceReports in body.
     */
    @GetMapping("/invoice-reports")
    public List<InvoiceReport> getAllInvoiceReports(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all InvoiceReports");
        return invoiceReportService.findAll();
    }

    /**
     * {@code GET  /invoice-reports/:id} : get the "id" invoiceReport.
     *
     * @param id the id of the invoiceReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-reports/{id}")
    public ResponseEntity<InvoiceReport> getInvoiceReport(@PathVariable Long id) {
        log.debug("REST request to get InvoiceReport : {}", id);
        Optional<InvoiceReport> invoiceReport = invoiceReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceReport);
    }

    /**
     * {@code DELETE  /invoice-reports/:id} : delete the "id" invoiceReport.
     *
     * @param id the id of the invoiceReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-reports/{id}")
    public ResponseEntity<Void> deleteInvoiceReport(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceReport : {}", id);
        invoiceReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/invoice-reports?query=:query} : search for the invoiceReport corresponding
     * to the query.
     *
     * @param query the query of the invoiceReport search.
     * @return the result of the search.
     */
    @GetMapping("/_search/invoice-reports")
    public List<InvoiceReport> searchInvoiceReports(@RequestParam String query) {
        log.debug("REST request to search InvoiceReports for query {}", query);
        return invoiceReportService.search(query);
    }
}
