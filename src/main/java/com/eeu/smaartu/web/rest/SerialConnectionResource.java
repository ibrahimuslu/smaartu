package com.eeu.smaartu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eeu.smaartu.service.SerialConnectionService;
import com.eeu.smaartu.web.rest.util.HeaderUtil;
import com.eeu.smaartu.service.dto.SerialConnectionDTO;
import gnu.io.CommPortIdentifier;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing SerialConnection.
 */
@RestController
@RequestMapping("/api")
public class SerialConnectionResource {

    private final Logger log = LoggerFactory.getLogger(SerialConnectionResource.class);

    private static final String ENTITY_NAME = "serialConnection";

    private final SerialConnectionService serialConnectionService;

    public SerialConnectionResource(SerialConnectionService serialConnectionService) {
        this.serialConnectionService = serialConnectionService;
    }

    /**
     * POST  /serial-connections : Create a new serialConnection.
     *
     * @param serialConnectionDTO the serialConnectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serialConnectionDTO, or with status 400 (Bad Request) if the serialConnection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/serial-connections")
    @Timed
    public ResponseEntity<SerialConnectionDTO> createSerialConnection(@RequestBody SerialConnectionDTO serialConnectionDTO) throws URISyntaxException {
        log.debug("REST request to save SerialConnection : {}", serialConnectionDTO);
        if (serialConnectionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serialConnection cannot already have an ID")).body(null);
        }
        SerialConnectionDTO result = serialConnectionService.save(serialConnectionDTO);
        return ResponseEntity.created(new URI("/api/serial-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /serial-connections : Updates an existing serialConnection.
     *
     * @param serialConnectionDTO the serialConnectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serialConnectionDTO,
     * or with status 400 (Bad Request) if the serialConnectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the serialConnectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/serial-connections")
    @Timed
    public ResponseEntity<SerialConnectionDTO> updateSerialConnection(@RequestBody SerialConnectionDTO serialConnectionDTO) throws URISyntaxException {
        log.debug("REST request to update SerialConnection : {}", serialConnectionDTO);
        if (serialConnectionDTO.getId() == null) {
            return createSerialConnection(serialConnectionDTO);
        }
        SerialConnectionDTO result = serialConnectionService.save(serialConnectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serialConnectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /serial-connections : get all the serialConnections.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of serialConnections in body
     */
    @GetMapping("/serial-connections")
    @Timed
    public List<SerialConnectionDTO> getAllSerialConnections(@RequestParam(required = false) String filter) {
        if ("endnode-is-null".equals(filter)) {
            log.debug("REST request to get all SerialConnections where endNode is null");
            return serialConnectionService.findAllWhereEndNodeIsNull();
        }
        log.debug("REST request to get all SerialConnections");
        return serialConnectionService.findAll();
    }

    /**
     * GET  /serial-connections/:id : get the "id" serialConnection.
     *
     * @param id the id of the serialConnectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serialConnectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/serial-connections/{id}")
    @Timed
    public ResponseEntity<SerialConnectionDTO> getSerialConnection(@PathVariable Long id) {
        log.debug("REST request to get SerialConnection : {}", id);
        SerialConnectionDTO serialConnectionDTO = serialConnectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serialConnectionDTO));
    }

    /**
     * GET  /serial-connections/ports : get the ports of  serialConnection.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the serialConnectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/serial-connections/ports")
    @Timed
    public ResponseEntity<List<String>> getSerialConnectionPorts() {
        log.debug("REST request to get SerialConnection : {}");
        ArrayList<String> portList = new ArrayList<>();
        CommPortIdentifier cpi = null;
        Enumeration e = CommPortIdentifier.getPortIdentifiers();

        while (e.hasMoreElements()) {
            try {
                cpi = (CommPortIdentifier) e.nextElement();
            } catch (NoSuchElementException n) {

            }

            portList.add(cpi.getName());
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(portList));
    }

    /**
     * DELETE  /serial-connections/:id : delete the "id" serialConnection.
     *
     * @param id the id of the serialConnectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/serial-connections/{id}")
    @Timed
    public ResponseEntity<Void> deleteSerialConnection(@PathVariable Long id) {
        log.debug("REST request to delete SerialConnection : {}", id);
        serialConnectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
