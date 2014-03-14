package com.studio.bookings.entity;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Account {

	@Index
	@Getter @Setter
	@Id Long id;
	
    
    private List<Ref<Instructor>> instructors;
	
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public List<Ref<Instructor>> getInstructors() {
    	return instructors;
    }
	
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void addInstructors(Ref<Instructor> instructor) {
    	this.instructors.add(instructor);
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void removeInstructors(Ref<Instructor> instructor) {
    	this.instructors.remove(instructor);
    }
    
    
	@Getter @Setter
	private String name;
	
	public Account(){};
	
	public Account(String name) {
		this.name = name;
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

	public void addInstructor(Ref<Instructor> create) {
		// TODO Auto-generated method stub
		
	}
}