package com.studio.bookings.entity;

import lombok.Getter;
import lombok.Setter;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;

@Entity
public class AccessControlList  {
    
	@Getter @Setter
	@Id Long id;
	
	@Index
	@Getter @Setter
    private UserType userType;
	
	@Index
	@Getter @Setter
    private Permission permission;
    
	@Getter @Setter
	private Boolean canView;
    
	@Getter @Setter
	private Boolean canInsert;
    
	@Getter @Setter
	private Boolean canUpdate;
    
	@Getter @Setter
	private Boolean canDelete;
	
	public AccessControlList(){}
	
	public AccessControlList(String permission, String canView, String canInsert, String canUpdate, 
			String canDelete, String userType) {
		this.permission = 	Permission.valueOf(permission);
		this.canView = 		Boolean.valueOf(canView);
		this.canInsert = 	Boolean.valueOf(canInsert);
		this.canUpdate = 	Boolean.valueOf(canUpdate);
		this.canDelete = 	Boolean.valueOf(canDelete);
		this.userType = 	UserType.valueOf(userType);
	}
	
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Key<AccessControlList> getKey() {
    	if(id == null){
    		return null;
    	}
        return Key.create(this.getClass(), id);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ID=" + this.getId());
        sb.append(", PERMISSION=" + this.getPermission());
        sb.append(", CAN_VIEW=" + this.getCanView());
        sb.append(", CAN_INSERT=" + this.getCanInsert());
        sb.append(", CAN_UPDATE=" + this.getCanUpdate());
        sb.append(", CAN_DELETE=" + this.getCanDelete());
        sb.append("]");

        return sb.toString();
    }
}
