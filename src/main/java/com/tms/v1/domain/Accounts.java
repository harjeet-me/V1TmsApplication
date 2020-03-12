package com.tms.v1.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Accounts.
 */
@Entity
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "accounts")
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "over_30")
    private Double over30;

    @Column(name = "over_60")
    private Double over60;

    @Column(name = "over_90")
    private Double over90;

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
    private Customer customer;

    @OneToMany(mappedBy = "account")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TransactionsRecord> transactionsRecords = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public Accounts balance(Double balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getOver30() {
        return over30;
    }

    public Accounts over30(Double over30) {
        this.over30 = over30;
        return this;
    }

    public void setOver30(Double over30) {
        this.over30 = over30;
    }

    public Double getOver60() {
        return over60;
    }

    public Accounts over60(Double over60) {
        this.over60 = over60;
        return this;
    }

    public void setOver60(Double over60) {
        this.over60 = over60;
    }

    public Double getOver90() {
        return over90;
    }

    public Accounts over90(Double over90) {
        this.over90 = over90;
        return this;
    }

    public void setOver90(Double over90) {
        this.over90 = over90;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Accounts createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Accounts createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Accounts updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Accounts updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Accounts customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<TransactionsRecord> getTransactionsRecords() {
        return transactionsRecords;
    }

    public Accounts transactionsRecords(Set<TransactionsRecord> transactionsRecords) {
        this.transactionsRecords = transactionsRecords;
        return this;
    }

    public Accounts addTransactionsRecord(TransactionsRecord transactionsRecord) {
        this.transactionsRecords.add(transactionsRecord);
        transactionsRecord.setAccount(this);
        return this;
    }

    public Accounts removeTransactionsRecord(TransactionsRecord transactionsRecord) {
        this.transactionsRecords.remove(transactionsRecord);
        transactionsRecord.setAccount(null);
        return this;
    }

    public void setTransactionsRecords(Set<TransactionsRecord> transactionsRecords) {
        this.transactionsRecords = transactionsRecords;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accounts)) {
            return false;
        }
        return id != null && id.equals(((Accounts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Accounts{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", over30=" + getOver30() +
            ", over60=" + getOver60() +
            ", over90=" + getOver90() +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
