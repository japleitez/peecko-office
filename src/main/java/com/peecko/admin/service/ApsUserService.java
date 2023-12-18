package com.peecko.admin.service;

import com.peecko.admin.domain.ApsUser;
import com.peecko.admin.repository.ApsUserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.ApsUser}.
 */
@Service
@Transactional
public class ApsUserService {

    private final Logger log = LoggerFactory.getLogger(ApsUserService.class);

    private final ApsUserRepository apsUserRepository;

    public ApsUserService(ApsUserRepository apsUserRepository) {
        this.apsUserRepository = apsUserRepository;
    }

    /**
     * Save a apsUser.
     *
     * @param apsUser the entity to save.
     * @return the persisted entity.
     */
    public ApsUser save(ApsUser apsUser) {
        log.debug("Request to save ApsUser : {}", apsUser);
        return apsUserRepository.save(apsUser);
    }

    /**
     * Update a apsUser.
     *
     * @param apsUser the entity to save.
     * @return the persisted entity.
     */
    public ApsUser update(ApsUser apsUser) {
        log.debug("Request to update ApsUser : {}", apsUser);
        return apsUserRepository.save(apsUser);
    }

    /**
     * Partially update a apsUser.
     *
     * @param apsUser the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApsUser> partialUpdate(ApsUser apsUser) {
        log.debug("Request to partially update ApsUser : {}", apsUser);

        return apsUserRepository
            .findById(apsUser.getId())
            .map(existingApsUser -> {
                if (apsUser.getName() != null) {
                    existingApsUser.setName(apsUser.getName());
                }
                if (apsUser.getUsername() != null) {
                    existingApsUser.setUsername(apsUser.getUsername());
                }
                if (apsUser.getUsernameVerified() != null) {
                    existingApsUser.setUsernameVerified(apsUser.getUsernameVerified());
                }
                if (apsUser.getPrivateEmail() != null) {
                    existingApsUser.setPrivateEmail(apsUser.getPrivateEmail());
                }
                if (apsUser.getPrivateVerified() != null) {
                    existingApsUser.setPrivateVerified(apsUser.getPrivateVerified());
                }
                if (apsUser.getLanguage() != null) {
                    existingApsUser.setLanguage(apsUser.getLanguage());
                }
                if (apsUser.getLicense() != null) {
                    existingApsUser.setLicense(apsUser.getLicense());
                }
                if (apsUser.getActive() != null) {
                    existingApsUser.setActive(apsUser.getActive());
                }
                if (apsUser.getPassword() != null) {
                    existingApsUser.setPassword(apsUser.getPassword());
                }
                if (apsUser.getCreated() != null) {
                    existingApsUser.setCreated(apsUser.getCreated());
                }
                if (apsUser.getUpdated() != null) {
                    existingApsUser.setUpdated(apsUser.getUpdated());
                }

                return existingApsUser;
            })
            .map(apsUserRepository::save);
    }

    /**
     * Get all the apsUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApsUser> findAll(Pageable pageable) {
        log.debug("Request to get all ApsUsers");
        return apsUserRepository.findAll(pageable);
    }

    /**
     * Get one apsUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApsUser> findOne(Long id) {
        log.debug("Request to get ApsUser : {}", id);
        return apsUserRepository.findById(id);
    }

    /**
     * Delete the apsUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApsUser : {}", id);
        apsUserRepository.deleteById(id);
    }
}
