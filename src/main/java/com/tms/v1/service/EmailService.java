package com.tms.v1.service;

import com.tms.v1.domain.Email;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Interface for managing {@link Email}.
 */
public interface EmailService {

    /**
     * Save a email.
     *
     * @param email the entity to save.
     * @return the persisted entity.
     */
    Email save(Email email);

    /**
     * Get all the emails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Email> findAll(Pageable pageable);

    /**
     * Get the "id" email.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Email> findOne(Long id);

    /**
     * Delete the "id" email.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the email corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Email> search(String query, Pageable pageable);

	Set<Email> findByCustomerId(Long id);
}
