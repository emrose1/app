package com.studio.bookings.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Calendar {

	@Getter @Setter
	@Id Long id;

	@Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Account> accountRef;
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Account getAccount() { 
    	return accountRef.get(); 
    }
    
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void setAccount(Account account) { 
    	accountRef = Ref.create(account); 
    }

	@Getter
	@Setter
	private String description;

	@Index
	Date dateCreated = new Date();
	@Index
	Date dateUpdated = new Date();

	public Calendar() {}

	public Calendar(String description, Account account) {
		this.description = description;
		this.setAccount(account);
	};

	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Calendar> getKey() {
		if (id == null) {
			return null;
		}
		return Key.create(this.getClass(), id);
	}

	public String toString() {
		return this.description + ": " + this.id;
	}
}
