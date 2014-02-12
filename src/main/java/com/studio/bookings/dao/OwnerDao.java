package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Owner;

public class OwnerDao {

	static{
		ObjectifyService.register(Owner.class);
		
	}

	public Key<Owner> save(Owner owner) { 
		Key<Owner> ownerKey = ofy().save().entity(owner).now();
		return ownerKey;
	}
	
	public Owner getOwnerById(Long ownerId) throws NotFoundException {
		return ofy().load().type(Owner.class).id(ownerId).safe();
	}

	
	public Owner getOwnerByKey(Key<Owner> ownerKey) throws NotFoundException {
		Owner owner = ofy().load().key(ownerKey).safe();
		return owner;
	}
		
	public List<Owner> findAll() {
		List<Owner> owners = ofy().load().type(Owner.class).list();
		return owners;
	}

}