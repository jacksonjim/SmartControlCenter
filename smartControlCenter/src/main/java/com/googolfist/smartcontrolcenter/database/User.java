package com.googolfist.smartcontrolcenter.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/21.
 */


/**
 * 1 @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
 * 2 @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
 * 3 @Property：可以自定义字段名，注意外键不能使用该属性
 * 4 @NotNull：属性不能为空
 * 5 @Transient：使用该注释的属性不会被存入数据库的字段中
 * 6 @Unique：该属性值必须在数据库中是唯一值
 * 7 @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
 */
@Entity(indexes = {
        @Index(value = "userName, date DESC", unique = true)
})
public class User {

    public static final int TYPE_USER = 0x01;

    @Id(autoincrement = true)
    @NotNull
    private Long id;

    @Unique
    @NotNull
    private String userName;
    private Date date;

    @Generated(hash = 1062382497)
    public User(@NotNull Long id, @NotNull String userName, Date date) {
        this.id = id;
        this.userName = userName;
        this.date = date;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
