package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PropertyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Property getPropertySample1() {
        return new Property()
            .id(1L)
            .location("location1")
            .description("description1")
            .availabilityStatus("availabilityStatus1")
            .propertySize(1)
            .type("type1");
    }

    public static Property getPropertySample2() {
        return new Property()
            .id(2L)
            .location("location2")
            .description("description2")
            .availabilityStatus("availabilityStatus2")
            .propertySize(2)
            .type("type2");
    }

    public static Property getPropertyRandomSampleGenerator() {
        return new Property()
            .id(longCount.incrementAndGet())
            .location(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .availabilityStatus(UUID.randomUUID().toString())
            .propertySize(intCount.incrementAndGet())
            .type(UUID.randomUUID().toString());
    }
}
