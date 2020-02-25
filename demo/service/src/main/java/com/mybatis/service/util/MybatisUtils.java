package com.mybatis.service.util;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;

import java.util.Properties;

public class MybatisUtils {

    public static Interceptor resetPageHelper(String helperDialect) {
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();

        properties.setProperty("helperDialect", helperDialect);
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "false");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "pageNum=pageNumKey;pageSize=pageSizeKey;");
        interceptor.setProperties(properties);
        return interceptor;
    }

}
