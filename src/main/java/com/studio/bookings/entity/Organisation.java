package com.studio.bookings.entity;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Organisation {
	@Getter @Setter
	@Id private Long id;
	
	@Getter @Setter
    private String name;
	
	public Organisation(){}
	
	public Organisation(String name) {
		this.name = name;
	}
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<Organisation> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ID=" + this.getId());
        sb.append(", NAME=" + this.getName());
        sb.append("]");

        return sb.toString();
    }

}
