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


@Entity
@EqualsAndHashCode(exclude={"id"})
public class Account   {

	@Getter @Setter
	@Id Long id;
	
/*	@Parent
	@Load
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Ref<Application> applicationRef;
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Application getApplication() { 
    	return applicationRef.get(); 
    }
    
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public void setApplication(Application application) { 
    	applicationRef = Ref.create(application); 
    }*/

	@Getter @Setter
	private String name;
	
	@Index
	@Getter @Setter
	private AccountSettings accountSettings;
	
	@Index
	Date dateCreated;
	
	public Account(){};
	
	public Account(String name) {
		this.name = name;
		this.accountSettings = new AccountSettings();
		dateCreated = new Date();
		//this.setApplication(app);
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

}