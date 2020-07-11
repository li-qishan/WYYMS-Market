package com.buf.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
*Created by Liangzhu on 2018/5/21.
 */
public class MyUtils {
    /**
    *org.mybatis.spring.boot.autoconfigure包下MybatisProperties里面的方法直接拿来用
     *
    *@param mapperLocations xml路径数组
    *@return 资源数组
     */
    public static Resource[] resolveMapperLocations(String[] mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList();
        if (mapperLocations != null) {
            String[] var3 = mapperLocations;
            int var4 = var3.length;
            for (int var5 = 0; var5 < var4; ++var5) {
                String mapperLocation = var3[var5];
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException var8) {
                    ;
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }
}
