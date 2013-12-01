package com.studio.bookings.entity;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class User {

    @Getter @Setter
	@Id private Long id;

    @Getter @Setter
    private String username;

    @Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<UserType> userType;
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Ref<UserType> getUserType() {
        return this.userType;
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void setUserType(Ref<UserType> userType) {
        this.userType = userType;
    }

    @Getter @Setter
    private String password;
    
    public User(){}
    
    public User(String username, String password, UserType userType) {
    	this.username = username;
    	this.password = password;
    	this.userType = Ref.create(userType);
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