package com.eeu.smaartu.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.eeu.smaartu.domain.enumeration.DataBits;
import com.eeu.smaartu.domain.enumeration.Parity;
import com.eeu.smaartu.domain.enumeration.StopBits;
import com.eeu.smaartu.domain.enumeration.FlowControl;

/**
 * A DTO for the SerialConnection entity.
 */
public class SerialConnectionDTO implements Serializable {

    private Long id;

    private String port;

    private Long baudRate;

    private DataBits dataBits;

    private Parity parity;

    private StopBits stopBits;

    private FlowControl flowControl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Long getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(Long baudRate) {
        this.baudRate = baudRate;
    }

    public DataBits getDataBits() {
        return dataBits;
    }

    public void setDataBits(DataBits dataBits) {
        this.dataBits = dataBits;
    }

    public Parity getParity() {
        return parity;
    }

    public void setParity(Parity parity) {
        this.parity = parity;
    }

    public StopBits getStopBits() {
        return stopBits;
    }

    public void setStopBits(StopBits stopBits) {
        this.stopBits = stopBits;
    }

    public FlowControl getFlowControl() {
        return flowControl;
    }

    public void setFlowControl(FlowControl flowControl) {
        this.flowControl = flowControl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SerialConnectionDTO serialConnectionDTO = (SerialConnectionDTO) o;
        if(serialConnectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serialConnectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SerialConnectionDTO{" +
            "id=" + getId() +
            ", port='" + getPort() + "'" +
            ", baudRate='" + getBaudRate() + "'" +
            ", dataBits='" + getDataBits() + "'" +
            ", parity='" + getParity() + "'" +
            ", stopBits='" + getStopBits() + "'" +
            ", flowControl='" + getFlowControl() + "'" +
            "}";
    }
}
