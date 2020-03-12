package com.tms.v1.web.rest;

import com.tms.v1.TmsV1ApplicationApp;
import com.tms.v1.domain.Email;
import com.tms.v1.repository.EmailRepository;
import com.tms.v1.repository.search.EmailSearchRepository;
import com.tms.v1.service.EmailService;

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
 * Integration tests for the {@link EmailResource} REST controller.
 */
@SpringBootTest(classes = TmsV1ApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmailResourceIT {

    private static final String DEFAULT_USERTO = "AAAAAAAAAA";
    private static final String UPDATED_USERTO = "BBBBBBBBBB";

    private static final String DEFAULT_USERCC = "AAAAAAAAAA";
    private static final String UPDATED_USERCC = "BBBBBBBBBB";

    private static final String DEFAULT_USERBCC = "AAAAAAAAAA";
    private static final String UPDATED_USERBCC = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MULTIPART = false;
    private static final Boolean UPDATED_MULTIPART = true;

    private static final Boolean DEFAULT_HTML_BODY = false;
    private static final Boolean UPDATED_HTML_BODY = true;

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ATTACHMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_SENT_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SENT_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    /**
     * This repository is mocked in the com.tms.v1.repository.search test package.
     *
     * @see com.tms.v1.repository.search.EmailSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmailSearchRepository mockEmailSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailMockMvc;

    private Email email;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createEntity(EntityManager em) {
        Email email = new Email()
            .userto(DEFAULT_USERTO)
            .usercc(DEFAULT_USERCC)
            .userbcc(DEFAULT_USERBCC)
            .subject(DEFAULT_SUBJECT)
            .message(DEFAULT_MESSAGE)
            .multipart(DEFAULT_MULTIPART)
            .htmlBody(DEFAULT_HTML_BODY)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE)
            .attachmentName(DEFAULT_ATTACHMENT_NAME)
            .status(DEFAULT_STATUS)
            .sentDateTime(DEFAULT_SENT_DATE_TIME)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return email;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createUpdatedEntity(EntityManager em) {
        Email email = new Email()
            .userto(UPDATED_USERTO)
            .usercc(UPDATED_USERCC)
            .userbcc(UPDATED_USERBCC)
            .subject(UPDATED_SUBJECT)
            .message(UPDATED_MESSAGE)
            .multipart(UPDATED_MULTIPART)
            .htmlBody(UPDATED_HTML_BODY)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .attachmentName(UPDATED_ATTACHMENT_NAME)
            .status(UPDATED_STATUS)
            .sentDateTime(UPDATED_SENT_DATE_TIME)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return email;
    }

    @BeforeEach
    public void initTest() {
        email = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmail() throws Exception {
        int databaseSizeBeforeCreate = emailRepository.findAll().size();

        // Create the Email
        restEmailMockMvc.perform(post("/api/emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(email)))
            .andExpect(status().isCreated());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate + 1);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getUserto()).isEqualTo(DEFAULT_USERTO);
        assertThat(testEmail.getUsercc()).isEqualTo(DEFAULT_USERCC);
        assertThat(testEmail.getUserbcc()).isEqualTo(DEFAULT_USERBCC);
        assertThat(testEmail.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEmail.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEmail.isMultipart()).isEqualTo(DEFAULT_MULTIPART);
        assertThat(testEmail.isHtmlBody()).isEqualTo(DEFAULT_HTML_BODY);
        assertThat(testEmail.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testEmail.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testEmail.getAttachmentName()).isEqualTo(DEFAULT_ATTACHMENT_NAME);
        assertThat(testEmail.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmail.getSentDateTime()).isEqualTo(DEFAULT_SENT_DATE_TIME);
        assertThat(testEmail.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmail.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmail.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testEmail.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the Email in Elasticsearch
        verify(mockEmailSearchRepository, times(1)).save(testEmail);
    }

    @Test
    @Transactional
    public void createEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailRepository.findAll().size();

        // Create the Email with an existing ID
        email.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailMockMvc.perform(post("/api/emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(email)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate);

        // Validate the Email in Elasticsearch
        verify(mockEmailSearchRepository, times(0)).save(email);
    }


    @Test
    @Transactional
    public void getAllEmails() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList
        restEmailMockMvc.perform(get("/api/emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].userto").value(hasItem(DEFAULT_USERTO)))
            .andExpect(jsonPath("$.[*].usercc").value(hasItem(DEFAULT_USERCC)))
            .andExpect(jsonPath("$.[*].userbcc").value(hasItem(DEFAULT_USERBCC)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].multipart").value(hasItem(DEFAULT_MULTIPART.booleanValue())))
            .andExpect(jsonPath("$.[*].htmlBody").value(hasItem(DEFAULT_HTML_BODY.booleanValue())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].attachmentName").value(hasItem(DEFAULT_ATTACHMENT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sentDateTime").value(hasItem(DEFAULT_SENT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get the email
        restEmailMockMvc.perform(get("/api/emails/{id}", email.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(email.getId().intValue()))
            .andExpect(jsonPath("$.userto").value(DEFAULT_USERTO))
            .andExpect(jsonPath("$.usercc").value(DEFAULT_USERCC))
            .andExpect(jsonPath("$.userbcc").value(DEFAULT_USERBCC))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.multipart").value(DEFAULT_MULTIPART.booleanValue()))
            .andExpect(jsonPath("$.htmlBody").value(DEFAULT_HTML_BODY.booleanValue()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.attachmentName").value(DEFAULT_ATTACHMENT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.sentDateTime").value(DEFAULT_SENT_DATE_TIME.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingEmail() throws Exception {
        // Get the email
        restEmailMockMvc.perform(get("/api/emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmail() throws Exception {
        // Initialize the database
        emailService.save(email);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEmailSearchRepository);

        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Update the email
        Email updatedEmail = emailRepository.findById(email.getId()).get();
        // Disconnect from session so that the updates on updatedEmail are not directly saved in db
        em.detach(updatedEmail);
        updatedEmail
            .userto(UPDATED_USERTO)
            .usercc(UPDATED_USERCC)
            .userbcc(UPDATED_USERBCC)
            .subject(UPDATED_SUBJECT)
            .message(UPDATED_MESSAGE)
            .multipart(UPDATED_MULTIPART)
            .htmlBody(UPDATED_HTML_BODY)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .attachmentName(UPDATED_ATTACHMENT_NAME)
            .status(UPDATED_STATUS)
            .sentDateTime(UPDATED_SENT_DATE_TIME)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restEmailMockMvc.perform(put("/api/emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmail)))
            .andExpect(status().isOk());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getUserto()).isEqualTo(UPDATED_USERTO);
        assertThat(testEmail.getUsercc()).isEqualTo(UPDATED_USERCC);
        assertThat(testEmail.getUserbcc()).isEqualTo(UPDATED_USERBCC);
        assertThat(testEmail.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmail.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEmail.isMultipart()).isEqualTo(UPDATED_MULTIPART);
        assertThat(testEmail.isHtmlBody()).isEqualTo(UPDATED_HTML_BODY);
        assertThat(testEmail.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testEmail.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testEmail.getAttachmentName()).isEqualTo(UPDATED_ATTACHMENT_NAME);
        assertThat(testEmail.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmail.getSentDateTime()).isEqualTo(UPDATED_SENT_DATE_TIME);
        assertThat(testEmail.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmail.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmail.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testEmail.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the Email in Elasticsearch
        verify(mockEmailSearchRepository, times(1)).save(testEmail);
    }

    @Test
    @Transactional
    public void updateNonExistingEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Create the Email

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc.perform(put("/api/emails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(email)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Email in Elasticsearch
        verify(mockEmailSearchRepository, times(0)).save(email);
    }

    @Test
    @Transactional
    public void deleteEmail() throws Exception {
        // Initialize the database
        emailService.save(email);

        int databaseSizeBeforeDelete = emailRepository.findAll().size();

        // Delete the email
        restEmailMockMvc.perform(delete("/api/emails/{id}", email.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Email in Elasticsearch
        verify(mockEmailSearchRepository, times(1)).deleteById(email.getId());
    }

    @Test
    @Transactional
    public void searchEmail() throws Exception {
        // Initialize the database
        emailService.save(email);
        when(mockEmailSearchRepository.search(queryStringQuery("id:" + email.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(email), PageRequest.of(0, 1), 1));
        // Search the email
        restEmailMockMvc.perform(get("/api/_search/emails?query=id:" + email.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].userto").value(hasItem(DEFAULT_USERTO)))
            .andExpect(jsonPath("$.[*].usercc").value(hasItem(DEFAULT_USERCC)))
            .andExpect(jsonPath("$.[*].userbcc").value(hasItem(DEFAULT_USERBCC)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].multipart").value(hasItem(DEFAULT_MULTIPART.booleanValue())))
            .andExpect(jsonPath("$.[*].htmlBody").value(hasItem(DEFAULT_HTML_BODY.booleanValue())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].attachmentName").value(hasItem(DEFAULT_ATTACHMENT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sentDateTime").value(hasItem(DEFAULT_SENT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
}
