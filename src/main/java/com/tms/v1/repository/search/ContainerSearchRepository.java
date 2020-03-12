package com.tms.v1.repository.search;

import com.tms.v1.domain.Container;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Container} entity.
 */
public interface ContainerSearchRepository extends ElasticsearchRepository<Container, Long> {
}
