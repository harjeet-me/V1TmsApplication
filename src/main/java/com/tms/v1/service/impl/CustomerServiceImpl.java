package com.tms.v1.service.impl;

import com.tms.v1.service.CustomerService;
import com.tms.v1.domain.Customer;
import com.tms.v1.repository.CustomerRepository;
import com.tms.v1.repository.search.CustomerSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    private final CustomerSearchRepository customerSearchRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerSearchRepository customerSearchRepository) {
        this.customerRepository = customerRepository;
        this.customerSearchRepository = customerSearchRepository;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        Customer result = customerRepository.save(customer);
        customerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable);
    }

    /**
     * Get all the customers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Customer> findAllWithEagerRelationships(Pageable pageable) {
        return customerRepository.findAllWithEagerRelationships(pageable);
    }


    /**
     *  Get all the customers where Accounts is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Customer> findAllWhereAccountsIsNull() {
        log.debug("Request to get all customers where Accounts is null");
        return StreamSupport
            .stream(customerRepository.findAll().spliterator(), false)
            .filter(customer -> customer.getAccounts() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the customer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
        customerSearchRepository.deleteById(id);
    }

    /**
     * Search for the customer corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Customers for query {}", query);
        return customerSearchRepository.search(queryStringQuery(query), pageable);    }
}
