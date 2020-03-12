package com.tms.v1.web.rest;

import com.tms.v1.TmsV1ApplicationApp;
import com.tms.v1.domain.AccountHistory;
import com.tms.v1.repository.AccountHistoryRepository;
import com.tms.v1.repository.search.AccountHistorySearchRepository;
import com.tms.v1.service.AccountHistoryService;

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

/**
 * Integration tests for the {@link AccountHistoryResource} REST controller.
 */
@SpringBootTest(classes = TmsV1ApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountHistoryResourceIT {

    private static final String DEFAULT_ENITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_LINK = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @Autowired
    private AccountHistoryService accountHistoryService;

    /**
     * This repository is mocked in the com.tms.v1.repository.search test package.
     *
     * @see com.tms.v1.repository.search.AccountHistorySearchRepositoryMockConfiguration
     */
    @Autowired
    private AccountHistorySearchRepository mockAccountHistorySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountHistoryMockMvc;

    private AccountHistory accountHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountHistory createEntity(EntityManager em) {
        AccountHistory accountHistory = new AccountHistory()
            .enityName(DEFAULT_ENITY_NAME)
            .entityLink(DEFAULT_ENTITY_LINK)
            .action(DEFAULT_ACTION)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return accountHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountHistory createUpdatedEntity(EntityManager em) {
        AccountHistory accountHistory = new AccountHistory()
            .enityName(UPDATED_ENITY_NAME)
            .entityLink(UPDATED_ENTITY_LINK)
            .action(UPDATED_ACTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return accountHistory;
    }

    @BeforeEach
    public void initTest() {
        accountHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountHistory() throws Exception {
        int databaseSizeBeforeCreate = accountHistoryRepository.findAll().size();

        // Create the AccountHistory
        restAccountHistoryMockMvc.perform(post("/api/account-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountHistory)))
            .andExpect(status().isCreated());

        // Validate the AccountHistory in the database
        List<AccountHistory> accountHistoryList = accountHistoryRepository.findAll();
        assertThat(accountHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        AccountHistory testAccountHistory = accountHistoryList.get(accountHistoryList.size() - 1);
        assertThat(testAccountHistory.getEnityName()).isEqualTo(DEFAULT_ENITY_NAME);
        assertThat(testAccountHistory.getEntityLink()).isEqualTo(DEFAULT_ENTITY_LINK);
        assertThat(testAccountHistory.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testAccountHistory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAccountHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAccountHistory.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testAccountHistory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the AccountHistory in Elasticsearch
        verify(mockAccountHistorySearchRepository, times(1)).save(testAccountHistory);
    }

    @Test
    @Transactional
    public void createAccountHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountHistoryRepository.findAll().size();

        // Create the AccountHistory with an existing ID
        accountHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountHistoryMockMvc.perform(post("/api/account-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountHistory)))
            .andExpect(status().isBadRequest());

        // Validate the AccountHistory in the database
        List<AccountHistory> accountHistoryList = accountHistoryRepository.findAll();
        assertThat(accountHistoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the AccountHistory in Elasticsearch
        verify(mockAccountHistorySearchRepository, times(0)).save(accountHistory);
    }


    @Test
    @Transactional
    public void getAllAccountHistories() throws Exception {
        // Initialize the database
        accountHistoryRepository.saveAndFlush(accountHistory);

        // Get all the accountHistoryList
        restAccountHistoryMockMvc.perform(get("/api/account-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].enityName").value(hasItem(DEFAULT_ENITY_NAME)))
            .andExpect(jsonPath("$.[*].entityLink").value(hasItem(DEFAULT_ENTITY_LINK)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getAccountHistory() throws Exception {
        // Initialize the database
        accountHistoryRepository.saveAndFlush(accountHistory);

        // Get the accountHistory
        restAccountHistoryMockMvc.perform(get("/api/account-histories/{id}", accountHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountHistory.getId().intValue()))
            .andExpect(jsonPath("$.enityName").value(DEFAULT_ENITY_NAME))
            .andExpect(jsonPath("$.entityLink").value(DEFAULT_ENTITY_LINK))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingAccountHistory() throws Exception {
        // Get the accountHistory
        restAccountHistoryMockMvc.perform(get("/api/account-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountHistory() throws Exception {
        // Initialize the database
        accountHistoryService.save(accountHistory);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAccountHistorySearchRepository);

        int databaseSizeBeforeUpdate = accountHistoryRepository.findAll().size();

        // Update the accountHistory
        AccountHistory updatedAccountHistory = accountHistoryRepository.findById(accountHistory.getId()).get();
        // Disconnect from session so that the updates on updatedAccountHistory are not directly saved in db
        em.detach(updatedAccountHistory);
        updatedAccountHistory
            .enityName(UPDATED_ENITY_NAME)
            .entityLink(UPDATED_ENTITY_LINK)
            .action(UPDATED_ACTION)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restAccountHistoryMockMvc.perform(put("/api/account-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccountHistory)))
            .andExpect(status().isOk());

        // Validate the AccountHistory in the database
        List<AccountHistory> accountHistoryList = accountHistoryRepository.findAll();
        assertThat(accountHistoryList).hasSize(databaseSizeBeforeUpdate);
        AccountHistory testAccountHistory = accountHistoryList.get(accountHistoryList.size() - 1);
        assertThat(testAccountHistory.getEnityName()).isEqualTo(UPDATED_ENITY_NAME);
        assertThat(testAccountHistory.getEntityLink()).isEqualTo(UPDATED_ENTITY_LINK);
        assertThat(testAccountHistory.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAccountHistory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAccountHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAccountHistory.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testAccountHistory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the AccountHistory in Elasticsearch
        verify(mockAccountHistorySearchRepository, times(1)).save(testAccountHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountHistory() throws Exception {
        int databaseSizeBeforeUpdate = accountHistoryRepository.findAll().size();

        // Create the AccountHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountHistoryMockMvc.perform(put("/api/account-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountHistory)))
            .andExpect(status().isBadRequest());

        // Validate the AccountHistory in the database
        List<AccountHistory> accountHistoryList = accountHistoryRepository.findAll();
        assertThat(accountHistoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccountHistory in Elasticsearch
        verify(mockAccountHistorySearchRepository, times(0)).save(accountHistory);
    }

    @Test
    @Transactional
    public void deleteAccountHistory() throws Exception {
        // Initialize the database
        accountHistoryService.save(accountHistory);

        int databaseSizeBeforeDelete = accountHistoryRepository.findAll().size();

        // Delete the accountHistory
        restAccountHistoryMockMvc.perform(delete("/api/account-histories/{id}", accountHistory.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountHistory> accountHistoryList = accountHistoryRepository.findAll();
        assertThat(accountHistoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AccountHistory in Elasticsearch
        verify(mockAccountHistorySearchRepository, times(1)).deleteById(accountHistory.getId());
    }

    @Test
    @Transactional
    public void searchAccountHistory() throws Exception {
        // Initialize the database
        accountHistoryService.save(accountHistory);
        when(mockAccountHistorySearchRepository.search(queryStringQuery("id:" + accountHistory.getId())))
            .thenReturn(Collections.singletonList(accountHistory));
        // Search the accountHistory
        restAccountHistoryMockMvc.perform(get("/api/_search/account-histories?query=id:" + accountHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].enityName").value(hasItem(DEFAULT_ENITY_NAME)))
            .andExpect(jsonPath("$.[*].entityLink").value(hasItem(DEFAULT_ENTITY_LINK)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
}
