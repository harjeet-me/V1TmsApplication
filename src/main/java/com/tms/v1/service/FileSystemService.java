package com.tms.v1.service;

import com.tms.v1.domain.FileSystem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link FileSystem}.
 */
public interface FileSystemService {

    /**
     * Save a fileSystem.
     *
     * @param fileSystem the entity to save.
     * @return the persisted entity.
     */
    FileSystem save(FileSystem fileSystem);

    /**
     * Get all the fileSystems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileSystem> findAll(Pageable pageable);

    /**
     * Get the "id" fileSystem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileSystem> findOne(Long id);

    /**
     * Delete the "id" fileSystem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fileSystem corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileSystem> search(String query, Pageable pageable);
}
