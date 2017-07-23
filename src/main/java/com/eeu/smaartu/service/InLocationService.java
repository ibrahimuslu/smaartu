package com.eeu.smaartu.service;

import com.eeu.smaartu.service.dto.InLocationDTO;
import java.util.List;

/**
 * Service Interface for managing InLocation.
 */
public interface InLocationService {

    /**
     * Save a inLocation.
     *
     * @param inLocationDTO the entity to save
     * @return the persisted entity
     */
    InLocationDTO save(InLocationDTO inLocationDTO);

    /**
     *  Get all the inLocations.
     *
     *  @return the list of entities
     */
    List<InLocationDTO> findAll();

    /**
     *  Get the "id" inLocation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InLocationDTO findOne(Long id);

    /**
     *  Delete the "id" inLocation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
