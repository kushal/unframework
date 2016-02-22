package com.kushaldave.unframework.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Because GSON only does @Expose, create our own annotation.
 *
 * See http://stackoverflow.com/a/27986860/1055986
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exclude {
}