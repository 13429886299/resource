package com.zmlh.server;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

/**
 * @Interface ExceptionHandler
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-07 14:56
 * @Version 1.0
 **/
public interface ExceptionHandler<E extends Exception> {

    @SneakyThrows
    default void exceptionHandler ( String message, Class<E> e ) {

        throw e.getConstructor(String.class).newInstance(message);
    }
}
