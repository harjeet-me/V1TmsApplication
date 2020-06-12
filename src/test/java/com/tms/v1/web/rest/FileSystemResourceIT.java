package com.tms.v1.web.rest;

import com.tms.v1.TmsV1ApplicationApp;
import com.tms.v1.domain.FileSystem;
import com.tms.v1.repository.FileSystemRepository;
import com.tms.v1.repository.search.FileSystemSearchRepository;
import com.tms.v1.service.FileSystemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FileSystemResource} REST controller.
 */
@SpringBootTest(classes = TmsV1ApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FileSystemResourceIT {

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private FileSystemRepository fileSystemRepository;

    @Autowired
    private FileSystemService fileSystemService;

    /**
     * This repository is mocked in the com.tms.v1.repository.search test package.
     *
     * @see com.tms.v1.repository.search.FileSystemSearchRepositoryMockConfiguration
     */
    @Autowired
    private FileSystemSearchRepository mockFileSystemSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileSystemMockMvc;

    private FileSystem fileSystem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileSystem createEntity(EntityManager em) {
        FileSystem fileSystem = new FileSystem()
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE)
            .fileName(DEFAULT_FILE_NAME)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return fileSystem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileSystem createUpdatedEntity(EntityManager em) {
        FileSystem fileSystem = new FileSystem()
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return fileSystem;
    }

    @BeforeEach
    public void initTest() {
        fileSystem = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileSystem() throws Exception {
        int databaseSizeBeforeCreate = fileSystemRepository.findAll().size();

        // Create the FileSystem
        restFileSystemMockMvc.perform(post("/api/file-systems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileSystem)))
            .andExpect(status().isCreated());

        // Validate the FileSystem in the database
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeCreate + 1);
        FileSystem testFileSystem = fileSystemList.get(fileSystemList.size() - 1);
        assertThat(testFileSystem.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testFileSystem.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
        assertThat(testFileSystem.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileSystem.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFileSystem.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFileSystem.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testFileSystem.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);

        // Validate the FileSystem in Elasticsearch
        verify(mockFileSystemSearchRepository, times(1)).save(testFileSystem);
    }

    @Test
    @Transactional
    public void createFileSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileSystemRepository.findAll().size();

        // Create the FileSystem with an existing ID
        fileSystem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileSystemMockMvc.perform(post("/api/file-systems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileSystem)))
            .andExpect(status().isBadRequest());

        // Validate the FileSystem in the database
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeCreate);

        // Validate the FileSystem in Elasticsearch
        verify(mockFileSystemSearchRepository, times(0)).save(fileSystem);
    }


    @Test
    @Transactional
    public void getAllFileSystems() throws Exception {
        // Initialize the database
        fileSystemRepository.saveAndFlush(fileSystem);

        // Get all the fileSystemList
        restFileSystemMockMvc.perform(get("/api/file-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getFileSystem() throws Exception {
        // Initialize the database
        fileSystemRepository.saveAndFlush(fileSystem);

        // Get the fileSystem
        restFileSystemMockMvc.perform(get("/api/file-systems/{id}", fileSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileSystem.getId().intValue()))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingFileSystem() throws Exception {
        // Get the fileSystem
        restFileSystemMockMvc.perform(get("/api/file-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileSystem() throws Exception {
        // Initialize the database
        fileSystemService.save(fileSystem);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFileSystemSearchRepository);

        int databaseSizeBeforeUpdate = fileSystemRepository.findAll().size();

        // Update the fileSystem
        FileSystem updatedFileSystem = fileSystemRepository.findById(fileSystem.getId()).get();
        // Disconnect from session so that the updates on updatedFileSystem are not directly saved in db
        em.detach(updatedFileSystem);
        updatedFileSystem
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restFileSystemMockMvc.perform(put("/api/file-systems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileSystem)))
            .andExpect(status().isOk());

        // Validate the FileSystem in the database
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeUpdate);
        FileSystem testFileSystem = fileSystemList.get(fileSystemList.size() - 1);
        assertThat(testFileSystem.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testFileSystem.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
        assertThat(testFileSystem.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileSystem.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFileSystem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFileSystem.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testFileSystem.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);

        // Validate the FileSystem in Elasticsearch
        verify(mockFileSystemSearchRepository, times(1)).save(testFileSystem);
    }

    @Test
    @Transactional
    public void updateNonExistingFileSystem() throws Exception {
        int databaseSizeBeforeUpdate = fileSystemRepository.findAll().size();

        // Create the FileSystem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileSystemMockMvc.perform(put("/api/file-systems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileSystem)))
            .andExpect(status().isBadRequest());

        // Validate the FileSystem in the database
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FileSystem in Elasticsearch
        verify(mockFileSystemSearchRepository, times(0)).save(fileSystem);
    }

    @Test
    @Transactional
    public void deleteFileSystem() throws Exception {
        // Initialize the database
        fileSystemService.save(fileSystem);

        int databaseSizeBeforeDelete = fileSystemRepository.findAll().size();

        // Delete the fileSystem
        restFileSystemMockMvc.perform(delete("/api/file-systems/{id}", fileSystem.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FileSystem in Elasticsearch
        verify(mockFileSystemSearchRepository, times(1)).deleteById(fileSystem.getId());
    }

    @Test
    @Transactional
    public void searchFileSystem() throws Exception {
        // Initialize the database
        fileSystemService.save(fileSystem);
        when(mockFileSystemSearchRepository.search(queryStringQuery("id:" + fileSystem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fileSystem), PageRequest.of(0, 1), 1));
        // Search the fileSystem
        restFileSystemMockMvc.perform(get("/api/_search/file-systems?query=id:" + fileSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
}
