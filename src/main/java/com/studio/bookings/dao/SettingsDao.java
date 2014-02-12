package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.LoadResult;
import com.googlecode.objectify.NotFoundException;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.Owner;
import com.studio.bookings.entity.Settings;

public class SettingsDao {
	
	public Settings getSettings(Key<Settings> settingsKey) throws NotFoundException {
		return ofy().load().key(settingsKey).safe();
	}
	
	public Settings find(Long settingsId, Owner owner) throws NotFoundException {
		return ofy().load().type(Settings.class).parent(owner).id(settingsId).safe();
	}
	
	public Key<Settings> save(Settings settings) { 
		Key<Settings> settingsKey = ofy().save().entity(settings).now();
		return settingsKey;
	}
	
	public Settings findSettingsByOwner(Owner owner) {
		return ofy().load().type(Settings.class).ancestor(owner.getKey()).first().safe();
	}

}
