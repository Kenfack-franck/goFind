package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PassengerDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Passenger}.
 */
public interface PassengerService {
    /**
     * Save a passenger.
     *
     * @param passengerDTO the entity to save.
     * @return the persisted entity.
     */
    PassengerDTO save(PassengerDTO passengerDTO);

    /**
     * Updates a passenger.
     *
     * @param passengerDTO the entity to update.
     * @return the persisted entity.
     */
    PassengerDTO update(PassengerDTO passengerDTO);

    /**
     * Partially updates a passenger.
     *
     * @param passengerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PassengerDTO> partialUpdate(PassengerDTO passengerDTO);

    /**
     * Get all the passengers.
     *
     * @return the list of entities.
     */
    List<PassengerDTO> findAll();

    /**
     * Get the "id" passenger.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PassengerDTO> findOne(Long id);

    /**
     * Delete the "id" passenger.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
