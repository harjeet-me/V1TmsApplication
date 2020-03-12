package com.tms.v1.service.impl;

import com.tms.v1.service.AccountHistoryService;
import com.tms.v1.domain.AccountHistory;
import com.tms.v1.repository.AccountHistoryRepository;
import com.tms.v1.repository.search.AccountHistorySearchRepository;
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
 * Service Implementation for managing {@link AccountHistory}.
 */
@Service
@Transactional
public class AccountHistoryServiceImpl implements AccountHistoryService {

    private final Logger log = LoggerFactory.getLogger(AccountHistoryServiceImpl.class);

    private final AccountHistoryRepository accountHistoryRepository;

    private final AccountHistorySearchRepository accountHistorySearchRepository;

    public AccountHistoryServiceImpl(AccountHistoryRepository accountHistoryRepository, AccountHistorySearchRepository accountHistorySearchRepository) {
        this.accountHistoryRepository = accountHistoryRepository;
        this.accountHistorySearchRepository = accountHistorySearchRepository;
    }

    /**
     * Save a accountHistory.
     *
     * @param accountHistory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountHistory save(AccountHistory accountHistory) {
        log.debug("Request to save AccountHistory : {}", accountHistory);
        AccountHistory result = accountHistoryRepository.save(accountHistory);
        accountHistorySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the accountHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountHistory> findAll() {
        log.debug("Request to get all AccountHistories");
        return accountHistoryRepository.findAll();
    }

    /**
     * Get one accountHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountHistory> findOne(Long id) {
        log.debug("Request to get AccountHistory : {}", id);
        return accountHistoryRepository.findById(id);
    }

    /**
     * Delete the accountHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountHistory : {}", id);
        accountHistoryRepository.deleteById(id);
        accountHistorySearchRepository.deleteById(id);
    }

    /**
     * Search for the accountHistory corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountHistory> search(String query) {
        log.debug("Request to search AccountHistories for query {}", query);
        return StreamSupport
            .stream(accountHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
