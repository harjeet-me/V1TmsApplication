package com.tms.v1.service.impl;

import com.tms.v1.service.AccountsService;
import com.tms.v1.domain.Accounts;
import com.tms.v1.repository.AccountsRepository;
import com.tms.v1.repository.search.AccountsSearchRepository;
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
 * Service Implementation for managing {@link Accounts}.
 */
@Service
@Transactional
public class AccountsServiceImpl implements AccountsService {

    private final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private final AccountsRepository accountsRepository;

    private final AccountsSearchRepository accountsSearchRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository, AccountsSearchRepository accountsSearchRepository) {
        this.accountsRepository = accountsRepository;
        this.accountsSearchRepository = accountsSearchRepository;
    }

    /**
     * Save a accounts.
     *
     * @param accounts the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Accounts save(Accounts accounts) {
        log.debug("Request to save Accounts : {}", accounts);
        Accounts result = accountsRepository.save(accounts);
        accountsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the accounts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Accounts> findAll() {
        log.debug("Request to get all Accounts");
        return accountsRepository.findAll();
    }

    /**
     * Get one accounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Accounts> findOne(Long id) {
        log.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id);
    }

    /**
     * Delete the accounts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
        accountsSearchRepository.deleteById(id);
    }

    /**
     * Search for the accounts corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Accounts> search(String query) {
        log.debug("Request to search Accounts for query {}", query);
        return StreamSupport
            .stream(accountsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
