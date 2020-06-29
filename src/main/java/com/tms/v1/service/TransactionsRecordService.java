package com.tms.v1.service;

import com.tms.v1.domain.TransactionsRecord;
import com.tms.v1.domain.enumeration.TransactionType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TransactionsRecord}.
 */
public interface TransactionsRecordService {

    /**
     * Save a transactionsRecord.
     *
     * @param transactionsRecord the entity to save.
     * @return the persisted entity.
     */
    TransactionsRecord save(TransactionsRecord transactionsRecord);

    /**
     * Get all the transactionsRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionsRecord> findAll(Pageable pageable);

    /**
     * Get the "id" transactionsRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionsRecord> findOne(Long id);

    /**
     * Delete the "id" transactionsRecord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the transactionsRecord corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionsRecord> search(String query, Pageable pageable);
    
    
    public List<TransactionsRecord> findByDescription(String description);
    

	List<TransactionsRecord> findByTxTypeAndTxRef(TransactionType txType, String txRef);
}
