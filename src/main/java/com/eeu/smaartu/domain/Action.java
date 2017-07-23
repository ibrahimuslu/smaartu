package com.eeu.smaartu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.eeu.smaartu.domain.enumeration.UnitStatus;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_condition")
    private UnitStatus condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_status")
    private UnitStatus actionStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private EndNodeUnit conditionUnit;

    @OneToOne
    @JoinColumn(unique = true)
    private EndNodeUnit actionUnit;

    @ManyToOne
    private Mode mode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnitStatus getCondition() {
        return condition;
    }

    public Action condition(UnitStatus condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(UnitStatus condition) {
        this.condition = condition;
    }

    public UnitStatus getActionStatus() {
        return actionStatus;
    }

    public Action actionStatus(UnitStatus actionStatus) {
        this.actionStatus = actionStatus;
        return this;
    }

    public void setActionStatus(UnitStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public EndNodeUnit getConditionUnit() {
        return conditionUnit;
    }

    public Action conditionUnit(EndNodeUnit endNodeUnit) {
        this.conditionUnit = endNodeUnit;
        return this;
    }

    public void setConditionUnit(EndNodeUnit endNodeUnit) {
        this.conditionUnit = endNodeUnit;
    }

    public EndNodeUnit getActionUnit() {
        return actionUnit;
    }

    public Action actionUnit(EndNodeUnit endNodeUnit) {
        this.actionUnit = endNodeUnit;
        return this;
    }

    public void setActionUnit(EndNodeUnit endNodeUnit) {
        this.actionUnit = endNodeUnit;
    }

    public Mode getMode() {
        return mode;
    }

    public Action mode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Action action = (Action) o;
        if (action.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", condition='" + getCondition() + "'" +
            ", actionStatus='" + getActionStatus() + "'" +
            "}";
    }
}
