package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CarpoolDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Carpool}.
 */
public interface CarpoolService {
    /**
     * Save a carpool.
     *
     * @param carpoolDTO the entity to save.
     * @return the persisted entity.
     */
    CarpoolDTO save(CarpoolDTO carpoolDTO);

    /**
     * Updates a carpool.
     *
     * @param carpoolDTO the entity to update.
     * @return the persisted entity.
     */
    CarpoolDTO update(CarpoolDTO carpoolDTO);

    /**
     * Partially updates a carpool.
     *
     * @param carpoolDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarpoolDTO> partialUpdate(CarpoolDTO carpoolDTO);

    /**
     * Get all the carpools.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarpoolDTO> findAll(Pageable pageable);

    /**
     * Get all the carpools with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarpoolDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" carpool.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarpoolDTO> findOne(Long id);

    /**
     * Delete the "id" carpool.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
