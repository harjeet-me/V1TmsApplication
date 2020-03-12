package com.tms.v1.service;

import com.tms.v1.domain.Carrier;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Carrier}.
 */
public interface CarrierService {

    /**
     * Save a carrier.
     *
     * @param carrier the entity to save.
     * @return the persisted entity.
     */
    Carrier save(Carrier carrier);

    /**
     * Get all the carriers.
     *
     * @return the list of entities.
     */
    List<Carrier> findAll();

    /**
     * Get the "id" carrier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Carrier> findOne(Long id);

    /**
     * Delete the "id" carrier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the carrier corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Carrier> search(String query);
}
