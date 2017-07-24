package com.eeu.smaartu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eeu.smaartu.service.EndNodeUnitService;
import com.eeu.smaartu.web.rest.util.HeaderUtil;
import com.eeu.smaartu.web.rest.util.PaginationUtil;
import com.eeu.smaartu.service.dto.EndNodeUnitDTO;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing EndNodeUnit.
 */
@RestController
@RequestMapping("/api")
public class EndNodeUnitResource {

    private final Logger log = LoggerFactory.getLogger(EndNodeUnitResource.class);

    private static final String ENTITY_NAME = "endNodeUnit";

    private final EndNodeUnitService endNodeUnitService;

    public EndNodeUnitResource(EndNodeUnitService endNodeUnitService) {
        this.endNodeUnitService = endNodeUnitService;
    }

    /**
     * POST  /end-node-units : Create a new endNodeUnit.
     *
     * @param endNodeUnitDTO the endNodeUnitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new endNodeUnitDTO, or with status 400 (Bad Request) if the endNodeUnit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/end-node-units")
    @Timed
    public ResponseEntity<EndNodeUnitDTO> createEndNodeUnit(@RequestBody EndNodeUnitDTO endNodeUnitDTO) throws URISyntaxException {
        log.debug("REST request to save EndNodeUnit : {}", endNodeUnitDTO);
        if (endNodeUnitDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new endNodeUnit cannot already have an ID")).body(null);
        }
        EndNodeUnitDTO result = endNodeUnitService.save(endNodeUnitDTO);
        return ResponseEntity.created(new URI("/api/end-node-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /end-node-units : Updates an existing endNodeUnit.
     *
     * @param endNodeUnitDTO the endNodeUnitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated endNodeUnitDTO,
     * or with status 400 (Bad Request) if the endNodeUnitDTO is not valid,
     * or with status 500 (Internal Server Error) if the endNodeUnitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/end-node-units")
    @Timed
    public ResponseEntity<EndNodeUnitDTO> updateEndNodeUnit(@RequestBody EndNodeUnitDTO endNodeUnitDTO) throws URISyntaxException {
        log.debug("REST request to update EndNodeUnit : {}", endNodeUnitDTO);
        if (endNodeUnitDTO.getId() == null) {
            return createEndNodeUnit(endNodeUnitDTO);
        }
        EndNodeUnitDTO result = endNodeUnitService.save(endNodeUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, endNodeUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /end-node-units : get all the endNodeUnits.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of endNodeUnits in body
     */
    @GetMapping("/end-node-units")
    @Timed
    public ResponseEntity<List<EndNodeUnitDTO>> getAllEndNodeUnits(@ApiParam Pageable pageable, @RequestParam(required = false) String filter) {
        if ("condition-is-null".equals(filter)) {
            log.debug("REST request to get all EndNodeUnits where condition is null");
            return new ResponseEntity<>(endNodeUnitService.findAllWhereConditionIsNull(),
                    HttpStatus.OK);
        }
        if ("action-is-null".equals(filter)) {
            log.debug("REST request to get all EndNodeUnits where action is null");
            return new ResponseEntity<>(endNodeUnitService.findAllWhereActionIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of EndNodeUnits");
        Page<EndNodeUnitDTO> page = endNodeUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/end-node-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /end-node-units/:id : get the "id" endNodeUnit.
     *
     * @param id the id of the endNodeUnitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the endNodeUnitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/end-node-units/{id}")
    @Timed
    public ResponseEntity<EndNodeUnitDTO> getEndNodeUnit(@PathVariable Long id) {
        log.debug("REST request to get EndNodeUnit : {}", id);
        EndNodeUnitDTO endNodeUnitDTO = endNodeUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(endNodeUnitDTO));
    }

    /**
     * DELETE  /end-node-units/:id : delete the "id" endNodeUnit.
     *
     * @param id the id of the endNodeUnitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/end-node-units/{id}")
    @Timed
    public ResponseEntity<Void> deleteEndNodeUnit(@PathVariable Long id) {
        log.debug("REST request to delete EndNodeUnit : {}", id);
        endNodeUnitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
