package com.studio.bookings.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Ref;
import com.studio.bookings.dao.AccountDao;
import com.studio.bookings.dao.CalendarDao;
import com.studio.bookings.dao.EventAttributeDao;
import com.studio.bookings.dao.EventCategoryDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.dao.EventItemDao;
import com.studio.bookings.dao.InstructorDao;
import com.studio.bookings.dao.SettingsDao;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.EventItem;
import com.studio.bookings.entity.EventItemDetails;
import com.studio.bookings.entity.EventRepeatType;
import com.studio.bookings.entity.Instructor;
import com.studio.bookings.entity.Settings;
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

public class EventTestingService {
	
	public static ArrayList<Event> events = new ArrayList<Event>();
	public static EventDao eventDao = new EventDao();
	public static EventItemDao eventItemDao = new EventItemDao();
	public static CalendarDao calendarDao = new CalendarDao();
	public static EventAttributeDao eventAttributeDao = new EventAttributeDao();
	public static EventCategoryDao eventCategoryDao = new EventCategoryDao();
	public static InstructorDao instructorDao = new InstructorDao();
	public static AccountDao accountDao = new AccountDao();
	public static SettingsDao settingsDao = new SettingsDao();

	@ApiMethod(name = "calendar.addAccount", path="calendar.addAccount", httpMethod = "post")
	public Account insertAccount(@Named("account") String accountName) {
		Account account = new Account(accountName);
		Key<Account> accountKey = accountDao.save(account);
		Settings setting = new Settings(accountKey);
		settingsDao.save(setting);
		return account;
	}
	
	@ApiMethod(name = "calendar.getAccountById", path="bookings.getAccountById", httpMethod = "get")
	public Account getAccountById(@Named("accountId") Long accountId) {
		return accountDao.getAccountById(accountId);
	}
	
	@ApiMethod(name = "calendar.listAccounts", path="calendar.listAccounts", httpMethod = "get")
	public List<Account> listAccounts() {
		return accountDao.findAll();
	}
	
	@ApiMethod(name = "calendar.addCalendar", path="calendar.addCalendar", httpMethod = "post")
	public Calendar insertCalendar( 
			@Named("description") String description,  
			@Named("account") Long AccountId)  throws Exception {
		Long oId = new Long(AccountId);
		Account account =  accountDao.getAccountById(oId);
		Calendar cal = new Calendar(description, account);
		Key<Calendar> calKey = calendarDao.save(cal);
	    return cal; 
	}
	
	@ApiMethod(name = "calendar.listCalendars", path="calendar.listCalendars", httpMethod = "get")
	public List<Calendar> listCalendars(
			@Named("Account") Long AccountId
			) {
		Long oId = new Long(AccountId);
		Account Account = getAccountById(oId);
		return calendarDao.getCalendarsByAccount(Account);
	}
	
	@ApiMethod(name = "calendar.addInstructor", path="calendar.addInstructor", httpMethod = "post")
	public Instructor addInstructor( 
			@Named("name") String name,  
			@Named("email") String email,
			@Named("description") String description,
			@Named("Account") Long AccountId)  throws Exception {
		
		Instructor instructor = new Instructor(name, email, description);
		Key<Instructor> instructorKey = instructorDao.save(instructor);
		
		Instructor fetchedInstructor = instructorDao.getInstructor(instructorKey);
		
		Account Account = getAccountById(AccountId);
		Account.addInstructor(Ref.create(instructorKey));
		
	    return fetchedInstructor;
	}
	
	@ApiMethod(name = "calendar.listInstructors", path="calendar.listInstructors", httpMethod = "get")
	public List<Instructor> listInstructors() {
		return instructorDao.findAll();
	}

	@ApiMethod(name = "calendar.addEventCategory", path="calendar.addEventCategory", httpMethod = "post")
	public EventCategory addEventCategory( 
			@Named("name") String name,  
			@Named("calendar") Long calendarId,
			@Named("account") Long account)  throws Exception {
			
			Account accountAccount = accountDao.getAccountById(account);
			Calendar cal = calendarDao.getCalendarById(calendarId, accountAccount);
			EventCategory ec = new EventCategory(name, cal);
			Key<EventCategory> ecKey = eventCategoryDao.save(ec);
			EventCategory eventCategory = eventCategoryDao.getEventCategory(ecKey);
			return eventCategory;
	}
	
