package com.eebbk.bfc.db.generator.demo;

import com.eebbk.bfc.db.generator.DbGenerator;
import com.eebbk.bfc.db.generator.field.DbField;
import com.eebbk.bfc.db.generator.field.DbFieldPrimary;
import com.eebbk.bfc.db.generator.table.DbTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.PropertyType;
import de.greenrobot.daogenerator.ToMany;

/**
 * Desc:
 * Author: llp
 * Create Time: 2016-11-24 16:23
 * Email: jacklulu29@gmail.com
 */

public class DbGenerate {

    private static final String USER_CLASS_NAME = "UserInfo";
    private static final String USER_TABLE_NAME = "user_table";

    private static final String BOOK_CLASS_NAME = "BookInfo";
    private static final String BOOK_TABLE_NAME = "book_table";

    public static void main(String[] args) {
        generateCode();
    }

    private static void generateCode() {
        DbGenerator.init(1, "com.eebbk.bfc.db.demo.db.entity");

        List<DbTable> tableList = new ArrayList<>();

        /*********** 用户信息表 ***********/
        DbTable userTable = new DbTable();
        userTable.setTableEntity(USER_CLASS_NAME);
        userTable.setTableName(USER_TABLE_NAME);

        DbFieldPrimary userId = new DbFieldPrimary("userId", PropertyType.String, false);
        userTable.addField(userId);
        DbField userName = new DbField("userName", PropertyType.String);
        userTable.addField(userName);
        DbField userPwd = new DbField("userPwd", PropertyType.String);
        userTable.addField(userPwd);

        tableList.add(userTable);
        /*********** End *****************/

        /*********** 书籍信息表 ***********/
        DbTable bookTable = new DbTable();
        bookTable.setTableEntity(BOOK_CLASS_NAME);
        bookTable.setTableName(BOOK_TABLE_NAME);

        DbFieldPrimary bookId = new DbFieldPrimary("bookId", PropertyType.String, false);
        bookTable.addField(bookId);
        DbField bookName = new DbField("bookName", PropertyType.String);
        bookTable.addField(bookName);

        tableList.add(bookTable);
        /*********** End *****************/

        DbGenerator.getInstance().initTables(tableList);

        // 此处可进行多表关联的设置
        HashMap<String, Entity> entityList = DbGenerator.getInstance().getEntityList();
        Entity userEntity = entityList.get(USER_CLASS_NAME);
        Entity bookEntity = entityList.get(BOOK_CLASS_NAME);

        // 一对一
        /*Property foreignKey = userEntity.addProperty(PropertyType.String, "appId").getProperty();
        ToOne toOne=userEntity.addToOne(appEntity, foreignKey);*/
        // 一对多
        Property foreignKey = bookEntity.addProperty(PropertyType.String, "userId").getProperty();
        ToMany toMany = userEntity.addToMany(bookEntity, foreignKey);

        // 最终生成代码
        DbGenerator.getInstance().doGenerate("E:/java");
    }

}
