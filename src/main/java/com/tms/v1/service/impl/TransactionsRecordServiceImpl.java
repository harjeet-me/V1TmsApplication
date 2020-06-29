package com.tms.v1.service.impl;

import com.tms.v1.service.TransactionsRecordService;
import com.tms.v1.domain.TransactionsRecord;
import com.tms.v1.repository.TransactionsRecordRepository;
import com.tms.v1.repository.search.TransactionsRecordSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TransactionsRecord}.
 */
@Service
@Transactional
public class TransactionsRecordServiceImpl implements TransactionsRecordService {

    private final Logger log = LoggerFactory.getLogger(TransactionsRecordServiceImpl.class);

    private final TransactionsRecordRepository transactionsRecordRepository;

    private final TransactionsRecordSearchRepository transactionsRecordSearchRepository;

    public TransactionsRecordServiceImpl(TransactionsRecordRepository transactionsRecordRepository, TransactionsRecordSearchRepository transactionsRecordSearchRepository) {
        this.transactionsRecordRepository = transactionsRecordRepository;
        this.transactionsRecordSearchRepository = transactionsRecordSearchRepository;
    }

    /**
     * Save a transactionsRecord.
     *
     * @param transactionsRecord the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionsRecord save(TransactionsRecord transactionsRecord) {
        log.debug("Request to save TransactionsRecord : {}", transactionsRecord);
        TransactionsRecord result = transactionsRecordRepository.save(transactionsRecord);
      //  transactionsRecordSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the transactionsRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionsRecord> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionsRecords");
        return transactionsRecordRepository.findAll(pageable);
    }

    /**
     * Get one transactionsRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionsRecord> findOne(Long id) {
        log.debug("Request to get TransactionsRecord : {}", id);
        return transactionsRecordRepository.findById(id);
    }

    /**
     * Delete the transactionsRecord by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionsRecord : {}", id);
        transactionsRecordRepository.deleteById(id);
        transactionsRecordSearchRepository.deleteById(id);
    }

    /**
     * Search for the transactionsRecord corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionsRecord> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TransactionsRecords for query {}", query);
        return transactionsRecordSearchRepository.search(queryStringQuery(query), pageable);    }

	@Override
	public List<TransactionsRecord> findByDescription(String description) {
		return transactionsRecordRepository.findByDescription(description);
	}
}
