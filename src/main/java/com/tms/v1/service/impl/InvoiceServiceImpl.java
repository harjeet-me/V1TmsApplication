package com.tms.v1.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tms.v1.domain.Customer;
import com.tms.v1.domain.Email;
import com.tms.v1.domain.Invoice;
import com.tms.v1.repository.InvoiceItemRepository;
import com.tms.v1.repository.InvoiceRepository;
import com.tms.v1.repository.search.InvoiceSearchRepository;
import com.tms.v1.service.EmailService;
import com.tms.v1.service.InvoiceService;
import com.tms.v1.service.mapper.InvoiceUtil;

/**
 * Service Implementation for managing {@link Invoice}.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

	private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	@Autowired
	private InvoiceUtil invoiceUtil;
	
	@Autowired
	InvoiceItemRepository invoiceItemRepository;
	
	@Autowired
	EmailService emailService;

	private final InvoiceRepository invoiceRepository;

	private final InvoiceSearchRepository invoiceSearchRepository;

	public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceSearchRepository invoiceSearchRepository) {
		this.invoiceRepository = invoiceRepository;
		this.invoiceSearchRepository = invoiceSearchRepository;
	}

	/**
	 * Save a invoice.
	 *
	 * @param invoice the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public Invoice save(Invoice invoice) { 
		log.debug("Request to save Invoice : {}", invoice);
		Invoice result = invoiceUtil.save(invoice);
		/*
		 * Email email = result.getNotification(); Customer customer= new Customer();
		 * customer.setId(invoice.getCustomer().getId()); email.setCustomer(customer);
		 * emailService.save(email);
		 */		//invoiceSearchRepository.save(result);
		
		return result;
	}

	/**
	 * Get all the invoices.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Invoice> findAll(Pageable pageable) {
		log.debug("Request to get all Invoices");
		return invoiceRepository.findAll(pageable);
	}

	/**
	 * Get one invoice by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Invoice> findOne(Long id) {
		log.debug("Request to get Invoice : {}", id);
		Optional<Invoice> inOptional = invoiceRepository.findById(id);
		inOptional.get().setInvoiceItems(invoiceItemRepository.findByInvoice_Id(id));
		return invoiceRepository.findById(id);
	}

	/**
	 * Delete the invoice by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Invoice : {}", id);
		invoiceRepository.deleteById(id);
		invoiceSearchRepository.deleteById(id);
	}

	/**
	 * Search for the invoice corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Invoice> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Invoices for query {}", query);
		return invoiceSearchRepository.search(queryStringQuery(query), pageable);
	}

	@Override
	public List<Invoice> findByTrip_Id(Long tripId) {
		return invoiceRepository.findByTrip_Id(tripId);
	}

	@Override
	public List<Invoice> findByCustomer_IdAndInvoiceDateBetween(Long customerId, LocalDate invoiceDateStart,
			LocalDate invoiceDateEnd) {
		return invoiceRepository.findByCustomer_IdAndInvoiceDateBetween(customerId, invoiceDateStart, invoiceDateEnd);
	}
	
	@Override
	public Set<Invoice> findByCustomerId(Long customerId) {
		return invoiceRepository.findByCustomerId(customerId);
	}

	@Override
	public Optional<Long> getMaxInvoiceId() {
		return invoiceRepository.getMaxId();
	}
}
