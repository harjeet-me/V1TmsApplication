package com.tms.v1.repository.search;

import com.tms.v1.domain.Trip;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Trip} entity.
 */
public interface TripSearchRepository extends ElasticsearchRepository<Trip, Long> {
}
