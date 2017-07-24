package com.eeu.smaartu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eeu.smaartu.service.InLocationService;
import com.eeu.smaartu.web.rest.util.HeaderUtil;
import com.eeu.smaartu.service.dto.InLocationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InLocation.
 */
@RestController
@RequestMapping("/api")
public class InLocationResource {

    private final Logger log = LoggerFactory.getLogger(InLocationResource.class);

    private static final String ENTITY_NAME = "inLocation";

    private final InLocationService inLocationService;

    public InLocationResource(InLocationService inLocationService) {
        this.inLocationService = inLocationService;
    }

    /**
     * POST  /in-locations : Create a new inLocation.
     *
     * @param inLocationDTO the inLocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inLocationDTO, or with status 400 (Bad Request) if the inLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/in-locations")
    @Timed
    public ResponseEntity<InLocationDTO> createInLocation(@RequestBody InLocationDTO inLocationDTO) throws URISyntaxException {
        log.debug("REST request to save InLocation : {}", inLocationDTO);
        if (inLocationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new inLocation cannot already have an ID")).body(null);
        }
        InLocationDTO result = inLocationService.save(inLocationDTO);
        return ResponseEntity.created(new URI("/api/in-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /in-locations : Updates an existing inLocation.
     *
     * @param inLocationDTO the inLocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inLocationDTO,
     * or with status 400 (Bad Request) if the inLocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the inLocationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/in-locations")
    @Timed
    public ResponseEntity<InLocationDTO> updateInLocation(@RequestBody InLocationDTO inLocationDTO) throws URISyntaxException {
        log.debug("REST request to update InLocation : {}", inLocationDTO);
        if (inLocationDTO.getId() == null) {
            return createInLocation(inLocationDTO);
        }
        InLocationDTO result = inLocationService.save(inLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /in-locations : get all the inLocations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inLocations in body
     */
    @GetMapping("/in-locations")
    @Timed
    public List<InLocationDTO> getAllInLocations() {
        log.debug("REST request to get all InLocations");
        return inLocationService.findAll();
    }

    /**
     * GET  /in-locations/:id : get the "id" inLocation.
     *
     * @param id the id of the inLocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inLocationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/in-locations/{id}")
    @Timed
    public ResponseEntity<InLocationDTO> getInLocation(@PathVariable Long id) {
        log.debug("REST request to get InLocation : {}", id);
        InLocationDTO inLocationDTO = inLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inLocationDTO));
    }

    /**
     * DELETE  /in-locations/:id : delete the "id" inLocation.
     *
     * @param id the id of the inLocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/in-locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteInLocation(@PathVariable Long id) {
        log.debug("REST request to delete InLocation : {}", id);
        inLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
