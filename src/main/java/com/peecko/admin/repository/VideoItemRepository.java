package com.peecko.admin.repository;

import com.peecko.admin.domain.VideoItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VideoItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoItemRepository extends JpaRepository<VideoItem, Long> {}
