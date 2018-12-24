package com.example.invoke.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceContextHolder {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static final ThreadLocal<DataSourceKey> currentDatesource = new ThreadLocal();

    /**
     * 清除当前数据源
     */
    public static void clear() {
        currentDatesource.remove();
    }

    /**
     * 获取当前使用的数据源
     *
     * @return 当前使用数据源的ID
     */
    public static DataSourceKey get() {
        return currentDatesource.get();
    }

    /**
     * 设置当前使用的数据源
     *
     * @param value 需要设置的数据源ID
     */
    public static void set(DataSourceKey value) {
        currentDatesource.set(value);
    }

    /**
     * 设置从从库读取数据
     * 采用简单生成随机数的方式切换不同的从库
     */
    public static void setSlave() {
        DynamicDataSourceContextHolder.set(DataSourceKey.DB_MASTER);
//        if (RandomUtils.nextInt(0, 2) > 0) {
//            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE2);
//        } else {
//            DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE1);
//        }
    }
}