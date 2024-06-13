package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CarpoolTestSamples.*;
import static com.mycompany.myapp.domain.PassengerTestSamples.*;
import static com.mycompany.myapp.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CarpoolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carpool.class);
        Carpool carpool1 = getCarpoolSample1();
        Carpool carpool2 = new Carpool();
        assertThat(carpool1).isNotEqualTo(carpool2);

        carpool2.setId(carpool1.getId());
        assertThat(carpool1).isEqualTo(carpool2);

        carpool2 = getCarpoolSample2();
        assertThat(carpool1).isNotEqualTo(carpool2);
    }

    @Test
    void passengersTest() throws Exception {
        Carpool carpool = getCarpoolRandomSampleGenerator();
        Passenger passengerBack = getPassengerRandomSampleGenerator();

        carpool.addPassengers(passengerBack);
        assertThat(carpool.getPassengers()).containsOnly(passengerBack);

        carpool.removePassengers(passengerBack);
        assertThat(carpool.getPassengers()).doesNotContain(passengerBack);

        carpool.passengers(new HashSet<>(Set.of(passengerBack)));
        assertThat(carpool.getPassengers()).containsOnly(passengerBack);

        carpool.setPassengers(new HashSet<>());
        assertThat(carpool.getPassengers()).doesNotContain(passengerBack);
    }

    @Test
    void driverTest() throws Exception {
        Carpool carpool = getCarpoolRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        carpool.setDriver(utilisateurBack);
        assertThat(carpool.getDriver()).isEqualTo(utilisateurBack);

        carpool.driver(null);
        assertThat(carpool.getDriver()).isNull();
    }
}
