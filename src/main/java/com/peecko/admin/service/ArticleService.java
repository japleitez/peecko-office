package com.peecko.admin.service;

import com.peecko.admin.domain.Article;
import com.peecko.admin.repository.ArticleRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.Article}.
 */
@Service
@Transactional
public class ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Save a article.
     *
     * @param article the entity to save.
     * @return the persisted entity.
     */
    public Article save(Article article) {
        log.debug("Request to save Article : {}", article);
        return articleRepository.save(article);
    }

    /**
     * Update a article.
     *
     * @param article the entity to save.
     * @return the persisted entity.
     */
    public Article update(Article article) {
        log.debug("Request to update Article : {}", article);
        return articleRepository.save(article);
    }

    /**
     * Partially update a article.
     *
     * @param article the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Article> partialUpdate(Article article) {
        log.debug("Request to partially update Article : {}", article);

        return articleRepository
            .findById(article.getId())
            .map(existingArticle -> {
                if (article.getCode() != null) {
                    existingArticle.setCode(article.getCode());
                }
                if (article.getTitle() != null) {
                    existingArticle.setTitle(article.getTitle());
                }
                if (article.getSubtitle() != null) {
                    existingArticle.setSubtitle(article.getSubtitle());
                }
                if (article.getSummary() != null) {
                    existingArticle.setSummary(article.getSummary());
                }
                if (article.getDuration() != null) {
                    existingArticle.setDuration(article.getDuration());
                }
                if (article.getLanguage() != null) {
                    existingArticle.setLanguage(article.getLanguage());
                }
                if (article.getThumbnail() != null) {
                    existingArticle.setThumbnail(article.getThumbnail());
                }
                if (article.getAudioUrl() != null) {
                    existingArticle.setAudioUrl(article.getAudioUrl());
                }
                if (article.getContent() != null) {
                    existingArticle.setContent(article.getContent());
                }

                return existingArticle;
            })
            .map(articleRepository::save);
    }

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Article> findAll(Pageable pageable) {
        log.debug("Request to get all Articles");
        return articleRepository.findAll(pageable);
    }

    /**
     * Get one article by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Article> findOne(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findById(id);
    }

    /**
     * Delete the article by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
}
