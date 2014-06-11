/*package com.studio.bookings.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.dao.EventAttributeDao;
import com.studio.bookings.dao.EventCategoryDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.dao.EventItemDao;
import com.studio.bookings.dao.InstructorDao;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.EventItem;
import com.studio.bookings.entity.EventItemDetails;
import com.studio.bookings.entity.Instructor;
import com.studio.bookings.entity.Person;
import com.studio.bookings.entity.UserSession;
import com.studio.bookings.enums.EventRepeatType;
import com.studio.bookings.util.Constants;
import com.studio.bookings.util.LoadDummyData;

*//**
 * Defines v1 of a booking API
 *//*
@Api(
    name = "booking",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)

public class EventTestingService extends BaseService {
	
	public static ArrayList<Event> events = new ArrayList<Event>();
	public static EventDao eventDao = new EventDao();
	public static EventItemDao eventItemDao = new EventItemDao();
	public static EventAttributeDao eventAttributeDao = new EventAttributeDao();
	public static EventCategoryDao eventCategoryDao = new EventCategoryDao();
	public static InstructorDao instructorDao = new InstructorDao();
	
	BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	ChildBaseDao<Calendar, Account> calendarDao = new ChildBaseDao<Calendar, Account>(Calendar.class, Account.class);

	
	
	
	
	
	
	@ApiMethod(name = "calendar.addInstructor", path="calendar.addInstructor", httpMethod = "post")
	public Instructor addInstructor( 
			@Named("name") String name,  
			@Named("lastname") String lastname,
			@Named("description") String description,
			@Named("Calendar") Long calendarId,
			@Named("Account") Long accountId)  throws Exception {
		
		Account account = accountDao.retrieve(accountId);
		
		Instructor instructor = new Instructor(name, lastname, description);
		instructorDao.save(instructor);
	    return instructor;
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
			
			Account accountAccount = accountDao.retrieve(account);
			EventCategory ec = new EventCategory(name, accountAccount);
			Key<EventCategory> ecKey = eventCategoryDao.save(ec);
			EventCategory eventCategory = eventCategoryDao.getEventCategory(ecKey);
			return eventCategory;
	}
	
	@ApiMethod(name = "calendar.addEventAttribute", path="calendar.addEventAttribute", httpMethod = "post")
	public EventAttribute addEventAttribute( 
			@Named("name") String name,  
			@Named("calendar") Long calendarId,
			@Named("account") Long accountId)  throws Exception {
			
			Calendar cal = findCalendar(calendarId, accountId);
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
		Account accountAccount = accountDao.retrieve(accountId);
		
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
			//Settings settingsKeyFetched = accountAccount.getSettings();
			//eventFinalDateTime = settingsKeyFetched.getRepeatEventFinalDate();
		}
		
		
		// retrieving Calendar, Event Category and Event Attribute, Event Repeat Type
		try {
			Long calId1 = new Long(calendarId);
			Calendar calendar =  findCalendar(calId1, accountId);
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
		Account accountAccount = accountDao.retrieve(accountId);
		
		// Get Calendar
		Calendar cal = findCalendar(calendarId, accountId);
		
		List<EventItem> events = eventItemDao.findEventItemsByCalendar(cal);
		return events;
	}
	
	@ApiMethod(name = "calendar.listEventAttributes", path="calendar.listEventAttributes", httpMethod = "get")
	public List<EventAttribute> listEventAttributes(
			@Named("account") Long accountId,
			@Named("calendarId") Long calendarId) {
		
		// Get Account
		Account accountAccount = accountDao.retrieve(accountId);
		
		// Get Calendar
		Calendar cal = findCalendar(calendarId, accountId);
		
		List<EventAttribute> eventAttributes = eventAttributeDao.findEventAttributesByCalendar(cal);
	    return eventAttributes;
	}
	
	@ApiMethod(name = "calendar.listEventCategories", path="calendar.listEventCategories", httpMethod = "get")
	public List<EventCategory> listEventCategories(
			@Named("account") Long accountId,
			@Named("calendarId") Long calendarId) {
		
		// Get Account
		Account accountAccount = accountDao.retrieve(accountId);
		
		// Get Calendar
		Calendar cal = findCalendar(calendarId, accountId);
		
		List<EventCategory> eventCategories = eventCategoryDao.findEventCategorysByCalendar(cal);
	    return eventCategories;
	}
	
	@ApiMethod(name = "calendar.listEventRepeatTypes", path="calendar.listEventRepeatTypes", httpMethod = "get")
	public List<EventRepeatType> listEventRepeatTypes() {
		return Arrays.asList(EventRepeatType.values());
	}
	
	
	
	

	
	//TODO return permission role
	// to test var message = {'email' : 'admin', 'password': 'password'}; console.log(message); 
	// gapi.client.booking.calendar.authUserSession(message).execute(function(resp) { console.log(resp);});
	
	@ApiMethod(name = "calendar.authUserSession", path="calendar.authUserSession", httpMethod = "post")
	public UserSession authUserSession(@Named("username") String username, @Named("password") String password, HttpServletRequest request) {
		
		if (userDao.list().size() == 0) {
    		LoadDummyData ldd = new LoadDummyData();
    		ldd.initSetup();
    	}
		
		User user = userDao.getByUsernamePassword(username, password);
		UserSession userSession;
		
		if(user != null) {
			userSession = new UserSession(new Date(), user);
            request.getSession(true).setAttribute("userSession", userSession);
        } else {
            return null;
        }
	    return userSession;
	}
}
*/