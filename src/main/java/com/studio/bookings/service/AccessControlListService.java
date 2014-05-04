package com.studio.bookings.service;

import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.google.api.server.spi.config.Api;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.HelloGreetings;
import com.studio.bookings.entity.Person;
import com.studio.bookings.entity.UserSession;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;
import com.studio.bookings.util.Constants;

@Api(
	    name = "booking",
	    version = "v1",
	    scopes = {Constants.EMAIL_SCOPE},
	    clientIds = {Constants.WEB_CLIENT_ID},
	    audiences = {Constants.ANDROID_AUDIENCE}
	)


public class AccessControlListService extends BaseService {
	
	
	public AccessControlList getByUserTypeAndPermission(UserType userType, Permission permission) throws Exception {
        return null;
    }

	
	// create
    public AccessControlList newAccessControlList(
    		@Named("permission") String permission,
    		@Named("canView") String canView,
    		@Named("canInsert") String canInsert,
    		@Named("canUpdate") String canUpdate,
    		@Named("canDelete") String canDelete,
    		@Named("userType") String userType,
    		@Named("account") Long accountId,
			User user) throws Exception {
    	
    	if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		// TODO CHECK USER PERMISSION

	    	Long oId = new Long(accountId);
			Account account =  accountDao.retrieve(oId);
			AccessControlList acl = new AccessControlList(
					permission, canView, canInsert, canUpdate, 
					canDelete, userType, account);
			accessControlListDao.save(acl);
			return acl;
    	}
    	
    	else {
    		return null;
    	}
    }

    // TODO retrieve one
    public AccessControlList get(Long id) throws Exception {
        return accessControlListDao.(id);
    }

    // TODO update 
    public AccessControlList update(AccessControlList entity) throws Exception {
        updateEntity(entity);
        
        return entity;
    }
    
    // TODO delete
    public void delete(AccessControlList entity) throws Exception {
        deleteEntity(entity);
    }

    // TODO list all
    public List<AccessControlList> listAll() throws Exception {
        return listAll(AccessControlList.class);
    }


    public boolean allowView(Permission permission, UserType userType) throws Exception {
    	// TODO get USERTYPE OF PERSON - filter acl with accountID ancestor and userType & permission, two filter query
        return this.getByUserTypeAndPermission(userType, permission).isCanView();
    }

    public boolean allowInsert(Permission permission, UserType userType) throws Exception {
        return this.getByUserTypeAndPermission(userType, permission).isCanInsert();
    }

    public boolean allowUpdate(Permission permission, UserType userType) throws Exception {
        return this.getByUserTypeAndPermission(userType, permission).isCanUpdate();
    }

    public boolean allowDelete(Permission permission, UserType userType) throws Exception {
        return this.getByUserTypeAndPermission(userType, permission).isCanDelete();
    }

    public boolean allowView(Permission permission, User user) throws Exception {
        return this.getByUserTypeAndPermission(getUser().getUserType(), permission).isCanView();
    }

    public boolean allowInsert(Permission permission, User user) throws Exception {
    	// TODO get USERTYPE OF PERSON - filter person with accountID ancestor and userId, get userType from person found, one filter query
        return this.getByUserTypeAndPermission(((UserSession) session.getAttribute("userSession")).getUser().getUserType(), permission).isCanInsert();
    }

    public boolean allowUpdate(Permission permission, User user) throws Exception {
        return this.getByUserTypeAndPermission(((UserSession) session.getAttribute("userSession")).getUser().getUserType(), permission).isCanUpdate();
    }

    public boolean allowDelete(Permission permission, User user) throws Exception {
        return this.getByUserTypeAndPermission(((UserSession) session.getAttribute("userSession")).getUser().getUserType(), permission).isCanDelete();
    }
	
	
}
