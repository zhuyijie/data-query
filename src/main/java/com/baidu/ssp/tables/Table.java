package com.baidu.ssp.tables;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 表描述类
 * @author zhuyijie
 * @date 14-8-5
 */
public class Table {

    /**
     * 在数据库中的表名
     * */
    private String rawName;

    /**
     * 用于显示的名称
     * */
    private String tableName;

    /**
     * 所属数据源
     * */
    private String dataSourceId;

    /**
     * 字段名到Field的映射
     * */
    private Map<String, Field> nameToFieldMap;

    public Table(String rawName, String tableName,
                 String dataSourceId, Collection<Field> fields) {
        this.rawName = rawName;
        this.tableName = tableName;
        this.dataSourceId = dataSourceId;
        this.setFields(fields);
    }

    public void setFields(Collection<Field> fields) {
        nameToFieldMap = new HashMap<String, Field>();
        for (Field field : fields) {
            nameToFieldMap.put(field.getName(), field);
        }
    }

    public Collection<Field> getFields() {
        return Collections.unmodifiableCollection(nameToFieldMap.values());
    }

    public Field getField(String fieldName) {
        return nameToFieldMap.get(fieldName);
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}
