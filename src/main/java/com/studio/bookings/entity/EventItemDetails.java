package com.studio.bookings.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Embed
public class EventItemDetails {
	
	@Getter @Setter
	@Id Long id;
	
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
	private String organizer;
	
	@Getter @Setter (AccessLevel.PUBLIC)
	protected String summary;
	
	@Getter @Setter
	private String duration;
	
	@Getter @Setter
	private Integer maxAttendees;
	
	public EventItemDetails(){};
	
	public EventItemDetails(String organizer, String summary, String duration, Integer maxAttendees) {
		this.organizer = organizer;
		this.summary = summary;
		this.duration = duration;
		this.maxAttendees = maxAttendees;
	};
	
	public String toString() {
		return ("organizer: " + organizer + ", summary: " + summary);	
	}

}
