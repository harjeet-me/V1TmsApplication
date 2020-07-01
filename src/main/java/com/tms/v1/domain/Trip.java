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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tms.v1.domain.enumeration.COVEREDBY;
import com.tms.v1.domain.enumeration.LoadType;
import com.tms.v1.domain.enumeration.SizeEnum;
import com.tms.v1.domain.enumeration.StatusEnum;
import com.tms.v1.domain.enumeration.TripType;

/**
 * A Trip.
 */
@Entity
@Table(name = "trip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "trip")
public class Trip extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "custom_trip_number")
    private String customTripNumber;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "trip_type")
    private TripType tripType;

    @Column(name = "shipment_number")
    private String shipmentNumber;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "bol")
    private String bol;

    @Column(name = "pickup")
    private LocalDate pickup;

    @Column(name = "jhi_drop")
    private LocalDate drop;

    @Column(name = "current_location")
    private String currentLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "detention")
    private Long detention;

    @Column(name = "chasis_in_time")
    private Instant chasisInTime;

    @Lob
    @Column(name = "order_document")
    private byte[] orderDocument;

    @Column(name = "order_document_content_type")
    private String orderDocumentContentType;

    @Lob
    @Column(name = "pod")
    private byte[] pod;

    @Column(name = "pod_content_type")
    private String podContentType;

    @Column(name = "hazmat")
    private Boolean hazmat;

    @Column(name = "refrigerated")
    private Boolean refrigerated;

    @Column(name = "liftgate")
    private Boolean liftgate;

    @Column(name = "recieved_by")
    private String recievedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "covered_by")
    private COVEREDBY coveredBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "load_type")
    private LoadType loadType;

    @Enumerated(EnumType.STRING)
    @Column(name = "container_size")
    private SizeEnum containerSize;

    @Column(name = "numbers_of_container")
    private Integer numbersOfContainer;

    @Column(name = "comments")
    private String comments;

    @Column(name = "auto_generate_invoice")
    private Boolean autoGenerateInvoice;

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

    @OneToMany(mappedBy = "trip")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "trip",fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Container> containers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("trippicks")
    private Location pickupLocation;

    @ManyToOne
    @JsonIgnoreProperties("tripdrops")
    private Location dropLocation;

    @ManyToOne
    @JsonIgnoreProperties("loadOrders")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("trips")
    private Driver driver;

    @ManyToOne
    @JsonIgnoreProperties("trips")
    private Equipment equipment;

    @ManyToOne
    @JsonIgnoreProperties("loadOrders")
    private Carrier carrier;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomTripNumber() {
        return customTripNumber;
    }

    public Trip customTripNumber(String customTripNumber) {
        this.customTripNumber = customTripNumber;
        return this;
    }

    public void setCustomTripNumber(String customTripNumber) {
        this.customTripNumber = customTripNumber;
    }

    public String getDescription() {
        return description;
    }

    public Trip description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TripType getTripType() {
        return tripType;
    }

    public Trip tripType(TripType tripType) {
        this.tripType = tripType;
        return this;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public Trip shipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
        return this;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Trip orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBol() {
        return bol;
    }

    public Trip bol(String bol) {
        this.bol = bol;
        return this;
    }

    public void setBol(String bol) {
        this.bol = bol;
    }

    public LocalDate getPickup() {
        return pickup;
    }

    public Trip pickup(LocalDate pickup) {
        this.pickup = pickup;
        return this;
    }

    public void setPickup(LocalDate pickup) {
        this.pickup = pickup;
    }

    public LocalDate getDrop() {
        return drop;
    }

    public Trip drop(LocalDate drop) {
        this.drop = drop;
        return this;
    }

    public void setDrop(LocalDate drop) {
        this.drop = drop;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public Trip currentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
        return this;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Trip status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Long getDetention() {
        return detention;
    }

    public Trip detention(Long detention) {
        this.detention = detention;
        return this;
    }

    public void setDetention(Long detention) {
        this.detention = detention;
    }

    public Instant getChasisInTime() {
        return chasisInTime;
    }

    public Trip chasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
        return this;
    }

    public void setChasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
    }

    public byte[] getOrderDocument() {
        return orderDocument;
    }

    public Trip orderDocument(byte[] orderDocument) {
        this.orderDocument = orderDocument;
        return this;
    }

    public void setOrderDocument(byte[] orderDocument) {
        this.orderDocument = orderDocument;
    }

    public String getOrderDocumentContentType() {
        return orderDocumentContentType;
    }

    public Trip orderDocumentContentType(String orderDocumentContentType) {
        this.orderDocumentContentType = orderDocumentContentType;
        return this;
    }

    public void setOrderDocumentContentType(String orderDocumentContentType) {
        this.orderDocumentContentType = orderDocumentContentType;
    }

    public byte[] getPod() {
        return pod;
    }

    public Trip pod(byte[] pod) {
        this.pod = pod;
        return this;
    }

    public void setPod(byte[] pod) {
        this.pod = pod;
    }

    public String getPodContentType() {
        return podContentType;
    }

    public Trip podContentType(String podContentType) {
        this.podContentType = podContentType;
        return this;
    }

    public void setPodContentType(String podContentType) {
        this.podContentType = podContentType;
    }

    public Boolean isHazmat() {
        return hazmat;
    }

    public Trip hazmat(Boolean hazmat) {
        this.hazmat = hazmat;
        return this;
    }

    public void setHazmat(Boolean hazmat) {
        this.hazmat = hazmat;
    }

    public Boolean isRefrigerated() {
        return refrigerated;
    }

    public Trip refrigerated(Boolean refrigerated) {
        this.refrigerated = refrigerated;
        return this;
    }

    public void setRefrigerated(Boolean refrigerated) {
        this.refrigerated = refrigerated;
    }

    public Boolean isLiftgate() {
        return liftgate;
    }

    public Trip liftgate(Boolean liftgate) {
        this.liftgate = liftgate;
        return this;
    }

    public void setLiftgate(Boolean liftgate) {
        this.liftgate = liftgate;
    }

    public String getRecievedBy() {
        return recievedBy;
    }

    public Trip recievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
        return this;
    }

    public void setRecievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
    }

    public COVEREDBY getCoveredBy() {
        return coveredBy;
    }

    public Trip coveredBy(COVEREDBY coveredBy) {
        this.coveredBy = coveredBy;
        return this;
    }

    public void setCoveredBy(COVEREDBY coveredBy) {
        this.coveredBy = coveredBy;
    }

    public LoadType getLoadType() {
        return loadType;
    }

    public Trip loadType(LoadType loadType) {
        this.loadType = loadType;
        return this;
    }

    public void setLoadType(LoadType loadType) {
        this.loadType = loadType;
    }

    public SizeEnum getContainerSize() {
        return containerSize;
    }

    public Trip containerSize(SizeEnum containerSize) {
        this.containerSize = containerSize;
        return this;
    }

    public void setContainerSize(SizeEnum containerSize) {
        this.containerSize = containerSize;
    }

    public Integer getNumbersOfContainer() {
        return numbersOfContainer;
    }

    public Trip numbersOfContainer(Integer numbersOfContainer) {
        this.numbersOfContainer = numbersOfContainer;
        return this;
    }

    public void setNumbersOfContainer(Integer numbersOfContainer) {
        this.numbersOfContainer = numbersOfContainer;
    }

    public String getComments() {
        return comments;
    }

    public Trip comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean isAutoGenerateInvoice() {
        return autoGenerateInvoice;
    }

    public Trip autoGenerateInvoice(Boolean autoGenerateInvoice) {
        this.autoGenerateInvoice = autoGenerateInvoice;
        return this;
    }

    public void setAutoGenerateInvoice(Boolean autoGenerateInvoice) {
        this.autoGenerateInvoice = autoGenerateInvoice;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Trip createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Trip createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Trip lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Trip lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Trip invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Trip addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setTrip(this);
        return this;
    }

    public Trip removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setTrip(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<Container> getContainers() {
        return containers;
    }

    public Trip containers(Set<Container> containers) {
        this.containers = containers;
        return this;
    }

    public Trip addContainer(Container container) {
        this.containers.add(container);
        container.setTrip(this);
        return this;
    }

    public Trip removeContainer(Container container) {
        this.containers.remove(container);
        container.setTrip(null);
        return this;
    }

    public void setContainers(Set<Container> containers) {
        this.containers = containers;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Trip pickupLocation(Location location) {
        this.pickupLocation = location;
        return this;
    }

    public void setPickupLocation(Location location) {
        this.pickupLocation = location;
    }

    public Location getDropLocation() {
        return dropLocation;
    }

    public Trip dropLocation(Location location) {
        this.dropLocation = location;
        return this;
    }

    public void setDropLocation(Location location) {
        this.dropLocation = location;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Trip customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public Trip driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Trip equipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public Trip carrier(Carrier carrier) {
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
        if (!(o instanceof Trip)) {
            return false;
        }
        return id != null && id.equals(((Trip) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trip{" +
            "id=" + getId() +
            ", customTripNumber='" + getCustomTripNumber() + "'" +
            ", description='" + getDescription() + "'" +
            ", tripType='" + getTripType() + "'" +
            ", shipmentNumber='" + getShipmentNumber() + "'" +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", bol='" + getBol() + "'" +
            ", pickup='" + getPickup() + "'" +
            ", drop='" + getDrop() + "'" +
            ", currentLocation='" + getCurrentLocation() + "'" +
            ", status='" + getStatus() + "'" +
            ", detention=" + getDetention() +
            ", chasisInTime='" + getChasisInTime() + "'" +
            ", orderDocument='" + getOrderDocument() + "'" +
            ", orderDocumentContentType='" + getOrderDocumentContentType() + "'" +
            ", pod='" + getPod() + "'" +
            ", podContentType='" + getPodContentType() + "'" +
            ", hazmat='" + isHazmat() + "'" +
            ", refrigerated='" + isRefrigerated() + "'" +
            ", liftgate='" + isLiftgate() + "'" +
            ", recievedBy='" + getRecievedBy() + "'" +
            ", coveredBy='" + getCoveredBy() + "'" +
            ", loadType='" + getLoadType() + "'" +
            ", containerSize='" + getContainerSize() + "'" +
            ", numbersOfContainer=" + getNumbersOfContainer() +
            ", comments='" + getComments() + "'" +
            ", autoGenerateInvoice='" + isAutoGenerateInvoice() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
