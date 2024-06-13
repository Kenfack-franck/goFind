package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.PropertyTestSamples.*;
import static com.mycompany.myapp.domain.RentalTestSamples.*;
import static com.mycompany.myapp.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RentalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rental.class);
        Rental rental1 = getRentalSample1();
        Rental rental2 = new Rental();
        assertThat(rental1).isNotEqualTo(rental2);

        rental2.setId(rental1.getId());
        assertThat(rental1).isEqualTo(rental2);

        rental2 = getRentalSample2();
        assertThat(rental1).isNotEqualTo(rental2);
    }

    @Test
    void renterTest() throws Exception {
        Rental rental = getRentalRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        rental.setRenter(utilisateurBack);
        assertThat(rental.getRenter()).isEqualTo(utilisateurBack);

        rental.renter(null);
        assertThat(rental.getRenter()).isNull();
    }

    @Test
    void propertyTest() throws Exception {
        Rental rental = getRentalRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        rental.setProperty(propertyBack);
        assertThat(rental.getProperty()).isEqualTo(propertyBack);

        rental.property(null);
        assertThat(rental.getProperty()).isNull();
    }
}
