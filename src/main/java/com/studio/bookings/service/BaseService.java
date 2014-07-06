package com.studio.bookings.service;

import java.util.ArrayList;

import com.google.api.server.spi.config.Api;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Application;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.Person;
import com.studio.bookings.util.Constants;

/**
 * Defines v1 of a booking API
 */
@Api(
    name = "booking",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)

public class BaseService {
	
	public static ArrayList<Event> events = new ArrayList<Event>();
	
	public static BaseDao<Application> applicationDao = new BaseDao<Application>(Application.class);
	public static BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	public static BaseDao<AccessControlList> aclDao = new BaseDao<AccessControlList>(AccessControlList.class);
	public static ChildBaseDao<Calendar, Account> calendarDao = new ChildBaseDao<Calendar, Account>(Calendar.class, Account.class);
	
	public static ChildBaseDao<Event, Calendar> eventDao = 
			new ChildBaseDao<Event, Calendar>(Event.class, Calendar.class);
			
	public static ChildBaseDao<EventAttribute, Account> eventAttributeDao = 
			new ChildBaseDao<EventAttribute, Account>(EventAttribute.class, Account.class);
	
	public static ChildBaseDao<EventCategory, Account> eventCategoryDao = 
			new ChildBaseDao<EventCategory, Account>(EventCategory.class, Account.class);

	public static ChildBaseDao<Person, Account> personDao = 
			new ChildBaseDao<Person, Account>(Person.class, Account.class);
	
	public static ChildBaseDao<Person, Application> personAppDao = 
			new ChildBaseDao<Person, Application>(Person.class, Application.class);
	
}
