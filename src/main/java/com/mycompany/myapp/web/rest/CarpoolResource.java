package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CarpoolRepository;
import com.mycompany.myapp.service.CarpoolService;
import com.mycompany.myapp.service.dto.CarpoolDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Carpool}.
 */
@RestController
@RequestMapping("/api/carpools")
public class CarpoolResource {

    private final Logger log = LoggerFactory.getLogger(CarpoolResource.class);

    private static final String ENTITY_NAME = "carpool";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarpoolService carpoolService;

    private final CarpoolRepository carpoolRepository;

    public CarpoolResource(CarpoolService carpoolService, CarpoolRepository carpoolRepository) {
        this.carpoolService = carpoolService;
        this.carpoolRepository = carpoolRepository;
    }

    /**
     * {@code POST  /carpools} : Create a new carpool.
     *
     * @param carpoolDTO the carpoolDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carpoolDTO, or with status {@code 400 (Bad Request)} if the carpool has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CarpoolDTO> createCarpool(@Valid @RequestBody CarpoolDTO carpoolDTO) throws URISyntaxException {
        log.debug("REST request to save Carpool : {}", carpoolDTO);
        if (carpoolDTO.getId() != null) {
            throw new BadRequestAlertException("A new carpool cannot already have an ID", ENTITY_NAME, "idexists");
        }
        carpoolDTO = carpoolService.save(carpoolDTO);
        return ResponseEntity.created(new URI("/api/carpools/" + carpoolDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carpoolDTO.getId().toString()))
            .body(carpoolDTO);
    }

    /**
     * {@code PUT  /carpools/:id} : Updates an existing carpool.
     *
     * @param id the id of the carpoolDTO to save.
     * @param carpoolDTO the carpoolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carpoolDTO,
     * or with status {@code 400 (Bad Request)} if the carpoolDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carpoolDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarpoolDTO> updateCarpool(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CarpoolDTO carpoolDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Carpool : {}, {}", id, carpoolDTO);
        if (carpoolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carpoolDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carpoolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        carpoolDTO = carpoolService.update(carpoolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carpoolDTO.getId().toString()))
            .body(carpoolDTO);
    }

    /**
     * {@code PATCH  /carpools/:id} : Partial updates given fields of an existing carpool, field will ignore if it is null
     *
     * @param id the id of the carpoolDTO to save.
     * @param carpoolDTO the carpoolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carpoolDTO,
     * or with status {@code 400 (Bad Request)} if the carpoolDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carpoolDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carpoolDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarpoolDTO> partialUpdateCarpool(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CarpoolDTO carpoolDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Carpool partially : {}, {}", id, carpoolDTO);
        if (carpoolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carpoolDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carpoolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarpoolDTO> result = carpoolService.partialUpdate(carpoolDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carpoolDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carpools} : get all the carpools.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carpools in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CarpoolDTO>> getAllCarpools(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Carpools");
        Page<CarpoolDTO> page;
        if (eagerload) {
            page = carpoolService.findAllWithEagerRelationships(pageable);
        } else {
            page = carpoolService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carpools/:id} : get the "id" carpool.
     *
     * @param id the id of the carpoolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carpoolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarpoolDTO> getCarpool(@PathVariable("id") Long id) {
        log.debug("REST request to get Carpool : {}", id);
        Optional<CarpoolDTO> carpoolDTO = carpoolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carpoolDTO);
    }

    /**
     * {@code DELETE  /carpools/:id} : delete the "id" carpool.
     *
     * @param id the id of the carpoolDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarpool(@PathVariable("id") Long id) {
        log.debug("REST request to delete Carpool : {}", id);
        carpoolService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
