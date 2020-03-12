package com.tms.v1.service.impl;

import com.tms.v1.service.FileSystemService;
import com.tms.v1.domain.FileSystem;
import com.tms.v1.repository.FileSystemRepository;
import com.tms.v1.repository.search.FileSystemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FileSystem}.
 */
@Service
@Transactional
public class FileSystemServiceImpl implements FileSystemService {

    private final Logger log = LoggerFactory.getLogger(FileSystemServiceImpl.class);

    private final FileSystemRepository fileSystemRepository;

    private final FileSystemSearchRepository fileSystemSearchRepository;

    public FileSystemServiceImpl(FileSystemRepository fileSystemRepository, FileSystemSearchRepository fileSystemSearchRepository) {
        this.fileSystemRepository = fileSystemRepository;
        this.fileSystemSearchRepository = fileSystemSearchRepository;
    }

    /**
     * Save a fileSystem.
     *
     * @param fileSystem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FileSystem save(FileSystem fileSystem) {
        log.debug("Request to save FileSystem : {}", fileSystem);
        FileSystem result = fileSystemRepository.save(fileSystem);
        fileSystemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the fileSystems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileSystem> findAll(Pageable pageable) {
        log.debug("Request to get all FileSystems");
        return fileSystemRepository.findAll(pageable);
    }

    /**
     * Get one fileSystem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FileSystem> findOne(Long id) {
        log.debug("Request to get FileSystem : {}", id);
        return fileSystemRepository.findById(id);
    }

    /**
     * Delete the fileSystem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileSystem : {}", id);
        fileSystemRepository.deleteById(id);
        fileSystemSearchRepository.deleteById(id);
    }

    /**
     * Search for the fileSystem corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileSystem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FileSystems for query {}", query);
        return fileSystemSearchRepository.search(queryStringQuery(query), pageable);    }
}
