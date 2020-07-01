package com.tms.v1.service;

import com.tms.v1.domain.Container;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Interface for managing {@link Container}.
 */
public interface ContainerService {

    /**
     * Save a container.
     *
     * @param container the entity to save.
     * @return the persisted entity.
     */
    Container save(Container container);

    /**
     * Get all the containers.
     *
     * @return the list of entities.
     */
    List<Container> findAll();

    /**
     * Get the "id" container.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Container> findOne(Long id);

    /**
     * Delete the "id" container.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the container corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Container> search(String query);

	Set<Container> findByTripId(Long tripId);
}
