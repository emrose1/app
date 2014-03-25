package com.studio.bookings.service;


import org.testng.annotations.Test;

import com.studio.bookings.dao.BaseDao;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.util.TestBase;

import static com.studio.bookings.util.TestObjectifyService.ofy;


public class CalendarServiceTest extends TestBase {
	
	public static AccountService accountService = new AccountService();
	CalendarService calendarService = new CalendarService();


	@Test
	public void insertCalendar() {
		Account account = accountService.insertAccount("Testing Account");
		Calendar calendar = calendarService.insertCalendar("Testing Calendar", account.getId());
		
		Calendar calendarFetched = calendarService.findCalendar(calendar.getId(), account.getId());
		assert calendar.getId().equals(calendarFetched.getId());
		assert calendar.getDescription().equals(calendarFetched.getDescription());
		assert calendar.getAccount().getId().equals(calendarFetched.getAccount().getId());
	}
	
}
