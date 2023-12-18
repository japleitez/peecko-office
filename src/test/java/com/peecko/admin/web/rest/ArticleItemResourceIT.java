package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.ArticleItem;
import com.peecko.admin.repository.ArticleItemRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArticleItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticleItemResourceIT {

    private static final String DEFAULT_PREVIOUS = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NEXT = "AAAAAAAAAA";
    private static final String UPDATED_NEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/article-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArticleItemRepository articleItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleItemMockMvc;

    private ArticleItem articleItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleItem createEntity(EntityManager em) {
        ArticleItem articleItem = new ArticleItem().previous(DEFAULT_PREVIOUS).code(DEFAULT_CODE).next(DEFAULT_NEXT);
        return articleItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleItem createUpdatedEntity(EntityManager em) {
        ArticleItem articleItem = new ArticleItem().previous(UPDATED_PREVIOUS).code(UPDATED_CODE).next(UPDATED_NEXT);
        return articleItem;
    }

    @BeforeEach
    public void initTest() {
        articleItem = createEntity(em);
    }

    @Test
    @Transactional
    void createArticleItem() throws Exception {
        int databaseSizeBeforeCreate = articleItemRepository.findAll().size();
        // Create the ArticleItem
        restArticleItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleItem)))
            .andExpect(status().isCreated());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleItem testArticleItem = articleItemList.get(articleItemList.size() - 1);
        assertThat(testArticleItem.getPrevious()).isEqualTo(DEFAULT_PREVIOUS);
        assertThat(testArticleItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testArticleItem.getNext()).isEqualTo(DEFAULT_NEXT);
    }

    @Test
    @Transactional
    void createArticleItemWithExistingId() throws Exception {
        // Create the ArticleItem with an existing ID
        articleItem.setId(1L);

        int databaseSizeBeforeCreate = articleItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleItem)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArticleItems() throws Exception {
        // Initialize the database
        articleItemRepository.saveAndFlush(articleItem);

        // Get all the articleItemList
        restArticleItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].previous").value(hasItem(DEFAULT_PREVIOUS)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].next").value(hasItem(DEFAULT_NEXT)));
    }

    @Test
    @Transactional
    void getArticleItem() throws Exception {
        // Initialize the database
        articleItemRepository.saveAndFlush(articleItem);

        // Get the articleItem
        restArticleItemMockMvc
            .perform(get(ENTITY_API_URL_ID, articleItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleItem.getId().intValue()))
            .andExpect(jsonPath("$.previous").value(DEFAULT_PREVIOUS))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.next").value(DEFAULT_NEXT));
    }

    @Test
    @Transactional
    void getNonExistingArticleItem() throws Exception {
        // Get the articleItem
        restArticleItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticleItem() throws Exception {
        // Initialize the database
        articleItemRepository.saveAndFlush(articleItem);

        int databaseSizeBeforeUpdate = articleItemRepository.findAll().size();

        // Update the articleItem
        ArticleItem updatedArticleItem = articleItemRepository.findById(articleItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArticleItem are not directly saved in db
        em.detach(updatedArticleItem);
        updatedArticleItem.previous(UPDATED_PREVIOUS).code(UPDATED_CODE).next(UPDATED_NEXT);

        restArticleItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArticleItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedArticleItem))
            )
            .andExpect(status().isOk());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeUpdate);
        ArticleItem testArticleItem = articleItemList.get(articleItemList.size() - 1);
        assertThat(testArticleItem.getPrevious()).isEqualTo(UPDATED_PREVIOUS);
        assertThat(testArticleItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArticleItem.getNext()).isEqualTo(UPDATED_NEXT);
    }

    @Test
    @Transactional
    void putNonExistingArticleItem() throws Exception {
        int databaseSizeBeforeUpdate = articleItemRepository.findAll().size();
        articleItem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticleItem() throws Exception {
        int databaseSizeBeforeUpdate = articleItemRepository.findAll().size();
        articleItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticleItem() throws Exception {
        int databaseSizeBeforeUpdate = articleItemRepository.findAll().size();
        articleItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleItemWithPatch() throws Exception {
        // Initialize the database
        articleItemRepository.saveAndFlush(articleItem);

        int databaseSizeBeforeUpdate = articleItemRepository.findAll().size();

        // Update the articleItem using partial update
        ArticleItem partialUpdatedArticleItem = new ArticleItem();
        partialUpdatedArticleItem.setId(articleItem.getId());

        partialUpdatedArticleItem.previous(UPDATED_PREVIOUS).code(UPDATED_CODE);

        restArticleItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleItem))
            )
            .andExpect(status().isOk());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeUpdate);
        ArticleItem testArticleItem = articleItemList.get(articleItemList.size() - 1);
        assertThat(testArticleItem.getPrevious()).isEqualTo(UPDATED_PREVIOUS);
        assertThat(testArticleItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArticleItem.getNext()).isEqualTo(DEFAULT_NEXT);
    }

    @Test
    @Transactional
    void fullUpdateArticleItemWithPatch() throws Exception {
        // Initialize the database
        articleItemRepository.saveAndFlush(articleItem);

        int databaseSizeBeforeUpdate = articleItemRepository.findAll().size();

        // Update the articleItem using partial update
        ArticleItem partialUpdatedArticleItem = new ArticleItem();
        partialUpdatedArticleItem.setId(articleItem.getId());

        partialUpdatedArticleItem.previous(UPDATED_PREVIOUS).code(UPDATED_CODE).next(UPDATED_NEXT);

        restArticleItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleItem))
            )
            .andExpect(status().isOk());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeUpdate);
        ArticleItem testArticleItem = articleItemList.get(articleItemList.size() - 1);
        assertThat(testArticleItem.getPrevious()).isEqualTo(UPDATED_PREVIOUS);
        assertThat(testArticleItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArticleItem.getNext()).isEqualTo(UPDATED_NEXT);
    }

    @Test
    @Transactional
    void patchNonExistingArticleItem() throws Exception {
        int databaseSizeBeforeUpdate = articleItemRepository.findAll().size();
        articleItem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticleItem() throws Exception {
        int databaseSizeBeforeUpdate = articleItemRepository.findAll().size();
        articleItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticleItem() throws Exception {
        int databaseSizeBeforeUpdate = articleItemRepository.findAll().size();
        articleItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(articleItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleItem in the database
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticleItem() throws Exception {
        // Initialize the database
        articleItemRepository.saveAndFlush(articleItem);

        int databaseSizeBeforeDelete = articleItemRepository.findAll().size();

        // Delete the articleItem
        restArticleItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, articleItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticleItem> articleItemList = articleItemRepository.findAll();
        assertThat(articleItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
