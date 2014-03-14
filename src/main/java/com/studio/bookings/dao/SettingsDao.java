package com.studio.bookings.dao;

import static com.studio.bookings.util.OfyService.ofy;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Settings;

public class SettingsDao {
	
	public Settings getSettings(Key<Settings> settingsKey) throws NotFoundException {
		return ofy().load().key(settingsKey).safe();
	}
	
	public Settings find(Long settingsId, Account account) throws NotFoundException {
		return ofy().load().type(Settings.class).parent(account).id(settingsId).safe();
	}
	
	public Key<Settings> save(Settings settings) { 
		Key<Settings> settingsKey = ofy().save().entity(settings).now();
		return settingsKey;
	}
	
	public Settings findSettingsByAccount(Account account) {
		return ofy().load().type(Settings.class).ancestor(account.getKey()).first().safe();
	}

}
