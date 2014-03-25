package com.studio.bookings.service;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.util.Constants;

@Api(
	    name = "booking",
	    version = "v1",
	    scopes = {Constants.EMAIL_SCOPE},
	    clientIds = {Constants.WEB_CLIENT_ID},
	    audiences = {Constants.ANDROID_AUDIENCE}
	)

public class CalendarService extends BaseService {
	
	@ApiMethod(name = "calendar.addCalendar", path="calendar.addCalendar", httpMethod = "post")
	public Calendar insertCalendar( 
			@Named("description") String description,  
			@Named("account") Long accountId) {
		Long oId = new Long(accountId);
		Account account =  accountDao.retrieve(oId);
		Calendar calendar = new Calendar(description, account);
		calendarDao.save(calendar);
	    return calendar; 
	}
	
	@ApiMethod(name = "calendar.findCalendar", path="calendar.Calendar", httpMethod = "post")
	public Calendar findCalendar(@Named("calendar") Long calendarId, @Named("account") Long accountId) {
		Account account = accountDao.retrieve(accountId);
		return calendarDao.retrieveAncestor(calendarId, account);
	}
	
	@ApiMethod(name = "calendar.listCalendars", path="calendar.listCalendars", httpMethod = "get")
	public List<Calendar> listCalendars(
			@Named("Account") Long AccountId
			) {
		Long oId = new Long(AccountId);
		Account account = accountDao.retrieve(oId);
		return calendarDao.listAncestors(account.getKey());
	}
}
