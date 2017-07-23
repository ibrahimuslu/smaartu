package com.eeu.smaartu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eeu.smaartu.service.ModeService;
import com.eeu.smaartu.web.rest.util.HeaderUtil;
import com.eeu.smaartu.service.dto.ModeDTO;
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
 * REST controller for managing Mode.
 */
@RestController
@RequestMapping("/api")
public class ModeResource {

    private final Logger log = LoggerFactory.getLogger(ModeResource.class);

    private static final String ENTITY_NAME = "mode";

    private final ModeService modeService;

    public ModeResource(ModeService modeService) {
        this.modeService = modeService;
    }

    /**
     * POST  /modes : Create a new mode.
     *
     * @param modeDTO the modeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new modeDTO, or with status 400 (Bad Request) if the mode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/modes")
    @Timed
    public ResponseEntity<ModeDTO> createMode(@RequestBody ModeDTO modeDTO) throws URISyntaxException {
        log.debug("REST request to save Mode : {}", modeDTO);
        if (modeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mode cannot already have an ID")).body(null);
        }
        ModeDTO result = modeService.save(modeDTO);
        return ResponseEntity.created(new URI("/api/modes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modes : Updates an existing mode.
     *
     * @param modeDTO the modeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated modeDTO,
     * or with status 400 (Bad Request) if the modeDTO is not valid,
     * or with status 500 (Internal Server Error) if the modeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/modes")
    @Timed
    public ResponseEntity<ModeDTO> updateMode(@RequestBody ModeDTO modeDTO) throws URISyntaxException {
        log.debug("REST request to update Mode : {}", modeDTO);
        if (modeDTO.getId() == null) {
            return createMode(modeDTO);
        }
        ModeDTO result = modeService.save(modeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, modeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modes : get all the modes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of modes in body
     */
    @GetMapping("/modes")
    @Timed
    public List<ModeDTO> getAllModes() {
        log.debug("REST request to get all Modes");
        return modeService.findAll();
    }

    /**
     * GET  /modes/:id : get the "id" mode.
     *
     * @param id the id of the modeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the modeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/modes/{id}")
    @Timed
    public ResponseEntity<ModeDTO> getMode(@PathVariable Long id) {
        log.debug("REST request to get Mode : {}", id);
        ModeDTO modeDTO = modeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(modeDTO));
    }

    /**
     * DELETE  /modes/:id : delete the "id" mode.
     *
     * @param id the id of the modeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/modes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMode(@PathVariable Long id) {
        log.debug("REST request to delete Mode : {}", id);
        modeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
