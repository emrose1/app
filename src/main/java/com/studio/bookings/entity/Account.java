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
public class Account {

	@Index
	@Getter @Setter
	@Id Long id;
	 
	@Getter @Setter
	private String name;
	
	@Index
	@Getter @Setter
	private AccountSettings accountSettings;
	
	public Account(){};
	
	public Account(String name) {
		this.name = name;
		this.accountSettings = new AccountSettings();
	}
	
	public String toString() {
		StringBuilder tmp = new StringBuilder();
		tmp.append(" {id:").append(id);
		return tmp.toString();
	}

	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Account> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }

}