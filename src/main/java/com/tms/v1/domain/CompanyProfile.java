package com.tms.v1.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

import com.tms.v1.domain.enumeration.CountryEnum;

import com.tms.v1.domain.enumeration.ToggleStatus;

import com.tms.v1.domain.enumeration.CURRENCY;

/**
 * A CompanyProfile.
 */
@Entity
@Table(name = "company_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "companyprofile")
public class CompanyProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "company")
    private String company;

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

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "dot")
    private String dot;

    @Column(name = "mc")
    private Long mc;

    @Lob
    @Column(name = "company_logo")
    private byte[] companyLogo;

    @Column(name = "company_logo_content_type")
    private String companyLogoContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_status")
    private ToggleStatus profileStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "preffred_currency")
    private CURRENCY preffredCurrency;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public CompanyProfile active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCompany() {
        return company;
    }

    public CompanyProfile company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public CompanyProfile address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public CompanyProfile streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public CompanyProfile city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public CompanyProfile stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public CompanyProfile country(CountryEnum country) {
        this.country = country;
        return this;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public CompanyProfile postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public CompanyProfile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public CompanyProfile website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public CompanyProfile phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDot() {
        return dot;
    }

    public CompanyProfile dot(String dot) {
        this.dot = dot;
        return this;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public Long getMc() {
        return mc;
    }

    public CompanyProfile mc(Long mc) {
        this.mc = mc;
        return this;
    }

    public void setMc(Long mc) {
        this.mc = mc;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public CompanyProfile companyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
        return this;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLogoContentType() {
        return companyLogoContentType;
    }

    public CompanyProfile companyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
        return this;
    }

    public void setCompanyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
    }

    public ToggleStatus getProfileStatus() {
        return profileStatus;
    }

    public CompanyProfile profileStatus(ToggleStatus profileStatus) {
        this.profileStatus = profileStatus;
        return this;
    }

    public void setProfileStatus(ToggleStatus profileStatus) {
        this.profileStatus = profileStatus;
    }

    public CURRENCY getPreffredCurrency() {
        return preffredCurrency;
    }

    public CompanyProfile preffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
        return this;
    }

    public void setPreffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public CompanyProfile createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CompanyProfile createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public CompanyProfile updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CompanyProfile updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyProfile)) {
            return false;
        }
        return id != null && id.equals(((CompanyProfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompanyProfile{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", company='" + getCompany() + "'" +
            ", address='" + getAddress() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", dot='" + getDot() + "'" +
            ", mc=" + getMc() +
            ", companyLogo='" + getCompanyLogo() + "'" +
            ", companyLogoContentType='" + getCompanyLogoContentType() + "'" +
            ", profileStatus='" + getProfileStatus() + "'" +
            ", preffredCurrency='" + getPreffredCurrency() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
