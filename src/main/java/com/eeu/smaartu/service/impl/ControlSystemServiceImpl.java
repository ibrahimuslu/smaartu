package com.eeu.smaartu.service.impl;

import com.eeu.smaartu.service.ControlSystemService;
import com.eeu.smaartu.domain.ControlSystem;
import com.eeu.smaartu.repository.ControlSystemRepository;
import com.eeu.smaartu.service.dto.ControlSystemDTO;
import com.eeu.smaartu.service.mapper.ControlSystemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ControlSystem.
 */
@Service
@Transactional
public class ControlSystemServiceImpl implements ControlSystemService{

    private final Logger log = LoggerFactory.getLogger(ControlSystemServiceImpl.class);

    private final ControlSystemRepository controlSystemRepository;

    private final ControlSystemMapper controlSystemMapper;

    public ControlSystemServiceImpl(ControlSystemRepository controlSystemRepository, ControlSystemMapper controlSystemMapper) {
        this.controlSystemRepository = controlSystemRepository;
        this.controlSystemMapper = controlSystemMapper;
    }

    /**
     * Save a controlSystem.
     *
     * @param controlSystemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ControlSystemDTO save(ControlSystemDTO controlSystemDTO) {
        log.debug("Request to save ControlSystem : {}", controlSystemDTO);
        ControlSystem controlSystem = controlSystemMapper.toEntity(controlSystemDTO);
        controlSystem = controlSystemRepository.save(controlSystem);
        return controlSystemMapper.toDto(controlSystem);
    }

    /**
     *  Get all the controlSystems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ControlSystemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ControlSystems");
        return controlSystemRepository.findAll(pageable)
            .map(controlSystemMapper::toDto);
    }

    /**
     *  Get one controlSystem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ControlSystemDTO findOne(Long id) {
        log.debug("Request to get ControlSystem : {}", id);
        ControlSystem controlSystem = controlSystemRepository.findOne(id);
        return controlSystemMapper.toDto(controlSystem);
    }

    /**
     *  Delete the  controlSystem by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ControlSystem : {}", id);
        controlSystemRepository.delete(id);
    }
}
