package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.RentalAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Rental;
import com.mycompany.myapp.repository.RentalRepository;
import com.mycompany.myapp.service.dto.RentalDTO;
import com.mycompany.myapp.service.mapper.RentalMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RentalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RentalResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 0D;
    private static final Double UPDATED_PRICE = 1D;

    private static final String ENTITY_API_URL = "/api/rentals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRentalMockMvc;

    private Rental rental;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rental createEntity(EntityManager em) {
        Rental rental = new Rental().startDate(DEFAULT_START_DATE).endDate(DEFAULT_END_DATE).status(DEFAULT_STATUS).price(DEFAULT_PRICE);
        return rental;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rental createUpdatedEntity(EntityManager em) {
        Rental rental = new Rental().startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).status(UPDATED_STATUS).price(UPDATED_PRICE);
        return rental;
    }

    @BeforeEach
    public void initTest() {
        rental = createEntity(em);
    }

    @Test
    @Transactional
    void createRental() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);
        var returnedRentalDTO = om.readValue(
            restRentalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RentalDTO.class
        );

        // Validate the Rental in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRental = rentalMapper.toEntity(returnedRentalDTO);
        assertRentalUpdatableFieldsEquals(returnedRental, getPersistedRental(returnedRental));
    }

    @Test
    @Transactional
    void createRentalWithExistingId() throws Exception {
        // Create the Rental with an existing ID
        rental.setId(1L);
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rental.setStartDate(null);

        // Create the Rental, which fails.
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        restRentalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rental.setEndDate(null);

        // Create the Rental, which fails.
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        restRentalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rental.setPrice(null);

        // Create the Rental, which fails.
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        restRentalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRentals() throws Exception {
        // Initialize the database
        rentalRepository.saveAndFlush(rental);

        // Get all the rentalList
        restRentalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rental.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getRental() throws Exception {
        // Initialize the database
        rentalRepository.saveAndFlush(rental);

        // Get the rental
        restRentalMockMvc
            .perform(get(ENTITY_API_URL_ID, rental.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rental.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingRental() throws Exception {
        // Get the rental
        restRentalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRental() throws Exception {
        // Initialize the database
        rentalRepository.saveAndFlush(rental);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rental
        Rental updatedRental = rentalRepository.findById(rental.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRental are not directly saved in db
        em.detach(updatedRental);
        updatedRental.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).status(UPDATED_STATUS).price(UPDATED_PRICE);
        RentalDTO rentalDTO = rentalMapper.toDto(updatedRental);

        restRentalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentalDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRentalToMatchAllProperties(updatedRental);
    }

    @Test
    @Transactional
    void putNonExistingRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentalDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRentalWithPatch() throws Exception {
        // Initialize the database
        rentalRepository.saveAndFlush(rental);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rental using partial update
        Rental partialUpdatedRental = new Rental();
        partialUpdatedRental.setId(rental.getId());

        partialUpdatedRental.endDate(UPDATED_END_DATE);

        restRentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRental.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRental))
            )
            .andExpect(status().isOk());

        // Validate the Rental in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentalUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRental, rental), getPersistedRental(rental));
    }

    @Test
    @Transactional
    void fullUpdateRentalWithPatch() throws Exception {
        // Initialize the database
        rentalRepository.saveAndFlush(rental);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rental using partial update
        Rental partialUpdatedRental = new Rental();
        partialUpdatedRental.setId(rental.getId());

        partialUpdatedRental.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).status(UPDATED_STATUS).price(UPDATED_PRICE);

        restRentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRental.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRental))
            )
            .andExpect(status().isOk());

        // Validate the Rental in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentalUpdatableFieldsEquals(partialUpdatedRental, getPersistedRental(partialUpdatedRental));
    }

    @Test
    @Transactional
    void patchNonExistingRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rentalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRental() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rental.setId(longCount.incrementAndGet());

        // Create the Rental
        RentalDTO rentalDTO = rentalMapper.toDto(rental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rentalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rental in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRental() throws Exception {
        // Initialize the database
        rentalRepository.saveAndFlush(rental);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rental
        restRentalMockMvc
            .perform(delete(ENTITY_API_URL_ID, rental.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rentalRepository.count();
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

    protected Rental getPersistedRental(Rental rental) {
        return rentalRepository.findById(rental.getId()).orElseThrow();
    }

    protected void assertPersistedRentalToMatchAllProperties(Rental expectedRental) {
        assertRentalAllPropertiesEquals(expectedRental, getPersistedRental(expectedRental));
    }

    protected void assertPersistedRentalToMatchUpdatableProperties(Rental expectedRental) {
        assertRentalAllUpdatablePropertiesEquals(expectedRental, getPersistedRental(expectedRental));
    }
}
