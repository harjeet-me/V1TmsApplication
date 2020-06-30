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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.tms.v1.domain.enumeration.ToggleStatus;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "driver")
public class Driver extends AbstractAuditingEntity  implements Serializable {

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

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "licence_number")
    private String licenceNumber;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "company_joined_on")
    private LocalDate companyJoinedOn;

    @Column(name = "company_left_on")
    private LocalDate companyLeftOn;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "licence_image")
    private byte[] licenceImage;

    @Column(name = "licence_image_content_type")
    private String licenceImageContentType;

    @Column(name = "remarks")
    private String remarks;

    @Lob
    @Column(name = "contract_doc")
    private byte[] contractDoc;

    @Column(name = "contract_doc_content_type")
    private String contractDocContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToggleStatus status;

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

    @OneToMany(mappedBy = "driver")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trip> trips = new HashSet<>();

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

    public Driver company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public Driver firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Driver lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Driver email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Driver phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public Driver licenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
        return this;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Driver dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getCompanyJoinedOn() {
        return companyJoinedOn;
    }

    public Driver companyJoinedOn(LocalDate companyJoinedOn) {
        this.companyJoinedOn = companyJoinedOn;
        return this;
    }

    public void setCompanyJoinedOn(LocalDate companyJoinedOn) {
        this.companyJoinedOn = companyJoinedOn;
    }

    public LocalDate getCompanyLeftOn() {
        return companyLeftOn;
    }

    public Driver companyLeftOn(LocalDate companyLeftOn) {
        this.companyLeftOn = companyLeftOn;
        return this;
    }

    public void setCompanyLeftOn(LocalDate companyLeftOn) {
        this.companyLeftOn = companyLeftOn;
    }

    public byte[] getImage() {
        return image;
    }

    public Driver image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Driver imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getLicenceImage() {
        return licenceImage;
    }

    public Driver licenceImage(byte[] licenceImage) {
        this.licenceImage = licenceImage;
        return this;
    }

    public void setLicenceImage(byte[] licenceImage) {
        this.licenceImage = licenceImage;
    }

    public String getLicenceImageContentType() {
        return licenceImageContentType;
    }

    public Driver licenceImageContentType(String licenceImageContentType) {
        this.licenceImageContentType = licenceImageContentType;
        return this;
    }

    public void setLicenceImageContentType(String licenceImageContentType) {
        this.licenceImageContentType = licenceImageContentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public Driver remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getContractDoc() {
        return contractDoc;
    }

    public Driver contractDoc(byte[] contractDoc) {
        this.contractDoc = contractDoc;
        return this;
    }

    public void setContractDoc(byte[] contractDoc) {
        this.contractDoc = contractDoc;
    }

    public String getContractDocContentType() {
        return contractDocContentType;
    }

    public Driver contractDocContentType(String contractDocContentType) {
        this.contractDocContentType = contractDocContentType;
        return this;
    }

    public void setContractDocContentType(String contractDocContentType) {
        this.contractDocContentType = contractDocContentType;
    }

    public ToggleStatus getStatus() {
        return status;
    }

    public Driver status(ToggleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ToggleStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Driver createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Driver createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Driver lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Driver lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public Driver trips(Set<Trip> trips) {
        this.trips = trips;
        return this;
    }

    public Driver addTrip(Trip trip) {
        this.trips.add(trip);
        trip.setDriver(this);
        return this;
    }

    public Driver removeTrip(Trip trip) {
        this.trips.remove(trip);
        trip.setDriver(null);
        return this;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        return id != null && id.equals(((Driver) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", licenceNumber=" + getLicenceNumber() +
            ", dob='" + getDob() + "'" +
            ", companyJoinedOn='" + getCompanyJoinedOn() + "'" +
            ", companyLeftOn='" + getCompanyLeftOn() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", licenceImage='" + getLicenceImage() + "'" +
            ", licenceImageContentType='" + getLicenceImageContentType() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", contractDoc='" + getContractDoc() + "'" +
            ", contractDocContentType='" + getContractDocContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
