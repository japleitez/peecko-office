package com.peecko.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NotificationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Notification getNotificationSample1() {
        return new Notification().id(1L).companyId(1L).title("title1").message("message1").imageUrl("imageUrl1").videoUrl("videoUrl1");
    }

    public static Notification getNotificationSample2() {
        return new Notification().id(2L).companyId(2L).title("title2").message("message2").imageUrl("imageUrl2").videoUrl("videoUrl2");
    }

    public static Notification getNotificationRandomSampleGenerator() {
        return new Notification()
            .id(longCount.incrementAndGet())
            .companyId(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .message(UUID.randomUUID().toString())
            .imageUrl(UUID.randomUUID().toString())
            .videoUrl(UUID.randomUUID().toString());
    }
}
