package com.tms.v1.service.mapper;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.tms.v1.service.CompanyProfileService;
import com.tms.v1.service.CustomerService;
import com.tms.v1.service.EmailService;
import com.tms.v1.service.InvoiceItemService;
import com.tms.v1.service.MailService;
import com.tms.v1.service.TransactionsRecordService;
import com.tms.v1.service.impl.InvoiceServiceImpl;
import com.tms.v1.service.impl.JasperInvoiceReportServiceImpl;

@Service
@Transactional
public class InvoiceUtil {
	
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
	@Autowired
	private  InvoiceRepository invoiceRepository;

	public static Invoice getInvoiceFromCustomer(Customer customer) {

		Invoice invoice = new Invoice();

		/*
		 * invoice.setCreatedBy("SYSTEM"); invoice.setCreatedOn(Instant.now());
		 * invoice.setUpdatedBy("SYSTEM"); invoice.setUpdatedOn(Instant.now());
		 */

		invoice.setPayterm(customer.getPayterms());
		invoice.setStatus(InvoiceStatus.DRAFT);
		invoice.setCustomer(customer);

		return invoice;
	}

	/*
	 * public static Invoice getInvoiceWithInvoiceItem(Invoice invoice) {
	 * 
	 * if (invoice.getItemName1() != null) {
	 * 
	 * InvoiceItem item = new InvoiceItem();
	 * item.setItemName(invoice.getItemName1());
	 * item.setDescription(invoice.getItemDesc1());
	 * item.setQty(invoice.getItemQty1()); item.setPrice(invoice.getItemPrice1());
	 * item.setTotal(invoice.getItemTotal1()); invoice.getInvoiceItems().add(item);
	 * }
	 * 
	 * if (invoice.getItemName2() != null) {
	 * 
	 * InvoiceItem item = new InvoiceItem();
	 * item.setItemName(invoice.getItemName2());
	 * item.setDescription(invoice.getItemDesc2());
	 * item.setQty(invoice.getItemQty2()); item.setPrice(invoice.getItemPrice2());
	 * item.setTotal(invoice.getItemTotal2()); invoice.getInvoiceItems().add(item);
	 * }
	 * 
	 * if (invoice.getItemName3() != null) {
	 * 
	 * InvoiceItem item = new InvoiceItem();
	 * item.setItemName(invoice.getItemName3());
	 * item.setDescription(invoice.getItemDesc3());
	 * item.setQty(invoice.getItemQty3()); item.setPrice(invoice.getItemPrice3());
	 * item.setTotal(invoice.getItemTotal3()); invoice.getInvoiceItems().add(item);
	 * }
	 * 
	 * return invoice; }
	 */
	
	
	
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
			invoice.setInvoiceNo("IVN-" + (invoiceRepository.getMaxId().get() + 1));
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
				//invoice.setInvoicePdfContentType("text/html");
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
			if(invoice.getTrip().getOrderDocument()!=null)
			{
				if(invoice.getTrip().getOrderDocumentContentType()=="application/pdf") {
				fileList.add(invoice.getTrip().getOrderDocument());
				}
				}
			else {
				
				// attach file directly to email 
				
			}
		}  
		 
		
		
		if(invoice.getNotification()!=null) {
			email.setId(invoice.getNotification().getId());
			
		}
		
		  if(invoice.getStatus()==InvoiceStatus.GENERATED) {
		//  invoice.setInvoicePdf(mailService.mergePdf(fileList));
		  
		  email.setAttachmentContentType(invoice.getInvoicePdfContentType());
		  email.setHtmlBody(true);
		  email.setMessage("<h2> PLease Find invoice  attached  </h2>");
		  email.setAttachmentName(email.getSubject());
		  email.setAttachment(invoice.getInvoicePdf()); 
		  
		  if(invoice.getNotification()!=null ) {
			 // already exist email for invoice  
			 email.setId(invoice.getNotification().getId());
		  }
		  emailService.save(email);
		  invoice.setNotification(email); }
		 
		
		if (invoice.getStatus()!=null && invoice.getStatus()==InvoiceStatus.GENERATED) {			
			TransactionsRecord transactionsRecord = new TransactionsRecord();
			transactionsRecord.setCustomer(invoice.getCustomer());
		//	transactionsRecord.setAccount(invoice.getCustomer().getAccounts());
			transactionsRecord.setTxType(TransactionType.INVOICE);
			transactionsRecord.description("INVOICE CREATED -"+invoice.getInvoiceNo());
			transactionsRecord.setTxAmmount(invoice.getInvoiceTotal());			
			recordService.save(transactionsRecord);			
		
		}
		
		Invoice result = invoiceRepository.save(invoice);
		
		return result;
	}

}
