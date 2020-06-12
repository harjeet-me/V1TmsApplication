package com.tms.v1.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tms.v1.domain.enumeration.CURRENCY;
import com.tms.v1.domain.enumeration.InvoiceStatus;
import com.tms.v1.domain.enumeration.InvoiveRef;
import com.tms.v1.domain.enumeration.TaxType;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "invoice")
public class Invoice extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "tax_rate")
    private Double taxRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type")
    private TaxType taxType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CURRENCY currency;

    @Column(name = "invoice_tax_total")
    private Double invoiceTaxTotal;

    @Column(name = "invoice_sub_total")
    private Double invoiceSubTotal;

    @Column(name = "invoice_total")
    private Double invoiceTotal;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "invoice_paid_date")
    private LocalDate invoicePaidDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ref_option_1")
    private InvoiveRef refOption1;

    @Column(name = "ref_value_1")
    private String refValue1;

    @Enumerated(EnumType.STRING)
    @Column(name = "ref_option_2")
    private InvoiveRef refOption2;

    @Column(name = "ref_value_2")
    private String refValue2;

    @Enumerated(EnumType.STRING)
    @Column(name = "ref_option_3")
    private InvoiveRef refOption3;

    @Column(name = "ref_value_3")
    private String refValue3;

    @Column(name = "pay_ref_no")
    private String payRefNo;

    @Column(name = "invoice_due_date")
    private LocalDate invoiceDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

    @Lob
    @Column(name = "invoice_pdf")
    private byte[] invoicePdf;

    @Column(name = "invoice_pdf_content_type")
    private String invoicePdfContentType;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "customer_info")
    private String customerInfo;

    @Column(name = "payterm")
    private String payterm;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "advance")
    private Double advance;

    @Column(name = "discount")
    private Double discount;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    
    @OneToOne
    @JoinColumn(unique = true)
    private Email notification;

    @OneToMany(mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

    @OneToMany(mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceHistory> invoiceHistories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Trip trip;

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Customer customer;

    @ManyToMany(mappedBy = "invoices")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<InvoiceReport> invoiceReports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public Invoice orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public Invoice invoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public Invoice taxRate(Double taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public Invoice taxType(TaxType taxType) {
        this.taxType = taxType;
        return this;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    public CURRENCY getCurrency() {
        return currency;
    }

    public Invoice currency(CURRENCY currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(CURRENCY currency) {
        this.currency = currency;
    }

    public Double getInvoiceTaxTotal() {
        return invoiceTaxTotal;
    }

    public Invoice invoiceTaxTotal(Double invoiceTaxTotal) {
        this.invoiceTaxTotal = invoiceTaxTotal;
        return this;
    }

    public void setInvoiceTaxTotal(Double invoiceTaxTotal) {
        this.invoiceTaxTotal = invoiceTaxTotal;
    }

    public Double getInvoiceSubTotal() {
        return invoiceSubTotal;
    }

    public Invoice invoiceSubTotal(Double invoiceSubTotal) {
        this.invoiceSubTotal = invoiceSubTotal;
        return this;
    }

    public void setInvoiceSubTotal(Double invoiceSubTotal) {
        this.invoiceSubTotal = invoiceSubTotal;
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public Invoice invoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
        return this;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Invoice invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getInvoicePaidDate() {
        return invoicePaidDate;
    }

    public Invoice invoicePaidDate(LocalDate invoicePaidDate) {
        this.invoicePaidDate = invoicePaidDate;
        return this;
    }

    public void setInvoicePaidDate(LocalDate invoicePaidDate) {
        this.invoicePaidDate = invoicePaidDate;
    }

    public InvoiveRef getRefOption1() {
        return refOption1;
    }

    public Invoice refOption1(InvoiveRef refOption1) {
        this.refOption1 = refOption1;
        return this;
    }

    public void setRefOption1(InvoiveRef refOption1) {
        this.refOption1 = refOption1;
    }

    public String getRefValue1() {
        return refValue1;
    }

    public Invoice refValue1(String refValue1) {
        this.refValue1 = refValue1;
        return this;
    }

    public void setRefValue1(String refValue1) {
        this.refValue1 = refValue1;
    }

    public InvoiveRef getRefOption2() {
        return refOption2;
    }

    public Invoice refOption2(InvoiveRef refOption2) {
        this.refOption2 = refOption2;
        return this;
    }

    public void setRefOption2(InvoiveRef refOption2) {
        this.refOption2 = refOption2;
    }

    public String getRefValue2() {
        return refValue2;
    }

    public Invoice refValue2(String refValue2) {
        this.refValue2 = refValue2;
        return this;
    }

    public void setRefValue2(String refValue2) {
        this.refValue2 = refValue2;
    }

    public InvoiveRef getRefOption3() {
        return refOption3;
    }

    public Invoice refOption3(InvoiveRef refOption3) {
        this.refOption3 = refOption3;
        return this;
    }

    public void setRefOption3(InvoiveRef refOption3) {
        this.refOption3 = refOption3;
    }

    public String getRefValue3() {
        return refValue3;
    }

    public Invoice refValue3(String refValue3) {
        this.refValue3 = refValue3;
        return this;
    }

    public void setRefValue3(String refValue3) {
        this.refValue3 = refValue3;
    }

    public String getPayRefNo() {
        return payRefNo;
    }

    public Invoice payRefNo(String payRefNo) {
        this.payRefNo = payRefNo;
        return this;
    }

    public void setPayRefNo(String payRefNo) {
        this.payRefNo = payRefNo;
    }

    public LocalDate getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public Invoice invoiceDueDate(LocalDate invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
        return this;
    }

    public void setInvoiceDueDate(LocalDate invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public Invoice status(InvoiceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public byte[] getInvoicePdf() {
        return invoicePdf;
    }

    public Invoice invoicePdf(byte[] invoicePdf) {
        this.invoicePdf = invoicePdf;
        return this;
    }

    public void setInvoicePdf(byte[] invoicePdf) {
        this.invoicePdf = invoicePdf;
    }

    public String getInvoicePdfContentType() {
        return invoicePdfContentType;
    }

    public Invoice invoicePdfContentType(String invoicePdfContentType) {
        this.invoicePdfContentType = invoicePdfContentType;
        return this;
    }

    public void setInvoicePdfContentType(String invoicePdfContentType) {
        this.invoicePdfContentType = invoicePdfContentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public Invoice remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public Invoice customerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
        return this;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getPayterm() {
        return payterm;
    }

    public Invoice payterm(String payterm) {
        this.payterm = payterm;
        return this;
    }

    public void setPayterm(String payterm) {
        this.payterm = payterm;
    }

    public Double getBalance() {
        return balance;
    }

    public Invoice balance(Double balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getAdvance() {
        return advance;
    }

    public Invoice advance(Double advance) {
        this.advance = advance;
        return this;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getDiscount() {
        return discount;
    }

    public Invoice discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Invoice createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Invoice createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Invoice lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Invoice lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Email getNotification() {
        return notification;
    }

    public Invoice notification(Email email) {
        this.notification = email;
        return this;
    }

    public void setNotification(Email email) {
        this.notification = email;
    }

    public Set<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public Invoice invoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
        return this;
    }

    public Invoice addInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItems.add(invoiceItem);
        invoiceItem.setInvoice(this);
        return this;
    }

    public Invoice removeInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItems.remove(invoiceItem);
        invoiceItem.setInvoice(null);
        return this;
    }

    public void setInvoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public Set<InvoiceHistory> getInvoiceHistories() {
        return invoiceHistories;
    }

    public Invoice invoiceHistories(Set<InvoiceHistory> invoiceHistories) {
        this.invoiceHistories = invoiceHistories;
        return this;
    }

    public Invoice addInvoiceHistory(InvoiceHistory invoiceHistory) {
        this.invoiceHistories.add(invoiceHistory);
        invoiceHistory.setInvoice(this);
        return this;
    }

    public Invoice removeInvoiceHistory(InvoiceHistory invoiceHistory) {
        this.invoiceHistories.remove(invoiceHistory);
        invoiceHistory.setInvoice(null);
        return this;
    }

    public void setInvoiceHistories(Set<InvoiceHistory> invoiceHistories) {
        this.invoiceHistories = invoiceHistories;
    }

    public Trip getTrip() {
        return trip;
    }

    public Invoice trip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Invoice customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<InvoiceReport> getInvoiceReports() {
        return invoiceReports;
    }

    public Invoice invoiceReports(Set<InvoiceReport> invoiceReports) {
        this.invoiceReports = invoiceReports;
        return this;
    }

    public Invoice addInvoiceReport(InvoiceReport invoiceReport) {
        this.invoiceReports.add(invoiceReport);
        invoiceReport.getInvoices().add(this);
        return this;
    }

    public Invoice removeInvoiceReport(InvoiceReport invoiceReport) {
        this.invoiceReports.remove(invoiceReport);
        invoiceReport.getInvoices().remove(this);
        return this;
    }

    public void setInvoiceReports(Set<InvoiceReport> invoiceReports) {
        this.invoiceReports = invoiceReports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", orderNo='" + getOrderNo() + "'" +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", taxRate=" + getTaxRate() +
            ", taxType='" + getTaxType() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", invoiceTaxTotal=" + getInvoiceTaxTotal() +
            ", invoiceSubTotal=" + getInvoiceSubTotal() +
            ", invoiceTotal=" + getInvoiceTotal() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", invoicePaidDate='" + getInvoicePaidDate() + "'" +
            ", refOption1='" + getRefOption1() + "'" +
            ", refValue1='" + getRefValue1() + "'" +
            ", refOption2='" + getRefOption2() + "'" +
            ", refValue2='" + getRefValue2() + "'" +
            ", refOption3='" + getRefOption3() + "'" +
            ", refValue3='" + getRefValue3() + "'" +
            ", payRefNo='" + getPayRefNo() + "'" +
            ", invoiceDueDate='" + getInvoiceDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", invoicePdf='" + getInvoicePdf() + "'" +
            ", invoicePdfContentType='" + getInvoicePdfContentType() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", customerInfo='" + getCustomerInfo() + "'" +
            ", payterm='" + getPayterm() + "'" +
            ", balance=" + getBalance() +
            ", advance=" + getAdvance() +
            ", discount=" + getDiscount() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
