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
import com.studio.bookings.service.EventRepeatService;
import com.studio.bookings.service.EventService;

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
	
	@Getter
	Date repeatFinalDate;
	
	public void setRepeatFinalDate(Date finalRepeatDate){
		if(finalRepeatDate != null) {
			this.repeatFinalDate = finalRepeatDate;
		} else {
			this.repeatFinalDate = new DateTime(new Date()).plusYears(3000).toDate();
		}
	}
	
	@Getter
	Integer repeatCount;
	
	public void setRepeatCount(Integer eventRepeatCount) {
		EventRepeatService eventRepeatService = new EventRepeatService();
		
		if (eventRepeatCount != 0 && eventRepeatCount != null) {
	       Date repeatCountDate = this.startDateTime;
	       
	       this.repeatFinalDate = null;

	       for (int i = 0; i < eventRepeatCount; i++) {
	           repeatCountDate = eventRepeatService.findNextOccurrence(this, new DateTime(repeatCountDate).plusMinutes(1).toDate());
	       }
	       this.repeatCount = eventRepeatCount;
	       this.repeatFinalDate = new DateTime(repeatCountDate).plusMinutes(durationMinutes).toDate();
	    }
		else {
			this.repeatCount = 0;
		}
	}
	
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
	protected String title;
    
    @Index
   	@Getter @Setter
   	private Date startDateTime;
	
   	@Getter @Setter
   	private Date endDateTime;
   	
   	@Getter @Setter
   	private Boolean allDay;
   	
   	private Integer durationMinutes;
   	
   	public void setDurationMinutes() {
		this.durationMinutes = Minutes.minutesBetween(new DateTime(startDateTime), 
				new DateTime(endDateTime)).getMinutes();
	}
   	
   	public Integer getDurationMinutes() {
		return this.durationMinutes;
	}
    
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
			String title, 
			Date startDateTime, 
			Date endDateTime, 
			Boolean allDay,
			Integer maxAttendees, 
			Person instructor, 
			EventCategory eventCategory, 
			EventAttribute eventAttribute) {
		
		this.setDurationMinutes();
		this.setCalendar(calendar);
		this.repeatEvent = repeatEvent;
		this.repeatType = eventRepeatType;
		this.repeatInterval = repeatInterval;
		this.repeatDaysOfWeek = repeatDaysOfWeek;
		this.excludeDays =  excludeDays;
		this.title = title;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.maxAttendees = maxAttendees;
		this.instructorRef = Ref.create(instructor);
		this.eventCategoryRef = Ref.create(eventCategory);
		this.eventAttributeRef = Ref.create(eventAttribute);
		setRepeatFinalDate(eventFinalRepeatDate);
		setRepeatCount(eventRepeatCount);
		
	};
	
	public Event(Event event) {
		this.setCalendar(event.getCalendar());
		this.repeatEvent = event.getRepeatEvent();
		this.repeatType = event.getRepeatType();
		this.repeatInterval = event.getRepeatInterval();
		this.repeatFinalDate = event.getRepeatFinalDate();
		this.repeatCount = event.getRepeatCount();
		this.repeatDaysOfWeek = event.getRepeatDaysOfWeek();
		this.excludeDays =  event.getExcludeDays();
		this.title = event.getTitle();
		this.startDateTime = event.getStartDateTime();
		this.endDateTime = event.getEndDateTime();
		this.allDay = event.getAllDay();
		this.maxAttendees = event.getMaxAttendees();
		this.instructorRef = Ref.create(event.getInstructor());
		this.eventCategoryRef = Ref.create(event.getEventCategory());
		this.eventAttributeRef = Ref.create(event.getEventAttribute());
		this.setDurationMinutes();
	};
	
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
