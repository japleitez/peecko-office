package com.peecko.admin.domain;

import static com.peecko.admin.domain.ArticleItemTestSamples.*;
import static com.peecko.admin.domain.ArticleListTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticleItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleItem.class);
        ArticleItem articleItem1 = getArticleItemSample1();
        ArticleItem articleItem2 = new ArticleItem();
        assertThat(articleItem1).isNotEqualTo(articleItem2);

        articleItem2.setId(articleItem1.getId());
        assertThat(articleItem1).isEqualTo(articleItem2);

        articleItem2 = getArticleItemSample2();
        assertThat(articleItem1).isNotEqualTo(articleItem2);
    }

    @Test
    void articleListTest() throws Exception {
        ArticleItem articleItem = getArticleItemRandomSampleGenerator();
        ArticleList articleListBack = getArticleListRandomSampleGenerator();

        articleItem.setArticleList(articleListBack);
        assertThat(articleItem.getArticleList()).isEqualTo(articleListBack);

        articleItem.articleList(null);
        assertThat(articleItem.getArticleList()).isNull();
    }
}
