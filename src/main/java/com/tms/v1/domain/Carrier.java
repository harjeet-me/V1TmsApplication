package com.tms.v1.domain;

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
 * A Carrier.
 */
@Entity
@Table(name = "carrier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "carrier")
public class Carrier implements Serializable {

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
    private String preferredContactTime;

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
    private Insurance operInsurance;

    @OneToMany(mappedBy = "carrier")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trip> loadOrders = new HashSet<>();

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

    public Carrier company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public Carrier firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Carrier lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Designation getContactDesignation() {
        return contactDesignation;
    }

    public Carrier contactDesignation(Designation contactDesignation) {
        this.contactDesignation = contactDesignation;
        return this;
    }

    public void setContactDesignation(Designation contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

    public String getEmail() {
        return email;
    }

    public Carrier email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Carrier phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPhoneNumberExtention() {
        return phoneNumberExtention;
    }

    public Carrier phoneNumberExtention(Long phoneNumberExtention) {
        this.phoneNumberExtention = phoneNumberExtention;
        return this;
    }

    public void setPhoneNumberExtention(Long phoneNumberExtention) {
        this.phoneNumberExtention = phoneNumberExtention;
    }

    public PreffredContactType getPreffredContactType() {
        return preffredContactType;
    }

    public Carrier preffredContactType(PreffredContactType preffredContactType) {
        this.preffredContactType = preffredContactType;
        return this;
    }

    public void setPreffredContactType(PreffredContactType preffredContactType) {
        this.preffredContactType = preffredContactType;
    }

    public String getWebsite() {
        return website;
    }

    public Carrier website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAlternateContactPerson() {
        return alternateContactPerson;
    }

    public Carrier alternateContactPerson(String alternateContactPerson) {
        this.alternateContactPerson = alternateContactPerson;
        return this;
    }

    public void setAlternateContactPerson(String alternateContactPerson) {
        this.alternateContactPerson = alternateContactPerson;
    }

    public Long getAlternateContactNumber() {
        return alternateContactNumber;
    }

    public Carrier alternateContactNumber(Long alternateContactNumber) {
        this.alternateContactNumber = alternateContactNumber;
        return this;
    }

    public void setAlternateContactNumber(Long alternateContactNumber) {
        this.alternateContactNumber = alternateContactNumber;
    }

    public Long getAlternatePhoneNumberExtention() {
        return alternatePhoneNumberExtention;
    }

    public Carrier alternatePhoneNumberExtention(Long alternatePhoneNumberExtention) {
        this.alternatePhoneNumberExtention = alternatePhoneNumberExtention;
        return this;
    }

    public void setAlternatePhoneNumberExtention(Long alternatePhoneNumberExtention) {
        this.alternatePhoneNumberExtention = alternatePhoneNumberExtention;
    }

    public String getAlternateContactEmail() {
        return alternateContactEmail;
    }

    public Carrier alternateContactEmail(String alternateContactEmail) {
        this.alternateContactEmail = alternateContactEmail;
        return this;
    }

    public void setAlternateContactEmail(String alternateContactEmail) {
        this.alternateContactEmail = alternateContactEmail;
    }

    public String getPreferredContactTime() {
        return preferredContactTime;
    }

    public Carrier preferredContactTime(String preferredContactTime) {
        this.preferredContactTime = preferredContactTime;
        return this;
    }

    public void setPreferredContactTime(String preferredContactTime) {
        this.preferredContactTime = preferredContactTime;
    }

    public Long getFax() {
        return fax;
    }

    public Carrier fax(Long fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(Long fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public Carrier address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Carrier streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public Carrier city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public Carrier stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public Carrier country(CountryEnum country) {
        this.country = country;
        return this;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Carrier postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDot() {
        return dot;
    }

    public Carrier dot(String dot) {
        this.dot = dot;
        return this;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public Long getMc() {
        return mc;
    }

    public Carrier mc(Long mc) {
        this.mc = mc;
        return this;
    }

    public void setMc(Long mc) {
        this.mc = mc;
    }

    public String getTaxId() {
        return taxId;
    }

    public Carrier taxId(String taxId) {
        this.taxId = taxId;
        return this;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public Carrier companyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
        return this;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLogoContentType() {
        return companyLogoContentType;
    }

    public Carrier companyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
        return this;
    }

    public void setCompanyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }

    public Carrier customerSince(LocalDate customerSince) {
        this.customerSince = customerSince;
        return this;
    }

    public void setCustomerSince(LocalDate customerSince) {
        this.customerSince = customerSince;
    }

    public String getNotes() {
        return notes;
    }

    public Carrier notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getContract() {
        return contract;
    }

    public Carrier contract(byte[] contract) {
        this.contract = contract;
        return this;
    }

    public void setContract(byte[] contract) {
        this.contract = contract;
    }

    public String getContractContentType() {
        return contractContentType;
    }

    public Carrier contractContentType(String contractContentType) {
        this.contractContentType = contractContentType;
        return this;
    }

    public void setContractContentType(String contractContentType) {
        this.contractContentType = contractContentType;
    }

    public ToggleStatus getStatus() {
        return status;
    }

    public Carrier status(ToggleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ToggleStatus status) {
        this.status = status;
    }

    public CURRENCY getPreffredCurrency() {
        return preffredCurrency;
    }

    public Carrier preffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
        return this;
    }

    public void setPreffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
    }

    public String getPayterms() {
        return payterms;
    }

    public Carrier payterms(String payterms) {
        this.payterms = payterms;
        return this;
    }

    public void setPayterms(String payterms) {
        this.payterms = payterms;
    }

    public ZonedDateTime getTimeZone() {
        return timeZone;
    }

    public Carrier timeZone(ZonedDateTime timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(ZonedDateTime timeZone) {
        this.timeZone = timeZone;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Carrier createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Carrier createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Carrier updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Carrier updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Insurance getOperInsurance() {
        return operInsurance;
    }

    public Carrier operInsurance(Insurance insurance) {
        this.operInsurance = insurance;
        return this;
    }

    public void setOperInsurance(Insurance insurance) {
        this.operInsurance = insurance;
    }

    public Set<Trip> getLoadOrders() {
        return loadOrders;
    }

    public Carrier loadOrders(Set<Trip> trips) {
        this.loadOrders = trips;
        return this;
    }

    public Carrier addLoadOrder(Trip trip) {
        this.loadOrders.add(trip);
        trip.setCarrier(this);
        return this;
    }

    public Carrier removeLoadOrder(Trip trip) {
        this.loadOrders.remove(trip);
        trip.setCarrier(null);
        return this;
    }

    public void setLoadOrders(Set<Trip> trips) {
        this.loadOrders = trips;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carrier)) {
            return false;
        }
        return id != null && id.equals(((Carrier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Carrier{" +
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
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
