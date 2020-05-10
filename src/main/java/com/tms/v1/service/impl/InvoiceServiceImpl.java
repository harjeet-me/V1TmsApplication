package com.tms.v1.service.impl;

import com.tms.v1.service.InvoiceService;
import com.tms.v1.domain.Invoice;
import com.tms.v1.repository.InvoiceRepository;
import com.tms.v1.repository.search.InvoiceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.tms.v1.domain.Customer;
import com.tms.v1.domain.Email;
import com.tms.v1.domain.Invoice;
import com.tms.v1.domain.InvoiceItem;
import com.tms.v1.domain.TransactionsRecord;
import com.tms.v1.domain.enumeration.CURRENCY;
import com.tms.v1.domain.enumeration.InvoiceStatus;
import com.tms.v1.domain.enumeration.TransactionType;
import com.tms.v1.repository.InvoiceItemRepository;
import com.tms.v1.repository.InvoiceRepository;
import com.tms.v1.repository.search.InvoiceSearchRepository;
import com.tms.v1.service.CompanyProfileService;
import com.tms.v1.service.CustomerService;
import com.tms.v1.service.EmailService;
import com.tms.v1.service.InvoiceItemService;
import com.tms.v1.service.InvoiceService;
import com.tms.v1.service.MailService;
import com.tms.v1.service.TransactionsRecordService;
import java.util.Optional;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Invoice}.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {


	private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	@Autowired
	InvoiceItemRepository invoiceItemRepository;

	@Autowired
	MailService mailService;

	@Autowired
	JasperInvoiceReportServiceImpl jasperInvoiceReportServiceImpl;

	@Autowired
	CustomerService customerService;

	@Autowired
	InvoiceItemService invoiceItemServiceImpl;

	@Autowired
	CompanyProfileService companyProfileService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	TransactionsRecordService recordService;

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
		// invoice = InvoiceUtil.getInvoiceWithInvoiceItem(invoice);
		Set<InvoiceItem> invoiceLineItem = invoice.getInvoiceItems();
		Set<InvoiceItem> savedLineItem = invoice.getInvoiceItems();
		log.debug("Request to save Invoice : {}", invoiceLineItem);
		if (invoice.getCurrency() == null) {
			invoice.setCurrency(CURRENCY.USD);
		}
		if (invoice.getId() == null && (invoice.getInvoiceNo() == null || invoice.getInvoiceNo() == "")) {
			invoice.setInvoiceNo("IVN-" + (this.getMaxInvoiceId().get() + 1));
		} else if (invoice.getId() != null && (invoice.getInvoiceNo() == null || invoice.getInvoiceNo() == "")) {
			invoice.setInvoiceNo("IVN-" + invoice.getId());
		}
		if(invoice.getCustomer()==null) {
			throw new IllegalStateException("Customer is not selected");
		}
		
		invoice = invoiceRepository.save(invoice);
		Customer customer = customerService.findOne(invoice.getCustomer().getId()).get();
		try {
			for (InvoiceItem invoiceItem : invoiceLineItem) {
				invoiceItem.setInvoice(invoice);
				log.debug("save invoice item by harjeet", invoiceItem);
				savedLineItem.add(invoiceItemServiceImpl.save(invoiceItem));
			}
		
			invoice.setCurrency(customer.getPreffredCurrency());
			if (invoice.getInvoiceDate() == null) {
				invoice.setInvoiceDate(LocalDate.now());
			}
			if (invoice.getInvoiceDueDate() == null) {
				invoice.setInvoiceDueDate(invoice.getInvoiceDate());
			}

			invoice.setInvoiceItems(savedLineItem);
			if (invoice.getStatus() != null && invoice.getStatus() == InvoiceStatus.GENERATED) {
				invoice.setInvoicePdf(jasperInvoiceReportServiceImpl.generateReport(customer, invoice,
						companyProfileService.findOne(1L).get()));
				invoice.setInvoiceItems(null);
				invoice.setInvoicePdfContentType("application/pdf");
			}

		} catch (Exception e) {
			throw new IllegalStateException("Exception Occured Generating Invoice ", e);
		}
		
		
		Email email = new Email();
		email.setUserto(customer.getEmail());
		email.setSubject("Invoice_"+customer.getCompany() + "_"+invoice.getInvoiceNo());
		
		List<byte[] > fileList= new ArrayList<>();
		
		if(invoice.getTrip()==null) {
			Log.debug(invoice.getTrip());
		}
		
		if(invoice.getInvoicePdf()!=null) {
			 fileList.add(invoice.getInvoicePdf());
		}
		
	   
		if(invoice.getTrip()!=null)
		{
			if(invoice.getTrip().getPod()!=null) {
				fileList.add(invoice.getTrip().getPod());
			}
			if(invoice.getTrip().getOrderDocument()!=null) {
				fileList.add(invoice.getTrip().getOrderDocument());
			}
		}  
		 
		
		
		if(invoice.getNotification()!=null) {
			email.setId(invoice.getNotification().getId());
			
		}
		if(invoice.getStatus()==InvoiceStatus.GENERATED) {
			invoice.setInvoicePdf(mailService.mergePdf(fileList));
			
			email.setAttachmentContentType(invoice.getInvoicePdfContentType());
			email.setHtmlBody(true);
			email.setMessage("<h2> PLease Find invoice  attached  </h2>");
			email.setAttachmentName(email.getSubject());
			email.setAttachment(invoice.getInvoicePdf());
			emailService.save(email);
			invoice.setNotification(email); 
		}
		
		if (invoice.getStatus()!=null && invoice.getStatus()==InvoiceStatus.GENERATED) {
			
			TransactionsRecord transactionsRecord = new TransactionsRecord();
			transactionsRecord.setCustomer(invoice.getCustomer());
			transactionsRecord.setAccount(invoice.getCustomer().getAccounts());
			transactionsRecord.setTxType(TransactionType.CREDIT);
			transactionsRecord.description("INVOICE CREATED -"+invoice.getInvoiceNo());
			transactionsRecord.setTxAmmount(invoice.getInvoiceTotal());
			
			recordService.save(transactionsRecord);
			
		
		}
		
		Invoice result = invoiceRepository.save(invoice);
		invoiceSearchRepository.save(result);
		
		
        
		
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
	public Optional<Long> getMaxInvoiceId() {
		return invoiceRepository.getMaxId();
	}
}
