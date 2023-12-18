package com.peecko.admin.repository;

import com.peecko.admin.domain.ArticleList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArticleList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleListRepository extends JpaRepository<ArticleList, Long> {}
