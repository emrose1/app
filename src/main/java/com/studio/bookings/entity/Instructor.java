package com.studio.bookings.entity;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Subclass;

@Subclass(index=true)
public class Instructor extends User {
	
	@Getter @Setter
	private String firstname;
	
	@Getter @Setter
	private String lastname;
	
	@Getter @Setter
	private String description;
	
	public Instructor(){}
	
	public Instructor(String firstname, String lastname, String description) {
		this.description = description;
	};
	
	public String toString() {
		return "first name: " + this.firstname + " last name: " + this.lastname + " description: " + this.description;
	}
}