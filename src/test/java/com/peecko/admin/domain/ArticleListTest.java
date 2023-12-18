package com.peecko.admin.domain;

import static com.peecko.admin.domain.ApsUserTestSamples.*;
import static com.peecko.admin.domain.ArticleItemTestSamples.*;
import static com.peecko.admin.domain.ArticleListTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.peecko.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ArticleListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleList.class);
        ArticleList articleList1 = getArticleListSample1();
        ArticleList articleList2 = new ArticleList();
        assertThat(articleList1).isNotEqualTo(articleList2);

        articleList2.setId(articleList1.getId());
        assertThat(articleList1).isEqualTo(articleList2);

        articleList2 = getArticleListSample2();
        assertThat(articleList1).isNotEqualTo(articleList2);
    }

    @Test
    void articleItemTest() throws Exception {
        ArticleList articleList = getArticleListRandomSampleGenerator();
        ArticleItem articleItemBack = getArticleItemRandomSampleGenerator();

        articleList.addArticleItem(articleItemBack);
        assertThat(articleList.getArticleItems()).containsOnly(articleItemBack);
        assertThat(articleItemBack.getArticleList()).isEqualTo(articleList);

        articleList.removeArticleItem(articleItemBack);
        assertThat(articleList.getArticleItems()).doesNotContain(articleItemBack);
        assertThat(articleItemBack.getArticleList()).isNull();

        articleList.articleItems(new HashSet<>(Set.of(articleItemBack)));
        assertThat(articleList.getArticleItems()).containsOnly(articleItemBack);
        assertThat(articleItemBack.getArticleList()).isEqualTo(articleList);

        articleList.setArticleItems(new HashSet<>());
        assertThat(articleList.getArticleItems()).doesNotContain(articleItemBack);
        assertThat(articleItemBack.getArticleList()).isNull();
    }

    @Test
    void apsUserTest() throws Exception {
        ArticleList articleList = getArticleListRandomSampleGenerator();
        ApsUser apsUserBack = getApsUserRandomSampleGenerator();

        articleList.setApsUser(apsUserBack);
        assertThat(articleList.getApsUser()).isEqualTo(apsUserBack);

        articleList.apsUser(null);
        assertThat(articleList.getApsUser()).isNull();
    }
}
