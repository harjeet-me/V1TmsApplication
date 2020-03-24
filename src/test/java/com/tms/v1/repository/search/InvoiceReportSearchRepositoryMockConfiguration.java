package com.tms.v1.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link InvoiceReportSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InvoiceReportSearchRepositoryMockConfiguration {

    @MockBean
    private InvoiceReportSearchRepository mockInvoiceReportSearchRepository;

}
