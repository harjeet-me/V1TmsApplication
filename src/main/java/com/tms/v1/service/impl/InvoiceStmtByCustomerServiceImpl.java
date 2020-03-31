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
public class InvoiceStmtByCustomerServiceImpl {
	private final Logger log = LoggerFactory.getLogger(InvoiceStmtByCustomerServiceImpl.class);

	@Autowired
	InvoiceItemService invoiceItemService;

	@Autowired
	ProductItemService productItemService;

	public byte[] generateReport(List<Invoice> invoices) throws Exception {
		
		File resource = new ClassPathResource("report/invoiceStmtRp.jrxml").getFile();
		InputStream targetStream = new FileInputStream(resource);

		JasperReport jasperReport = JasperCompileManager.compileReport(targetStream);
	
		// Get your data source
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(invoices);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("itemDataSource", jrBeanCollectionDataSource);

		// Fill the report
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);

		// Export the report to a PDF file
		// JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath +
		// "\\Emp-Rpt.pdf");

		final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		System.out.println("Generated Successfully");
		
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
