package com.tms.v1.repository.search;

import com.tms.v1.domain.TransactionsRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TransactionsRecord} entity.
 */
public interface TransactionsRecordSearchRepository extends ElasticsearchRepository<TransactionsRecord, Long> {
}
