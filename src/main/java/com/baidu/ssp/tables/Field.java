package com.baidu.ssp.tables;
/**
 * 表字段类
 * @author zhuyijie
 * @date 14-8-5
 */
public class Field {
    //字段名
    private String name;
    //对应数据库的表达式,如果是Frame的则此项为null
    private String rawExpression;

    private Type type;

    public Field(String name, String rawExpression, Type type) {
        this.type = type;
        this.rawExpression = rawExpression;
        this.name = name;
    }

    public String getRawExpression() {
        return rawExpression;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

}
