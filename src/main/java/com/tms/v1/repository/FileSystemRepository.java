package com.tms.v1.repository;

import com.tms.v1.domain.FileSystem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FileSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileSystemRepository extends JpaRepository<FileSystem, Long> {
}
