package com.zmlh.myexception;

/**
 * @ClassName ExcelAnalysisEventListenerException
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-15 9:59
 * @Version 1.0
 **/
public class ExcelAnalysisEventListenerException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ExcelAnalysisEventListenerException () {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ExcelAnalysisEventListenerException ( String message ) {
        super(message);
    }
}
