package com.studio.bookings.util;

import static com.studio.bookings.util.TestObjectifyService.ofy;

import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFilter;
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
import com.studio.bookings.service.AccessControlListService;
import com.studio.bookings.service.AccountService;
import com.studio.bookings.service.CalendarService;
import com.studio.bookings.service.EventAttributeService;
import com.studio.bookings.service.EventCategoryService;
import com.studio.bookings.service.EventService;
import com.studio.bookings.service.PersonService;

/**
 * All tests should extend this class to set up the GAE environment.
 * @see <a href="http://code.google.com/appengine/docs/java/howto/unittesting.html">Unit Testing in Appengine</a>
 *
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
public class TestBase {

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


	/** */
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(TestBase.class.getName());

	/** */
	private final LocalServiceTestHelper helper =
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	/** */

	@BeforeMethod
	public void setUp() {
		this.helper.setUp();
	}

	/** */
	@AfterMethod
	public void tearDown() {
		// This is normally done in ObjectifyFilter but that doesn't exist for tests
		ObjectifyFilter.complete();

		this.helper.tearDown();
	}

	/** Utility methods that puts, clears the session, and immediately gets an entity */
	protected <T> T putClearGet(T saveMe) {
		Key<T> key = ofy().save().entity(saveMe).now();

		try {
			Entity ent = ds().get(null, key.getRaw());
			System.out.println(ent);
		}
		catch (EntityNotFoundException e) { throw new RuntimeException(e); }

		ofy().clear();

		return ofy().load().key(key).now();
	}

	/** Get a DatastoreService */
	protected DatastoreService ds() {
		return DatastoreServiceFactory.getDatastoreService();
	}
}