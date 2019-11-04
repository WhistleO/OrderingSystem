package com.example.orderingsystem.structure;

import android.app.Application;

public class MyApplication extends Application {
    public int state;
    public Boolean loginstatus = false;
    public String uid;

    public int getState() {
        return this.state;
    }

    public void setState(int sstate) {
        this.state = sstate;
    }

    public void setLoginstatus(Boolean sstate) {
        this.loginstatus = sstate;
    }

    public Boolean getLoginState() {
        return this.loginstatus;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

