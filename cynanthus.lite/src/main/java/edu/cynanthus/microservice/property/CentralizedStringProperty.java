package edu.cynanthus.microservice.property;

import edu.cynanthus.common.observable.Observable;

import java.util.function.Function;

/**
 * El tipo Centralized string property.
 */
public class CentralizedStringProperty extends CentralizedProperty<String>
    implements Comparable<CentralizedStringProperty> {

    /**
     * Instancia un nuevo Centralized string property.
     *
     * @param value el value
     */
    CentralizedStringProperty(String value) {
        super(value);
    }

    /**
     * As other property property.
     *
     * @param <T>                 el parámetro de tipo
     * @param toOtherTypeFunction el to other type function
     * @return el property
     */
    public final <T> Property<T> asOtherProperty(Function<String, T> toOtherTypeFunction) {
        return asOtherProperty(toOtherTypeFunction, String::valueOf);
    }

    /**
     * As other observable property observable property.
     *
     * @param <T>                 el parámetro de tipo
     * @param toOtherTypeFunction el to other type function
     * @return el observable property
     */
    public final <T> ObservableProperty<T> asOtherObservableProperty(Function<String, T> toOtherTypeFunction) {
        return asOtherObservableProperty(toOtherTypeFunction, String::valueOf);
    }

    /**
     * As read only byte property read only property.
     *
     * @return el read only property
     */
    public final ReadOnlyProperty<Byte> asReadOnlyByteProperty() {
        return asOtherReadOnlyProperty(Byte::parseByte);
    }

    /**
     * As byte property property.
     *
     * @return el property
     */
    public final Property<Byte> asByteProperty() {
        return asOtherProperty(Byte::parseByte);
    }

    /**
     * As observable byte property observable property.
     *
     * @return el observable property
     */
    public final ObservableProperty<Byte> asObservableByteProperty() {
        return asOtherObservableProperty(Byte::parseByte);
    }

    /**
     * As read only short property read only property.
     *
     * @return el read only property
     */
    public final ReadOnlyProperty<Short> asReadOnlyShortProperty() {
        return asOtherReadOnlyProperty(Short::parseShort);
    }

    /**
     * As short property property.
     *
     * @return el property
     */
    public final Property<Short> asShortProperty() {
        return asOtherProperty(Short::parseShort);
    }

    /**
     * As observable short property observable property.
     *
     * @return el observable property
     */
    public final ObservableProperty<Short> asObservableShortProperty() {
        return asOtherObservableProperty(Short::parseShort);
    }

    /**
     * As read only integer property read only property.
     *
     * @return el read only property
     */
    public final ReadOnlyProperty<Integer> asReadOnlyIntegerProperty() {
        return asOtherReadOnlyProperty(Integer::parseInt);
    }

    /**
     * As integer property property.
     *
     * @return el property
     */
    public final Property<Integer> asIntegerProperty() {
        return asOtherProperty(Integer::parseInt);
    }

    /**
     * As observable integer property observable property.
     *
     * @return el observable property
     */
    public final ObservableProperty<Integer> asObservableIntegerProperty() {
        return asOtherObservableProperty(Integer::parseInt);
    }

    /**
     * As read only long property read only property.
     *
     * @return el read only property
     */
    public final ReadOnlyProperty<Long> asReadOnlyLongProperty() {
        return asOtherReadOnlyProperty(Long::parseLong);
    }

    /**
     * As long property property.
     *
     * @return el property
     */
    public final Property<Long> asLongProperty() {
        return asOtherProperty(Long::parseLong);
    }

    /**
     * As observable long property observable property.
     *
     * @return el observable property
     */
    public final ObservableProperty<Long> asObservableLongProperty() {
        return asOtherObservableProperty(Long::parseLong);
    }

    /**
     * As read only float property read only property.
     *
     * @return el read only property
     */
    public final ReadOnlyProperty<Float> asReadOnlyFloatProperty() {
        return asOtherReadOnlyProperty(Float::parseFloat);
    }

    /**
     * As float property property.
     *
     * @return el property
     */
    public final Property<Float> asFloatProperty() {
        return asOtherProperty(Float::parseFloat);
    }

    /**
     * As observable float property observable property.
     *
     * @return el observable property
     */
    public final ObservableProperty<Float> asObservableFloatProperty() {
        return asOtherObservableProperty(Float::parseFloat);
    }

    /**
     * As read only double property read only property.
     *
     * @return el read only property
     */
    public final ReadOnlyProperty<Double> asReadOnlyDoubleProperty() {
        return asOtherReadOnlyProperty(Double::parseDouble);
    }

    /**
     * As double property property.
     *
     * @return el property
     */
    public final Property<Double> asDoubleProperty() {
        return asOtherProperty(Double::parseDouble);
    }

    /**
     * As observable double property observable property.
     *
     * @return el observable property
     */
    public final ObservableProperty<Double> asObservableDoubleProperty() {
        return asOtherObservableProperty(Double::parseDouble);
    }

    /**
     * As read only character property read only property.
     *
     * @return el read only property
     */
    public final ReadOnlyProperty<Character> asReadOnlyCharacterProperty() {
        return asOtherReadOnlyProperty(string -> string.charAt(0));
    }

    /**
     * As character property property.
     *
     * @return el property
     */
    public final Property<Character> asCharacterProperty() {
        return asOtherProperty(string -> string.charAt(0));
    }

    /**
     * As observable character property observable property.
     *
     * @return el observable property
     */
    public final ObservableProperty<Character> asObservableCharacterProperty() {
        return asOtherObservableProperty(string -> string.charAt(0));
    }

    /**
     * As read only boolean property read only property.
     *
     * @return el read only property
     */
    public final ReadOnlyProperty<Boolean> asReadOnlyBooleanProperty() {
        return asOtherReadOnlyProperty(Boolean::parseBoolean);
    }

    /**
     * As boolean property property.
     *
     * @return el property
     */
    public final Property<Boolean> asBooleanProperty() {
        return asOtherProperty(Boolean::parseBoolean);
    }

    /**
     * As observable boolean property observable property.
     *
     * @return el observable property
     */
    public final ObservableProperty<Boolean> asObservableBooleanProperty() {
        return asOtherObservableProperty(Boolean::parseBoolean);
    }

    /**
     * Compare to int.
     *
     * @param o el o
     * @return el int
     */
    @Override
    public final int compareTo(CentralizedStringProperty o) {
        return value.compareTo(o.value);
    }

    /**
     * Clone centralized string property.
     *
     * @return el centralized string property
     */
    @Override
    public CentralizedStringProperty clone() {
        return Observable.shareObservers(this, new CentralizedStringProperty(value));
    }

    /**
     * As centralized string property centralized string property.
     *
     * @param value el value
     * @return el centralized string property
     */
    public static CentralizedStringProperty asCentralizedStringProperty(String value) {
        return new CentralizedStringProperty(value);
    }

}
