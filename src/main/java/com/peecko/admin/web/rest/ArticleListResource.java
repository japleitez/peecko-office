package com.peecko.admin.web.rest;

import com.peecko.admin.domain.ArticleList;
import com.peecko.admin.repository.ArticleListRepository;
import com.peecko.admin.service.ArticleListService;
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
 * REST controller for managing {@link com.peecko.admin.domain.ArticleList}.
 */
@RestController
@RequestMapping("/api/article-lists")
public class ArticleListResource {

    private final Logger log = LoggerFactory.getLogger(ArticleListResource.class);

    private static final String ENTITY_NAME = "articleList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleListService articleListService;

    private final ArticleListRepository articleListRepository;

    public ArticleListResource(ArticleListService articleListService, ArticleListRepository articleListRepository) {
        this.articleListService = articleListService;
        this.articleListRepository = articleListRepository;
    }

    /**
     * {@code POST  /article-lists} : Create a new articleList.
     *
     * @param articleList the articleList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleList, or with status {@code 400 (Bad Request)} if the articleList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArticleList> createArticleList(@Valid @RequestBody ArticleList articleList) throws URISyntaxException {
        log.debug("REST request to save ArticleList : {}", articleList);
        if (articleList.getId() != null) {
            throw new BadRequestAlertException("A new articleList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticleList result = articleListService.save(articleList);
        return ResponseEntity
            .created(new URI("/api/article-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-lists/:id} : Updates an existing articleList.
     *
     * @param id the id of the articleList to save.
     * @param articleList the articleList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleList,
     * or with status {@code 400 (Bad Request)} if the articleList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleList> updateArticleList(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArticleList articleList
    ) throws URISyntaxException {
        log.debug("REST request to update ArticleList : {}, {}", id, articleList);
        if (articleList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArticleList result = articleListService.update(articleList);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articleList.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /article-lists/:id} : Partial updates given fields of an existing articleList, field will ignore if it is null
     *
     * @param id the id of the articleList to save.
     * @param articleList the articleList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleList,
     * or with status {@code 400 (Bad Request)} if the articleList is not valid,
     * or with status {@code 404 (Not Found)} if the articleList is not found,
     * or with status {@code 500 (Internal Server Error)} if the articleList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticleList> partialUpdateArticleList(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArticleList articleList
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArticleList partially : {}, {}", id, articleList);
        if (articleList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articleList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articleListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticleList> result = articleListService.partialUpdate(articleList);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articleList.getId().toString())
        );
    }

    /**
     * {@code GET  /article-lists} : get all the articleLists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleLists in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArticleList>> getAllArticleLists(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArticleLists");
        Page<ArticleList> page = articleListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-lists/:id} : get the "id" articleList.
     *
     * @param id the id of the articleList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleList> getArticleList(@PathVariable("id") Long id) {
        log.debug("REST request to get ArticleList : {}", id);
        Optional<ArticleList> articleList = articleListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleList);
    }

    /**
     * {@code DELETE  /article-lists/:id} : delete the "id" articleList.
     *
     * @param id the id of the articleList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleList(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArticleList : {}", id);
        articleListService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
