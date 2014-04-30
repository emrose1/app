package com.studio.bookings.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.googlecode.objectify.annotation.Index;

public class AccountSettings {

	@Index
	@Getter
	@Setter
	Date repeatEventFinalDate;

	public AccountSettings() {
		this.repeatEventFinalDate = createFinalRepeatEventDate();
	}
	
	private Date createFinalRepeatEventDate() {
		int dt = new DateTime().plus(Period.years(5)).getYear();
		return new DateTime(dt, 12, 31, 23, 59).toDate();
	}
	
	public String toString() {
		return "Repeat Event Final Date: " + this.repeatEventFinalDate;
	}

}
