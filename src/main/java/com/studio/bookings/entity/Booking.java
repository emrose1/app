package com.studio.bookings.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class Booking {
	
	@Getter @Setter
	@Id Long id;
	
	@Parent
	@Load
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Person> userRef;
	
    public Person getUser() { 
    	return userRef.get(); 
    }
    
    public void setUser(Person value) { 
    	userRef = Ref.create(value); 
    }
    
    @Load
    @Index
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    Ref<Event> eventRef;
	
    public Event getEvent() { 
    	return eventRef.get(); 
    }
    
    public void setEvent(Event value) { 
    	eventRef = Ref.create(value); 
    }
    
    @Getter @Setter
    private Date dateBooked;
    
    @Getter @Setter
    private Boolean paymentReceived;
    
    public Booking(){}
    
    public Booking(Person user, Event event) {
    	this.dateBooked = new Date();
    	this.userRef = Ref.create(user);
    	this.eventRef = Ref.create(event);
    }
    
    public String toString() {
    	return eventRef.get() + " " + userRef.get();
    }

}
