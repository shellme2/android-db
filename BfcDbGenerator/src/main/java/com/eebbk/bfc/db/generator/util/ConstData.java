package com.eebbk.bfc.db.generator.util;

public final class ConstData {
    private ConstData() {

    }

    /**
     * 表之间的一对一映射关系
     */
    public static final int ONE_TO_ONE = 1;
    /**
     * 表之间的一对多映射关系
     */
    public static final int ONE_TO_MANY = 2;
    /**
     * 表之间的多对多映射关系
     */
    public static final int MANY_TO_MANY = 3;
}
