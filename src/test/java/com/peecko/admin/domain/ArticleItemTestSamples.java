package com.peecko.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArticleItem getArticleItemSample1() {
        return new ArticleItem().id(1L).previous("previous1").code("code1").next("next1");
    }

    public static ArticleItem getArticleItemSample2() {
        return new ArticleItem().id(2L).previous("previous2").code("code2").next("next2");
    }

    public static ArticleItem getArticleItemRandomSampleGenerator() {
        return new ArticleItem()
            .id(longCount.incrementAndGet())
            .previous(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .next(UUID.randomUUID().toString());
    }
}
