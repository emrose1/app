package com.studio.bookings.service;

import java.util.ArrayList;

import com.google.api.server.spi.config.Api;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.dao.EventAttributeDao;
import com.studio.bookings.dao.EventCategoryDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.dao.EventItemDao;
import com.studio.bookings.dao.InstructorDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
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
	public static EventDao eventDao = new EventDao();
	public static EventItemDao eventItemDao = new EventItemDao();
	public static EventAttributeDao eventAttributeDao = new EventAttributeDao();
	public static EventCategoryDao eventCategoryDao = new EventCategoryDao();
	public static InstructorDao instructorDao = new InstructorDao();

	public static BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	public static BaseDao<AccessControlList> aclDao = new BaseDao<AccessControlList>(AccessControlList.class);

	public static ChildBaseDao<Calendar, Account> calendarDao = new ChildBaseDao<Calendar, Account>(Calendar.class, Account.class);
	public static ChildBaseDao<Person, Account> personDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	
}
