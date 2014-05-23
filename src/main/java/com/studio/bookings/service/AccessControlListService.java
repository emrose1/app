package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
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
	
	Permission aclPermission = Permission.ACL;
	
	@ApiMethod(name = "calendar.addAcl", path="calendar.addAcl", httpMethod = "post")
    public AccessControlList insertAccessControlList(
    		@Named("permission") String permission,
    		@Named("canView") String canView,
    		@Named("canViewAll") String canViewAll,
    		@Named("canInsert") String canInsert,
    		@Named("canUpdate") String canUpdate,
    		@Named("canDelete") String canDelete,
    		@Named("userType") String userType,
    		@Named("account") Long accountId,
			User user) {
		AccessControlList acl = null;
    	if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (this.allowInsert(accountId, aclPermission.toString(), user).get(0)) {
				acl = new AccessControlList(
						permission, canView, canViewAll, canInsert, canUpdate, 
						canDelete, userType);
				aclDao.save(acl);
    		}
    	}
    	return acl;
	}

    @ApiMethod(name = "calendar.findAcl", path="calendar.findAcl", httpMethod = "get")
	public AccessControlList findAccessControlList(
			@Named("acl") Long aclId,
			@Named("account") Long accountId,
			User user) {
    	
    	AccessControlList acl = null;
    	if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (this.allowView(accountId, aclPermission.toString(), user).get(0)) {
    			acl = aclDao.retrieve(aclId);
    		}
    	}
    	return acl;
	}
	
    @ApiMethod(name = "calendar.updateAcl", path="calendar.updateAcl", httpMethod = "post")
	public AccessControlList updateAccessControlList(
			@Named("acl") Long aclId,
			@Named("permission") String permission,
    		@Named("canView") String canView,
    		@Named("canViewAll") String canViewAll,
    		@Named("canInsert") String canInsert,
    		@Named("canUpdate") String canUpdate,
    		@Named("canDelete") String canDelete,
    		@Named("userType") String userType,
    		@Named("account") Long accountId,
    		User user) {
    	
		AccessControlList aclFetched = aclDao.retrieve(aclId);
    	if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (this.allowUpdate(accountId, aclPermission.toString(), user).get(0)) {
				aclFetched.setPermission(Permission.valueOf(permission));
				aclFetched.setCanView(Boolean.valueOf(canView));
				aclFetched.setCanViewAll(Boolean.valueOf(canViewAll));
				aclFetched.setCanInsert(Boolean.valueOf(canInsert));
				aclFetched.setCanUpdate(Boolean.valueOf(canUpdate));
				aclFetched.setCanDelete(Boolean.valueOf(canDelete));
				aclFetched.setUserType(UserType.valueOf(userType));
				aclFetched.setCanDelete(Boolean.valueOf(canDelete));
				aclDao.save(aclFetched);
    		}
    	}
		return aclFetched;
	}
	

	@ApiMethod(name = "calendar.deleteAcl", path="calendar.deleteAcl", httpMethod = "post")
	public void deleteAccessControlList (
			@Named("aclList") Long aclId,
			@Named("account") Long accountId,
			User user) {

    	if(user != null) { 
    		if (this.allowDelete(accountId, aclPermission.toString(), user).get(0)) {
    			aclDao.delete(aclId);
    		}
    	}
	}
    
	@ApiMethod(name = "calendar.listAcl", path="calendar.listAcl", httpMethod = "post")
    public List<AccessControlList> listAccessControlList(@Named("account") Long accountId, User user) {
		List<AccessControlList> aclList = null;
		if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (this.allowView(accountId, aclPermission.toString(), user).get(0)) {
    			aclList = aclDao.list();    	
    		}
		}
		return aclList;
    }

	private AccessControlList getByUserTypeAndPermission(UserType userType, Permission permission) {
		return aclDao.twoFilterQuery("userType", userType.toString(), "permission", permission.toString());
    }
	
	private UserType getUserType(Account account, User user) throws Exception {
    	UserType userType = personDao.oneFilterAncestorQuery("userId", user.getUserId(), account).getUserType();
    	if (userType == null) {
    		throw new NotFoundException("User doesn't have a user type for this account");
    	}
    	return userType;
	}

	@ApiMethod(name = "calendar.isCanView", path="calendar.isCanView", httpMethod = "get")
    public List<Boolean> allowView(
    		@Named("account") Long accountId, 
    		@Named("permission") String permission, 
    		User user) {
    	Account accountFetched = accountDao.retrieve(accountId);
    	UserType ut = null;
		try {
			ut = this.getUserType(accountFetched, user);
    	} catch (Exception e) {
    		ut = UserType.ATTENDEE;
    	}
    	List<Boolean> result = new ArrayList<Boolean>();
    	result.add(getByUserTypeAndPermission(ut, Permission.valueOf(permission)).getCanView());
        return result;
    }
	
	@ApiMethod(name = "calendar.isCanViewAll", path="calendar.isCanViewAll", httpMethod = "get")
    public List<Boolean> allowViewAll(
    		@Named("account") Long accountId, 
    		@Named("permission") String permission, 
    		User user) {
		Account accountFetched = accountDao.retrieve(accountId);
		UserType ut = null;
		try {
			ut = this.getUserType(accountFetched, user);
    	} catch (Exception e) {
    		ut = UserType.ATTENDEE;
    	}
    	List<Boolean> result = new ArrayList<Boolean>();
        result.add(this.getByUserTypeAndPermission(ut, Permission.valueOf(permission)).getCanViewAll());
        return result;
    }
	
	@ApiMethod(name = "calendar.isCanInsert", path="calendar.isCanInsert", httpMethod = "get")
    public List<Boolean> allowInsert(
    		@Named("account") Long accountId, 
    		@Named("permission") String permission, 
    		User user) {
		Account accountFetched = accountDao.retrieve(accountId);
		UserType ut = null;
		try {
			ut = this.getUserType(accountFetched, user);
    	} catch (Exception e) {
    		ut = UserType.ATTENDEE;
    	}
    	List<Boolean> result = new ArrayList<Boolean>();
    	result.add(this.getByUserTypeAndPermission(ut, Permission.valueOf(permission)).getCanInsert());
        return result;
    }

	@ApiMethod(name = "calendar.isCanUpdate", path="calendar.isCanUpdate", httpMethod = "get")
    public List<Boolean> allowUpdate(
    		@Named("account") Long accountId, 
    		@Named("permission") String permission, 
    		User user)  {
    	Account accountFetched = accountDao.retrieve(accountId);
		UserType ut = null;
		try {
			ut = this.getUserType(accountFetched, user);
    	} catch (Exception e) {
    		ut = UserType.ATTENDEE;
    	}
    	List<Boolean> result = new ArrayList<Boolean>();
    	result.add(this.getByUserTypeAndPermission(ut, Permission.valueOf(permission)).getCanUpdate());
    	return result;
    }

	@ApiMethod(name = "calendar.isCanDelete", path="calendar.isCanDelete", httpMethod = "get")
    public List<Boolean> allowDelete(
    		@Named("account") Long accountId, 
    		@Named("permission") String permission, 
    		User user)  {
		Account accountFetched = accountDao.retrieve(accountId);
		UserType ut = null;
		try {
			ut = this.getUserType(accountFetched, user);
    	} catch (Exception e) {
    		ut = UserType.ATTENDEE;
    	}
    	List<Boolean> result = new ArrayList<Boolean>();
    	result.add(this.getByUserTypeAndPermission(ut, Permission.valueOf(permission)).getCanDelete());
    	return result;
    }
	
}
