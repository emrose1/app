package com.studio.bookings.entity;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Owner {

	@Index
	@Getter @Setter
	@Id Long id;
	
	private List<Ref<Calendar>> calendars;
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Ref<Calendar>> getCalendars() {
    	return calendars;
    }
	
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void addCalendar(Ref<Calendar> calendar) {
    	this.calendars.add(calendar);
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void removeCalendar(Ref<Calendar> calendar) {
    	this.calendars.remove(calendar);
    }
	
	@Getter @Setter
	private String name;
	
	public Owner(){};
	
	public Owner(String name) {
		this.name = name;
		calendars = new ArrayList();
	}
	
	public String toString() {
		StringBuilder tmp = new StringBuilder();
		tmp.append(" {id:").append(id);
		if (calendars.size() > 0){
			for (Ref<Calendar> cal : calendars){
				tmp.append("cal: " + cal.get());
			}
		}
		return tmp.toString();
	}

	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Owner> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
}