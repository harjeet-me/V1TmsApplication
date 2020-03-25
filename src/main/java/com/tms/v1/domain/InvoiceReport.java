package com.tms.v1.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A InvoiceReport.
 */
@Entity
@Table(name = "invoice_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "invoicereport")
public class InvoiceReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer")
    private Long customer;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "remarks")
    private String remarks;

    @Lob
    @Column(name = "invoice_report")
    private byte[] invoiceReport;

    @Column(name = "invoice_report_content_type")
    private String invoiceReportContentType;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "invoice_report_invoice",
               joinColumns = @JoinColumn(name = "invoice_report_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "invoice_id", referencedColumnName = "id"))
    private Set<Invoice> invoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomer() {
        return customer;
    }

    public InvoiceReport customer(Long customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public InvoiceReport fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public InvoiceReport toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public InvoiceReport remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getInvoiceReport() {
        return invoiceReport;
    }

    public InvoiceReport invoiceReport(byte[] invoiceReport) {
        this.invoiceReport = invoiceReport;
        return this;
    }

    public void setInvoiceReport(byte[] invoiceReport) {
        this.invoiceReport = invoiceReport;
    }

    public String getInvoiceReportContentType() {
        return invoiceReportContentType;
    }

    public InvoiceReport invoiceReportContentType(String invoiceReportContentType) {
        this.invoiceReportContentType = invoiceReportContentType;
        return this;
    }

    public void setInvoiceReportContentType(String invoiceReportContentType) {
        this.invoiceReportContentType = invoiceReportContentType;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public InvoiceReport createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public InvoiceReport createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public InvoiceReport updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public InvoiceReport updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public InvoiceReport invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public InvoiceReport addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.getInvoiceReports().add(this);
        return this;
    }

    public InvoiceReport removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.getInvoiceReports().remove(this);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceReport)) {
            return false;
        }
        return id != null && id.equals(((InvoiceReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InvoiceReport{" +
            "id=" + getId() +
            ", customer=" + getCustomer() +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", invoiceReport='" + getInvoiceReport() + "'" +
            ", invoiceReportContentType='" + getInvoiceReportContentType() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