	@ApiMethod(name = "calendar.addEventAttribute", path="calendar.addEventAttribute", httpMethod = "post")
	public EventAttribute addEventAttribute( 
			@Named("name") String name,  
			@Named("calendar") Long calendarId,
			@Named("account") Long account)  throws Exception {
			
			Account accountAccount = accountDao.getAccountById(account);
			Calendar cal = calendarDao.getCalendarById(calendarId, accountAccount);
			EventAttribute ea = new EventAttribute(name, cal);
			Key<EventAttribute> eaKey = eventAttributeDao.save(ea);
			EventAttribute eventAttribute = eventAttributeDao.getEventAttribute(eaKey);
			return eventAttribute;
	}
	
	@ApiMethod(name = "calendar.addEvent", path="calendar.addEvent", httpMethod = "post")
	public List<EventItem> addEvent(
			@Named("account") Long accountId,
			@Named("instructorId") Long instructorId, 
			@Named("summary") String summary, 
			@Named("calendarId") Long calendarId,
			@Named("startDateTime") String startDateTime,
			@Named("startDate") Long startDate,
			@Named("duration") String duration,
			@Named("maxAttendees") String maxAttendees,
			@Named("eventCategory") String eventCategory,
			@Named("eventAttribute") String eventAttribute,
			@Named("repeatEvent") String repeatEvent,
			@Named("eventRepeatType") String eventRepeatType,
			@Named("finalRepeatEvent") String finalRepeatEvent
			) throws ParseException, NotFoundException {
		
		
		List<String> errorMessage = new ArrayList<String>();
		List<EventItem> eventItems = new ArrayList<EventItem>();
		
		//summary
		String eventSummary = new String(summary);
		
		Long eventStartDate = new Long(startDate);
		String eventDuration = new String(duration);
		Integer eventMaxAttendees = new Integer(maxAttendees);
		
		// Get Account
		Account accountAccount = accountDao.getAccountById(accountId);
		Calendar cal = calendarDao.getCalendarById(calendarId, accountAccount);
		
		//Format Dates
		DateFormat formatter = new SimpleDateFormat("HH:mm dd MM yyyy");
		Date eventStart = new Date();
		
		String eventStartDateTime = new String(startDateTime);
		try {
			eventStart = new DateTime(formatter.parse(eventStartDateTime)).toDate();
		} catch (ParseException e) {
			errorMessage.add("Start Date format not recognised");
		}
		
		// TODO create utils method for Set Repeat Final Date 
		Date eventFinalDateTime = null;
		try {
			eventFinalDateTime = new DateTime(formatter.parse(finalRepeatEvent)).toDate();
		} catch (ParseException e) {
			Settings settingsKeyFetched = settingsDao.findSettingsByAccount(accountAccount);
			eventFinalDateTime = settingsKeyFetched.getRepeatEventFinalDate();
		}
		
		
		// retrieving Calendar, Event Category and Event Attribute, Event Repeat Type
		try {
			Long calId1 = new Long(calendarId);
			Calendar calendar =  calendarDao.getCalendarById(calId1, accountAccount);
			Boolean repeatBoolean = new Boolean(false);
			EventRepeatType repeatType = null;
			
			if (eventRepeatType.length() > 0) {
				try {
					repeatType = EventRepeatType.valueOf(eventRepeatType);
					repeatBoolean = new Boolean(repeatEvent);
				} catch (IllegalArgumentException e) {
					errorMessage.add("Repeat Event period not recognised");
				}
			}
			
			try {
				Event event = new Event(calendar, repeatBoolean, repeatType);
				Key<Event> eventKey = eventDao.save(event);
		
				EventItemDetails eventItemDetails = new EventItemDetails(eventSummary, eventDuration, eventMaxAttendees);

				if (!instructorId.equals("")) {
					try {
						
						Instructor instructorFetched =  instructorDao.find(instructorId, calendar);
						eventItemDetails.setInstructor(instructorFetched);
					} catch (NumberFormatException e) {
						errorMessage.add("Instructor not recognised");
					} catch (NotFoundException e) {
						errorMessage.add("Instructor not found");
					}
				}
				
				if (!eventCategory.equals("")) {
					try {
						
						EventCategory eventCategoryFetched =  eventCategoryDao.findByName(eventCategory, calendar);
						eventItemDetails.setEventCategory(eventCategoryFetched);
					} catch (NumberFormatException e) {
						errorMessage.add("Event Category not recognised");
					} catch (NotFoundException e) {
						errorMessage.add("Event Category not found");
					}
				}
				if (!eventAttribute.equals("")) {
					try {
						EventAttribute eventAttributeFetched =  eventAttributeDao.findByName(eventAttribute, calendar);
						eventItemDetails.setEventAttribute(eventAttributeFetched);
					} catch (NumberFormatException e) {
						errorMessage.add("Event Attribute not recognised");
					} catch (NotFoundException e) {
						errorMessage.add("Event Attribute not found");
					}
				}
				
				eventItems = event.createRepeatEventItems(eventStart, eventStartDate, eventKey, repeatType, 
			    		eventItemDetails, repeatBoolean, eventFinalDateTime);
			    
				Map<Key<EventItem>, EventItem> eventMap = eventItemDao.saveCollection(eventItems);
			    
			    List<Key<EventItem>> eventKeys = new ArrayList<Key<EventItem>>(eventMap.keySet());
			    
			    if(eventKeys.size() == eventItems.size()) {
			    	errorMessage.add("Event saved successfully");
			    } else {
			    	errorMessage.add("Event not saved please try again");
			    }
				 
			} catch (NotFoundException e) {
				errorMessage.add("Event not saved please try again");

			}
		    
		} catch (NumberFormatException e) {
			errorMessage.add("Calendar not recognised");
		} catch (NotFoundException e) {
			errorMessage.add("Calendar not found");
		}
		
		return eventItems;
	}
 
