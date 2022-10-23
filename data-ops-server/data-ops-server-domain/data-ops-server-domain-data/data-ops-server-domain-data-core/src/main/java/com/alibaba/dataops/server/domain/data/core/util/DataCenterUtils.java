package com.alibaba.dataops.server.domain.data.core.util;

import java.util.Map;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;
import com.alibaba.dataops.server.domain.data.core.model.DataSourceWrapper;
import com.alibaba.dataops.server.domain.data.core.model.JdbcDataTemplate;
import com.alibaba.dataops.server.tools.base.excption.BusinessException;
import com.alibaba.dataops.server.tools.common.enums.ErrorEnum;
import com.alibaba.druid.DbType;

import com.google.common.collect.Maps;

/**
 * 用来存储连接等数据
 *
 * @author 是仪
 */
public class DataCenterUtils {

    /**
     * 数据源
     * key: dataSourceId
     */
    public static final Map<Long, DataSourceWrapper> DATA_SOURCE_CACHE = Maps.newConcurrentMap();

    /**
     * 数据执行模板列表
     * key: dataSourceId
     */
    public static final Map<Long, Map<Long, JdbcDataTemplate>> JDBC_TEMPLATE_CACHE = Maps.newConcurrentMap();

    /**
     * 默认的的数据执行模板列表
     * key: dataSourceId
     */
    public static final Map<Long, JdbcDataTemplate> DEFAULT_JDBC_TEMPLATE_CACHE = Maps.newConcurrentMap();

    /**
     * 获取默认的JdbcDataTemplate
     *
     * @param dataSourceId
     * @return
     */
    public static JdbcDataTemplate getDefaultJdbcDataTemplate(Long dataSourceId) {
        JdbcDataTemplate jdbcDataTemplate = DEFAULT_JDBC_TEMPLATE_CACHE.get(dataSourceId);
        if (jdbcDataTemplate == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        return jdbcDataTemplate;
    }

    /**
     * 根据  dataSourceId  consoleId 获取JdbcDataTemplate
     *
     * @param dataSourceId
     * @param consoleId
     * @return
     */
    public static JdbcDataTemplate getJdbcDataTemplate(Long dataSourceId, Long consoleId) {
        Map<Long, JdbcDataTemplate> jdbcDataTemplateMap = JDBC_TEMPLATE_CACHE.get(dataSourceId);
        if (jdbcDataTemplateMap == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        JdbcDataTemplate jdbcDataTemplate = jdbcDataTemplateMap.get(consoleId);
        if (jdbcDataTemplate == null) {
            throw new BusinessException(ErrorEnum.CONSOLE_NOT_FOUND);
        }
        return jdbcDataTemplate;
    }

    /**
     * 根据dataSourceId 获取数据库类型
     *
     * @param dataSourceId
     * @return
     */
    public static DbTypeEnum getDbTypeByDataSourceId(Long dataSourceId) {
        DataSourceWrapper dataSourceWrapper = DATA_SOURCE_CACHE.get(dataSourceId);
        if (dataSourceWrapper == null) {
            throw new BusinessException(ErrorEnum.DATA_SOURCE_NOT_FOUND);
        }
        return dataSourceWrapper.getDbType();
    }

    /**
     * 根据dataSourceId 获取Druid数据库类型
     *
     * @param dataSourceId
     * @return
     */
    public static DbType getDruidDbTypeByDataSourceId(Long dataSourceId) {
        return JdbcUtils.parse2DruidDbType(getDbTypeByDataSourceId(dataSourceId));
    }
}
