package com.eeu.smaartu.service;

import com.eeu.smaartu.service.dto.EndNodeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EndNode.
 */
public interface EndNodeService {

    /**
     * Save a endNode.
     *
     * @param endNodeDTO the entity to save
     * @return the persisted entity
     */
    EndNodeDTO save(EndNodeDTO endNodeDTO);

    /**
     *  Get all the endNodes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EndNodeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" endNode.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EndNodeDTO findOne(Long id);

    /**
     *  Delete the "id" endNode.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
