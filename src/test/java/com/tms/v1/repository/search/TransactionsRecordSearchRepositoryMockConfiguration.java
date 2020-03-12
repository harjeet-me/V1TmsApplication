package com.tms.v1.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TransactionsRecordSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TransactionsRecordSearchRepositoryMockConfiguration {

    @MockBean
    private TransactionsRecordSearchRepository mockTransactionsRecordSearchRepository;

}
