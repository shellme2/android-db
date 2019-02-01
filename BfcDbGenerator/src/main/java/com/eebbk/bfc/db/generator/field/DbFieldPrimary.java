package com.eebbk.bfc.db.generator.field;

import de.greenrobot.daogenerator.PropertyType;

public class DbFieldPrimary extends AbstractField {

    private PropertyType fieldType;
    private String fieldName;
    private boolean isAutoIncrement = false;

    public DbFieldPrimary(String fieldName, PropertyType fieldType, boolean isAutoIncrement) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.isAutoIncrement = isAutoIncrement;
    }

    public void setIsAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public void setFieldType(PropertyType fieldType) {
        this.fieldType = fieldType;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public PropertyType getFieldType() {
        return fieldType;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public boolean isPrimaryKey() {
        return true;
    }

    @Override
    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }
}
