package com.googolfist.smartcontrolcenter.database;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by Administrator on 2017/6/22.
 */

public class UserTypeConverter implements PropertyConverter<Object, Object> {
    @Override
    public Object convertToEntityProperty(Object databaseValue) {
        return null;
    }

    @Override
    public Object convertToDatabaseValue(Object entityProperty) {
        return null;
    }
}
