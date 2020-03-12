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

import com.tms.v1.domain.enumeration.EquipmentEnum;

import com.tms.v1.domain.enumeration.ToggleStatus;

/**
 * A Equipment.
 */
@Entity
@Table(name = "equipment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "equipment")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enumber")
    private String enumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EquipmentEnum type;

    @Column(name = "ownershiptype")
    private String ownershiptype;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToggleStatus status;

    @Column(name = "vin")
    private String vin;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "description")
    private String description;

    @Column(name = "year")
    private String year;

    @Column(name = "year_purchased")
    private String yearPurchased;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    @Column(name = "license_plate_expiration")
    private LocalDate licensePlateExpiration;

    @Column(name = "inspection_sticker_expiration")
    private LocalDate inspectionStickerExpiration;

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
    private Insurance insurance;

    @OneToMany(mappedBy = "equipment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trip> trips = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnumber() {
        return enumber;
    }

    public Equipment enumber(String enumber) {
        this.enumber = enumber;
        return this;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public EquipmentEnum getType() {
        return type;
    }

    public Equipment type(EquipmentEnum type) {
        this.type = type;
        return this;
    }

    public void setType(EquipmentEnum type) {
        this.type = type;
    }

    public String getOwnershiptype() {
        return ownershiptype;
    }

    public Equipment ownershiptype(String ownershiptype) {
        this.ownershiptype = ownershiptype;
        return this;
    }

    public void setOwnershiptype(String ownershiptype) {
        this.ownershiptype = ownershiptype;
    }

    public ToggleStatus getStatus() {
        return status;
    }

    public Equipment status(ToggleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ToggleStatus status) {
        this.status = status;
    }

    public String getVin() {
        return vin;
    }

    public Equipment vin(String vin) {
        this.vin = vin;
        return this;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public Equipment make(String make) {
        this.make = make;
        return this;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public Equipment model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public Equipment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public Equipment year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYearPurchased() {
        return yearPurchased;
    }

    public Equipment yearPurchased(String yearPurchased) {
        this.yearPurchased = yearPurchased;
        return this;
    }

    public void setYearPurchased(String yearPurchased) {
        this.yearPurchased = yearPurchased;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public Equipment licensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
        return this;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public LocalDate getLicensePlateExpiration() {
        return licensePlateExpiration;
    }

    public Equipment licensePlateExpiration(LocalDate licensePlateExpiration) {
        this.licensePlateExpiration = licensePlateExpiration;
        return this;
    }

    public void setLicensePlateExpiration(LocalDate licensePlateExpiration) {
        this.licensePlateExpiration = licensePlateExpiration;
    }

    public LocalDate getInspectionStickerExpiration() {
        return inspectionStickerExpiration;
    }

    public Equipment inspectionStickerExpiration(LocalDate inspectionStickerExpiration) {
        this.inspectionStickerExpiration = inspectionStickerExpiration;
        return this;
    }

    public void setInspectionStickerExpiration(LocalDate inspectionStickerExpiration) {
        this.inspectionStickerExpiration = inspectionStickerExpiration;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Equipment createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Equipment createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Equipment updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Equipment updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public Equipment insurance(Insurance insurance) {
        this.insurance = insurance;
        return this;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public Equipment trips(Set<Trip> trips) {
        this.trips = trips;
        return this;
    }

    public Equipment addTrip(Trip trip) {
        this.trips.add(trip);
        trip.setEquipment(this);
        return this;
    }

    public Equipment removeTrip(Trip trip) {
        this.trips.remove(trip);
        trip.setEquipment(null);
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
        if (!(o instanceof Equipment)) {
            return false;
        }
        return id != null && id.equals(((Equipment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Equipment{" +
            "id=" + getId() +
            ", enumber='" + getEnumber() + "'" +
            ", type='" + getType() + "'" +
            ", ownershiptype='" + getOwnershiptype() + "'" +
            ", status='" + getStatus() + "'" +
            ", vin='" + getVin() + "'" +
            ", make='" + getMake() + "'" +
            ", model='" + getModel() + "'" +
            ", description='" + getDescription() + "'" +
            ", year='" + getYear() + "'" +
            ", yearPurchased='" + getYearPurchased() + "'" +
            ", licensePlateNumber='" + getLicensePlateNumber() + "'" +
            ", licensePlateExpiration='" + getLicensePlateExpiration() + "'" +
            ", inspectionStickerExpiration='" + getInspectionStickerExpiration() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
