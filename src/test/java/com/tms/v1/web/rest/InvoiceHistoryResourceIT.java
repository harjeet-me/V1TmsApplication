package com.tms.v1.web.rest;

import com.tms.v1.TmsV1ApplicationApp;
import com.tms.v1.domain.InvoiceHistory;
import com.tms.v1.repository.InvoiceHistoryRepository;
import com.tms.v1.repository.search.InvoiceHistorySearchRepository;
import com.tms.v1.service.InvoiceHistoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tms.v1.domain.enumeration.InvoiceStatus;
/**
 * Integration tests for the {@link InvoiceHistoryResource} REST controller.
 */
@SpringBootTest(classes = TmsV1ApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceHistoryResourceIT {

    private static final InvoiceStatus DEFAULT_STATUS = InvoiceStatus.DRAFT;
    private static final InvoiceStatus UPDATED_STATUS = InvoiceStatus.GENERATED;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private InvoiceHistoryRepository invoiceHistoryRepository;

    @Autowired
    private InvoiceHistoryService invoiceHistoryService;

    /**
     * This repository is mocked in the com.tms.v1.repository.search test package.
     *
     * @see com.tms.v1.repository.search.InvoiceHistorySearchRepositoryMockConfiguration
     */
    @Autowired
    private InvoiceHistorySearchRepository mockInvoiceHistorySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceHistoryMockMvc;

    private InvoiceHistory invoiceHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceHistory createEntity(EntityManager em) {
        InvoiceHistory invoiceHistory = new InvoiceHistory()
            .status(DEFAULT_STATUS)
            .comment(DEFAULT_COMMENT)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return invoiceHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceHistory createUpdatedEntity(EntityManager em) {
        InvoiceHistory invoiceHistory = new InvoiceHistory()
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return invoiceHistory;
    }

    @BeforeEach
    public void initTest() {
        invoiceHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceHistory() throws Exception {
        int databaseSizeBeforeCreate = invoiceHistoryRepository.findAll().size();

        // Create the InvoiceHistory
        restInvoiceHistoryMockMvc.perform(post("/api/invoice-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceHistory)))
            .andExpect(status().isCreated());

        // Validate the InvoiceHistory in the database
        List<InvoiceHistory> invoiceHistoryList = invoiceHistoryRepository.findAll();
        assertThat(invoiceHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceHistory testInvoiceHistory = invoiceHistoryList.get(invoiceHistoryList.size() - 1);
        assertThat(testInvoiceHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInvoiceHistory.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testInvoiceHistory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInvoiceHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInvoiceHistory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testInvoiceHistory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the InvoiceHistory in Elasticsearch
        verify(mockInvoiceHistorySearchRepository, times(1)).save(testInvoiceHistory);
    }

    @Test
    @Transactional
    public void createInvoiceHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceHistoryRepository.findAll().size();

        // Create the InvoiceHistory with an existing ID
        invoiceHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceHistoryMockMvc.perform(post("/api/invoice-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceHistory in the database
        List<InvoiceHistory> invoiceHistoryList = invoiceHistoryRepository.findAll();
        assertThat(invoiceHistoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the InvoiceHistory in Elasticsearch
        verify(mockInvoiceHistorySearchRepository, times(0)).save(invoiceHistory);
    }


    @Test
    @Transactional
    public void getAllInvoiceHistories() throws Exception {
        // Initialize the database
        invoiceHistoryRepository.saveAndFlush(invoiceHistory);

        // Get all the invoiceHistoryList
        restInvoiceHistoryMockMvc.perform(get("/api/invoice-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getInvoiceHistory() throws Exception {
        // Initialize the database
        invoiceHistoryRepository.saveAndFlush(invoiceHistory);

        // Get the invoiceHistory
        restInvoiceHistoryMockMvc.perform(get("/api/invoice-histories/{id}", invoiceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceHistory.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceHistory() throws Exception {
        // Get the invoiceHistory
        restInvoiceHistoryMockMvc.perform(get("/api/invoice-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceHistory() throws Exception {
        // Initialize the database
        invoiceHistoryService.save(invoiceHistory);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInvoiceHistorySearchRepository);

        int databaseSizeBeforeUpdate = invoiceHistoryRepository.findAll().size();

        // Update the invoiceHistory
        InvoiceHistory updatedInvoiceHistory = invoiceHistoryRepository.findById(invoiceHistory.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceHistory are not directly saved in db
        em.detach(updatedInvoiceHistory);
        updatedInvoiceHistory
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restInvoiceHistoryMockMvc.perform(put("/api/invoice-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceHistory)))
            .andExpect(status().isOk());

        // Validate the InvoiceHistory in the database
        List<InvoiceHistory> invoiceHistoryList = invoiceHistoryRepository.findAll();
        assertThat(invoiceHistoryList).hasSize(databaseSizeBeforeUpdate);
        InvoiceHistory testInvoiceHistory = invoiceHistoryList.get(invoiceHistoryList.size() - 1);
        assertThat(testInvoiceHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInvoiceHistory.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testInvoiceHistory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInvoiceHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInvoiceHistory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testInvoiceHistory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the InvoiceHistory in Elasticsearch
        verify(mockInvoiceHistorySearchRepository, times(1)).save(testInvoiceHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceHistory() throws Exception {
        int databaseSizeBeforeUpdate = invoiceHistoryRepository.findAll().size();

        // Create the InvoiceHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceHistoryMockMvc.perform(put("/api/invoice-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceHistory in the database
        List<InvoiceHistory> invoiceHistoryList = invoiceHistoryRepository.findAll();
        assertThat(invoiceHistoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InvoiceHistory in Elasticsearch
        verify(mockInvoiceHistorySearchRepository, times(0)).save(invoiceHistory);
    }

    @Test
    @Transactional
    public void deleteInvoiceHistory() throws Exception {
        // Initialize the database
        invoiceHistoryService.save(invoiceHistory);

        int databaseSizeBeforeDelete = invoiceHistoryRepository.findAll().size();

        // Delete the invoiceHistory
        restInvoiceHistoryMockMvc.perform(delete("/api/invoice-histories/{id}", invoiceHistory.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceHistory> invoiceHistoryList = invoiceHistoryRepository.findAll();
        assertThat(invoiceHistoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InvoiceHistory in Elasticsearch
        verify(mockInvoiceHistorySearchRepository, times(1)).deleteById(invoiceHistory.getId());
    }

    @Test
    @Transactional
    public void searchInvoiceHistory() throws Exception {
        // Initialize the database
        invoiceHistoryService.save(invoiceHistory);
        when(mockInvoiceHistorySearchRepository.search(queryStringQuery("id:" + invoiceHistory.getId())))
            .thenReturn(Collections.singletonList(invoiceHistory));
        // Search the invoiceHistory
        restInvoiceHistoryMockMvc.perform(get("/api/_search/invoice-histories?query=id:" + invoiceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
}
