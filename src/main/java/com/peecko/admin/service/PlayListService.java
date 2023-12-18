package com.peecko.admin.service;

import com.peecko.admin.domain.PlayList;
import com.peecko.admin.repository.PlayListRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.peecko.admin.domain.PlayList}.
 */
@Service
@Transactional
public class PlayListService {

    private final Logger log = LoggerFactory.getLogger(PlayListService.class);

    private final PlayListRepository playListRepository;

    public PlayListService(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    /**
     * Save a playList.
     *
     * @param playList the entity to save.
     * @return the persisted entity.
     */
    public PlayList save(PlayList playList) {
        log.debug("Request to save PlayList : {}", playList);
        return playListRepository.save(playList);
    }

    /**
     * Update a playList.
     *
     * @param playList the entity to save.
     * @return the persisted entity.
     */
    public PlayList update(PlayList playList) {
        log.debug("Request to update PlayList : {}", playList);
        return playListRepository.save(playList);
    }

    /**
     * Partially update a playList.
     *
     * @param playList the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlayList> partialUpdate(PlayList playList) {
        log.debug("Request to partially update PlayList : {}", playList);

        return playListRepository
            .findById(playList.getId())
            .map(existingPlayList -> {
                if (playList.getName() != null) {
                    existingPlayList.setName(playList.getName());
                }
                if (playList.getCounter() != null) {
                    existingPlayList.setCounter(playList.getCounter());
                }
                if (playList.getCreated() != null) {
                    existingPlayList.setCreated(playList.getCreated());
                }
                if (playList.getUpdated() != null) {
                    existingPlayList.setUpdated(playList.getUpdated());
                }

                return existingPlayList;
            })
            .map(playListRepository::save);
    }

    /**
     * Get all the playLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlayList> findAll(Pageable pageable) {
        log.debug("Request to get all PlayLists");
        return playListRepository.findAll(pageable);
    }

    /**
     * Get one playList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlayList> findOne(Long id) {
        log.debug("Request to get PlayList : {}", id);
        return playListRepository.findById(id);
    }

    /**
     * Delete the playList by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlayList : {}", id);
        playListRepository.deleteById(id);
    }
}
