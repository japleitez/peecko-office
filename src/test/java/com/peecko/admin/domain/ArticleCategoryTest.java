package com.peecko.admin.domain;

import static com.peecko.admin.domain.ArticleCategoryTestSamples.*;
import static com.peecko.admin.domain.ArticleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ArticleCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleCategory.class);
        ArticleCategory articleCategory1 = getArticleCategorySample1();
        ArticleCategory articleCategory2 = new ArticleCategory();
        assertThat(articleCategory1).isNotEqualTo(articleCategory2);

        articleCategory2.setId(articleCategory1.getId());
        assertThat(articleCategory1).isEqualTo(articleCategory2);

        articleCategory2 = getArticleCategorySample2();
        assertThat(articleCategory1).isNotEqualTo(articleCategory2);
    }

    @Test
    void articleTest() throws Exception {
        ArticleCategory articleCategory = getArticleCategoryRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        articleCategory.addArticle(articleBack);
        assertThat(articleCategory.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getArticleCategory()).isEqualTo(articleCategory);

        articleCategory.removeArticle(articleBack);
        assertThat(articleCategory.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getArticleCategory()).isNull();

        articleCategory.articles(new HashSet<>(Set.of(articleBack)));
        assertThat(articleCategory.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getArticleCategory()).isEqualTo(articleCategory);

        articleCategory.setArticles(new HashSet<>());
        assertThat(articleCategory.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getArticleCategory()).isNull();
    }
}
