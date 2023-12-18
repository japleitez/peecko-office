package com.peecko.admin.service;

import com.peecko.admin.domain.ArticleCategory;
import com.peecko.admin.repository.ArticleCategoryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ArticleCategory}.
 */
@Service
@Transactional
public class ArticleCategoryService {

    private final Logger log = LoggerFactory.getLogger(ArticleCategoryService.class);

    private final ArticleCategoryRepository articleCategoryRepository;

    public ArticleCategoryService(ArticleCategoryRepository articleCategoryRepository) {
        this.articleCategoryRepository = articleCategoryRepository;
    }

    /**
     * Save a articleCategory.
     *
     * @param articleCategory the entity to save.
     * @return the persisted entity.
     */
    public ArticleCategory save(ArticleCategory articleCategory) {
        log.debug("Request to save ArticleCategory : {}", articleCategory);
        return articleCategoryRepository.save(articleCategory);
    }

    /**
     * Update a articleCategory.
     *
     * @param articleCategory the entity to save.
     * @return the persisted entity.
     */
    public ArticleCategory update(ArticleCategory articleCategory) {
        log.debug("Request to update ArticleCategory : {}", articleCategory);
        return articleCategoryRepository.save(articleCategory);
    }

    /**
     * Partially update a articleCategory.
     *
     * @param articleCategory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArticleCategory> partialUpdate(ArticleCategory articleCategory) {
        log.debug("Request to partially update ArticleCategory : {}", articleCategory);

        return articleCategoryRepository
            .findById(articleCategory.getId())
            .map(existingArticleCategory -> {
                if (articleCategory.getCode() != null) {
                    existingArticleCategory.setCode(articleCategory.getCode());
                }
                if (articleCategory.getTitle() != null) {
                    existingArticleCategory.setTitle(articleCategory.getTitle());
                }
                if (articleCategory.getLabel() != null) {
                    existingArticleCategory.setLabel(articleCategory.getLabel());
                }
                if (articleCategory.getCreated() != null) {
                    existingArticleCategory.setCreated(articleCategory.getCreated());
                }
                if (articleCategory.getRelease() != null) {
                    existingArticleCategory.setRelease(articleCategory.getRelease());
                }
                if (articleCategory.getArchived() != null) {
                    existingArticleCategory.setArchived(articleCategory.getArchived());
                }

                return existingArticleCategory;
            })
            .map(articleCategoryRepository::save);
    }

    /**
     * Get all the articleCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticleCategory> findAll(Pageable pageable) {
        log.debug("Request to get all ArticleCategories");
        return articleCategoryRepository.findAll(pageable);
    }

    /**
     * Get one articleCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArticleCategory> findOne(Long id) {
        log.debug("Request to get ArticleCategory : {}", id);
        return articleCategoryRepository.findById(id);
    }

    /**
     * Delete the articleCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArticleCategory : {}", id);
        articleCategoryRepository.deleteById(id);
    }
}
