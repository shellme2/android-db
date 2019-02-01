# 数据库操作

## 关于

中间件数据库，属于中间件子项目，封装了greendao:2.2.1，greendao-generator:2.2.0。支持数据库建表、增删改查操作。

## 特性

+ 封装了第三方开源库GreenDao，继承了该库的特性
+ 可以自动生成数据实体类

## 版本和项目名称

+ 发布版本：2.2.0
+ GitLab项目名：BfcDb
+ 库名称：bfc-db
+ 需要 Android API >= 15

## 功能列表

+ 设置数据库的版本号，存放路径；
+ 设置数据库表的实体类名，数据库表Table的名称；
+ 设置数据库表的的字段名，字段类型，对应数据库表的实体类的成员变量名称；
+ 生成数据库表的实体类代码；
+ 数据库增删改查基本操作；

## 升级清单文档

+ 文档名称：UPDATE.md (http://172.28.2.93/bfc/BfcDb/blob/master/UPDATE.md)

## 使用

### 前置条件

#### 1、需要申请的权限

            <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
            <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

#### 2、项目中如何依赖数据库

-  添加`compile bfcBuildConfig.deps.'bfc-push'`
>  [详细请查看网络配置使用方法](http://172.28.2.93/bfc/Bfc/blob/develop/public-config/%E4%BE%9D%E8%B5%96%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md);


### 生成数据实体类

[生成数据库实体类代码Demo](http://172.28.2.93/bfc/BfcDb/blob/master/BfcDbGeneratorDemo/src/main/java/com/eebbk/bfc/db/generator/demo/DbGenerate.java)

### 公开接口说明

#### 接口1：初始化

    /**
     * 初始化表操作
     * @param dbVersion  数据库版本号
     * @param codeSavePackage  数据库保存路径——包名
     */
    public static void init(int dbVersion, String codeSavePackage){

    }
#### 接口2：返回DbGenerator单列

    public static DbGenerator getInstance() {
            return DbGeneratorHandler.mInstance;
    }

#### 接口3：获取所有表实体

    /**
     * 获取所有表实体
     */
    public HashMap<String, Entity> getEntityList() {
        return entityList;
    }

#### 接口4：取所有表实体里面的字段

    /**
     * 获取所有表实体里面的字段
     * 通过获取此结果可以设置多表关联，如1对n
     */
    public HashMap<String, HashMap<String, Property>> getPropertyList() {
        return propertyList;
    }

#### 接口5：设置表及其字段

       /**
         * 设置表及其字段
         */
        public void initTables(List<DbTable> tableList) {
        }

#### 接口6：生成实体类代码（[数据库表的增删该查接口均在生成的代码里边,使用可参考Demo](http://172.28.2.93/bfc/BfcDb/tree/master/BfcDbDemo/src/main/java/com/eebbk/bfc/db/demo/db)）

       /**
         * 生成代码
         * @param outputPath 代码存放的绝对路径
         * @return 返回是否生成成功
         */
        public void doGenerate(String outputPath) {
        }

#### 接口7：获取SDK版本信息接口
 - 名称：SDKVersion
 - 作用说明：用于获取SDK版本信息

 - 使用方式：

		   Int code=SDKVersion.getSDKInt();
	       String versionName=SDKVersion.getVersionName();
	       String buildName=SDKVersion.getBuildName();
	       String buildTime=SDKVersion.getBuildTime();
	       String buildTag=SDKVersion.getBuildTag();
	       String buildHead=SDKVersion.getBuildHead();

## 源码保存地址
http://172.28.2.93/bfc/BfcDb.git

## 相关文档获取方式
http://172.28.2.93/bfc/BfcDb.git

## 问题反馈

使用过程成遇到任何问题，有任何建议或者意见都可以使用下面这个地址反馈给我们。欢迎大家提出问题，我们将会在最短的时间内解决问题。 地址：http://172.28.2.93/bfc/BfcDb/issues
