package com.studio.bookings.entity;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class EventCategory {
	
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
	
    @Index
	@Getter @Setter
	String name;
	
	public EventCategory(){}
	
	public EventCategory(String name, Calendar calendar) {
		this.calendarKey = calendar.getKey();
		this.name = name;
	}
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<EventCategory> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
	
	public String toString() {
		return this.name;
	}
	
}
