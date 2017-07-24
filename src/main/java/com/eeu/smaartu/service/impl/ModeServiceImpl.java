package com.eeu.smaartu.service.impl;

import com.eeu.smaartu.service.ModeService;
import com.eeu.smaartu.domain.Mode;
import com.eeu.smaartu.repository.ModeRepository;
import com.eeu.smaartu.service.dto.ModeDTO;
import com.eeu.smaartu.service.mapper.ModeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Mode.
 */
@Service
@Transactional
public class ModeServiceImpl implements ModeService{

    private final Logger log = LoggerFactory.getLogger(ModeServiceImpl.class);

    private final ModeRepository modeRepository;

    private final ModeMapper modeMapper;

    public ModeServiceImpl(ModeRepository modeRepository, ModeMapper modeMapper) {
        this.modeRepository = modeRepository;
        this.modeMapper = modeMapper;
    }

    /**
     * Save a mode.
     *
     * @param modeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ModeDTO save(ModeDTO modeDTO) {
        log.debug("Request to save Mode : {}", modeDTO);
        Mode mode = modeMapper.toEntity(modeDTO);
        mode = modeRepository.save(mode);
        return modeMapper.toDto(mode);
    }

    /**
     *  Get all the modes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ModeDTO> findAll() {
        log.debug("Request to get all Modes");
        return modeRepository.findAll().stream()
            .map(modeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one mode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ModeDTO findOne(Long id) {
        log.debug("Request to get Mode : {}", id);
        Mode mode = modeRepository.findOne(id);
        return modeMapper.toDto(mode);
    }

    /**
     *  Delete the  mode by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mode : {}", id);
        modeRepository.delete(id);
    }
}
