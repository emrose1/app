package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.User;

public class UserDao {
	
	static{
		ObjectifyService.register(User.class);
	}
	
	public User find(Long userId) {
		User user =  new User();
		Key<User> rootKey = Key.create(User.class, userId);
		user = ofy().load().key(rootKey).now();
		return user;
	}

	public User save(User user) {
		ofy().save().entity(user).now();
		return user;
	}

	public List<User> findAll() {
		List<User> user = ofy().load().type(User.class).list();
		return user;
	}

}