	@ApiMethod(name = "calendar.listEvents", path="calendar.listEvents", httpMethod = "get")
	public List<EventItem> listEvents(
			@Named("account") Long accountId,
			@Named("calendarId") Long calendarId) {
		
		Long calId = new Long(calendarId);
		
		// Get Account
		Account accountAccount = accountDao.getAccountById(accountId);
		
		// Get Calendar
		Calendar cal = calendarDao.getCalendarById(calendarId, accountAccount);
		
		List<EventItem> events = eventItemDao.findEventItemsByCalendar(cal);
		return events;
	}
	
	@ApiMethod(name = "calendar.listEventAttributes", path="calendar.listEventAttributes", httpMethod = "get")
	public List<EventAttribute> listEventAttributes(
			@Named("account") Long accountId,
			@Named("calendarId") Long calendarId) {
		
		// Get Account
		Account accountAccount = accountDao.getAccountById(accountId);
		
		// Get Calendar
		Calendar cal = calendarDao.getCalendarById(calendarId, accountAccount);
		
		List<EventAttribute> eventAttributes = eventAttributeDao.findEventAttributesByCalendar(cal);
	    return eventAttributes;
	}
	
	@ApiMethod(name = "calendar.listEventCategories", path="calendar.listEventCategories", httpMethod = "get")
	public List<EventCategory> listEventCategories(
			@Named("account") Long accountId,
			@Named("calendarId") Long calendarId) {
		
		// Get Account
		Account accountAccount = accountDao.getAccountById(accountId);
		
		// Get Calendar
		Calendar cal = calendarDao.getCalendarById(calendarId, accountAccount);
		
		List<EventCategory> eventCategories = eventCategoryDao.findEventCategorysByCalendar(cal);
	    return eventCategories;
	}
	
	@ApiMethod(name = "calendar.listEventRepeatTypes", path="calendar.listEventRepeatTypes", httpMethod = "get")
	public List<EventRepeatType> listEventRepeatTypes() {
		return Arrays.asList(EventRepeatType.values());
	}

}
