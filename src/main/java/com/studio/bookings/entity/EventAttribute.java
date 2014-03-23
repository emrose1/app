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
public class EventAttribute {
	
	@Index
	@Getter @Setter
	@Id Long id;
	
	@Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Key<Calendar> calendarKey;
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<Calendar> getCalendar() { 
    	return this.calendarKey; 
    }
    
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void setCalendarKey(Key<Calendar> value) { 
    	this.calendarKey = value; 
    }
    
    @Index
	@Getter @Setter
	String name;
	
	public EventAttribute(){}
	
	public EventAttribute(String name, Calendar calendar) {
		this.calendarKey = calendar.getKey();
		this.name = name;
	}
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<EventAttribute> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
	
	public String toString() {
		return this.name;
	}
}
