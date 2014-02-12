package com.studio.bookings.entity;

import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class EventItem {
	
	@Getter @Setter
	@Id Long id;
    
    @Parent
	@Load
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Key<Event> eventKey;
	
    public Key<Event> getEventKey() { 
    	return this.eventKey; 
    }
    
    public void setEventKey(Key<Event> value) { 
    	this.eventKey = value; 
    }
    
    @Getter @Setter
    EventItemDetails eventItemDetails;

    @Getter @Setter
	private Long startDate;
 
	@Getter @Setter
	private Date startDateTime;
	
	@Getter @Setter
	Date lastInstanceAdded;
    
	private Set<Ref<Booking>> bookings;
	
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void addBooking(Ref<Booking> booking) {
    	this.bookings.add(booking);
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void removeBooking(Ref<Booking> booking) {
    	this.bookings.remove(booking);
    }
    
    public EventItem(){};
	
	public EventItem(Date eventStartDateTime, Long startDate, Key<Event> parentEvent, EventItemDetails eventItemDetails) {
		this.startDateTime = eventStartDateTime;
		this.startDate = new Long(startDate);
		this.eventKey = parentEvent;
		this.eventItemDetails = eventItemDetails;
	};

	public String toString() {
		return (id + ": " + startDateTime + " ParentKey: " + this.getEventKey());	
	}
}
