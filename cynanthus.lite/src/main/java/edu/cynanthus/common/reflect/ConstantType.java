package edu.cynanthus.common.reflect;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * La enumeración Basic type.
 */
public enum ConstantType {

    /**
     * El Byte.
     */
    BYTE(Byte::parseByte) {
        @Override
        public boolean is(Type type) {
            return byte.class.equals(type) || Byte.class.equals(type);
        }
    },
    /**
     * El Short.
     */
    SHORT(Short::parseShort) {
        @Override
        public boolean is(Type type) {
            return short.class.equals(type) || Short.class.equals(type);
        }
    },
    /**
     * El Integer.
     */
    INTEGER(Integer::parseInt) {
        @Override
        public boolean is(Type type) {
            return int.class.equals(type) || Integer.class.equals(type);
        }
    },
    /**
     * El Long.
     */
    LONG(Long::parseLong) {
        @Override
        public boolean is(Type type) {
            return long.class.equals(type) || Long.class.equals(type);
        }
    },
    /**
     * El Float.
     */
    FLOAT(Float::parseFloat) {
        @Override
        public boolean is(Type type) {
            return float.class.equals(type) || Float.class.equals(type);
        }
    },
    /**
     * El Double.
     */
    DOUBLE(Double::parseDouble) {
        @Override
        public boolean is(Type type) {
            return double.class.equals(type) || Double.class.equals(type);
        }
    },
    /**
     * El Boolean.
     */
    BOOLEAN(Boolean::parseBoolean) {
        @Override
        public boolean is(Type type) {
            return boolean.class.equals(type) || Boolean.class.equals(type);
        }
    },
    /**
     * El Character.
     */
    CHARACTER(string -> string.charAt(0)) {
        @Override
        public boolean is(Type type) {
            return char.class.equals(type) || Character.class.equals(type);
        }
    },
    /**
     * El String.
     */
    STRING(string -> string) {
        @Override
        public boolean is(Type type) {
            return String.class.equals(type);
        }
    };

    /**
     * El Parsing function.
     */
    private final Function<String, Object> parsingFunction;

    /**
     * Instancia un nuevo Basic type.
     *
     * @param parsingFunction el parsing function
     */
    ConstantType(Function<String, Object> parsingFunction) {
        this.parsingFunction = parsingFunction;
    }

    /**
     * Is boolean.
     *
     * @param type el type
     * @return el boolean
     */
    public abstract boolean is(Type type);

    /**
     * Parse object.
     *
     * @param string el string
     * @return el object
     */
    public Object parse(String string) {
        return parsingFunction.apply(string);
    }

    /**
     * Basic type of basic type.
     *
     * @param type el type
     * @return el basic type
     */
    public static ConstantType constantTypeOf(Type type) {
        for (ConstantType constantType : ConstantType.values())
            if (constantType.is(type))
                return constantType;

        return null;
    }

    /**
     * Is basic type boolean.
     *
     * @param type el type
     * @return el boolean
     */
    public static boolean isConstantType(Type type) {
        return constantTypeOf(type) != null;
    }

}
