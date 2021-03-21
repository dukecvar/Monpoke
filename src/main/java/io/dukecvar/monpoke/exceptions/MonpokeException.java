package io.dukecvar.monpoke.exceptions;

/**
 * Generic and simple runtime exception for the Monpoke game
 * @author Duke Cvar
 */
public class MonpokeException extends RuntimeException {
    /**
     * Simple constructor override
     * @param message Exception message
     */
    public MonpokeException(String message) {
        super(message);
    }
}
