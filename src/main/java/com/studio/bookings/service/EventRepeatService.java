package com.studio.bookings.service;

import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;

import com.studio.bookings.entity.Event;
import com.studio.bookings.enums.EventRepeatType;

public class EventRepeatService {
	
	public Date findNextOccurrence(Event event, Date afterDate) {
        Date nextOccurrence = null;

        if (!event.getRepeatEvent()) {
            // non-repeating event
            nextOccurrence = null;
        } else if (event.getRepeatEvent() && event.getRepeatFinalDate() != null && afterDate.after(event.getRepeatFinalDate())) {
            // Event is already over
            nextOccurrence = null;
        } else if (afterDate.before(event.getStartDateTime())) {
            // First occurrence
            if (event.getRepeatType() == EventRepeatType.WEEKLY && !(isOnRecurringDay(event, event.getStartDateTime()))) {
                Date nextDay = new DateTime(event.getStartDateTime()).plusDays(1).toDate();
                nextOccurrence = findNextOccurrence(event, nextDay);
            }
            else {
                nextOccurrence = event.getStartDateTime();
            }
        } else {
            switch (event.getRepeatType()) {
                case DAILY:
                    nextOccurrence = findNextDailyOccurrence(event, afterDate);
                    break;
                case WEEKLY:
                    nextOccurrence = findNextWeeklyOccurrence(event, afterDate);
                    break;
                case MONTHLY:
                    nextOccurrence = findNextMonthlyOccurrence(event, afterDate);
                    break;
                case YEARLY:
                    nextOccurrence = findNextYearlyOccurrence(event, afterDate);
                    break;
                default:
                	nextOccurrence = null;
                	break;
                	
            }
        }

        if (isOnExcludedDay(event, nextOccurrence)) {
            // Skip this occurrence and go to the next one
            nextOccurrence = findNextOccurrence(event, nextOccurrence);
        }
        else if (nextOccurrence != null && event.getRepeatFinalDate() != null) {
        	if (event.getRepeatFinalDate().before(nextOccurrence) || event.getRepeatFinalDate().equals(nextOccurrence)) {
        		// Next occurrence happens after recurUntil date
        		nextOccurrence = null;
        	}
        }

        return nextOccurrence;
    }

    private Date findNextDailyOccurrence(Event event, Date afterDate) {
        DateTime nextOccurrence = new DateTime(event.getStartDateTime());

        int daysBeforeDate = Days.daysBetween(new DateTime(event.getStartDateTime()), new DateTime(afterDate)).getDays();

        double occurrencesBeforeDate = Math.floor(daysBeforeDate / event.getRepeatInterval());

        double daysToAdd = (occurrencesBeforeDate + 1) * event.getRepeatInterval();

        nextOccurrence = nextOccurrence.plusDays((int) daysToAdd);

        return nextOccurrence.toDate();
    }


    private Date findNextWeeklyOccurrence(Event event, Date afterDate) {
    	
    	// weeks between event start date and date after last event
        int weeksBeforeDate = Weeks.weeksBetween(new DateTime(event.getStartDateTime()), new DateTime(afterDate)).getWeeks();
       
        // number of occurrences in the weeks between event start date and date after last event
        int weekOccurrencesBeforeDate = (int) Math.floor(weeksBeforeDate / event.getRepeatInterval());

        // original start date
        DateTime lastOccurrence = new DateTime(event.getStartDateTime());
        // start date plus weeks of events so far
        lastOccurrence = lastOccurrence.plusWeeks(weekOccurrencesBeforeDate * event.getRepeatInterval());
        // gets the Monday date in the week of the last occurrence
        lastOccurrence = lastOccurrence.withDayOfWeek(MONDAY);

        DateTime nextOccurrence;
        
        // if last occurrence and date after last event are in same week, set next occurrence to one day after Monday
        // of that week to check for any repeatDaysOfWeek
        if (isInSameWeek(lastOccurrence.toDate(), afterDate)) {
            nextOccurrence = lastOccurrence.plusDays(1);
        }
        // if not in same in same week add a weekly repeat interval on top of last event
        else {
            nextOccurrence = lastOccurrence.plusWeeks(event.getRepeatInterval());
        }

        boolean occurrenceFound = false;

        while (!occurrenceFound) {
            if (nextOccurrence.toDate().after(afterDate) && isOnRecurringDay(event, nextOccurrence.toDate())) {
                occurrenceFound = true;
            }
            else {
                if (nextOccurrence.getDayOfWeek() == SUNDAY) {
                    // we're about to pass into the next week
                    nextOccurrence = nextOccurrence.withDayOfWeek(MONDAY).plusWeeks(event.getRepeatInterval());
                }
                else {
                    nextOccurrence = nextOccurrence.plusDays(1);
                }
            }
        }
        return nextOccurrence.toDate();
    }

    private Date findNextMonthlyOccurrence(Event event, Date afterDate) {
        DateTime nextOccurrence = new DateTime(event.getStartDateTime());

        int monthsBeforeDate = Months.monthsBetween(new DateTime(event.getStartDateTime()), new DateTime(afterDate)).getMonths();
        int occurrencesBeforeDate = (int) Math.floor(monthsBeforeDate / event.getRepeatInterval());
        nextOccurrence = nextOccurrence.plusMonths((occurrencesBeforeDate + 1) * event.getRepeatInterval());

        return nextOccurrence.toDate();
    }

    private Date findNextYearlyOccurrence(Event event, Date afterDate) {
        DateTime nextOccurrence = new DateTime(event.getStartDateTime());

        int yearsBeforeDate = Years.yearsBetween(new DateTime(event.getStartDateTime()), new DateTime(afterDate)).getYears();
        int occurrencesBeforeDate = (int) Math.floor(yearsBeforeDate / event.getRepeatInterval());
        nextOccurrence = nextOccurrence.plusYears((occurrencesBeforeDate + 1) * event.getRepeatInterval());

        return nextOccurrence.toDate();
    }


    private boolean isInSameWeek(Date date1, Date date2) {
        DateTime dateTime1 = new DateTime(date1);
        DateTime dateTime2 = new DateTime(date2);

        return ((Weeks.weeksBetween(dateTime1, dateTime2)).getWeeks() == 0);
    }

    private boolean isOnSameDay(Date date1, Date date2) {
        DateTime dateTime1 = new DateTime(date1);
        DateTime dateTime2 = new DateTime(date2);

       return ((Days.daysBetween(dateTime1, dateTime2)).getDays() == 0);
    }

    private boolean isOnRecurringDay(Event event, Date date) {
        int day = new DateTime(date).getDayOfWeek();
        List<Integer> repeatDays = event.getRepeatDaysOfWeek();
        if (repeatDays == null) {
        	return false;
        }
        return repeatDays.contains(day);
    }

    private boolean isOnExcludedDay(Event event, Date date) {
        date = (new DateTime(date)).withTime(0, 0, 0, 0).toDate();
        
        List<Date> excludeDays = event.getExcludeDays();
        if (excludeDays == null) {
        	return false;
        }
        return excludeDays.contains(date);
    }

}
