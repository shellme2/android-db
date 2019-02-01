package com.eebbk.bfc.db.generator.field;

import de.greenrobot.daogenerator.PropertyType;

public class DbField extends AbstractField {

    private PropertyType fieldType;
    private String fieldName;

    public DbField(String fieldName, PropertyType fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    @Override
    public PropertyType getFieldType() {
        return fieldType;
    }

    public void setFieldType(PropertyType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean isPrimaryKey() {
        return false;
    }

    @Override
    public boolean isAutoIncrement() {
        return false;
    }
}
