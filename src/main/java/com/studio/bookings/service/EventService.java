package com.studio.bookings.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.EventItem;
import com.studio.bookings.entity.EventItemDetails;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.EventRepeatType;


public class EventService extends BaseService {
	
	public static CalendarService calendarService = new CalendarService();
	
/*	@ApiMethod(name = "calendar.addInstructor", path="calendar.addInstructor", httpMethod = "post")
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
	}*/

	
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
			@Named("eventCategory") Long eventCategory,
			@Named("eventAttribute") Long eventAttribute,
			@Named("repeatEvent") String repeatEvent,
			@Named("eventRepeatType") String eventRepeatType,
			@Named("finalRepeatEvent") String finalRepeatEvent
			) throws ParseException, NotFoundException {
		
		List<EventItem> eventItems = new ArrayList<EventItem>();
		
		//summary
		String eventSummary = new String(summary);
		Long eventStartDate = new Long(startDate);
		String eventDuration = new String(duration);
		Integer eventMaxAttendees = new Integer(maxAttendees);
		
		// Get Account
		Account account = accountDao.retrieve(accountId);
		
		//Format Dates
		DateFormat formatter = new SimpleDateFormat("HH:mm dd MM yyyy");
		
		Date eventStart = new DateTime(formatter.parse(startDateTime)).toDate();
		Date eventFinalDateTime = new DateTime(formatter.parse(finalRepeatEvent)).toDate();

		// retrieving Calendar, Event Category and Event Attribute, Event Repeat Type		
		Calendar calendar = calendarDao.retrieveAncestor(calendarId, account);
		Boolean repeatBoolean = new Boolean(false);
		EventRepeatType repeatType = null;
				
		if (eventRepeatType.length() > 0) {
			repeatType = EventRepeatType.valueOf(eventRepeatType);
			repeatBoolean = new Boolean(repeatEvent);
		}

		Event event = new Event(calendar, repeatBoolean, repeatType);
		Key eventKey = eventDao.saveWithKey(event);

		// Create Event Item Details
		EventItemDetails eventItemDetails = new EventItemDetails(eventSummary, eventDuration, eventMaxAttendees);
		
		// Set Instructor
		Person instructor = personDao.retrieveAncestor(instructorId, account);
		eventItemDetails.setInstructor(instructor);
		
		// Set Event Item Category
		EventCategory eventCategoryFetched =  eventCategoryDao.retrieveAncestor(eventCategory, account);
		eventItemDetails.setEventCategory(eventCategoryFetched);
					
		EventAttribute eventAttributeFetched =  eventAttributeDao.retrieveAncestor(eventAttribute, account);
		eventItemDetails.setEventAttribute(eventAttributeFetched);
	
		eventItems = event.createRepeatEventItems(eventStart, eventStartDate, eventKey, repeatType, 
	    		eventItemDetails, repeatBoolean, eventFinalDateTime);
	    
		Map<Key<EventItem>, EventItem> eventMap = eventItemDao.saveCollection(eventItems);
	    
	    List<Key<EventItem>> eventKeys = new ArrayList<Key<EventItem>>(eventMap.keySet());
			    
		return eventItems;
	}
 
	@ApiMethod(name = "calendar.listEvents", path="calendar.listEvents", httpMethod = "get")
	public List<EventItem> listEvents(
			@Named("account") Long accountId,
			@Named("calendarId") Long calendarId) {
		
		Long calId = new Long(calendarId);
		
		// Get Account
		Account account = accountDao.retrieve(accountId);
		
		// Get Calendar
		Calendar calendar = calendarDao.retrieveAncestor(calendarId, account);
		
		List<EventItem> events = eventItemDao.listAncestors(calendar);
		return events;
	}
	

}
