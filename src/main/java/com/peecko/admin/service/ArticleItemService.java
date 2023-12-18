package com.peecko.admin.service;

import com.peecko.admin.domain.ArticleItem;
import com.peecko.admin.repository.ArticleItemRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ArticleItem}.
 */
@Service
@Transactional
public class ArticleItemService {

    private final Logger log = LoggerFactory.getLogger(ArticleItemService.class);

    private final ArticleItemRepository articleItemRepository;

    public ArticleItemService(ArticleItemRepository articleItemRepository) {
        this.articleItemRepository = articleItemRepository;
    }

    /**
     * Save a articleItem.
     *
     * @param articleItem the entity to save.
     * @return the persisted entity.
     */
    public ArticleItem save(ArticleItem articleItem) {
        log.debug("Request to save ArticleItem : {}", articleItem);
        return articleItemRepository.save(articleItem);
    }

    /**
     * Update a articleItem.
     *
     * @param articleItem the entity to save.
     * @return the persisted entity.
     */
    public ArticleItem update(ArticleItem articleItem) {
        log.debug("Request to update ArticleItem : {}", articleItem);
        return articleItemRepository.save(articleItem);
    }

    /**
     * Partially update a articleItem.
     *
     * @param articleItem the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArticleItem> partialUpdate(ArticleItem articleItem) {
        log.debug("Request to partially update ArticleItem : {}", articleItem);

        return articleItemRepository
            .findById(articleItem.getId())
            .map(existingArticleItem -> {
                if (articleItem.getPrevious() != null) {
                    existingArticleItem.setPrevious(articleItem.getPrevious());
                }
                if (articleItem.getCode() != null) {
                    existingArticleItem.setCode(articleItem.getCode());
                }
                if (articleItem.getNext() != null) {
                    existingArticleItem.setNext(articleItem.getNext());
                }

                return existingArticleItem;
            })
            .map(articleItemRepository::save);
    }

    /**
     * Get all the articleItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticleItem> findAll(Pageable pageable) {
        log.debug("Request to get all ArticleItems");
        return articleItemRepository.findAll(pageable);
    }

    /**
     * Get one articleItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArticleItem> findOne(Long id) {
        log.debug("Request to get ArticleItem : {}", id);
        return articleItemRepository.findById(id);
    }

    /**
     * Delete the articleItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArticleItem : {}", id);
        articleItemRepository.deleteById(id);
    }
}
