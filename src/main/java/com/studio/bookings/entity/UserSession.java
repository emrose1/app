package com.studio.bookings.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserSession implements Serializable {

    @Getter @Setter
	@Id private Long id;
    
	@Getter @Setter
    private Person user;
	
	@Getter @Setter
    private Date loginTime;

	public UserSession(){}
	
    public UserSession(Date loginTime, Person user) {
    	this.user = user;
    	this.loginTime = loginTime;
    }

}
