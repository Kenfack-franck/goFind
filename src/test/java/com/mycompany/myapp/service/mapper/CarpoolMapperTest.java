package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CarpoolAsserts.*;
import static com.mycompany.myapp.domain.CarpoolTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarpoolMapperTest {

    private CarpoolMapper carpoolMapper;

    @BeforeEach
    void setUp() {
        carpoolMapper = new CarpoolMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCarpoolSample1();
        var actual = carpoolMapper.toEntity(carpoolMapper.toDto(expected));
        assertCarpoolAllPropertiesEquals(expected, actual);
    }
}
