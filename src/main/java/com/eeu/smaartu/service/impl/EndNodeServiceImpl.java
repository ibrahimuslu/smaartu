package com.eeu.smaartu.service.impl;

import com.eeu.smaartu.service.EndNodeService;
import com.eeu.smaartu.domain.EndNode;
import com.eeu.smaartu.repository.EndNodeRepository;
import com.eeu.smaartu.service.dto.EndNodeDTO;
import com.eeu.smaartu.service.mapper.EndNodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EndNode.
 */
@Service
@Transactional
public class EndNodeServiceImpl implements EndNodeService{

    private final Logger log = LoggerFactory.getLogger(EndNodeServiceImpl.class);

    private final EndNodeRepository endNodeRepository;

    private final EndNodeMapper endNodeMapper;

    public EndNodeServiceImpl(EndNodeRepository endNodeRepository, EndNodeMapper endNodeMapper) {
        this.endNodeRepository = endNodeRepository;
        this.endNodeMapper = endNodeMapper;
    }

    /**
     * Save a endNode.
     *
     * @param endNodeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EndNodeDTO save(EndNodeDTO endNodeDTO) {
        log.debug("Request to save EndNode : {}", endNodeDTO);
        EndNode endNode = endNodeMapper.toEntity(endNodeDTO);
        endNode = endNodeRepository.save(endNode);
        return endNodeMapper.toDto(endNode);
    }

    /**
     *  Get all the endNodes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EndNodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EndNodes");
        return endNodeRepository.findAll(pageable)
            .map(endNodeMapper::toDto);
    }

    /**
     *  Get one endNode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EndNodeDTO findOne(Long id) {
        log.debug("Request to get EndNode : {}", id);
        EndNode endNode = endNodeRepository.findOne(id);
        return endNodeMapper.toDto(endNode);
    }

    /**
     *  Delete the  endNode by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EndNode : {}", id);
        endNodeRepository.delete(id);
    }
}
