package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CarpoolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Carpool getCarpoolSample1() {
        return new Carpool().id(1L).origin("origin1").destination("destination1").seatsAvailable(1).description("description1");
    }

    public static Carpool getCarpoolSample2() {
        return new Carpool().id(2L).origin("origin2").destination("destination2").seatsAvailable(2).description("description2");
    }

    public static Carpool getCarpoolRandomSampleGenerator() {
        return new Carpool()
            .id(longCount.incrementAndGet())
            .origin(UUID.randomUUID().toString())
            .destination(UUID.randomUUID().toString())
            .seatsAvailable(intCount.incrementAndGet())
            .description(UUID.randomUUID().toString());
    }
}
