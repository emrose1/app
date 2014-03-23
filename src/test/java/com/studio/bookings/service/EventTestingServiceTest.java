package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.googlecode.objectify.Key;
import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.util.TestBase;

import static com.studio.bookings.util.TestObjectifyService.ofy;


public class EventTestingServiceTest extends TestBase {
	
	EventTestingService ets = new EventTestingService();
	BaseDao<Account> accountDao = new BaseDao<Account>(Account.class);
	
	@Test
	public void addAccount() {
		
		Account account = ets.insertAccount("Testing Account");
		Account accountFetched = ets.findAccount(account.getId());
		
		assert account.getId().equals(accountFetched.getId());
		assert account.getName().equals(accountFetched.getName());
		Assert.assertNotNull(account.getAccountSettings().getRepeatEventFinalDate());
		
		Date finalRepeatDate = new Date();
		accountFetched.getAccountSettings().setRepeatEventFinalDate(finalRepeatDate);
		account.getAccountSettings().setRepeatEventFinalDate(finalRepeatDate);
		
		assert account.getAccountSettings().getRepeatEventFinalDate()
			.compareTo(accountFetched.getAccountSettings().getRepeatEventFinalDate()) == 0;
	}
	
	@Test
	public void findAccount() {
		
		String accountName1 = "Account 1";
		String accountName2 = "Account 2";

		Account account1 = ets.insertAccount(accountName1);
		Account account2 = ets.insertAccount(accountName2);
		
		Account accountFetched1 = ets.findAccount(account1.getId());
		Account accountFetched2 = ets.findAccount(account2.getId());
		
		assert accountFetched1.getId().equals(account1.getId());
		assert accountFetched1.getName().equals(accountFetched1.getName());
		
		assert accountFetched2.getId().equals(account2.getId());
		assert accountFetched2.getName().equals(accountFetched2.getName());
		
		Assert.assertNotEquals(accountFetched1.getId(), accountFetched2.getId());
		Assert.assertNotEquals(accountFetched1.getName(), accountFetched2.getName());
	}
	
	@Test
	public void ListAccounts() {
		
		String accountName1 = "Account 1";
		String accountName2 = "Account 2";

		Account account1 = ets.insertAccount(accountName1);
		Account account2 = ets.insertAccount(accountName2);
		
		List<Account> objs = new ArrayList<Account>();
		objs.add(account1);
		objs.add(account2);
		
		List<Key<Account>> accounts = accountDao.save(objs);
		List<Account> accountsFetched = ets.listAccounts();
		
		assert accountsFetched.size() == accounts.size();
		assert accountsFetched.size() == 2;
	}
	
	@Test
	public void insertCalendar() {
		Account account = ets.insertAccount("Testing Account");
		Calendar calendar = ets.insertCalendar("Testing Calendar", account.getId());
	}
	
	/*@Test
	public void addInstructor() {  
		Account account = ets.insertAccount("Testing Calendar");
		ets.addInstructor("teach", "email@email.com", "description", account.getId());
	}
	 */ 
/*	@Test
	public void addEventItemDetails() {  
		List<EventItem> eventItems = new ArrayList<EventItem>();
		EventItemDetails eventItemDetails = new EventItemDetails(eventSummary, eventDuration, eventMaxAttendees);
	}
  
	@Test
	public void addEvent() {
		List<EventItem> eventItems = new ArrayList<EventItem>();
		Long instructor = new Long(inst2.getId());
		String summary = new String("summary1");
		String duration = new String ("1 hour");
		String maxAttendees = new String ("10");
		String repeatEvent = new String("true");
		String eventRepeatType = new String("DAILY");
		String finalRepeatEvent = new String ("12:20 05 03 2014");
	}*/
	  
	  /*
	  @Test
	  public void addEventItems() {
		  eventItems = event.createRepeatEventItems(eventStart, eventStartDate, eventKey, repeatType, 
		    		eventItemDetails, repeatBoolean, eventFinalDateTime);
		    
			Map<Key<EventItem>, EventItem> eventMap = eventItemDao.saveCollection(eventItems);
		    
		    List<Key<EventItem>> eventKeys = new ArrayList<Key<EventItem>>(eventMap.keySet());
		    
		    if(eventKeys.size() == eventItems.size()) {
	  }*/
}
