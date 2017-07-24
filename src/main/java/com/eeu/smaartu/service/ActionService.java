package com.eeu.smaartu.service;

import com.eeu.smaartu.service.dto.ActionDTO;
import java.util.List;

/**
 * Service Interface for managing Action.
 */
public interface ActionService {

    /**
     * Save a action.
     *
     * @param actionDTO the entity to save
     * @return the persisted entity
     */
    ActionDTO save(ActionDTO actionDTO);

    /**
     *  Get all the actions.
     *
     *  @return the list of entities
     */
    List<ActionDTO> findAll();

    /**
     *  Get the "id" action.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ActionDTO findOne(Long id);

    /**
     *  Delete the "id" action.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
