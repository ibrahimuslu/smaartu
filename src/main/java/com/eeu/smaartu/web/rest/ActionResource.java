package com.eeu.smaartu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eeu.smaartu.service.ActionService;
import com.eeu.smaartu.web.rest.util.HeaderUtil;
import com.eeu.smaartu.service.dto.ActionDTO;
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
 * REST controller for managing Action.
 */
@RestController
@RequestMapping("/api")
public class ActionResource {

    private final Logger log = LoggerFactory.getLogger(ActionResource.class);

    private static final String ENTITY_NAME = "action";

    private final ActionService actionService;

    public ActionResource(ActionService actionService) {
        this.actionService = actionService;
    }

    /**
     * POST  /actions : Create a new action.
     *
     * @param actionDTO the actionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actionDTO, or with status 400 (Bad Request) if the action has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actions")
    @Timed
    public ResponseEntity<ActionDTO> createAction(@RequestBody ActionDTO actionDTO) throws URISyntaxException {
        log.debug("REST request to save Action : {}", actionDTO);
        if (actionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new action cannot already have an ID")).body(null);
        }
        ActionDTO result = actionService.save(actionDTO);
        return ResponseEntity.created(new URI("/api/actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actions : Updates an existing action.
     *
     * @param actionDTO the actionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actionDTO,
     * or with status 400 (Bad Request) if the actionDTO is not valid,
     * or with status 500 (Internal Server Error) if the actionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actions")
    @Timed
    public ResponseEntity<ActionDTO> updateAction(@RequestBody ActionDTO actionDTO) throws URISyntaxException {
        log.debug("REST request to update Action : {}", actionDTO);
        if (actionDTO.getId() == null) {
            return createAction(actionDTO);
        }
        ActionDTO result = actionService.save(actionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actions : get all the actions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actions in body
     */
    @GetMapping("/actions")
    @Timed
    public List<ActionDTO> getAllActions() {
        log.debug("REST request to get all Actions");
        return actionService.findAll();
    }

    /**
     * GET  /actions/:id : get the "id" action.
     *
     * @param id the id of the actionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/actions/{id}")
    @Timed
    public ResponseEntity<ActionDTO> getAction(@PathVariable Long id) {
        log.debug("REST request to get Action : {}", id);
        ActionDTO actionDTO = actionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(actionDTO));
    }

    /**
     * DELETE  /actions/:id : delete the "id" action.
     *
     * @param id the id of the actionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAction(@PathVariable Long id) {
        log.debug("REST request to delete Action : {}", id);
        actionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
