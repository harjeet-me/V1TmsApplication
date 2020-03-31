package com.tms.v1.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.v1.domain.InvoiceItem;

/**
 * Spring Data  repository for the InvoiceItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

	public Set<InvoiceItem> findByInvoice_Id(Long id);
}
