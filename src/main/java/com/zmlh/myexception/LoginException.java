package com.zmlh.myexception;

/**
 * @ClassName LoginException
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-07 14:35
 * @Version 1.0
 **/
public class LoginException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public LoginException ( String message ) {
        super(message);
    }

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public LoginException () {
    }
}
