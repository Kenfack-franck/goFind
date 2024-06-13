package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.RentalAsserts.*;
import static com.mycompany.myapp.domain.RentalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RentalMapperTest {

    private RentalMapper rentalMapper;

    @BeforeEach
    void setUp() {
        rentalMapper = new RentalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRentalSample1();
        var actual = rentalMapper.toEntity(rentalMapper.toDto(expected));
        assertRentalAllPropertiesEquals(expected, actual);
    }
}
