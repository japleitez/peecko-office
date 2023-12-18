package com.peecko.admin.repository;

import com.peecko.admin.domain.ApsUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApsUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApsUserRepository extends JpaRepository<ApsUser, Long> {}
