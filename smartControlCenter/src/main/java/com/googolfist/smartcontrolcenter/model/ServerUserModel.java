package com.googolfist.smartcontrolcenter.model;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ServerUserModel {
    private String mUser;
    private String mPassword;

    public ServerUserModel() {
        super();
        mUser = "";
        mPassword = "";
    }

    public ServerUserModel(String user) {
        mUser = user;
        mPassword = "";
    }


    public String getUser() {
        return mUser;
    }

    public void seUser(String user) {
        if (!mUser.equals(user)) {
            mUser = user;
        }
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        if (!mPassword.equals(password)) {
            mPassword = password;
        }
    }
}
