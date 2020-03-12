package com.tms.v1.repository;

import com.tms.v1.domain.TransactionsRecord;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransactionsRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionsRecordRepository extends JpaRepository<TransactionsRecord, Long> {
}
