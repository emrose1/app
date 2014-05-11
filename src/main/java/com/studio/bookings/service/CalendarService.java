package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.Constants;

@Api(
	    name = "booking",
	    version = "v1",
	    scopes = {Constants.EMAIL_SCOPE},
	    clientIds = {Constants.WEB_CLIENT_ID},
	    audiences = {Constants.ANDROID_AUDIENCE}
	)

public class CalendarService extends BaseService {
	public static AccessControlListService aclService = new AccessControlListService();
	Permission aclPermission = Permission.CALENDAR;
	
	@ApiMethod(name = "calendar.addCalendar", path="calendar.addCalendar", httpMethod = "post")
	public Calendar insertCalendar( 
		@Named("description") String description,  
		@Named("account") Long accountId,
		User user) {
		
		Calendar calendar = null;
		Long oId = new Long(accountId);
		if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (aclService.allowInsert(accountId, aclPermission.toString(), user).get(0)) {
				Account account =  accountDao.retrieve(oId);
				calendar = new Calendar(description, account);
				calendarDao.save(calendar);
    		}
		}
	    return calendar; 
	}
	
	@ApiMethod(name = "calendar.findCalendar", path="calendar.findCalendar", httpMethod = "get")
	public Calendar findCalendar(
		@Named("calendar") Long calendarId, 
		@Named("account") Long accountId,
		User user) {

		Calendar calendar = null;
		if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (aclService.allowView(accountId, aclPermission.toString(), user).get(0)) {
    			Account account = accountDao.retrieve(accountId);
    			calendar = calendarDao.retrieveAncestor(calendarId, account);
    		}
		}
		return calendar;
	}
	
	@ApiMethod(name = "calendar.listCalendars", path="calendar.listCalendars", httpMethod = "get")
	public List<Calendar> listCalendars(
			@Named("account") Long accountId,
			User user) {
		
		List<Calendar> calendarList = null;
		if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		if (aclService.allowViewAll(accountId, aclPermission.toString(), user).get(0)) {
    			Account accountFetched = accountDao.retrieve(accountId);
    			calendarList = calendarDao.listAncestors(accountFetched);
    		}
		}
		return calendarList;
	}
	
	@ApiMethod(name = "calendar.updateCalendar", path="calendar.updateCalendar", httpMethod = "post")
	public Calendar updateCalendar(@Named("calendar") Long calendarId,  @Named("account") Long accountId, 
			@Named("description") String description, User user) {
		Calendar calendar = null;
		if(user != null) {	
			if (aclService.allowUpdate(accountId, aclPermission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(accountId);
				calendar = calendarDao.retrieveAncestor(calendarId, accountFetched);
				calendar.setDescription(description);
				calendarDao.save(calendar);
			}
		}
		return calendar;
	}
	
	@ApiMethod(name = "calendar.deleteCalendars", path="calendar.deleteCalendars", httpMethod = "post")
	public void deleteCalendars(
			@Named("calendar") List<Long> calendarIds,
			@Named("account") Long accountId,
			User user) {
		
		if(user != null) {	
			if (aclService.allowDelete(accountId, aclPermission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(accountId);
				calendarDao.deleteAncestors(calendarIds, accountFetched);
			}
		}
	}

}
