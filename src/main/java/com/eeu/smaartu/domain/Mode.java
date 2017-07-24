package com.eeu.smaartu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eeu.smaartu.domain.enumeration.UnitStatus;

/**
 * A Mode.
 */
@Entity
@Table(name = "mode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UnitStatus status;

    @OneToMany(mappedBy = "mode")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Action> actions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Mode name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public Mode status(UnitStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public Mode actions(Set<Action> actions) {
        this.actions = actions;
        return this;
    }

    public Mode addAction(Action action) {
        this.actions.add(action);
        action.setMode(this);
        return this;
    }

    public Mode removeAction(Action action) {
        this.actions.remove(action);
        action.setMode(null);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mode mode = (Mode) o;
        if (mode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mode{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
