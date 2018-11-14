package com.qiyuely.remex.dawn.common.msg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 入参日志排除标识
 * 
 * @author Qiaoxin.Hong
 *
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InArgsLogExclude {

}
