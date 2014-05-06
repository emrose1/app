package com.studio.bookings.entity;

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
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;

@Entity
public class AccessControlList {
    
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
    private UserType userType;
	
	@Index
	@Getter @Setter
    Permission permission;
    
	@Getter @Setter
	private boolean canView;
    
	@Getter @Setter
	private boolean canInsert;
    
	@Getter @Setter
	private boolean canUpdate;
    
	@Getter @Setter
	private boolean canDelete;
	
	public AccessControlList(){}
	
	public AccessControlList(String permission, String canView, String canInsert, String canUpdate, 
			String canDelete, String userType, Account account) {
		this.permission = 	Permission.valueOf(permission);
		this.canView = 		Boolean.valueOf(canView);
		this.canInsert = 	Boolean.valueOf(canInsert);
		this.canUpdate = 	Boolean.valueOf(canUpdate);
		this.canDelete = 	Boolean.valueOf(canDelete);
		this.userType = 	UserType.valueOf(userType);
		this.setAccount(account);
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
        sb.append(", CAN_VIEW=" + this.isCanView());
        sb.append(", CAN_INSERT=" + this.isCanInsert());
        sb.append(", CAN_UPDATE=" + this.isCanUpdate());
        sb.append(", CAN_DELETE=" + this.isCanDelete());
        sb.append("]");

        return sb.toString();
    }
}
