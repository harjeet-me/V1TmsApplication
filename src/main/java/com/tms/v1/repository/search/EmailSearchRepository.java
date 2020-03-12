package com.tms.v1.repository.search;

import com.tms.v1.domain.Email;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Email} entity.
 */
public interface EmailSearchRepository extends ElasticsearchRepository<Email, Long> {
}
