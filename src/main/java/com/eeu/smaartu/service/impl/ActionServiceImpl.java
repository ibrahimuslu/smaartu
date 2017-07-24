package com.eeu.smaartu.service.impl;

import com.eeu.smaartu.service.ActionService;
import com.eeu.smaartu.domain.Action;
import com.eeu.smaartu.repository.ActionRepository;
import com.eeu.smaartu.service.dto.ActionDTO;
import com.eeu.smaartu.service.mapper.ActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Action.
 */
@Service
@Transactional
public class ActionServiceImpl implements ActionService{

    private final Logger log = LoggerFactory.getLogger(ActionServiceImpl.class);

    private final ActionRepository actionRepository;

    private final ActionMapper actionMapper;

    public ActionServiceImpl(ActionRepository actionRepository, ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    /**
     * Save a action.
     *
     * @param actionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActionDTO save(ActionDTO actionDTO) {
        log.debug("Request to save Action : {}", actionDTO);
        Action action = actionMapper.toEntity(actionDTO);
        action = actionRepository.save(action);
        return actionMapper.toDto(action);
    }

    /**
     *  Get all the actions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActionDTO> findAll() {
        log.debug("Request to get all Actions");
        return actionRepository.findAll().stream()
            .map(actionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one action by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ActionDTO findOne(Long id) {
        log.debug("Request to get Action : {}", id);
        Action action = actionRepository.findOne(id);
        return actionMapper.toDto(action);
    }

    /**
     *  Delete the  action by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Action : {}", id);
        actionRepository.delete(id);
    }
}
