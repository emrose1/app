package com.studio.bookings.service;

import static com.studio.bookings.util.OfyService.ofy;

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
import com.studio.bookings.dao.CalendarDao;
import com.studio.bookings.dao.EventAttributeDao;
import com.studio.bookings.dao.EventCategoryDao;
import com.studio.bookings.dao.EventDao;
import com.studio.bookings.dao.EventItemDao;
import com.studio.bookings.dao.OwnerDao;
import com.studio.bookings.dao.SettingsDao;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.EventItem;
import com.studio.bookings.entity.EventItemDetails;
import com.studio.bookings.entity.EventRepeatType;
import com.studio.bookings.entity.Owner;
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
	public static OwnerDao ownerDao = new OwnerDao();
	public static SettingsDao settingsDao = new SettingsDao();

	@ApiMethod(name = "bookings.addAccount", path="bookings.addAccount", httpMethod = "post")
	public Owner createOwner(@Named("owner") String ownerName) {
		Owner owner = new Owner(ownerName);
		Key<Owner> ownerKey = ownerDao.save(owner);
		Settings setting = new Settings(ownerKey);
		settingsDao.save(setting);
		return owner;
	}
	
	@ApiMethod(name = "bookings.getAccountById", path="bookings.getAccountById", httpMethod = "get")
	public Owner getOwnerById(@Named("owner") Long ownerId) {
		Owner ownerFetched;
		try {
			ownerFetched = ownerDao.getOwnerById(ownerId);
		} catch (NotFoundException e) {
			throw e;
		}
		return ownerFetched;
	}
	
	@ApiMethod(name = "calendar.addCalendar", path="calendar.addCalendar", httpMethod = "post")
	public Calendar addCalendar( 
			@Named("description") String description,  
			@Named("owner") Long ownerId)  throws Exception {
		Calendar cal = new Calendar(description);
		Key<Calendar> calKey = calendarDao.save(cal);
		Calendar fetchedCal = calendarDao.getCalendarByKey(calKey);
		Owner owner = getOwnerById(ownerId);
		owner.addCalendar(Ref.create(calKey));
	    return fetchedCal; 
	}
	
	@ApiMethod(name = "calendar.listCalendars", path="calendar.listCalendars", httpMethod = "get")
	public List<Calendar> listCalendars() {
		
		// Get Owner
		/*Owner owner = ofy().load().type(Owner.class).first().now();
	
		Long oId = new Long(owner.getId());
		owner = getOwnerById(oId);*/
		return calendarDao.findAll();
	}
	
	@ApiMethod(name = "calendar.listOwners", path="calendar.listOwners", httpMethod = "get")
	public List<Owner> listOwners() {
		
		// Get Owner
		/*Owner owner = ofy().load().type(Owner.class).first().now();
	
		Long oId = new Long(owner.getId());
		owner = getOwnerById(oId);*/
		return ownerDao.findAll();
	}
	

	@ApiMethod(name = "calendar.addEventCategory", path="calendar.addEventCategory", httpMethod = "post")
	public EventCategory addEventCategory( 
			@Named("name") String name,  
			@Named("calendar") Long calendarId)  throws Exception {
		
			Calendar cal = calendarDao.getCalendarById(calendarId);
			EventCategory ec = new EventCategory(name, cal);
			Key<EventCategory> ecKey = eventCategoryDao.save(ec);
			EventCategory eventCategory = eventCategoryDao.getEventCategory(ecKey);
			return eventCategory;
	}
	
	@ApiMethod(name = "calendar.addEventAttribute", path="calendar.addEventAttribute", httpMethod = "post")
	public EventAttribute addEventAttribute( 
			@Named("name") String name,  
			@Named("calendar") Long calendarId)  throws Exception {
		
			Calendar cal = calendarDao.getCalendarById(calendarId);
			EventAttribute ea = new EventAttribute(name, cal);
			Key<EventAttribute> eaKey = eventAttributeDao.save(ea);
			EventAttribute eventAttribute = eventAttributeDao.getEventAttribute(eaKey);
			return eventAttribute;
	}
	
	@ApiMethod(name = "calendar.addEvent", path="calendar.addEvent", httpMethod = "post")
	public List<EventItem> addEvent(
			@Named("organizer") String organizer, 
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
		
		//organizer
		String eventOrganizer = new String(organizer);
		
		//summary
		String eventSummary = new String(summary);
		
		Long eventStartDate = new Long(startDate);
		String eventDuration = new String(duration);
		Integer eventMaxAttendees = new Integer(maxAttendees);
		
		// Get Owner
		Owner owner = ofy().load().type(Owner.class).first().now();
		
			Long oId = new Long(owner.getId());
			owner = getOwnerById(oId);
		
		
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
			Settings settingsKeyFetched = settingsDao.findSettingsByOwner(owner);
			eventFinalDateTime = settingsKeyFetched.getRepeatEventFinalDate();
			errorMessage.add("got final end date from settings: " + eventFinalDateTime);
		}
		
		
		// retrieving Calendar, Event Category and Event Attribute, Event Repeat Type
		try {
			Long calId1 = new Long(calendarId);
			Calendar calendar =  calendarDao.getCalendarById(calId1);
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
		
				EventItemDetails eventItemDetails = new EventItemDetails(eventOrganizer, eventSummary, eventDuration, eventMaxAttendees);

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
	public List<EventItem> listEvents(@Named("calendarId") Long calendarId) {
		Long calId = new Long(calendarId);
		Calendar cal =  calendarDao.getCalendarById(calId);
		List<EventItem> events = eventItemDao.findEventItemsByCalendar(cal);
		return events;
	}
	
	@ApiMethod(name = "calendar.listEventAttributes", path="calendar.listEventAttributes", httpMethod = "get")
	public List<EventAttribute> listEventAttributes(@Named("calendarId") Long calendarId) {
		Long calId = new Long(calendarId);
		Calendar cal =  calendarDao.getCalendarById(calId);
		List<EventAttribute> eventAttributes = eventAttributeDao.findEventAttributesByCalendar(cal);
	    return eventAttributes;
	}
	
	@ApiMethod(name = "calendar.listEventCategories", path="calendar.listEventCategories", httpMethod = "get")
	public List<EventCategory> listEventCategories(@Named("calendarId") Long calendarId) {
		Long l = new Long(calendarId);
		Calendar cal =  calendarDao.getCalendarById(l);
		List<EventCategory> eventCategories = eventCategoryDao.findEventCategorysByCalendar(cal);
	    return eventCategories;
	}
	
	@ApiMethod(name = "calendar.listEventRepeatTypes", path="calendar.listEventRepeatTypes", httpMethod = "get")
	public List<EventRepeatType> listEventRepeatTypes() {
		return Arrays.asList(EventRepeatType.values());
	}

}
