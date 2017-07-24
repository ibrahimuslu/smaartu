package com.eeu.smaartu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eeu.smaartu.service.EndNodeService;
import com.eeu.smaartu.web.rest.util.HeaderUtil;
import com.eeu.smaartu.web.rest.util.PaginationUtil;
import com.eeu.smaartu.service.dto.EndNodeDTO;
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
 * REST controller for managing EndNode.
 */
@RestController
@RequestMapping("/api")
public class EndNodeResource {

    private final Logger log = LoggerFactory.getLogger(EndNodeResource.class);

    private static final String ENTITY_NAME = "endNode";

    private final EndNodeService endNodeService;

    public EndNodeResource(EndNodeService endNodeService) {
        this.endNodeService = endNodeService;
    }

    /**
     * POST  /end-nodes : Create a new endNode.
     *
     * @param endNodeDTO the endNodeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new endNodeDTO, or with status 400 (Bad Request) if the endNode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/end-nodes")
    @Timed
    public ResponseEntity<EndNodeDTO> createEndNode(@RequestBody EndNodeDTO endNodeDTO) throws URISyntaxException {
        log.debug("REST request to save EndNode : {}", endNodeDTO);
        if (endNodeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new endNode cannot already have an ID")).body(null);
        }
        EndNodeDTO result = endNodeService.save(endNodeDTO);
        return ResponseEntity.created(new URI("/api/end-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /end-nodes : Updates an existing endNode.
     *
     * @param endNodeDTO the endNodeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated endNodeDTO,
     * or with status 400 (Bad Request) if the endNodeDTO is not valid,
     * or with status 500 (Internal Server Error) if the endNodeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/end-nodes")
    @Timed
    public ResponseEntity<EndNodeDTO> updateEndNode(@RequestBody EndNodeDTO endNodeDTO) throws URISyntaxException {
        log.debug("REST request to update EndNode : {}", endNodeDTO);
        if (endNodeDTO.getId() == null) {
            return createEndNode(endNodeDTO);
        }
        EndNodeDTO result = endNodeService.save(endNodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, endNodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /end-nodes : get all the endNodes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of endNodes in body
     */
    @GetMapping("/end-nodes")
    @Timed
    public ResponseEntity<List<EndNodeDTO>> getAllEndNodes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EndNodes");
        Page<EndNodeDTO> page = endNodeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/end-nodes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /end-nodes/:id : get the "id" endNode.
     *
     * @param id the id of the endNodeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the endNodeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/end-nodes/{id}")
    @Timed
    public ResponseEntity<EndNodeDTO> getEndNode(@PathVariable Long id) {
        log.debug("REST request to get EndNode : {}", id);
        EndNodeDTO endNodeDTO = endNodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(endNodeDTO));
    }

    /**
     * DELETE  /end-nodes/:id : delete the "id" endNode.
     *
     * @param id the id of the endNodeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/end-nodes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEndNode(@PathVariable Long id) {
        log.debug("REST request to delete EndNode : {}", id);
        endNodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
