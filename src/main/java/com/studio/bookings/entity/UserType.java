package com.studio.bookings.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserType implements Serializable{
    
	@Getter @Setter
	@Id private Long id;
	
	@Getter @Setter
    private String type;
	
	public UserType(){}
	
	public UserType(String type) {
		this.type = type;
	}
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<UserType> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ID=" + this.getId());
        sb.append(", TYPE=" + this.getType());
        sb.append("]");

        return sb.toString();
    }

}
