package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PassengerRepository;
import com.mycompany.myapp.service.PassengerService;
import com.mycompany.myapp.service.dto.PassengerDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Passenger}.
 */
@RestController
@RequestMapping("/api/passengers")
public class PassengerResource {

    private final Logger log = LoggerFactory.getLogger(PassengerResource.class);

    private static final String ENTITY_NAME = "passenger";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PassengerService passengerService;

    private final PassengerRepository passengerRepository;

    public PassengerResource(PassengerService passengerService, PassengerRepository passengerRepository) {
        this.passengerService = passengerService;
        this.passengerRepository = passengerRepository;
    }

    /**
     * {@code POST  /passengers} : Create a new passenger.
     *
     * @param passengerDTO the passengerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new passengerDTO, or with status {@code 400 (Bad Request)} if the passenger has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PassengerDTO> createPassenger(@Valid @RequestBody PassengerDTO passengerDTO) throws URISyntaxException {
        log.debug("REST request to save Passenger : {}", passengerDTO);
        if (passengerDTO.getId() != null) {
            throw new BadRequestAlertException("A new passenger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        passengerDTO = passengerService.save(passengerDTO);
        return ResponseEntity.created(new URI("/api/passengers/" + passengerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, passengerDTO.getId().toString()))
            .body(passengerDTO);
    }

    /**
     * {@code PUT  /passengers/:id} : Updates an existing passenger.
     *
     * @param id the id of the passengerDTO to save.
     * @param passengerDTO the passengerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passengerDTO,
     * or with status {@code 400 (Bad Request)} if the passengerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the passengerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PassengerDTO passengerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Passenger : {}, {}", id, passengerDTO);
        if (passengerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, passengerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!passengerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        passengerDTO = passengerService.update(passengerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, passengerDTO.getId().toString()))
            .body(passengerDTO);
    }

    /**
     * {@code PATCH  /passengers/:id} : Partial updates given fields of an existing passenger, field will ignore if it is null
     *
     * @param id the id of the passengerDTO to save.
     * @param passengerDTO the passengerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passengerDTO,
     * or with status {@code 400 (Bad Request)} if the passengerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the passengerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the passengerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PassengerDTO> partialUpdatePassenger(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PassengerDTO passengerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Passenger partially : {}, {}", id, passengerDTO);
        if (passengerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, passengerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!passengerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PassengerDTO> result = passengerService.partialUpdate(passengerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, passengerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /passengers} : get all the passengers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of passengers in body.
     */
    @GetMapping("")
    public List<PassengerDTO> getAllPassengers() {
        log.debug("REST request to get all Passengers");
        return passengerService.findAll();
    }

    /**
     * {@code GET  /passengers/:id} : get the "id" passenger.
     *
     * @param id the id of the passengerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the passengerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassenger(@PathVariable("id") Long id) {
        log.debug("REST request to get Passenger : {}", id);
        Optional<PassengerDTO> passengerDTO = passengerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(passengerDTO);
    }

    /**
     * {@code DELETE  /passengers/:id} : delete the "id" passenger.
     *
     * @param id the id of the passengerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable("id") Long id) {
        log.debug("REST request to delete Passenger : {}", id);
        passengerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
