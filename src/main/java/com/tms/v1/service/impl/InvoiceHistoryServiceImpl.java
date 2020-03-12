package com.tms.v1.service.impl;

import com.tms.v1.service.InvoiceHistoryService;
import com.tms.v1.domain.InvoiceHistory;
import com.tms.v1.repository.InvoiceHistoryRepository;
import com.tms.v1.repository.search.InvoiceHistorySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link InvoiceHistory}.
 */
@Service
@Transactional
public class InvoiceHistoryServiceImpl implements InvoiceHistoryService {

    private final Logger log = LoggerFactory.getLogger(InvoiceHistoryServiceImpl.class);

    private final InvoiceHistoryRepository invoiceHistoryRepository;

    private final InvoiceHistorySearchRepository invoiceHistorySearchRepository;

    public InvoiceHistoryServiceImpl(InvoiceHistoryRepository invoiceHistoryRepository, InvoiceHistorySearchRepository invoiceHistorySearchRepository) {
        this.invoiceHistoryRepository = invoiceHistoryRepository;
        this.invoiceHistorySearchRepository = invoiceHistorySearchRepository;
    }

    /**
     * Save a invoiceHistory.
     *
     * @param invoiceHistory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoiceHistory save(InvoiceHistory invoiceHistory) {
        log.debug("Request to save InvoiceHistory : {}", invoiceHistory);
        InvoiceHistory result = invoiceHistoryRepository.save(invoiceHistory);
        invoiceHistorySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the invoiceHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceHistory> findAll() {
        log.debug("Request to get all InvoiceHistories");
        return invoiceHistoryRepository.findAll();
    }

    /**
     * Get one invoiceHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceHistory> findOne(Long id) {
        log.debug("Request to get InvoiceHistory : {}", id);
        return invoiceHistoryRepository.findById(id);
    }

    /**
     * Delete the invoiceHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceHistory : {}", id);
        invoiceHistoryRepository.deleteById(id);
        invoiceHistorySearchRepository.deleteById(id);
    }

    /**
     * Search for the invoiceHistory corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceHistory> search(String query) {
        log.debug("Request to search InvoiceHistories for query {}", query);
        return StreamSupport
            .stream(invoiceHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
