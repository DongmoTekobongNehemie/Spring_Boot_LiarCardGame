package com.nehms.game.util;

public interface Converter<T, R> {

    R convert(T t);

}
