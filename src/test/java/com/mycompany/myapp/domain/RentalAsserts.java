package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RentalAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAllPropertiesEquals(Rental expected, Rental actual) {
        assertRentalAutoGeneratedPropertiesEquals(expected, actual);
        assertRentalAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAllUpdatablePropertiesEquals(Rental expected, Rental actual) {
        assertRentalUpdatableFieldsEquals(expected, actual);
        assertRentalUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAutoGeneratedPropertiesEquals(Rental expected, Rental actual) {
        assertThat(expected)
            .as("Verify Rental auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalUpdatableFieldsEquals(Rental expected, Rental actual) {
        assertThat(expected)
            .as("Verify Rental relevant properties")
            .satisfies(e -> assertThat(e.getStartDate()).as("check startDate").isEqualTo(actual.getStartDate()))
            .satisfies(e -> assertThat(e.getEndDate()).as("check endDate").isEqualTo(actual.getEndDate()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getPrice()).as("check price").isEqualTo(actual.getPrice()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalUpdatableRelationshipsEquals(Rental expected, Rental actual) {
        assertThat(expected)
            .as("Verify Rental relationships")
            .satisfies(e -> assertThat(e.getRenter()).as("check renter").isEqualTo(actual.getRenter()))
            .satisfies(e -> assertThat(e.getProperty()).as("check property").isEqualTo(actual.getProperty()));
    }
}
