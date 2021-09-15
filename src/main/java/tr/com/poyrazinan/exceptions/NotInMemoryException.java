package tr.com.poyrazinan.exceptions;

public class NotInMemoryException extends Exception {

    /**
     * Throws when cached value doesn't contain
     * the person who want to touch.
     *
     * @param message
     */
    public NotInMemoryException(String message) {
        super(message);
    }
}