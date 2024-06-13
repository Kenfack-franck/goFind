package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CarpoolAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Carpool;
import com.mycompany.myapp.repository.CarpoolRepository;
import com.mycompany.myapp.service.CarpoolService;
import com.mycompany.myapp.service.dto.CarpoolDTO;
import com.mycompany.myapp.service.mapper.CarpoolMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CarpoolResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CarpoolResourceIT {

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEPARTURE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPARTURE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SEATS_AVAILABLE = 1;
    private static final Integer UPDATED_SEATS_AVAILABLE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String ENTITY_API_URL = "/api/carpools";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CarpoolRepository carpoolRepository;

    @Mock
    private CarpoolRepository carpoolRepositoryMock;

    @Autowired
    private CarpoolMapper carpoolMapper;

    @Mock
    private CarpoolService carpoolServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarpoolMockMvc;

    private Carpool carpool;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carpool createEntity(EntityManager em) {
        Carpool carpool = new Carpool()
            .origin(DEFAULT_ORIGIN)
            .destination(DEFAULT_DESTINATION)
            .departureTime(DEFAULT_DEPARTURE_TIME)
            .seatsAvailable(DEFAULT_SEATS_AVAILABLE)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE);
        return carpool;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carpool createUpdatedEntity(EntityManager em) {
        Carpool carpool = new Carpool()
            .origin(UPDATED_ORIGIN)
            .destination(UPDATED_DESTINATION)
            .departureTime(UPDATED_DEPARTURE_TIME)
            .seatsAvailable(UPDATED_SEATS_AVAILABLE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);
        return carpool;
    }

    @BeforeEach
    public void initTest() {
        carpool = createEntity(em);
    }

    @Test
    @Transactional
    void createCarpool() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Carpool
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);
        var returnedCarpoolDTO = om.readValue(
            restCarpoolMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carpoolDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CarpoolDTO.class
        );

        // Validate the Carpool in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCarpool = carpoolMapper.toEntity(returnedCarpoolDTO);
        assertCarpoolUpdatableFieldsEquals(returnedCarpool, getPersistedCarpool(returnedCarpool));
    }

    @Test
    @Transactional
    void createCarpoolWithExistingId() throws Exception {
        // Create the Carpool with an existing ID
        carpool.setId(1L);
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarpoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carpoolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carpool in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOriginIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carpool.setOrigin(null);

        // Create the Carpool, which fails.
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        restCarpoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carpoolDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDestinationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carpool.setDestination(null);

        // Create the Carpool, which fails.
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        restCarpoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carpoolDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartureTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carpool.setDepartureTime(null);

        // Create the Carpool, which fails.
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        restCarpoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carpoolDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeatsAvailableIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carpool.setSeatsAvailable(null);

        // Create the Carpool, which fails.
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        restCarpoolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carpoolDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarpools() throws Exception {
        // Initialize the database
        carpoolRepository.saveAndFlush(carpool);

        // Get all the carpoolList
        restCarpoolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carpool.getId().intValue())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].departureTime").value(hasItem(DEFAULT_DEPARTURE_TIME.toString())))
            .andExpect(jsonPath("$.[*].seatsAvailable").value(hasItem(DEFAULT_SEATS_AVAILABLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCarpoolsWithEagerRelationshipsIsEnabled() throws Exception {
        when(carpoolServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCarpoolMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(carpoolServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCarpoolsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(carpoolServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCarpoolMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(carpoolRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCarpool() throws Exception {
        // Initialize the database
        carpoolRepository.saveAndFlush(carpool);

        // Get the carpool
        restCarpoolMockMvc
            .perform(get(ENTITY_API_URL_ID, carpool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carpool.getId().intValue()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION))
            .andExpect(jsonPath("$.departureTime").value(DEFAULT_DEPARTURE_TIME.toString()))
            .andExpect(jsonPath("$.seatsAvailable").value(DEFAULT_SEATS_AVAILABLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingCarpool() throws Exception {
        // Get the carpool
        restCarpoolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCarpool() throws Exception {
        // Initialize the database
        carpoolRepository.saveAndFlush(carpool);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carpool
        Carpool updatedCarpool = carpoolRepository.findById(carpool.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCarpool are not directly saved in db
        em.detach(updatedCarpool);
        updatedCarpool
            .origin(UPDATED_ORIGIN)
            .destination(UPDATED_DESTINATION)
            .departureTime(UPDATED_DEPARTURE_TIME)
            .seatsAvailable(UPDATED_SEATS_AVAILABLE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(updatedCarpool);

        restCarpoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carpoolDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carpoolDTO))
            )
            .andExpect(status().isOk());

        // Validate the Carpool in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCarpoolToMatchAllProperties(updatedCarpool);
    }

    @Test
    @Transactional
    void putNonExistingCarpool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carpool.setId(longCount.incrementAndGet());

        // Create the Carpool
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarpoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carpoolDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carpoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carpool in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarpool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carpool.setId(longCount.incrementAndGet());

        // Create the Carpool
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarpoolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carpoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carpool in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarpool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carpool.setId(longCount.incrementAndGet());

        // Create the Carpool
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarpoolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carpoolDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carpool in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarpoolWithPatch() throws Exception {
        // Initialize the database
        carpoolRepository.saveAndFlush(carpool);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carpool using partial update
        Carpool partialUpdatedCarpool = new Carpool();
        partialUpdatedCarpool.setId(carpool.getId());

        partialUpdatedCarpool
            .origin(UPDATED_ORIGIN)
            .departureTime(UPDATED_DEPARTURE_TIME)
            .seatsAvailable(UPDATED_SEATS_AVAILABLE)
            .description(UPDATED_DESCRIPTION);

        restCarpoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarpool.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarpool))
            )
            .andExpect(status().isOk());

        // Validate the Carpool in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarpoolUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCarpool, carpool), getPersistedCarpool(carpool));
    }

    @Test
    @Transactional
    void fullUpdateCarpoolWithPatch() throws Exception {
        // Initialize the database
        carpoolRepository.saveAndFlush(carpool);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carpool using partial update
        Carpool partialUpdatedCarpool = new Carpool();
        partialUpdatedCarpool.setId(carpool.getId());

        partialUpdatedCarpool
            .origin(UPDATED_ORIGIN)
            .destination(UPDATED_DESTINATION)
            .departureTime(UPDATED_DEPARTURE_TIME)
            .seatsAvailable(UPDATED_SEATS_AVAILABLE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);

        restCarpoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarpool.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarpool))
            )
            .andExpect(status().isOk());

        // Validate the Carpool in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarpoolUpdatableFieldsEquals(partialUpdatedCarpool, getPersistedCarpool(partialUpdatedCarpool));
    }

    @Test
    @Transactional
    void patchNonExistingCarpool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carpool.setId(longCount.incrementAndGet());

        // Create the Carpool
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarpoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carpoolDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carpoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carpool in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarpool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carpool.setId(longCount.incrementAndGet());

        // Create the Carpool
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarpoolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carpoolDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carpool in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarpool() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carpool.setId(longCount.incrementAndGet());

        // Create the Carpool
        CarpoolDTO carpoolDTO = carpoolMapper.toDto(carpool);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarpoolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(carpoolDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carpool in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarpool() throws Exception {
        // Initialize the database
        carpoolRepository.saveAndFlush(carpool);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the carpool
        restCarpoolMockMvc
            .perform(delete(ENTITY_API_URL_ID, carpool.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return carpoolRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Carpool getPersistedCarpool(Carpool carpool) {
        return carpoolRepository.findById(carpool.getId()).orElseThrow();
    }

    protected void assertPersistedCarpoolToMatchAllProperties(Carpool expectedCarpool) {
        assertCarpoolAllPropertiesEquals(expectedCarpool, getPersistedCarpool(expectedCarpool));
    }

    protected void assertPersistedCarpoolToMatchUpdatableProperties(Carpool expectedCarpool) {
        assertCarpoolAllUpdatablePropertiesEquals(expectedCarpool, getPersistedCarpool(expectedCarpool));
    }
}
