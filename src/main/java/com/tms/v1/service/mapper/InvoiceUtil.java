package com.tms.v1.service.mapper;

import java.time.Instant;

import com.tms.v1.domain.Customer;
import com.tms.v1.domain.Invoice;
import com.tms.v1.domain.InvoiceItem;
import com.tms.v1.domain.enumeration.InvoiceStatus;

public class InvoiceUtil {

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
}
