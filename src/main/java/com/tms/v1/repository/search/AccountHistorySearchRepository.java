package com.tms.v1.repository.search;

import com.tms.v1.domain.AccountHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AccountHistory} entity.
 */
public interface AccountHistorySearchRepository extends ElasticsearchRepository<AccountHistory, Long> {
}
