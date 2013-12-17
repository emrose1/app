package com.studio.bookings.entity;

import java.io.Serializable;
import java.util.Date;

public class UserSession implements Serializable {
    private User user;
    private Date loginTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
