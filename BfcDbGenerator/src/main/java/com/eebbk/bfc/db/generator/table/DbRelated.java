/**
 * 文  件：DbRelated.java
 * 公  司：步步高教育电子
 * 日  期：2016/10/21  16:33
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.db.generator.table;

/**
 * 本表与被关联表的关联信息
 */
public class DbRelated {
    private String relatedTableEntity;
    private DbMapType relatedMapType;

    public String getRelatedTableEntity() {
        return relatedTableEntity;
    }

    /**
     * 设置被关联的表对应的实体类名
     *
     * @param relatedTableEntity 实体类名，被关联的表在创建时记得要setTableEntity这个值进去
     */
    public void setRelatedTableEntity(String relatedTableEntity) {
        this.relatedTableEntity = relatedTableEntity;
    }

    public DbMapType getRelatedMapType() {
        return relatedMapType;
    }

    /**
     * 设置本表与relatedTableEntity之间的关联类型
     *
     * @param relatedMapType 目前只支持三种关系：
     *                       <br>{@link DbMapType#oneToOne} 一对一
     *                       <br>{@link DbMapType#oneToMany} 一对多
     *                       <br>{@link DbMapType#manyToMany} 多对多
     */
    public void setRelatedMapType(DbMapType relatedMapType) {
        this.relatedMapType = relatedMapType;
    }
}
