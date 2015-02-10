package com.baidu.ssp.tables;

import com.baidu.ssp.exceptions.FieldNotExistsException;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The table, containing table name and fields
 * is used to describe the corresponding 'table in database'
 * @author zhuyijie
 * @date 14-8-5
 */
public class Table {


    /**
     * the table used for displaying
     * */
    private String tableName;

    /**
     * the data source
     * */
    private DataSource dataSource;

    /**
     * the mapping from field to a Field Object
     * */
    private Map<String, Field> nameToFieldMap;

    public Table(String tableName,
                 DataSource dataSource, Collection<Field> fields) {
        this.tableName = tableName;
        this.dataSource = dataSource;
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
        if (!nameToFieldMap.containsKey(fieldName)) {
            throw new FieldNotExistsException("field : " + fieldName + " not exists in table " + this.tableName);
        }
        return nameToFieldMap.get(fieldName);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
