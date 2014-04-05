package com.studio.bookings.entity;

import java.io.Serializable;
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
public class User implements Serializable {

    @Getter @Setter
	@Id private Long id;
    
    @Index
    @Getter @Setter
    private Account account;
    
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
    
    @Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<UserType> userTypeRef;
    

    public UserType getUserType() {
        return this.userTypeRef.get();
    }
    
    public void setUserType(UserType userType) {
        this.userTypeRef = Ref.create(userType);
    }
    
    public User(){}
    
    public User(String username, String password, UserType userType) {
    	this.username = username;
    	this.password = password;
    	this.userTypeRef = Ref.create(userType);
    	dateCreated = new Date();
    }

    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<User> getKey() {
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