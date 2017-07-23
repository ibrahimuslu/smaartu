package com.eeu.smaartu.service.mapper;

import com.eeu.smaartu.domain.*;
import com.eeu.smaartu.service.dto.ActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Action and its DTO ActionDTO.
 */
@Mapper(componentModel = "spring", uses = {EndNodeUnitMapper.class, ModeMapper.class, })
public interface ActionMapper extends EntityMapper <ActionDTO, Action> {

    @Mapping(source = "conditionUnit.id", target = "conditionUnitId")

    @Mapping(source = "actionUnit.id", target = "actionUnitId")

    @Mapping(source = "mode.id", target = "modeId")
    ActionDTO toDto(Action action); 

    @Mapping(source = "conditionUnitId", target = "conditionUnit")

    @Mapping(source = "actionUnitId", target = "actionUnit")

    @Mapping(source = "modeId", target = "mode")
    Action toEntity(ActionDTO actionDTO); 
    default Action fromId(Long id) {
        if (id == null) {
            return null;
        }
        Action action = new Action();
        action.setId(id);
        return action;
    }
}
