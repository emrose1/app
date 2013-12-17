package com.studio.bookings.entity;

import java.io.Serializable;

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
    private String username;

    @Index
    @Getter @Setter
    private String password;
    
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
    
  /*  @Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Organisation> organisation;
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Ref<Organisation> getOrganisation() {
        return this.organisation;
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void setOrganisation(Ref<Organisation> organisation) {
        this.organisation = organisation;
    }*/
    
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