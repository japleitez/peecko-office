package com.peecko.admin.web.rest;

import com.peecko.admin.domain.ArticleCategory;
import com.peecko.admin.repository.ArticleCategoryRepository;
import com.peecko.admin.service.ArticleCategoryService;
import com.peecko.admin.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.peecko.admin.domain.ArticleCategory}.
 */
@RestController
@RequestMapping("/api/article-categories")
public class ArticleCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ArticleCategoryResource.class);

    private static final String ENTITY_NAME = "articleCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleCategoryService articleCategoryService;

    private final ArticleCategoryRepository articleCategoryRepository;

    public ArticleCategoryResource(ArticleCategoryService articleCategoryService, ArticleCategoryRepository articleCategoryRepository) {
        this.articleCategoryService = articleCategoryService;
        this.articleCategoryRepository = articleCategoryRepository;
    }

    /**
     * {@code POST  /article-categories} : Create a new articleCategory.
     *
     * @param articleCategory the articleCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleCategory, or with status {@code 400 (Bad Request)} if the articleCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArticleCategory> createArticleCategory(@Valid @RequestBody ArticleCategory articleCategory)
        throws URISyntaxException {
        log.debug("REST request to save ArticleCategory : {}", articleCategory);
        if (articleCategory.getId() != null) {
            throw new BadRequestAlertException("A new articleCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleCategory result = articleCategoryService.save(articleCategory);
        return ResponseEntity
            .created(new URI("/api/article-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-categories/:id} : Updates an existing articleCategory.
     *
     * @param id the id of the articleCategory to save.
     * @param articleCategory the articleCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleCategory,
     * or with status {@code 400 (Bad Request)} if the articleCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleCategory> updateArticleCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArticleCategory articleCategory
    ) throws URISyntaxException {
        log.debug("REST request to update ArticleCategory : {}, {}", id, articleCategory);
        if (articleCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArticleCategory result = articleCategoryService.update(articleCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articleCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /article-categories/:id} : Partial updates given fields of an existing articleCategory, field will ignore if it is null
     *
     * @param id the id of the articleCategory to save.
     * @param articleCategory the articleCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleCategory,
     * or with status {@code 400 (Bad Request)} if the articleCategory is not valid,
     * or with status {@code 404 (Not Found)} if the articleCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticleCategory> partialUpdateArticleCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArticleCategory articleCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArticleCategory partially : {}, {}", id, articleCategory);
        if (articleCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticleCategory> result = articleCategoryService.partialUpdate(articleCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articleCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /article-categories} : get all the articleCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleCategories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArticleCategory>> getAllArticleCategories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ArticleCategories");
        Page<ArticleCategory> page = articleCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-categories/:id} : get the "id" articleCategory.
     *
     * @param id the id of the articleCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleCategory> getArticleCategory(@PathVariable("id") Long id) {
        log.debug("REST request to get ArticleCategory : {}", id);
        Optional<ArticleCategory> articleCategory = articleCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleCategory);
    }

    /**
     * {@code DELETE  /article-categories/:id} : delete the "id" articleCategory.
     *
     * @param id the id of the articleCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleCategory(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArticleCategory : {}", id);
        articleCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
