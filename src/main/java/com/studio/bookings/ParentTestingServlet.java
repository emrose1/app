package com.studio.bookings;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ParentTestingServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PrintWriter rw = resp.getWriter();
		resp.setContentType("text/plain");
		
		/*EventDao eventDao = new EventDao();
		EventInstanceDao eventInstanceDao = new EventInstanceDao();
		EventAttributeDao eventAttributeDao = new EventAttributeDao();
		EventCategoryDao eventCategoryDao = new EventCategoryDao();
		CalendarDao calDao = new CalendarDao();
		UserDao userDao = new UserDao();
		UserTypeDao userTypeDao = new UserTypeDao();
		AccessControlListDao accessControlListDao = new AccessControlListDao();
		BookingDao bookingDao = new BookingDao();
		OwnerDao ownerDao = new OwnerDao();
		SettingsDao settingsDao = new SettingsDao();
		
		// TODO Create new owner and Settings Service
		Settings setting = new Settings();
		Key<Settings> settingsKey = settingsDao.save(setting);
		Owner owner = new Owner("Big C's", settingsKey);
		Key<Owner> ownerKey = ownerDao.save(owner);
		Owner ownerFetched = ownerDao.find(ownerKey);
		
		Settings settingsFetched =  ofy().load().key(settingsKey).now();
		Key<Settings> ownerSettingsKeyFetched = owner.getSettingsKey();
		
		rw.println(ownerSettingsKeyFetched.equals(settingsFetched.getKey()));
		
		rw.println("===================================");
		rw.println("Setting Final Repeat Event Date");
		rw.println("===================================");
		rw.println(setting.getRepeatEventFinalDate());
		
		rw.println("===================================");
		
		rw.println("===================================");
		
		Calendar calendar1 = new Calendar("calendar1", ownerFetched);
		Key<Calendar> calendarKey = ofy().save().entity(calendar1).now();
		
		Calendar calendar2 = new Calendar("calendar2", ownerFetched);
		Key<Calendar> calendarKey2 = ofy().save().entity(calendar2).now();
		
		EventCategory ec1 = new EventCategory("Pilates Matwork", calendar1);
		eventCategoryDao.save(ec1); 
		
		EventAttribute ea1 = new EventAttribute("Beginners", calendar1);
		eventAttributeDao.save(ea1); 
		
		
		EventRepeatType repeatDaily  = EventRepeatType.DAILY;
		EventRepeatType repeatWeekly  = EventRepeatType.WEEKLY;
		EventRepeatType repeatMonthly  = EventRepeatType.MONTHLY;
		
		Boolean btrue = new Boolean("true");
		
		Event parent = new Event(calendar1, btrue, repeatDaily);
		//parent.setSummary("parent_summary");
        Key<Event> eventKey = ofy().save().entity(parent).now();
        
        Event parent2 = new Event(calendar2, btrue, repeatDaily);
        Key<Event> eventKey2 = ofy().save().entity(parent2).now();
        
        Event parent3 = new Event(calendar2, btrue, repeatDaily);
        Key<Event> eventKey3 = ofy().save().entity(parent3).now();
        
		//dt = dt.plus(Period.months(1));
		//Date dt2 = dt.plus(Period.months(2)).toDate();
		//Date dt3 = dt.plus(Period.months(3)).toDate();
		
		// Create Repeat Event - repeat with no repeat end date (in which case the end date is final repeat date in settings)
		
		
		// add year to settings final repeat date, update all outstanding repeat events with future instances
		
		
		// Create Repeat Event with specified final repeat date
		
		
		
		// Event Instance Setup for Date Range Query
		
        DateTime apt1 = new DateTime(2014, 01, 31, 11, 45);
        DateTime apt2 = new DateTime(2014, 02, 03, 11, 45);
        DateTime apt3 = new DateTime(2014, 01, 31, 11, 45);
        DateTime apt4 = new DateTime(2014, 02, 20, 11, 45);
        DateTime apt5 = new DateTime(2014, 02, 21, 11, 45);
        DateTime apt6 = new DateTime(2014, 02, 23, 11, 45);
        DateTime apt7 = new DateTime(2014, 02, 24, 11, 45);
        DateTime apt8 = new DateTime(2014, 02, 27, 11, 45);
        DateTime apt9 = new DateTime(2014, 03, 28, 19, 45);
        DateTime apt10 = new DateTime(2014, 03, 31, 11, 45);
        
        Date date = apt1.toDate();
		Long dateTime = date.getTime();	
		
		EventInstance ei1 = new EventInstance(apt1.toDate(), dateTime, eventKey);
		ei1.setSummary("child_summary");
		EventInstance ei2 = new EventInstance(apt2.toDate(), dateTime, eventKey);
		EventInstance ei3 = new EventInstance(apt3.toDate(), dateTime, eventKey2);
		EventInstance ei4 = new EventInstance(apt4.toDate(), dateTime, eventKey3);
		EventInstance ei5 = new EventInstance(apt5.toDate(), dateTime, eventKey);
		EventInstance ei6 = new EventInstance(apt6.toDate(), dateTime, eventKey);
		EventInstance ei7 = new EventInstance(apt7.toDate(), dateTime, eventKey2);
		EventInstance ei8 = new EventInstance(apt8.toDate(), dateTime, eventKey3);
		EventInstance ei9 = new EventInstance(apt9.toDate(), dateTime, eventKey);
		EventInstance ei10 = new EventInstance(apt10.toDate(), dateTime, eventKey);
		
		
        Key<EventInstance> eventInstanceKey = ofy().save().entity(ei1).now();
        Key<EventInstance> eventInstanceKey2 = ofy().save().entity(ei2).now();
        Key<EventInstance> eventInstanceKey3 = ofy().save().entity(ei3).now();
        Key<EventInstance> eventInstanceKey4 = ofy().save().entity(ei4).now();
        Key<EventInstance> eventInstanceKey5 = ofy().save().entity(ei5).now();
        Key<EventInstance> eventInstanceKey6 = ofy().save().entity(ei6).now();
        Key<EventInstance> eventInstanceKey7 = ofy().save().entity(ei7).now();
        Key<EventInstance> eventInstanceKey8 = ofy().save().entity(ei8).now();
        Key<EventInstance> eventInstanceKey9 = ofy().save().entity(ei9).now();
        Key<EventInstance> eventInstanceKey10 = ofy().save().entity(ei10).now();
        
        //EventInstance fetched = ofy().load().key(eventInstanceKey).now();
       
		//rw.println(fetched.getEventKey().equals(ei1.getEventKey()));
		//rw.println(fetched.getStartDate().equals(ei1.getStartDate()));

        
		
		// List Events By Calendar
		List<EventInstance> eventInstances = ofy().load().type(EventInstance.class).ancestor(calendar1).list();
			  
		rw.println("Event Instances from Calendar1 -  size:" + eventInstances.size());
		for (EventInstance eventInstance : eventInstances) {
			rw.println(eventInstance.toString());
		
		}
		
		eventInstances = ofy().load().type(EventInstance.class).ancestor(calendar2).list();
		  
		rw.println("Event Instances from Calendar2 -  size:" + eventInstances.size());
		for (EventInstance eventInstance : eventInstances) {
			rw.println(eventInstance.toString());
		}
		
		// Find Events in Calendar by Date Range
		Date begin = new Date();
		Date end = new Date();
		try{
			begin = new SimpleDateFormat("yyyy MM dd HH mm").parse("2014 02 01 10 28");
			end = new SimpleDateFormat("yyyy MM dd HH mm").parse("2014 02 28 18 30");
			rw.println(begin + " " + end);
		} catch (Exception e) {
			rw.println(e);
		}
		
		eventInstances = ofy().load().type(EventInstance.class).ancestor(calendar1)
				.filter("startDateTime >=", begin)
				.filter("startDateTime <", end).list();
		
		rw.println("Event Instances from Calendar1 after 1/2/2014 at 10:30 and before 28/2/2014 at 18:30   -  size:" + eventInstances.size());
		for (EventInstance eventInstance : eventInstances) {
			rw.println(eventInstance.toString());
		}
		
		eventInstances = ofy().load().type(EventInstance.class).ancestor(calendar2)
				.filter("startDateTime >=", begin)
				.filter("startDateTime <", end).list();
		
		rw.println("Event Instances from Calendar2 after 1/2/2014 at 10:30 and before 28/2/2014 at 18:30   -  size:" + eventInstances.size());
		for (EventInstance eventInstance : eventInstances) {
			rw.println(eventInstance.toString());
		}*/
	}
}
