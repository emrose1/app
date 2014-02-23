package com.studio.bookings.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Event {
	
	@Index
	@Getter @Setter
	@Id Long id;
	
    
    @Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Key<Calendar> calendarKey;
	
    public Key<Calendar> getCalendarKey() { 
    	return this.calendarKey; 
    }
    
    public void setCalendarKey(Key<Calendar> value) { 
    	this.calendarKey = value; 
    }

	@Getter @Setter
	private Boolean repeatEvent;
	
	@Getter @Setter
	EventRepeatType eventRepeatType;
	
	@Getter @Setter
	Date lastInstanceAdded;
	
	@Getter @Setter
	Date eventFinalRepeatDate;
	
	public Event() {};
	
	public Event(Calendar calendar, Boolean repeatEvent, EventRepeatType eventRepeatType) {
		this.calendarKey = calendar.getKey();
		this.repeatEvent = repeatEvent;
		this.eventRepeatType = eventRepeatType;
	};
	
	public List<EventItem> createRepeatEventItems(Date eventStartDateTime, Long startDate, Key<Event> parentEvent, 
			EventRepeatType repeatType, EventItemDetails eventItemDetails, Boolean repeatBoolean, Date eventFinalDateTime) 
			
		{
		
		DateTime finalRepeatDate = new DateTime(eventFinalDateTime);
		DateTime startDateTime = new DateTime(eventStartDateTime);
		
		List<EventItem> els = new ArrayList<EventItem>();
		
		if (repeatBoolean) {
			
			Period periodBefore = Period.months(-1);
			Period periodIncrement = Period.months(1);
			
			switch (repeatType) {
	            case DAILY:
	            	periodBefore = Period.days(-1);
	    			periodIncrement = Period.days(1);
	                break;
	                    
	            case WEEKLY:
	            	periodBefore = Period.weeks(-1);
	    			periodIncrement = Period.weeks(1);
	                break;
	                         
	            case MONTHLY:
	            	periodBefore = Period.months(-1);
	    			periodIncrement = Period.months(1);
	                break;
	        }
			
			while (startDateTime.isBefore(finalRepeatDate.plus(periodBefore)) || startDateTime.isEqual(finalRepeatDate.plus(periodBefore))) {
				startDateTime = startDateTime.plus(periodIncrement);
				
				Date startTime = startDateTime.withTimeAtStartOfDay().toDate();

				EventItem ei = new EventItem(startDateTime.toDate(), startTime.getTime(), parentEvent, eventItemDetails);
				els.add(ei);
			}
			
		} else {
			els.add(new EventItem(startDateTime.toDate(), startDate, parentEvent, eventItemDetails));
		}
		return els;
	}	
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Event> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
	
	@Override
	public String toString() {
		StringBuilder tmp = new StringBuilder();
		tmp.append(" {id:").append(id);
		tmp.append(", repeatEvent:").append(this.repeatEvent);
		if(this.calendarKey == null){
			tmp.append(", calendar is null. }");
		}else{
			tmp.append(", calendar is not null. } ");
		}
		return tmp.toString();
	}
	
}
