package com.eeu.smaartu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eeu.smaartu.service.ControlSystemService;
import com.eeu.smaartu.web.rest.util.HeaderUtil;
import com.eeu.smaartu.web.rest.util.PaginationUtil;
import com.eeu.smaartu.service.dto.ControlSystemDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ControlSystem.
 */
@RestController
@RequestMapping("/api")
public class ControlSystemResource {

    private final Logger log = LoggerFactory.getLogger(ControlSystemResource.class);

    private static final String ENTITY_NAME = "controlSystem";

    private final ControlSystemService controlSystemService;

    public ControlSystemResource(ControlSystemService controlSystemService) {
        this.controlSystemService = controlSystemService;
    }

    /**
     * POST  /control-systems : Create a new controlSystem.
     *
     * @param controlSystemDTO the controlSystemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new controlSystemDTO, or with status 400 (Bad Request) if the controlSystem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/control-systems")
    @Timed
    public ResponseEntity<ControlSystemDTO> createControlSystem(@RequestBody ControlSystemDTO controlSystemDTO) throws URISyntaxException {
        log.debug("REST request to save ControlSystem : {}", controlSystemDTO);
        if (controlSystemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new controlSystem cannot already have an ID")).body(null);
        }
        ControlSystemDTO result = controlSystemService.save(controlSystemDTO);
        return ResponseEntity.created(new URI("/api/control-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /control-systems : Updates an existing controlSystem.
     *
     * @param controlSystemDTO the controlSystemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated controlSystemDTO,
     * or with status 400 (Bad Request) if the controlSystemDTO is not valid,
     * or with status 500 (Internal Server Error) if the controlSystemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/control-systems")
    @Timed
    public ResponseEntity<ControlSystemDTO> updateControlSystem(@RequestBody ControlSystemDTO controlSystemDTO) throws URISyntaxException {
        log.debug("REST request to update ControlSystem : {}", controlSystemDTO);
        if (controlSystemDTO.getId() == null) {
            return createControlSystem(controlSystemDTO);
        }
        ControlSystemDTO result = controlSystemService.save(controlSystemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, controlSystemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /control-systems : get all the controlSystems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of controlSystems in body
     */
    @GetMapping("/control-systems")
    @Timed
    public ResponseEntity<List<ControlSystemDTO>> getAllControlSystems(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ControlSystems");
        Page<ControlSystemDTO> page = controlSystemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/control-systems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /control-systems/:id : get the "id" controlSystem.
     *
     * @param id the id of the controlSystemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the controlSystemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/control-systems/{id}")
    @Timed
    public ResponseEntity<ControlSystemDTO> getControlSystem(@PathVariable Long id) {
        log.debug("REST request to get ControlSystem : {}", id);
        ControlSystemDTO controlSystemDTO = controlSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(controlSystemDTO));
    }

    /**
     * DELETE  /control-systems/:id : delete the "id" controlSystem.
     *
     * @param id the id of the controlSystemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/control-systems/{id}")
    @Timed
    public ResponseEntity<Void> deleteControlSystem(@PathVariable Long id) {
        log.debug("REST request to delete ControlSystem : {}", id);
        controlSystemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
