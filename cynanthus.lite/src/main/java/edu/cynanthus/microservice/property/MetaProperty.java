package edu.cynanthus.microservice.property;

import edu.cynanthus.common.observable.Observable;

import java.util.Objects;

/**
 * El tipo Meta property.
 */
public final class MetaProperty extends CentralizedStringProperty {

    /**
     * El Meta data.
     */
    private String metaData;

    /**
     * Instancia un nuevo Meta property.
     *
     * @param metaData el meta data
     * @param value    el value
     */
    MetaProperty(String metaData, String value) {
        super(value);
        setMetaData(metaData);
    }

    /**
     * Permite obtener meta data.
     *
     * @return el meta data
     */
    public String getMetaData() {
        return metaData;
    }

    /**
     * Permite establecer meta data.
     *
     * @param metaData el meta data
     */
    public void setMetaData(String metaData) {
        this.metaData = Objects.requireNonNull(metaData);
    }

    /**
     * Equals boolean.
     *
     * @param o el o
     * @return el boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MetaProperty that = (MetaProperty) o;
        return Objects.equals(metaData, that.metaData);
    }

    /**
     * Hash code int.
     *
     * @return el int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), metaData);
    }

    /**
     * Clone meta property.
     *
     * @return el meta property
     */
    @Override
    public MetaProperty clone() {
        return Observable.shareObservers(this, new MetaProperty(metaData, value));
    }

    /**
     * As meta property meta property.
     *
     * @param metaData el meta data
     * @param value    el value
     * @return el meta property
     */
    public static MetaProperty asMetaProperty(String metaData, String value) {
        return new MetaProperty(metaData, value);
    }

}
