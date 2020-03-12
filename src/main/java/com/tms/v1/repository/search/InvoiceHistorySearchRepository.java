package com.tms.v1.repository.search;

import com.tms.v1.domain.InvoiceHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InvoiceHistory} entity.
 */
public interface InvoiceHistorySearchRepository extends ElasticsearchRepository<InvoiceHistory, Long> {
}
