package com.eeu.smaartu.service.mapper;

import com.eeu.smaartu.domain.*;
import com.eeu.smaartu.service.dto.ModeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mode and its DTO ModeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModeMapper extends EntityMapper <ModeDTO, Mode> {
    
    @Mapping(target = "actions", ignore = true)
    Mode toEntity(ModeDTO modeDTO); 
    default Mode fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mode mode = new Mode();
        mode.setId(id);
        return mode;
    }
}
