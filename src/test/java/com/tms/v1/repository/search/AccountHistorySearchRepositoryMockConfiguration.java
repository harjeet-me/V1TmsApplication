package com.tms.v1.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AccountHistorySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AccountHistorySearchRepositoryMockConfiguration {

    @MockBean
    private AccountHistorySearchRepository mockAccountHistorySearchRepository;

}
