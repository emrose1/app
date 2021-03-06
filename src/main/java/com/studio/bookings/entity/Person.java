package com.studio.bookings.entity;

import java.util.Date;

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
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;
import com.studio.bookings.enums.UserType;

@EqualsAndHashCode
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
	
    @Getter @Setter
    Date dateCreated;
    
    @Index
    @Getter @Setter
    String userId;
    
    @Getter @Setter
    private String username;
    
    @Getter @Setter
    String email;
    
    @Index
    @Getter @Setter
    public UserType userType;
    
    public Person(){}
    
    public Person(Account account, String userId, String username, String email, String userType) {
    	this.setAccount(account);
    	this.userId = userId;
    	this.username = username;
    	this.email = email;
    	this.userType = UserType.valueOf(userType);
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