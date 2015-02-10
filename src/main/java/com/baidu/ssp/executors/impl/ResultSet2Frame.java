package com.baidu.ssp.executors.impl;

import com.baidu.ssp.Frame;
import com.baidu.ssp.element.ListFrame;
import com.baidu.ssp.tables.Table;
import com.baidu.ssp.tables.Type;
import com.google.common.base.Function;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojie on 2015/2/10.
 */
public class ResultSet2Frame implements Function<ResultSet, Frame> {

    private Table table;

    public ResultSet2Frame(Table table) {
        this.table = table;
    }

    @Override
    public Frame apply(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> columns = new ArrayList<String>(columnCount);
            for (int i = 0; i < columnCount; i++) {
                String label = metaData.getColumnLabel(i + 1);
                columns.add(label);
            }
            List<List<Object>> data = new ArrayList<List<Object>>(resultSet.getFetchSize());
            while (resultSet.next()) {
                List<Object> rowData = new ArrayList<Object>(columnCount);
                for (int i = 0; i < columnCount; i++) {
                    Type type = table.getField(columns.get(i)).getType();
                    try {
                        rowData.add(type.newValueWithDefaultValue(resultSet.getObject(i + 1)));
                    } catch (Exception e) {

                        rowData.add(resultSet.getObject(i + 1));

                    }
                }
                data.add(rowData);
            }
            return ListFrame.newInstance(data, columns);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
