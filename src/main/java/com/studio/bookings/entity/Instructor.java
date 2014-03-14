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
public class Instructor {
	
	@Index
	@Getter @Setter
	@Id Long id;
    
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String email;
	
	@Getter @Setter
	private String description;
	
	@Index Date dateCreated = new Date();
	
	public Instructor(){}
	
	public Instructor(String name, String email, String description) {
		this.name = name;
		this.email = email;
		this.description = description;
	};
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Instructor> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
	
	public String toString() {
		return "name: " + this.name + ", " + "email: " + this.email  + ", description " + this.description + ": " + this.id ;
	}
}