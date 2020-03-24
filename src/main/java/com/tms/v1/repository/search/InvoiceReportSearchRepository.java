package com.tms.v1.repository.search;

import com.tms.v1.domain.InvoiceReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InvoiceReport} entity.
 */
public interface InvoiceReportSearchRepository extends ElasticsearchRepository<InvoiceReport, Long> {
}
