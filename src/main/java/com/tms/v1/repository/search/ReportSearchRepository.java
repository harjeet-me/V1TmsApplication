package com.tms.v1.repository.search;

import com.tms.v1.domain.Report;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Report} entity.
 */
public interface ReportSearchRepository extends ElasticsearchRepository<Report, Long> {
}
