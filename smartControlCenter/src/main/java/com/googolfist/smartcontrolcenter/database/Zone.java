package com.googolfist.smartcontrolcenter.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/6/21.
 */
@Entity
public class Zone {
    public static final int TYPE_ZONE = 0x03;

    @Id(autoincrement = true)
    @NotNull
    public long id;

    @Unique
    @NotNull
    public String zoneName;

    @Generated(hash = 959994077)
    public Zone(long id, @NotNull String zoneName) {
        this.id = id;
        this.zoneName = zoneName;
    }

    @Generated(hash = 1333518924)
    public Zone() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZoneName() {
        return this.zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
