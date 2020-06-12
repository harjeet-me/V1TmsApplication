package com.tms.v1.web.rest;

import com.tms.v1.TmsV1ApplicationApp;
import com.tms.v1.domain.InvoiceReport;
import com.tms.v1.repository.InvoiceReportRepository;
import com.tms.v1.repository.search.InvoiceReportSearchRepository;
import com.tms.v1.service.InvoiceReportService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InvoiceReportResource} REST controller.
 */
@SpringBootTest(classes = TmsV1ApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceReportResourceIT {

    private static final Long DEFAULT_CUSTOMER = 1L;
    private static final Long UPDATED_CUSTOMER = 2L;

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_INVOICE_REPORT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_INVOICE_REPORT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_INVOICE_REPORT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_INVOICE_REPORT_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private InvoiceReportRepository invoiceReportRepository;

    @Mock
    private InvoiceReportRepository invoiceReportRepositoryMock;

    @Mock
    private InvoiceReportService invoiceReportServiceMock;

    @Autowired
    private InvoiceReportService invoiceReportService;

    /**
     * This repository is mocked in the com.tms.v1.repository.search test package.
     *
     * @see com.tms.v1.repository.search.InvoiceReportSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvoiceReportSearchRepository mockInvoiceReportSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceReportMockMvc;

    private InvoiceReport invoiceReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceReport createEntity(EntityManager em) {
        InvoiceReport invoiceReport = new InvoiceReport()
            .customer(DEFAULT_CUSTOMER)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .remarks(DEFAULT_REMARKS)
            .invoiceReport(DEFAULT_INVOICE_REPORT)
            .invoiceReportContentType(DEFAULT_INVOICE_REPORT_CONTENT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return invoiceReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceReport createUpdatedEntity(EntityManager em) {
        InvoiceReport invoiceReport = new InvoiceReport()
            .customer(UPDATED_CUSTOMER)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .remarks(UPDATED_REMARKS)
            .invoiceReport(UPDATED_INVOICE_REPORT)
            .invoiceReportContentType(UPDATED_INVOICE_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return invoiceReport;
    }

    @BeforeEach
    public void initTest() {
        invoiceReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceReport() throws Exception {
        int databaseSizeBeforeCreate = invoiceReportRepository.findAll().size();

        // Create the InvoiceReport
        restInvoiceReportMockMvc.perform(post("/api/invoice-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceReport)))
            .andExpect(status().isCreated());

        // Validate the InvoiceReport in the database
        List<InvoiceReport> invoiceReportList = invoiceReportRepository.findAll();
        assertThat(invoiceReportList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceReport testInvoiceReport = invoiceReportList.get(invoiceReportList.size() - 1);
        assertThat(testInvoiceReport.getCustomer()).isEqualTo(DEFAULT_CUSTOMER);
        assertThat(testInvoiceReport.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testInvoiceReport.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testInvoiceReport.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testInvoiceReport.getInvoiceReport()).isEqualTo(DEFAULT_INVOICE_REPORT);
        assertThat(testInvoiceReport.getInvoiceReportContentType()).isEqualTo(DEFAULT_INVOICE_REPORT_CONTENT_TYPE);
        assertThat(testInvoiceReport.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInvoiceReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInvoiceReport.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testInvoiceReport.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the InvoiceReport in Elasticsearch
        verify(mockInvoiceReportSearchRepository, times(1)).save(testInvoiceReport);
    }

    @Test
    @Transactional
    public void createInvoiceReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceReportRepository.findAll().size();

        // Create the InvoiceReport with an existing ID
        invoiceReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceReportMockMvc.perform(post("/api/invoice-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceReport)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceReport in the database
        List<InvoiceReport> invoiceReportList = invoiceReportRepository.findAll();
        assertThat(invoiceReportList).hasSize(databaseSizeBeforeCreate);

        // Validate the InvoiceReport in Elasticsearch
        verify(mockInvoiceReportSearchRepository, times(0)).save(invoiceReport);
    }


    @Test
    @Transactional
    public void getAllInvoiceReports() throws Exception {
        // Initialize the database
        invoiceReportRepository.saveAndFlush(invoiceReport);

        // Get all the invoiceReportList
        restInvoiceReportMockMvc.perform(get("/api/invoice-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER.intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].invoiceReportContentType").value(hasItem(DEFAULT_INVOICE_REPORT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoiceReport").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_REPORT))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllInvoiceReportsWithEagerRelationshipsIsEnabled() throws Exception {
        when(invoiceReportServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoiceReportMockMvc.perform(get("/api/invoice-reports?eagerload=true"))
            .andExpect(status().isOk());

        verify(invoiceReportServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllInvoiceReportsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(invoiceReportServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoiceReportMockMvc.perform(get("/api/invoice-reports?eagerload=true"))
            .andExpect(status().isOk());

        verify(invoiceReportServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getInvoiceReport() throws Exception {
        // Initialize the database
        invoiceReportRepository.saveAndFlush(invoiceReport);

        // Get the invoiceReport
        restInvoiceReportMockMvc.perform(get("/api/invoice-reports/{id}", invoiceReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceReport.getId().intValue()))
            .andExpect(jsonPath("$.customer").value(DEFAULT_CUSTOMER.intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.invoiceReportContentType").value(DEFAULT_INVOICE_REPORT_CONTENT_TYPE))
            .andExpect(jsonPath("$.invoiceReport").value(Base64Utils.encodeToString(DEFAULT_INVOICE_REPORT)))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceReport() throws Exception {
        // Get the invoiceReport
        restInvoiceReportMockMvc.perform(get("/api/invoice-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceReport() throws Exception {
        // Initialize the database
        invoiceReportService.save(invoiceReport);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInvoiceReportSearchRepository);

        int databaseSizeBeforeUpdate = invoiceReportRepository.findAll().size();

        // Update the invoiceReport
        InvoiceReport updatedInvoiceReport = invoiceReportRepository.findById(invoiceReport.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceReport are not directly saved in db
        em.detach(updatedInvoiceReport);
        updatedInvoiceReport
            .customer(UPDATED_CUSTOMER)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .remarks(UPDATED_REMARKS)
            .invoiceReport(UPDATED_INVOICE_REPORT)
            .invoiceReportContentType(UPDATED_INVOICE_REPORT_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restInvoiceReportMockMvc.perform(put("/api/invoice-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceReport)))
            .andExpect(status().isOk());

        // Validate the InvoiceReport in the database
        List<InvoiceReport> invoiceReportList = invoiceReportRepository.findAll();
        assertThat(invoiceReportList).hasSize(databaseSizeBeforeUpdate);
        InvoiceReport testInvoiceReport = invoiceReportList.get(invoiceReportList.size() - 1);
        assertThat(testInvoiceReport.getCustomer()).isEqualTo(UPDATED_CUSTOMER);
        assertThat(testInvoiceReport.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testInvoiceReport.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testInvoiceReport.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testInvoiceReport.getInvoiceReport()).isEqualTo(UPDATED_INVOICE_REPORT);
        assertThat(testInvoiceReport.getInvoiceReportContentType()).isEqualTo(UPDATED_INVOICE_REPORT_CONTENT_TYPE);
        assertThat(testInvoiceReport.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInvoiceReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInvoiceReport.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testInvoiceReport.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the InvoiceReport in Elasticsearch
        verify(mockInvoiceReportSearchRepository, times(1)).save(testInvoiceReport);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceReport() throws Exception {
        int databaseSizeBeforeUpdate = invoiceReportRepository.findAll().size();

        // Create the InvoiceReport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceReportMockMvc.perform(put("/api/invoice-reports").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceReport)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceReport in the database
        List<InvoiceReport> invoiceReportList = invoiceReportRepository.findAll();
        assertThat(invoiceReportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InvoiceReport in Elasticsearch
        verify(mockInvoiceReportSearchRepository, times(0)).save(invoiceReport);
    }

    @Test
    @Transactional
    public void deleteInvoiceReport() throws Exception {
        // Initialize the database
        invoiceReportService.save(invoiceReport);

        int databaseSizeBeforeDelete = invoiceReportRepository.findAll().size();

        // Delete the invoiceReport
        restInvoiceReportMockMvc.perform(delete("/api/invoice-reports/{id}", invoiceReport.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceReport> invoiceReportList = invoiceReportRepository.findAll();
        assertThat(invoiceReportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InvoiceReport in Elasticsearch
        verify(mockInvoiceReportSearchRepository, times(1)).deleteById(invoiceReport.getId());
    }

    @Test
    @Transactional
    public void searchInvoiceReport() throws Exception {
        // Initialize the database
        invoiceReportService.save(invoiceReport);
        when(mockInvoiceReportSearchRepository.search(queryStringQuery("id:" + invoiceReport.getId())))
            .thenReturn(Collections.singletonList(invoiceReport));
        // Search the invoiceReport
        restInvoiceReportMockMvc.perform(get("/api/_search/invoice-reports?query=id:" + invoiceReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER.intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].invoiceReportContentType").value(hasItem(DEFAULT_INVOICE_REPORT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoiceReport").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_REPORT))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
}
