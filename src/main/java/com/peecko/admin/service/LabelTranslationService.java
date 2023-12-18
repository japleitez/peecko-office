package com.peecko.admin.service;

import com.peecko.admin.domain.LabelTranslation;
import com.peecko.admin.repository.LabelTranslationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.LabelTranslation}.
 */
@Service
@Transactional
public class LabelTranslationService {

    private final Logger log = LoggerFactory.getLogger(LabelTranslationService.class);

    private final LabelTranslationRepository labelTranslationRepository;

    public LabelTranslationService(LabelTranslationRepository labelTranslationRepository) {
        this.labelTranslationRepository = labelTranslationRepository;
    }

    /**
     * Save a labelTranslation.
     *
     * @param labelTranslation the entity to save.
     * @return the persisted entity.
     */
    public LabelTranslation save(LabelTranslation labelTranslation) {
        log.debug("Request to save LabelTranslation : {}", labelTranslation);
        return labelTranslationRepository.save(labelTranslation);
    }

    /**
     * Update a labelTranslation.
     *
     * @param labelTranslation the entity to save.
     * @return the persisted entity.
     */
    public LabelTranslation update(LabelTranslation labelTranslation) {
        log.debug("Request to update LabelTranslation : {}", labelTranslation);
        return labelTranslationRepository.save(labelTranslation);
    }

    /**
     * Partially update a labelTranslation.
     *
     * @param labelTranslation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LabelTranslation> partialUpdate(LabelTranslation labelTranslation) {
        log.debug("Request to partially update LabelTranslation : {}", labelTranslation);

        return labelTranslationRepository
            .findById(labelTranslation.getId())
            .map(existingLabelTranslation -> {
                if (labelTranslation.getLabel() != null) {
                    existingLabelTranslation.setLabel(labelTranslation.getLabel());
                }
                if (labelTranslation.getLang() != null) {
                    existingLabelTranslation.setLang(labelTranslation.getLang());
                }
                if (labelTranslation.getTranslation() != null) {
                    existingLabelTranslation.setTranslation(labelTranslation.getTranslation());
                }

                return existingLabelTranslation;
            })
            .map(labelTranslationRepository::save);
    }

    /**
     * Get all the labelTranslations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LabelTranslation> findAll(Pageable pageable) {
        log.debug("Request to get all LabelTranslations");
        return labelTranslationRepository.findAll(pageable);
    }

    /**
     * Get one labelTranslation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LabelTranslation> findOne(Long id) {
        log.debug("Request to get LabelTranslation : {}", id);
        return labelTranslationRepository.findById(id);
    }

    /**
     * Delete the labelTranslation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LabelTranslation : {}", id);
        labelTranslationRepository.deleteById(id);
    }
}
