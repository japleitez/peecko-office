package com.peecko.admin.repository;

import com.peecko.admin.domain.ArticleCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArticleCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Long> {}
