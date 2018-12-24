package com.example.invoke.annotation;

import com.example.invoke.common.DataSourceKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {
    DataSourceKey dataSourceKey() default DataSourceKey.DB_MASTER;
}