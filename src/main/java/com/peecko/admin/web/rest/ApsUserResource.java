package com.peecko.admin.web.rest;

import com.peecko.admin.domain.ApsUser;
import com.peecko.admin.repository.ApsUserRepository;
import com.peecko.admin.service.ApsUserService;
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
 * REST controller for managing {@link com.peecko.admin.domain.ApsUser}.
 */
@RestController
@RequestMapping("/api/aps-users")
public class ApsUserResource {

    private final Logger log = LoggerFactory.getLogger(ApsUserResource.class);

    private static final String ENTITY_NAME = "apsUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApsUserService apsUserService;

    private final ApsUserRepository apsUserRepository;

    public ApsUserResource(ApsUserService apsUserService, ApsUserRepository apsUserRepository) {
        this.apsUserService = apsUserService;
        this.apsUserRepository = apsUserRepository;
    }

    /**
     * {@code POST  /aps-users} : Create a new apsUser.
     *
     * @param apsUser the apsUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apsUser, or with status {@code 400 (Bad Request)} if the apsUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ApsUser> createApsUser(@Valid @RequestBody ApsUser apsUser) throws URISyntaxException {
        log.debug("REST request to save ApsUser : {}", apsUser);
        if (apsUser.getId() != null) {
            throw new BadRequestAlertException("A new apsUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApsUser result = apsUserService.save(apsUser);
        return ResponseEntity
            .created(new URI("/api/aps-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aps-users/:id} : Updates an existing apsUser.
     *
     * @param id the id of the apsUser to save.
     * @param apsUser the apsUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsUser,
     * or with status {@code 400 (Bad Request)} if the apsUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apsUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApsUser> updateApsUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApsUser apsUser
    ) throws URISyntaxException {
        log.debug("REST request to update ApsUser : {}, {}", id, apsUser);
        if (apsUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApsUser result = apsUserService.update(apsUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aps-users/:id} : Partial updates given fields of an existing apsUser, field will ignore if it is null
     *
     * @param id the id of the apsUser to save.
     * @param apsUser the apsUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apsUser,
     * or with status {@code 400 (Bad Request)} if the apsUser is not valid,
     * or with status {@code 404 (Not Found)} if the apsUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the apsUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApsUser> partialUpdateApsUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApsUser apsUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApsUser partially : {}, {}", id, apsUser);
        if (apsUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apsUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apsUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApsUser> result = apsUserService.partialUpdate(apsUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apsUser.getId().toString())
        );
    }

    /**
     * {@code GET  /aps-users} : get all the apsUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apsUsers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ApsUser>> getAllApsUsers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ApsUsers");
        Page<ApsUser> page = apsUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aps-users/:id} : get the "id" apsUser.
     *
     * @param id the id of the apsUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apsUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApsUser> getApsUser(@PathVariable("id") Long id) {
        log.debug("REST request to get ApsUser : {}", id);
        Optional<ApsUser> apsUser = apsUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apsUser);
    }

    /**
     * {@code DELETE  /aps-users/:id} : delete the "id" apsUser.
     *
     * @param id the id of the apsUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApsUser(@PathVariable("id") Long id) {
        log.debug("REST request to delete ApsUser : {}", id);
        apsUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
