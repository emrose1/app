package com.studio.bookings.entity;

import lombok.EqualsAndHashCode;
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

@EqualsAndHashCode
@Entity
public class EventCategory {
	
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
	
    @Index
	@Getter @Setter
	String name;
	
	public EventCategory(){}
	
	public EventCategory(String name, Account account) {
		this.setAccount(account);
		this.name = name;
	}
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<EventCategory> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
	
	public String toString() {
		return this.name;
	}
	
}
