package com.tms.v1.repository.search;

import com.tms.v1.domain.CompanyProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CompanyProfile} entity.
 */
public interface CompanyProfileSearchRepository extends ElasticsearchRepository<CompanyProfile, Long> {
}
