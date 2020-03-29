package com.tms.v1.web.rest;

import com.tms.v1.TmsV1ApplicationApp;
import com.tms.v1.domain.Invoice;
import com.tms.v1.repository.InvoiceRepository;
import com.tms.v1.repository.search.InvoiceSearchRepository;
import com.tms.v1.service.InvoiceService;

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
import java.time.ZoneId;
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

import com.tms.v1.domain.enumeration.TaxType;
import com.tms.v1.domain.enumeration.CURRENCY;
import com.tms.v1.domain.enumeration.InvoiveRef;
import com.tms.v1.domain.enumeration.InvoiveRef;
import com.tms.v1.domain.enumeration.InvoiveRef;
import com.tms.v1.domain.enumeration.InvoiceStatus;
/**
 * Integration tests for the {@link InvoiceResource} REST controller.
 */
@SpringBootTest(classes = TmsV1ApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceResourceIT {

    private static final String DEFAULT_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_TAX_RATE = 1D;
    private static final Double UPDATED_TAX_RATE = 2D;

    private static final TaxType DEFAULT_TAX_TYPE = TaxType.GST;
    private static final TaxType UPDATED_TAX_TYPE = TaxType.FEDRAL;

    private static final CURRENCY DEFAULT_CURRENCY = CURRENCY.USD;
    private static final CURRENCY UPDATED_CURRENCY = CURRENCY.CAD;

    private static final Double DEFAULT_INVOICE_TAX_TOTAL = 1D;
    private static final Double UPDATED_INVOICE_TAX_TOTAL = 2D;

    private static final Double DEFAULT_INVOICE_SUB_TOTAL = 1D;
    private static final Double UPDATED_INVOICE_SUB_TOTAL = 2D;

    private static final Double DEFAULT_INVOICE_TOTAL = 1D;
    private static final Double UPDATED_INVOICE_TOTAL = 2D;

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_INVOICE_PAID_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_PAID_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final InvoiveRef DEFAULT_REF_OPTION_1 = InvoiveRef.ORDER_NO;
    private static final InvoiveRef UPDATED_REF_OPTION_1 = InvoiveRef.SHIPMENT_NO;

    private static final String DEFAULT_REF_VALUE_1 = "AAAAAAAAAA";
    private static final String UPDATED_REF_VALUE_1 = "BBBBBBBBBB";

    private static final InvoiveRef DEFAULT_REF_OPTION_2 = InvoiveRef.ORDER_NO;
    private static final InvoiveRef UPDATED_REF_OPTION_2 = InvoiveRef.SHIPMENT_NO;

    private static final String DEFAULT_REF_VALUE_2 = "AAAAAAAAAA";
    private static final String UPDATED_REF_VALUE_2 = "BBBBBBBBBB";

    private static final InvoiveRef DEFAULT_REF_OPTION_3 = InvoiveRef.ORDER_NO;
    private static final InvoiveRef UPDATED_REF_OPTION_3 = InvoiveRef.SHIPMENT_NO;

    private static final String DEFAULT_REF_VALUE_3 = "AAAAAAAAAA";
    private static final String UPDATED_REF_VALUE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_PAY_REF_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INVOICE_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final InvoiceStatus DEFAULT_STATUS = InvoiceStatus.DRAFT;
    private static final InvoiceStatus UPDATED_STATUS = InvoiceStatus.GENERATED;

    private static final byte[] DEFAULT_INVOICE_PDF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_INVOICE_PDF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_INVOICE_PDF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_INVOICE_PDF_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_INFO = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_PAYTERM = "AAAAAAAAAA";
    private static final String UPDATED_PAYTERM = "BBBBBBBBBB";

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final Double DEFAULT_ADVANCE = 1D;
    private static final Double UPDATED_ADVANCE = 2D;

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    /**
     * This repository is mocked in the com.tms.v1.repository.search test package.
     *
     * @see com.tms.v1.repository.search.InvoiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvoiceSearchRepository mockInvoiceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .orderNo(DEFAULT_ORDER_NO)
            .invoiceNo(DEFAULT_INVOICE_NO)
            .taxRate(DEFAULT_TAX_RATE)
            .taxType(DEFAULT_TAX_TYPE)
            .currency(DEFAULT_CURRENCY)
            .invoiceTaxTotal(DEFAULT_INVOICE_TAX_TOTAL)
            .invoiceSubTotal(DEFAULT_INVOICE_SUB_TOTAL)
            .invoiceTotal(DEFAULT_INVOICE_TOTAL)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .invoicePaidDate(DEFAULT_INVOICE_PAID_DATE)
            .refOption1(DEFAULT_REF_OPTION_1)
            .refValue1(DEFAULT_REF_VALUE_1)
            .refOption2(DEFAULT_REF_OPTION_2)
            .refValue2(DEFAULT_REF_VALUE_2)
            .refOption3(DEFAULT_REF_OPTION_3)
            .refValue3(DEFAULT_REF_VALUE_3)
            .payRefNo(DEFAULT_PAY_REF_NO)
            .invoiceDueDate(DEFAULT_INVOICE_DUE_DATE)
            .status(DEFAULT_STATUS)
            .invoicePdf(DEFAULT_INVOICE_PDF)
            .invoicePdfContentType(DEFAULT_INVOICE_PDF_CONTENT_TYPE)
            .remarks(DEFAULT_REMARKS)
            .customerInfo(DEFAULT_CUSTOMER_INFO)
            .payterm(DEFAULT_PAYTERM)
            .balance(DEFAULT_BALANCE)
            .advance(DEFAULT_ADVANCE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return invoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createUpdatedEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .orderNo(UPDATED_ORDER_NO)
            .invoiceNo(UPDATED_INVOICE_NO)
            .taxRate(UPDATED_TAX_RATE)
            .taxType(UPDATED_TAX_TYPE)
            .currency(UPDATED_CURRENCY)
            .invoiceTaxTotal(UPDATED_INVOICE_TAX_TOTAL)
            .invoiceSubTotal(UPDATED_INVOICE_SUB_TOTAL)
            .invoiceTotal(UPDATED_INVOICE_TOTAL)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .invoicePaidDate(UPDATED_INVOICE_PAID_DATE)
            .refOption1(UPDATED_REF_OPTION_1)
            .refValue1(UPDATED_REF_VALUE_1)
            .refOption2(UPDATED_REF_OPTION_2)
            .refValue2(UPDATED_REF_VALUE_2)
            .refOption3(UPDATED_REF_OPTION_3)
            .refValue3(UPDATED_REF_VALUE_3)
            .payRefNo(UPDATED_PAY_REF_NO)
            .invoiceDueDate(UPDATED_INVOICE_DUE_DATE)
            .status(UPDATED_STATUS)
            .invoicePdf(UPDATED_INVOICE_PDF)
            .invoicePdfContentType(UPDATED_INVOICE_PDF_CONTENT_TYPE)
            .remarks(UPDATED_REMARKS)
            .customerInfo(UPDATED_CUSTOMER_INFO)
            .payterm(UPDATED_PAYTERM)
            .balance(UPDATED_BALANCE)
            .advance(UPDATED_ADVANCE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return invoice;
    }

    @BeforeEach
    public void initTest() {
        invoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice
        restInvoiceMockMvc.perform(post("/api/invoices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testInvoice.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
        assertThat(testInvoice.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);
        assertThat(testInvoice.getTaxType()).isEqualTo(DEFAULT_TAX_TYPE);
        assertThat(testInvoice.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testInvoice.getInvoiceTaxTotal()).isEqualTo(DEFAULT_INVOICE_TAX_TOTAL);
        assertThat(testInvoice.getInvoiceSubTotal()).isEqualTo(DEFAULT_INVOICE_SUB_TOTAL);
        assertThat(testInvoice.getInvoiceTotal()).isEqualTo(DEFAULT_INVOICE_TOTAL);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoice.getInvoicePaidDate()).isEqualTo(DEFAULT_INVOICE_PAID_DATE);
        assertThat(testInvoice.getRefOption1()).isEqualTo(DEFAULT_REF_OPTION_1);
        assertThat(testInvoice.getRefValue1()).isEqualTo(DEFAULT_REF_VALUE_1);
        assertThat(testInvoice.getRefOption2()).isEqualTo(DEFAULT_REF_OPTION_2);
        assertThat(testInvoice.getRefValue2()).isEqualTo(DEFAULT_REF_VALUE_2);
        assertThat(testInvoice.getRefOption3()).isEqualTo(DEFAULT_REF_OPTION_3);
        assertThat(testInvoice.getRefValue3()).isEqualTo(DEFAULT_REF_VALUE_3);
        assertThat(testInvoice.getPayRefNo()).isEqualTo(DEFAULT_PAY_REF_NO);
        assertThat(testInvoice.getInvoiceDueDate()).isEqualTo(DEFAULT_INVOICE_DUE_DATE);
        assertThat(testInvoice.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInvoice.getInvoicePdf()).isEqualTo(DEFAULT_INVOICE_PDF);
        assertThat(testInvoice.getInvoicePdfContentType()).isEqualTo(DEFAULT_INVOICE_PDF_CONTENT_TYPE);
        assertThat(testInvoice.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testInvoice.getCustomerInfo()).isEqualTo(DEFAULT_CUSTOMER_INFO);
        assertThat(testInvoice.getPayterm()).isEqualTo(DEFAULT_PAYTERM);
        assertThat(testInvoice.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testInvoice.getAdvance()).isEqualTo(DEFAULT_ADVANCE);
        assertThat(testInvoice.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testInvoice.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInvoice.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testInvoice.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(1)).save(testInvoice);
    }

    @Test
    @Transactional
    public void createInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice with an existing ID
        invoice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc.perform(post("/api/invoices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(0)).save(invoice);
    }


    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].taxType").value(hasItem(DEFAULT_TAX_TYPE.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].invoiceTaxTotal").value(hasItem(DEFAULT_INVOICE_TAX_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceSubTotal").value(hasItem(DEFAULT_INVOICE_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceTotal").value(hasItem(DEFAULT_INVOICE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoicePaidDate").value(hasItem(DEFAULT_INVOICE_PAID_DATE.toString())))
            .andExpect(jsonPath("$.[*].refOption1").value(hasItem(DEFAULT_REF_OPTION_1.toString())))
            .andExpect(jsonPath("$.[*].refValue1").value(hasItem(DEFAULT_REF_VALUE_1)))
            .andExpect(jsonPath("$.[*].refOption2").value(hasItem(DEFAULT_REF_OPTION_2.toString())))
            .andExpect(jsonPath("$.[*].refValue2").value(hasItem(DEFAULT_REF_VALUE_2)))
            .andExpect(jsonPath("$.[*].refOption3").value(hasItem(DEFAULT_REF_OPTION_3.toString())))
            .andExpect(jsonPath("$.[*].refValue3").value(hasItem(DEFAULT_REF_VALUE_3)))
            .andExpect(jsonPath("$.[*].payRefNo").value(hasItem(DEFAULT_PAY_REF_NO)))
            .andExpect(jsonPath("$.[*].invoiceDueDate").value(hasItem(DEFAULT_INVOICE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].invoicePdfContentType").value(hasItem(DEFAULT_INVOICE_PDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoicePdf").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_PDF))))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].customerInfo").value(hasItem(DEFAULT_CUSTOMER_INFO)))
            .andExpect(jsonPath("$.[*].payterm").value(hasItem(DEFAULT_PAYTERM)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].advance").value(hasItem(DEFAULT_ADVANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO))
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.doubleValue()))
            .andExpect(jsonPath("$.taxType").value(DEFAULT_TAX_TYPE.toString()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.invoiceTaxTotal").value(DEFAULT_INVOICE_TAX_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.invoiceSubTotal").value(DEFAULT_INVOICE_SUB_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.invoiceTotal").value(DEFAULT_INVOICE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.invoicePaidDate").value(DEFAULT_INVOICE_PAID_DATE.toString()))
            .andExpect(jsonPath("$.refOption1").value(DEFAULT_REF_OPTION_1.toString()))
            .andExpect(jsonPath("$.refValue1").value(DEFAULT_REF_VALUE_1))
            .andExpect(jsonPath("$.refOption2").value(DEFAULT_REF_OPTION_2.toString()))
            .andExpect(jsonPath("$.refValue2").value(DEFAULT_REF_VALUE_2))
            .andExpect(jsonPath("$.refOption3").value(DEFAULT_REF_OPTION_3.toString()))
            .andExpect(jsonPath("$.refValue3").value(DEFAULT_REF_VALUE_3))
            .andExpect(jsonPath("$.payRefNo").value(DEFAULT_PAY_REF_NO))
            .andExpect(jsonPath("$.invoiceDueDate").value(DEFAULT_INVOICE_DUE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.invoicePdfContentType").value(DEFAULT_INVOICE_PDF_CONTENT_TYPE))
            .andExpect(jsonPath("$.invoicePdf").value(Base64Utils.encodeToString(DEFAULT_INVOICE_PDF)))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.customerInfo").value(DEFAULT_CUSTOMER_INFO))
            .andExpect(jsonPath("$.payterm").value(DEFAULT_PAYTERM))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.advance").value(DEFAULT_ADVANCE.doubleValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInvoiceSearchRepository);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).get();
        // Disconnect from session so that the updates on updatedInvoice are not directly saved in db
        em.detach(updatedInvoice);
        updatedInvoice
            .orderNo(UPDATED_ORDER_NO)
            .invoiceNo(UPDATED_INVOICE_NO)
            .taxRate(UPDATED_TAX_RATE)
            .taxType(UPDATED_TAX_TYPE)
            .currency(UPDATED_CURRENCY)
            .invoiceTaxTotal(UPDATED_INVOICE_TAX_TOTAL)
            .invoiceSubTotal(UPDATED_INVOICE_SUB_TOTAL)
            .invoiceTotal(UPDATED_INVOICE_TOTAL)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .invoicePaidDate(UPDATED_INVOICE_PAID_DATE)
            .refOption1(UPDATED_REF_OPTION_1)
            .refValue1(UPDATED_REF_VALUE_1)
            .refOption2(UPDATED_REF_OPTION_2)
            .refValue2(UPDATED_REF_VALUE_2)
            .refOption3(UPDATED_REF_OPTION_3)
            .refValue3(UPDATED_REF_VALUE_3)
            .payRefNo(UPDATED_PAY_REF_NO)
            .invoiceDueDate(UPDATED_INVOICE_DUE_DATE)
            .status(UPDATED_STATUS)
            .invoicePdf(UPDATED_INVOICE_PDF)
            .invoicePdfContentType(UPDATED_INVOICE_PDF_CONTENT_TYPE)
            .remarks(UPDATED_REMARKS)
            .customerInfo(UPDATED_CUSTOMER_INFO)
            .payterm(UPDATED_PAYTERM)
            .balance(UPDATED_BALANCE)
            .advance(UPDATED_ADVANCE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restInvoiceMockMvc.perform(put("/api/invoices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoice)))
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testInvoice.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
        assertThat(testInvoice.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);
        assertThat(testInvoice.getTaxType()).isEqualTo(UPDATED_TAX_TYPE);
        assertThat(testInvoice.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testInvoice.getInvoiceTaxTotal()).isEqualTo(UPDATED_INVOICE_TAX_TOTAL);
        assertThat(testInvoice.getInvoiceSubTotal()).isEqualTo(UPDATED_INVOICE_SUB_TOTAL);
        assertThat(testInvoice.getInvoiceTotal()).isEqualTo(UPDATED_INVOICE_TOTAL);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoice.getInvoicePaidDate()).isEqualTo(UPDATED_INVOICE_PAID_DATE);
        assertThat(testInvoice.getRefOption1()).isEqualTo(UPDATED_REF_OPTION_1);
        assertThat(testInvoice.getRefValue1()).isEqualTo(UPDATED_REF_VALUE_1);
        assertThat(testInvoice.getRefOption2()).isEqualTo(UPDATED_REF_OPTION_2);
        assertThat(testInvoice.getRefValue2()).isEqualTo(UPDATED_REF_VALUE_2);
        assertThat(testInvoice.getRefOption3()).isEqualTo(UPDATED_REF_OPTION_3);
        assertThat(testInvoice.getRefValue3()).isEqualTo(UPDATED_REF_VALUE_3);
        assertThat(testInvoice.getPayRefNo()).isEqualTo(UPDATED_PAY_REF_NO);
        assertThat(testInvoice.getInvoiceDueDate()).isEqualTo(UPDATED_INVOICE_DUE_DATE);
        assertThat(testInvoice.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInvoice.getInvoicePdf()).isEqualTo(UPDATED_INVOICE_PDF);
        assertThat(testInvoice.getInvoicePdfContentType()).isEqualTo(UPDATED_INVOICE_PDF_CONTENT_TYPE);
        assertThat(testInvoice.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testInvoice.getCustomerInfo()).isEqualTo(UPDATED_CUSTOMER_INFO);
        assertThat(testInvoice.getPayterm()).isEqualTo(UPDATED_PAYTERM);
        assertThat(testInvoice.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testInvoice.getAdvance()).isEqualTo(UPDATED_ADVANCE);
        assertThat(testInvoice.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testInvoice.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInvoice.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testInvoice.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(1)).save(testInvoice);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Create the Invoice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc.perform(put("/api/invoices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(0)).save(invoice);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Delete the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Invoice in Elasticsearch
        verify(mockInvoiceSearchRepository, times(1)).deleteById(invoice.getId());
    }

    @Test
    @Transactional
    public void searchInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);
        when(mockInvoiceSearchRepository.search(queryStringQuery("id:" + invoice.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(invoice), PageRequest.of(0, 1), 1));
        // Search the invoice
        restInvoiceMockMvc.perform(get("/api/_search/invoices?query=id:" + invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].taxType").value(hasItem(DEFAULT_TAX_TYPE.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].invoiceTaxTotal").value(hasItem(DEFAULT_INVOICE_TAX_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceSubTotal").value(hasItem(DEFAULT_INVOICE_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceTotal").value(hasItem(DEFAULT_INVOICE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoicePaidDate").value(hasItem(DEFAULT_INVOICE_PAID_DATE.toString())))
            .andExpect(jsonPath("$.[*].refOption1").value(hasItem(DEFAULT_REF_OPTION_1.toString())))
            .andExpect(jsonPath("$.[*].refValue1").value(hasItem(DEFAULT_REF_VALUE_1)))
            .andExpect(jsonPath("$.[*].refOption2").value(hasItem(DEFAULT_REF_OPTION_2.toString())))
            .andExpect(jsonPath("$.[*].refValue2").value(hasItem(DEFAULT_REF_VALUE_2)))
            .andExpect(jsonPath("$.[*].refOption3").value(hasItem(DEFAULT_REF_OPTION_3.toString())))
            .andExpect(jsonPath("$.[*].refValue3").value(hasItem(DEFAULT_REF_VALUE_3)))
            .andExpect(jsonPath("$.[*].payRefNo").value(hasItem(DEFAULT_PAY_REF_NO)))
            .andExpect(jsonPath("$.[*].invoiceDueDate").value(hasItem(DEFAULT_INVOICE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].invoicePdfContentType").value(hasItem(DEFAULT_INVOICE_PDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoicePdf").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_PDF))))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].customerInfo").value(hasItem(DEFAULT_CUSTOMER_INFO)))
            .andExpect(jsonPath("$.[*].payterm").value(hasItem(DEFAULT_PAYTERM)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].advance").value(hasItem(DEFAULT_ADVANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
}
