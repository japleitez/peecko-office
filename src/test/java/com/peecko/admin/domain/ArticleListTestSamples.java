package com.peecko.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleListTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ArticleList getArticleListSample1() {
        return new ArticleList().id(1L).name("name1").counter(1);
    }

    public static ArticleList getArticleListSample2() {
        return new ArticleList().id(2L).name("name2").counter(2);
    }

    public static ArticleList getArticleListRandomSampleGenerator() {
        return new ArticleList().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).counter(intCount.incrementAndGet());
    }
}
