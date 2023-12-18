package com.peecko.admin.repository;

import com.peecko.admin.domain.PlayList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlayList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {}
