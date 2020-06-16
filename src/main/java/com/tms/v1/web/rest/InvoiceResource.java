package com.tms.v1.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.v1.domain.DataList;
import com.tms.v1.domain.DataObject;
import com.tms.v1.domain.Invoice;
import com.tms.v1.service.InvoiceService;
import com.tms.v1.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.tms.v1.domain.Invoice}.
 */
@RestController
@RequestMapping("/api")
public class InvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    private static final String ENTITY_NAME = "invoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceService invoiceService;

    public InvoiceResource(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * {@code POST  /invoices} : Create a new invoice.
     *
     * @param invoice the invoice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoice, or with status {@code 400 (Bad Request)} if the invoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to save Invoice : {}", invoice);
        if (invoice.getId() != null) {
            throw new BadRequestAlertException("A new invoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Invoice result = invoiceService.save(invoice);
        return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoices} : Updates an existing invoice.
     *
     * @param invoice the invoice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoice,
     * or with status {@code 400 (Bad Request)} if the invoice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoices")
    public ResponseEntity<Invoice> updateInvoice(@RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to update Invoice : {}", invoice);
        if (invoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Invoice result = invoiceService.save(invoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoices} : get all the invoices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoices in body.
     */
    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices(Pageable pageable) {
        log.debug("REST request to get a page of Invoices");
        Page<Invoice> page = invoiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoices/:id} : get the "id" invoice.
     *
     * @param id the id of the invoice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        log.debug("REST request to get Invoice : {}", id);
        Optional<Invoice> invoice = invoiceService.findOne(id);
		/*
		 * Invoice object =invoice.get();
		 * 
		 * DataList dataList= new DataList(); DataObject dataObject = new DataObject();
		 * dataObject.setName("EMAIL"); dataObject.setValue("132323232");
		 * 
		 * DataObject dataObject1 = new DataObject(); dataObject.setName("EMAIL");
		 * dataObject.setValue("132323232");
		 * dataList.setDataObjects(Arrays.asList(dataObject,dataObject1)); ObjectMapper
		 * Obj = new ObjectMapper(); try { String jsonStr =
		 * Obj.writeValueAsString(dataList); object.setPayterm(jsonStr); } catch
		 * (JsonProcessingException e) { throw new IllegalStateException(e); }
		 */
        return ResponseUtil.wrapOrNotFound(invoice); 
    }

    /**
     * {@code DELETE  /invoices/:id} : delete the "id" invoice.
     *
     * @param id the id of the invoice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.debug("REST request to delete Invoice : {}", id);
        invoiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/invoices?query=:query} : search for the invoice corresponding
     * to the query.
     *
     * @param query the query of the invoice search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/invoices")
    public ResponseEntity<List<Invoice>> searchInvoices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Invoices for query {}", query);
        Page<Invoice> page = invoiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
