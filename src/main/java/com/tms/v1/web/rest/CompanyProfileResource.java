package com.tms.v1.web.rest;

import com.tms.v1.domain.CompanyProfile;
import com.tms.v1.service.CompanyProfileService;
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
 * REST controller for managing {@link com.tms.v1.domain.CompanyProfile}.
 */
@RestController
@RequestMapping("/api")
public class CompanyProfileResource {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileResource.class);

    private static final String ENTITY_NAME = "companyProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyProfileService companyProfileService;

    public CompanyProfileResource(CompanyProfileService companyProfileService) {
        this.companyProfileService = companyProfileService;
    }

    /**
     * {@code POST  /company-profiles} : Create a new companyProfile.
     *
     * @param companyProfile the companyProfile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyProfile, or with status {@code 400 (Bad Request)} if the companyProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-profiles")
    public ResponseEntity<CompanyProfile> createCompanyProfile(@RequestBody CompanyProfile companyProfile) throws URISyntaxException {
        log.debug("REST request to save CompanyProfile : {}", companyProfile);
        if (companyProfile.getId() != null) {
            throw new BadRequestAlertException("A new companyProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyProfile result = companyProfileService.save(companyProfile);
        return ResponseEntity.created(new URI("/api/company-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-profiles} : Updates an existing companyProfile.
     *
     * @param companyProfile the companyProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyProfile,
     * or with status {@code 400 (Bad Request)} if the companyProfile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-profiles")
    public ResponseEntity<CompanyProfile> updateCompanyProfile(@RequestBody CompanyProfile companyProfile) throws URISyntaxException {
        log.debug("REST request to update CompanyProfile : {}", companyProfile);
        if (companyProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyProfile result = companyProfileService.save(companyProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyProfile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-profiles} : get all the companyProfiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyProfiles in body.
     */
    @GetMapping("/company-profiles")
    public List<CompanyProfile> getAllCompanyProfiles() {
        log.debug("REST request to get all CompanyProfiles");
        return companyProfileService.findAll();
    }

    /**
     * {@code GET  /company-profiles/:id} : get the "id" companyProfile.
     *
     * @param id the id of the companyProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyProfile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-profiles/{id}")
    public ResponseEntity<CompanyProfile> getCompanyProfile(@PathVariable Long id) {
        log.debug("REST request to get CompanyProfile : {}", id);
        Optional<CompanyProfile> companyProfile = companyProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyProfile);
    }

    /**
     * {@code DELETE  /company-profiles/:id} : delete the "id" companyProfile.
     *
     * @param id the id of the companyProfile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-profiles/{id}")
    public ResponseEntity<Void> deleteCompanyProfile(@PathVariable Long id) {
        log.debug("REST request to delete CompanyProfile : {}", id);
        companyProfileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/company-profiles?query=:query} : search for the companyProfile corresponding
     * to the query.
     *
     * @param query the query of the companyProfile search.
     * @return the result of the search.
     */
    @GetMapping("/_search/company-profiles")
    public List<CompanyProfile> searchCompanyProfiles(@RequestParam String query) {
        log.debug("REST request to search CompanyProfiles for query {}", query);
        return companyProfileService.search(query);
    }
}
