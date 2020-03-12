package com.tms.v1.service;

import com.tms.v1.domain.Trip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Trip}.
 */
public interface TripService {

    /**
     * Save a trip.
     *
     * @param trip the entity to save.
     * @return the persisted entity.
     */
    Trip save(Trip trip);

    /**
     * Get all the trips.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trip> findAll(Pageable pageable);

    /**
     * Get the "id" trip.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Trip> findOne(Long id);

    /**
     * Delete the "id" trip.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the trip corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trip> search(String query, Pageable pageable);
}
