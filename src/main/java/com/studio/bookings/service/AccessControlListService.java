package com.studio.bookings.service;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.User;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.HelloGreetings;
import com.studio.bookings.entity.UserSession;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;

public class AccessControlListService extends BaseService {
	
	
	public AccessControlList getByUserTypeAndPermission(User user, Permission permission) throws Exception {
        return null;
    }
	
	public HelloGreetings authedGreeting(User user) {
		HelloGreetings response = new HelloGreetings("hello " + user.getEmail());
		return response;
	}
 
/* 
    public boolean allowView(Permission permission, User user) throws Exception {
    	
    	Person p = personDao.
        return this.getByUserTypeAndPermission(((UserSession) session.getAttribute("userSession")).getUser().getUserType(), permission).isCanView();
    }
 
    public boolean allowInsert(HttpSession session, Permission permission) throws Exception {
        return this.getByUserTypeAndPermission(((UserSession) session.getAttribute("userSession")).getUser().getUserType(), permission).isCanInsert();
    }
 
    public boolean allowUpdate(User user, Permission permission) throws Exception {
        return this.getByUserTypeAndPermission(((UserSession) session.getAttribute("userSession")).getUser().getUserType(), permission).isCanUpdate();
    }
 
    public boolean allowDelete(HttpSession session, Permission permission) throws Exception {
        return this.getByUserTypeAndPermission(((UserSession) session.getAttribute("userSession")).getUser().getUserType(), permission).isCanDelete();
    }
*/
}
