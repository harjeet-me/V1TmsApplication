package com.tms.v1.repository.search;

import com.tms.v1.domain.Equipment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Equipment} entity.
 */
public interface EquipmentSearchRepository extends ElasticsearchRepository<Equipment, Long> {
}
