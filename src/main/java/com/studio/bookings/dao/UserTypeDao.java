package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.UserType;

public class UserTypeDao {
	
	static{
		ObjectifyService.register(UserType.class);
	}
	
	public UserType find(Long userId) {
		UserType userType =  new UserType();
		Key<UserType> rootKey = Key.create(UserType.class, userId);
		userType = ofy().load().key(rootKey).now();
		return userType;
	}

	public UserType save(UserType userType) {
		ofy().save().entity(userType).now();
		return userType;
	}

	public List<UserType> findAll() {
		List<UserType> userType = ofy().load().type(UserType.class).list();
		return userType;
	}

}
