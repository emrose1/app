package com.studio.bookings.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Calendar {
	
	@Getter @Setter
	@Id Long id;
	
	public Long getId() {
		return this.id;
	}
	
	@Index Date dateCreated = new Date();
	@Index Date dateUpdated = new Date();
	
	public Calendar(){}
	
	public Calendar(String description) {
		this.description = description;
	}
	
	@Getter @Setter
	private String description;
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Calendar> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
	
	public String toString() {
		return this.description + ": " + this.id ;
	}


	/*public Ref<Calendar> getRef() {
    	if(id == null){
    		return null;
    	}
        return Ref.create(this.getClass(), id);
    }*/
}
