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
import com.googlecode.objectify.annotation.Parent;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.enums.UserType;

@Entity
public class AccessControlList {
    
	@Getter @Setter
	@Id private Long id;
	
	@Parent
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
	
	public AccessControlList(Permission permission, boolean canView, boolean canInsert, boolean canUpdate, 
			boolean canDelete, UserType userType, Account account) {
		this.permission = permission;
		this.canView = canView;
		this.canInsert = canInsert;
		this.canUpdate = canUpdate;
		this.canDelete = canDelete;
		this.userType = userType;
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
