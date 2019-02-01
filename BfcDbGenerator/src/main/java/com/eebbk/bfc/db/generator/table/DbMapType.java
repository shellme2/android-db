/**
 * 文  件：DbMapType.java
 * 公  司：步步高教育电子
 * 日  期：2016/10/21  16:34
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.db.generator.table;

import com.eebbk.bfc.db.generator.util.ConstData;

/**
 * 表关联时的映射类型
 */
public final class DbMapType {
    /**
     * 本表与该表之间存在一对一映射关系<br>
     * 即本表的一个对象对应该表的一个对象，本表的主键在该表是外键
     */
    public static final DbMapType oneToOne = new DbMapType(ConstData.ONE_TO_ONE, "一对一");
    /**
     * 本表与该表之间存在一对多映射关系<br>
     * 即本表的一个对象对应该表的多个子对象，本表的主键在该表是外键
     */
    public static final DbMapType oneToMany = new DbMapType(ConstData.ONE_TO_MANY, "一对多");
    /**
     * 本表与该表之间存在多对多映射关系<br>
     * 本表的一个对象对应该表的多个对象，本表的主键在该表是外键<br>
     * 该表的一个对象也对应本表的多个对象，该表的主键在本表是外键<br>
     */
    public static final DbMapType manyToMany = new DbMapType(ConstData.MANY_TO_MANY, "多对多");

    private int typeCode;
    private String typeDescribe;

    private DbMapType(int typeCode, String typeDescribe) {
        this.typeCode = typeCode;
        this.typeDescribe = typeDescribe;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public String getTypeDescribe() {
        return typeDescribe;
    }
}
