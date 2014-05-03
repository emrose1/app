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
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;
import com.studio.bookings.enums.UserType;

@Entity
public class Person {

    @Getter @Setter
	@Id private Long id;
    
    @Parent
    @Load
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
    private String username;

    @Index
    @Getter @Setter
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private String password;
    
    @Index
    @Getter @Setter
    Date dateCreated;
    
    public UserType userType;
    
    public Person(){}
    
    public Person(String username, String password, String userType, Account account) {
    	this.username = username;
    	this.password = password;
    	this.userType = UserType.valueOf(userType);
    	this.setAccount(account);
    	dateCreated = new Date();
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Person> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }
    
    public String toString() {        
        StringBuffer sb = new StringBuffer();
        sb.append("[ID=" + this.getId());
        sb.append("[, USERNAME=" + this.getUsername());
        sb.append("]");
        return sb.toString();
    }
}