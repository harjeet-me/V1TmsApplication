package com.tms.v1.repository;

import com.tms.v1.domain.AccountHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AccountHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
}
