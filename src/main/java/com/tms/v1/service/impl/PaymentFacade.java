/**
 * 
 */
package com.tms.v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.v1.domain.Payment;
import com.tms.v1.domain.TransactionsRecord;
import com.tms.v1.domain.enumeration.InvoiceStatus;
import com.tms.v1.domain.enumeration.TransactionType;
import com.tms.v1.service.InvoiceService;
import com.tms.v1.service.TransactionsRecordService;




/**
 * @author harjeet
 *
 */

@Service
public class PaymentFacade {
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	TransactionsRecordService recordService;
	
	public Payment payInvoice(Payment payment) {
		 String invoiceNumber=payment.getInvoiceNo();
		 
		 if (payment.getAmmount()!=null && payment.getAmmount()>0) {			
				TransactionsRecord transactionsRecord = new TransactionsRecord();
				transactionsRecord.setCustomer(payment.getCustomer());
			//	transactionsRecord.setAccount(invoice.getCustomer().getAccounts());
				transactionsRecord.setTxType(TransactionType.PAYMENT);
				transactionsRecord.description("PAYMENT RECIEVED -"+ payment.getId() + " " + payment.getPayRefNo());
				transactionsRecord.setTxAmmount(payment.getAmmount());			
				recordService.save(transactionsRecord);			
			
			}else {
				throw new IllegalStateException("payment amount shoulb greater than 0");
			}
		
		
		return payment;
		
	}

}
