package com.tms.v1.web.rest;

import com.tms.v1.domain.AccountHistory;
import com.tms.v1.service.AccountHistoryService;
import com.tms.v1.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.tms.v1.domain.AccountHistory}.
 */
@RestController
@RequestMapping("/api")
public class AccountHistoryResource {

    private final Logger log = LoggerFactory.getLogger(AccountHistoryResource.class);

    private static final String ENTITY_NAME = "accountHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountHistoryService accountHistoryService;

    public AccountHistoryResource(AccountHistoryService accountHistoryService) {
        this.accountHistoryService = accountHistoryService;
    }

    /**
     * {@code POST  /account-histories} : Create a new accountHistory.
     *
     * @param accountHistory the accountHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountHistory, or with status {@code 400 (Bad Request)} if the accountHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-histories")
    public ResponseEntity<AccountHistory> createAccountHistory(@RequestBody AccountHistory accountHistory) throws URISyntaxException {
        log.debug("REST request to save AccountHistory : {}", accountHistory);
        if (accountHistory.getId() != null) {
            throw new BadRequestAlertException("A new accountHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountHistory result = accountHistoryService.save(accountHistory);
        return ResponseEntity.created(new URI("/api/account-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-histories} : Updates an existing accountHistory.
     *
     * @param accountHistory the accountHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountHistory,
     * or with status {@code 400 (Bad Request)} if the accountHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-histories")
    public ResponseEntity<AccountHistory> updateAccountHistory(@RequestBody AccountHistory accountHistory) throws URISyntaxException {
        log.debug("REST request to update AccountHistory : {}", accountHistory);
        if (accountHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountHistory result = accountHistoryService.save(accountHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-histories} : get all the accountHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountHistories in body.
     */
    @GetMapping("/account-histories")
    public List<AccountHistory> getAllAccountHistories() {
        log.debug("REST request to get all AccountHistories");
        return accountHistoryService.findAll();
    }

    /**
     * {@code GET  /account-histories/:id} : get the "id" accountHistory.
     *
     * @param id the id of the accountHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-histories/{id}")
    public ResponseEntity<AccountHistory> getAccountHistory(@PathVariable Long id) {
        log.debug("REST request to get AccountHistory : {}", id);
        Optional<AccountHistory> accountHistory = accountHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountHistory);
    }

    /**
     * {@code DELETE  /account-histories/:id} : delete the "id" accountHistory.
     *
     * @param id the id of the accountHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-histories/{id}")
    public ResponseEntity<Void> deleteAccountHistory(@PathVariable Long id) {
        log.debug("REST request to delete AccountHistory : {}", id);
        accountHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/account-histories?query=:query} : search for the accountHistory corresponding
     * to the query.
     *
     * @param query the query of the accountHistory search.
     * @return the result of the search.
     */
    @GetMapping("/_search/account-histories")
    public List<AccountHistory> searchAccountHistories(@RequestParam String query) {
        log.debug("REST request to search AccountHistories for query {}", query);
        return accountHistoryService.search(query);
    }
}
