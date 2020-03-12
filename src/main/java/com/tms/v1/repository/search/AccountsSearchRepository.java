package com.tms.v1.repository.search;

import com.tms.v1.domain.Accounts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Accounts} entity.
 */
public interface AccountsSearchRepository extends ElasticsearchRepository<Accounts, Long> {
}
