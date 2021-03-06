package com.zmlh.until;

import lombok.SneakyThrows;

/**
 * @Interface AbstractExceptionHandler
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-07 14:56
 * @Version 1.0
 **/
public interface AbstractExceptionHandler<E extends Exception> {

    @SneakyThrows
    default void exceptionHandler ( String message, Class<E> eClass ) {
        throw eClass.getConstructor(String.class).newInstance(message);
    }
}
