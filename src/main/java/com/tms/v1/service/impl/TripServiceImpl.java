package com.tms.v1.service.impl;

import com.tms.v1.service.TripService;
import com.tms.v1.domain.Trip;
import com.tms.v1.repository.TripRepository;
import com.tms.v1.repository.search.TripSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Trip}.
 */
@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final Logger log = LoggerFactory.getLogger(TripServiceImpl.class);

    private final TripRepository tripRepository;

    private final TripSearchRepository tripSearchRepository;

    public TripServiceImpl(TripRepository tripRepository, TripSearchRepository tripSearchRepository) {
        this.tripRepository = tripRepository;
        this.tripSearchRepository = tripSearchRepository;
    }

    /**
     * Save a trip.
     *
     * @param trip the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Trip save(Trip trip) {
        log.debug("Request to save Trip : {}", trip);
        Trip result = tripRepository.save(trip);
        tripSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the trips.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trip> findAll(Pageable pageable) {
        log.debug("Request to get all Trips");
        return tripRepository.findAll(pageable);
    }

    /**
     * Get one trip by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Trip> findOne(Long id) {
        log.debug("Request to get Trip : {}", id);
        return tripRepository.findById(id);
    }

    /**
     * Delete the trip by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trip : {}", id);
        tripRepository.deleteById(id);
        tripSearchRepository.deleteById(id);
    }

    /**
     * Search for the trip corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trip> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Trips for query {}", query);
        return tripSearchRepository.search(queryStringQuery(query), pageable);    }
}
