package net.greenmanov.anime.rubybooru.server.api;

/**
 * Route parameter type
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public enum ParamType {
    INT, STRING, BOOL;

    /**
     * Converts parameter from string to specified object
     *
     * @param value String value
     * @return Object of specified type
     * @throws ConvertException If
     */
    public Object convert(String value) throws ConvertException {
        try {
            switch (this) {
                case STRING:
                    return value;
                case INT:
                    return Integer.valueOf(value);
                case BOOL:
                    return Boolean.valueOf(value);
            }
        } catch (NumberFormatException e) {
            throw new ConvertException("Problem with converting parameter", e);
        }
        return null;
    }

    public class ConvertException extends Exception {
        /**
         * Constructs a new exception with the specified detail message.  The
         * cause is not initialized, and may subsequently be initialized by
         * a call to {@link #initCause}.
         *
         * @param message the detail message. The detail message is saved for
         *                later retrieval by the {@link #getMessage()} method.
         */
        public ConvertException(String message) {
            super(message);
        }

        /**
         * Constructs a new exception with the specified detail message and
         * cause.  <p>Note that the detail message associated with
         * {@code cause} is <i>not</i> automatically incorporated in
         * this exception's detail message.
         *
         * @param message the detail message (which is saved for later retrieval
         *                by the {@link #getMessage()} method).
         * @param cause   the cause (which is saved for later retrieval by the
         *                {@link #getCause()} method).  (A <tt>null</tt> value is
         *                permitted, and indicates that the cause is nonexistent or
         *                unknown.)
         * @since 1.4
         */
        public ConvertException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}