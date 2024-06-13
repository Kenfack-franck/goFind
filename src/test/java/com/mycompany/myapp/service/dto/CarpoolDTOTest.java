package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CarpoolDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarpoolDTO.class);
        CarpoolDTO carpoolDTO1 = new CarpoolDTO();
        carpoolDTO1.setId(1L);
        CarpoolDTO carpoolDTO2 = new CarpoolDTO();
        assertThat(carpoolDTO1).isNotEqualTo(carpoolDTO2);
        carpoolDTO2.setId(carpoolDTO1.getId());
        assertThat(carpoolDTO1).isEqualTo(carpoolDTO2);
        carpoolDTO2.setId(2L);
        assertThat(carpoolDTO1).isNotEqualTo(carpoolDTO2);
        carpoolDTO1.setId(null);
        assertThat(carpoolDTO1).isNotEqualTo(carpoolDTO2);
    }
}
