package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PassengerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Passenger getPassengerSample1() {
        return new Passenger().id(1L).status("status1");
    }

    public static Passenger getPassengerSample2() {
        return new Passenger().id(2L).status("status2");
    }

    public static Passenger getPassengerRandomSampleGenerator() {
        return new Passenger().id(longCount.incrementAndGet()).status(UUID.randomUUID().toString());
    }
}
