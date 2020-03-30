package com.tms.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

import com.tms.v1.domain.enumeration.TransactionType;

import com.tms.v1.domain.enumeration.TxStatus;

/**
 * A TransactionsRecord.
 */
@Entity
@Table(name = "transactions_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transactionsrecord")
public class TransactionsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tx_type")
    private TransactionType txType;

    @Column(name = "description")
    private String description;

    @Column(name = "tx_ammount")
    private Double txAmmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TxStatus status;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    @JsonIgnoreProperties("transactionsRecords")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("transactionsRecords")
    private Accounts account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTxType() {
        return txType;
    }

    public TransactionsRecord txType(TransactionType txType) {
        this.txType = txType;
        return this;
    }

    public void setTxType(TransactionType txType) {
        this.txType = txType;
    }

    public String getDescription() {
        return description;
    }

    public TransactionsRecord description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTxAmmount() {
        return txAmmount;
    }

    public TransactionsRecord txAmmount(Double txAmmount) {
        this.txAmmount = txAmmount;
        return this;
    }

    public void setTxAmmount(Double txAmmount) {
        this.txAmmount = txAmmount;
    }

    public TxStatus getStatus() {
        return status;
    }

    public TransactionsRecord status(TxStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TxStatus status) {
        this.status = status;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public TransactionsRecord createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TransactionsRecord createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public TransactionsRecord updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public TransactionsRecord updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public TransactionsRecord customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Accounts getAccount() {
        return account;
    }

    public TransactionsRecord account(Accounts accounts) {
        this.account = accounts;
        return this;
    }

    public void setAccount(Accounts accounts) {
        this.account = accounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionsRecord)) {
            return false;
        }
        return id != null && id.equals(((TransactionsRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionsRecord{" +
            "id=" + getId() +
            ", txType='" + getTxType() + "'" +
            ", description='" + getDescription() + "'" +
            ", txAmmount=" + getTxAmmount() +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
