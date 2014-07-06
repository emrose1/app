package com.studio.bookings.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;
import com.studio.bookings.enums.EventRepeatType;

@EqualsAndHashCode
@Entity
public class Event {
	@Index
	@Getter @Setter
	@Id Long id;
	
	@Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Calendar> calendarRef;
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Calendar getCalendar() { 
    	return calendarRef.get(); 
    }
    
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void setCalendar(Calendar calendar) { 
    	calendarRef = Ref.create(calendar); 
    }
    
    private Set<Ref<Booking>> bookings;
	
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void addBooking(Ref<Booking> booking) {
    	this.bookings.add(booking);
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void removeBooking(Ref<Booking> booking) {
    	this.bookings.remove(booking);
    }

	@Getter @Setter
	private Boolean repeatEvent;
	
	@Getter @Setter
	EventRepeatType repeatType;
	
	@Getter @Setter
	Integer repeatInterval;
	
	@Getter @Setter
	Date repeatFinalDate;
	
	@Getter @Setter
	Integer repeatCount;
	
	@Getter @Setter
	List<Integer> repeatDaysOfWeek = new ArrayList<Integer>();
	
	@Getter @Setter
	List<Date> excludeDays = new ArrayList<Date>();
	
	@Load
	@Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<EventCategory> eventCategoryRef;
	
    public EventCategory getEventCategory() { 
    	return eventCategoryRef.get(); 
    }
    
    public void setEventCategory(EventCategory value) { 
    	eventCategoryRef = Ref.create(value); 
    }
	
    @Load
    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<EventAttribute> eventAttributeRef;
	
    public EventAttribute getEventAttribute() { 
    	return eventAttributeRef.get(); 
    }
    
    public void setEventAttribute(EventAttribute value) { 
    	eventAttributeRef = Ref.create(value); 
    }

	@Load
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Person> instructorRef;
	
    public Person getInstructor() { 
    	return instructorRef.get(); 
    }
    
    public void setInstructor(Person value) { 
    	instructorRef = Ref.create(value); 
    }
    
    @Getter @Setter (AccessLevel.PUBLIC)
	protected String summary;
    
    @Index
   	@Getter @Setter
   	private Date startDateTime;
	
   	@Getter @Setter
   	private Date endDateTime;
    
	@Getter @Setter
	private Integer maxAttendees;
	
	public Event() {};
	
	public Event(
			Calendar calendar, 
			Boolean repeatEvent, 
			EventRepeatType eventRepeatType, 
			Integer repeatInterval, 
			Date eventFinalRepeatDate,
			Integer eventRepeatCount, 
			List<Integer> repeatDaysOfWeek, 
			List<Date> excludeDays, 
			String summary, 
			Date startDateTime, 
			Date endDateTime, 
			Integer maxAttendees, 
			Person instructor, 
			EventCategory eventCategory, 
			EventAttribute eventAttribute) {
		
		this.setCalendar(calendar);
		this.repeatEvent = repeatEvent;
		this.repeatType = eventRepeatType;
		this.repeatInterval = repeatInterval;
		this.repeatFinalDate = eventFinalRepeatDate;
		this.repeatCount = eventRepeatCount;
		this.repeatDaysOfWeek = repeatDaysOfWeek;
		this.excludeDays =  excludeDays;
		this.summary = summary;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.maxAttendees = maxAttendees;
		this.instructorRef = Ref.create(instructor);
		this.eventCategoryRef = Ref.create(eventCategory);
		this.eventAttributeRef = Ref.create(eventAttribute);
	};
	
	public  int getDurationMinutes() {
		return Minutes.minutesBetween(new DateTime(startDateTime), new DateTime(endDateTime)).getMinutes();
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
		/*if(this.calendarKey == null){
			tmp.append(", calendar is null. }");
		}else{
			tmp.append(", calendar is not null. } ");
		}*/
		return tmp.toString();
	}
	
}
