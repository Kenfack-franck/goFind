package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AlertTestSamples.*;
import static com.mycompany.myapp.domain.ItemTestSamples.*;
import static com.mycompany.myapp.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlertTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alert.class);
        Alert alert1 = getAlertSample1();
        Alert alert2 = new Alert();
        assertThat(alert1).isNotEqualTo(alert2);

        alert2.setId(alert1.getId());
        assertThat(alert1).isEqualTo(alert2);

        alert2 = getAlertSample2();
        assertThat(alert1).isNotEqualTo(alert2);
    }

    @Test
    void itemTest() throws Exception {
        Alert alert = getAlertRandomSampleGenerator();
        Item itemBack = getItemRandomSampleGenerator();

        alert.setItem(itemBack);
        assertThat(alert.getItem()).isEqualTo(itemBack);

        alert.item(null);
        assertThat(alert.getItem()).isNull();
    }

    @Test
    void utilisateurTest() throws Exception {
        Alert alert = getAlertRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        alert.setUtilisateur(utilisateurBack);
        assertThat(alert.getUtilisateur()).isEqualTo(utilisateurBack);

        alert.utilisateur(null);
        assertThat(alert.getUtilisateur()).isNull();
    }
}
