package com.studio.bookings.service;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
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
	
	@ApiMethod(name = "calendar.addAcl", path="calendar.addAcl", httpMethod = "post")
    public AccessControlList insertAccessControlList(
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
    		if (this.allowInsert(accountId, permission, user)) {

		    	Long oId = new Long(accountId);
				Account account =  accountDao.retrieve(oId);
				AccessControlList acl = new AccessControlList(
						permission, canView, canInsert, canUpdate, 
						canDelete, userType, account);
				aclDao.save(acl);
				return acl;
    		}
    		return null;
    	}
    	
    	else { return null; }
    }

    @ApiMethod(name = "calendar.findAcl", path="calendar.findAcl", httpMethod = "get")
	public AccessControlList findAcl(@Named("acl") Long aclId, @Named("account") Long accountId) {
		Account account = accountDao.retrieve(accountId);
		return aclDao.retrieveAncestor(aclId, account);
	}
	
    @ApiMethod(name = "calendar.updateAcl", path="calendar.updateAcl", httpMethod = "post")
	public AccessControlList updateAcl(
			@Named("acl") Long aclId,
			@Named("permission") String permission,
    		@Named("canView") String canView,
    		@Named("canInsert") String canInsert,
    		@Named("canUpdate") String canUpdate,
    		@Named("canDelete") String canDelete,
    		@Named("userType") String userType,
    		@Named("account") Long accountId) {
		Account accountFetched = accountDao.retrieve(accountId);
		AccessControlList aclFetched = aclDao.retrieveAncestor(aclId, accountFetched);
		aclFetched.setPermission(Permission.valueOf(permission));
		aclFetched.setCanView(Boolean.valueOf(canView));
		aclFetched.setCanInsert(Boolean.valueOf(canInsert));
		aclFetched.setCanUpdate(Boolean.valueOf(canUpdate));
		aclFetched.setCanDelete(Boolean.valueOf(canDelete));
		aclFetched.setUserType(UserType.valueOf(userType));
		aclFetched.setCanDelete(Boolean.valueOf(canDelete));
		aclDao.save(aclFetched);
		return aclFetched;
	}
	

	@ApiMethod(name = "calendar.deleteAcl", path="calendar.deleteAcl", httpMethod = "post")
	public void deleteAcl(
			@Named("aclList") List<Long> aclIds,
			@Named("account") Long accountId) {
		Account accountFetched = accountDao.retrieve(accountId); 
		aclDao.deleteAncestors(aclIds, accountFetched);
	}
    
    
	@ApiMethod(name = "calendar.listAcl", path="calendar.listAcl", httpMethod = "post")
    public List<AccessControlList> listAcl (@Named("account") Long accountId) {
		Account accountFetched = accountDao.retrieve(accountId);
		return aclDao.listAncestors(accountFetched);    	
    }

	private AccessControlList getByUserTypeAndPermission(UserType userType, Permission permission, Account accountFetched) {
		return aclDao.twoFilterAncestorQuery("userType", userType.toString(), "permission", permission.toString(), accountFetched);
    }
	
	private UserType getUserType(Account account, User user) {
    	return personDao.oneFilterAncestorQuery("userId", user.getUserId(), account).getUserType();
	}

	@ApiMethod(name = "calendar.isCanView", path="calendar.isCanView", httpMethod = "get")
    public Boolean allowView(@Named("account") Long accountId, @Named("permission") String permission, User user) {
    	Account accountFetched = accountDao.retrieve(accountId);
    	UserType ut = this.getUserType(accountFetched, user);
        return this.getByUserTypeAndPermission(ut, Permission.valueOf(permission), accountFetched).isCanView();
    }
	
	@ApiMethod(name = "calendar.isCanInsert", path="calendar.isCanInsert", httpMethod = "get")
    public Boolean allowInsert(@Named("account") Long accountId, @Named("permission") String permission, User user) {
    	Account accountFetched = accountDao.retrieve(accountId);
    	UserType ut = this.getUserType(accountFetched, user);
        return this.getByUserTypeAndPermission(ut, Permission.valueOf(permission), accountFetched).isCanInsert();
    }

	@ApiMethod(name = "calendar.isCanUpdate", path="calendar.isCanUpdate", httpMethod = "get")
    public Boolean allowUpdate(@Named("account") Long accountId, @Named("permission") String permission, User user)  {
    	Account accountFetched = accountDao.retrieve(accountId);
    	UserType ut = this.getUserType(accountFetched, user);
        return this.getByUserTypeAndPermission(ut, Permission.valueOf(permission), accountFetched).isCanUpdate();
    }

	@ApiMethod(name = "calendar.isCanDelete", path="calendar.isCanDelete", httpMethod = "get")
    public Boolean allowDelete(@Named("account") Long accountId, @Named("permission") String permission, User user)  {
    	Account accountFetched = accountDao.retrieve(accountId);
    	UserType ut = this.getUserType(accountFetched, user);
        return this.getByUserTypeAndPermission(ut, Permission.valueOf(permission), accountFetched).isCanDelete();
    }
	
}
