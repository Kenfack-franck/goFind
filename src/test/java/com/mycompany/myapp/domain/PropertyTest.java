package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.PropertyTestSamples.*;
import static com.mycompany.myapp.domain.RentalTestSamples.*;
import static com.mycompany.myapp.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PropertyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Property.class);
        Property property1 = getPropertySample1();
        Property property2 = new Property();
        assertThat(property1).isNotEqualTo(property2);

        property2.setId(property1.getId());
        assertThat(property1).isEqualTo(property2);

        property2 = getPropertySample2();
        assertThat(property1).isNotEqualTo(property2);
    }

    @Test
    void rentalsTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        Rental rentalBack = getRentalRandomSampleGenerator();

        property.addRentals(rentalBack);
        assertThat(property.getRentals()).containsOnly(rentalBack);
        assertThat(rentalBack.getProperty()).isEqualTo(property);

        property.removeRentals(rentalBack);
        assertThat(property.getRentals()).doesNotContain(rentalBack);
        assertThat(rentalBack.getProperty()).isNull();

        property.rentals(new HashSet<>(Set.of(rentalBack)));
        assertThat(property.getRentals()).containsOnly(rentalBack);
        assertThat(rentalBack.getProperty()).isEqualTo(property);

        property.setRentals(new HashSet<>());
        assertThat(property.getRentals()).doesNotContain(rentalBack);
        assertThat(rentalBack.getProperty()).isNull();
    }

    @Test
    void ownerTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        property.setOwner(utilisateurBack);
        assertThat(property.getOwner()).isEqualTo(utilisateurBack);

        property.owner(null);
        assertThat(property.getOwner()).isNull();
    }
}
