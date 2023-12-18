package com.peecko.admin.repository;

import com.peecko.admin.domain.ArticleItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArticleItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleItemRepository extends JpaRepository<ArticleItem, Long> {}
