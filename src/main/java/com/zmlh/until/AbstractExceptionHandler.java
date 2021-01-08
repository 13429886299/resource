package com.zmlh.until;

import lombok.SneakyThrows;

/**
 * @Interface AbstractExceptionHandler
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-07 14:56
 * @Version 1.0
 **/
public abstract class AbstractExceptionHandler<E extends Exception> {

    @SneakyThrows
    protected void exceptionHandler ( String message, Class<E> eClass ) {
        throw eClass.getConstructor(String.class).newInstance(message);
    }
}
