package com.tms.v1.repository.search;

import com.tms.v1.domain.FileSystem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FileSystem} entity.
 */
public interface FileSystemSearchRepository extends ElasticsearchRepository<FileSystem, Long> {
}
