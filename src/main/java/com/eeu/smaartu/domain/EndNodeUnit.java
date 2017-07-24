package com.eeu.smaartu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.eeu.smaartu.domain.enumeration.UnitType;

import com.eeu.smaartu.domain.enumeration.UnitStatus;

/**
 * A EndNodeUnit.
 */
@Entity
@Table(name = "end_node_unit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EndNodeUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private UnitType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UnitStatus status;

    @ManyToOne
    private EndNode endNode;

    @OneToOne(mappedBy = "conditionUnit")
    @JsonIgnore
    private Action condition;

    @OneToOne(mappedBy = "actionUnit")
    @JsonIgnore
    private Action action;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EndNodeUnit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitType getType() {
        return type;
    }

    public EndNodeUnit type(UnitType type) {
        this.type = type;
        return this;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public EndNodeUnit status(UnitStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public EndNode getEndNode() {
        return endNode;
    }

    public EndNodeUnit endNode(EndNode endNode) {
        this.endNode = endNode;
        return this;
    }

    public void setEndNode(EndNode endNode) {
        this.endNode = endNode;
    }

    public Action getCondition() {
        return condition;
    }

    public EndNodeUnit condition(Action action) {
        this.condition = action;
        return this;
    }

    public void setCondition(Action action) {
        this.condition = action;
    }

    public Action getAction() {
        return action;
    }

    public EndNodeUnit action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EndNodeUnit endNodeUnit = (EndNodeUnit) o;
        if (endNodeUnit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endNodeUnit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EndNodeUnit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
