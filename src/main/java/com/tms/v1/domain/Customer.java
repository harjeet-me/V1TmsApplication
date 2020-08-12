package com.tms.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.tms.v1.domain.enumeration.Designation;

import com.tms.v1.domain.enumeration.PreffredContactType;

import com.tms.v1.domain.enumeration.CountryEnum;

import com.tms.v1.domain.enumeration.ToggleStatus;

import com.tms.v1.domain.enumeration.CURRENCY;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_designation")
    private Designation contactDesignation;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "phone_number_extention")
    private Long phoneNumberExtention;

    @Enumerated(EnumType.STRING)
    @Column(name = "preffred_contact_type")
    private PreffredContactType preffredContactType;

    @Column(name = "website")
    private String website;

    @Column(name = "alternate_contact_person")
    private String alternateContactPerson;

    @Column(name = "alternate_contact_number")
    private Long alternateContactNumber;

    @Column(name = "alternate_phone_number_extention")
    private Long alternatePhoneNumberExtention;

    @Column(name = "alternate_contact_email")
    private String alternateContactEmail;

    @Column(name = "preferred_contact_time")
    private Instant preferredContactTime;

    @Column(name = "fax")
    private Long fax;

    @Column(name = "address")
    private String address;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private CountryEnum country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "dot")
    private String dot;

    @Column(name = "mc")
    private Long mc;

    @Column(name = "tax_id")
    private String taxId;

    @Lob
    @Column(name = "company_logo")
    private byte[] companyLogo;

    @Column(name = "company_logo_content_type")
    private String companyLogoContentType;

    @Column(name = "customer_since")
    private LocalDate customerSince;

    @Column(name = "notes")
    private String notes;

    @Lob
    @Column(name = "contract")
    private byte[] contract;

    @Column(name = "contract_content_type")
    private String contractContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToggleStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "preffred_currency")
    private CURRENCY preffredCurrency;

    @Column(name = "payterms")
    private String payterms;

    @Column(name = "time_zone")
    private ZonedDateTime timeZone;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trip> loadOrders = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Email> emails = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> morecontacts = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TransactionsRecord> transactionsRecords = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "customer_charge",
               joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "charge_id", referencedColumnName = "id"))
    private Set<ProductItem> charges = new HashSet<>();

    @OneToOne(mappedBy = "customer")
    @JsonIgnore
    private Accounts accounts;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public Customer company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Designation getContactDesignation() {
        return contactDesignation;
    }

    public Customer contactDesignation(Designation contactDesignation) {
        this.contactDesignation = contactDesignation;
        return this;
    }

    public void setContactDesignation(Designation contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Customer phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPhoneNumberExtention() {
        return phoneNumberExtention;
    }

    public Customer phoneNumberExtention(Long phoneNumberExtention) {
        this.phoneNumberExtention = phoneNumberExtention;
        return this;
    }

    public void setPhoneNumberExtention(Long phoneNumberExtention) {
        this.phoneNumberExtention = phoneNumberExtention;
    }

    public PreffredContactType getPreffredContactType() {
        return preffredContactType;
    }

    public Customer preffredContactType(PreffredContactType preffredContactType) {
        this.preffredContactType = preffredContactType;
        return this;
    }

    public void setPreffredContactType(PreffredContactType preffredContactType) {
        this.preffredContactType = preffredContactType;
    }

    public String getWebsite() {
        return website;
    }

    public Customer website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAlternateContactPerson() {
        return alternateContactPerson;
    }

    public Customer alternateContactPerson(String alternateContactPerson) {
        this.alternateContactPerson = alternateContactPerson;
        return this;
    }

    public void setAlternateContactPerson(String alternateContactPerson) {
        this.alternateContactPerson = alternateContactPerson;
    }

    public Long getAlternateContactNumber() {
        return alternateContactNumber;
    }

    public Customer alternateContactNumber(Long alternateContactNumber) {
        this.alternateContactNumber = alternateContactNumber;
        return this;
    }

    public void setAlternateContactNumber(Long alternateContactNumber) {
        this.alternateContactNumber = alternateContactNumber;
    }

    public Long getAlternatePhoneNumberExtention() {
        return alternatePhoneNumberExtention;
    }

    public Customer alternatePhoneNumberExtention(Long alternatePhoneNumberExtention) {
        this.alternatePhoneNumberExtention = alternatePhoneNumberExtention;
        return this;
    }

    public void setAlternatePhoneNumberExtention(Long alternatePhoneNumberExtention) {
        this.alternatePhoneNumberExtention = alternatePhoneNumberExtention;
    }

    public String getAlternateContactEmail() {
        return alternateContactEmail;
    }

    public Customer alternateContactEmail(String alternateContactEmail) {
        this.alternateContactEmail = alternateContactEmail;
        return this;
    }

    public void setAlternateContactEmail(String alternateContactEmail) {
        this.alternateContactEmail = alternateContactEmail;
    }

    public Instant getPreferredContactTime() {
        return preferredContactTime;
    }

    public Customer preferredContactTime(Instant preferredContactTime) {
        this.preferredContactTime = preferredContactTime;
        return this;
    }

    public void setPreferredContactTime(Instant preferredContactTime) {
        this.preferredContactTime = preferredContactTime;
    }

    public Long getFax() {
        return fax;
    }

    public Customer fax(Long fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(Long fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Customer streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public Customer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public Customer stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public Customer country(CountryEnum country) {
        this.country = country;
        return this;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Customer postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDot() {
        return dot;
    }

    public Customer dot(String dot) {
        this.dot = dot;
        return this;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public Long getMc() {
        return mc;
    }

    public Customer mc(Long mc) {
        this.mc = mc;
        return this;
    }

    public void setMc(Long mc) {
        this.mc = mc;
    }

    public String getTaxId() {
        return taxId;
    }

    public Customer taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public Customer companyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
        return this;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLogoContentType() {
        return companyLogoContentType;
    }

    public Customer companyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
        return this;
    }

    public void setCompanyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }

    public Customer customerSince(LocalDate customerSince) {
        this.customerSince = customerSince;
        return this;
    }

    public void setCustomerSince(LocalDate customerSince) {
        this.customerSince = customerSince;
    }

    public String getNotes() {
        return notes;
    }

    public Customer notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getContract() {
        return contract;
    }

    public Customer contract(byte[] contract) {
        this.contract = contract;
        return this;
    }

    public void setContract(byte[] contract) {
        this.contract = contract;
    }

    public String getContractContentType() {
        return contractContentType;
    }

    public Customer contractContentType(String contractContentType) {
        this.contractContentType = contractContentType;
        return this;
    }

    public void setContractContentType(String contractContentType) {
        this.contractContentType = contractContentType;
    }

    public ToggleStatus getStatus() {
        return status;
    }

    public Customer status(ToggleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ToggleStatus status) {
        this.status = status;
    }

    public CURRENCY getPreffredCurrency() {
        return preffredCurrency;
    }

    public Customer preffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
        return this;
    }

    public void setPreffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
    }

    public String getPayterms() {
        return payterms;
    }

    public Customer payterms(String payterms) {
        this.payterms = payterms;
        return this;
    }

    public void setPayterms(String payterms) {
        this.payterms = payterms;
    }

    public ZonedDateTime getTimeZone() {
        return timeZone;
    }

    public Customer timeZone(ZonedDateTime timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(ZonedDateTime timeZone) {
        this.timeZone = timeZone;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Customer createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Customer createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Customer lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Customer lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Trip> getLoadOrders() {
        return loadOrders;
    }

    public Customer loadOrders(Set<Trip> trips) {
        this.loadOrders = trips;
        return this;
    }

    public Customer addLoadOrder(Trip trip) {
        this.loadOrders.add(trip);
        trip.setCustomer(this);
        return this;
    }

    public Customer removeLoadOrder(Trip trip) {
        this.loadOrders.remove(trip);
        trip.setCustomer(null);
        return this;
    }

    public void setLoadOrders(Set<Trip> trips) {
        this.loadOrders = trips;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Customer invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Customer addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setCustomer(this);
        return this;
    }

    public Customer removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setCustomer(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Customer payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Customer addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setCustomer(this);
        return this;
    }

    public Customer removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setCustomer(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public Customer emails(Set<Email> emails) {
        this.emails = emails;
        return this;
    }

    public Customer addEmail(Email email) {
        this.emails.add(email);
        email.setCustomer(this);
        return this;
    }

    public Customer removeEmail(Email email) {
        this.emails.remove(email);
        email.setCustomer(null);
        return this;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    public Set<Contact> getMorecontacts() {
        return morecontacts;
    }

    public Customer morecontacts(Set<Contact> contacts) {
        this.morecontacts = contacts;
        return this;
    }

    public Customer addMorecontact(Contact contact) {
        this.morecontacts.add(contact);
        contact.setCustomer(this);
        return this;
    }

    public Customer removeMorecontact(Contact contact) {
        this.morecontacts.remove(contact);
        contact.setCustomer(null);
        return this;
    }

    public void setMorecontacts(Set<Contact> contacts) {
        this.morecontacts = contacts;
    }

    public Set<TransactionsRecord> getTransactionsRecords() {
        return transactionsRecords;
    }

    public Customer transactionsRecords(Set<TransactionsRecord> transactionsRecords) {
        this.transactionsRecords = transactionsRecords;
        return this;
    }

    public Customer addTransactionsRecord(TransactionsRecord transactionsRecord) {
        this.transactionsRecords.add(transactionsRecord);
        transactionsRecord.setCustomer(this);
        return this;
    }

    public Customer removeTransactionsRecord(TransactionsRecord transactionsRecord) {
        this.transactionsRecords.remove(transactionsRecord);
        transactionsRecord.setCustomer(null);
        return this;
    }

    public void setTransactionsRecords(Set<TransactionsRecord> transactionsRecords) {
        this.transactionsRecords = transactionsRecords;
    }

    public Set<ProductItem> getCharges() {
        return charges;
    }

    public Customer charges(Set<ProductItem> productItems) {
        this.charges = productItems;
        return this;
    }

    public Customer addCharge(ProductItem productItem) {
        this.charges.add(productItem);
        productItem.getCustomers().add(this);
        return this;
    }

    public Customer removeCharge(ProductItem productItem) {
        this.charges.remove(productItem);
        productItem.getCustomers().remove(this);
        return this;
    }

    public void setCharges(Set<ProductItem> productItems) {
        this.charges = productItems;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public Customer accounts(Accounts accounts) {
        this.accounts = accounts;
        return this;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", contactDesignation='" + getContactDesignation() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", phoneNumberExtention=" + getPhoneNumberExtention() +
            ", preffredContactType='" + getPreffredContactType() + "'" +
            ", website='" + getWebsite() + "'" +
            ", alternateContactPerson='" + getAlternateContactPerson() + "'" +
            ", alternateContactNumber=" + getAlternateContactNumber() +
            ", alternatePhoneNumberExtention=" + getAlternatePhoneNumberExtention() +
            ", alternateContactEmail='" + getAlternateContactEmail() + "'" +
            ", preferredContactTime='" + getPreferredContactTime() + "'" +
            ", fax=" + getFax() +
            ", address='" + getAddress() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", dot='" + getDot() + "'" +
            ", mc=" + getMc() +
            ", taxId='" + getTaxId() + "'" +
            ", companyLogo='" + getCompanyLogo() + "'" +
            ", companyLogoContentType='" + getCompanyLogoContentType() + "'" +
            ", customerSince='" + getCustomerSince() + "'" +
            ", notes='" + getNotes() + "'" +
            ", contract='" + getContract() + "'" +
            ", contractContentType='" + getContractContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", preffredCurrency='" + getPreffredCurrency() + "'" +
            ", payterms='" + getPayterms() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
