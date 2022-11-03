package edu.cynanthus.domain;

import edu.cynanthus.bean.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class LiteSample implements Bean {

    @NotNull(message = "#{NotNull.liteSample.mac}")
    @Pattern(regexp = Patterns.MAC, groups = {Required.class, ValidInfo.class}, message = "#{Pattern.liteSample.mac}")
    @JProperty
    private String mac;

    @NotNull(message = "#{NotNull.liteSample.rssi}")
    @Negative(groups = {Required.class, ValidInfo.class}, message = "#{Negative.liteSample.rssi}")
    @JProperty
    private Float rssi;

    @NotNull(groups = Required.class, message = "#{NotNull.liteSample.temp}")
    @Min(value = 0, groups = {Required.class, ValidInfo.class}, message = "#{Min.liteSample.temp}")
    @JProperty
    private Float temp;

    public LiteSample(String mac, Float rssi, Float temp) {
        this.mac = mac;
        this.rssi = rssi;
        this.temp = temp;
    }

    public LiteSample() {
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Float getRssi() {
        return rssi;
    }

    public void setRssi(Float rssi) {
        this.rssi = rssi;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    @Override
    public LiteSample clone() {
        return new LiteSample(mac, rssi, temp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteSample that = (LiteSample) o;
        return Objects.equals(mac, that.mac) && Objects.equals(rssi, that.rssi) && Objects.equals(temp, that.temp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mac, rssi, temp);
    }

    @Override
    public String toString() {
        return "{" +
            "mac:'" + mac + '\'' +
            ",rssi:" + rssi +
            ",temp:" + temp +
            '}';
    }

}
