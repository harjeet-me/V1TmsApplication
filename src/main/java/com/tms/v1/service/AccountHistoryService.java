package com.tms.v1.service;

import com.tms.v1.domain.AccountHistory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link AccountHistory}.
 */
public interface AccountHistoryService {

    /**
     * Save a accountHistory.
     *
     * @param accountHistory the entity to save.
     * @return the persisted entity.
     */
    AccountHistory save(AccountHistory accountHistory);

    /**
     * Get all the accountHistories.
     *
     * @return the list of entities.
     */
    List<AccountHistory> findAll();

    /**
     * Get the "id" accountHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccountHistory> findOne(Long id);

    /**
     * Delete the "id" accountHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the accountHistory corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<AccountHistory> search(String query);
}
