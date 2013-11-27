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
import com.googlecode.objectify.annotation.Parent;

@Data
@Entity
public class Event {
	
	@Getter @Setter
	@Id Long id;
	
	@Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Calendar> calendarRef;
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Ref<Calendar> getCalendar() 
    {
        return this.calendarRef;
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void setCalendar(Ref<Calendar> cal) 
    {
        this.calendarRef = cal;
    }
    @Getter @Setter
    @Index Set<Key<User>> attendees;
    
    public void addAttendee(Key<User> attendee) {
    	this.attendees.add(attendee);
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
	
	@Getter @Setter
	private String numberOfAttendees;
	
	public Event() {};
	
	public Event(String organizer, String summary, Date eventStartDate, 
			Date eventEndDate, Integer maxAttendees, Calendar calendar) {
		this.organizer = organizer;
		this.summary = summary;
		this.startDate = eventStartDate;
		this.endDate = eventEndDate;
		this.maxAttendees = maxAttendees;
		this.calendarRef = Ref.create(calendar);
		this.attendees = new HashSet();
	};
	
	public Key<Event> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
	
	/*@Override
	public String toString() {
		StringBuilder tmp = new StringBuilder();
		tmp.append(" {id:").append(id);
		tmp.append(", summary:").append(this.summary);
		if(this.calendar == null){
			tmp.append(", calendar is null. }");
		}else{
			tmp.append(", calendar is not null. } ");
		}
		return tmp.toString();
	}*/
	
}
