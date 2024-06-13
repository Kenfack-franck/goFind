package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AlertTestSamples.*;
import static com.mycompany.myapp.domain.CarpoolTestSamples.*;
import static com.mycompany.myapp.domain.ItemTestSamples.*;
import static com.mycompany.myapp.domain.PassengerTestSamples.*;
import static com.mycompany.myapp.domain.PropertyTestSamples.*;
import static com.mycompany.myapp.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UtilisateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utilisateur.class);
        Utilisateur utilisateur1 = getUtilisateurSample1();
        Utilisateur utilisateur2 = new Utilisateur();
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);

        utilisateur2.setId(utilisateur1.getId());
        assertThat(utilisateur1).isEqualTo(utilisateur2);

        utilisateur2 = getUtilisateurSample2();
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
    }

    @Test
    void itemsTest() throws Exception {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        utilisateur.addItems(itemBack);
        assertThat(utilisateur.getItems()).containsOnly(itemBack);
        assertThat(itemBack.getOwner()).isEqualTo(utilisateur);

        utilisateur.removeItems(itemBack);
        assertThat(utilisateur.getItems()).doesNotContain(itemBack);
        assertThat(itemBack.getOwner()).isNull();

        utilisateur.items(new HashSet<>(Set.of(itemBack)));
        assertThat(utilisateur.getItems()).containsOnly(itemBack);
        assertThat(itemBack.getOwner()).isEqualTo(utilisateur);

        utilisateur.setItems(new HashSet<>());
        assertThat(utilisateur.getItems()).doesNotContain(itemBack);
        assertThat(itemBack.getOwner()).isNull();
    }

    @Test
    void alertsTest() throws Exception {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Alert alertBack = getAlertRandomSampleGenerator();

        utilisateur.addAlerts(alertBack);
        assertThat(utilisateur.getAlerts()).containsOnly(alertBack);
        assertThat(alertBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.removeAlerts(alertBack);
        assertThat(utilisateur.getAlerts()).doesNotContain(alertBack);
        assertThat(alertBack.getUtilisateur()).isNull();

        utilisateur.alerts(new HashSet<>(Set.of(alertBack)));
        assertThat(utilisateur.getAlerts()).containsOnly(alertBack);
        assertThat(alertBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.setAlerts(new HashSet<>());
        assertThat(utilisateur.getAlerts()).doesNotContain(alertBack);
        assertThat(alertBack.getUtilisateur()).isNull();
    }

    @Test
    void carpoolsTest() throws Exception {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Carpool carpoolBack = getCarpoolRandomSampleGenerator();

        utilisateur.addCarpools(carpoolBack);
        assertThat(utilisateur.getCarpools()).containsOnly(carpoolBack);
        assertThat(carpoolBack.getDriver()).isEqualTo(utilisateur);

        utilisateur.removeCarpools(carpoolBack);
        assertThat(utilisateur.getCarpools()).doesNotContain(carpoolBack);
        assertThat(carpoolBack.getDriver()).isNull();

        utilisateur.carpools(new HashSet<>(Set.of(carpoolBack)));
        assertThat(utilisateur.getCarpools()).containsOnly(carpoolBack);
        assertThat(carpoolBack.getDriver()).isEqualTo(utilisateur);

        utilisateur.setCarpools(new HashSet<>());
        assertThat(utilisateur.getCarpools()).doesNotContain(carpoolBack);
        assertThat(carpoolBack.getDriver()).isNull();
    }

    @Test
    void passengersTest() throws Exception {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Passenger passengerBack = getPassengerRandomSampleGenerator();

        utilisateur.addPassengers(passengerBack);
        assertThat(utilisateur.getPassengers()).containsOnly(passengerBack);
        assertThat(passengerBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.removePassengers(passengerBack);
        assertThat(utilisateur.getPassengers()).doesNotContain(passengerBack);
        assertThat(passengerBack.getUtilisateur()).isNull();

        utilisateur.passengers(new HashSet<>(Set.of(passengerBack)));
        assertThat(utilisateur.getPassengers()).containsOnly(passengerBack);
        assertThat(passengerBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.setPassengers(new HashSet<>());
        assertThat(utilisateur.getPassengers()).doesNotContain(passengerBack);
        assertThat(passengerBack.getUtilisateur()).isNull();
    }

    @Test
    void propertiesTest() throws Exception {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        utilisateur.addProperties(propertyBack);
        assertThat(utilisateur.getProperties()).containsOnly(propertyBack);
        assertThat(propertyBack.getOwner()).isEqualTo(utilisateur);

        utilisateur.removeProperties(propertyBack);
        assertThat(utilisateur.getProperties()).doesNotContain(propertyBack);
        assertThat(propertyBack.getOwner()).isNull();

        utilisateur.properties(new HashSet<>(Set.of(propertyBack)));
        assertThat(utilisateur.getProperties()).containsOnly(propertyBack);
        assertThat(propertyBack.getOwner()).isEqualTo(utilisateur);

        utilisateur.setProperties(new HashSet<>());
        assertThat(utilisateur.getProperties()).doesNotContain(propertyBack);
        assertThat(propertyBack.getOwner()).isNull();
    }
}
