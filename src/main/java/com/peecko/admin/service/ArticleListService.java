package com.peecko.admin.service;

import com.peecko.admin.domain.ArticleList;
import com.peecko.admin.repository.ArticleListRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ArticleList}.
 */
@Service
@Transactional
public class ArticleListService {

    private final Logger log = LoggerFactory.getLogger(ArticleListService.class);

    private final ArticleListRepository articleListRepository;

    public ArticleListService(ArticleListRepository articleListRepository) {
        this.articleListRepository = articleListRepository;
    }

    /**
     * Save a articleList.
     *
     * @param articleList the entity to save.
     * @return the persisted entity.
     */
    public ArticleList save(ArticleList articleList) {
        log.debug("Request to save ArticleList : {}", articleList);
        return articleListRepository.save(articleList);
    }

    /**
     * Update a articleList.
     *
     * @param articleList the entity to save.
     * @return the persisted entity.
     */
    public ArticleList update(ArticleList articleList) {
        log.debug("Request to update ArticleList : {}", articleList);
        return articleListRepository.save(articleList);
    }

    /**
     * Partially update a articleList.
     *
     * @param articleList the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArticleList> partialUpdate(ArticleList articleList) {
        log.debug("Request to partially update ArticleList : {}", articleList);

        return articleListRepository
            .findById(articleList.getId())
            .map(existingArticleList -> {
                if (articleList.getName() != null) {
                    existingArticleList.setName(articleList.getName());
                }
                if (articleList.getCounter() != null) {
                    existingArticleList.setCounter(articleList.getCounter());
                }
                if (articleList.getCreated() != null) {
                    existingArticleList.setCreated(articleList.getCreated());
                }
                if (articleList.getUpdated() != null) {
                    existingArticleList.setUpdated(articleList.getUpdated());
                }

                return existingArticleList;
            })
            .map(articleListRepository::save);
    }

    /**
     * Get all the articleLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticleList> findAll(Pageable pageable) {
        log.debug("Request to get all ArticleLists");
        return articleListRepository.findAll(pageable);
    }

    /**
     * Get one articleList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArticleList> findOne(Long id) {
        log.debug("Request to get ArticleList : {}", id);
        return articleListRepository.findById(id);
    }

    /**
     * Delete the articleList by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ArticleList : {}", id);
        articleListRepository.deleteById(id);
    }
}
