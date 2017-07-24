package com.eeu.smaartu.service.mapper;

import com.eeu.smaartu.domain.*;
import com.eeu.smaartu.service.dto.EndNodeUnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EndNodeUnit and its DTO EndNodeUnitDTO.
 */
@Mapper(componentModel = "spring", uses = {EndNodeMapper.class, })
public interface EndNodeUnitMapper extends EntityMapper <EndNodeUnitDTO, EndNodeUnit> {

    @Mapping(source = "endNode.id", target = "endNodeId")
    EndNodeUnitDTO toDto(EndNodeUnit endNodeUnit); 

    @Mapping(source = "endNodeId", target = "endNode")
    @Mapping(target = "condition", ignore = true)
    @Mapping(target = "action", ignore = true)
    EndNodeUnit toEntity(EndNodeUnitDTO endNodeUnitDTO); 
    default EndNodeUnit fromId(Long id) {
        if (id == null) {
            return null;
        }
        EndNodeUnit endNodeUnit = new EndNodeUnit();
        endNodeUnit.setId(id);
        return endNodeUnit;
    }
}
