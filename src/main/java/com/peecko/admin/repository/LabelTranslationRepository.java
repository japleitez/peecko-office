package com.peecko.admin.repository;

import com.peecko.admin.domain.LabelTranslation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LabelTranslation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LabelTranslationRepository extends JpaRepository<LabelTranslation, Long> {}
