package com.tms.v1.service.impl;

import com.tms.v1.service.CustomerService;
import com.tms.v1.service.InvoiceService;
import com.tms.v1.service.TripService;

import liquibase.pro.packaged.in;
import liquibase.pro.packaged.it;

import com.tms.v1.domain.Customer;
import com.tms.v1.domain.Invoice;
import com.tms.v1.domain.InvoiceItem;
import com.tms.v1.domain.Trip;
import com.tms.v1.domain.enumeration.InvoiceStatus;
import com.tms.v1.domain.enumeration.InvoiveRef;
import com.tms.v1.domain.enumeration.StatusEnum;
import com.tms.v1.repository.TripRepository;
import com.tms.v1.repository.search.TripSearchRepository;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Trip}.
 */
@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final Logger log = LoggerFactory.getLogger(TripServiceImpl.class);

    private final TripRepository tripRepository;
    
    @Autowired
    public InvoiceService invoiceService;
    @Autowired
    public CustomerService customerService;

    private final TripSearchRepository tripSearchRepository;

    public TripServiceImpl(TripRepository tripRepository, TripSearchRepository tripSearchRepository) {
        this.tripRepository = tripRepository;
        this.tripSearchRepository = tripSearchRepository;
    }

    /**
     * Save a trip.
     *
     * @param trip the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Trip save(Trip trip) {
        log.debug("Request to save Trip : {}", trip);
        
        Trip result = tripRepository.save(trip);
        tripSearchRepository.save(result);
        
        if(trip.getStatus()!=null && trip.getStatus()==StatusEnum.COMPLETED) {
        	Invoice invoiceDraft= new Invoice();        	
        	invoiceDraft.setTrip(result);
        	invoiceDraft.setStatus(InvoiceStatus.DRAFT);
        	invoiceDraft.setCustomer(trip.getCustomer());   
        	
        	if(trip.getOrderNumber()!=null) {
        		invoiceDraft.setOrderNo(trip.getOrderNumber());
        	}
        	if(trip.getShipmentNumber()!=null) {
        		invoiceDraft.setRefOption1(InvoiveRef.SHIPMENT_NO);
            	invoiceDraft.setRefValue1(trip.getShipmentNumber());
        	}  
        	
        	List<Invoice> invoices =invoiceService.findByTrip_Id(trip.getId());
        	
        	if(invoices!=null && invoices.size()==1 && invoices.get(0).getTrip().getId()==trip.getId()) {
        		invoiceDraft.setId(invoices.get(0).getId());
        	}
        	
        	Log.debug("found invoices by trip Id"+invoices);
        	
        	Customer customer =customerService.findOne(trip.getCustomer().getId()).get();
        	
        	InvoiceItem item=new InvoiceItem(null, "LOAD MOVE",result.getPickupLocation().getCity()+" TO  "+result.getDropLocation().getCity() ,0.0,0.0 );
        	HashSet<InvoiceItem> itemSet=new HashSet<>(InvoiceReportServiceImpl.getInvoiceItemList(customer.getCharges()));
        	itemSet.add(item);
        	invoiceDraft.setInvoiceItems(itemSet);
        	invoiceService.save(invoiceDraft);
        	
        	
        	
        }
        return result;
    }

    /**
     * Get all the trips.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trip> findAll(Pageable pageable) {
        log.debug("Request to get all Trips");
        return tripRepository.findAll(pageable);
    }

    /**
     * Get one trip by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Trip> findOne(Long id) {
        log.debug("Request to get Trip : {}", id);
        return tripRepository.findById(id);
    }

    /**
     * Delete the trip by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trip : {}", id);
        tripRepository.deleteById(id);
        tripSearchRepository.deleteById(id);
    }

    /**
     * Search for the trip corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trip> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Trips for query {}", query);
        return tripSearchRepository.search(queryStringQuery(query), pageable);    }
}
