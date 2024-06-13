package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CarpoolTestSamples.*;
import static com.mycompany.myapp.domain.PassengerTestSamples.*;
import static com.mycompany.myapp.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PassengerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Passenger.class);
        Passenger passenger1 = getPassengerSample1();
        Passenger passenger2 = new Passenger();
        assertThat(passenger1).isNotEqualTo(passenger2);

        passenger2.setId(passenger1.getId());
        assertThat(passenger1).isEqualTo(passenger2);

        passenger2 = getPassengerSample2();
        assertThat(passenger1).isNotEqualTo(passenger2);
    }

    @Test
    void utilisateurTest() throws Exception {
        Passenger passenger = getPassengerRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        passenger.setUtilisateur(utilisateurBack);
        assertThat(passenger.getUtilisateur()).isEqualTo(utilisateurBack);

        passenger.utilisateur(null);
        assertThat(passenger.getUtilisateur()).isNull();
    }

    @Test
    void carpoolsTest() throws Exception {
        Passenger passenger = getPassengerRandomSampleGenerator();
        Carpool carpoolBack = getCarpoolRandomSampleGenerator();

        passenger.addCarpools(carpoolBack);
        assertThat(passenger.getCarpools()).containsOnly(carpoolBack);
        assertThat(carpoolBack.getPassengers()).containsOnly(passenger);

        passenger.removeCarpools(carpoolBack);
        assertThat(passenger.getCarpools()).doesNotContain(carpoolBack);
        assertThat(carpoolBack.getPassengers()).doesNotContain(passenger);

        passenger.carpools(new HashSet<>(Set.of(carpoolBack)));
        assertThat(passenger.getCarpools()).containsOnly(carpoolBack);
        assertThat(carpoolBack.getPassengers()).containsOnly(passenger);

        passenger.setCarpools(new HashSet<>());
        assertThat(passenger.getCarpools()).doesNotContain(carpoolBack);
        assertThat(carpoolBack.getPassengers()).doesNotContain(passenger);
    }
}
