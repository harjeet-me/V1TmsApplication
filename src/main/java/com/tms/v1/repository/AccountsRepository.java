package com.tms.v1.repository;

import com.tms.v1.domain.Accounts;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Accounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
}
