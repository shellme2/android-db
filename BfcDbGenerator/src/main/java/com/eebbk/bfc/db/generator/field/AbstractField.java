package com.eebbk.bfc.db.generator.field;

import de.greenrobot.daogenerator.PropertyType;

public abstract class AbstractField {

    public abstract PropertyType getFieldType();

    public abstract String getFieldName();

    public abstract boolean isPrimaryKey();

    public abstract boolean isAutoIncrement();
}
