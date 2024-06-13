package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UtilisateurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Utilisateur getUtilisateurSample1() {
        return new Utilisateur().id(1L).name("name1").email("email1").password("password1").phoneNumber("phoneNumber1");
    }

    public static Utilisateur getUtilisateurSample2() {
        return new Utilisateur().id(2L).name("name2").email("email2").password("password2").phoneNumber("phoneNumber2");
    }

    public static Utilisateur getUtilisateurRandomSampleGenerator() {
        return new Utilisateur()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString());
    }
}
