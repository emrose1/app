package com.studio.bookings;

import static com.studio.bookings.util.OfyService.ofy;

import java.util.Date;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.studio.bookings.entity.Event;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class TestingTest {

  /** */
  @SuppressWarnings("unused")
  private static Logger log = Logger.getLogger(TestingTest.class.getName());

  private final LocalServiceTestHelper helper =
          new LocalServiceTestHelper(
  new LocalDatastoreServiceTestConfig(),//.setAlternateHighRepJobPolicyClass(AlwaysApplyJobPolicy.class),
  new LocalMemcacheServiceTestConfig(),
  new LocalTaskQueueTestConfig());


    @BeforeMethod
      public void setUp() {
          helper.setUp();
      }

    @AfterMethod
      public void tearDown() {
          helper.tearDown();
      }


  /** */
  @Test
  public void testGenerateId() throws Exception {

    ObjectifyService.register(Event.class);

    // Note that 5 is not the id, it's part of the payload
    Event event = new Event("foo", "bar", new Date());
    Key<Event> k = ofy().save().entity(event).now();

    assert k.getKind().equals(event.getClass().getSimpleName());
    assert k.getId() == event.getId();

    Key<Event> created = Key.create(Event.class, k.getId());
    assert k.equals(created);

    Event fetched = ofy().load().key(k).now();

    assert fetched.getId().equals(k.getId());
    assert fetched.getSummary() == event.getSummary();
    assert fetched.getStartDate().equals(event.getStartDate());
  }

  /** */
  @Test
  public void testOverwriteId() throws Exception {
    ObjectifyService.register(Event.class);

    Event event = new Event("foo", "bar", new Date());
    Key<Event> k = ofy().save().entity(event).now();

    Event event2 = new Event(k.getId(), "bar", new Date());
    Key<Event> k2 = ofy().save().entity(event2).now();

    assert k2.equals(k);

    Event fetched = ofy().load().key(k).now();

    assert fetched.getId() == k.getId();
    assert fetched.getSummary() == event2.getSummary();
    assert fetched.getStartDate() == event2.getStartDate();
  }

}
