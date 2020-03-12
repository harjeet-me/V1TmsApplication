package com.tms.v1.service.impl;

import com.tms.v1.service.EmailService;
import com.tms.v1.domain.Email;
import com.tms.v1.repository.EmailRepository;
import com.tms.v1.repository.search.EmailSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Email}.
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailRepository emailRepository;

    private final EmailSearchRepository emailSearchRepository;

    public EmailServiceImpl(EmailRepository emailRepository, EmailSearchRepository emailSearchRepository) {
        this.emailRepository = emailRepository;
        this.emailSearchRepository = emailSearchRepository;
    }

    /**
     * Save a email.
     *
     * @param email the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Email save(Email email) {
        log.debug("Request to save Email : {}", email);
        Email result = emailRepository.save(email);
        emailSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the emails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Email> findAll(Pageable pageable) {
        log.debug("Request to get all Emails");
        return emailRepository.findAll(pageable);
    }

    /**
     * Get one email by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Email> findOne(Long id) {
        log.debug("Request to get Email : {}", id);
        return emailRepository.findById(id);
    }

    /**
     * Delete the email by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Email : {}", id);
        emailRepository.deleteById(id);
        emailSearchRepository.deleteById(id);
    }

    /**
     * Search for the email corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Email> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Emails for query {}", query);
        return emailSearchRepository.search(queryStringQuery(query), pageable);    }
}
