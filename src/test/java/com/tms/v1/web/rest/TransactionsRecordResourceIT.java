package com.tms.v1.web.rest;

import com.tms.v1.TmsV1ApplicationApp;
import com.tms.v1.domain.TransactionsRecord;
import com.tms.v1.repository.TransactionsRecordRepository;
import com.tms.v1.repository.search.TransactionsRecordSearchRepository;
import com.tms.v1.service.TransactionsRecordService;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tms.v1.domain.enumeration.TransactionType;
import com.tms.v1.domain.enumeration.TxStatus;
import com.tms.v1.domain.enumeration.CURRENCY;
/**
 * Integration tests for the {@link TransactionsRecordResource} REST controller.
 */
@SpringBootTest(classes = TmsV1ApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionsRecordResourceIT {

    private static final TransactionType DEFAULT_TX_TYPE = TransactionType.CREDIT;
    private static final TransactionType UPDATED_TX_TYPE = TransactionType.PAYMENT;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_TX_AMMOUNT = 1D;
    private static final Double UPDATED_TX_AMMOUNT = 2D;

    private static final String DEFAULT_TX_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_TX_REF_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TX_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_TX_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TX_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TX_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TX_COMPLETED_BY = "AAAAAAAAAA";
    private static final String UPDATED_TX_COMPLETED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TX_COMPLETED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TX_COMPLETED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final TxStatus DEFAULT_STATUS = TxStatus.INITIATED;
    private static final TxStatus UPDATED_STATUS = TxStatus.UNDERPROCESS;

    private static final byte[] DEFAULT_TX_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TX_DOC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TX_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TX_DOC_CONTENT_TYPE = "image/png";

    private static final CURRENCY DEFAULT_CURRENCY = CURRENCY.USD;
    private static final CURRENCY UPDATED_CURRENCY = CURRENCY.CAD;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private TransactionsRecordRepository transactionsRecordRepository;

    @Autowired
    private TransactionsRecordService transactionsRecordService;

    /**
     * This repository is mocked in the com.tms.v1.repository.search test package.
     *
     * @see com.tms.v1.repository.search.TransactionsRecordSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransactionsRecordSearchRepository mockTransactionsRecordSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionsRecordMockMvc;

    private TransactionsRecord transactionsRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionsRecord createEntity(EntityManager em) {
        TransactionsRecord transactionsRecord = new TransactionsRecord()
            .txType(DEFAULT_TX_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .txAmmount(DEFAULT_TX_AMMOUNT)
            .txRefNo(DEFAULT_TX_REF_NO)
            .txCreatedBy(DEFAULT_TX_CREATED_BY)
            .txCreatedDate(DEFAULT_TX_CREATED_DATE)
            .txCompletedBy(DEFAULT_TX_COMPLETED_BY)
            .txCompletedDate(DEFAULT_TX_COMPLETED_DATE)
            .status(DEFAULT_STATUS)
            .txDoc(DEFAULT_TX_DOC)
            .txDocContentType(DEFAULT_TX_DOC_CONTENT_TYPE)
            .currency(DEFAULT_CURRENCY)
            .remarks(DEFAULT_REMARKS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return transactionsRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionsRecord createUpdatedEntity(EntityManager em) {
        TransactionsRecord transactionsRecord = new TransactionsRecord()
            .txType(UPDATED_TX_TYPE)
            .description(UPDATED_DESCRIPTION)
            .txAmmount(UPDATED_TX_AMMOUNT)
            .txRefNo(UPDATED_TX_REF_NO)
            .txCreatedBy(UPDATED_TX_CREATED_BY)
            .txCreatedDate(UPDATED_TX_CREATED_DATE)
            .txCompletedBy(UPDATED_TX_COMPLETED_BY)
            .txCompletedDate(UPDATED_TX_COMPLETED_DATE)
            .status(UPDATED_STATUS)
            .txDoc(UPDATED_TX_DOC)
            .txDocContentType(UPDATED_TX_DOC_CONTENT_TYPE)
            .currency(UPDATED_CURRENCY)
            .remarks(UPDATED_REMARKS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return transactionsRecord;
    }

    @BeforeEach
    public void initTest() {
        transactionsRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionsRecord() throws Exception {
        int databaseSizeBeforeCreate = transactionsRecordRepository.findAll().size();

        // Create the TransactionsRecord
        restTransactionsRecordMockMvc.perform(post("/api/transactions-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionsRecord)))
            .andExpect(status().isCreated());

        // Validate the TransactionsRecord in the database
        List<TransactionsRecord> transactionsRecordList = transactionsRecordRepository.findAll();
        assertThat(transactionsRecordList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionsRecord testTransactionsRecord = transactionsRecordList.get(transactionsRecordList.size() - 1);
        assertThat(testTransactionsRecord.getTxType()).isEqualTo(DEFAULT_TX_TYPE);
        assertThat(testTransactionsRecord.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTransactionsRecord.getTxAmmount()).isEqualTo(DEFAULT_TX_AMMOUNT);
        assertThat(testTransactionsRecord.getTxRefNo()).isEqualTo(DEFAULT_TX_REF_NO);
        assertThat(testTransactionsRecord.getTxCreatedBy()).isEqualTo(DEFAULT_TX_CREATED_BY);
        assertThat(testTransactionsRecord.getTxCreatedDate()).isEqualTo(DEFAULT_TX_CREATED_DATE);
        assertThat(testTransactionsRecord.getTxCompletedBy()).isEqualTo(DEFAULT_TX_COMPLETED_BY);
        assertThat(testTransactionsRecord.getTxCompletedDate()).isEqualTo(DEFAULT_TX_COMPLETED_DATE);
        assertThat(testTransactionsRecord.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransactionsRecord.getTxDoc()).isEqualTo(DEFAULT_TX_DOC);
        assertThat(testTransactionsRecord.getTxDocContentType()).isEqualTo(DEFAULT_TX_DOC_CONTENT_TYPE);
        assertThat(testTransactionsRecord.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testTransactionsRecord.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTransactionsRecord.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTransactionsRecord.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTransactionsRecord.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testTransactionsRecord.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the TransactionsRecord in Elasticsearch
        verify(mockTransactionsRecordSearchRepository, times(1)).save(testTransactionsRecord);
    }

    @Test
    @Transactional
    public void createTransactionsRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionsRecordRepository.findAll().size();

        // Create the TransactionsRecord with an existing ID
        transactionsRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionsRecordMockMvc.perform(post("/api/transactions-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionsRecord)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionsRecord in the database
        List<TransactionsRecord> transactionsRecordList = transactionsRecordRepository.findAll();
        assertThat(transactionsRecordList).hasSize(databaseSizeBeforeCreate);

        // Validate the TransactionsRecord in Elasticsearch
        verify(mockTransactionsRecordSearchRepository, times(0)).save(transactionsRecord);
    }


    @Test
    @Transactional
    public void getAllTransactionsRecords() throws Exception {
        // Initialize the database
        transactionsRecordRepository.saveAndFlush(transactionsRecord);

        // Get all the transactionsRecordList
        restTransactionsRecordMockMvc.perform(get("/api/transactions-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionsRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].txType").value(hasItem(DEFAULT_TX_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].txAmmount").value(hasItem(DEFAULT_TX_AMMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].txRefNo").value(hasItem(DEFAULT_TX_REF_NO)))
            .andExpect(jsonPath("$.[*].txCreatedBy").value(hasItem(DEFAULT_TX_CREATED_BY)))
            .andExpect(jsonPath("$.[*].txCreatedDate").value(hasItem(DEFAULT_TX_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].txCompletedBy").value(hasItem(DEFAULT_TX_COMPLETED_BY)))
            .andExpect(jsonPath("$.[*].txCompletedDate").value(hasItem(DEFAULT_TX_COMPLETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].txDocContentType").value(hasItem(DEFAULT_TX_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].txDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_TX_DOC))))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getTransactionsRecord() throws Exception {
        // Initialize the database
        transactionsRecordRepository.saveAndFlush(transactionsRecord);

        // Get the transactionsRecord
        restTransactionsRecordMockMvc.perform(get("/api/transactions-records/{id}", transactionsRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionsRecord.getId().intValue()))
            .andExpect(jsonPath("$.txType").value(DEFAULT_TX_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.txAmmount").value(DEFAULT_TX_AMMOUNT.doubleValue()))
            .andExpect(jsonPath("$.txRefNo").value(DEFAULT_TX_REF_NO))
            .andExpect(jsonPath("$.txCreatedBy").value(DEFAULT_TX_CREATED_BY))
            .andExpect(jsonPath("$.txCreatedDate").value(DEFAULT_TX_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.txCompletedBy").value(DEFAULT_TX_COMPLETED_BY))
            .andExpect(jsonPath("$.txCompletedDate").value(DEFAULT_TX_COMPLETED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.txDocContentType").value(DEFAULT_TX_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.txDoc").value(Base64Utils.encodeToString(DEFAULT_TX_DOC)))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionsRecord() throws Exception {
        // Get the transactionsRecord
        restTransactionsRecordMockMvc.perform(get("/api/transactions-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionsRecord() throws Exception {
        // Initialize the database
        transactionsRecordService.save(transactionsRecord);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTransactionsRecordSearchRepository);

        int databaseSizeBeforeUpdate = transactionsRecordRepository.findAll().size();

        // Update the transactionsRecord
        TransactionsRecord updatedTransactionsRecord = transactionsRecordRepository.findById(transactionsRecord.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionsRecord are not directly saved in db
        em.detach(updatedTransactionsRecord);
        updatedTransactionsRecord
            .txType(UPDATED_TX_TYPE)
            .description(UPDATED_DESCRIPTION)
            .txAmmount(UPDATED_TX_AMMOUNT)
            .txRefNo(UPDATED_TX_REF_NO)
            .txCreatedBy(UPDATED_TX_CREATED_BY)
            .txCreatedDate(UPDATED_TX_CREATED_DATE)
            .txCompletedBy(UPDATED_TX_COMPLETED_BY)
            .txCompletedDate(UPDATED_TX_COMPLETED_DATE)
            .status(UPDATED_STATUS)
            .txDoc(UPDATED_TX_DOC)
            .txDocContentType(UPDATED_TX_DOC_CONTENT_TYPE)
            .currency(UPDATED_CURRENCY)
            .remarks(UPDATED_REMARKS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restTransactionsRecordMockMvc.perform(put("/api/transactions-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionsRecord)))
            .andExpect(status().isOk());

        // Validate the TransactionsRecord in the database
        List<TransactionsRecord> transactionsRecordList = transactionsRecordRepository.findAll();
        assertThat(transactionsRecordList).hasSize(databaseSizeBeforeUpdate);
        TransactionsRecord testTransactionsRecord = transactionsRecordList.get(transactionsRecordList.size() - 1);
        assertThat(testTransactionsRecord.getTxType()).isEqualTo(UPDATED_TX_TYPE);
        assertThat(testTransactionsRecord.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTransactionsRecord.getTxAmmount()).isEqualTo(UPDATED_TX_AMMOUNT);
        assertThat(testTransactionsRecord.getTxRefNo()).isEqualTo(UPDATED_TX_REF_NO);
        assertThat(testTransactionsRecord.getTxCreatedBy()).isEqualTo(UPDATED_TX_CREATED_BY);
        assertThat(testTransactionsRecord.getTxCreatedDate()).isEqualTo(UPDATED_TX_CREATED_DATE);
        assertThat(testTransactionsRecord.getTxCompletedBy()).isEqualTo(UPDATED_TX_COMPLETED_BY);
        assertThat(testTransactionsRecord.getTxCompletedDate()).isEqualTo(UPDATED_TX_COMPLETED_DATE);
        assertThat(testTransactionsRecord.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransactionsRecord.getTxDoc()).isEqualTo(UPDATED_TX_DOC);
        assertThat(testTransactionsRecord.getTxDocContentType()).isEqualTo(UPDATED_TX_DOC_CONTENT_TYPE);
        assertThat(testTransactionsRecord.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testTransactionsRecord.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTransactionsRecord.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTransactionsRecord.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTransactionsRecord.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testTransactionsRecord.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the TransactionsRecord in Elasticsearch
        verify(mockTransactionsRecordSearchRepository, times(1)).save(testTransactionsRecord);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionsRecord() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRecordRepository.findAll().size();

        // Create the TransactionsRecord

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionsRecordMockMvc.perform(put("/api/transactions-records").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionsRecord)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionsRecord in the database
        List<TransactionsRecord> transactionsRecordList = transactionsRecordRepository.findAll();
        assertThat(transactionsRecordList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TransactionsRecord in Elasticsearch
        verify(mockTransactionsRecordSearchRepository, times(0)).save(transactionsRecord);
    }

    @Test
    @Transactional
    public void deleteTransactionsRecord() throws Exception {
        // Initialize the database
        transactionsRecordService.save(transactionsRecord);

        int databaseSizeBeforeDelete = transactionsRecordRepository.findAll().size();

        // Delete the transactionsRecord
        restTransactionsRecordMockMvc.perform(delete("/api/transactions-records/{id}", transactionsRecord.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionsRecord> transactionsRecordList = transactionsRecordRepository.findAll();
        assertThat(transactionsRecordList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TransactionsRecord in Elasticsearch
        verify(mockTransactionsRecordSearchRepository, times(1)).deleteById(transactionsRecord.getId());
    }

    @Test
    @Transactional
    public void searchTransactionsRecord() throws Exception {
        // Initialize the database
        transactionsRecordService.save(transactionsRecord);
        when(mockTransactionsRecordSearchRepository.search(queryStringQuery("id:" + transactionsRecord.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(transactionsRecord), PageRequest.of(0, 1), 1));
        // Search the transactionsRecord
        restTransactionsRecordMockMvc.perform(get("/api/_search/transactions-records?query=id:" + transactionsRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionsRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].txType").value(hasItem(DEFAULT_TX_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].txAmmount").value(hasItem(DEFAULT_TX_AMMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].txRefNo").value(hasItem(DEFAULT_TX_REF_NO)))
            .andExpect(jsonPath("$.[*].txCreatedBy").value(hasItem(DEFAULT_TX_CREATED_BY)))
            .andExpect(jsonPath("$.[*].txCreatedDate").value(hasItem(DEFAULT_TX_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].txCompletedBy").value(hasItem(DEFAULT_TX_COMPLETED_BY)))
            .andExpect(jsonPath("$.[*].txCompletedDate").value(hasItem(DEFAULT_TX_COMPLETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].txDocContentType").value(hasItem(DEFAULT_TX_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].txDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_TX_DOC))))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
}
