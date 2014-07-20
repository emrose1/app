package com.studio.bookings;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.dao.ChildBaseDao;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Application;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.EventAttribute;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.entity.EventItem;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.EventRepeatType;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.service.AccessControlListService;
import com.studio.bookings.service.AccountService;
import com.studio.bookings.service.CalendarService;
import com.studio.bookings.service.EventAttributeService;
import com.studio.bookings.service.EventCategoryService;
import com.studio.bookings.service.EventService;
import com.studio.bookings.service.PersonService;

/**
 * Servlet implementation class EventServiceTestingServlet
 */
@SuppressWarnings("serial")
public class EventServiceTestingServlet extends HttpServlet {
	
	static Permission permission = Permission.EVENT;
	
	public AccountService accountService = new AccountService();
	public BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	
	public AccessControlListService aclService = new AccessControlListService();
	public BaseDao<AccessControlList> aclDao = new BaseDao<AccessControlList>(AccessControlList.class);
	
	public BaseDao<Application> applicationDao = new BaseDao<Application>(Application.class);
	
	public CalendarService calendarService = new CalendarService();
	public ChildBaseDao<Calendar, Account> calendarDao = new ChildBaseDao<Calendar, Account>(Calendar.class, Account.class);
	
	public EventAttributeService eventAttributeService = new EventAttributeService();
	public ChildBaseDao<EventAttribute, Account> eventAttributeDao = new ChildBaseDao<EventAttribute, Account>(EventAttribute.class, Account.class);
	
	public EventCategoryService eventCategoryService = new EventCategoryService();
	public ChildBaseDao<EventCategory, Account> eventCategoryDao = new ChildBaseDao<EventCategory, Account>(EventCategory.class, Account.class);
	
	public EventService eventService = new EventService();
	public ChildBaseDao<Event, Calendar> eventDao = new ChildBaseDao<Event, Calendar>(Event.class, Calendar.class);
	
	public PersonService personService = new PersonService();
	public ChildBaseDao<Person, Account> personDao = new ChildBaseDao<Person, Account>(Person.class, Account.class);
	public ChildBaseDao<Person, Application> personAppDao = new ChildBaseDao<Person, Application>(Person.class, Application.class);	
	public BaseDao<User> userTestDao = new BaseDao<User>(User.class);
	
	public User setUpUser() {
		OAuthService oauth = OAuthServiceFactory.getOAuthService();
		User user = null;
		try {
			user = oauth.getCurrentUser();
		} catch (OAuthRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public void setUp(Account userAccount, User user) {
		AccessControlList acl = new AccessControlList(permission.toString(), "true", "true", "true", "true", "true", "SUPERADMIN");
		aclDao.save(acl);
		Person p = new Person(userAccount, user.getUserId(), "test1", "email", "SUPERADMIN");
		personDao.save(p);
	}
	
	 public DateTime calcNextMonday(DateTime dateTime) {
        if (dateTime.getDayOfWeek() >= DateTimeConstants.MONDAY) {
            dateTime = dateTime.plusWeeks(1);
        }
        return dateTime.withDayOfWeek(DateTimeConstants.MONDAY);
    }
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter rw = response.getWriter();
		response.setContentType("text/plain");
		rw.println("hello");
		
		User user = this.setUpUser();
		
		Account account = new Account("Account1");
		accountDao.save(account);
		
		this.setUp(account, user);
		
		Calendar calendar1 = new Calendar("Testing Calendar1", account);
		calendarDao.save(calendar1);
		
		Person instructor1 = new Person( account, "0", "testing1", "testing1@test.com", "INSTRUCTOR"); 
		personDao.save(instructor1);
		
		EventCategory eventCategory1 = new EventCategory("Event Category1", account);
		eventCategoryDao.save(eventCategory1);
		
		EventAttribute eventAttribute1 = new EventAttribute("Event Attribute1", account);
		eventAttributeDao.save(eventAttribute1);


		Date dateStart = calcNextMonday(new DateTime()).withTime(0, 0, 0, 0).plusHours(1).toDate();
		Date dateEnd = new DateTime(dateStart).plusHours(2).toDate();
		
		Date finalRepeatWeeklyDate = new DateTime(dateEnd).plusWeeks(5).plusDays(1).toDate();
		List<Integer> daysOfWeek = Arrays.asList(1);
		
	    rw.println("repeat Monthly dates");
		Date finalDate = new DateTime(dateStart).plusMonths(6).plusDays(1).toDate();

		
		Event ev2 = new Event(calendar1, true, EventRepeatType.MONTHLY, new Integer(1), finalDate,  null, 
			daysOfWeek, null,  "summary", dateStart, dateEnd, new Integer(10), instructor1, 
			eventCategory1, eventAttribute1);
		
		eventDao.save(ev2);
		
		Date today = new DateTime().minusMonths(1).toDate();
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy kk:mm:ss zzz");
	    String fromDate = formatter.format(today);
		
		List<EventItem> events2 = new ArrayList<EventItem>();
		try {
			events2 = eventService.listEvents(account.getId(), calendar1.getId(), fromDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    for (EventItem event : events2 ) {
	    	rw.println(event);
	    }
		
	}
}