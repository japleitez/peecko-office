package com.peecko.admin.web.rest;

import com.peecko.admin.domain.LabelTranslation;
import com.peecko.admin.repository.LabelTranslationRepository;
import com.peecko.admin.service.LabelTranslationService;
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
 * REST controller for managing {@link com.peecko.admin.domain.LabelTranslation}.
 */
@RestController
@RequestMapping("/api/label-translations")
public class LabelTranslationResource {

    private final Logger log = LoggerFactory.getLogger(LabelTranslationResource.class);

    private static final String ENTITY_NAME = "labelTranslation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LabelTranslationService labelTranslationService;

    private final LabelTranslationRepository labelTranslationRepository;

    public LabelTranslationResource(
        LabelTranslationService labelTranslationService,
        LabelTranslationRepository labelTranslationRepository
    ) {
        this.labelTranslationService = labelTranslationService;
        this.labelTranslationRepository = labelTranslationRepository;
    }

    /**
     * {@code POST  /label-translations} : Create a new labelTranslation.
     *
     * @param labelTranslation the labelTranslation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new labelTranslation, or with status {@code 400 (Bad Request)} if the labelTranslation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LabelTranslation> createLabelTranslation(@Valid @RequestBody LabelTranslation labelTranslation)
        throws URISyntaxException {
        log.debug("REST request to save LabelTranslation : {}", labelTranslation);
        if (labelTranslation.getId() != null) {
            throw new BadRequestAlertException("A new labelTranslation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LabelTranslation result = labelTranslationService.save(labelTranslation);
        return ResponseEntity
            .created(new URI("/api/label-translations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /label-translations/:id} : Updates an existing labelTranslation.
     *
     * @param id the id of the labelTranslation to save.
     * @param labelTranslation the labelTranslation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated labelTranslation,
     * or with status {@code 400 (Bad Request)} if the labelTranslation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the labelTranslation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LabelTranslation> updateLabelTranslation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LabelTranslation labelTranslation
    ) throws URISyntaxException {
        log.debug("REST request to update LabelTranslation : {}, {}", id, labelTranslation);
        if (labelTranslation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, labelTranslation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!labelTranslationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LabelTranslation result = labelTranslationService.update(labelTranslation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, labelTranslation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /label-translations/:id} : Partial updates given fields of an existing labelTranslation, field will ignore if it is null
     *
     * @param id the id of the labelTranslation to save.
     * @param labelTranslation the labelTranslation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated labelTranslation,
     * or with status {@code 400 (Bad Request)} if the labelTranslation is not valid,
     * or with status {@code 404 (Not Found)} if the labelTranslation is not found,
     * or with status {@code 500 (Internal Server Error)} if the labelTranslation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LabelTranslation> partialUpdateLabelTranslation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LabelTranslation labelTranslation
    ) throws URISyntaxException {
        log.debug("REST request to partial update LabelTranslation partially : {}, {}", id, labelTranslation);
        if (labelTranslation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, labelTranslation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!labelTranslationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LabelTranslation> result = labelTranslationService.partialUpdate(labelTranslation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, labelTranslation.getId().toString())
        );
    }

    /**
     * {@code GET  /label-translations} : get all the labelTranslations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of labelTranslations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LabelTranslation>> getAllLabelTranslations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LabelTranslations");
        Page<LabelTranslation> page = labelTranslationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /label-translations/:id} : get the "id" labelTranslation.
     *
     * @param id the id of the labelTranslation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the labelTranslation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LabelTranslation> getLabelTranslation(@PathVariable("id") Long id) {
        log.debug("REST request to get LabelTranslation : {}", id);
        Optional<LabelTranslation> labelTranslation = labelTranslationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(labelTranslation);
    }

    /**
     * {@code DELETE  /label-translations/:id} : delete the "id" labelTranslation.
     *
     * @param id the id of the labelTranslation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabelTranslation(@PathVariable("id") Long id) {
        log.debug("REST request to delete LabelTranslation : {}", id);
        labelTranslationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
