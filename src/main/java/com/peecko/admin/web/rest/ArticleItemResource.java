package com.peecko.admin.web.rest;

import com.peecko.admin.domain.ArticleItem;
import com.peecko.admin.repository.ArticleItemRepository;
import com.peecko.admin.service.ArticleItemService;
import com.peecko.admin.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.peecko.admin.domain.ArticleItem}.
 */
@RestController
@RequestMapping("/api/article-items")
public class ArticleItemResource {

    private final Logger log = LoggerFactory.getLogger(ArticleItemResource.class);

    private static final String ENTITY_NAME = "articleItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleItemService articleItemService;

    private final ArticleItemRepository articleItemRepository;

    public ArticleItemResource(ArticleItemService articleItemService, ArticleItemRepository articleItemRepository) {
        this.articleItemService = articleItemService;
        this.articleItemRepository = articleItemRepository;
    }

    /**
     * {@code POST  /article-items} : Create a new articleItem.
     *
     * @param articleItem the articleItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleItem, or with status {@code 400 (Bad Request)} if the articleItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArticleItem> createArticleItem(@RequestBody ArticleItem articleItem) throws URISyntaxException {
        log.debug("REST request to save ArticleItem : {}", articleItem);
        if (articleItem.getId() != null) {
            throw new BadRequestAlertException("A new articleItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleItem result = articleItemService.save(articleItem);
        return ResponseEntity
            .created(new URI("/api/article-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-items/:id} : Updates an existing articleItem.
     *
     * @param id the id of the articleItem to save.
     * @param articleItem the articleItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleItem,
     * or with status {@code 400 (Bad Request)} if the articleItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleItem> updateArticleItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArticleItem articleItem
    ) throws URISyntaxException {
        log.debug("REST request to update ArticleItem : {}, {}", id, articleItem);
        if (articleItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArticleItem result = articleItemService.update(articleItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articleItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /article-items/:id} : Partial updates given fields of an existing articleItem, field will ignore if it is null
     *
     * @param id the id of the articleItem to save.
     * @param articleItem the articleItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleItem,
     * or with status {@code 400 (Bad Request)} if the articleItem is not valid,
     * or with status {@code 404 (Not Found)} if the articleItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticleItem> partialUpdateArticleItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArticleItem articleItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArticleItem partially : {}, {}", id, articleItem);
        if (articleItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticleItem> result = articleItemService.partialUpdate(articleItem);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articleItem.getId().toString())
        );
    }

    /**
     * {@code GET  /article-items} : get all the articleItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleItems in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArticleItem>> getAllArticleItems(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArticleItems");
        Page<ArticleItem> page = articleItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-items/:id} : get the "id" articleItem.
     *
     * @param id the id of the articleItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleItem> getArticleItem(@PathVariable("id") Long id) {
        log.debug("REST request to get ArticleItem : {}", id);
        Optional<ArticleItem> articleItem = articleItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleItem);
    }

    /**
     * {@code DELETE  /article-items/:id} : delete the "id" articleItem.
     *
     * @param id the id of the articleItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleItem(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArticleItem : {}", id);
        articleItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
