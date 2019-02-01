package com.eebbk.bfc.db.generator;

import com.eebbk.bfc.db.generator.field.AbstractField;
import com.eebbk.bfc.db.generator.table.DbTable;

import java.util.HashMap;
import java.util.List;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public final class DbGenerator {

    private Schema schema = null;
    /**
     * 一个Entity代表有一个表，以此类推
     * String:表对应的对象名，即className
     */
    private HashMap<String, Entity> entityList = null;

    /**
     * 一个Property代表一个字段
     * 第一个String:表对应的对象名，即className
     * 第二个String:对象里面的属性名
     */
    private HashMap<String, HashMap<String, Property>> propertyList = null;

    private static class DbGeneratorHandler {
        private static final DbGenerator mInstance = new DbGenerator();
    }

    private DbGenerator() {
        entityList = new HashMap<>();
        propertyList = new HashMap<>();
    }

    public static void init(int dbVersion, String codeSavePackage){
        Schema schema = new Schema(dbVersion, codeSavePackage);
        // 使下次代码生成的时候不覆盖keep内的代码
        schema.enableKeepSectionsByDefault();
        DbGeneratorHandler.mInstance.schema = schema;
    }

    public static DbGenerator getInstance() {
        return DbGeneratorHandler.mInstance;
    }

    /**
     * 获取所有表实体
     */
    public HashMap<String, Entity> getEntityList() {
        return entityList;
    }

    /**
     * 获取所有表实体里面的字段
     * 通过获取此结果可以设置多表关联，如1对n
     */
    public HashMap<String, HashMap<String, Property>> getPropertyList() {
        return propertyList;
    }

    /**
     * 设置表及其字段
     */
    public void initTables(List<DbTable> tableList) {
        if(schema == null){
            throw new IllegalArgumentException("must call DbGenerator.init(...) first");
        }
        Entity entity = null;
        HashMap<String, Property> propertyHashMap = null;
        for (DbTable dbtable : tableList) {
            entity = schema.addEntity(dbtable.getTableEntity());
            entity.setTableName(dbtable.getTableName());
            entityList.put(dbtable.getTableEntity(), entity);
            propertyHashMap = new HashMap<>();
            for (AbstractField dbField : dbtable.getFieldList()) {
                Property property = null;
                if (dbField.isPrimaryKey() || dbField.isAutoIncrement()) {
                    if (dbField.isPrimaryKey() && !dbField.isAutoIncrement()) {
                        property = entity.addProperty(dbField.getFieldType(), dbField.getFieldName()).primaryKey().getProperty();
                    } else if (dbField.isAutoIncrement() && !dbField.isPrimaryKey()) {
                        property = entity.addProperty(dbField.getFieldType(), dbField.getFieldName()).autoincrement().getProperty();
                    } else {
                        property = entity.addProperty(dbField.getFieldType(), dbField.getFieldName()).primaryKey().autoincrement().getProperty();
                    }
                } else {
                    property = entity.addProperty(dbField.getFieldType(), dbField.getFieldName()).getProperty();
                }
                propertyHashMap.put(dbField.getFieldName(), property);
            }
            propertyList.put(dbtable.getTableEntity(), propertyHashMap);
        }
    }

    /**
     * 生成代码
     * @param outputPath 代码存放的绝对路径
     * @return 返回是否生成成功
     */
    public void doGenerate(String outputPath) {
        if (schema.getEntities() == null || schema.getEntities().size() == 0) {
            throw new IllegalArgumentException("Entities is empty!");
        }
        try {
            new DaoGenerator().generateAll(schema, outputPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
