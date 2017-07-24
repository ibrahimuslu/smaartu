package com.eeu.smaartu.service;

import com.eeu.smaartu.service.dto.ModeDTO;
import java.util.List;

/**
 * Service Interface for managing Mode.
 */
public interface ModeService {

    /**
     * Save a mode.
     *
     * @param modeDTO the entity to save
     * @return the persisted entity
     */
    ModeDTO save(ModeDTO modeDTO);

    /**
     *  Get all the modes.
     *
     *  @return the list of entities
     */
    List<ModeDTO> findAll();

    /**
     *  Get the "id" mode.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ModeDTO findOne(Long id);

    /**
     *  Delete the "id" mode.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
