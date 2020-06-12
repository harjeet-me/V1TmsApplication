package com.tms.v1.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Insurance.
 */
@Entity
@Table(name = "insurance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "insurance")
public class Insurance extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Lob
    @Column(name = "policy_document")
    private byte[] policyDocument;

    @Column(name = "policy_document_content_type")
    private String policyDocumentContentType;

    @Column(name = "coverage_statement")
    private String coverageStatement;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToOne(mappedBy = "operInsurance")
    @JsonIgnore
    private Carrier carrier;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public Insurance providerName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public Insurance issueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Insurance expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public byte[] getPolicyDocument() {
        return policyDocument;
    }

    public Insurance policyDocument(byte[] policyDocument) {
        this.policyDocument = policyDocument;
        return this;
    }

    public void setPolicyDocument(byte[] policyDocument) {
        this.policyDocument = policyDocument;
    }

    public String getPolicyDocumentContentType() {
        return policyDocumentContentType;
    }

    public Insurance policyDocumentContentType(String policyDocumentContentType) {
        this.policyDocumentContentType = policyDocumentContentType;
        return this;
    }

    public void setPolicyDocumentContentType(String policyDocumentContentType) {
        this.policyDocumentContentType = policyDocumentContentType;
    }

    public String getCoverageStatement() {
        return coverageStatement;
    }

    public Insurance coverageStatement(String coverageStatement) {
        this.coverageStatement = coverageStatement;
        return this;
    }

    public void setCoverageStatement(String coverageStatement) {
        this.coverageStatement = coverageStatement;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Insurance createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Insurance createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Insurance lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Insurance lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public Insurance carrier(Carrier carrier) {
        this.carrier = carrier;
        return this;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Insurance)) {
            return false;
        }
        return id != null && id.equals(((Insurance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Insurance{" +
            "id=" + getId() +
            ", providerName='" + getProviderName() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", policyDocument='" + getPolicyDocument() + "'" +
            ", policyDocumentContentType='" + getPolicyDocumentContentType() + "'" +
            ", coverageStatement='" + getCoverageStatement() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
