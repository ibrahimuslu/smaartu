package com.eeu.smaartu.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.eeu.smaartu.domain.enumeration.UnitStatus;
import com.eeu.smaartu.domain.enumeration.UnitStatus;

/**
 * A DTO for the Action entity.
 */
public class ActionDTO implements Serializable {

    private Long id;

    private UnitStatus condition;

    private UnitStatus actionStatus;

    private Long conditionUnitId;

    private Long actionUnitId;

    private Long modeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnitStatus getCondition() {
        return condition;
    }

    public void setCondition(UnitStatus condition) {
        this.condition = condition;
    }

    public UnitStatus getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(UnitStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Long getConditionUnitId() {
        return conditionUnitId;
    }

    public void setConditionUnitId(Long endNodeUnitId) {
        this.conditionUnitId = endNodeUnitId;
    }

    public Long getActionUnitId() {
        return actionUnitId;
    }

    public void setActionUnitId(Long endNodeUnitId) {
        this.actionUnitId = endNodeUnitId;
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionDTO actionDTO = (ActionDTO) o;
        if(actionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActionDTO{" +
            "id=" + getId() +
            ", condition='" + getCondition() + "'" +
            ", actionStatus='" + getActionStatus() + "'" +
            "}";
    }
}
