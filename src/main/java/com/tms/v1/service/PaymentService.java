package com.tms.v1.service;

import com.tms.v1.domain.Payment;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Payment}.
 */
public interface PaymentService {

    /**
     * Save a payment.
     *
     * @param payment the entity to save.
     * @return the persisted entity.
     */
    Payment save(Payment payment);

    /**
     * Get all the payments.
     *
     * @return the list of entities.
     */
    List<Payment> findAll();

    /**
     * Get the "id" payment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Payment> findOne(Long id);

    /**
     * Delete the "id" payment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the payment corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Payment> search(String query);
}
