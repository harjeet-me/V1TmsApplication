package com.tms.v1.service.impl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.tms.v1.domain.CompanyProfile;
import com.tms.v1.domain.Customer;
import com.tms.v1.domain.Invoice;
import com.tms.v1.domain.InvoiceItem;
import com.tms.v1.domain.ProductItem;
import com.tms.v1.domain.enumeration.InvoiveRef;
import com.tms.v1.service.InvoiceItemService;
import com.tms.v1.service.ProductItemService;

import liquibase.pro.packaged.in;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperInvoiceReportServiceImpl {
	private final Logger log = LoggerFactory.getLogger(JasperInvoiceReportServiceImpl.class);

	@Autowired
	InvoiceItemService invoiceItemService;

	@Autowired
	ProductItemService productItemService;

	public byte[] generateReport(Customer customer, Invoice invoice, CompanyProfile companyProfile) throws Exception {
		Set<InvoiceItem> itemList = new HashSet<>();

		itemList.add(new InvoiceItem(null,"dummy","dummy",0.0,0.0));
		Set<InvoiceItem> fromDbItem=   invoiceItemService.findByInvoiceId(invoice.getId());
		if (fromDbItem.size() == 0) {
			throw new IllegalStateException("line item are null or size zero");
		}
		List<InvoiceItem> newitemList = new ArrayList<>(invoice.getInvoiceItems());
		
		newitemList.removeIf(item -> item.getItemName() == null || item.getItemName() == "");
		
		itemList.addAll(newitemList);
		/*
		 * for (Iterator iterator = fromDbItem.iterator(); iterator.hasNext();) {
		 * InvoiceItem invoiceItem = (InvoiceItem) iterator.next();
		 * 
		 * invoiceItem.setDescription(invoiceItem.getItemName() + "\n " +
		 * invoiceItem.getDescription());
		 * 
		 * itemList.add(invoiceItem);
		 * 
		 * }
		 */		
		log.debug(" Item List", itemList);
		File resource = new ClassPathResource("report/InvoiceRpt.jrxml").getFile();
		InputStream targetStream = new FileInputStream(resource);

		JasperReport jasperReport = JasperCompileManager.compileReport(targetStream);

		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(itemList);

		// Add parameters
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("itemDataSource", jrBeanCollectionDataSource);
		String customerAdd= customer.getCompany() +" \n Attn : " +customer.getFirstName() + " " + customer.getLastName()
        +"\n"+customer.getAddress() +" "+ customer.getStreetAddress() +"\n "+"" + customer.getCity() + " , " + customer.getStateProvince() + " , " + customer.getPostalCode()
        +" \nPh : " + customer.getPhoneNumber() + " " + customer.getPhoneNumberExtention()
        +"\nEmail :"+ customer.getEmail() 
        + "\nWebite : " + customer.getWebsite();
		
		parameters.put("customerName",invoice.getCustomerInfo()
				);
		parameters.put("oraganization", customer.getCompany());
		parameters.put("customerAddress", customer.getAddress());
		parameters.put("customerAddress2", customer.getStreetAddress());
		parameters.put("customerCitySateZip",
				"" + customer.getCity() + " , " + customer.getStateProvince() + " , " + customer.getPostalCode());
		parameters.put("customerPhone", "" + customer.getPhoneNumber() + " " + customer.getPhoneNumberExtention());
		parameters.put("customerWeb", customer.getEmail() + " , " + customer.getWebsite());

		parameters.put("ProfileCompany", companyProfile.getCompany());
		// parameters.put("ProfileAddress", companyProfile.getAddress());
		
		String profileAdd=companyProfile.getAddress() +" "+ companyProfile.getStreetAddress() +"\n"+companyProfile.getCity() + " , " + companyProfile.getStateProvince() + " , " + companyProfile.getPostalCode()
        +" \nPhone :" + companyProfile.getPhoneNumber() 
        +"\nEmail : "+ companyProfile.getEmail() 
        + "\nWebsite : " + companyProfile.getWebsite();
		
		parameters.put("ProfileAddress", profileAdd.toUpperCase());
		
		parameters.put("ProfileStreetAddress", companyProfile.getStreetAddress());
		parameters.put("ProfileCityStateZip", companyProfile.getCity() + " , " + companyProfile.getStateProvince()
				+ " , " + companyProfile.getPostalCode());
		parameters.put("ProfilePhone", "" + companyProfile.getPhoneNumber());
		parameters.put("ProfileWeb", companyProfile.getEmail());

		String invoiceNum = invoice.getInvoiceNo();

		if (invoiceNum == null || invoiceNum =="") {
			invoiceNum = "INV"+invoice.getId();
		}

		parameters.put("invoiceNo", invoiceNum);
		parameters.put("PO", invoice.getOrderNo());
		parameters.put("BookingNo", invoice.getOrderNo());
		parameters.put("containerNo", customer.getStreetAddress());
		parameters.put("refNo", getRefString(invoice.getRefOption1(), invoice.getRefValue1()));
		parameters.put("ref2Value", getRefString(invoice.getRefOption2(), invoice.getRefValue2()));
		parameters.put("ref3Value", getRefString(invoice.getRefOption3(), invoice.getRefValue3()));

		parameters.put("invoiceTotal", invoice.getCurrency() + " " + invoice.getInvoiceTotal());
		parameters.put("payterms", companyProfile.getCompany() + " \n " + companyProfile.getAddress() + " " +

				companyProfile.getStreetAddress() + " " + companyProfile.getCity() + " "
				+ companyProfile.getStateProvince() + " " +

				companyProfile.getPostalCode());
		parameters.put("invoiceDueDate", invoice.getInvoiceDueDate().toString());
		parameters.put("invoiceDate", invoice.getInvoiceDate().toString());


		// Fill the report
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);

		// Export the report to a PDF file
		// JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath +
		// "\\Emp-Rpt.pdf");

		// JasperExportManager.exportReportToHtmlFile(jasperPrint,"Emp-Rpt.html");
		
		final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		
		return outStream.toByteArray();

	}

	static Set<InvoiceItem> getInvoiceItemList(Set<ProductItem> productItems) {
		Set<InvoiceItem> iItem = new HashSet<InvoiceItem>();
		for (ProductItem item : productItems) {

			iItem.add(new InvoiceItem(null, item.getItemName(), item.getDescription(), item.getPrice(), item.getPrice()));

		}
		return iItem;

	}

	String getRefString(InvoiveRef ref, String Value) {
		if (ref != null && Value != null) {
			return ref.toString() + " " + Value;
		}
		return "NA";
	}
}