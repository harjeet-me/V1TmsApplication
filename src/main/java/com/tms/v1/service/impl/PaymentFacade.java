/**
 * 
 */
package com.tms.v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.v1.domain.Payment;
import com.tms.v1.service.InvoiceService;




/**
 * @author harjeet
 *
 */

@Service
public class PaymentFacade {
	
	@Autowired
	InvoiceService invoiceService;
	
	public Payment payInvoice(Payment payment) {
		 String invoiceNumber=payment.getInvoiceNo();
		
		
		return payment;
		
	}

}
