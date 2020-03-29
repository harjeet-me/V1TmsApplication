package com.tms.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

import com.tms.v1.domain.enumeration.InvoiceStatus;

/**
 * A InvoiceHistory.
 */
@Entity
@Table(name = "invoice_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "invoicehistory")
public class InvoiceHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToOne
    @JoinColumn(unique = true)
    private InvoiceHistory previous;

    @OneToOne
    @JoinColumn(unique = true)
    private InvoiceHistory next;

    @ManyToOne
    @JsonIgnoreProperties("invoiceHistories")
    private Invoice invoice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public InvoiceHistory status(InvoiceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public InvoiceHistory comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public InvoiceHistory createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public InvoiceHistory createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public InvoiceHistory updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public InvoiceHistory updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InvoiceHistory getPrevious() {
        return previous;
    }

    public InvoiceHistory previous(InvoiceHistory invoiceHistory) {
        this.previous = invoiceHistory;
        return this;
    }

    public void setPrevious(InvoiceHistory invoiceHistory) {
        this.previous = invoiceHistory;
    }

    public InvoiceHistory getNext() {
        return next;
    }

    public InvoiceHistory next(InvoiceHistory invoiceHistory) {
        this.next = invoiceHistory;
        return this;
    }

    public void setNext(InvoiceHistory invoiceHistory) {
        this.next = invoiceHistory;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public InvoiceHistory invoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceHistory)) {
            return false;
        }
        return id != null && id.equals(((InvoiceHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InvoiceHistory{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
