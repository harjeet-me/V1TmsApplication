package com.tms.v1.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CompanyProfileSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CompanyProfileSearchRepositoryMockConfiguration {

    @MockBean
    private CompanyProfileSearchRepository mockCompanyProfileSearchRepository;

}
