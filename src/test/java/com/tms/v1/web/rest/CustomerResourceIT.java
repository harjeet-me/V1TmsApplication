package com.tms.v1.web.rest;

import com.tms.v1.TmsV1ApplicationApp;
import com.tms.v1.domain.Customer;
import com.tms.v1.repository.CustomerRepository;
import com.tms.v1.repository.search.CustomerSearchRepository;
import com.tms.v1.service.CustomerService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tms.v1.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tms.v1.domain.enumeration.Designation;
import com.tms.v1.domain.enumeration.PreffredContactType;
import com.tms.v1.domain.enumeration.CountryEnum;
import com.tms.v1.domain.enumeration.ToggleStatus;
import com.tms.v1.domain.enumeration.CURRENCY;
/**
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = TmsV1ApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerResourceIT {

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Designation DEFAULT_CONTACT_DESIGNATION = Designation.MANAGER;
    private static final Designation UPDATED_CONTACT_DESIGNATION = Designation.ACCOUNTANT;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE_NUMBER = 1L;
    private static final Long UPDATED_PHONE_NUMBER = 2L;

    private static final Long DEFAULT_PHONE_NUMBER_EXTENTION = 1L;
    private static final Long UPDATED_PHONE_NUMBER_EXTENTION = 2L;

    private static final PreffredContactType DEFAULT_PREFFRED_CONTACT_TYPE = PreffredContactType.PHONE;
    private static final PreffredContactType UPDATED_PREFFRED_CONTACT_TYPE = PreffredContactType.EMAIL;

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_ALTERNATE_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_ALTERNATE_CONTACT_PERSON = "BBBBBBBBBB";

    private static final Long DEFAULT_ALTERNATE_CONTACT_NUMBER = 1L;
    private static final Long UPDATED_ALTERNATE_CONTACT_NUMBER = 2L;

    private static final Long DEFAULT_ALTERNATE_PHONE_NUMBER_EXTENTION = 1L;
    private static final Long UPDATED_ALTERNATE_PHONE_NUMBER_EXTENTION = 2L;

    private static final String DEFAULT_ALTERNATE_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ALTERNATE_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_PREFERRED_CONTACT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PREFERRED_CONTACT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_FAX = 1L;
    private static final Long UPDATED_FAX = 2L;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final CountryEnum DEFAULT_COUNTRY = CountryEnum.USA;
    private static final CountryEnum UPDATED_COUNTRY = CountryEnum.CANADA;

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DOT = "AAAAAAAAAA";
    private static final String UPDATED_DOT = "BBBBBBBBBB";

    private static final Long DEFAULT_MC = 1L;
    private static final Long UPDATED_MC = 2L;

    private static final String DEFAULT_TAX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMPANY_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMPANY_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMPANY_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMPANY_LOGO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CUSTOMER_SINCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CUSTOMER_SINCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTRACT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTRACT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTRACT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTRACT_CONTENT_TYPE = "image/png";

    private static final ToggleStatus DEFAULT_STATUS = ToggleStatus.ACTIVE;
    private static final ToggleStatus UPDATED_STATUS = ToggleStatus.INACTIVE;

    private static final CURRENCY DEFAULT_PREFFRED_CURRENCY = CURRENCY.USD;
    private static final CURRENCY UPDATED_PREFFRED_CURRENCY = CURRENCY.CAD;

    private static final String DEFAULT_PAYTERMS = "AAAAAAAAAA";
    private static final String UPDATED_PAYTERMS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME_ZONE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_ZONE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerRepository;

    @Mock
    private CustomerRepository customerRepositoryMock;

    @Mock
    private CustomerService customerServiceMock;

    @Autowired
    private CustomerService customerService;

    /**
     * This repository is mocked in the com.tms.v1.repository.search test package.
     *
     * @see com.tms.v1.repository.search.CustomerSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomerSearchRepository mockCustomerSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .company(DEFAULT_COMPANY)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .contactDesignation(DEFAULT_CONTACT_DESIGNATION)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .phoneNumberExtention(DEFAULT_PHONE_NUMBER_EXTENTION)
            .preffredContactType(DEFAULT_PREFFRED_CONTACT_TYPE)
            .website(DEFAULT_WEBSITE)
            .alternateContactPerson(DEFAULT_ALTERNATE_CONTACT_PERSON)
            .alternateContactNumber(DEFAULT_ALTERNATE_CONTACT_NUMBER)
            .alternatePhoneNumberExtention(DEFAULT_ALTERNATE_PHONE_NUMBER_EXTENTION)
            .alternateContactEmail(DEFAULT_ALTERNATE_CONTACT_EMAIL)
            .preferredContactTime(DEFAULT_PREFERRED_CONTACT_TIME)
            .fax(DEFAULT_FAX)
            .address(DEFAULT_ADDRESS)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .dot(DEFAULT_DOT)
            .mc(DEFAULT_MC)
            .taxId(DEFAULT_TAX_ID)
            .companyLogo(DEFAULT_COMPANY_LOGO)
            .companyLogoContentType(DEFAULT_COMPANY_LOGO_CONTENT_TYPE)
            .customerSince(DEFAULT_CUSTOMER_SINCE)
            .notes(DEFAULT_NOTES)
            .contract(DEFAULT_CONTRACT)
            .contractContentType(DEFAULT_CONTRACT_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .preffredCurrency(DEFAULT_PREFFRED_CURRENCY)
            .payterms(DEFAULT_PAYTERMS)
            .timeZone(DEFAULT_TIME_ZONE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .company(UPDATED_COMPANY)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .contactDesignation(UPDATED_CONTACT_DESIGNATION)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .phoneNumberExtention(UPDATED_PHONE_NUMBER_EXTENTION)
            .preffredContactType(UPDATED_PREFFRED_CONTACT_TYPE)
            .website(UPDATED_WEBSITE)
            .alternateContactPerson(UPDATED_ALTERNATE_CONTACT_PERSON)
            .alternateContactNumber(UPDATED_ALTERNATE_CONTACT_NUMBER)
            .alternatePhoneNumberExtention(UPDATED_ALTERNATE_PHONE_NUMBER_EXTENTION)
            .alternateContactEmail(UPDATED_ALTERNATE_CONTACT_EMAIL)
            .preferredContactTime(UPDATED_PREFERRED_CONTACT_TIME)
            .fax(UPDATED_FAX)
            .address(UPDATED_ADDRESS)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postalCode(UPDATED_POSTAL_CODE)
            .dot(UPDATED_DOT)
            .mc(UPDATED_MC)
            .taxId(UPDATED_TAX_ID)
            .companyLogo(UPDATED_COMPANY_LOGO)
            .companyLogoContentType(UPDATED_COMPANY_LOGO_CONTENT_TYPE)
            .customerSince(UPDATED_CUSTOMER_SINCE)
            .notes(UPDATED_NOTES)
            .contract(UPDATED_CONTRACT)
            .contractContentType(UPDATED_CONTRACT_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .preffredCurrency(UPDATED_PREFFRED_CURRENCY)
            .payterms(UPDATED_PAYTERMS)
            .timeZone(UPDATED_TIME_ZONE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testCustomer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomer.getContactDesignation()).isEqualTo(DEFAULT_CONTACT_DESIGNATION);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCustomer.getPhoneNumberExtention()).isEqualTo(DEFAULT_PHONE_NUMBER_EXTENTION);
        assertThat(testCustomer.getPreffredContactType()).isEqualTo(DEFAULT_PREFFRED_CONTACT_TYPE);
        assertThat(testCustomer.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testCustomer.getAlternateContactPerson()).isEqualTo(DEFAULT_ALTERNATE_CONTACT_PERSON);
        assertThat(testCustomer.getAlternateContactNumber()).isEqualTo(DEFAULT_ALTERNATE_CONTACT_NUMBER);
        assertThat(testCustomer.getAlternatePhoneNumberExtention()).isEqualTo(DEFAULT_ALTERNATE_PHONE_NUMBER_EXTENTION);
        assertThat(testCustomer.getAlternateContactEmail()).isEqualTo(DEFAULT_ALTERNATE_CONTACT_EMAIL);
        assertThat(testCustomer.getPreferredContactTime()).isEqualTo(DEFAULT_PREFERRED_CONTACT_TIME);
        assertThat(testCustomer.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCustomer.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testCustomer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomer.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testCustomer.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCustomer.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCustomer.getDot()).isEqualTo(DEFAULT_DOT);
        assertThat(testCustomer.getMc()).isEqualTo(DEFAULT_MC);
        assertThat(testCustomer.getTaxId()).isEqualTo(DEFAULT_TAX_ID);
        assertThat(testCustomer.getCompanyLogo()).isEqualTo(DEFAULT_COMPANY_LOGO);
        assertThat(testCustomer.getCompanyLogoContentType()).isEqualTo(DEFAULT_COMPANY_LOGO_CONTENT_TYPE);
        assertThat(testCustomer.getCustomerSince()).isEqualTo(DEFAULT_CUSTOMER_SINCE);
        assertThat(testCustomer.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCustomer.getContract()).isEqualTo(DEFAULT_CONTRACT);
        assertThat(testCustomer.getContractContentType()).isEqualTo(DEFAULT_CONTRACT_CONTENT_TYPE);
        assertThat(testCustomer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomer.getPreffredCurrency()).isEqualTo(DEFAULT_PREFFRED_CURRENCY);
        assertThat(testCustomer.getPayterms()).isEqualTo(DEFAULT_PAYTERMS);
        assertThat(testCustomer.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);
        assertThat(testCustomer.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCustomer.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCustomer.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCustomer.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(1)).save(testCustomer);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(0)).save(customer);
    }


    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].contactDesignation").value(hasItem(DEFAULT_CONTACT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].phoneNumberExtention").value(hasItem(DEFAULT_PHONE_NUMBER_EXTENTION.intValue())))
            .andExpect(jsonPath("$.[*].preffredContactType").value(hasItem(DEFAULT_PREFFRED_CONTACT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].alternateContactPerson").value(hasItem(DEFAULT_ALTERNATE_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].alternateContactNumber").value(hasItem(DEFAULT_ALTERNATE_CONTACT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].alternatePhoneNumberExtention").value(hasItem(DEFAULT_ALTERNATE_PHONE_NUMBER_EXTENTION.intValue())))
            .andExpect(jsonPath("$.[*].alternateContactEmail").value(hasItem(DEFAULT_ALTERNATE_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].preferredContactTime").value(hasItem(DEFAULT_PREFERRED_CONTACT_TIME.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].dot").value(hasItem(DEFAULT_DOT)))
            .andExpect(jsonPath("$.[*].mc").value(hasItem(DEFAULT_MC.intValue())))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)))
            .andExpect(jsonPath("$.[*].companyLogoContentType").value(hasItem(DEFAULT_COMPANY_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].companyLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPANY_LOGO))))
            .andExpect(jsonPath("$.[*].customerSince").value(hasItem(DEFAULT_CUSTOMER_SINCE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].contractContentType").value(hasItem(DEFAULT_CONTRACT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contract").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTRACT))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].preffredCurrency").value(hasItem(DEFAULT_PREFFRED_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].payterms").value(hasItem(DEFAULT_PAYTERMS)))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(sameInstant(DEFAULT_TIME_ZONE))))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCustomersWithEagerRelationshipsIsEnabled() throws Exception {
        when(customerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCustomerMockMvc.perform(get("/api/customers?eagerload=true"))
            .andExpect(status().isOk());

        verify(customerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCustomersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(customerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCustomerMockMvc.perform(get("/api/customers?eagerload=true"))
            .andExpect(status().isOk());

        verify(customerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.contactDesignation").value(DEFAULT_CONTACT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()))
            .andExpect(jsonPath("$.phoneNumberExtention").value(DEFAULT_PHONE_NUMBER_EXTENTION.intValue()))
            .andExpect(jsonPath("$.preffredContactType").value(DEFAULT_PREFFRED_CONTACT_TYPE.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.alternateContactPerson").value(DEFAULT_ALTERNATE_CONTACT_PERSON))
            .andExpect(jsonPath("$.alternateContactNumber").value(DEFAULT_ALTERNATE_CONTACT_NUMBER.intValue()))
            .andExpect(jsonPath("$.alternatePhoneNumberExtention").value(DEFAULT_ALTERNATE_PHONE_NUMBER_EXTENTION.intValue()))
            .andExpect(jsonPath("$.alternateContactEmail").value(DEFAULT_ALTERNATE_CONTACT_EMAIL))
            .andExpect(jsonPath("$.preferredContactTime").value(DEFAULT_PREFERRED_CONTACT_TIME.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.dot").value(DEFAULT_DOT))
            .andExpect(jsonPath("$.mc").value(DEFAULT_MC.intValue()))
            .andExpect(jsonPath("$.taxId").value(DEFAULT_TAX_ID))
            .andExpect(jsonPath("$.companyLogoContentType").value(DEFAULT_COMPANY_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.companyLogo").value(Base64Utils.encodeToString(DEFAULT_COMPANY_LOGO)))
            .andExpect(jsonPath("$.customerSince").value(DEFAULT_CUSTOMER_SINCE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.contractContentType").value(DEFAULT_CONTRACT_CONTENT_TYPE))
            .andExpect(jsonPath("$.contract").value(Base64Utils.encodeToString(DEFAULT_CONTRACT)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.preffredCurrency").value(DEFAULT_PREFFRED_CURRENCY.toString()))
            .andExpect(jsonPath("$.payterms").value(DEFAULT_PAYTERMS))
            .andExpect(jsonPath("$.timeZone").value(sameInstant(DEFAULT_TIME_ZONE)))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCustomerSearchRepository);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .company(UPDATED_COMPANY)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .contactDesignation(UPDATED_CONTACT_DESIGNATION)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .phoneNumberExtention(UPDATED_PHONE_NUMBER_EXTENTION)
            .preffredContactType(UPDATED_PREFFRED_CONTACT_TYPE)
            .website(UPDATED_WEBSITE)
            .alternateContactPerson(UPDATED_ALTERNATE_CONTACT_PERSON)
            .alternateContactNumber(UPDATED_ALTERNATE_CONTACT_NUMBER)
            .alternatePhoneNumberExtention(UPDATED_ALTERNATE_PHONE_NUMBER_EXTENTION)
            .alternateContactEmail(UPDATED_ALTERNATE_CONTACT_EMAIL)
            .preferredContactTime(UPDATED_PREFERRED_CONTACT_TIME)
            .fax(UPDATED_FAX)
            .address(UPDATED_ADDRESS)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postalCode(UPDATED_POSTAL_CODE)
            .dot(UPDATED_DOT)
            .mc(UPDATED_MC)
            .taxId(UPDATED_TAX_ID)
            .companyLogo(UPDATED_COMPANY_LOGO)
            .companyLogoContentType(UPDATED_COMPANY_LOGO_CONTENT_TYPE)
            .customerSince(UPDATED_CUSTOMER_SINCE)
            .notes(UPDATED_NOTES)
            .contract(UPDATED_CONTRACT)
            .contractContentType(UPDATED_CONTRACT_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .preffredCurrency(UPDATED_PREFFRED_CURRENCY)
            .payterms(UPDATED_PAYTERMS)
            .timeZone(UPDATED_TIME_ZONE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restCustomerMockMvc.perform(put("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testCustomer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomer.getContactDesignation()).isEqualTo(UPDATED_CONTACT_DESIGNATION);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustomer.getPhoneNumberExtention()).isEqualTo(UPDATED_PHONE_NUMBER_EXTENTION);
        assertThat(testCustomer.getPreffredContactType()).isEqualTo(UPDATED_PREFFRED_CONTACT_TYPE);
        assertThat(testCustomer.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testCustomer.getAlternateContactPerson()).isEqualTo(UPDATED_ALTERNATE_CONTACT_PERSON);
        assertThat(testCustomer.getAlternateContactNumber()).isEqualTo(UPDATED_ALTERNATE_CONTACT_NUMBER);
        assertThat(testCustomer.getAlternatePhoneNumberExtention()).isEqualTo(UPDATED_ALTERNATE_PHONE_NUMBER_EXTENTION);
        assertThat(testCustomer.getAlternateContactEmail()).isEqualTo(UPDATED_ALTERNATE_CONTACT_EMAIL);
        assertThat(testCustomer.getPreferredContactTime()).isEqualTo(UPDATED_PREFERRED_CONTACT_TIME);
        assertThat(testCustomer.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCustomer.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testCustomer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomer.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testCustomer.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCustomer.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCustomer.getDot()).isEqualTo(UPDATED_DOT);
        assertThat(testCustomer.getMc()).isEqualTo(UPDATED_MC);
        assertThat(testCustomer.getTaxId()).isEqualTo(UPDATED_TAX_ID);
        assertThat(testCustomer.getCompanyLogo()).isEqualTo(UPDATED_COMPANY_LOGO);
        assertThat(testCustomer.getCompanyLogoContentType()).isEqualTo(UPDATED_COMPANY_LOGO_CONTENT_TYPE);
        assertThat(testCustomer.getCustomerSince()).isEqualTo(UPDATED_CUSTOMER_SINCE);
        assertThat(testCustomer.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomer.getContract()).isEqualTo(UPDATED_CONTRACT);
        assertThat(testCustomer.getContractContentType()).isEqualTo(UPDATED_CONTRACT_CONTENT_TYPE);
        assertThat(testCustomer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomer.getPreffredCurrency()).isEqualTo(UPDATED_PREFFRED_CURRENCY);
        assertThat(testCustomer.getPayterms()).isEqualTo(UPDATED_PAYTERMS);
        assertThat(testCustomer.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);
        assertThat(testCustomer.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCustomer.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomer.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCustomer.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(1)).save(testCustomer);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(0)).save(customer);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(1)).deleteById(customer.getId());
    }

    @Test
    @Transactional
    public void searchCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);
        when(mockCustomerSearchRepository.search(queryStringQuery("id:" + customer.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(customer), PageRequest.of(0, 1), 1));
        // Search the customer
        restCustomerMockMvc.perform(get("/api/_search/customers?query=id:" + customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].contactDesignation").value(hasItem(DEFAULT_CONTACT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].phoneNumberExtention").value(hasItem(DEFAULT_PHONE_NUMBER_EXTENTION.intValue())))
            .andExpect(jsonPath("$.[*].preffredContactType").value(hasItem(DEFAULT_PREFFRED_CONTACT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].alternateContactPerson").value(hasItem(DEFAULT_ALTERNATE_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].alternateContactNumber").value(hasItem(DEFAULT_ALTERNATE_CONTACT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].alternatePhoneNumberExtention").value(hasItem(DEFAULT_ALTERNATE_PHONE_NUMBER_EXTENTION.intValue())))
            .andExpect(jsonPath("$.[*].alternateContactEmail").value(hasItem(DEFAULT_ALTERNATE_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].preferredContactTime").value(hasItem(DEFAULT_PREFERRED_CONTACT_TIME.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].dot").value(hasItem(DEFAULT_DOT)))
            .andExpect(jsonPath("$.[*].mc").value(hasItem(DEFAULT_MC.intValue())))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID)))
            .andExpect(jsonPath("$.[*].companyLogoContentType").value(hasItem(DEFAULT_COMPANY_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].companyLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPANY_LOGO))))
            .andExpect(jsonPath("$.[*].customerSince").value(hasItem(DEFAULT_CUSTOMER_SINCE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].contractContentType").value(hasItem(DEFAULT_CONTRACT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contract").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTRACT))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].preffredCurrency").value(hasItem(DEFAULT_PREFFRED_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].payterms").value(hasItem(DEFAULT_PAYTERMS)))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(sameInstant(DEFAULT_TIME_ZONE))))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
}
