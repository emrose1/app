package com.studio.bookings.entity;

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
public class EventCategory {

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
	
	@Index
	@Getter @Setter
	String name;
	
	public EventCategory(){}
	
	public EventCategory(String name, Calendar calendar) {
		this.calendarRef = Ref.create(calendar);
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
}
