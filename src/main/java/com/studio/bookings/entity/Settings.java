package com.studio.bookings.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Settings {

	@Getter @Setter
	@Id Long id;
	
	@Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Key<Owner> ownerKey;
	
    public Key<Owner> getOwnerKey() { 
    	return this.ownerKey; 
    }
    
    public void setOwnerKey(Key<Owner> value) { 
    	this.ownerKey = value; 
    }
	
	@Getter
	Date repeatEventFinalDate;
	
	private void setRepeatEventFinalDate() {
		int dt = new DateTime().plus(Period.years(5)).getYear();
		this.repeatEventFinalDate = new DateTime(dt, 12, 31, 23, 59) .toDate();			
	}
	
	public Settings(){};

	public Settings(Key<Owner> ownerKey) {
		setRepeatEventFinalDate();
		this.ownerKey = ownerKey;
	}
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Settings> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
	
	public String toString() {
		return "Repeat Event Final Date: " + this.repeatEventFinalDate;
	}
	
	
}
