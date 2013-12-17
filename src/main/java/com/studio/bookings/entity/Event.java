package com.studio.bookings.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Event {
	
	@Getter @Setter
	@Id Long id;
	
	@Parent
	@Load
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Calendar> calendarRef;
	
    public Calendar getCalendar() { 
    	return calendarRef.get(); 
    }
    
    public void setCalendar(Calendar value) { 
    	calendarRef = Ref.create(value); 
    }
    
    @Load
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<EventCategory> eventCategoryRef;
	
    public EventCategory getEventCategory() { 
    	return eventCategoryRef.get(); 
    }
    
    public void setEventCategory(EventCategory value) { 
    	eventCategoryRef = Ref.create(value); 
    }
    
    @Load
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<EventAttribute> eventAttributeRef;
	
    public EventAttribute getEventAttributeRef() { 
    	return eventAttributeRef.get(); 
    }
    
    public void setEventAttribute(EventAttribute value) { 
    	eventAttributeRef = Ref.create(value); 
    }
    
    @Getter @Setter
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    @Index 
    @Load
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
	private String organizer;
	
	@Getter @Setter (AccessLevel.PUBLIC)
	private String summary;
	
	@Getter @Setter
	private Date startDate;
	
	@Getter @Setter
	private Date endDate;
	
	@Getter @Setter
	private String allDay;
	
	@Getter @Setter
	private Integer maxAttendees;
	
	public Event() {};
	
	public Event(String organizer, String summary, Date eventStartDate, 
			Date eventEndDate, Integer maxAttendees, Calendar calendar, 
			EventCategory eventCategory, EventAttribute eventAttribute) {
		this.organizer = organizer;
		this.summary = summary;
		this.startDate = eventStartDate;
		this.endDate = eventEndDate;
		this.maxAttendees = maxAttendees;
		this.calendarRef = Ref.create(calendar);
		this.bookings = new HashSet();
		this.eventCategoryRef = Ref.create(eventCategory);
		this.eventAttributeRef = Ref.create(eventAttribute);
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
		tmp.append(", summary:").append(this.summary);
		if(this.calendarRef == null){
			tmp.append(", calendar is null. }");
		}else{
			tmp.append(", calendar is not null. } ");
		}
		return tmp.toString();
	}
	
}
