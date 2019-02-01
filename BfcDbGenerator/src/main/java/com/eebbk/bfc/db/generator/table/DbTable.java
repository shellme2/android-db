package com.eebbk.bfc.db.generator.table;

import com.eebbk.bfc.db.generator.field.AbstractField;

import java.util.ArrayList;
import java.util.List;

public class DbTable {
    /**
     * 表所映射的实体类名
     */
    private String tableEntity;
    /**
     * 表在数据库中的名字
     */
    private String tableName;
    /**
     * 表所含的字段列表
     */
    private List<AbstractField> fieldList;

    /**
     * 此表与其他表的关联关系
     */
    private List<DbRelated> relatedList;

    public DbTable() {
        fieldList = new ArrayList<AbstractField>();
        relatedList = new ArrayList<DbRelated>();
    }

    public String getTableEntity() {
        return tableEntity;
    }

    /**
     * 设置表对应的实体类名，非空，且驼峰命名
     *
     * @param entityName 实体类名，如传：UserInfo，则最终给你生成UserInfo.java这个类，所以要遵循命名规则来传这个值
     */
    public void setTableEntity(String entityName) {
        this.tableEntity = entityName;
    }

    public String getTableName() {
        if (tableName == null) {
            return tableEntity;
        }
        return tableName;
    }

    /**
     * 设置表在数据库里的表名，如果没有传这个值，则默认表名为tableEntity，即实体类名
     *
     * @param tableName 表名，如传：UserInfo，则最终给你生成UserInfo.java这个类，所以要遵循命名规则来传这个值
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<AbstractField> getFieldList() {
        return fieldList;
    }

    public List<DbRelated> getRelatedList() {
        return relatedList;
    }

    /**
     * 添加表字段
     *
     * @param field 传AbstractField的子类实例 有2种字段：
     *              <br>DbFieldPrimary 主键字段，只能传一个
     *              <br>DbField 其他非主键字段
     */
    public void addField(AbstractField field) {
        fieldList.add(field);
    }

    /**
     * 添加关联的表信息
     * <br>1.若你的app仅有一个表或多表之间并无关联，则无需add
     * <br>2.由于一个表可能与多个表有关联，所以可能需要add多个关联，以此形成了一个List
     *
     * @param related 关联的信息 related包含：被关联的表实体名、映射关系
     */
    public void addRelate(DbRelated related) {
        relatedList.add(related);
    }
}
