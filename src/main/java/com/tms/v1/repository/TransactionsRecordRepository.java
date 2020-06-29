package com.tms.v1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.v1.domain.TransactionsRecord;

/**
 * Spring Data  repository for the TransactionsRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionsRecordRepository extends JpaRepository<TransactionsRecord, Long> {
	public List<TransactionsRecord> findByCustomerId(Long customerId);
	public List<TransactionsRecord> findByDescription(String description);
	
	
	
}
