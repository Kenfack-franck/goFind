package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AlertTestSamples.*;
import static com.mycompany.myapp.domain.ItemTestSamples.*;
import static com.mycompany.myapp.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Item.class);
        Item item1 = getItemSample1();
        Item item2 = new Item();
        assertThat(item1).isNotEqualTo(item2);

        item2.setId(item1.getId());
        assertThat(item1).isEqualTo(item2);

        item2 = getItemSample2();
        assertThat(item1).isNotEqualTo(item2);
    }

    @Test
    void alertsTest() throws Exception {
        Item item = getItemRandomSampleGenerator();
        Alert alertBack = getAlertRandomSampleGenerator();

        item.addAlerts(alertBack);
        assertThat(item.getAlerts()).containsOnly(alertBack);
        assertThat(alertBack.getItem()).isEqualTo(item);

        item.removeAlerts(alertBack);
        assertThat(item.getAlerts()).doesNotContain(alertBack);
        assertThat(alertBack.getItem()).isNull();

        item.alerts(new HashSet<>(Set.of(alertBack)));
        assertThat(item.getAlerts()).containsOnly(alertBack);
        assertThat(alertBack.getItem()).isEqualTo(item);

        item.setAlerts(new HashSet<>());
        assertThat(item.getAlerts()).doesNotContain(alertBack);
        assertThat(alertBack.getItem()).isNull();
    }

    @Test
    void ownerTest() throws Exception {
        Item item = getItemRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        item.setOwner(utilisateurBack);
        assertThat(item.getOwner()).isEqualTo(utilisateurBack);

        item.owner(null);
        assertThat(item.getOwner()).isNull();
    }
}
