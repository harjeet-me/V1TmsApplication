package com.tms.v1.service;

import com.tms.v1.domain.Accounts;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Accounts}.
 */
public interface AccountsService {

    /**
     * Save a accounts.
     *
     * @param accounts the entity to save.
     * @return the persisted entity.
     */
    Accounts save(Accounts accounts);

    /**
     * Get all the accounts.
     *
     * @return the list of entities.
     */
    List<Accounts> findAll();

    /**
     * Get the "id" accounts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Accounts> findOne(Long id);

    /**
     * Delete the "id" accounts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the accounts corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Accounts> search(String query);
}
