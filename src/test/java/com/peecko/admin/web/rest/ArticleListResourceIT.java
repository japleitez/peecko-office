package com.peecko.admin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peecko.admin.IntegrationTest;
import com.peecko.admin.domain.ArticleList;
import com.peecko.admin.repository.ArticleListRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ArticleListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticleListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNTER = 1;
    private static final Integer UPDATED_COUNTER = 2;

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/article-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArticleListRepository articleListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleListMockMvc;

    private ArticleList articleList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleList createEntity(EntityManager em) {
        ArticleList articleList = new ArticleList()
            .name(DEFAULT_NAME)
            .counter(DEFAULT_COUNTER)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return articleList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleList createUpdatedEntity(EntityManager em) {
        ArticleList articleList = new ArticleList()
            .name(UPDATED_NAME)
            .counter(UPDATED_COUNTER)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return articleList;
    }

    @BeforeEach
    public void initTest() {
        articleList = createEntity(em);
    }

    @Test
    @Transactional
    void createArticleList() throws Exception {
        int databaseSizeBeforeCreate = articleListRepository.findAll().size();
        // Create the ArticleList
        restArticleListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleList)))
            .andExpect(status().isCreated());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleList testArticleList = articleListList.get(articleListList.size() - 1);
        assertThat(testArticleList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArticleList.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testArticleList.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testArticleList.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createArticleListWithExistingId() throws Exception {
        // Create the ArticleList with an existing ID
        articleList.setId(1L);

        int databaseSizeBeforeCreate = articleListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleList)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleListRepository.findAll().size();
        // set the field null
        articleList.setName(null);

        // Create the ArticleList, which fails.

        restArticleListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleList)))
            .andExpect(status().isBadRequest());

        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCounterIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleListRepository.findAll().size();
        // set the field null
        articleList.setCounter(null);

        // Create the ArticleList, which fails.

        restArticleListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleList)))
            .andExpect(status().isBadRequest());

        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleListRepository.findAll().size();
        // set the field null
        articleList.setCreated(null);

        // Create the ArticleList, which fails.

        restArticleListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleList)))
            .andExpect(status().isBadRequest());

        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleListRepository.findAll().size();
        // set the field null
        articleList.setUpdated(null);

        // Create the ArticleList, which fails.

        restArticleListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleList)))
            .andExpect(status().isBadRequest());

        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticleLists() throws Exception {
        // Initialize the database
        articleListRepository.saveAndFlush(articleList);

        // Get all the articleListList
        restArticleListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getArticleList() throws Exception {
        // Initialize the database
        articleListRepository.saveAndFlush(articleList);

        // Get the articleList
        restArticleListMockMvc
            .perform(get(ENTITY_API_URL_ID, articleList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingArticleList() throws Exception {
        // Get the articleList
        restArticleListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticleList() throws Exception {
        // Initialize the database
        articleListRepository.saveAndFlush(articleList);

        int databaseSizeBeforeUpdate = articleListRepository.findAll().size();

        // Update the articleList
        ArticleList updatedArticleList = articleListRepository.findById(articleList.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArticleList are not directly saved in db
        em.detach(updatedArticleList);
        updatedArticleList.name(UPDATED_NAME).counter(UPDATED_COUNTER).created(UPDATED_CREATED).updated(UPDATED_UPDATED);

        restArticleListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArticleList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedArticleList))
            )
            .andExpect(status().isOk());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeUpdate);
        ArticleList testArticleList = articleListList.get(articleListList.size() - 1);
        assertThat(testArticleList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArticleList.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testArticleList.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testArticleList.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingArticleList() throws Exception {
        int databaseSizeBeforeUpdate = articleListRepository.findAll().size();
        articleList.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleList))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticleList() throws Exception {
        int databaseSizeBeforeUpdate = articleListRepository.findAll().size();
        articleList.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articleList))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticleList() throws Exception {
        int databaseSizeBeforeUpdate = articleListRepository.findAll().size();
        articleList.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleListMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articleList)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleListWithPatch() throws Exception {
        // Initialize the database
        articleListRepository.saveAndFlush(articleList);

        int databaseSizeBeforeUpdate = articleListRepository.findAll().size();

        // Update the articleList using partial update
        ArticleList partialUpdatedArticleList = new ArticleList();
        partialUpdatedArticleList.setId(articleList.getId());

        partialUpdatedArticleList.created(UPDATED_CREATED);

        restArticleListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleList))
            )
            .andExpect(status().isOk());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeUpdate);
        ArticleList testArticleList = articleListList.get(articleListList.size() - 1);
        assertThat(testArticleList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArticleList.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testArticleList.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testArticleList.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateArticleListWithPatch() throws Exception {
        // Initialize the database
        articleListRepository.saveAndFlush(articleList);

        int databaseSizeBeforeUpdate = articleListRepository.findAll().size();

        // Update the articleList using partial update
        ArticleList partialUpdatedArticleList = new ArticleList();
        partialUpdatedArticleList.setId(articleList.getId());

        partialUpdatedArticleList.name(UPDATED_NAME).counter(UPDATED_COUNTER).created(UPDATED_CREATED).updated(UPDATED_UPDATED);

        restArticleListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticleList))
            )
            .andExpect(status().isOk());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeUpdate);
        ArticleList testArticleList = articleListList.get(articleListList.size() - 1);
        assertThat(testArticleList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArticleList.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testArticleList.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testArticleList.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingArticleList() throws Exception {
        int databaseSizeBeforeUpdate = articleListRepository.findAll().size();
        articleList.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleList))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticleList() throws Exception {
        int databaseSizeBeforeUpdate = articleListRepository.findAll().size();
        articleList.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articleList))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticleList() throws Exception {
        int databaseSizeBeforeUpdate = articleListRepository.findAll().size();
        articleList.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleListMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(articleList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleList in the database
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticleList() throws Exception {
        // Initialize the database
        articleListRepository.saveAndFlush(articleList);

        int databaseSizeBeforeDelete = articleListRepository.findAll().size();

        // Delete the articleList
        restArticleListMockMvc
            .perform(delete(ENTITY_API_URL_ID, articleList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticleList> articleListList = articleListRepository.findAll();
        assertThat(articleListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
