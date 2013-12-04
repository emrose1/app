package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.UserType;
import com.studio.bookings.enums.Permission;

public class AccessControlListDao {
	static{
		ObjectifyService.register(AccessControlList.class);
	}
	

	public AccessControlList find(Long aclId) {
		AccessControlList acl =  new AccessControlList();
		Key<AccessControlList> rootKey = Key.create(AccessControlList.class,aclId);
		acl = ofy().load().key(rootKey).now();
		return acl;
	}

	public AccessControlList save(AccessControlList acl) {
		ofy().save().entity(acl).now();
		return acl;
	}
	
	public List<AccessControlList> findAll() {
		List<AccessControlList> acl = ofy().load().type(AccessControlList.class).order("permission").list();
		return acl;
	}
	
	public AccessControlList getByUserTypeAndPermission(UserType userType, Permission permission) throws Exception {
		AccessControlList acl = ofy().load().type(AccessControlList.class).ancestor(userType.getKey()).filter("permission", permission).first().now();
        return acl;
    }

}
