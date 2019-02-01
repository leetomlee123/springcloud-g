package com.example.invoke.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {

        return DynamicDataSourceContextHolder.get();
    }
}